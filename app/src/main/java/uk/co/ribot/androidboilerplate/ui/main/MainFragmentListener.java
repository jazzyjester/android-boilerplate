package uk.co.ribot.androidboilerplate.ui.main;

import uk.co.ribot.androidboilerplate.data.model.Movie;

/**
 * Created by ronnyr on 21/09/2016.
 */
public interface MainFragmentListener {
    void showMoviesSearch();
    void showMoviesEditor();
    void showMovies();

    void editMovie(Movie movie);
    void showSnackBarMessage(String message);
}
