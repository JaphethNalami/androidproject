package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class admin extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        button = findViewById(R.id.admin_login);
        button.setOnClickListener(View -> {
            Intent intent = new Intent(admin.this, adminhomepage.class);
            startActivity(intent);
        });
    }
}