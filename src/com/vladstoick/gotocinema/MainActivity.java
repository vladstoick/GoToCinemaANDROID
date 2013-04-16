package com.vladstoick.gotocinema;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class MainActivity extends FragmentActivity implements LocationListener {
    static private TextView timeUsed,locationUsedText;
    static ProgressDialog pd=null;
    static private int hourUsed, minuteUsed;
    static boolean  isUsingLocation = true,settedALocation = false;
    private LocationManager locationManager;
    private String provider;
    static Location locationUsed;
    private static ArrayList<AparitiiCinema>filme = new ArrayList<AparitiiCinema>(); 
    public static ArrayList<AparitiiCinema> getFilme() {
		return filme;
	}
	public static void setFilme(ArrayList<AparitiiCinema> filmeToBeFilled) {
		filme = filmeToBeFilled;
	}
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        timeUsed = (TextView) findViewById(R.id.hourUsed);
        locationUsedText = (TextView) findViewById(R.id.locationUsed);
        hourUsed = c.get(Calendar.HOUR_OF_DAY);
        minuteUsed = c.get(Calendar.MINUTE);
        updateHour();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
          } else {
            Toast.makeText(this, "Trebuie să activaţi o metodă de localizare", Toast.LENGTH_LONG);
          }
        pd = ProgressDialog.show(this, "Preluare filme", "Vă rugăm aşteptaţi", true);
		RequestTask newV = new RequestTask(1,MainActivity.this);
		newV.execute("http://parsercinema.eu01.aws.af.cm/date.json");
        
    }
    //HOUR SETUP
    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            Bundle b = m.getData();
            hourUsed = b.getInt("set_hour");
            minuteUsed = b.getInt("set_minute");
            updateHour();
        }
    };
    private static void updateHour() {
        String deAfisat = Utils.getStringFromDate(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed));
        timeUsed.setText("Ora folosita " + deAfisat);
    }
    public void changeTime(View v) {
        Bundle b = new Bundle();
        b.putInt("set_hour", hourUsed);
        b.putInt("set_minute", minuteUsed);
        ChangeDialog timePicker = new ChangeDialog(mHandler);
        timePicker.setArguments(b);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(timePicker, "time_picker");
        ft.commit();
    }
    public static Date getDate()
    {
    	return Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed);
    }
    //BUTOTONS AND FUNCTIONALITY
    public void calculateCinema(View view) {
    	if(settedALocation==false)
    	{
    		Toast.makeText(this, "Aplicaţia încă te localizează", Toast.LENGTH_SHORT).show();
    	}
    	else{
    		pd = ProgressDialog.show(this, "Calcuare distanţă", "Vă rugăm aşteptaţi", true);
    		RequestTask newV = new RequestTask(2,MainActivity.this);
    		newV.execute("http://parsercinema.eu01.aws.af.cm/getDistance.php?lat=44.419560&lng=26.1266510");
    	}
       
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_enterSettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //LOCATION
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		System.out.println("location is"+location.getLongitude());
		if(MainActivity.isUsingLocation==true)
		{
			locationUsed=location;
			MainActivity.settedALocation=true;
			System.out.println(settedALocation);
			locationUsedText.setText("Poziţia folosită: locaţia ta");
		}
	}
	public static Location getLocation()
	{
		return locationUsed;
	}
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
}
