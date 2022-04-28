package com.android.systemui.dreams.complication.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.systemui.dreams.complication.dagger.ComplicationHostViewComponent_ComplicationHostViewModule_ProvidesComplicationHostViewFactory */
public final class C0792x8a1d9ad2 implements Factory<ConstraintLayout> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public final Object get() {
        ConstraintLayout constraintLayout = (ConstraintLayout) Preconditions.checkNotNull((ConstraintLayout) this.layoutInflaterProvider.get().inflate(C1777R.layout.dream_overlay_complications_layer, (ViewGroup) null), "R.layout.dream_overlay_complications_layer did not properly inflated");
        Objects.requireNonNull(constraintLayout, "Cannot return null from a non-@Nullable @Provides method");
        return constraintLayout;
    }

    public C0792x8a1d9ad2(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }
}
