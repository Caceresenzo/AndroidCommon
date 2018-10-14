package caceresenzo.android.libs.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class ActivityUtils {
	
	public static void openUrl(Activity activity, String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		activity.startActivity(intent);
	}
	
}