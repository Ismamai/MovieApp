package com.iblesa.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MovieReview model class. Contains all MovieReview properties. This class is immutable, in order to
 * modify its values, just use Builder provided.
 */

public class MovieReview implements Parcelable {

    private String id;
    private String author;
    private String content;
    private String url;


    public MovieReview(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    private MovieReview(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

    public final static Creator<MovieReview> CREATOR = new Creator<MovieReview>() {

        @Override
        public MovieReview createFromParcel(Parcel source) {
            return new MovieReview(source);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    /**
     * @return unique id of the movie
     */
    public String getId() {
        return id;
    }

    /**
     * @return author of the movie
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return content of the movie
     */
    public String getContent() {
        return content;
    }

    /**
     * @return release date of the movie as String
     */

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    /**
     * @return Builder object to be able to get new instances
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Builder class.
     */
    public static class Builder {

        private String id;
        private String author;
        private String content;
        private String url;

        Builder(MovieReview movie) {
            this.id = movie.getId();
            this.author = movie.getAuthor();
            this.content = movie.getContent();
            this.url = movie.getUrl();
        }

        public Builder() {

        }

        /**
         * @param id unique movie id
         * @return id
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * @param author of the movie
         * @return author
         */
        public Builder author(String author) {
            this.author = author;
            return this;
        }

        /**
         * @param content of the movie
         * @return content
         */
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * @param url of the movie
         * @return url
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }


        /**
         * @return Movie object with specified values
         */
        public MovieReview build() {
            return new MovieReview(id, author, content, url);
        }
    }
}
