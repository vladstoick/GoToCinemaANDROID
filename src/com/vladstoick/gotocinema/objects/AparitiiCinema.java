package com.vladstoick.gotocinema.objects;

import android.os.Parcel;
import android.os.Parcelable;
import com.vladstoick.gotocinema.gotocinemaUtilityClasses.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;


public class AparitiiCinema implements Parcelable {
    public final String roTitle;
    public final String enTitle;
    public final String cinemaName;
    public final String imgUrl;
    public final Date ora;
    public final String nota;
    public final String regizor;
    public final String actori;
    public final String gen;
    public String distanta="-", durataDrum="-";
    public String latCinema="-" , lonCinema="-";
    public static final Parcelable.Creator<AparitiiCinema> CREATOR = new Parcelable.Creator<AparitiiCinema>() {
        @Override
		public AparitiiCinema createFromParcel(Parcel in) {
            return new AparitiiCinema(in);
        }

        @Override
		public AparitiiCinema[] newArray(int size) {
            return new AparitiiCinema[size];
        }
    };
    public static ArrayList<AparitiiCinema> merge(ArrayList<AparitiiCinema> movies, Hashtable<String, Cinema> cinemas){
    	System.out.println(cinemas.size());
    	for(int i=0;i<movies.size();i++)
		{
			AparitiiCinema newObject = movies.get(i);
			newObject.distanta=cinemas.get(newObject.cinemaName).distance;
			newObject.durataDrum=cinemas.get(newObject.cinemaName).duration;
			newObject.latCinema=cinemas.get(newObject.cinemaName).latCinema;
			newObject.lonCinema=cinemas.get(newObject.cinemaName).lngCinema;
			movies.set(i,newObject);
		}
    	return movies;
    }
    private AparitiiCinema(Parcel in) {
        roTitle = in.readString();
        enTitle = in.readString();
        cinemaName = in.readString();
        ora = Utils.getDateFromHourAndMinuteString(in.readString());
        nota = in.readString();
        regizor=in.readString();
        actori = in.readString();
        gen = in.readString();
        distanta = in.readString();
        durataDrum = in.readString();
        latCinema = in.readString();
        lonCinema = in.readString();
        imgUrl=in.readString();
    }

    public AparitiiCinema(String roTitle, String enTitle, String cinemaName, String oraString, String nota, String regizor, String actori,String gen,String imgUrl) {
        this.roTitle = roTitle;
        this.enTitle = enTitle;
        this.cinemaName = cinemaName;
        this.ora = Utils.getDateFromHourAndMinuteString(oraString);
        this.nota=nota;
        this.regizor=regizor;
        this.actori=actori;
        this.gen=gen;
        this.imgUrl=imgUrl;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(roTitle);
        dest.writeString(enTitle);
        dest.writeString(cinemaName);
        dest.writeString(Utils.getStringFromDate(ora));
        dest.writeString(nota);
        dest.writeString(regizor);
        dest.writeString(actori);
        dest.writeString(gen);
        dest.writeString(distanta);
        dest.writeString(durataDrum);
        dest.writeString(latCinema);
        dest.writeString(lonCinema);
        dest.writeString(imgUrl);
    }
}
