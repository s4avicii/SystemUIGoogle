package com.android.systemui.unfold.util;

import android.os.Trace;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ATraceLoggerTransitionProgressListener.kt */
public final class ATraceLoggerTransitionProgressListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final String traceName;

    public final void onTransitionFinished() {
        Trace.endAsyncSection(this.traceName, 0);
    }

    public final void onTransitionProgress(float f) {
        Trace.setCounter(this.traceName, (long) (f * ((float) 100)));
    }

    public final void onTransitionStarted() {
        Trace.beginAsyncSection(this.traceName, 0);
    }

    public ATraceLoggerTransitionProgressListener(String str) {
        this.traceName = Intrinsics.stringPlus(str, "#FoldUnfoldTransitionInProgress");
    }
}
