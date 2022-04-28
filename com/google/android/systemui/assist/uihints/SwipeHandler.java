package com.google.android.systemui.assist.uihints;

import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import java.util.Objects;

public final class SwipeHandler implements NgaMessageHandler.SwipeListener {
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());

    public static void injectMotionEvent(int i, int i2, long j, float f, float f2, float f3) {
        int i3;
        int i4 = i;
        int[] deviceIds = InputDevice.getDeviceIds();
        int length = deviceIds.length;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                i3 = 0;
                break;
            }
            int i6 = deviceIds[i5];
            if (InputDevice.getDevice(i6).supportsSource(i4)) {
                i3 = i6;
                break;
            }
            i5++;
        }
        MotionEvent obtain = MotionEvent.obtain(j, j, i2, f, f2, f3, 1.0f, 0, 1.0f, 1.0f, i3, 0);
        obtain.setSource(i4);
        InputManager.getInstance().injectInputEvent(obtain, 0);
    }

    public final void onSwipe(Bundle bundle) {
        Bundle bundle2 = bundle;
        final float f = bundle2.getFloat("start_x", 0.0f);
        final float f2 = bundle2.getFloat("start_y", 0.0f);
        final float f3 = bundle2.getFloat("end_x", 0.0f);
        final float f4 = bundle2.getFloat("end_y", 0.0f);
        int i = bundle2.getInt("duration_ms", 1000);
        int i2 = bundle2.getInt("num_motion_events", (i * 60) / 1000);
        if (i2 < 1 || i2 > 600) {
            Log.e("SwipeHandler", "Invalid number of motion events requested");
        } else if (i < 0 || i > 10000) {
            Log.e("SwipeHandler", "Invalid swipe duration requested");
        } else {
            long uptimeMillis = SystemClock.uptimeMillis();
            injectMotionEvent(4098, 0, uptimeMillis, f, f2, 1.0f);
            final long j = uptimeMillis + ((long) i);
            int i3 = i / i2;
            final long j2 = uptimeMillis;
            final int i4 = i;
            final int i5 = i3;
            this.mUiHandler.postDelayed(new Runnable() {
                public final /* synthetic */ int val$inputSource = 4098;

                public final void run() {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    if (uptimeMillis < j) {
                        float f = ((float) (uptimeMillis - j2)) / ((float) i4);
                        SwipeHandler swipeHandler = SwipeHandler.this;
                        int i = this.val$inputSource;
                        float f2 = f;
                        float m = MotionController$$ExternalSyntheticOutline0.m7m(f3, f2, f, f2);
                        float f3 = f2;
                        Objects.requireNonNull(swipeHandler);
                        SwipeHandler.injectMotionEvent(i, 2, uptimeMillis, m, f3 + ((f4 - f3) * f), 1.0f);
                        SwipeHandler.this.mUiHandler.postDelayed(this, (long) i5);
                        return;
                    }
                    SwipeHandler swipeHandler2 = SwipeHandler.this;
                    int i2 = this.val$inputSource;
                    float f4 = f3;
                    float f5 = f4;
                    Objects.requireNonNull(swipeHandler2);
                    SwipeHandler.injectMotionEvent(i2, 1, uptimeMillis, f4, f5, 0.0f);
                }
            }, (long) i3);
        }
    }
}
