package caceresenzo.android.libs.internet;

import java.net.URLDecoder;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import caceresenzo.android.libs.toast.ToastUtils;
import caceresenzo.libs.filesystem.FilenameUtils;

public class AndroidDownloader {
	
	public static void askDownload(Context context, Uri uri) {
		askDownload(context, uri, null);
	}
	
	public static void askDownload(Context context, Uri uri, String downloadFileName) {
		askDownload(context, uri, downloadFileName, null);
	}
	
	@SuppressWarnings("deprecation")
	public static void askDownload(Context context, Uri uri, String downloadFileName, String downloadMessage) {
		if (downloadMessage != null) {
			ToastUtils.makeLong(context, downloadMessage);
		}
		
		if (downloadFileName == null) {
			downloadFileName = FilenameUtils.getName(URLDecoder.decode(uri.getEncodedPath()));
		}
		
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadFileName);
		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE);
		downloadManager.enqueue(request);
	}
	
}