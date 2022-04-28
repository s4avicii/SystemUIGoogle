package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.channels.Channel;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1172}, mo21076m = "sendHunStateChanges")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyManagerKt$sendHunStateChanges$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public int label;
    public /* synthetic */ Object result;

    public NotificationVoiceReplyManagerKt$sendHunStateChanges$1(Continuation<? super NotificationVoiceReplyManagerKt$sendHunStateChanges$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        NotificationVoiceReplyManagerKt.access$sendHunStateChanges((HeadsUpManager) null, (Channel) null, this);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }
}
