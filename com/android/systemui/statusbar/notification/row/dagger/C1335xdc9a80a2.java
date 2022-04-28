package com.android.systemui.statusbar.notification.row.dagger;

import android.service.notification.StatusBarNotification;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory */
public final class C1335xdc9a80a2 implements Factory<String> {
    public final Provider<StatusBarNotification> statusBarNotificationProvider;

    public final Object get() {
        String key = this.statusBarNotificationProvider.get().getKey();
        Objects.requireNonNull(key, "Cannot return null from a non-@Nullable @Provides method");
        return key;
    }

    public C1335xdc9a80a2(C1336xc255c3ca expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory) {
        this.statusBarNotificationProvider = expandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory;
    }
}
