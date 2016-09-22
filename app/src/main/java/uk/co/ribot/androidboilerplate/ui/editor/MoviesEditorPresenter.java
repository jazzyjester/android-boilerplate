package uk.co.ribot.androidboilerplate.ui.editor;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtil;

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



}