package com.google.android.systemui.communal.dock.callbacks.mediashell;

import com.android.systemui.dreams.DreamOverlayStateController;
import com.google.android.systemui.communal.dock.callbacks.mediashell.dagger.MediaShellComponent$Factory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaShellCallback_Factory implements Factory<MediaShellCallback> {
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<MediaShellComponent$Factory> factoryProvider;
    public final Provider<MediaShellComplication> mediaShellComplicationProvider;

    public final Object get() {
        return new MediaShellCallback(this.factoryProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.mediaShellComplicationProvider.get());
    }

    public MediaShellCallback_Factory(Provider<MediaShellComponent$Factory> provider, Provider<DreamOverlayStateController> provider2, Provider<MediaShellComplication> provider3) {
        this.factoryProvider = provider;
        this.dreamOverlayStateControllerProvider = provider2;
        this.mediaShellComplicationProvider = provider3;
    }
}
