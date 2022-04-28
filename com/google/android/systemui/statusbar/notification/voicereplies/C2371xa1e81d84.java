package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$invokeSuspend$$inlined$mapNotNull$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2371xa1e81d84 implements Flow<NotificationEntry> {
    public final /* synthetic */ VoiceReplyTarget $candidate$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public C2371xa1e81d84(Flow flow, VoiceReplyTarget voiceReplyTarget) {
        this.$this_unsafeTransform$inlined = flow;
        this.$candidate$inlined = voiceReplyTarget;
    }

    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        Flow flow = this.$this_unsafeTransform$inlined;
        final VoiceReplyTarget voiceReplyTarget = this.$candidate$inlined;
        Object collect = flow.collect(new FlowCollector<Pair<? extends NotificationEntry, ? extends Boolean>>() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r0 = r6 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2371xa1e81d84.C23722.C23731
                    if (r0 == 0) goto L_0x0013
                    r0 = r6
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$invokeSuspend$$inlined$mapNotNull$1$2$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2371xa1e81d84.C23722.C23731) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$invokeSuspend$$inlined$mapNotNull$1$2$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$invokeSuspend$$inlined$mapNotNull$1$2$1
                    r0.<init>(r4, r6)
                L_0x0018:
                    java.lang.Object r6 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L_0x002f
                    if (r2 != r3) goto L_0x0027
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L_0x005c
                L_0x0027:
                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                    r4.<init>(r5)
                    throw r4
                L_0x002f:
                    kotlin.ResultKt.throwOnFailure(r6)
                    kotlinx.coroutines.flow.FlowCollector r6 = r3
                    kotlin.Pair r5 = (kotlin.Pair) r5
                    java.lang.Object r5 = r5.component1()
                    com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r5
                    java.util.Objects.requireNonNull(r5)
                    java.lang.String r2 = r5.mKey
                    com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r4 = r2
                    java.util.Objects.requireNonNull(r4)
                    java.lang.String r4 = r4.notifKey
                    boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
                    if (r4 == 0) goto L_0x004f
                    goto L_0x0050
                L_0x004f:
                    r5 = 0
                L_0x0050:
                    if (r5 != 0) goto L_0x0053
                    goto L_0x005c
                L_0x0053:
                    r0.label = r3
                    java.lang.Object r4 = r6.emit(r5, r0)
                    if (r4 != r1) goto L_0x005c
                    return r1
                L_0x005c:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2371xa1e81d84.C23722.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
