package com.android.systemui.statusbar;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.phone.KeyguardBypassController;

public final class NotificationShelfController {
    public final ActivatableNotificationViewController mActivatableNotificationViewController;
    public AmbientState mAmbientState;
    public final KeyguardBypassController mKeyguardBypassController;
    public final C11791 mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        public final void onViewAttachedToWindow(View view) {
            NotificationShelfController notificationShelfController = NotificationShelfController.this;
            notificationShelfController.mStatusBarStateController.addCallback(notificationShelfController.mView, 3);
        }

        public final void onViewDetachedFromWindow(View view) {
            NotificationShelfController notificationShelfController = NotificationShelfController.this;
            notificationShelfController.mStatusBarStateController.removeCallback(notificationShelfController.mView);
        }
    };
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final NotificationShelf mView;

    public NotificationShelfController(NotificationShelf notificationShelf, ActivatableNotificationViewController activatableNotificationViewController, KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.mView = notificationShelf;
        this.mActivatableNotificationViewController = activatableNotificationViewController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
    }
}
