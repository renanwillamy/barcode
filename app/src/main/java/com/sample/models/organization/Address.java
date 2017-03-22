package com.sample.models.organization;


public class Address {

    private String mStreet;
    private String mComplement;
    private String mCity;
    private String mState;
    private String mZip;

    public Address(String street, String city, String zip) {
        mStreet = street;
        mCity = city;
        mZip = zip;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        mStreet = street;
    }

    public String getComplement() {
        return mComplement;
    }

    public void setComplement(String complement) {
        mComplement = complement;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getZip() {
        return mZip;
    }

    public void setZip(String zip) {
        mZip = zip;
    }
}
