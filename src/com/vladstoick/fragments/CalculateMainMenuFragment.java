package com.vladstoick.fragments;

import java.util.Calendar;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;
import com.vladstoick.gotocinemaUtilityClasses.LocationFragmentListener;
import com.vladstoick.gotocinemaUtilityClasses.Utils;
public class CalculateMainMenuFragment extends Fragment implements LocationFragmentListener{
	private OnFragmentInteractionListener mListener;
	View view;
	Location locationUsed,currentLocation;
	static TextView timeUsed,locationUsedTextView;
	static private int hourUsed, minuteUsed;
	boolean hasALocation=false,isUsingCurrentLocation=true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(
				R.layout.fragment_calculate, container,
				false);
		timeUsed = (TextView) view.findViewById(R.id.hourUsed);
		locationUsedTextView = (TextView) view.findViewById(R.id.locationUsed);
		Calendar c = Calendar.getInstance();
		hourUsed = c.get(Calendar.HOUR_OF_DAY);
        minuteUsed = c.get(Calendar.MINUTE);
        updateHour();
        if(mListener.getCurrentLocation()!=null)
        {
        	currentLocation = locationUsed = mListener.getCurrentLocation();
        	hasALocation=true;
        }
		return view;
	}
	private void setLocationUsed(Location location)
	{
		locationUsed=location;
		if(isUsingCurrentLocation==true)
			locationUsedTextView.setText("Locația folosită: locația ta");
		else
			locationUsedTextView.setText("Locația folosită: locația aleasă de tine");
	}
	@Override
	public void updatedCurrentLocation(Location location) {
		currentLocation=location;
		if(hasALocation==false)
			hasALocation=true;
		if(isUsingCurrentLocation==true)
			setLocationUsed(currentLocation);
	}
	private static void updateHour() {
        String deAfisat = Utils.getStringFromDate(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed));
        timeUsed.setText("Ora folosita " + deAfisat);
    }
	public void setTime(int hour, int minute)
	{
		hourUsed=hour;
		minuteUsed=minute;
		updateHour();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

}
