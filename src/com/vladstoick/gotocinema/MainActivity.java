package com.vladstoick.gotocinema;
import com.vladstoick.gotocinema.ChangeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import org.json.*;


public class MainActivity extends FragmentActivity {
	static ArrayList<AparitiiCinema> list = new ArrayList<AparitiiCinema>();
	static TextView currentHour;
	
	static int hourUsed = 15;
    static int minuteUsed = 15;
    static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message m){
             Bundle b = m.getData();
             hourUsed = b.getInt("set_hour");
             minuteUsed = b.getInt("set_minute");
             updateHour();
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RequestTask newV = new RequestTask() ;
		newV.execute ("http://warm-eyrie-7268.herokuapp.com/date.json" ) ;
		Calendar c = Calendar.getInstance();
		currentHour = (TextView) findViewById(R.id.hourUsed);
		hourUsed = c.get(Calendar.HOUR_OF_DAY);
		minuteUsed = c.get(Calendar.MINUTE);
		updateHour();
		
    }
	private static void updateHour()
	{
		String deAfisat = Utils.getStringFromDate(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed));
		currentHour.setText("Ora folosita "+deAfisat);
	}
    public void changeTime(View v)
    {
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
    
    public static void fill(String result)
    {
    	try{
    		
    		JSONObject jObject = new JSONObject(result);
    		JSONArray jArray = jObject.getJSONArray("movies");
     		for (int i=0; i < jArray.length(); i++)
    		{
     			
     			JSONObject oneObject = jArray.getJSONObject(i);
    		    String titluRo = oneObject.getString("titluRo");
    		    String titluEn = oneObject.getString("titluEn");
    		    String cinema  = oneObject.getString("cinema");
    		    String oraString= oneObject.getString("ora");
    	
    		    AparitiiCinema aparitie = new AparitiiCinema (titluRo,titluEn,cinema,oraString);
    		    list.add(aparitie);
    		}
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	public void calculateCinema(View view)
    {
    	Intent intent = new Intent(this, CinemaList.class); 
    	intent.putParcelableArrayListExtra("CINEMALISTDATA", getAparitii(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed)));
        startActivity(intent);
    }
    public static ArrayList<AparitiiCinema> getAparitii(Date dateToBeUsed)
    {
    	ArrayList <AparitiiCinema> listToBeReturned = new ArrayList<AparitiiCinema>();
    	for(int i=0;i<list.size();i++)
    	{
    		if(dateToBeUsed.getTime()-list.get(i).ora.getTime()<0)
    			listToBeReturned.add(list.get(i));
    	}
    	return listToBeReturned;
    }
}
