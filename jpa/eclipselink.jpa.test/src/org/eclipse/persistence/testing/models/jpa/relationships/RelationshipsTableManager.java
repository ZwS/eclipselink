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
package org.eclipse.persistence.testing.models.jpa.relationships;

import org.eclipse.persistence.tools.schemaframework.FieldDefinition;
import org.eclipse.persistence.tools.schemaframework.TableCreator;
import org.eclipse.persistence.tools.schemaframework.TableDefinition;

public class RelationshipsTableManager extends TableCreator {

    public static TableCreator tableCreator;

    public RelationshipsTableManager() {
        setName("Relationships");
        addTableDefinition(buildCMP3_CUSTOMERTable());
        addTableDefinition(buildCMP3_ITEMTable());
        addTableDefinition(buildCMP3_ISOLATEDITEMTable());
        addTableDefinition(buildCMP3_ORDERTable());
        //addTableDefinition(buildCMP3_ORDER_SEQTable());
        addTableDefinition(buildCMP3_SALESPERSONTable());
        addTableDefinition(buildCUSTOMER_CUSTOMERTable());
        addTableDefinition(buildCMP3_ENTITYATable());
        addTableDefinition(buildCMP3_ENTITYBTable());
        addTableDefinition(buildCMP3_ENTITYA_ENTITYBTable());
        addTableDefinition(buildCMP3_ENTITYCTable());
        addTableDefinition(buildCMP3_ENTITYDTable());
        addTableDefinition(buildCMP3_UNIDIR_ENTITYC_ENTITYDTable());
    }
        
    public static TableCreator getCreator(){
        if (RelationshipsTableManager.tableCreator == null){
            RelationshipsTableManager.tableCreator = new RelationshipsTableManager();
        }
        return RelationshipsTableManager.tableCreator;
    }
    
    public static TableDefinition buildCMP3_CUSTOMERTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_CUSTOMER");

        FieldDefinition fieldCITY = new FieldDefinition();
        fieldCITY.setName("CITY");
        fieldCITY.setTypeName("VARCHAR2");
        fieldCITY.setSize(80);
        fieldCITY.setSubSize(0);
        fieldCITY.setIsPrimaryKey(false);
        fieldCITY.setIsIdentity(false);
        fieldCITY.setUnique(false);
        fieldCITY.setShouldAllowNull(true);
        table.addField(fieldCITY);

        FieldDefinition fieldCUST_ID = new FieldDefinition();
        fieldCUST_ID.setName("CUST_ID");
        fieldCUST_ID.setTypeName("NUMBER");
        fieldCUST_ID.setSize(15);
        fieldCUST_ID.setSubSize(0);
        fieldCUST_ID.setIsPrimaryKey(true);
        fieldCUST_ID.setIsIdentity(false);
        fieldCUST_ID.setUnique(false);
        fieldCUST_ID.setShouldAllowNull(false);
        table.addField(fieldCUST_ID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        FieldDefinition field10 = new FieldDefinition();
        field10.setName("CUST_VERSION");
        field10.setTypeName("NUMERIC");
        field10.setSize(15);
        field10.setShouldAllowNull(true );
        field10.setIsPrimaryKey(false );
        field10.setUnique(false );
        field10.setIsIdentity(false );
        table.addField(field10);

        return table;
    }

    public static TableDefinition buildCMP3_ISOLATEDITEMTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ISOLATED_ITEM");

        FieldDefinition fieldDESCRIPTION = new FieldDefinition();
        fieldDESCRIPTION.setName("DESCRIPTION");
        fieldDESCRIPTION.setTypeName("VARCHAR2");
        fieldDESCRIPTION.setSize(80);
        fieldDESCRIPTION.setSubSize(0);
        fieldDESCRIPTION.setIsPrimaryKey(false);
        fieldDESCRIPTION.setIsIdentity(false);
        fieldDESCRIPTION.setUnique(false);
        fieldDESCRIPTION.setShouldAllowNull(true);
        table.addField(fieldDESCRIPTION);

        FieldDefinition fieldITEM_ID = new FieldDefinition();
        fieldITEM_ID.setName("ID");
        fieldITEM_ID.setTypeName("NUMBER");
        fieldITEM_ID.setSize(15);
        fieldITEM_ID.setSubSize(0);
        fieldITEM_ID.setIsPrimaryKey(true);
        fieldITEM_ID.setIsIdentity(false);
        fieldITEM_ID.setUnique(false);
        fieldITEM_ID.setShouldAllowNull(false);
        table.addField(fieldITEM_ID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        return table;
    }

    public static TableDefinition buildCMP3_ITEMTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ITEM");

