package com.application.iitkharagpurproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityTasks extends AppCompatActivity {

    private Button Task2;
    private Button Task3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Task2 = findViewById(R.id.task2);
        Task3 = findViewById(R.id.task3);

        Task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityTasks.this,MainActivity.class);
                startActivity(i);
            }
        });

        Task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityTasks.this,MLActivity.class);
                startActivity(i);
            }
        });
    }
}
