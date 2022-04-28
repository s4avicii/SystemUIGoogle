package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import dagger.internal.Factory;

/* renamed from: com.android.systemui.dreams.complication.dagger.DreamClockDateComplicationComponent_DreamClockDateComplicationModule_ProvideLayoutParamsFactory */
public final class C0796x871f559c implements Factory<ComplicationLayoutParams> {

    /* renamed from: com.android.systemui.dreams.complication.dagger.DreamClockDateComplicationComponent_DreamClockDateComplicationModule_ProvideLayoutParamsFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final C0796x871f559c INSTANCE = new C0796x871f559c();
    }

    public final Object get() {
        return new ComplicationLayoutParams(6, 8, 2);
    }
}
