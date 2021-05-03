package com.siki.android.sqlite.fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.siki.android.MainActivity;
import com.siki.android.R;
import com.siki.android.sqlite.db.PerusahaanDAO;
import com.siki.android.sqlite.to.Perusahaan;

public class CustomPerDialogFragment extends DialogFragment {

	// UI references
	private EditText perNameEtxt;
	private EditText perNoIuiEtxt;
	private EditText perTglIuiEtxt;
	
	private EditText perTenakerAsingEtxt;
	private EditText perTenakerDNEtxt;
	private EditText perKdBdnHkmEtxt;
	private EditText perNPWPEtxt;
	private EditText perProdUtamaEtxt;
	private EditText perSektorEtxt;
	
	
	private LinearLayout submitLayout;

	private Perusahaan Perusahaan;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	
	PerusahaanDAO PerusahaanDAO;
	
	public static final String ARG_ITEM_ID = "per_dialog_fragment";

	public interface perDialogFragmentListener {
		void onFinishDialog();
	}

	public CustomPerDialogFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		PerusahaanDAO = new PerusahaanDAO(getActivity());

		Bundle bundle = this.getArguments();
		Perusahaan = bundle.getParcelable("selectedPerusahaan");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.fragment_add_per,
				null);
		builder.setView(customDialogView);

		perNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_name);
		perNoIuiEtxt = (EditText) customDialogView.findViewById(R.id.etxt_noiui);
		perTglIuiEtxt = (EditText) customDialogView.findViewById(R.id.etxt_tgliui);
		
		perTenakerAsingEtxt = (EditText) customDialogView.findViewById(R.id.etxt_tenakerasing);
		perTenakerDNEtxt = (EditText) customDialogView.findViewById(R.id.etxt_tenakerdn);
		perKdBdnHkmEtxt = (EditText) customDialogView.findViewById(R.id.etxt_kdbdnhkm);
		perNPWPEtxt = (EditText) customDialogView.findViewById(R.id.etxt_npwp);
		perProdUtamaEtxt = (EditText) customDialogView.findViewById(R.id.etxt_produtama);
		perSektorEtxt = (EditText) customDialogView.findViewById(R.id.etxt_sektor);
		
		
		
		submitLayout = (LinearLayout) customDialogView.findViewById(R.id.layout_submit);
		submitLayout.setVisibility(View.GONE);

		setValue();

		builder.setTitle(R.string.update_per);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.update,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						
					/*
						try {
							
							Perusahaan.settgl_iui(perTglIuiEtxt.getText().toString());
							
							//Perusahaan.settgl_iui(formatter.parse(perTglIuiEtxt.getText().toString()));
						} catch (ParseException e) {
							Toast.makeText(getActivity(),
									"Invalid date format!",
									Toast.LENGTH_SHORT).show();
							return;
						}
	*/
						Perusahaan.settgl_iui(perTglIuiEtxt.getText().toString());
						
						Perusahaan.setNama(perNameEtxt.getText().toString());
						//belum ditambah....
						Perusahaan.setNama(perNoIuiEtxt.getText().toString());
						
						
						//Perusahaan.setSalary(Double.parseDouble(perSalaryEtxt.getText().toString()));
						long result = PerusahaanDAO.update(Perusahaan);
						if (result > 0) {
							MainActivity activity = (MainActivity) getActivity();
							activity.onFinishDialog();
						} else {
							Toast.makeText(getActivity(),
									"Unable to update Perusahaan",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	private void setValue() {
		if (Perusahaan != null) {
			perNameEtxt.setText(Perusahaan.getNama());
			perNoIuiEtxt.setText(Perusahaan.getno_iui() + "");
			perTglIuiEtxt.setText(formatter.format(Perusahaan.gettgl_iui()));
			
			perTenakerAsingEtxt.setText(Perusahaan.gettenaga_kerja_asing() + "");
			perTenakerDNEtxt.setText(Perusahaan.gettenaga_kerja_dn() + "");
			perKdBdnHkmEtxt.setText(Perusahaan.getkd_badan_hukum() + "");
			perProdUtamaEtxt.setText(Perusahaan.getproduksi_utama() + "");
			perSektorEtxt.setText(Perusahaan.getsektor() + "");

		}
	}
}
