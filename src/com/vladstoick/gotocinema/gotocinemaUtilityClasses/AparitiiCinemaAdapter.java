package com.vladstoick.gotocinema.gotocinemaUtilityClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.objects.AparitiiCinema;

import java.util.ArrayList;

public class AparitiiCinemaAdapter extends ArrayAdapter<AparitiiCinema> {
    static class AparitiiCinemaRowHandler {
        TextView roTitle, enTitle, ora, cinema,distanta;
        ImageView imgView;
    }
    private final Context context;
    private final int layoutResourceId;

    private ArrayList<AparitiiCinema> data = new ArrayList<AparitiiCinema>();

    public AparitiiCinemaAdapter(Context context, int textViewResourceId, ArrayList<AparitiiCinema> data) {
        super(context, textViewResourceId, data);
        this.layoutResourceId = textViewResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AparitiiCinemaRowHandler holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AparitiiCinemaRowHandler();

            holder.roTitle = (TextView) row.findViewById(R.id.rowRoTitle);
            holder.enTitle = (TextView) row.findViewById(R.id.rowEnTitle);
            holder.ora = (TextView) row.findViewById(R.id.rowOra);
            holder.cinema = (TextView) row.findViewById(R.id.rowCinema);
            holder.distanta = (TextView) row.findViewById(R.id.rowKM);
            holder.imgView = (ImageView) row.findViewById(R.id.cinemaPoster);
            row.setTag(holder);
        } else {
            holder = (AparitiiCinemaRowHandler) row.getTag();
        }
        AparitiiCinema aparitie = data.get(position);
        holder.enTitle.setText(aparitie.enTitle);
        holder.roTitle.setText(aparitie.roTitle);
        holder.ora.setText(Utils.getStringFromDate(aparitie.ora));
        holder.cinema.setText(aparitie.cinemaName);
        holder.distanta.setText(aparitie.distanta);
        ImageLoader.getInstance().displayImage(aparitie.imgUrl, holder.imgView);
        return row;
    }
}
