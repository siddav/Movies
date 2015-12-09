package com.example.sidda.movies.network;

import com.example.sidda.movies.constants.MovieConstants;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by sidda on 9/12/15.
 */
public class MoviesServiceProvider {
    public static MoviesService moviesService;

    public static MoviesService getMoviesService() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient();
// add your other interceptors …

// add logging as last interceptor
        httpClient.interceptors().add(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieConstants.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        moviesService = retrofit.create(MoviesService.class);
        return moviesService;
    }
}
