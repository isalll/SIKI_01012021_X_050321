package com.siki.android.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.siki.android.AboutActivity;
import com.siki.android.Globals;
import com.siki.android.Keyword;
import com.siki.android.KeywordAdapter;
import com.siki.android.LonjakanActivity;
import com.siki.android.PerusahaanActivity;
import com.siki.android.R;
import com.siki.android.VisitorActivity;
import com.siki.android.utils.ConnectivityUtil;
import com.siki.android.utils.PopupUtil;
import com.siki.library.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.mordred.wordcloud.WordCloud;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

//import net.alhazmy13.example.R;
//import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.Random;

//import com.example.android.fragments.ArticleFragment;
//import com.example.android.fragments.ArticleFragment;
//import com.example.android.fragments.ArticleFragment;
//import com.github.mikephil.charting.data;
//import android.app.FragmentTransaction;




@SuppressLint("NewApi")
public class MediaFragment extends Fragment {
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

	public static final int FRAGMENT_DASHBOARD = 0;
	public static final int FRAGMENT_NEWS = 1;
	public static final int FRAGMENT_FINDHS = 2;
	public static final int FRAGMENT_ABOUT = 3;
	public static final int FRAGMENT_PERUSAHAAN = 4;

	public static final String TAG = "DashActivity";
	private OkHttpClient mOkHttpClient = new OkHttpClient();
	private PieChart mChart1;
	private PieChart mChart2;
	private PieChart mChart3;
	private PieChart mChart4;
	private PieChart mChart5;
	private PieChart mChart6;
	private BarChart mChart7;
	private LineChart mChart8;
	private LineChart mChart9;
	private LineChart mChart10;
	private BarChart mChart11;
	private BarChart mChart12;
	private BarChart mChart13;
	private BarChart mChart14;

	//private ProgressWheel prog_one, prog_two, prog_three, prog_four;
	private TextView tv_prog_one, tv_prog_two, tv_prog_three, tv_prog_four, tv_title,tv_catatan,tv_title_chart1,tv_title_chart2,tv_title_chart3,tv_title_chart4,
			tv_title_chart5,tv_title_chart6,tv_title_chart7,tv_title_chart8,tv_title_chart9,tv_title_chart10,tv_title_chart11,tv_title_chart12,tv_title_chart13,tv_title_chart14;
	private TextView tv_prog_one_lblup,tv_prog_one_lbldown,tv_prog_two_lblup,tv_prog_two_lbldown,tv_prog_three_lblup,tv_prog_three_lbldown,tv_prog_four_lbldown;

	private TabHost mTabHost;
	private ListView lvKeyword;
	private KeywordAdapter adapterKeyword;
	private ArrayList<Keyword> listKeyword;
	private ArrayList<Keyword> listHSKeyword;

	TabWidget tabWidget;
	public static final int FRAGMENT_CHARTHS = 5;

		  private String[] mMenuText;

		  private String[] mMenuSummary;
		  //private String[] listHS;



	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	List<WordCloud> listwc ;
	String text = "Otomotif Battery Handphone Oil Agro IoT";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		final View v = inflater.inflate(R.layout.activity_media, container, false);
		//final View v = inflater.inflate(R.layout.activity_dashboard, container, false);

	   	StrictMode.setThreadPolicy(policy);

	   	/*
		mChart1 = (PieChart) v.findViewById(R.id.chart_bec1);
		mChart2 = (PieChart) v.findViewById(R.id.chart_bec2);
		mChart3 = (PieChart) v.findViewById(R.id.chart_sektor1);
		mChart4 = (PieChart) v.findViewById(R.id.chart_sektor2);
		mChart5 = (PieChart) v.findViewById(R.id.chart_subsektor1);
		mChart6 = (PieChart) v.findViewById(R.id.chart_subsektor2);
		mChart7 = (BarChart) v.findViewById(R.id.chart_total_eksim);
		mChart8 = (LineChart) v.findViewById(R.id.chart_timeseries_eksim);
		mChart9 = (LineChart) v.findViewById(R.id.chart_timeseries_impor_bec_industri);
		mChart10 = (LineChart) v.findViewById(R.id.chart_timeseries_ekspor_bec_industri);
		mChart11 = (BarChart) v.findViewById(R.id.chart_top_10_HS_impor);
		mChart12 = (BarChart) v.findViewById(R.id.chart_top_10_HS_ekspor);
		mChart13 = (BarChart) v.findViewById(R.id.chart_top_10_negara_impor);
		mChart14 = (BarChart) v.findViewById(R.id.chart_top_10_negara_ekspor);
*/
		mChart8 = (LineChart) v.findViewById(R.id.chart_timeseries_eksim);

