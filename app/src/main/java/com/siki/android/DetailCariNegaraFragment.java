package com.siki.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.siki.android.sqlite.db.PerusahaanDAO;
import com.siki.android.sqlite.to.Perusahaan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailCariNegaraFragment extends DialogFragment {

	TextView kode,uraian,sektor,subsektor,kd_komoditas,kd_bec,kd_kbli05,sektor_btki2012,bea_masuk,kd_kbli09,uraian_indo,id_sni_wajib,sni_wajib_ex,ditjen,jasa_industri,jasa_industri2,nama_bec,nama_komoditas,nama_kbli,nama_kbli09,tarif;

	private static final String AR_KODE = "kode";
	private static final String AR_URAIAN = "uraian";
	private static final String AR_SEKTOR = "sektor";
	private static final String AR_SUBSEKTOR = "subsektor";
	private static final String AR_KDKOMODITAS = "kd_komoditas";
	private static final String AR_KDBEC = "kd_bec";
	private static final String AR_KDKBLI05 = "kd_kbli05";
	private static final String AR_BTKI2012 = "sektor_btki2012";
	private static final String AR_BEAMASUK = "bea_masuk";
	private static final String AR_KDKBLI09 = "kd_kbli09";
	private static final String AR_URAIANINDO = "uraian_indo";
	private static final String AR_IDSNI = "id_sni_wajib";
	private static final String AR_SNIEX = "sni_wajib_ex";
	private static final String AR_DITJEN = "ditjen";
	private static final String AR_JASA = "jasa_industri";
	private static final String AR_JASA2 = "jasa_industri2";
	private static final String AR_NAMABEC = "nama_bec";
	private static final String AR_NAMAKOMODITAS = "nama_komoditas";
	private static final String AR_NAMAKBLI = "nama_kbli";
	private static final String AR_NAMAKBLI09 = "nama_kbli09";
	private static final String AR_TARIF = "nama_tarif";

	JSONArray detail_berita = null;

	private Perusahaan Perusahaan;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	PerusahaanDAO PerusahaanDAO;

	public static final String ARG_ITEM_ID = "per_dialog_fragment";




	public interface perDialogFragmentListener {
		void onFinishDialog();
	}

	public DetailCariNegaraFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		PerusahaanDAO = new PerusahaanDAO(getActivity());

		Bundle bundle = this.getArguments();
		Perusahaan = bundle.getParcelable("selectedPerusahaan");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.fragment_cari_hs,
				null);
		builder.setView(customDialogView);
		kode = (TextView) customDialogView.findViewById(R.id.kode_id);
		kode.setText(Globals.kode_berita);

		com.siki.library.JSONParser jParser1 = new com.siki.library.JSONParser();
		JSONObject json_detail = jParser1.AmbilJson(Globals.url_detailberita1+"?kode="+Globals.kode_berita);
		try {
			detail_berita = json_detail.getJSONArray("berita");
			JSONObject ad = detail_berita.getJSONObject(0);

			uraian = (TextView) customDialogView.findViewById(R.id.uraian);
			sektor = (TextView) customDialogView.findViewById(R.id.sektor);
			subsektor = (TextView) customDialogView.findViewById(R.id.subsektor);
			kd_komoditas = (TextView) customDialogView.findViewById(R.id.kd_komoditas);
			kd_bec = (TextView) customDialogView.findViewById(R.id.kd_bec);
			kd_kbli05 = (TextView) customDialogView.findViewById(R.id.kd_kbli05);
			sektor_btki2012 = (TextView) customDialogView.findViewById(R.id.sektor_btki2012);
			bea_masuk = (TextView) customDialogView.findViewById(R.id.bea_masuk);
			kd_kbli09 = (TextView) customDialogView.findViewById(R.id.kd_kbli09);
			uraian_indo = (TextView) customDialogView.findViewById(R.id.uraian_indo);
			id_sni_wajib = (TextView) customDialogView.findViewById(R.id.id_sni_wajib);
			sni_wajib_ex = (TextView) customDialogView.findViewById(R.id.sni_wajib_ex);
			ditjen = (TextView) customDialogView.findViewById(R.id.ditjen);
			jasa_industri = (TextView) customDialogView.findViewById(R.id.jasa_industri);
			jasa_industri2 = (TextView) customDialogView.findViewById(R.id.jasa_industri2);
			nama_bec = (TextView) customDialogView.findViewById(R.id.nama_bec);
			nama_komoditas = (TextView) customDialogView.findViewById(R.id.nama_komoditas);
			nama_kbli = (TextView) customDialogView.findViewById(R.id.nama_kbli);
			nama_kbli09 = (TextView) customDialogView.findViewById(R.id.nama_kbli09);
			tarif = (TextView) customDialogView.findViewById(R.id.tarif);

			uraian.setText(ad.getString(AR_URAIAN));
			sektor.setText(ad.getString(AR_SEKTOR));
			subsektor.setText(ad.getString(AR_SUBSEKTOR));
			kd_komoditas.setText(ad.getString(AR_KDKOMODITAS));
			kd_bec.setText(ad.getString(AR_KDBEC));
			kd_kbli05.setText(ad.getString(AR_KDKBLI05));
			sektor_btki2012.setText(ad.getString(AR_BTKI2012));
			bea_masuk.setText(ad.getString(AR_BEAMASUK));
			kd_kbli09.setText(ad.getString(AR_KDKBLI09));
			uraian_indo.setText(ad.getString(AR_URAIANINDO));
			id_sni_wajib.setText(ad.getString(AR_IDSNI));
			sni_wajib_ex.setText(ad.getString(AR_SNIEX));
			ditjen.setText(ad.getString(AR_DITJEN));
			jasa_industri.setText(ad.getString(AR_JASA));
			jasa_industri2.setText(ad.getString(AR_JASA2));
			nama_bec.setText(ad.getString(AR_NAMABEC));
			nama_komoditas.setText(ad.getString(AR_NAMAKOMODITAS));
			nama_kbli.setText(ad.getString(AR_NAMAKBLI));
			nama_kbli09.setText(ad.getString(AR_NAMAKBLI09));
			tarif.setText(ad.getString(AR_TARIF));
		}catch (JSONException e){
			e.printStackTrace();
		}
		builder.setTitle(R.string.detail);
		builder.setCancelable(false);
		builder.setNegativeButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}
}
