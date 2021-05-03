package com.siki.android;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.siki.android.models.HSLINK;
import com.siki.android.models.HSLINKHs;
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

public class HSLinkDetailActivity extends AppCompatActivity {
    private static final String TAG = "HSLinkDetailActivity";
    public static final String KEY_PILIHAN = "pilihan";
    public static final String KEY_PDHS = "EWShs";
    public static final String KEY_PD = "EWS";
    public static final String KEY_KODEHS = "kodehs";

    JSONArray grapik,infodatabase = null;
    private static final String AR_THNMAX = "thn_max";
    private static final String AR_THNMIN = "thn_min";
    private static final String AR_BLNMAX = "bln_max";
    private static final String AR_BLNMIN = "bln_min";

    private HSLINK mPd;
    private HSLINKHs mPdhs;
    private String mPilihan;
    private String mKodeHS;

    private TextView mKodeHsTextView;
    private TextView mTitleHsTextView;
    private TextView mForecastTextView;
    private TextView mUraianTextView;
    private TextView mSektorTextView;
    private TextView mSubsektorTextView;
    private TextView mBoardTextView;
    private TextView kelompokTextView;
    private TextView ditjenTextView;
    private TextView beamasukTextView;

    private TextView mTitleChart1TextView;
    private TextView mTitleChart2TextView;
    private TextView mTitleChart3TextView;
    private PieChart mChart1;
    private LineChart mChart2;
    private LineChart mChart3;
    private OkHttpClient mOkHttpClient;
    public static final String ARG_ITEM_ID = "per_dialog_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hslink_hs_detail);

        mPilihan = getIntent().getStringExtra(KEY_PILIHAN);
        mKodeHS = getIntent().getStringExtra(KEY_KODEHS);

        mPdhs = getIntent().getParcelableExtra(KEY_PDHS);
        mPd = getIntent().getParcelableExtra(KEY_PD);

        String title = getIntent().getStringExtra("title");

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(title);

        mTitleHsTextView = (TextView) findViewById(R.id.tv_title_hs);
        mKodeHsTextView = (TextView) findViewById(R.id.tv_kode_hs);
        mForecastTextView = (TextView) findViewById(R.id.tv_title_forecast);
        mUraianTextView = (TextView) findViewById(R.id.tv_uraian_hs);
        mSektorTextView = (TextView) findViewById(R.id.tv_sektor);
        mSubsektorTextView = (TextView) findViewById(R.id.tv_subsektor);
        mBoardTextView = (TextView) findViewById(R.id.tv_broad);
        kelompokTextView = (TextView) findViewById(R.id.tv_kelompok);
        ditjenTextView = (TextView) findViewById(R.id.tv_ditjen);
        beamasukTextView = (TextView) findViewById(R.id.tv_bea_masuk);

        mTitleChart1TextView = (TextView) findViewById(R.id.tv_title_chart1);
        mTitleChart2TextView = (TextView) findViewById(R.id.tv_title_chart2);
        mTitleChart3TextView = (TextView) findViewById(R.id.tv_title_chart3);

        mChart1 = (PieChart) findViewById(R.id.chart1);
        mChart2 = (LineChart) findViewById(R.id.chart2);
        mChart3 = (LineChart) findViewById(R.id.chart3);

        mOkHttpClient = new OkHttpClient();



