package com.vladstoick.gotocinema.slidingactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.vladstoick.gotocinema.fragments.SlidingMenuFragment;
import com.vladstoick.gotocinema.R;



class BaseActivity extends SlidingFragmentActivity {
	
	private final int mTitleRes;

    BaseActivity(int titleRes){
		mTitleRes = R.string.app_name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(mTitleRes);
		
		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        SherlockFragment mFrag = new SlidingMenuFragment();
		ft.replace(R.id.menu_frame, mFrag);
		ft.commit();
		
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(15);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(200);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				try{
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				}
				catch(NullPointerException e)
				{
					e.printStackTrace();
				}
			}	
		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			toggle();
			return true;
		}
		return onOptionsItemSelected(item);
	}
}