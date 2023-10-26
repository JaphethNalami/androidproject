package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.student);
        button.setOnClickListener(View-> {
            Intent intent=new Intent(MainActivity.this, login.class);
            startActivity(intent);
        }
);
        button=findViewById(R.id.lecturer);
        button.setOnClickListener(View-> {
            Intent intent=new Intent(MainActivity.this, leclogin.class);
            startActivity(intent);
                });
        button=findViewById(R.id.admin);
        button.setOnClickListener(View-> {
            Intent intent=new Intent(MainActivity.this, admin.class);
            startActivity(intent);
        });
    }
}