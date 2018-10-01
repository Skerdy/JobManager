package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelState {

    @SerializedName("")
    @Expose
    private List<String> error = null;

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}