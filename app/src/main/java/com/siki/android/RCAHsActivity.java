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

import com.siki.android.adapters.RCAHsItemAdapter;
import com.siki.android.models.RCAHs;
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

public class RCAHsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {
    public static final String TAG = "RCAHsActivity";
    public static final String KEY_DITJEN = "ditjen";
    public static final String KEY_GROUP = "group";
    public static final String KEY_RCA = "rca";

    private RCAHsItemAdapter mAdapter;
    private List<RCAHs> pdHsList = new ArrayList<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private String mDitjen;
    private String mGroup;
    private String mPilihan;
    private RCA mPd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcaa);

        mDitjen = getIntent().getStringExtra(KEY_DITJEN);
        mGroup = getIntent().getStringExtra(KEY_GROUP);
        //mPilihan = getIntent().getStringExtra(KEY_PILIHAN);
        mPd = getIntent().getParcelableExtra(KEY_RCA);

        mAdapter = new RCAHsItemAdapter(this, pdHsList);
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
        titleTextView.setText(mPd.group);

        Log.d(TAG, "onCreate");
    }

    private void loadData() {
        PopupUtil.showLoading(this, "", "Loading data ...");

        //String pilihan = mPilihan;
        String ditjen = mDitjen;
        String group = mPd.group;

        String url = null;



            url = Globals.base_host_bigdata + "get_data_group_rca_bulanan?" +
                    "group=" + group;



        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAHsActivity.this, "Error load data", PopupUtil.SHORT);
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
                        String uraian = row.getString("Uraian");
                        String rca = row.getString("RCA");
                        String rsca = row.getString("RSCA");
                        String tbi = row.getString("TBI");

                        RCAHs pdHs = new RCAHs();
                        pdHs.kodeHS = kodeHS;
                        pdHs.uraian = uraian;
                        pdHs.rca = rca;
                        pdHs.rsca = rsca;
                        pdHs.tbi = tbi;

                        pdHsList.add(pdHs);
                    }
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
        RCAHs pdHs = pdHsList.get(i);

        Intent intent = new Intent(this, RCAHsDetailActivity.class);
        intent.putExtra(RCAHsDetailActivity.KEY_PILIHAN, mPilihan);
        intent.putExtra(RCAHsDetailActivity.KEY_PDHS, pdHs);
        intent.putExtra(RCAHsDetailActivity.KEY_PD, mPd);
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
            //openChart();
        }
    }


}
