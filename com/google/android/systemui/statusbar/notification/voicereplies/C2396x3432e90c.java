package com.google.android.systemui.statusbar.notification.voicereplies;

import androidx.preference.R$color;
import java.util.Objects;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$4", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1174}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$4 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2396x3432e90c extends SuspendLambda implements Function2<VoiceReplyTarget, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2396x3432e90c(NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super C2396x3432e90c> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        C2396x3432e90c notificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$4 = new C2396x3432e90c(this.this$0, continuation);
        notificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$4.L$0 = obj;
        return notificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$4;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2396x3432e90c) create((VoiceReplyTarget) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Throwable th;
        VoiceReplyTarget voiceReplyTarget;
        NotificationVoiceReplyController notificationVoiceReplyController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            VoiceReplyTarget voiceReplyTarget2 = (VoiceReplyTarget) this.L$0;
            if (voiceReplyTarget2 == null) {
                return Unit.INSTANCE;
            }
            this.this$0.logger.logHotwordAvailabilityChanged(voiceReplyTarget2.userId, true);
            for (NotificationVoiceReplyHandler onNotifAvailableForReplyChanged : voiceReplyTarget2.handlers) {
                onNotifAvailableForReplyChanged.onNotifAvailableForReplyChanged(true);
            }
            NotificationVoiceReplyController notificationVoiceReplyController2 = this.this$0;
            try {
                this.L$0 = voiceReplyTarget2;
                this.L$1 = notificationVoiceReplyController2;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                    return coroutineSingletons;
                }
                voiceReplyTarget = voiceReplyTarget2;
                notificationVoiceReplyController = notificationVoiceReplyController2;
            } catch (Throwable th2) {
                notificationVoiceReplyController = notificationVoiceReplyController2;
                VoiceReplyTarget voiceReplyTarget3 = voiceReplyTarget2;
                th = th2;
                voiceReplyTarget = voiceReplyTarget3;
                NotificationVoiceReplyLogger notificationVoiceReplyLogger = notificationVoiceReplyController.logger;
                Objects.requireNonNull(voiceReplyTarget);
                notificationVoiceReplyLogger.logHotwordAvailabilityChanged(voiceReplyTarget.userId, false);
                for (NotificationVoiceReplyHandler onNotifAvailableForReplyChanged2 : voiceReplyTarget.handlers) {
                    onNotifAvailableForReplyChanged2.onNotifAvailableForReplyChanged(false);
                }
                throw th;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            notificationVoiceReplyController = (NotificationVoiceReplyController) this.L$1;
            voiceReplyTarget = (VoiceReplyTarget) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
            }
        }
        throw new KotlinNothingValueException();
    }
}
