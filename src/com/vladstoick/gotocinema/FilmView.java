package com.vladstoick.gotocinema;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.RouteManager;
public class FilmView extends MapActivity {
    protected MapView map;
    private MyLocationOverlay myLocationOverlay;
    AparitiiCinema currentInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_film_view);
      Intent intent = getIntent();
      currentInfo = intent.getParcelableExtra("INFO");
      setTitle(currentInfo.enTitle);
      setupMapView();
      setupMyLocation();
      WebView itinerary = (WebView) findViewById(R.id.itinerary);

      // pass in your AppKey to RouteManager when not extending MapActivity 
      RouteManager routeManager = new RouteManager(this);

      // generate and display the route narrative (itinerary) 
      routeManager.setItineraryView(itinerary);
      routeManager.createRoute("San Francisco, CA", "Fremont, CA");
    }

    // set your map and enable default zoom controls 
    private void setupMapView() {
      this.map = (MapView) findViewById(R.id.mapQuestView);
      map.setBuiltInZoomControls(true);
    }

    // set up a MyLocationOverlay and execute the runnable once we have a location fix 
    private void setupMyLocation() {
      this.myLocationOverlay = new MyLocationOverlay(this, map);
      myLocationOverlay.enableMyLocation();
      myLocationOverlay.runOnFirstFix(new Runnable() {
        @Override
        public void run() {
//          GeoPoint currentLocation = myLocationOverlay.getMyLocation();
          map.getController().setZoom(16);
          //TODO de luat o locatie pe bune

          map.getOverlays().add(myLocationOverlay);
          map.getController().setCenter(new GeoPoint(44.419560,26.1266510));
//          myLocationOverlay.setFollowing(true);
        }
      });
    }

    // enable features of the overlay 
    @Override
    protected void onResume() {
      myLocationOverlay.enableMyLocation();
      myLocationOverlay.enableCompass();
      super.onResume();
    }

    // disable features of the overlay when in the background 
    @Override
    protected void onPause() {
      super.onPause();
      myLocationOverlay.disableCompass();
      myLocationOverlay.disableMyLocation();
    }

    @Override
    public boolean isRouteDisplayed() {
      return false;
    }
}