package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* compiled from: RemoteInputCoordinator.kt */
public final class RemoteInputCoordinatorKt {
    public static final Lazy DEBUG$delegate = LazyKt__LazyJVMKt.lazy(RemoteInputCoordinatorKt$DEBUG$2.INSTANCE);

    public static final boolean access$getDEBUG() {
        return ((Boolean) DEBUG$delegate.getValue()).booleanValue();
    }
}
