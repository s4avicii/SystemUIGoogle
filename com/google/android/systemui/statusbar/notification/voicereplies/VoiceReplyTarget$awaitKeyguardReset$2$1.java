package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyManager.kt */
final class VoiceReplyTarget$awaitKeyguardReset$2$1 extends Lambda implements Function1<Throwable, Unit> {
    public final /* synthetic */ VoiceReplyTarget$awaitKeyguardReset$2$callback$1 $callback;
    public final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VoiceReplyTarget$awaitKeyguardReset$2$1(VoiceReplyTarget voiceReplyTarget, VoiceReplyTarget$awaitKeyguardReset$2$callback$1 voiceReplyTarget$awaitKeyguardReset$2$callback$1) {
        super(1);
        this.this$0 = voiceReplyTarget;
        this.$callback = voiceReplyTarget$awaitKeyguardReset$2$callback$1;
    }

    public final Object invoke(Object obj) {
        Throwable th = (Throwable) obj;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.this$0.statusBarKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        KeyguardBouncer keyguardBouncer = statusBarKeyguardViewManager.mBouncer;
        VoiceReplyTarget$awaitKeyguardReset$2$callback$1 voiceReplyTarget$awaitKeyguardReset$2$callback$1 = this.$callback;
        Objects.requireNonNull(keyguardBouncer);
        keyguardBouncer.mResetCallbacks.remove(voiceReplyTarget$awaitKeyguardReset$2$callback$1);
        return Unit.INSTANCE;
    }
}
