package com.google.android.systemui.statusbar.notification.voicereplies;

import androidx.preference.R$color;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1172}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2393x8afb2cc extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CompletableDeferred<Unit> $completion;
    public final /* synthetic */ String $key;
    public final /* synthetic */ RemoteInputViewController $rivc;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs;
    public Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2393x8afb2cc(RemoteInputViewController remoteInputViewController, NotificationVoiceReplyController.Connection connection, String str, NotificationVoiceReplyController notificationVoiceReplyController, CompletableDeferred<Unit> completableDeferred, Continuation<? super C2393x8afb2cc> continuation) {
        super(2, continuation);
        this.$rivc = remoteInputViewController;
        this.$this_logUiEventsForActivatedVoiceReplyInputs = connection;
        this.$key = str;
        this.this$0 = notificationVoiceReplyController;
        this.$completion = completableDeferred;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2393x8afb2cc(this.$rivc, this.$this_logUiEventsForActivatedVoiceReplyInputs, this.$key, this.this$0, this.$completion, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        ((C2393x8afb2cc) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    public final Object invokeSuspend(Object obj) {
        C2394xcec46519 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1;
        RemoteInputViewController remoteInputViewController;
        C2394xcec46519 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$12;
        RemoteInputViewController remoteInputViewController2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1 = new C2394xcec46519(this.$this_logUiEventsForActivatedVoiceReplyInputs, this.$key, this.this$0, this.$completion);
            this.$rivc.addOnSendRemoteInputListener(notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1);
            remoteInputViewController = this.$rivc;
            try {
                this.L$0 = notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1;
                this.L$1 = remoteInputViewController;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                    return coroutineSingletons;
                }
                notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$12 = notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1;
                remoteInputViewController2 = remoteInputViewController;
            } catch (Throwable th) {
                th = th;
                remoteInputViewController.removeOnSendRemoteInputListener(notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1);
                throw th;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            remoteInputViewController2 = (RemoteInputViewController) this.L$1;
            notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$12 = (C2394xcec46519) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th2) {
                remoteInputViewController = remoteInputViewController2;
                Throwable th3 = th2;
                notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1 = notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$12;
                th = th3;
            }
        }
        throw new KotlinNothingValueException();
    }
}
