package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.service.dreams.IDreamManager;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.collection.coordinator.SharedCoordinatorLogger;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideHideDisplayCutoutFactory */
public final class WMShellBaseModule_ProvideHideDisplayCutoutFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider hideDisplayCutoutControllerProvider;

    public /* synthetic */ WMShellBaseModule_ProvideHideDisplayCutoutFactory(Provider provider, int i) {
        this.$r8$classId = i;
        this.hideDisplayCutoutControllerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional map = ((Optional) this.hideDisplayCutoutControllerProvider.get()).map(WMShellBaseModule$$ExternalSyntheticLambda2.INSTANCE);
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
            case 1:
                LatencyTracker instance = LatencyTracker.getInstance((Context) this.hideDisplayCutoutControllerProvider.get());
                Objects.requireNonNull(instance, "Cannot return null from a non-@Nullable @Provides method");
                return instance;
            case 2:
                return new DialogLaunchAnimator((IDreamManager) this.hideDisplayCutoutControllerProvider.get());
            case 3:
                return new SharedCoordinatorLogger((LogBuffer) this.hideDisplayCutoutControllerProvider.get());
            case 4:
                PhoneStatusBarView phoneStatusBarView = (PhoneStatusBarView) ((CollapsedStatusBarFragment) this.hideDisplayCutoutControllerProvider.get()).getView();
                Objects.requireNonNull(phoneStatusBarView, "Cannot return null from a non-@Nullable @Provides method");
                return phoneStatusBarView;
            default:
                return Boolean.valueOf(((Resources) this.hideDisplayCutoutControllerProvider.get()).getBoolean(17891644));
        }
    }
}
