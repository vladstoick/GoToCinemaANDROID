package com.vladstoick.gotocinema;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.dialogfragments.TimePickerFragment;
import com.vladstoick.fragments.*;
import com.vladstoick.dialogfragments.ProgressDialogFragment;
import com.vladstoick.objects.AparitiiCinema;
import com.vladstoick.utility.CinemaRestClient;
import com.vladstoick.utility.JSONParser;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {

    public static String userID;
    private final ProgressDialogFragment progressDialog= new ProgressDialogFragment();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        SharedPreferences settings =  this.getSharedPreferences("appPref",Context.MODE_PRIVATE);
        String userAPI = settings.getString("api_acces", "0");
        userID = settings.getString("user_id","0");
        currentLocation = findLocation();
        progressDialog.show(getSupportFragmentManager(),TAGLOADING);
        CinemaRestClient.get("/movies", null, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(Throwable error, String content) {
                error.printStackTrace();
            }

            @Override
            public void onSuccess(String resultString) {
                allMovies = JSONParser.parseMoviesList(resultString);
                if (cinemas.size() > 10) {
                    allMovies = AparitiiCinema.merge(allMovies, cinemas);
                }
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onSettedATime(int hour, int minute) {
        if( getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)!=null)
            ((CalculateMainMenuFragment) getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)).setTime(hour,minute);

    }
    @Override
    public void openFilmViewWithData(AparitiiCinema data)	{
        switchContent(FilmDetailsFragment.newInstance(data),true);
    }
    @Override
    public void openNewCinemaList(ArrayList<AparitiiCinema> moviesToBeShown){
        switchContent(FilmListFragment.newInstance(moviesToBeShown),true);
    }
    @Override
    public void showCalculateFragment(){
        switchContent(new CalculateMainMenuFragment(), false);
    }
    @Override
    public void showProfileWithId(String id) {
        switchContent(UserFragment.newInstance(id),true);
    }
    @Override
    public void showOwnProfile(){
        switchContent(UserFragment.newInstance(userID),false);
    }
    @Override
    public void showSearchFragment(){
        switchContent( new SearchFragment(),false);
    }
    @Override
    public void showFavorites() {
        switchContent(new FavoriteFragment(),false);
    }

    public void showTimePicker(View view){
        SherlockDialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    @Override
    public ArrayList<AparitiiCinema> getAllMovies() {
        return allMovies;
    }
    @Override
    public Location getCurrentLocation(){
        return currentLocation;
    }

}