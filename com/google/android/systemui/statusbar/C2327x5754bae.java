package com.google.android.systemui.statusbar;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2327x5754bae implements Flow<Pair<? extends Integer, ? extends Integer>> {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    public C2327x5754bae(SharedFlowImpl sharedFlowImpl, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1) {
        this.$this_unsafeTransform$inlined = sharedFlowImpl;
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
    }

    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        Flow flow = this.$this_unsafeTransform$inlined;
        final NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$0;
        Object collect = flow.collect(new FlowCollector<Pair<? extends Integer, ? extends Integer>>() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r0 = r6 instanceof com.google.android.systemui.statusbar.C2327x5754bae.C23282.C23291
                    if (r0 == 0) goto L_0x0013
                    r0 = r6
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1$2$1 r0 = (com.google.android.systemui.statusbar.C2327x5754bae.C23282.C23291) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1$2$1 r0 = new com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1$2$1
                    r0.<init>(r4, r6)
                L_0x0018:
                    java.lang.Object r6 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L_0x002f
                    if (r2 != r3) goto L_0x0027
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L_0x005a
                L_0x0027:
                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                    r4.<init>(r5)
                    throw r4
                L_0x002f:
                    kotlin.ResultKt.throwOnFailure(r6)
                    kotlinx.coroutines.flow.FlowCollector r6 = r3
                    r2 = r5
                    kotlin.Pair r2 = (kotlin.Pair) r2
                    java.lang.Object r2 = r2.getFirst()
                    java.lang.Number r2 = (java.lang.Number) r2
                    int r2 = r2.intValue()
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1 r4 = r2
                    java.util.Objects.requireNonNull(r4)
                    int r4 = com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1.getUserId()
                    if (r2 != r4) goto L_0x004e
                    r4 = r3
                    goto L_0x004f
                L_0x004e:
                    r4 = 0
                L_0x004f:
                    if (r4 == 0) goto L_0x005a
                    r0.label = r3
                    java.lang.Object r4 = r6.emit(r5, r0)
                    if (r4 != r1) goto L_0x005a
                    return r1
                L_0x005a:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.C2327x5754bae.C23282.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
