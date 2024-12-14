package org.nau.diploma.chantarellecheck.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.StartViewBinding;


public class StartActivity extends AppCompatActivity {

    private static final String PROPERTIES_FILE_NAME = "properties.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartViewBinding startViewBinding = StartViewBinding.inflate(getLayoutInflater());
        setContentView(startViewBinding.getRoot());

        MaterialButton termsAndPrivacyButton = findViewById(R.id.termsAndPrivacyButton);
        termsAndPrivacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, TermsAndPrivacyActivity.class));
            }
        });

        MaterialButton actionButton = findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(StartActivity.this.getFilesDir(), PROPERTIES_FILE_NAME);
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
                        isWarningActivated = jsonObject.get("warning").toString();
                    } catch (JSONException e) {
                        isWarningActivated = "true";
                    }
                }


                if (Boolean.parseBoolean(isWarningActivated)) {
                 //   Toast.makeText(StartActivity.this, isWarningActivated, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StartActivity.this, WarningActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, HomeActivity.class));
                }
            }
        });
    }

    public static boolean hasPermission(Context context) {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher
            = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this,
                                    "Permission request granted",
                                    Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(this,
                                    "Permission request denied",
                                    Toast.LENGTH_LONG)
                            .show();
                }
            });

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }

    public void startApp(View v){
        startActivity(new Intent(StartActivity.this, BaseActivity.class));
    }
}