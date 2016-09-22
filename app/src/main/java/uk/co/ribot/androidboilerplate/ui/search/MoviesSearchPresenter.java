package uk.co.ribot.androidboilerplate.ui.search;

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
import uk.co.ribot.androidboilerplate.ui.movies.MoviesMvpView;
import uk.co.ribot.androidboilerplate.util.RxUtil;

@ConfigPersistent
public class MoviesSearchPresenter extends BasePresenter<MoviesSearchMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MoviesSearchPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MoviesSearchMvpView mvpView) {
        super.attachView(mvpView);

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
                        getMvpView().showMessageMovieSaved();

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

        getMvpView().showProgressBar();
        getMvpView().showMoviesEmpty();
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getMoviesByQuery(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the movies.");
                        getMvpView().hideProgressBar();

                    }

                    @Override
                    public void onNext(List<Movie> movies) {

                        if (movies.isEmpty()) {
                            if (isViewAttached()) getMvpView().showMoviesEmpty();
                        } else {
                            getMvpView().showMovies(movies);
                            getMvpView().hideProgressBar();
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



}