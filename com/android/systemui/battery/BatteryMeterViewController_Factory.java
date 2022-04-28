package com.android.systemui.battery;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerService;
import com.google.android.systemui.columbus.ColumbusSettings;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.actions.TakeScreenshot;
import com.google.android.systemui.columbus.actions.UserSelectedAction;
import com.google.android.systemui.columbus.gates.VrMode_Factory;
import dagger.internal.Factory;
import java.util.Map;
import javax.inject.Provider;

public final class BatteryMeterViewController_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider batteryControllerProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider configurationControllerProvider;
    public final Provider contentResolverProvider;
    public final Provider mainHandlerProvider;
    public final Provider tunerServiceProvider;
    public final Provider viewProvider;

    public /* synthetic */ BatteryMeterViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, int i) {
        this.$r8$classId = i;
        this.viewProvider = provider;
        this.configurationControllerProvider = provider2;
        this.tunerServiceProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.mainHandlerProvider = provider5;
        this.contentResolverProvider = provider6;
        this.batteryControllerProvider = provider7;
    }

    public static BatteryMeterViewController_Factory create(VrMode_Factory vrMode_Factory, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new BatteryMeterViewController_Factory(vrMode_Factory, provider, provider2, provider3, provider4, provider5, provider6, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new BatteryMeterViewController((BatteryMeterView) this.viewProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (Handler) this.mainHandlerProvider.get(), (ContentResolver) this.contentResolverProvider.get(), (BatteryController) this.batteryControllerProvider.get());
            default:
                return new UserSelectedAction((Context) this.viewProvider.get(), (ColumbusSettings) this.configurationControllerProvider.get(), (Map) this.tunerServiceProvider.get(), (TakeScreenshot) this.broadcastDispatcherProvider.get(), (KeyguardStateController) this.mainHandlerProvider.get(), (PowerManagerWrapper) this.contentResolverProvider.get(), (WakefulnessLifecycle) this.batteryControllerProvider.get());
        }
    }
}
