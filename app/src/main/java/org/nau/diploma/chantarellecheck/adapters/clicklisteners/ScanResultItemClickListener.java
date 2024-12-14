package org.nau.diploma.chantarellecheck.adapters.clicklisteners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import org.nau.diploma.chantarellecheck.ServiceForLoadingMushroomsDataset;
import org.nau.diploma.chantarellecheck.activities.SingleElementView;
import org.nau.diploma.chantarellecheck.adapters.ScanResultRecyclerViewAdapter;
import org.nau.diploma.chantarellecheck.RecycleViewItem;

import java.util.ArrayList;

public class ScanResultItemClickListener implements ScanResultRecyclerViewAdapter.ItemClickListener {
    private ScanResultRecyclerViewAdapter adapter;
    private Activity activity;

    public ScanResultItemClickListener(ScanResultRecyclerViewAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), SingleElementView.class);

        String selectedItemId = adapter.getItem(position);
        RecycleViewItem selectedItem = ServiceForLoadingMushroomsDataset.getItemById(selectedItemId, activity);

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
