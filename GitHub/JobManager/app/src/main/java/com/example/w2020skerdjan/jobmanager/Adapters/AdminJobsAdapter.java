package com.example.w2020skerdjan.jobmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Job;
import com.example.w2020skerdjan.jobmanager.R;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.List;

public class AdminJobsAdapter extends RecyclerView.Adapter<AdminJobsAdapter.JobViewHolder> {

    private List<Job> jobs ;
    private Job job;
    private Context ctx;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    public AdminJobsAdapter(Context ctx, List<Job> jobs){
        this.jobs = jobs;
        this.ctx = ctx;
        expansionsCollection.openOnlyOne(true);
    }


    public AdminJobsAdapter(){
        expansionsCollection.openOnlyOne(true);
    }


    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return  new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        job = jobs.get(position);
        expansionsCollection.add(holder.expansionLayout);

        holder.experienceHeader.setText(""+job.getExperienceRequiredYears());
        holder.educationHeader.setText(job.getEducationRequired());

        holder.jobTitle.setText(job.getJobTitle());
        holder.startDate.setText(job.getStartDate().substring(0,10));
        holder.endDate.setText(job.getEndDate().substring(0,10));
        holder.description.setText(job.getJobDescription());
        holder.education.setText(job.getEducationRequired());
        holder.experience.setText(""+job.getExperienceRequiredYears());
        holder.salary.setText(job.getMinSalary());

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }



    public class JobViewHolder extends RecyclerView.ViewHolder {


        private TextView jobTitle;
        private TextView startDate;
        private TextView endDate;
        private TextView description;
        private TextView education;
        private TextView experience;
        private TextView salary;
        private TextView educationHeader;
        private TextView experienceHeader;

        private ExpansionLayout expansionLayout;


        public JobViewHolder(View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            description = itemView.findViewById(R.id.descriptionJob);
            education = itemView.findViewById(R.id.educationJob);
            experience = itemView.findViewById(R.id.experienceJob);
            salary = itemView.findViewById(R.id.salaryJob);
            expansionLayout = itemView.findViewById(R.id.expansionLayout);
            educationHeader = itemView.findViewById(R.id.EducationMark);
            experienceHeader = itemView.findViewById(R.id.experienceMark);
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }
}
