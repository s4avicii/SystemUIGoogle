package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.phone.KeyguardBypassController;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1384xbae1b0c3 implements KeyguardBypassController.OnBypassStateChangedListener {
    public final /* synthetic */ NotificationStackScrollLayout f$0;

    public /* synthetic */ C1384xbae1b0c3(NotificationStackScrollLayout notificationStackScrollLayout) {
        this.f$0 = notificationStackScrollLayout;
    }

    public final void onBypassStateChanged(boolean z) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mKeyguardBypassEnabled = z;
    }
}
