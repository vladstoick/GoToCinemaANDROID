package com.vladstoick.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteMovie implements Parcelable{
    public String imgUrl, enTitle;
    public static final Parcelable.Creator<FavoriteMovie> CREATOR = new Parcelable.Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel in) {
            return new FavoriteMovie(in);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };
    public FavoriteMovie(String imgUrl,String enTitle)
    {
        this.imgUrl=imgUrl;
        this.enTitle=enTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
        dest.writeString(enTitle);
    }
    private FavoriteMovie(Parcel in) {
        this.imgUrl=in.readString();
        this.enTitle=in.readString();
    }
}
