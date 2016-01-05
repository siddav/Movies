package com.example.sidda.movies;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sidda.movies.adapters.ReviewsAdapter;
import com.example.sidda.movies.adapters.TrailersAdapter;
import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.Movie;
import com.example.sidda.movies.model.MovieReviewsResponse;
import com.example.sidda.movies.model.VideoResponse;
import com.example.sidda.movies.network.MoviesService;
import com.example.sidda.movies.network.MoviesServiceProvider;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String SELECTED_MOVIE_ID = "movieId";
    private static final String SELECTED_MOVIE = "selectedMovie";
    private long selectedMovieId = 0l;

    Movie movie;
    @Bind(R.id.movie_name)
    TextView movieName;
    @Bind(R.id.movie_poster)
    ImageView moviePoster;
    @Bind(R.id.release_year) TextView releaseYear;
    @Bind(R.id.movie_length) TextView movieLength;
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.movie_description) TextView description;
    /*@Bind(R.id.trailers_thumbnails)
    ListView thumbNailsListView;
    @Bind(R.id.movie_reviews)
    ListView reviewsListView;*/

    ReviewsAdapter reviewsAdapter = new ReviewsAdapter();
    TrailersAdapter trailersAdapter = new TrailersAdapter();

    private OnFragmentInteractionListener mListener;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_MOVIE, movie);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
          movie = savedInstanceState.getParcelable(SELECTED_MOVIE);
          updateContent(movie);
        }
        /*else {
            Intent intent = getActivity().getIntent();
            if(intent.getExtras() != null) {
                selectedMovieId = intent.getExtras().getLong("movieId");
                new FetchMovieTask().execute(selectedMovieId);
            }
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateContent(selectedMovieId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedMovieId = getArguments().getLong(SELECTED_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        /*thumbNailsListView.setAdapter(trailersAdapter);
        reviewsListView.setAdapter(reviewsAdapter);*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateContent(long movieId) {
        getView().setVisibility(View.VISIBLE);
        new FetchMovieTask().execute(movieId);
        new FetchTrailersTask().execute(movieId);
        //new FetchReviewsTask().execute(movieId);
    }

    public void updateContent(Movie movie) {
        if (movie != null) {
            this.movie = movie;
            movieName.setText(movie.title + " " + movie.id);
            Picasso.with(getActivity()).load(MovieConstants.BASE_IMAGE_URL + movie.posterPath).into(moviePoster);
            releaseYear.setText(getYear(movie.releaseDate));
            movieLength.setText(movie.runtime + " min");
            description.setText(movie.overView);
            rating.setText(movie.rating+"/10");
        }
    }

    public void updateTrailers(VideoResponse videoResponse) {
       if(videoResponse != null && videoResponse.videos != null) {
         trailersAdapter.addAllVideos(videoResponse.videos);
       }
    }

    public void updateReviews(MovieReviewsResponse reviewsResponse) {
        if(reviewsResponse != null && reviewsResponse.reviews != null) {
            reviewsAdapter.addAllVideos(reviewsResponse.reviews);
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    private class FetchMovieTask extends AsyncTask<Long, Void, Void> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Long... params) {
            if (params.length == 0) {
                return null;
            }
            long movieId = params[0];
                String apiKey = MovieConstants.MOVIE_DB_API_KEY;

                MoviesService moviesService = MoviesServiceProvider.getMoviesService();
                Call<Movie> call = moviesService.getMovieDetails(movieId, apiKey);
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Response<Movie> response, Retrofit retrofit) {
                        if(response.isSuccess()) {
                            Movie mv = response.body();
                            if(mv!=null) {
                                updateContent(mv);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            return null;
        }
    }
    private class FetchTrailersTask extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... params) {
            if(params == null) {
                return null;
            }
            long key = params[0];
            String apiKey = MovieConstants.MOVIE_DB_API_KEY;
            MoviesService moviesService = MoviesServiceProvider.getMoviesService();
            Call<VideoResponse> call = moviesService.getMovieVideos(key, apiKey);
            call.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Response<VideoResponse> response, Retrofit retrofit) {
                    if(response.isSuccess()) {
                        VideoResponse videoResponse = response.body();
                        if(videoResponse!=null) {
                            updateTrailers(videoResponse);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
            return null;
        }
    }

    private class FetchReviewsTask extends AsyncTask<Long, Void, Void> {
        @Override
        protected Void doInBackground(Long... params) {
            if(params == null) {
                return null;
            }
            long key = params[0];
            String apiKey = MovieConstants.MOVIE_DB_API_KEY;
            MoviesService moviesService = MoviesServiceProvider.getMoviesService();
            Call<MovieReviewsResponse> call = moviesService.getMovieReviews(key, apiKey);
            call.enqueue(new Callback<MovieReviewsResponse>() {
                @Override
                public void onResponse(Response<MovieReviewsResponse> response, Retrofit retrofit) {
                    if(response.isSuccess()) {
                        MovieReviewsResponse reviewsResponse = response.body();
                        if(reviewsResponse!=null) {
                            updateReviews(reviewsResponse);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
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
