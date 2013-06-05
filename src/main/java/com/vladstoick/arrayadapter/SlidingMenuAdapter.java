package com.vladstoick.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vladstoick.gotocinema.R;

/**
 * Created by vlad on 6/5/13.
 */
public class SlidingMenuAdapter extends BaseAdapter{
    private final Integer[] thumbIds;
    private final String[] list_contents;
    private Context context;
    private TextView mTV;
    private ImageView mIV;
    public SlidingMenuAdapter(Context context,Integer[] thumbIds, String[] list_contents) {
        this.thumbIds = thumbIds;
        this.list_contents = list_contents;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list_contents.length;
    }

    @Override
    public Object getItem(int position) {
        return list_contents[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.sliding_row_view, parent, false);
            mTV = (TextView) row.findViewById(R.id.slidingTitle);
            mIV = (ImageView) row.findViewById(R.id.slidingImg);
        }
        mTV.setText(list_contents[position]);
        mIV.setImageResource(thumbIds[position]);
        return row;
    }
}
