package com.vladstoick.gotocinemaUtilityClasses;

import java.util.ArrayList;
import java.util.Hashtable;


import org.json.JSONArray;
import org.json.JSONObject;

import com.vladstoick.gotocinema.objects.AparitiiCinema;
import com.vladstoick.gotocinema.objects.Cinema;

public class JSONParser {
	public static Hashtable<String, Cinema>parseMoviesAndDistances (String result)
	{
		Hashtable<String, Cinema> hashMap = new Hashtable<String, Cinema>();
    	try{
   
    		JSONArray jArray = new JSONArray(result);
    		
    		for(int i=0;i<jArray.length();i++)
    		{
    			JSONObject oneObject = jArray.getJSONObject(i);
    			String numeCinema = oneObject.getString("name");
//    			hashMap.put(numeCinema,new Distance(oneObject.getString("duration"), oneObject.getString("distance"),oneObject.getString("lng_cinema"),oneObject.getString("lat_cinema")));
    			hashMap.put(numeCinema,new Cinema(oneObject.getString("distance"), oneObject.getString("duration"),oneObject.getString("lat"),oneObject.getString("lng"),oneObject.getString("name")));
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return hashMap;
	}
	public static ArrayList<AparitiiCinema> parseMoviesList (String result)
	{
		ArrayList<AparitiiCinema> list = new ArrayList<AparitiiCinema>();
        try {
        	list.clear();
            JSONArray jArray = new JSONArray(result);
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
                String imgUrl = oneObject.getString("image");
                AparitiiCinema aparitie = new AparitiiCinema(titluRo, titluEn, cinema, oraString, nota, regizor, actori, gen,imgUrl);
                list.add(aparitie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        }
        return list;
	}
}
