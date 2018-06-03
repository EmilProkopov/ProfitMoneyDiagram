package com.course_project.profitmoneydiagram.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.course_project.profitmoneydiagram.R;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }


}
