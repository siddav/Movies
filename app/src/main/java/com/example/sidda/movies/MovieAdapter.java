package com.example.sidda.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by sidda on 24/11/15.
 */
public class MovieAdapter extends BaseAdapter {
    int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    @Override
    public int getCount() {
        return numbers.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_view_item, parent, false);
        }
        return convertView;
    }
}
