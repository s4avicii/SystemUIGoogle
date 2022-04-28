package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.CtaState;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2324xa845190b implements Flow<CtaState> {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public C2324xa845190b(C2319x8eb430a3 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1) {
        this.$this_unsafeTransform$inlined = notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1;
    }

    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<Pair<? extends Integer, ? extends Integer>>() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r0 = r6 instanceof com.google.android.systemui.statusbar.C2324xa845190b.C23252.C23261
                    if (r0 == 0) goto L_0x0013
                    r0 = r6
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.google.android.systemui.statusbar.C2324xa845190b.C23252.C23261) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1$2$1
                    r0.<init>(r4, r6)
                L_0x0018:
                    java.lang.Object r6 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L_0x002f
                    if (r2 != r3) goto L_0x0027
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L_0x0059
                L_0x0027:
                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                    r4.<init>(r5)
                    throw r4
                L_0x002f:
                    kotlin.ResultKt.throwOnFailure(r6)
                    kotlinx.coroutines.flow.FlowCollector r4 = r2
                    kotlin.Pair r5 = (kotlin.Pair) r5
                    java.lang.Object r5 = r5.getSecond()
                    java.lang.Number r5 = (java.lang.Number) r5
                    int r5 = r5.intValue()
                    byte[][] r6 = com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt.AGSA_CERTS
                    r6 = 2
                    if (r5 == r6) goto L_0x004e
                    r6 = 3
                    if (r5 == r6) goto L_0x004b
                    com.google.android.systemui.statusbar.notification.voicereplies.CtaState r5 = com.google.android.systemui.statusbar.notification.voicereplies.CtaState.NONE
                    goto L_0x0050
                L_0x004b:
                    com.google.android.systemui.statusbar.notification.voicereplies.CtaState r5 = com.google.android.systemui.statusbar.notification.voicereplies.CtaState.QUICK_PHRASE
                    goto L_0x0050
                L_0x004e:
                    com.google.android.systemui.statusbar.notification.voicereplies.CtaState r5 = com.google.android.systemui.statusbar.notification.voicereplies.CtaState.HOTWORD
                L_0x0050:
                    r0.label = r3
                    java.lang.Object r4 = r4.emit(r5, r0)
                    if (r4 != r1) goto L_0x0059
                    return r1
                L_0x0059:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.C2324xa845190b.C23252.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
