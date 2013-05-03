package com.vladstoick.gotocinemaUtilityClasses;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {
	public static ArrayList<AparitiiCinema> parseMoviesList (String result)
	{
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
	public static ArrayList<AparitiiCinema> parseMoviesAndDistances (String result,ArrayList<AparitiiCinema> movies)
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
    		for(int i=0;i<movies.size();i++)
    		{
    			AparitiiCinema newObject = movies.get(i);
    			newObject.distanta=hashMap.get(newObject.cinemaName).dis;
    			newObject.durataDrum=hashMap.get(newObject.cinemaName).dur;
    			newObject.latCinema=hashMap.get(newObject.cinemaName).lat;
    			newObject.lonCinema=hashMap.get(newObject.cinemaName).lon;
    			movies.set(i,newObject);
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return movies;
	}
}
