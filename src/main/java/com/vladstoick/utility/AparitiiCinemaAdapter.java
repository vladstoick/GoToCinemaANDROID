package com.vladstoick.utility;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstoick.fragments.FilmListFragment;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.AparitiiCinema;

import java.util.ArrayList;

public class AparitiiCinemaAdapter extends BaseAdapter {
    static class AparitiiCinemaRowHandler {
        TextView roTitle, enTitle, ora, cinema,distanta;
        ImageView imgView;
    }
    private final Context context;
    private ArrayList<AparitiiCinema> data = new ArrayList<AparitiiCinema>();

    public AparitiiCinemaAdapter(Context context,  ArrayList<AparitiiCinema> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public AparitiiCinema getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AparitiiCinemaRowHandler holder;
        final AparitiiCinema aparitie = data.get(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_row_view, parent, false);
            holder = new AparitiiCinemaRowHandler();
            holder.roTitle = (TextView) row.findViewById(R.id.rowRoTitle);
            holder.enTitle = (TextView) row.findViewById(R.id.rowEnTitle);
            holder.ora = (TextView) row.findViewById(R.id.rowOra);
            holder.cinema = (TextView) row.findViewById(R.id.rowCinema);
            holder.distanta = (TextView) row.findViewById(R.id.rowKM);
            holder.imgView = (ImageView) row.findViewById(R.id.cinemaPoster);
            ImageButton favorite = (ImageButton) row.findViewById(R.id.favorite);
            ImageButton like = (ImageButton) row.findViewById(R.id.like);
            ImageButton dislike = (ImageButton) row.findViewById(R.id.dislike);
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilmListFragment.clickedFavorite(position,aparitie);
                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilmListFragment.clickedLike(position, aparitie);
                }
            });
            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilmListFragment.clickedDislike(position,aparitie);
                }
            });
            row.setTag(holder);
        } else {
            holder = (AparitiiCinemaRowHandler) row.getTag();
        }

        holder.enTitle.setText(aparitie.enTitle);
        holder.roTitle.setText(aparitie.roTitle);
        if(aparitie.ora!=null)
        {
            holder.ora.setText(Utils.getStringFromDate(aparitie.ora));
            holder.cinema.setText(aparitie.cinemaName);
            holder.distanta.setText(aparitie.distanta);
        }
//        ImageLoader.getInstance().displayImage(aparitie.imgUrl, holder.imgView);
        Picasso.with(context).load(aparitie.imgUrl).into(holder.imgView);
        return row;
    }
}
