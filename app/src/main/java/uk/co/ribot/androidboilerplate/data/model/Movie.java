package uk.co.ribot.androidboilerplate.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by ronnyr on 19/09/2016.
 */
@AutoValue
public abstract class Movie implements Parcelable {
    public abstract String id();
    public abstract String title();
    public abstract int year();
    public abstract Posters posters();

    public static Movie create(String id,String title, int year, Posters posters) {
        return new AutoValue_Movie(id,title,year,posters);
    }

    public static Builder builder() {
        return new AutoValue_Movie.Builder();
    }

    public static TypeAdapter<Movie> typeAdapter(Gson gson) {
        return new AutoValue_Movie.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String id);
        public abstract Builder setTitle(String title);
        public abstract Builder setYear(int year);
        public abstract Builder setPosters(Posters poster);
        public abstract Movie build();
    }


}
