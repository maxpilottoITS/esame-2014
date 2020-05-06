package com.maxpilotto.esame2014.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.esame2014.R;
import com.maxpilotto.esame2014.models.Hotel;
import com.maxpilotto.esame2014.persitence.HotelProvider;
import com.maxpilotto.esame2014.persitence.tables.HotelTable;

public class UpdateHotelActivity extends AppCompatActivity {
    public static final String ID_KEY = "extra.id_key";

    private TextView nameTv;
    private TextView cityTv;
    private RatingBar ratingBar;
    private TextView priceTv;
    private Hotel hotel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Long id = getIntent().getLongExtra(ID_KEY, -1);
        Cursor cursor = getContentResolver().query(
                HotelProvider.URI_HOTELS,
                null,
                HotelTable._ID + "=" + id,
                null,
                null
        );

        nameTv = findViewById(R.id.name);
        cityTv = findViewById(R.id.city);
        ratingBar = findViewById(R.id.rating);
        priceTv = findViewById(R.id.price);

        cursor.moveToNext();

        nameTv.setText(cursor.getString(cursor.getColumnIndex(HotelTable.COLUMN_NAME)));
        cityTv.setText(cursor.getString(cursor.getColumnIndex(HotelTable.COLUMN_CITY)));
        ratingBar.setProgress(cursor.getInt(cursor.getColumnIndex(HotelTable.COLUMN_RATING)));
        priceTv.setText(cursor.getDouble(cursor.getColumnIndex(HotelTable.COLUMN_PRICE)) + "");

        cursor.close();

        findViewById(R.id.cancel).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.save).setOnClickListener(v -> {
            String name = nameTv.getText().toString();
            String city = cityTv.getText().toString();
            Integer rating = ratingBar.getProgress();
            Double price;

            try {
                price = Double.parseDouble(priceTv.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(this, getString(R.string.errorPrice), Toast.LENGTH_SHORT).show();

                return;
            }

            if (name.isEmpty() || city.isEmpty() || rating == 0) {
                Toast.makeText(this, getString(R.string.errorEmptyField), Toast.LENGTH_SHORT).show();

                return;
            }

            ContentValues values = new ContentValues();

            values.put(HotelTable.COLUMN_NAME, name);
            values.put(HotelTable.COLUMN_CITY, city);
            values.put(HotelTable.COLUMN_RATING, rating);
            values.put(HotelTable.COLUMN_PRICE, price);

            getContentResolver().update(
                    HotelProvider.URI_HOTELS,
                    values,
                    HotelTable._ID + "=" + id,
                    null
            );

            finish();
        });
    }
}
