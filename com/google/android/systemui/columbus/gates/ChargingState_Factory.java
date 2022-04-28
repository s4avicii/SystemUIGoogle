package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.os.Handler;
import com.android.p012wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.systemui.unfold.UnfoldProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.google.android.systemui.columbus.ColumbusModule_ProvideTransientGateDurationFactory;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class ChargingState_Factory implements Factory {
    public final /* synthetic */ int $r8$classId = 1;
    public final Provider contextProvider;
    public final Object gateDurationProvider;
    public final Provider handlerProvider;

    public ChargingState_Factory(Provider provider, Provider provider2) {
        ColumbusModule_ProvideTransientGateDurationFactory columbusModule_ProvideTransientGateDurationFactory = ColumbusModule_ProvideTransientGateDurationFactory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.gateDurationProvider = columbusModule_ProvideTransientGateDurationFactory;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ChargingState((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), ((Long) ((Provider) this.gateDurationProvider).get()).longValue());
            default:
                Optional optional = (Optional) this.handlerProvider.get();
                Objects.requireNonNull((UnfoldTransitionModule) this.gateDurationProvider);
                if (!((UnfoldTransitionConfig) this.contextProvider.get()).isEnabled() || !optional.isPresent()) {
                    return ShellUnfoldProgressProvider.NO_PROVIDER;
                }
                return new UnfoldProgressProvider((UnfoldTransitionProgressProvider) optional.get());
        }
    }

    public ChargingState_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider, Provider provider2) {
        this.gateDurationProvider = unfoldTransitionModule;
        this.contextProvider = provider;
        this.handlerProvider = provider2;
    }
}
