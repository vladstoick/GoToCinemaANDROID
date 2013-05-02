package com.vladstoick.gotocinema;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class CalculateMainMenuFragment extends Fragment{
//	private OnFragmentInteractionListener mListener;
	View view;
	static TextView timeUsed;
	static private int hourUsed, minuteUsed;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(
				R.layout.fragment_calculate, container,
				false);
		timeUsed = (TextView) view.findViewById(R.id.hourUsed);
		Calendar c = Calendar.getInstance();
		hourUsed = c.get(Calendar.HOUR_OF_DAY);
        minuteUsed = c.get(Calendar.MINUTE);
        updateHour();
		return view;
	}
	private static void updateHour() {
		System.out.println("Hello World");
        String deAfisat = Utils.getStringFromDate(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed));
        timeUsed.setText("Ora folosita " + deAfisat);
    }
	public void setTime(int hour, int minute)
	{
		hourUsed=hour;
		minuteUsed=minute;
		updateHour();
	}
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		try {
//			mListener = (OnFragmentInteractionListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		mListener = null;
//	}

}
