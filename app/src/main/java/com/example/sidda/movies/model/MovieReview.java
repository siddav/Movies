package com.example.sidda.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sidda on 10/12/15.
 */
public class MovieReview implements Parcelable{
    public String id;
    public String author;
    public String content;
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public MovieReview() {
    }

    protected MovieReview(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        public MovieReview createFromParcel(Parcel source) {
            return new MovieReview(source);
        }

        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
}
