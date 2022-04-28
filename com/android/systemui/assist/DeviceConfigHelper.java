package com.android.systemui.assist;

import com.android.systemui.DejankUtils;

public final class DeviceConfigHelper {
    public static long getLong(String str, long j) {
        return ((Long) DejankUtils.whitelistIpcs(new DeviceConfigHelper$$ExternalSyntheticLambda0(str, j))).longValue();
    }
}
