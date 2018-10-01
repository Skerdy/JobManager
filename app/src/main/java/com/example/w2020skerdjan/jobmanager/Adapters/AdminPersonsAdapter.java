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

import com.example.w2020skerdjan.jobmanager.Models.HttpRequest.Member;
import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Utils.OnExpandListener;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

public class AdminPersonsAdapter extends RecyclerView.Adapter<AdminPersonsAdapter.AdminPersonsViewHolder> implements OnExpandListener {

    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private List<Member> allMembers;
    private List<Member> members;
    private List<Member> temporaryMembers;
    private Member member;
    private Context ctx;
    private int lastExpandedPosition, startRange = -1, endRange;
    private boolean artificialExpand = false;
    private OnExpandListener onExpandListener;
    private boolean stopExpanding =false;

    public AdminPersonsAdapter(Context ctx, List<Member> members) {
        this.ctx = ctx;
        this.members = members;
        this.allMembers = members;
        expansionsCollection.openOnlyOne(true);
    }


    @NonNull
    @Override
    public AdminPersonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new AdminPersonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminPersonsViewHolder holder, final int position) {
        member = members.get(position);
        expansionsCollection.add(holder.expansionLayout);
        StringBuilder name = new StringBuilder();
        name.append(member.getFirstName()).append(" ").append(member.getLastName());
        holder.userName.setText(name.toString());
        holder.notes.setText(member.getNotes());
        if(member.getBirthDate()!=null)
        holder.birthday.setText(member.getBirthDate().substring(0,10));
        holder.mobile.setText(member.getPhone());
        holder.expansionLayout.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {
                if (expanded) {
                    Log.d("SkerdiExpand ", " Expanded  with position : " + position);

                    lastExpandedPosition = position;

                    if(artificialExpand) {

                        notifyDataSetChanged();
                        onExpand(position, expansionLayout);

                    }
                  /*  if (startRange > 0 && lastExpandedPosition >= startRange) {

                        if (lastExpandedPosition == position) {
                            Log.d("SkerdiExpand ", " Collapsed After Expand at position :  " + position);
                            if (position % 2 == 0) {
                                startRange = position + 2;
                            } else {
                                startRange = position + 1;
                            }
                            endRange = members.size();
                            //notifyItemRangeChanged(startRange,6 );

                            temporaryMembers = returnMembersWithRange(startRange, endRange, members);
                            Log.d("SkerdiRange", "size eshte : " + startRange);
                            Log.d("SkerdiRange", "size eshte : " + temporaryMembers.size());
                            removeCalculatedMembers(startRange);
                            notifyDataSetChanged();
                            addTemporaryItemsAgain();
                            notifyDataSetChanged();
                            expansionLayout.expand(true);
                        }
                    }*/
                } else {
                    if (lastExpandedPosition == position) {
                        artificialExpand = true;
                      Log.d("SkerdiExpand ", " Collapsed After Expand at position :  " + position);
                        if (position % 2 == 0) {
                            startRange = position + 2;
                        } else {
                            startRange = position + 1;
                        }
                        /*
                        endRange = members.size();
                        //notifyItemRangeChanged(startRange,6 );
                        temporaryMembers = returnMembersWithRange(startRange, endRange, members);
                        Log.d("SkerdiRange", "size eshte : " + startRange);
                        Log.d("SkerdiRange", "size eshte : " + temporaryMembers.size());
                        removeCalculatedMembers(startRange);
                        notifyDataSetChanged();
                        addTemporaryItemsAgain();
                        notifyDataSetChanged();
                        */

                        Log.d("SkerdiExpand ", " New Data set notified");
                    }
                }
            }
        });

        }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void setMembers(List<Member> members) {
        this.members = members;
        this.allMembers = members;
        notifyDataSetChanged();
    }

    private List<Member> returnMembersWithRange(int start, int end, List<Member> members) {
        List<Member> result = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            if (i >= start && i <= end) {
                result.add(members.get(i));
            }
        }
        return result;
    }

    private void removeCalculatedMembers(int startRange) {
        while (members.size() > startRange) {
            members.remove(startRange);
        }
    }

    private void addTemporaryItemsAgain() {
        for (int i = 0; i < temporaryMembers.size(); i++) {
            members.add(temporaryMembers.get(i));
        }
    }

    @Override
    public void onExpand(int position, ExpansionLayout expansionLayout) {
            expansionLayout.expand(false);
            artificialExpand = false;
            Log.d("SkerdiExpand", " Listener expanded position : " + position);
            stopExpanding =true;
    }


    public class AdminPersonsViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private ImageView imageView;
        private TextView birthday;
        private TextView notes;
        private TextView mobile;
        private ExpansionLayout expansionLayout;

        public AdminPersonsViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            imageView = itemView.findViewById(R.id.user_avatar);
            birthday = itemView.findViewById(R.id.memberBirthday);
            notes = itemView.findViewById(R.id.memberNotes);
            mobile = itemView.findViewById(R.id.memberMobile);
            expansionLayout = itemView.findViewById(R.id.expansionLayout);
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }

}
