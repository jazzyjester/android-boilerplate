package uk.co.ribot.androidboilerplate.ui.editor;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.base.BaseFragment;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesAdapter;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesAdapterListener;


public class MoviesEditorFragment extends BaseFragment implements MoviesEditorMvpView {

    @Inject MoviesEditorPresenter mMoviesSearchPresenter;
    @Inject MoviesAdapter mMoviesAdapter;

    @BindView(R.id.progressbar) ProgressBar mProgressBar;
    @BindView(R.id.recycler_view) protected RecyclerView mRecyclerView;
    @BindView(R.id.fabMovies) protected FloatingActionButton mFabMovies;

    private MenuItem mSearchItem;

    public MoviesEditorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_search, container, false);
        ButterKnife.bind(this,view);

        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFabMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.FloatingButtonMoviesClick();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);
        mMoviesSearchPresenter.attachView(this);
    }


    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }
    @Override
    protected String getTitle() {
        return getString(R.string.toolbar_title_movie_add);
    }

}
