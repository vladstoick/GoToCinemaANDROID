package com.vladstoick.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.vladstoick.gotocinema.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.Post;
import com.vladstoick.utility.CinemaRestClient;
import com.vladstoick.utility.PostsAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserFragment extends SherlockFragment {
    private final static String ARG_USERID="userid";
    private View view;
    private String userID = "";
    private final ArrayList<Post> posts = new ArrayList<Post>();
	public static UserFragment newInstance(String userID) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERID, userID);
        fragment.setArguments(args);
        return fragment;

	}
    private OnFragmentInteractionListener mListener;
	@SuppressWarnings("WeakerAccess")
    private UserFragment() {
		// Required empty public constructor
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        userID = getArguments().getString(ARG_USERID);
        CinemaRestClient.get("user/" + userID + "/wall", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                JSONObject wall;
                try {
                    wall = new JSONObject(response);
                    String fullname = wall.getString("fullname");
                    String userProfileImg = wall.getString("image");
                    String dob = wall.getString("DOB");
                    TextView dobTV = (TextView) view.findViewById(R.id.dob);
                    TextView fullnameTV = ((TextView) view.findViewById(R.id.fullname));
                    ImageView userProfileImgIV = (ImageView) view.findViewById(R.id.userProfileImg);
                    Picasso.with(getSherlockActivity().getApplicationContext()).load(userProfileImg).into(userProfileImgIV);
//                    ImageLoader.getInstance().displayImage(userProfileImg, userProfileImgIV);
                    fullnameTV.setText(fullname);
                    dobTV.setText("Data naşterii:" + dob);
                    JSONArray wallPosts = wall.getJSONArray("wall_posts");
                    for (int i = 0; i < wallPosts.length(); i++) {
                        JSONObject wallPost = wallPosts.getJSONObject(i);
                        String content = wallPost.getString("content");
                        String title = wallPost.getString("title");

                        JSONObject senderInfo = wallPost.getJSONObject("sender");
                        String posterId = senderInfo.getString("id");
                        String posterImage = senderInfo.getString("image");
                        String posterFullname = senderInfo.getString("fullname") + ":";
                        Post wallpost = new Post(posterId, posterImage, posterFullname, content, title);
                        posts.add(i, wallpost);
                    }
                    ListView wallPostsLV = (ListView) view.findViewById(R.id.wallPostsListView);
                    wallPostsLV.setAdapter(new PostsAdapter(getActivity(), R.layout.list_row_profile, posts));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        final Button calculateBtn = (Button) view.findViewById(R.id.posteaza);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                mListener.showPostFragment(userID);
            }
        });
        return view;
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


}
