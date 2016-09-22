package uk.co.ribot.androidboilerplate.ui.editor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    @BindView(R.id.fabMovies) FloatingActionButton mFabMovies;
    @BindView(R.id.movie_subject) EditText mMovieSubject;
    @BindView(R.id.movie_body) EditText mMovieBody;
    @BindView(R.id.movie_year) EditText mMovieYear;
    @BindView(R.id.button_ok) Button mMovieButtonOk;
    @BindView(R.id.button_cancel) Button mMovieButtonCancel;

    private Movie mCurrentMovie;

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
                mFragmentListener.showMovies();
            }
        });

        mMovieButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoviesSearchPresenter.saveMovie(mCurrentMovie);
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

    public void setSubject(String subject)
    {
        mMovieSubject.setText(subject);
    }
    public void setBody(String body)
    {
        mMovieBody.setText(body);
    }
    public void setYear(String year)
    {
        mMovieYear.setText(year);
    }

    @Override
    public String getSubject() {
        return mMovieSubject.getText().toString();
    }

    @Override
    public String getBody() {
        return mMovieBody.getText().toString();
    }

    @Override
    public String getYear() {
        return mMovieYear.getText().toString();
    }

    @Override
    public void showUpdateMessage() {
        mFragmentListener.showSnackBarMessage("Movie Updated.");
    }

    @Override
    public void showMovies() {
        mFragmentListener.showMovies();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);
        mMoviesSearchPresenter.attachView(this);

        mMoviesSearchPresenter.populateMovie(mCurrentMovie);
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
