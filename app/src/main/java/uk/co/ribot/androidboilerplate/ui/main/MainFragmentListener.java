package uk.co.ribot.androidboilerplate.ui.main;

import uk.co.ribot.androidboilerplate.data.model.Movie;

/**
 * Created by ronnyr on 21/09/2016.
 */
public interface MainFragmentListener {
    void floatingButtonSearchClick();
    void floatingButtonEditorClick();
    void floatingButtonMoviesClick();

    void editMovie(Movie movie);
}
