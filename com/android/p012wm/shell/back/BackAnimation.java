package com.android.p012wm.shell.back;

import android.view.MotionEvent;

/* renamed from: com.android.wm.shell.back.BackAnimation */
public interface BackAnimation {
    IBackAnimation createExternalInterface() {
        return null;
    }

    void onBackMotion(MotionEvent motionEvent, int i);

    void setSwipeThresholds(float f, float f2);

    void setTriggerBack(boolean z);
}
