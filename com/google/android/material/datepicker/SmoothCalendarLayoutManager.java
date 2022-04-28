package com.google.android.material.datepicker;

import android.util.DisplayMetrics;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class SmoothCalendarLayoutManager extends LinearLayoutManager {
    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        C20161 r0 = new LinearSmoothScroller(recyclerView.getContext()) {
            public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / ((float) displayMetrics.densityDpi);
            }
        };
        r0.mTargetPosition = i;
        startSmoothScroll(r0);
    }

    public SmoothCalendarLayoutManager(int i) {
        super(i);
    }
}
