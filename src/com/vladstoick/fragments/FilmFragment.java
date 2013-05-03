package com.vladstoick.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.gotocinema.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.dummy.DummyContent;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;
import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;
import com.vladstoick.gotocinemaUtilityClasses.CinemaRestClient;
import com.vladstoick.gotocinemaUtilityClasses.JSONParser;

public class FilmFragment extends SherlockListFragment {

	private static final String ARG_LINK = "link1";
	private static final String ARG_DATE = "link1";
	private String link;
	private OnFragmentInteractionListener mListener;
	ProgressDialogFragment progressDialog= new ProgressDialogFragment();
	ArrayList <AparitiiCinema> moviesToBeShown;
	// TODO: Rename and change types of parameters
	public static FilmFragment newInstance(String param1,String param2) {
		FilmFragment fragment = new FilmFragment();
		Bundle args = new Bundle();
		args.putString(ARG_LINK, param1);
		args.putString(ARG_DATE, param2);
		fragment.setArguments(args);
		return fragment;
	}
	public FilmFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			link = getArguments().getString(ARG_LINK);
			progressDialog.show(getChildFragmentManager(),"Loading");
			CinemaRestClient.get(link, null, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String resultString) {
	            	moviesToBeShown=JSONParser.parseMoviesAndDistances(resultString,mListener.getAllMovies());
	                progressDialog.dismiss();
	            }
	        });
		}

		setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				DummyContent.ITEMS));
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

//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
//
//		if (null != mListener) {
//			// Notify the active callbacks interface (the activity, if the
//			// fragment is attached to one) that an item has been selected.
//			mListener
//					.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//		}
//	}


}
