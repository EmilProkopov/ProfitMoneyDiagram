package com.course_project.profitmoneydiagram.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.asynctasks.LoggerAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoggerAsyncTask at = new LoggerAsyncTask(this);
        at.execute();
    }
}
