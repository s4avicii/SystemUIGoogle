package com.android.systemui.doze.dagger;

import android.os.SystemProperties;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.doze.DozeBrightnessHostForwarder;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeScreenStatePreventingAdapter;
import com.android.systemui.doze.DozeSuspendScreenStatePreventingAdapter;
import com.android.systemui.statusbar.phone.DozeParameters;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import java.util.Objects;
import javax.inject.Provider;

public final class DozeModule_ProvidesWrappedServiceFactory implements Factory<DozeMachine.Service> {
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeMachine.Service> dozeMachineServiceProvider;
    public final Provider<DozeParameters> dozeParametersProvider;

    public final Object get() {
        DozeParameters dozeParameters = this.dozeParametersProvider.get();
        DozeMachine.Service dozeBrightnessHostForwarder = new DozeBrightnessHostForwarder(this.dozeMachineServiceProvider.get(), this.dozeHostProvider.get());
        Objects.requireNonNull(dozeParameters);
        if (!SystemProperties.getBoolean("doze.display.supported", dozeParameters.mResources.getBoolean(C1777R.bool.doze_display_state_supported))) {
            dozeBrightnessHostForwarder = new DozeScreenStatePreventingAdapter(dozeBrightnessHostForwarder);
        }
        if (!dozeParameters.mResources.getBoolean(C1777R.bool.doze_suspend_display_state_supported)) {
            return new DozeSuspendScreenStatePreventingAdapter(dozeBrightnessHostForwarder);
        }
        return dozeBrightnessHostForwarder;
    }

    public DozeModule_ProvidesWrappedServiceFactory(InstanceFactory instanceFactory, Provider provider, Provider provider2) {
        this.dozeMachineServiceProvider = instanceFactory;
        this.dozeHostProvider = provider;
        this.dozeParametersProvider = provider2;
    }
}
