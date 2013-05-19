package com.vladstoick.gotocinema.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vlad on 5/19/13.
 */
public class Post implements Parcelable {
    public final String posterID,posterImg,posterFullname,postContent,postTitle;
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    private Post(Parcel in)
    {
        posterID=in.readString();
        posterImg=in.readString();
        posterFullname=in.readString();
        postContent=in.readString();
        postTitle=in.readString();
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterID);
        dest.writeString(posterImg);
        dest.writeString(posterFullname);
        dest.writeString(postContent);
        dest.writeString(postTitle);
    }
    public Post(String posterID,String posterImg,String posterFullname,String postContent,String postTitle)
    {
        this.posterID=posterID;
        this.posterImg=posterImg;
        this.posterFullname=posterFullname;
        this.postContent=postContent;
        this.postTitle=postTitle;
    }
}
