package com.example.w2020skerdjan.jobmanager.RoleUserLogic.EmployeeLogic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.w2020skerdjan.jobmanager.Adapters.DiscreteScrollViewAdapter;
import com.example.w2020skerdjan.jobmanager.R;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        DiscreteScrollView scrollView = findViewById(R.id.picker);
        scrollView.setAdapter(new DiscreteScrollViewAdapter());
    }
}
