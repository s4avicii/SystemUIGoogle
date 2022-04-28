package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$mapNotNull$1$2 */
/* compiled from: Collect.kt */
public final class C2386xa0517967 implements FlowCollector<NotificationEntry> {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine$inlined;
    public final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2386xa0517967(FlowCollector flowCollector, NotificationVoiceReplyController notificationVoiceReplyController, NotificationVoiceReplyController.Connection connection) {
        this.$this_unsafeFlow$inlined = flowCollector;
        this.this$0 = notificationVoiceReplyController;
        this.$this_stateMachine$inlined = connection;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2386xa0517967.C23871
            if (r0 == 0) goto L_0x0013
            r0 = r6
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$mapNotNull$1$2$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2386xa0517967.C23871) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$mapNotNull$1$2$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$mapNotNull$1$2$1
            r0.<init>(r4, r6)
        L_0x0018:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L_0x002f
            if (r2 != r3) goto L_0x0027
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x004a
        L_0x0027:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002f:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r5
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r2 = r4.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r4 = r4.$this_stateMachine$inlined
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r4 = com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController.extractCandidate$default(r2, r4, r5)
            if (r4 != 0) goto L_0x0041
            goto L_0x004a
        L_0x0041:
            r0.label = r3
            java.lang.Object r4 = r6.emit(r4, r0)
            if (r4 != r1) goto L_0x004a
            return r1
        L_0x004a:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2386xa0517967.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
