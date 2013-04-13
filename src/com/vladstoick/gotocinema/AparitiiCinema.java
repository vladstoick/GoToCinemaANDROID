package com.vladstoick.gotocinema;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AparitiiCinema implements Parcelable {
    public String roTitle, enTitle, cinemaName;
    public Date ora;
    public String nota, regizor,actori,gen;
    public AparitiiCinema(String roTitle, String enTitle, String cinemaName, String oraString, String nota, String regizor, String actori,String gen) {
        this.roTitle = roTitle;
        this.enTitle = enTitle;
        this.cinemaName = cinemaName;
        this.ora = Utils.getDateFromHourAndMinuteString(oraString);
        this.nota=nota;
        this.regizor=regizor;
        this.actori=actori;
        this.gen=gen;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    private AparitiiCinema(Parcel in) {
        roTitle = in.readString();
        enTitle = in.readString();
        cinemaName = in.readString();
        ora = (Date) Utils.getDateFromHourAndMinuteString(in.readString());
        nota = in.readString();
        regizor=in.readString();
        actori = in.readString();
        gen = in.readString();
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
    }

    public static final Parcelable.Creator<AparitiiCinema> CREATOR = new Parcelable.Creator<AparitiiCinema>() {
        public AparitiiCinema createFromParcel(Parcel in) {
            return new AparitiiCinema(in);
        }

        public AparitiiCinema[] newArray(int size) {
            return new AparitiiCinema[size];
        }
    };
}
