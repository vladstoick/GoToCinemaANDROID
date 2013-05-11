package com.vladstoick.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;

public class SlidingMenuFragment extends SherlockFragment{
	String[] list_contents = {
			"Profilul tău",
			"Cauta alt utilizator",
			"Calculează",
		};
	View view=null;
	private OnFragmentInteractionListener mListener;
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		ListView listView= (ListView) view.findViewById(R.id.listview);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.sliding_row_view, list_contents));
		listView.setOnItemClickListener(new OnItemClickListener() {
			        @Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position,
		                long id) {
			        	
			        switch(position)
			        {
			        	case 0: {
			        		
			        		break;
			        	}
			        	case 1: {
			        		mListener.showSearchFragment();
			        		break;
			        	}
			        	default:{
			        		mListener.showCalculateFragment();
			        		break;
			        	}
			        }
		            	
		        }
		    });
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		view=inflater.inflate(R.layout.fragment_sliding_list, container, false);
		return view;
	}
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

}
