package com.example.sidda.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sidda on 10/12/15.
 */
public class MovieVideos implements Parcelable{
    public String id;
    public String key;
    public String name;
    public String site;
    public int size;
    public String type;
    @SerializedName("iso_639_1")
    public String language;

    public MovieVideos() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
        dest.writeString(this.language);
    }

    protected MovieVideos(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
        this.language = in.readString();
    }

    public static final Creator<MovieVideos> CREATOR = new Creator<MovieVideos>() {
        public MovieVideos createFromParcel(Parcel source) {
            return new MovieVideos(source);
        }

        public MovieVideos[] newArray(int size) {
            return new MovieVideos[size];
        }
    };
}
