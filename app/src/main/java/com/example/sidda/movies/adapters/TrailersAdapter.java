package com.example.sidda.movies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.sidda.movies.R;
import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.MovieVideos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidda on 4/1/16.
 */
public class TrailersAdapter extends BaseAdapter {
    List<MovieVideos> videos = new ArrayList<>();
    Integer[] str = {R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4};
    int count = 0;
    public void addAllVideos(List<MovieVideos> vds) {
        videos.addAll(vds);
        notifyDataSetChanged();
    }
    public void clear() {
        videos.removeAll(videos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_thumbnail_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnail_image);
        MovieVideos video = videos.get(position);
        // of the format http://img.youtube.com/vi/{KEY}/0.jpg
        // MovieConstants.YOUTUBE_THUMBNAIL_BASE_URL + video.key + "/0.jpg"
        Picasso.with(parent.getContext()).load(MovieConstants.YOUTUBE_THUMBNAIL_BASE_URL + video.key + "/0.jpg").into(imageView);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public int getCount() {
        return videos.size();
    }
}
