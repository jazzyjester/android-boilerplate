package uk.co.ribot.androidboilerplate.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.MovieResults;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.remote.MoviesService;

@Singleton
public class DataManager {

    private final MoviesService mMovieService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(MoviesService movieService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mMovieService = movieService;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }


    public Observable<List<Movie>> getMoviesByQuery(String query) {
        return mMovieService.getMoviesBySearch("u6ecrp8r634k4yah7ctg6z24", query, 30)
                .map(new Func1<MovieResults, List<Movie>>() {
                    @Override
                    public List<Movie> call(MovieResults movieResults) {
                        return movieResults.movies();
                    }
                });
    }

    public Observable<List<Movie>> getMovies() {

        return mDatabaseHelper.getMovies();
    }

    public Observable<Movie> saveMovie(Movie movie) {

        return mDatabaseHelper.addMovie(movie);
    }


    public Observable<Movie> deleteMovie(Movie movie) {

        return mDatabaseHelper.deleteMovie(movie);
    }

}
