package com.siki.android.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.siki.android.Globals;
import com.siki.android.Keyword;
import com.siki.android.KeywordAdapter;
import com.siki.android.R;
import com.siki.android.adapters.HSLinkHsItemAdapter;

import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;
import com.siki.library.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.siki.android.models.HSLINKHs;
import com.siki.android.HSLinkDetailActivity;

public class HSLinkFragment extends Fragment {
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_title;


	//private TabHost mTabHost;
	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	private Button cari;
	private EditText editcari;
	private String isicari;
	private ProgressDialog pDialog;int flag=0;
	JSONParser jsonParser = new JSONParser();
	private static String urlcari = Globals.url_cari_hs2;
	TabWidget tabWidget;
	public static final String TAG = "HSLinkActivity";
	private OkHttpClient mOkHttpClient = new OkHttpClient();
	public HSLinkHsItemAdapter mAdapter;
	//private List<HSLINKHs> mHSlink = new ArrayList<>();
	public List<HSLINKHs> HSLinkList = new ArrayList<>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		final View v = inflater.inflate(R.layout.activity_hslink, container, false);


		editcari=(EditText)v.findViewById(R.id.editcari);
		cari=(Button)v.findViewById(R.id.cari);
		isicari="";



		mAdapter = new HSLinkHsItemAdapter(getContext(), HSLinkList);
		ListView listView = (ListView) v.findViewById(R.id.list_view_hslink);
		//listView.setAdapter(mAdapter);
		//listView.setOnItemClickListener(getContext());

		cari.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				//onPreExecute();
				//Check all fields
				if(editcari.length()<0)
				{
					Toast.makeText(getActivity(),"Please Enter Comment", Toast.LENGTH_LONG).show();
					return;
				}


				isicari = editcari.getText().toString();
				//new pencarian().execute();

				//new loginAccess();
				String url = null;
				final String isi = isicari;

				//url = Globals.base_host_bigdata   + "cari_hs?"  +
				//		"cari=" + isi;





