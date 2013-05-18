package com.vladstoick.gotocinema.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.gotocinema.R;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class SearchFragment extends SherlockFragment {
	private View view=null;
	private EditText searchInput=null;
	private ListView searchResults;
	public SearchFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_search, container, false);
		searchInput = (EditText) view.findViewById(R.id.searchInput);
		searchResults = (ListView) view.findViewById(R.id.searchResults);
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
							for(int i=0;i<jResults.length();i++)
								results.add(jResults.getJSONObject(i).getString("nume")+" "+jResults.getJSONObject(i).getString("prenume"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
	                    updateSearchResults(results);
	                }
	            });
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
//		searchInput.setOnKeyListener(new OnKeyListener() {
//		    @Override
//		    public boolean onKey(View v, int keyCode, KeyEvent event) {
//		    	 if (event.getAction()!=KeyEvent.ACTION_DOWN)
//	                    return true;
//		        if (keyCode == KeyEvent.KEYCODE_ENTER) { 
//		            String text = searchInput.getText().toString();
//		            AsyncHttpClient client = new AsyncHttpClient();
//		            client.get("http://cinemadistance.eu01.aws.af.cm/user/search?q="+text, new AsyncHttpResponseHandler() {
//		                @Override
//		                public void onSuccess(String response) {
//		                	ArrayList<String> results=new ArrayList<String>();
//		    
//		                    System.out.println(response);
//		                    try {
//								JSONArray jResults = new JSONArray(response);
//								for(int i=0;i<jResults.length();i++)
//									results.add(jResults.getJSONObject(i).getString("username"));
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} 
//		                    updateSearchResults(results);
//		                }
//		            });
//		        }
//				return false;
//		    }
//		});
		return view;
	}
	void updateSearchResults(ArrayList<String> results)
	{
		searchResults.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, results));
	}

}
