package uk.co.ribot.androidboilerplate.data.model;

/**
 * Created by ronnyr on 19/09/2016.
 */

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by ronnyr on 19/09/2016.
 */
@AutoValue
public abstract class MovieResults implements Parcelable {

    public abstract int total();
    public abstract List<Movie> movies();

    public static MovieResults create(int total,List<Movie> movies) {

        return new AutoValue_MovieResults(total,movies);
    }

    public static TypeAdapter<MovieResults> typeAdapter(Gson gson) {
        return new AutoValue_MovieResults.GsonTypeAdapter(gson);
    }


    public static Builder builder() {
        return AutoValue_MovieResults.builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTotal(int total);
        public abstract Builder setMovies(List<Movie> movies);
        public abstract MovieResults build();
    }


}
