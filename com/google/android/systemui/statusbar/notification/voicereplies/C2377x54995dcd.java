package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3$invokeSuspend$$inlined$filter$1$2 */
/* compiled from: Collect.kt */
public final class C2377x54995dcd implements FlowCollector<String> {
    public final /* synthetic */ VoiceReplyTarget $candidate$inlined;
    public final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

    public C2377x54995dcd(FlowCollector flowCollector, VoiceReplyTarget voiceReplyTarget) {
        this.$this_unsafeFlow$inlined = flowCollector;
        this.$candidate$inlined = voiceReplyTarget;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2377x54995dcd.C23781
            if (r0 == 0) goto L_0x0013
            r0 = r6
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2377x54995dcd.C23781) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3$invokeSuspend$$inlined$filter$1$2$1
            r0.<init>(r4, r6)
        L_0x0018:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L_0x002f
            if (r2 != r3) goto L_0x0027
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x004d
        L_0x0027:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002f:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow$inlined
            r2 = r5
            java.lang.String r2 = (java.lang.String) r2
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r4 = r4.$candidate$inlined
            java.util.Objects.requireNonNull(r4)
            java.lang.String r4 = r4.notifKey
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
            if (r4 == 0) goto L_0x004d
            r0.label = r3
            java.lang.Object r4 = r6.emit(r5, r0)
            if (r4 != r1) goto L_0x004d
            return r1
        L_0x004d:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2377x54995dcd.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
