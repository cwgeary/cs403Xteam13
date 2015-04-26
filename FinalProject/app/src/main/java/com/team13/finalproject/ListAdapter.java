package com.team13.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.List;

/**
 * Created by Trev on 4/25/15.
 */
class ListAdapter extends ArrayAdapter<TwoItemListable> {

    public ListAdapter(Context context, TwoItemListable[] items) {
        super(context, android.R.layout.simple_list_item_1, items);
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.two_line_list_item, parent, false);
        TextView valueTextView = (TextView) row.findViewById(R.id.value);
        TextView descriptionTextView = (TextView) row.findViewById(R.id.description);

        TwoItemListable item = getItem(index);
        valueTextView.setText(item.getValue());
        descriptionTextView.setText(item.getDescription());

        return row;
    }
}