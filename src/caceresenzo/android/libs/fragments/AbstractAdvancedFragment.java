package caceresenzo.android.libs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractAdvancedFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getLayout(), container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews(getView());
		onActivityReady(savedInstanceState);
	}
	
	public abstract void onActivityReady(Bundle savedInstanceState);
	
	public abstract void initializeViews(View view);
	
	public abstract int getLayout();
	
}