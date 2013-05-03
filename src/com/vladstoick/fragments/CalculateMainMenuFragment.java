package com.vladstoick.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;
import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;
import com.vladstoick.gotocinemaUtilityClasses.CinemaRestClient;
import com.vladstoick.gotocinemaUtilityClasses.JSONParser;
import com.vladstoick.gotocinemaUtilityClasses.LocationFragmentListener;
import com.vladstoick.gotocinemaUtilityClasses.Utils;
public class CalculateMainMenuFragment extends Fragment{
	private OnFragmentInteractionListener mListener;
	View view;
	static TextView timeUsed,locationUsedTextView;
	static private int hourUsed, minuteUsed;
	boolean hasALocation=false,isUsingCurrentLocation=true;
	ProgressDialogFragment progressDialog= new ProgressDialogFragment();
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
        final Button calculateBtn = (Button) view.findViewById(R.id.btnCalculate);
     
        calculateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {         	
            	if(mListener.getCurrentLocation()==null)
            		Toast.makeText(getActivity(), "Aplicatia inca te localizeaza", Toast.LENGTH_SHORT).show();
            	mListener.openNewCinemaList(Utils.getAparitii(mListener.getAllMovies(),
                		Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed)));
            }
        });
		return view;
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
