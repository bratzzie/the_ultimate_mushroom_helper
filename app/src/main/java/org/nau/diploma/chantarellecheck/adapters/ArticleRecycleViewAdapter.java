package org.nau.diploma.chantarellecheck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import org.nau.diploma.chantarellecheck.Article;

import java.util.List;

import rg.nau.diploma.chantarellecheck.R;

public class ArticleRecycleViewAdapter extends RecyclerView.Adapter<ArticleRecycleViewAdapter.ViewHolder> {

    private List<Article> articles;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;
    private boolean showArticles;

    // data is passed into the constructor
    public ArticleRecycleViewAdapter(Context context, List<Article> data) {
        this.inflater = LayoutInflater.from(context);
        this.articles = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.article_widget, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.image.setImageResource(holder.itemView.getResources().getIdentifier(article.getImageDrawableId(), "drawable", holder.itemView.getContext().getPackageName()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return showArticles ? articles.size() : 0;
    }

    public boolean isShowArticles() {
        return showArticles;
    }

    public void setShowArticles(boolean showArticles) {
        this.showArticles = showArticles;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ShapeableImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.article_title);
            description = itemView.findViewById(R.id.article_description);
            image = itemView.findViewById(R.id.article_image);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Article getItem(int id) {
        return articles.get(id);
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
