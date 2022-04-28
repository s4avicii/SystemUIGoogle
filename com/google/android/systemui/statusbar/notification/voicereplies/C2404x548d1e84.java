package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$resetStateOnUserChange$listener$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2404x548d1e84 implements NotificationLockscreenUserManager.UserChangedListener {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_resetStateOnUserChange;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2404x548d1e84(NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController) {
        this.$this_resetStateOnUserChange = connection;
        this.this$0 = notificationVoiceReplyController;
    }

    public final void onUserChanged(int i) {
        NotificationVoiceReplyController.Connection connection = this.$this_resetStateOnUserChange;
        Objects.requireNonNull(connection);
        connection.stateFlow.setValue(this.this$0.queryInitialState(this.$this_resetStateOnUserChange));
    }
}
