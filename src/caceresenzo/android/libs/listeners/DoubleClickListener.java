package caceresenzo.android.libs.listeners;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class DoubleClickListener implements OnClickListener {
	
	private static final long DOUBLE_CLICK_TIME_DELTA = 300; // ms
	
	private long lastClickTime = 0;
	
	@Override
	public void onClick(View view) {
		long clickTime = System.currentTimeMillis();
		if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
			onDoubleClick(view);
			lastClickTime = 0;
		} else {
			onSingleClick(view);
		}
		lastClickTime = clickTime;
	}
	
	public abstract void onSingleClick(View view);
	
	public abstract void onDoubleClick(View view);
	
}