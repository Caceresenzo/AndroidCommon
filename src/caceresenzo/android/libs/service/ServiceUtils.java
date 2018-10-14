package caceresenzo.android.libs.service;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Service Utilities
 * 
 * @author Enzo CACERES
 */
public class ServiceUtils {
	
	/**
	 * Check if your service is running by its class name
	 * 
	 * @param context
	 *            Application context
	 * @param serviceClass
	 *            Service clazz you want to check
	 * @return If the service is running or not
	 * @throws NullPointerException
	 *             If the {@code context} or the {@code clazz} is null
	 */
	public static boolean isServiceRunning(Context context, Class<?> clazz) {
		if (context == null || clazz == null) {
			throw new NullPointerException((context == null ? "Context" : "Service class") + " can't be null.");
		}
		
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (clazz.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		
		return false;
	}
	
}