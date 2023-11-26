package com.example.examinations;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class markinput extends AppCompatActivity {

    private EditText assignment1, assignment2, cat1, cat2, exam;
    private Spinner studentSpinner, lecCourseSpinner;


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

        Button submitButton = findViewById(R.id.submitMarks);
        submitButton.setOnClickListener(v -> {
            submitMarks();
        });
    }

    private void submitMarks() {
        try {
            // Retrieve marks from EditText fields
            int assignment1Marks = Integer.parseInt(assignment1.getText().toString().trim());
            int assignment2Marks = Integer.parseInt(assignment2.getText().toString().trim());
            int cat1Marks = Integer.parseInt(cat1.getText().toString().trim());
            int cat2Marks = Integer.parseInt(cat2.getText().toString().trim());
            int examMarks = Integer.parseInt(exam.getText().toString().trim());

            // Validate marks
            if (assignment1Marks <= 0 || assignment1Marks > 10 ||
                    assignment2Marks <= 0 || assignment2Marks > 20 ||
                    cat1Marks <= 0 || cat1Marks > 20 ||
                    cat2Marks <= 0 || cat2Marks > 20 ||
                    examMarks <= 0 || examMarks > 70
            ) {
                // Display an error toast
                Toast.makeText(this, "Invalid marks. Please check the values.", Toast.LENGTH_SHORT).show();
            } else {
                // Log the successful validation
                Log.d("SubmitMarks", "Marks validation successful");

                // Perform the submission logic here
                // ...

                // Optional: You can also check if the Spinners are selected
                String selectedStudent = studentSpinner.getSelectedItem().toString();
                String selectedCourse = lecCourseSpinner.getSelectedItem().toString();
                if (selectedStudent.isEmpty() || selectedCourse.isEmpty()) {
                    // Display an error toast for Spinners
                    Toast.makeText(this, "Please select a student and course.", Toast.LENGTH_SHORT).show();
                } else {
                    // Log the successful selection
                    Log.d("SubmitMarks", "Student: " + selectedStudent + ", Course: " + selectedCourse);
                }
            }
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            Toast.makeText(this, "Invalid input. Please enter numeric values.", Toast.LENGTH_SHORT).show();
            e.printStackTrace(); // Log the exception for further investigation
        }
    }
}
