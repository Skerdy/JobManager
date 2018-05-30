package com.example.w2020skerdjan.jobmanager.Utils;

import java.util.HashMap;
import java.util.Map;

public class RetrofitParamGenerator {

    public static Map<String,String> generateLoginMap(String username, String password, String grant_type){
        Map<String,String>resultmap = new HashMap<>();
        resultmap.put("username", username);
        resultmap.put("password", password);
        resultmap.put("grant_type", grant_type);
        return resultmap;
    }
}
