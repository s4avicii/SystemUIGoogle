package com.google.android.systemui.communal.dock.callbacks.mediashell;

import android.view.View;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.google.android.systemui.communal.dock.callbacks.mediashell.dagger.MediaShellModule_ProvidesComplicationLayoutParamsFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MediaShellComplication_Factory implements Factory<MediaShellComplication> {
    public final Provider<ComplicationLayoutParams> layoutParamsProvider;
    public final Provider<View> viewProvider;

    public MediaShellComplication_Factory(Provider provider) {
        MediaShellModule_ProvidesComplicationLayoutParamsFactory mediaShellModule_ProvidesComplicationLayoutParamsFactory = MediaShellModule_ProvidesComplicationLayoutParamsFactory.InstanceHolder.INSTANCE;
        this.viewProvider = provider;
        this.layoutParamsProvider = mediaShellModule_ProvidesComplicationLayoutParamsFactory;
    }

    public final Object get() {
        return new MediaShellComplication(this.viewProvider.get(), this.layoutParamsProvider.get());
    }
}
