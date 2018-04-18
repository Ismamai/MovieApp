package com.iblesa.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Movie model class. Contains all Movie properties. This class is immutable, in order to
 * modify its values, just use Builder provided.
 */

public class Movie implements Parcelable {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("popularity")
    @Expose
    private float popularity;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("video")
    @Expose
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;


    public Movie(int id, String title, String overview, String releaseDate, float voteAverage, float popularity, String posterPath, String backdropPath, boolean video) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.video = video;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        voteAverage = in.readFloat();
        popularity = in.readFloat();
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
        dest.writeString(backdropPath);
        dest.writeFloat(voteAverage);
        dest.writeFloat(popularity);
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

    /**
     *
     * @return popularity of the movie
     */
    public float getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", backdrop_path='" + backdropPath + '\'' +
                ", video=" + video +
                '}';
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
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
        private float popularity;
        private String posterPath;
        private String backdrop_path;
        private boolean video;

        Builder(Movie movie) {
            this.id = movie.getId();
            this.title = movie.getTitle();
            this.overview = movie.getOverview();
            this.releaseDate = movie.getReleaseDate();
            this.voteAverage = movie.getVoteAverage();
            this.popularity = movie.getPopularity();
            this.posterPath = movie.getPosterPath();
            this.backdrop_path = movie.getBackdropPath();
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

        public Builder popularity(float popularity) {
            this.popularity = popularity;
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
            return new Movie(id, title, overview, releaseDate, voteAverage, popularity, posterPath, backdrop_path, video);
        }
    }
}
