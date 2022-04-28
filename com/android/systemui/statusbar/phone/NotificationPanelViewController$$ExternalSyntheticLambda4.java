package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.phone.panelstate.PanelStateListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda4 implements PanelStateListener {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda4(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public final void onPanelStateChanged(int i) {
        boolean z;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        Objects.requireNonNull(notificationPanelViewController);
        AmbientState ambientState = notificationPanelViewController.mAmbientState;
        if (i == 1) {
            z = true;
        } else {
            z = false;
        }
        Objects.requireNonNull(ambientState);
        ambientState.mIsShadeOpening = z;
        notificationPanelViewController.updateQSExpansionEnabledAmbient();
        if (i == 2 && notificationPanelViewController.mCurrentPanelState != i) {
            notificationPanelViewController.mView.sendAccessibilityEvent(32);
        }
        if (i == 1) {
            notificationPanelViewController.mStatusBar.makeExpandedVisible(false);
        }
        if (i == 0) {
            notificationPanelViewController.mView.post(notificationPanelViewController.mMaybeHideExpandedRunnable);
        }
        notificationPanelViewController.mCurrentPanelState = i;
    }
}
