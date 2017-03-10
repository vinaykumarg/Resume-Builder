package com.example.vinayg.resumebuilder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.authorization.SessionManager;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.models.Projects;

import java.util.HashMap;
import java.util.List;

public class ProjectEditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mTitletext,mDescriptionText;
    private Button mEditButton,mDeleteButton;
    private MyAppDb myAppDb;
    private List<Projects> projects;
    private Projects mProjects;
    private SessionManager session;
    private HashMap<String, String> user;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        setContentView(R.layout.activity_project_edit);
        mTitletext = (EditText) findViewById(R.id.input_title);
        mTitletext.setEnabled(false);
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();
        uid = user.get(SessionManager.KEY_ID);
        projects = myAppDb.gettAllProjects(uid);
        mDescriptionText = (EditText) findViewById(R.id.input_desc);
        mDescriptionText.setEnabled(false);
        mEditButton = (Button) findViewById(R.id.btn_save);
        mEditButton.setText("edit");
        mEditButton.setOnClickListener(this);
        mDeleteButton = (Button) findViewById(R.id.btn_delete);
        mDeleteButton.setOnClickListener(this);
        mProjects = projects.get(position);
        mTitletext.setText(mProjects.getTitle());
        mDescriptionText.setText(mProjects.getDescription());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_save) {
            if (mEditButton.getText().toString().equals("edit")) {
                mEditButton.setText("save");
                mTitletext.setEnabled(true);
                mDescriptionText.setEnabled(true);
            }  else {
                String title = mTitletext.getText().toString();
                String description = mDescriptionText.getText().toString();
                myAppDb.updateProject(mProjects.getPid(),title,description);
                setResult(1);
                finish();
            }
        }else {
            myAppDb.deleteProject(mProjects.getPid());
            setResult(1);
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAppDb.close();
    }
}
