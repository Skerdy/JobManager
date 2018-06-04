package com.example.w2020skerdjan.jobmanager.Retrofit.Requests;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Address;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Job;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.LoginResponse;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.RegisterResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestsAPI {

    @Headers({"Content-Type:application/json"})
    @FormUrlEncoded
    @POST("Token")
    Call<LoginResponse> login(@FieldMap Map<String, String> userCredentials);

    @Headers({"Content-Type:application/json"})
    @GET("member/all")
    Call<List<Member>> getAllMembers();


    @Headers({"Content-Type:application/json"})
    @GET("address/all")
    Call<List<Address>> getAllAddresses();

    @Headers({"Content-Type:application/json"})
    @GET("member/GetMemberByUserType/{userTypeId}")
    Call<List<Member>> getMemberByUserType(@Path("userTypeId") Integer Id);


    @Headers({"Content-Type:application/json"})
    @GET("member/GetMemberByUserID/{userASPId}")
    Call<List<Member>> getMemberByUserId(@Path("userASPId") String AspId);

    @Headers({"Content-Type:application/json"})
    @FormUrlEncoded
    @POST("account/register")
    Call<RegisterResponse> register(@FieldMap Map<String, String> registerCredentials);



    @Headers({"Content-Type:application/json"})
    @GET("jobs/all")
    Call<List<Job>> getAllJobs();

  /*  @Headers({"Content-Type:application/json"})
    @POST("member/PostNewMember")

*/
}
