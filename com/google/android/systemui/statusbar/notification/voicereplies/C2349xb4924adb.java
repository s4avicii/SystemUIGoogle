package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Pair;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2349xb4924adb implements FlowCollector<Pair<? extends String, ? extends RemoteInputViewController>> {
    public final /* synthetic */ CoroutineScope $$this$coroutineScope$inlined;
    public final /* synthetic */ Flow $remoteInputViewActivatedForVoiceReply$inlined;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs$inlined;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2349xb4924adb(CoroutineScope coroutineScope, NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController, Flow flow) {
        this.$$this$coroutineScope$inlined = coroutineScope;
        this.$this_logUiEventsForActivatedVoiceReplyInputs$inlined = connection;
        this.this$0 = notificationVoiceReplyController;
        this.$remoteInputViewActivatedForVoiceReply$inlined = flow;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(kotlin.Pair<? extends java.lang.String, ? extends com.android.systemui.statusbar.policy.RemoteInputViewController> r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            r13 = this;
            boolean r0 = r15 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2349xb4924adb.C23501
            if (r0 == 0) goto L_0x0013
            r0 = r15
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2349xb4924adb.C23501) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1$1
            r0.<init>(r13, r15)
        L_0x0018:
            java.lang.Object r15 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x003d
            if (r2 != r3) goto L_0x0035
            java.lang.Object r13 = r0.L$2
            kotlinx.coroutines.Job r13 = (kotlinx.coroutines.Job) r13
            java.lang.Object r14 = r0.L$1
            kotlinx.coroutines.Job r14 = (kotlinx.coroutines.Job) r14
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.Job r0 = (kotlinx.coroutines.Job) r0
            kotlin.ResultKt.throwOnFailure(r15)
            goto L_0x00d6
        L_0x0035:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r15)
            kotlin.Pair r14 = (kotlin.Pair) r14
            java.lang.Object r15 = r14.component1()
            java.lang.String r15 = (java.lang.String) r15
            java.lang.Object r14 = r14.component2()
            r6 = r14
            com.android.systemui.statusbar.policy.RemoteInputViewController r6 = (com.android.systemui.statusbar.policy.RemoteInputViewController) r6
            kotlinx.coroutines.CompletableDeferredImpl r14 = new kotlinx.coroutines.CompletableDeferredImpl
            r14.<init>(r4)
            kotlinx.coroutines.CoroutineScope r2 = r13.$$this$coroutineScope$inlined
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1 r12 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r7 = r13.$this_logUiEventsForActivatedVoiceReplyInputs$inlined
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r9 = r13.this$0
            r11 = 0
            r5 = r12
            r8 = r15
            r10 = r14
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r5 = 3
            kotlinx.coroutines.StandaloneCoroutine r2 = kotlinx.coroutines.BuildersKt.launch$default(r2, r4, r4, r12, r5)
            kotlinx.coroutines.CoroutineScope r6 = r13.$$this$coroutineScope$inlined
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$removalJob$1 r7 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$removalJob$1
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r8 = r13.$this_logUiEventsForActivatedVoiceReplyInputs$inlined
            r7.<init>(r8, r14, r15, r4)
            kotlinx.coroutines.StandaloneCoroutine r6 = kotlinx.coroutines.BuildersKt.launch$default(r6, r4, r4, r7, r5)
            kotlinx.coroutines.CoroutineScope r7 = r13.$$this$coroutineScope$inlined
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$restartedJob$1 r8 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$restartedJob$1
            kotlinx.coroutines.flow.Flow r13 = r13.$remoteInputViewActivatedForVoiceReply$inlined
            r8.<init>(r13, r14, r15, r4)
            kotlinx.coroutines.StandaloneCoroutine r13 = kotlinx.coroutines.BuildersKt.launch$default(r7, r4, r4, r8, r5)
            r0.L$0 = r2
            r0.L$1 = r6
            r0.L$2 = r13
            r0.label = r3
        L_0x008a:
            java.lang.Object r15 = r14.mo21281x8adbf455()
            boolean r5 = r15 instanceof kotlinx.coroutines.Incomplete
            if (r5 != 0) goto L_0x00a9
            boolean r14 = r15 instanceof kotlinx.coroutines.CompletedExceptionally
            if (r14 == 0) goto L_0x00a4
            kotlinx.coroutines.CompletedExceptionally r15 = (kotlinx.coroutines.CompletedExceptionally) r15
            java.lang.Throwable r13 = r15.cause
            boolean r14 = kotlinx.coroutines.DebugKt.RECOVER_STACK_TRACES
            if (r14 == 0) goto L_0x00a3
            java.lang.Throwable r13 = kotlinx.coroutines.internal.StackTraceRecoveryKt.access$recoverFromStackFrame(r13, r0)
            throw r13
        L_0x00a3:
            throw r13
        L_0x00a4:
            java.lang.Object r14 = kotlinx.coroutines.JobSupportKt.unboxState(r15)
            goto L_0x00d1
        L_0x00a9:
            int r15 = r14.startInternal(r15)
            if (r15 < 0) goto L_0x008a
            kotlinx.coroutines.JobSupport$AwaitContinuation r15 = new kotlinx.coroutines.JobSupport$AwaitContinuation
            kotlin.coroutines.Continuation r0 = androidx.preference.R$color.intercepted(r0)
            r15.<init>(r0, r14)
            r15.initCancellability()
            kotlinx.coroutines.ResumeAwaitOnCompletion r0 = new kotlinx.coroutines.ResumeAwaitOnCompletion
            r0.<init>(r15)
            r5 = 0
            kotlinx.coroutines.DisposableHandle r14 = r14.invokeOnCompletion(r5, r3, r0)
            kotlinx.coroutines.DisposeOnCancel r0 = new kotlinx.coroutines.DisposeOnCancel
            r0.<init>(r14)
            r15.invokeOnCancellation(r0)
            java.lang.Object r14 = r15.getResult()
        L_0x00d1:
            if (r14 != r1) goto L_0x00d4
            return r1
        L_0x00d4:
            r0 = r2
            r14 = r6
        L_0x00d6:
            r0.cancel(r4)
            r14.cancel(r4)
            r13.cancel(r4)
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2349xb4924adb.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
