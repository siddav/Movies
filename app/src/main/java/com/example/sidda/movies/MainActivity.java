package com.example.sidda.movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sidda.movies.constants.MovieConstants;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieClickListener, MovieDetailFragment.OnFragmentInteractionListener {
    boolean isTwoPaneLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getFragmentManager()
                .findFragmentById(R.id.movie_detail_frag);
        if (movieDetailFragment != null) {
           isTwoPaneLayout = true;
        }
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
        /**if (id == R.id.action_settings) {
            return true;
        } **/
        MoviesFragment moviesFragment = (MoviesFragment) getFragmentManager().findFragmentById(R.id.movies_frag);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        switch (id) {
            case R.id.top_rated:
                editor.putString(MovieConstants.PREF_QUERY, MovieConstants.MOVIE_TOP_RATED);
                editor.commit();
                moviesFragment.updateContent(MovieConstants.MOVIE_TOP_RATED);
                return true;
            case R.id.popular:
                editor.putString(MovieConstants.PREF_QUERY, MovieConstants.MOVIE_POPULAR);
                editor.commit();
                moviesFragment.updateContent(MovieConstants.MOVIE_POPULAR);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onMovieClick(long position) {

        if(!isTwoPaneLayout) {
            Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
            intent.putExtra("movieId", position);
            startActivity(intent);
        } else {
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getFragmentManager()
                    .findFragmentById(R.id.movie_detail_frag);
            movieDetailFragment.updateContent(position);
        }
    }

    @Override
    public void onMoviesLoaded(long position) {
        if(isTwoPaneLayout) {
            onMovieClick(position);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // not implemented - no interaction till now
    }
}
