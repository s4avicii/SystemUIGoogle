package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$awaitKeyguardReset$2$callback$1 implements KeyguardBouncer.KeyguardResetCallback {

    /* renamed from: $k */
    public final /* synthetic */ CancellableContinuation<Unit> f149$k;
    public final /* synthetic */ AtomicBoolean $latch;
    public final /* synthetic */ VoiceReplyTarget this$0;

    public VoiceReplyTarget$awaitKeyguardReset$2$callback$1(AtomicBoolean atomicBoolean, VoiceReplyTarget voiceReplyTarget, CancellableContinuationImpl cancellableContinuationImpl) {
        this.$latch = atomicBoolean;
        this.this$0 = voiceReplyTarget;
        this.f149$k = cancellableContinuationImpl;
    }

    public final void onKeyguardReset() {
        AtomicBoolean atomicBoolean = this.$latch;
        VoiceReplyTarget voiceReplyTarget = this.this$0;
        CancellableContinuation<Unit> cancellableContinuation = this.f149$k;
        if (atomicBoolean.getAndSet(false)) {
            StatusBarKeyguardViewManager statusBarKeyguardViewManager = voiceReplyTarget.statusBarKeyguardViewManager;
            Objects.requireNonNull(statusBarKeyguardViewManager);
            KeyguardBouncer keyguardBouncer = statusBarKeyguardViewManager.mBouncer;
            Objects.requireNonNull(keyguardBouncer);
            keyguardBouncer.mResetCallbacks.remove(this);
            cancellableContinuation.resumeWith(Unit.INSTANCE);
        }
    }
}
