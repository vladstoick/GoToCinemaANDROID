package com.vladstoick.gotocinema;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FilmView extends Activity {

    AparitiiCinema currentInfo;
    LatLng pozitieCinema = new LatLng(44.419560, 26.1266510);
    private GoogleMap mMap;
    static ProgressDialog pd=null;
    static TextView distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_view);
        Intent intent = getIntent();
        currentInfo = intent.getParcelableExtra("INFO");
        setTitle(currentInfo.enTitle);
        TextView note = (TextView) findViewById(R.id.note);
        note.setText(currentInfo.nota);
        TextView gen = (TextView) findViewById(R.id.gen);
        gen.setText(currentInfo.gen);
        TextView actori = (TextView) findViewById(R.id.actori);
        actori.setText(currentInfo.actori);
        TextView regizor = (TextView) findViewById(R.id.regizor);
        regizor.setText(currentInfo.regizor);
        distance = (TextView) findViewById(R.id.distance);
        int timp = Integer.parseInt(currentInfo.durataDrum);
        int ore = timp / 3600;
        int minute = ( timp % 3600 )/60;
        distance.setText("Poţi ajunge în "+ore+":"+minute+" ("+currentInfo.distanta+")");
        pozitieCinema = new LatLng(Double.parseDouble(currentInfo.latCinema),Double.parseDouble(currentInfo.lonCinema));
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pozitieCinema));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(pozitieCinema)
                .title(currentInfo.cinemaName));

    }
    public void openInGoogleMaps(View v)
    {
//    	Intent intent = new Intent(
//                android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?saddr="+ pozitieFolosita.latitude + "," + pozitieFolosita.longitude + "&daddr="+
//                pozitieCinema.latitude+","+pozitieCinema.longitude+""));
//        intent.setClassName("com.google.android.apps.maps",
//                "com.google.android.maps.MapsActivity");
//        startActivity(intent);
    }
}