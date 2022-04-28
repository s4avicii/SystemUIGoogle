package com.google.android.systemui.statusbar.notification.voicereplies;

import android.view.View;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$awaitFocusState$2$listener$1 implements View.OnFocusChangeListener {
    public final /* synthetic */ boolean $expected;

    /* renamed from: $k */
    public final /* synthetic */ CancellableContinuation<Unit> f148$k;
    public final /* synthetic */ AtomicBoolean $latch;
    public final /* synthetic */ RemoteInputView $this_awaitFocusState;

    public VoiceReplyTarget$awaitFocusState$2$listener$1(boolean z, AtomicBoolean atomicBoolean, RemoteInputView remoteInputView, CancellableContinuationImpl cancellableContinuationImpl) {
        this.$expected = z;
        this.$latch = atomicBoolean;
        this.$this_awaitFocusState = remoteInputView;
        this.f148$k = cancellableContinuationImpl;
    }

    public final void onFocusChange(View view, boolean z) {
        if (z == this.$expected) {
            AtomicBoolean atomicBoolean = this.$latch;
            RemoteInputView remoteInputView = this.$this_awaitFocusState;
            CancellableContinuation<Unit> cancellableContinuation = this.f148$k;
            if (atomicBoolean.getAndSet(false)) {
                Objects.requireNonNull(remoteInputView);
                remoteInputView.mEditTextFocusChangeListeners.remove(this);
                cancellableContinuation.resumeWith(Unit.INSTANCE);
            }
        }
    }
}
