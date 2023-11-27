package com.example.examinations;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
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

        // Validate input data
        if (assignment1Mark.isEmpty() || assignment2Mark.isEmpty() || cat1Mark.isEmpty()
                || cat2Mark.isEmpty() || examMark.isEmpty()) {
            Toast.makeText(this, "Please fill in all marks", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate marks range
        if (!isValidMark(assignment1Mark, 10) || !isValidMark(assignment2Mark, 10)
                || !isValidMark(cat1Mark, 30) || !isValidMark(cat2Mark, 30)
                || !isValidMark(examMark, 60)) {
            Toast.makeText(this, "Invalid mark values. Please check the ranges.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate total
        double total = (Double.parseDouble(assignment1Mark) + Double.parseDouble(assignment2Mark)) / 2
                + (Double.parseDouble(cat1Mark) + Double.parseDouble(cat2Mark)) / 2
                + Double.parseDouble(examMark);

        // Create a map to store assessment values
        Map<String, String> assessmentsMap = new HashMap<>();
        assessmentsMap.put("Assignment1", assignment1Mark);
        assessmentsMap.put("Assignment2", assignment2Mark);
        assessmentsMap.put("Cat1", cat1Mark);
        assessmentsMap.put("Cat2", cat2Mark);
        assessmentsMap.put("Exam", examMark);
        assessmentsMap.put("Total", String.valueOf(total)); // Add total to the map

        // Save marks to the Realtime Database under the selected course (unit)
        databaseReference.child("Students").child(selectedRegistrationNumber).child("Courses")
                .child(selectedCourse).setValue(assessmentsMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Marks submitted successfully", Toast.LENGTH_SHORT).show();

                    // Update the "Mean" and "Sum" after submitting unit marks
                    updateMeanAndSum(selectedRegistrationNumber);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit marks: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Helper method to update "Mean" and "Sum"
    private void updateMeanAndSum(String selectedRegistrationNumber) {
        DatabaseReference studentRef = databaseReference.child("Students").child(selectedRegistrationNumber);

        studentRef.child("Courses").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot coursesSnapshot = task.getResult();
                if (coursesSnapshot.exists()) {
                    double sum = 0.0;
                    int totalUnits = 0;

                    // Calculate sum of unit totals
                    for (DataSnapshot courseSnapshot : coursesSnapshot.getChildren()) {
                        // Use getDouble to directly retrieve the value as a double
                        Double unitTotal = courseSnapshot.child("Total").getValue(Double.class);
                        if (unitTotal != null) {
                            sum += unitTotal;
                            totalUnits++;
                        }
                    }


                    // Calculate mean
                    double mean = sum / totalUnits;

                    // Update "Mean" and "Sum" in the Realtime Database
                    studentRef.child("Mean").setValue(String.valueOf(mean))
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Mean updated successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to update Mean: " + e.getMessage(), Toast.LENGTH_SHORT).show());

                    studentRef.child("Sum").setValue(String.valueOf(sum))
                            .addOnSuccessListener(aVoid -> Toast.makeText(this, "Sum updated successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to update Sum: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // Helper method to validate if a mark is within the specified range
    private boolean isValidMark(String mark, int max) {
        try {
            int markValue = Integer.parseInt(mark);
            return markValue >= 0 && markValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
