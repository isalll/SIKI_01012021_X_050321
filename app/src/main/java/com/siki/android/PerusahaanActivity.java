package com.siki.android;

//import com.androidopentutorials.sqlite.fragment.EmpAddFragment;
//import com.androidopentutorials.sqlite.fragment.EmpListFragment;
//import com.androidopentutorials.sqlite.fragment.EmpAddFragment;
//import com.androidopentutorials.sqlite.fragment.EmpListFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;

import com.siki.android.sqlite.fragment.PerAddFragment;
import com.siki.android.sqlite.fragment.PerListFragment;

public class PerusahaanActivity extends Fragment{

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
	private Fragment contentFragment;
	private PerListFragment perusahaanListFragment;
	private PerAddFragment perusahaanAddFragment;


	private TextView tv_title;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.activity_perusahaan, container, false);

		//tv_title =(TextView)v.findViewById(R.id.tv_title);
		//tv_title.setText("Tentang IRIS");
//--------------------------------------------------------------------------
		
		FragmentManager fragmentManager = getFragmentManager();

		/*
		 * This is called when orientation is changed.
		 */
	
		
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("content")) {
				String content = savedInstanceState.getString("content");
				if (content.equals(PerAddFragment.ARG_ITEM_ID)) {
					if (fragmentManager
							.findFragmentByTag(PerAddFragment.ARG_ITEM_ID) != null) {
						setFragmentTitle(R.string.add_emp);
						contentFragment = fragmentManager
								.findFragmentByTag(PerAddFragment.ARG_ITEM_ID);
					}
				}
			}
			if (fragmentManager.findFragmentByTag(PerListFragment.ARG_ITEM_ID) != null) {
				perusahaanListFragment = (PerListFragment) fragmentManager
						.findFragmentByTag(PerListFragment.ARG_ITEM_ID);
				contentFragment = perusahaanListFragment;
			}
		} else {
			perusahaanListFragment = new PerListFragment();
			setFragmentTitle(R.string.app_name);
			switchContent(perusahaanListFragment, PerListFragment.ARG_ITEM_ID);
		}
		/*
		perusahaanListFragment = new PerListFragment();
		//setFragmentTitle(R.string.app_name);
		switchContent(perusahaanListFragment, PerListFragment.ARG_ITEM_ID);
*/
		
		
		return v;
	}

	public void switchContent(Fragment fragment, String tag) {
		FragmentManager fragmentManager = getFragmentManager();
		//while (fragmentManager.popBackStackImmediate())
		//	;

		if (fragment != null) {
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.content_frame, fragment, tag);
			// Only EmpAddFragment is added to the back stack.
			if (!(fragment instanceof PerListFragment)) {
				transaction.addToBackStack(tag);
			}
			transaction.commit();
			contentFragment = fragment;
		}
	}

	
	protected void setFragmentTitle(int resourseId) {
		//setTitle(resourseId);
		//getActionBar().setTitle(resourseId);

	}

	
	
}
