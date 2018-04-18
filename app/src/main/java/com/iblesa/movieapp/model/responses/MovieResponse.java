package com.iblesa.movieapp.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iblesa.movieapp.model.Movie;

import java.util.List;

public class MovieResponse {


        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("results")
        @Expose
        private List<Movie> movies = null;
        @SerializedName("total_pages")
        @Expose
        private Integer totalPages;
        @SerializedName("total_results")
        @Expose
        private Integer totalResults;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public List<Movie> getMovies() {
            return movies;
        }

        public void setMovies(List<Movie> movieReviews) {
            this.movies = movieReviews;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

    }
