package com.vladstoick.gotocinema.gotocinemaUtilityClasses;

import com.vladstoick.gotocinema.objects.AparitiiCinema;
import com.vladstoick.gotocinema.objects.Cinema;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

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
            JSONArray moviesArray= new JSONArray(result);
            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject movie = moviesArray.getJSONObject(i);
                String actori = movie.getString("actori");
                String imgUrl = movie.getString("image");
                String nota =movie.getString("nota");
                String regizor = movie.getString("regizor");
                String titluEn = movie.getString("titluEn");
                String titluRo = movie.getString("titluRo");
                String gen = movie.getString("gen");
                JSONArray showtimesArray = movie.getJSONArray("showtimes");
                for(int showtimeI = 0; showtimeI < showtimesArray.length(); showtimeI++ )
                {
                    JSONObject showTime = showtimesArray.getJSONObject(showtimeI);
                    String cinema = showTime.getString("place");
                    String oraString = showTime.getString("hour");
                    AparitiiCinema aparitie = new AparitiiCinema(titluRo, titluEn, cinema, oraString, nota, regizor, actori, gen,imgUrl);
                    list.add(aparitie);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        }
        return list;
	}
}
