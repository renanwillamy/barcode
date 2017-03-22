package com.sample.models.material;


public class MaterialType {

    private long mId;
    private String mName;
    private String mDescription;

    public MaterialType(String mName, String mDescription) {
        this.mName = mName;
        this.mDescription = mDescription;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }
}
