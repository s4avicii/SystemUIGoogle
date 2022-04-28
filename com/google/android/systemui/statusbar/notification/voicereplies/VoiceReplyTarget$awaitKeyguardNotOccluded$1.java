package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {847, 853}, mo21076m = "awaitKeyguardNotOccluded")
/* compiled from: NotificationVoiceReplyManager.kt */
final class VoiceReplyTarget$awaitKeyguardNotOccluded$1 extends ContinuationImpl {
    public Object L$0;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ VoiceReplyTarget this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public VoiceReplyTarget$awaitKeyguardNotOccluded$1(VoiceReplyTarget voiceReplyTarget, Continuation<? super VoiceReplyTarget$awaitKeyguardNotOccluded$1> continuation) {
        super(continuation);
        this.this$0 = voiceReplyTarget;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return VoiceReplyTarget.access$awaitKeyguardNotOccluded(this.this$0, this);
    }
}
