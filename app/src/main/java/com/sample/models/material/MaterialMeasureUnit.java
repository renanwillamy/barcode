package com.sample.models.material;

public class MaterialMeasureUnit {

    private long mId;
    private String mName;

    public MaterialMeasureUnit(long id, String name) {
        mId = id;
        mName = name;
    }

    public MaterialMeasureUnit(String name) {
        mName = name;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long id) {
        this.mId = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String name) {
        this.mName = name;
    }
}
