package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.w2020skerdjan.jobmanager.Adapters.CompanyAdapter;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Company;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployerSearchCompaniesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CompanyAdapter companyAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Retrofit retrofit;
    private RetrofitClient retrofitClient;
    private RequestsAPI requestsAPI;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitClient = new RetrofitClient();
        retrofit = retrofitClient.krijoRetrofit();
        requestsAPI = retrofit.create(RequestsAPI.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employer_search_companies,  container, false);
    }

    private void setupAllViews(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        setupSwipeToRefresh();
    }

    public void setRefreshingTrue() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
                makeRequest();
            }
        });
    }

    private void makeRequest(){
        setRefreshingTrue();
        requestsAPI.getAllCompanies().enqueue(getAllCompanies);
    }

    Callback<List<Company>> getAllCompanies = new Callback<List<Company>>() {
        @Override
        public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
            if(response.isSuccessful()){
                companyAdapter = new CompanyAdapter(response.body(), getActivity());
                recyclerView.setAdapter(companyAdapter);
            }
            else {
                Log.d("Skerdi", "Problem request get member by id");
            }
        }

        @Override
        public void onFailure(Call<List<Company>> call, Throwable t) {
            Log.d("Skerdi", "Failure get all companies: " + t.getMessage());
        }
    };
}
