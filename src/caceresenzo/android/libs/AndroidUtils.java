package caceresenzo.android.libs;

/**
 * Android Utilities
 * 
 * @author Enzo CACERES
 */
public class AndroidUtils {
	
	/* Constructor */
	private AndroidUtils() {
		throw new IllegalStateException();
	}
	
	/**
	 * @return If you have access to big notification, useful in music player for exemple
	 */
	public static boolean currentVersionSupportBigNotification() {
		return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN;
	}
	
	/**
	 * @return If you have access to control available on the lock screen, useful in music player for exemple
	 */
	public static boolean currentVersionSupportLockScreenControls() {
		return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
}