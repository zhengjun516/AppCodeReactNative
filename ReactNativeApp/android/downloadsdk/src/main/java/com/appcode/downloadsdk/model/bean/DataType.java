package com.appcode.downloadsdk.model.bean;

public abstract class DataType {

    private final String mTypeName;

    public DataType(String typeName) {
        mTypeName = typeName;
    }

    public String getTypeName() {
        return mTypeName;
    }
}
