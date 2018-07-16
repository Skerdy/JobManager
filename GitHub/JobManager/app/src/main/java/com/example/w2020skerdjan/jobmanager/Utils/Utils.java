package com.example.w2020skerdjan.jobmanager.Utils;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.EducationResponse;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.JobType;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.ProfessionResponse;

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


    public static List<String> getListOfStringsFromResponse(List<Object> list ){
        List<String> result = new ArrayList<>();
        if(list.isEmpty()){
           return result;
        }

        if(list.get(0) instanceof EducationResponse){
            for(Object object :  list){
                EducationResponse educationResponse = (EducationResponse) object;
                result.add(educationResponse.getEducation());
            }
        }
        else if (list.get(0) instanceof ProfessionResponse){
            for(Object object :  list){
                ProfessionResponse professionResponse = (ProfessionResponse) object;
                result.add(professionResponse.getName());
            }
        }
        return result;
    }

    public static List<Object> getObjectsProfesion(List<ProfessionResponse> professionResponses){
        List<Object> objects = new ArrayList<>();
        for(ProfessionResponse professionResponse : professionResponses){
            objects.add(professionResponse);
        }
        return objects;
    }
    public static List<Object> getObjectsEducation(List<EducationResponse> educationResponses){
        List<Object> objects = new ArrayList<>();
        for(EducationResponse educationResponse : educationResponses){
            objects.add(educationResponse);
        }
        return objects;
    }
}
