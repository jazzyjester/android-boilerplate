package uk.co.ribot.androidboilerplate.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesAdapter;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesAdapterListener;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesPresenter;


public class MoviesSearchFragment extends Fragment implements MoviesSearchMvpView, MoviesAdapterListener {

    // Container Activity must implement this interface
    public interface MoviesSearchListener {

        MenuItem getSearchItem();
    }


    @Inject MoviesSearchPresenter mMoviesSearchPresenter;
    @Inject MoviesAdapter mMoviesAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private MenuItem mSearchItem;
    private MoviesSearchListener mSearchListener;


    public MoviesSearchFragment() {
    }

    public static MoviesSearchFragment newInstance() {
        MoviesSearchFragment fragment = new MoviesSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_search, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMoviesSearchPresenter.attachView(this);

        showMoviesEmpty();


    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mSearchListener = (MoviesSearchListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
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

        //mSearchItem = mSearchListener.getSearchItem();

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
        initSearchItem();

        super.onCreateOptionsMenu(menu, inflater);
    }
}