				//reLoadTable(v, url);
				if(ConnectivityUtil.isConnected(getContext())) {
					//loadData(v, Globals.base_host_bigdata   + "cari_hs?"  +
					//		"cari=" + isicari);

					reLoadTable(v, Globals.base_host_bigdata   + "cari_hs?"  +
							"cari=" + isicari);
				}
				else {
					PopupUtil.showMsg(getActivity(), "Can't load data, no internet connection", PopupUtil.SHORT);
				}

			}
		});

		init(v);

		return v;
	}

	private void init(View v) {
		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("HS Link");
	}

	public void loadData(View v, String url) {
		PopupUtil.showLoading(getContext(), "", "Loading data ...");

		//String pilihan = mPilihan;
		//String tahun1 = mPd.tahun1;
		//String tahun2 = mPd.tahun2;
		//String bulan1 = mPd.bulan1;
		//String bulan2 = mPd.bulan2;
		//String ditjen = mDitjen;
	//	String url = null;



			//url = Globals.base_host_bigdata   + "cari_hs?"  +
			//		"cari=" + isicari;

		mAdapter = new HSLinkHsItemAdapter(getContext(), HSLinkList);

		Log.d(TAG, "url8:" + url);
		Request request = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response7:" + body);

				try {

					//body = '{"periode":["2017/1",'
					JSONObject json = new JSONObject(body);
					JSONArray kdhsJson = json.getJSONArray("kd_hs");
					JSONArray uraianJson = json.getJSONArray("uraian");

					int flag=1;

					ArrayList<Keyword> kodehsarr = new ArrayList<>();
					for(int i = 0; i < kdhsJson.length(); i++) {
						String kodehs = kdhsJson.getString(i);
						String uraian = uraianJson.getString(i);
						String title = kodehs+ uraian;

						HSLINKHs pd = new HSLINKHs();
						pd.kodeHS = kodehs  ;
						pd.deskripsi = uraian;


						HSLinkList.add(pd);
						//listKeyword.add(addKeyword(kodehs+" : "+ uraian));

					}




					//listKeyword=kodehsarr;
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mAdapter.notifyDataSetChanged();

						}
					});


				} catch (JSONException e) {
					e.printStackTrace();
				}
			}


		});


		ListView listView = (ListView) v.findViewById(R.id.list_view_hslink);
		listView.setAdapter(mAdapter);
		//listView.setOnItemClickListener(v.getContext());
	}

	//@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		/*
		Keyword selectedHS=listKeyword.get(i);
		String[] separated = selectedHS.getText().split(" ");
		Toast.makeText(getActivity(),  "Pencarian HS : "+separated[0],   Toast.LENGTH_LONG).show();
		Globals.kode_hs = separated[0];
		String kodehs = separated[0];
		*/
		//HSLINKHs HSLINKHs = mHSlink.get(i);

		//HSLINKHs pd = mHSlink.get(position);
		Intent intent = new Intent(HSLinkFragment.super.getActivity(), HSLinkDetailActivity.class);

		//kenapaaa... ga bisa jump intentnya?????????????????
		//intent.putExtra(HSLinkDetailActivity.KEY_PDHS, HSLINKHs);
		//intent.putExtra(HSLinkDetailActivity.KEY_KODEHS, kodehs);
		intent.putExtra("title", "Detail Hs");

		startActivity(intent);
	}

	//==============================
	public void reLoadTable(final View v, String url) {
		//FragmentActivity activity = getActivity();
		//final View v= new View();

		//lvKeyword = (ListView) v.findViewById(R.id.lvSource);

		lvKeyword = (ListView) v.findViewById(R.id.lvSource);
		listKeyword = new ArrayList<Keyword>();

		Log.d(TAG, "url8:" + url);
		Request request = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response7:" + body);

				try {

					//body = '{"periode":["2017/1",'
					JSONObject json = new JSONObject(body);
					JSONArray kdhsJson = json.getJSONArray("kd_hs");
					JSONArray uraianJson = json.getJSONArray("uraian");

					int flag=1;

					ArrayList<Keyword> kodehsarr = new ArrayList<>();
					for(int i = 0; i < kdhsJson.length(); i++) {
						String kodehs = kdhsJson.getString(i);
						String uraian = uraianJson.getString(i);
						String title = kodehs+ uraian;

						HSLINKHs pd = new HSLINKHs();
						pd.kodeHS = title  ;
						pd.deskripsi = uraian;


						HSLinkList.add(pd);
						listKeyword.add(addKeyword(kodehs+" : "+ uraian));

					}







					//listKeyword=kodehsarr;
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							//mAdapter.notifyDataSetChanged();
							adapterKeyword.notifyDataSetChanged();

						}
					});


				} catch (JSONException e) {
					e.printStackTrace();
				}
			}


		});

		//mAdapter = new HSLinkHsItemAdapter(getActivity(), HSLinkList);
		//ListView listView = (ListView) v.findViewById(R.id.list_view_hslink);
		//listView.setAdapter(mAdapter);
		//listView.setOnItemClickListener(getContext());
		//adapterKeyword.notifyDataSetChanged();


		Log.e("donni", "--");
		//mAdapter = new HSLinkHsItemAdapter(getContext(), HSLinkList);

		//OK-------->

		adapterKeyword = new KeywordAdapter(getActivity(), listKeyword);
		lvKeyword.setAdapter(adapterKeyword);
		lvKeyword.setOnItemClickListener(new OnItemClickListener()
		{
			// argument position gives the index of item which is clicked
			@Override
			public void onItemClick(AdapterView<?> parent, View v,int position, long id)
			{
				//super.onItemClick();
				Keyword selectedHS=listKeyword.get(position);
				String[] separated = selectedHS.getText().split(" ");
				Toast.makeText(getActivity(),  "Pencarian HS : "+separated[0],   Toast.LENGTH_LONG).show();
				Globals.kode_hs = separated[0];
				String kodehs = separated[0];

				//HSLINKHs pd = mHSlink.get(position);
				HSLinkDetailActivity detailhs = new HSLinkDetailActivity();
				Intent intent = new Intent(HSLinkFragment.super.getContext(),HSLinkDetailActivity.class);

				//PR (04/12/2020)..............kenapaaa... ga bisa jump intentnya?????????????????
				intent.putExtra(HSLinkDetailActivity.KEY_KODEHS, kodehs);
				intent.putExtra("title", "Detail Hs");

                //ini bisa
				//Bundle arguments = new Bundle();
				//DetailGraphFragment detailFragment = new DetailGraphFragment();
				//detailFragment.setArguments(arguments);
				//detailFragment.show(getFragmentManager(),DetailCariHSFragment.ARG_ITEM_ID);


				startActivity(intent);
			}
		});


		//lvKeyword.setOnItemClickListener(listener);

		/*
		OnItemClickListener listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Keyword selectedHS=listKeyword.get(position);
				String[] separated = selectedHS.getText().split(" ");
				Toast.makeText(getActivity(),  "Pencarian HS : "+separated[0],   Toast.LENGTH_LONG).show();
				Globals.kode_hs = separated[0];
				String kodehs = separated[0];

				//HSLINKHs pd = mHSlink.get(position);
				HSLinkDetailActivity detailhs = new HSLinkDetailActivity();

				//detailhs.
				Intent intent = new Intent(getContext(), HSLinkDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				//kenapaaa... ga bisa jump intentnya?????????????????
				intent.putExtra(detailhs.KEY_KODEHS, kodehs);
				intent.putExtra("title", "Detail Hs");

				getContext().startActivity(intent);
				//finish();
			}
		};
		lvKeyword.setOnItemClickListener(listener);

		 */



	}


	public void kembali(View view) {
		//finish();
	}


	private Keyword addKeyword(String text) {
		Keyword keyword = new Keyword();
		keyword.setText(text);
		//keyword.setText(text);
		return keyword;
	}

}