package uk.co.ribot.androidboilerplate.ui.movies;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtil;

@ConfigPersistent
public class MoviesPresenter extends BasePresenter<MoviesMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private boolean mShowingMyMoviesState = true;

    @Inject
    public MoviesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MoviesMvpView mvpView) {
        super.attachView(mvpView);

        getMvpView().showMovieSearchPage();
    }

    @Override
    public void detachView() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.detachView();
    }

    public void saveMovie(Movie movie) {

        mSubscription = mDataManager.saveMovieToDb(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {

                        Timber.d("a");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("a");

                    }

                    @Override
                    public void onNext(Movie movie) {

                        Timber.d("a");
                    }
                });


    }

    public void loadMoviesByQuery(String query) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getMoviesByQuery(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the movies.");
                        getMvpView().showError();

                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        if (movies.isEmpty()) {
                            if (isViewAttached()) getMvpView().showMoviesEmpty();
                        } else {
                            getMvpView().showMovies(movies);
                        }


                    }
                });

    }

    public void loadMovies() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the movies.");
                        getMvpView().showError();

                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        if (movies.isEmpty()) {
                            getMvpView().showMoviesEmpty();
                        } else {
                            getMvpView().showMovies(movies);
                        }


                    }
                });


    }


    public void toggleMoviesState() {
        mShowingMyMoviesState = !mShowingMyMoviesState;

        if (mShowingMyMoviesState) {

            getMvpView().toggleSearch(false);
            getMvpView().setActionBarTitle(R.string.toolbar_title_my_movies);
            getMvpView().setFloatingActionBarIcon(R.drawable.ic_search_black_24dp);
            loadMovies();

        } else {
            getMvpView().toggleSearch(true);
            getMvpView().setActionBarTitle(R.string.toolbar_title_search_movie);
            getMvpView().setFloatingActionBarIcon(R.drawable.ic_apps_black_24dp);
            getMvpView().showMoviesEmpty();

        }


    }

}