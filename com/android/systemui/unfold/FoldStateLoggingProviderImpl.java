package com.android.systemui.unfold;

import com.android.systemui.unfold.FoldStateLoggingProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: FoldStateLoggingProviderImpl.kt */
public final class FoldStateLoggingProviderImpl implements FoldStateLoggingProvider, FoldStateProvider.FoldUpdatesListener {
    public Long actionStartMillis;
    public final SystemClock clock;
    public final FoldStateProvider foldStateProvider;
    public Integer lastState;
    public final ArrayList outputListeners = new ArrayList();

    public final void onHingeAngleUpdate(float f) {
    }

    public final void addCallback(Object obj) {
        this.outputListeners.add((FoldStateLoggingProvider.FoldStateLoggingListener) obj);
    }

    public final void dispatchState(int i) {
        long elapsedRealtime = this.clock.elapsedRealtime();
        Integer num = this.lastState;
        Long l = this.actionStartMillis;
        if (!(num == null || num.intValue() == i || l == null)) {
            FoldStateChange foldStateChange = new FoldStateChange(num.intValue(), i, elapsedRealtime - l.longValue());
            Iterator it = this.outputListeners.iterator();
            while (it.hasNext()) {
                ((FoldStateLoggingProvider.FoldStateLoggingListener) it.next()).onFoldUpdate(foldStateChange);
            }
        }
        this.actionStartMillis = null;
        this.lastState = Integer.valueOf(i);
    }

    public final void init() {
        this.foldStateProvider.addCallback(this);
        this.foldStateProvider.start();
    }

    public final void onFoldUpdate(int i) {
        long elapsedRealtime = this.clock.elapsedRealtime();
        if (i == 0) {
            this.lastState = 2;
            this.actionStartMillis = Long.valueOf(elapsedRealtime);
        } else if (i == 1) {
            this.actionStartMillis = Long.valueOf(elapsedRealtime);
        } else if (i == 3) {
            dispatchState(3);
        } else if (i == 4) {
            dispatchState(1);
        } else if (i == 5) {
            dispatchState(2);
        }
    }

    public final void removeCallback(Object obj) {
        this.outputListeners.remove((FoldStateLoggingProvider.FoldStateLoggingListener) obj);
    }

    public FoldStateLoggingProviderImpl(FoldStateProvider foldStateProvider2, SystemClockImpl systemClockImpl) {
        this.foldStateProvider = foldStateProvider2;
        this.clock = systemClockImpl;
    }
}
