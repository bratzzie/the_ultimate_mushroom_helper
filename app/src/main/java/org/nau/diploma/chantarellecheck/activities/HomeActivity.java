package org.nau.diploma.chantarellecheck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.nau.diploma.chantarellecheck.Article;
import org.nau.diploma.chantarellecheck.adapters.ArticleRecycleViewAdapter;
import org.nau.diploma.chantarellecheck.adapters.clicklisteners.NewKnowledgeItemClickListener;
import org.nau.diploma.chantarellecheck.adapters.clicklisteners.SeasonItemClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rg.nau.diploma.chantarellecheck.R;

public class HomeActivity extends AppCompatActivity  {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        FloatingActionButton calendarButton = findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CalendarActivity.class));
            }
        });


        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.home_bottom_navigation);

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
                        startActivity(new Intent(getApplicationContext(), CameraActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.encyclopedia_page:
                        startActivity(new Intent(getApplicationContext(), CollectionActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.findings_page:
                        startActivity(new Intent(getApplicationContext(),FindingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        ImageButton show = (ImageButton) findViewById(R.id.show);
        ImageButton hide = (ImageButton) findViewById(R.id.hide);

        ImageButton show2 = (ImageButton) findViewById(R.id.show_2);
        ImageButton hide2 = (ImageButton) findViewById(R.id.hide_2);


        // data to populate the RecyclerView with
        List<Article> allArticles = new ArrayList<>();

        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("articles.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        try {
            allArticles = objectMapper.readValue(json, new TypeReference<List<Article>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        ArticleRecycleViewAdapter newKnowledgeArticleRecycleViewAdapter = new ArticleRecycleViewAdapter(this, allArticles.stream().filter(article -> article.getCategory().equals("new_knowledge")).collect(Collectors.toList()));
        NewKnowledgeItemClickListener newKnowledgeItemClickListener = new NewKnowledgeItemClickListener(newKnowledgeArticleRecycleViewAdapter);
        newKnowledgeArticleRecycleViewAdapter.setClickListener(newKnowledgeItemClickListener);
        RecyclerView learn_more_widget_recycle_view = findViewById(R.id.expandable_widget_recycler_view);

        learn_more_widget_recycle_view.setAdapter(newKnowledgeArticleRecycleViewAdapter);
        newKnowledgeArticleRecycleViewAdapter.notifyDataSetChanged();

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        learn_more_widget_recycle_view.setLayoutManager(verticalLayoutManager);
        learn_more_widget_recycle_view.setAdapter(newKnowledgeArticleRecycleViewAdapter);

        show.setVisibility(View.INVISIBLE);
        hide.setVisibility(View.VISIBLE);
        newKnowledgeArticleRecycleViewAdapter.setShowArticles(true);
        newKnowledgeArticleRecycleViewAdapter.notifyDataSetChanged();

        show.setOnClickListener(v -> {
            show.setVisibility(View.INVISIBLE);
            hide.setVisibility(View.VISIBLE);
            newKnowledgeArticleRecycleViewAdapter.setShowArticles(true);
            newKnowledgeArticleRecycleViewAdapter.notifyDataSetChanged();
        });

        hide.setOnClickListener(v -> {
            hide.setVisibility(View.INVISIBLE);
            show.setVisibility(View.VISIBLE);
            newKnowledgeArticleRecycleViewAdapter.setShowArticles(false);
            newKnowledgeArticleRecycleViewAdapter.notifyDataSetChanged();
        });


        ArticleRecycleViewAdapter seasonArticleRecycleViewAdapter = new ArticleRecycleViewAdapter(this, allArticles.stream().filter(article -> article.getCategory().equals("current_month") && article.getTitle().substring( article.getTitle().lastIndexOf("in ") + 3).toLowerCase().equals(ZonedDateTime.now().getMonth().toString().toLowerCase())).collect(Collectors.toList()));
        SeasonItemClickListener seasonItemClickListener = new SeasonItemClickListener(seasonArticleRecycleViewAdapter);
        seasonArticleRecycleViewAdapter.setClickListener(seasonItemClickListener);
        RecyclerView season_recycle_view = findViewById(R.id.expandable_widget_2_recycler_view);

        season_recycle_view.setAdapter(seasonArticleRecycleViewAdapter);
        seasonArticleRecycleViewAdapter.notifyDataSetChanged();

        LinearLayoutManager verticalLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        season_recycle_view.setLayoutManager(verticalLayoutManager2);
        season_recycle_view.setAdapter(seasonArticleRecycleViewAdapter);

        show2.setVisibility(View.INVISIBLE);
        hide2.setVisibility(View.VISIBLE);
        seasonArticleRecycleViewAdapter.setShowArticles(true);
        seasonArticleRecycleViewAdapter.notifyDataSetChanged();

        show2.setOnClickListener(v -> {
            show2.setVisibility(View.INVISIBLE);
            hide2.setVisibility(View.VISIBLE);
            seasonArticleRecycleViewAdapter.setShowArticles(true);
            seasonArticleRecycleViewAdapter.notifyDataSetChanged();
        });

        hide2.setOnClickListener(v -> {
            hide2.setVisibility(View.INVISIBLE);
            show2.setVisibility(View.VISIBLE);
            seasonArticleRecycleViewAdapter.setShowArticles(false);
            seasonArticleRecycleViewAdapter.notifyDataSetChanged();
        });
    }

}