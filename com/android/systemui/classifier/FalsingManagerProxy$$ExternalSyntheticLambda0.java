package com.android.systemui.classifier;

import android.provider.DeviceConfig;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FalsingManagerProxy$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ FalsingManagerProxy f$0;

    public /* synthetic */ FalsingManagerProxy$$ExternalSyntheticLambda0(FalsingManagerProxy falsingManagerProxy) {
        this.f$0 = falsingManagerProxy;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        FalsingManagerProxy falsingManagerProxy = this.f$0;
        Objects.requireNonNull(falsingManagerProxy);
        if ("systemui".equals(properties.getNamespace())) {
            falsingManagerProxy.setupFalsingManager();
        }
    }
}
