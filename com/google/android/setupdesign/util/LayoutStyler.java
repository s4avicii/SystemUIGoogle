package com.google.android.setupdesign.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin$$ExternalSyntheticOutline0;

public final class LayoutStyler {
    @TargetApi(17)
    public static void applyPartnerCustomizationExtraPaddingStyle(View view) {
        int i;
        int i2;
        if (view != null) {
            Context context = view.getContext();
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
            boolean isPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
            boolean isPartnerConfigAvailable2 = partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2);
            if (!PartnerStyleHelper.shouldApplyPartnerResource(view)) {
                return;
            }
            if (isPartnerConfigAvailable || isPartnerConfigAvailable2) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{C1777R.attr.sudMarginStart, C1777R.attr.sudMarginEnd});
                int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
                int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(1, 0);
                obtainStyledAttributes.recycle();
                if (isPartnerConfigAvailable) {
                    i = Math.max(0, ((int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig, 0.0f)) - dimensionPixelSize);
                } else {
                    i = view.getPaddingStart();
                }
                if (isPartnerConfigAvailable2) {
                    i2 = Math.max(0, ((int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig2, 0.0f)) - dimensionPixelSize2);
                } else {
                    i2 = view.getPaddingEnd();
                }
                if (i != view.getPaddingStart() || i2 != view.getPaddingEnd()) {
                    int paddingTop = view.getPaddingTop();
                    if (view.getId() == C1777R.C1779id.sud_layout_content) {
                        i2 = i;
                    }
                    view.setPadding(i, paddingTop, i2, view.getPaddingBottom());
                }
            }
        }
    }

    @TargetApi(17)
    public static void applyPartnerCustomizationLayoutPaddingStyle(View view) {
        int i;
        int i2;
        if (view != null) {
            Context context = view.getContext();
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
            boolean isPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
            boolean isPartnerConfigAvailable2 = partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2);
            if (!PartnerStyleHelper.shouldApplyPartnerResource(view)) {
                return;
            }
            if (isPartnerConfigAvailable || isPartnerConfigAvailable2) {
                if (isPartnerConfigAvailable) {
                    i = (int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig, 0.0f);
                } else {
                    i = view.getPaddingStart();
                }
                if (isPartnerConfigAvailable2) {
                    i2 = (int) FooterBarMixin$$ExternalSyntheticOutline0.m82m(context, context, partnerConfig2, 0.0f);
                } else {
                    i2 = view.getPaddingEnd();
                }
                if (i != view.getPaddingStart() || i2 != view.getPaddingEnd()) {
                    view.setPadding(i, view.getPaddingTop(), i2, view.getPaddingBottom());
                }
            }
        }
    }
}
