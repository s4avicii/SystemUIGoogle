package com.google.android.systemui.assist.uihints;

import android.os.Handler;
import android.os.Looper;
import com.android.systemui.assist.AssistManager;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.Lazy;
import java.util.concurrent.TimeUnit;

public final class TimeoutManager implements NgaMessageHandler.KeepAliveListener {
    public static final long SESSION_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10);
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final TimeoutManager$$ExternalSyntheticLambda0 mOnTimeout;
    public TimeoutCallback mTimeoutCallback;

    public interface TimeoutCallback {
    }

    public final void onKeepAlive() {
        this.mHandler.removeCallbacks(this.mOnTimeout);
        this.mHandler.postDelayed(this.mOnTimeout, SESSION_TIMEOUT_MS);
    }

    public TimeoutManager(Lazy<AssistManager> lazy) {
        this.mOnTimeout = new TimeoutManager$$ExternalSyntheticLambda0(this, lazy);
    }
}
