package com.android.systemui.tuner;

import com.android.systemui.tuner.TunerService;

public class TunablePadding implements TunerService.Tunable {

    public static class TunablePaddingService {
    }

    public final void onTuningChanged(String str, String str2) {
        if (str2 != null) {
            try {
                Integer.parseInt(str2);
            } catch (NumberFormatException unused) {
            }
        }
        throw null;
    }
}
