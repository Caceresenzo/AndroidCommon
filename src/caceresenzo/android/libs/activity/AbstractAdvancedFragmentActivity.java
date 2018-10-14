package caceresenzo.android.libs.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class AbstractAdvancedFragmentActivity extends FragmentActivity {
	
	private static final Handler HANDLER = new Handler();
	
	protected CharSequence applicationHeadingTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		applicationHeadingTitle = getTitle();
		initializeViews(null);
	}
	
	public abstract void initializeViews(View view);
	
	public void restoreActionBar() {
		HANDLER.post(new Runnable() {
			@Override
			public void run() {
				ActionBar actionBar = getActionBar();
				// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				actionBar.setDisplayShowTitleEnabled(true);
				actionBar.setTitle(applicationHeadingTitle);
			}
		});
	}
	
	public void setHeadingTitle(String title) {
		this.applicationHeadingTitle = getTitle() + " / " + title;
		restoreActionBar();
	}
	
	public void setHeadingTitle() {
		restoreActionBar();
	}
	
	public static Handler getHandler() {
		return HANDLER;
	}
}