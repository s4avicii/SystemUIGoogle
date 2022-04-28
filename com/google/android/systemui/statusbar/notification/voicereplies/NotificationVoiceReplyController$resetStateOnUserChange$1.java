package com.google.android.systemui.statusbar.notification.voicereplies;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1172}, mo21076m = "resetStateOnUserChange")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyController$resetStateOnUserChange$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyController$resetStateOnUserChange$1(NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super NotificationVoiceReplyController$resetStateOnUserChange$1> continuation) {
        super(continuation);
        this.this$0 = notificationVoiceReplyController;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        NotificationVoiceReplyController.access$resetStateOnUserChange(this.this$0, (NotificationVoiceReplyController.Connection) null, this);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }
}
