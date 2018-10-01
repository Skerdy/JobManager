package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationResponse {

    @SerializedName("educationid")
    @Expose
    private Integer educationid;
    @SerializedName("education")
    @Expose
    private String education;

    public Integer getEducationid() {
        return educationid;
    }

    public void setEducationid(Integer educationid) {
        this.educationid = educationid;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

}