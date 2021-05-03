package com.siki.android;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.siki.android.models.PeringatanDini;
import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;

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

public class PdChartActivity extends AppCompatActivity {
    public static final String TAG = "PdHsActivity";
    public static final String KEY_DITJEN = "ditjen";
    public static final String KEY_PILIHAN = "pilihan";
    public static final String KEY_PD = "pd";

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private String mDitjen;
    private String mPilihan;
    private PeringatanDini mPd;
    private PieChart mChart1;
    private PieChart mChart2;
    private PieChart mChart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pd_chart);

        mDitjen = getIntent().getStringExtra(KEY_DITJEN);
        mPilihan = getIntent().getStringExtra(KEY_PILIHAN);
        mPd = getIntent().getParcelableExtra(KEY_PD);

        mChart1 = (PieChart) findViewById(R.id.chart1);
        mChart2 = (PieChart) findViewById(R.id.chart2);
        mChart3 = (PieChart) findViewById(R.id.chart3);

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(mPd.judul);

        if(ConnectivityUtil.isConnected(this)) {
            loadData();
        }
        else {
            PopupUtil.showMsg(this, "Can't load data, no internet connection", PopupUtil.SHORT);
        }
    }

    private void loadData() {
        PopupUtil.showLoading(this, "", "Loading data ...");

        String pilihan = mPilihan;
        String tahun1 = mPd.tahun1;
        String tahun2 = mPd.tahun2;
        String bulan1 = mPd.bulan1;
        String bulan2 = mPd.bulan2;
        String ditjen = mDitjen;
        String url = null;

        try {
            //base_url = https://kemenperin.binerapps.com/modules/ews/


            url = Globals.base_host   + "pie_bec.php?" +
                    "pilihan=" + pilihan +
                    "&tahun1=" + tahun1 +
                    "&tahun2=" + tahun2 +
                    "&bulan1=" + bulan1 +
                    "&bulan2=" + bulan2 +
                    "&ditjen=" + URLEncoder.encode(ditjen, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(url == null) {
            return;
        }

        for(int i = 0; i < 3; i++) {
            final int index = i;
            String type = Integer.toString(i + 1);
            url += "&type=" + type;

            Log.d(TAG, "url:" + url);
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    PopupUtil.dismissDialog();
                    PopupUtil.showMsg(PdChartActivity.this, "Error load data", PopupUtil.SHORT);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    PopupUtil.dismissDialog();

                    String body = response.body().string();
                    Log.d(TAG, "response:" + body);

                    try {
                        JSONObject json = new JSONObject(body);
                        String barangKonsumsi = json.getString("Barang Konsumsi");
                        String bahanBaku = json.getString("Bahan Baku");
                        String barangModal = json.getString("Barang Modal");

                        float bk = Float.parseFloat(barangKonsumsi);
                        float bb = Float.parseFloat(bahanBaku);
                        float bm = Float.parseFloat(barangModal);

                        List<PieEntry> entries = new ArrayList<PieEntry>();
                        entries.add(new PieEntry(bk, "Barang Konsumsi"));
                        entries.add(new PieEntry(bb, "Bahan Baku"));
                        entries.add(new PieEntry(bm, "Barang Modal"));

                        PieDataSet dataSet = new PieDataSet(entries, "Chart " + (index + 1));
                        dataSet.setColors(new int[]{
                                Color.parseColor("#efb447"),
                                Color.parseColor("#84ff30"),
                                Color.parseColor("#d8635b")
                        });
                        dataSet.setValueTextColor(Color.BLACK);

                        final PieData pieData = new PieData(dataSet);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (index) {
                                    case 0:
                                        mChart1.setData(pieData);
                                        mChart1.invalidate();
                                        break;
                                    case 1:
                                        mChart2.setData(pieData);
                                        mChart2.invalidate();
                                        break;
                                    case 2:
                                        mChart3.setData(pieData);
                                        mChart3.invalidate();
                                        break;
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void kembali(View view) {
        finish();
    }
}
