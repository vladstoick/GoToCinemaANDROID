package com.vladstoick.gotocinema;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import org.json.*;
import org.apache.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

import com.vladstoick.gotocinema.* ;
@SuppressWarnings("unused")


public class MainActivity extends Activity {
	static ArrayList<AparitiiCinema> list = new ArrayList<AparitiiCinema>();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println ( "vlad" ) ;
		String abc ;
		RequestTask newV = new RequestTask() ;
		
		newV.execute ("http://warm-eyrie-7268.herokuapp.com/date.json" ) ;
		//System.out.println ( abc ) ;
		//tryingOut ( ) ;
    }
    public static void fill(String result)
    {
    	try{
    		JSONObject jObject = new JSONObject(result);
    		JSONArray jArray = jObject.getJSONArray("movies");
    		for (int i=0; i < jArray.length(); i++)
    		{
    		    JSONObject oneObject = jArray.getJSONObject(i);
    		    // Pulling items from the array
    		    String titluRo = oneObject.getString("titluRo");
    		    String titluEn = oneObject.getString("titluEn");
    		    String cinema  = oneObject.getString("cinema");
    		    String ora     = oneObject.getString("ora");
    		    AparitiiCinema aparitie = new AparitiiCinema (titluRo,titluEn,cinema,ora);
    		    list.add(aparitie);
    		}
    		System.out.println("Ready");
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void calculateCinema(View view)
    {
    	Intent intent = new Intent(this, CinemaList.class);
        startActivity(intent);
    }
    public static ArrayList<AparitiiCinema> getAparitii()
    {
    	return list;
    }
}
