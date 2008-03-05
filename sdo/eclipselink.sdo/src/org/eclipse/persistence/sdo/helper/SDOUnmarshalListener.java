/*******************************************************************************
 * Copyright (c) 1998, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.sdo.helper;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Sequence;
import commonj.sdo.helper.HelperContext;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.persistence.sdo.SDOChangeSummary;
import org.eclipse.persistence.sdo.SDOConstants;
import org.eclipse.persistence.sdo.SDODataObject;
import org.eclipse.persistence.sdo.SDOSequence;
import org.eclipse.persistence.sdo.SDOType;
import org.eclipse.persistence.exceptions.SDOException;
import org.eclipse.persistence.internal.oxm.XMLConversionManager;
import org.eclipse.persistence.oxm.XMLRoot;
import org.eclipse.persistence.oxm.XMLUnmarshaller;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p><b>Purpose</b>: Implementation of XMLUnmarshalListener used when unmarshalling XML to XMLDocuments
 * <p><b>Responsibilities</b>:<ul>
 * <li> When creating a DataObject we need to call setType and setHelperContext with the appropriate values
 * <li> When we are finished Unmarshalling the root object we need to set up the ChangeSummary objects.
 * ChangeSummaries have xpaths to other parts of the documents so the rest of the objects need to be built before we process the ChangeSummaries
 * </ul>
 */
public class SDOUnmarshalListener extends SDOCSUnmarshalListener {
    private List changeSummaries;

    public SDOUnmarshalListener(HelperContext aContext) {
        super(aContext);
        initialize();
    }

    public void beforeUnmarshal(Object target, Object parent) {
        //Setting the helperContext and type on the DataObject or ChangeSummary
        super.beforeUnmarshal(target, parent);
    }

