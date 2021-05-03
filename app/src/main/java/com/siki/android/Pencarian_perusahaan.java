package com.siki.android;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.siki.android.sqlite.fragment.CustomPerDialogFragment;

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

public class Pencarian_perusahaan extends Fragment {
	private TextView tv_title;

	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	private EditText editcari;
	private static String urlcari = Globals.url_pencarian_perusahaan;
	private static String urlperusahaan = Globals.url_perusahaan;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		super.onCreateView(inflater, container, savedInstanceState);

    		final View v = inflater.inflate(R.layout.activity_pencarian_perusahaan, container, false);

			//radio button
			reLoadTable(v, urlperusahaan);
			editcari=(EditText)v.findViewById(R.id.editcari);
			editcari.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {


				}

				@Override
				public void onTextChanged(CharSequence cs, int i, int i1, int i2) {

					pencarian_perusahaan(v,cs.toString());
				}

				@Override
				public void afterTextChanged(Editable editable) {

				}
			});

			init(v);

			return v;
    	}
		public void pencarian_perusahaan(View a, String isi){

			reLoadTable(a, urlcari+"?cari="+isi);
		}
    	private void init(View v) {           
             tv_title = (TextView) v.findViewById(R.id.tv_title);
             tv_title.setText("Perusahaan");
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
								try {
									JSONObject json_data = jArray.getJSONObject(i);

									listKeyword.add(addKeyword(json_data.getString("id") + " : " + json_data.getString("nama")+"\n"+
																"No IUI : "+json_data.getString("no_iui")+"\n"+
																"Tgl IUI : "+json_data.getString("tgl_iui")+"\n"+
																"Tenaga Kerja Asing : "+json_data.getString("tenaga_kerja_asing")+"\n"+
																"Tenaga Kerja DN : "+json_data.getString("tenaga_kerja_dn")+"\n"+
																"Kd. Badan Hukum : "+json_data.getString("kd_badan_hukum")+"\n"+
																"NPWP : "+json_data.getString("npwp")+"\n"+
																"Produksi Utama : "+json_data.getString("produksi_utama")+"\n"+
																"Sektor : "+json_data.getString("sektor")));
								}catch (JSONException a){
									//a.printStackTrace();
									Log.e("Err",a.toString());
									//listKeyword.add(addKeyword(a.printStackTrace()));
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
                           //Toast.makeText(getActivity(),  "Berita Selected : "+separated[0],   Toast.LENGTH_LONG).show();
                       		//Globals.kode_berita = separated[0];

						   /*Bundle arguments = new Bundle();
						   DetailGraphFragment detailFragment = new DetailGraphFragment();
						   detailFragment.setArguments(arguments);
						   detailFragment.show(getFragmentManager(),DetailCariHSFragment.ARG_ITEM_ID);*/
						   Bundle arguments = new Bundle();
						   //arguments.putParcelable("selectedperusahaan", perusahaan);
						   CustomPerDialogFragment customPerDialogFragment = new CustomPerDialogFragment();
						   customPerDialogFragment.setArguments(arguments);
						   customPerDialogFragment.show(getFragmentManager(),CustomPerDialogFragment.ARG_ITEM_ID);
                       }
               });
        }
        
        private Keyword addKeyword(String text) {
    		Keyword keyword = new Keyword();
    		keyword.setText(text);
    		return keyword;
    	}

}