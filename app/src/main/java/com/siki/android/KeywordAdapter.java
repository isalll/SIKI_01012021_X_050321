package com.siki.android;

import java.util.ArrayList;

import com.siki.android.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KeywordAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Keyword> data;
	private LayoutInflater inflater;

	public KeywordAdapter(Activity a, ArrayList<Keyword> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.item_keywords, null);
		}
		TextView tvKodeHS = (TextView) vi.findViewById(R.id.tvKodeHS);
		//TextView tvUraian = (TextView) vi.findViewById(R.id.tvUraian);
		
		Keyword keyword = new Keyword();
		keyword = data.get(position);
		
		tvKodeHS.setText(keyword.getText());
		//tvUraian.setText(keyword.getNumber());
		
		return vi;
	}

}
