package com.android.systemui;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.ForegroundServiceController;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ForegroundServiceNotificationListener$$ExternalSyntheticLambda0 implements ForegroundServiceController.UserStateUpdateCallback {
    public final /* synthetic */ ForegroundServiceNotificationListener f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ForegroundServiceNotificationListener$$ExternalSyntheticLambda0(ForegroundServiceNotificationListener foregroundServiceNotificationListener, StatusBarNotification statusBarNotification, int i) {
        this.f$0 = foregroundServiceNotificationListener;
        this.f$1 = statusBarNotification;
        this.f$2 = i;
    }

    public final boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState) {
        String[] strArr;
        ForegroundServiceNotificationListener foregroundServiceNotificationListener = this.f$0;
        StatusBarNotification statusBarNotification = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(foregroundServiceNotificationListener);
        Objects.requireNonNull(foregroundServiceNotificationListener.mForegroundServiceController);
        if (ForegroundServiceController.isDisclosureNotification(statusBarNotification)) {
            Bundle bundle = statusBarNotification.getNotification().extras;
            if (bundle != null) {
                String[] stringArray = bundle.getStringArray("android.foregroundApps");
                long j = statusBarNotification.getNotification().when;
                if (stringArray != null) {
                    strArr = (String[]) Arrays.copyOf(stringArray, stringArray.length);
                } else {
                    strArr = null;
                }
                foregroundServicesUserState.mRunning = strArr;
                foregroundServicesUserState.mServiceStartTime = j;
            }
        } else {
            String packageName = statusBarNotification.getPackageName();
            String key = statusBarNotification.getKey();
            ArrayMap<String, ArraySet<String>> arrayMap = foregroundServicesUserState.mImportantNotifications;
            ArraySet arraySet = arrayMap.get(packageName);
            if (arraySet != null) {
                arraySet.remove(key);
                if (arraySet.size() == 0) {
                    arrayMap.remove(packageName);
                }
            }
            ArrayMap<String, ArraySet<String>> arrayMap2 = foregroundServicesUserState.mStandardLayoutNotifications;
            ArraySet arraySet2 = arrayMap2.get(packageName);
            if (arraySet2 != null) {
                arraySet2.remove(key);
                if (arraySet2.size() == 0) {
                    arrayMap2.remove(packageName);
                }
            }
            if ((statusBarNotification.getNotification().flags & 64) != 0 && i > 1) {
                String packageName2 = statusBarNotification.getPackageName();
                String key2 = statusBarNotification.getKey();
                ArrayMap<String, ArraySet<String>> arrayMap3 = foregroundServicesUserState.mImportantNotifications;
                if (arrayMap3.get(packageName2) == null) {
                    arrayMap3.put(packageName2, new ArraySet());
                }
                arrayMap3.get(packageName2).add(key2);
            }
            if (Notification.Builder.recoverBuilder(foregroundServiceNotificationListener.mContext, statusBarNotification.getNotification()).usesStandardHeader()) {
                String packageName3 = statusBarNotification.getPackageName();
                String key3 = statusBarNotification.getKey();
                ArrayMap<String, ArraySet<String>> arrayMap4 = foregroundServicesUserState.mStandardLayoutNotifications;
                if (arrayMap4.get(packageName3) == null) {
                    arrayMap4.put(packageName3, new ArraySet());
                }
                arrayMap4.get(packageName3).add(key3);
            }
        }
        return true;
    }
}
