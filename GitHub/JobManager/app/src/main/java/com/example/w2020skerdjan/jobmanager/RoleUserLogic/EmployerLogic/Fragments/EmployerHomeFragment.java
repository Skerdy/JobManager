package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployerLogic.Activities.EmployerActivity;

public class EmployerHomeFragment extends Fragment {

    private CardView searchJobs, myJobs, findInMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employer_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        findInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EmployerActivity)getActivity()).initfindEmployesActivity();
            }
        });
        searchJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EmployerActivity)getActivity()).initSearchJobsEmployer();
            }
        });
    }

    private void initViews(View view){
        searchJobs = view.findViewById(R.id.allJobs);
        myJobs = view.findViewById(R.id.myJobs);
        findInMap = view.findViewById(R.id.findEmployeesMap);
    }
}
