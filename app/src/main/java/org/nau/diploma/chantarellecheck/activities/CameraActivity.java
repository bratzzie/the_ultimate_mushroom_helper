package org.nau.diploma.chantarellecheck.activities;

import static org.nau.diploma.chantarellecheck.activities.WarningActivity.PROPERTIES_FILE_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.nau.diploma.chantarellecheck.fragments.CameraFragment;
import org.nau.diploma.chantarellecheck.fragments.ScanTipFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import rg.nau.diploma.chantarellecheck.R;

public class CameraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        File file = new File(CameraActivity.this.getFilesDir(), PROPERTIES_FILE_NAME);
        String isWarningActivated = "true";
        if(file.exists()) {
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (line != null){
                stringBuilder.append(line).append("\n");
                try {
                    line = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                bufferedReader.close();// This responce will have Json Format String
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String preferences = stringBuilder.toString();

            JSONObject jsonObject  = null;//Java Object
            try {
                jsonObject = new JSONObject(preferences);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                isWarningActivated = jsonObject.get("scan_tip").toString();
            } catch (JSONException e) {
                isWarningActivated = "true";
            }
        }


        if (Boolean.parseBoolean(isWarningActivated)) {
            Toast.makeText(CameraActivity.this, isWarningActivated, Toast.LENGTH_SHORT).show();
            ScanTipFragment fragment;
            fragment = new ScanTipFragment();
            loadFragment(fragment);
        } else {
            CameraFragment fragment;
            fragment = new CameraFragment();
            loadFragment(fragment);
        }



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.scan_bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.scan_page);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.scan_page:
                        return true;
                    case R.id.encyclopedia_page:
                        startActivity(new Intent(getApplicationContext(), CollectionActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.findings_page:
                        startActivity(new Intent(getApplicationContext(), FindingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //frame_container is your layout name in xml file
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
