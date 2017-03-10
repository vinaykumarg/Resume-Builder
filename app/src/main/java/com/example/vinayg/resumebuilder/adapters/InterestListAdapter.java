package com.example.vinayg.resumebuilder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.models.Interest;

import java.util.List;

/**
 * Created by vinay.g.
 */

public class InterestListAdapter extends RecyclerView.Adapter<InterestListAdapter.MyViewHolder> {
    private List<Interest> interestlist;

    public InterestListAdapter(List<Interest> interests) {
        this.interestlist = interests;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interest_tile, parent, false);
        InterestListAdapter.MyViewHolder myViewHolder = new InterestListAdapter.MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Interest interest = interestlist.get(position);
        holder.interestTv.setText(interest.getInterest());
    }

    @Override
    public int getItemCount() {
        return interestlist.size();
    }

    public void swap(List<Interest> interests) {
        interestlist.clear();
        interestlist.addAll(interests);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView interestTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            interestTv = (TextView) itemView.findViewById(R.id.interest);

        }
    }
}
