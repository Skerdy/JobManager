package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;
    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("educationRequired")
    @Expose
    private String educationRequired;
    @SerializedName("experienceRequiredYears")
    @Expose
    private Integer experienceRequiredYears;
    @SerializedName("minSalary")
    @Expose
    private String minSalary;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getEducationRequired() {
        return educationRequired;
    }

    public void setEducationRequired(String educationRequired) {
        this.educationRequired = educationRequired;
    }

    public Integer getExperienceRequiredYears() {
        return experienceRequiredYears;
    }

    public void setExperienceRequiredYears(Integer experienceRequiredYears) {
        this.experienceRequiredYears = experienceRequiredYears;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}

