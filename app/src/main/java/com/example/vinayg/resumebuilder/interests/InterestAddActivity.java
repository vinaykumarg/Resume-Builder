package com.example.vinayg.resumebuilder.interests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.homepage.SessionManager;

import java.util.HashMap;

public class InterestAddActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mInterest;
    private Button saveButton;
    private MyAppDb myAppDb;
    private SessionManager session;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_add);
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        session = new SessionManager(this);
        user  = session.getUserDetails();
        mInterest = (EditText) findViewById(R.id.input_interest);
        saveButton = (Button) findViewById(R.id.btn_save);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = user.get(SessionManager.KEY_ID);
        String interest = mInterest.getText().toString();
        if (id.equals("")||interest.equals("")){
            Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
        } else {
            myAppDb.createInterest(id, interest);
            setResult(1);
            finish();
        }
    }
}
