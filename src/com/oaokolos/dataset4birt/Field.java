/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oaokolos.dataset4birt;

import java.sql.*;

/**
 *
 * @author rkievskiy
 */
public class Field {
    private int type;
    private Object value;
    
    public Field(int fieldType, Object fieldValue){
        this.type = fieldType;
        this.value = fieldValue;
    }
    
    private void wrongTypes(int have, String want) throws Exception {
        String haveStr = "";
        switch (have) {
            case java.sql.Types.ARRAY: 			haveStr = "ARRAY"; break;
            case java.sql.Types.BIGINT: 		haveStr = "BIGINT"; break;
            case java.sql.Types.BINARY: 		haveStr = "BINARY"; break;
            case java.sql.Types.BIT: 			haveStr = "BIT"; break;
            case java.sql.Types.BLOB: 			haveStr = "BLOB"; break;
            case java.sql.Types.BOOLEAN: 		haveStr = "BOOLEAN"; break;
            case java.sql.Types.CHAR: 			haveStr = "CHAR"; break;
            case java.sql.Types.CLOB: 			haveStr = "CLOB"; break;
            case java.sql.Types.DATALINK: 		haveStr = "DATALINK"; break;
            case java.sql.Types.DATE: 			haveStr = "DATE"; break;
            case java.sql.Types.DECIMAL: 		haveStr = "DECIMAL"; break;
            case java.sql.Types.DISTINCT: 		haveStr = "DISTINCT"; break;
            case java.sql.Types.DOUBLE: 		haveStr = "DOUBLE"; break;
            case java.sql.Types.FLOAT: 			haveStr = "FLOAT"; break;
            case java.sql.Types.INTEGER: 		haveStr = "INTEGER"; break;
            case java.sql.Types.JAVA_OBJECT: 	haveStr = "JAVA_OBJECT"; break;
            case java.sql.Types.LONGNVARCHAR: 	haveStr = "LONGNVARCHAR"; break;
            case java.sql.Types.LONGVARBINARY: 	haveStr = "LONGVARBINARY"; break;
            case java.sql.Types.LONGVARCHAR: 	haveStr = "LONGVARCHAR"; break;
            case java.sql.Types.NCHAR: 			haveStr = "NCHAR"; break;
            case java.sql.Types.NCLOB: 			haveStr = "NCLOB"; break;
            case java.sql.Types.NULL: 			haveStr = "NULL"; break;
            case java.sql.Types.NUMERIC: 		haveStr = "NUMERIC"; break;
            case java.sql.Types.NVARCHAR: 		haveStr = "NVARCHAR"; break;
            case java.sql.Types.OTHER: 			haveStr = "OTHER"; break;
            case java.sql.Types.REAL: 			haveStr = "REAL"; break;
            case java.sql.Types.REF: 			haveStr = "REF"; break;
            case java.sql.Types.REF_CURSOR: 	haveStr = "REF_CURSOR"; break;
            case java.sql.Types.ROWID: 			haveStr = "ROWID"; break;
            case java.sql.Types.SMALLINT: 		haveStr = "SMALLINT"; break;
            case java.sql.Types.SQLXML: 		haveStr = "SQLXML"; break;
            case java.sql.Types.STRUCT: 		haveStr = "STRUCT"; break;
            case java.sql.Types.TIME: 			haveStr = "TIME"; break;
            case java.sql.Types.TIMESTAMP: 		haveStr = "TIMESTAMP"; break;
            case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:haveStr = "TIMESTAMP_WITH_TIMEZONE"; break;
            case java.sql.Types.TIME_WITH_TIMEZONE: 	haveStr = "TIME_WITH_TIMEZONE"; break;
            case java.sql.Types.TINYINT: 		haveStr = "TINYINT"; break;
            case java.sql.Types.VARBINARY: 		haveStr = "VARBINARY"; break;
            case java.sql.Types.VARCHAR: 		haveStr = "VARCHAR"; break;
            default: haveStr = "Unknown type";
        }
        
        Exception ex = new Exception("Can't convert value to " + want + " (current type : " + haveStr + ")");
        throw ex;
    }
    
