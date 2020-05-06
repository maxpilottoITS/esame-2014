package com.maxpilotto.esame2014.persitence;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxpilotto.esame2014.persitence.tables.HotelTable;

public class HotelProvider extends ContentProvider {
    public static final String AUTHORITY = "com.maxpilotto.esame2014.database.ContentProvider";

    public static final String BASE_PATH_HOTELS = "hotels";

    public static final int ALL_HOTELS = 100;
    public static final int SINGLE_HOTEL = 120;

    public static final String MIME_TYPE_ALL_HOTELS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_hotels";
    public static final String MIME_TYPE_HOTEL = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_hotel";

    public static final Uri URI_HOTELS = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_HOTELS);

    private HotelHelper database;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH_HOTELS, ALL_HOTELS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_HOTELS + "/#", SINGLE_HOTEL);
    }

    @Override
    public boolean onCreate() {
        database = new HotelHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case SINGLE_HOTEL:
                builder.setTables(HotelTable.NAME);
                builder.appendWhere(HotelTable._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_HOTELS:
                builder.setTables(HotelTable.NAME);
                break;
        }

        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_HOTEL:
                return MIME_TYPE_HOTEL;

            case ALL_HOTELS:
                return MIME_TYPE_ALL_HOTELS;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == ALL_HOTELS) {
            long result = database.getWritableDatabase().insert(HotelTable.NAME, null, values);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_HOTELS + "/" + result;

            getContext().getContentResolver().notifyChange(uri, null);

            return Uri.parse(resultString);
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_HOTEL:
                table = HotelTable.NAME;
                query = HotelTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_HOTELS:
                table = HotelTable.NAME;
                query = selection;
                break;
        }

        int deletedRows = db.delete(table, query, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case SINGLE_HOTEL:
                table = HotelTable.NAME;
                query = HotelTable._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;

            case ALL_HOTELS:
                table = HotelTable.NAME;
                query = selection;
                break;
        }

        int updatedRows = db.update(table, values, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }
}
