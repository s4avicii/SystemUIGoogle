package com.android.systemui.dreams.complication.dagger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dreams.complication.dagger.DreamClockDateComplicationComponent_DreamClockDateComplicationModule_ProvideComplicationViewFactory */
public final class C0795x28793289 implements Factory<View> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public final Object get() {
        View view = (View) Preconditions.checkNotNull(this.layoutInflaterProvider.get().inflate(C1777R.layout.dream_overlay_complication_clock_date, (ViewGroup) null, false), "R.layout.dream_overlay_complication_clock_date did not properly inflated");
        Objects.requireNonNull(view, "Cannot return null from a non-@Nullable @Provides method");
        return view;
    }

    public C0795x28793289(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }
}
