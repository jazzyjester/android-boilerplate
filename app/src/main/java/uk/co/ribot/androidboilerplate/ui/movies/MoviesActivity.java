package uk.co.ribot.androidboilerplate.ui.movies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.util.DialogFactory;

public class MoviesActivity extends BaseActivity implements MoviesMvpView, MoviesAdapterListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MoviesActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject MoviesPresenter mMoviesPresenter;
    public MoviesAdapter mMoviesAdapter;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab) FloatingActionButton mFab;
    public MenuItem mActionSearch;


    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(getString(R.string.toolbar_title_my_movies));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMoviesPresenter.toggleMoviesState();

            }
        });


        mMoviesAdapter = new MoviesAdapter(this);

        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMoviesPresenter.attachView(this);
        mMoviesPresenter.loadMovies();

        //if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
        //    startService(SyncService.getStartIntent(this));
        //}
    }

    @Override
    protected void onDestroy() {
        mMoviesPresenter.detachView();
        super.onDestroy();

    }


    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showMovies(List<Movie> movies) {

        mMoviesAdapter.setMovies(movies);
        mMoviesAdapter.notifyDataSetChanged();

    }

    @Override
    public void showMoviesEmpty() {
        mMoviesAdapter.setMovies(Collections.<Movie>emptyList());
        mMoviesAdapter.notifyDataSetChanged();


    }

    @Override
    public void enterStateMyMovies() {

        mActionSearch.setVisible(false);
        mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_my_movies));

    }

    @Override
    public void enterStateSearch() {

        mActionSearch.setVisible(true);
        mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_apps_black_24dp));
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_search_movie));
    }

    @Override
    public void toggleSearch(boolean isShow) {
        mActionSearch.setVisible(isShow);
    }

    @Override
    public void onMovieLongPressClick(View view, Movie movie) {

        mMoviesPresenter.saveMovie(movie);

        //Snackbar.make(view, "Movie Saved.", Snackbar.LENGTH_SHORT)
        //        .setAction("Action", null).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        mActionSearch = menu.findItem(R.id.action_search);
        mActionSearch.setVisible(false);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) mActionSearch.getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {

                    Timber.d(query);

                    mMoviesPresenter.loadMoviesByQuery(query);


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    Timber.d(newText);


                    return false;
                }
            });

        }

        return super.onCreateOptionsMenu(menu);

    }
}
