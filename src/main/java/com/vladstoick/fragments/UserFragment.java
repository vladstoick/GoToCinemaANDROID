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
import com.vladstoick.arrayadapter.PostsAdapter;
import com.vladstoick.gotocinema.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.Post;
import com.vladstoick.utility.CinemaRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserFragment extends SherlockFragment {
    private final static String ARG_USERID="userid";
    private final static String ARG_WALLPOST="wallpost";
    private final static String ARG_USERIMGURL="userimgurl";
    private final static String ARG_USERNAME="username";
    private final static String ARG_USERDOB="userdob";
    private View view;
    private String userID = "",userImgUrl,userName,userDob;
    private ArrayList<Post> posts = new ArrayList<Post>();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("CALLED");


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("called");
        outState.putParcelableArrayList(ARG_WALLPOST, posts);
        outState.putString(ARG_USERIMGURL,userImgUrl);
        outState.putString(ARG_USERNAME,userName);
        outState.putString(ARG_USERDOB,userDob);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    private final void setUpFragment()
    {
        TextView dobTV = (TextView) view.findViewById(R.id.dob);
        TextView fullnameTV = ((TextView) view.findViewById(R.id.fullname));
        ImageView userProfileImgIV = (ImageView) view.findViewById(R.id.userProfileImg);
        ListView wallPostsLV = (ListView) view.findViewById(R.id.wallPostsListView);
        Picasso.with(getSherlockActivity().getApplicationContext()).load(userImgUrl).into(userProfileImgIV);
        fullnameTV.setText(userName);
        wallPostsLV.setAdapter(new PostsAdapter(getActivity(), R.layout.list_row_profile, posts));
        dobTV.setText("Data naşterii:" + userDob);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        final Button calculateBtn = (Button) view.findViewById(R.id.posteaza);
        final Button viewFavorites = (Button) view.findViewById(R.id.viewFavorites);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                mListener.showPostFragment(userID);
            }
        });
        viewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showFavorites(userID+"/favorites",true);
            }
        });
        if(posts.size()>0)
        {
            setUpFragment();
        }
        if(savedInstanceState!=null)
        {
            posts=savedInstanceState.getParcelableArrayList(ARG_WALLPOST);
            userImgUrl = savedInstanceState.getString(ARG_USERIMGURL);
            userName = savedInstanceState.getString(ARG_USERNAME);
            userDob = savedInstanceState.getString(userDob);
            setUpFragment();
        }
        else
        {
            userID = getArguments().getString(ARG_USERID);
            CinemaRestClient.get("user/" + userID + "/wall", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    JSONObject wall;
                    try {
                        posts.clear();
                        wall = new JSONObject(response);
                        userName = wall.getString("fullname");
                        userImgUrl = wall.getString("image");
                        userDob = wall.getString("DOB");

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
                        setUpFragment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
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
