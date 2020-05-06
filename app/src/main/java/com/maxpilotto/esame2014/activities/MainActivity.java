package com.maxpilotto.esame2014.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.maxpilotto.esame2014.R;
import com.maxpilotto.esame2014.adapters.HotelAdapter;
import com.maxpilotto.esame2014.dialogs.DeleteDialog;
import com.maxpilotto.esame2014.persitence.HotelProvider;
import com.maxpilotto.esame2014.persitence.tables.HotelTable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final Integer ID = 1000;

    private ListView listView;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new HotelAdapter(this, null);

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            DeleteDialog dialog = new DeleteDialog(
                    getString(R.string.deleteTitle),
                    ""
            );

            dialog.setCallback(new DeleteDialog.Callback() {
                @Override
                public void onConfirm(DialogInterface dialog) {
                    getContentResolver().delete(HotelProvider.URI_HOTELS, HotelTable._ID + "=" + id, null);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show(getSupportFragmentManager(), null);

            return true;
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, UpdateHotelActivity.class);
            i.putExtra(UpdateHotelActivity.ID_KEY, id);

            startActivity(i);
        });

        findViewById(R.id.add).setOnClickListener(v -> {
            startActivity(new Intent(this, InsetHotelActivity.class));
        });

        getSupportLoaderManager().initLoader(ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, HotelProvider.URI_HOTELS, null, null, null, HotelTable.COLUMN_NAME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }
}
