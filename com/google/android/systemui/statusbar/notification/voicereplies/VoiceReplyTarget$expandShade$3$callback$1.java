package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$expandShade$3$callback$1 implements StatusBarStateController.StateListener {

    /* renamed from: $k */
    public final /* synthetic */ CancellableContinuation<Boolean> f151$k;
    public final /* synthetic */ AtomicBoolean $latch;
    public final /* synthetic */ VoiceReplyTarget this$0;

    public VoiceReplyTarget$expandShade$3$callback$1(AtomicBoolean atomicBoolean, VoiceReplyTarget voiceReplyTarget, CancellableContinuationImpl cancellableContinuationImpl) {
        this.$latch = atomicBoolean;
        this.this$0 = voiceReplyTarget;
        this.f151$k = cancellableContinuationImpl;
    }

    public final void onExpandedChanged(boolean z) {
        AtomicBoolean atomicBoolean = this.$latch;
        VoiceReplyTarget voiceReplyTarget = this.this$0;
        CancellableContinuation<Boolean> cancellableContinuation = this.f151$k;
        if (atomicBoolean.getAndSet(false)) {
            voiceReplyTarget.statusBarStateController.removeCallback(this);
            cancellableContinuation.resumeWith(Boolean.valueOf(z));
        }
    }
}
