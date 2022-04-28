package com.android.systemui;

import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.SparseArray;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.theme.ThemeOverlayApplier;

public final class ForegroundServiceController {
    public static final int[] APP_OPS = {24};
    public final Handler mMainHandler;
    public final Object mMutex = new Object();
    public final SparseArray<ForegroundServicesUserState> mUserServices = new SparseArray<>();

    public interface UserStateUpdateCallback {
        boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
        return r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isDisclosureNeededForUser(int r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mMutex
            monitor-enter(r0)
            android.util.SparseArray<com.android.systemui.ForegroundServicesUserState> r6 = r6.mUserServices     // Catch:{ all -> 0x0040 }
            java.lang.Object r6 = r6.get(r7)     // Catch:{ all -> 0x0040 }
            com.android.systemui.ForegroundServicesUserState r6 = (com.android.systemui.ForegroundServicesUserState) r6     // Catch:{ all -> 0x0040 }
            r7 = 0
            if (r6 != 0) goto L_0x0010
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return r7
        L_0x0010:
            java.lang.String[] r1 = r6.mRunning     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x003e
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0040 }
            long r3 = r6.mServiceStartTime     // Catch:{ all -> 0x0040 }
            long r1 = r1 - r3
            r3 = 5000(0x1388, double:2.4703E-320)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 < 0) goto L_0x003e
            java.lang.String[] r1 = r6.mRunning     // Catch:{ all -> 0x0040 }
            int r2 = r1.length     // Catch:{ all -> 0x0040 }
            r3 = r7
        L_0x0025:
            if (r3 >= r2) goto L_0x003e
            r4 = r1[r3]     // Catch:{ all -> 0x0040 }
            android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> r5 = r6.mImportantNotifications     // Catch:{ all -> 0x0040 }
            java.lang.Object r4 = r5.get(r4)     // Catch:{ all -> 0x0040 }
            android.util.ArraySet r4 = (android.util.ArraySet) r4     // Catch:{ all -> 0x0040 }
            if (r4 == 0) goto L_0x003d
            int r4 = r4.size()     // Catch:{ all -> 0x0040 }
            if (r4 != 0) goto L_0x003a
            goto L_0x003d
        L_0x003a:
            int r3 = r3 + 1
            goto L_0x0025
        L_0x003d:
            r7 = 1
        L_0x003e:
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return r7
        L_0x0040:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ForegroundServiceController.isDisclosureNeededForUser(int):boolean");
    }

    public final boolean updateUserState(int i, UserStateUpdateCallback userStateUpdateCallback, boolean z) {
        synchronized (this.mMutex) {
            ForegroundServicesUserState foregroundServicesUserState = this.mUserServices.get(i);
            if (foregroundServicesUserState == null) {
                if (!z) {
                    return false;
                }
                foregroundServicesUserState = new ForegroundServicesUserState();
                this.mUserServices.put(i, foregroundServicesUserState);
            }
            boolean updateUserState = userStateUpdateCallback.updateUserState(foregroundServicesUserState);
            return updateUserState;
        }
    }

    public ForegroundServiceController(AppOpsController appOpsController, Handler handler) {
        this.mMainHandler = handler;
        appOpsController.addCallback(APP_OPS, new ForegroundServiceController$$ExternalSyntheticLambda0(this));
    }

    public static boolean isDisclosureNotification(StatusBarNotification statusBarNotification) {
        if (statusBarNotification.getId() == 40 && statusBarNotification.getTag() == null && statusBarNotification.getPackageName().equals(ThemeOverlayApplier.ANDROID_PACKAGE)) {
            return true;
        }
        return false;
    }
}
