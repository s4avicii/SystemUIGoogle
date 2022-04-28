package com.android.systemui.dreams.complication.dagger;

import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dreams.complication.dagger.ComplicationHostViewComponent_ComplicationHostViewModule_ProvidesComplicationPaddingFactory */
public final class C0793x7dca8e24 implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public final Object get() {
        return Integer.valueOf(this.resourcesProvider.get().getDimensionPixelSize(C1777R.dimen.dream_overlay_complication_margin));
    }

    public C0793x7dca8e24(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }
}
