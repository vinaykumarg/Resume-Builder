package com.example.vinayg.resumebuilder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.database.Education;

import java.util.List;

/**
 * Created by vinay.g.
 */

public class EducationListAdapter extends RecyclerView.Adapter<EducationListAdapter.MyViewHolder>{
    private List<Education> mEducationList;
    public EducationListAdapter(List<Education> educationList){
        this.mEducationList = educationList;
    }
    public void swapList(List<Education> educationList) {
        mEducationList.clear();
        mEducationList.addAll(educationList);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eds_list_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Education education = mEducationList.get(position);
        holder.type.setText(education.getInstituteType());
        holder.institute.setText(education.getInstitute());
        holder.stream.setText(education.getStream());
        holder.yearofpass.setText(education.getStartyear()+" - "+education.getEndyear());
        holder.score.setText(String.valueOf(education.getScore()));
    }

    @Override
    public int getItemCount() {
        return mEducationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type,institute,stream,yearofpass,score;
        public MyViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.type);
            institute = (TextView) itemView.findViewById(R.id.institute);
            stream = (TextView) itemView.findViewById(R.id.stream);
            yearofpass = (TextView) itemView.findViewById(R.id.years);
            score = (TextView) itemView.findViewById(R.id.score);

        }
    }
}