    public Array asArray() throws Exception {
        if (this.type != java.sql.Types.ARRAY) {
            wrongTypes(this.type, "Array");   
        }
        
        return (Array) this.value;
    }

    public Long asBigint() throws Exception {
        if (this.type != java.sql.Types.BIGINT) {
            wrongTypes(this.type, "Bigint");   
        }
        
        return (Long) this.value;
    }
/*
    public Binary asBinary() throws Exception {
        if (this.type != java.sql.Types.BINARY) {
            wrongTypes(this.type, "Binary");   
        }
        
        return (Binary) this.value;
    }
*/
    public boolean asBit() throws Exception {
        if (this.type != java.sql.Types.BIT) {
            wrongTypes(this.type, "Bit");   
        }
        
        return (boolean) this.value;
    }

    public Blob asBlob() throws Exception {
        if (this.type != java.sql.Types.BLOB) {
            wrongTypes(this.type, "Blob");   
        }
        
        return (Blob) this.value;
    }

    public Boolean asBoolean() throws Exception {
        if (this.type != java.sql.Types.BOOLEAN) {
            wrongTypes(this.type, "Boolean");   
        }
        
        return (Boolean) this.value;
    }

    public String asChar() throws Exception {
        if (this.type != java.sql.Types.CHAR) {
            wrongTypes(this.type, "Char");   
        }
        
        return (String) this.value;
    }

    public Clob asClob() throws Exception {
        if (this.type != java.sql.Types.CLOB) {
            wrongTypes(this.type, "Clob");   
        }
        
        return (Clob) this.value;
    }
/*
    public Datalink asDatalink() throws Exception {
        if (this.type != java.sql.Types.DATALINK) {
            wrongTypes(this.type, "Datalink");   
        }
        
        return (Datalink) this.value;
    }
*/
    public Date asDate() throws Exception {
        if (this.type != java.sql.Types.DATE) {
            wrongTypes(this.type, "Date");   
        }
        
        return (Date) this.value;
    }
/*
    public Decimal asDecimal() throws Exception {
        if (this.type != java.sql.Types.DECIMAL) {
            wrongTypes(this.type, "Decimal");   
        }
        
        return (Decimal) this.value;
    }

    public Distinct asDistinct() throws Exception {
        if (this.type != java.sql.Types.DISTINCT) {
            wrongTypes(this.type, "Distinct");   
        }
        
        return (Distinct) this.value;
    }
*/
    public Double asDouble() throws Exception {
        if (this.type != java.sql.Types.DOUBLE) {
            wrongTypes(this.type, "Double");   
        }
        
        return (Double) this.value;
    }

    public Float asFloat() throws Exception {
        if (this.type != java.sql.Types.FLOAT) {
            wrongTypes(this.type, "Float");   
        }
        
        return (Float) this.value;
    }

    public Integer asInteger() throws Exception {
        if (this.type != java.sql.Types.INTEGER) {
            wrongTypes(this.type, "Integer");   
        }
        
        return (Integer) this.value;
    }

    public Object asObject() throws Exception {
        if (this.type != java.sql.Types.JAVA_OBJECT) {
            wrongTypes(this.type, "Java_object");   
        }
        
        return this.value;
    }

    public String asLongNVarchar() throws Exception {
        if (this.type != java.sql.Types.LONGNVARCHAR) {
            wrongTypes(this.type, "Longnvarchar");   
        }
        
        return (String) this.value;
    }
/*
    public Longvarbinary asLongVarbinary() throws Exception {
        if (this.type != java.sql.Types.LONGVARBINARY) {
            wrongTypes(this.type, "Longvarbinary");   
        }
        
        return (Longvarbinary) this.value;
    }
*/
    public String asLongVarchar() throws Exception {
        if (this.type != java.sql.Types.LONGVARCHAR) {
            wrongTypes(this.type, "Longvarchar");   
        }
        
        return (String) this.value;
    }

