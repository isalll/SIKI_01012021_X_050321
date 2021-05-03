package com.siki.android;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.siki.android.adapters.EWSHsItemAdapter;
import com.siki.android.models.EWSHs;
import com.siki.android.models.EWS;
import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EWSHsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {
    public static final String TAG = "EWSHsActivity";
    public static final String KEY_DITJEN = "ditjen";
    public static final String KEY_PILIHAN = "pilihan";
    public static final String KEY_PD = "pd";

    private EWSHsItemAdapter mAdapter;
    private List<EWSHs> pdHsList = new ArrayList<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private String mDitjen;
    //private String mPilihan;
    private EWS mPd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewsa);

        mDitjen = getIntent().getStringExtra(KEY_DITJEN);
        //mPilihan = getIntent().getStringExtra(KEY_PILIHAN);
        mPd = getIntent().getParcelableExtra(KEY_PD);   //Parcel

        mAdapter = new EWSHsItemAdapter(this, pdHsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        if(ConnectivityUtil.isConnected(this)) {
            loadData();
        }
        else {
            PopupUtil.showMsg(this, "Can't load data, no internet connection", PopupUtil.SHORT);
        }

        //Button chartButton = (Button) findViewById(R.id.button_chart);
        //chartButton.setOnClickListener(this);

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(mPd.judul);

        Log.d(TAG, "onCreate");
    }

    private void loadData() {
        PopupUtil.showLoading(this, "", "Loading data ...");

        //String pilihan = mPilihan;
        //String tahun1 = mPd.tahun1;
        //String tahun2 = mPd.tahun2;
        //String bulan1 = mPd.bulan1;
        //String bulan2 = mPd.bulan2;
        String ditjen = mDitjen;
        String url = null;

        try {

            url = Globals.base_host_bigdata + "get_data_ews_lonjakan_tahunan?" +
                    "&ditjen=" + URLEncoder.encode(ditjen, "UTF-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(url == null) {
            return;
        }

        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(EWSHsActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                pdHsList.clear();
                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");


                    for(int i = 0; i < data.length(); i++) {
                        JSONObject row = data.getJSONObject(i);

                        String kodeHS = row.getString("Kode_HS");
                        String deskripsi = row.getString("Deskripsi");
                        String logest_berat = row.getString("Trend_Volume");
                        String rata_rata_berat = row.getString("Rata_rata_berat_3th");
                        //String bec = row.getString("bec");
                        //String kelompok = row.getString("kelompok");

                        EWSHs pdHs = new EWSHs();
                        pdHs.kodeHS = kodeHS;
                        pdHs.deskripsi = deskripsi;
                        pdHs.lonjakan = logest_berat;
                        pdHs.berat = rata_rata_berat;
                        //pdHs.bec = bec;
                        //pdHs.kelompok = kelompok;

                        pdHsList.add(pdHs);
                    }



                    /* OK ---> object json array
                    JSONArray kodehsJson = json.getJSONArray("kd_hs");
                    JSONArray deskripsiJson = json.getJSONArray("deskripsi");
                    JSONArray logestJson = json.getJSONArray("logest_berat");
                    JSONArray beratJson = json.getJSONArray("rata_rata_berat");

                    for(int i = 0; i < kodehsJson.length(); i++) {
                        String kodeHS = kodehsJson.getString(i);
                        String deskripsi = deskripsiJson.getString(i);
                        String logest_berat = logestJson.getString(i);
                        String rata_rata_berat = beratJson.getString(i);

                        EWSHs pdHs = new EWSHs();
                        pdHs.kodeHS = kodeHS;
                        pdHs.deskripsi = deskripsi;
                        pdHs.lonjakan = logest_berat;
                        pdHs.berat = rata_rata_berat;
                        pdHsList.add(pdHs);

                    }


                     */

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        EWSHs EWShs = pdHsList.get(i);

        Intent intent = new Intent(this, EWSHsDetailActivity.class);
        //intent.putExtra(EWSHsDetailActivity.KEY_PILIHAN, mPilihan);
        intent.putExtra(EWSHsDetailActivity.KEY_PDHS, EWShs);
        intent.putExtra(EWSHsDetailActivity.KEY_PD, mPd);
        intent.putExtra("title", "Detail Hs");
        startActivity(intent);
    }



    public void kembali(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.button_chart) {
            openChart();
        }
    }

    private void openChart() {
        Intent intent = new Intent(this, EWSChartActivity.class);
        intent.putExtra(EWSChartActivity.KEY_DITJEN, mDitjen);
        //intent.putExtra(EWSChartActivity.KEY_PILIHAN, mPilihan);
        intent.putExtra(EWSChartActivity.KEY_PD, mPd);
        startActivity(intent);
    }
}
