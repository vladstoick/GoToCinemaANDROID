package com.vladstoick.gotocinema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class CinemaList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cinema_list);
		Intent intent = getIntent();
		Date dateToBeUsed = (Date) intent.getSerializableExtra("DATE");
		ArrayList<AparitiiCinema> aparitii = MainActivity.getAparitii(dateToBeUsed);
		Collections.sort(aparitii,new ArrayComparator());
		final ListView listview = (ListView) findViewById(R.id.listview);
		AparitiiCinemaAdapter adapter = new AparitiiCinemaAdapter(this, R.layout.list_row_view, aparitii);
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cinema_list, menu);
		return true;
	}

}