    public String asNChar() throws Exception {
        if (this.type != java.sql.Types.NCHAR) {
            wrongTypes(this.type, "Nchar");   
        }
        
        return (String) this.value;
    }
/*
    public Nclob asNClob() throws Exception {
        if (this.type != java.sql.Types.NCLOB) {
            wrongTypes(this.type, "Nclob");   
        }
        
        return (Nclob) this.value;
    }

    public Null asNull() throws Exception {
        if (this.type != java.sql.Types.NULL) {
            wrongTypes(this.type, "Null");   
        }
        
        return (Null) this.value;
    }

    public Numeric asNumeric() throws Exception {
        if (this.type != java.sql.Types.NUMERIC) {
            wrongTypes(this.type, "Numeric");   
        }
        
        return (Numeric) this.value;
    }
*/
    public String asNVarchar() throws Exception {
        if (this.type != java.sql.Types.NVARCHAR) {
            wrongTypes(this.type, "Nvarchar");   
        }
        
        return (String) this.value;
    }
/*
    public Other asOther() throws Exception {
        if (this.type != java.sql.Types.OTHER) {
            wrongTypes(this.type, "Other");   
        }
        
        return (Other) this.value;
    }
*/
    public float asReal() throws Exception {
        if (this.type != java.sql.Types.REAL) {
            wrongTypes(this.type, "Real");   
        }
        
        return (float) this.value;
    }

    public Ref asRef() throws Exception {
        if (this.type != java.sql.Types.REF) {
            wrongTypes(this.type, "Ref");   
        }
        
        return (Ref) this.value;
    }
/*
    public Ref_cursor asRef_cursor() throws Exception {
        if (this.type != java.sql.Types.REF_CURSOR) {
            wrongTypes(this.type, "Ref_cursor");   
        }
        
        return (Ref_cursor) this.value;
    }

    public Rowid asRowid() throws Exception {
        if (this.type != java.sql.Types.ROWID) {
            wrongTypes(this.type, "Rowid");   
        }
        
        return (Rowid) this.value;
    }
*/
    public Integer asSmallint() throws Exception {
        if (this.type != java.sql.Types.SMALLINT) {
            wrongTypes(this.type, "Smallint");   
        }
        
        return (Integer) this.value;
    }
/*
    public Sqlxml asSqlxml() throws Exception {
        if (this.type != java.sql.Types.SQLXML) {
            wrongTypes(this.type, "Sqlxml");   
        }
        
        return (Sqlxml) this.value;
    }
*/
    public Struct asStruct() throws Exception {
        if (this.type != java.sql.Types.STRUCT) {
            wrongTypes(this.type, "Struct");   
        }
        
        return (Struct) this.value;
    }

    public Time asTime() throws Exception {
        if (this.type != java.sql.Types.TIME) {
            wrongTypes(this.type, "Time");   
        }
        
        return (Time) this.value;
    }

    public Timestamp asTimestamp() throws Exception {
        if (this.type != java.sql.Types.TIMESTAMP) {
            wrongTypes(this.type, "Timestamp");   
        }
        
        return (Timestamp) this.value;
    }

    public Timestamp asTimestamp_with_timezone() throws Exception {
        if (this.type != java.sql.Types.TIMESTAMP_WITH_TIMEZONE) {
            wrongTypes(this.type, "Timestamp_with_timezone");   
        }
        
        return (Timestamp) this.value;
    }

    public Time asTime_with_timezone() throws Exception {
        if (this.type != java.sql.Types.TIME_WITH_TIMEZONE) {
            wrongTypes(this.type, "Time_with_timezone");   
        }
        
        return (Time) this.value;
    }

    public Integer asTineint() throws Exception {
        if (this.type != java.sql.Types.TINYINT) {
            wrongTypes(this.type, "Tinyint");   
        }
        
        return (Integer) this.value;
    }
/*
    public Varbinary asVarbinary() throws Exception {
        if (this.type != java.sql.Types.VARBINARY) {
            wrongTypes(this.type, "Varbinary");   
        }
        
        return (Varbinary) this.value;
    }
*/
    public String asVarchar() throws Exception {
        if (this.type != java.sql.Types.VARCHAR) {
            wrongTypes(this.type, "Varchar");   
        }
        
        return (String) this.value;
    }
}