        FieldDefinition fieldDESCRIPTION = new FieldDefinition();
        fieldDESCRIPTION.setName("DESCRIPTION");
        fieldDESCRIPTION.setTypeName("VARCHAR2");
        fieldDESCRIPTION.setSize(80);
        fieldDESCRIPTION.setSubSize(0);
        fieldDESCRIPTION.setIsPrimaryKey(false);
        fieldDESCRIPTION.setIsIdentity(false);
        fieldDESCRIPTION.setUnique(false);
        fieldDESCRIPTION.setShouldAllowNull(true);
        table.addField(fieldDESCRIPTION);

        FieldDefinition fieldITEM_ID = new FieldDefinition();
        fieldITEM_ID.setName("ID");
        fieldITEM_ID.setTypeName("NUMBER");
        fieldITEM_ID.setSize(15);
        fieldITEM_ID.setSubSize(0);
        fieldITEM_ID.setIsPrimaryKey(true);
        fieldITEM_ID.setIsIdentity(false);
        fieldITEM_ID.setUnique(false);
        fieldITEM_ID.setShouldAllowNull(false);
        table.addField(fieldITEM_ID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        FieldDefinition field10 = new FieldDefinition();
        field10.setName("ITEM_VERSION");
        field10.setTypeName("NUMERIC");
        field10.setSize(15);
        field10.setShouldAllowNull(true );
        field10.setIsPrimaryKey(false );
        field10.setUnique(false );
        field10.setIsIdentity(false );
        table.addField(field10);

        return table;
    }

    public static TableDefinition buildCMP3_ORDERTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ORDER");

        FieldDefinition fieldCUST_ID = new FieldDefinition();
        fieldCUST_ID.setName("CUSTOMER_CUST_ID");
        fieldCUST_ID.setTypeName("NUMBER");
        fieldCUST_ID.setSize(15);
        fieldCUST_ID.setSubSize(0);
        fieldCUST_ID.setIsPrimaryKey(false);
        fieldCUST_ID.setIsIdentity(false);
        fieldCUST_ID.setUnique(false);
        fieldCUST_ID.setShouldAllowNull(true);
        fieldCUST_ID.setForeignKeyFieldName("CMP3_CUSTOMER.CUST_ID");
        table.addField(fieldCUST_ID);
        
        FieldDefinition fieldBILLED_CUST_ID = new FieldDefinition();
        fieldBILLED_CUST_ID.setName("BILLEDCUSTOMER_CUST_ID");
		//fieldBILLED_CUST_ID.setName("BILLCUST_CUST_ID");
        fieldBILLED_CUST_ID.setTypeName("NUMBER");
        fieldBILLED_CUST_ID.setSize(15);
        fieldBILLED_CUST_ID.setSubSize(0);
        fieldBILLED_CUST_ID.setIsPrimaryKey(false);
        fieldBILLED_CUST_ID.setIsIdentity(false);
        fieldBILLED_CUST_ID.setUnique(false);
        fieldBILLED_CUST_ID.setShouldAllowNull(true);
        fieldBILLED_CUST_ID.setForeignKeyFieldName("CMP3_CUSTOMER.CUST_ID");
        table.addField(fieldBILLED_CUST_ID);
        
        FieldDefinition fieldSALESPERSON_ID = new FieldDefinition();
        fieldSALESPERSON_ID.setName("SALESPERSON_ID");
        fieldSALESPERSON_ID.setTypeName("NUMBER");
        fieldSALESPERSON_ID.setSize(15);
        fieldSALESPERSON_ID.setSubSize(0);
        fieldSALESPERSON_ID.setIsPrimaryKey(false);
        fieldSALESPERSON_ID.setIsIdentity(false);
        fieldSALESPERSON_ID.setUnique(false);
        fieldSALESPERSON_ID.setShouldAllowNull(true);
        table.addField(fieldSALESPERSON_ID);
        
        FieldDefinition fieldITEM_ID = new FieldDefinition();
        fieldITEM_ID.setName("ITEM_ID");
        fieldITEM_ID.setTypeName("NUMBER");
        fieldITEM_ID.setSize(15);
        fieldITEM_ID.setSubSize(0);
        fieldITEM_ID.setIsPrimaryKey(false);
        fieldITEM_ID.setIsIdentity(false);
        fieldITEM_ID.setUnique(false);
        fieldITEM_ID.setShouldAllowNull(true);
        fieldITEM_ID.setForeignKeyFieldName("CMP3_ITEM.ID");
        table.addField(fieldITEM_ID);

        FieldDefinition fieldORDER_ID = new FieldDefinition();
        fieldORDER_ID.setName("ORDER_ID");
        fieldORDER_ID.setTypeName("NUMBER");
        fieldORDER_ID.setSize(15);
        fieldORDER_ID.setSubSize(0);
        fieldORDER_ID.setIsPrimaryKey(true);
        fieldORDER_ID.setIsIdentity(false);
        fieldORDER_ID.setUnique(false);
        fieldORDER_ID.setShouldAllowNull(false);
        table.addField(fieldORDER_ID);

