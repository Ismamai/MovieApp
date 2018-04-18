package com.iblesa.movieapp.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iblesa.movieapp.model.MovieVideo;

import java.util.List;

public class MovieVideosResponse {


        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("results")
        @Expose
        private List<MovieVideo> movieVideos = null;
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

        public List<MovieVideo> getMovieVideos() {
            return movieVideos;
        }

        public void setMovieVideos(List<MovieVideo> movieReviews) {
            this.movieVideos = movieReviews;
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
