package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeMap {

    @SerializedName("geoLongMap")
    @Expose
    private Double geoLongMap;
    @SerializedName("geoLatMap")
    @Expose
    private Double geoLatMap;
    @SerializedName("userNameMap")
    @Expose
    private String userNameMap;
    @SerializedName("addressMap")
    @Expose
    private String addressMap;
    @SerializedName("logoMap")
    @Expose
    private Object logoMap;
    @SerializedName("description")
    @Expose
    private String description;

    public Double getGeoLongMap() {
        return geoLongMap;
    }

    public void setGeoLongMap(Double geoLongMap) {
        this.geoLongMap = geoLongMap;
    }

    public Double getGeoLatMap() {
        return geoLatMap;
    }

    public void setGeoLatMap(Double geoLatMap) {
        this.geoLatMap = geoLatMap;
    }

    public String getUserNameMap() {
        return userNameMap;
    }

    public void setUserNameMap(String userNameMap) {
        this.userNameMap = userNameMap;
    }

    public String getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(String addressMap) {
        this.addressMap = addressMap;
    }

    public Object getLogoMap() {
        return logoMap;
    }

    public void setLogoMap(Object logoMap) {
        this.logoMap = logoMap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}