    /**
     * @param target assumed to be non-null
     * @param parent may be null, indicating target is root object
     */
    public void afterUnmarshal(Object target, Object parent) {
        SDODataObject targetDataObject;
        // assume target is DataObject or ChangeSummary
        try {
            targetDataObject = (SDODataObject)target;
        } catch (ClassCastException ccex) {
            // each time we hit a ChangeSummary store it to process later and set its root 
            // object - this is because we can't fully process the cs's because they have 
            // references that can't be resolved until the full tree is built
            ((SDOChangeSummary)target).setRootDataObject((DataObject)parent);
            changeSummaries.add(target);
            return;
        }
        
        // if getType is sequenced, then update values to settings map
        if (targetDataObject.getType().isSequenced()) {
            ((SDOSequence)(targetDataObject.getSequence())).afterUnmarshal();
        }
        
        // if parent is null we are back to the root object 
        // the last object that will hit the afterUnmarshal method
        if (parent == null) {
            SDOChangeSummary nextCS = null;
            XMLUnmarshaller unmarshaller = ((SDOXMLHelper)aHelperContext.getXMLHelper()).getXmlContext().createUnmarshaller();
            for (int i = 0; i < changeSummaries.size(); i++) {
                nextCS = (SDOChangeSummary)changeSummaries.get(i);
                // LOGGING
                nextCS.setLogging(nextCS.isLoggingMapping());
                // CREATES
                // For each xpath in the create attribute convert it to an sdo path and execute it against the root
                // dataobject to get the dataobject being pointed to and set that dataobject to be created
                List xpaths = nextCS.getCreatedXPaths();
                String nextXPath = null;
                SDODataObject nextCreatedDO = null;
                for (int j = 0; j < xpaths.size(); j++) {
                    nextXPath = (String)xpaths.get(j);
                    nextCreatedDO = (SDODataObject)targetDataObject.getDataObject(convertXPathToSDOPath(nextXPath));
                    if (nextCreatedDO != null) {
                        nextCreatedDO._setCreated(true);
                        nextCS.getOldContainers().remove(nextCreatedDO);
                    } else {
                        throw SDOException.errorProcessingXPath(nextXPath);
                    }
                }
                //clear the createxpaths list that was read in from XML                
                nextCS.setCreatedXPaths(null);
                //MODIFIED                
                List modifiedDoms = nextCS.getModifiedDoms();
                Element nextNode = null;
                String refValue = null;
                SDODataObject nextModifiedDO;
                for (int j = 0; j < modifiedDoms.size(); j++) {
                    nextNode = (Element)modifiedDoms.get(j);
                    refValue = nextNode.getAttributeNS(SDOConstants.SDO_URL, SDOConstants.CHANGESUMMARY_REF);
                    if ((refValue == null) || (refValue.length() == 0)) {
                        throw SDOException.missingRefAttribute();
                    }
                    //nextModifiedDO is the real modified current data object
                    nextModifiedDO = (SDODataObject)targetDataObject.getDataObject(convertXPathToSDOPath(refValue));
                    String unsetValue = nextNode.getAttributeNS(SDOConstants.SDO_URL, SDOConstants.CHANGESUMMARY_UNSET);
                    List unsetValueList = new ArrayList();
                    if ((unsetValue != null) && (unsetValue.length() > 0)) {
                        unsetValueList = (List)XMLConversionManager.getDefaultXMLManager().convertObject(unsetValue, List.class);
                    }
                    if (nextModifiedDO != null) {
                        nextModifiedDO._setModified(true);
                        SDOCSUnmarshalListener listener = new SDOCSUnmarshalListener(((SDOType)nextModifiedDO.getType()).getHelperContext(), true);
                        unmarshaller.setUnmarshalListener(listener);
                        unmarshaller.getProperties().put("sdoHelperContext", aHelperContext);
                        unmarshaller.setUnmappedContentHandlerClass(SDOUnmappedContentHandler.class);                        
                        Object unmarshalledNode = unmarshaller.unmarshal(nextNode, ((SDOType)nextModifiedDO.getType()).getXmlDescriptor().getJavaClass());
                        //unmarshalledDO is the modified dataobject from the changesummary xml
                        DataObject unmarshalledDO = null;
                        // Assumption: unmarshalledNode should always be either an instance of XMLRoot or DataObject                        
                        if (unmarshalledNode instanceof XMLRoot) {
                            unmarshalledDO = (DataObject)((XMLRoot)unmarshalledNode).getObject();
                        } else if (unmarshalledNode instanceof DataObject) {
                            unmarshalledDO = (DataObject)unmarshalledNode;
                        }
                        List modifiedProps = new ArrayList();
                        NodeList children = nextNode.getChildNodes();
                        for (int p = 0; p < children.getLength(); p++) {
                            Node n = children.item(p);
                            if (n.getNodeType() == Node.ELEMENT_NODE) {
                                String propName = n.getLocalName();
                                Property nextProp = unmarshalledDO.getInstanceProperty(propName);
                                if (nextProp == null) {
                                    nextProp = aHelperContext.getTypeHelper().getOpenContentProperty(n.getNamespaceURI(), propName);
                                }
                                if (!modifiedProps.contains(nextProp)) {
                                    modifiedProps.add(nextProp);
                                }
                            }
                        }
                        //instead of iterating over all props can we just check elements in cs and get appropriate properties from DO
                        for (int k = 0; k < modifiedProps.size(); k++) {
                            Property nextProp = (Property)modifiedProps.get(k);
                            if (!nextProp.getType().isDataType()) {
                                if (nextProp.isMany()) {
                                    //original value is the list from the changesummary xml
                                    List originalValue = unmarshalledDO.getList(nextProp);
                                    List newList = new ArrayList();
                                    List toDelete = new ArrayList();
                                    List indexsToDelete = new ArrayList();
                                    for (int l = 0; l < originalValue.size(); l++) {
                                        //TODO: need to handle "already set" existing value in the many case
                                        SDODataObject nextInList = (SDODataObject)originalValue.get(l);
                                        String sdoRef = nextInList._getSdoRef();
                                        if (sdoRef != null) {
                                            //if sdoRef is not null then object is modified                                                                                                                    
                                            newList.add(targetDataObject.getDataObject(convertXPathToSDOPath(sdoRef)));
                                        } else {
                                            //if sdo ref is null there is a deleted object                                                                                                                                                                                                  
                                            toDelete.add(nextInList);
                                            indexsToDelete.add(new Integer(l));
                                            newList.add(nextInList);
                                        }
                                    }
                                    //lw is the list from the real current data object
                                    ListWrapper lw = ((ListWrapper)nextModifiedDO.getList(nextProp));
                                    if (indexsToDelete.size() > 0) {
                                        //after this loop, lw will have the entire list when logging was turned on                                        
                                        nextCS.pauseLogging();
                                        for (int m = 0; m < indexsToDelete.size(); m++) {
                                            int toDeleteIndex = ((Integer)indexsToDelete.get(m)).intValue();
                                            SDODataObject nextToDelete = (SDODataObject)toDelete.get(m);
                                            lw.add(toDeleteIndex, nextToDelete);
                                        }
                                        nextCS.setPropertyInternal(nextModifiedDO, nextProp, lw);
                                        SDOSequence nextSeq = ((SDOSequence)nextCS.getOriginalSequences().get(nextModifiedDO));
                                        nextCS.resumeLogging();
                                        nextModifiedDO._setModified(true);
                                        for (int m = indexsToDelete.size() - 1; m >= 0; m--) {
                                            int toDeleteIndex = ((Integer)indexsToDelete.get(m)).intValue();
                                            SDODataObject nextToDelete = (SDODataObject)toDelete.get(m);
                                            if(nextSeq != null){
                                               nextSeq.addSettingWithoutModifyingDataObject(-1, nextProp, nextToDelete);
                                            }
                                            nextToDelete.resetChanges();

                                            lw.remove(toDeleteIndex);
                                        }
                                    }
                                    nextCS.getOriginalElements().put(lw, newList);
                                } else {
                                    SDODataObject value = (SDODataObject)unmarshalledDO.getDataObject(nextProp);
                                    if (value != null) {
                                        String sdoRef = value._getSdoRef();
                                        if (sdoRef != null) {
                                            //modified                                                
                                            nextModifiedDO._setModified(true);
                                        } else {
                                            //deleted       
                                            //TODO: should be able to clean this up
                                            value._setChangeSummary(nextCS);
                                            nextModifiedDO._setModified(true);
                                            nextCS.pauseLogging();
                                            boolean wasSet = nextModifiedDO.isSet(nextProp);

                                            Object existingValue = nextModifiedDO.get(nextProp);
                                            // grab index of nextProp's Setting for use during setting below
                                            Sequence nextModifiedDOSequence = nextModifiedDO.getSequence();
                                            int settingIdx = -1; 
                                            if (nextModifiedDOSequence != null) {
                                                settingIdx = ((SDOSequence)nextModifiedDOSequence).getIndexForProperty(nextProp); 
                                            }
                                            value._setContainmentPropertyName(null);
                                            value._setContainer(null);
                                            nextModifiedDO.set(nextProp, value);
                                            nextCS.setPropertyInternal(nextModifiedDO, nextProp, value);
                                            SDOSequence nextSeq = ((SDOSequence)nextCS.getOriginalSequences().get(nextModifiedDO));
                                            if(nextSeq != null){
                                              nextSeq.addSettingWithoutModifyingDataObject(-1, nextProp, value);
                                            }
      
  
                                            nextCS.resumeLogging();
                                            nextModifiedDO._setModified(true);

                                            value.resetChanges();
                                            value.delete();
                                            if (wasSet) {
                                                // need to add at the right pos in the list, not at the end
                                                nextModifiedDO.set(nextProp, existingValue, false);
                                                if (settingIdx != -1) {
                                                    ((SDOSequence)nextModifiedDO.getSequence()).addSettingWithoutModifyingDataObject(settingIdx, nextProp, existingValue);
                                                }
                                            } else {
                                                nextModifiedDO.unset(nextProp);
                                            }
                                        }
                                    } else {
                                        nextModifiedDO._setModified(true);
                                        nextCS.setPropertyInternal(nextModifiedDO, nextProp, null);
                                    }
                                }
                            } else {
                                nextModifiedDO._setModified(true);
                                Object value = unmarshalledDO.get(nextProp);
                                //lw is the list from the real current data object              
                                
                                if(nextProp.isMany()){                                       
                                                                  
                                  Property theProp = nextModifiedDO.getInstanceProperty(nextProp.getName());
                                  if(theProp == null){
                                    Property newProp = nextModifiedDO.defineOpenContentProperty(nextProp.getName(), new ArrayList(), nextProp.getType());
                                    nextModifiedDO.set(newProp, new ArrayList());
                                    theProp = newProp;
                                  }
                                  List lw = nextModifiedDO.getList(theProp.getName());                                                                    
                                  nextCS.setPropertyInternal(nextModifiedDO, theProp, lw);
                                  nextCS.getOriginalElements().put(lw, ((ListWrapper)value).getCurrentElements());
                                }else{
                                  nextCS.setPropertyInternal(nextModifiedDO, nextProp, value);  
                                }
                            }
                        }
                        for (int k = 0; k < unsetValueList.size(); k++) {
                            Property nextProp = unmarshalledDO.getInstanceProperty((String)unsetValueList.get(k));
                            if (nextProp != null) {
                                Object oldValue = null;
                                if (nextProp.getType().isDataType() || nextProp.isMany()) {
                                    //to get default
                                    oldValue = unmarshalledDO.get(nextProp);
                                }
                                nextModifiedDO._setModified(true);
                                nextCS.setPropertyInternal(nextModifiedDO, nextProp, oldValue);
                                nextCS.unsetPropertyInternal(nextModifiedDO, nextProp);
                            } else {
                                nextProp = nextModifiedDO.getInstanceProperty((String)unsetValueList.get(k));
                                nextModifiedDO._setModified(true);
                                nextCS.setPropertyInternal(nextModifiedDO, nextProp, null);
                                nextCS.unsetPropertyInternal(nextModifiedDO, nextProp);
                            }
                        }
                    } else {
                        throw SDOException.errorProcessingXPath(refValue);
                    }
                }
                //clear modified doms list
                nextCS.setModifiedDoms(null);
                //clear deleted xpaths list
                nextCS.setDeletedXPaths(null);
            }
            // reset changeSummary list - we are done with it
            initialize();
        }
    }

    private void initialize() {
        changeSummaries = new ArrayList();
    }

    private String convertXPathToSDOPath(String xpath) {
        if ((xpath == null) || (xpath.length() < SDOConstants.SDO_CHANGESUMMARY_REF_PATH_PREFIX_LENGTH) ||//
                !xpath.startsWith(SDOConstants.SDO_CHANGESUMMARY_REF_PATH_PREFIX)) {
            throw SDOException.errorProcessingXPath(xpath);
        }
        if (xpath.equals("#/")) {
            return "/";
        } else if (xpath.startsWith("#/")) {
            return xpath.substring(2, xpath.length());
        } else {
            // remove the sdo ref prefix only "#", leave the root path identifier "/"        
            return xpath.substring(1, xpath.length());
        }
    }
}
