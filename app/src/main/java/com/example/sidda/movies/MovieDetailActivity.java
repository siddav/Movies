package com.example.sidda.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movie;
    @Bind(R.id.movie_name) TextView movieName;
    @Bind(R.id.movie_poster) ImageView moviePoster;
    @Bind(R.id.release_year) TextView releaseYear;
    @Bind(R.id.movie_length) TextView movieLength;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.movie_description) TextView description;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        long movieId = intent.getExtras().getLong("movieId");
        new FetchMovieTask().execute(movieId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
private class FetchMovieTask extends AsyncTask<Long, Void, Movie> {
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    @Override
    protected void onPostExecute(Movie mv) {
        if (mv != null) {
            movie = mv;
            movieName.setText(movie.title);
            Picasso.with(getApplicationContext()).load(MovieConstants.BASE_IMAGE_URL + movie.posterPath).into(moviePoster);
            releaseYear.setText(getYear(movie.releaseDate));
            movieLength.setText(movie.runtime + " min");
            description.setText(movie.overView);
            rating.setText(movie.rating+"/10");
        }
    }

    @Override
    protected Movie doInBackground(Long... params) {
        if (params.length == 0) {
            return null;
        }
        long movieId = params[0];
        String movieJsonString = null;
        try {
            String apiKey = MovieConstants.MOVIE_DB_API_KEY;

            URL url = new URL(MovieConstants.MOVIE_ID_BASE_URL + movieId + "?api_key=" + apiKey);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            movieJsonString = buffer.toString();
            Log.v(LOG_TAG, "fetched json" + movieJsonString);

            Gson gson = new Gson();
            Movie movie = gson.fromJson(movieJsonString, Movie.class);
            return movie;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
    private String getYear(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date dat = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dat);
            return String.valueOf(cal.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
