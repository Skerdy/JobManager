package com.example.w2020skerdjan.jobmanager.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.w2020skerdjan.jobmanager.Activities.LoginActivity;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.LoginResponse;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.RegisterResponse;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.Utils.RetrofitParamGenerator;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private RetrofitClient retrofitClient;
    private Retrofit retrofit;
    private RequestsAPI requestsAPI;
    private EditText email, password, confirmPassword;
    private NiceSpinner niceSpinner;
    private boolean passwordsMatch = false;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitClient = new RetrofitClient();
        retrofit  =retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         niceSpinner = (NiceSpinner) view.findViewById(R.id.nice_spinner);
        relativeLayout = view.findViewById(R.id.registerTrigger);
        email = view.findViewById(R.id.input_email);
        password = view.findViewById(R.id.input_password);
        confirmPassword = view.findViewById(R.id.input_confirm_password);
        List<String> dataset = new LinkedList<>(Arrays.asList("Employee", "Employeer", "Manager", "Admin"));
        niceSpinner.attachDataSource(dataset);

        progressDialog= new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering...");

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                register();
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordsMatch = doPasswordsMatch(confirmPassword.getText().toString(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordsMatch = doPasswordsMatch(password.getText().toString(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void register(){
        if(validate()){
            requestsAPI.register(RetrofitParamGenerator.generateRegisterMap(email.getText().toString(),password.getText().toString(),confirmPassword.getText().toString(),niceSpinner.getText().toString())).enqueue(registerCallback);
        }

    }


    Callback<RegisterResponse> registerCallback  = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if(response.isSuccessful()){
                progressDialog.dismiss();
                Log.d("Register" , "Register with success , code : " + response.code());
                Toast.makeText(getActivity(),"Register successful!", Toast.LENGTH_SHORT).show();
                ((LoginActivity)getActivity()).initLogin();

            }
            else
                {
                    progressDialog.dismiss();
                    try {
                        Log.d("Register" , "Register NO success , code : " + response.code() + " message" + response.message() + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(response.body()!=null && response.body().getModelState()!=null && response.body().getModelState().getError()!=null && !response.body().getModelState().getError().isEmpty()){
                    Toast.makeText(getActivity(),response.body().getModelState().getError().get(0), Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            progressDialog.dismiss();
            Log.d("Register" , "Request failed : " + t.getMessage());
        }
    };





    private boolean validate() {
        boolean valid = true;
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();

        if (emailTxt.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordTxt.isEmpty() || passwordTxt.length() < 4 || passwordTxt.length() > 30) {
            password.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if(!passwordsMatch){
            valid = false;
            confirmPassword.setError("Passwords must match!");
        }
        else {
            confirmPassword.setError(null);
        }
        return valid;
    }

    private boolean doPasswordsMatch(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }
        else{
            return false;
        }
    }


}
