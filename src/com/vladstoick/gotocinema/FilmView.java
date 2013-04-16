package com.vladstoick.gotocinema;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
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
    LatLng pozitieFolosita = null;
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
        distance = (TextView) findViewById(R.id.distance);
        regizor.setText(currentInfo.regizor);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pozitieCinema));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(pozitieCinema)
                .title(currentInfo.cinemaName));
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        pozitieFolosita = new LatLng(location.getLatitude(),location.getLongitude());
//        RequestTask newV = new RequestTask(2,FilmView.this);
//        pd = ProgressDialog.show(this, "Calculare distanţă", "Vă rugăm aşteptaţi", true);
//        newV.execute("http://maps.googleapis.com/maps/api/directions/json?origin="+pozitieFolosita.latitude+","+pozitieFolosita.longitude+"&destination=44.419560,26.1266510&sensor=false");

    }
    public static void showDirections(String result)
    {
    	try {
            JSONObject jObject = new JSONObject(result);
            JSONArray jArray = jObject.getJSONArray("routes");
            JSONObject mainArray = jArray.getJSONObject(0);
            JSONArray legsArray = mainArray.getJSONArray("legs");
            JSONObject legsObject = legsArray.getJSONObject(0);
            JSONObject duration = legsObject.getJSONObject("duration");
            JSONObject distanta = legsObject.getJSONObject("distance");
            String distantaSTR = (String) distanta.get("text");
            int secunde = (Integer) duration.get("value");
            int minute = secunde/60;
            int ore = minute/60;
            minute = minute%60;
            distance.setText("Se afla la:"+distantaSTR+"("+ore+":"+minute+")");
            System.out.println(duration.toString());
//            
//            distance.setText(duration."text");
//            for (int i = 0; i < jArray.length(); i++) {
//
//                JSONObject oneObject = jArray.getJSONObject(i);
//                String titluRo = oneObject.getString("titluRo");
//                String titluEn = oneObject.getString("titluEn");
//                String cinema = oneObject.getString("cinema");
//                String oraString = oneObject.getString("ora");
//                String nota =oneObject.getString("nota");
//                String gen = oneObject.getString("gen");
//                String actori = oneObject.getString("actori");
//                String regizor = oneObject.getString("regizor");
//                AparitiiCinema aparitie = new AparitiiCinema(titluRo, titluEn, cinema, oraString, nota, regizor, actori, gen);
//                list.add(aparitie);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        	pd.dismiss();
        }
    }
    public void openInGoogleMaps(View v)
    {
    	Intent intent = new Intent(
                android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+ pozitieFolosita.latitude + "," + pozitieFolosita.longitude + "&daddr="+
                pozitieCinema.latitude+","+pozitieCinema.longitude+""));
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}