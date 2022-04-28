package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.util.Objects;

public final class NotificationListController {
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final C12382 mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() {
        public final void onDeviceProvisionedChanged() {
            NotificationListController.this.mEntryManager.updateNotifications("device provisioned changed");
        }
    };
    public final C12371 mEntryListener = new NotificationEntryListener() {
        public final void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
            NotificationListController.this.mListContainer.cleanUpViewStateForEntry(notificationEntry);
        }
    };
    public final NotificationEntryManager mEntryManager;
    public final NotificationListContainer mListContainer;

    public NotificationListController(NotificationEntryManager notificationEntryManager, NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, DeviceProvisionedController deviceProvisionedController) {
        Objects.requireNonNull(notificationEntryManager);
        this.mEntryManager = notificationEntryManager;
        Objects.requireNonNull(notificationListContainerImpl);
        this.mListContainer = notificationListContainerImpl;
        Objects.requireNonNull(deviceProvisionedController);
        this.mDeviceProvisionedController = deviceProvisionedController;
    }
}
