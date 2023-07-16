package com.example.money_care_android.listener;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;

import com.example.money_care_android.navigation.ReportActivity;
import com.example.money_care_android.navigation.details.PieDetailActivity;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

public class OnPieChartTappingListener implements OnChartGestureListener {

    PieData pieData;
    Context context;

    public OnPieChartTappingListener(PieData pieData, Context context) {
        this.pieData = pieData;
        this.context = context;
    }

    /**
     * Callbacks when a touch-gesture has started on the chart (ACTION_DOWN)
     *
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    /**
     * Callbacks when a touch-gesture has ended on the chart (ACTION_UP, ACTION_CANCEL)
     *
     * @param me
     * @param lastPerformedGesture
     */
    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    /**
     * Callbacks when the chart is longpressed.
     *
     * @param me
     */
    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    /**
     * Callbacks when the chart is double-tapped.
     *
     * @param me
     */
    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    /**
     * Callbacks when the chart is single-tapped.
     *
     * @param me
     */
    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Intent intent = new Intent(context, PieDetailActivity.class);
    }

    /**
     * Callbacks then a fling gesture is made on the chart.
     *
     * @param me1
     * @param me2
     * @param velocityX
     * @param velocityY
     */
    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    /**
     * Callbacks when the chart is scaled / zoomed via pinch zoom gesture.
     *
     * @param me
     * @param scaleX scalefactor on the x-axis
     * @param scaleY scalefactor on the y-axis
     */
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    /**
     * Callbacks when the chart is moved / translated via drag gesture.
     *
     * @param me
     * @param dX translation distance on the x-axis
     * @param dY translation distance on the y-axis
     */
    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
}
