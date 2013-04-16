package com.vladstoick.gotocinema;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


class RequestTask extends AsyncTask<String, Void, String> {
	int type;
	Context context;
	Activity activity;
	public RequestTask(int i,Activity activity)
	{
		this.type=i;
		this.activity=activity;
	}
    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        for (String url : urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            try {
                HttpResponse execute = client.execute(httpGet);
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
        	if(type==1)
        	{	
                
        		Intent intent = new Intent(activity,CinemaList.class);
        		intent.putParcelableArrayListExtra("CINEMALISTDATA", MainActivity.getAparitii(MainActivity.fill(result)));
        		activity.startActivity(intent);
//        		startActivity(new Intent(MainActivity.this, CinemaList.class));
//        		activity.StartActivit
        	}
        	if(type==2)
        		FilmView.showDirections(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}