		if(ConnectivityUtil.isConnected(getActivity())) {
			//loadData();
		}
		else {
			PopupUtil.showMsg(getActivity(), "Can't load data, no internet connection", PopupUtil.SHORT);
		}

     init(v);


		return v;
	}


	private void init(View v) {

		//cari status data terakhir diserver ......>>>
		String thn_min = null;
		String thn_max = null;
		String bln_max = null;
		 url = Globals.base_host_bigdata   + "infodatabase?req=1" ;

		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.AmbilJson(url);
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

		//tv_catatan = (TextView) v.findViewById(R.id.textViewCatatan);
		//tv_catatan.setText("Data ekspor-impor tersedia dari tahun : " + thn_min+" s.d "+thn_max+". Data terupdate bulan : "+bln_max+" tahun "+thn_max );
		tv_title = (TextView) v.findViewById(R.id.tv_title);
        tv_title.setText("Analisis Media");

		tv_title_chart1 = (TextView) v.findViewById(R.id.tv_title_wordcloud2);
		tv_title_chart1.setText("Word Cloud Media Industri - ("+bln_max+"/"+thn_max+")");



		//Wordcloud
		/*
		ImageView imgView = (ImageView) v.findViewById(R.id.imageView);

		Map<String, Integer> nMap = new HashMap<>();

		nMap.put("oguzhan", 2);
		nMap.put("mordred", 2);
		nMap.put("is", 4);
		nMap.put("on",2);
		nMap.put("the", 3);
		nMap.put("salda lake",5);

		WordCloud wd = new WordCloud(nMap, 250, 250,Color.RED,Color.WHITE);
		wd.setWordColorOpacityAuto(true);
		wd.setPaddingX(5);
		wd.setPaddingY(5);

		Bitmap generatedWordCloudBmp = wd.generate();

		imgView.setImageBitmap(generatedWordCloudBmp);
*/

		generateRandomText();
		WordCloudView wordCloud = (WordCloudView) v.findViewById(R.id.wordCloud);
		wordCloud.setDataSet(listwc);
		wordCloud.setSize(390,390);
		wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
		wordCloud.notifyDataSetChanged();


	}

	public void generateRandomText() {
		String[] data = text.split(" ");
		listwc = new ArrayList<>();
		Random random = new Random();
		for (String s : data) {
			listwc.add(new WordCloud(s,random.nextInt(50)));
		}
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
                               LINE CHART TIME SERIES EKSIM
####################################################################################################
 */
		url = Globals.base_host_bigdata   + "line_timeseries_eksim_dash?req=1" ;
		if(url == null) {
			return;
		}

		Log.d(TAG, "url8:" + url);
		Request request8 = new Request.Builder()
				.url(url)
				.build();

		mOkHttpClient.newCall(request8).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				PopupUtil.dismissDialog();
				PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				PopupUtil.dismissDialog();

				String body = response.body().string();
				Log.d(TAG, "response7:" + body);

				try {

					//body = '{"periode":["2017/1",'
					JSONObject json = new JSONObject(body);
					JSONArray periodeJson = json.getJSONArray("periode");
					JSONArray imporJson = json.getJSONArray("impor");
					JSONArray eksporJson = json.getJSONArray("ekspor");
					JSONArray eksporIndustriJson = json.getJSONArray("ekspor_industri");
					JSONArray imporIndustriJson = json.getJSONArray("impor_industri");

  				    ArrayList<Entry> valImpor = new ArrayList<>();
					for(int i = 0; i < periodeJson.length(); i++) {
						String lbl = periodeJson.getString(i);
						Double impor = imporJson.getDouble(i);
						valImpor.add(new Entry(i + 1, (impor.floatValue())));

					}

					ArrayList<Entry> valEkspor = new ArrayList<>();
					for(int i = 0; i < periodeJson.length(); i++) {
						String lbl = periodeJson.getString(i);
						Double ekspor = eksporJson.getDouble(i);
						valEkspor.add(new Entry(i + 1, (ekspor.floatValue())));

					}

					ArrayList<Entry> valEksporIndustri = new ArrayList<>();
					for(int i = 0; i < periodeJson.length(); i++) {
						String lbl = periodeJson.getString(i);
						Double eksporIndustri = eksporIndustriJson.getDouble(i);
						valEksporIndustri.add(new Entry(i + 1, (eksporIndustri.floatValue())));

					}

					ArrayList<Entry> valImporIndustri = new ArrayList<>();
					for(int i = 0; i < periodeJson.length(); i++) {
						String lbl = periodeJson.getString(i);
						Double imporIndustri = imporIndustriJson.getDouble(i);
						valImporIndustri.add(new Entry(i + 1, (imporIndustri.floatValue())));

					}

					LineDataSet lineDataSet1 = new LineDataSet(valImpor, "Impor");
					lineDataSet1.setDrawValues(false);
					//lineDataSet1.setDrawCircleHole(true);
					lineDataSet1.setColor(Color.RED);
					lineDataSet1.setMode(LineDataSet.Mode.LINEAR);
					lineDataSet1.setDrawCircles(false);
					lineDataSet1.setCubicIntensity(0.15f);
					lineDataSet1.setCircleColor(Color.RED);
					lineDataSet1.setLineWidth(1);

					LineDataSet lineDataSet2 = new LineDataSet(valEkspor, "Ekspor");
					lineDataSet2.setDrawValues(false);
					//lineDataSet2.setDrawCircleHole(true);
					lineDataSet2.setColor(Color.parseColor("#4CAF50"));
					lineDataSet2.setMode(LineDataSet.Mode.LINEAR);
					lineDataSet2.setDrawCircles(false);
					lineDataSet2.setCubicIntensity(0.15f);
					lineDataSet2.setCircleColor(Color.parseColor("#4CAF50"));
					lineDataSet2.setLineWidth(1);

					LineDataSet lineDataSet3 = new LineDataSet(valEksporIndustri, "Ekspor-Industri");
					lineDataSet3.setDrawValues(false);
					//lineDataSet3.setDrawCircleHole(true);
					lineDataSet3.setColor(Color.BLUE);
					lineDataSet3.setMode(LineDataSet.Mode.LINEAR);
					lineDataSet3.setDrawCircles(false);
					lineDataSet3.setCubicIntensity(0.15f);
					lineDataSet3.setCircleColor(Color.BLUE);
					lineDataSet3.setLineWidth(1);

					LineDataSet lineDataSet4 = new LineDataSet(valImporIndustri, "Impor-Industri");
					lineDataSet4.setDrawValues(false);
					//lineDataSet4.setDrawCircleHole(true);
					lineDataSet4.setColor(Color.MAGENTA);
					lineDataSet4.setMode(LineDataSet.Mode.LINEAR);
					lineDataSet4.setDrawCircles(false);
					lineDataSet4.setCubicIntensity(0.15f);
					lineDataSet4.setCircleColor(Color.MAGENTA);
					lineDataSet4.setLineWidth(1);

					Description description = new Description();
					description.setText("");
					mChart8.setDescription(description);


					ArrayList<ILineDataSet> dataSets = new ArrayList<>();

					dataSets.add(lineDataSet1);
					dataSets.add(lineDataSet2);
					dataSets.add(lineDataSet3);
					dataSets.add(lineDataSet4);


					Log.d(TAG, "donni-datasets:" + dataSets);

					LineData lineData = new LineData(dataSets);
					Log.d(TAG, "donni-linedata:" + lineData);

					//mChart8.setVisibleXRangeMaximum(5);
					//mChart8.setScaleXEnabled(true);
					mChart8.setData(lineData);
					mChart8.invalidate();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});


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
