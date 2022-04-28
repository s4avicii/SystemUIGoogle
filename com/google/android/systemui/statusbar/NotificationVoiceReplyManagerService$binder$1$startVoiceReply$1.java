package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.SharedFlowImpl;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {92}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    public final /* synthetic */ int $sessionToken;
    public final /* synthetic */ String $userMessageContent;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$0;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, int i, String str, Continuation<? super NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1> continuation) {
        super(1, continuation);
        this.this$0 = notificationVoiceReplyManagerService;
        this.this$1 = notificationVoiceReplyManagerService$binder$1;
        this.$sessionToken = i;
        this.$userMessageContent = str;
    }

    public final Object invoke(Object obj) {
        return new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1(this.this$0, this.this$1, this.$sessionToken, this.$userMessageContent, (Continuation) obj).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
            Objects.requireNonNull(this.this$1);
            notificationVoiceReplyLogger.logStartVoiceReply(NotificationVoiceReplyManagerService$binder$1.getUserId(), this.$sessionToken, this.$userMessageContent);
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$1;
            Objects.requireNonNull(notificationVoiceReplyManagerService$binder$1);
            SharedFlowImpl sharedFlowImpl = notificationVoiceReplyManagerService$binder$1.startVoiceReplyFlow;
            Objects.requireNonNull(this.this$1);
            StartVoiceReplyData startVoiceReplyData = new StartVoiceReplyData(NotificationVoiceReplyManagerService$binder$1.getUserId(), this.$sessionToken, this.$userMessageContent);
            this.label = 1;
            if (sharedFlowImpl.emit(startVoiceReplyData, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
