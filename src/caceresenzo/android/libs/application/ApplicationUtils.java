package caceresenzo.android.libs.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

public class ApplicationUtils {
	
	public static boolean isAppInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getApplicationInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException ignored) {
			return false;
		}
	}
	
	public static Application getApplicationUsingReflection() throws Exception {
		return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
	}
	
	public static Application getApplicationGlobalUsingReflection() throws Exception {
		return (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null, (Object[]) null);
	}
	
}