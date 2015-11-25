package com.example.sidda.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.DiscoverMovieResponse;
import com.example.sidda.movies.model.Movie;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.gv_movies) GridView moviesGridView;
    MovieAdapter adapter = new MovieAdapter();

    @Override
    public void onStart() {
        super.onStart();
        new FetchMoviesTask().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moviesGridView.setAdapter(adapter);
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast t = Toast.makeText(getApplicationContext(), "position " + position, Toast.LENGTH_SHORT);
                t.show();
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("movie", adapter.getItem(position));
                intent.putExtra("movieId", adapter.getItemId(position));
                startActivity(intent);
            }
        });

        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if(movies != null) {
                adapter.addAllMovies(movies);
            }
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            String movieJsonString = null;
            try {
                String apiKey = MovieConstants.MOVIE_DB_API_KEY;

                URL url = new URL(MovieConstants.MOVIE_DISCOVER_BASE_URL+"api_key="+apiKey);
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
                Log.v(LOG_TAG, "fetched json" +movieJsonString);
                Gson gson = new Gson();
                DiscoverMovieResponse response = gson.fromJson(movieJsonString, DiscoverMovieResponse.class);
                List<Movie> moviesList = response.results;
                return moviesList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
