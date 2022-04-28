package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import com.google.android.systemui.statusbar.notification.voicereplies.C2404x548d1e84;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

public class NotificationLockscreenUserManagerImpl implements Dumpable, NotificationLockscreenUserManager, StatusBarStateController.StateListener {
    public final C11581 mAllUsersReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(intent.getAction()) && NotificationLockscreenUserManagerImpl.this.isCurrentProfile(getSendingUserId())) {
                NotificationLockscreenUserManagerImpl.this.mUsersAllowingPrivateNotifications.clear();
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManagerImpl.this.getEntryManager().updateNotifications("ACTION_DEVICE_POLICY_MANAGER_STATE_CHANGED");
            }
        }
    };
    public boolean mAllowLockscreenRemoteInput;
    public final C11592 mBaseBroadcastReceiver = new BroadcastReceiver() {
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onReceive(android.content.Context r7, android.content.Intent r8) {
            /*
                r6 = this;
                java.lang.String r7 = r8.getAction()
                java.util.Objects.requireNonNull(r7)
                int r0 = r7.hashCode()
                r1 = -1
                switch(r0) {
                    case -1238404651: goto L_0x0048;
                    case -864107122: goto L_0x003d;
                    case -598152660: goto L_0x0032;
                    case 833559602: goto L_0x0027;
                    case 959232034: goto L_0x001c;
                    case 1121780209: goto L_0x0011;
                    default: goto L_0x000f;
                }
            L_0x000f:
                r7 = r1
                goto L_0x0052
            L_0x0011:
                java.lang.String r0 = "android.intent.action.USER_ADDED"
                boolean r7 = r7.equals(r0)
                if (r7 != 0) goto L_0x001a
                goto L_0x000f
            L_0x001a:
                r7 = 5
                goto L_0x0052
            L_0x001c:
                java.lang.String r0 = "android.intent.action.USER_SWITCHED"
                boolean r7 = r7.equals(r0)
                if (r7 != 0) goto L_0x0025
                goto L_0x000f
            L_0x0025:
                r7 = 4
                goto L_0x0052
            L_0x0027:
                java.lang.String r0 = "android.intent.action.USER_UNLOCKED"
                boolean r7 = r7.equals(r0)
                if (r7 != 0) goto L_0x0030
                goto L_0x000f
            L_0x0030:
                r7 = 3
                goto L_0x0052
            L_0x0032:
                java.lang.String r0 = "com.android.systemui.statusbar.work_challenge_unlocked_notification_action"
                boolean r7 = r7.equals(r0)
                if (r7 != 0) goto L_0x003b
                goto L_0x000f
            L_0x003b:
                r7 = 2
                goto L_0x0052
            L_0x003d:
                java.lang.String r0 = "android.intent.action.MANAGED_PROFILE_AVAILABLE"
                boolean r7 = r7.equals(r0)
                if (r7 != 0) goto L_0x0046
                goto L_0x000f
            L_0x0046:
                r7 = 1
                goto L_0x0052
            L_0x0048:
                java.lang.String r0 = "android.intent.action.MANAGED_PROFILE_UNAVAILABLE"
                boolean r7 = r7.equals(r0)
                if (r7 != 0) goto L_0x0051
                goto L_0x000f
            L_0x0051:
                r7 = 0
            L_0x0052:
                switch(r7) {
                    case 0: goto L_0x0112;
                    case 1: goto L_0x0112;
                    case 2: goto L_0x00cf;
                    case 3: goto L_0x00c3;
                    case 4: goto L_0x0057;
                    case 5: goto L_0x0112;
                    default: goto L_0x0055;
                }
            L_0x0055:
                goto L_0x0117
            L_0x0057:
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                java.lang.String r0 = "android.intent.extra.user_handle"
                int r8 = r8.getIntExtra(r0, r1)
                r7.mCurrentUserId = r8
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                r7.updateCurrentProfilesCache()
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = "userId "
                r7.append(r8)
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r8 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                int r8 = r8.mCurrentUserId
                r7.append(r8)
                java.lang.String r8 = " is in the house"
                r7.append(r8)
                java.lang.String r7 = r7.toString()
                java.lang.String r8 = "LockscreenUserManager"
                android.util.Log.v(r8, r7)
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                r7.updateLockscreenNotificationSetting()
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                r7.updatePublicMode()
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                com.android.systemui.statusbar.notification.NotificationEntryManager r7 = r7.getEntryManager()
                java.lang.String r8 = "user switched"
                r7.reapplyFilterAndSort(r8)
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                com.android.systemui.statusbar.NotificationPresenter r8 = r7.mPresenter
                int r7 = r7.mCurrentUserId
                com.android.systemui.statusbar.phone.StatusBarNotificationPresenter r8 = (com.android.systemui.statusbar.phone.StatusBarNotificationPresenter) r8
                r8.onUserSwitched(r7)
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r7 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                java.util.ArrayList r7 = r7.mListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x00af:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x0117
                java.lang.Object r8 = r7.next()
                com.android.systemui.statusbar.NotificationLockscreenUserManager$UserChangedListener r8 = (com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener) r8
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r0 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                int r0 = r0.mCurrentUserId
                r8.onUserChanged(r0)
                goto L_0x00af
            L_0x00c3:
                java.lang.Class<com.android.systemui.recents.OverviewProxyService> r6 = com.android.systemui.recents.OverviewProxyService.class
                java.lang.Object r6 = com.android.systemui.Dependency.get(r6)
                com.android.systemui.recents.OverviewProxyService r6 = (com.android.systemui.recents.OverviewProxyService) r6
                r6.startConnectionToCurrentUser()
                goto L_0x0117
            L_0x00cf:
                java.lang.String r7 = "android.intent.extra.INTENT"
                android.os.Parcelable r7 = r8.getParcelableExtra(r7)
                r1 = r7
                android.content.IntentSender r1 = (android.content.IntentSender) r1
                java.lang.String r7 = "android.intent.extra.INDEX"
                java.lang.String r7 = r8.getStringExtra(r7)
                if (r1 == 0) goto L_0x00eb
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r8 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this     // Catch:{ SendIntentException -> 0x00eb }
                android.content.Context r0 = r8.mContext     // Catch:{ SendIntentException -> 0x00eb }
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r0.startIntentSender(r1, r2, r3, r4, r5)     // Catch:{ SendIntentException -> 0x00eb }
            L_0x00eb:
                if (r7 == 0) goto L_0x0117
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r8 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                dagger.Lazy<com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider> r8 = r8.mVisibilityProviderLazy
                java.lang.Object r8 = r8.get()
                com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r8 = (com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider) r8
                com.android.internal.statusbar.NotificationVisibility r8 = r8.obtain(r7)
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r6 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                com.android.systemui.statusbar.NotificationClickNotifier r6 = r6.mClickNotifier
                java.util.Objects.requireNonNull(r6)
                com.android.internal.statusbar.IStatusBarService r0 = r6.barService     // Catch:{ RemoteException -> 0x0107 }
                r0.onNotificationClick(r7, r8)     // Catch:{ RemoteException -> 0x0107 }
            L_0x0107:
                java.util.concurrent.Executor r8 = r6.mainExecutor
                com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1 r0 = new com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1
                r0.<init>(r6, r7)
                r8.execute(r0)
                goto L_0x0117
            L_0x0112:
                com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl r6 = com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.this
                r6.updateCurrentProfilesCache()
            L_0x0117:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.C11592.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final NotificationClickNotifier mClickNotifier;
    public final Lazy<CommonNotifCollection> mCommonNotifCollectionLazy;
    public final Context mContext;
    public final SparseArray<UserInfo> mCurrentManagedProfiles = new SparseArray<>();
    public final SparseArray<UserInfo> mCurrentProfiles = new SparseArray<>();
    public int mCurrentUserId = 0;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public NotificationEntryManager mEntryManager;
    public KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public ArrayList mKeyguardSuppressors = new ArrayList();
    public final ArrayList mListeners = new ArrayList();
    public final Object mLock = new Object();
    public LockPatternUtils mLockPatternUtils;
    public final SparseBooleanArray mLockscreenPublicMode = new SparseBooleanArray();
    public C11603 mLockscreenSettingsObserver;
    public final Handler mMainHandler;
    public NotificationPresenter mPresenter;
    public C11614 mSettingsObserver;
    public final SparseBooleanArray mShouldHideNotifsLatestResult = new SparseBooleanArray();
    public boolean mShowLockscreenNotifications;
    public int mState = 0;
    public final UserManager mUserManager;
    public final SparseBooleanArray mUsersAllowingNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersAllowingPrivateNotifications = new SparseBooleanArray();
    public final SparseBooleanArray mUsersInLockdownLatestResult = new SparseBooleanArray();
    public final SparseBooleanArray mUsersWithSeparateWorkChallenge = new SparseBooleanArray();
    public final Lazy<NotificationVisibilityProvider> mVisibilityProviderLazy;

    public final boolean isLockscreenPublicMode(int i) {
        if (i == -1) {
            return this.mLockscreenPublicMode.get(this.mCurrentUserId, false);
        }
        return this.mLockscreenPublicMode.get(i, false);
    }

    public final boolean shouldHideNotifications(int i) {
        boolean z;
        int i2;
        if ((!isLockscreenPublicMode(i) || userAllowsNotificationsInPublic(i)) && (i == (i2 = this.mCurrentUserId) || !shouldHideNotifications(i2))) {
            int i3 = i == -1 ? this.mCurrentUserId : i;
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
            Objects.requireNonNull(keyguardUpdateMonitor);
            boolean containsFlag = KeyguardUpdateMonitor.containsFlag(keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(i3), 32);
            this.mUsersInLockdownLatestResult.put(i3, containsFlag);
            if (!containsFlag) {
                z = false;
                this.mShouldHideNotifsLatestResult.put(i, z);
                return z;
            }
        }
        z = true;
        this.mShouldHideNotifsLatestResult.put(i, z);
        return z;
    }

    public final boolean userAllowsPrivateNotificationsInPublic(int i) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (i == -1) {
            return true;
        }
        if (this.mUsersAllowingPrivateNotifications.indexOfKey(i) >= 0) {
            return this.mUsersAllowingPrivateNotifications.get(i);
        }
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_allow_private_notifications", 0, i) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (i == -1 || (this.mDevicePolicyManager.getKeyguardDisabledFeatures((ComponentName) null, i) & 8) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z || !z2) {
            z3 = false;
        }
        this.mUsersAllowingPrivateNotifications.append(i, z3);
        return z3;
    }

    public final void addKeyguardNotificationSuppressor(NotificationLockscreenUserManager.KeyguardNotificationSuppressor keyguardNotificationSuppressor) {
        this.mKeyguardSuppressors.add(keyguardNotificationSuppressor);
    }

    public final void addUserChangedListener(NotificationLockscreenUserManager.UserChangedListener userChangedListener) {
        this.mListeners.add(userChangedListener);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("NotificationLockscreenUserManager state:");
        printWriter.print("  mCurrentUserId=");
        printWriter.println(this.mCurrentUserId);
        printWriter.print("  mShowLockscreenNotifications=");
        printWriter.println(this.mShowLockscreenNotifications);
        printWriter.print("  mAllowLockscreenRemoteInput=");
        printWriter.println(this.mAllowLockscreenRemoteInput);
        printWriter.print("  mCurrentProfiles=");
        synchronized (this.mLock) {
            for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size += -1) {
                printWriter.print("" + this.mCurrentProfiles.valueAt(size).id + " ");
            }
        }
        printWriter.println();
        printWriter.print("  mCurrentManagedProfiles=");
        synchronized (this.mLock) {
            for (int size2 = this.mCurrentManagedProfiles.size() - 1; size2 >= 0; size2 += -1) {
                printWriter.print("" + this.mCurrentManagedProfiles.valueAt(size2).id + " ");
            }
        }
        printWriter.println();
        printWriter.print("  mLockscreenPublicMode=");
        printWriter.println(this.mLockscreenPublicMode);
        printWriter.print("  mUsersWithSeparateWorkChallenge=");
        printWriter.println(this.mUsersWithSeparateWorkChallenge);
        printWriter.print("  mUsersAllowingPrivateNotifications=");
        printWriter.println(this.mUsersAllowingPrivateNotifications);
        printWriter.print("  mUsersAllowingNotifications=");
        printWriter.println(this.mUsersAllowingNotifications);
        printWriter.print("  mUsersInLockdownLatestResult=");
        printWriter.println(this.mUsersInLockdownLatestResult);
        printWriter.print("  mShouldHideNotifsLatestResult=");
        printWriter.println(this.mShouldHideNotifsLatestResult);
    }

    public final NotificationEntryManager getEntryManager() {
        if (this.mEntryManager == null) {
            this.mEntryManager = (NotificationEntryManager) Dependency.get(NotificationEntryManager.class);
        }
        return this.mEntryManager;
    }

    public final boolean isAnyProfilePublicMode() {
        synchronized (this.mLock) {
            for (int size = this.mCurrentProfiles.size() - 1; size >= 0; size--) {
                if (isLockscreenPublicMode(this.mCurrentProfiles.valueAt(size).id)) {
                    return true;
                }
            }
            return false;
        }
    }

    public final boolean isCurrentProfile(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    if (this.mCurrentProfiles.get(i) == null) {
                        z = false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            z = true;
        }
        return z;
    }

    public final boolean needsSeparateWorkChallenge(int i) {
        return this.mUsersWithSeparateWorkChallenge.get(i, false);
    }

    public final void onStateChanged(int i) {
        this.mState = i;
        updatePublicMode();
    }

    public final void removeUserChangedListener(C2404x548d1e84 notificationVoiceReplyController$resetStateOnUserChange$listener$1) {
        this.mListeners.remove(notificationVoiceReplyController$resetStateOnUserChange$listener$1);
    }

    public final void setUpWithPresenter(NotificationPresenter notificationPresenter) {
        this.mPresenter = notificationPresenter;
        this.mLockscreenSettingsObserver = new ContentObserver(this.mMainHandler) {
            public final void onChange(boolean z) {
                NotificationLockscreenUserManagerImpl.this.mUsersAllowingPrivateNotifications.clear();
                NotificationLockscreenUserManagerImpl.this.mUsersAllowingNotifications.clear();
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                NotificationLockscreenUserManagerImpl.this.getEntryManager().updateNotifications("LOCK_SCREEN_SHOW_NOTIFICATIONS, or LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS change");
            }
        };
        this.mSettingsObserver = new ContentObserver(this.mMainHandler) {
            public final void onChange(boolean z) {
                NotificationLockscreenUserManagerImpl.this.updateLockscreenNotificationSetting();
                if (NotificationLockscreenUserManagerImpl.this.mDeviceProvisionedController.isDeviceProvisioned()) {
                    NotificationLockscreenUserManagerImpl.this.getEntryManager().updateNotifications("LOCK_SCREEN_ALLOW_REMOTE_INPUT or ZEN_MODE change");
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_show_notifications"), false, this.mLockscreenSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_allow_private_notifications"), true, this.mLockscreenSettingsObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, this.mSettingsObserver);
        this.mBroadcastDispatcher.registerReceiver(this.mAllUsersReceiver, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), (Executor) null, UserHandle.ALL);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        this.mBroadcastDispatcher.registerReceiver(this.mBaseBroadcastReceiver, intentFilter, (Executor) null, UserHandle.ALL);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.android.systemui.statusbar.work_challenge_unlocked_notification_action");
        this.mContext.registerReceiver(this.mBaseBroadcastReceiver, intentFilter2, "com.android.systemui.permission.SELF", (Handler) null, 2);
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        updateCurrentProfilesCache();
        this.mSettingsObserver.onChange(false);
    }

    public final boolean shouldShowOnKeyguard(NotificationEntry notificationEntry) {
        boolean z;
        if (this.mCommonNotifCollectionLazy.get() == null) {
            Log.wtf("LockscreenUserManager", "mCommonNotifCollectionLazy was null!", new Throwable());
            return false;
        }
        for (int i = 0; i < this.mKeyguardSuppressors.size(); i++) {
            if (((NotificationLockscreenUserManager.KeyguardNotificationSuppressor) this.mKeyguardSuppressors.get(i)).shouldSuppressOnKeyguard(notificationEntry)) {
                return false;
            }
        }
        if (((Boolean) DejankUtils.whitelistIpcs(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda0(this))).booleanValue()) {
            Objects.requireNonNull(notificationEntry);
            int i2 = notificationEntry.mBucket;
            if (i2 == 1 || (i2 != 6 && notificationEntry.getImportance() >= 3)) {
                z = true;
            } else {
                z = false;
            }
        } else {
            Objects.requireNonNull(notificationEntry);
            z = !notificationEntry.mRanking.isAmbient();
        }
        if (!this.mShowLockscreenNotifications || !z) {
            return false;
        }
        return true;
    }

    public final void updateCurrentProfilesCache() {
        synchronized (this.mLock) {
            this.mCurrentProfiles.clear();
            this.mCurrentManagedProfiles.clear();
            UserManager userManager = this.mUserManager;
            if (userManager != null) {
                for (UserInfo userInfo : userManager.getProfiles(this.mCurrentUserId)) {
                    this.mCurrentProfiles.put(userInfo.id, userInfo);
                    if ("android.os.usertype.profile.MANAGED".equals(userInfo.userType)) {
                        this.mCurrentManagedProfiles.put(userInfo.id, userInfo);
                    }
                }
            }
        }
        this.mMainHandler.post(new WifiEntry$$ExternalSyntheticLambda2(this, 4));
    }

    public final void updateLockscreenNotificationSetting() {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_show_notifications", 1, this.mCurrentUserId) != 0) {
            z = true;
        } else {
            z = false;
        }
        if ((this.mDevicePolicyManager.getKeyguardDisabledFeatures((ComponentName) null, this.mCurrentUserId) & 4) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z || !z2) {
            z3 = false;
        }
        this.mShowLockscreenNotifications = z3;
        this.mAllowLockscreenRemoteInput = false;
    }

    public void updatePublicMode() {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.mState != 0 || this.mKeyguardStateController.isShowing()) {
            z = true;
        } else {
            z = false;
        }
        if (!z || !this.mKeyguardStateController.isMethodSecure()) {
            z2 = false;
        } else {
            z2 = true;
        }
        SparseArray<UserInfo> sparseArray = this.mCurrentProfiles;
        this.mUsersWithSeparateWorkChallenge.clear();
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            int i = sparseArray.valueAt(size).id;
            boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new NotificationLockscreenUserManagerImpl$$ExternalSyntheticLambda1(this, i))).booleanValue();
            if (z2 || i == this.mCurrentUserId || !booleanValue || !this.mLockPatternUtils.isSecure(i)) {
                z3 = z2;
            } else if (z || this.mKeyguardManager.isDeviceLocked(i)) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.mLockscreenPublicMode.put(i, z3);
            this.mUsersWithSeparateWorkChallenge.put(i, booleanValue);
        }
        getEntryManager().updateNotifications("NotificationLockscreenUserManager.updatePublicMode");
    }

    public NotificationLockscreenUserManagerImpl(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, Lazy<NotificationVisibilityProvider> lazy, Lazy<CommonNotifCollection> lazy2, NotificationClickNotifier notificationClickNotifier, KeyguardManager keyguardManager, StatusBarStateController statusBarStateController, Handler handler, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, DumpManager dumpManager) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mUserManager = userManager;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mVisibilityProviderLazy = lazy;
        this.mCommonNotifCollectionLazy = lazy2;
        this.mClickNotifier = notificationClickNotifier;
        statusBarStateController.addCallback(this);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mKeyguardManager = keyguardManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mKeyguardStateController = keyguardStateController;
        dumpManager.registerDumpable(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean needsRedaction(com.android.systemui.statusbar.notification.collection.NotificationEntry r6) {
        /*
            r5 = this;
            java.util.Objects.requireNonNull(r6)
            android.service.notification.StatusBarNotification r0 = r6.mSbn
            int r0 = r0.getUserId()
            int r1 = r5.mCurrentUserId
            boolean r1 = r5.userAllowsPrivateNotificationsInPublic(r1)
            r2 = 1
            r1 = r1 ^ r2
            android.util.SparseArray<android.content.pm.UserInfo> r3 = r5.mCurrentManagedProfiles
            boolean r3 = r3.contains(r0)
            boolean r0 = r5.userAllowsPrivateNotificationsInPublic(r0)
            r0 = r0 ^ r2
            r4 = 0
            if (r3 != 0) goto L_0x0021
            if (r1 != 0) goto L_0x0023
        L_0x0021:
            if (r0 == 0) goto L_0x0025
        L_0x0023:
            r0 = r2
            goto L_0x0026
        L_0x0025:
            r0 = r4
        L_0x0026:
            android.service.notification.StatusBarNotification r1 = r6.mSbn
            android.app.Notification r1 = r1.getNotification()
            int r1 = r1.visibility
            if (r1 != 0) goto L_0x0032
            r1 = r2
            goto L_0x0033
        L_0x0032:
            r1 = r4
        L_0x0033:
            android.service.notification.StatusBarNotification r6 = r6.mSbn
            java.lang.String r6 = r6.getKey()
            dagger.Lazy<com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection> r3 = r5.mCommonNotifCollectionLazy
            java.lang.Object r3 = r3.get()
            if (r3 != 0) goto L_0x004e
            java.lang.Throwable r5 = new java.lang.Throwable
            r5.<init>()
            java.lang.String r6 = "LockscreenUserManager"
            java.lang.String r3 = "mEntryManager was null!"
            android.util.Log.wtf(r6, r3, r5)
            goto L_0x0064
        L_0x004e:
            dagger.Lazy<com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection> r5 = r5.mCommonNotifCollectionLazy
            java.lang.Object r5 = r5.get()
            com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection r5 = (com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection) r5
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getEntry(r6)
            if (r5 == 0) goto L_0x0066
            android.service.notification.NotificationListenerService$Ranking r5 = r5.mRanking
            int r5 = r5.getLockscreenVisibilityOverride()
            if (r5 != 0) goto L_0x0066
        L_0x0064:
            r5 = r2
            goto L_0x0067
        L_0x0066:
            r5 = r4
        L_0x0067:
            if (r5 != 0) goto L_0x006f
            if (r1 == 0) goto L_0x006e
            if (r0 == 0) goto L_0x006e
            goto L_0x006f
        L_0x006e:
            r2 = r4
        L_0x006f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl.needsRedaction(com.android.systemui.statusbar.notification.collection.NotificationEntry):boolean");
    }

    public final boolean userAllowsNotificationsInPublic(int i) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (isCurrentProfile(i) && i != this.mCurrentUserId) {
            return true;
        }
        if (this.mUsersAllowingNotifications.indexOfKey(i) >= 0) {
            return this.mUsersAllowingNotifications.get(i);
        }
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "lock_screen_show_notifications", 0, i) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (i == -1 || (this.mDevicePolicyManager.getKeyguardDisabledFeatures((ComponentName) null, i) & 4) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean privateNotificationsAllowed = this.mKeyguardManager.getPrivateNotificationsAllowed();
        if (!z || !z2 || !privateNotificationsAllowed) {
            z3 = false;
        }
        this.mUsersAllowingNotifications.append(i, z3);
        return z3;
    }

    public final boolean shouldHideNotifications(String str) {
        if (this.mCommonNotifCollectionLazy.get() == null) {
            Log.wtf("LockscreenUserManager", "mCommonNotifCollectionLazy was null!", new Throwable());
            return true;
        }
        NotificationEntry entry = this.mCommonNotifCollectionLazy.get().getEntry(str);
        if (!isLockscreenPublicMode(this.mCurrentUserId) || entry == null || entry.mRanking.getLockscreenVisibilityOverride() != -1) {
            return false;
        }
        return true;
    }

    public final int getCurrentUserId() {
        return this.mCurrentUserId;
    }

    public final boolean shouldAllowLockscreenRemoteInput() {
        return this.mAllowLockscreenRemoteInput;
    }

    public final boolean shouldShowLockscreenNotifications() {
        return this.mShowLockscreenNotifications;
    }
}
