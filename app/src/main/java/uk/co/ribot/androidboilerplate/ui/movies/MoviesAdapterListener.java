package uk.co.ribot.androidboilerplate.ui.movies;

import android.view.View;

import uk.co.ribot.androidboilerplate.data.model.Movie;

/**
 * Created by ronnyr on 20/09/2016.
 */
public interface MoviesAdapterListener {

    void onMovieLongPressClick(View view,Movie movie);
}
