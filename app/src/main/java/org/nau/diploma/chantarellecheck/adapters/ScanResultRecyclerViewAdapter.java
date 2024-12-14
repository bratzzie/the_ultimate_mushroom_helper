package org.nau.diploma.chantarellecheck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import org.nau.diploma.chantarellecheck.ServiceForLoadingMushroomsDataset;

import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class ScanResultRecyclerViewAdapter extends RecyclerView.Adapter<ScanResultRecyclerViewAdapter.ViewHolder> {
    private List<String> labels;
    private List<String> scores;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    Context context;

    // data is passed into the constructor
    public ScanResultRecyclerViewAdapter(Context context, List<String> labels, List<String> scores) {
        this.inflater = LayoutInflater.from(context);
        this.labels = labels;
        this.scores = scores;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.scan_result_fragment, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String label = labels.get(position);
        System.err.println("Label:" + ServiceForLoadingMushroomsDataset.getItemName(label, context));

        holder.title.setText(ServiceForLoadingMushroomsDataset.getItemName(label, context));

       holder.score.setText(( String.format("%.2f",Float.parseFloat(scores.get(position)) * 100 ) + "%"));

        holder.image.setImageResource(context.getResources().getIdentifier(label.trim(), "drawable", context.getPackageName()));

        holder.other_titles.setText("(" + ServiceForLoadingMushroomsDataset.getOtherNamesById(label, holder.other_titles.getContext()) + ")");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return labels.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView other_titles;
        TextView score;
        ShapeableImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.textView22);
            score = itemView.findViewById(R.id.textView21);
            image = itemView.findViewById(R.id.imageView14);
            other_titles = itemView.findViewById(R.id.textView23);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return labels.get(id);
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
