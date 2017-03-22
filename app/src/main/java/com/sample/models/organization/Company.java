package com.sample.models.organization;


public class Company {

    private long mId;
    private String mCode;
    private String mCnpj;
    private String mEmail;
    private String mTradeName;
    private String mRegisteredName;
    private String mPhone;
    private Address mAddress;

    public Company(String code, String tradeName, String registeredName, Address address) {
        this.mCode = code;
        this.mTradeName = tradeName;
        this.mRegisteredName = registeredName;
        this.mAddress = address;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getCnpj() {
        return mCnpj;
    }

    public void setCnpj(String cnpj) {
        this.mCnpj = cnpj;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getTradeName() {
        return mTradeName;
    }

    public void setTradeName(String tradeName) {
        this.mTradeName = tradeName;
    }

    public String getRegisteredName() {
        return mRegisteredName;
    }

    public void setRegisteredName(String registeredName) {
        this.mRegisteredName = registeredName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        this.mAddress = address;
    }
}
