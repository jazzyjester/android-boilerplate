package uk.co.ribot.androidboilerplate.injection.component;

import dagger.Subcomponent;
import uk.co.ribot.androidboilerplate.injection.PerActivity;
import uk.co.ribot.androidboilerplate.injection.module.ActivityModule;
import uk.co.ribot.androidboilerplate.ui.editor.MoviesEditorFragment;
import uk.co.ribot.androidboilerplate.ui.main.MainActivity;
import uk.co.ribot.androidboilerplate.ui.movies.MoviesFragment;
import uk.co.ribot.androidboilerplate.ui.search.MoviesSearchFragment;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(MoviesFragment moviesSearchFragment);

    void inject(MoviesSearchFragment moviesSearchFragment);

    void inject(MoviesEditorFragment moviesEditorFragment);
}
