package com.vladstoick.utility;

import android.annotation.SuppressLint;

import com.vladstoick.objects.AparitiiCinema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class Utils {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    public static ArrayList<AparitiiCinema> getAparitii(ArrayList<AparitiiCinema> list, Date dateToBeUsed) {
        ArrayList<AparitiiCinema> listToBeReturned = new ArrayList<AparitiiCinema>();
        System.out.println(list.get(1).durataDrum);
        for (AparitiiCinema aList : list)
        {
            if(aList.durataDrum.equals('-'))
                continue;
            if (dateToBeUsed.getTime() + (new Date(Integer.parseInt(aList.durataDrum) * 1000)).getTime() - aList.ora.getTime() < 0)
                listToBeReturned.add(aList);
        }
        return listToBeReturned;
    }

    public static Date getDateFromHourAndMinuteInts(int hour, int minute) {
        try {
            return formatter.parse(hour + ":" + minute);
        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromHourAndMinuteString(String time) {
        try {
            return formatter.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromDate(Date time) {
        return formatter.format(time);
    }
}
