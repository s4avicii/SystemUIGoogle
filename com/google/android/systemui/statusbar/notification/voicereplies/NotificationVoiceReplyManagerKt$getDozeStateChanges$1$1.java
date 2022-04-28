package com.google.android.systemui.statusbar.notification.voicereplies;

import androidx.preference.R$color;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1172}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Channel<Boolean> $chan;
    public final /* synthetic */ StatusBarStateController $this_getDozeStateChanges;
    public Object L$0;
    public Object L$1;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1(StatusBarStateController statusBarStateController, Channel<Boolean> channel, Continuation<? super NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1> continuation) {
        super(2, continuation);
        this.$this_getDozeStateChanges = statusBarStateController;
        this.$chan = channel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1(this.$this_getDozeStateChanges, this.$chan, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        ((NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    public final Object invokeSuspend(Object obj) {
        Throwable th;
        C2429x60ba525e notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$1;
        StatusBarStateController statusBarStateController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            C2429x60ba525e notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$12 = new C2429x60ba525e(this.$chan);
            this.$this_getDozeStateChanges.addCallback(notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$12);
            StatusBarStateController statusBarStateController2 = this.$this_getDozeStateChanges;
            try {
                this.L$0 = notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$12;
                this.L$1 = statusBarStateController2;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                    return coroutineSingletons;
                }
                notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$1 = notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$12;
                statusBarStateController = statusBarStateController2;
            } catch (Throwable th2) {
                statusBarStateController = statusBarStateController2;
                C2429x60ba525e notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$13 = notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$12;
                th = th2;
                notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$1 = notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$13;
                statusBarStateController.removeCallback(notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$1);
                throw th;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            statusBarStateController = (StatusBarStateController) this.L$1;
            notificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$1 = (C2429x60ba525e) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
            }
        }
        throw new KotlinNothingValueException();
    }
}