        if(mPdhs != null) {
            loadData();
            loadPieChart();
            loadLineChart();
        }
    }

    private void loadData() {
        PopupUtil.showLoading(this, "Loading ...", "");
        String kode_hs = mKodeHS;


        String url = null;

        url = Globals.base_host_bigdata   + "get_detail_kode_hs?" +
                "&kode_hs=" + kode_hs;




        Log.d(TAG, "urlHSLink: " + url);
        Log.d(TAG, "donni hs: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(HSLinkDetailActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "response:" + body);

                try {
                    JSONObject json = new JSONObject(body);
                   // JSONObject data = json.getJSONObject("data");

                    //final Double forecast = data.getDouble("forecast");

                    //final String growth = data.getString("growth");
                    //final Double forecast = data.getDouble("forecast");
                    //final JSONObject pdHs = data.getJSONObject("hs");

                    /*
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

                    JSONObject json = new JSONObject(body);
                    JSONArray data = json.getJSONArray("data");
*/

                    //for(int i = 0; i < data.length(); i++) {

                       JSONArray data = json.getJSONArray("data");
                        JSONObject row = data.getJSONObject(0);

                    final String kdHs = row.getString("kd_hs");
                    final String uraian = row.getString("uraian");
                    final   String sektor = row.getString("sektor");
                    final    String subsektor = row.getString("subsektor");
                    final     String bec = row.getString("bec_uraian");
                    final    String kelompok = row.getString("kelompok");
                    final    String ditjen = row.getString("ditjen");
                    final    String bea_masuk = row.getString("bea_masuk");

                    //}

                    //mKodeHsTextView.setText(kdHs);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String title = String.format("Impor Detail HS %s ", kdHs);

                            /*
                            if(mPilihan.equals("Ekspor")) {
                                title = String.format("Ekspor Detail HS %s: %s%%", kdHs, growth);
                            }
*/
                            mTitleHsTextView.setText(title);
                            mKodeHsTextView.setText(kdHs);
                            mForecastTextView.setText("Lonjakan :" + mPdhs.lonjakan + "%");
                            mUraianTextView.setText(uraian);
                            mSektorTextView.setText(sektor);
                            mSubsektorTextView.setText(subsektor);
                            mBoardTextView.setText(bec);
                            kelompokTextView.setText(kelompok);
                            ditjenTextView.setText(ditjen);
                            beamasukTextView.setText(bea_masuk);

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
        String kode_hs = mKodeHS;
        String url = null;

        String title = String.format("Share Negara Asal Impor %s %s s/d %s %s",
                DateUtil.toMonth(bulan1), tahun1,
                DateUtil.toMonth(bulan2), tahun2);

       /*
        if(mPilihan.equals("Expor")) {
            title = String.format("Share Negara Tujuan Ekspor %s %s s/d %s %s",
                    DateUtil.toMonth(bulan1), tahun1,
                    DateUtil.toMonth(bulan2), tahun2);
        }
*/
        //mTitleChart1TextView.setText(title);

        url = Globals.base_host_bigdata  + "pie_timeseries_impor_negara_hs_detail?" +
                "&kode_hs=" + kode_hs;

        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(HSLinkDetailActivity.this, "Error load data", PopupUtil.SHORT);
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

                    dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                            //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    //dataSet.setColors(color);

                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(12);



                    final PieData pieData = new PieData(dataSet);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Description description = new Description();
                            description.setText("");
                            mChart1.setDescription(description);
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
        String kode_hs = mKodeHS;
        String url = null;


        //cari status data terakhir diserver ......>>>
        String thn_min = null;
        String thn_max = null;
        String bln_max = null;
        String bln_min = null;



        url = Globals.base_host_bigdata  + "line_timeseries_impor_hs_detail_rentang?" +
                "&kode_hs=" + kode_hs;

        Request request_rentang = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request_rentang).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(HSLinkDetailActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "responsedonni:" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    final String thn_max = json.getString("thn_max");
                    final String thn_min = json.getString("thn_min");
                    final String bln_max = json.getString("bln_max");
                    final String bln_min = json.getString("bln_min");

                    Globals.tahunmax=thn_max;
                    Globals.bulanmax=bln_max;
                    Globals.tahunmin=thn_min;
                    Globals.bulanmin=bln_min;


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTitleChart2TextView.setText("Grafik Impor Berdasarkan Berat (" + Globals.bulanmin +"/"+ Globals.tahunmin + " s.d. " +  Globals.bulanmax +"/"+ Globals.tahunmax +")");
                            mTitleChart3TextView.setText("Grafik Impor Berdasarkan Nilai (" + Globals.bulanmin +"/"+ Globals.tahunmin + " s.d. " +  Globals.bulanmax +"/"+ Globals.tahunmax +")");
                            mTitleChart1TextView.setText("Share Negara Asal Impor (" + Globals.bulanmin +"/"+ Globals.tahunmin + " s.d. " +  Globals.bulanmax +"/"+ Globals.tahunmax +")");

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //==================================================================



/*
        String title2 = String.format("Grafik Impor Berdasarkan Volume %s %s s/d %s %s",
                DateUtil.toMonth(bln_min), thn_min,
                DateUtil.toMonth(bln_max), thn_max);
        String title3 = String.format("Grafik Impor Berdasarkan Nilai %s %s s/d %s %s",
                DateUtil.toMonth(bln_min), thn_min,
                DateUtil.toMonth(bln_max), thn_max);

 */
 /*
        if(mPilihan.equals("Expor")) {
            title2 = String.format("Grafik Ekspor Berdasarkan Volume %s %s s/d %s %s",
                    DateUtil.toMonth(bulan1), tahun1,
                    DateUtil.toMonth(bulan2), tahun2);
            title3 = String.format("Grafik Ekspor Berdasarkan Nilai %s %s s/d %s %s",
                    DateUtil.toMonth(bulan1), tahun1,
                    DateUtil.toMonth(bulan2), tahun2);
        }
*/


        url = Globals.base_host_bigdata  + "line_timeseries_impor_hs_detail?" +
                "&kode_hs=" + kode_hs;

        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(HSLinkDetailActivity.this, "Error load data", PopupUtil.SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();

                String body = response.body().string();
                Log.d(TAG, "responsedonni:" + body);

                try {
                    JSONObject json = new JSONObject(body);
                    JSONArray labelJson = json.getJSONArray("periode");
                    JSONArray volumeJson = json.getJSONArray("berat");
                    JSONArray valueJson = json.getJSONArray("nilai");

                    List<Entry> entries1 = new ArrayList<Entry>();
                    List<Entry> entries2 = new ArrayList<Entry>();

                    for(int i = 0; i < labelJson.length(); i++) {
                        String lbl = labelJson.getString(i);
                        Double volume = volumeJson.getDouble(i);
                        Double value = valueJson.getDouble(i);

                        entries1.add(new Entry(i + 1, volume.floatValue()));
                        entries2.add(new Entry(i + 1, value.floatValue()));
                    }

                    LineDataSet dataSet1 = new LineDataSet(entries1, "Berat (satuan berat)");
                    LineDataSet dataSet2 = new LineDataSet(entries2, "Nilai (USD) ");
                    dataSet1.setValueTextColor(Color.BLACK);
                    dataSet1.setColor(Color.RED);
                    dataSet1.setMode(LineDataSet.Mode.LINEAR);
                    dataSet1.setLineWidth(2);
                    dataSet1.setValueTextSize(8);
                    dataSet1.setDrawCircles(true);
                    dataSet1.setCircleColor(Color.RED);

                    dataSet2.setValueTextColor(Color.BLACK);
                    dataSet2.setColor(Color.RED);
                    dataSet2.setMode(LineDataSet.Mode.LINEAR);
                    dataSet2.setLineWidth(2);
                    dataSet2.setValueTextSize(8);
                    dataSet2.setDrawCircles(true);
                    dataSet2.setCircleColor(Color.RED);


                    final LineData lineData1 = new LineData(dataSet1);
                    final LineData lineData2 = new LineData(dataSet2);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Description description = new Description();
                            description.setText("");
                            mChart2.setDescription(description);
                            mChart3.setDescription(description);

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
