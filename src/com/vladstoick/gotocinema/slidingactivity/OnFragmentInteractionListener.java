package com.vladstoick.gotocinema.slidingactivity;

import android.location.Location;
import com.vladstoick.gotocinema.objects.AparitiiCinema;
import com.vladstoick.gotocinema.objects.Cinema;

import java.util.ArrayList;


public interface OnFragmentInteractionListener {
	public ArrayList<AparitiiCinema> getAllMovies();
	public Location getCurrentLocation();
	public void onSettedATime(int hour,int minute);
	public void openFilmViewWithData(AparitiiCinema data);
	void openNewCinemaList(ArrayList<AparitiiCinema> moviesToBeShown);
	public void showCalculateFragment();
	public void showProfileWithId(String id);
    public void showOwnProfile();
	public void showSearchFragment();
	public Cinema getCinemaInfoForCinemaFor(String name);
    public void showPostFragment(String userId);
}