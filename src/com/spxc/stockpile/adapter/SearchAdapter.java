package com.spxc.stockpile.adapter;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.spxc.stockpile.R;

public class SearchAdapter extends CursorAdapter {
	 
    private List items;
 
    private TextView text;
 
    public SearchAdapter(Context context, Cursor cursor, List items) {
 
        super(context, cursor, false);
 
        this.items = items;
 
    }
 
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
 
        // Show list item data from cursor
        text.setText((CharSequence) items.get(cursor.getPosition()));
 
        // Alternatively show data direct from database
        //text.setText(cursor.getString(cursor.getColumnIndex("text")));
 
    }
 
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
 
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View view = inflater.inflate(R.layout.search_item, parent, false);
 
        text = (TextView) view.findViewById(R.id.text);
 
        return view;
 
    }
 
}