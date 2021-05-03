package com.siki.android.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.siki.android.R;
import com.siki.android.sqlite.db.PerusahaanDAO;
import com.siki.android.sqlite.to.Perusahaan;

public class PerAddFragment extends Fragment implements OnClickListener {

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
	
	
	private Button addButton;
	private Button resetButton;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	DatePickerDialog datePickerDialog;
	Calendar dateCalendar;

	Perusahaan perusahaan = null;
	private PerusahaanDAO perusahaanDAO;
	private AddperTask task;

	public static final String ARG_ITEM_ID = "per_add_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		perusahaanDAO = new PerusahaanDAO(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_per, container,
				false);

		findViewsById(rootView);

		setListeners();

		//For orientation change. 
		if (savedInstanceState != null) {
			dateCalendar = Calendar.getInstance();
			if (savedInstanceState.getLong("dateCalendar") != 0)
				dateCalendar.setTime(new Date(savedInstanceState
						.getLong("dateCalendar")));
		}

		return rootView;
	}

	private void setListeners() {
		perTglIuiEtxt.setOnClickListener(this);
		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(getActivity(),
				new OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						dateCalendar = Calendar.getInstance();
						dateCalendar.set(year, monthOfYear, dayOfMonth);
						perTglIuiEtxt.setText(formatter.format(dateCalendar
								.getTime()));
					}

				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));

		addButton.setOnClickListener(this);
		resetButton.setOnClickListener(this);
	}

	protected void resetAllFields() {
		perNameEtxt.setText("");
		perNoIuiEtxt.setText("");
		perTglIuiEtxt.setText("");
		
		perTenakerAsingEtxt.setText("");
		perTenakerDNEtxt.setText("");
		perKdBdnHkmEtxt.setText("");
		perNPWPEtxt.setText("");
		perProdUtamaEtxt.setText("");
		perSektorEtxt.setText("");
		
	}

	private void setperusahaan() {
		perusahaan = new Perusahaan();
		perusahaan.setNama(perNameEtxt.getText().toString());
		perusahaan.setNama(perNoIuiEtxt.getText().toString());
		
		
//		perusahaan.setSalary(Double.parseDouble(perSalaryEtxt.getText()
//				.toString()));
		//if (dateCalendar != null)
			//perusahaan.settgl_iui(dateCalendar.getTime());
	}

	@SuppressLint("NewApi")
	@Override
	public void onResume() {
		getActivity().setTitle(R.string.add_per);
		//getActivity().getActionBar().setTitle(R.string.add_per);
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (dateCalendar != null)
			outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
	}

	private void findViewsById(View rootView) {
		perNameEtxt = (EditText) rootView.findViewById(R.id.etxt_name);
		perNoIuiEtxt = (EditText) rootView.findViewById(R.id.etxt_noiui);
		//perDobEtxt = (EditText) rootView.findViewById(R.id.etxt_dob);
		perTglIuiEtxt = (EditText) rootView.findViewById(R.id.etxt_tgliui);
		perTglIuiEtxt.setInputType(InputType.TYPE_NULL);
		
		perTenakerAsingEtxt = (EditText) rootView.findViewById(R.id.etxt_tenakerasing);
		perTenakerDNEtxt = (EditText) rootView.findViewById(R.id.etxt_tenakerdn);
		perKdBdnHkmEtxt = (EditText) rootView.findViewById(R.id.etxt_kdbdnhkm);
		perNPWPEtxt = (EditText) rootView.findViewById(R.id.etxt_npwp);
		perProdUtamaEtxt = (EditText) rootView.findViewById(R.id.etxt_produtama);
		perSektorEtxt = (EditText) rootView.findViewById(R.id.etxt_sektor);

		
		addButton = (Button) rootView.findViewById(R.id.button_add);
		resetButton = (Button) rootView.findViewById(R.id.button_reset);
	}

	@Override
	public void onClick(View view) {
		if (view == perTglIuiEtxt) {
			datePickerDialog.show();
		} else if (view == addButton) {
			setperusahaan();

			task = new AddperTask(getActivity());
			task.execute((Void) null);
		} else if (view == resetButton) {
			resetAllFields();
		}
	}

	public class AddperTask extends AsyncTask<Void, Void, Long> {

		private final WeakReference<Activity> activityWeakRef;

		public AddperTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected Long doInBackground(Void... arg0) {
			long result = perusahaanDAO.save(perusahaan);
			return result;
		}

		@Override
		protected void onPostExecute(Long result) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				if (result != -1)
					Toast.makeText(activityWeakRef.get(), "perusahaan Saved",
							Toast.LENGTH_LONG).show();
			}
		}
	}
}
