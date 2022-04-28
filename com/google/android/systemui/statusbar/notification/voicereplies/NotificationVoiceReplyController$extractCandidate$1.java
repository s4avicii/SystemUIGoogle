package com.google.android.systemui.statusbar.notification.voicereplies;

import android.app.Notification;
import android.content.Context;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyController$extractCandidate$1 extends Lambda implements Function0<Notification.Builder> {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyController$extractCandidate$1(NotificationVoiceReplyController notificationVoiceReplyController, NotificationEntry notificationEntry) {
        super(0);
        this.this$0 = notificationVoiceReplyController;
        this.$entry = notificationEntry;
    }

    public final Object invoke() {
        Context context = this.this$0.context;
        NotificationEntry notificationEntry = this.$entry;
        Objects.requireNonNull(notificationEntry);
        return Notification.Builder.recoverBuilder(context, notificationEntry.mSbn.getNotification());
    }
}
