package uk.co.ribot.androidboilerplate.ui.movies;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private MoviesAdapterListener mListener;
    private List<Movie> mMovies;

    @Inject
    public MoviesAdapter() {
        mMovies = new ArrayList<>();
    }

    public void setListener(MoviesAdapterListener listener) {
        mListener = listener;
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
    public void onBindViewHolder(final MoviesViewHolder holder, final int position) {
        Movie movie = mMovies.get(position);
        holder.titleTextView.setText(String.format("%s", movie.title()));
        holder.yearTextView.setText(String.format("%s", movie.year()));

        if (movie.body() != null) {
            holder.descriptionTextView.setVisibility(View.VISIBLE);
            holder.descriptionTextView.setText(String.format("%s", movie.body()));
        }
        else
        {
            holder.descriptionTextView.setVisibility(View.GONE);
        }

        Context context = holder.titleTextView.getContext();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    mListener.onMovieLongPressClick(v, mMovies.get(position));
                }
                return true;
            }
        });

        setThumbnail(context,holder,mMovies.get(position));


    }

    private void setThumbnail(Context context, MoviesViewHolder viewHolder, Movie movie) {
        if (viewHolder.movieImageView != null) {
            final String thumbnailUrl = movie.posters().thumbnail();
            if (thumbnailUrl != null && thumbnailUrl.length() > 0) {
                GlideUrl url = new GlideUrl(thumbnailUrl);

                Glide.with(context)
                        .load(url)
                        .crossFade()
                        .centerCrop()
                        .placeholder(R.drawable.ic_query_builder_black_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<GlideUrl, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, GlideUrl model, Target<GlideDrawable> target, boolean isFirstResource) {

                                return true;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, GlideUrl model, Target<GlideDrawable> target,
                                                           boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        }).into(viewHolder.movieImageView);
            } else {

                Timber.e("Error...");

            }
        }
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.movie_image) ImageView movieImageView;
        @BindView(R.id.movie_title) TextView titleTextView;
        @BindView(R.id.movie_year) TextView yearTextView;
        @BindView(R.id.movie_description) TextView descriptionTextView;


        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
