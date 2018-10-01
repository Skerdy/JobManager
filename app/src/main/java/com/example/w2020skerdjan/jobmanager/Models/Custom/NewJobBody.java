package com.example.w2020skerdjan.jobmanager.Models.Custom;

public class NewJobBody {

    private String JobDescription;
    private Integer ExperienceRequiredYears;
    private String MinSalary;
    private String StartDate;
    private String EndDate;
    private String ProfessionId;
    private String EducationId;

    public NewJobBody(String jobDescription, Integer experienceRequiredYears, String minSalary, String startDate, String endDate, String professionId, String educationId) {
        JobDescription = jobDescription;
        ExperienceRequiredYears = experienceRequiredYears;
        MinSalary = minSalary;
        StartDate = startDate;
        EndDate = endDate;
        ProfessionId = professionId;
        EducationId = educationId;
    }
    public NewJobBody(){

    }


    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String jobDescription) {
        JobDescription = jobDescription;
    }

    public Integer getExperienceRequiredYears() {
        return ExperienceRequiredYears;
    }

    public void setExperienceRequiredYears(Integer experienceRequiredYears) {
        ExperienceRequiredYears = experienceRequiredYears;
    }

    public String getMinSalary() {
        return MinSalary;
    }

    public void setMinSalary(String minSalary) {
        MinSalary = minSalary;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getProfessionId() {
        return ProfessionId;
    }

    public void setProfessionId(String professionId) {
        ProfessionId = professionId;
    }

    public String getEducationId() {
        return EducationId;
    }

    public void setEducationId(String educationId) {
        EducationId = educationId;
    }
}
