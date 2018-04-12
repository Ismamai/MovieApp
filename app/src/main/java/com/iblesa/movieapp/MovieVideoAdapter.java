package com.iblesa.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iblesa.movieapp.model.MovieVideo;

import java.util.List;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.MovieVideoHolder> {
    final private MovieVideoListItemClickListener onClickListerner;
    private List<MovieVideo> videos;

    MovieVideoAdapter(MovieVideoListItemClickListener onClickListerner) {
        this.onClickListerner = onClickListerner;
    }

    @Override
    public MovieVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.video_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieVideoHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        if (videos == null) {
            return 0;
        } else {
            return videos.size();
        }
    }

    public void setVideos(List<MovieVideo> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    class MovieVideoHolder extends ViewHolder implements View.OnClickListener {
        TextView video;

        MovieVideoHolder(View itemView) {
            super(itemView);
            video = itemView.findViewById(R.id.tv_movie_video);
            itemView.setOnClickListener(this);
        }

        void bind(int element) {
            MovieVideo review = videos.get(element);
            video.setText(review.getName());
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieVideo review = videos.get(adapterPosition);
            onClickListerner.onMovieVideoListItemClick(review);
        }
    }

    public interface MovieVideoListItemClickListener {
        void onMovieVideoListItemClick(MovieVideo movieReviewSelected);
    }
}
