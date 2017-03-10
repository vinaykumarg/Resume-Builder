package com.example.vinayg.resumebuilder.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.example.vinayg.resumebuilder.fragments.MyPreferenceFragment;
import com.example.vinayg.resumebuilder.authorization.SessionManager;

public class AboutActivity extends PreferenceActivity {

    public static SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        session = new SessionManager(this);

    }
    public void  logout(){
        session.logoutUser();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
