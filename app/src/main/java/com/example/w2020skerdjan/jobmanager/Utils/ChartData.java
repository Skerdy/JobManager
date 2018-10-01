package com.example.w2020skerdjan.jobmanager.Utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.example.w2020skerdjan.jobmanager.Activities.MainActivity;
import com.example.w2020skerdjan.jobmanager.R;
import com.github.mikephil.charting.charts.ScatterChart;
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

import java.util.ArrayList;

public class ChartData {

    public static SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Users\n  2018");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    public static PieData generatePieData() {



        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();

      /*  for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }*/
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 60), "Users"));
       // entries1.get(0).setIcon(getResources().getDrawable(R.drawable.ic_icon_users));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 20), "Managers"));
      //  entries1.get(1).setIcon(getResources().getDrawable(R.drawable.ic_icon_manager));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 5), "Administrators"));
       // entries1.get(2).setIcon(getResources().getDrawable(R.drawable.ic_icon_profile));


        PieDataSet ds1 = new PieDataSet(entries1, "Registered Users Stats");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);

        return d;
    }


    public static BarData generateBarData(int dataSets, float range, int count) {

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
        return d;
    }

    public static String[] mLabels = new String[] {"Jobs" };

    public static String getLabel(int i) {
        return mLabels[i];
    }


    public static ScatterData generateScatterData(int dataSets, float range, int count) {

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

        return d;
    }

    public static LineData generateLineData(Context ctx) {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(ctx.getAssets(), "sine.txt"), "Employement Rate");
       // LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(ctx.getAssets(), "cosine.txt"), "Cosine function");

        ds1.setLineWidth(2f);
      //  ds2.setLineWidth(2f);

        ds1.setDrawCircles(false);
      //  ds2.setDrawCircles(false);

        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
      //  ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);

        // load DataSets from textfiles in assets folders
        sets.add(ds1);
      //  sets.add(ds2);

        LineData d = new LineData(sets);

        return d;
    }
}
