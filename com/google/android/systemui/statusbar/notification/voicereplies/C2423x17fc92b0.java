package com.google.android.systemui.statusbar.notification.voicereplies;

import java.util.Objects;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$1$session$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2423x17fc92b0 implements Session {
    public final /* synthetic */ AuthStateRef $authState;

    public C2423x17fc92b0(AuthStateRef authStateRef) {
        this.$authState = authStateRef;
    }

    public final void setVoiceAuthState(int i) {
        AuthStateRef authStateRef = this.$authState;
        Objects.requireNonNull(authStateRef);
        authStateRef.value = i;
    }
}
