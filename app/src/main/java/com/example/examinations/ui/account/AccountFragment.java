package com.example.examinations.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.examinations.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DatabaseReference rootdatabaseref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = rootdatabaseref.child("Students").child(uid);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("AccountFragment", "onDataChange triggered");
                    if (dataSnapshot.exists()) {
                        // Retrieve student details from dataSnapshot
                        String fullName = dataSnapshot.child("fullname").getValue(String.class);
                        String regNo = dataSnapshot.child("reg_no").getValue(String.class);
                        String nid = dataSnapshot.child("nid").getValue(String.class);
                        String dob = dataSnapshot.child("dob").getValue(String.class);
                        String phoneNo = dataSnapshot.child("phone_no").getValue(String.class);

                        // Update UI with student details
                        TextView fullNameTextView = binding.textFullName;
                        fullNameTextView.setText(fullName);

                        TextView regNoTextView = binding.textRegNo;
                        regNoTextView.setText(regNo);

                        TextView nidTextView = binding.textNid;
                        nidTextView.setText(nid);

                        TextView dobTextView = binding.textDob;
                        dobTextView.setText(dob);

                        TextView phoneNoTextView = binding.textPhoneNo;
                        phoneNoTextView.setText(phoneNo);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("AccountFragment", "Error: " + databaseError.getMessage());
                }
            });
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}