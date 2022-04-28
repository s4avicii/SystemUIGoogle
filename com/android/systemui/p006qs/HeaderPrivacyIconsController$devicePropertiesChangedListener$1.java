package com.android.systemui.p006qs;

import android.provider.DeviceConfig;

/* renamed from: com.android.systemui.qs.HeaderPrivacyIconsController$devicePropertiesChangedListener$1 */
/* compiled from: HeaderPrivacyIconsController.kt */
public final class HeaderPrivacyIconsController$devicePropertiesChangedListener$1 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ HeaderPrivacyIconsController this$0;

    public HeaderPrivacyIconsController$devicePropertiesChangedListener$1(HeaderPrivacyIconsController headerPrivacyIconsController) {
        this.this$0 = headerPrivacyIconsController;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        this.this$0.safetyCenterEnabled = properties.getBoolean("safety_center_is_enabled", false);
    }
}
