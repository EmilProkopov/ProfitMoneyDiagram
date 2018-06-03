package com.course_project.profitmoneydiagram.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.asynctasks.LoggerAsyncTask;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoggerAsyncTask at = new LoggerAsyncTask(this);
        at.execute();
    }
}
