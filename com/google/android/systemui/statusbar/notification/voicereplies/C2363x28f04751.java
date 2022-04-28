package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$map$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2363x28f04751 implements Flow<Boolean> {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public C2363x28f04751(C2360xcff0be74 notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$combine$1) {
        this.$this_unsafeTransform$inlined = notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$combine$1;
    }

    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<CtaState>() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r0 = r6 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2363x28f04751.C23642.C23651
                    if (r0 == 0) goto L_0x0013
                    r0 = r6
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$map$1$2$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2363x28f04751.C23642.C23651) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$map$1$2$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$map$1$2$1
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
                    kotlinx.coroutines.flow.FlowCollector r4 = r2
                    com.google.android.systemui.statusbar.notification.voicereplies.CtaState r5 = (com.google.android.systemui.statusbar.notification.voicereplies.CtaState) r5
                    com.google.android.systemui.statusbar.notification.voicereplies.CtaState r6 = com.google.android.systemui.statusbar.notification.voicereplies.CtaState.QUICK_PHRASE
                    if (r5 != r6) goto L_0x003c
                    r5 = r3
                    goto L_0x003d
                L_0x003c:
                    r5 = 0
                L_0x003d:
                    java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                    r0.label = r3
                    java.lang.Object r4 = r4.emit(r5, r0)
                    if (r4 != r1) goto L_0x004a
                    return r1
                L_0x004a:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2363x28f04751.C23642.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
