package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$id;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.view.StatusBarBackgroundLayout;
import java.util.Objects;

public final class StatusBarMixin implements Mixin {
    public final View decorView;
    public LinearLayout linearLayout;
    public final PartnerCustomizationLayout partnerCustomizationLayout;
    public StatusBarBackgroundLayout statusBarLayout;

    public final void setStatusBarBackground(Drawable drawable) {
        boolean z;
        if (this.partnerCustomizationLayout.shouldApplyPartnerResource() && !this.partnerCustomizationLayout.useFullDynamicColor()) {
            Context context = this.partnerCustomizationLayout.getContext();
            drawable = PartnerConfigHelper.get(context).getDrawable(context, PartnerConfig.CONFIG_STATUS_BAR_BACKGROUND);
        }
        StatusBarBackgroundLayout statusBarBackgroundLayout = this.statusBarLayout;
        if (statusBarBackgroundLayout == null) {
            this.linearLayout.setBackgroundDrawable(drawable);
            return;
        }
        Objects.requireNonNull(statusBarBackgroundLayout);
        statusBarBackgroundLayout.statusBarBackground = drawable;
        boolean z2 = true;
        if (drawable == null) {
            z = true;
        } else {
            z = false;
        }
        statusBarBackgroundLayout.setWillNotDraw(z);
        if (drawable == null) {
            z2 = false;
        }
        statusBarBackgroundLayout.setFitsSystemWindows(z2);
        statusBarBackgroundLayout.invalidate();
    }

    public StatusBarMixin(PartnerCustomizationLayout partnerCustomizationLayout2, Window window, AttributeSet attributeSet, int i) {
        boolean z;
        this.partnerCustomizationLayout = partnerCustomizationLayout2;
        View findManagedViewById = partnerCustomizationLayout2.findManagedViewById(C1777R.C1779id.suc_layout_status);
        Objects.requireNonNull(findManagedViewById, "sucLayoutStatus cannot be null in StatusBarMixin");
        if (findManagedViewById instanceof StatusBarBackgroundLayout) {
            this.statusBarLayout = (StatusBarBackgroundLayout) findManagedViewById;
        } else {
            this.linearLayout = (LinearLayout) findManagedViewById;
        }
        View decorView2 = window.getDecorView();
        this.decorView = decorView2;
        window.setStatusBarColor(0);
        TypedArray obtainStyledAttributes = partnerCustomizationLayout2.getContext().obtainStyledAttributes(attributeSet, R$id.SucStatusBarMixin, i, 0);
        if ((decorView2.getSystemUiVisibility() & 8192) == 8192) {
            z = true;
        } else {
            z = false;
        }
        boolean z2 = obtainStyledAttributes.getBoolean(0, z);
        if (partnerCustomizationLayout2.shouldApplyPartnerResource()) {
            Context context = partnerCustomizationLayout2.getContext();
            z2 = PartnerConfigHelper.get(context).getBoolean(context, PartnerConfig.CONFIG_LIGHT_STATUS_BAR, false);
        }
        if (z2) {
            decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() | 8192);
        } else {
            decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() & -8193);
        }
        setStatusBarBackground(obtainStyledAttributes.getDrawable(1));
        obtainStyledAttributes.recycle();
    }
}
