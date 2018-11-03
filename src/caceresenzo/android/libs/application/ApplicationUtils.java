package caceresenzo.android.libs.application;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import caceresenzo.libs.exception.UtilityClassCantBeInstanced;

/**
 * Application Utilities
 * 
 * @author Enzo CACERES
 */
public class ApplicationUtils {
	
	/* Constants */
	public static final String VLC_PACKAGE = "org.videolan.vlc";
	public static final ComponentName VLC_COMPONENT = new ComponentName(VLC_PACKAGE, "org.videolan.vlc.gui.video.VideoPlayerActivity");
	
	/**
	 * Use it with {@link #openVlc(Activity, int, Uri, long, String)}, this will start the video ignoring saved progress
	 */
	public static final int FROM_START = -1;
	
	/**
	 * Use it with {@link #openVlc(Activity, int, Uri, long, String)}, this will start the video and restore saved progress
	 */
	public static final int FROM_PROGRESS = -2;
	
	/* Constructor */
	private ApplicationUtils() {
		throw new UtilityClassCantBeInstanced();
	}
	
	/**
	 * Check if an application is installed by its package
	 * 
	 * @param context
	 *            Application context
	 * @param packageName
	 *            Target application package
	 * @return If the application is installed on the device
	 */
	public static boolean isApplicationInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException ignored) {
			return false;
		}
	}
	
	/**
	 * Start a vlc intent with settings
	 * 
	 * @param activity
	 *            Activity to lunch from
	 * @param requestId
	 *            Request id to request progress once the user has finish (onActivityResult)
	 * @param uri
	 *            Traget file uri (local or online)
	 * @param time
	 *            Time to seek through, can also use {@link #FROM_START} or {@link #FROM_PROGRESS}
	 * @param title
	 *            Video title to show
	 */
	public static void openVlc(Activity activity, int requestId, Uri uri, long time, String title) {
		Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
		
		vlcIntent.setPackage(VLC_PACKAGE);
		vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
		
		long position = time;
		if (time == FROM_START) {
			position = 1;
		} else if (time == FROM_PROGRESS) {
			position = 0;
		}
		
		vlcIntent.putExtra("position", position);
		vlcIntent.putExtra("title", title);
		vlcIntent.setComponent(VLC_COMPONENT);
		
		activity.startActivityForResult(vlcIntent, requestId);
	}
	
	/**
	 * Open the PlayStore for this application.<br>
	 * <br>
	 * See {@link ApplicationUtils#openStore(Context, String)} for more help.
	 * 
	 * @param context
	 *            Application context
	 */
	public static void openStore(Context context) {
		openStore(context, context.getPackageName());
	}
	
	/**
	 * Open the PlayStore with a specified target application package.<br>
	 * If the PlayStore is not installed or not available, this will the web version.
	 * 
	 * @param context
	 *            Application context
	 * @param applicationPackageName
	 *            Target application context
	 */
	public static void openStore(Context context, String applicationPackageName) {
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + applicationPackageName)));
		} catch (android.content.ActivityNotFoundException activityNotFoundException) {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + applicationPackageName)));
		}
	}
	
	/**
	 * Rarely working, get main Android application instance
	 * 
	 * @return {@link android.app.ActivityThread} application instance
	 * @throws Exception
	 */
	public static Application getApplicationUsingReflection() throws Exception {
		return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
	}
	
	/**
	 * Rarely working, get global Android application instance
	 * 
	 * @return {@link android.app.AppGlobals} application instance
	 * @throws Exception
	 */
	public static Application getApplicationGlobalUsingReflection() throws Exception {
		return (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null, (Object[]) null);
	}
	
}