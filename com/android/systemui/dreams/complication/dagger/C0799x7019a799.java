package com.android.systemui.dreams.complication.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dreams.complication.dagger.DreamWeatherComplicationComponent_DreamWeatherComplicationModule_ProvideComplicationViewFactory */
public final class C0799x7019a799 implements Factory<TextView> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public final Object get() {
        TextView textView = (TextView) Preconditions.checkNotNull((TextView) this.layoutInflaterProvider.get().inflate(C1777R.layout.dream_overlay_complication_weather, (ViewGroup) null, false), "R.layout.dream_overlay_complication_weather did not properly inflated");
        Objects.requireNonNull(textView, "Cannot return null from a non-@Nullable @Provides method");
        return textView;
    }

    public C0799x7019a799(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }
}
