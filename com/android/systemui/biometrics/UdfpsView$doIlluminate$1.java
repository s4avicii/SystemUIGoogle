package com.android.systemui.biometrics;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

/* compiled from: UdfpsView.kt */
public final class UdfpsView$doIlluminate$1 implements Runnable {
    public final /* synthetic */ Runnable $onIlluminatedRunnable;
    public final /* synthetic */ UdfpsView this$0;

    public UdfpsView$doIlluminate$1(UdfpsView udfpsView, Runnable runnable) {
        this.this$0 = udfpsView;
        this.$onIlluminatedRunnable = runnable;
    }

    public final void run() {
        UdfpsView udfpsView = this.this$0;
        UdfpsSurfaceView udfpsSurfaceView = udfpsView.ghbmView;
        if (udfpsSurfaceView != null) {
            RectF rectF = udfpsView.sensorRect;
            if (!udfpsSurfaceView.mHasValidSurface) {
                Log.e("UdfpsSurfaceView", "drawIlluminationDot | the surface is destroyed or was never created.");
            } else {
                Canvas canvas = null;
                try {
                    canvas = udfpsSurfaceView.mHolder.lockCanvas();
                    canvas.drawOval(rectF, udfpsSurfaceView.mSensorPaint);
                    udfpsSurfaceView.mHolder.unlockCanvasAndPost(canvas);
                } catch (Throwable th) {
                    if (canvas != null) {
                        udfpsSurfaceView.mHolder.unlockCanvasAndPost(canvas);
                    }
                    throw th;
                }
            }
        }
        Runnable runnable = this.$onIlluminatedRunnable;
        if (runnable != null) {
            UdfpsView udfpsView2 = this.this$0;
            udfpsView2.postDelayed(runnable, udfpsView2.onIlluminatedDelayMs);
            return;
        }
        Log.w("UdfpsView", "doIlluminate | onIlluminatedRunnable is null");
    }
}
