package uk.co.ribot.androidboilerplate.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.editor.MoviesEditorFragment;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesFragment;
import uk.co.ribot.androidboilerplate.ui.search.MoviesSearchFragment;

public class MainActivity extends BaseActivity implements MainMvpView,MainFragmentListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MoviesActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter.attachView(this);
        if (savedInstanceState == null)
        {
            mMainPresenter.showState();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showMyMovies() {
        setMainFragment(new MoviesFragment(), MoviesFragment.class.getSimpleName());
    }

    @Override
    public void showMovieSearch() {
        setMainFragment(new MoviesSearchFragment(), MoviesSearchFragment.class.getSimpleName());
    }

    @Override
    public void showMovieEditor() {
        setMainFragment(new MoviesEditorFragment(), MoviesEditorFragment.class.getSimpleName());
    }

    @Override
    public void showMovieEditor(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Movie.class.toString(),movie);
        Fragment f = new MoviesEditorFragment();
        f.setArguments(bundle);

        setMainFragment(f, MoviesEditorFragment.class.getSimpleName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setMainFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_app_hook, fragment, tag)
                .commit();
    }

    @Override
    public void floatingButtonSearchClick() {
        mMainPresenter.showMoviesSearch();
        Timber.d("Toggle...");
    }

    @Override
    public void floatingButtonEditorClick() {

        mMainPresenter.showMoviesEditor();
        Timber.d("Toggle...");
    }

    @Override
    public void floatingButtonMoviesClick() {
        mMainPresenter.showMovies();
    }

    @Override
    public void editMovie(Movie movie) {

        Timber.d("Edit Movie : " + movie.title());
        mMainPresenter.editMovie(movie);

    }
}
