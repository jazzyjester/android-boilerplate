package uk.co.ribot.androidboilerplate.ui.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> mMovies;

    @Inject
    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        //holder.hexColorView.setBackgroundColor(Color.parseColor(ribot.profile().hexColor()));
        holder.titleTextView.setText(String.format("%s", movie.title()));
        holder.yearTextView.setText(String.format("%s", movie.year()));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_hex_color) View hexColorView;
        @BindView(R.id.movie_title) TextView titleTextView;
        @BindView(R.id.movie_year) TextView yearTextView;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
