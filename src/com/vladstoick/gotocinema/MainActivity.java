package com.vladstoick.gotocinema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends FragmentActivity {
    static ArrayList<AparitiiCinema> list = new ArrayList<AparitiiCinema>();
    static TextView currentHour;
    static ProgressDialog pd=null;
    static int hourUsed = 15;
    static int minuteUsed = 15;
    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            Bundle b = m.getData();
            hourUsed = b.getInt("set_hour");
            minuteUsed = b.getInt("set_minute");
            updateHour();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        currentHour = (TextView) findViewById(R.id.hourUsed);
        hourUsed = c.get(Calendar.HOUR_OF_DAY);
        minuteUsed = c.get(Calendar.MINUTE);
        updateHour();

    }

    private static void updateHour() {
        String deAfisat = Utils.getStringFromDate(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed));
        currentHour.setText("Ora folosita " + deAfisat);
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

    public static ArrayList<AparitiiCinema> fill(String result) {
        try {
        	list.clear();
            JSONObject jObject = new JSONObject(result);
            JSONArray jArray = jObject.getJSONArray("movies");
            for (int i = 0; i < jArray.length(); i++) {

                JSONObject oneObject = jArray.getJSONObject(i);
                String titluRo = oneObject.getString("titluRo");
                String titluEn = oneObject.getString("titluEn");
                String cinema = oneObject.getString("cinema");
                String oraString = oneObject.getString("ora");
                String nota =oneObject.getString("nota");
                String gen = oneObject.getString("gen");
                String actori = oneObject.getString("actori");
                String regizor = oneObject.getString("regizor");
                AparitiiCinema aparitie = new AparitiiCinema(titluRo, titluEn, cinema, oraString, nota, regizor, actori, gen);
                list.add(aparitie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        	pd.dismiss();
        }
        return list;
    }

    public void calculateCinema(View view) {
    	pd = ProgressDialog.show(this, "Preluare date", "Vă rugăm aşteptaţi", true);
    	RequestTask newV = new RequestTask(1,MainActivity.this);
    	newV.execute("http://parsercinema.eu01.aws.af.cm/date.json");
//    	RequestTask newV = new RequestTask(1,MainActivity.this);
//        newV.execute("http://parsercinema.eu01.aws.af.cm/date.json");
//        Intent intent = new Intent(this, CinemaList.class);
//        intent.putParcelableArrayListExtra("CINEMALISTDATA", getAparitii(Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed)));
//        startActivity(intent);
        
    }

    public static ArrayList<AparitiiCinema> getAparitii(ArrayList<AparitiiCinema> list) {
    	Date dateToBeUsed = Utils.getDateFromHourAndMinuteInts(hourUsed, minuteUsed);
        ArrayList<AparitiiCinema> listToBeReturned = new ArrayList<AparitiiCinema>();
        for (int i = 0; i < list.size(); i++) {
            if (dateToBeUsed.getTime() - list.get(i).ora.getTime() < 0)
                listToBeReturned.add(list.get(i));
        }
        return listToBeReturned;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_enterSettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
