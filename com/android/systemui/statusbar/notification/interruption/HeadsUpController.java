package com.android.systemui.statusbar.notification.interruption;

import android.app.Notification;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.util.Objects;

public final class HeadsUpController {
    public C12941 mCollectionListener = new NotifCollectionListener() {
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            if (HeadsUpController.this.mInterruptStateProvider.shouldHeadsUp(notificationEntry)) {
                HeadsUpController headsUpController = HeadsUpController.this;
                headsUpController.mHeadsUpViewBinder.bindHeadsUpView(notificationEntry, new HeadsUpController$1$$ExternalSyntheticLambda0(headsUpController));
            }
        }

        public final void onEntryCleanUp(NotificationEntry notificationEntry) {
            HeadsUpController.this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
        }

        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            boolean z;
            HeadsUpController headsUpController = HeadsUpController.this;
            Objects.requireNonNull(headsUpController);
            Objects.requireNonNull(notificationEntry);
            String str = notificationEntry.mKey;
            if (headsUpController.mHeadsUpManager.isAlerting(str)) {
                if (!headsUpController.mRemoteInputManager.isSpinning(str) || NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                    VisualStabilityManager visualStabilityManager = headsUpController.mVisualStabilityManager;
                    Objects.requireNonNull(visualStabilityManager);
                    if (visualStabilityManager.mReorderingAllowed) {
                        z = false;
                        headsUpController.mHeadsUpManager.removeNotification(str, z);
                    }
                }
                z = true;
                headsUpController.mHeadsUpManager.removeNotification(str, z);
            }
        }

        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            boolean z;
            HeadsUpController headsUpController = HeadsUpController.this;
            Objects.requireNonNull(headsUpController);
            Objects.requireNonNull(notificationEntry);
            Notification notification = notificationEntry.mSbn.getNotification();
            if (!notificationEntry.interruption || (notification.flags & 8) == 0) {
                z = true;
            } else {
                z = false;
            }
            boolean shouldHeadsUp = headsUpController.mInterruptStateProvider.shouldHeadsUp(notificationEntry);
            if (headsUpController.mHeadsUpManager.isAlerting(notificationEntry.mKey)) {
                if (shouldHeadsUp) {
                    headsUpController.mHeadsUpManager.updateNotification(notificationEntry.mKey, z);
                } else {
                    headsUpController.mHeadsUpManager.removeNotification(notificationEntry.mKey, false);
                }
            } else if (shouldHeadsUp && z) {
                HeadsUpViewBinder headsUpViewBinder = headsUpController.mHeadsUpViewBinder;
                HeadsUpManager headsUpManager = headsUpController.mHeadsUpManager;
                Objects.requireNonNull(headsUpManager);
                headsUpViewBinder.bindHeadsUpView(notificationEntry, new HeadsUpController$$ExternalSyntheticLambda0(headsUpManager));
            }
        }
    };
    public final HeadsUpManager mHeadsUpManager;
    public final HeadsUpViewBinder mHeadsUpViewBinder;
    public final NotificationInterruptStateProvider mInterruptStateProvider;
    public final NotificationListener mNotificationListener;
    public C12952 mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() {
        public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            if (!z) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                Objects.requireNonNull(expandableNotificationRow);
                if (!expandableNotificationRow.mRemoved) {
                    HeadsUpController.this.mHeadsUpViewBinder.unbindHeadsUpView(notificationEntry);
                }
            }
        }
    };
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final StatusBarStateController mStatusBarStateController;
    public final VisualStabilityManager mVisualStabilityManager;

    public HeadsUpController(HeadsUpViewBinder headsUpViewBinder, NotificationInterruptStateProvider notificationInterruptStateProvider, HeadsUpManager headsUpManager, NotificationRemoteInputManager notificationRemoteInputManager, StatusBarStateController statusBarStateController, VisualStabilityManager visualStabilityManager, NotificationListener notificationListener) {
        this.mHeadsUpViewBinder = headsUpViewBinder;
        this.mHeadsUpManager = headsUpManager;
        this.mInterruptStateProvider = notificationInterruptStateProvider;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mVisualStabilityManager = visualStabilityManager;
        this.mNotificationListener = notificationListener;
    }
}
