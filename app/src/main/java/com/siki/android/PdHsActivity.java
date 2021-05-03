package com.siki.android;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.siki.android.adapters.PdHsItemAdapter;
import com.siki.android.models.PdHs;
import com.siki.android.models.PeringatanDini;
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

public class PdHsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {
    public static final String TAG = "PdHsActivity";
    public static final String KEY_DITJEN = "ditjen";
    public static final String KEY_PILIHAN = "pilihan";
    public static final String KEY_PD = "pd";

    private PdHsItemAdapter mAdapter;
    private List<PdHs> pdHsList = new ArrayList<>();
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    private String mDitjen;
    private String mPilihan;
    private PeringatanDini mPd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pda);

        mDitjen = getIntent().getStringExtra(KEY_DITJEN);
        mPilihan = getIntent().getStringExtra(KEY_PILIHAN);
        mPd = getIntent().getParcelableExtra(KEY_PD);

        mAdapter = new PdHsItemAdapter(this, pdHsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        if(ConnectivityUtil.isConnected(this)) {
            loadData();
        }
        else {
            PopupUtil.showMsg(this, "Can't load data, no internet connection", PopupUtil.SHORT);
        }

        Button chartButton = (Button) findViewById(R.id.button_chart);
        chartButton.setOnClickListener(this);

        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        titleTextView.setText(mPd.judul);

        Log.d(TAG, "onCreate");
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
            /*
            url = "http://iris.kemenperin.go.id/android/ews/" + "get_data2.php?" +
                    "pilihan=" + pilihan +
                    "&tahun1=" + tahun1 +
                    "&tahun2=" + tahun2 +
                    "&bulan1=" + bulan1 +
                    "&bulan2=" + bulan2 +
                    "&ditjen=" + URLEncoder.encode(ditjen, "UTF-8");
                    */

            url = Globals.base_host + "get_data2.php?" +
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

        Log.d(TAG, "url:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
                PopupUtil.showMsg(PdHsActivity.this, "Error load data", PopupUtil.SHORT);
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

                    //$tabel->addBaris(array("_NO_URUT_.","<a href='detail.php?id=$row[kd_hs]&tahun1=$_REQUEST[tahun1]&tahun2=$_REQUEST[tahun2]&bulan1=$_REQUEST[bulan1]&bulan2=$_REQUEST[bulan2]&pilihan=$_REQUEST[pilihan]'>$row[kd_hs]</a>",
                    //        "$row[uraian]","$value","$row[bec]","$row[kelompok]"));

                    for(int i = 0; i < data.length(); i++) {
                        JSONObject row = data.getJSONObject(i);

                        String kodeHS = row.getString("kd_hs");
                        String uraian = row.getString("uraian");
                        String logest_berat = row.getString("logest_berat");
                        String bec = row.getString("bec");
                        String kelompok = row.getString("kelompok");

                        PdHs pdHs = new PdHs();
                        pdHs.kodeHS = kodeHS;
                        pdHs.deskripsi = uraian;
                        pdHs.lonjakan = logest_berat;
                        pdHs.bec = bec;
                        pdHs.kelompok = kelompok;

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
        PdHs pdHs = pdHsList.get(i);

        Intent intent = new Intent(this, PdHsDetailActivity.class);
        intent.putExtra(PdHsDetailActivity.KEY_PILIHAN, mPilihan);
        intent.putExtra(PdHsDetailActivity.KEY_PDHS, pdHs);
        intent.putExtra(PdHsDetailActivity.KEY_PD, mPd);
        intent.putExtra("title", "Detail Hs");
        startActivity(intent);
    }

    /*
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pda);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Fragment pdHsFragment;
        private Fragment pdChartFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            pdHsFragment = new PdHsFragment();
            pdChartFragment = new PdChartFragment();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return pdHsFragment;
                case 1:
                    return pdChartFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Data";
                case 1:
                    return "Chart";
            }
            return null;
        }
    } */

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
        Intent intent = new Intent(this, PdChartActivity.class);
        intent.putExtra(PdChartActivity.KEY_DITJEN, mDitjen);
        intent.putExtra(PdChartActivity.KEY_PILIHAN, mPilihan);
        intent.putExtra(PdChartActivity.KEY_PD, mPd);
        startActivity(intent);
    }
}
