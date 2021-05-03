package com.siki.android;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutActivity extends Fragment{

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

	private TextView tv_title;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.activity_about, container, false);

		tv_title =(TextView)v.findViewById(R.id.tv_title);
		tv_title.setText("Tentang SIKI");
		
		return v;
	}

	
}
