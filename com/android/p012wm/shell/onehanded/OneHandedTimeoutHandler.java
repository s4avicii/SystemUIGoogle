package com.android.p012wm.shell.onehanded;

import com.android.p012wm.shell.common.ShellExecutor;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* renamed from: com.android.wm.shell.onehanded.OneHandedTimeoutHandler */
public final class OneHandedTimeoutHandler {
    public ArrayList mListeners = new ArrayList();
    public final ShellExecutor mMainExecutor;
    public int mTimeout = 8;
    public long mTimeoutMs = TimeUnit.SECONDS.toMillis((long) 8);
    public final SuggestController$$ExternalSyntheticLambda1 mTimeoutRunnable = new SuggestController$$ExternalSyntheticLambda1(this, 9);

    /* renamed from: com.android.wm.shell.onehanded.OneHandedTimeoutHandler$TimeoutListener */
    public interface TimeoutListener {
        void onTimeout();
    }

    public boolean hasScheduledTimeout() {
        return this.mMainExecutor.hasCallback(this.mTimeoutRunnable);
    }

    public final void resetTimer() {
        this.mMainExecutor.removeCallbacks(this.mTimeoutRunnable);
        int i = this.mTimeout;
        if (i != 0 && i != 0) {
            this.mMainExecutor.executeDelayed(this.mTimeoutRunnable, this.mTimeoutMs);
        }
    }

    public OneHandedTimeoutHandler(ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
    }
}
