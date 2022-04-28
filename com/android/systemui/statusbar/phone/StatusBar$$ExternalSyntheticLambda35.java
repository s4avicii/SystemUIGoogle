package com.android.systemui.statusbar.phone;

import android.os.Bundle;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.plugins.p005qs.C0961QS;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda35 implements Supplier {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda35(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final Object get() {
        StatusBar statusBar = this.f$0;
        Objects.requireNonNull(statusBar);
        FragmentHostManager fragmentHostManager = FragmentHostManager.get(statusBar.mNotificationShadeWindowView);
        Objects.requireNonNull(fragmentHostManager);
        return (C0961QS) fragmentHostManager.mPlugins.instantiate(fragmentHostManager.mContext, QSFragment.class.getName(), (Bundle) null);
    }
}
