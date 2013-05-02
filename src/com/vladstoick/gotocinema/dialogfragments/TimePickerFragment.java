package com.vladstoick.gotocinema.dialogfragments;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;

public class TimePickerFragment extends SherlockDialogFragment
	implements TimePickerDialog.OnTimeSetListener {
	private OnFragmentInteractionListener mListener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
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

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		mListener.onSettedATime(hourOfDay, minute);
	}
}
