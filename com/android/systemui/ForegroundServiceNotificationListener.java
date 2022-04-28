package com.android.systemui;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Objects;

public final class ForegroundServiceNotificationListener {
    public final Context mContext;
    public final ForegroundServiceController mForegroundServiceController;

    public ForegroundServiceNotificationListener(Context context, ForegroundServiceController foregroundServiceController, NotificationEntryManager notificationEntryManager, NotifPipeline notifPipeline) {
        this.mContext = context;
        this.mForegroundServiceController = foregroundServiceController;
        notificationEntryManager.addNotificationEntryListener(new NotificationEntryListener() {
            public final void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
                ForegroundServiceNotificationListener foregroundServiceNotificationListener = ForegroundServiceNotificationListener.this;
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Objects.requireNonNull(foregroundServiceNotificationListener);
                foregroundServiceNotificationListener.mForegroundServiceController.updateUserState(statusBarNotification.getUserId(), new ForegroundServiceController.UserStateUpdateCallback(statusBarNotification) {
                    public final /* synthetic */ StatusBarNotification val$sbn;

                    {
                        this.val$sbn = r2;
                    }

                    public final boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState) {
                        boolean z;
                        ForegroundServiceController foregroundServiceController = ForegroundServiceNotificationListener.this.mForegroundServiceController;
                        StatusBarNotification statusBarNotification = this.val$sbn;
                        Objects.requireNonNull(foregroundServiceController);
                        if (ForegroundServiceController.isDisclosureNotification(statusBarNotification)) {
                            foregroundServicesUserState.mRunning = null;
                            foregroundServicesUserState.mServiceStartTime = 0;
                            return true;
                        }
                        String packageName = this.val$sbn.getPackageName();
                        String key = this.val$sbn.getKey();
                        ArrayMap<String, ArraySet<String>> arrayMap = foregroundServicesUserState.mImportantNotifications;
                        ArraySet arraySet = arrayMap.get(packageName);
                        boolean z2 = false;
                        if (arraySet == null) {
                            z = false;
                        } else {
                            z = arraySet.remove(key);
                            if (arraySet.size() == 0) {
                                arrayMap.remove(packageName);
                            }
                        }
                        boolean z3 = z | false;
                        ArrayMap<String, ArraySet<String>> arrayMap2 = foregroundServicesUserState.mStandardLayoutNotifications;
                        ArraySet arraySet2 = arrayMap2.get(packageName);
                        if (arraySet2 != null) {
                            z2 = arraySet2.remove(key);
                            if (arraySet2.size() == 0) {
                                arrayMap2.remove(packageName);
                            }
                        }
                        return z2 | z3;
                    }
                }, false);
            }

            public final void onPendingEntryAdded(NotificationEntry notificationEntry) {
                ForegroundServiceNotificationListener foregroundServiceNotificationListener = ForegroundServiceNotificationListener.this;
                int importance = notificationEntry.getImportance();
                Objects.requireNonNull(foregroundServiceNotificationListener);
                foregroundServiceNotificationListener.updateNotification(notificationEntry, importance);
            }

            public final void onPreEntryUpdated(NotificationEntry notificationEntry) {
                ForegroundServiceNotificationListener.this.updateNotification(notificationEntry, notificationEntry.getImportance());
            }
        });
        notifPipeline.addCollectionListener(new NotifCollectionListener() {
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                ForegroundServiceNotificationListener foregroundServiceNotificationListener = ForegroundServiceNotificationListener.this;
                int importance = notificationEntry.getImportance();
                Objects.requireNonNull(foregroundServiceNotificationListener);
                foregroundServiceNotificationListener.updateNotification(notificationEntry, importance);
            }

            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                ForegroundServiceNotificationListener foregroundServiceNotificationListener = ForegroundServiceNotificationListener.this;
                Objects.requireNonNull(notificationEntry);
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Objects.requireNonNull(foregroundServiceNotificationListener);
                foregroundServiceNotificationListener.mForegroundServiceController.updateUserState(statusBarNotification.getUserId(), new ForegroundServiceController.UserStateUpdateCallback(statusBarNotification) {
                    public final /* synthetic */ StatusBarNotification val$sbn;

                    {
                        this.val$sbn = r2;
                    }

                    public final boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState) {
                        boolean z;
                        ForegroundServiceController foregroundServiceController = ForegroundServiceNotificationListener.this.mForegroundServiceController;
                        StatusBarNotification statusBarNotification = this.val$sbn;
                        Objects.requireNonNull(foregroundServiceController);
                        if (ForegroundServiceController.isDisclosureNotification(statusBarNotification)) {
                            foregroundServicesUserState.mRunning = null;
                            foregroundServicesUserState.mServiceStartTime = 0;
                            return true;
                        }
                        String packageName = this.val$sbn.getPackageName();
                        String key = this.val$sbn.getKey();
                        ArrayMap<String, ArraySet<String>> arrayMap = foregroundServicesUserState.mImportantNotifications;
                        ArraySet arraySet = arrayMap.get(packageName);
                        boolean z2 = false;
                        if (arraySet == null) {
                            z = false;
                        } else {
                            z = arraySet.remove(key);
                            if (arraySet.size() == 0) {
                                arrayMap.remove(packageName);
                            }
                        }
                        boolean z3 = z | false;
                        ArrayMap<String, ArraySet<String>> arrayMap2 = foregroundServicesUserState.mStandardLayoutNotifications;
                        ArraySet arraySet2 = arrayMap2.get(packageName);
                        if (arraySet2 != null) {
                            z2 = arraySet2.remove(key);
                            if (arraySet2.size() == 0) {
                                arrayMap2.remove(packageName);
                            }
                        }
                        return z2 | z3;
                    }
                }, false);
            }

            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                ForegroundServiceNotificationListener.this.updateNotification(notificationEntry, notificationEntry.getImportance());
            }
        });
    }

    public final void updateNotification(NotificationEntry notificationEntry, int i) {
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        this.mForegroundServiceController.updateUserState(statusBarNotification.getUserId(), new ForegroundServiceNotificationListener$$ExternalSyntheticLambda0(this, statusBarNotification, i), true);
    }
}
