package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$$inlined$map$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2351xc58fde74 implements Flow<VoiceReplyTarget> {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public C2351xc58fde74(MutableStateFlow mutableStateFlow) {
        this.$this_unsafeTransform$inlined = mutableStateFlow;
    }

    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<VoiceReplyState>() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r0 = r6 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2351xc58fde74.C23522.C23531
                    if (r0 == 0) goto L_0x0013
                    r0 = r6
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$$inlined$map$1$2$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2351xc58fde74.C23522.C23531) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$$inlined$map$1$2$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfHotwordReplyAvailability$$inlined$map$1$2$1
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
                    kotlinx.coroutines.flow.FlowCollector r4 = r2
                    com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyState r5 = (com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyState) r5
                    boolean r6 = r5 instanceof com.google.android.systemui.statusbar.notification.voicereplies.HasCandidate
                    r2 = 0
                    if (r6 == 0) goto L_0x003e
                    com.google.android.systemui.statusbar.notification.voicereplies.HasCandidate r5 = (com.google.android.systemui.statusbar.notification.voicereplies.HasCandidate) r5
                    goto L_0x003f
                L_0x003e:
                    r5 = r2
                L_0x003f:
                    if (r5 != 0) goto L_0x0042
                    goto L_0x0044
                L_0x0042:
                    com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r2 = r5.candidate
                L_0x0044:
                    r0.label = r3
                    java.lang.Object r4 = r4.emit(r2, r0)
                    if (r4 != r1) goto L_0x004d
                    return r1
                L_0x004d:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2351xc58fde74.C23522.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
