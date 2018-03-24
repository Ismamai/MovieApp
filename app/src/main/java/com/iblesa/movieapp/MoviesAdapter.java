package com.iblesa.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iblesa.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Adapter used by ReyclerView
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;

    final private ListItemClickListener onClickListerner;

    public MoviesAdapter(ListItemClickListener clickListener) {
        this.onClickListerner = clickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.main_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new MovieViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        } else {
            return movies.size();
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView holder;
        private final Context context;

        MovieViewHolder(View itemView, Context context) {
            super(itemView);
            holder = itemView.findViewById(R.id.iv_movie);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        void bind(int element) {
            Movie movie = movies.get(element);
            String posterPath = movie.getPosterPath();
            String imgSample = "http://image.tmdb.org/t/p/w185/"+posterPath;
            Picasso.with(context).load(imgSample)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder);
            Log.d(Constants.TAG, "Loading img " + element);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movies.get(adapterPosition);
            onClickListerner.onListItemClick(movie);
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(Movie movieSelected);
    }
}
