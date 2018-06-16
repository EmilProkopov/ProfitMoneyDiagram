package com.course_project.profitmoneydiagram.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.asynctasks.LoggerAsyncTask;
import com.course_project.profitmoneydiagram.asynctasks.SoloAsyncTask;
import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    LoggerAsyncTask loggerAsyncTask;
    SoloAsyncTask soloAsyncTask;
    Boolean paused;

    //onClick for settings button.
    public void onClick1 (View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onClick2 (View v) {
        FloatingActionButton fab = (FloatingActionButton) v;
        Drawable d = fab.getDrawable();
        paused = !paused;
        if (paused) {
            Log.e("MainActivity", "Paused");
            if (loggerAsyncTask != null) {
                Log.e("MainActivity", "loggerAsyncTask cancelling");

                loggerAsyncTask.cancel(true);
            }
            if (soloAsyncTask != null) {
                Log.e("MainActivity", "soloAsyncTask cancelled");

                soloAsyncTask.cancel(true);
            }
        } else {
            Log.e("MainActivity", "Played");

            if(sp.getBoolean("extract_data_directly", false)) {
                startSoloAsyncTask();
            } else {
                startLoggerAsyncTask();
            }
        }

        d.setLevel(d.getLevel() == 0 ? 1 : 0 );
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
        paused = false;

        LineChart chart = findViewById(R.id.diagram);
        chart.setNoDataText("Please wait. Data receiving may take up to 10 seconds");

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        /*sp.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                          String key) {
                        if (key.equals("extract_data_directly")) {

                            if (sharedPreferences.getBoolean(key, false)) {
                                startSoloAsyncTask();
                            } else {
                                startLoggerAsyncTask();
                            }
                        }
                    }
                }
        );*/

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
        paused = false;
    }

    @Override
    protected void onRestart () {
        //Cancel previous AsyncTask and start new.
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
