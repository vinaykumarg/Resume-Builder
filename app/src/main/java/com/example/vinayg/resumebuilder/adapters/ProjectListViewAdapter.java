package com.example.vinayg.resumebuilder.adapters;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.vinayg.resumebuilder.R;
        import com.example.vinayg.resumebuilder.models.Projects;

        import java.util.List;

/**
 * Created by vinay.g.
 */

public class ProjectListViewAdapter extends BaseAdapter {
    private List<Projects> mProjectsList;
    private Context mContext;
    private static LayoutInflater inflater=null;
    public ProjectListViewAdapter(List<Projects> projectsList, Context context){
        this.mProjectsList = projectsList;
        this.mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mProjectsList.size();
    }

    public void swapList(List<Projects> projectsList) {
        mProjectsList.clear();
        mProjectsList.addAll(projectsList);
    }

    @Override
    public Projects getItem(int position) {
        return mProjectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.projects_list_row, null);

        TextView title = (TextView)view.findViewById(R.id.title); // title
        TextView description = (TextView)view.findViewById(R.id.description); // artist name


        // Setting all values in listview
        title.setText(mProjectsList.get(position).getTitle());
        description.setText(mProjectsList.get(position).getDescription());
        return view;
    }
}
