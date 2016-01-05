package com.example.sidda.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sidda on 10/12/15.
 */
public class MovieReviewsResponse {
    public long id;
    public int page;
    @SerializedName("results")
    public List<MovieReview> reviews;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("total_results")
    public int totalReviews;
}
