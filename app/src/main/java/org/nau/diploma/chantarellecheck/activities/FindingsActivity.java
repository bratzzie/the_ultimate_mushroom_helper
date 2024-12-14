package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.nau.diploma.chantarellecheck.fragments.CameraFragment;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.FindingsActivityBinding;

public class FindingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FindingsActivityBinding findingsActivity
                = FindingsActivityBinding.inflate(getLayoutInflater());
        setContentView(findingsActivity.getRoot());

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView= findViewById(R.id.findings_bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home_page);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan_page:
                        startActivity(new Intent(getApplicationContext(), CameraFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.encyclopedia_page:
                        startActivity(new Intent(getApplicationContext(), CollectionActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.findings_page:
                        return true;
                }
                return false;
            }
        });
    }
}