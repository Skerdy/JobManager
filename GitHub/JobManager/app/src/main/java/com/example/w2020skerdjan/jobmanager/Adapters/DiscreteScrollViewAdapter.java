package com.example.w2020skerdjan.jobmanager.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.w2020skerdjan.jobmanager.R;

public class DiscreteScrollViewAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_discrete_item, parent, false);
        return  new DiscreteScrollViewAdapter.JobDiscreteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class JobDiscreteView extends RecyclerView.ViewHolder {

        public JobDiscreteView(View itemView) {
            super(itemView);
        }
    }
}
