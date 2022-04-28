package com.android.systemui.statusbar.notification.collection;

import android.content.pm.PackageManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TargetSdkResolver.kt */
public final class TargetSdkResolver$initialize$1 implements NotifCollectionListener {
    public final /* synthetic */ TargetSdkResolver this$0;

    public TargetSdkResolver$initialize$1(TargetSdkResolver targetSdkResolver) {
        this.this$0 = targetSdkResolver;
    }

    public final void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        TargetSdkResolver targetSdkResolver = this.this$0;
        Objects.requireNonNull(targetSdkResolver);
        int i = 0;
        try {
            i = StatusBar.getPackageManagerForUser(targetSdkResolver.context, statusBarNotification.getUser().getIdentifier()).getApplicationInfo(statusBarNotification.getPackageName(), 0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TargetSdkResolver", Intrinsics.stringPlus("Failed looking up ApplicationInfo for ", statusBarNotification.getPackageName()), e);
        }
        notificationEntry.targetSdk = i;
    }
}
