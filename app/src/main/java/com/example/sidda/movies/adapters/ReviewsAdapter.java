package com.example.sidda.movies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.sidda.movies.R;
import com.example.sidda.movies.model.MovieReview;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidda on 4/1/16.
 */
public class ReviewsAdapter extends BaseAdapter {
    List<MovieReview> reviews = new ArrayList<>();

    public void addAllReviews(List<MovieReview> revs) {
        reviews.addAll(revs);
        notifyDataSetChanged();
    }
    public void clear() {
        reviews.removeAll(reviews);
    }
    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_text_view, parent, false);
        }
        TextView reviewContent = (TextView) convertView.findViewById(R.id.review_item);
        MovieReview review = reviews.get(position);
        reviewContent.setText(review.content);
        return convertView;
    }
}
