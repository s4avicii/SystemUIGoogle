package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.OnSendRemoteInputListener;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletableDeferred;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2394xcec46519 implements OnSendRemoteInputListener {
    public final /* synthetic */ CompletableDeferred<Unit> $completion;
    public final /* synthetic */ String $key;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2394xcec46519(NotificationVoiceReplyController.Connection connection, String str, NotificationVoiceReplyController notificationVoiceReplyController, CompletableDeferred<Unit> completableDeferred) {
        this.$this_logUiEventsForActivatedVoiceReplyInputs = connection;
        this.$key = str;
        this.this$0 = notificationVoiceReplyController;
        this.$completion = completableDeferred;
    }

    public final void onSendRemoteInput() {
        InSession inSession;
        VoiceReplyTarget voiceReplyTarget;
        NotificationVoiceReplyController.Connection connection = this.$this_logUiEventsForActivatedVoiceReplyInputs;
        Objects.requireNonNull(connection);
        VoiceReplyState value = connection.stateFlow.getValue();
        String str = null;
        if (value instanceof InSession) {
            inSession = (InSession) value;
        } else {
            inSession = null;
        }
        if (!(inSession == null || (voiceReplyTarget = inSession.target) == null)) {
            str = voiceReplyTarget.notifKey;
        }
        if (!Intrinsics.areEqual(str, this.$key)) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
            String str2 = this.$key;
            Objects.requireNonNull(notificationVoiceReplyLogger);
            notificationVoiceReplyLogger.eventLogger.log(VoiceReplyEvent.MSG_SEND_DELAYED);
            notificationVoiceReplyLogger.logMsgSent(str2, NotificationVoiceReplyLogger.SendType.DELAYED);
        } else if (this.this$0.statusBarStateController.getState() == 2) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger2 = this.this$0.logger;
            String str3 = this.$key;
            Objects.requireNonNull(notificationVoiceReplyLogger2);
            notificationVoiceReplyLogger2.eventLogger.log(VoiceReplyEvent.MSG_SEND_AUTH_BYPASSED);
            notificationVoiceReplyLogger2.logMsgSent(str3, NotificationVoiceReplyLogger.SendType.BYPASS);
        } else {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger3 = this.this$0.logger;
            String str4 = this.$key;
            Objects.requireNonNull(notificationVoiceReplyLogger3);
            notificationVoiceReplyLogger3.eventLogger.log(VoiceReplyEvent.MSG_SEND_UNLOCKED);
            notificationVoiceReplyLogger3.logMsgSent(str4, NotificationVoiceReplyLogger.SendType.UNLOCKED);
        }
        this.$completion.complete();
    }

    public final void onSendRequestBounced() {
        NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
        String str = this.$key;
        Objects.requireNonNull(notificationVoiceReplyLogger);
        notificationVoiceReplyLogger.eventLogger.log(VoiceReplyEvent.MSG_SEND_BOUNCED);
        notificationVoiceReplyLogger.logMsgSent(str, NotificationVoiceReplyLogger.SendType.BOUNCED);
    }
}
