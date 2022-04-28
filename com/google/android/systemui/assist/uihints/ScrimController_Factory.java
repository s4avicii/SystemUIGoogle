package com.google.android.systemui.assist.uihints;

import android.view.ViewGroup;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ScrimController_Factory implements Factory<ScrimController> {
    public final Provider<LightnessProvider> lightnessProvider;
    public final Provider<OverlappedElementController> overlappedElementControllerProvider;
    public final Provider<ViewGroup> parentProvider;
    public final Provider<TouchInsideHandler> touchInsideHandlerProvider;

    public final Object get() {
        OverlappedElementController overlappedElementController = this.overlappedElementControllerProvider.get();
        return new ScrimController(this.parentProvider.get(), overlappedElementController, this.lightnessProvider.get(), this.touchInsideHandlerProvider.get());
    }

    public ScrimController_Factory(Provider<ViewGroup> provider, Provider<OverlappedElementController> provider2, Provider<LightnessProvider> provider3, Provider<TouchInsideHandler> provider4) {
        this.parentProvider = provider;
        this.overlappedElementControllerProvider = provider2;
        this.lightnessProvider = provider3;
        this.touchInsideHandlerProvider = provider4;
    }
}
