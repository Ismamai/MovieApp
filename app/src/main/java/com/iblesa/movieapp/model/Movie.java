package com.iblesa.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Movie model class. Contains all Movie properties. This class is immutable, in order to
 * modify its values, just use Builder provided.
 */

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private float voteAverage;
    private String posterPath;
    private String backdrop_path;
    private boolean video;


    public Movie(int id, String title, String overview, String releaseDate, float voteAverage, String posterPath, String backdrop_path, boolean video) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.backdrop_path = backdrop_path;
        this.video = video;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backdrop_path = in.readString();
        voteAverage = in.readFloat();
        video = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeString(backdrop_path);
        dest.writeFloat(voteAverage);
        dest.writeInt(video ? 1 : 0);

    }

    public final static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    /**
     *
     * @return unique id of the movie
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return overview of the movie
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @return release date of the movie as String
     */

    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     *
     * @return vote average of the movie
     */
    public float getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", posterPath='" + posterPath + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", video=" + video +
                '}';
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isVideo() {
        return video;
    }

    /**
     *
     * @return Builder object to be able to get new instances
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Builder class.
     */
    public static class Builder {

        private int id = -1;
        private String title;
        private String overview;
        private String releaseDate;
        private float voteAverage;
        private String posterPath;
        private String backdrop_path;
        private boolean video;

        public Builder(Movie movie) {
            this.id = movie.getId();
            this.title = movie.getTitle();
            this.overview = movie.getOverview();
            this.releaseDate = movie.getReleaseDate();
            this.voteAverage = movie.getVoteAverage();
            this.posterPath = movie.getPosterPath();
            this.backdrop_path = movie.getBackdrop_path();
            this.video = movie.isVideo();
        }

        public Builder() {

        }

        /**
         *
         * @param id unique movie id
         * @return id
         */
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        /**
         *
         * @param title of the movie
         * @return title
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         *
         * @param overview of the movie
         * @return overview
         */
        public Builder overview(String overview) {
            this.overview = overview;
            return this;
        }

        /**
         *
         * @param releaseDate of the movie
         * @return releaseDate
         */
        public Builder releaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        /**
         *
         * @param voteAverage of the movie
         * @return voteAverage
         */
        public Builder voteAverage(float voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder posterPath(String posterPath) {
            this.posterPath = posterPath;
            return this;
        }

        public Builder backdropPath(String backdrop_path) {
            this.backdrop_path = backdrop_path;
            return this;
        }

        public Builder video(boolean video) {
            this.video = video;
            return this;
        }

        /**
         *
         * @return Movie object with specified values
         */
        public Movie build() {
            return new Movie(id, title, overview, releaseDate, voteAverage, posterPath, backdrop_path, video);
        }
    }
}
