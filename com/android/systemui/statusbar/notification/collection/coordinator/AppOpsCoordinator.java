package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;

public final class AppOpsCoordinator implements Coordinator {
    public final AppOpsController mAppOpsController;
    public final ForegroundServiceController mForegroundServiceController;
    public final DelayableExecutor mMainExecutor;
    public final C12501 mNotifFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            Objects.requireNonNull(notificationEntry);
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Objects.requireNonNull(AppOpsCoordinator.this.mForegroundServiceController);
            if (!ForegroundServiceController.isDisclosureNotification(statusBarNotification) || AppOpsCoordinator.this.mForegroundServiceController.isDisclosureNeededForUser(statusBarNotification.getUser().getIdentifier())) {
                return false;
            }
            return true;
        }
    };
    public final C12512 mNotifSectioner = new NotifSectioner() {
        public final boolean isInSection(ListEntry listEntry) {
            boolean z;
            boolean z2;
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                return false;
            }
            Notification notification = representativeEntry.mSbn.getNotification();
            if (!notification.isForegroundService() || !notification.isColorized() || representativeEntry.getImportance() <= 1) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                Notification notification2 = representativeEntry.mSbn.getNotification();
                if (representativeEntry.getImportance() <= 1 || !notification2.isStyle(Notification.CallStyle.class)) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (!z2) {
                    return false;
                }
            }
            return true;
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mNotifFilter);
    }

    public AppOpsCoordinator(ForegroundServiceController foregroundServiceController, AppOpsController appOpsController, DelayableExecutor delayableExecutor) {
        this.mForegroundServiceController = foregroundServiceController;
        this.mAppOpsController = appOpsController;
        this.mMainExecutor = delayableExecutor;
    }
}
