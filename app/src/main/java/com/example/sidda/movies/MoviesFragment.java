package com.example.sidda.movies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sidda.movies.adapters.MovieAdapter;
import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.DiscoverMovieResponse;
import com.example.sidda.movies.model.Movie;
import com.example.sidda.movies.network.MoviesService;
import com.example.sidda.movies.network.MoviesServiceProvider;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnMovieClickListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment implements AbsListView.OnScrollListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SAVE_QUERY = "query";
    // by default popular movies
    private String query = MovieConstants.MOVIE_POPULAR;

    @Bind(R.id.gv_movies)
    GridView moviesGridView;
    MovieAdapter adapter = new MovieAdapter();
    DiscoverMovieResponse moviesResult = new DiscoverMovieResponse();
    volatile boolean isLoading = true;
    boolean userScrolled = false;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_QUERY, query);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnMovieClickListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
           query = savedInstanceState.getString(SAVE_QUERY);
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefQuery = preferences.getString(MovieConstants.PREF_QUERY, null);
        if (prefQuery != null) {
            query = prefQuery;
        }
            // call for page 1
            new FetchMoviesTask(query).execute(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);

        moviesGridView.setAdapter(adapter);
        moviesGridView.setOnScrollListener(this);
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast t = Toast.makeText(getActivity(), "position " + position, Toast.LENGTH_SHORT);
                // t.show();
                if (mListener != null) {
                    mListener.onMovieClick(adapter.getItemId(position));
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMovieClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMovieClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
            userScrolled = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int currentPage = moviesResult.page;
        int totalPages = moviesResult.totalPages;
        if (userScrolled && firstVisibleItem + visibleItemCount == totalItemCount && currentPage < totalPages && !isLoading) {
           // Toast t = Toast.makeText(getActivity(), "task called for page " + (currentPage+1), Toast.LENGTH_SHORT);
           // t.show();
            new FetchMoviesTask(query).execute(currentPage + 1);
        }
    }

    public void updateContent(String query) {
        this.query = query;
        adapter.clear();
        // call for page 1
        new FetchMoviesTask(query).execute(1);
    }

    public void updateAdapter(List<Movie> movies) {
        isLoading = false;
        if(movies != null) {
            adapter.addAllMovies(movies);
            // check in main activity whether to display default 0th position movie
            mListener.onMoviesLoaded(adapter.getItemId(0));
        }
        if (query.equalsIgnoreCase(MovieConstants.MOVIE_TOP_RATED)) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.top_rated);
        } else {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.popular);
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
    public interface OnMovieClickListener {
        // TODO: Update argument type and name
        public void onMovieClick(long position);
        public void onMoviesLoaded(long defaultPosition);
    }

    private class FetchMoviesTask extends AsyncTask<Integer, Void, Void> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private String query;

        FetchMoviesTask(String query) {
            this.query = query;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            isLoading = true;
            String apiKey = MovieConstants.MOVIE_DB_API_KEY;
            if (apiKey.equalsIgnoreCase("replace this key")) {
                Log.e(LOG_TAG, "please replace api key in MovieConstants.MOVIE_DB_API_KEY in com.example.sidda.movies.constants.MovieConstants");
            }
            // default page number
            // this is added to just let the reviewer to add his/her api key.
            int page = 1;
            if (params != null) {
               page = params[0];
            }

                MoviesService moviesService = MoviesServiceProvider.getMoviesService();
                Call<DiscoverMovieResponse> call = moviesService.discoverMovies(apiKey, query, page);
                call.enqueue(new Callback<DiscoverMovieResponse>() {
                    @Override
                    public void onResponse(Response<DiscoverMovieResponse> response, Retrofit retrofit) {
                        Log.v(LOG_TAG, "inside response "+ query);
                        if (response.isSuccess()) {
                            moviesResult = response.body();
                            updateAdapter(moviesResult.results);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                         Log.e(LOG_TAG, t.getMessage());
                    }
                });
            return null;
        }
    }

    private class TrailersTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            return null;
        }
    }

    private class ReviewsTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            return null;
        }
    }

}
