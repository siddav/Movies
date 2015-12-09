package com.example.sidda.movies.network;

import com.example.sidda.movies.model.DiscoverMovieResponse;
import com.example.sidda.movies.model.Movie;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by sidda on 9/12/15.
 */
public interface MoviesService {
    @GET("discover/movie")
    Call<DiscoverMovieResponse> discoverMovies(@Query("api_key") String apikey, @Query("sort_by") String sortBy, @Query("page") int page);

    @GET("movie/{movieId}")
    Call<Movie> getMovieDetails(@Path("movieId") long movieId, @Query("api_key") String apiKey);
}
