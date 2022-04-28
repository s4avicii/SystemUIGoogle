package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$1$sessionJob$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {600}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$1$sessionJob$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2424x5b674fe7 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function2<Session, Continuation<? super Unit>, Object> $block;
    public final /* synthetic */ C2423x17fc92b0 $session;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2424x5b674fe7(NotificationVoiceReplyController notificationVoiceReplyController, Function2<? super Session, ? super Continuation<? super Unit>, ? extends Object> function2, C2423x17fc92b0 notificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1, Continuation<? super C2424x5b674fe7> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
        this.$block = function2;
        this.$session = notificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2424x5b674fe7(this.this$0, this.$block, this.$session, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2424x5b674fe7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
            Objects.requireNonNull(notificationVoiceReplyLogger);
            LogBuffer logBuffer = notificationVoiceReplyLogger.logBuffer;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$2 = new NotificationVoiceReplyLogger$logStatic$2("inSession: launching session block");
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                logBuffer.push(logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$2));
            }
            Function2<Session, Continuation<? super Unit>, Object> function2 = this.$block;
            C2423x17fc92b0 notificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1 = this.$session;
            this.label = 1;
            if (function2.invoke(notificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1, this) == coroutineSingletons) {
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
