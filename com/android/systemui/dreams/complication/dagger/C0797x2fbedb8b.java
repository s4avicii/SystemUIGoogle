package com.android.systemui.dreams.complication.dagger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dreams.complication.dagger.DreamClockTimeComplicationComponent_DreamClockTimeComplicationModule_ProvideComplicationViewFactory */
public final class C0797x2fbedb8b implements Factory<View> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public final Object get() {
        TextClock textClock = (TextClock) Preconditions.checkNotNull((TextClock) this.layoutInflaterProvider.get().inflate(C1777R.layout.dream_overlay_complication_clock_time, (ViewGroup) null, false), "R.layout.dream_overlay_complication_clock_time did not properly inflated");
        textClock.setFontVariationSettings("'wght' 200");
        return textClock;
    }

    public C0797x2fbedb8b(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }
}
