package com.airbnb.lottie;

import android.view.View;
import androidx.core.view.ViewPropertyAnimatorListener;

/* renamed from: com.airbnb.lottie.L */
public class C0438L implements ViewPropertyAnimatorListener {
    public static int depthPastMaxDepth;

    public void onAnimationCancel(View view) {
    }

    public void onAnimationStart() {
    }

    public static void endSection() {
        int i = depthPastMaxDepth;
        if (i > 0) {
            depthPastMaxDepth = i - 1;
        }
    }
}
