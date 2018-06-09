package com.course_project.profitmoneydiagram.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.asynctasks.LoggerAsyncTask;
import com.course_project.profitmoneydiagram.asynctasks.SoloAsyncTask;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    LoggerAsyncTask loggerAsyncTask;
    SoloAsyncTask soloAsyncTask;

    //onClick for settings button.
    public void onClick1 (View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startLoggerAsyncTask () {

        loggerAsyncTask = new LoggerAsyncTask(this);
        loggerAsyncTask.execute();
    }

    public void startSoloAsyncTask () {

        soloAsyncTask = new SoloAsyncTask(this);
        soloAsyncTask.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggerAsyncTask = null;
        soloAsyncTask = null;

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp.getBoolean("extract_data_directly", false)) {
            startSoloAsyncTask();
        } else {
            startLoggerAsyncTask();
        }
    }

    @Override
    protected void onStop () {

        super.onStop();
        if (loggerAsyncTask != null) {
            loggerAsyncTask.cancel(true);
        }
        if (soloAsyncTask != null) {
            soloAsyncTask.cancel(true);
        }
    }

    @Override
    protected void onRestart () {
        //Canceal previous AsyncTask and start new.
        super.onRestart();
        if (loggerAsyncTask != null) {
            loggerAsyncTask.cancel(true);
        }
        if (soloAsyncTask != null) {
            soloAsyncTask.cancel(true);
        }

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp.getBoolean("extract_data_directly", false)) {
            startSoloAsyncTask();
        } else {
            startLoggerAsyncTask();
        }
    }

}
