package com.example.w2020skerdjan.jobmanager.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.w2020skerdjan.jobmanager.Activities.LoginActivity;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic.AdminActivity;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.LoginResponse;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;
import com.example.w2020skerdjan.jobmanager.Utils.MySharedPref;
import com.example.w2020skerdjan.jobmanager.Utils.RetrofitParamGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {

    private LinearLayout registerTextViews ;
    private EditText emailET, passwordET;
    private RelativeLayout loginButton;
    private Intent intent;
    private Retrofit retrofit;
    private RetrofitClient retrofitClient;
    private RequestsAPI requestsAPI;
    private LoginResponse loginResponse;
    private MySharedPref mySharedPref;
    private String emailStr = null , passwordStr = null;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
        mySharedPref = new MySharedPref(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);

        if(mySharedPref.getSavedObjectFromPreference(CodesUtil.USERNAME, String.class)!=null){
            emailStr = mySharedPref.getSavedObjectFromPreference(CodesUtil.USERNAME, String.class);
            emailET.setText(emailStr);
        }

        if(mySharedPref.getStringFromSharedPref(CodesUtil.PASSWORD)!=null){
            passwordStr = mySharedPref.getStringFromSharedPref(CodesUtil.PASSWORD);
            passwordET.setText(passwordStr);
        }
        Log.d("Login", " email : " + emailStr);
        Log.d("Login", "password: " + passwordStr);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                Log.d("Login", " email : " + emailStr);
                Log.d("Login", "password: " + passwordStr);
                mySharedPref.saveStringInSharedPref(CodesUtil.USERNAME, emailET.getText().toString());
                mySharedPref.saveStringInSharedPref(CodesUtil.PASSWORD, passwordET.getText().toString());
                loginApiCall();
            }
        });

        registerTextViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity)getActivity()).initRegister();
            }
        });



    }

    private void bindViews(View view){
        registerTextViews = view.findViewById(R.id.registerTrigger);
        emailET = view.findViewById(R.id.input_email);
        passwordET = view.findViewById(R.id.input_password);
        loginButton = view.findViewById(R.id.loginTrigger);

        progressDialog= new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
    }

    private boolean validate() {
        boolean valid = true;
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            passwordET.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordET.setError(null);
        }
        return valid;
    }

/*    private void login(){
     if(validate()){
         if(emailET.getText().toString().trim().equals("admin@w2020.com")){
             intent = new Intent(getActivity(), AdminActivity.class);
             startActivity(intent);
             getActivity().finish();
         }
         else if(emailET.getText().toString().trim().equals("manager@w2020.com")){
             intent = new Intent(getActivity(), ManagerActivity.class);
             startActivity(intent);
             getActivity().finish();
         }

         else {
             intent = new Intent(getActivity(), UserActivity.class);
             startActivity(intent);
             getActivity().finish();
         }
     }
     else {
         Toast.makeText(getActivity(),"Login failed !", Toast.LENGTH_LONG).show();
     }
    }*/


    private void loginApiCall(){
        if(validate())
        requestsAPI.login(RetrofitParamGenerator.generateLoginMap(emailET.getText().toString().trim(), passwordET.getText().toString(), "password")).enqueue(loginCallback);
        Log.d("Login", " email : " + emailStr);
        Log.d("Login", "password: " + passwordStr);
    }

    Callback<LoginResponse> loginCallback = new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            if(response.isSuccessful()){
                loginResponse = response.body();
                mySharedPref.saveStringInSharedPref(CodesUtil.ACCESS_TOKEN, loginResponse.getAccessToken());
                redirectTo(CodesUtil.EMPLOYER_CLASS);
            }
            else {
                  Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
                try {
                    Log.d("Login", "Login Failed : " + response.message() + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            progressDialog.dismiss();
            String message = "";
            Toast.makeText(getActivity(), "Login failed: Network Error!", Toast.LENGTH_SHORT).show();
            if(t!=null && t.getMessage()!=null){
                message = t.getMessage();
            }
            Log.d("Login", "OnFailure : " + message);
        }
    };

    private void redirectTo(Class T){
        intent = new Intent(getActivity(), T);
        startActivity(intent);
        progressDialog.dismiss();
        getActivity().finish();
    }
}
