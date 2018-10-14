package caceresenzo.android.libs.widget.itemtouchhelper;

import android.support.v7.widget.RecyclerView;

/**
 * Listener for manual initiation of a drag.
 */
public interface OnStartDragListener<ViewHolderClass extends RecyclerView.ViewHolder> {
	
	/**
	 * Called when a view is requesting a start of a drag.
	 *
	 * @param viewHolder
	 *            The holder of the view to drag.
	 */
	void onStartDrag(ViewHolderClass viewHolder);
	// void onStartDrag(RecyclerView.ViewHolder viewHolder);
	
}