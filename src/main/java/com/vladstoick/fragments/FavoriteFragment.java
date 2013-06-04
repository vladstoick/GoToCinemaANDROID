package com.vladstoick.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.arrayadapter.FavoriteAdapter;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.FavoriteMovie;
import com.vladstoick.utility.CinemaRestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link FavoriteFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class FavoriteFragment extends SherlockFragment {
	View view;
	private static final String ARG_URL = "urlparam";
	private String url;

	private OnFragmentInteractionListener mListener;

	// TODO: Rename and change types and number of parameters
//	public static FavoriteFragment newInstance(String param1, String param2) {
	public static FavoriteFragment newInstance(String url){
		FavoriteFragment fragment = new FavoriteFragment();
		Bundle arg = new Bundle();
		arg.putString(ARG_URL, url);
//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(arg);
		return fragment;
	}

	public FavoriteFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			url = getArguments().getString(ARG_URL);
//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view =  inflater.inflate(R.layout.fragment_favorite, container, false);
		final GridView mGV = (GridView) view.findViewById(R.id.gridview);
        System.out.println(url);
        CinemaRestClient.get("user/"+url,null,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response)
            {
                ArrayList<FavoriteMovie> data = new ArrayList<FavoriteMovie>();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println(response);
                    System.out.println(jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object= jsonArray.getJSONObject(i);
                        data.add(i, new FavoriteMovie(object.getString("image"), object.getString("name")));
                        System.out.println(data.size());
                    }
                }
                catch (Exception e) {
                        e.printStackTrace();
                }
                mGV.setAdapter(new FavoriteAdapter(getActivity(),data));
            }

        });

		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		try {
//			mListener = (OnFragmentInteractionListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
