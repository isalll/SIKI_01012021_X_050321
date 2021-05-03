package com.siki.android;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;
import com.siki.library.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.example.android.fragments.ArticleFragment;
//import com.example.android.fragments.ArticleFragment;
//import com.example.android.fragments.ArticleFragment;
//import com.github.mikephil.charting.data;
//import android.app.FragmentTransaction;

@SuppressLint("NewApi")
public class DashboardActivityDetail extends Fragment {
	JSONArray grapik,infodatabase = null;
	private static String url;

	private static final String AR_JUDUL = "kode";
	private static final String AR_CONTENT = "komentar";
	private static final String AR_USER = "username";
	private static final String AR_DATE = "date";
	private static final String AR_TIME = "time";
	private static final String AR_URAIAN = "uraian";
	private static final String AR_NILAI = "nilai";

	private static final String AR_THNMAX = "thn_max";
	private static final String AR_THNMIN = "thn_min";
	private static final String AR_BLNMAX = "bln_max";



	//private InfoDataUpdate IDU = new InfoDataUpdate();

	public static final int FRAGMENT_DASHBOARD = 0;
	public static final int FRAGMENT_NEWS = 1;
	public static final int FRAGMENT_FINDHS = 2;
	public static final int FRAGMENT_ABOUT = 3;
	public static final int FRAGMENT_PERUSAHAAN = 4;

	public static final String TAG = "DashActivity";
	private OkHttpClient mOkHttpClient = new OkHttpClient();
	//private PieChart mChart1;
	//private PieChart mChart2;
	//private PieChart mChart3;
	//private PieChart mChart4;
	//private PieChart mChart5;
	//private PieChart mChart6;
	//private BarChart mChart7;
	//private LineChart mChart8;
	//private LineChart mChart9;
	//private LineChart mChart10;
	//private BarChart mChart11;
	//private BarChart mChart12;
	//private BarChart mChart13;
	//private BarChart mChart14;
	private BarChart mChart15;
	private BarChart mChart16;
	private BarChart mChart17;
	private BarChart mChart18;
	private BarChart mChart19;
	private BarChart mChart20;
	private BarChart mChart21;
	private BarChart mChart22;
	private BarChart mChart23;


	//private ProgressWheel prog_one, prog_two, prog_three, prog_four;
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_prog_four, tv_title,tv_catatan,tv_title_chart15,tv_title_chart16,tv_title_chart17,tv_title_chart18,tv_title_chart19,tv_title_chart20,tv_title_chart21,tv_title_chart22,tv_title_chart23;
	private TextView tv_prog_one_lblup,tv_prog_one_lbldown,tv_prog_two_lblup,tv_prog_two_lbldown,tv_prog_three_lblup,tv_prog_three_lbldown,tv_prog_four_lbldown;

	private TabHost mTabHost;
	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	private ArrayList<Keyword> listHSKeyword;



	//private String thn_min;
	//private String thn_max,thnmax;
	//private String bln_max;


	TabWidget tabWidget;
	public static final int FRAGMENT_CHARTHS = 5;

		  private String[] mMenuText;

		  private String[] mMenuSummary;
		  //private String[] listHS;

	Globals sharedData = Globals.getInstance();

	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	ArrayList<String> list_eksdown,list_eksup,list_impdown,list_impup,listHSArrayString,listUraianHSArrayString = new ArrayList<String>();

	public DashboardActivityDetail() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		final View v = inflater.inflate(R.layout.activity_dashboard_detail, container, false);
		//final View v = inflater.inflate(R.layout.activity_dashboard, container, false);

