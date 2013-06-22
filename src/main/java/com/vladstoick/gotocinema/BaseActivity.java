package com.vladstoick.gotocinema;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.dialogfragments.ProgressDialogFragment;
import com.vladstoick.fragments.CalculateMainMenuFragment;
import com.vladstoick.objects.AparitiiCinema;
import com.vladstoick.objects.Cinema;
import com.vladstoick.utility.CinemaRestClient;
import com.vladstoick.utility.JSONParser;

import java.util.ArrayList;
import java.util.Hashtable;


class BaseActivity extends SherlockFragmentActivity implements LocationListener {
    static final String TAGCALCULATE ="CalculateMainMenuFragment";
    static final String TAGLOADING = "LoadingFragment";
    private static final String ARGMOVIES="movies";
    public static String userID;
    public static String userAPI;
    static Hashtable<String, Cinema> cinemas = new Hashtable<String, Cinema>();
    static ArrayList<AparitiiCinema> allMovies= new ArrayList<AparitiiCinema>();
    private DrawerLayout mDrawerLayout;
    Location currentLocation;
    private Fragment mContent;
    private View mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("ALLMOVIES", allMovies);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        getSupportFragmentManager().putFragment(savedInstanceState,"content_frame",fragment);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            allMovies = savedInstanceState.getParcelableArrayList(ARGMOVIES);
        }
        SharedPreferences settings =  this.getSharedPreferences("appPref",Context.MODE_PRIVATE);
        userAPI = settings.getString("api_acces", "0");
        userID = settings.getString("user_id","0");
        if(userAPI.equals("0") || userID.equals("0"))
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.menu_frame);
        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "content_frame");
        if (mContent == null)
            mContent = new CalculateMainMenuFragment();
        mDrawerList = findViewById(R.id.left_drawer);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent, TAGCALCULATE).commit();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
            }
            public void onDrawerOpened(View drawerView) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                try{
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                catch(NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    void switchContent(Fragment fragment, boolean addToBack){
        mContent = fragment;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(addToBack)
        {
            ft.addToBackStack(null);
            ft.setTransition(0);
            ft.setCustomAnimations(android.R.anim.slide_in_left , android.R.anim.slide_out_right );
        }
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
            {
                if(mDrawerLayout.isDrawerOpen(mDrawerList))
                    mDrawerLayout.closeDrawer(mDrawerList);
                else
                    mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    Location findLocation() {
        Location location = null;
        try {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled)
                Toast.makeText(getApplicationContext(), "NO LOCATION METHOD", Toast.LENGTH_LONG).show();
            else
            if (isNetworkEnabled)
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000,10, this);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled)
                if (location == null)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 10, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onLocationChanged(Location location) {
        if(cinemas.size()==0)
        {
            currentLocation=location;
            final ProgressDialogFragment progressDialog2 = new ProgressDialogFragment();
            try {
                String link="/distance/googleMaps?lat="+location.getLatitude()+"&lng="+location.getLongitude();

                progressDialog2.show(getSupportFragmentManager(),TAGLOADING);
                CinemaRestClient.get(link, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String resultString) {
                        cinemas = JSONParser.parseMoviesAndDistances(resultString);
                        if (allMovies.size() > 10) {
                            allMovies = AparitiiCinema.merge(allMovies, cinemas);
                        }
                        progressDialog2.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}