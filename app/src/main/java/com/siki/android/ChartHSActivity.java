package com.siki.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;

import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.achartengine.renderer.

import android.graphics.Color;
import android.graphics.Paint.Align;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ChartHSActivity extends Fragment{

	private GraphicalView mChart;
	
	private String[] mMonth = new String[] {
				"Jan", "Feb" , "Mar", "Apr", "May", "Jun",
				"Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
			};

	private TextView tv_title,uraian;
	public static final int FRAGMENT_NEWS = 1;
	public static final int FRAGMENT_LONJAKAN = 7;

	ArrayList<String> list_eksdown,list_eksup,list_impdown,list_impup,listHSArrayString,listUraianHSArrayString = new ArrayList<String>(); 

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
	   	container.removeAllViewsInLayout();
   
		View v = inflater.inflate(R.layout.activity_chart_fragment, container, false);
		//Globals.jlhbaris=0;
		

		if (Globals.isLoadingDataImpor==true && Globals.isLoadingDataEkspor==false ){
		LoadTabel(v,"http://iris.kemenperin.go.id/android/getpiechartimpor.php","id",Globals.kd_hs);
		}
		if (Globals.isLoadingDataEkspor==true && Globals.isLoadingDataImpor==false){
		LoadTabel(v,"http://iris.kemenperin.go.id/android/getpiechartekspor.php","id",Globals.kd_hs);
		}
		
		//LoadTabel(v,"http://iris.kemenperin.go.id/android/dsb_impup.php","id",Globals.kd_hs);
		
 
	return v;

    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Intent intent = getActivity().getIntent();

		tv_title =(TextView)getActivity().findViewById(R.id.tv_title);
		   tv_title.setText("Kode HS : " + Globals.kd_hs);
		  uraian= (TextView)getActivity().findViewById(R.id.tv_uraian);
		  uraian.setText(Globals.uraian_hs);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int n= Globals.jlhbaris;
		
		//ok.....................
		//BubbleChart();
		//BarChartView();
		//TimeChartView();
		
		//LineChartView();
       	Toast.makeText(getActivity(), Globals.max_tahun, Toast.LENGTH_SHORT).show();

		if ((n != 0)) PieChartView();
	}

	public void setNewPage(Fragment fragment, int pageIndex) {
		getFragmentManager().beginTransaction()

				.replace(R.id.fragment_chart, fragment, "currentFragment")
				.commit();
	}


	public void LoadTabel(View v, String url, String id, String getvalue) {

    String result = null;
   	InputStream is = null;
   	
   	//int n= Globals.jlhbaris;
   	
   	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

   	nameValuePairs.add(new BasicNameValuePair(id,getvalue));
       	try
       	{
   	        HttpClient httpclient = new DefaultHttpClient();
   	        HttpPost httppost = new HttpPost(url);
   	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	        HttpResponse response = httpclient.execute(httppost); 
   	        HttpEntity entity = response.getEntity();
   	        is = entity.getContent();

   	        Log.e("log_tag", "connection success ");
   	     //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
       	}
       catch(Exception e)
       	{
   	        Log.e("log_tag", "Error in http connection "+e.toString());
   	        Toast.makeText(getActivity(), "Connection fail", Toast.LENGTH_SHORT).show();

       	}
   	//convert response to string
       	try{
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
   	try{
   		
   		JSONArray jArray = new JSONArray(result);
   		Globals.values_hs_arr = new double[jArray.length()];
   		Globals.jlhbaris = jArray.length()-1; //n=8
   		
   		//n=Globals.jlhbaris;
   		Globals.negara_arr= new String[jArray.length()];
   		
   		/*
   		JSONObject object = new JSONObject(result);
         String maxbln=object.getString("max_bulan");
         String maxthn=object.getString("max_tahun");
        
         
         
          	Globals.max_bulan=maxbln;
          	Globals.max_bulan=maxthn;
*/
   		
   		//String re=jArray.getString(jArray.length()-1);
   		
   		//----------- List utk chart --------------
   		
   		//-----------------------------
   	  
   		//listHSArrayString = new ArrayList<String>();
   		//listUraianHSArrayString = new ArrayList<String>();

   		int flag=1;
   		 
   		for(int i=-1;i<jArray.length()-1;i++)
   		        
   		{

   			if(flag==1)
   			            {
   			            //header tabel	
   		      	    		//listKeyword.add(addKeyword("KODE HS"));
   			            	flag=0;
   			            }
   	        
   			            else
   			            {
   	  	            	JSONObject json_data = jArray.getJSONObject(i);
   	  	            	Globals.values_hs_arr[i]=json_data.getDouble("nilaitotal");
   	  	            	//Globals.negara_arr[i]=json_data.getInt("kd_negara");
   	  	            	Globals.negara_arr[i]=json_data.getString("kd_negara"); 
   	  	            	
  	  	            	

   	  	            	//Toast.makeText(getActivity(), i + " : " + json_data.getString("nilaitotal"), Toast.LENGTH_SHORT).show();
   	  	            	//Toast.makeText(getActivity(), json_data.getDouble("nilaitotal"), Toast.LENGTH_SHORT).show();
   	  	            	//Toast.makeText(getActivity(), json_data.getString("nilaitotal"), Toast.LENGTH_SHORT).show();
   	  	            	//Toast.makeText(getActivity(), Integer.toString(Globals.jlhbaris), Toast.LENGTH_SHORT).show();
	    		        
   	    	              //LOAD DATA TABEL KE LIST (Array)
   	    	            //Cek status

   			            }
   			
   			
   	            }
   		
   		//Globals.db_results.add(nums);

   		}
   		catch(JSONException e)
   		{
   		        Log.e("log_tag", "Error parsing data "+e.toString());
   		        Toast.makeText(getActivity(), "JsonArray fail", Toast.LENGTH_SHORT).show();
   		}
   		
     //Toast.makeText(getActivity(), Globals.jlhbaris, Toast.LENGTH_SHORT).show();

   	//Globals.values_hs_pie=db_results;
       //Toast.makeText(getActivity(), listHSArrayString.get(1).toString(), Toast.LENGTH_SHORT).show();
}

	
	  @SuppressWarnings("deprecation")
	private void PieChartView() {
		    
		    //TESt DATA
		    //double[] values = new double[] { 12, 14, 11, 10, 19 };
		  
		  int n= Globals.jlhbaris;
		   double[] values = new double[n];
		   int[] colors = new int[n];
		   String[] labels = new String[n];
		   
		    int[] intwarna = new int[] {Color.YELLOW ,Color.RED,Color.BLUE, Color.GREEN, Color.MAGENTA,Color.CYAN,Color.WHITE,Color.GRAY};

		  
		   for (int i = 0; i < n; i++) {
			   
			  values[i]=Globals.values_hs_arr[i];
			  labels[i]=Globals.negara_arr[i];
			  
			  colors[i]=intwarna[i];
		   }
		  
		    //int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA };
		    

		    DefaultRenderer renderer = buildCategoryRenderer(colors);
		    //renderer.setZoomButtonsVisible(true);
		    //renderer.setZoomEnabled(true);
		    
		    renderer.setBackgroundColor(Color.BLACK);
		    //renderer.setGridColor(Color.GRAY);
		    //renderer.setBackgroundColorHex("#000000");
		    renderer.setLegendTextSize(30);
		    renderer.setDisplayValues(false);
		    renderer.setApplyBackgroundColor(true);

		    renderer.setChartTitleTextSize(30);
		    renderer.setDisplayValues(true);
		    renderer.setShowLabels(true);
		    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
		    r.setGradientEnabled(true);
		    r.setGradientStart(0, Color.BLUE);
		    r.setGradientStop(0, Color.GREEN);
		    r.setHighlighted(true);

		    GraphicalView chartView;

		    chartView= ChartFactory.getPieChartView(getActivity().getBaseContext(), 
			        buildCategoryDatasetLabel("Negara Importir", values,labels),  renderer);
		    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.chart_container);	    
		    
		    layout.addView(chartView, new LayoutParams(960,
		              LayoutParams.FILL_PARENT));
	  
	  }

	
    private void ComboChartView(){
    	int[] x = { 0,1,2,3,4,5,6,7 };
    	int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800};
    	int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };
    	
    	// Creating an  XYSeries for Income
    	XYSeries incomeSeries = new XYSeries("Income");
    	// Creating an  XYSeries for Expense
    	XYSeries expenseSeries = new XYSeries("Expense");
    	// Adding data to Income and Expense Series
    	for(int i=0;i<x.length;i++){
    		incomeSeries.add(x[i], income[i]);
    		expenseSeries.add(x[i],expense[i]);
    	}
    	
    	// Creating a dataset to hold each series
    	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    	// Adding Income Series to the dataset
    	dataset.addSeries(incomeSeries);
    	// Adding Expense Series to dataset
    	dataset.addSeries(expenseSeries);    	
    	
    	
    	// Creating XYSeriesRenderer to customize incomeSeries
    	XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
    	incomeRenderer.setColor(Color.WHITE);
    	incomeRenderer.setPointStyle(PointStyle.CIRCLE);
    	incomeRenderer.setFillPoints(true);
    	incomeRenderer.setLineWidth(2);
    	incomeRenderer.setDisplayChartValues(true);

    	
    	// Creating XYSeriesRenderer to customize expenseSeries
    	XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
    	expenseRenderer.setColor(Color.YELLOW);
    	expenseRenderer.setPointStyle(PointStyle.CIRCLE);
    	expenseRenderer.setFillPoints(true);
    	expenseRenderer.setLineWidth(2);
    	expenseRenderer.setDisplayChartValues(true);
    	
    	
    	
    	// Creating a XYMultipleSeriesRenderer to customize the whole chart
    	XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
    	multiRenderer.setXLabels(0);
    	multiRenderer.setChartTitle("Income vs Expense Chart");
    	multiRenderer.setXTitle("Year 2012");
    	multiRenderer.setYTitle("Amount in Dollars");
    	multiRenderer.setZoomButtonsVisible(true);
    	multiRenderer.setBarSpacing(4);
    	for(int i=0;i<x.length;i++){
    		multiRenderer.addXTextLabel(i, mMonth[i]);    		
    	}    	
    	
    	// Adding incomeRenderer and expenseRenderer to multipleRenderer
    	// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
    	// should be same
    	multiRenderer.addSeriesRenderer(incomeRenderer);
    	multiRenderer.addSeriesRenderer(expenseRenderer);
    	
    	// Getting a reference to LinearLayout of the MainActivity Layout
    	//LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
	    LinearLayout chartContainer = (LinearLayout) getActivity().findViewById(R.id.chart_container);	    
   	
   		
    	// Specifying chart types to be drawn in the graph
    	// Number of data series and number of types should be same
    	// Order of data series and chart type will be same
    	String[] types = new String[] { LineChart.TYPE, BarChart.TYPE };

    	// Creating a combined chart with the chart types specified in types array
