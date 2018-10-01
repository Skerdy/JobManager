package com.example.w2020skerdjan.jobmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Company;
import com.example.w2020skerdjan.jobmanager.R;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>{

    private Context context;
    private List<Company> companies;

    public CompanyAdapter (List<Company> companies , Context context){
        this.context = context;
        this.companies = companies;
    }


    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        holder.company_name.setText(companies.get(position).getCompanyName());
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {

        private TextView company_name;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            company_name = itemView.findViewById(R.id.company_name_label);
        }
    }
}
