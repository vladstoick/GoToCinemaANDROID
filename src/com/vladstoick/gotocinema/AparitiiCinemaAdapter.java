package com.vladstoick.gotocinema;

import java.util.ArrayList;
import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.Activity;
public class AparitiiCinemaAdapter extends ArrayAdapter<AparitiiCinema>{
	Context context; 
    int layoutResourceId;    
    ArrayList<AparitiiCinema> data = new ArrayList<AparitiiCinema>();
	public AparitiiCinemaAdapter(Context context, int textViewResourceId, ArrayList<AparitiiCinema> data) {
		super(context, textViewResourceId, data);
		this.layoutResourceId = textViewResourceId;
        this.context = context;
        this.data = data;
        System.out.println(data.size());
	}
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;     
        AparitiiCinemaRowHandler holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new AparitiiCinemaRowHandler();
            holder.roTitle = (TextView)row.findViewById(R.id.rowRoTitle);
            holder.enTitle = (TextView)row.findViewById(R.id.rowEnTitle);
            holder.ora = (TextView)row.findViewById(R.id.rowOra);
            holder.cinema = (TextView)row.findViewById(R.id.rowCinema);
            row.setTag(holder);
        }
        else
        {
            holder = (AparitiiCinemaRowHandler)row.getTag(); 
        }
        AparitiiCinema aparitie = data.get(position);
        holder.enTitle.setText(aparitie.enTitle);
        holder.roTitle.setText(aparitie.roTitle);
        System.out.println(holder.ora.getText().toString());
        holder.ora.setText(Utils.getStringFromDate(aparitie.ora));
        holder.cinema.setText(aparitie.cinemaName);
        return row;
    }
	static class AparitiiCinemaRowHandler
    {
        TextView roTitle, enTitle, ora, cinema;
    }
}
