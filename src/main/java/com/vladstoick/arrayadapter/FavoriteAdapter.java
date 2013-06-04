package com.vladstoick.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.FavoriteMovie;

import java.util.ArrayList;

/**
 * Created by vlad on 6/3/13.
 */
public class FavoriteAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<FavoriteMovie> mArrayList = new ArrayList<FavoriteMovie>();
    public FavoriteAdapter(Context c,ArrayList<FavoriteMovie> data) {
        mContext = c;
        mArrayList = data;
    }

    public int getCount() {
        return mArrayList.size();
    }

    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width= metrics.widthPixels;
        View view;
        FavoriteMovie movie = mArrayList.get(position);
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.grid_item_favorite, parent,false);
            int height = (int) ((width/2)*1.4);
            view.setLayoutParams(new GridView.LayoutParams( width / 2, height));
        }
        else
            view = convertView;

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Picasso.with(mContext).load(movie.imgUrl).resize(505,715).into(imageView);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(movie.enTitle);
        return view;
    }


}