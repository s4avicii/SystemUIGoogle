package com.android.systemui.flags;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaControllerFactory;
import com.android.systemui.media.MediaTimeoutListener;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximitySensor;
import com.google.android.systemui.assist.uihints.KeyboardMonitor;
import com.google.android.systemui.columbus.gates.Proximity;
import com.google.android.systemui.elmyra.actions.SilenceCall;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class FeatureFlagsRelease_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dumpManagerProvider;
    public final Provider resourcesProvider;

    public /* synthetic */ FeatureFlagsRelease_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.resourcesProvider = provider;
        this.dumpManagerProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new FeatureFlagsRelease((Resources) this.resourcesProvider.get(), (DumpManager) this.dumpManagerProvider.get());
            case 1:
                return new MediaTimeoutListener((MediaControllerFactory) this.resourcesProvider.get(), (DelayableExecutor) this.dumpManagerProvider.get());
            case 2:
                return new UnfoldTransitionWallpaperController((UnfoldTransitionProgressProvider) this.resourcesProvider.get(), (WallpaperController) this.dumpManagerProvider.get());
            case 3:
                return new KeyboardMonitor((Context) this.resourcesProvider.get(), (Optional) this.dumpManagerProvider.get());
            case 4:
                return new Proximity((Context) this.resourcesProvider.get(), (ProximitySensor) this.dumpManagerProvider.get());
            default:
                return new SilenceCall((Context) this.resourcesProvider.get(), (TelephonyListenerManager) this.dumpManagerProvider.get());
        }
    }
}
