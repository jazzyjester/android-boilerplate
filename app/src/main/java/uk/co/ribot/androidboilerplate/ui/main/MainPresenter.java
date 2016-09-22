package uk.co.ribot.androidboilerplate.ui.main;

import javax.inject.Inject;

import rx.Subscription;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final static int STATE_MOVIES = 1;
    private final static int STATE_MOVIES_SEARCH = 2;
    private final static int STATE_MOVIES_EDITOR = 3;

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private int mCurrentState = STATE_MOVIES;



    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);

        showMovies();
    }

    @Override
    public void detachView() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.detachView();
    }

    private void selectState(int stateIndex)
    {
        mCurrentState  = stateIndex;
    }

    public void showMovies()
    {
        selectState(STATE_MOVIES);
        showState();
    }

    public void showMoviesSearch()
    {
        selectState(STATE_MOVIES_SEARCH);
        showState();
    }

    public void showMoviesEditor()
    {
        selectState(STATE_MOVIES_EDITOR);
        showState();
    }

    private void showState() {

        switch (mCurrentState)
        {
            case STATE_MOVIES: getMvpView().showMyMovies();break;
            case STATE_MOVIES_SEARCH: getMvpView().showMovieSearch();break;
            case STATE_MOVIES_EDITOR: getMvpView().showMovieEditor();break;
        }
    }
}