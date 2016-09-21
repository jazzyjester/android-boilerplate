package uk.co.ribot.androidboilerplate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.main.MainFragmentListener;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesAdapter;

public class BaseFragment extends Fragment  {

    protected @Inject MoviesAdapter  mMoviesAdapter;

    @BindView(R.id.recycler_view) protected RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.fab) protected FloatingActionButton mFab;

    private MenuItem mActionSearch;
    protected MainFragmentListener mFragmentListener;


    public BaseFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mFragmentListener = (MainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setAdapter(mMoviesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initUI();
    }

    protected Toolbar getToolbar() {
        return null;
    }

    protected boolean displayHomeEnabled() {
        return true;
    }

    protected String getTitle() {
        return null;
    }

    protected void setToolbar() {
        if (getToolbar() != null) {
            if (getActivity() != null) {
                final AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.setSupportActionBar(getToolbar());
                final ActionBar supportActionBar = activity.getSupportActionBar();
                if (supportActionBar != null) {
                    //supportActionBar.setDisplayHomeAsUpEnabled(displayHomeEnabled());
                    supportActionBar.setHomeButtonEnabled(displayHomeEnabled());
                    final String title = getTitle();
                    if (title != null) {
                        supportActionBar.setTitle(title);
                    }
                }
            }
        } else {
            Timber.e("You called setToolbar but didn't override getToolbar, toolbar will not be displayed");
        }
    }

    public void initUI()
    {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.FloatingButtonClick();
            }
        });

        setToolbar();


    }
}
