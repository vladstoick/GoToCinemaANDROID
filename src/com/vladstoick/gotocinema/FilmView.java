package com.vladstoick.gotocinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_view);
        Intent intent = getIntent();
        currentInfo = intent.getParcelableExtra("INFO");
        setTitle(currentInfo.enTitle);
        //TODO currentInfo.lat && currentInfo.lng de implementat
        TextView note = (TextView) findViewById(R.id.note);
        note.setText(currentInfo.nota);
        TextView gen = (TextView) findViewById(R.id.gen);
        gen.setText(currentInfo.gen);
        TextView actori = (TextView) findViewById(R.id.actori);
        actori.setText(currentInfo.actori);
        TextView regizor = (TextView) findViewById(R.id.regizor);
        regizor.setText(currentInfo.regizor);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pozitieCinema));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(pozitieCinema)
                .title("Cinema-ul cautat de tine"));
    }
}