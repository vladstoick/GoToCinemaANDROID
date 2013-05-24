package com.vladstoick.gotocinema.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.objects.AparitiiCinema;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.gotocinemaUtilityClasses.AparitiiCinemaAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilmListFragment extends SherlockListFragment {
	private class ArrayComparatorByDistance implements Comparator<AparitiiCinema> {
        @Override
        public int compare(AparitiiCinema o1, AparitiiCinema o2) {
        	double km1 = Double.parseDouble(o1.distanta.substring(0, o1.distanta.length()-2));
        	double km2 = Double.parseDouble(o2.distanta.substring(0, o2.distanta.length()-2));
            return Double.compare(km1, km2) ;
        }
    }
	private class ArrayComparatorByName implements Comparator<AparitiiCinema> {
    	@Override
    	public int compare(AparitiiCinema o1, AparitiiCinema o2)
    	{
    		return (o1.enTitle).compareTo(o2.enTitle);
    	}
    }
	private class ArrayComparatorByTime implements Comparator<AparitiiCinema> {
        @Override
        public int compare(AparitiiCinema o1, AparitiiCinema o2) {
            return (o1.ora).compareTo(o2.ora);
        }
    }
	public static FilmListFragment newInstance(ArrayList<AparitiiCinema> param2) {
		FilmListFragment fragment = new FilmListFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList(ARG_MOVIES, param2);
		fragment.setArguments(args);
		return fragment;
	}

    private static final String ARG_MOVIES = "movies";
    private OnFragmentInteractionListener mListener;
    ProgressDialogFragment progressDialog= new ProgressDialogFragment();
	private ArrayList <AparitiiCinema> moviesToBeShown;
	private FilmListFragment() {
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			moviesToBeShown=getArguments().getParcelableArrayList(ARG_MOVIES);
			Collections.sort(moviesToBeShown, new ArrayComparatorByTime());
            AparitiiCinemaAdapter adapter = new AparitiiCinemaAdapter(getActivity(), R.layout.list_row_view, moviesToBeShown);
			setListAdapter(adapter);
		}
		setHasOptionsMenu(true);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (null != mListener) {
			mListener.openFilmViewWithData(moviesToBeShown.get(position));
		}
	}
	

}
