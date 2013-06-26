package com.vladstoick.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.gotocinema.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.R;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.widget.AdapterView.OnItemClickListener;


public class SearchFragment extends SherlockFragment {
    private EditText searchInput=null;
    private OnFragmentInteractionListener mListener;
    private final ArrayList<String> searchId=new ArrayList<String>();
	private ListView searchResults;
	public SearchFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
		searchInput = (EditText) view.findViewById(R.id.searchInput);
		searchResults = (ListView) view.findViewById(R.id.searchResults);
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager inputManager = (InputMethodManager)getSherlockActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, 0);
                return false;
            }
        });
		searchInput.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	String text = searchInput.getText().toString();
	            AsyncHttpClient client = new AsyncHttpClient();
	            client.get("http://cinemadistance.eu01.aws.af.cm/user/search?q="+text, new AsyncHttpResponseHandler() {
	                @Override
	                public void onSuccess(String response) {
	                	ArrayList<String> results=new ArrayList<String>();
	                    System.out.println(response);
	                    try {
							JSONArray jResults = new JSONArray(response);
                            searchId.clear();
							for(int i=0;i<jResults.length();i++)
                            {
								results.add(jResults.getJSONObject(i).getString("nume")+" "+jResults.getJSONObject(i).getString("prenume"));
                                searchId.add(jResults.getJSONObject(i).getString("id"));
                            }
						} catch (JSONException e) {

							e.printStackTrace();
						} 
	                    updateSearchResults(results);
	                }
	            });
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    });
        searchResults.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.showProfileWithId(searchId.get(position));
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
	void updateSearchResults(ArrayList<String> results)
	{
		searchResults.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, results));
	}

}
