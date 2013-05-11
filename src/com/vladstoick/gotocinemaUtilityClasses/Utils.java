package com.vladstoick.gotocinemaUtilityClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.vladstoick.gotocinema.objects.AparitiiCinema;


import android.annotation.SuppressLint;



public class Utils {
    @SuppressLint("SimpleDateFormat")
	static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    public static ArrayList<AparitiiCinema> getAparitii(ArrayList<AparitiiCinema> list, Date dateToBeUsed) {
        ArrayList<AparitiiCinema> listToBeReturned = new ArrayList<AparitiiCinema>();
        System.out.println(list.get(1).durataDrum);
        for (int i = 0; i < list.size(); i++) 
            if (dateToBeUsed.getTime() + (new Date(Integer.parseInt(list.get(i).durataDrum)*1000)).getTime() -list.get(i).ora.getTime() < 0)
                listToBeReturned.add(list.get(i));
        return listToBeReturned;
    }

    public static Date getDateFromHourAndMinuteInts(int hour, int minute) {
        try {
            return formatter.parse(hour + ":" + minute);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromHourAndMinuteString(String time) {
        try {
            return formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromDate(Date time) {
        return formatter.format(time);
    }
}
