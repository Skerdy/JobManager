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
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.LoginResponse;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;
import com.example.w2020skerdjan.jobmanager.Utils.MySharedPref;
import com.example.w2020skerdjan.jobmanager.Utils.RetrofitParamGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
    private String ASPNETUSERID = null;
    private boolean eligibleForTutorial = false;

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
                requestsAPI.getAspNetUserIdByEmail(response.body().getUserName()).enqueue(getASPuserNetIdCallBack);
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

    Callback<Member> getASPuserNetIdCallBack = new Callback<Member>() {
        @Override
        public void onResponse(Call<Member> call, Response<Member> response) {
            if(response.isSuccessful()){
                Log.d("Skerdi", "name:" + response.body().getFirstName());
                if(response.body().getFirstName().equals("PLeaseupdate")){
                    eligibleForTutorial = true;
                }
                else {
                    Log.d("Skerdi", "false");
                    eligibleForTutorial = false;
                }
                progressDialog.dismiss();
                ASPNETUSERID = response.body().getAspNetUserID();
                mySharedPref.saveStringInSharedPref(CodesUtil.ASP_NET_USER_ID, ASPNETUSERID);
                Thread thread = new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            requestRole();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                    thread.start();
            }
            else {
                progressDialog.dismiss();
                try {
                    Log.d("Login", "getAspnNetuserId failed : " + response.message() + response.errorBody().string());
                    Toast.makeText(getActivity(), "There was a problem with extracting your role. Please contact the admins!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<Member> call, Throwable t) {
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
        getActivity().finish();
    }

    private void requestRole () throws IOException {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://www.oncallemployee.com/client/api/roles/get?aspnetuserid=").append(ASPNETUSERID);
        URL url = null;
        try {
            url = new URL(urlString.toString());
            Log.d("Skerdi", "u krijuar Url");
        } catch (MalformedURLException e) {
            Log.d("Skerdi", "Error Malformes url");
            e.printStackTrace();
        }

        HttpURLConnection con = null;

        try {
            con = (HttpURLConnection) url.openConnection();



        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("GET");

        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine;
        StringBuffer content = new StringBuffer();
        if (in != null) {
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        try {
            if(in!=null)
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Skerdi", "response = " + content.toString().trim() );
        Log.d("Skerdi", "response = " + content.toString().trim() + " length = " + content.toString().length() );
        Log.d("Skerdi", "length of role stored = " + CodesUtil.EMPLOYEE_ROLE.length() );

        switch (content.toString().replace("\"","")){
            case CodesUtil.ADMINISTRATOR_ROLE:
                if(eligibleForTutorial){
                    redirectTo(CodesUtil.ADMIN_CLASS);
                }
                else
                redirectTo(CodesUtil.ADMIN_CLASS);
                break;
            case CodesUtil.MANAGER_ROLE:
                if(eligibleForTutorial){
                    redirectTo(CodesUtil.MANAGER_CLASS);
                }
                else
                redirectTo(CodesUtil.MANAGER_CLASS);
                break;
            case CodesUtil.EMPLOYER_ROLE:
                if(eligibleForTutorial){
                redirectTo(CodesUtil.TUTORIAL_EMPLOYER_CLASS);
                }
                else
                redirectTo(CodesUtil.EMPLOYER_CLASS);
                break;
            case CodesUtil.EMPLOYEE_ROLE:
                if(eligibleForTutorial){
                    redirectTo(CodesUtil.EMPLOYEE_CLASS);
                }
                else
                redirectTo(CodesUtil.EMPLOYEE_CLASS);
                break;
            default:
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"There was a problem with extracting your role. Please contact the admins!", Toast.LENGTH_LONG).show();
                    }
                });

                break;
        }
    }
}
