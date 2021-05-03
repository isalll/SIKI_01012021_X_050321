package com.siki.android.sqlite.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siki.android.R;
import com.siki.android.sqlite.to.KomentarPerberita;
import com.siki.android.sqlite.to.Perusahaan;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class KomentarListAdapter extends ArrayAdapter<KomentarPerberita> {

	private Context context;
	List<KomentarPerberita> komentarlist;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public KomentarListAdapter(Context context, List<KomentarPerberita> komentars) {
		super(context, R.layout.list_item, komentars);
		this.context = context;
		this.komentarlist = komentars;
	}

	private class ViewHolder {
		TextView perIdTxt;
		TextView perNamaTxt;
		TextView perNoIuiTxt;
		TextView perTglIuiTxt;
		
		TextView perTenKerAsingTxt;
		TextView perTenKerDNTxt;
		TextView perKdBdnHkmTxt;
		TextView perNPWPTxt;
		TextView perProdUtamaTxt;
		TextView perSektorTxt;
		
	}

	@Override
	public int getCount() {
		return komentarlist.size();
	}

	@Override
	public KomentarPerberita getItem(int position) {
		return komentarlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();

			holder.perIdTxt = (TextView) convertView
					.findViewById(R.id.txt_per_id);
			holder.perNamaTxt = (TextView) convertView
					.findViewById(R.id.txt_per_nama);
			holder.perNoIuiTxt = (TextView) convertView
					.findViewById(R.id.txt_per_no_iui);
			holder.perTglIuiTxt = (TextView) convertView
					.findViewById(R.id.txt_per_tgl_iui);
			
			holder.perTenKerAsingTxt = (TextView) convertView
					.findViewById(R.id.txt_per_ten_ker_asing);
			holder.perTenKerDNTxt = (TextView) convertView
					.findViewById(R.id.txt_per_ten_ker_dn);
			holder.perKdBdnHkmTxt = (TextView) convertView
					.findViewById(R.id.txt_per_bdn_hkm);
			holder.perNPWPTxt = (TextView) convertView
					.findViewById(R.id.txt_per_npwp);
			holder.perProdUtamaTxt = (TextView) convertView
					.findViewById(R.id.txt_per_prod_utama);
			holder.perSektorTxt = (TextView) convertView
					.findViewById(R.id.txt_per_sektor);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		KomentarPerberita koper = (KomentarPerberita) getItem(position);
		holder.perIdTxt.setText(koper.getId() + ". ");
		holder.perNoIuiTxt.setText("Kode HS : " + koper.getKDHS() + "");
		holder.perTglIuiTxt.setText("Komentar : " + koper.getKomentar() + "");
		holder.perTglIuiTxt.setText("Username : " + koper.getUsername() + "");

		return convertView;
	}

	@Override
	public void add(KomentarPerberita koper) {
		komentarlist.add(koper);
		notifyDataSetChanged();
		super.add(koper);
	}

	@Override
	public void remove(KomentarPerberita koper) {
		komentarlist.remove(koper);
		notifyDataSetChanged();
		super.remove(koper);
	}
}
