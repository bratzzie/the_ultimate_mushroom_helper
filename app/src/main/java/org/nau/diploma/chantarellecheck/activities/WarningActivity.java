package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import rg.nau.diploma.chantarellecheck.R;
import rg.nau.diploma.chantarellecheck.databinding.WarningActivityBinding;

public class WarningActivity extends AppCompatActivity {
public static final String PROPERTIES_FILE_NAME = "properties.json";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WarningActivityBinding termsPrivacyBinding = WarningActivityBinding.inflate(getLayoutInflater());
        setContentView(termsPrivacyBinding.getRoot());

        MaterialButton actionButton = findViewById(R.id.warning_page_action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WarningActivity.this, HomeActivity.class));
            }
        });

        MaterialButton doNotRemindButton = findViewById(R.id.wp_do_not_remind_me_button);
        doNotRemindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("warning", false);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // Convert JsonObject to String Format
                String userString = jsonObject.toString();// Define the File Path and its Name
                File file = new File(WarningActivity.this.getFilesDir(), PROPERTIES_FILE_NAME);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    bufferedWriter.write(userString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                startActivity(new Intent(WarningActivity.this, BaseActivity.class));
            }
        });
    }
}