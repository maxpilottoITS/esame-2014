package com.maxpilotto.esame2014.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.maxpilotto.esame2014.R;

public class DeleteDialog extends DialogFragment {
    private Callback callback;
    private String title;
    private String message;

    public DeleteDialog(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    if (callback != null) {
                        callback.onConfirm(dialog);
                    }
                })
                .setNegativeButton(R.string.cancel,(dialog, which) -> {
                    if (callback != null) {
                        callback.onCancel(dialog);
                    }
                })
                .create();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onConfirm(DialogInterface dialog);
        void onCancel(DialogInterface dialog);
    }
}
