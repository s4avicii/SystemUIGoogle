package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.util.Objects;

public final class DeviceProvisionedCoordinator implements Coordinator {
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final C12582 mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() {
        public final void onDeviceProvisionedChanged() {
            DeviceProvisionedCoordinator.this.mNotifFilter.invalidateList();
        }
    };
    public final IPackageManager mIPackageManager;
    public final C12571 mNotifFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z;
            boolean z2;
            if (!DeviceProvisionedCoordinator.this.mDeviceProvisionedController.isDeviceProvisioned()) {
                DeviceProvisionedCoordinator deviceProvisionedCoordinator = DeviceProvisionedCoordinator.this;
                Objects.requireNonNull(notificationEntry);
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Objects.requireNonNull(deviceProvisionedCoordinator);
                try {
                    if (deviceProvisionedCoordinator.mIPackageManager.checkUidPermission("android.permission.NOTIFICATION_DURING_SETUP", statusBarNotification.getUid()) == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z || !statusBarNotification.getNotification().extras.getBoolean("android.allowDuringSetup")) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (!z2) {
                        return true;
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return false;
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
        notifPipeline.addPreGroupFilter(this.mNotifFilter);
    }

    public DeviceProvisionedCoordinator(DeviceProvisionedController deviceProvisionedController, IPackageManager iPackageManager) {
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mIPackageManager = iPackageManager;
    }
}
