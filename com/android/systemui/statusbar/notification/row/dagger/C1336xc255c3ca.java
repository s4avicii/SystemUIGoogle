package com.android.systemui.statusbar.notification.row.dagger;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory */
public final class C1336xc255c3ca implements Factory<StatusBarNotification> {
    public final Provider<NotificationEntry> notificationEntryProvider;

    public final Object get() {
        NotificationEntry notificationEntry = this.notificationEntryProvider.get();
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Objects.requireNonNull(statusBarNotification, "Cannot return null from a non-@Nullable @Provides method");
        return statusBarNotification;
    }

    public C1336xc255c3ca(InstanceFactory instanceFactory) {
        this.notificationEntryProvider = instanceFactory;
    }
}
