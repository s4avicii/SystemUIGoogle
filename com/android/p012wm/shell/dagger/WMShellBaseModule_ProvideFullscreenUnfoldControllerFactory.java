package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.fullscreen.FullscreenUnfoldController;
import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideFullscreenUnfoldControllerFactory */
public final class WMShellBaseModule_ProvideFullscreenUnfoldControllerFactory implements Factory<Optional<FullscreenUnfoldController>> {
    public final Provider<Optional<FullscreenUnfoldController>> fullscreenUnfoldControllerProvider;
    public final Provider<Optional<ShellUnfoldProgressProvider>> progressProvider;

    public final Object get() {
        Optional optional = this.fullscreenUnfoldControllerProvider.get();
        Optional optional2 = this.progressProvider.get();
        if (!optional2.isPresent() || optional2.get() == ShellUnfoldProgressProvider.NO_PROVIDER) {
            optional = Optional.empty();
        }
        Objects.requireNonNull(optional, "Cannot return null from a non-@Nullable @Provides method");
        return optional;
    }

    public WMShellBaseModule_ProvideFullscreenUnfoldControllerFactory(Provider<Optional<FullscreenUnfoldController>> provider, Provider<Optional<ShellUnfoldProgressProvider>> provider2) {
        this.fullscreenUnfoldControllerProvider = provider;
        this.progressProvider = provider2;
    }
}
