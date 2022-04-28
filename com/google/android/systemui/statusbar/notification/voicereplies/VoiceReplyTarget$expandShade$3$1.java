package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyManager.kt */
final class VoiceReplyTarget$expandShade$3$1 extends Lambda implements Function1<Throwable, Unit> {
    public final /* synthetic */ VoiceReplyTarget$expandShade$3$callback$1 $callback;
    public final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VoiceReplyTarget$expandShade$3$1(VoiceReplyTarget voiceReplyTarget, VoiceReplyTarget$expandShade$3$callback$1 voiceReplyTarget$expandShade$3$callback$1) {
        super(1);
        this.this$0 = voiceReplyTarget;
        this.$callback = voiceReplyTarget$expandShade$3$callback$1;
    }

    public final Object invoke(Object obj) {
        Throwable th = (Throwable) obj;
        this.this$0.statusBarStateController.removeCallback(this.$callback);
        return Unit.INSTANCE;
    }
}
