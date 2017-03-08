package com.example.vinayg.resumebuilder.projects;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vinayg.resumebuilder.adapters.ClickListener;
import com.example.vinayg.resumebuilder.adapters.RecyclerTouchListener;
import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.homepage.SessionManager;
import com.example.vinayg.resumebuilder.adapters.ProjectListAdapter;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.database.Projects;

import java.util.HashMap;
import java.util.List;

public class ProjectsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProjectListAdapter mAdapter;
    private MyAppDb myAppDb;
    private List<Projects> projects;
    private int projectsave = 1;
    private SessionManager session;
    private static final int REFRESH = 222;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        session = new SessionManager(this);
        user  = session.getUserDetails();
        projects = myAppDb.gettAllProjects(user.get(SessionManager.KEY_ID));
        mAdapter = new ProjectListAdapter(projects);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Projects project = projects.get(position);
                Intent intent =  new Intent(getApplicationContext(), ProjectEditActivity.class);
                intent.putExtra("position",position);
                startActivityForResult(intent,projectsave);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProjectsActivity.class);
                startActivityForResult(intent,projectsave);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==projectsave) {
            mAdapter.swapList(myAppDb.gettAllProjects(user.get(SessionManager.KEY_ID)));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(REFRESH);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAppDb.close();
    }
}
