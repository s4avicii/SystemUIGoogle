package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;

/* compiled from: NotifPipelineChoreographer.kt */
public final class NotifPipelineChoreographerImpl$frameCallback$1 implements Choreographer.FrameCallback {
    public final /* synthetic */ NotifPipelineChoreographerImpl this$0;

    public NotifPipelineChoreographerImpl$frameCallback$1(NotifPipelineChoreographerImpl notifPipelineChoreographerImpl) {
        this.this$0 = notifPipelineChoreographerImpl;
    }

    public final void doFrame(long j) {
        NotifPipelineChoreographerImpl notifPipelineChoreographerImpl = this.this$0;
        if (notifPipelineChoreographerImpl.isScheduled) {
            notifPipelineChoreographerImpl.isScheduled = false;
            Runnable runnable = notifPipelineChoreographerImpl.timeoutSubscription;
            if (runnable != null) {
                runnable.run();
            }
            for (Runnable run : this.this$0.listeners) {
                run.run();
            }
        }
    }
}
