package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities.EmployerProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployerProfileFragment extends Fragment {

    private TextView email, mobile, company;
    private Retrofit retrofit;
    private RetrofitClient retrofitClient;
    private RequestsAPI requestsAPI;
    private String emailStr;
    private static final String EMAIL_ARG = "emailArg";
    private Member member;

    public static EmployerProfileFragment newInstance(String email){
        EmployerProfileFragment employerProfileFragment = new EmployerProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EMAIL_ARG, email);
        employerProfileFragment.setArguments(bundle);
        return employerProfileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitClient = new RetrofitClient();
        retrofit  = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
        emailStr = getArguments().getString(EMAIL_ARG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employer_profile_final,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestsAPI.getAspNetUserIdByEmail(emailStr).enqueue(memberCallback);
    }

    Callback<Member> memberCallback = new Callback<Member>() {
        @Override
        public void onResponse(Call<Member> call, Response<Member> response) {
            if(response.isSuccessful()){
                member = response.body();
                mapDataWithUI(member);
            }
            else {
                Log.d("Skerdi", "Problem request get member by id");
            }
        }

        @Override
        public void onFailure(Call<Member> call, Throwable t) {
            Log.d("Skerdi", "Failure get member by id : " + t.getMessage());
        }
    };

    private void mapDataWithUI(Member member){
        this.email.setText(member.getEmail());
        this.company.setText(member.getNotes());
        this.mobile.setText("+23 456 78 96");
    }
}
