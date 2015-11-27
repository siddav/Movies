package com.example.sidda.movies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidda on 24/11/15.
 */
public class MovieAdapter extends BaseAdapter {
    List<Movie> moviesList = new ArrayList<Movie>();
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public void addAllMovies(List<Movie> movies) {
        moviesList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Movie getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moviesList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_view_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_poster);
        Movie movie = moviesList.get(position);
        String posterPath = movie.posterPath;
        Picasso.with(parent.getContext()).load(MovieConstants.BASE_IMAGE_URL+posterPath).into(imageView);
        return convertView;

    }
}
