package uk.co.ribot.androidboilerplate.ui.editor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.ui.base.BaseFragment;


public class MoviesEditorFragment extends BaseFragment implements MoviesEditorMvpView {

    @Inject MoviesEditorPresenter mMoviesSearchPresenter;

    @BindView(R.id.fabMovies) protected FloatingActionButton mFabMovies;

    Movie mCurrentMovie;

    public MoviesEditorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((BaseActivity) getActivity()).activityComponent().inject(this);

        readArguments();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        ButterKnife.bind(this,view);

        mFabMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.floatingButtonMoviesClick();
            }
        });

        return view;
    }

    public void readArguments()
    {
        Bundle bundle=getArguments();

        if (bundle != null && bundle.getParcelable(Movie.class.toString()) != null)
        {
            mCurrentMovie = bundle.getParcelable(Movie.class.toString());

            Timber.d("Movie Loaded : " + mCurrentMovie.title());
        }

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
