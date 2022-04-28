package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import java.util.function.ToIntFunction;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StandardWifiEntry$$ExternalSyntheticLambda3 implements ToIntFunction {
    public static final /* synthetic */ StandardWifiEntry$$ExternalSyntheticLambda3 INSTANCE = new StandardWifiEntry$$ExternalSyntheticLambda3();

    public final int applyAsInt(Object obj) {
        return ((ScanResult) obj).level * -1;
    }
}
