package caceresenzo.android.libs.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class BlockableLinearLayoutManager extends LinearLayoutManager {
	private boolean isScrollEnabled = true;
	
	public BlockableLinearLayoutManager(Context context) {
		super(context);
	}
	
	public void setScrollEnabled(boolean flag) {
		this.isScrollEnabled = flag;
	}
	
	@Override
	public boolean canScrollVertically() {
		return isScrollEnabled && super.canScrollVertically();
	}
	
}