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

import com.vladstoick.gotocinema.R;

/**
 * Created by vlad on 6/3/13.
 */
public class FavoriteAdapter extends BaseAdapter {
    private Context mContext;

    public FavoriteAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.grid_item_favorite, parent,false);
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width= metrics.widthPixels;
            view.setLayoutParams(new GridView.LayoutParams(width/2, width/2));
//            view.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
        else
            view = convertView;
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("HELLO WORLD");
//        ImageView imageView;
//        if (convertView == null) {  // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//
//            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
//            int width= metrics.widthPixels;
//            imageView.setLayoutParams(new GridView.LayoutParams(width/2, width/2));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);
        return view;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}