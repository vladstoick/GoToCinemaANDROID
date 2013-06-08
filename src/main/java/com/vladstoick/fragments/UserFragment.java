package com.vladstoick.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.vladstoick.arrayadapter.PostsAdapter;
import com.vladstoick.dialogfragments.PostDialogFragment;
import com.vladstoick.gotocinema.MainActivity;
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
    public static final int DIALOG_FRAGMENT = 1;
    private View view,viewAbove;
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
    private UserFragment() {
		// Required empty public constructor
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
        TextView dobTV = (TextView) viewAbove.findViewById(R.id.dob);
        TextView fullnameTV = ((TextView) viewAbove.findViewById(R.id.fullname));
        ImageView userProfileImgIV = (ImageView) viewAbove.findViewById(R.id.userProfileImg);
        ListView wallPostsLV = (ListView) view.findViewById(R.id.wallPostsListView);

        Picasso.with(getSherlockActivity().getApplicationContext()).load(userImgUrl).into(userProfileImgIV);
        fullnameTV.setText(userName);
        wallPostsLV.setAdapter(new PostsAdapter(getActivity(), R.layout.list_row_profile, posts));
        dobTV.setText("Data naşterii:" + userDob);
    }
    public void showDialog(int type) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        switch (type) {

            case DIALOG_FRAGMENT:

                PostDialogFragment dialogFrag = PostDialogFragment.newInstance(123);
                dialogFrag.setTargetFragment(this, DIALOG_FRAGMENT);
                dialogFrag.show(getFragmentManager().beginTransaction(), "dialog");

                break;
        }
    }
    private void updatePosts()
    {

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
                        String content = "";
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        viewAbove = View.inflate(getActivity(), R.layout.fragment_user_above, null);
        ListView wallPostsLV = (ListView) view.findViewById(R.id.wallPostsListView);
        wallPostsLV.addHeaderView(viewAbove);
        final Button calculateBtn = (Button) viewAbove.findViewById(R.id.posteaza);
        final Button viewFavorites = (Button) viewAbove.findViewById(R.id.viewFavorites);
        userID = getArguments().getString(ARG_USERID);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               showDialog(DIALOG_FRAGMENT);
            }
        });
        viewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showFavorites(userID+"/favorites",true);
            }
        });
        if(posts.size()>0)
            setUpFragment();
        if(savedInstanceState!=null)
        {
            posts=savedInstanceState.getParcelableArrayList(ARG_WALLPOST);
            userImgUrl = savedInstanceState.getString(ARG_USERIMGURL);
            userName = savedInstanceState.getString(ARG_USERNAME);
            userDob = savedInstanceState.getString(userDob);
            setUpFragment();
        }
        else
            updatePosts();
        return view;
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DIALOG_FRAGMENT:

                if (resultCode == Activity.RESULT_OK) {

                    System.out.println(MainActivity.userAPI);
                    String text = data.getStringExtra("TEXT");
                    RequestParams params = new RequestParams();
                    params.put("token", MainActivity.userAPI); params.put("title",text); params.put("content","");params.put("receiver_id",userID);
                    CinemaRestClient.post("/user/post",params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String response)
                        {
                            System.out.println(response);
                            updatePosts();
                        }
                    });
                } else if (resultCode == Activity.RESULT_CANCELED){
                    // After Cancel code.
                }

                break;
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
