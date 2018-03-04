package com.iblesa.movieapp.model;

/**
 * Movie model class. Contains all Movie properties. This class is immutable, in order to
 * modify its values, just use Builder provided.
 */

public class Movie {

    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private float voteAverage;

    public Movie(int id, String title, String overview, String releaseDate, float voteAverage) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

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
                '}';
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
    public class Builder {

        private int id = -1;
        private String title;
        private String overview;
        private String releaseDate;
        private float voteAverage;

        public Builder(Movie movie) {
            this.id = movie.getId();
            this.title = movie.getTitle();
            this.overview = movie.getOverview();
            this.releaseDate = movie.getReleaseDate();
            this.voteAverage = movie.getVoteAverage();
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

        /**
         *
         * @return Movie object with specified values
         */
        public Movie build() {
            return new Movie(id, title, overview, releaseDate, voteAverage);
        }
    }
}
