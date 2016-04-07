package com.example.sidda.movies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.OnFragmentInteractionListener {
private static final String MOVIE_DETAIL_FRAGMENT = "movie_detail_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putLong(MovieDetailFragment.SELECTED_MOVIE_ID, getIntent().getExtras().getLong("movieId"));

            Fragment fragment = MovieDetailFragment.newInstance(getIntent().getExtras().getLong("movieId"));
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_frag_container, fragment, MOVIE_DETAIL_FRAGMENT).commit();
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
    public void onFragmentInteraction(Uri uri) {
        Toast t = Toast.makeText(getApplicationContext(), "uri " + uri, Toast.LENGTH_SHORT);
        t.show();
    }
}
