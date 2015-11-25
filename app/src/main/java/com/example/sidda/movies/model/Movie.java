package com.example.sidda.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sidda on 24/11/15.
 */
public class Movie implements Parcelable{
    @SerializedName("adult")
    public boolean adult;
    @SerializedName("backdrop_path")
    public String backDropPath;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("title")
    public String title;
    @SerializedName("id")
    public long id;
    @SerializedName("runtime")
    public int runtime;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("vote_average")
    public String rating;
    @SerializedName("overview")
    public String overView;

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backDropPath);
        dest.writeString(this.originalTitle);
        dest.writeString(this.posterPath);
        dest.writeString(this.title);
        dest.writeLong(this.id);
        dest.writeInt(this.runtime);
        dest.writeString(this.releaseDate);
        dest.writeString(this.rating);
        dest.writeString(this.overView);
    }

    protected Movie(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backDropPath = in.readString();
        this.originalTitle = in.readString();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.id = in.readLong();
        this.runtime = in.readInt();
        this.releaseDate = in.readString();
        this.rating = in.readString();
        this.overView = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
