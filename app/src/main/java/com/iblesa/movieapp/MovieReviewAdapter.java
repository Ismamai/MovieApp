package com.iblesa.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
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
        static final int MAX_AUTHOR_LENGTH = 20;
        static final String CLICK_FOR_MORE = " ... ";
        TextView author;
        TextView content;

        MovieReviewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_movie_review_author);
            content = itemView.findViewById(R.id.tv_movie_review_content);
            itemView.setOnClickListener(this);
        }

        void bind(int element) {
            MovieReview review = reviews.get(element);
            content.setText(getPrintableString(review.getContent(), MAX_REVIEW_LENGTH));
            if (TextUtils.isEmpty(review.getAuthor())) {
                author.setText(R.string.movie_review_author_anonymous);
            } else {
                author.setText(getPrintableString(review.getAuthor(), MAX_AUTHOR_LENGTH));
            }
        }

        @NonNull
        private String getPrintableString(String content, int maxReviewLength) {
            StringBuilder result = new StringBuilder();
            if (!TextUtils.isEmpty(content)) {
                int min = Math.min(maxReviewLength, content.length());
                String substring = content.substring(0,
                        min);
                    result.append(substring);
                if (min != content.length()) {
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
