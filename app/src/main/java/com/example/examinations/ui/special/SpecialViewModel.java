package com.example.examinations.ui.special;

import androidx.annotation.NonNull;
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

public class SpecialViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<String>> selectedSpecials;

    public SpecialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Apply Special Examination.");
        selectedSpecials = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<String>> getSelectedSpecial() {
        return selectedSpecials;
    }

    public void setSelectedSpecials(List<String> specials) {
        selectedSpecials.setValue(specials);
    }

    public void retrieveSelectedSpecialFromFirebase(String userId) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        databaseRef.child("Students").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Specials")) {
                            DataSnapshot specialsSnapshot = dataSnapshot.child("Specials");
                            List<String> specials = new ArrayList<>();
                            for (DataSnapshot specialSnapshot : specialsSnapshot.getChildren()) {
                                String specialName = specialSnapshot.getKey();
                                specials.add(specialName);
                            }
                            setSelectedSpecials(specials);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors if any
                    }
                });
    }
}