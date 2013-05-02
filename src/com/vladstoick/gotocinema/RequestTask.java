package com.vladstoick.gotocinema;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;


class RequestTask extends AsyncTask<String, Void, String> {
	int type;
	FragmentManager fm;
	ProgressDialogFragment  progressDialog = new ProgressDialogFragment();
	public RequestTask(FragmentManager contextRecieved,int typeRecieved)
	{
		fm=contextRecieved;
		type=typeRecieved;
	}
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		
		progressDialog.show(fm, "LOADER");
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
    	progressDialog.dismiss();
        try {
        	if(type==1)
        	{	
 
        
        		MainActivity.setAllMovies(parse1(result));

        	}
        	if(type==2)
        	{
//        		System.out.println(result);
//        		Intent intent = new Intent(activity,CinemaList.class);
//        		Date dateToBeUsed = MainActivity.getDate();
//    			intent.putParcelableArrayListExtra("CINEMALISTDATA", Utils.getAparitii(parse2(MainActivity.getFilme(),result),dateToBeUsed) );
//    			MainActivity.pd.dismiss();
//    			activity.startActivity(intent);
        		
        	}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static class Distance{
    	String dur,dis,lon,lat;
    	public Distance(String dur,String dis,String lon,String lat)
    	{
    		this.dur=dur;
    		this.dis=dis;
    		this.lon=lon;
    		this.lat=lat;
    	}
    }
    public static ArrayList<AparitiiCinema>  parse2(ArrayList<AparitiiCinema> filme, String result)
    {
    	Hashtable<String, Distance> hashMap = new Hashtable<String, Distance>();
    	try{
    		JSONObject jObject = new JSONObject(result);
    		JSONArray jArray = jObject.getJSONArray("cinema");
    		
    		for(int i=0;i<jArray.length();i++)
    		{
    			JSONObject oneObject = jArray.getJSONObject(i);
    			String numeCinema = oneObject.getString("name");
    			hashMap.put(numeCinema,new Distance(oneObject.getString("min"), oneObject.getString("km"),oneObject.getString("lng_cinema"),oneObject.getString("lat_cinema")));
    		}
    		for(int i=0;i<filme.size();i++)
    		{
    			AparitiiCinema newObject = filme.get(i);
    			newObject.distanta=hashMap.get(newObject.cinemaName).dis;
    			newObject.durataDrum=hashMap.get(newObject.cinemaName).dur;
    			newObject.latCinema=hashMap.get(newObject.cinemaName).lat;
    			newObject.lonCinema=hashMap.get(newObject.cinemaName).lon;
    			filme.set(i,newObject);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return filme;
    	
    }
    public static ArrayList<AparitiiCinema> parse1(String result) {
    	ArrayList<AparitiiCinema> list = new ArrayList<AparitiiCinema>();
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
        }
        return list;
    }
    public static class ProgressDialogFragment extends DialogFragment {
    	 
    	public static ProgressDialogFragment newInstance() {
    		ProgressDialogFragment frag = new ProgressDialogFragment ();
    		return frag;
    	}
     
    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
     
    		final ProgressDialog dialog = new ProgressDialog(getActivity());
    		dialog.setMessage("Se încarcă");
    		dialog.setIndeterminate(true);
    		dialog.setCancelable(false);
     
    		// Disable the back button
    		OnKeyListener keyListener = new OnKeyListener() {
     
    			@Override
    			public boolean onKey(DialogInterface dialog, int keyCode,
    					KeyEvent event) {
    				
    				if( keyCode == KeyEvent.KEYCODE_BACK){					
    					return true;
    				}
    				return false;
    			}    
    		
    		};
    		dialog.setOnKeyListener(keyListener);
    		return dialog;
    	}
     
    }
}