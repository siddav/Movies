package com.example.sidda.movies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sidda on 10/12/15.
 */
public class VideoResponse {
    @SerializedName("id")
    public long id;
    @SerializedName("results")
    public List<MovieVideos> videos;
}
