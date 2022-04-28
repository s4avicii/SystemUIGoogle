package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;

public final class KeyguardCoordinator implements Coordinator {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public boolean mHideSilentNotificationsOnLockscreen;
    public final HighPriorityProvider mHighPriorityProvider;
    public final C12674 mKeyguardCallback = new KeyguardStateController.Callback() {
        public final void onKeyguardShowingChanged() {
            KeyguardCoordinator.m232$$Nest$minvalidateListFromFilter(KeyguardCoordinator.this);
        }

        public final void onUnlockedChanged() {
            KeyguardCoordinator.m232$$Nest$minvalidateListFromFilter(KeyguardCoordinator.this);
        }
    };
    public final KeyguardStateController mKeyguardStateController;
    public final C12696 mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() {
        public final void onStrongAuthStateChanged(int i) {
            KeyguardCoordinator.m232$$Nest$minvalidateListFromFilter(KeyguardCoordinator.this);
        }
    };
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final Handler mMainHandler;
    public final C12641 mNotifFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            int i;
            Objects.requireNonNull(notificationEntry);
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if (!KeyguardCoordinator.this.mKeyguardStateController.isShowing()) {
                return false;
            }
            if (!KeyguardCoordinator.this.mLockscreenUserManager.shouldShowLockscreenNotifications()) {
                return true;
            }
            int currentUserId = KeyguardCoordinator.this.mLockscreenUserManager.getCurrentUserId();
            if (statusBarNotification.getUser().getIdentifier() == -1) {
                i = currentUserId;
            } else {
                i = statusBarNotification.getUser().getIdentifier();
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardCoordinator.this.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            if (!KeyguardUpdateMonitor.containsFlag(keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(currentUserId), 32)) {
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardCoordinator.this.mKeyguardUpdateMonitor;
                Objects.requireNonNull(keyguardUpdateMonitor2);
                if (!KeyguardUpdateMonitor.containsFlag(keyguardUpdateMonitor2.mStrongAuthTracker.getStrongAuthForUser(i), 32) && ((!KeyguardCoordinator.this.mLockscreenUserManager.isLockscreenPublicMode(currentUserId) && !KeyguardCoordinator.this.mLockscreenUserManager.isLockscreenPublicMode(i)) || (notificationEntry.mRanking.getLockscreenVisibilityOverride() != -1 && KeyguardCoordinator.this.mLockscreenUserManager.userAllowsNotificationsInPublic(currentUserId) && KeyguardCoordinator.this.mLockscreenUserManager.userAllowsNotificationsInPublic(i)))) {
                    if (notificationEntry.getParent() != null) {
                        if (KeyguardCoordinator.m233$$Nest$mpriorityExceedsLockscreenShowingThreshold(KeyguardCoordinator.this, notificationEntry.getParent())) {
                            return false;
                        }
                    }
                    return !KeyguardCoordinator.m233$$Nest$mpriorityExceedsLockscreenShowingThreshold(KeyguardCoordinator.this, notificationEntry);
                }
            }
            return true;
        }
    };
    public final SectionHeaderVisibilityProvider mSectionHeaderVisibilityProvider;
    public final StatusBarStateController mStatusBarStateController;
    public final C12685 mStatusBarStateListener = new StatusBarStateController.StateListener() {
        public final void onStateChanged(int i) {
            KeyguardCoordinator.m232$$Nest$minvalidateListFromFilter(KeyguardCoordinator.this);
        }
    };

    /* renamed from: -$$Nest$mpriorityExceedsLockscreenShowingThreshold  reason: not valid java name */
    public static boolean m233$$Nest$mpriorityExceedsLockscreenShowingThreshold(KeyguardCoordinator keyguardCoordinator, ListEntry listEntry) {
        if (listEntry == null) {
            Objects.requireNonNull(keyguardCoordinator);
            return false;
        } else if (keyguardCoordinator.mHideSilentNotificationsOnLockscreen) {
            return keyguardCoordinator.mHighPriorityProvider.isHighPriority(listEntry);
        } else {
            if (listEntry.getRepresentativeEntry() == null) {
                return false;
            }
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            Objects.requireNonNull(representativeEntry);
            return !representativeEntry.mRanking.isAmbient();
        }
    }

    public final void attach(NotifPipeline notifPipeline) {
        boolean z;
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "lock_screen_show_silent_notifications", 1) == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mHideSilentNotificationsOnLockscreen = z;
        this.mKeyguardStateController.addCallback(this.mKeyguardCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateCallback);
        C12652 r0 = new ContentObserver(this.mMainHandler) {
            public final void onChange(boolean z, Uri uri) {
                if (uri.equals(Settings.Secure.getUriFor("lock_screen_show_silent_notifications"))) {
                    KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
                    Objects.requireNonNull(keyguardCoordinator);
                    boolean z2 = true;
                    if (Settings.Secure.getInt(keyguardCoordinator.mContext.getContentResolver(), "lock_screen_show_silent_notifications", 1) != 0) {
                        z2 = false;
                    }
                    keyguardCoordinator.mHideSilentNotificationsOnLockscreen = z2;
                }
                if (KeyguardCoordinator.this.mKeyguardStateController.isShowing()) {
                    KeyguardCoordinator keyguardCoordinator2 = KeyguardCoordinator.this;
                    uri.toString();
                    KeyguardCoordinator.m232$$Nest$minvalidateListFromFilter(keyguardCoordinator2);
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_show_notifications"), false, r0, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_allow_private_notifications"), true, r0, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, r0);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_show_silent_notifications"), false, r0, -1);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mBroadcastDispatcher.registerReceiver(new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if (KeyguardCoordinator.this.mKeyguardStateController.isShowing()) {
                    KeyguardCoordinator keyguardCoordinator = KeyguardCoordinator.this;
                    intent.getAction();
                    KeyguardCoordinator.m232$$Nest$minvalidateListFromFilter(keyguardCoordinator);
                }
            }
        }, new IntentFilter("android.intent.action.USER_SWITCHED"));
        notifPipeline.addFinalizeFilter(this.mNotifFilter);
        updateSectionHeadersVisibility();
    }

    public final void updateSectionHeadersVisibility() {
        boolean z;
        boolean z2 = false;
        if (this.mStatusBarStateController.getState() == 1) {
            z = true;
        } else {
            z = false;
        }
        SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider = this.mSectionHeaderVisibilityProvider;
        Objects.requireNonNull(sectionHeaderVisibilityProvider);
        boolean z3 = sectionHeaderVisibilityProvider.neverShowSectionHeaders;
        if (!z && !z3) {
            z2 = true;
        }
        SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider2 = this.mSectionHeaderVisibilityProvider;
        Objects.requireNonNull(sectionHeaderVisibilityProvider2);
        sectionHeaderVisibilityProvider2.sectionHeadersVisible = z2;
    }

    /* renamed from: -$$Nest$minvalidateListFromFilter  reason: not valid java name */
    public static void m232$$Nest$minvalidateListFromFilter(KeyguardCoordinator keyguardCoordinator) {
        Objects.requireNonNull(keyguardCoordinator);
        keyguardCoordinator.updateSectionHeadersVisibility();
        keyguardCoordinator.mNotifFilter.invalidateList();
    }

    public KeyguardCoordinator(Context context, Handler handler, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, BroadcastDispatcher broadcastDispatcher, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, HighPriorityProvider highPriorityProvider, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mKeyguardStateController = keyguardStateController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mSectionHeaderVisibilityProvider = sectionHeaderVisibilityProvider;
    }
}
