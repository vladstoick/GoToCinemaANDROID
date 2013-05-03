package com.vladstoick.gotocinema.slidingactivity;

import java.util.ArrayList;

import android.location.Location;

import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;

public interface OnFragmentInteractionListener {
	public void onSettedATime(int hour,int minute);
	public Location getCurrentLocation();
	public void openNewCinemaList(String adress);
	public ArrayList<AparitiiCinema> getAllMovies();
}