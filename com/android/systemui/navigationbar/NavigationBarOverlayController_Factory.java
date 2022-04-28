package com.android.systemui.navigationbar;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class NavigationBarOverlayController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;

    public /* synthetic */ NavigationBarOverlayController_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new NavigationBarOverlayController((Context) this.contextProvider.get());
            default:
                ColorDisplayManager colorDisplayManager = (ColorDisplayManager) ((Context) this.contextProvider.get()).getSystemService(ColorDisplayManager.class);
                Objects.requireNonNull(colorDisplayManager, "Cannot return null from a non-@Nullable @Provides method");
                return colorDisplayManager;
        }
    }
}
