package com.vladstoick.gotocinema.slidingactivity;
import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.slidingmenu.lib.SlidingMenu;
import com.vladstoick.fragments.CalculateMainMenuFragment;
import com.vladstoick.fragments.FilmDetailsFragment;
import com.vladstoick.fragments.FilmMasterFragment;
import com.vladstoick.fragments.SlidingMenuFragment;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.dialogfragments.TimePickerFragment;
import com.vladstoick.gotocinemaUtilityClasses.AparitiiCinema;
import com.vladstoick.gotocinemaUtilityClasses.CinemaRestClient;
import com.vladstoick.gotocinemaUtilityClasses.JSONParser;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener, LocationListener {
	static String TAGCALCULATE ="CalculateMainMenuFragment";
	static String TAGLOADING = "LoadingFragment";
	ProgressDialogFragment progressDialog= new ProgressDialogFragment();
	private Fragment mContent;
	Location currentLocation=null;
	String result;
	static ArrayList<AparitiiCinema> allMovies;
	public MainActivity(){
		super(R.string.app_name);
	}
	public static void setAllMovies(ArrayList<AparitiiCinema> filmeParsed)
	{
		allMovies=filmeParsed;
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new CalculateMainMenuFragment();
		
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent , TAGCALCULATE).commit();
		
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();
		
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setSlidingActionBarEnabled(true);
		currentLocation = findLocation();
		
		progressDialog.show(getSupportFragmentManager(),TAGLOADING);
		CinemaRestClient.get("date.json", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String resultString) {
            	allMovies=JSONParser.parseMoviesList(resultString);
                progressDialog.dismiss();
            }
        });
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment){
		mContent = fragment;
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.content_frame, fragment);
		ft.addToBackStack(null);
		ft.commit();
		getSlidingMenu().showContent();
	}
	
	//comunicare cu fragmente
	public void showTimePicker(View view)
	{
	    SherlockDialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	@Override
	public void onSettedATime(int hour, int minute) {
		if( getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)!=null)
			((CalculateMainMenuFragment) getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)).setTime(hour,minute);
		
	}
	@Override
	public Location getCurrentLocation(){
		return currentLocation;
	}
	@Override
	public void openNewCinemaList(ArrayList<AparitiiCinema> moviesToBeShown){
		SherlockListFragment newFragment = FilmMasterFragment.newInstance(moviesToBeShown);
		switchContent(newFragment);
	}
	@Override
	public ArrayList<AparitiiCinema> getAllMovies() {
		return allMovies;
	}
	@Override
	public void openFilmViewWithData(AparitiiCinema data)	{
		FilmDetailsFragment newFragment = FilmDetailsFragment.newInstance(data);
		switchContent(newFragment);
	}
	//comunicare cu fragmente ended
	public Location findLocation() {
		Location location = null;
	    try {
	    	
	        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	        // getting GPS status
	        boolean isGPSEnabled = locationManager
	                .isProviderEnabled(LocationManager.GPS_PROVIDER);

	        // getting network status
	        boolean isNetworkEnabled = locationManager
	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	        if (!isGPSEnabled && !isNetworkEnabled) {
	            // no network provider is enabled
	        } else {
	            if (isNetworkEnabled) {
	                locationManager.requestLocationUpdates(
	                        LocationManager.NETWORK_PROVIDER,
	                        20000,
	                        10, this);
	                Log.d("Network", "Network Enabled");
	                if (locationManager != null) {
	                    location = locationManager
	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                }
	            }
	            // if GPS Enabled get lat/long using GPS Services
	            if (isGPSEnabled) {
	                if (location == null) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.GPS_PROVIDER,
	                            20000,
	                            10, this);
	                    Log.d("GPS", "GPS Enabled");
	                    if (locationManager != null) {
	                        location = locationManager
	                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	               
	                    }
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return location;
	}
	@Override
	public void onLocationChanged(Location location) {
		
		currentLocation=location;
		final ProgressDialogFragment progressDialog2 = new ProgressDialogFragment();
		try {
			String link="getDistance.php?lat="+location.getLatitude()+"&lng="+location.getLongitude();
			progressDialog2.show(getSupportFragmentManager(),TAGLOADING);
			CinemaRestClient.get(link, null, new AsyncHttpResponseHandler() {
	            @Override
	            public void onSuccess(String resultString) {
	            	System.out.println("SUCCES");
	            	allMovies=JSONParser.parseMoviesAndDistances(resultString,allMovies);
	            	progressDialog2.dismiss();

	            }
	        });
		} catch (ClassCastException e) {
			System.out.println("in a fragment that doesn't need this");
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	

}
