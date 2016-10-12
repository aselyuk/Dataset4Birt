/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oaokolos.dataset4birt;

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
            case java.sql.Types.ARRAY: haveStr = "Array"; break;
            case java.sql.Types.BIGINT: haveStr = "Long"; break;
            // ...
            default: haveStr = "Unknown type";
        }
        
        Exception ex = new Exception("Can't convert value to " + want + " (current type : " + haveStr + ")");
        throw ex;
    }
    
    public int asInteger() {
        if (this.type != java.sql.Types.INTEGER) {
            wrongTypes(this.type, "Integer");   // call exception
        }
        
        return (Integer) this.value;
    }
}
