package com.vladstoick.gotocinema.slidingactivity;

import android.location.Location;

public interface OnFragmentInteractionListener {
	public void onSettedATime(int hour,int minute);
	public Location getCurrentLocation();
}