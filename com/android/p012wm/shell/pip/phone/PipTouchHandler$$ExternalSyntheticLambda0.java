package com.android.p012wm.shell.pip.phone;

import android.provider.DeviceConfig;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ PipTouchHandler f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda0(PipTouchHandler pipTouchHandler) {
        this.f$0 = pipTouchHandler;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        PipTouchHandler pipTouchHandler = this.f$0;
        Objects.requireNonNull(pipTouchHandler);
        if (properties.getKeyset().contains("pip_stashing")) {
            pipTouchHandler.mEnableStash = properties.getBoolean("pip_stashing", true);
        }
    }
}
