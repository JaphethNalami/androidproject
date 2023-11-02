package com.example.examinations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class adminhomepage extends AppCompatActivity {

    Button btn_mark_update, btn_lecturer_registration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);

        btn_lecturer_registration = findViewById(R.id.lecturer_register);
        btn_mark_update = findViewById(R.id.mark_update);

        btn_lecturer_registration.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, admin_lecregister.class);
            startActivity(intent);
        });

        btn_mark_update.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, markupdate.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Intent intent = new Intent(adminhomepage.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
    }
}