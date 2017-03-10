package com.example.vinayg.resumebuilder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.listeners.ClickListener;
import com.example.vinayg.resumebuilder.adapters.EducationListAdapter;
import com.example.vinayg.resumebuilder.listeners.RecyclerTouchListener;
import com.example.vinayg.resumebuilder.models.Education;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

import java.util.HashMap;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

    private MyAppDb myAppDb;
    private SessionManager session;
    private HashMap<String, String> user;
    private RecyclerView recyclerView;
    public static final int EDUCATIONSAVED = 1;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Education> mEducations;
    private EducationListAdapter mAdapter;
    private static final int REFRESH = 222;
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        infoTextView  = (TextView)findViewById(R.id.noeducation);
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        session = new SessionManager(this);
        user  = session.getUserDetails();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEducationActivity.class);
                startActivityForResult(intent,1);
            }
        });
        mEducations = myAppDb.gettAllEducation(user.get(SessionManager.KEY_ID));
        mAdapter = new EducationListAdapter(mEducations);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent =  new Intent(getApplicationContext(), EducationEditorActivity.class);
                intent.putExtra("position",position);
                startActivityForResult(intent, EDUCATIONSAVED);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== EDUCATIONSAVED) {
            mAdapter.swapList(myAppDb.gettAllEducation(user.get(SessionManager.KEY_ID)));
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!mEducations.isEmpty()) {
            infoTextView.setVisibility(View.INVISIBLE);
        } else {
            infoTextView.setVisibility(View.VISIBLE);
        }
    }
}
