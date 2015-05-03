package com.team13.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;

/**
 * Created by Trev on 4/25/15.
 */
class DataAdapter extends CursorAdapter {

    private final BindTwoLineCursor binder;

    public DataAdapter(Activity context, Cursor c, BindTwoLineCursor binder) {
        super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
        this.binder = binder;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return inflater.inflate(R.layout.two_line_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        binder.bindView(view, cursor);
    }

    public void showAddDialog(Context context, String title, final OnAddItemListener onAdd) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.add_dialog, null);

        final EditText input = (EditText) dialogView.findViewById(R.id.input);

        final DataAdapter adapter = this;

        new AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle(title)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onAdd.onAddItem(input.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    public static interface OnAddItemListener {
        public void onAddItem(String input);
    }
}