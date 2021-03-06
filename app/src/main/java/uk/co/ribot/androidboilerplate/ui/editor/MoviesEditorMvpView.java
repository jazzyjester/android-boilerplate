package uk.co.ribot.androidboilerplate.ui.editor;

import android.widget.TextView;

import org.w3c.dom.Text;

import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface MoviesEditorMvpView extends MvpView {
    void setSubject(String subject);
    void setBody(String body);
    void setYear(String year);

    String getSubject();
    String getBody();
    String getYear();

    Movie createMovie();
    void showMovies();
    void showMessage(int messageID);

    void setTitle(int titleID);
    void hideDeleteButton();
    void deleteMovie(Movie movie);


}
