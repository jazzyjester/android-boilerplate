package uk.co.ribot.androidboilerplate.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.MovieResults;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

public interface MoviesService {

//    String ENDPOINT = "https://api.ribot.io/";

//    String ENDPOINT = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=u6ecrp8r634k4yah7ctg6z24&q=hacking&page_limit=1";

    String ENDPOINT = "http://api.rottentomatoes.com";

    @GET("/api/public/v1.0/movies.json")
    Observable<MovieResults> getMoviesBySearch(@Query("apikey") String apikey, @Query("q") String query, @Query("page_limit") int pageLimit);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static MoviesService newMoviesService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MoviesService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(MoviesService.class);
        }
    }
}
