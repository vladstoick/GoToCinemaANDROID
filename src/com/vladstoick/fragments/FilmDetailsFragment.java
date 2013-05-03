package com.vladstoick.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;

public class FilmDetailsFragment extends SherlockFragment {
	private static final String ARG_MOVIE = "movie";
	View view;
	LatLng pozitieCinema = new LatLng(44.419560, 26.1266510);
    private GoogleMap mMap;
    static ProgressDialog pd=null;
    static TextView distance;
	AparitiiCinema movie= null;
	public static FilmMasterFragment newInstance(AparitiiCinema param1) {
		FilmMasterFragment fragment = new FilmMasterFragment();
		Bundle args = new Bundle();
		args.putParcelable(ARG_MOVIE, param1);
		fragment.setArguments(args);
		return fragment;
	}
	public FilmDetailsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_film_details, container,
				false);
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			movie=getArguments().getParcelable(ARG_MOVIE);
			TextView note = (TextView) view.findViewById(R.id.note);
	        note.setText(movie.nota);
	        TextView gen = (TextView) view.findViewById(R.id.gen);
	        gen.setText(movie.gen);
	        TextView actori = (TextView) view.findViewById(R.id.actori);
	        actori.setText(movie.actori);
	        TextView regizor = (TextView) view.findViewById(R.id.regizor);
	        regizor.setText(movie.regizor);
	        distance = (TextView) view.findViewById(R.id.distance);
	        int timp = Integer.parseInt(movie.durataDrum);
	        int ore = timp / 3600;
	        int minute = ( timp % 3600 )/60;
	        distance.setText("Poţi ajunge în "+ore+":"+minute+" ("+movie.distanta+")");
	        pozitieCinema = new LatLng(Double.parseDouble(movie.latCinema),Double.parseDouble(movie.lonCinema));
	        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView)).getMap();
	        mMap.moveCamera(CameraUpdateFactory.newLatLng(pozitieCinema));
	        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));
	        mMap.setMyLocationEnabled(true);
	        mMap.addMarker(new MarkerOptions()
	                .position(pozitieCinema)
	                .title(movie.cinemaName));
		}
	}
}
