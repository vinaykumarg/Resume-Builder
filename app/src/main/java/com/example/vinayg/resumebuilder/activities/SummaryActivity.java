package com.example.vinayg.resumebuilder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.models.Summary;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

import java.util.HashMap;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener{
    EditText msummaryText;
    private Button mSaveButton;
    private MyAppDb myAppDb;
    private SessionManager session;
    HashMap<String, String> user;
    private static final int REFRESH = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        user = session.getUserDetails();
        setContentView(R.layout.activity_summary);
        msummaryText = (EditText)findViewById(R.id.input_summary);
        Summary summaryObj = myAppDb.getSummary(user.get(SessionManager.KEY_ID));
        if (summaryObj!=null) {
            String summary = summaryObj.getSummary();
            msummaryText.setText(summary);
        }
        mSaveButton = (Button) findViewById(R.id.save);
        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.save) {
            String summary = msummaryText.getText().toString();
            if (!summary.equals("")) {
                myAppDb.createSummary(user.get(SessionManager.KEY_ID),summary);
                setResult(REFRESH);
                finish();
            } else {
                Toast.makeText(this,"enter details",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAppDb.close();
    }
}
