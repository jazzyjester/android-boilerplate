package uk.co.ribot.androidboilerplate.data.local;

/**
 * Created by ronnyr on 19/09/2016.
 */

import android.content.ContentValues;
import android.database.Cursor;

import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.Posters;


public class MovieDb {

    public MovieDb() { }

    public abstract static class MovieTable {
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_THUMBNAIL = "thumbnail";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_THUMBNAIL + " TEXT NOT NULL, " +
                        COLUMN_YEAR + " TEXT NOT NULL " +
                        " ); ";

        public static ContentValues toContentValues(Movie movie) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, movie.id());
            values.put(COLUMN_TITLE, movie.title());
            values.put(COLUMN_YEAR, movie.year());
            values.put(COLUMN_THUMBNAIL,movie.posters().thumbnail());
            return values;
        }

        public static Movie parseCursor(Cursor cursor) {

            Posters posters = Posters.create(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL)));

            return  Movie.builder()
                    .setPosters(posters)
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)))
                    .setYear(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)))
                    .build();
        }
    }
}
