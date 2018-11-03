package caceresenzo.android.libs.menu;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import caceresenzo.libs.android.R;

public class MenuTintUtils {
	
	public static void tintAllIcons(Menu menu, final int color, int... ignoredItemIds) {
		for (int i = 0; i < menu.size(); ++i) {
			final MenuItem item = menu.getItem(i);
			
			boolean canTint = true;
			if (ignoredItemIds.length != 0) {
				for(int itemId : ignoredItemIds) {
					if (item.getItemId() == itemId) {
						canTint = false;
						break;
					}
				}
			}
			
			if (canTint) {
				tintMenuItemIcon(color, item);
				tintShareIconIfPresent(color, item);
			}
		}
	}
	
	public static void tintMenuItemIcon(int color, MenuItem item) {
		final Drawable drawable = item.getIcon();
		if (drawable != null) {
			final Drawable wrapped = DrawableCompat.wrap(drawable);
			drawable.mutate();
			DrawableCompat.setTint(wrapped, color);
			item.setIcon(drawable);
		}
	}
	
	private static void tintShareIconIfPresent(int color, MenuItem item) {
		if (item.getActionView() != null) {
			final View actionView = item.getActionView();
			final View expandActivitiesButton = actionView.findViewById(R.id.expand_activities_button);
			if (expandActivitiesButton != null) {
				final ImageView image = (ImageView) expandActivitiesButton.findViewById(R.id.image);
				if (image != null) {
					final Drawable drawable = image.getDrawable();
					final Drawable wrapped = DrawableCompat.wrap(drawable);
					drawable.mutate();
					DrawableCompat.setTint(wrapped, color);
					image.setImageDrawable(drawable);
				}
			}
		}
	}
	
}