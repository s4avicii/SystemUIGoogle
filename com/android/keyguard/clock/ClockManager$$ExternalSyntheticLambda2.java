package com.android.keyguard.clock;

import android.os.Looper;
import com.android.keyguard.clock.ClockManager;
import com.android.systemui.plugins.ClockPlugin;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClockManager$$ExternalSyntheticLambda2 implements BiConsumer {
    public final /* synthetic */ ClockManager f$0;

    public /* synthetic */ ClockManager$$ExternalSyntheticLambda2(ClockManager clockManager) {
        this.f$0 = clockManager;
    }

    public final void accept(Object obj, Object obj2) {
        ClockManager clockManager = this.f$0;
        ClockManager.ClockChangedListener clockChangedListener = (ClockManager.ClockChangedListener) obj;
        ClockManager.AvailableClocks availableClocks = (ClockManager.AvailableClocks) obj2;
        Objects.requireNonNull(clockManager);
        availableClocks.reloadCurrentClock();
        ClockPlugin clockPlugin = availableClocks.mCurrentClock;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (clockPlugin instanceof DefaultClockController) {
                clockPlugin = null;
            }
            clockChangedListener.onClockChanged(clockPlugin);
            return;
        }
        clockManager.mMainHandler.post(new ClockManager$$ExternalSyntheticLambda1(clockChangedListener, clockPlugin, 0));
    }
}
