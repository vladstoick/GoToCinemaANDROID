package com.vladstoick.gotocinema.slidingactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.vladstoick.fragments.SlidingMenuFragment;
import com.vladstoick.gotocinema.R;



public class BaseActivity extends SlidingFragmentActivity {
	
	private int mTitleRes;
	protected ListFragment mFrag;
	
	public BaseActivity(int titleRes){
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(mTitleRes);
		
		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		mFrag = new SlidingMenuFragment();
		ft.replace(R.id.menu_frame, mFrag);
		ft.commit();
		
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidth(15);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(200);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}