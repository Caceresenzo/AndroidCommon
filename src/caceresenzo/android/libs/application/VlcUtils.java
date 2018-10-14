package caceresenzo.android.libs.application;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

public class VlcUtils {
	
	public static void openVlc(Activity activity, int requestId, Uri uri, long time, boolean defaultTime, String title) {
		Intent vlcIntent = new Intent(Intent.ACTION_VIEW);
		vlcIntent.setPackage("org.videolan.vlc");
		vlcIntent.setDataAndTypeAndNormalize(uri, "video/*");
		vlcIntent.putExtra("position", defaultTime ? 0 : (time == -1 ? 1 : time));
		vlcIntent.putExtra("title", title);
		vlcIntent.setComponent(new ComponentName("org.videolan.vlc", "org.videolan.vlc.gui.video.VideoPlayerActivity"));
		activity.startActivityForResult(vlcIntent, requestId);
	}
	
	
}