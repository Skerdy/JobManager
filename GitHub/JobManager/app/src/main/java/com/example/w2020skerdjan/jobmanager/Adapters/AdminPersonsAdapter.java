package com.example.w2020skerdjan.jobmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Job;
import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.List;

public class AdminPersonsAdapter extends RecyclerView.Adapter<AdminPersonsAdapter.AdminPersonsViewHolder> {

    private List<Member> members ;
    private List<Member> temporaryMembers;
    private Member member;
    private Context ctx;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private int lastExpandedPosition, startRange, endRange;

    public  AdminPersonsAdapter(Context ctx, List<Member> members){
        this.ctx= ctx;
        this.members = members;
        expansionsCollection.openOnlyOne(true);
    }


    @NonNull
    @Override
    public AdminPersonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return  new AdminPersonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPersonsViewHolder holder, final int position) {
        expansionsCollection.add(holder.expansionLayout);
            holder.expansionLayout.addListener(new ExpansionLayout.Listener() {
                @Override
                public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                    if(expanded){
                        Log.d("SkerdiExpand ", " Expanded ");
                        lastExpandedPosition = position;
                    }
                    else{
                        Log.d("SkerdiExpand ", " Collapsed ");
                        if(lastExpandedPosition==position){
                            Log.d("SkerdiExpand ", " Collapsed After Expand at position :  " + position);
                            if(position%2==0){
                                startRange = position+2;
                            }
                            else {
                                startRange = position+1;
                            }
                            endRange = 10;
                           notifyItemRangeChanged(startRange,6 );
                        }
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class AdminPersonsViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private ImageView imageView;
        private TextView startDate;
        private TextView endDate;
        private TextView description;
        private TextView education;
        private TextView experience;
        private TextView salary;
        private TextView educationHeader;
        private TextView experienceHeader;

        private ExpansionLayout expansionLayout;


        public AdminPersonsViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            imageView = itemView.findViewById(R.id.user_avatar);
            /*startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            description = itemView.findViewById(R.id.descriptionJob);
            education = itemView.findViewById(R.id.educationJob);
            experience = itemView.findViewById(R.id.experienceJob);
            salary = itemView.findViewById(R.id.salaryJob);

            educationHeader = itemView.findViewById(R.id.EducationMark);*/
            expansionLayout = itemView.findViewById(R.id.expansionLayout);
            experienceHeader = itemView.findViewById(R.id.experienceMark);
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }

    public void setMembers(List<Member> members) {
        this.members = members;
        notifyDataSetChanged();
    }
}
