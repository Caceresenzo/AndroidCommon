package caceresenzo.android.libs.internet;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import caceresenzo.android.libs.dialog.DialogUtils;
import caceresenzo.android.libs.intent.CommonIntentUtils;
import caceresenzo.libs.comparator.Version;
import caceresenzo.libs.network.Downloader;
import caceresenzo.libs.network.Downloader.OnDownloadProgress;

public class AndroidUpdater {
	
	public static void updateIfNeeded(final Activity activity, final String applicationName, Version actual, final Version target, final String url, final OnUpdateStateChange listener, final int requestId) {
		if (actual.equals(target)) {
			return;
		}
		
		Thread updater = new Thread(new Runnable() {
			@Override
			public void run() {
				// File directory = new File(Environment.getExternalStorageDirectory() + "/" + applicationName + "/update/");
				File directory = new File("/sdcard" + "/" + applicationName + "/update/");
				File file = new File(directory.getAbsolutePath() + "/release." + target.get().replace(".", "-").replace(" ", ".") + ".apk");
				try {
					directory.mkdirs();
					file.createNewFile();
					Downloader.downloadFile(file, url, new OnDownloadProgress() {
						@Override
						public void onProgress(int length) {
							if (listener != null) {
								listener.onProgress(length);
							}
						}
					});
				} catch (IOException exception) {
					if (listener != null) {
						listener.onFailed(exception);
					}
					return;
				}
				
				if (listener != null) {
					listener.onInstall();
				}
				CommonIntentUtils.installApplication(activity, file.getAbsolutePath(), requestId);
				if (listener != null) {
					listener.onFinish();
				}
			}
		}, "AndroidAppsUpdater");
		updater.start();
	}
	
	public static void updateIfNeeded(final Activity activity, final String applicationName, Version actual, final Version target, final String url, final OnUpdateStateChange listener, final int requestId, final String authority) {
		if (actual.equals(target)) {
			return;
		}
		
		Thread updater = new Thread(new Runnable() {
			@Override
			public void run() {
				// File directory = new File(Environment.getExternalStorageDirectory() + "/" + applicationName + "/update/");
				
				try {
					File directory = new File(Environment.getExternalStorageDirectory() + "/" + applicationName + "/update/");
					final File targetFile = new File(directory.getAbsolutePath() + "/release." + target.get().replace(".", "-") + ".apk");
					
					final File file = new File(FileProvider.getUriForFile(activity, authority, targetFile).getEncodedPath());
					
					Handler HANDLER = new Handler(Looper.getMainLooper());
					HANDLER.post(new Runnable() {
						
						@Override
						public void run() {
							DialogUtils.showDialog(activity, "uri", file.getAbsolutePath());
							DialogUtils.showDialog(activity, "uri", FileProvider.getUriForFile(activity, authority, targetFile).toString());
						}
					});
					
					boolean a = true;
					
					if (!a) {
						return;
					}
					
					file.getParentFile().mkdirs();
					file.createNewFile();
					Downloader.downloadFile(file, url, new OnDownloadProgress() {
						@Override
						public void onProgress(int length) {
							if (listener != null) {
								listener.onProgress(length);
							}
						}
					});
					
					if (listener != null) {
						listener.onInstall();
					}
					CommonIntentUtils.installApplication(activity, file.getAbsolutePath(), requestId);
					if (listener != null) {
						listener.onFinish();
					}
				} catch (IOException exception) {
					if (listener != null) {
						listener.onFailed(exception);
					}
					return;
				}
			}
		}, "AndroidAppsUpdater");
		updater.start();
	}
	
	public interface OnUpdateStateChange {
		void onFailed(Exception exception);
		
		void onProgress(int length);
		
		void onInstall();
		
		void onFinish();
	}
	
}