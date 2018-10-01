package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {
    @SerializedName("memberID")
    @Expose
    private Integer memberID;
    @SerializedName("aspNetUserID")
    @Expose
    private String aspNetUserID;
    @SerializedName("titleOfCourtesy")
    @Expose
    private String titleOfCourtesy;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("birthDate")
    @Expose
    private String birthDate;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("extension")
    @Expose
    private Object extension;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("photo")
    @Expose
    private Object photo;
    @SerializedName("imageMimeType")
    @Expose
    private Object imageMimeType;

    @SerializedName("professionId")
    @Expose
    private Integer professionId;
    @SerializedName("education")
    @Expose
    private Integer education;
    @SerializedName("experienceYears")
    @Expose
    private Integer experienceYears;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public String getAspNetUserID() {
        return aspNetUserID;
    }

    public void setAspNetUserID(String aspNetUserID) {
        this.aspNetUserID = aspNetUserID;
    }

    public String getTitleOfCourtesy() {
        return titleOfCourtesy;
    }

    public void setTitleOfCourtesy(String titleOfCourtesy) {
        this.titleOfCourtesy = titleOfCourtesy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getExtension() {
        return extension;
    }

    public void setExtension(Object extension) {
        this.extension = extension;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public Object getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(Object imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}