package uk.co.ribot.androidboilerplate.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
/**
 * Created by ronnyr on 19/09/2016.
 */
@AutoValue
public abstract class Movie implements Parcelable {
    public abstract String id();
    public abstract String title();
    public abstract int year();

    public static Movie create(String id,String title, int year) {
        return new AutoValue_Movie(id,title,year);
    }

    public static Builder builder() {
        return AutoValue_Movie.builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String id);
        public abstract Builder setTitle(String title);
        public abstract Builder setYear(int year);
        public abstract Movie build();
    }


}
