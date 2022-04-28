package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.UserInfo;
import android.util.SparseArray;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import java.util.Objects;

public final class HideNotifsForOtherUsersCoordinator implements Coordinator {
    public final C12621 mFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            NotificationLockscreenUserManager notificationLockscreenUserManager = HideNotifsForOtherUsersCoordinator.this.mLockscreenUserManager;
            Objects.requireNonNull(notificationEntry);
            return !notificationLockscreenUserManager.isCurrentProfile(notificationEntry.mSbn.getUser().getIdentifier());
        }
    };
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final SharedCoordinatorLogger mLogger;
    public final C12632 mUserChangedListener = new NotificationLockscreenUserManager.UserChangedListener() {
        public final void onCurrentProfilesChanged(SparseArray<UserInfo> sparseArray) {
            HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator = HideNotifsForOtherUsersCoordinator.this;
            SharedCoordinatorLogger sharedCoordinatorLogger = hideNotifsForOtherUsersCoordinator.mLogger;
            int currentUserId = hideNotifsForOtherUsersCoordinator.mLockscreenUserManager.getCurrentUserId();
            Objects.requireNonNull(HideNotifsForOtherUsersCoordinator.this);
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int i = 0; i < sparseArray.size(); i++) {
                sb.append(sparseArray.keyAt(i));
                if (i < sparseArray.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("}");
            String sb2 = sb.toString();
            Objects.requireNonNull(sharedCoordinatorLogger);
            LogBuffer logBuffer = sharedCoordinatorLogger.buffer;
            LogLevel logLevel = LogLevel.INFO;
            SharedCoordinatorLogger$logUserOrProfileChanged$2 sharedCoordinatorLogger$logUserOrProfileChanged$2 = SharedCoordinatorLogger$logUserOrProfileChanged$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("NotCurrentUserFilter", logLevel, sharedCoordinatorLogger$logUserOrProfileChanged$2);
                obtain.int1 = currentUserId;
                obtain.str1 = sb2;
                logBuffer.push(obtain);
            }
            HideNotifsForOtherUsersCoordinator.this.mFilter.invalidateList();
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mFilter);
        this.mLockscreenUserManager.addUserChangedListener(this.mUserChangedListener);
    }

    public HideNotifsForOtherUsersCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager, SharedCoordinatorLogger sharedCoordinatorLogger) {
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mLogger = sharedCoordinatorLogger;
    }
}
