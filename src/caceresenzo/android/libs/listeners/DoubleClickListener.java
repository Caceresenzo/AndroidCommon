package caceresenzo.android.libs.listeners;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * Double-click listener to be used with {@link View#setOnClickListener(OnClickListener)}
 * 
 * @author Enzo CACERES
 */
public abstract class DoubleClickListener implements OnClickListener {
	
	/* Constants */
	/**
	 * Maxmimal time between a click to be considered as a double click<br>
	 * This value is in milliseconde
	 */
	private static final long DOUBLE_CLICK_TIME_DELTA = 300;
	
	/* Variables */
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
	
	/**
	 * Called when a single click as been detected
	 * 
	 * @param view
	 *            Original view
	 */
	public abstract void onSingleClick(View view);
	
	/**
	 * Called when a double click as been detected
	 * 
	 * @param view
	 */
	public abstract void onDoubleClick(View view);
	
}