//    	mChart = (GraphicalView) ChartFactory.getCombinedXYChartView(getActivity().getBaseContext(), dataset, multiRenderer, types);
  
	    GraphicalView chartView;
	    //errorS
	    //chartView= ChartFactory.getCombinedXYChartView(getActivity().getBaseContext(), dataset, multiRenderer, types);

    	
   		multiRenderer.setClickEnabled(true);
     	multiRenderer.setSelectableBuffer(10);
     	mChart.setOnClickListener(new View.OnClickListener() {
     		@Override
     	    public void onClick(View v) {
     	    	
     			SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();

     			if (seriesSelection != null) {     				
     				int seriesIndex = seriesSelection.getSeriesIndex();
            	  	String selectedSeries="Income";
            	  	if(seriesIndex==0)
            	  		selectedSeries = "Income";
            	  	else
            	  		selectedSeries = "Expense";
            	  	// Getting the clicked Month
          			String month = mMonth[(int)seriesSelection.getXValue()];
          			// Getting the y value 
          			int amount = (int) seriesSelection.getValue();
          			Toast.makeText(
          					getActivity().getBaseContext(),
                	       selectedSeries + " in "  + month + " : " + amount ,
                	       Toast.LENGTH_SHORT).show();
     			}
     		}
  	
     	});
     	
   		// Adding the Combined Chart to the LinearLayout
    	chartContainer.addView(mChart);    	
    }
	
	
	  @SuppressWarnings("deprecation")
	private void TimeChartView() {
		    String[] titles = new String[] { "Logest", "Forecast" };
		    List<Date[]> dates = new ArrayList<Date[]>();
		    List<double[]> values = new ArrayList<double[]>();
		    int length = titles.length;
		    for (int i = 0; i < length; i++) {
		      dates.add(new Date[12]);
		      dates.get(i)[0] = new Date(2015, 9, 1);
		      dates.get(i)[1] = new Date(2015, 9, 8);
		      dates.get(i)[2] = new Date(2015, 9, 15);
		      dates.get(i)[3] = new Date(2015, 9, 22);
		      dates.get(i)[4] = new Date(2015, 9, 29);
		      dates.get(i)[5] = new Date(2015, 10, 5);
		      dates.get(i)[6] = new Date(2015, 10, 12);
		      dates.get(i)[7] = new Date(2015, 10, 19);
		      dates.get(i)[8] = new Date(2015, 10, 26);
		      dates.get(i)[9] = new Date(2015, 11, 3);
		      dates.get(i)[10] = new Date(2015, 11, 10);
		      dates.get(i)[11] = new Date(2015, 11, 17);
		    }
		    values.add(new double[] { 142, 123, 142, 152, 149, 122, 110, 120, 125, 155, 146, 150 });
		    values.add(new double[] { 102, 90, 112, 105, 125, 112, 125, 112, 105, 115, 116, 135 });
		    length = values.get(0).length;
		    int[] colors = new int[] { Color.BLUE, Color.GREEN };
		    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
		    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		    setChartSettings(renderer, "Lonjakan Impor", "Date", "Berat", dates.get(0)[0].getTime(),
		        dates.get(0)[11].getTime(), 50, 190, Color.BLUE, Color.YELLOW);
		    renderer.setXLabels(0);
		    renderer.setYLabels(10);
		    //renderer.addYTextLabel(100, "test");
		    renderer.setBackgroundColor(Color.BLACK);
		    renderer.setGridColor(Color.GRAY);
		    //renderer.setBackgroundColorHex("#000000");
		    renderer.setLegendTextSize(30);
		    renderer.setDisplayValues(false);
		    renderer.setApplyBackgroundColor(true);
		 
		    renderer.setAxesColor(Color.YELLOW);
		    renderer.setShowGridX(true);
		    //renderer.setShowGridY(true);
	    	//renderer.setZoomButtonsVisible(true);
	    	//renderer.setBarSpacing(4);
	    
		    
		    length = renderer.getSeriesRendererCount();
		    for (int i = 0; i < length; i++) {
		      XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
		      seriesRenderer.setDisplayChartValues(true);
		    }
		    renderer.setXRoundedLabels(false);
//		    return ChartFactory.getTimeChartView(getActivity(), buildDateDataset(titles, dates, values),
//		        renderer, "MM/dd/yyyy");

		    final GraphicalView chartView;
		    chartView= ChartFactory.getTimeChartView(getActivity(), buildDateDataset(titles, dates, values),
			        renderer, "MM/dd/yyyy");
		    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.chart_container);	    

		    
	   		renderer.setClickEnabled(true);
	     	renderer.setSelectableBuffer(10);
	     	chartView.setOnClickListener(new View.OnClickListener() {
	     		@Override
	     	    public void onClick(View v) {
	     	    	
	     			
	     			SeriesSelection seriesSelection = chartView.getCurrentSeriesAndPoint();
	     			//error
	     			/*
	     			if (seriesSelection != null) {     				
	     				int seriesIndex = seriesSelection.getSeriesIndex();
	            	  	String selectedSeries="Income";
	            	  	if(seriesIndex==0)
	            	  		selectedSeries = "Income";
	            	  	else
	            	  		selectedSeries = "Expense";
	            	  	// Getting the clicked Month
	          			String month = mMonth[(int)seriesSelection.getXValue()];
	          			// Getting the y value 
	          			int amount = (int) seriesSelection.getValue();
	          		*/
	          		
	     			
	     			//Toast.makeText(	getActivity().getBaseContext(), " touch  : " ,
	                //	       Toast.LENGTH_SHORT).show();
	     			
	     		}
	  	
	     	});
		    
		    
		    
		    layout.addView(chartView, new LayoutParams(960,
		              LayoutParams.FILL_PARENT));
	  
	  
	  }
	
	
	private void BubbleChart() {
	    XYMultipleSeriesDataset series = new XYMultipleSeriesDataset();
	    XYValueSeries newTicketSeries = new XYValueSeries("New Tickets");
	    newTicketSeries.add(1f, 2, 14);
	    newTicketSeries.add(2f, 2, 12);
	    newTicketSeries.add(3f, 2, 18);
	    newTicketSeries.add(4f, 2, 5);
	    newTicketSeries.add(5f, 2, 1);
	    series.addSeries(newTicketSeries);
	    XYValueSeries fixedTicketSeries = new XYValueSeries("Fixed Tickets");
	    fixedTicketSeries.add(1f, 1, 7);
	    fixedTicketSeries.add(2f, 1, 4);
	    fixedTicketSeries.add(3f, 1, 18);
	    fixedTicketSeries.add(4f, 1, 3);
	    fixedTicketSeries.add(5f, 1, 1);
	    series.addSeries(fixedTicketSeries);

	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setRange(new double[]{0, 6, 0, 6});
	    
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
	    newTicketRenderer.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(newTicketRenderer);
	    XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
	    fixedTicketRenderer.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(fixedTicketRenderer);

	    renderer.setXLabels(0);
	    renderer.setYLabels(10);
	    //renderer.setDisplayChartValues(false);
	    renderer.setShowGrid(true);
	    renderer.setShowLegend(true);
	    renderer.setShowLabels(true);
	    
	    renderer.setZoomEnabled(true, true);
	    

        setChartSettings(renderer, "Monthly sales in the last 2 years", "Month", "Units sold", 0.5,
                12.5, 0, 24000, Color.BLACK, Color.LTGRAY);

	    
	    GraphicalView chartView;
		/*if (chartView != null) {
			chartView.repaint();
		}
		else { */
	    
			chartView = ChartFactory.getBubbleChartView(getActivity(), series, renderer);
		//}
		
		
	    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.chart_container);	    
	    //layout.removeAllViews();
	    layout.addView(chartView, new LayoutParams(960,
	              LayoutParams.FILL_PARENT));
	}

    private void BarChartView(){
        String[] titles = new String[] { "2007", "2008" };
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[] { 5230, 7300, 9240, 10540, 7900, 9200, 12030, 11200, 9500, 10500,
            11600, 13500 });
        values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200, 22030, 21200, 19500, 15500,
            12600, 14000 });
        int[] colors = new int[] { Color.CYAN, Color.BLUE };
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(Orientation.HORIZONTAL);
        setChartSettings(renderer, "Monthly sales in the last 2 years", "Month", "Units sold", 0.5,
            12.5, 0, 24000, Color.GRAY, Color.LTGRAY);
        renderer.setXLabels(1);
        renderer.setYLabels(10);
        renderer.addXTextLabel(1, "Jan");
        renderer.addXTextLabel(3, "Mar");
        renderer.addXTextLabel(5, "May");
        renderer.addXTextLabel(7, "Jul");
        renderer.addXTextLabel(10, "Oct");
        renderer.addXTextLabel(12, "Dec");
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
          XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
          seriesRenderer.setDisplayChartValues(true);
        }
 
	    GraphicalView chartView;
		chartView = ChartFactory.getBarChartView(getActivity(), buildBarDataset(titles, values), renderer,
	            Type.DEFAULT);

    	
    	// Creating a combined chart with the chart types specified in types array
    	//mChart = (GraphicalView) ChartFactory.getBarChartView(getActivity(), dataset, multiRenderer);
   		
   		//multiRenderer.setClickEnabled(true);
     	//multiRenderer.setSelectableBuffer(10);
     	chartView.setOnClickListener(new View.OnClickListener() {
     		@Override
     	    public void onClick(View v) {
     	    	
     			SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();

     			if (seriesSelection != null) {     				
     				int seriesIndex = seriesSelection.getSeriesIndex();
            	  	String selectedSeries="Income";
            	  	if(seriesIndex==0)
            	  		selectedSeries = "Income";
            	  	else
            	  		selectedSeries = "Expense";
            	  	// Getting the clicked Month
          			String month = mMonth[(int)seriesSelection.getXValue()];
          			// Getting the y value 
          			int amount = (int) seriesSelection.getValue();
          			Toast.makeText(
          					getActivity().getBaseContext(),
                	       selectedSeries + " in "  + month + " : " + amount ,
                	       Toast.LENGTH_SHORT).show();
     			}
     		}
  	
     	});
     	
   		// Adding the Combined Chart to the LinearLayout
	    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.chart_container);	    
	    //layout.removeAllViews();
	    layout.addView(chartView, new LayoutParams(960,
	              LayoutParams.FILL_PARENT));
    }

    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[] { 20, 30, 15, 0 });
        for (int color : colors) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(color);
          renderer.addSeriesRenderer(r);
        }
        return renderer;
      }
	
	  protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		    renderer.setAxisTitleTextSize(16);
		    renderer.setChartTitleTextSize(20);
		    renderer.setLabelsTextSize(15);
		    renderer.setLegendTextSize(15);
		    int length = colors.length;
		    for (int i = 0; i < length; i++) {
		      XYSeriesRenderer r = new XYSeriesRenderer();
		      r.setColor(colors[i]);
		      renderer.addSeriesRenderer(r);
		    }
		    return renderer;
		  }

	  protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
		      String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
		      int labelsColor) {
		    renderer.setChartTitle(title);
		    renderer.setXTitle(xTitle);
		    renderer.setYTitle(yTitle);
		    renderer.setXAxisMin(xMin);
		    renderer.setXAxisMax(xMax);
		    renderer.setYAxisMin(yMin);
		    renderer.setYAxisMax(yMax);
		    renderer.setAxesColor(axesColor);
		    renderer.setLabelsColor(labelsColor);
		  }
	  protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
		    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		    int length = titles.length;
		    for (int i = 0; i < length; i++) {
		      CategorySeries series = new CategorySeries(titles[i]);
		      double[] v = values.get(i);
		      int seriesLength = v.length;
		      for (int k = 0; k < seriesLength; k++) {
		        series.add(v[k]);
		      }
		      dataset.addSeries(series.toXYSeries());
		    }
		    return dataset;
		  }

	  protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
		    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		    setRenderer(renderer, colors, styles);
		    return renderer;
		  }
	  protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
		    renderer.setAxisTitleTextSize(16);
		    renderer.setChartTitleTextSize(20);
		    renderer.setLabelsTextSize(15);
		    renderer.setLegendTextSize(15);
		    renderer.setPointSize(5f);
		    renderer.setMargins(new int[] { 20, 30, 15, 20 });
		    int length = colors.length;
		    for (int i = 0; i < length; i++) {
		      XYSeriesRenderer r = new XYSeriesRenderer();
		      r.setColor(colors[i]);
		      r.setPointStyle(styles[i]);
		      renderer.addSeriesRenderer(r);
		    }
		  }

	  protected CategorySeries buildCategoryDataset(String title, double[] values) {
		    CategorySeries series = new CategorySeries(title);
		    int k = 0;
		    for (double value : values) {
		      series.add("Project " + ++k, value);
		    }

		    return series;
		  }

	  protected CategorySeries buildCategoryDatasetLabel(String title, double[] values, String[] labels) {
		    CategorySeries series = new CategorySeries(title);

		    int n= Globals.jlhbaris;
			   for (int i = 0; i < n; i++) {
				   
				   series.add(labels[i] , values[i]); 
			   }
			   
			    return series;
					    
		  }

	  protected XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,
		      List<double[]> yValues) {
		    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		    addXYSeries(dataset, titles, xValues, yValues, 0);
		    return dataset;
		  }
	  public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
		      List<double[]> yValues, int scale) {
		    int length = titles.length;
		    for (int i = 0; i < length; i++) {
		      XYSeries series = new XYSeries(titles[i], scale);
		      double[] xV = xValues.get(i);
		      double[] yV = yValues.get(i);
		      int seriesLength = xV.length;
		      for (int k = 0; k < seriesLength; k++) {
		        series.add(xV[k], yV[k]);
		      }
		      dataset.addSeries(series);
		    }
		  }

	  protected XYMultipleSeriesDataset buildDateDataset(String[] titles, List<Date[]> xValues,
		      List<double[]> yValues) {
		    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		    int length = titles.length;
		    for (int i = 0; i < length; i++) {
		      TimeSeries series = new TimeSeries(titles[i]);
		      Date[] xV = xValues.get(i);
		      double[] yV = yValues.get(i);
		      int seriesLength = xV.length;
		      for (int k = 0; k < seriesLength; k++) {
		        series.add(xV[k], yV[k]);
		      }
		      dataset.addSeries(series);
		    }
		    return dataset;
		  }

    private void LineChartView(){
        String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
          x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        }
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
            13.9 });
        values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14, 11 });
        values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
        values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
        int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
            PointStyle.TRIANGLE, PointStyle.SQUARE };
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
          ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0.5, 12.5, -10, 40,
            Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Align.RIGHT);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
        renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

        XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
        XYSeries series = dataset.getSeriesAt(0);
        series.addAnnotation("Vacation", 6, 30);

        XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
        r.setAnnotationsColor(Color.GREEN);
        r.setAnnotationsTextSize(15);
        r.setAnnotationsTextAlign(Align.CENTER);

	    GraphicalView chartView;
		chartView = ChartFactory.getLineChartView(getActivity(), buildBarDataset(titles, values), renderer);

