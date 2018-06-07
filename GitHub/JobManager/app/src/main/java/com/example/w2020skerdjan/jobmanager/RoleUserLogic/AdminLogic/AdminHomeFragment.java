package com.example.w2020skerdjan.jobmanager.RoleUserLogic.AdminLogic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.w2020skerdjan.jobmanager.R;
import com.example.w2020skerdjan.jobmanager.Utils.ChartData;
import com.example.w2020skerdjan.jobmanager.Utils.MyMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class AdminHomeFragment extends Fragment {

    private PieChart mChart;
    private BarChart barChart;
    private ScatterChart scatterChart;
    private CardView barChartCard;
    private LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_home, container, false);
        setupPieChart(v);
        setupBarChart(v);
        //setupScatterChart(v);
        setupLineChart(v);
        return v;
    }


    private void setupBarChart(View v){
        // create a new bar chart object
        barChart = new BarChart(getActivity());
        barChart.getDescription().setEnabled(false);
        /* barChart.setOnChartGestureListener(this);*/

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        barChart.setMarker(mv);

        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);


        barChart.setData(ChartData.generateBarData(1, 1000, 6));

       /* Legend l = barChart.getLegend();
        l.setTypeface(tf);*/
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChart.getAxisRight().setEnabled(false);
        XAxis xAxis = barChart
                .getXAxis();
        xAxis.setEnabled(false);

        // programatically add the chart
        FrameLayout parent = v.findViewById(R.id.barChartCard);
        parent.addView(barChart);
    }


    private void setupPieChart(View v){
        mChart = v.findViewById(R.id.pieChart);
        mChart.getDescription().setEnabled(false);
        mChart.setCenterText(ChartData.generateCenterText());
        mChart.setCenterTextSize(10f);


        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(40f);
        mChart.setTransparentCircleRadius(50f);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        mChart.setData(ChartData.generatePieData());
    }


    private void setupScatterChart(View v){
       // scatterChart = v.findViewById(R.id.scatterChart);
        scatterChart.getDescription().setEnabled(false);



        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(scatterChart); // For bounds control
        scatterChart.setMarker(mv);

        scatterChart.setDrawGridBackground(false);
        scatterChart.setData(ChartData.generateScatterData(1, 100, 10));

        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = scatterChart.getAxisLeft();


        YAxis rightAxis = scatterChart.getAxisRight();

        rightAxis.setDrawGridLines(false);
        scatterChart.getAxisRight().setDrawLabels(false);

        Legend l = scatterChart.getLegend();
        l.setWordWrapEnabled(true);

        l.setFormSize(14f);
        l.setTextSize(9f);

        // increase the space between legend & bottom and legend & content
        l.setYOffset(13f);
        scatterChart.setExtraBottomOffset(16f);
    }


    private void setupLineChart(View v){
        lineChart = v.findViewById(R.id.lineChart);

        lineChart.getDescription().setEnabled(false);

        lineChart.setDrawGridBackground(false);

        lineChart.setData(ChartData.generateLineData(getActivity()));
        lineChart.animateX(3000);


        Legend l = lineChart.getLegend();


        YAxis leftAxis = lineChart.getAxisLeft();

        leftAxis.setAxisMaximum(1.2f);
        leftAxis.setAxisMinimum(-1.2f);

        lineChart.getAxisRight().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(false);
    }


}
