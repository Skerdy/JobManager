package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("companyID")
    @Expose
    private Integer companyID;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("contactPerson")
    @Expose
    private String contactPerson;

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

}