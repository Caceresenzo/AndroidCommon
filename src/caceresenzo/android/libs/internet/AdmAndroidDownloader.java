package caceresenzo.android.libs.internet;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import caceresenzo.android.libs.application.ApplicationUtils;
import caceresenzo.libs.filesystem.FilenameUtils;

public class AdmAndroidDownloader {
	
	/* Tag */
	public static final String TAG = AdmAndroidDownloader.class.getSimpleName();
	
	/* Constants */
	public static final String NORMAL_VERSION_PACKAGE = "com.dv.adm";
	public static final String PRO_VERSION_PACKAGE = "com.dv.adm.pay";
	
	public static final int NO_VERSION = -1;
	public static final int NORMAL_VERSION = 0;
	public static final int PRO_VERSION = 1;
	
	public static void askDownload(Context context, String url, boolean useAdmIfPossible) {
		askDownload(context, url, null, useAdmIfPossible);
	}
	
	public static void askDownload(Context context, String url, String downloadFileName, boolean useAdmIfPossible) {
		askDownload(context, url, downloadFileName, null, useAdmIfPossible);
	}
	
	public static void askDownload(Context context, String url, String downloadFileName, String downloadMessage, boolean useAdmIfPossible) {
		if (downloadFileName == null) {
			downloadFileName = FilenameUtils.getName(url);
		}
		
		boolean normal = ApplicationUtils.isApplicationInstalled(context, NORMAL_VERSION_PACKAGE);
		boolean pro = ApplicationUtils.isApplicationInstalled(context, PRO_VERSION_PACKAGE);
		
		if (useAdmIfPossible && (normal || pro)) {
			String admPackage = pro ? PRO_VERSION_PACKAGE : NORMAL_VERSION_PACKAGE;
			
			Intent intent = new Intent("android.intent.action.MAIN");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClassName(admPackage, admPackage + ".AEditor");
			
			intent.putExtra("android.intent.extra.TEXT", url);
			intent.putExtra("com.android.extra.filename", downloadFileName);
			
			try {
				context.startActivity(intent);
			} catch (ActivityNotFoundException exception) {
				Log.w(TAG, "ADM not found with package: " + admPackage, exception);
			}
		} else {
			AndroidDownloader.askDownload(context, Uri.parse(url), downloadFileName, downloadMessage);
		}
	}
	
	public static int isAdmInstalled(Context context) {
		if (ApplicationUtils.isApplicationInstalled(context, PRO_VERSION_PACKAGE)) {
			return PRO_VERSION;
		}
		
		if (ApplicationUtils.isApplicationInstalled(context, NORMAL_VERSION_PACKAGE)) {
			return NORMAL_VERSION;
		}
		
		return NO_VERSION;
	}
	
}