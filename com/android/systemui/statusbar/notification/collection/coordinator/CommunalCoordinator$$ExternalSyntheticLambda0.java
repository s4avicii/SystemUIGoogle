package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CommunalCoordinator$$ExternalSyntheticLambda0 implements NotificationLockscreenUserManager.KeyguardNotificationSuppressor {
    public final /* synthetic */ CommunalCoordinator f$0;

    public /* synthetic */ CommunalCoordinator$$ExternalSyntheticLambda0(CommunalCoordinator communalCoordinator) {
        this.f$0 = communalCoordinator;
    }

    public final boolean shouldSuppressOnKeyguard(NotificationEntry notificationEntry) {
        CommunalCoordinator communalCoordinator = this.f$0;
        Objects.requireNonNull(communalCoordinator);
        CommunalStateController communalStateController = communalCoordinator.mCommunalStateController;
        Objects.requireNonNull(communalStateController);
        return communalStateController.mCommunalViewShowing;
    }
}
