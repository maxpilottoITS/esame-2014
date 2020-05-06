package com.maxpilotto.esame2014.persitence.tables;

import android.provider.BaseColumns;

public class HotelTable implements BaseColumns {
    public static final String NAME = "hotels";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_PRICE = "price";

    public static final String CREATE = "CREATE TABLE " + NAME + "(" +
            _ID + " integer primary key autoincrement," +
            COLUMN_NAME + " text," +
            COLUMN_CITY + " text," +
            COLUMN_RATING + " integer," +
            COLUMN_PRICE + " real" +
            ");";

}