		StrictMode.setThreadPolicy(policy);

//		mChart1 = (PieChart) v.findViewById(R.id.chart_bec1);
//		mChart2 = (PieChart) v.findViewById(R.id.chart_bec2);
//		mChart3 = (PieChart) v.findViewById(R.id.chart_sektor1);
//		mChart4 = (PieChart) v.findViewById(R.id.chart_sektor2);
//		mChart5 = (PieChart) v.findViewById(R.id.chart_subsektor1);
//		mChart6 = (PieChart) v.findViewById(R.id.chart_subsektor2);
//		mChart7 = (BarChart) v.findViewById(R.id.chart_total_eksim);
//		mChart8 = (LineChart) v.findViewById(R.id.chart_timeseries_eksim);
//		mChart9 = (LineChart) v.findViewById(R.id.chart_timeseries_impor_bec_industri);
//		mChart10 = (LineChart) v.findViewById(R.id.chart_timeseries_ekspor_bec_industri);
//		mChart11 = (BarChart) v.findViewById(R.id.chart_top_10_HS_impor);
//		mChart12 = (BarChart) v.findViewById(R.id.chart_top_10_HS_ekspor);
//		mChart13 = (BarChart) v.findViewById(R.id.chart_top_10_negara_impor);
//		mChart14 = (BarChart) v.findViewById(R.id.chart_top_10_negara_ekspor);
		mChart15 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor);
		mChart16 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor1);
		mChart17 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor2);
		mChart18 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor3);
		mChart19 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor4);
		mChart20 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor5);
		mChart21 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor6);
		mChart22 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor7);
		mChart23 = (BarChart) v.findViewById(R.id.chart_top_10_hs_negara_impor8);



		if(ConnectivityUtil.isConnected(getActivity())) {
			loadData();
		}
		else {
			PopupUtil.showMsg(getActivity(), "Can't load data, no internet connection", PopupUtil.SHORT);
		}

		//loadInfoData();
		init(v);




		return v;
	}


	public void loadInfoData() {
		url = Globals.base_host_bigdata   + "infodatabase?req=1" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url:" + url);
		Request request = new Request.Builder()
				.url(url)
				.build();


//kalo di luar loop : ok
		sharedData.setValue("donni");

		//final String thn_max;

		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "infodata response:" + body);

				try {
					JSONObject json = new JSONObject(body);
					/*
					thn_max = json.getString("thn_max");
					thn_min = json.getString("thn_min");
					bln_max = json.getString("bln_max");


					Globals.thn_max = thn_max;
					Globals.thn_min = thn_min;
					Globals.bln_max = bln_max;

					 thnmax = thn_max;

					//InfoDataUpdate IDU = new InfoDataUpdate();

					//IDU.bulan1 = "1";
					//IDU.bulan2 = bln_max;
					//IDU.tahun1 = thn_min;
					//IDU.tahun2 = thn_max;

					//didalam loop tdk bisa dibaca
					sharedData.setValue(thn_max);
*/

					//error di memori utk thread
					//tv_title_chart1.setText("Impor dari Negara Mitra - BEC Industri (%) - ("+bln_max+"/"+thn_max+")");

				} catch (JSONException e) {
					e.printStackTrace();
				}




			}
		});
	}



	private void init(View v) {

		//cari status data terakhir diserver ......>>>
		String thn_min = null;
		String thn_max = null;
		String bln_max = null;

		//url = Globals.base_host_bigdata   + "infodatabase?req=1" ;
		//url = "https://siki.kemenperin.go.id/r-api/infodatabase" ;
		url = Globals.base_host_bigdata   + "infodatabase?req=1" ;

		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.AmbilJsonGet(url);
		Log.d(TAG, "infodatadonni response:" + json);

		try{
			grapik = json.getJSONArray("info");
			JSONObject ad = grapik.getJSONObject(0);

			thn_min=ad.getString(AR_THNMIN);
			thn_max=ad.getString(AR_THNMAX);
			bln_max=ad.getString(AR_BLNMAX);
			Globals.tahunmax=thn_max;
			Globals.bulanmax=bln_max;


		}catch (JSONException e){
			e.printStackTrace();
		}


		//String n = sharedData.getValue();

		//tv_catatan = (TextView) v.findViewById(R.id.textViewCatatan);
		//tv_catatan.setText("Data ekspor-impor tersedia dari tahun : " + thn_min+" s.d "+thn_max+". Data terupdate bulan : "+bln_max+" tahun "+thn_max );
		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText("Negara Mitra Tujuan Impor Industri");

//		tv_title_chart1 = (TextView) v.findViewById(R.id.tv_title_chart1);
//
//		tv_title_chart2 = (TextView) v.findViewById(R.id.tv_title_chart2);
//
//		tv_title_chart3 = (TextView) v.findViewById(R.id.tv_title_chart3);
//		//tv_title_chart3.setText("Impor dari Negara Mitra - Kategori SEKTOR (%) - ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart4 = (TextView) v.findViewById(R.id.tv_title_chart4);
//		//tv_title_chart4.setText("Ekspor ke Negara Mitra - Kategori SEKTOR (%) -  ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart5 = (TextView) v.findViewById(R.id.tv_title_chart5);
//		//tv_title_chart5.setText("Impor dari Negara Mitra - Kategori SUB SEKTOR (%) -  ("+bln_max+"/"+thn_max+")");
//
//
//		tv_title_chart6 = (TextView) v.findViewById(R.id.tv_title_chart6);
//		//tv_title_chart6.setText("Ekspor ke Negara Mitra - Kategori SUB SEKTOR (%) - ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart7 = (TextView) v.findViewById(R.id.tv_title_chart7);
//		//tv_title_chart7.setText("Total Ekspor - Impor dalam USD ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart8 = (TextView) v.findViewById(R.id.tv_title_chart8);
//		//tv_title_chart8.setText("Timeseries Ekspor VS Impor dalam USD (01/"+thn_min+" s.d "+bln_max+"/"+thn_max+")");
//
//		tv_title_chart9 = (TextView) v.findViewById(R.id.tv_title_chart9);
//		//tv_title_chart9.setText("Timeseries Impor BEC Industri (%) - (01/"+thn_min+" s.d "+bln_max+"/"+thn_max+")");
//
//		tv_title_chart10 = (TextView) v.findViewById(R.id.tv_title_chart10);
//		//tv_title_chart10.setText("Timeseries Ekspor BEC Industri (%) - (01/"+thn_min+" s.d "+bln_max+"/"+thn_max+")");
//
//		tv_title_chart11 = (TextView) v.findViewById(R.id.tv_title_chart11);
//		//tv_title_chart11.setText("Top 10 HS Impor Industri dari negara Mitra (USD) - ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart12 = (TextView) v.findViewById(R.id.tv_title_chart12);
//		//tv_title_chart12.setText("Top 10 HS Ekspor Industri ke negara Mitra (USD) - ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart13 = (TextView) v.findViewById(R.id.tv_title_chart13);
//		//tv_title_chart13.setText("Top 10 Negara Mitra Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
//
//		tv_title_chart14 = (TextView) v.findViewById(R.id.tv_title_chart14);
		//tv_title_chart14.setText("Top 10 Negara Mitra Tujuan Ekspor Industri (USD) - ("+bln_max+"/"+thn_max+")");

		tv_title_chart15 = (TextView) v.findViewById(R.id.tv_title_chart15);
		//tv_title_chart14.setText("Top 10 Negara Mitra Tujuan Ekspor Industri (USD) - ("+bln_max+"/"+thn_max+")");

		tv_title_chart16 = (TextView) v.findViewById(R.id.tv_title_chart16);

		tv_title_chart17 = (TextView) v.findViewById(R.id.tv_title_chart17);

		tv_title_chart18 = (TextView) v.findViewById(R.id.tv_title_chart18);

		tv_title_chart19 = (TextView) v.findViewById(R.id.tv_title_chart19);

		tv_title_chart20 = (TextView) v.findViewById(R.id.tv_title_chart20);

		tv_title_chart21 = (TextView) v.findViewById(R.id.tv_title_chart21);

		tv_title_chart22 = (TextView) v.findViewById(R.id.tv_title_chart22);

		tv_title_chart23 = (TextView) v.findViewById(R.id.tv_title_chart23);


//		tv_title_chart1.setText("Impor dari Negara Mitra - BEC Industri (%) - ("+bln_max+"/"+thn_max+")");
//		tv_title_chart2.setText("Ekspor ke Negara Mitra - BEC Industri (%) -  ("+bln_max+"/"+thn_max+")");
//		tv_title_chart3.setText("Impor dari Negara Mitra - Kategori SEKTOR (%) - ("+bln_max+"/"+thn_max+")");
//		tv_title_chart4.setText("Ekspor ke Negara Mitra - Kategori SEKTOR (%) -  ("+bln_max+"/"+thn_max+")");
//		tv_title_chart5.setText("Impor dari Negara Mitra - Kategori SUB SEKTOR (%) -  ("+bln_max+"/"+thn_max+")");
//		tv_title_chart6.setText("Ekspor ke Negara Mitra - Kategori SUB SEKTOR (%) - ("+bln_max+"/"+thn_max+")");
//		tv_title_chart7.setText("Total Ekspor - Impor dalam USD ("+bln_max+"/"+thn_max+")");
//		tv_title_chart8.setText("Timeseries Ekspor VS Impor dalam USD (01/"+thn_min+" s.d "+bln_max+"/"+thn_max+")");
//		tv_title_chart9.setText("Timeseries Impor BEC Industri (%) - (01/"+thn_min+" s.d "+bln_max+"/"+thn_max+")");
//		tv_title_chart10.setText("Timeseries Ekspor BEC Industri (%) - (01/"+thn_min+" s.d "+bln_max+"/"+thn_max+")");
//		tv_title_chart11.setText("Top 10 HS Impor Industri dari negara Mitra (USD) - ("+bln_max+"/"+thn_max+")");
//		tv_title_chart12.setText("Top 10 HS Ekspor Industri ke negara Mitra (USD) - ("+bln_max+"/"+thn_max+")");
//		tv_title_chart13.setText("Top 10 Negara Mitra Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
//		tv_title_chart14.setText("Top 10 Negara Mitra Tujuan Ekspor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart15.setText("HS Negara REP.RAKYAT CINA Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart16.setText("HS Negara JEPANG Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart17.setText("HS Negara THAILAND Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart18.setText("HS Negara SINGAPURA Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart19.setText("HS Negara KOREA SELATAN Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart20.setText("HS Negara MALAYSIA Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart21.setText("HS Negara AMERIKA SERIKAT Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart22.setText("HS Negara INDIA Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");
		tv_title_chart23.setText("HS Negara AUSTRALIA Mitra Tujuan Impor Industri (USD) - ("+bln_max+"/"+thn_max+")");



		//============================================================




	}


	public class MyAxisValueFormatter implements IAxisValueFormatter
	{

		private DecimalFormat mFormat;

		public MyAxisValueFormatter() {
			mFormat = new DecimalFormat("###,###,###,##0.0");
		}

		@Override
		public String getFormattedValue(float value, AxisBase axis) {
			return mFormat.format(value) + " $";
		}
	}

	public void loadData() {
		PopupUtil.showLoading(getActivity(), "", "Loading data ...");


		String url = null;

		/*
		try {
			//base_url = https://kemenperin.binerapps.com/modules/ews/


			url = Globals.base_host_bigdata   + "pie_bec_dash" ;
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("UTF-8 is unknown");
		}
*/


/*
####################################################################################################
                               PIE CHART IMPOR - BEC
####################################################################################################
 */
/*
		url = Globals.base_host_bigdata   + "pie_impor_bec_dash?req=1" ;
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
					PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
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

						PieDataSet dataSet = new PieDataSet(entries, "Chart Impor BEC");
						dataSet.setColors(new int[]{
								Color.GREEN,
								Color.YELLOW,
								Color.RED
						});
						dataSet.setValueTextColor(Color.BLACK);
						dataSet.setValueTextSize(12);

						final PieData pieData = new PieData(dataSet);
						Description description = new Description();
						description.setText("");
						mChart1.setDescription(description);
						mChart1.setData(pieData);
						mChart1.invalidate();


					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
*/
/*
####################################################################################################
                               PIE CHART EKSPOR - BEC
####################################################################################################
 */
//		url = Globals.base_host_bigdata   + "pie_ekspor_bec_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url2:" + url);
//		Request request2 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request2).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response2:" + body);
//
//				try {
//					JSONObject json = new JSONObject(body);
//					String barangKonsumsi = json.getString("Barang Konsumsi");
//					String bahanBaku = json.getString("Bahan Baku");
//					String barangModal = json.getString("Barang Modal");
//
//					float bk = Float.parseFloat(barangKonsumsi);
//					float bb = Float.parseFloat(bahanBaku);
//					float bm = Float.parseFloat(barangModal);
//
//					List<PieEntry> entries = new ArrayList<PieEntry>();
//					entries.add(new PieEntry(bk, "Barang Konsumsi"));
//					entries.add(new PieEntry(bb, "Bahan Baku"));
//					entries.add(new PieEntry(bm, "Barang Modal"));
//
//					PieDataSet dataSet = new PieDataSet(entries, "Chart Ekspor BEC");
//					dataSet.setColors(new int[]{
//							Color.GREEN,
//							Color.RED,
//							Color.YELLOW
//					});
//					dataSet.setValueTextColor(Color.BLACK);
//					dataSet.setValueTextSize(12);
//
//					final PieData pieData = new PieData(dataSet);
//					Description description = new Description();
//					description.setText("");
//					mChart2.setDescription(description);
//					mChart2.setData(pieData);
//					mChart2.invalidate();
//
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

/*
####################################################################################################
                               PIE CHART IMPOR - SEKTOR
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "pie_impor_sektor_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url3:" + url);
//		Request request3 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request3).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response3:" + body);
//
//				try {
//					JSONObject json = new JSONObject(body);
//					String barangMigas = json.getString("MIGAS");
//					String bahanNonMigas = json.getString("NON MIGAS");
//
//
//					float bk = Float.parseFloat(barangMigas);
//					float bb = Float.parseFloat(bahanNonMigas);
//
//
//					List<PieEntry> entries = new ArrayList<PieEntry>();
//					entries.add(new PieEntry(bk, "MIGAS"));
//					entries.add(new PieEntry(bb, "NON MIGAS"));
//
//
//					PieDataSet dataSet = new PieDataSet(entries, "Chart Impor  SEKTOR");
//					dataSet.setColors(new int[]{
//							Color.parseColor("#FFEB3B"),
//							Color.parseColor("#2196F3")
//					});
//					dataSet.setValueTextColor(Color.BLACK);
//					dataSet.setValueTextSize(12);
//
//					final PieData pieData = new PieData(dataSet);
//					Description description = new Description();
//					description.setText("");
//					mChart3.setDescription(description);
//					mChart3.setData(pieData);
//					mChart3.invalidate();
//
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

/*
####################################################################################################
                               PIE CHART EKSPOR - SEKTOR
####################################################################################################
 */
//		url = Globals.base_host_bigdata   + "pie_ekspor_sektor_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url4:" + url);
//		Request request4 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request4).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response4:" + body);
//
//				try {
//					JSONObject json = new JSONObject(body);
//					String barangMigas = json.getString("MIGAS");
//					String bahanNonMigas = json.getString("NON MIGAS");
//
//
//					float bk = Float.parseFloat(barangMigas);
//					float bb = Float.parseFloat(bahanNonMigas);
//
//
//					List<PieEntry> entries = new ArrayList<PieEntry>();
//					entries.add(new PieEntry(bk, "MIGAS"));
//					entries.add(new PieEntry(bb, "NON MIGAS"));
//
//
//					PieDataSet dataSet = new PieDataSet(entries, "Chart Ekspor  SEKTOR");
//					dataSet.setColors(new int[]{
//							Color.parseColor("#FFEB3B"),
//							Color.parseColor("#2196F3")
//					});
//					dataSet.setValueTextColor(Color.BLACK);
//					dataSet.setValueTextSize(12);
//
//					final PieData pieData = new PieData(dataSet);
//					Description description = new Description();
//					description.setText("");
//					mChart4.setDescription(description);
//					mChart4.setData(pieData);
//					mChart4.invalidate();
//
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		/*
####################################################################################################
                               PIE CHART IMPOR - SUBSEKTOR
####################################################################################################
 */
//		url = Globals.base_host_bigdata   + "pie_impor_subsektor_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url5:" + url);
//		Request request5 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request5).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response5:" + body);
//
//				try {
//
//
//					JSONObject json = new JSONObject(body);
//					String barangIndustri = json.getString("Industri");
//					String bahanPertanian = json.getString("Pertanian");
//					String bahanHasilMinyak = json.getString("Hasil Minyak");
//					String bahanMinyakMentah = json.getString("Minyak Mentah");
//					String bahanGasAlam = json.getString("Gas Alam");
//					String bahanPertambangan = json.getString("Pertambangan");
//					String bahanLainnya = json.getString("Lainnya");
//
//
//					float bi = Float.parseFloat(barangIndustri);
//					float bp = Float.parseFloat(bahanPertanian);
//					float bh = Float.parseFloat(bahanHasilMinyak);
//					float bm = Float.parseFloat(bahanMinyakMentah);
//					float bg = Float.parseFloat(bahanGasAlam);
//					float bt = Float.parseFloat(bahanPertambangan);
//					float bl = Float.parseFloat(bahanLainnya);
//
//					List<PieEntry> entries = new ArrayList<PieEntry>();
//					entries.add(new PieEntry(bi, "Industri"));
//					entries.add(new PieEntry(bp, "Pertanian"));
//					entries.add(new PieEntry(bh, "Hasil Minyak"));
//					entries.add(new PieEntry(bm, "Minyak Mentah"));
//					entries.add(new PieEntry(bg, "Gas Alam"));
//					entries.add(new PieEntry(bt, "Pertambangan"));
//					entries.add(new PieEntry(bl, "Lainnya"));
//
//					PieDataSet dataSet = new PieDataSet(entries, "Chart Impor  SUBSEKTOR");
//					dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//
//					Legend legend = mChart5.getLegend();
//					legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//					legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//					legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//					legend.setDrawInside(false);
///*
//					dataSet.setColors(new int[]{
//							Color.parseColor("#FFEB3B"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3")
//					});
//*/
//
//
//					dataSet.setValueTextColor(Color.BLACK);
//					dataSet.setValueTextSize(12);
//
//					final PieData pieData = new PieData(dataSet);
//					Description description = new Description();
//					description.setText("");
//					mChart5.setDescription(description);
//					mChart5.setData(pieData);
//					mChart5.invalidate();
//
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		/*
####################################################################################################
                               PIE CHART EKSPOR - SUBSEKTOR
####################################################################################################
 */
//		url = Globals.base_host_bigdata   + "pie_ekspor_subsektor_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url6:" + url);
//		Request request6 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request6).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response6:" + body);
//
//				try {
//
//
//					JSONObject json = new JSONObject(body);
//					String barangIndustri = json.getString("Industri");
//					String bahanPertanian = json.getString("Pertanian");
//					String bahanHasilMinyak = json.getString("Hasil Minyak");
//					String bahanMinyakMentah = json.getString("Minyak Mentah");
//					String bahanGasAlam = json.getString("Gas Alam");
//					String bahanPertambangan = json.getString("Pertambangan");
//					String bahanLainnya = json.getString("Lainnya");
//
//
//					float bi = Float.parseFloat(barangIndustri);
//					float bp = Float.parseFloat(bahanPertanian);
//					float bh = Float.parseFloat(bahanHasilMinyak);
//					float bm = Float.parseFloat(bahanMinyakMentah);
//					float bg = Float.parseFloat(bahanGasAlam);
//					float bt = Float.parseFloat(bahanPertambangan);
//					float bl = Float.parseFloat(bahanLainnya);
//
//					List<PieEntry> entries = new ArrayList<PieEntry>();
//					entries.add(new PieEntry(bi, "Industri"));
//					entries.add(new PieEntry(bp, "Pertanian"));
//					entries.add(new PieEntry(bh, "Hasil Minyak"));
//					entries.add(new PieEntry(bm, "Minyak Mentah"));
//					entries.add(new PieEntry(bg, "Gas Alam"));
//					entries.add(new PieEntry(bt, "Pertambangan"));
//					entries.add(new PieEntry(bl, "Lainnya"));
//
//					PieDataSet dataSet = new PieDataSet(entries, "Chart Ekspor  SUBSEKTOR");
//					dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//
//					Legend legend = mChart6.getLegend();
//					legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//					legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//					legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//					legend.setDrawInside(false);
///*
//					dataSet.setColors(new int[]{
//							Color.parseColor("#FFEB3B"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3"),
//							Color.parseColor("#2196F3")
//					});
//*/
//
//
//					dataSet.setValueTextColor(Color.BLACK);
//					dataSet.setValueTextSize(12);
//
//					final PieData pieData = new PieData(dataSet);
//
//					Description description = new Description();
//					description.setText("");
//					mChart6.setDescription(description);
//					mChart6.setData(pieData);
//					mChart6.invalidate();
//
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

				/*
####################################################################################################
                               BAR CHART EKSIM
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "bar_total_eksim_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url7:" + url);
//		Request request7 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request7).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response7:" + body);
//
//				try {
//
//					JSONObject json = new JSONObject(body);
//					String totEkspor = json.getString("Ekspor");
//					String totEksporIndustri = json.getString("Ekspor-Industri");
//					String totImpor = json.getString("Impor");
//					String totImporIndustri = json.getString("Impor-Industri");
//					String balance = json.getString("Balance");
//
//					float eks = Float.parseFloat(totEkspor);
//					float eksInd = Float.parseFloat(totEksporIndustri);
//					float imp = Float.parseFloat(totImpor);
//					float impInd = Float.parseFloat(totImporIndustri);
//					float bal = Float.parseFloat(balance);
//
//  				    ArrayList<BarEntry> ekspor = new ArrayList<>();
//					ekspor.add(new BarEntry(0, eks));
//
//					ArrayList<BarEntry> eksporind = new ArrayList<>();
//					eksporind.add(new BarEntry(1, eksInd));
//
//					ArrayList<BarEntry> impor = new ArrayList<>();
//					impor.add(new BarEntry(2, imp));
//
//					ArrayList<BarEntry> imporind = new ArrayList<>();
//					imporind.add(new BarEntry(3, impInd));
//
//					ArrayList<BarEntry> balanced = new ArrayList<>();
//					balanced.add(new BarEntry(4, bal));
//
//					ArrayList<BarEntry> balancedind = new ArrayList<>();
//					balancedind.add(new BarEntry(5, bal));
//
//					BarDataSet dsekspor = new BarDataSet(ekspor, "Ekspor");
//					dsekspor.setColors(Color.parseColor("#4CAF50"));
//					dsekspor.setValueTextSize(10);
//
//					BarDataSet dseksporind = new BarDataSet(eksporind, "Ekspor-Industri");
//					dseksporind.setColors(Color.BLUE);
//					dseksporind.setValueTextSize(10);
//
//					BarDataSet dsimpor = new BarDataSet(impor, "Impor");
//					dsimpor.setColors(Color.parseColor("#F44336"));
//					dsimpor.setValueTextSize(10);
//
//					BarDataSet dsimporind = new BarDataSet(imporind, "Impor-Industri");
//					dsimporind.setColors(Color.MAGENTA);
//					dsimporind.setValueTextSize(10);
//
//					BarDataSet dsbalance = new BarDataSet(balanced, "Balance");
//					dsbalance.setColors(Color.parseColor("#FFEB3B"));
//					dsbalance.setValueTextSize(10);
//
//					BarDataSet dsbalanceind = new BarDataSet(balancedind, "Balance-Industri");
//					dsbalanceind.setColors(Color.parseColor("#F3EBAB"));
//					dsbalanceind.setValueTextSize(10);
//
//					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//					dataSets.add(dsekspor);
//					dataSets.add(dseksporind);
//					dataSets.add(dsimpor);
//					dataSets.add(dsimporind);
//					dataSets.add(dsbalance);
//					dataSets.add(dsbalanceind);
//
//					BarData Data = new BarData(dataSets);
//					Log.d(TAG, "donni-bar-data:" + Data);
//
//					XAxis xAxis = mChart7.getXAxis();
//					xAxis.setDrawGridLines(false);
//
//					mChart7.getAxisLeft().setDrawGridLines(false);
//					mChart7.getAxisRight().setDrawGridLines(false);
//					mChart7.getAxisRight().setEnabled(false);
//					mChart7.getAxisLeft().setEnabled(true);
//					mChart7.getXAxis().setDrawGridLines(false);
//
//					Description description = new Description();
//					description.setText("");
//					mChart7.setDescription(description);
//
//
//					mChart7.getLegend().setEnabled(true);
//
//					mChart7.getAxisRight().setDrawLabels(false);
//					mChart7.getAxisLeft().setDrawLabels(true);
//					mChart7.setTouchEnabled(false);
//					mChart7.setDoubleTapToZoomEnabled(false);
//					mChart7.getXAxis().setEnabled(false);
//					mChart7.getXAxis().setPosition(XAxisPosition.BOTTOM);
//					mChart7.setData(Data);
//					mChart7.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		/*
####################################################################################################
                               LINE CHART TIME SERIES EKSIM
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "line_timeseries_eksim_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url8:" + url);
//		Request request8 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request8).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response7:" + body);
//
//				try {
//
//					//body = '{"periode":["2017/1",'
//					JSONObject json = new JSONObject(body);
//					JSONArray periodeJson = json.getJSONArray("periode");
//					JSONArray imporJson = json.getJSONArray("impor");
//					JSONArray eksporJson = json.getJSONArray("ekspor");
//					JSONArray eksporIndustriJson = json.getJSONArray("ekspor_industri");
//					JSONArray imporIndustriJson = json.getJSONArray("impor_industri");
//
//  				    ArrayList<Entry> valImpor = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double impor = imporJson.getDouble(i);
//						valImpor.add(new Entry(i + 1, (impor.floatValue())));
//
//					}
//
//					ArrayList<Entry> valEkspor = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double ekspor = eksporJson.getDouble(i);
//						valEkspor.add(new Entry(i + 1, (ekspor.floatValue())));
//
//					}
//
//					ArrayList<Entry> valEksporIndustri = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double eksporIndustri = eksporIndustriJson.getDouble(i);
//						valEksporIndustri.add(new Entry(i + 1, (eksporIndustri.floatValue())));
//
//					}
//
//					ArrayList<Entry> valImporIndustri = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double imporIndustri = imporIndustriJson.getDouble(i);
//						valImporIndustri.add(new Entry(i + 1, (imporIndustri.floatValue())));
//
//					}
//
//					LineDataSet lineDataSet1 = new LineDataSet(valImpor, "Impor");
//					lineDataSet1.setDrawValues(false);
//					//lineDataSet1.setDrawCircleHole(true);
//					lineDataSet1.setColor(Color.RED);
//					lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet1.setDrawCircles(false);
//					lineDataSet1.setCubicIntensity(0.15f);
//					lineDataSet1.setCircleColor(Color.RED);
//					lineDataSet1.setLineWidth(1);
//
//					LineDataSet lineDataSet2 = new LineDataSet(valEkspor, "Ekspor");
//					lineDataSet2.setDrawValues(false);
//					//lineDataSet2.setDrawCircleHole(true);
//					lineDataSet2.setColor(Color.parseColor("#4CAF50"));
//					lineDataSet2.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet2.setDrawCircles(false);
//					lineDataSet2.setCubicIntensity(0.15f);
//					lineDataSet2.setCircleColor(Color.parseColor("#4CAF50"));
//					lineDataSet2.setLineWidth(1);
//
//					LineDataSet lineDataSet3 = new LineDataSet(valEksporIndustri, "Ekspor-Industri");
//					lineDataSet3.setDrawValues(false);
//					//lineDataSet3.setDrawCircleHole(true);
//					lineDataSet3.setColor(Color.BLUE);
//					lineDataSet3.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet3.setDrawCircles(false);
//					lineDataSet3.setCubicIntensity(0.15f);
//					lineDataSet3.setCircleColor(Color.BLUE);
//					lineDataSet3.setLineWidth(1);
//
//					LineDataSet lineDataSet4 = new LineDataSet(valImporIndustri, "Impor-Industri");
//					lineDataSet4.setDrawValues(false);
//					//lineDataSet4.setDrawCircleHole(true);
//					lineDataSet4.setColor(Color.MAGENTA);
//					lineDataSet4.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet4.setDrawCircles(false);
//					lineDataSet4.setCubicIntensity(0.15f);
//					lineDataSet4.setCircleColor(Color.MAGENTA);
//					lineDataSet4.setLineWidth(1);
//
//					Description description = new Description();
//					description.setText("");
//					mChart8.setDescription(description);
//
//
//					ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
//					dataSets.add(lineDataSet1);
//					dataSets.add(lineDataSet2);
//					dataSets.add(lineDataSet3);
//					dataSets.add(lineDataSet4);
//
//
//					Log.d(TAG, "donni-datasets:" + dataSets);
//
//					LineData lineData = new LineData(dataSets);
//					Log.d(TAG, "donni-linedata:" + lineData);
//
//					//mChart8.setVisibleXRangeMaximum(5);
//					//mChart8.setScaleXEnabled(true);
//					mChart8.setData(lineData);
//					mChart8.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

				/*
####################################################################################################
                               LINE CHART TIME SERIES IMPOR BEC INDUSTRI
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "line_timeseries_impor_bec_industri_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url9:" + url);
//		Request request9 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request9).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response7:" + body);
//
//				try {
//
//					//body = '{"periode":["2017/1",'
//					JSONObject json = new JSONObject(body);
//					JSONArray periodeJson = json.getJSONArray("Periode");
//					JSONArray konsumsiJson = json.getJSONArray("Barang_Konsumsi");
//					JSONArray bakuJson = json.getJSONArray("Bahan_Baku");
//					JSONArray modalJson = json.getJSONArray("Barang_Modal");
//
//					ArrayList<Entry> valKonsumsi = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double konsumsi = konsumsiJson.getDouble(i);
//						valKonsumsi.add(new Entry(i + 1, (konsumsi.floatValue())));
//
//					}
//
//					ArrayList<Entry> valBaku = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double baku = bakuJson.getDouble(i);
//						valBaku.add(new Entry(i + 1, (baku.floatValue())));
//
//					}
//
//					ArrayList<Entry> valModal = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double modal = modalJson.getDouble(i);
//						valModal.add(new Entry(i + 1, (modal.floatValue())));
//
//					}
//
//
//					LineDataSet lineDataSet1 = new LineDataSet(valKonsumsi, "Barang Modal");
//					lineDataSet1.setDrawValues(false);
//					lineDataSet1.setDrawCircleHole(true);
//					lineDataSet1.setColor(Color.RED);
//					lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet1.setDrawCircles(true);
//					lineDataSet1.setCubicIntensity(0.15f);
//					lineDataSet1.setCircleColor(Color.RED);
//					lineDataSet1.setLineWidth(1);
//					lineDataSet1.setDrawFilled(true);
//					lineDataSet1.setFillColor(Color.RED);
//
//					LineDataSet lineDataSet2 = new LineDataSet(valBaku, "Barang Konsumsi");
//					lineDataSet2.setDrawValues(false);
//					lineDataSet2.setDrawCircleHole(true);
//					lineDataSet2.setColor(Color.GREEN);
//					lineDataSet2.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet2.setDrawCircles(true);
//					lineDataSet2.setCubicIntensity(0.15f);
//					lineDataSet2.setCircleColor(Color.GREEN);
//					lineDataSet2.setLineWidth(1);
//					lineDataSet2.setDrawFilled(true);
//					lineDataSet2.setFillColor(Color.GREEN);
//
//					LineDataSet lineDataSet3 = new LineDataSet(valModal, "Bahan Baku");
//					lineDataSet3.setDrawValues(false);
//					lineDataSet3.setDrawCircleHole(true);
//					lineDataSet3.setColor(Color.YELLOW);
//					lineDataSet3.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet3.setDrawCircles(true);
//					lineDataSet3.setCubicIntensity(0.15f);
//					lineDataSet3.setCircleColor(Color.YELLOW);
//					lineDataSet3.setLineWidth(1);
//					lineDataSet3.setDrawFilled(true);
//					lineDataSet3.setFillColor(Color.YELLOW);
//
//					Description description = new Description();
//					description.setText("");
//					mChart9.setDescription(description);
//
//
//					ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
//					dataSets.add(lineDataSet1);
//					dataSets.add(lineDataSet2);
//					dataSets.add(lineDataSet3);
//
//					Log.d(TAG, "donni-datasets:" + dataSets);
//
//					LineData lineData = new LineData(dataSets);
//					Log.d(TAG, "donni-linedata:" + lineData);
//
//					//mChart8.setVisibleXRangeMaximum(5);
//					//mChart8.setScaleXEnabled(true);
//					mChart9.setData(lineData);
//					mChart9.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

						/*
####################################################################################################
                               LINE CHART TIME SERIES EKSPOR BEC INDUSTRI
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "line_timeseries_ekspor_bec_industri_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url10:" + url);
//		Request request10 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request10).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response7:" + body);
//
//				try {
//
//					//body = '{"periode":["2017/1",'
//					JSONObject json = new JSONObject(body);
//					JSONArray periodeJson = json.getJSONArray("Periode");
//					JSONArray konsumsiJson = json.getJSONArray("Barang_Konsumsi");
//					JSONArray bakuJson = json.getJSONArray("Bahan_Baku");
//					JSONArray modalJson = json.getJSONArray("Barang_Modal");
//
//					ArrayList<Entry> valKonsumsi = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double konsumsi = konsumsiJson.getDouble(i);
//						valKonsumsi.add(new Entry(i + 1, (konsumsi.floatValue())));
//
//					}
//
//					ArrayList<Entry> valBaku = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double baku = bakuJson.getDouble(i);
//						valBaku.add(new Entry(i + 1, (baku.floatValue())));
//
//					}
//
//					ArrayList<Entry> valModal = new ArrayList<>();
//					for(int i = 0; i < periodeJson.length(); i++) {
//						String lbl = periodeJson.getString(i);
//						Double modal = modalJson.getDouble(i);
//						valModal.add(new Entry(i + 1, (modal.floatValue())));
//
//					}
//
//
//					LineDataSet lineDataSet1 = new LineDataSet(valKonsumsi, "Bahan Baku");
//					lineDataSet1.setDrawValues(false);
//					lineDataSet1.setDrawCircleHole(true);
//					lineDataSet1.setColor(Color.RED);
//					lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet1.setDrawCircles(true);
//					lineDataSet1.setCubicIntensity(0.15f);
//					lineDataSet1.setCircleColor(Color.RED);
//					lineDataSet1.setLineWidth(1);
//					lineDataSet1.setDrawFilled(true);
//					lineDataSet1.setFillColor(Color.RED);
//
//
//					LineDataSet lineDataSet2 = new LineDataSet(valBaku, "Barang Konsumsi");
//					lineDataSet2.setDrawValues(false);
//					lineDataSet2.setDrawCircleHole(true);
//					lineDataSet2.setColor(Color.GREEN);
//					lineDataSet2.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet2.setDrawCircles(true);
//					lineDataSet2.setCubicIntensity(0.15f);
//					lineDataSet2.setCircleColor(Color.GREEN);
//					lineDataSet2.setLineWidth(1);
//					lineDataSet2.setDrawFilled(true);
//					lineDataSet2.setFillColor(Color.GREEN);
//
//
//					LineDataSet lineDataSet3 = new LineDataSet(valModal, "Barang MOdal");
//					lineDataSet3.setDrawValues(false);
//					lineDataSet3.setDrawCircleHole(true);
//					lineDataSet3.setColor(Color.YELLOW);
//					lineDataSet3.setMode(LineDataSet.Mode.LINEAR);
//					lineDataSet3.setDrawCircles(true);
//					lineDataSet3.setCubicIntensity(0.15f);
//					lineDataSet3.setCircleColor(Color.YELLOW);
//					lineDataSet3.setLineWidth(1);
//					lineDataSet3.setDrawFilled(true);
//					lineDataSet3.setFillColor(Color.YELLOW);
//
//
//
//					Description description = new Description();
//					description.setText("");
//					mChart10.setDescription(description);
//
//
//					ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
//					dataSets.add(lineDataSet1);
//					dataSets.add(lineDataSet2);
//					dataSets.add(lineDataSet3);
//
//					Log.d(TAG, "donni-datasets:" + dataSets);
//
//					LineData lineData = new LineData(dataSets);
//					Log.d(TAG, "donni-linedata:" + lineData);
//
//					//mChart8.setVisibleXRangeMaximum(5);
//					//mChart8.setScaleXEnabled(true);
//					mChart10.setData(lineData);
//					mChart10.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

						/*
####################################################################################################
                               BAR CHART TOP 10 HS IMPOR Industri
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "bar_top_10_HS_impor_industri_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url11:" + url);
//		Request request11 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request11).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response11:" + body);
//
//				try {
//
//					JSONObject json = new JSONObject(body);
//					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
//					//JSONArray uraianJson = json.getJSONArray("Uraian");
//					JSONArray imporJson = json.getJSONArray("Impor");
//
//
//
//					//ArrayList<BarEntry> ekspor = new ArrayList<>();
//					//ekspor.add(new BarEntry(0, eks));
//
//					int jumlahdata = kodehsJson.length();
//					//jumlahdata=5;
//
//					ArrayList<BarEntry> valImpor = new ArrayList<>();
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						Double impor = imporJson.getDouble(i);
//						valImpor.add(new BarEntry(i + 1, (impor.floatValue())));
//
//					}
//
//					BarDataSet dsimpor = new BarDataSet(valImpor, "Top 10 HS Impor");
//					dsimpor.setColors(new int[]{
//							Color.RED
//					});
//					dsimpor.setValueTextSize(10);
//
//
//					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//					dataSets.add(dsimpor);
//
//					BarData Data = new BarData(dataSets);
//					Log.d(TAG, "donni-bar-data:" + Data);
//
//
//					final ArrayList<String> xAxisLabel = new ArrayList<>();
//					//xAxisLabel.add("");
//
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						xAxisLabel.add("HS:"+lbl);
//
//					}
//					//xAxisLabel.add("");
//
//					XAxis xAxis = mChart11.getXAxis();
//					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//					xAxis.setValueFormatter(new IAxisValueFormatter() {
//						@Override
//						public String getFormattedValue(float value, AxisBase axis) {
//							if (value >= 0) {
//								if (value <= xAxisLabel.size() - 1) {
//									return xAxisLabel.get((int) value);
//								}
//								return "";
//							}
//							return "";
//						}
//					});
//
//					xAxis.setDrawGridLines(false);
//					xAxis.setEnabled(true);
//					xAxis.setTextSize(10);
//					xAxis.setPosition(XAxisPosition.BOTTOM);
//					xAxis.setLabelRotationAngle(315f);
//					xAxis.setLabelCount(12, true);
//					xAxis.enableGridDashedLine(2f, 7f, 0f);
//					xAxis.setGranularityEnabled(true);
//					xAxis.setGranularity(7f);
//
//					xAxis.setCenterAxisLabels(true);
//					xAxis.setDrawLimitLinesBehindData(true);
//
//					float groupSpace = 0.2f;
//					float barSpace = 0f;
//					float barWidth = 0.16f;
//
//					Description description = new Description();
//					description.setText("");
//					mChart11.setDescription(description);
//					mChart11.getLegend().setEnabled(false);
//					mChart11.setData(Data);
//					mChart11.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});
/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri
####################################################################################################
 */
//		url = Globals.base_host_bigdata   + "bar_top_10_HS_ekspor_industri_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url12:" + url);
//		Request request12 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request12).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response12:" + body);
//
//				try {
//
//					JSONObject json = new JSONObject(body);
//					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
//					//JSONArray uraianJson = json.getJSONArray("Uraian");
//					JSONArray imporJson = json.getJSONArray("Ekspor");
//
//
//
//					//ArrayList<BarEntry> ekspor = new ArrayList<>();
//					//ekspor.add(new BarEntry(0, eks));
//
//					int jumlahdata = kodehsJson.length();
//					//jumlahdata=5;
//
//					ArrayList<BarEntry> valEkspor = new ArrayList<>();
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						Double ekspor = imporJson.getDouble(i);
//						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));
//
//					}
//
//					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
//					dsekspor.setColors(new int[]{
//							Color.GREEN
//					});
//					dsekspor.setValueTextSize(10);
//
//
//					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//					dataSets.add(dsekspor);
//
//					BarData Data = new BarData(dataSets);
//					Log.d(TAG, "donni-bar-data:" + Data);
//
//
//					final ArrayList<String> xAxisLabel = new ArrayList<>();
//					//xAxisLabel.add("");
//
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						xAxisLabel.add("HS:"+lbl);
//
//					}
//					//xAxisLabel.add("");
//
//					XAxis xAxis = mChart12.getXAxis();
//					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//					xAxis.setValueFormatter(new IAxisValueFormatter() {
//						@Override
//						public String getFormattedValue(float value, AxisBase axis) {
//							if (value >= 0) {
//								if (value <= xAxisLabel.size() - 1) {
//									return xAxisLabel.get((int) value);
//								}
//								return "";
//							}
//							return "";
//						}
//					});
//
//					xAxis.setDrawGridLines(false);
//					xAxis.setEnabled(true);
//					xAxis.setTextSize(10);
//					xAxis.setPosition(XAxisPosition.BOTTOM);
//					xAxis.setLabelRotationAngle(315f);
//					xAxis.setLabelCount(12, true);
//					xAxis.enableGridDashedLine(2f, 7f, 0f);
//					xAxis.setGranularityEnabled(true);
//					xAxis.setGranularity(7f);
//
//					xAxis.setCenterAxisLabels(true);
//					xAxis.setDrawLimitLinesBehindData(true);
//
//					float groupSpace = 0.2f;
//					float barSpace = 0f;
//					float barWidth = 0.16f;
//
//					Description description = new Description();
//					description.setText("");
//					mChart12.setDescription(description);
//					mChart12.getLegend().setEnabled(false);
//					mChart12.setData(Data);
//					mChart12.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		/*
####################################################################################################
                               BAR CHART TOP 10 Negara IMPOR Industri
####################################################################################################
// */
//		url = Globals.base_host_bigdata   + "bar_top_10_negara_impor_industri_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url13:" + url);
//		Request request13 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request13).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response13:" + body);
//
//				try {
//
//					JSONObject json = new JSONObject(body);
//					JSONArray kodehsJson = json.getJSONArray("Negara");
//					//JSONArray uraianJson = json.getJSONArray("Uraian");
//					JSONArray imporJson = json.getJSONArray("Impor");
//
//
//
//					//ArrayList<BarEntry> ekspor = new ArrayList<>();
//					//ekspor.add(new BarEntry(0, eks));
//
//					int jumlahdata = kodehsJson.length();
//					//jumlahdata=5;
//
//					ArrayList<BarEntry> valImpor = new ArrayList<>();
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						Double impor = imporJson.getDouble(i);
//						valImpor.add(new BarEntry(i + 1, (impor.floatValue())));
//
//					}
//
//					BarDataSet dsimpor = new BarDataSet(valImpor, "Top 10 Negara Impor");
//					dsimpor.setColors(new int[]{
//							Color.RED
//					});
//					dsimpor.setValueTextSize(10);
//
//
//					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//					dataSets.add(dsimpor);
//
//					BarData Data = new BarData(dataSets);
//					Log.d(TAG, "donni-bar-data:" + Data);
//
//
//					final ArrayList<String> xAxisLabel = new ArrayList<>();
//					//xAxisLabel.add("");
//
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						xAxisLabel.add(lbl);
//
//					}
//					//xAxisLabel.add("");
//
//					XAxis xAxis = mChart13.getXAxis();
//					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//					xAxis.setValueFormatter(new IAxisValueFormatter() {
//						@Override
//						public String getFormattedValue(float value, AxisBase axis) {
//							if (value >= 0) {
//								if (value <= xAxisLabel.size() - 1) {
//									return xAxisLabel.get((int) value);
//								}
//								return "";
//							}
//							return "";
//						}
//					});
//
//					xAxis.setDrawGridLines(false);
//					xAxis.setEnabled(true);
//					xAxis.setTextSize(10);
//					xAxis.setPosition(XAxisPosition.BOTTOM);
//					xAxis.setLabelRotationAngle(315f);
//					xAxis.setLabelCount(12, true);
//					xAxis.enableGridDashedLine(2f, 7f, 0f);
//					xAxis.setGranularityEnabled(true);
//					xAxis.setGranularity(7f);
//
//					xAxis.setCenterAxisLabels(true);
//					xAxis.setDrawLimitLinesBehindData(true);
//
//					float groupSpace = 0.2f;
//					float barSpace = 0f;
//					float barWidth = 0.16f;
//
//					Description description = new Description();
//					description.setText("");
//					mChart13.setDescription(description);
//					mChart13.getLegend().setEnabled(false);
//					mChart13.setData(Data);
//					mChart13.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

				/*
####################################################################################################
                               BAR CHART TOP 10 Negara Ekspor Industri
####################################################################################################
 */
//		url = Globals.base_host_bigdata   + "bar_top_10_negara_ekspor_industri_dash?req=1" ;
//		if(url == null) {
//			return;
//		}
//
//		Log.d(TAG, "url14:" + url);
//		Request request14 = new Request.Builder()
//				.url(url)
//				.build();
//
//		mOkHttpClient.newCall(request14).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				PopupUtil.dismissDialog();
//				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				PopupUtil.dismissDialog();
//
//				String body = response.body().string();
//				Log.d(TAG, "response14:" + body);
//
//				try {
//
//					JSONObject json = new JSONObject(body);
//					JSONArray kodehsJson = json.getJSONArray("Negara");
//					//JSONArray uraianJson = json.getJSONArray("Uraian");
//					JSONArray eksporJson = json.getJSONArray("Ekspor");
//
//
//
//					//ArrayList<BarEntry> ekspor = new ArrayList<>();
//					//ekspor.add(new BarEntry(0, eks));
//
//					int jumlahdata = kodehsJson.length();
//					//jumlahdata=5;
//
//					ArrayList<BarEntry> valEkspor = new ArrayList<>();
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						Double ekspor = eksporJson.getDouble(i);
//						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));
//
//					}
//
//					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 Negara Ekspor");
//					dsekspor.setColors(new int[]{
//							Color.GREEN
//					});
//					dsekspor.setValueTextSize(10);
//
//
//					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//					dataSets.add(dsekspor);
//
//					BarData Data = new BarData(dataSets);
//					Log.d(TAG, "donni-bar-data:" + Data);
//
//
//					final ArrayList<String> xAxisLabel = new ArrayList<>();
//					//xAxisLabel.add("");
//
//					for(int i = 0; i < jumlahdata; i++) {
//						String lbl = kodehsJson.getString(i);
//						xAxisLabel.add(lbl);
//
//					}
//					//xAxisLabel.add("");
//
//					XAxis xAxis = mChart14.getXAxis();
//					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
//
//					xAxis.setValueFormatter(new IAxisValueFormatter() {
//						@Override
//						public String getFormattedValue(float value, AxisBase axis) {
//							if (value >= 0) {
//								if (value <= xAxisLabel.size() - 1) {
//									return xAxisLabel.get((int) value);
//								}
//								return "";
//							}
//							return "";
//						}
//					});
//
//					xAxis.setDrawGridLines(false);
//					xAxis.setEnabled(true);
//					xAxis.setTextSize(10);
//					xAxis.setPosition(XAxisPosition.BOTTOM);
//					xAxis.setLabelRotationAngle(315f);
//					xAxis.setLabelCount(12, true);
//					xAxis.enableGridDashedLine(2f, 7f, 0f);
//					xAxis.setGranularityEnabled(true);
//					xAxis.setGranularity(7f);
//
//					xAxis.setCenterAxisLabels(true);
//					xAxis.setDrawLimitLinesBehindData(true);
//
//					float groupSpace = 0.2f;
//					float barSpace = 0f;
//					float barWidth = 0.16f;
//
//					Description description = new Description();
//					description.setText("");
//					mChart14.setDescription(description);
//					mChart14.getLegend().setEnabled(false);
//					mChart14.setData(Data);
//					mChart14.invalidate();
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

						/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=116" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request15 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request15).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response15:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart15.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart15.setDescription(description);
					mChart15.getLegend().setEnabled(false);
					mChart15.setData(Data);
					mChart15.invalidate();
					mChart15.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart15.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart15 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//end barchar15

								/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 1
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=111" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request16 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request16).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response16:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart16.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart16.setDescription(description);
					mChart16.getLegend().setEnabled(false);
					mChart16.setData(Data);
					mChart16.invalidate();
					mChart16.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart16.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart16 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart

								/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 2
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=121" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request17 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request17).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response17:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart17.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart17.setDescription(description);
					mChart17.getLegend().setEnabled(false);
					mChart17.setData(Data);
					mChart17.invalidate();
					mChart17.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart17.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart17 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart

		/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 3
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=122" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request18 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request18).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response18:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart18.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart18.setDescription(description);
					mChart18.getLegend().setEnabled(false);
					mChart18.setData(Data);
					mChart18.invalidate();
					mChart18.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart18.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart18 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart


		/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 4
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=114" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request19 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request19).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response19:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart19.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart19.setDescription(description);
					mChart19.getLegend().setEnabled(false);
					mChart19.setData(Data);
					mChart19.invalidate();
					mChart19.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart19.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart19 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart


				/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 5
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=124" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request20 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request20).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response20:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart20.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart20.setDescription(description);
					mChart20.getLegend().setEnabled(false);
					mChart20.setData(Data);
					mChart20.invalidate();
					mChart20.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart20.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart20 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart


		/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 6
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=411" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request21 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request21).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response21:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart21.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart21.setDescription(description);
					mChart21.getLegend().setEnabled(false);
					mChart21.setData(Data);
					mChart21.invalidate();
					mChart21.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart21.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart21 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart

		/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 7
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=133" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request22 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request22).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response22:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart22.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart22.setDescription(description);
					mChart22.getLegend().setEnabled(false);
					mChart22.setData(Data);
					mChart22.invalidate();
					mChart22.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart22.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart22 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart

		/*
####################################################################################################
		BAR CHART TOP 10 HS EKSPOR Industri TEST 8
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "bar_top_10_hs_negara_impor_industri_imp?kd_negara=311" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url12:" + url);
		Request request23 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request23).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response23:" + body);

				try {

					JSONObject json = new JSONObject(body);
					JSONArray kodehsJson = json.getJSONArray("Kode_HS");
					JSONArray imporJson = json.getJSONArray("Impor");
					//JSONArray uraianJson = json.getJSONArray("Uraian");



					//ArrayList<BarEntry> ekspor = new ArrayList<>();
					//ekspor.add(new BarEntry(0, eks));

					int jumlahdata = kodehsJson.length();
					//jumlahdata=5;

					final ArrayList<BarEntry> valEkspor = new ArrayList<>();
					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						Double ekspor = imporJson.getDouble(i);
						valEkspor.add(new BarEntry(i + 1, (ekspor.floatValue())));

					}

					BarDataSet dsekspor = new BarDataSet(valEkspor, "Top 10 HS Ekspor");
					dsekspor.setColors(new int[]{
							Color.RED
					});
					dsekspor.setValueTextSize(10);


					ArrayList<IBarDataSet> dataSets = new ArrayList<>();
					dataSets.add(dsekspor);

					BarData Data = new BarData(dataSets);
					Log.d(TAG, "donni-bar-data:" + Data);


					final ArrayList<String> xAxisLabel = new ArrayList<>();
					//xAxisLabel.add("");

					for(int i = 0; i < jumlahdata; i++) {
						String lbl = kodehsJson.getString(i);
						xAxisLabel.add("HS:"+lbl);

					}
					//xAxisLabel.add("");

					XAxis xAxis = mChart23.getXAxis();
					//xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

					xAxis.setValueFormatter(new IAxisValueFormatter() {
						@Override
						public String getFormattedValue(float value, AxisBase axis) {
							if (value >= 0) {
								if (value <= xAxisLabel.size() - 1) {
									return xAxisLabel.get((int) value);
								}
								return "";
							}
							return "";
						}
					});

					xAxis.setDrawGridLines(false);
					xAxis.setEnabled(true);
					xAxis.setTextSize(10);
					xAxis.setPosition(XAxisPosition.BOTTOM);
					xAxis.setLabelRotationAngle(315f);
					xAxis.setLabelCount(12, true);
					xAxis.enableGridDashedLine(2f, 7f, 0f);
					xAxis.setGranularityEnabled(true);
					xAxis.setGranularity(7f);


					xAxis.setCenterAxisLabels(true);
					xAxis.setDrawLimitLinesBehindData(true);

					float groupSpace = 0.2f;
					float barSpace = 0f;
					float barWidth = 0.16f;

					Description description = new Description();
					description.setText("");
					mChart23.setDescription(description);
					mChart23.getLegend().setEnabled(false);
					mChart23.setData(Data);
					mChart23.invalidate();
					mChart23.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
						@Override
						public void onValueSelected(Entry e, Highlight h) {
							int x = mChart19.getData().getDataSetForEntry(e).getEntryIndex((BarEntry)e);
							if (mChart19 == null) {
								return;
							}

						}

						@Override
						public void onNothingSelected() {

						}
					});

				}

				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//endbarchart
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
   	        Toast.makeText(getActivity(), "pass", Toast.LENGTH_SHORT).show();
   	}
   	catch(Exception e)
   	{
   	        Log.e("log_tag", "Error in http connection "+e.toString());
   	       Toast.makeText(getActivity(), "Connection fail", Toast.LENGTH_SHORT).show();

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
	                Toast.makeText(getActivity(), "Input Reading pass", Toast.LENGTH_SHORT).show();
	        }
	        is.close();

	        result=sb.toString();
	}
	catch(Exception e)
	{
	       Log.e("log_tag", "Error converting result "+e.toString());
	    Toast.makeText(getActivity(), " Input reading fail", Toast.LENGTH_SHORT).show();

	}

	//parse json data

	try
	{

		JSONArray jArray = new JSONArray(result);

	//String re=jArray.getString(jArray.length()-1);

	//----------- List utk chart --------------


//-----------------------------

	lvKeyword = (ListView) v.findViewById(R.id.lvDashboard);

	listKeyword = new ArrayList<Keyword>();
	listHSKeyword = new ArrayList<Keyword>();
	listHSArrayString = new ArrayList<String>();
	listUraianHSArrayString = new ArrayList<String>();

//
//	prog_one = (ProgressWheel) v.findViewById(R.id.prog_one);
//	prog_two = (ProgressWheel) v.findViewById(R.id.prog_two);
//	prog_three = (ProgressWheel) v.findViewById(R.id.prog_three);
//		prog_four = (ProgressWheel) v.findViewById(R.id.prog_four);


    tv_prog_one = (TextView) v.findViewById(R.id.tv_prog_one);
    tv_prog_two = (TextView) v.findViewById(R.id.tv_prog_two);
    tv_prog_three = (TextView) v.findViewById(R.id.tv_prog_three);
	tv_prog_four = (TextView) v.findViewById(R.id.tv_prog_four);

    tv_prog_one_lbldown = (TextView) v.findViewById(R.id.textView3);

    tv_prog_two_lbldown = (TextView) v.findViewById(R.id.textView5);

    tv_prog_three_lbldown = (TextView) v.findViewById(R.id.textView7);

	tv_prog_four_lbldown = (TextView) v.findViewById(R.id.textView8);

    //prog_two.setProgress((int)(33*3.6f));  	// Same above
    tv_prog_two.setText(33+"");

    //prog_three.setProgress((int)(20*3.6f));
    tv_prog_three.setText(20+"");


	int flag=1;

		for(int i=1;i<=4;i++){
			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.AmbilJson(Globals.url_graph+"?kon=graph"+i);
			try{
				grapik = json.getJSONArray("dash");
				JSONObject ad = grapik.getJSONObject(0);

				if (i==1){
					//prog_one.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
					tv_prog_one.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
					tv_prog_one_lbldown.setText(ad.getString(AR_DATE)+"");

				}

				/*
				if (i==2){
					prog_two.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
					tv_prog_two.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
					tv_prog_two_lbldown.setText(ad.getString(AR_DATE)+"");

				}
				if (i==3){
					prog_three.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
					tv_prog_three.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
					tv_prog_three_lbldown.setText(ad.getString(AR_DATE)+"");

				}
				if (i==4){
					prog_four.setProgress((int)(ad.getInt(AR_TIME))); 	// Set progress 50%
					tv_prog_four.setText(ad.getInt(AR_TIME)+"");				// Set value 50 for TextView
					tv_prog_four_lbldown.setText(ad.getString(AR_DATE)+"");

				}
				*/


			}catch (JSONException e){
				e.printStackTrace();
			}
		}
	}
	catch(JSONException e)
	{
	        Log.e("log_tag", "Error parsing data "+e.toString());
	        Toast.makeText(getActivity(), "JsonArray fail", Toast.LENGTH_SHORT).show();
	}


	}

	private Keyword addKeyword(String text) {
		Keyword keyword = new Keyword();
		keyword.setText(text);
		//keyword.setText(text);
		return keyword;
	}

	public void setNewPage(Fragment fragment, int pageIndex) {
/*
        final FragmentManager fm = getFragmentManager();
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
*/

		getFragmentManager().beginTransaction()

				.replace(R.id.fragment_dashboard, fragment, "currentFragment")
				.commit();

		//FragmentManager.popBackStack(String name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	public void onNews(View v) {
   		setNewPage(new LonjakanActivity(), FRAGMENT_NEWS);
	}

	public void onFindHS(View v) {
    	setNewPage(new VisitorActivity(), FRAGMENT_FINDHS);
    }
	public void onAbout(View v) {
		setNewPage(new AboutActivity(), FRAGMENT_ABOUT);
	}

	public void onPerusahaan(View v) {
		//masih error........ test lagi script: doInBackground
		setNewPage(new PerusahaanActivity(), FRAGMENT_PERUSAHAAN);
	}

}
