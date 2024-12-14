package org.nau.diploma.chantarellecheck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.nau.diploma.chantarellecheck.SimilarSpeciesItem;

import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class SimilarSpeciesRecyclerViewAdapter extends RecyclerView.Adapter<SimilarSpeciesRecyclerViewAdapter.ViewHolder> {

    private List<SimilarSpeciesItem> items;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private boolean showItems;

    // data is passed into the constructor
    public SimilarSpeciesRecyclerViewAdapter(Context context, List<SimilarSpeciesItem> data) {
        this.inflater = LayoutInflater.from(context);
        this.items = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.similar_species_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimilarSpeciesItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.edibility.setText(item.getEdibility());

        holder.image.setImageResource(holder.itemView.getResources().getIdentifier(item.getId(), "drawable", holder.itemView.getContext().getPackageName()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return showItems ? items.size() : 0;
    }

    public boolean isShowItems() {
        return showItems;
    }

    public void setShowItems(boolean showItems) {
        this.showItems = showItems;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView description;
        TextView edibility;
        ImageView image;


        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.item_name);
            description = itemView.findViewById(R.id.textView47);
            image = itemView.findViewById(R.id.similar_item_thumbnail_image);
            edibility = itemView.findViewById(R.id.textView46);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public SimilarSpeciesItem getItem(int id) {
        return items.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
