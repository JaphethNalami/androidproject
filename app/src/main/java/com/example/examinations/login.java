package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class login extends AppCompatActivity {
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=findViewById(R.id.regnew);
        button.setOnClickListener(View-> {
            Intent intent=new Intent(login.this, register.class);
            startActivity(intent);
        });
    }
}