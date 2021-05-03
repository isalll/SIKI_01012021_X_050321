package com.siki.android;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.siki.android.models.PdHs;
import com.siki.android.models.PeringatanDini;
import com.siki.android.utils.DateUtil;
import com.siki.android.utils.PopupUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RCAHsDetailActivity extends AppCompatActivity {
    private static final String TAG = "PdHsDetailActivity";
    public static final String KEY_PILIHAN = "pilihan";
    public static final String KEY_PDHS = "pdhs";
    public static final String KEY_PD = "pd";

    private PeringatanDini mPd;
    private PdHs mPdhs;
    private String mPilihan;

    private TextView mKodeHsTextView;
    private TextView mTitleHsTextView;
    private TextView mForecastTextView;
    private TextView mUraianTextView;
    private TextView mUraianIndoTextView;
    private TextView mKomoditasTextView;
    private TextView mBoardTextView;
    private TextView kbliTextView;
    private TextView tarifMfnTextView;
    private TextView tarifFtaTextView;
    private TextView bmadTextView;
    private TextView bmtpTextView;
    private TextView sniWajibTextView;
    private TextView mTitleChart1TextView;
    private TextView mTitleChart2TextView;
    private TextView mTitleChart3TextView;
    private PieChart mChart1;
    private LineChart mChart2;
    private LineChart mChart3;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rca_hs_detail);

        mPilihan = getIntent().getStringExtra(KEY_PILIHAN);
        mPdhs = getIntent().getParcelableExtra(KEY_PDHS);
        mPd = getIntent().getParcelableExtra(KEY_PD);

        String title = getIntent().getStringExtra("title");

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(title);

        mTitleHsTextView = (TextView) findViewById(R.id.tv_title_hs);
        mKodeHsTextView = (TextView) findViewById(R.id.tv_kode_hs);
        mForecastTextView = (TextView) findViewById(R.id.tv_title_forecast);
        mUraianTextView = (TextView) findViewById(R.id.tv_uraian_hs);
        mUraianIndoTextView = (TextView) findViewById(R.id.tv_uraian_indo);
        mKomoditasTextView = (TextView) findViewById(R.id.tv_komoditas);
        mBoardTextView = (TextView) findViewById(R.id.tv_broad);
        kbliTextView = (TextView) findViewById(R.id.tv_kbli);
        tarifMfnTextView = (TextView) findViewById(R.id.tv_mfn);
        tarifFtaTextView = (TextView) findViewById(R.id.tv_tarif_fta);
        bmadTextView = (TextView) findViewById(R.id.tv_bmtp);
        bmtpTextView = (TextView) findViewById(R.id.tv_bmtp);
        sniWajibTextView = (TextView) findViewById(R.id.tv_sni_wajib);
        mTitleChart1TextView = (TextView) findViewById(R.id.tv_title_chart1);
        mTitleChart2TextView = (TextView) findViewById(R.id.tv_title_chart2);
        mTitleChart3TextView = (TextView) findViewById(R.id.tv_title_chart3);

        mChart1 = (PieChart) findViewById(R.id.chart1);
        mChart2 = (LineChart) findViewById(R.id.chart2);
        mChart3 = (LineChart) findViewById(R.id.chart3);

        mOkHttpClient = new OkHttpClient();

        if(mPdhs != null && !TextUtils.isEmpty(mPilihan) && mPd != null) {
            loadData();
            loadPieChart();
            loadLineChart();
        }
    }

    private void loadData() {
        PopupUtil.showLoading(this, "Loading ...", "");
        String id = mPdhs.kodeHS;
        String tahun1 = mPd.tahun1;
        String tahun2 = mPd.tahun2;
        String bulan1 = mPd.bulan1;
        String bulan2 = mPd.bulan2;

        String url = null;
/*
        url = "http://iris.kemenperin.go.id/android/ews/"  + "get_detail.php?" +
                "tahun1=" + tahun1 +
                "&tahun2=" + tahun2 +
                "&bulan1=" + bulan1 +
                "&bulan2=" + bulan2 +
                "&pilihan=" + mPilihan +
                "&id=" + id;
                */

        url = Globals.base_host   + "get_detail.php?" +
                "tahun1=" + tahun1 +
                "&tahun2=" + tahun2 +
                "&bulan1=" + bulan1 +
                "&bulan2=" + bulan2 +
                "&pilihan=" + mPilihan +
                "&id=" + id;




        Log.d(TAG, "url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAHsDetailActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONObject data = json.getJSONObject("data");

                    final String growth = data.getString("growth");
                    final Double forecast = data.getDouble("forecast");
                    final JSONObject pdHs = data.getJSONObject("hs");

                    final String kdHs = pdHs.getString("kd_hs");
                    final String uraian = pdHs.getString("uraian");
                    final String uraianIndo = pdHs.getString("uraian_indo");
                    final String komoditas = pdHs.getString("kd_komoditas");
                    final String kbli = pdHs.getString("kd_kbli09");
                    final String tarifMfn = pdHs.getString("tarif");
                    final String tarifFta = pdHs.getString("tarif_fta");
                    final String bmad = pdHs.getString("bmad");
                    final String bmtp = pdHs.getString("bmtp");
                    final String sni = pdHs.getString("no_wajib");
                    final String bec = pdHs.getString("nama_bec");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String title = String.format("Impor Detail HS %s: %s%%", kdHs, growth);

                            if(mPilihan.equals("Ekspor")) {
                                title = String.format("Ekspor Detail HS %s: %s%%", kdHs, growth);
                            }

                            mTitleHsTextView.setText(title);
                            mKodeHsTextView.setText(kdHs);
                            mForecastTextView.setText(Double.toString(forecast) + "%");
                            mUraianTextView.setText(uraian);
                            mUraianIndoTextView.setText(uraianIndo);
                            mKomoditasTextView.setText(komoditas);
                            mBoardTextView.setText(bec);
                            kbliTextView.setText(kbli);
                            tarifMfnTextView.setText(tarifMfn);
                            tarifFtaTextView.setText(tarifFta);
                            bmadTextView.setText(bmad);
                            bmtpTextView.setText(bmtp);
                            sniWajibTextView.setText(sni);
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

    private void loadPieChart() {
        String pilihan = mPilihan;
        String tahun1 = mPd.tahun1;
        String tahun2 = mPd.tahun2;
        String bulan1 = mPd.bulan1;
        String bulan2 = mPd.bulan2;
        String id = mPdhs.kodeHS;
        String url = null;

        String title = String.format("Share Negara Asal Impor %s %s s/d %s %s",
                DateUtil.toMonth(bulan1), tahun1,
                DateUtil.toMonth(bulan2), tahun2);

        if(mPilihan.equals("Expor")) {
            title = String.format("Share Negara Tujuan Ekspor %s %s s/d %s %s",
                    DateUtil.toMonth(bulan1), tahun1,
                    DateUtil.toMonth(bulan2), tahun2);
        }

        mTitleChart1TextView.setText(title);

        url = Globals.base_host   + "pie.php?" +
                "pilihan=" + pilihan +
                "&tahun1=" + tahun1 +
                "&tahun2=" + tahun2 +
                "&bulan1=" + bulan1 +
                "&bulan2=" + bulan2 +
                "&id=" + id;

        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAHsDetailActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                Random rnd = new Random();

                try {
                    JSONObject json = new JSONObject(body);
                    Iterator<String> keys = json.keys();

                    List<PieEntry> entries = new ArrayList<PieEntry>();

                    while(keys.hasNext()) {
                        String key = keys.next();
                        String val = json.getString(key);

                        entries.add(new PieEntry(Float.parseFloat(val), key));
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "");
                    /*
                    dataSet.setColors(new int[]{
                            Color.parseColor("#efb447"),
                            Color.parseColor("#84ff30"),
                            Color.parseColor("#d8635b")
                    });
                    */

                    dataSet.setColors(Color.YELLOW ,Color.RED,Color.BLUE, Color.GREEN, Color.MAGENTA,Color.CYAN,Color.WHITE,Color.GRAY);

                            //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    //dataSet.setColors(color);

                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(12);



                    final PieData pieData = new PieData(dataSet);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChart1.setData(pieData);
                            mChart1.invalidate();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadLineChart() {
        String pilihan = mPilihan;
        String tahun1 = mPd.tahun1;
        String tahun2 = mPd.tahun2;
        String bulan1 = mPd.bulan1;
        String bulan2 = mPd.bulan2;
        String id = mPdhs.kodeHS;
        String url = null;

        String title2 = String.format("Grafik Impor Berdasarkan Volume %s %s s/d %s %s",
                DateUtil.toMonth(bulan1), tahun1,
                DateUtil.toMonth(bulan2), tahun2);
        String title3 = String.format("Grafik Impor Berdasarkan Nilai %s %s s/d %s %s",
                DateUtil.toMonth(bulan1), tahun1,
                DateUtil.toMonth(bulan2), tahun2);

        if(mPilihan.equals("Expor")) {
            title2 = String.format("Grafik Ekspor Berdasarkan Volume %s %s s/d %s %s",
                    DateUtil.toMonth(bulan1), tahun1,
                    DateUtil.toMonth(bulan2), tahun2);
            title3 = String.format("Grafik Ekspor Berdasarkan Nilai %s %s s/d %s %s",
                    DateUtil.toMonth(bulan1), tahun1,
                    DateUtil.toMonth(bulan2), tahun2);
        }

        mTitleChart2TextView.setText(title2);
        mTitleChart3TextView.setText(title3);

        url = Globals.base_host  + "line.php?" +
                "pilihan=" + pilihan +
                "&tahun1=" + tahun1 +
                "&tahun2=" + tahun2 +
                "&bulan1=" + bulan1 +
                "&bulan2=" + bulan2 +
                "&id=" + id;

        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(RCAHsDetailActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "responsedonni:" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray labelJson = json.getJSONArray("periode");
                    JSONArray volumeJson = json.getJSONArray("volume");
                    JSONArray valueJson = json.getJSONArray("value");

                    List<Entry> entries1 = new ArrayList<Entry>();
                    List<Entry> entries2 = new ArrayList<Entry>();

                    for(int i = 0; i < labelJson.length(); i++) {
                        String lbl = labelJson.getString(i);
                        Double volume = volumeJson.getDouble(i);
                        Double value = valueJson.getDouble(i);

                        entries1.add(new Entry(i + 1, volume.floatValue()));
                        entries2.add(new Entry(i + 1, value.floatValue()));
                    }

                    LineDataSet dataSet1 = new LineDataSet(entries1, "Chart ");
                    LineDataSet dataSet2 = new LineDataSet(entries2, "Chart ");
                    dataSet1.setValueTextColor(Color.BLACK);

                    final LineData lineData1 = new LineData(dataSet1);
                    final LineData lineData2 = new LineData(dataSet2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChart2.setData(lineData1);
                            mChart2.invalidate();

                            mChart3.setData(lineData2);
                            mChart3.invalidate();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void kembali(View view) {
        finish();
    }
}
