package com.vladstoick.gotocinema.slidingactivity;

import java.util.ArrayList;

import com.vladstoick.gotocinema.objects.AparitiiCinema;
import com.vladstoick.gotocinema.objects.Cinema;


import android.location.Location;


public interface OnFragmentInteractionListener {
	public ArrayList<AparitiiCinema> getAllMovies();
	public Location getCurrentLocation();
	public void onSettedATime(int hour,int minute);
	public void openFilmViewWithData(AparitiiCinema data);
	void openNewCinemaList(ArrayList<AparitiiCinema> moviesToBeShown);
	public void showCalculateFragment();
	public void showProfileWithId(int id);
	public void showSearchFragment();
	public Cinema getCinemaInfoForCinemaFor(String name);
}