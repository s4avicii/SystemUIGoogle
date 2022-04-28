package androidx.lifecycle;

import android.os.Handler;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ReportFragment;
import java.util.Objects;

public final class ProcessLifecycleOwner implements LifecycleOwner {
    public static final long TIMEOUT_MS = 700;
    public static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
    public C02471 mDelayedPauseRunnable = new Runnable() {
        public final void run() {
            ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
            Objects.requireNonNull(processLifecycleOwner);
            if (processLifecycleOwner.mResumedCounter == 0) {
                processLifecycleOwner.mPauseSent = true;
                processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
            }
            ProcessLifecycleOwner processLifecycleOwner2 = ProcessLifecycleOwner.this;
            Objects.requireNonNull(processLifecycleOwner2);
            if (processLifecycleOwner2.mStartedCounter == 0 && processLifecycleOwner2.mPauseSent) {
                processLifecycleOwner2.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                processLifecycleOwner2.mStopSent = true;
            }
        }
    };
    public Handler mHandler;
    public C02482 mInitializationListener = new ReportFragment.ActivityInitializationListener() {
    };
    public boolean mPauseSent = true;
    public final LifecycleRegistry mRegistry = new LifecycleRegistry(this, true);
    public int mResumedCounter = 0;
    public int mStartedCounter = 0;
    public boolean mStopSent = true;

    public final void activityResumed() {
        int i = this.mResumedCounter + 1;
        this.mResumedCounter = i;
        if (i != 1) {
            return;
        }
        if (this.mPauseSent) {
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
            this.mPauseSent = false;
            return;
        }
        this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
    }

    public final Lifecycle getLifecycle() {
        return this.mRegistry;
    }
}
