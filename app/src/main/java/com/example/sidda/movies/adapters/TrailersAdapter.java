package com.example.sidda.movies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.sidda.movies.R;
import com.example.sidda.movies.constants.MovieConstants;
import com.example.sidda.movies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidda on 4/1/16.
 */
public class TrailersAdapter extends BaseAdapter {
    List<Video> videos = new ArrayList<>();

    public void addAllVideos(List<Video> vds) {
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
        Video video = videos.get(position);
        // of the format http://img.youtube.com/vi/{KEY}/0.jpg
        Picasso.with(parent.getContext()).load(MovieConstants.BASE_IMAGE_URL + "/" + video.key + "/0.jpg").into(imageView);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return videos.get(position).id;
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
