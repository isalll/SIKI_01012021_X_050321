package com.siki.android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;
import com.siki.library.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChartHSActivity_g2 extends Fragment {

    private Typeface tf;
    private PieChart mChart;
    private TextView tv_title,uraian;
    JSONArray datagrafik = null;
    private static String url = "";
    private static String negara,judulpie;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViewsInLayout();

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        View v = inflater.inflate(R.layout.activity_chart_fragment_mpchart, container, false);


        //boolean isImpor = Globals.isLoadingDataEkspor;
        //boolean isEkspor = Globals.isLoadingDataImpor;
        if (Globals.isLoadingDataImpor==true && Globals.isLoadingDataEkspor==false ){
            //LoadTabel(v,"http://iris.kemenperin.go.id/android/getpiechartimpor.php","id",Globals.kd_hs);
            url="http://iris.kemenperin.go.id/android/getpiechartimpornew.php?kd_hs="+Globals.kd_hs;
            judulpie="Negara Pengimpor bulan "+ Globals.bulanmax + " tahun " + Globals.tahunmax;
        }
        if (Globals.isLoadingDataEkspor==true && Globals.isLoadingDataImpor==false){
            //LoadTabel(v,"http://iris.kemenperin.go.id/android/getpiechartekspor.php","id",Globals.kd_hs);
            url="http://iris.kemenperin.go.id/android/getpiechartekspornew.php?kd_hs="+Globals.kd_hs;
            judulpie="Negara Tujuan Ekspor bulan "+ Globals.bulanmax + " tahun " + Globals.tahunmax;

        }

        Log.e("CHART", "[donnitest]  : status Globals.isLoadingDataImpor="+Globals.isLoadingDataImpor+" Globals.isLoadingDataEkspor="+Globals.isLoadingDataEkspor);

        mChart = (PieChart) v.findViewById(R.id.chart_container);
        mChart.getDescription().setEnabled(false);

        mChart.setCenterTextTypeface(tf);
        //mChart.setCenterText(getActivity().generateCenterText());
        mChart.setCenterTextSize(10f);
        mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        mChart.setData(generatePieData());

        return v;    }

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

    protected BarData generateBarData(int dataSets, float range, int count) {

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

//            entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

            for(int j = 0; j < count; j++) {
                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
            }

            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    protected ScatterData generateScatterData(int dataSets, float range, int count) {

        ArrayList<IScatterDataSet> sets = new ArrayList<IScatterDataSet>();

        ScatterChart.ScatterShape[] shapes = ScatterChart.ScatterShape.getAllDefaultShapes();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<Entry> entries = new ArrayList<Entry>();

            for(int j = 0; j < count; j++) {
                entries.add(new Entry(j, (float) (Math.random() * range) + range / 4));
            }

            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9f);
            sets.add(ds);
        }

        ScatterData d = new ScatterData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return
     */
    protected PieData generatePieData() {


        JSONParser jParser = new JSONParser();
       // JSONObject json = jParser.AmbilJson("http://iris.kemenperin.go.id/android/getpiechartekspornew.php?kd_hs="+Globals.kd_hs);
        JSONObject json = jParser.AmbilJson(url);

        //int count = 4;
        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

        try{
            datagrafik = json.getJSONArray("datapie");
            //JSONObject ad = datagrafik.getJSONObject(0);

            int count = datagrafik.length();
            for(int i=0;i<=count;i++)
            {
                JSONObject ad = datagrafik.getJSONObject(i);
                negara=ad.getString("negara");
                Float nilaitotal= Float.parseFloat(ad.getString("nilaitotal"));

                entries1.add(new PieEntry((nilaitotal), negara));

            }
            }catch (JSONException e){
            e.printStackTrace();
        }

        PieDataSet ds1 = new PieDataSet(entries1, judulpie);
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(tf);


        return d;
    }

    protected LineData generateLineData() {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "sine.txt"), "Sine function");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "cosine.txt"), "Cosine function");

        ds1.setLineWidth(2f);
        ds2.setLineWidth(2f);

        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);

        LineData d = new LineData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    protected LineData getComplexity() {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "n.txt"), "O(n)");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "nlogn.txt"), "O(nlogn)");
        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "square.txt"), "O(n\u00B2)");
        LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "three.txt"), "O(n\u00B3)");

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3]);

        ds1.setLineWidth(2.5f);
        ds1.setCircleRadius(3f);
        ds2.setLineWidth(2.5f);
        ds2.setCircleRadius(3f);
        ds3.setLineWidth(2.5f);
        ds3.setCircleRadius(3f);
        ds4.setLineWidth(2.5f);
        ds4.setCircleRadius(3f);


        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);
        sets.add(ds3);
        sets.add(ds4);

        LineData d = new LineData(sets);
        d.setValueTypeface(tf);
        return d;
    }

    private String[] mLabels = new String[] { "Company A", "Company B", "Company C", "Company D", "Company E", "Company F" };
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

    private String getLabel(int i) {
        return mLabels[i];
    }


}
