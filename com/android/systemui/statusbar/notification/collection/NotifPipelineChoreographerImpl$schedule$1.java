package com.android.systemui.statusbar.notification.collection;

import java.util.Iterator;
import java.util.Objects;

/* compiled from: NotifPipelineChoreographer.kt */
public /* synthetic */ class NotifPipelineChoreographerImpl$schedule$1 implements Runnable {
    public final /* synthetic */ NotifPipelineChoreographerImpl $tmp0;

    public NotifPipelineChoreographerImpl$schedule$1(NotifPipelineChoreographerImpl notifPipelineChoreographerImpl) {
        this.$tmp0 = notifPipelineChoreographerImpl;
    }

    public final void run() {
        NotifPipelineChoreographerImpl notifPipelineChoreographerImpl = this.$tmp0;
        Objects.requireNonNull(notifPipelineChoreographerImpl);
        if (notifPipelineChoreographerImpl.isScheduled) {
            notifPipelineChoreographerImpl.isScheduled = false;
            notifPipelineChoreographerImpl.viewChoreographer.removeFrameCallback(notifPipelineChoreographerImpl.frameCallback);
            Iterator<Runnable> it = notifPipelineChoreographerImpl.listeners.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }
    }
}
