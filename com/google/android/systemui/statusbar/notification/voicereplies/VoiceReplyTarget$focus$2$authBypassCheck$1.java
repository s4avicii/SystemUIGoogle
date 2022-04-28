package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import java.util.Objects;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class VoiceReplyTarget$focus$2$authBypassCheck$1 implements NotificationRemoteInputManager.AuthBypassPredicate {
    public final /* synthetic */ AuthStateRef $authState;

    public VoiceReplyTarget$focus$2$authBypassCheck$1(AuthStateRef authStateRef) {
        this.$authState = authStateRef;
    }

    public final boolean canSendRemoteInputWithoutBouncer() {
        AuthStateRef authStateRef = this.$authState;
        Objects.requireNonNull(authStateRef);
        if (authStateRef.value == 1) {
            return true;
        }
        return false;
    }
}
