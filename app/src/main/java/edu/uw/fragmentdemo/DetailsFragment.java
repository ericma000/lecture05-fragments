package edu.uw.fragmentdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private static final String IMDB_ARG = "imdbid";
    private static final String TITLE_ARG = "title";

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Movie movie) {

        Bundle args = new Bundle();
        args.putString(IMDB_ARG, movie.imdbId);
        args.putString(TITLE_ARG, movie.title);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false); // returns a view that will get attached to the overall view hierarchy
        TextView titleView = (TextView) rootView.findViewById(R.id.txtMovieTitle);
        TextView imdbView = (TextView)rootView.findViewById(R.id.txtMovieIMDB);

        Bundle args = getArguments();
        titleView.setText(args.getString(TITLE_ARG));
        imdbView.setText(args.getString(IMDB_ARG));
        return rootView;
    }

}
