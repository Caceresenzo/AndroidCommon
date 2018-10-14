package caceresenzo.android.libs.intent;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

	public static void installApplication(Activity activity, String path, int requestId) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		activity.startActivityForResult(intent, requestId);
	}
	
	public static void shareText(Context context, String text, String information) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, information);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(intent, information));
	}
	
	public static void shareUrl(Context context, String text, String information) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, information);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(intent, information));
	}
	
	
}