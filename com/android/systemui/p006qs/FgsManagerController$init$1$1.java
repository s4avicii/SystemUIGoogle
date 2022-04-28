package com.android.systemui.p006qs;

import android.provider.DeviceConfig;

/* renamed from: com.android.systemui.qs.FgsManagerController$init$1$1 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$init$1$1 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ FgsManagerController this$0;

    public FgsManagerController$init$1$1(FgsManagerController fgsManagerController) {
        this.this$0 = fgsManagerController;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        FgsManagerController fgsManagerController = this.this$0;
        fgsManagerController.isAvailable = properties.getBoolean("task_manager_enabled", fgsManagerController.isAvailable);
    }
}
