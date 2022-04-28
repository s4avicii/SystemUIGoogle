package com.google.android.setupcompat.template;

import android.view.Window;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;

public final class SystemNavBarMixin implements Mixin {
    public final boolean applyPartnerResources;
    public final TemplateLayout templateLayout;
    public final boolean useFullDynamicColor;
    public final Window windowOfActivity;

    public SystemNavBarMixin(TemplateLayout templateLayout2, Window window) {
        boolean z;
        this.templateLayout = templateLayout2;
        this.windowOfActivity = window;
        boolean z2 = templateLayout2 instanceof PartnerCustomizationLayout;
        boolean z3 = false;
        if (!z2 || !((PartnerCustomizationLayout) templateLayout2).shouldApplyPartnerResource()) {
            z = false;
        } else {
            z = true;
        }
        this.applyPartnerResources = z;
        if (z2 && ((PartnerCustomizationLayout) templateLayout2).useFullDynamicColor()) {
            z3 = true;
        }
        this.useFullDynamicColor = z3;
    }
}
