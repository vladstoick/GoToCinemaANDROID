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
import com.vladstoick.gotocinema.objects.Post;

import java.util.ArrayList;

public class PostsAdapter extends ArrayAdapter<Post> {
    static class PostRowHandler {
        TextView poster, title;
        ImageView profilePosterIV;
    }
    private final Context context;
    private final int layoutResourceId;

    private ArrayList<Post> data = new ArrayList<Post>();

    public PostsAdapter(Context context, int textViewResourceId, ArrayList<Post> data) {
        super(context, textViewResourceId, data);
        System.out.println("datasize"+data.size());
        this.layoutResourceId = textViewResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PostRowHandler holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PostRowHandler();
            holder.poster = (TextView) row.findViewById(R.id.poster);
            holder.title = (TextView) row.findViewById(R.id.title);
            holder.profilePosterIV = (ImageView) row.findViewById(R.id.posterIMG);
            row.setTag(holder);
        } else {
            holder = (PostRowHandler) row.getTag();
        }
        Post post = data.get(position);
        holder.poster.setText(post.posterFullname);
        holder.title.setText(post.postTitle);
        ImageLoader.getInstance().displayImage(post.posterImg, holder.profilePosterIV);
        return row;
    }
}
