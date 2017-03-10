package com.example.vinayg.resumebuilder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.authorization.SessionManager;
import com.example.vinayg.resumebuilder.database.MyAppDb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddEducationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button saveBtn;
    private SessionManager session;
    private MyAppDb myAppDb;
    private EditText mSchool,mStream,mScore;
    private Spinner spinStartYear;
    private Spinner spinEndYear;
    private Spinner instituteType;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        user  = session.getUserDetails();
        setContentView(R.layout.activity_add_education);
        mSchool = (EditText) findViewById(R.id.input_college) ;
        mStream = (EditText) findViewById(R.id.input_board);
        mScore = (EditText) findViewById(R.id.input_Score);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1950; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spinStartYear = (Spinner)findViewById(R.id.yearspin);
        spinEndYear = (Spinner) findViewById(R.id.yearspin2);
        instituteType = (Spinner) findViewById(R.id.typeSpin);
        spinStartYear.setAdapter(adapter1);
        spinEndYear.setAdapter(adapter2);
        ArrayList<String> typeofInstitute = new ArrayList<String>();
        typeofInstitute.add("School");
        typeofInstitute.add("High School/ Jr. College");
        typeofInstitute.add("Degree College");
        typeofInstitute.add("Post Graduation");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeofInstitute);
        instituteType.setAdapter(adapter3);
        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_save) {
            String type = instituteType.getSelectedItem().toString();
            String uid = user.get(SessionManager.KEY_ID);
            String institute = mSchool.getText().toString();
            String stream = mStream.getText().toString();
            String startyear = spinStartYear.getSelectedItem().toString();
            String endyear = spinEndYear.getSelectedItem().toString();
            String score = mScore.getText().toString();
            if(type.equals("")||uid.equals("")||institute.equals("")
                    ||stream.equals("")||startyear.equals("")
                    ||endyear.equals("")||score.equals("")){
                Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show();
            } else {
                myAppDb.createEducation(type,uid,institute,stream,startyear,endyear,score);
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
