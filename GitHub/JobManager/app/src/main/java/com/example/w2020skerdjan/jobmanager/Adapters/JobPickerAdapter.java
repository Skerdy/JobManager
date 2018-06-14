package com.example.w2020skerdjan.jobmanager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.w2020skerdjan.jobmanager.R;

import java.util.List;

/**
 * Created by adityagohad on 06/06/17.
 */

public class JobPickerAdapter extends RecyclerView.Adapter<JobPickerAdapter.TextVH> {

    private Context context;
    private List<String> dataList;
    private RecyclerView recyclerView;

    public JobPickerAdapter(Context context, List<String> dataList, RecyclerView recyclerView) {
        this.context = context;
        this.dataList = dataList;
        this.recyclerView = recyclerView;
    }

    @Override
    public TextVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.job_picker_item, parent, false);
        return new JobPickerAdapter.TextVH(view);
    }

    @Override
    public void onBindViewHolder(TextVH holder, final int position) {
        TextVH textVH = holder;
        textVH.pickerTxt.setText(dataList.get(position));
        textVH.pickerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView != null) {
                    recyclerView.smoothScrollToPosition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void swapData(List<String> newData) {
        dataList = newData;
        notifyDataSetChanged();
    }

    class TextVH extends RecyclerView.ViewHolder {
        TextView pickerTxt;

        public TextVH(View itemView) {
            super(itemView);
            pickerTxt = (TextView) itemView.findViewById(R.id.job_item);
        }
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }


}
