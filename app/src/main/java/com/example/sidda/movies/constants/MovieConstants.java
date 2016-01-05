package com.example.sidda.movies.constants;

import com.example.sidda.movies.BuildConfig;

/**
 * Created by sidda on 25/11/15.
 */
public class MovieConstants {
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    // this has to be populated with movie db org API KEY
    public static final String MOVIE_DB_API_KEY = BuildConfig.MOVIES_DB_API_KEY;
    public static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String MOVIE_POPULAR = "popularity.desc";
    public static final String MOVIE_TOP_RATED = "vote_average.desc";

    // Application specific constants
    public static final String PREF_QUERY = "pref_query";
    public static final String YOUTUBE_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/";
}
