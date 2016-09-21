package uk.co.ribot.androidboilerplate.ui.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;


public class MoviesFragment extends Fragment implements MoviesMvpView, MoviesAdapterListener {

    @Inject MoviesPresenter mMoviesPresenter;
    @Inject MoviesAdapter mMoviesAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mMoviesPresenter.attachView(this);
        mMoviesPresenter.loadMovies();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMovieLongPressClick(View view, Movie movie) {

    }

    @Override
    public void showError() {

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
    public void toggleSearch(boolean isShow) {

    }

    @Override
    public void setFloatingActionBarIcon(int resID) {

    }

    @Override
    public void setActionBarTitle(int resID) {

    }

    @Override
    public void showMyMoviesPage() {

    }

    @Override
    public void showMovieSearchPage() {

    }
}
