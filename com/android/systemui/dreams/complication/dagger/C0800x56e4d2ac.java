package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import dagger.internal.Factory;

/* renamed from: com.android.systemui.dreams.complication.dagger.DreamWeatherComplicationComponent_DreamWeatherComplicationModule_ProvideLayoutParamsFactory */
public final class C0800x56e4d2ac implements Factory<ComplicationLayoutParams> {

    /* renamed from: com.android.systemui.dreams.complication.dagger.DreamWeatherComplicationComponent_DreamWeatherComplicationModule_ProvideLayoutParamsFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final C0800x56e4d2ac INSTANCE = new C0800x56e4d2ac();
    }

    public final Object get() {
        return new ComplicationLayoutParams(6, 8, 1);
    }
}
