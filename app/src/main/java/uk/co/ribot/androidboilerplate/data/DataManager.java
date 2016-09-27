package uk.co.ribot.androidboilerplate.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.MovieResults;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.remote.MoviesService;
import uk.co.ribot.androidboilerplate.data.remote.RibotsService;

@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final MoviesService mMovieService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(RibotsService ribotsService, MoviesService movieService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mMovieService = movieService;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });


    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }


    public Observable<List<Movie>> getMoviesByQuery(String query) {
        return mMovieService.getMoviesBySearch("u6ecrp8r634k4yah7ctg6z24", query, 30)
                .flatMap(new Func1<MovieResults, Observable<List<Movie>>>() {
                    @Override
                    public Observable<List<Movie>> call(MovieResults movieResults) {
                        return Observable.just(movieResults.movies());
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
