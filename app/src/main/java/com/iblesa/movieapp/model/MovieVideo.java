package com.iblesa.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MovieVideo model class. Contains all MovieVideo properties. This class is immutable, in order to
 * modify its values, just use Builder provided.
 */
public class MovieVideo implements Parcelable {

    private String id;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;


    public MovieVideo(String id, String key, String name, String site, int size, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    private MovieVideo(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(type);
    }

    public final static Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {

        @Override
        public MovieVideo createFromParcel(Parcel source) {
            return new MovieVideo(source);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };

    /**
     * @return unique id of the movie
     */
    public String getId() {
        return id;
    }

    /**
     * @return key of the movie
     */
    public String getKey() {
        return key;
    }

    /**
     * @return name of the movie
     */
    public String getName() {
        return name;
    }

    /**
     * @return release date of the movie as String
     */

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "MovieVideo{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
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
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        Builder(MovieVideo movie) {
            this.id = movie.getId();
            this.key = movie.getKey();
            this.name = movie.getName();
            this.site = movie.getSite();
            this.size = movie.getSize();
            this.type = movie.getType();
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
         * @param key of the movie video
         * @return builder
         */
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * @param name of the movie
         * @return builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * @param site of the movie
         * @return builder
         */
        public Builder site(String site) {
            this.site = site;
            return this;
        }

        /**
         * @param size of the movie
         * @return builder
         */
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        /**
         * @param type of the movie
         * @return builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }


        /**
         * @return Movie object with specified values
         */
        public MovieVideo build() {
            return new MovieVideo(id, key, name, site, size, type);
        }
    }
}
