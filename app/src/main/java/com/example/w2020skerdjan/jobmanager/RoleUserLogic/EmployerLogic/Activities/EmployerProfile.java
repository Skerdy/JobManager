package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployerProfile extends AppCompatActivity {

    private TextView email, mobile, company, name_surname;
    private Retrofit retrofit;
    private RetrofitClient retrofitClient;
    private RequestsAPI requestsAPI;
    private String emailStr;
    public static final String EMAIL_ARG = "emailArg";
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);
        bindViews();
        retrofitClient = new RetrofitClient();
        retrofit  = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
        emailStr = getIntent().getStringExtra(EMAIL_ARG);
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
        this.name_surname.setText(member.getFirstName() + " " + member.getLastName());
    }

    private void bindViews(){
      this.email = findViewById(R.id.email_label);
      this.mobile = findViewById(R.id.mobile_label);
      this.company = findViewById(R.id.company_label);
      this.name_surname = findViewById(R.id.name_surname);
    }
}
