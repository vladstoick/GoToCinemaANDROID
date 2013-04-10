package com.vladstoick.gotocinema;

import java.util.Date;
public class AparitiiCinema {
	public String roTitle,enTitle,cinemaName;
	public Date ora;
	
	public AparitiiCinema(String roTitle,String  enTitle,String cinemaName,String oraString)
	{
		this.roTitle=roTitle;
		this.enTitle=enTitle;
		this.cinemaName=cinemaName;
		this.ora = Utils.getDateFromHourAndMinuteString(oraString);
	}
}
