package uk.co.ribot.androidboilerplate.ui.search;

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


public class MoviesSearchFragment extends BaseFragment implements MoviesSearchMvpView, MoviesAdapterListener {

    @Inject MoviesSearchPresenter mMoviesSearchPresenter;
    @Inject MoviesAdapter mMoviesAdapter;

    @BindView(R.id.progressbar) ProgressBar mProgressBar;
    @BindView(R.id.recycler_view) protected RecyclerView mRecyclerView;
    @BindView(R.id.fabMovies) protected FloatingActionButton mFabMovies;

    private MenuItem mSearchItem;

    public MoviesSearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);

        mMoviesAdapter.setListener(this);

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

        showMoviesEmpty();

        mMoviesAdapter.setListener(this);

    }

    @Override
    public void onMovieLongPressClick(View view, Movie movie) {

        mMoviesSearchPresenter.saveMovie(movie);
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
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessageMovieSaved() {
        Snackbar.make(mRecyclerView,"Movie Saved",Snackbar.LENGTH_SHORT).show();
    }

    private void initSearchItem()
    {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) mSearchItem.getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {

                    Timber.d(query);

                    mMoviesSearchPresenter.loadMoviesByQuery(query);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    Timber.d(newText);

                    return false;
                }
            });
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard, menu);

        mSearchItem = menu.findItem(R.id.action_search);
        mSearchItem.setVisible(true);
        initSearchItem();

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }
    @Override
    protected String getTitle() {
        return getString(R.string.toolbar_title_search_movie);
    }

}
