package com.vladstoick.gotocinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FilmView extends Activity {

    AparitiiCinema currentInfo; 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_view);
        Intent intent = getIntent();
        int pozitie = (Integer) intent.getSerializableExtra("POZ");
        currentInfo = CinemaList.getDataForPosition(pozitie);
        setTitle(currentInfo.enTitle);
    }
}