package com.example.vinayg.resumebuilder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.database.Education;

import java.util.List;

/**
 * Created by vinay.g.
 */

public class EducationListViewAdapter extends BaseAdapter {
    private List<Education> mEducationList;
    private Context mContext;
    private static LayoutInflater inflater=null;
    public EducationListViewAdapter(List<Education> educationList, Context context){
        this.mEducationList = educationList;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mEducationList.size();
    }

    @Override
    public Education getItem(int position) {
        return mEducationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void swapList(List<Education> educationList) {
        mEducationList.clear();
        mEducationList.addAll(educationList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.eds_list_row, null);

        TextView type = (TextView) view.findViewById(R.id.type);
        TextView institute = (TextView) view.findViewById(R.id.institute);
        TextView stream = (TextView) view.findViewById(R.id.stream);
        TextView yearofpass = (TextView) view.findViewById(R.id.years);
        TextView score = (TextView) view.findViewById(R.id.score);
        Education education = mEducationList.get(position);
        // Setting all values in listview
        type.setText(education.getInstituteType());
        institute.setText(education.getInstitute());
        stream.setText(education.getStream());
        yearofpass.setText(education.getStartyear()+" - "+education.getEndyear());
        score.setText(String.valueOf(education.getScore()));
        return view;
    }
}

