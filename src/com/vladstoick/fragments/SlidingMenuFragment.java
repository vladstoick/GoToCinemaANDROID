package com.vladstoick.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.vladstoick.gotocinema.R;

public class SlidingMenuFragment extends SherlockListFragment{
	String[] list_contents = {
			"item 1",
			"item 2",
			"item 3",
			"item 4",
			"item 5"
		};
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			return inflater.inflate(R.layout.fragment_sliding_list, container, false);
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState){
			super.onActivityCreated(savedInstanceState);
			setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_contents));
		}
}
