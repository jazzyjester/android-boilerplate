package uk.co.ribot.androidboilerplate.ui.main;

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
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private boolean mShowingMyMoviesState = true;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);

        getMvpView().showMyMoviesPage();
    }

    @Override
    public void detachView() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.detachView();
    }

    public void toggleMoviesState() {
        mShowingMyMoviesState = !mShowingMyMoviesState;

        if (mShowingMyMoviesState) {

            getMvpView().toggleSearch(false);
            getMvpView().setActionBarTitle(R.string.toolbar_title_my_movies);
            getMvpView().setFloatingActionBarIcon(R.drawable.ic_search_black_24dp);
            getMvpView().showMyMoviesPage();

        } else {
            getMvpView().toggleSearch(true);
            getMvpView().setActionBarTitle(R.string.toolbar_title_search_movie);
            getMvpView().setFloatingActionBarIcon(R.drawable.ic_apps_black_24dp);
            getMvpView().showMovieSearchPage();

        }


    }

}