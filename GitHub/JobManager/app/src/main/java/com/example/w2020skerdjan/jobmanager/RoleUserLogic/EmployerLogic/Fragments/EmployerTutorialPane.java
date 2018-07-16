package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.EducationResponse;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.ProfessionResponse;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities.EmployerTutorialActivity;
import com.example.w2020skerdjan.jobmanager.Utils.Utils;
import com.lamudi.phonefield.PhoneEditText;
import com.lamudi.phonefield.PhoneInputLayout;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployerTutorialPane extends Fragment  {

    final static String LAYOUT_ID = "layoutid";
    final static String INDEX = "index";
    private int index;

    private EditText name, last_name, experience_years;
    private NiceSpinner education_spinner, profession_spinner;
    private PhoneInputLayout phoneInputLayout;
    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private RequestsAPI requestsAPI;
    private List<EducationResponse> educationResponses;
    private List<ProfessionResponse> professionResponses;


    public static EmployerTutorialPane newInstance(int layoutId, int tutorialIndex ) {
        EmployerTutorialPane pane = new EmployerTutorialPane();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        args.putInt(INDEX, tutorialIndex);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        index = getArguments().getInt(INDEX);

        switch (index){
            case 0:

                break;
            case 1:
                SetupLogicForNameSurname(view);
                break;
            case 2:
                SetupLogicForPhoneEducation(view);
                break;
            case 3:
                SetupLogicForProfessionAndExperience(view);
                break;
        }

    }

    private void SetupLogicForNameSurname(View view){
        name = view.findViewById(R.id.input_emer);
        last_name = view.findViewById(R.id.input_mbiemer);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((EmployerTutorialActivity)getActivity()).setName(name.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        last_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((EmployerTutorialActivity)getActivity()).setLast_name(last_name.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void SetupLogicForPhoneEducation(View view){
        phoneInputLayout = view.findViewById(R.id.phone_input_layout);
        education_spinner = view.findViewById(R.id.spinner_education);
        //mbushim spinnerin me te dhenat nga DB

        requestsAPI.getAllEducations().enqueue(getAllEducations);

        phoneInputLayout.setHint(R.string.phone_hint);
        phoneInputLayout.setDefaultCountry("US");

        this.phoneInputLayout.getTextInputLayout().getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Mobile"," number changed : " + phoneInputLayout.getPhoneNumber());
                ((EmployerTutorialActivity)getActivity()).setMobile_number(phoneInputLayout.getPhoneNumber());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        education_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((EmployerTutorialActivity)getActivity()).setEducation(education_spinner.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public boolean getMobileValidity(){
        return true;
    }

    private void SetupLogicForProfessionAndExperience(View view){

        profession_spinner = view.findViewById(R.id.spinner_profession);
        experience_years = view.findViewById(R.id.input_experience_years);

        profession_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((EmployerTutorialActivity)getActivity()).setProfession(profession_spinner.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        experience_years.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((EmployerTutorialActivity)getActivity()).setExperience_years(experience_years.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        requestsAPI.getAllProfessions().enqueue(getAllProfessions);
    }


    Callback<List<EducationResponse>> getAllEducations = new Callback<List<EducationResponse>>() {
        @Override
        public void onResponse(Call<List<EducationResponse>> call, Response<List<EducationResponse>> response) {
            if(response.isSuccessful()){
               education_spinner.attachDataSource(Utils.getListOfStringsFromResponse(Utils.getObjectsEducation(response.body())));
               educationResponses = response.body();
            }
            else{
                Log.d("Response" , "Not successful");
            }
        }

        @Override
        public void onFailure(Call<List<EducationResponse>> call, Throwable t) {

        }
    };

    Callback<List<ProfessionResponse>> getAllProfessions = new Callback<List<ProfessionResponse>>() {
        @Override
        public void onResponse(Call<List<ProfessionResponse>> call, Response<List<ProfessionResponse>> response) {
            if(response.isSuccessful()){
                profession_spinner.attachDataSource(Utils.getListOfStringsFromResponse(Utils.getObjectsProfesion(response.body())));
                professionResponses = response.body();
            }
            else{
                Log.d("Response" , "Not successful");
            }
        }

        @Override
        public void onFailure(Call<List<ProfessionResponse>> call, Throwable t) {

        }
    };

    public Integer returnIdFromEducation(String Education){
        if(educationResponses!=null && !educationResponses.isEmpty()) {
            for (EducationResponse educationResponse: educationResponses){
                if(educationResponse.getEducation().equals(Education)){
                    return educationResponse.getEducationid();
                }
            }
        }
        return  -1;
    }

    public Integer returnIdFromProfession(String profession){
        if(professionResponses!=null&& !professionResponses.isEmpty()){
            for(ProfessionResponse professionResponse : professionResponses){
                if(professionResponse.getName().equals(profession)){
                    return professionResponse.getId();
                }
            }
        }
        return -1;
    }

}
