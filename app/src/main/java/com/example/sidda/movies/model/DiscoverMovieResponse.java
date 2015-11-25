package com.example.sidda.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sidda on 25/11/15.
 */
public class DiscoverMovieResponse {
    @SerializedName("page")
    public String page;
    @SerializedName("results")
    public List<Movie> results;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("total_results")
    public int totalResults;
}
