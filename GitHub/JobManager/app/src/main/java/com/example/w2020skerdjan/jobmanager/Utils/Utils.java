package com.example.w2020skerdjan.jobmanager.Utils;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.JobType;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> getJobsTypesFromResponse(List<JobType> jobTypes){
        List<String> jobTypesResult = new ArrayList<>();
        for(JobType jobType :jobTypes){
            jobTypesResult.add(jobType.getJobTitle());
        }
        return jobTypesResult;
    }
}
