package com.siki.android;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.siki.library.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class ListCommentKlipingActivity extends ListActivity {
    //private static String link_url = "http://10.0.2.2/json-parsing/artikel-json.php";
    TextView kode,status,uraian,kelompok,id_kliping,judul,judulkliping,sumber,kd_hs;
    EditText inputSearch;
    //List Komen
    private static final String AR_IDKLIPING = "id_kliping";
    private static final String AR_JUDUL = "judul";
    private static final String AR_CONTENT = "komentar";
    private static final String AR_USER = "username";
    private static final String AR_UPDATETIME = "updatetime";
    //Detail kliping
    private static final String AR_URAIAN = "uraian";
    private static final String AR_KELOMPOK = "kelompok";

    private static final String AR_SUMBER = "sumber";
    private static final String AR_KATEGORI = "kategori";
    private static final String AR_KDHS = "kd_hs";


    private ListCommentKlipingActivity madapter;
    //refresh
    private int mInterval = 5000;
    private Handler handler;
    private String TAG = ListCommentKlipingActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;


    Button simpan,refresh;
    private ProgressWheel prog_one_visitor, prog_two_visitor;
    private TextView tv_prog_one_visitor, tv_prog_two_visitor, tv_title;
    EditText komen;
    ImageView kmbl;
    private ProgressDialog pDialog;int flag=0;
    JSONParser jsonParser = new JSONParser();
    //private static String url = "http://10.0.2.2/rdias/register.php";
    //private static String url = "http://boemikawani.com/iris/php/komen.php";
    private static String url = Globals.url_komenkliping;
    private static final String TAG_SUCCESS = "success";

    JSONArray kliping = null;
    JSONArray detail_kliping = null;
    ArrayList<HashMap<String, String>> daftar_kliping = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kliping_comment);
        komen =  (EditText) findViewById(R.id.editTextComent);

        id_kliping = (TextView) findViewById(R.id.id_kliping);
        id_kliping.setText(Globals.id_kliping);
        simpan=(Button)findViewById(R.id.saveComent);
        refresh = (Button) findViewById(R.id.refreshComent);
        kmbl = (ImageView) findViewById(R.id.back);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        //button simpan komen
        simpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Check all fields
                if(komen.length()<0)
                {
                    Toast.makeText(ListCommentKlipingActivity.this,"Please Enter Comment", Toast.LENGTH_LONG).show();
                    return;
                }
                //check connectivity
                if(!isOnline(ListCommentKlipingActivity.this))
                {
                    Toast.makeText(ListCommentKlipingActivity.this,"No network connection", Toast.LENGTH_LONG).show();
                    return;
                }

                //from login.java
                new loginAccess().execute();
            }
        });
        kmbl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Check all fields
                if(komen.length()<0)
                {
                    Toast.makeText(ListCommentKlipingActivity.this,"Please Enter Comment", Toast.LENGTH_LONG).show();
                    return;
                }
                //check connectivity
                if(!isOnline(ListCommentKlipingActivity.this))
                {
                    Toast.makeText(ListCommentKlipingActivity.this,"No network connection", Toast.LENGTH_LONG).show();
                    return;
                }

                //from login.java
                new back().execute();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Check all fields
                if(komen.length()<0)
                {
                    Toast.makeText(ListCommentKlipingActivity.this,"Please Enter Comment", Toast.LENGTH_LONG).show();
                    return;
                }
                //check connectivity
                if(!isOnline(ListCommentKlipingActivity.this))
                {
                    Toast.makeText(ListCommentKlipingActivity.this,"No network connection", Toast.LENGTH_LONG).show();
                    return;
                }

                //from login.java
                new refres().execute();
            }
        });
        /*JSONParser jParser = new JSONParser();
        JSONObject json_detail = jParser.AmbilJson(Globals.url_detailkliping+"?kode="+Globals.kode_kliping);

        // ini buat Detail kliping -------------------
        try {
            detail_kliping = json_detail.getJSONArray("kliping");
            for (int i = 0; i < kliping.length(); i++) {
                JSONObject dt = kliping.getJSONObject(i);

                tv_title = (TextView) findViewById(R.id.tv_title);
                tv_title.setText(dt.getString(AR_STATUS));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }*/
        //list komentar

        JSONParser jParser1 = new JSONParser();
        JSONObject json_detail = jParser1.AmbilJson(Globals.url_detailkliping+"?id_kliping="+Globals.id_kliping);
        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.AmbilJson(Globals.url_listkomenkliping+"?id_kliping="+Globals.id_kliping);

        try {
            detail_kliping = json_detail.getJSONArray("kliping");
            kliping = json.getJSONArray("kliping");

            //----------------- Detail kliping ------------------------//
            JSONObject ad = detail_kliping.getJSONObject(0);

                tv_title = (TextView) findViewById(R.id.tv_title);
                judulkliping = (TextView) findViewById(R.id.judulkliping);
                uraian = (TextView) findViewById(R.id.uraian);
                sumber = (TextView) findViewById(R.id.sumber);
                kd_hs = (TextView) findViewById(R.id.kd_hs);

                tv_title.setText("Forum Kliping Industri");
                judulkliping.setText(ad.getString(AR_JUDUL));
                sumber.setText(ad.getString(AR_SUMBER));
                kd_hs.setText(ad.getString(AR_KDHS));
                uraian.setText(ad.getString(AR_URAIAN));


            //----------------- List Komentar ------------------------//
            for (int i = 0; i < kliping.length(); i++) {
                JSONObject ar = kliping.getJSONObject(i);

                //String id = ar.getString(AR_ID);
                String judul = ar.getString(AR_IDKLIPING);
                String content = ar.getString(AR_CONTENT);
                String username = ar.getString(AR_USER);
                //String date = ar.getString(AR_DATE);
                //String time = ar.getString(AR_TIME);
                String updatetime = ar.getString(AR_UPDATETIME);
                HashMap<String, String> map = new HashMap<String, String>();

                //map.put(AR_ID, id);
                map.put(AR_IDKLIPING, judul);
                map.put(AR_CONTENT, content);
                map.put(AR_USER, username);
                //map.put(AR_DATE, date);
                map.put(AR_UPDATETIME, updatetime);

                daftar_kliping.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapter_listview();
    }
/*
    class MyTimerTask extends TimerTask {
        public void run() {
            // ERROR
            //hTextView.setText("Impossible");
            Intent intent = new Intent(getApplicationContext(),ListCommentKlipingActivity.class);
            startActivity(intent);
            finish();
            // how update TextView in link below
            // http://android.okhelp.cz/timer-task-timertask-run-cancel-android-example/

        }
    }*/
    private boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }


    public void adapter_listview() {
        //swipeRefreshLayout.setRefreshing(true);
        //MyTimerTask myTask = new MyTimerTask();
        //Timer myTimer = new Timer();
        //myTimer.schedule(myTask, 3000, 1500);
        ListAdapter adapter = new SimpleAdapter(this, daftar_kliping, R.layout.list_item_komen_kliping, new String[]{AR_CONTENT, AR_USER, AR_UPDATETIME}, new int[]{R.id.komentar, R.id.user, R.id.updatetime});
        setListAdapter(adapter);

        /*inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ListCommentKlipingActivity.this.madapter.getFilter().filter(cs);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                ListCommentKlipingActivity.this.adapter.getFilter().filter(arg0);
            }
        });
        //adapter.notifyAll();
        //swipeRefreshLayout.setRefreshing(false);
        /*ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kode = ((TextView) view.findViewById(R.id.kode)).getText().toString();

                Intent in = new Intent(ListCommentKlipingActivity.this, MainActivity.class);
                in.putExtra(AR_ID, kode);
                startActivity(in);

            }
        });*/
    }
    class loginAccess extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListCommentKlipingActivity.this);
            pDialog.setMessage("Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        // PROSES SIMPAN ----------------------------------
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String id = id_kliping.getText().toString();
            String coment = komen.getText().toString();
            String username = Globals.user_on;

            params.add(new BasicNameValuePair("id_kliping",id));
            params.add(new BasicNameValuePair("komen",coment));
            params.add(new BasicNameValuePair("username",username));

            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);

            Log.d("Create Response", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    flag=0;
                    //Intent i = new Intent(getApplicationContext(),ListCommentKlipingActivity.class);
                    Intent i = new Intent(getApplicationContext(),ListCommentKlipingActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    // failed to Sign in
                    flag=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(flag==1)
                Toast.makeText(ListCommentKlipingActivity.this,"Please Enter Correct informations", Toast.LENGTH_LONG).show();

        }

    }
    class refres extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListCommentKlipingActivity.this);
            pDialog.setMessage("Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        // PROSES SIMPAN ----------------------------------
        protected String doInBackground(String... arg0) {
            Intent i = new Intent(getApplicationContext(),ListCommentKlipingActivity.class);
            startActivity(i);
            finish();

            return null;
        }

    }
    class back extends AsyncTask<String, String, String> {


        @Override

        protected String doInBackground(String... arg0) {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();

            return null;
        }

    }
}
