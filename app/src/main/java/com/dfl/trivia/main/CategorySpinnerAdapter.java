package com.dfl.trivia.main;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dfl.trivia.main.model.Category;

import java.util.ArrayList;

/**
 * Created by Loureiro on 16/11/2017.
 */

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {

    private Context context;
    private ArrayList<Category> values;

    public CategorySpinnerAdapter(Context context, int textViewResourceId,
                                  ArrayList<Category> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Category getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getName());
        return label;
    }
}