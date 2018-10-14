package caceresenzo.android.libs.storage;

import android.os.Environment;

/**
 * 
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *
 */
public class StorageUtils {
	
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
	
	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}
	
}
