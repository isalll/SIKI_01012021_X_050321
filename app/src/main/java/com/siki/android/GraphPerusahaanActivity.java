package com.siki.android;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
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
import com.siki.library.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GraphPerusahaanActivity extends Fragment {
	JSONArray grapik = null;

	private static final String AR_JUDUL = "kode";
	private static final String AR_NAMA = "name";
	private static final String AR_USER = "username";
	private static final String AR_DATE = "date";
	private static final String AR_TIME = "time";

	private ProgressWheel prog_one, prog_two, prog_three, prog_four;
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_prog_four, tv_title;
	private TextView tv_prog_one_lblup,tv_prog_one_lbldown,tv_prog_two_lblup,tv_prog_two_lbldown,tv_prog_three_lblup,tv_prog_three_lbldown,tv_prog_four_lbldown;

	private static String url_dsb_perusahaan   = Globals.url_graph_perusahaan2+"?kode="+ Globals.id;
	private static String url_dsb_impup   = Globals.url_dsb_impup;
	private static String url_dsb_impdown = Globals.url_dsb_impdown;
	private static String url_dsb_eksdown = Globals.url_dsb_eksdown;
	private static String url_dsb_eksup   = Globals.url_dsb_eksup;

	private TabHost mTabHost;
	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	private ArrayList<Keyword> listHSKeyword;
	TabWidget tabWidget;
	LinearLayout progress1,progress2,progress3,progress4;
	
        ArrayList<String> list_eksdown,list_eksup,list_impdown,list_impup,listHSArrayString,listUraianHSArrayString = new ArrayList<String>();
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		super.onCreateView(inflater, container, savedInstanceState);

    		final View v = inflater.inflate(R.layout.activity_graph_perusahaan, container, false);

    		
    		setupTabHost(v);

    		setupTab(new TextView(getActivity()), "Data");
    		mTabHost.setCurrentTab(0);	    
    		//reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_impup.php");
			//reLoadTable(v,"http://192.168.43.8/iris/android/dsb_impup.php");
			//reLoadTable(v,"http://boemikawani.com/iris/android/dsb_impup.php");
			//reLoadTable(v, url_dsb_perusahaan);

			//reLoadTable(v, url_dsb_impup);
			reLoadTable(v, url_dsb_perusahaan);

			init(v);

			return v;
    	}
      
    	private void init(View v) {           
             //tv_title = (TextView) v.findViewById(R.id.tv_title);
             //tv_title.setText(Globals.dash_graph);
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
    				.inflate(R.layout.tabs_graph, null);
    		TextView tv = (TextView) view.findViewById(R.id.tabsText);
    		tv.setText(text);
    		return view;
    	}
        
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
           	     //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
           	}
           	catch(Exception e)
           	{
           	        Log.e("log_tag", "Error in http connection "+e.toString());
           	        //Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();

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
        	    //Toast.makeText(getApplicationContext(), " Input reading fail", Toast.LENGTH_SHORT).show();

        	}
        		
        	//parse json data
        	try
        	{

        	JSONArray jArray = new JSONArray(result);

				lvKeyword = (ListView) v.findViewById(R.id.lvDashboard);

				listKeyword = new ArrayList<Keyword>();
				listHSKeyword = new ArrayList<Keyword>();
				listHSArrayString = new ArrayList<String>();
				listUraianHSArrayString = new ArrayList<String>();


				prog_one = (ProgressWheel) v.findViewById(R.id.prog_one);


				tv_prog_one = (TextView) v.findViewById(R.id.tv_prog_one);

				tv_prog_one_lbldown = (TextView) v.findViewById(R.id.textView3);


        	//String re=jArray.getString(jArray.length()-1);
        	
          
        	lvKeyword = (ListView) v.findViewById(R.id.lvSource);
        	listKeyword = new ArrayList<Keyword>();
        	
           
        	int flag=1;
				for(int i=1;i<=1;i++){
					JSONParser jParser = new JSONParser();
					//JSONObject json = jParser.AmbilJson(Globals.url_graph+"?kon=graph"+i);
					JSONObject json = jParser.AmbilJson(Globals.url_graph_perusahaan+"?kode="+ Globals.id+"&&kondisi=data1");
					try{
						grapik = json.getJSONArray("dash");
						JSONObject ad = grapik.getJSONObject(0);

						if (i==1){
							prog_one.setProgress((int)(100)); 	// Set progress 50%
							tv_prog_one.setText(ad.getInt(AR_JUDUL)+"");				// Set value 50 for TextView
							tv_prog_one_lbldown.setText(ad.getString(AR_NAMA)+"");

						}
					}catch (JSONException e){
						e.printStackTrace();
					}
				}
        	for(int i=-1;i<jArray.length()-1;i++)
        	        
        	{

        		if(flag==1)
        		            {
        		            //header tabel	
        	      	    		//listKeyword.add(addKeyword("KODE HS"));
        		            	flag=0;
        		            }
                
        		            else
        		            {
								JSONObject json_data = jArray.getJSONObject(i);
        		        	    
              	             /* Log.i("log_tag","Kode HS: "+json_data.getInt("kd_hs")+
              	                        ", Uraian: "+json_data.getString("uraian")+
              	                        ", Forecast: "+json_data.getInt("forecast"));
              	       
        		            */
								listKeyword.add(addKeyword(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian_indo")+
										" ( kmdts: "+json_data.getString("kd_komoditas") + "; dtjn: "+json_data.getString("ditjen")+
										" )"));
								tv_title = (TextView) v.findViewById(R.id.tv_title);
								tv_title.setText(json_data.getString("nama"));
								//LOAD DATA TABEL KE LIST (Array)
								//Cek status

/*
								if (json_data.getString("status")=="ImporU"){
									list_impup.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
											" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
											" )");
								}
*/
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
                           Toast.makeText(getActivity(),  "Berita Selected : "+separated[0],   Toast.LENGTH_LONG).show();
                       		Globals.kode_berita = separated[0];
						   Bundle arguments = new Bundle();
						   DetailGraphFragment detailFragment = new DetailGraphFragment();
						   detailFragment.setArguments(arguments);
						   detailFragment.show(getFragmentManager(), DetailGraphFragment.ARG_ITEM_ID);
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