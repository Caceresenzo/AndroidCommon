package caceresenzo.android.libs.storage;

import android.os.Environment;

/**
 * Storage Utilities
 * 
 * Require these to be added in your AndroidManifest.xml:<br>
 * <code>&lt;uses-permission android:name=&quot;android.permission.READ_EXTERNAL_STORAGE&quot; /&gt; <br>
 * &lt;uses-permission android:name=&quot;android.permission.WRITE_EXTERNAL_STORAGE&quot; /&gt;</code>
 * 
 * @author Enzo CACERES
 */
public class StorageUtils {
	
	/* Constructor */
	private StorageUtils() {
		throw new IllegalStateException();
	}
	
	/**
	 * Checks if external storage is available to at least read
	 * 
	 * @return If you can read on the external storage
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}
	
	/**
	 * Check if external storage is available to write
	 * 
	 * @return If you can write on the external storage
	 */
	public static boolean isExternalStorageWritable() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}
	
}