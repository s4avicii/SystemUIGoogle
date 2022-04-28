package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.SensorManager;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.pip.phone.PipAppOpsListener;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.hinge.EmptyHingeAngleProvider;
import com.android.systemui.unfold.updates.hinge.HingeSensorAngleProvider;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class UnfoldSharedModule_HingeAngleProviderFactory implements Factory {
    public final /* synthetic */ int $r8$classId = 0;
    public final Provider configProvider;
    public final Object module;
    public final Provider sensorManagerProvider;

    public UnfoldSharedModule_HingeAngleProviderFactory(UnfoldSharedModule unfoldSharedModule, Provider provider, Provider provider2) {
        this.module = unfoldSharedModule;
        this.configProvider = provider;
        this.sensorManagerProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                SensorManager sensorManager = (SensorManager) this.sensorManagerProvider.get();
                Objects.requireNonNull((UnfoldSharedModule) this.module);
                if (((UnfoldTransitionConfig) this.configProvider.get()).isHingeAngleEnabled()) {
                    return new HingeSensorAngleProvider(sensorManager);
                }
                return EmptyHingeAngleProvider.INSTANCE;
            default:
                PipTouchHandler pipTouchHandler = (PipTouchHandler) this.sensorManagerProvider.get();
                Objects.requireNonNull(pipTouchHandler);
                return new PipAppOpsListener((Context) this.configProvider.get(), pipTouchHandler.mMotionHelper, (ShellExecutor) ((Provider) this.module).get());
        }
    }

    public UnfoldSharedModule_HingeAngleProviderFactory(Provider provider, Provider provider2, Provider provider3) {
        this.configProvider = provider;
        this.sensorManagerProvider = provider2;
        this.module = provider3;
    }
}
