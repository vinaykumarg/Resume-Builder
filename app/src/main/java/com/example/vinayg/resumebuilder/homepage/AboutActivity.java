package com.example.vinayg.resumebuilder.homepage;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class AboutActivity extends PreferenceActivity {

    public static SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        session = new SessionManager(this);

    }
    public static void  logout(){
        session.logoutUser();
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
