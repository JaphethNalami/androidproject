package com.example.examinations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Lec_Marks_Enter extends AppCompatActivity {

    EditText editTextStudentId, editTextAssignment1, editTextAssignment2, editTextCat1, editTextCat2, editTextExam;
    DatabaseReference databaseReference;

    public static class ScoreData {
        // Fields or properties
        private final int assignment1;
        private final int assignment2;
        private final int cat1;
        private final int cat2;
        private final int exam;

        // Constructor
        public ScoreData(int assignment1, int assignment2, int cat1, int cat2, int exam) {
            this.assignment1 = assignment1;
            this.assignment2 = assignment2;
            this.cat1 = cat1;
            this.cat2 = cat2;
            this.exam = exam;
        }

        // Other methods or code as needed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_marks_enter);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("scores");

        // Initialize UI elements
        editTextStudentId = findViewById(R.id.editTextStudentId);
        editTextAssignment1 = findViewById(R.id.editTextAssignment1);
        editTextAssignment2 = findViewById(R.id.editTextAssignment2);
        editTextCat1 = findViewById(R.id.editTextCat1);
        editTextCat2 = findViewById(R.id.editTextCat2);
        editTextExam = findViewById(R.id.editTextExam);
    }

    public void submitScores(View view) {
        // Retrieve data from EditText fields
        String studentId = editTextStudentId.getText().toString();
        int assignment1 = parseScore(editTextAssignment1);
        int assignment2 = parseScore(editTextAssignment2);
        int cat1 = parseScore(editTextCat1);
        int cat2 = parseScore(editTextCat2);
        int exam = parseScore(editTextExam);

        // If any score is invalid, show an error message and return
        if (assignment1 == -1 || assignment2 == -1 || cat1 == -1 || cat2 == -1 || exam == -1) {
            Toast.makeText(this, "Invalid score entered. Please enter valid scores.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a ScoreData object
        ScoreData scoreData = new ScoreData(assignment1, assignment2, cat1, cat2, exam);

        // Save scores to the database
        databaseReference.child(studentId).setValue(scoreData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(Lec_Marks_Enter.this, "Scores submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Lec_Marks_Enter.this, "Error submitting scores: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Helper method to parse scores and validate input
    private int parseScore(EditText editText) {
        try {
            int score = Integer.parseInt(editText.getText().toString());
            // Assume a valid score range, for example, 0 to 100
            if (score < 0 || score > 100) {
                return -1; // Invalid score
            }
            return score;
        } catch (NumberFormatException e) {
            return -1; // Invalid input (not a number)
        }
    }
}