        FieldDefinition fieldQUANTITY = new FieldDefinition();
        fieldQUANTITY.setName("QUANTITY");
        fieldQUANTITY.setTypeName("NUMBER");
        fieldQUANTITY.setSize(15);
        fieldQUANTITY.setSubSize(0);
        fieldQUANTITY.setIsPrimaryKey(false);
        fieldQUANTITY.setIsIdentity(false);
        fieldQUANTITY.setUnique(false);
        fieldQUANTITY.setShouldAllowNull(true);
        table.addField(fieldQUANTITY);

        FieldDefinition fieldSHIP_ADDR = new FieldDefinition();
        fieldSHIP_ADDR.setName("SHIP_ADDR");
        fieldSHIP_ADDR.setTypeName("VARCHAR2");
        fieldSHIP_ADDR.setSize(80);
        fieldSHIP_ADDR.setSubSize(0);
        fieldSHIP_ADDR.setIsPrimaryKey(false);
        fieldSHIP_ADDR.setIsIdentity(false);
        fieldSHIP_ADDR.setUnique(false);
        fieldSHIP_ADDR.setShouldAllowNull(true);
        table.addField(fieldSHIP_ADDR);

        FieldDefinition field10 = new FieldDefinition();
        field10.setName("ORDER_VERSION");
        field10.setTypeName("NUMERIC");
        field10.setSize(15);
        field10.setShouldAllowNull(true );
        field10.setIsPrimaryKey(false );
        field10.setUnique(false );
        field10.setIsIdentity(false );
        table.addField(field10);

