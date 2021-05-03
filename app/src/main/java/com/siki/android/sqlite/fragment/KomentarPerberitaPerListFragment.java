package com.siki.android.sqlite.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.siki.android.R;
import com.siki.android.sqlite.adapter.KomentarListAdapter;
import com.siki.android.sqlite.db.KomentarPerberitaDAO;
import com.siki.android.sqlite.to.KomentarPerberita;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

//import android.util.Log;

public class KomentarPerberitaPerListFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "komentar_list";

	Activity activity;
	ListView komentarListView;
	ArrayList<KomentarPerberita> komentarList;

	KomentarListAdapter komentarListAdapter;
	KomentarPerberitaDAO komentarDAO;

	private GetPerTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		komentarDAO = new KomentarPerberitaDAO(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_per_list, container,
				false);
		findViewsById(view);

		task = new GetPerTask(activity);
		task.execute((Void) null);

		komentarListView.setOnItemClickListener(this);
		komentarListView.setOnItemLongClickListener(this);
		return view;
	}

	private void findViewsById(View view) {
		komentarListView = (ListView) view.findViewById(R.id.list_per);
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
		KomentarPerberita Koper = (KomentarPerberita) list.getItemAtPosition(position);

		if (Koper != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("selectedkomentar", Koper);
			CustomPerDialogFragment customPerDialogFragment = new CustomPerDialogFragment();
			customPerDialogFragment.setArguments(arguments);
			customPerDialogFragment.show(getFragmentManager(),
					CustomPerDialogFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long arg3) {
		KomentarPerberita Koper = (KomentarPerberita) parent.getItemAtPosition(position);

		// Use AsyncTask to delete from database
		komentarDAO.delete(Koper);
		komentarListAdapter.remove(Koper);
		return true;
	}

	public class GetPerTask extends AsyncTask<Void, Void, ArrayList<KomentarPerberita>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetPerTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<KomentarPerberita> doInBackground(Void... arg0) {
			ArrayList<KomentarPerberita> komentarList = komentarDAO.getKomentar();
			return komentarList;
		}

		@Override
		protected void onPostExecute(ArrayList<KomentarPerberita> PerList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				//Log.d("perusahaanList", PerList.toString());
				komentarList = PerList;
				if (PerList != null) {
					if (PerList.size() != 0) {
						KomentarListAdapter komentarListAdapter = new KomentarListAdapter(activity,
								PerList);
						komentarListView.setAdapter(komentarListAdapter);
					} else {
						Toast.makeText(activity, "No perusahaan Records",
								Toast.LENGTH_LONG).show();
					}
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
