package org.nau.diploma.chantarellecheck.adapters.clicklisteners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import org.nau.diploma.chantarellecheck.activities.SingleElementView;
import org.nau.diploma.chantarellecheck.fragments.CollectionRecycleViewAdapter;
import org.nau.diploma.chantarellecheck.RecycleViewItem;

import java.util.ArrayList;

public class CollectionItemClickListener implements CollectionRecycleViewAdapter.ItemClickListener {
    private CollectionRecycleViewAdapter recycleViewAdapter;
    private Activity activity;
    public CollectionItemClickListener(CollectionRecycleViewAdapter adapter, Activity activity) {
        recycleViewAdapter = adapter;
        this.activity = activity;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), SingleElementView.class);

        RecycleViewItem selectedItem = recycleViewAdapter.getItem(position);

        intent.putExtra("name", selectedItem.getName());
        intent.putStringArrayListExtra("scientificNames", (ArrayList<String>) selectedItem.getScientificNames());
        intent.putStringArrayListExtra("commonNames", (ArrayList<String>) selectedItem.getCommonNames());
        intent.putExtra("image", selectedItem.getImageURL());
        intent.putExtra("habitat", selectedItem.getHabitat());
        intent.putExtra("distribution", selectedItem.getDistribution());
        intent.putExtra("cap", selectedItem.getCap());
        intent.putExtra("tubes", selectedItem.getTubes());
        intent.putExtra("stem", selectedItem.getStem());
        intent.putExtra("flesh", selectedItem.getFlesh());
        intent.putExtra("AMH", selectedItem.getAMH());
        intent.putExtra("ACW", selectedItem.getACW());
        intent.putExtra("smell", selectedItem.getSmell());
        intent.putExtra("sporePrint", selectedItem.getSporePrint());
        intent.putExtra("frequency", selectedItem.getFrequency());
        intent.putExtra("season", selectedItem.getSeason());
        intent.putExtra("eatable", selectedItem.getEatable());
        intent.putExtra("id", selectedItem.getId());


        activity.startActivity(intent);
    }
}
