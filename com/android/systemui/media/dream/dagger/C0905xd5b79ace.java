package com.android.systemui.media.dream.dagger;

import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import dagger.internal.Factory;

/* renamed from: com.android.systemui.media.dream.dagger.MediaComplicationComponent_MediaComplicationModule_ProvideLayoutParamsFactory */
public final class C0905xd5b79ace implements Factory<ComplicationLayoutParams> {

    /* renamed from: com.android.systemui.media.dream.dagger.MediaComplicationComponent_MediaComplicationModule_ProvideLayoutParamsFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final C0905xd5b79ace INSTANCE = new C0905xd5b79ace();
    }

    public final Object get() {
        return new ComplicationLayoutParams(0, 5, 2, 0, true);
    }
}
