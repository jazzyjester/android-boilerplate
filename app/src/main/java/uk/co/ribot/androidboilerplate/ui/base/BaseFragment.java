package uk.co.ribot.androidboilerplate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.main.MainFragmentListener;

public class BaseFragment extends Fragment  {


    @BindView(R.id.toolbar) protected Toolbar mToolbar;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        setToolbar();
    }
}
