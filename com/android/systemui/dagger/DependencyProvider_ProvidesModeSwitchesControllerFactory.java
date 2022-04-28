package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.accessibility.ModeSwitchesController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.systemui.lowlightclock.LightSensorEventsDebounceAlgorithm;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class DependencyProvider_ProvidesModeSwitchesControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId = 0;
    public final Provider contextProvider;
    public final Object module;

    public DependencyProvider_ProvidesModeSwitchesControllerFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.module = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Objects.requireNonNull((DependencyProvider) this.module);
                return new ModeSwitchesController((Context) this.contextProvider.get());
            default:
                return new LightSensorEventsDebounceAlgorithm((DelayableExecutor) this.contextProvider.get(), (Resources) ((Provider) this.module).get());
        }
    }

    public DependencyProvider_ProvidesModeSwitchesControllerFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
    }
}
