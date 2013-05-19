package com.vladstoick.gotocinema.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vladstoick.gotocinema.R;
import com.vladstoick.gotocinema.gotocinemaUtilityClasses.CinemaRestClient;
import com.vladstoick.gotocinema.gotocinemaUtilityClasses.PostsAdapter;
import com.vladstoick.gotocinema.objects.Post;
import com.vladstoick.gotocinema.slidingactivity.OnFragmentInteractionListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    private final static String ARG_USERID="userid";
    private View view;
    private String userID="0";
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
		if (getArguments() != null) {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        userID =getArguments().getString(ARG_USERID);
        CinemaRestClient.get("user/"+userID+"/wall",null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response) {
                JSONObject wall = null;
                try{
                    wall = new JSONObject(response);
                    String fullname = wall.getString("fullname");
                    String userProfileImg = wall.getString("image");
                    TextView fullnameTV=((TextView) view.findViewById(R.id.fullname));
                    ImageView userProfileImgIV = (ImageView) view.findViewById(R.id.userProfileImg);
                    ImageLoader.getInstance().displayImage(userProfileImg, userProfileImgIV);
                    fullnameTV.setText(fullname);
                    JSONArray wallPosts = wall.getJSONArray("wall_posts");
                    for(int i=0;i<wallPosts.length();i++)
                    {
                        JSONObject wallPost=wallPosts.getJSONObject(i);
                        String content = wallPost.getString("content");
                        String title = wallPost.getString("title");
                        JSONObject senderInfo = wallPost.getJSONObject("sender");
                        String posterId = senderInfo.getString("id");
                        String posterImage = senderInfo.getString("image");
                        String posterFullname = senderInfo.getString("fullname");
                        Post wallpost = new Post(posterId,posterImage,posterFullname,content,title);
                        posts.add(i,wallpost);
                    }
                    ListView wallPostsLV= (ListView) view.findViewById(R.id.wallPostsListView);
                    wallPostsLV.setAdapter( new PostsAdapter(getActivity(), R.layout.list_row_profile, posts));
            }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
//			mListener.onFragmentInteraction(uri);
		}
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
