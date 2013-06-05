package com.vladstoick.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.vladstoick.arrayadapter.SlidingMenuAdapter;
import com.vladstoick.gotocinema.MainActivity;
import com.vladstoick.gotocinema.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.R;

public class SlidingMenuFragment extends SherlockFragment{
	private final String[] list_contents = {
			"Profilul tău",
			"Cauta alt utilizator",
			"Calculează",
            "Favorite"
		};
    private final Integer[] mThumbIds={
        R.drawable.social_person,
        R.drawable.action_search,
        R.drawable.location_map,
        R.drawable.rating_favorite
    };
	private View view=null;
    public SlidingMenuFragment(){}
	private OnFragmentInteractionListener mListener;
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		ListView listView= (ListView) view.findViewById(R.id.listview);
		listView.setAdapter(new SlidingMenuAdapter(getSherlockActivity(),mThumbIds,list_contents));
		listView.setOnItemClickListener(new OnItemClickListener() {
			        @Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position,
		                long id) {
			        	
			        switch(position)
			        {
			        	case 0: {
			        		mListener.showOwnProfile();
			        		break;
			        	}
			        	case 1: {
			        		mListener.showSearchFragment();
			        		break;
			        	}
			        	case 2:{
			        		mListener.showCalculateFragment();
			        		break;
			        	}
                        case 3:{
                            mListener.showFavorites(MainActivity.userID+"/favorites",false);
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
