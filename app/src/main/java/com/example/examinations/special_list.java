package com.example.examinations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class special_list extends AppCompatActivity {
    ListView listView1;
    Button button1;
    TextView textView1;

    // firebase database instance
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference rootdatabaseref = FirebaseDatabase.getInstance().getReference().child("Student");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_list);

        listView1 = findViewById(R.id.special_list);
        textView1 = findViewById(R.id.name);

        // retrieve and display data from firebase child node Student subcategory Special
        rootdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot studentSnapshot : snapshot.getChildren()) {

                        String fullname = studentSnapshot.child("fullname").getValue(String.class);

                        List<String> list = (List<String>) snapshot.getValue();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(special_list.this, android.R.layout.simple_list_item_1, list);
                        listView1.setAdapter(adapter);

                        // set fullname to textview1
                        textView1.setText(fullname);
                }
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

}


