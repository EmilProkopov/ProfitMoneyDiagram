package com.course_project.profitmoneydiagram.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.asynctasks.LoggerAsyncTask;
import com.course_project.profitmoneydiagram.asynctasks.SoloAsyncTask;
import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    LoggerAsyncTask loggerAsyncTask;
    SoloAsyncTask soloAsyncTask;
    FloatingActionButton fab;
    ProgressBar pb;
    Boolean paused;

    //onClick for settings button.
    public void onClick1 (View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onClick2 (View v) {

        paused = !paused;
        if (paused) {
            Log.d("MainActivity", "Paused");
            if (loggerAsyncTask != null) {
                Log.d("MainActivity", "loggerAsyncTask cancelling");

                loggerAsyncTask.cancel(true);
            }
            if (soloAsyncTask != null) {
                Log.d("MainActivity", "soloAsyncTask cancelled");

                soloAsyncTask.cancel(true);
            }
        } else {
            Log.d("MainActivity", "Played");

            if(sp.getBoolean("extract_data_directly", false)) {
                Log.d("MainActivity", "soloAsyncTask starting");
                startSoloAsyncTask();
            } else {
                Log.d("MainActivity", "loggerAsyncTask starting");
                startLoggerAsyncTask();
            }
        }
        updateFABandProgressBar();

    }

    private void updateFABandProgressBar() {
        Drawable d = fab.getDrawable();
        if (!paused) {
            d.setLevel(0);
        } else {
            d.setLevel(1);
        }
        pb.setProgress(0);
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

        Log.d("MainActivity", "ON_CREATE");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggerAsyncTask = null;
        soloAsyncTask = null;
        paused = false;

        LineChart chart = findViewById(R.id.diagram);
        chart.setNoDataText("Please wait. Data receiving may take up to 10 seconds");

        fab = findViewById(R.id.fab);
        pb = findViewById(R.id.progress_bar);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if(sp.getBoolean("extract_data_directly", false)) {
            startSoloAsyncTask();
        } else {
            startLoggerAsyncTask();
        }

        updateFABandProgressBar();
    }

    @Override
    protected void onStop () {
        Log.d("MainActivity", "ON_STOP");
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
        Log.d("MainActivity", "ON_RESTART");

        paused = false;
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

        updateFABandProgressBar();
    }

}
