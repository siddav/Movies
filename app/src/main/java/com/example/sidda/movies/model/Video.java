package com.example.sidda.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sidda on 10/12/15.
 */
public class Video implements Parcelable{
    public long id;
    public String key;
    public String name;
    public String site;
    public int size;
    public String type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.id = in.readLong();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
