package com.example.money_care_android.models;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChartSdize {

    public static BarData lastThisMonth(int lastMonth, int thisMonth) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        //two colors for two bar entry, the first one is darker than the second one
        int[] colorClassArray = new int[]{
                Color.parseColor("#ff5e5e"),
                Color.parseColor("#ff9d9d")
        };
        barEntries.add(new BarEntry(1, lastMonth));
        barEntries.add(new BarEntry(2, thisMonth));
        BarDataSet barDataSet = new BarDataSet(barEntries, "Tổng chi");
        barDataSet.setColors(colorClassArray);
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);

        return barData;
    }

    //Generate PieData base on Category
    //transactionOverall.getCategoryLists() is a list of CategoryList contains Category and list of Payment
    public static PieData monthDetail(TransactionOverall transactionOverall, boolean type, PieChart pieChart) {
        int[] colorClassArray = new int[]{
                Color.parseColor("#f44336"),
                Color.parseColor("#e91e63"),
                Color.parseColor("#9c27b0"),
                Color.parseColor("#673ab7"),
                Color.parseColor("#3f51b5"),
                Color.parseColor("#2196f3"),
                Color.parseColor("#03a9f4"),
                Color.parseColor("#00bcd4"),
                Color.parseColor("#009688"),
                Color.parseColor("#4caf50"),
                Color.parseColor("#8bc34a"),
                Color.parseColor("#cddc39"),
                Color.parseColor("#ffeb3b"),
                Color.parseColor("#ffc107"),
                Color.parseColor("#ff9800"),
                Color.parseColor("#ff5722"),
                Color.parseColor("#795548"),
                Color.parseColor("#9e9e9e"),
                Color.parseColor("#607d8b")
        };
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (CategoryList categoryList : transactionOverall.getCategoryLists()) {
            if (categoryList.isType() == type) {
                pieEntries.add(new PieEntry(categoryList.getTotalPayment(), categoryList.getName(), getUrlDrawable(categoryList.getImage())));
            }
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colorClassArray);
        pieDataSet.setFormSize(4);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueLinePart1OffsetPercentage(100f); /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
        pieDataSet.setValueLinePart1Length(0.6f); /** When valuePosition is OutsideSlice, indicates length of first half of the line */
        pieDataSet.setValueLinePart2Length(0.6f); /** When valuePosition is OutsideSlice, indicates length of second half of the line */
        pieDataSet.setSliceSpace(0f);
        pieChart.setExtraOffsets(0.f, 5.f, 0.f, 5.f);

        PieData pieData = new PieData();
        pieData.addDataSet(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(5f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        //Click event
        pieChart.setTouchEnabled(true);
        pieChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setTextSize(5f);



        pieChart.invalidate();
        return pieData;
    }

    @SuppressWarnings("deprecation")
    public static Drawable getUrlDrawable(String url) {
        try {/* www .  ja va 2 s. c  o m*/
            URL aryURI = new URL(url);
            URLConnection conn = aryURI.openConnection();
            InputStream is = conn.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(is);
            return new BitmapDrawable(bmp);
        } catch (Exception e) {
            return null;
        }
    }

    public static BarData monthBarData(TransactionDetail transactionDetail, BarChart barChart) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (DailyMoney daily : transactionDetail.getDateInMonth()) {
            barEntries.add(new BarEntry(daily.getDateString(), daily.getTotalIncomes() - daily.getTotalExpenses()));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Tổng chi tiêu");
        barDataSet.setColors(Color.parseColor("#ff5e5e"));
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setBarWidth(0.5f);
        YAxis left = barChart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line
        barChart.getAxisRight().setEnabled(false); // no right axis
        barChart.setFitBars(true);
        barChart.setExtraOffsets(0.f, 5.f, 0.f, 5.f);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();

        return barData;
    }
}
