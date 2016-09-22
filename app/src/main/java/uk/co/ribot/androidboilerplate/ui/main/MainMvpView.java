package uk.co.ribot.androidboilerplate.ui.main;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showMyMovies();

    void showMovieSearch();

    void showMovieEditor();


}
