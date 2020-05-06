package com.maxpilotto.esame2014.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.esame2014.R;
import com.maxpilotto.esame2014.persitence.HotelProvider;
import com.maxpilotto.esame2014.persitence.tables.HotelTable;

public class InsetHotelActivity extends AppCompatActivity {
    private TextView nameTv;
    private TextView cityTv;
    private RatingBar ratingBar;
    private TextView priceTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        nameTv = findViewById(R.id.name);
        cityTv = findViewById(R.id.city);
        ratingBar = findViewById(R.id.rating);
        priceTv = findViewById(R.id.price);

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

            getContentResolver().insert(HotelProvider.URI_HOTELS, values);

            finish();
        });
    }
}
