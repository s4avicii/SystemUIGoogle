package com.android.systemui.people;

import android.app.Notification;
import android.content.pm.PackageManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ PackageManager f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda5(PackageManager packageManager, String str) {
        this.f$0 = packageManager;
        this.f$1 = str;
    }

    public final boolean test(Object obj) {
        boolean z;
        boolean z2;
        PackageManager packageManager = this.f$0;
        String str = this.f$1;
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        Objects.requireNonNull(notificationEntry);
        if (packageManager.checkPermission("android.permission.READ_CONTACTS", notificationEntry.mSbn.getPackageName()) == 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        Notification notification = notificationEntry.mSbn.getNotification();
        if (notification == null) {
            z2 = false;
        } else {
            z2 = NotificationHelper.isMissedCall(notification);
        }
        if (!z2 || !Objects.equals(str, NotificationHelper.getContactUri(notificationEntry.mSbn))) {
            return false;
        }
        return true;
    }
}
