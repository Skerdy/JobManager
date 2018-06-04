package com.example.w2020skerdjan.jobmanager.Models.HttpRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("modelState")
    @Expose
    private ModelState modelState;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelState getModelState() {
        return modelState;
    }

    public void setModelState(ModelState modelState) {
        this.modelState = modelState;
    }

}