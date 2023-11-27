package com.example.examinations;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fail_list extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_list);

        listView = findViewById(R.id.listView);
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Students");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Extracting necessary information from the snapshot
                    String fullName = snapshot.child("fullname").getValue(String.class);
                    DataSnapshot coursesSnapshot = snapshot.child("Courses");
                    Double meanDouble = coursesSnapshot.child("mean").getValue(Double.class);

                    // Declare mean outside the if block
                    double mean = 0.0;  // Default value or any other appropriate default value

                    // Check if the mean is not null and then convert it to a primitive double
                    if (meanDouble != null) {
                        mean = meanDouble; // Now mean is a primitive double
                    }

                    // Check if the mean is less than 40
                    if (mean < 40) {
                        String listItem = fullName + "\nMean: " + mean;
                        studentList.add(listItem);
                    }
                }

                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
    }