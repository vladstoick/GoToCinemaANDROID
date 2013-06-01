package com.vladstoick.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.vladstoick.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.AparitiiCinema;
import com.vladstoick.utility.AparitiiCinemaAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilmListFragment extends SherlockFragment {
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
    private View view;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_film_list, container,false);

        if (getArguments() != null) {
            moviesToBeShown=getArguments().getParcelableArrayList(ARG_MOVIES);
            Collections.sort(moviesToBeShown, new ArrayComparatorByTime());
            AparitiiCinemaAdapter adapter = new AparitiiCinemaAdapter(getActivity(), moviesToBeShown);
            SwipeListView slv = (SwipeListView) view.findViewById(R.id.swipelistview);
            slv.setAdapter(adapter);
            slv.setSwipeListViewListener(new BaseSwipeListViewListener() {
                @Override
                public void onClickFrontView(int position) {
                    mListener.openFilmViewWithData(moviesToBeShown.get(position));
                }
            });
        }
        return view;
    }
    public final static void clickedFavorite(int position)
    {
        Log.e("clicked","Clicked"+position);
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


	

}
