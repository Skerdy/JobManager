package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w2020skerdjan.jobmanager.Models.Custom.NewJobBody;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.EducationResponse;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.ProfessionResponse;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;
import com.example.w2020skerdjan.jobmanager.Utils.MySharedPref;
import com.example.w2020skerdjan.jobmanager.Utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewJobActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private EditText jobTitle, jobDescription, jobExperienceYears, jobMinSalary;
    private NiceSpinner job_profession , job_education;
    private Button saveJob;
    private CardView calendarStartCardView, calendarEndCardView;
    private TextView startDate, endDate;
    private boolean clickedStart , clickedEnd;
    private String titulli, pershkrimi, edukimi, profesioni, eksperienca, pagesaMinimale;

    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private RequestsAPI requestsAPI;
    private MySharedPref mySharedPref;
    private List<ProfessionResponse> professions;
    private List<EducationResponse> educations;
    private List<Object> objects;
    private android.support.v7.widget.Toolbar toolbar;
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job);
        setupViews();
        setupRetrofit();
        fillSpinnersWithData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("EmployerMap", "Pressed back navigation button");
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return true;
    }

    private void fillSpinnersWithData(){

        requestsAPI.getAllEducations().enqueue(getAllEducationsCallback);

        requestsAPI.getAllProfessions().enqueue(getAllProfessionsCallBack);

    }

    Callback<List<ProfessionResponse>> getAllProfessionsCallBack = new Callback<List<ProfessionResponse>>() {
        @Override
        public void onResponse(Call<List<ProfessionResponse>> call, Response<List<ProfessionResponse>> response) {
            if(response.isSuccessful()){
                professions = response.body();
                job_profession.attachDataSource(Utils.getListOfStringsFromResponse(getObjectsProfesion(professions)));
            }
            else{

            }
        }

        @Override
        public void onFailure(Call<List<ProfessionResponse>> call, Throwable t) {

        }
    };


    Callback<List<EducationResponse>> getAllEducationsCallback = new Callback<List<EducationResponse>>() {
        @Override
        public void onResponse(Call<List<EducationResponse>> call, Response<List<EducationResponse>> response) {
            if(response.isSuccessful()){
                educations = response.body();
                job_education.attachDataSource(Utils.getListOfStringsFromResponse(getObjectsEducation(educations)));
            }
            else{

            }
        }

        @Override
        public void onFailure(Call<List<EducationResponse>> call, Throwable t) {

        }
    };






    Callback<Void> postNewJobCallBack = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()){
                Log.d("NewJob", "New Job u ruajt me sukses");
                progressDialog.dismiss();
                Toast.makeText(AddNewJobActivity.this, "Job saved with success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewJobActivity.this, EmployerActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Log.d("NewJob", "New Job no success");
                progressDialog.dismiss();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.d("NewJob", "Failed");
            progressDialog.dismiss();
        }
    };



    private void setupRetrofit(){
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
        mySharedPref = new MySharedPref(this);
    }


    private void  setupViews(){
        progressDialog= new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating new Job!");
       // jobTitle = findViewById(R.id.input_job_title);
        jobDescription = findViewById(R.id.input_job_description);
        jobExperienceYears = findViewById(R.id.input_job_ezperience);
        jobMinSalary = findViewById(R.id.input_min_salary);
        job_profession = findViewById(R.id.profession_id_spinner);
        job_education = findViewById(R.id.education_id_spinner);
        saveJob = findViewById(R.id.save_job_button);
        calendarStartCardView = findViewById(R.id.calendar_start_cv);
        calendarEndCardView = findViewById(R.id.calendar_end_cv);
        startDate = findViewById(R.id.startDateTxt);
        endDate = findViewById(R.id.endDateTxt);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddNewJobActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        calendarStartCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show(getFragmentManager(),"Pick Start Date");
                clickedStart = true;
            }
        });
        calendarEndCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show(getFragmentManager(),"Pick End Date");
                clickedEnd=true;
            }
        });

        saveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(validate()){
                    requestsAPI.postNewJob(mySharedPref.getStringFromSharedPref(CodesUtil.ASP_NET_USER_ID), new NewJobBody(
                            jobDescription.getText().toString(),
                            Integer.parseInt( jobExperienceYears.getText().toString()),
                            jobMinSalary.getText().toString(),
                            startDate.getText().toString(),
                            endDate.getText().toString(),
                            returnIdFromProfessionName(job_profession.getText().toString()),
                            returnIdFromEducationName(job_education.getText().toString())
                    )).enqueue(postNewJobCallBack);
                }
                else{
                    progressDialog.dismiss();
                }
            }
        });

    }

    private String returnIdFromEducationName(String name){
        for(EducationResponse educationResponse : educations){
            if (educationResponse.getEducation().equals(name)){
                return educationResponse.getEducationid().toString();
            }
        }
        return "1";
    }

    private String returnIdFromProfessionName(String name){
        for(ProfessionResponse professionResponse : professions){
            if (professionResponse.getName().equals(name)){
                return professionResponse.getId().toString();
            }
        }
        return "1";
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        if(clickedStart){
            startDate.setText(date);
            clickedStart=false;
        }
        else if(clickedEnd) {
            endDate.setText(date);
            clickedEnd = false;
        }

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }


    public boolean validate() {

        boolean valid = true;

       // titulli = jobTitle.getText().toString();
        pershkrimi = jobDescription.getText().toString();
        edukimi = job_education.getText().toString();
        profesioni = job_profession.getText().toString();
        eksperienca = jobExperienceYears.getText().toString();
        pagesaMinimale = jobMinSalary.getText().toString();

        if(startDate.getText().toString().equals("Start Date")){
            valid =false;
            Toast.makeText(this,"Please Pick a Start date",Toast.LENGTH_LONG).show();
            return  false;
        }

        if(endDate.getText().toString().equals("End Date")){
            Toast.makeText(this,"Please Pick an End date",Toast.LENGTH_LONG).show();
            return  false;
        }

/*
        if (titulli.isEmpty()) {
            jobTitle.setError("Please insert a Job Title!");
            valid = false;
        } else {
            jobTitle.setError(null);
        }*/

        if (edukimi.isEmpty()) {
            job_education.setError("Please pick a valid education!");
            valid = false;
        } else {
            job_education.setError(null);
        }


        if (profesioni.isEmpty()) {
            job_profession.setError("Please pick a valid profession!");
            valid = false;
        } else {
            job_profession.setError(null);
        }

        if (eksperienca.isEmpty()) {
            jobExperienceYears.setError("Please insert experience required!");
            valid = false;
        } else {
            jobExperienceYears.setError(null);
        }

        if (pagesaMinimale.isEmpty()) {
            jobMinSalary.setError("Please insert minimum wage!");
            valid = false;
        } else {
            jobMinSalary.setError(null);
        }

        return valid;
    }

    private List<Object> getObjectsProfesion(List<ProfessionResponse> professionResponses){
        objects = new ArrayList<>();
        for(ProfessionResponse professionResponse : professionResponses){
            objects.add(professionResponse);
        }
        return objects;
    }
    private List<Object> getObjectsEducation(List<EducationResponse> educationResponses){
        objects = new ArrayList<>();
        for(EducationResponse educationResponse : educationResponses){
            objects.add(educationResponse);
        }
        return objects;
    }
}
