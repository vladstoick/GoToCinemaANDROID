package com.vladstoick.gotocinemaUtilityClasses;

import java.util.ArrayList;

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
	public static ArrayList<AparitiiCinema> parseMoviesAndDistances (String result,ArrayList<AparitiiCinema> movies)
	{
		return null;
	}
}
