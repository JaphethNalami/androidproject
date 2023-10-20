package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class leclogin extends AppCompatActivity {
      public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leclogin);
        button=findViewById(R.id.btnlink);
        button.setOnClickListener(View-> {
            gotoUrl("https://mail.google.com/mail/?view=cm&source=mailto&to=nalamijapheth@gmail.com&body=Type%20Email%20and%20password%20Below&su=LECTURER%20ACCOUNT%20REGISTRATION%20EMAIL");
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}