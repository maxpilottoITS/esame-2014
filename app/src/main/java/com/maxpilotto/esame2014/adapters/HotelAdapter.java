package com.maxpilotto.esame2014.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.maxpilotto.esame2014.R;
import com.maxpilotto.esame2014.models.Hotel;

public class HotelAdapter extends CursorAdapter {
    public HotelAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell_hotel, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Hotel h = new Hotel(cursor);
        String rate = context.getResources().getQuantityText(R.plurals.rating, h.getRating()).toString();

        ((TextView) view.findViewById(R.id.name)).setText(h.getName());
        ((TextView) view.findViewById(R.id.city)).setText(h.getCity());
        ((TextView) view.findViewById(R.id.rating)).setText(String.format(rate, h.getRating()));
        ((TextView) view.findViewById(R.id.price)).setText(h.getPrice().toString());
    }
}
