package uk.co.ribot.androidboilerplate.ui.editor;

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

@ConfigPersistent
public class MoviesEditorPresenter extends BasePresenter<MoviesEditorMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MoviesEditorPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MoviesEditorMvpView mvpView) {
        super.attachView(mvpView);

    }

    @Override
    public void detachView() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.detachView();
    }


    public void populateMovie(Movie movie)
    {
        if (movie != null) {
            getMvpView().setSubject(movie.title());
            getMvpView().setBody(movie.body());
            getMvpView().setYear(movie.year() + "");
        }

    }

    public void saveMovie(Movie movie)
    {

        Movie updatedMovie =  Movie.builder()
                .setBody(getMvpView().getBody())
                .setPosters(movie.posters())
                .setTitle(getMvpView().getSubject())
                .setId(movie.id())
                .setYear(Integer.parseInt(getMvpView().getYear()))
                .build();

        mSubscription = mDataManager.saveMovie(updatedMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {

                        getMvpView().showMovies();
                        getMvpView().showMessage(R.string.snack_bar_message_update);

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

    public void deleteMovie(Movie movie)
    {
        mSubscription = mDataManager.deleteMovie(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {

                        getMvpView().showMovies();
                        getMvpView().showMessage(R.string.snack_bar_message_delete);

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


}