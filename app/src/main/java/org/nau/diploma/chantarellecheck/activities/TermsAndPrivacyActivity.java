package org.nau.diploma.chantarellecheck.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.TermsPrivacyFragmentBinding;


public class TermsAndPrivacyActivity extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TermsAndPrivacyActivity() {
        // Required empty public constructor
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TermsPrivacyFragmentBinding termsPrivacyBinding = TermsPrivacyFragmentBinding.inflate(getLayoutInflater());
        setContentView(termsPrivacyBinding.getRoot());

        FloatingActionButton backButton = findViewById(R.id.start_view_return_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        }
}