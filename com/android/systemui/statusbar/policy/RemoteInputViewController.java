package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0;
import com.google.android.systemui.statusbar.notification.voicereplies.C2394xcec46519;

/* compiled from: RemoteInputViewController.kt */
public interface RemoteInputViewController {
    void addOnSendRemoteInputListener(C2394xcec46519 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1);

    void bind();

    void removeOnSendRemoteInputListener(C2394xcec46519 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$sendEventJob$1$listener$1);

    void setBouncerChecker(NotificationRemoteInputManager$$ExternalSyntheticLambda0 notificationRemoteInputManager$$ExternalSyntheticLambda0);

    void setPendingIntent(PendingIntent pendingIntent);

    void setRemoteInput(RemoteInput remoteInput);

    void setRemoteInputs(RemoteInput[] remoteInputArr);

    void unbind();

    boolean updatePendingIntentFromActions(Notification.Action[] actionArr);
}
