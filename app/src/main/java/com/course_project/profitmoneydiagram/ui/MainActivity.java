package com.course_project.profitmoneydiagram.ui;

import android.Manifest;
import android.os.AsyncTask;
import android.support.annotation.WorkerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.asynctasks.WorkerAsyncTask;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkerAsyncTask at = new WorkerAsyncTask(this);
        at.execute();
    }
}
