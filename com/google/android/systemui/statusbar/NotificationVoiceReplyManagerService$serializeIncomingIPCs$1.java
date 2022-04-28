package com.google.android.systemui.statusbar;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {186, 187}, mo21076m = "serializeIncomingIPCs")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerService$serializeIncomingIPCs$1 extends ContinuationImpl {
    public Object L$0;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerService$serializeIncomingIPCs$1(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, Continuation<? super NotificationVoiceReplyManagerService$serializeIncomingIPCs$1> continuation) {
        super(continuation);
        this.this$0 = notificationVoiceReplyManagerService;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return NotificationVoiceReplyManagerService.access$serializeIncomingIPCs(this.this$0, this);
    }
}
