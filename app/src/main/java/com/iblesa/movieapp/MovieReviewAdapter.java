package com.iblesa.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iblesa.movieapp.model.MovieReview;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewHolder> {
    final private MovieReviewListItemClickListener onClickListerner;
    private List<MovieReview> reviews;

    MovieReviewAdapter(MovieReviewListItemClickListener onClickListerner) {
        this.onClickListerner = onClickListerner;
    }

    @Override
    public MovieReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        } else {
            return reviews.size();
        }
    }

    public void setReviews(List<MovieReview> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class MovieReviewHolder extends ViewHolder implements View.OnClickListener {
        static final int MAX_REVIEW_LENGTH = 100;
        private static final String SEPARATOR = " - ";
        static final String CLICK_FOR_MORE = " ... ";
        TextView holder;

        MovieReviewHolder(View itemView) {
            super(itemView);
            holder = itemView.findViewById(R.id.tv_movie_review);
            itemView.setOnClickListener(this);
        }

        void bind(int element) {
            MovieReview review = reviews.get(element);
            String substring = getPrintableString(review, review.getAuthor(), MAX_REVIEW_LENGTH);
            holder.setText(substring);
        }

        @NonNull
        private String getPrintableString(MovieReview review, String shortReview, int maxReviewLength) {
            StringBuilder result = new StringBuilder(shortReview).append(SEPARATOR);
            if (result.length() < maxReviewLength) {
                String content = review.getContent();
                if (content != null && content.length() > 0) {
                    String substring = content.substring(0,
                            Math.min(MAX_REVIEW_LENGTH - shortReview.length(), content.length()));
                    result.append(substring);
                    result.append(CLICK_FOR_MORE);
                }
            }
            return result.toString();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieReview review = reviews.get(adapterPosition);
            onClickListerner.onListItemClick(review);
        }
    }

    public interface MovieReviewListItemClickListener {
        void onListItemClick(MovieReview movieReviewSelected);
    }
}
