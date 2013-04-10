package com.vladstoick.gotocinema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	static SimpleDateFormat  formatter = new SimpleDateFormat("HH:mm");
	
	public static Date getDateFromHourAndMinuteInts(int hour,int minute)
	{
		try {
			return (Date) formatter.parse(hour+":"+minute);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static Date getDateFromHourAndMinuteString(String time)
	{
		try {
			return (Date) formatter.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String getStringFromDate(Date time)
	{
		return (String) formatter.format(time);
	}
}
