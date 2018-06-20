package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic;

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
import android.widget.Toast;

import com.example.w2020skerdjan.jobmanager.Adapters.AdminJobsAdapter;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Job;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Retrofit.Requests.RequestsAPI;
import com.example.w2020skerdjan.jobmanager.Retrofit.RetrofitClient;
import com.example.w2020skerdjan.jobmanager.Utils.CodesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EmployeeSearchJobsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdminJobsAdapter adminJobsAdapter;
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
        return inflater.inflate(R.layout.fragment_admin_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAllViews(view);
        adminJobsAdapter = new AdminJobsAdapter(getActivity(), new ArrayList<Job>(), CodesUtil.EMPLOYER);
        recyclerView.setAdapter(adminJobsAdapter);
        makeRequest();
    }

    private void setupAllViews(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        setupSwipeToRefresh();
    }

    private void makeRequest(){
        setRefreshingTrue();
        requestsAPI.getAllJobs().enqueue(getAllJobsCallback);
    }

    Callback<List<Job>> getAllJobsCallback = new Callback<List<Job>>() {
        @Override
        public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
            if(response.isSuccessful()){
                adminJobsAdapter.setJobs(response.body());
            }
            else{
                try {
                    Log.d("Login", "Get All Jobs Failed : " + response.message() + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onFailure(Call<List<Job>> call, Throwable t) {
            String message = "";
            Toast.makeText(getActivity(), "Login failed: Network Error!", Toast.LENGTH_SHORT).show();
            if(t!=null && t.getMessage()!=null){
                message = t.getMessage();
            }
            Log.d("Login", "OnFailure : " + message);
            swipeRefreshLayout.setRefreshing(false);
        }
    };


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
}
