package caceresenzo.android.libs.list;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListUtils {
	
	public static void setDynamicHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		
		if (listAdapter == null) {
			return;
		}
		
		int height = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			height += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
		layoutParams.height = height + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(layoutParams);
		listView.requestLayout();
	}
	
}