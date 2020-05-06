package com.maxpilotto.esame2014.persitence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.maxpilotto.esame2014.persitence.tables.HotelTable;

public class HotelHelper extends SQLiteOpenHelper {
    public HotelHelper(@Nullable Context context) {
        super(context, "Esame_2014", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HotelTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
