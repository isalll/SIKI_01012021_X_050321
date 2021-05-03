package com.siki.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;


import com.siki.library.JSONParser;
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

public class GraphActivity extends Fragment {
	JSONArray grapik = null;

	private static final String AR_JUDUL = "kode";
	private static final String AR_CONTENT = "komentar";
	private static final String AR_USER = "username";
	private static final String AR_DATE = "date";
	private static final String AR_TIME = "time";

	private ProgressWheel prog_one, prog_two, prog_three, prog_four;
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_prog_four, tv_title;
	private TextView tv_prog_one_lblup,tv_prog_one_lbldown,tv_prog_two_lblup,tv_prog_two_lbldown,tv_prog_three_lblup,tv_prog_three_lbldown,tv_prog_four_lbldown;

	private static String url_dsb_impup   = Globals.url_lonjakan+"?kon=ImporUp";
	private static String url_dsb_impdown = Globals.url_lonjakan+"?kon=ImporDown";
	private static String url_dsb_eksdown = Globals.url_lonjakan+"?kon=ExsporDown";
	private static String url_dsb_eksup   = Globals.url_lonjakan+"?kon=ExsporUp";

	private FragmentManager dialog;
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

    		final View v = inflater.inflate(R.layout.activity_graph, container, false);

    		
    		setupTabHost(v);

    		setupTab(new TextView(getActivity()), "Top 5 lonjakan (kode HS : 2 Digit)");
    		mTabHost.setCurrentTab(0);	    
    		//reLoadTable(v,"http://iris.kemenperin.go.id/android/dsb_impup.php");
			//reLoadTable(v,"http://192.168.43.8/iris/android/dsb_impup.php");
			//reLoadTable(v,"http://boemikawani.com/iris/android/dsb_impup.php");
			reLoadTable(v, url_dsb_impup);
			if(Globals.dash_graph=="ImportUp"){
				progress2 = (LinearLayout) v.findViewById(R.id.prog2);
				progress2.setVisibility(View.INVISIBLE);

				progress3 = (LinearLayout) v.findViewById(R.id.prog3);
				progress3.setVisibility(View.INVISIBLE);

				progress4 = (LinearLayout) v.findViewById(R.id.prog4);
				progress4.setVisibility(View.INVISIBLE);

				reLoadTable(v, url_dsb_impup);
			}else if(Globals.dash_graph=="ImportDown"){
				progress1 = (LinearLayout) v.findViewById(R.id.prog1);
				progress1.setVisibility(View.INVISIBLE);

				progress3 = (LinearLayout) v.findViewById(R.id.prog3);
				progress3.setVisibility(View.INVISIBLE);

				progress4 = (LinearLayout) v.findViewById(R.id.prog4);
				progress4.setVisibility(View.INVISIBLE);

				reLoadTable(v, url_dsb_impdown);
			}else if(Globals.dash_graph=="ExportUp"){

				progress1 = (LinearLayout) v.findViewById(R.id.prog1);
				progress1.setVisibility(View.INVISIBLE);

				progress2 = (LinearLayout) v.findViewById(R.id.prog2);
				progress2.setVisibility(View.INVISIBLE);

				progress3 = (LinearLayout) v.findViewById(R.id.prog3);
				progress3.setVisibility(View.INVISIBLE);

				reLoadTable(v, url_dsb_eksup);
			}else if(Globals.dash_graph=="ExportDown"){
				progress1 = (LinearLayout) v.findViewById(R.id.prog1);
				progress1.setVisibility(View.INVISIBLE);

				progress2 = (LinearLayout) v.findViewById(R.id.prog2);
				progress2.setVisibility(View.INVISIBLE);

				progress4 = (LinearLayout) v.findViewById(R.id.prog4);
				progress4.setVisibility(View.INVISIBLE);

				reLoadTable(v, url_dsb_eksdown);
			}


			init(v);

			return v;
    	}
      
    	private void init(View v) {           
             tv_title = (TextView) v.findViewById(R.id.tv_title);
             tv_title.setText(Globals.dash_graph);
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
				prog_two = (ProgressWheel) v.findViewById(R.id.prog_two);
				prog_three = (ProgressWheel) v.findViewById(R.id.prog_three);
				prog_four = (ProgressWheel) v.findViewById(R.id.prog_four);


				tv_prog_one = (TextView) v.findViewById(R.id.tv_prog_one);
				tv_prog_two = (TextView) v.findViewById(R.id.tv_prog_two);
				tv_prog_three = (TextView) v.findViewById(R.id.tv_prog_three);
				tv_prog_four = (TextView) v.findViewById(R.id.tv_prog_four);

				tv_prog_one_lbldown = (TextView) v.findViewById(R.id.textView3);

				tv_prog_two_lbldown = (TextView) v.findViewById(R.id.textView5);

				tv_prog_three_lbldown = (TextView) v.findViewById(R.id.textView7);

				tv_prog_four_lbldown = (TextView) v.findViewById(R.id.textView8);

				prog_two.setProgress((int)(33*3.6f));  	// Same above
				tv_prog_two.setText(33+"");

				prog_three.setProgress((int)(20*3.6f));
				tv_prog_three.setText(20+"");
        	//String re=jArray.getString(jArray.length()-1);
        	
          
        	lvKeyword = (ListView) v.findViewById(R.id.lvSource);
        	listKeyword = new ArrayList<Keyword>();
        	
           
        	int flag=1;
				for(int i=1;i<=4;i++){
					JSONParser jParser = new JSONParser();
					JSONObject json = jParser.AmbilJson(Globals.url_graph+"?kon=graph"+i);
					try{
						grapik = json.getJSONArray("dash");
						JSONObject ad = grapik.getJSONObject(0);

						if (i==1){
							prog_one.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
							tv_prog_one.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
							tv_prog_one_lbldown.setText(ad.getString(AR_DATE)+"");

						}

						if (i==2){
							prog_two.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
							tv_prog_two.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
							tv_prog_two_lbldown.setText(ad.getString(AR_DATE)+"");

						}
						if (i==3){
							prog_three.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
							tv_prog_three.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
							tv_prog_three_lbldown.setText(ad.getString(AR_DATE)+"");

						}
						if (i==4){
							prog_four.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
							tv_prog_four.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
							tv_prog_four_lbldown.setText(ad.getString(AR_DATE)+"");

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
                           Toast.makeText(getActivity(),  "Kode hs Selected : "+separated[0],   Toast.LENGTH_LONG).show();
                       		Globals.kode_berita = separated[0];
						   Globals.fragment = "fragment1";
						   Intent intent = new Intent(getActivity(), MainActivity.class);
						   startActivity(intent);

						   /*Bundle arguments = new Bundle();
						   DetailGraphFragment detailFragment = new DetailGraphFragment();
						   detailFragment.setArguments(arguments);
						   detailFragment.show(getFragmentManager(),DetailGraphFragment.ARG_ITEM_ID);*/
                       }
               });
        }
        
        private Keyword addKeyword(String text) {
    		Keyword keyword = new Keyword();
    		keyword.setText(text);
    		//keyword.setText(text);
    		return keyword;
    	}
	/*public void onBackPressed() {
		Globals.fragment = "";
		Intent intent = new Intent(getActivity(), MainActivity.class);
		startActivity(intent);
	}*/

}