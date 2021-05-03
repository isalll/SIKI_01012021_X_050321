package com.siki.android;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.siki.android.adapters.EWSItemAdapter;
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
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EWSActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TAG = "EWSActivity";
    public static final String KEY_DITJEN = "ditjen";
    public static final String KEY_TITLE = "title";

    private String mDitjen;
    private Spinner mTahunSpinner;
    private Spinner mKomoditasSpinner;
    private Spinner mRentangSpinner;
    private EWSItemAdapter mAdapter;
    private List<EWS> mPds = new ArrayList<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ews);

        mDitjen = getIntent().getStringExtra("ditjen");
        String title = getIntent().getStringExtra("title");

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(title);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<String> tahuns = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            tahuns.add(Integer.toString(year - i));
        }

        /*
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tahuns);
        mTahunSpinner = (Spinner) findViewById(R.id.spinner_year);
        mKomoditasSpinner = (Spinner) findViewById(R.id.spinner_komoditas);
        mRentangSpinner = (Spinner) findViewById(R.id.spinner_rentang);


         */
        ListView listView = (ListView) findViewById(R.id.list_view);


        //mTahunSpinner.setAdapter(adapter);
        Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(this);

        mAdapter = new EWSItemAdapter(this, mPds);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.button_search) {
            if(ConnectivityUtil.isConnected(this)) {
                search();
            }
            else {
                PopupUtil.showMsg(this, "No internet connection", PopupUtil.SHORT);
            }
        }
    }

    private void search() {
        PopupUtil.showLoading(this, "Loading ...", "");
        //String tahun = mTahunSpinner.getSelectedItem().toString();
        //String komoditas = mKomoditasSpinner.getSelectedItem().toString();
        //final int rentang = getRentang();

        String url = null;

        try {
            url = Globals.base_host_bigdata   + "get_data_ews_tahunan?"  +
                    "ditjen=" + URLEncoder.encode(mDitjen, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(url == null) {
            return;
        }

        Log.d(TAG, "url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(EWSActivity.this, "Error load data", PopupUtil.SHORT);
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

                        String b1 = row.getString("bln_min");
                        String b2 = row.getString("bln_max");
                        String t1 = row.getString("thn_min");
                        String t2 = row.getString("thn_max");
                        //String period = row.getString("period");

                        int bb1 = Integer.parseInt(b1);
                        int bb2 = Integer.parseInt(b2);

                        //String title = String.format("%s Bulan: %s %s s/d %s %s", period, DateUtil.toMonth(bb1),
                        //        t1, DateUtil.toMonth(bb2), t2);

                        String title = String.format("Periode Tahunan dari Tahun: %s s/d %s",t1, t2);

                        String jml = row.getString("jumlah_30");
                        String tot = row.getString("jumlah_total");
                        String maks = row.getString("maksimal");
                        //String maxLog = row.getString("maxlog");
                        //String m1 = row.getString("m1");
                        //String m2 = row.getString("m2");
                        //String m3 = row.getString("m3");

                        EWS pd = new EWS();
                        pd.judul = title;
                        pd.jumlahData = tot;
                        pd.lonjakanTerbesar = maks;
                        pd.lonjakan30 = jml;
                        //pd.lonjakan20 = m2;
                        //pd.lonjakan15 = m3;
                        pd.bulan1 = b1;
                        pd.bulan2 = b2;
                        pd.tahun1 = t1;
                        pd.tahun2 = t2;

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
        EWS pd = mPds.get(i);
        //String komoditas = mKomoditasSpinner.getSelectedItem().toString();

        Intent intent = new Intent(this, EWSHsActivity.class);
        intent.putExtra(EWSHsActivity.KEY_DITJEN, mDitjen);
        //intent.putExtra(EWSHsActivity.KEY_PILIHAN, komoditas);
        intent.putExtra(EWSHsActivity.KEY_PD, pd);
        startActivity(intent);
    }

    public void kembali(View view) {
        finish();
    }
}
