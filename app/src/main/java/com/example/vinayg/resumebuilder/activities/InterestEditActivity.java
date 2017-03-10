package com.example.vinayg.resumebuilder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.models.Interest;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

import java.util.HashMap;

public class InterestEditActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mInterest;
    private Button mEditButton, mDeleteButton;
    private MyAppDb myAppDb;
    private SessionManager session;
    private HashMap<String, String> user;
    private String uid;
    private Interest interestObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_edit);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        mInterest = (EditText)findViewById(R.id.input_interest);
        mEditButton = (Button)findViewById(R.id.btn_save);
        mDeleteButton = (Button)findViewById(R.id.btn_delete);
        mEditButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        session = new SessionManager(getApplicationContext());
        user = session.getUserDetails();
        uid = user.get(SessionManager.KEY_ID);
        interestObj = myAppDb.gettAllInterest(uid).get(position);
        mInterest.setText(interestObj.getInterest());
        mInterest.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_save) {
            if (mEditButton.getText().toString().equals("edit")) {
                mEditButton.setText("save");
                mInterest.setEnabled(true);

            }  else {
                String interestid = interestObj.getId();
                String uid = user.get(SessionManager.KEY_ID);
                String interest = mInterest.getText().toString();
                if(interest.equals("")||uid.equals("")){
                    Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
                } else {
                    myAppDb.updateInterest(interestid,uid,interest);
                    setResult(1);
                    finish();
                }
            }
        }else {
            myAppDb.deleteInterest(interestObj.getId());
            setResult(1);
            finish();
        }
    }
}
