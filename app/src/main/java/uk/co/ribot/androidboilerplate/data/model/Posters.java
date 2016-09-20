package uk.co.ribot.androidboilerplate.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Date;

/**
 * Created by ronnyr on 20/09/2016.
 */
@AutoValue
public abstract class Posters implements Parcelable{

    public abstract String thumbnail();

    public static Posters create(String thumbnail) {
        return new AutoValue_Posters(thumbnail);
    }

    public static TypeAdapter<Posters> typeAdapter(Gson gson) {
        return new AutoValue_Posters.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return AutoValue_Posters.builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setThumbnail(String thumbnail);
        public abstract Posters build();
    }
}
