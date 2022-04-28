package com.android.systemui.statusbar.phone;

import android.os.Handler;
import android.view.IWindowManager;
import com.android.systemui.statusbar.phone.AutoHideController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AutoHideController_Factory_Factory implements Factory<AutoHideController.Factory> {
    public final Provider<Handler> handlerProvider;
    public final Provider<IWindowManager> iWindowManagerProvider;

    public final Object get() {
        return new AutoHideController.Factory(this.handlerProvider.get(), this.iWindowManagerProvider.get());
    }

    public AutoHideController_Factory_Factory(Provider<Handler> provider, Provider<IWindowManager> provider2) {
        this.handlerProvider = provider;
        this.iWindowManagerProvider = provider2;
    }
}
