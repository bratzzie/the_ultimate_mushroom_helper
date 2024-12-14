package org.nau.diploma.chantarellecheck.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.nau.diploma.chantarellecheck.RecycleViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import rg.nau.diploma.chantarellecheck.R;

public class CollectionRecycleViewAdapter extends RecyclerView.Adapter<CollectionRecycleViewAdapter.ViewHolder> {

    private final List<RecycleViewItem> originalData;
    private List<RecycleViewItem> currentData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CollectionRecycleViewAdapter(Context context, List<RecycleViewItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.originalData = data;
        this.currentData = data;
    }

    // method for filtering our recyclerview items.
    public void filterList(String filter) {
        List<RecycleViewItem> filterlist = currentData.stream().filter(recycleViewItem -> recycleViewItem.getName().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());
        if (filterlist.isEmpty()) {
            Toast.makeText(null, "No items found", Toast.LENGTH_SHORT).show();
        }
        currentData = filterlist;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecycleViewItem item = currentData.get(position);
        holder.image.setImageBitmap(item.getThumbnail());
        holder.name.setText(item.getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return currentData.size();
    }

    public void resetFilter() {
        currentData = new ArrayList<>(originalData);
        notifyDataSetChanged();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            image = itemView.findViewById(R.id.item_thumbnail_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public RecycleViewItem getItem(int id) {
        return currentData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}