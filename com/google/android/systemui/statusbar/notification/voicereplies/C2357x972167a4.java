package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$2 */
/* compiled from: SafeCollector.common.kt */
public final class C2357x972167a4 implements Flow<List<? extends NotificationEntry>> {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2357x972167a4(Flow flow, NotificationVoiceReplyController notificationVoiceReplyController) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = notificationVoiceReplyController;
    }

    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        Flow flow = this.$this_unsafeTransform$inlined;
        final NotificationVoiceReplyController notificationVoiceReplyController = this.this$0;
        Object collect = flow.collect(new FlowCollector<Pair<? extends NotificationEntry, ? extends Boolean>>() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0021  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                /*
                    r4 = this;
                    boolean r0 = r6 instanceof com.google.android.systemui.statusbar.notification.voicereplies.C2357x972167a4.C23582.C23591
                    if (r0 == 0) goto L_0x0013
                    r0 = r6
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$2$2$1 r0 = (com.google.android.systemui.statusbar.notification.voicereplies.C2357x972167a4.C23582.C23591) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$2$2$1 r0 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$2$2$1
                    r0.<init>(r4, r6)
                L_0x0018:
                    java.lang.Object r6 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L_0x002f
                    if (r2 != r3) goto L_0x0027
                    kotlin.ResultKt.throwOnFailure(r6)
                    goto L_0x0051
                L_0x0027:
                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                    r4.<init>(r5)
                    throw r4
                L_0x002f:
                    kotlin.ResultKt.throwOnFailure(r6)
                    kotlinx.coroutines.flow.FlowCollector r6 = r3
                    kotlin.Pair r5 = (kotlin.Pair) r5
                    com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r4 = r2
                    com.android.systemui.statusbar.policy.HeadsUpManager r4 = r4.headsUpManager
                    java.util.stream.Stream r4 = r4.getAllEntries()
                    java.util.stream.Collector r5 = java.util.stream.Collectors.toList()
                    java.lang.Object r4 = r4.collect(r5)
                    java.util.List r4 = (java.util.List) r4
                    r0.label = r3
                    java.lang.Object r4 = r6.emit(r4, r0)
                    if (r4 != r1) goto L_0x0051
                    return r1
                L_0x0051:
                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2357x972167a4.C23582.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, continuation);
        if (collect == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
