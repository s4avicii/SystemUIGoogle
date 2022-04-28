package com.google.android.systemui.communal.dock.callbacks.mediashell.dagger;

import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import dagger.internal.Factory;

public final class MediaShellModule_ProvidesComplicationLayoutParamsFactory implements Factory<ComplicationLayoutParams> {

    public static final class InstanceHolder {
        public static final MediaShellModule_ProvidesComplicationLayoutParamsFactory INSTANCE = new MediaShellModule_ProvidesComplicationLayoutParamsFactory();
    }

    public final Object get() {
        return new ComplicationLayoutParams(-2, 6, 8, 0, true);
    }
}
