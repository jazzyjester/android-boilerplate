package uk.co.ribot.androidboilerplate.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
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

        //getSupportActionBar().setTitle(getString(R.string.toolbar_title_my_movies));

        mMainPresenter.attachView(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showMyMoviesPage() {
        setMainFragment(new MoviesFragment(), MoviesFragment.class.getSimpleName());
    }

    @Override
    public void showMovieSearchPage() {
        setMainFragment(new MoviesSearchFragment(), MoviesSearchFragment.class.getSimpleName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.dashboard, menu);
//
//        mActionSearch = menu.findItem(R.id.action_search);
//        mActionSearch.setVisible(false);

//        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = (SearchView) mActionSearch.getActionView();
//
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
//
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//
//                    Timber.d(query);
//
//                    //mMoviesPresenter.loadMoviesByQuery(query);
//
//
//
//
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//
//                    Timber.d(newText);
//
//                    return false;
//                }
//            });
//
//        }
//
        return super.onCreateOptionsMenu(menu);

    }

    private void setMainFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_app_hook, fragment, tag)
                .commit();
    }

    @Override
    public void FloatingButtonClick() {
        mMainPresenter.showPageAndSwitch();

        Timber.d("Toggle...");
    }
}
