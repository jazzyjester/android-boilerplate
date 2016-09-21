package uk.co.ribot.androidboilerplate.ui.movies;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface MoviesMvpView extends MvpView {

    void showMovies(List<Movie> movies);

    void showMoviesEmpty();

}