//	       View view =  ChartFactory.getLineChartView(mContext, dataset, mXYRenderer);
       
       // Intent intent = ChartFactory.getLineChartView(getActivity().getBaseContext(), dataset, renderer,
       //     "Average temperature");
     	
   		// Adding the Combined Chart to the LinearLayout
	    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.chart_container);	    
	   //layout.removeAllViews();
	    layout.addView(chartView, new LayoutParams(960,
	              LayoutParams.FILL_PARENT));
	    
	    //---------------------------- cari intent
    }

    /*
    public View initLineGraphView() {

    	XYMultipleSeriesDataset dataset = getDataset();

    	  if (dataset == null) {
    	    return null;
    	  }

    	  XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    	  renderer.setAxisTitleTextSize(16);
    	  renderer.setChartTitleTextSize(20);
    	  renderer.setLabelsTextSize(15);
    	  renderer.setLegendTextSize(15);
    	  renderer.setPointSize(5f);
    	  renderer.setMargins(new int[] {20,50,15,20});

    	  XYSeriesRenderer seriesOne = new XYSeriesRenderer();
    	  seriesOne.setColor(Color.MAGENTA);
    	  seriesOne.setPointStyle(PointStyle.CIRCLE);
    	  renderer.addSeriesRenderer(seriesOne);

    	  XYSeriesRenderer seriesTwo = new XYSeriesRenderer();
    	  seriesTwo.setColor(Color.GREEN);
    	  seriesTwo.setPointStyle(PointStyle.TRIANGLE);
    	  renderer.addSeriesRenderer(seriesTwo);

    	  renderer.setChartTitle(("judul"));
    	  renderer.setXTitle("");
    	  renderer.setYTitle("mmHg");

    	  renderer.setShowGridX(true);
    	  renderer.setYLabelsAlign(Paint.Align.RIGHT);
    	  renderer.setYAxisMax(getYDataMax(dataset) + 10);
    	  renderer.setYAxisMin(getYDataMin(dataset) - 10);

    	  return ChartFactory.getTimeChartIntent(ctxt, getDataset(), renderer,
    	                                         "MM/dd/yyyy",
    				   ctxt.getString(R.string.app_name));

    }    
 */   
}
