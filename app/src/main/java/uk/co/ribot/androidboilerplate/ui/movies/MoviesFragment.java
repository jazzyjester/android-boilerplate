package uk.co.ribot.androidboilerplate.ui.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import uk.co.ribot.androidboilerplate.ui.base.BaseFragment;

public class MoviesFragment extends BaseFragment implements MoviesMvpView,MoviesAdapterListener {


    @Inject MoviesPresenter mMoviesPresenter;
    @Inject MoviesAdapter  mMoviesAdapter;

    @BindView(R.id.recycler_view) protected RecyclerView mRecyclerView;
    @BindView(R.id.fabSearch) protected FloatingActionButton mFabSearch;
    @BindView(R.id.fabEditor) protected FloatingActionButton mFabEditor;


    public MoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).activityComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this,view);

        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.floatingButtonSearchClick();
            }
        });

        mFabEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.floatingButtonEditorClick();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);

        mMoviesPresenter.attachView(this);
        mMoviesPresenter.loadMovies();

        mMoviesAdapter.setListener(this);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.toolbar_title_my_movies);
    }

    @Override
    public void onMovieLongPressClick(View view, Movie movie) {

        // edit movie
        mFragmentListener.editMovie(movie);


    }
}
