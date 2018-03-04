package com.iblesa.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Adapter used by ReyclerView
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

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
        return 20;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView holder;
        private final Context context;

        MovieViewHolder(View itemView, Context context) {
            super(itemView);
            holder = itemView.findViewById(R.id.iv_movie);
            this.context = context;
        }

        void bind(int element) {
            String imgSample = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
            Picasso.with(context).load(imgSample)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder);
//            Picasso.with(context).load(R.drawable.sample_movie_poster).into(holder);
            Log.d(MainActivity.TAG, "Loading img " + element);
        }
    }
}
