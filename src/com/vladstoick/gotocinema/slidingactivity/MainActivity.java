package com.vladstoick.gotocinema.slidingactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.slidingmenu.lib.SlidingMenu;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.dialogfragments.ProgressDialogFragment;
import com.vladstoick.gotocinema.dialogfragments.TimePickerFragment;
import com.vladstoick.gotocinema.fragments.*;
import com.vladstoick.gotocinema.gotocinemaUtilityClasses.CinemaRestClient;
import com.vladstoick.gotocinema.gotocinemaUtilityClasses.JSONParser;
import com.vladstoick.gotocinema.objects.AparitiiCinema;
import com.vladstoick.gotocinema.objects.Cinema;

import java.util.ArrayList;
import java.util.Hashtable;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {
    private static final String TAGCALCULATE ="CalculateMainMenuFragment";
    private static final String TAGLOADING = "LoadingFragment";
    private String userID;
    static ImageLoader imageLoader=ImageLoader.getInstance();
    private final ProgressDialogFragment progressDialog= new ProgressDialogFragment();
    private Fragment mContent;
    private Location currentLocation=null;
    String result;
    private static Hashtable<String, Cinema> cinemas = new Hashtable<String, Cinema>();
    private static ArrayList<AparitiiCinema> allMovies= new ArrayList<AparitiiCinema>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        SharedPreferences settings =  this.getSharedPreferences("appPref",Context.MODE_PRIVATE);
        String userAPI = settings.getString("api_acces", "0");
        userID = settings.getString("user_id","0");
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory()
                .cacheOnDisc()
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

        if (savedInstanceState != null)
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        if (mContent == null)
            mContent = new CalculateMainMenuFragment();

        setContentView(R.layout.content_frame);
        getSupportFragmentManager()		.beginTransaction()	.replace(R.id.content_frame, mContent , TAGCALCULATE).commit();
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager()		.beginTransaction()		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();

        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        setSlidingActionBarEnabled(true);
        currentLocation = findLocation();

        progressDialog.show(getSupportFragmentManager(),TAGLOADING);
        CinemaRestClient.get("/movies", null, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(Throwable error,
                                  String content)
            {
                error.printStackTrace();
            }
            @Override
            public void onSuccess(String resultString) {
                allMovies=JSONParser.parseMoviesList(resultString);
                if(cinemas.size()>10)
                {
                    allMovies=AparitiiCinema.merge(allMovies,cinemas);
                }
                progressDialog.dismiss();
            }
        });
    }
    public static void setAllMovies(ArrayList<AparitiiCinema> filmeParsed)
    {
        allMovies=filmeParsed;
    }
    public MainActivity(){
        super(R.string.app_name);
    }
    @Override
    public void onLocationChanged(Location location) {

        currentLocation=location;
        final ProgressDialogFragment progressDialog2 = new ProgressDialogFragment();
        try {
            String link="/distance/googleMaps?lat="+location.getLatitude()+"&lng="+location.getLongitude();
            progressDialog2.show(getSupportFragmentManager(),TAGLOADING);
            CinemaRestClient.get(link, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String resultString) {
                    cinemas=JSONParser.parseMoviesAndDistances(resultString);
                    if(allMovies.size()>10)
                    {
                        allMovies=AparitiiCinema.merge(allMovies,cinemas);
                    }

                    progressDialog2.dismiss();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onSettedATime(int hour, int minute) {
        if( getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)!=null)
            ((CalculateMainMenuFragment) getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)).setTime(hour,minute);

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    @Override
    public void openFilmViewWithData(AparitiiCinema data)	{
        FilmDetailsFragment newFragment = FilmDetailsFragment.newInstance(data);
        switchContent(newFragment,true);
    }
    @Override
    public void openNewCinemaList(ArrayList<AparitiiCinema> moviesToBeShown){
        SherlockListFragment newFragment = FilmListFragment.newInstance(moviesToBeShown);
        switchContent(newFragment,true);
    }
    @Override
    public void showCalculateFragment()
    {
        CalculateMainMenuFragment newFragment = new CalculateMainMenuFragment();
        switchContent(newFragment, false);
    }
    @Override
    public void showProfileWithId(String id) {
        UserFragment newFragment = UserFragment.newInstance(id);
        switchContent(newFragment,true);
    }
    @Override
    public void showOwnProfile()
    {

        UserFragment newFragment = UserFragment.newInstance(userID);
        switchContent(newFragment,false);
    }

    @Override
    public void showSearchFragment(){
        SearchFragment newFragment = new SearchFragment();
        switchContent(newFragment,false);
    }

    //comunicare cu fragmente
    public void showTimePicker(View view)
    {
        SherlockDialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    void switchContent(Fragment fragment, boolean addToBack){
        mContent = fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        if(addToBack)
            ft.addToBackStack(null);
        ft.commit();
        getSlidingMenu().showContent();
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