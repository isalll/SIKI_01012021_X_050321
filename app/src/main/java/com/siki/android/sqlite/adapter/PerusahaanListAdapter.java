package com.siki.android.sqlite.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siki.android.R;
import com.siki.android.sqlite.to.Perusahaan;

public class PerusahaanListAdapter extends ArrayAdapter<Perusahaan> {

	private Context context;
	List<Perusahaan> perusahaanlist;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public PerusahaanListAdapter(Context context, List<Perusahaan> perusahaans) {
		super(context, R.layout.list_item, perusahaans);
		this.context = context;
		this.perusahaanlist = perusahaans;
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
		return perusahaanlist.size();
	}

	@Override
	public Perusahaan getItem(int position) {
		return perusahaanlist.get(position);
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
		Perusahaan perusahaan = (Perusahaan) getItem(position);
		holder.perIdTxt.setText(perusahaan.getId() + ". ");
		holder.perNamaTxt.setText(perusahaan.getNama());
		holder.perNoIuiTxt.setText("No. IUI : " + perusahaan.getno_iui() + "");
		holder.perTglIuiTxt.setText("Tgl. IUI : " + perusahaan.gettgl_iui() + "");

		//holder.perTglIuiTxt.setText(formatter.format(perusahaan.gettgl_iui()));
		//holder.perTglIuiTxt.setText(formatter.format(perusahaan.getDateOfBirth()));

		
		holder.perTenKerAsingTxt.setText("Tenaga Kerja Asing : " +perusahaan.gettenaga_kerja_asing() + "");
		holder.perTenKerDNTxt.setText("Tenaga Kerja DN : " + perusahaan.gettenaga_kerja_dn() + "");
		holder.perKdBdnHkmTxt.setText("Kd. Badan Hukum : " + perusahaan.getkd_badan_hukum() + "");
		holder.perNPWPTxt.setText("NPWP : " + perusahaan.getnpwp() + "");
		holder.perProdUtamaTxt.setText("Produksi Utama : " + perusahaan.getproduksi_utama() + "");
		holder.perSektorTxt.setText("Sektor : " + perusahaan.getsektor() + "");

		return convertView;
	}

	@Override
	public void add(Perusahaan perusahaan) {
		perusahaanlist.add(perusahaan);
		notifyDataSetChanged();
		super.add(perusahaan);
	}

	@Override
	public void remove(Perusahaan perusahaan) {
		perusahaanlist.remove(perusahaan);
		notifyDataSetChanged();
		super.remove(perusahaan);
	}
}
