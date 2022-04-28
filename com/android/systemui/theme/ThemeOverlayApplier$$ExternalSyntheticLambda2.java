package com.android.systemui.theme;

import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ ThemeOverlayApplier f$0;

    public /* synthetic */ ThemeOverlayApplier$$ExternalSyntheticLambda2(ThemeOverlayApplier themeOverlayApplier) {
        this.f$0 = themeOverlayApplier;
    }

    public final Object apply(Object obj) {
        ThemeOverlayApplier themeOverlayApplier = this.f$0;
        Objects.requireNonNull(themeOverlayApplier);
        return (String) themeOverlayApplier.mCategoryToTargetPackage.get((String) obj);
    }
}
