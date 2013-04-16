package com.vladstoick.gotocinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CinemaList extends FragmentActivity {
	AparitiiCinemaAdapter adapter = null;
	ListView listview = null;
	static ArrayList<AparitiiCinema> aparitii = new ArrayList<AparitiiCinema>();
    public class ArrayComparatorByTime implements Comparator<AparitiiCinema> {
        @Override
        public int compare(AparitiiCinema o1, AparitiiCinema o2) {
            return (o1.ora).compareTo(o2.ora);
        }
    }
    public class ArrayComparatorByName implements Comparator<AparitiiCinema> {
    	@Override
    	public int compare(AparitiiCinema o1, AparitiiCinema o2)
    	{
    		return (o1.enTitle).compareTo(o2.enTitle);
    	}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list);
        Intent intent = getIntent();
        aparitii = intent.getParcelableArrayListExtra("CINEMALISTDATA");
        System.out.println("lungime"+aparitii.size());
        Collections.sort(aparitii, new ArrayComparatorByTime());
        listview =  (ListView) findViewById(R.id.listview);
        adapter = new AparitiiCinemaAdapter(this, R.layout.list_row_view, aparitii);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(CinemaList.this, FilmView.class);
                intent.putExtra("INFO", aparitii.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cinema_list, menu);
        return true;
    }
    public void sortByName()
    {
    	 
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_sortByDistance:
                System.out.println("Sort by Distance");
                return true;
            case R.id.action_sortByName:
            {
            	Collections.sort(aparitii, new ArrayComparatorByName());
            	adapter.notifyDataSetChanged();
            	listview.setSelection(0);
            	return true;
            }
            case R.id.action_sortByTime:
            {
            	Collections.sort(aparitii, new ArrayComparatorByTime());
            	adapter.notifyDataSetChanged();
            	listview.setSelection(0);
            	return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
