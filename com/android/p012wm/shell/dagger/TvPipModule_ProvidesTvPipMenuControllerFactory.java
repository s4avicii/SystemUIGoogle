package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.Handler;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.p006qs.ReduceBrightColorsController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvidesTvPipMenuControllerFactory */
public final class TvPipModule_ProvidesTvPipMenuControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId = 1;
    public final Provider contextProvider;
    public final Object mainHandlerProvider;
    public final Provider pipMediaControllerProvider;
    public final Provider systemWindowsProvider;
    public final Provider tvPipBoundsStateProvider;

    public TvPipModule_ProvidesTvPipMenuControllerFactory(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.mainHandlerProvider = dependencyProvider;
        this.contextProvider = provider;
        this.tvPipBoundsStateProvider = provider2;
        this.systemWindowsProvider = provider3;
        this.pipMediaControllerProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new TvPipMenuController((Context) this.contextProvider.get(), (TvPipBoundsState) this.tvPipBoundsStateProvider.get(), (SystemWindows) this.systemWindowsProvider.get(), (PipMediaController) this.pipMediaControllerProvider.get(), (Handler) ((Provider) this.mainHandlerProvider).get());
            default:
                UserTracker userTracker = (UserTracker) this.tvPipBoundsStateProvider.get();
                Objects.requireNonNull((DependencyProvider) this.mainHandlerProvider);
                return new ReduceBrightColorsController(userTracker, (Handler) this.contextProvider.get(), (ColorDisplayManager) this.systemWindowsProvider.get(), (SecureSettings) this.pipMediaControllerProvider.get());
        }
    }

    public TvPipModule_ProvidesTvPipMenuControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.tvPipBoundsStateProvider = provider2;
        this.systemWindowsProvider = provider3;
        this.pipMediaControllerProvider = provider4;
        this.mainHandlerProvider = provider5;
    }
}
