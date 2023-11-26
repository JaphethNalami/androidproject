package com.example.examinations.ui.special;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.examinations.R;
import com.example.examinations.databinding.FragmentSpecialBinding;
import com.example.examinations.special_examinations;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SpecialFragment extends Fragment {

    private FragmentSpecialBinding binding;
    private SpecialViewModel specialViewModel;
    Button btn_special;
    TextView text_special;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSpecialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            specialViewModel = new ViewModelProvider(this).get(SpecialViewModel.class);
            specialViewModel.retrieveSelectedSpecialFromFirebase(userId);

        final TextView textView = binding.textSpecial;
        specialViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        btn_special = root.findViewById(R.id.btn_special);
        text_special = root.findViewById(R.id.text_special);

        List<String> selectedSpecials = specialViewModel.getSelectedSpecial().getValue();
        if (selectedSpecials != null && !selectedSpecials.isEmpty()) {
            // User has registered for courses, show the list
            btn_special.setVisibility(View.GONE); // Hide the registration button
            text_special.setVisibility(View.GONE); //Hide the registration text

            // Display registered courses in TextViews
            for (int i = 0; i < selectedSpecials.size(); i++) {
                String specialId = "special" + (i + 1);
                int textViewId = getResources().getIdentifier(specialId, "id", requireActivity().getPackageName());
                TextView specialTextView = root.findViewById(textViewId);
                specialTextView.setText(selectedSpecials.get(i));
            }
        } else {
            // User has not registered for courses, show the registration button
            text_special.setText("Apply for special examinations");
            btn_special.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), special_examinations.class);
                startActivity(intent);
            });
        }


    }
        return root;
    }
}