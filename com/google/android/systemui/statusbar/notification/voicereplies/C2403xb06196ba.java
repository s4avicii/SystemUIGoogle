package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$refreshCandidateOnNotifChanges$2$notifListener$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2403xb06196ba implements NotifCollectionListener {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_refreshCandidateOnNotifChanges;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2403xb06196ba(NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController) {
        this.$this_refreshCandidateOnNotifChanges = connection;
        this.this$0 = notificationVoiceReplyController;
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        NotificationVoiceReplyController.Connection connection = this.$this_refreshCandidateOnNotifChanges;
        Objects.requireNonNull(connection);
        if (!connection.entryRemovals.tryEmit(notificationEntry.mKey)) {
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
            String str = notificationEntry.mKey;
            Objects.requireNonNull(notificationVoiceReplyLogger);
            LogBuffer logBuffer = notificationVoiceReplyLogger.logBuffer;
            LogLevel logLevel = LogLevel.WARNING;
            NotificationVoiceReplyLogger$logRemovalDropped$2 notificationVoiceReplyLogger$logRemovalDropped$2 = NotificationVoiceReplyLogger$logRemovalDropped$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logRemovalDropped$2);
                obtain.str1 = str;
                logBuffer.push(obtain);
            }
        }
    }
}
