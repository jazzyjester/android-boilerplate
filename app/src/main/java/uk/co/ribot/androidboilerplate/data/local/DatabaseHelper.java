package uk.co.ribot.androidboilerplate.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }


    public Observable<Movie> addMovie(final Movie movie)
    {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(Subscriber<? super Movie> subscriber) {

                BriteDatabase.Transaction transaction = mDb.newTransaction();

                try {
                    mDb.insert(MovieDb.MovieTable.TABLE_NAME, MovieDb.MovieTable.toContentValues(movie), SQLiteDatabase.CONFLICT_REPLACE);
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }
                finally {
                    transaction.end();
                }

            }
        });
    }


    public Observable<Movie> deleteMovie(final Movie movie)
    {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(Subscriber<? super Movie> subscriber) {

                BriteDatabase.Transaction transaction = mDb.newTransaction();

                try {
                    String whereClause =  MovieDb.MovieTable.COLUMN_ID + "=?";
                    mDb.delete(MovieDb.MovieTable.TABLE_NAME,whereClause,movie.id());
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }
                finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Movie>> getMovies() {


        return mDb.createQuery(MovieDb.MovieTable.TABLE_NAME,"SELECT * FROM " + MovieDb.MovieTable.TABLE_NAME).
                mapToList(new Func1<Cursor, Movie>() {
                    @Override
                    public Movie call(Cursor cursor) {
                        return MovieDb.MovieTable.parseCursor(cursor);
                    }
                });
        }

}
