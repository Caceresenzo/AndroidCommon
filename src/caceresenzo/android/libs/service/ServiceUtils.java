package caceresenzo.android.libs.service;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;

/**
 * Service Utilities
 * 
 * @author Enzo CACERES
 */
public class ServiceUtils {
	
	/* Constructor */
	private ServiceUtils() {
		throw new IllegalStateException();
	}
	
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
	@SuppressWarnings("deprecation")
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
	
	/**
	 * Create a notification channel
	 * 
	 * @param context
	 *            Application context
	 * @param channelId
	 *            Channel name/id
	 * @param nameRessourceId
	 *            Channel name (String ressource)
	 * @param descriptionRessourceId
	 *            Channel description (String ressource)
	 */
	public static void createNotificationChannel(Context context, String channelId, @StringRes int nameRessourceId, @StringRes int descriptionRessourceId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(channelId, context.getString(nameRessourceId), NotificationManager.IMPORTANCE_DEFAULT);
			channel.setDescription(context.getString(descriptionRessourceId));
			
			NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
	
}