package com.vladstoick.gotocinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class CinemaList extends FragmentActivity {
    static ArrayList<AparitiiCinema> aparitii = new ArrayList<AparitiiCinema>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Filmele la care poti ajunge!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list);
        Intent intent = getIntent();
        aparitii = intent.getParcelableArrayListExtra("CINEMALISTDATA");
        Collections.sort(aparitii, new ArrayComparator());
        final ListView listview = (ListView) findViewById(R.id.listview);
        AparitiiCinemaAdapter adapter = new AparitiiCinemaAdapter(this, R.layout.list_row_view, aparitii);
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

}
