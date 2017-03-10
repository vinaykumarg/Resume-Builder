package com.example.vinayg.resumebuilder.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toolbar;

import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.activities.AboutActivity;

/**
 * Created by vinay.g.
 */
public class MyPreferenceFragment extends PreferenceFragment
{
    private Toolbar toolbar;
    AppCompatDelegate mDelegate;


    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        mDelegate = AppCompatDelegate.create(getActivity(),null);
        ActionBar actionBar = mDelegate.getSupportActionBar();
        actionBar.setTitle("About");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        PackageManager manager = getActivity().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        Preference versionpreference = findPreference("versioninfo");
        versionpreference.setSummary(String.format("Version: %s", version));
        Preference pref = findPreference("logoutbtn");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                // TODO Auto-generated method
                ((AboutActivity)getActivity()).logout();
                return false;
            }
        });
    }

}
