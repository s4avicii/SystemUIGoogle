package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1;

/* compiled from: NotifPipelineChoreographer.kt */
public final class NotifPipelineChoreographerImpl implements NotifPipelineChoreographer {
    public final DelayableExecutor executor;
    public final NotifPipelineChoreographerImpl$frameCallback$1 frameCallback = new NotifPipelineChoreographerImpl$frameCallback$1(this);
    public boolean isScheduled;
    public final ListenerSet<Runnable> listeners = new ListenerSet<>();
    public Runnable timeoutSubscription;
    public final Choreographer viewChoreographer;

    public final void addOnEvalListener(WMShell$7$$ExternalSyntheticLambda1 wMShell$7$$ExternalSyntheticLambda1) {
        this.listeners.addIfAbsent(wMShell$7$$ExternalSyntheticLambda1);
    }

    public final void schedule() {
        if (!this.isScheduled) {
            this.isScheduled = true;
            this.viewChoreographer.postFrameCallback(this.frameCallback);
            if (this.isScheduled) {
                this.timeoutSubscription = this.executor.executeDelayed(new NotifPipelineChoreographerImpl$schedule$1(this), 100);
            }
        }
    }

    public NotifPipelineChoreographerImpl(Choreographer choreographer, DelayableExecutor delayableExecutor) {
        this.viewChoreographer = choreographer;
        this.executor = delayableExecutor;
    }
}
