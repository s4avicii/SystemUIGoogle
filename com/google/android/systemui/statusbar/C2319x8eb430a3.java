package com.google.android.systemui.statusbar;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2319x8eb430a3 implements Flow<Pair<? extends Integer, ? extends Integer>> {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    public C2319x8eb430a3(SharedFlowImpl sharedFlowImpl, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1) {
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
            public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                /*
                    r5 = this;
                    boolean r0 = r7 instanceof com.google.android.systemui.statusbar.C2319x8eb430a3.C23202.C23211
                    if (r0 == 0) goto L_0x0013
                    r0 = r7
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.google.android.systemui.statusbar.C2319x8eb430a3.C23202.C23211) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$filter$1$2$1
                    r0.<init>(r5, r7)
                L_0x0018:
                    java.lang.Object r7 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L_0x002f
                    if (r2 != r3) goto L_0x0027
                    kotlin.ResultKt.throwOnFailure(r7)
                    goto L_0x0066
                L_0x0027:
                    java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                    java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                    r5.<init>(r6)
                    throw r5
                L_0x002f:
                    kotlin.ResultKt.throwOnFailure(r7)
                    kotlinx.coroutines.flow.FlowCollector r7 = r3
                    r2 = r6
                    kotlin.Pair r2 = (kotlin.Pair) r2
                    java.lang.Object r4 = r2.getFirst()
                    java.lang.Number r4 = (java.lang.Number) r4
                    int r4 = r4.intValue()
                    com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1 r5 = r2
                    java.util.Objects.requireNonNull(r5)
                    int r5 = com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1.getUserId()
                    if (r4 != r5) goto L_0x005a
                    java.lang.Object r5 = r2.getSecond()
                    java.lang.Number r5 = (java.lang.Number) r5
                    int r5 = r5.intValue()
                    if (r5 == 0) goto L_0x005a
                    r5 = r3
                    goto L_0x005b
                L_0x005a:
                    r5 = 0
                L_0x005b:
                    if (r5 == 0) goto L_0x0066
                    r0.label = r3
                    java.lang.Object r5 = r7.emit(r6, r0)
                    if (r5 != r1) goto L_0x0066
                    return r1
                L_0x0066:
                    kotlin.Unit r5 = kotlin.Unit.INSTANCE
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.C2319x8eb430a3.C23202.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
