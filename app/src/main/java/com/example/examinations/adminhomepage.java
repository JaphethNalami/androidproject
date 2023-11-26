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

    Button btn_mark_update, miss_partial,list,passlist,fail, special;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);

        miss_partial = findViewById(R.id.missing_partial);
        btn_mark_update = findViewById(R.id.mark_update);
        list = findViewById(R.id.btn_stuaca);
        passlist = findViewById(R.id.passlist);
        fail = findViewById(R.id.btn_fail);
        special = findViewById(R.id.special);


        miss_partial.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, missing_marks_partial.class);
            startActivity(intent);
        });

        btn_mark_update.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, markupdate.class);
            startActivity(intent);
        });

        list.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, missing_marks_all.class);
            startActivity(intent);
        });

        passlist.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, pass_list.class);
            startActivity(intent);
        });

        fail.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, fail_list.class);
            startActivity(intent);
        });

        special.setOnClickListener(v -> {
            Intent intent = new Intent(adminhomepage.this, special_list.class);
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