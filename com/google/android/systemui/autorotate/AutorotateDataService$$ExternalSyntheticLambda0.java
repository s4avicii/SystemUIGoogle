package com.google.android.systemui.autorotate;

import android.provider.DeviceConfig;
import java.util.Objects;
import java.util.Set;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AutorotateDataService$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ AutorotateDataService f$0;

    public /* synthetic */ AutorotateDataService$$ExternalSyntheticLambda0(AutorotateDataService autorotateDataService) {
        this.f$0 = autorotateDataService;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        AutorotateDataService autorotateDataService = this.f$0;
        Objects.requireNonNull(autorotateDataService);
        Set keyset = properties.getKeyset();
        if (keyset.contains("log_raw_sensor_data") || keyset.contains("log_rotation_preindication")) {
            autorotateDataService.readFlagsToControlSensorLogging();
        }
    }
}
