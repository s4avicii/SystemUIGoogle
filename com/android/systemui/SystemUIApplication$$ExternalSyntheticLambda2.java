package com.android.systemui;

import java.util.Map;
import java.util.Objects;
import javax.inject.Provider;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIApplication$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ SystemUIApplication f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Map.Entry f$3;

    public /* synthetic */ SystemUIApplication$$ExternalSyntheticLambda2(SystemUIApplication systemUIApplication, int i, String str, Map.Entry entry) {
        this.f$0 = systemUIApplication;
        this.f$1 = i;
        this.f$3 = entry;
    }

    public final void run() {
        SystemUIApplication systemUIApplication = this.f$0;
        int i = this.f$1;
        Map.Entry entry = this.f$3;
        int i2 = SystemUIApplication.$r8$clinit;
        Objects.requireNonNull(systemUIApplication);
        CoreStartable[] coreStartableArr = systemUIApplication.mServices;
        CoreStartable coreStartable = (CoreStartable) ((Provider) entry.getValue()).get();
        coreStartable.start();
        coreStartableArr[i] = coreStartable;
    }
}
