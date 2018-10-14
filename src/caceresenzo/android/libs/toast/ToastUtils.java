package caceresenzo.android.libs.toast;

import android.content.Context;
import android.widget.Toast;
import caceresenzo.libs.exception.UtilityClassCantBeInstanced;

/**
 * {@link Toast} Utilities
 * 
 * @author Enzo CACERES
 */
public class ToastUtils {
	
	/* Constructor */
	private ToastUtils() {
		throw new UtilityClassCantBeInstanced();
	}
	
	/**
	 * Quicly display a short {@link Toast}
	 * 
	 * @param context
	 *            Application context
	 * @param text
	 *            Text to display
	 * @return {@link Toast} instane
	 */
	public static Toast makeShort(Context context, String text) {
		return make(context, text, Toast.LENGTH_SHORT);
	}
	
	/**
	 * Quicly display a long {@link Toast}
	 * 
	 * @param context
	 *            Application context
	 * @param text
	 *            Text to display
	 * @return {@link Toast} instane
	 */
	public static Toast makeLong(Context context, String text) {
		return make(context, text, Toast.LENGTH_LONG);
	}
	
	/**
	 * Create and directly display a {@link Toast}
	 * 
	 * @param context
	 *            Application context
	 * @param text
	 *            Text to display
	 * @param length
	 *            {@link Toast}'s length
	 * @return {@link Toast} instane
	 */
	private static Toast make(Context context, String text, int length) {
		Toast toast = Toast.makeText(context, text, length);
		
		toast.show();
		
		return toast;
	}
	
}