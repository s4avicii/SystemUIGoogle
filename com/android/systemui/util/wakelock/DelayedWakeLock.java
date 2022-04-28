package com.android.systemui.util.wakelock;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;

public final class DelayedWakeLock implements WakeLock {
    public final Handler mHandler;
    public final WakeLock mInner;

    public static class Builder {
        public final Context mContext;
        public Handler mHandler;
        public String mTag;

        public Builder(Context context) {
            this.mContext = context;
        }
    }

    public final void acquire(String str) {
        this.mInner.acquire(str);
    }

    public final void release(String str) {
        this.mHandler.postDelayed(new BubblesManager$5$$ExternalSyntheticLambda1(this, str, 2), 100);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("[DelayedWakeLock] ");
        m.append(this.mInner);
        return m.toString();
    }

    public DelayedWakeLock(Handler handler, WakeLock wakeLock) {
        this.mHandler = handler;
        this.mInner = wakeLock;
    }

    public final WakeLock$$ExternalSyntheticLambda0 wrap(Runnable runnable) {
        acquire("wrap");
        return new WakeLock$$ExternalSyntheticLambda0(runnable, this, 0);
    }
}
