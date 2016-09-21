package uk.co.ribot.androidboilerplate.ui.main;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void toggleSearch(boolean isShow);

    void setFloatingActionBarIcon(int resID);

    void setActionBarTitle(int resID);

    void showMyMoviesPage();

    void showMovieSearchPage();


}
