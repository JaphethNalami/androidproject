package com.example.examinations.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;

public class CoursesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<String>> selectedCourses;

    public CoursesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Register for your Courses.");
        selectedCourses = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<String>> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<String> courses) {
        selectedCourses.setValue(courses);
    }

    public void retrieveSelectedCoursesFromFirebase(String userId) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        databaseRef.child("Students").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Courses")) {
                            DataSnapshot coursesSnapshot = dataSnapshot.child("Courses");
                            List<String> courses = new ArrayList<>();
                            for (DataSnapshot courseSnapshot : coursesSnapshot.getChildren()) {
                                String courseName = courseSnapshot.getKey();
                                courses.add(courseName);
                            }
                            setSelectedCourses(courses);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors if any
                    }
                });
    }

}
