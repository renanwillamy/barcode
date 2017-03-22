package com.sample.models.material;


public class MaterialFamily {

    private String mName;
    private String mDescription;

    public MaterialFamily(String mName, String mDescription) {
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
}
