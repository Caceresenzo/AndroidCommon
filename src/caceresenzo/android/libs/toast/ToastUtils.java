package caceresenzo.android.libs.toast;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	public static void makeShort(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	public static void makeLong(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	
}