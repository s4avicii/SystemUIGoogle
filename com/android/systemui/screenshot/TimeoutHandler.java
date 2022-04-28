package com.android.systemui.screenshot;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityManager;

public final class TimeoutHandler extends Handler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public int mDefaultTimeout = 6000;
    public Runnable mOnTimeout;

    public final void resetTimeout() {
        removeMessages(2);
        sendMessageDelayed(obtainMessage(2), (long) ((AccessibilityManager) this.mContext.getSystemService("accessibility")).getRecommendedTimeoutMillis(this.mDefaultTimeout, 4));
    }

    public final void handleMessage(Message message) {
        if (message.what == 2) {
            this.mOnTimeout.run();
        }
    }

    public TimeoutHandler(Context context) {
        super(Looper.getMainLooper());
        this.mContext = context;
        this.mOnTimeout = TimeoutHandler$$ExternalSyntheticLambda0.INSTANCE;
    }
}
