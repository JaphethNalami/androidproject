package com.example.examinations.ui.home;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.examinations.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private Button btnSave, btnPrint;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnSave = view.findViewById(R.id.btn_save);
        btnPrint = view.findViewById(R.id.btn_print);
        btnSave.setOnClickListener(v -> {
            // Call the method to fetch and save course units to Firebase Storage
            fetchAndSaveCourseUnitsToStorage();
        });
        btnPrint.setOnClickListener(v -> {
            // Call the method to fetch and print/download course units from Firebase Storage
            fetchAndPrintCourseUnitsFromStorage();
        });


        return view;
    }

    private void fetchAndSaveCourseUnitsToStorage() {
        // Get the current user ID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not logged in
            return;
        }

        // Use the user ID to create a dynamic folder in Firebase Storage
        String userId = currentUser.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("student_courses").child(userId);

        // Fetching the selected courses for the specific user
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Students").child(userId).child("Courses")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Map<String, String> coursesMap = new HashMap<>();

                            for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                                // Get the course name
                                String courseName = courseSnapshot.getKey();

                                // Get the units directly from the "Courses" node
                                String units = courseSnapshot.getValue(Boolean.class) ? "1" : "0"; // Assuming units are boolean values

                                // Add the course details to the map
                                coursesMap.put(courseName, units);
                            }

                            // Save the coursesMap to Firebase Storage
                            storageReference.putBytes(mapToByteArray(coursesMap));
                        } else {
                            // Handle the case where the courses data doesn't exist
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that may occur
                    }
                });
    }

    // Convert a Map to a byte array (serialization)
    private byte[] mapToByteArray(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        return stringBuilder.toString().getBytes();
    }

    private void fetchAndPrintCourseUnitsFromStorage() {
        // Get the current user ID from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not logged in
            return;
        }

        // Use the user ID to create a dynamic folder in Firebase Storage
        String userId = currentUser.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("student_courses").child(userId);

        // Create a local file to save the downloaded data
        File localFile;
        try {
            localFile = File.createTempFile("courses", "txt", requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error creating local file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Download the file from Firebase Storage to the local file
        storageReference.getFile(localFile)
                .addOnSuccessListener(taskSnapshot -> {
                    // Handle the successful download, e.g., display or process the content
                    // Here, you can open the file or display the content as needed
                    // For simplicity, I'm just printing the path for now
                    Toast.makeText(getActivity(), "Downloaded to: " + localFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    // Handle failed download
                    Toast.makeText(getActivity(), "Download failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
