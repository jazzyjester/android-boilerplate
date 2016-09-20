package uk.co.ribot.androidboilerplate.ui.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.util.DialogFactory;

public class MoviesActivity extends BaseActivity implements MoviesMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MoviesActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject MoviesPresenter mMoviesPresenter;
    @Inject MoviesAdapter mMoviesAdapter;

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
        super.onDestroy();

        mMoviesPresenter.detachView();
    }



    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showMovies(List<Movie> movies) {

        mMoviesAdapter.setMovies(movies);
        mMoviesAdapter.notifyDataSetChanged();;

    }

    @Override
    public void showMoviesEmpty() {
        mMoviesAdapter.setMovies(Collections.<Movie>emptyList());
        mMoviesAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_movies, Toast.LENGTH_LONG).show();



    }


}