       return table;
    }

    public static TableDefinition buildCMP3_ORDER_SEQTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_CUSTOMER_SEQ");

        FieldDefinition fieldSEQ_NAME = new FieldDefinition();
        fieldSEQ_NAME.setName("SEQ_NAME");
        fieldSEQ_NAME.setTypeName("VARCHAR");
        fieldSEQ_NAME.setSize(80);
        fieldSEQ_NAME.setSubSize(0);
        fieldSEQ_NAME.setIsPrimaryKey(true);
        fieldSEQ_NAME.setIsIdentity(false);
        fieldSEQ_NAME.setUnique(false);
        fieldSEQ_NAME.setShouldAllowNull(false);
        table.addField(fieldSEQ_NAME);

        FieldDefinition fieldSEQ_VALUE = new FieldDefinition();
        fieldSEQ_VALUE.setName("SEQ_COUNT");
        fieldSEQ_VALUE.setTypeName("NUMERIC");
        fieldSEQ_VALUE.setSize(15);
        fieldSEQ_VALUE.setSubSize(0);
        fieldSEQ_VALUE.setIsPrimaryKey(false);
        fieldSEQ_VALUE.setIsIdentity(false);
        fieldSEQ_VALUE.setUnique(false);
        fieldSEQ_VALUE.setShouldAllowNull(false);
        table.addField(fieldSEQ_VALUE);

        return table;
    }
    
    public static TableDefinition buildCMP3_SALESPERSONTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_SALESPERSON");        

        FieldDefinition fieldID = new FieldDefinition();
        fieldID.setName("ID");
        fieldID.setTypeName("NUMBER");
        fieldID.setSize(15);
        fieldID.setSubSize(0);
        fieldID.setIsPrimaryKey(true);
        fieldID.setIsIdentity(false);
        fieldID.setUnique(false);
        fieldID.setShouldAllowNull(false);
        table.addField(fieldID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        return table;
    }
    
    public static TableDefinition buildCUSTOMER_CUSTOMERTable() {
        TableDefinition table = new TableDefinition();

        table.setName("CMP3_CUSTOMER_CUSTOMER");

        // SECTION: FIELD
        FieldDefinition field = new FieldDefinition();
        field.setName("CUSTOMER_CUST_ID");
        field.setTypeName("NUMERIC");
        field.setSize(15);
        field.setShouldAllowNull(false);
        field.setIsPrimaryKey(true);
        field.setUnique(false);
        field.setIsIdentity(false);
        field.setForeignKeyFieldName("CMP3_CUSTOMER.CUST_ID");
        table.addField(field);
    
        // SECTION: FIELD
        FieldDefinition field1 = new FieldDefinition();
        field1.setName("CCUSTOMERS_CUST_ID");
        field1.setTypeName("NUMERIC");
        field1.setSize(15);
        field1.setShouldAllowNull(false);
        field1.setIsPrimaryKey(true);
        field1.setUnique(false);
        field1.setIsIdentity(false);
        field1.setForeignKeyFieldName("CMP3_CUSTOMER.CUST_ID");
        table.addField(field1);

        return table;
    }

    public static TableDefinition buildCMP3_ENTITYATable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ENTITYA");        

        FieldDefinition fieldID = new FieldDefinition();
        fieldID.setName("ID");
        fieldID.setTypeName("NUMBER");
        fieldID.setSize(15);
        fieldID.setSubSize(0);
        fieldID.setIsPrimaryKey(true);
        fieldID.setIsIdentity(false);
        fieldID.setUnique(false);
        fieldID.setShouldAllowNull(false);
        table.addField(fieldID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        return table;
    }
    
    public static TableDefinition buildCMP3_ENTITYBTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ENTITYB");        

        FieldDefinition fieldID = new FieldDefinition();
        fieldID.setName("ID");
        fieldID.setTypeName("NUMBER");
        fieldID.setSize(15);
        fieldID.setSubSize(0);
        fieldID.setIsPrimaryKey(true);
        fieldID.setIsIdentity(false);
        fieldID.setUnique(false);
        fieldID.setShouldAllowNull(false);
        table.addField(fieldID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        return table;
    }
    
    public static TableDefinition buildCMP3_ENTITYA_ENTITYBTable() {
        TableDefinition table = new TableDefinition();

        table.setName("CMP3_ENTITYA_ENTITYB");

        // SECTION: FIELD
        FieldDefinition field = new FieldDefinition();
        field.setName("ENTITYA_ID");
        field.setTypeName("NUMERIC");
        field.setSize(15);
        field.setShouldAllowNull(false);
        field.setIsPrimaryKey(true);
        field.setUnique(false);
        field.setIsIdentity(false);
        field.setForeignKeyFieldName("CMP3_ENTITYA.ID");
        table.addField(field);
    
        // SECTION: FIELD
        FieldDefinition field1 = new FieldDefinition();
        field1.setName("ENTITYB_ID");
        field1.setTypeName("NUMERIC");
        field1.setSize(15);
        field1.setShouldAllowNull(false);
        field1.setIsPrimaryKey(true);
        field1.setUnique(false);
        field1.setIsIdentity(false);
        field1.setForeignKeyFieldName("CMP3_ENTITYB.ID");
        table.addField(field1);

        return table;
    }
    
    public static TableDefinition buildCMP3_ENTITYCTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ENTITYC");        

        FieldDefinition fieldID = new FieldDefinition();
        fieldID.setName("ID");
        fieldID.setTypeName("NUMBER");
        fieldID.setSize(15);
        fieldID.setSubSize(0);
        fieldID.setIsPrimaryKey(true);
        fieldID.setIsIdentity(false);
        fieldID.setUnique(false);
        fieldID.setShouldAllowNull(false);
        table.addField(fieldID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        return table;
    }
    
    public static TableDefinition buildCMP3_ENTITYDTable() {
        TableDefinition table = new TableDefinition();
        table.setName("CMP3_ENTITYD");        

        FieldDefinition fieldID = new FieldDefinition();
        fieldID.setName("ID");
        fieldID.setTypeName("NUMBER");
        fieldID.setSize(15);
        fieldID.setSubSize(0);
        fieldID.setIsPrimaryKey(true);
        fieldID.setIsIdentity(false);
        fieldID.setUnique(false);
        fieldID.setShouldAllowNull(false);
        table.addField(fieldID);

        FieldDefinition fieldNAME = new FieldDefinition();
        fieldNAME.setName("NAME");
        fieldNAME.setTypeName("VARCHAR2");
        fieldNAME.setSize(80);
        fieldNAME.setSubSize(0);
        fieldNAME.setIsPrimaryKey(false);
        fieldNAME.setIsIdentity(false);
        fieldNAME.setUnique(false);
        fieldNAME.setShouldAllowNull(true);
        table.addField(fieldNAME);

        return table;
    }
    
    public static TableDefinition buildCMP3_UNIDIR_ENTITYC_ENTITYDTable() {
        TableDefinition table = new TableDefinition();

        table.setName("CMP3_UNIDIR_ENTITYC_ENTITYD");

        // SECTION: FIELD
        FieldDefinition field = new FieldDefinition();
        field.setName("ENTITYC_ID");
        field.setTypeName("NUMERIC");
        field.setSize(15);
        field.setShouldAllowNull(false);
        field.setIsPrimaryKey(true);
        field.setUnique(false);
        field.setIsIdentity(false);
        field.setForeignKeyFieldName("CMP3_ENTITYC.ID");
        table.addField(field);
    
        // SECTION: FIELD
        FieldDefinition field1 = new FieldDefinition();
        field1.setName("ENTITYD_ID");
        field1.setTypeName("NUMERIC");
        field1.setSize(15);
        field1.setShouldAllowNull(false);
        field1.setIsPrimaryKey(true);
        field1.setUnique(false);
        field1.setIsIdentity(false);
        field1.setForeignKeyFieldName("CMP3_ENTITYD.ID");
        table.addField(field1);

        return table;
    }
}
