package com.vladstoick.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;
import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;
import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinemaAdapter;
import com.vladstoick.gotocinemaUtilityClasses.CinemaRestClient;
import com.vladstoick.gotocinemaUtilityClasses.JSONParser;

public class FilmFragment extends SherlockListFragment {
	AparitiiCinemaAdapter adapter = null;
	private static final String ARG_LINK = "link1";
	private static final String ARG_MOVIES = "movies";
	private String link;
	private OnFragmentInteractionListener mListener;
	ProgressDialogFragment progressDialog= new ProgressDialogFragment();
	ArrayList <AparitiiCinema> moviesToBeShown;
	public static FilmFragment newInstance(String param1,ArrayList<AparitiiCinema> param2) {
		FilmFragment fragment = new FilmFragment();
		Bundle args = new Bundle();
		args.putString(ARG_LINK, param1);
		args.putParcelableArrayList(ARG_MOVIES, param2);
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
			moviesToBeShown=getArguments().getParcelableArrayList(ARG_MOVIES);
			progressDialog.show(getChildFragmentManager(),"Loading");
			CinemaRestClient.get(link, null, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String resultString) {
	            	moviesToBeShown=JSONParser.parseMoviesAndDistances(resultString,moviesToBeShown);
	                progressDialog.dismiss();
	            }
	        });
		}
		adapter = new AparitiiCinemaAdapter(getActivity(), R.layout.list_row_view, moviesToBeShown);
		setListAdapter(adapter);
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
