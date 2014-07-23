package com.chinacloud.blackhole.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SourceField implements Serializable {

    private String column;
    private String type;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String toString() {
        return "{column:" + column + ", type:" + type + "}";
    }
    
}
