package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient$startClient$job$1$subscription$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2348x2f5c495e implements NotificationVoiceReplyHandler {
    public final /* synthetic */ Ref$BooleanRef $notifAvailable;
    public final StateFlowImpl showCta = new StateFlowImpl(CtaState.HOTWORD);
    public final int userId;

    public final void onNotifAvailableForQuickPhraseReplyChanged(boolean z) {
    }

    public C2348x2f5c495e(DebugNotificationVoiceReplyClient debugNotificationVoiceReplyClient, Ref$BooleanRef ref$BooleanRef) {
        this.$notifAvailable = ref$BooleanRef;
        this.userId = debugNotificationVoiceReplyClient.lockscreenUserManager.getCurrentUserId();
    }

    public final void onNotifAvailableForReplyChanged(boolean z) {
        this.$notifAvailable.element = z;
    }

    public final StateFlow<CtaState> getShowCta() {
        return this.showCta;
    }

    public final int getUserId() {
        return this.userId;
    }
}
