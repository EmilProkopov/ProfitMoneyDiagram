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

    //Show a text message on the screan.
    private void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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


        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp.getBoolean("extract_data_directly", false)) {
            startSoloAsyncTask();
        } else {
            startLoggerAsyncTask();
        }

        LoggerAsyncTask at = new LoggerAsyncTask(this);
        at.execute();
    }

}
