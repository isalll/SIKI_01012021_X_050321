package com.siki.android;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

public class NewsActivity extends Fragment {
	private ProgressWheel prog_one, prog_two, prog_three;
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_title;
	private TextView tv_prog_one_lblup,tv_prog_one_lbldown,tv_prog_two_lblup,tv_prog_two_lbldown,tv_prog_three_lblup,tv_prog_three_lbldown;
	
	private TabHost mTabHost;
	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	TabWidget tabWidget;
	//View v;
	
	//final TabHost tabHost = getTabHost();
	//static TabHost tabHost;
	//private FragmentTabHost fmTabHost;

	ArrayList<String> list_eksdown,list_eksup,list_impdown,list_impup = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		final View v = inflater.inflate(R.layout.activity_news, container, false);

		
		setupTabHost(v);

		setupTab(new TextView(getActivity()), "Impor Up");
		setupTab(new TextView(getActivity()), "Impor Down");
		setupTab(new TextView(getActivity()), "Ekspor Up");
		setupTab(new TextView(getActivity()), "Ekspor Down");
		
		mTabHost.setCurrentTab(0);	    
		reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_impup.php");
		
	    //ViewTable(v);

		
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
		        @Override
 		        public void onTabChanged(String tabId) {
		            //updateUi();
		    		if (tabId.compareTo("Ekspor Down") == 0) {

		    			reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_eksdown.php");
		    		}
		    		else if (tabId.compareTo("Ekspor Up") == 0){
		    			reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_eksup.php");
		    		}
		    		else if (tabId.compareTo("Impor Down") == 0){
		    			reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_impdown.php");
		    		}
		    		else if (tabId.compareTo("Impor Up") == 0){
		    			reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_impup.php");
		    		
		    		}

		        }
		    });

		
		init(v);

		
		return v;
	}

  
    public void updateUi()
	{
    	//-----------------------
	}

	public void setCurrentTab(int nIdx)
	{
    	mTabHost.setCurrentTab(nIdx);
	}

    
  
	private void init(View v) {
       
         tv_title = (TextView) v.findViewById(R.id.tv_title);
         tv_title.setText("Berita");
       
        
	}
    private void setupTabHost(View v) {
		mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
		mTabHost.setup();
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

	public void switchTab(int tab){
		mTabHost.setCurrentTab(tab);
}

	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	public void LoadTableData(String url) {
	    String result = null;
	   	InputStream is = null;
	  //1). COBA KONEKSI -------------------------------------------------------
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

		//2).Convert response to string -------------------------------------------------
		try
		{
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) 
		        {
		                sb.append(line + "\n");
		        }
		        is.close();

		        result=sb.toString();
		}
		catch(Exception e)
		{
		       Log.e("log_tag", "Error converting result "+e.toString());
		}

		//3). Parse json data
		try
		{

		JSONArray jArray = new JSONArray(result);
		//String[] stringArray = new stringArray[jArray.length];
	  
		//initial Awal ListView ditaruh kode dibawah ini ....
	    
		int flag=1;
		 
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
			        	    
	      	             // Log.i("log_tag","Kode HS: "+json_data.getInt("kd_hs")+
	      	             //           ", Uraian: "+json_data.getString("uraian")+
	      	              //          ", Forecast: "+json_data.getInt("forecast") + json_data.getString("status"));
	      	              
	      	              //LOAD DATA TABEL KE LIST (Array)
	      	            //Cek status
	      	            if (json_data.getString("status")=="EksporDown"){  
	      	            list_eksdown.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
	      	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
	      	    				" )");
	      	            Log.i("log_tag",list_eksdown.get(i));
			           }
	      	            
	      	            if (json_data.getString("status")=="EksporUp"){  
	      	            list_eksup.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
	      	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
	      	    				" )");
			           }

	      	            if (json_data.getString("status")=="ImporDown"){  
	      	            list_impdown.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
	      	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
	      	    				" )");
			           }

	      	            if (json_data.getString("status")=="ImporUp"){  
	      	            list_impup.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
	      	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
	      	    				" )");
			           }

	      	       }
	            }
		}
		catch(JSONException e)
		{
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}

		
		//4). 

