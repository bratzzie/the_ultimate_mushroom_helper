/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.nau.diploma.chantarellecheck.fragments.CameraFragment;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.MainActivityBinding;


public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding mainActivityBinding
                = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(mainActivityBinding.getRoot());

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home_page);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home_page:
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
                        startActivity(new Intent(getApplicationContext(), FindingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
