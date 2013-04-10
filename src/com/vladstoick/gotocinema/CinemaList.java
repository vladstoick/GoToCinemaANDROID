package com.vladstoick.gotocinema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CinemaList extends Activity {

	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cinema_list);
		ArrayList<AparitiiCinema> aparitii = MainActivity.getAparitii();
		Collections.sort(aparitii,new ArrayComparator());
		ArrayList<String> roTitle = new ArrayList<String>();
		final ListView listview = (ListView) findViewById(R.id.listview);
		for(int i=0;i<aparitii.size();i++)
		{
			roTitle.add(aparitii.get(i).roTitle);
			System.out.println(roTitle.get(i));
		}
//		final StableArrayAdapter adapter = new StableArrayAdapter(this,
//		        android.R.layout.list_view_row, roTitle);
		ArrayAdapter <String> adapter = new ArrayAdapter<String> ( this, R.layout.list_row_view,R.id.rowRoTitle,roTitle);
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cinema_list, menu);
		return true;
	}

}
