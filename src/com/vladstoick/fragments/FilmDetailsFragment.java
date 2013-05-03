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
    TextView note,gen,actori,distanta,distance,regizor;
	AparitiiCinema movie= null;
	public static FilmDetailsFragment newInstance(AparitiiCinema param1) {
		FilmDetailsFragment fragment = new FilmDetailsFragment();
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
		note = (TextView) view.findViewById(R.id.note);
		gen = (TextView) view.findViewById(R.id.gen);
        actori = (TextView) view.findViewById(R.id.actori);
        regizor = (TextView) view.findViewById(R.id.regizor);
        distance = (TextView) view.findViewById(R.id.distance);
        movie=getArguments().getParcelable(ARG_MOVIE);
		System.out.println(movie.actori);
        note.setText(movie.nota);
        gen.setText(movie.gen);
        actori.setText(movie.actori);
        regizor.setText(movie.regizor);
        int timp = Integer.parseInt(movie.durataDrum);
        int ore = timp / 3600;
        int minute = ( timp % 3600 )/60;
        distance.setText("Poţi ajunge în "+ore+":"+minute+" ("+movie.distanta+")");
        pozitieCinema = new LatLng(Double.parseDouble(movie.latCinema),Double.parseDouble(movie.lonCinema));
        SupportMapFragment mf = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mMap = mf.getMap();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pozitieCinema));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(pozitieCinema)
                .title(movie.cinemaName));
		return view;
	}
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		if (getArguments() != null) {
//			
//
//		}
//	}
}