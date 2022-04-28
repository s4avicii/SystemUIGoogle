package com.android.systemui.p006qs.tiles;

import android.provider.DeviceConfig;
import java.util.function.Supplier;

/* renamed from: com.android.systemui.qs.tiles.CameraToggleTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CameraToggleTile$$ExternalSyntheticLambda0 implements Supplier {
    public static final /* synthetic */ CameraToggleTile$$ExternalSyntheticLambda0 INSTANCE = new CameraToggleTile$$ExternalSyntheticLambda0();

    public final Object get() {
        int i = CameraToggleTile.$r8$clinit;
        return Boolean.valueOf(DeviceConfig.getBoolean("privacy", "camera_toggle_enabled", true));
    }
}
