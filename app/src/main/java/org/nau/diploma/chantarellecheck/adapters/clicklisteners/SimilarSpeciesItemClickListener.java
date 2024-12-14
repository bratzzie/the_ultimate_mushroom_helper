package org.nau.diploma.chantarellecheck.adapters.clicklisteners;

import android.view.View;

import org.nau.diploma.chantarellecheck.adapters.SimilarSpeciesRecyclerViewAdapter;

public class SimilarSpeciesItemClickListener implements SimilarSpeciesRecyclerViewAdapter.ItemClickListener {
    private SimilarSpeciesRecyclerViewAdapter adapter;
    public SimilarSpeciesItemClickListener(SimilarSpeciesRecyclerViewAdapter recyclerViewAdapter) {
        adapter = recyclerViewAdapter;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
