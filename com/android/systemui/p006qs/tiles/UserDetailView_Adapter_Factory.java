package com.android.systemui.p006qs.tiles;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.p006qs.tiles.UserDetailView;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.tiles.UserDetailView_Adapter_Factory */
public final class UserDetailView_Adapter_Factory implements Factory<UserDetailView.Adapter> {
    public final Provider<Context> contextProvider;
    public final Provider<UserSwitcherController> controllerProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new UserDetailView.Adapter(this.contextProvider.get(), this.controllerProvider.get(), this.uiEventLoggerProvider.get(), this.falsingManagerProvider.get());
    }

    public UserDetailView_Adapter_Factory(Provider<Context> provider, Provider<UserSwitcherController> provider2, Provider<UiEventLogger> provider3, Provider<FalsingManager> provider4) {
        this.contextProvider = provider;
        this.controllerProvider = provider2;
        this.uiEventLoggerProvider = provider3;
        this.falsingManagerProvider = provider4;
    }
}
