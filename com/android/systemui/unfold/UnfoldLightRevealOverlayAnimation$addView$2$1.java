package com.android.systemui.unfold;

import android.os.Trace;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.WindowlessWindowManager;
import java.util.concurrent.Executor;

/* compiled from: UnfoldLightRevealOverlayAnimation.kt */
public final class UnfoldLightRevealOverlayAnimation$addView$2$1 implements WindowlessWindowManager.ResizeCompleteCallback {
    public final /* synthetic */ Runnable $callback;
    public final /* synthetic */ UnfoldLightRevealOverlayAnimation this$0;

    public UnfoldLightRevealOverlayAnimation$addView$2$1(UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation, Runnable runnable) {
        this.this$0 = unfoldLightRevealOverlayAnimation;
        this.$callback = runnable;
    }

    public final void finished(final SurfaceControl.Transaction transaction) {
        final long vsyncId = Choreographer.getSfInstance().getVsyncId();
        Executor executor = this.this$0.backgroundExecutor;
        final Runnable runnable = this.$callback;
        executor.execute(new Runnable() {
            public final void run() {
                transaction.setFrameTimelineVsync(vsyncId).apply(true);
                transaction.setFrameTimelineVsync(vsyncId + 1).apply(true);
                Trace.endAsyncSection("UnfoldLightRevealOverlayAnimation#relayout", 0);
                runnable.run();
            }
        });
    }
}
