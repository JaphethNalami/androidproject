package com.example.examinations;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class markinput extends AppCompatActivity {

    private EditText assignment1, assignment2, cat1, cat2, exam;
    private Spinner studentSpinner, lecCourseSpinner;

    // Add a reference to your Firebase Realtime Database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markinput);

        // Initialize your EditText fields and Spinners
        assignment1 = findViewById(R.id.assignment1);
        assignment2 = findViewById(R.id.assignment2);
        cat1 = findViewById(R.id.cat1);
        cat2 = findViewById(R.id.cat2);
        exam = findViewById(R.id.exam);
        studentSpinner = findViewById(R.id.studentSpinner);
        lecCourseSpinner = findViewById(R.id.lecCourseSpinner);

        // Initialize your Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Button submitButton = findViewById(R.id.submitMarks);
        submitButton.setOnClickListener(v -> submitMarks());
    }

    private void submitMarks() {
        // Extract data from UI elements
        String selectedRegistrationNumber = studentSpinner.getSelectedItem().toString();
        String selectedCourse = lecCourseSpinner.getSelectedItem().toString();
        String assignment1Mark = assignment1.getText().toString();
        String assignment2Mark = assignment2.getText().toString();
        String cat1Mark = cat1.getText().toString();
        String cat2Mark = cat2.getText().toString();
        String examMark = exam.getText().toString();

        // Validate input data (you may want to add more validation)
        if (assignment1Mark.isEmpty() || assignment2Mark.isEmpty() || cat1Mark.isEmpty()
                || cat2Mark.isEmpty() || examMark.isEmpty()) {
            Toast.makeText(this, "Please fill in all marks", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to store assessment values
        Map<String, String> assessmentsMap = new HashMap<>();
        assessmentsMap.put("Assignment1", assignment1Mark);
        assessmentsMap.put("Assignment2", assignment2Mark);
        assessmentsMap.put("Cat1", cat1Mark);
        assessmentsMap.put("Cat2", cat2Mark);
        assessmentsMap.put("Exam", examMark);

        // Save marks to the Realtime Database under the selected course (unit)
        databaseReference.child("Students").child(selectedRegistrationNumber).child("Courses")
                .child(selectedCourse).setValue(assessmentsMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Marks submitted successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, you can save marks to Firebase Storage here if needed
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit marks: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
