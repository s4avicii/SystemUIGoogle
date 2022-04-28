package com.google.android.systemui;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.google.android.systemui.smartspace.SmartSpaceController;
import dagger.Lazy;

public final class NotificationLockscreenUserManagerGoogle extends NotificationLockscreenUserManagerImpl {
    public final Lazy<KeyguardBypassController> mKeyguardBypassControllerLazy;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardStateController.Callback mKeyguardVisibilityCallback;
    public final SmartSpaceController mSmartSpaceController;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationLockscreenUserManagerGoogle(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, Lazy<NotificationVisibilityProvider> lazy, Lazy<CommonNotifCollection> lazy2, NotificationClickNotifier notificationClickNotifier, KeyguardManager keyguardManager, StatusBarStateController statusBarStateController, Handler handler, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, Lazy<KeyguardBypassController> lazy3, SmartSpaceController smartSpaceController, DumpManager dumpManager) {
        super(context, broadcastDispatcher, devicePolicyManager, userManager, lazy, lazy2, notificationClickNotifier, keyguardManager, statusBarStateController, handler, deviceProvisionedController, keyguardStateController, dumpManager);
        KeyguardStateController keyguardStateController2 = keyguardStateController;
        C21571 r0 = new KeyguardStateController.Callback() {
            public final void onKeyguardShowingChanged() {
                NotificationLockscreenUserManagerGoogle.this.updateSmartSpaceVisibilitySettings();
            }
        };
        this.mKeyguardVisibilityCallback = r0;
        this.mKeyguardBypassControllerLazy = lazy3;
        this.mSmartSpaceController = smartSpaceController;
        this.mKeyguardStateController = keyguardStateController2;
        keyguardStateController2.addCallback(r0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00e4, code lost:
        if (r3.mCard.isWorkProfile != false) goto L_0x00e8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00d6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateSmartSpaceVisibilitySettings() {
        /*
            r7 = this;
            int r0 = r7.mCurrentUserId
            boolean r0 = r7.userAllowsPrivateNotificationsInPublic(r0)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x001a
            boolean r0 = r7.isAnyProfilePublicMode()
            if (r0 != 0) goto L_0x0018
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r7.mKeyguardStateController
            boolean r0 = r0.isShowing()
            if (r0 != 0) goto L_0x001a
        L_0x0018:
            r0 = r1
            goto L_0x001b
        L_0x001a:
            r0 = r2
        L_0x001b:
            java.lang.Object r3 = r7.mLock
            monitor-enter(r3)
            android.util.SparseArray<android.content.pm.UserInfo> r4 = r7.mCurrentManagedProfiles     // Catch:{ all -> 0x011f }
            int r4 = r4.size()     // Catch:{ all -> 0x011f }
            int r4 = r4 - r1
        L_0x0025:
            if (r4 < 0) goto L_0x003d
            android.util.SparseArray<android.content.pm.UserInfo> r5 = r7.mCurrentManagedProfiles     // Catch:{ all -> 0x011f }
            java.lang.Object r5 = r5.valueAt(r4)     // Catch:{ all -> 0x011f }
            android.content.pm.UserInfo r5 = (android.content.pm.UserInfo) r5     // Catch:{ all -> 0x011f }
            int r5 = r5.id     // Catch:{ all -> 0x011f }
            boolean r5 = r7.userAllowsPrivateNotificationsInPublic(r5)     // Catch:{ all -> 0x011f }
            if (r5 != 0) goto L_0x003a
            monitor-exit(r3)     // Catch:{ all -> 0x011f }
            r3 = r2
            goto L_0x003f
        L_0x003a:
            int r4 = r4 + -1
            goto L_0x0025
        L_0x003d:
            monitor-exit(r3)     // Catch:{ all -> 0x011f }
            r3 = r1
        L_0x003f:
            r3 = r3 ^ r1
            dagger.Lazy<com.android.systemui.statusbar.phone.KeyguardBypassController> r4 = r7.mKeyguardBypassControllerLazy
            java.lang.Object r4 = r4.get()
            com.android.systemui.statusbar.phone.KeyguardBypassController r4 = (com.android.systemui.statusbar.phone.KeyguardBypassController) r4
            boolean r4 = r4.getBypassEnabled()
            if (r4 == 0) goto L_0x004f
            goto L_0x0085
        L_0x004f:
            if (r3 == 0) goto L_0x0084
            java.lang.Object r3 = r7.mLock
            monitor-enter(r3)
            android.util.SparseArray<android.content.pm.UserInfo> r4 = r7.mCurrentManagedProfiles     // Catch:{ all -> 0x0081 }
            int r4 = r4.size()     // Catch:{ all -> 0x0081 }
            int r4 = r4 - r1
        L_0x005b:
            if (r4 < 0) goto L_0x0073
            android.util.SparseArray<android.content.pm.UserInfo> r5 = r7.mCurrentManagedProfiles     // Catch:{ all -> 0x0081 }
            java.lang.Object r5 = r5.valueAt(r4)     // Catch:{ all -> 0x0081 }
            android.content.pm.UserInfo r5 = (android.content.pm.UserInfo) r5     // Catch:{ all -> 0x0081 }
            int r5 = r5.id     // Catch:{ all -> 0x0081 }
            boolean r5 = r7.isLockscreenPublicMode(r5)     // Catch:{ all -> 0x0081 }
            if (r5 == 0) goto L_0x0070
            monitor-exit(r3)     // Catch:{ all -> 0x0081 }
            r3 = r1
            goto L_0x0075
        L_0x0070:
            int r4 = r4 + -1
            goto L_0x005b
        L_0x0073:
            monitor-exit(r3)     // Catch:{ all -> 0x0081 }
            r3 = r2
        L_0x0075:
            if (r3 != 0) goto L_0x007f
            com.android.systemui.statusbar.policy.KeyguardStateController r3 = r7.mKeyguardStateController
            boolean r3 = r3.isShowing()
            if (r3 != 0) goto L_0x0084
        L_0x007f:
            r3 = r1
            goto L_0x0085
        L_0x0081:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0081 }
            throw r7
        L_0x0084:
            r3 = r2
        L_0x0085:
            com.google.android.systemui.smartspace.SmartSpaceController r7 = r7.mSmartSpaceController
            java.util.Objects.requireNonNull(r7)
            boolean r4 = r7.mHidePrivateData
            if (r4 != r0) goto L_0x0094
            boolean r4 = r7.mHideWorkData
            if (r4 != r3) goto L_0x0094
            goto L_0x011e
        L_0x0094:
            r7.mHidePrivateData = r0
            r7.mHideWorkData = r3
            java.util.ArrayList r4 = new java.util.ArrayList
            java.util.ArrayList<com.google.android.systemui.smartspace.SmartSpaceUpdateListener> r5 = r7.mListeners
            r4.<init>(r5)
            r5 = r2
        L_0x00a0:
            int r6 = r4.size()
            if (r5 >= r6) goto L_0x00b2
            java.lang.Object r6 = r4.get(r5)
            com.google.android.systemui.smartspace.SmartSpaceUpdateListener r6 = (com.google.android.systemui.smartspace.SmartSpaceUpdateListener) r6
            r6.onSensitiveModeChanged(r0, r3)
            int r5 = r5 + 1
            goto L_0x00a0
        L_0x00b2:
            com.google.android.systemui.smartspace.SmartSpaceData r0 = r7.mData
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.smartspace.SmartSpaceCard r0 = r0.mCurrentCard
            if (r0 == 0) goto L_0x011e
            boolean r0 = r7.mHidePrivateData
            if (r0 == 0) goto L_0x00d1
            com.google.android.systemui.smartspace.SmartSpaceData r0 = r7.mData
            java.util.Objects.requireNonNull(r0)
            com.google.android.systemui.smartspace.SmartSpaceCard r0 = r0.mCurrentCard
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard r0 = r0.mCard
            boolean r0 = r0.isWorkProfile
            if (r0 != 0) goto L_0x00d1
            r0 = r1
            goto L_0x00d2
        L_0x00d1:
            r0 = r2
        L_0x00d2:
            boolean r3 = r7.mHideWorkData
            if (r3 == 0) goto L_0x00e7
            com.google.android.systemui.smartspace.SmartSpaceData r3 = r7.mData
            java.util.Objects.requireNonNull(r3)
            com.google.android.systemui.smartspace.SmartSpaceCard r3 = r3.mCurrentCard
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.smartspace.nano.SmartspaceProto$SmartspaceUpdate$SmartspaceCard r3 = r3.mCard
            boolean r3 = r3.isWorkProfile
            if (r3 == 0) goto L_0x00e7
            goto L_0x00e8
        L_0x00e7:
            r1 = r2
        L_0x00e8:
            if (r0 != 0) goto L_0x00ec
            if (r1 == 0) goto L_0x011e
        L_0x00ec:
            com.google.android.systemui.smartspace.ProtoStore r0 = r7.mStore
            java.lang.String r1 = "smartspace_"
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r3 = r7.mCurrentUserId
            r2.append(r3)
            java.lang.String r3 = "_true"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r3 = 0
            r0.store(r3, r2)
            com.google.android.systemui.smartspace.ProtoStore r0 = r7.mStore
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r7 = r7.mCurrentUserId
            r1.append(r7)
            java.lang.String r7 = "_false"
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.store(r3, r7)
        L_0x011e:
            return
        L_0x011f:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x011f }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.NotificationLockscreenUserManagerGoogle.updateSmartSpaceVisibilitySettings():void");
    }

    public final void updatePublicMode() {
        super.updatePublicMode();
        updateSmartSpaceVisibilitySettings();
    }
}
