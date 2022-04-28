package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;
import kotlin.Pair;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$refreshCandidateOnNotifChanges$2$bindListener$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2402x1a034015 implements BindEventManager.Listener {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_refreshCandidateOnNotifChanges;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2402x1a034015(NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController) {
        this.$this_refreshCandidateOnNotifChanges = connection;
        this.this$0 = notificationVoiceReplyController;
    }

    public final void onViewBound(NotificationEntry notificationEntry) {
        NotificationVoiceReplyController.Connection connection = this.$this_refreshCandidateOnNotifChanges;
        NotificationVoiceReplyController notificationVoiceReplyController = this.this$0;
        Objects.requireNonNull(connection);
        if (!connection.entryReinflations.tryEmit(new Pair(notificationEntry, "onViewBound"))) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = notificationVoiceReplyController.logger;
            String str = notificationEntry.mKey;
            Objects.requireNonNull(notificationVoiceReplyLogger);
            LogBuffer logBuffer = notificationVoiceReplyLogger.logBuffer;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationVoiceReplyLogger$logReinflationDropped$2 notificationVoiceReplyLogger$logReinflationDropped$2 = NotificationVoiceReplyLogger$logReinflationDropped$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logReinflationDropped$2);
                obtain.str1 = str;
                obtain.str2 = "onViewBound";
                logBuffer.push(obtain);
            }
        }
    }
}
