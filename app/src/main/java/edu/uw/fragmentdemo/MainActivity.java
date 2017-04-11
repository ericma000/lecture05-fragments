package edu.uw.fragmentdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieClickListener {

    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// moved to MoviesFragment.java
//        adapter = new ArrayAdapter<Movie>(this,
//                R.layout.list_item, R.id.txtItem, new ArrayList<Movie>());
//
//        ListView listView = (ListView)findViewById(R.id.listView);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Movie movie = (Movie)parent.getItemAtPosition(position);
//                Log.v(TAG, "You clicked on: "+movie);
//            }
//        });
    }


    //respond to search button clicking
    public void handleSearchClick(View v){
        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();


        // create fragment
        MoviesFragment fragment = MoviesFragment.newInstance(searchTerm);

        // use to interact with fragment controller
        FragmentManager fm = getSupportFragmentManager();

        // common methods --> add(), remove(), replace()
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment, "MovieFragment");
//      ft.add(R.id.othercontainer, ..., ....);
        ft.commit();




//        MoviesFragment fragment = (MoviesFragment) fm.findFragmentById(R.id.fragment);
        fragment.downloadMovieData(searchTerm);
    }

    @Override
    public void onMovieClick(Movie movie) {
        DetailsFragment fragment = DetailsFragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "DetailFragment")
                .addToBackStack(null)
                .commit();
    }
// moved to Movie.fragment.java
    //
//    //helper method for downloading the data via the MovieDownloadTask
//    public void downloadMovieData(String searchTerm){
//        Log.v(TAG, "You searched for: "+searchTerm);
//        MovieDownloadTask task = new MovieDownloadTask();
//        task.execute(searchTerm);
//    }
//
//    //A task to download movie data from the internet on a background thread
//    public class MovieDownloadTask extends AsyncTask<String, Void, ArrayList<Movie>> {
//
//        @Override
//        protected ArrayList<Movie> doInBackground(String... params) {
//            ArrayList<Movie> data = MovieDownloader.downloadMovieData(params[0]);
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Movie> movies) {
//            super.onPostExecute(movies);
//
//            adapter.clear();
//            for(Movie movie : movies){
//                adapter.add(movie);
//            }
//        }
//    }

}
