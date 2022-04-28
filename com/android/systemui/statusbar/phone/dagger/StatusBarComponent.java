package com.android.systemui.statusbar.phone.dagger;

import com.android.keyguard.LockIconViewController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.NotificationShadeWindowViewController;
import com.android.systemui.statusbar.phone.StatusBarCommandQueueCallbacks;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import java.util.Set;

public interface StatusBarComponent {

    public interface Factory {
        StatusBarComponent create();
    }

    public interface Startable {
        void start();

        void stop();
    }

    CollapsedStatusBarFragment createCollapsedStatusBarFragment();

    AuthRippleController getAuthRippleController();

    LockIconViewController getLockIconViewController();

    NotificationPanelViewController getNotificationPanelViewController();

    NotificationShadeWindowView getNotificationShadeWindowView();

    NotificationShadeWindowViewController getNotificationShadeWindowViewController();

    NotificationShelfController getNotificationShelfController();

    NotificationStackScrollLayoutController getNotificationStackScrollLayoutController();

    Set<Startable> getStartables();

    StatusBarCommandQueueCallbacks getStatusBarCommandQueueCallbacks();

    StatusBarHeadsUpChangeListener getStatusBarHeadsUpChangeListener();

    StatusBarInitializer getStatusBarInitializer();
}
