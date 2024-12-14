package org.nau.diploma.chantarellecheck.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class HorizontalPhotosAdapter extends RecyclerView.Adapter<HorizontalPhotosAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Bitmap> bitmapList;
    private ItemClickListener mClickListener;

    Bitmap getItem(int id) {
        return bitmapList.get(id);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView riv;

        public MyViewHolder(View view) {
            super(view);

            riv = (ImageView) view.findViewById(R.id.horizontal_item_view_image);
            riv.setOnClickListener(v -> {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            });
        }

    }


    public HorizontalPhotosAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_thumbnail_item, parent, false);

        if (itemView.getLayoutParams ().width == RecyclerView.LayoutParams.MATCH_PARENT)
            itemView.getLayoutParams ().width = RecyclerView.LayoutParams.WRAP_CONTENT;

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.riv.setImageBitmap(bitmapList.get(position));

    }


    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}