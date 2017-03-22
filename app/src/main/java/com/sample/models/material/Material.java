package com.sample.models.material;

import com.sample.models.organization.Company;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Material {

    private String mPartnumber;
    private MaterialType mType;
    private String description;
    private String materialGroup;
    private MaterialFamily mFamily;
    private BigDecimal mWeight;
    private boolean mActive = true;
    private boolean mLotControl = false;
    private boolean mQualityInspection = false;
    private Company mCompany;
    private MaterialMeasureUnit mMeasureUnit;
    private Long mExpiration;
    private Set<String> mPns = new HashSet<>();

    public Material(String partnumber, MaterialType type, MaterialFamily family, MaterialMeasureUnit measureUnit) {
        mPartnumber = partnumber;
        mType = type;
        this.mFamily = family;
        this.mMeasureUnit = measureUnit;
    }

    public String getPartnumber() {
        return mPartnumber;
    }

    public void setPartnumber(String partnumber) {
        this.mPartnumber = partnumber;
    }

    public MaterialType getType() {
        return mType;
    }

    public void setType(MaterialType type) {
        this.mType = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    public MaterialFamily getFamily() {
        return mFamily;
    }

    public void setFamily(MaterialFamily family) {
        this.mFamily = family;
    }

    public BigDecimal getWeight() {
        return mWeight;
    }

    public void setWeight(BigDecimal weight) {
        this.mWeight = weight;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean mActive) {
        this.mActive = mActive;
    }

    public boolean isLotControl() {
        return mLotControl;
    }

    public void setLotControl(boolean lotControl) {
        mLotControl = lotControl;
    }

    public boolean isQualityInspection() {
        return mQualityInspection;
    }

    public void setmQualityInspection(boolean qualityInspection) {
        this.mQualityInspection = qualityInspection;
    }

    public Company getCompany() {
        return mCompany;
    }

    public void setCompany(Company company) {
        mCompany = company;
    }

    public MaterialMeasureUnit getMeasureUnit() {
        return mMeasureUnit;
    }

    public void setMeasureUnit(MaterialMeasureUnit measureUnit) {
        this.mMeasureUnit = measureUnit;
    }

    public Long getExpiration() {
        return mExpiration;
    }

    public void setExpiration(Long expiration) {
        this.mExpiration = expiration;
    }

    public Set<String> getPns() {
        return mPns;
    }

    public void setPns(Set<String> pns) {
        this.mPns = pns;
    }
}
