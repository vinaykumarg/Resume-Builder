package com.example.vinayg.resumebuilder.adapters;

        import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.models.Interest;

import java.util.List;

/**
 * Created by vinay.g.
 */

public class InterestsListViewAdapter extends BaseAdapter {
    private List<Interest> interestlist;
    private Context mContext;
    private static LayoutInflater inflater=null;
    public InterestsListViewAdapter(List<Interest> interests, Context context){
        this.interestlist = interests;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return interestlist.size();
    }

    @Override
    public Interest getItem(int position) {
        return interestlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void swap(List<Interest> interests) {
        interestlist.clear();
        interestlist.addAll(interests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.interest_tile, null);

        TextView interestTv = (TextView) view.findViewById(R.id.interest);;

        Interest interest = interestlist.get(position);
        // Setting all values in listview
        interestTv.setText(interest.getInterest());
        return view;
    }
}
