package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$4", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$4 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2397xb38f1f7d extends SuspendLambda implements Function3<VoiceReplyTarget, List<? extends NotificationEntry>, Continuation<? super VoiceReplyTarget>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2397xb38f1f7d(NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super C2397xb38f1f7d> continuation) {
        super(3, continuation);
        this.this$0 = notificationVoiceReplyController;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C2397xb38f1f7d notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$4 = new C2397xb38f1f7d(this.this$0, (Continuation) obj3);
        notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$4.L$0 = (VoiceReplyTarget) obj;
        notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$4.L$1 = (List) obj2;
        return notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$4.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            int r0 = r6.label
            if (r0 != 0) goto L_0x0051
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r7 = (com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget) r7
            java.lang.Object r0 = r6.L$1
            java.util.List r0 = (java.util.List) r0
            r1 = 0
            if (r7 != 0) goto L_0x0014
        L_0x0012:
            r7 = r1
            goto L_0x0050
        L_0x0014:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r6 = r6.this$0
            boolean r2 = r0 instanceof java.util.Collection
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0024
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x0024
        L_0x0022:
            r0 = r4
            goto L_0x0042
        L_0x0024:
            java.util.Iterator r0 = r0.iterator()
        L_0x0028:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0022
            java.lang.Object r2 = r0.next()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r2
            java.util.Objects.requireNonNull(r2)
            java.lang.String r2 = r2.mKey
            java.lang.String r5 = r7.notifKey
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r5)
            if (r2 == 0) goto L_0x0028
            r0 = r3
        L_0x0042:
            if (r0 == 0) goto L_0x004d
            com.android.systemui.statusbar.SysuiStatusBarStateController r6 = r6.statusBarStateController
            boolean r6 = r6.isDozing()
            if (r6 == 0) goto L_0x004d
            goto L_0x004e
        L_0x004d:
            r3 = r4
        L_0x004e:
            if (r3 == 0) goto L_0x0012
        L_0x0050:
            return r7
        L_0x0051:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2397xb38f1f7d.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
