package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.keyguard.ScreenLifecycle;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UnfoldLatencyTracker.kt */
public final class UnfoldLatencyTracker implements ScreenLifecycle.Observer {
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final FoldStateListener foldStateListener;
    public Boolean folded;
    public final LatencyTracker latencyTracker;
    public final ScreenLifecycle screenLifecycle;
    public final Executor uiBgExecutor;

    /* compiled from: UnfoldLatencyTracker.kt */
    public final class FoldStateListener extends DeviceStateManager.FoldStateListener {
        public FoldStateListener(final UnfoldLatencyTracker unfoldLatencyTracker, Context context) {
            super(context, new Consumer() {
                public final void accept(Object obj) {
                    UnfoldLatencyTracker unfoldLatencyTracker = unfoldLatencyTracker;
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    Objects.requireNonNull(unfoldLatencyTracker);
                    if (!Intrinsics.areEqual(unfoldLatencyTracker.folded, Boolean.valueOf(booleanValue))) {
                        unfoldLatencyTracker.folded = Boolean.valueOf(booleanValue);
                        if (!booleanValue) {
                            unfoldLatencyTracker.latencyTracker.onActionStart(13);
                        }
                    }
                }
            });
        }
    }

    public final void onScreenTurnedOn() {
        if (Intrinsics.areEqual(this.folded, Boolean.FALSE)) {
            this.latencyTracker.onActionEnd(13);
        }
    }

    public UnfoldLatencyTracker(LatencyTracker latencyTracker2, DeviceStateManager deviceStateManager2, Executor executor, Context context2, ScreenLifecycle screenLifecycle2) {
        this.latencyTracker = latencyTracker2;
        this.deviceStateManager = deviceStateManager2;
        this.uiBgExecutor = executor;
        this.context = context2;
        this.screenLifecycle = screenLifecycle2;
        this.foldStateListener = new FoldStateListener(this, context2);
    }
}
