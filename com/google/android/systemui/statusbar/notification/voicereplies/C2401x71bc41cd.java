package com.google.android.systemui.statusbar.notification.voicereplies;

import androidx.preference.R$color;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
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
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$refreshCandidateOnNotifChanges$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1172}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$refreshCandidateOnNotifChanges$2 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2401x71bc41cd extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_refreshCandidateOnNotifChanges;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2401x71bc41cd(NotificationVoiceReplyController notificationVoiceReplyController, NotificationVoiceReplyController.Connection connection, Continuation<? super C2401x71bc41cd> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
        this.$this_refreshCandidateOnNotifChanges = connection;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2401x71bc41cd(this.this$0, this.$this_refreshCandidateOnNotifChanges, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        ((C2401x71bc41cd) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    public final Object invokeSuspend(Object obj) {
        BindEventManager.Listener listener;
        C2403xb06196ba notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1;
        NotificationVoiceReplyController notificationVoiceReplyController;
        BindEventManager.Listener listener2;
        C2403xb06196ba notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$12;
        NotificationVoiceReplyController notificationVoiceReplyController2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyController.Connection connection = this.$this_refreshCandidateOnNotifChanges;
            NotificationVoiceReplyController notificationVoiceReplyController3 = this.this$0;
            listener = new C2402x1a034015(connection, notificationVoiceReplyController3);
            notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1 = new C2403xb06196ba(connection, notificationVoiceReplyController3);
            notificationVoiceReplyController3.notifCollection.addCollectionListener(notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1);
            BindEventManager bindEventManager = this.this$0.bindEventManager;
            Objects.requireNonNull(bindEventManager);
            bindEventManager.listeners.addIfAbsent(listener);
            notificationVoiceReplyController = this.this$0;
            try {
                this.L$0 = listener;
                this.L$1 = notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1;
                this.L$2 = notificationVoiceReplyController;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                    return coroutineSingletons;
                }
                listener2 = listener;
                notificationVoiceReplyController2 = notificationVoiceReplyController;
                notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$12 = notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1;
            } catch (Throwable th) {
                th = th;
                notificationVoiceReplyController.notifCollection.removeCollectionListener(notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1);
                BindEventManager bindEventManager2 = notificationVoiceReplyController.bindEventManager;
                Objects.requireNonNull(bindEventManager2);
                bindEventManager2.listeners.remove(listener);
                throw th;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            notificationVoiceReplyController2 = (NotificationVoiceReplyController) this.L$2;
            notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$12 = (C2403xb06196ba) this.L$1;
            listener2 = (BindEventManager.Listener) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th2) {
                notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1 = notificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$12;
                notificationVoiceReplyController = notificationVoiceReplyController2;
                Throwable th3 = th2;
                listener = listener2;
                th = th3;
            }
        }
        throw new KotlinNothingValueException();
    }
}
