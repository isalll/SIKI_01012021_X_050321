package com.siki.android.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
//import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;

import com.siki.android.R;
import com.siki.android.sqlite.adapter.PerusahaanListAdapter;
import com.siki.android.sqlite.db.PerusahaanDAO;
import com.siki.android.sqlite.to.Perusahaan;

public class PerListFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "perusahaan_list";

	Activity activity;
	ListView perusahaanListView;
	ArrayList<Perusahaan> perusahaanList;

	PerusahaanListAdapter perusahaanListAdapter;
	PerusahaanDAO perusahaanDAO;

	private GetPerTask task;
	EditText inputSearch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = getActivity();
		perusahaanDAO = new PerusahaanDAO(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_per_list, container,
				false);
		findViewsById(view);

		task = new GetPerTask(activity);
		task.execute((Void) null);

		perusahaanListView.setOnItemClickListener(this);
		perusahaanListView.setOnItemLongClickListener(this);

		// perusahaan e = perusahaanDAO.getperusahaan(1);
		// Log.d("perusahaan e", e.toString());
		return view;
	}

	private void findViewsById(View view) {
		perusahaanListView = (ListView) view.findViewById(R.id.list_per);
		inputSearch = (EditText) view.findViewById(R.id.inputSearch);
	}

	@SuppressLint("NewApi")
	@Override
	public void onResume() {
		//getActivity().setTitle(R.string.app_name);
		//getActivity().getActionBar().setTitle(R.string.app_name);
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> list, View arg1, int position,
			long arg3) {
		Perusahaan perusahaan = (Perusahaan) list.getItemAtPosition(position);

		if (perusahaan != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("selectedperusahaan", perusahaan);
			CustomPerDialogFragment customPerDialogFragment = new CustomPerDialogFragment();
			customPerDialogFragment.setArguments(arguments);
			customPerDialogFragment.show(getFragmentManager(),CustomPerDialogFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long arg3) {
		Perusahaan perusahaan = (Perusahaan) parent.getItemAtPosition(position);

		// Use AsyncTask to delete from database
		perusahaanDAO.delete(perusahaan);
		perusahaanListAdapter.remove(perusahaan);
		return true;
	}

	public class GetPerTask extends AsyncTask<Void, Void, ArrayList<Perusahaan>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetPerTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<Perusahaan> doInBackground(Void... arg0) {
			ArrayList<Perusahaan> perusahaanList = perusahaanDAO.getPerusahaans();

			return perusahaanList;
		}

		@Override
			protected void onPostExecute(ArrayList<Perusahaan> PerList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				//Log.d("perusahaanList", PerList.toString());
				perusahaanList = PerList;
				if (PerList != null) {
					if (PerList.size() != 0) {
						perusahaanListAdapter = new PerusahaanListAdapter(activity,
								PerList);
						perusahaanListView.setAdapter(perusahaanListAdapter);


					} else {
						Toast.makeText(activity, "No perusahaan Records",
								Toast.LENGTH_LONG).show();
					}
					//inputSearch.setText(perusahaanListAdapter.toString());
					inputSearch.addTextChangedListener(new TextWatcher() {
						@Override
						public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
							PerListFragment.this.perusahaanListAdapter.getFilter().filter(cs);
						}

						@Override
						public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

						}

						@Override
						public void afterTextChanged(Editable editable) {

						}
					});
				}

			}
		}
	}

	/*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from CustomPerDialogFragment when an perusahaan record is updated.
	 * This is used for communicating between fragments.
	 */
	public void updateView() {
		task = new GetPerTask(activity);
		task.execute((Void) null);
	}
}
