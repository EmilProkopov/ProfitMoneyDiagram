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
import com.course_project.profitmoneydiagram.asynctasks.SoloAsyncTask;
import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    SoloAsyncTask soloAsyncTask;
    FloatingActionButton fab;
    ProgressBar pb;
    Boolean paused;

    //onClick for settings button.
    public void onClick1(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onClick2(View v) {

        paused = !paused;
        if (paused) {
            if (soloAsyncTask != null) {
                Log.d("MainActivity", "soloAsyncTask cancelled");

                soloAsyncTask.cancel(true);
            }
        } else {
            Log.d("MainActivity", "Played");
            Log.d("MainActivity", "soloAsyncTask starting");
            startSoloAsyncTask();
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

    public void startSoloAsyncTask() {

        soloAsyncTask = new SoloAsyncTask(this);
        soloAsyncTask.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("MainActivity", "ON_CREATE");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soloAsyncTask = null;
        paused = false;

        LineChart chart = findViewById(R.id.diagram);
        chart.setNoDataText("Please wait. Data receiving may take up to 10 seconds");

        fab = findViewById(R.id.fab);
        pb = findViewById(R.id.progress_bar);

        startSoloAsyncTask();

        updateFABandProgressBar();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "ON_STOP");
        super.onStop();

        if (soloAsyncTask != null) {
            soloAsyncTask.cancel(true);
        }
        paused = false;
    }

    @Override
    protected void onRestart() {
        //Cancel previous AsyncTask and start new.
        super.onRestart();
        Log.d("MainActivity", "ON_RESTART");

        paused = false;

        if (soloAsyncTask != null) {
            soloAsyncTask.cancel(true);
        }

        startSoloAsyncTask();

        updateFABandProgressBar();
    }

}