/*
		ArrayList<String> list = new ArrayList<String>();     
		JSONArray jsonArray = (JSONArray)jsonObject; 
		if (jsonArray != null) { 
			int len = jsonArray.length();
			for (int i=0;i<len;i++){ 
				list.add(jsonArray.get(i).toString());
			} 
		} 
*/

		
		//return list;
	}
	
	
	public void ViewTable(View v) {
	
		//lvKeyword = (ListView) v.findViewById(R.id.lvSource);
		//listKeyword = new ArrayList<Keyword>();
		
		//GET DATA DARI LIST Contoh list_eksup
/*
		listKeyword.add(addKeyword(list_eksup.get(1)));
		
		adapterKeyword = new KeywordAdapter(getActivity(), listKeyword);
		lvKeyword.setAdapter(adapterKeyword);
*/
		Log.e("log_tag", list_eksup.get(1));
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
	

	//String re=jArray.getString(jArray.length()-1);
	
  
	lvKeyword = (ListView) v.findViewById(R.id.lvSource);
	listKeyword = new ArrayList<Keyword>();

	prog_one = (ProgressWheel) v.findViewById(R.id.prog_one);
	prog_two = (ProgressWheel) v.findViewById(R.id.prog_two);
	prog_three = (ProgressWheel) v.findViewById(R.id.prog_three);
	
	
    tv_prog_one = (TextView) v.findViewById(R.id.tv_prog_one);
    tv_prog_two = (TextView) v.findViewById(R.id.tv_prog_two);
    tv_prog_three = (TextView) v.findViewById(R.id.tv_prog_three);

    tv_prog_one_lblup = (TextView) v.findViewById(R.id.textView2);
    tv_prog_one_lbldown = (TextView) v.findViewById(R.id.textView3);
   
    tv_prog_two_lblup = (TextView) v.findViewById(R.id.textView4);
    tv_prog_two_lbldown = (TextView) v.findViewById(R.id.textView5);
    
    tv_prog_three_lblup = (TextView) v.findViewById(R.id.textView6);
    tv_prog_three_lbldown = (TextView) v.findViewById(R.id.textView7);
   
    prog_two.setProgress((int)(33*3.6f));  	// Same above
    tv_prog_two.setText(33+"");
    
    prog_three.setProgress((int)(20*3.6f));
    tv_prog_three.setText(20+"");

   
	int flag=1;
	 
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
		        	    
      	              Log.i("log_tag","Kode HS: "+json_data.getInt("kd_hs")+
      	                        ", Uraian: "+json_data.getString("uraian")+
      	                        ", Forecast: "+json_data.getInt("forecast"));
      	       
		            
      	    		listKeyword.add(addKeyword(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
      	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
      	    				" )"));
		            
    	              //LOAD DATA TABEL KE LIST (Array)
    	            //Cek status
    	            if (json_data.getString("status")=="EksporDown"){  
    	            list_eksdown.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
    	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
    	    				" )");
    	            Log.i("log_tag",list_eksdown.get(i));
		           }
    	            
    	            if (json_data.getString("status")=="EksporUp"){  
    	            list_eksup.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
    	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
    	    				" )");
		           }

    	            if (json_data.getString("status")=="ImporDown"){  
    	            list_impdown.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
    	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
    	    				" )");
		           }

    	            if (json_data.getString("status")=="ImporUp"){  
    	            list_impup.add(json_data.getString("kd_hs")+" : "+ json_data.getString("uraian")+
    	    				" ( lgst: "+json_data.getString("logest_berat") + "; frcs: "+json_data.getString("forecast")+
    	    				" )");
		           }
		            
		            
    	            if (i==0){  
    	            prog_one.setProgress((int)(json_data.getInt("logest_berat")*3.6f)); 	// Set progress 50%
    	            tv_prog_one.setText(json_data.getInt("logest_berat")+"");				// Set value 50 for TextView
    	            tv_prog_one_lblup.setText(json_data.getString("kd_hs")+"");	
    	            tv_prog_one_lbldown.setText(json_data.getString("uraian")+"");	
    	    	            
    	            }

    	            if (i==1){  
    	            prog_two.setProgress((int)(json_data.getInt("logest_berat")*3.6f)); 	// Set progress 50%
    	            tv_prog_two.setText(json_data.getInt("logest_berat")+"");				// Set value 50 for TextView
    	            tv_prog_two_lblup.setText(json_data.getString("kd_hs")+"");	
    	            tv_prog_two_lbldown.setText(json_data.getString("uraian")+"");	
    	    	            
    	            }
    	            if (i==2){  
    	            prog_three.setProgress((int)(json_data.getInt("logest_berat")*3.6f)); 	// Set progress 50%
    	            tv_prog_three.setText(json_data.getInt("logest_berat")+"");				// Set value 50 for TextView
    	            tv_prog_three_lblup.setText(json_data.getString("kd_hs")+"");	
    	            tv_prog_three_lbldown.setText(json_data.getString("uraian")+"");	
    	    	           
    	            }

		            
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
	}

	private Keyword addKeyword(String text) {
		Keyword keyword = new Keyword();
		keyword.setText(text);
		//keyword.setText(text);
		return keyword;
	}
  
/*	
	@Override
    public void onTabSelected(Tab tab) {
        Object tag = tab.getTag();
        for (int i=0; i<mTabs.size(); i++) {
            if (mTabs.get(i) == tag) {
                mViewPager.setCurrentItem(i);
            }
        }
    }
*/
	
}
