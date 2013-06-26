package com.vladstoick.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vladstoick.arrayadapter.AparitiiCinemaAdapter;
import com.vladstoick.gotocinema.MainActivity;
import com.vladstoick.gotocinema.OnFragmentInteractionListener;
import com.vladstoick.gotocinema.R;
import com.vladstoick.objects.AparitiiCinema;
import com.vladstoick.utility.CinemaRestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilmListFragment extends SherlockFragment {
    private static final String ARG_MOVIES = "movies";
    private OnFragmentInteractionListener mListener;
    private ArrayList <AparitiiCinema> allMovies;
    private ArrayList <AparitiiCinema> moviesToBeShown;
    private SwipeListView slv;
    private int sortingTypeUsed = 1;
    private AparitiiCinemaAdapter adapter;
    private static Context mContext;
	private class ArrayComparatorByDistance implements Comparator<AparitiiCinema> {
        @Override
        public int compare(AparitiiCinema o1, AparitiiCinema o2) {
        	double km1 = Double.parseDouble(o1.distanta.substring(0, o1.distanta.length()-2));
        	double km2 = Double.parseDouble(o2.distanta.substring(0, o2.distanta.length()-2));
            return Double.compare(km1, km2) ;
        }
    }
	private class ArrayComparatorByName implements Comparator<AparitiiCinema> {
    	@Override
    	public int compare(AparitiiCinema o1, AparitiiCinema o2)
    	{
    		return (o1.enTitle).compareTo(o2.enTitle);
    	}
    }
	private class ArrayComparatorByTime implements Comparator<AparitiiCinema> {
        @Override
        public int compare(AparitiiCinema o1, AparitiiCinema o2) {
            return (o1.ora).compareTo(o2.ora);
        }
    }
	public static FilmListFragment newInstance(ArrayList<AparitiiCinema> param2) {
		FilmListFragment fragment = new FilmListFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList(ARG_MOVIES, param2);
		fragment.setArguments(args);
		return fragment;
	}

	private FilmListFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_film_list, container, false);
        if (getArguments() != null) {
            allMovies = getArguments().getParcelableArrayList(ARG_MOVIES);
            moviesToBeShown = new ArrayList<AparitiiCinema>(allMovies);
            Log.w("lungime",moviesToBeShown.size()+"lungime");
            Collections.sort(moviesToBeShown, new ArrayComparatorByTime());
            adapter = new AparitiiCinemaAdapter(getActivity(), moviesToBeShown);
            slv = (SwipeListView) view.findViewById(R.id.swipelistview);
            slv.setAdapter(adapter);
            slv.setSwipeListViewListener(new BaseSwipeListViewListener() {
                @Override
                public void onClickFrontView(int position) {
                    mListener.openFilmViewWithData(moviesToBeShown.get(position));
                }
            });
        }
        mContext=getActivity();
        final EditText searchInput = (EditText) view.findViewById(R.id.searchInput);
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager inputManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, 0);
                return false;
            }
        });
        searchInput.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String text = searchInput.getText().toString();
                System.out.println(text);
                moviesToBeShown.clear();
                for(int i=0;i<allMovies.size();i++)
                    if(allMovies.get(i).enTitle.toLowerCase().startsWith(text.toLowerCase()) || allMovies.get(i).roTitle.toLowerCase().startsWith(text.toLowerCase()))
                        moviesToBeShown.add(allMovies.get(i));
                switch (sortingTypeUsed)
                {
                    case 1 :sortByTime();
                    case 2 :sortByDistance();
                    case 3: sortByTime();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        return view;
    }

    public static void clickedFavorite(int position,AparitiiCinema data)
    {
        String url = "user/" + MainActivity.userID + "/favorites/?movie_id=" + data.id + "&token=" + MainActivity.userAPI;
        System.out.println(url);
        CinemaRestClient.post(url, null , new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String result)
            {
                Toast.makeText(mContext, "Adăugat cu succes", Toast.LENGTH_LONG).show();
                System.out.println(result);
            }
            @Override
            public void onFailure(Throwable e, String response) {
                e.printStackTrace();
            }
        });
    }
    public static void clickedLike(int position,AparitiiCinema data)
    {
        //TODO implement function
    }
    public static void clickedDislike(int position,AparitiiCinema data)
    {
        //TODO implement function
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getSherlockActivity().getSupportMenuInflater().inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort_by_time:
            {
                sortByTime();
                return true;
            }
            case R.id.sort_by_distance:{
                sortByDistance();
                return true;
            }
            case R.id.sort_by_name:{
                sortByName();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void sortByDistance()
    {
        Collections.sort(moviesToBeShown,new ArrayComparatorByDistance());
        adapter.notifyDataSetChanged();
        slv.setSelectionAfterHeaderView();
        sortingTypeUsed=1;
    }
    private void sortByName() {
        Collections.sort(moviesToBeShown, new ArrayComparatorByName());
        adapter.notifyDataSetChanged();
        slv.setSelectionAfterHeaderView();
        sortingTypeUsed=2;
    }


    private void sortByTime() {
        Collections.sort(moviesToBeShown, new ArrayComparatorByTime());
        slv.setSelectionAfterHeaderView();
        adapter.notifyDataSetChanged();
        sortingTypeUsed=3;
    }

    @Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


	

}
