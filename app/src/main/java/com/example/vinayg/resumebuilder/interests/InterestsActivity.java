package com.example.vinayg.resumebuilder.interests;

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
import com.example.vinayg.resumebuilder.adapters.InterestListAdapter;
import com.example.vinayg.resumebuilder.database.Interest;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.homepage.SessionManager;

import java.util.HashMap;
import java.util.List;

import static com.example.vinayg.resumebuilder.education.EducationActivity.EDUCATIONSAVED;

public class InterestsActivity extends AppCompatActivity {

    private MyAppDb myAppDb;
    private SessionManager session;
    private HashMap<String, String> user;
    private RecyclerView recyclerView;
    private List<Interest> mInterests;
    private InterestListAdapter mAdapter;
    private String uid;
    private static final int REFRESH = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        session = new SessionManager(this);
        user  = session.getUserDetails();
        uid = user.get(SessionManager.KEY_ID);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mInterests = myAppDb.gettAllInterest(user.get(SessionManager.KEY_ID));
        mAdapter = new InterestListAdapter(mInterests);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent =  new Intent(getApplicationContext(), InterestEditActivity.class);
                intent.putExtra("position",position);
                startActivityForResult(intent, EDUCATIONSAVED);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InterestAddActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            mAdapter.swap(myAppDb.gettAllInterest(uid));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(REFRESH);

    }
}
