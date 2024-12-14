package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.nau.diploma.chantarellecheck.adapters.ScanResultRecyclerViewAdapter;
import org.nau.diploma.chantarellecheck.adapters.clicklisteners.ScanResultItemClickListener;

import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class ScanResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        Intent intent = getIntent();
        List<String> items = intent.getStringArrayListExtra("results_ids");
        List<String> scores = intent.getStringArrayListExtra("results_scores");



        FloatingActionButton returnButton = findViewById(R.id.scan_result_return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanResultActivity.this.finish();
            }
        });


        ScanResultRecyclerViewAdapter scanResultRecyclerViewAdapter = new ScanResultRecyclerViewAdapter(this, items, scores);
        ScanResultItemClickListener scanResultItemClickListener = new ScanResultItemClickListener(scanResultRecyclerViewAdapter, this);
        scanResultRecyclerViewAdapter.setClickListener(scanResultItemClickListener);
        RecyclerView recyclerView = findViewById(R.id.scan_results_recycler_view);

        recyclerView.setAdapter(scanResultRecyclerViewAdapter);
        scanResultRecyclerViewAdapter.notifyDataSetChanged();

        LinearLayoutManager horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(scanResultRecyclerViewAdapter);
        scanResultRecyclerViewAdapter.notifyDataSetChanged();
    }
}