package com.example.vinayg.resumebuilder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

import java.util.HashMap;

public class AddProjectsActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mTitletext,mDescriptionText;
    private Button mButton;
    private SessionManager session;
    private MyAppDb myAppDb;
    HashMap<String, String> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        user  = session.getUserDetails();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_projects);
        mTitletext = (EditText) findViewById(R.id.input_title);
        mDescriptionText = (EditText) findViewById(R.id.input_desc);
        mButton = (Button) findViewById(R.id.btn_save);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_save) {
            String title = mTitletext.getText().toString();
            String Description  = mDescriptionText.getText().toString();
            String uid = user.get(SessionManager.KEY_ID);
            if (title.equals("")||Description.equals("")||uid.equals("")){
                Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
            } else {
                myAppDb.createProjects(uid, title, Description);
                Log.d("projects", "success");
                setResult(1);
                finish();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAppDb.close();
    }
}
