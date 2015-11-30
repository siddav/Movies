package com.example.sidda.movies.constants;

/**
 * Created by sidda on 25/11/15.
 */
public class MovieConstants {
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";
    // this has to be populated with movie db org API KEY
    public static final String MOVIE_DB_API_KEY = "replace this key";
    public static final String MOVIE_ID_BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String MOVIE_DISCOVER_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    public static final String MOVIE_POPULAR = MOVIE_DISCOVER_BASE_URL + "?sort_by=popularity.desc";
    public static final String MOVIE_TOP_RATED = MOVIE_DISCOVER_BASE_URL + "?sort_by=vote_average.desc";

    // Application specific constants
    public static final String PREF_QUERY = "pref_query";
}
