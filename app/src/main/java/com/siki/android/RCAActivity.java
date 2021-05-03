package com.siki.android;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.siki.android.adapters.RCAItemAdapter;
import com.siki.android.models.RCA;
import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;

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

public class RCAActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TAG = "RCAActivity";
    public static final String KEY_DITJEN = "ditjen";
    public static final String KEY_TITLE = "title";

    private String mDitjen;
    private Spinner mTahunSpinner;
    private Spinner mNegaraSpinner;
    private Spinner mRentangSpinner;
    private RCAItemAdapter mAdapter;
    private List<RCA> mPds = new ArrayList<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

   // private List<String> tahuns = new ArrayList<>();
    //private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rca);

        mDitjen = getIntent().getStringExtra("ditjen");
        String title = getIntent().getStringExtra("title");

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(title);

        getPeriode();
        getNegara();


        Button searchButton = (Button) findViewById(R.id.button_search_rca);
        searchButton.setOnClickListener(this);

        ListView listView = (ListView) findViewById(R.id.list_view);
        mAdapter = new RCAItemAdapter(this, mPds);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.button_search_rca) {
            if(ConnectivityUtil.isConnected(this)) {
                search();
            }
            else {
                PopupUtil.showMsg(this, "No internet connection", PopupUtil.SHORT);
            }
        }
    }


    public void getPeriode() {
        //PopupUtil.showLoading(this, "Loading ...", "");
        String url = null;
        url = Globals.base_host_bigdata  + "get_periode_rca?" +
                    "req=1";
         Log.d(TAG, "url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                try {

                    JSONObject json = new JSONObject(body);
                    JSONArray periodeJson = json.getJSONArray("periode");

                    final List<String> periodex = new ArrayList<>();

                    for(int i = 0; i < periodeJson.length(); i++) {
                        String periode = periodeJson.getString(i);
                        periodex.add(periode);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, periodex);
                            mTahunSpinner = (Spinner) findViewById(R.id.spinner_periode_rca);
                            mTahunSpinner.setAdapter(adapter);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        });



    }

    public void getNegara() {
        //PopupUtil.showLoading(this, "Loading ...", "");
        String url = null;
        url = Globals.base_host_bigdata  + "get_negara_rca?" +
                "req=1";
        Log.d(TAG, "url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                try {

                    JSONObject json = new JSONObject(body);
                    JSONArray negaraJson = json.getJSONArray("negara");

                    final List<String> negaralist = new ArrayList<>();

                    for(int i = 0; i < negaraJson.length(); i++) {
                        String negara = negaraJson.getString(i);
                        negaralist.add(negara);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, negaralist);
                            mNegaraSpinner = (Spinner) findViewById(R.id.spinner_negara_rca);
                            mNegaraSpinner.setAdapter(adapter);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        });
    }

    private void search() {
        PopupUtil.showLoading(this, "Loading ...", "");
        String periode = mTahunSpinner.getSelectedItem().toString();
        String negara = mNegaraSpinner.getSelectedItem().toString();
        //final int rentang = getRentang();

        String url = null;


            url = Globals.base_host_bigdata  + "get_data_rca_bulanan?" +
                    "periode=" + periode +
                  "&negara=" + negara +
                    "&ditjen=" + mDitjen;

        if(url == null) {
           // return;
        }

        Log.d(TAG, "url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                try {
                    mPds.clear();
                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");

                    for(int i = 0; i < data.length(); i++) {
                        JSONObject row = data.getJSONObject(i);

                        String group = row.getString("group");
                        String uraian = row.getString("uraian");
                        String jumlah = row.getString("jumlah");

                        RCA pd = new RCA();
                        pd.group = group;
                        pd.uraian = uraian;
                        pd.jumlah = jumlah;

                        mPds.add(pd);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RCA rca = mPds.get(i);
       // String komoditas = mKomoditasSpinner.getSelectedItem().toString();

        Intent intent = new Intent(this, RCAHsActivity.class);
        intent.putExtra(RCAHsActivity.KEY_DITJEN, mDitjen);
        //intent.putExtra(RCAHsActivity.KEY_GROUP, rca.group);
        //intent.putExtra(PdHsActivity.KEY_PILIHAN, komoditas);
        intent.putExtra(RCAHsActivity.KEY_RCA, rca);
        startActivity(intent);
    }

    public void kembali(View view) {
        finish();
    }
}
