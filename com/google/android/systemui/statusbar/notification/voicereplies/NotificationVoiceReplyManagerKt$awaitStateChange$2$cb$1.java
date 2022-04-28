package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 implements StatusBarWindowCallback {

    /* renamed from: $k */
    public final /* synthetic */ CancellableContinuation<StatusBarWindowState> f147$k;
    public final /* synthetic */ AtomicBoolean $latch;
    public final /* synthetic */ NotificationShadeWindowController $this_awaitStateChange;

    public NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1(AtomicBoolean atomicBoolean, NotificationShadeWindowController notificationShadeWindowController, CancellableContinuationImpl cancellableContinuationImpl) {
        this.$latch = atomicBoolean;
        this.$this_awaitStateChange = notificationShadeWindowController;
        this.f147$k = cancellableContinuationImpl;
    }

    public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4) {
        AtomicBoolean atomicBoolean = this.$latch;
        NotificationShadeWindowController notificationShadeWindowController = this.$this_awaitStateChange;
        CancellableContinuation<StatusBarWindowState> cancellableContinuation = this.f147$k;
        if (atomicBoolean.getAndSet(false)) {
            notificationShadeWindowController.unregisterCallback(this);
            cancellableContinuation.resumeWith(new StatusBarWindowState(z, z2, z3));
        }
    }
}
