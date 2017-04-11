package edu.uw.fragmentdemo;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    public static final String TAG = "MoviesFragment";
    private ArrayAdapter<Movie> adapter;
    private static final String SEARCH_ARG_KEY = "search_key";


    interface OnMovieClickListener {
        public void onMovieClick(Movie movie);
    }

    


    public MoviesFragment() {
        // Required empty public constructor
    }

    // to get around the rule that you have to keep the fragment constructor empty
    public static MoviesFragment newInstance(String searchTerm) {

        // key/value pairs, similar to maps
        // a super simple hashMap
        Bundle args = new Bundle();
        args.putString(SEARCH_ARG_KEY, searchTerm);

        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_movies, container, false); // returns a view that will get attached to the overall view hierarchy


        // getActivtiy --> returns the activity that the fragment is associated with
        adapter = new ArrayAdapter<Movie>(getActivity(),
                R.layout.list_item, R.id.txtItem, new ArrayList<Movie>());

        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)parent.getItemAtPosition(position);
                Log.v(TAG, "You clicked on: "+movie);

                // show the detail view
                // communicate with activity to let it know that movieFragment's been clicked on
                // activity.handleMovieClick(movie)
                ((OnMovieClickListener)getActivity()).onMovieClick(movie);
            }
        });
        //                  bundle                   key-value
        String searchTerm = getArguments().getString(SEARCH_ARG_KEY);
        downloadMovieData(searchTerm);
        return rootView; // changes the inflate value to true
    }



    //helper method for downloading the data via the MovieDownloadTask
    public void downloadMovieData(String searchTerm){
        Log.v(TAG, "You searched for: "+searchTerm);
        MovieDownloadTask task = new MovieDownloadTask();
        task.execute(searchTerm);
    }

    //A task to download movie data from the internet on a background thread
    public class MovieDownloadTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            ArrayList<Movie> data = MovieDownloader.downloadMovieData(params[0]);
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            adapter.clear();
            for(Movie movie : movies){
                adapter.add(movie);
            }
        }
    }

}
