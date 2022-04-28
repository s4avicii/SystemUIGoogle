package com.google.android.systemui.statusbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStatic$2;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$hideVisibleQuickPhraseCta$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {110}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$hideVisibleQuickPhraseCta$1 */
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class C2334xdd6b9453 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2334xdd6b9453(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, Continuation<? super C2334xdd6b9453> continuation) {
        super(1, continuation);
        this.this$0 = notificationVoiceReplyManagerService;
    }

    public final Object invoke(Object obj) {
        return new C2334xdd6b9453(this.this$0, (Continuation) obj).invokeSuspend(Unit.INSTANCE);
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
            NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$2 = new NotificationVoiceReplyLogger$logStatic$2("BINDER: hideVisibleQuickPhraseCta");
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                logBuffer.push(logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$2));
            }
            NotificationVoiceReplyManager notificationVoiceReplyManager = this.this$0.voiceReplyManager;
            if (notificationVoiceReplyManager == null) {
                notificationVoiceReplyManager = null;
            }
            this.label = 1;
            if (notificationVoiceReplyManager.requestHideQuickPhraseCTA(this) == coroutineSingletons) {
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
