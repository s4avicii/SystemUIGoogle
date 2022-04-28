package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import dagger.internal.Factory;

/* renamed from: com.android.systemui.dreams.complication.dagger.DreamClockTimeComplicationComponent_DreamClockTimeComplicationModule_ProvideLayoutParamsFactory */
public final class C0798x4215cf9e implements Factory<ComplicationLayoutParams> {

    /* renamed from: com.android.systemui.dreams.complication.dagger.DreamClockTimeComplicationComponent_DreamClockTimeComplicationModule_ProvideLayoutParamsFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final C0798x4215cf9e INSTANCE = new C0798x4215cf9e();
    }

    public final Object get() {
        return new ComplicationLayoutParams(6, 1, 0);
    }
}
