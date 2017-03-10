package com.example.vinayg.resumebuilder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.models.Education;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EducationEditorActivity extends AppCompatActivity implements View.OnClickListener {
    private Button saveBtn;
    private SessionManager session;
    private MyAppDb myAppDb;
    private EditText mSchool,mStream,mScore;
    private Spinner spinStartYear;
    private Spinner spinEndYear;
    private Spinner instituteType;
    private HashMap<String, String> user;
    private Education education;
    private Button deletbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_editor);
        session = new SessionManager(getApplicationContext());
        myAppDb = new MyAppDb(this);
        myAppDb.open();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        user  = session.getUserDetails();
        education = myAppDb.gettAllEducation(user.get(SessionManager.KEY_ID)).get(position);
        mSchool = (EditText) findViewById(R.id.input_college) ;
        mSchool.setEnabled(false);
        mSchool.setText(education.getInstitute());
        Log.d("indexxxx", mSchool.getText().toString());
        mStream = (EditText) findViewById(R.id.input_board);
        mStream.setEnabled(false);
        mStream.setText(education.getStream());
        mScore = (EditText) findViewById(R.id.input_Score);
        mScore.setText(String.valueOf(education.getScore()));
        mScore.setEnabled(false);
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
        spinStartYear.setSelection(years.indexOf(education.getStartyear()+""));
        spinEndYear.setSelection(years.indexOf(education.getEndyear()+""));
        ArrayList<String> typeofInstitute = new ArrayList<String>();
        typeofInstitute.add("School");
        typeofInstitute.add("High School/ Jr. College");
        typeofInstitute.add("Degree College");
        typeofInstitute.add("Post Graduation");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeofInstitute);
        instituteType.setAdapter(adapter3);
        instituteType.setSelection(typeofInstitute.indexOf(education.getInstituteType()));
        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(this);
        deletbtn = (Button) findViewById(R.id.btn_delete);
        deletbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.btn_save) {
            if (saveBtn.getText().toString().equals("edit")) {
                saveBtn.setText("save");
                mScore.setEnabled(true);
                mSchool.setEnabled(true);
                mStream.setEnabled(true);

            }  else {
                String type = instituteType.getSelectedItem().toString();
                String uid = user.get(SessionManager.KEY_ID);
                long educationid = education.getId();
                String institute = mSchool.getText().toString();
                String stream = mStream.getText().toString();
                String startyear = spinStartYear.getSelectedItem().toString();
                String endyear = spinEndYear.getSelectedItem().toString();
                String score = mScore.getText().toString();
                if(type.equals("")||uid.equals("")||institute.equals("")
                        ||stream.equals("")||startyear.equals("")
                        ||endyear.equals("")||score.equals("")){
                    Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT);
                } else {
                    myAppDb.editEducation(educationid,type,uid,institute,stream,startyear,endyear,score);
                    setResult(1);
                    finish();
                }
            }
        }else {
            myAppDb.deleteEducation(education.getId());
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

