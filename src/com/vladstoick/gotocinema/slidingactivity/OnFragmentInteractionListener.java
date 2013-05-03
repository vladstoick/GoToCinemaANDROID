package com.vladstoick.gotocinema.slidingactivity;

import java.util.ArrayList;

import android.location.Location;

import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;

public interface OnFragmentInteractionListener {
	public void onSettedATime(int hour,int minute);
	public Location getCurrentLocation();
	void openNewCinemaList(ArrayList<AparitiiCinema> moviesToBeShown);
	public ArrayList<AparitiiCinema> getAllMovies();
	public void openFilmViewWithData(AparitiiCinema data);
}