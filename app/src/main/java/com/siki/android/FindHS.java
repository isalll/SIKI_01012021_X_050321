package com.siki.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FindHS extends Fragment {
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_title;


	//private TabHost mTabHost;
	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	private Button cari;
	private EditText editcari;
	private String isicari;
	private ProgressDialog pDialog;int flag=0;
	com.siki.library.JSONParser jsonParser = new com.siki.library.JSONParser();
	private static String urlcari = Globals.url_cari_hs2;
	TabWidget tabWidget;

	ArrayList<String> list_eksdown,list_eksup,list_impdown,list_impup = new ArrayList<String>();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		final View v = inflater.inflate(R.layout.activity_find, container, false);


		editcari=(EditText)v.findViewById(R.id.editcari);
		cari=(Button)v.findViewById(R.id.cari);
		isicari="";

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
				reLoadTable(v, urlcari+"?cari="+isicari);

			}
		});
		//setupTabHost(v);
		//setupTab(new TextView(getActivity()), "Pencarian");

		//mTabHost.setCurrentTab(0);


		init(v);

		return v;
	}

	private void init(View v) {
		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("Pencarian HS");
	}

/*
        public void setCurrentTab(int nIdx)
    	{
        	mTabHost.setCurrentTab(nIdx);
    	}


        private void setupTab(final View view, final String tag) {
    		View tabview = createTabView(mTabHost.getContext(), tag);

    		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
    				.setContent(new TabContentFactory() {
    					public View createTabContent(String tag) {
    						return view;
    					}
    				});
    		mTabHost.addTab(setContent);
    		//mTabHost.setOnTabChangedListener(view);

    	}
        private void setupTabHost(View v) {
    		mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
    		mTabHost.setup();
    	}

        private static View createTabView(final Context context, final String text) {
    		View view = LayoutInflater.from(context)
    				.inflate(R.layout.tabs_bg, null);
    		TextView tv = (TextView) view.findViewById(R.id.tabsText);
    		tv.setText(text);
    		return view;
    	}
   */

	public void reLoadTable(View v, String url) {

		String result = null;
		InputStream is = null;

		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			Log.e("log_tag", "connection success ");
		}
		catch(Exception e)
		{
			Log.e("log_tag", "Error in http connection "+e.toString());

		}

		//convert response to string
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
				//  Toast.makeText(getApplicationContext(), "Input Reading pass", Toast.LENGTH_SHORT).show();
			}
			is.close();

			result=sb.toString();
		}
		catch(Exception e)
		{
			Log.e("log_tag", "Error converting result "+e.toString());

		}

		//parse json data
		try
		{

			JSONArray jArray = new JSONArray(result);


			lvKeyword = (ListView) v.findViewById(R.id.lvSource);
			listKeyword = new ArrayList<Keyword>();


			int flag=1;

			for(int i=-1;i<jArray.length()-1;i++)

			{

				if(flag==1)
				{
					//header tabel
					flag=0;
				}

				else
				{
					JSONObject json_data = jArray.getJSONObject(i);

					listKeyword.add(addKeyword(json_data.getString("kd_hs")+" : "+ json_data.getString("deskripsi")));

				}


			}
		}
		catch(JSONException e)
		{
			Log.e("log_tag", "Error parsing data "+e.toString());
			//Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
		}



		adapterKeyword = new KeywordAdapter(getActivity(), listKeyword);
		lvKeyword.setAdapter(adapterKeyword);
		// register onClickListener to handle click events on each item
		lvKeyword.setOnItemClickListener(new OnItemClickListener()
		{
			// argument position gives the index of item which is clicked
			public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
			{

				Keyword selectedBerita=listKeyword.get(position);
				String[] separated = selectedBerita.getText().split(" ");
				Toast.makeText(getActivity(),  "Pencarian HS : "+separated[0],   Toast.LENGTH_LONG).show();
				Globals.kode_berita = separated[0];

				Bundle arguments = new Bundle();
				DetailGraphFragment detailFragment = new DetailGraphFragment();
				detailFragment.setArguments(arguments);
				detailFragment.show(getFragmentManager(),DetailCariHSFragment.ARG_ITEM_ID);
						   /*
						   Intent intent = new Intent(getActivity(), ListCommentActivity.class);
	           				startActivity(intent);*/
			}
		});
	}

	private Keyword addKeyword(String text) {
		Keyword keyword = new Keyword();
		keyword.setText(text);
		//keyword.setText(text);
		return keyword;
	}

}