package com.vladstoick.gotocinema;

import android.location.Location;

public interface OnFragmentInteractionListener {
	public void onSettedATime(int hour,int minute);
	public Location getCurrentLocation();
}