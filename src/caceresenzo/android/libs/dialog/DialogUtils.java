package caceresenzo.android.libs.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

public class DialogUtils {
	
	public static void showDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) {
			builder.setTitle(title);
		}
		builder.setMessage(message).setCancelable(true).setPositiveButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	public static void showDialog(Handler handler, final Context context, final String title, final String message) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				showDialog(context, title, message);
			}
		});
	}
	
	public static void showDialog(Context context, String title, String message, String positiveText, DialogInterface.OnClickListener onPositive, String negativeText, DialogInterface.OnClickListener onNegative) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null) {
			builder.setTitle(title);
		}
		builder.setMessage(message).setCancelable(true).setPositiveButton(positiveText, onPositive).setNegativeButton(negativeText, onNegative);
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
	
	public static void showDialog(Handler handler, final Context context, final String title, final String message, final String positiveText, final DialogInterface.OnClickListener onPositive, final String negativeText, final DialogInterface.OnClickListener onNegative) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				showDialog(context, title, message, positiveText, onPositive, negativeText, onNegative);
			}
		});
	}
	
}