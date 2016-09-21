package uk.co.ribot.androidboilerplate.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.base.BaseFragment;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesAdapterListener;


public class MoviesSearchFragment extends BaseFragment implements MoviesSearchMvpView, MoviesAdapterListener {

    @Inject MoviesSearchPresenter mMoviesSearchPresenter;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);

        mMoviesSearchPresenter.attachView(this);

        showMoviesEmpty();

    }

    @Override
    public void onMovieLongPressClick(View view, Movie movie) {

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
