package com.example.vinayg.resumebuilder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.models.Projects;

import java.util.List;

/**
 * Created by vinay.g.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.MyViewHolder> {
    private List<Projects> mProjectsList;

    public ProjectListAdapter(List<Projects> projectsList) {
        this.mProjectsList = projectsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects_list_row, parent, false);
        ProjectListAdapter.MyViewHolder myViewHolder = new ProjectListAdapter.MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(mProjectsList.get(position).getTitle());
        holder.description.setText(mProjectsList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mProjectsList.size();
    }

    public void swapList(List<Projects> projectsList) {
        mProjectsList.clear();
        mProjectsList.addAll(projectsList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        public TextView titl;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title); // title
            description = (TextView)itemView.findViewById(R.id.description);

        }
    }
}
