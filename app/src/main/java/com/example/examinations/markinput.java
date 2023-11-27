package com.example.examinations;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class markinput extends AppCompatActivity {

    private EditText assignment1, assignment2, cat1, cat2, exam;
    private Spinner studentSpinner, lecCourseSpinner;
    private DatabaseReference databaseReference;
    private String selectedFullName;
    private DataSnapshot dataSnapshot; // Add this line at the beginning of your class

    private static final String TAG = "MarkInputActivity";
    private Handler handler;  // Added handler for UI thread operations

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

        handler = new Handler();  // Initialize the handler

        // Fetch Student Name from the database and populate the student Spinner
        fetchStudentFullName();

        // Set up ArrayAdapter for Course Spinner using the array from strings.xml
        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lecCourseSpinner.setAdapter(courseAdapter);

        // Set up a listener for changes in the course Spinner
        lecCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected course change if needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Set up a listener for changes in the student Spinner
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected student change
                selectedFullName = getSelectedFullName(); // Store the selected registration number
                fetchStudentCourses(selectedFullName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        Button submitButton = findViewById(R.id.submitMarks);
        submitButton.setOnClickListener(v -> {
            submitButton.setEnabled(false);  // Disable the button during processing
            submitMarks();
        });

    }

    private void fetchStudentFullName() {
        databaseReference.child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSnapshot = snapshot; // Assign dataSnapshot here
                List<String> fullNames = new ArrayList<>();

                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    String fullName = studentSnapshot.child("fullname").getValue(String.class);
                    if (fullName != null) {
                        fullNames.add(fullName);
                    }
                }

                // Set up ArrayAdapter for Student Spinner
                ArrayAdapter<String> studentAdapter = new ArrayAdapter<>(markinput.this, android.R.layout.simple_spinner_item, fullNames);
                studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                studentSpinner.setAdapter(studentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if needed
                Log.e(TAG, "Error fetching full names: " + databaseError.getMessage());
            }
        });
    }

    private String getSelectedFullName() {
        String selectedFullName = studentSpinner.getSelectedItem().toString();
        // Fetch registration number based on the selected full name
        for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
            String fullName = studentSnapshot.child("fullname").getValue(String.class);
            String registrationNumber = studentSnapshot.getKey();
            if (fullName != null && fullName.equals(selectedFullName)) {
                return registrationNumber;
            }
        }
        return null; // Handle the case where the registration number is not found
    }

    // Updated method to fetch courses dynamically from the Realtime Database
    private void fetchStudentCourses(String selectedRegistrationNumber) {
        databaseReference.child("Students").child(selectedRegistrationNumber).child("Courses")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> courses = new ArrayList<>();

                        for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                            String courseName = courseSnapshot.getKey();
                            if (courseName != null) {
                                courses.add(courseName);
                            }
                        }

                        // Set up ArrayAdapter for Course Spinner
                        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(markinput.this, android.R.layout.simple_spinner_item, courses);
                        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        lecCourseSpinner.setAdapter(courseAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors if needed
                        Log.e(TAG, "Error fetching courses: " + databaseError.getMessage());
                    }
                });
    }


    private void submitMarks() {
        String selectedRegistrationNumber = getSelectedFullName(); // Store the selected registration number
        String selectedCourse = lecCourseSpinner.getSelectedItem().toString(); // Store the selected course

        // Validate the input fields
        if (TextUtils.isEmpty(selectedRegistrationNumber)) {
            showToast("Please select a student");
            return;
        }

        if (TextUtils.isEmpty(selectedCourse)) {
            showToast("Please select a course");
            return;
        }

        String assignment1Mark = assignment1.getText().toString().trim();
        String assignment2Mark = assignment2.getText().toString().trim();
        String cat1Mark = cat1.getText().toString().trim();
        String cat2Mark = cat2.getText().toString().trim();
        String examMark = exam.getText().toString().trim();

        if (!isValidMark(assignment1Mark, 20)) {
            showToast("Please enter a valid Assignment 1 mark");
            return;
        }

        if (!isValidMark(assignment2Mark, 20)) {
            showToast("Please enter a valid Assignment 2 mark");
            return;
        }

        if (!isValidMark(cat1Mark, 20)) {
            showToast("Please enter a valid CAT 1 mark");
            return;
        }

        if (!isValidMark(cat2Mark, 20)) {
            showToast("Please enter a valid CAT 2 mark");
            return;
        }

        if (!isValidMark(examMark, 60)) {
            showToast("Please enter a valid Exam mark");
            return;
        }

        // Store the marks in a Map
        Map<String, Object> marks = new HashMap<>();
        marks.put("Assignment 1", assignment1Mark);
        marks.put("Assignment 2", assignment2Mark);
        marks.put("CAT 1", cat1Mark);
        marks.put("CAT 2", cat2Mark);
        marks.put("Exam", examMark);

        double total = (Double.parseDouble(assignment1Mark) + Double.parseDouble(assignment2Mark)) / 2
                + (Double.parseDouble(cat1Mark) + Double.parseDouble(cat2Mark)) / 2
                + Double.parseDouble(examMark);

        marks.put("Total", total);

        // Update the marks in the Realtime Database
        databaseReference.child("Students").child(selectedRegistrationNumber).child("Courses").child(selectedCourse).child("Marks")
                .updateChildren(marks)
                .addOnSuccessListener(aVoid -> {
                    showToast("Marks updated successfully");
                    updateSumAndMeanForStudent(selectedRegistrationNumber);
                    Intent intent = new Intent(markinput.this, markinput.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> showToast("Failed to update marks: " + e.getMessage()));
    }

    private void updateSumAndMeanForStudent(String registrationNumber) {
        DatabaseReference coursesRef = databaseReference.child("Students").child(registrationNumber).child("Courses");

        coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum = 0;
                int numberOfUnits = 0;

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    String courseName = courseSnapshot.getKey();

                    if (courseName != null && courseSnapshot.child("Marks").child("Total").getValue() != null) {
                        double totalMark = courseSnapshot.child("Marks").child("Total").getValue(Double.class);
                        sum += totalMark;
                        numberOfUnits++;
                    }
                }

                // Calculate the mean
                double mean = numberOfUnits > 0 ? sum / numberOfUnits : 0;

                // Update the sum and mean in the Realtime Database
                Map<String, Object> updateValues = new HashMap<>();
                updateValues.put("sum", sum);
                updateValues.put("mean", mean);

                databaseReference.child("Students").child(registrationNumber).child("Courses").updateChildren(updateValues)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Sum and Mean updated successfully"))
                        .addOnFailureListener(e -> Log.e(TAG, "Failed to update Sum and Mean: " + e.getMessage()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if needed
                Log.e(TAG, "Error fetching courses: " + databaseError.getMessage());
            }
        });
    }


    private boolean isValidMark(String mark, int maxMark) {
        if (TextUtils.isEmpty(mark)) {
            return false;
        }

        try {
            double markValue = Double.parseDouble(mark);
            return markValue >= 0 && markValue <= maxMark;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showToast(String message) {
        handler.post(() -> Toast.makeText(markinput.this, message, Toast.LENGTH_SHORT).show());
    }
}