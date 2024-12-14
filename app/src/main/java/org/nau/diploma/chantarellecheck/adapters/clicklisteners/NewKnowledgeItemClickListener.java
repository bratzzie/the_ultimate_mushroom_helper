package org.nau.diploma.chantarellecheck.adapters.clicklisteners;

import android.content.Intent;
import android.view.View;

import org.nau.diploma.chantarellecheck.Article;
import org.nau.diploma.chantarellecheck.activities.HomeCollectionActivity;
import org.nau.diploma.chantarellecheck.adapters.ArticleRecycleViewAdapter;

import java.util.ArrayList;

public class NewKnowledgeItemClickListener implements ArticleRecycleViewAdapter.ItemClickListener {
    ArticleRecycleViewAdapter adapter;
    public NewKnowledgeItemClickListener(ArticleRecycleViewAdapter adapter) {
        this.adapter = adapter;
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), HomeCollectionActivity.class);

        Article selectedItem = adapter.getItem(position);
        intent.putExtra("title", selectedItem.getTitle());
        intent.putExtra("image_id", selectedItem.getImageDrawableId());
        intent.putStringArrayListExtra("items_ids", (ArrayList<String>) selectedItem.getItems());

        view.getContext().startActivity(intent);
    }
}
