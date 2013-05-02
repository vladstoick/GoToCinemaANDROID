package com.vladstoick.gotocinema;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {
	static String TAGCALCULATE ="CalculateMainMenuFragment";
	private Fragment mContent;
	
	public MainActivity(){
		super(R.string.app_name);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new CalculateMainMenuFragment();
		
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent , TAGCALCULATE).commit();
		
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SlidingMenuFragment()).commit();
		
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setSlidingActionBarEnabled(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment){
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	public void showTimePicker(View view)
	{
	    SherlockDialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	@Override
	public void onSettedATime(int hour, int minute) {
		if( getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)!=null)
			((TimePickerFragment) getSupportFragmentManager().findFragmentByTag(TAGCALCULATE)).updateTime(hour,minute);
		
	}
	
	

}
