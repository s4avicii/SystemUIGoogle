package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.C1777R;

public final class AdaptiveIcon extends LayerDrawable {
    public AdaptiveConstantState mAdaptiveConstantState;
    public int mBackgroundColor = -1;

    public static class AdaptiveConstantState extends Drawable.ConstantState {
        public int mColor;
        public Context mContext;
        public Drawable mDrawable;

        public final int getChangingConfigurations() {
            return 0;
        }

        public final Drawable newDrawable() {
            AdaptiveIcon adaptiveIcon = new AdaptiveIcon(this.mContext, this.mDrawable);
            adaptiveIcon.setBackgroundColor(this.mColor);
            return adaptiveIcon;
        }

        public AdaptiveConstantState(Context context, Drawable drawable) {
            this.mContext = context;
            this.mDrawable = drawable;
        }
    }

    public AdaptiveIcon(Context context, Drawable drawable) {
        super(new Drawable[]{new AdaptiveIconShapeDrawable(context.getResources()), drawable});
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(C1777R.dimen.dashboard_tile_foreground_image_inset);
        setLayerInset(1, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        this.mAdaptiveConstantState = new AdaptiveConstantState(context, drawable);
    }

    public final void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        getDrawable(0).setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        StringBuilder sb = new StringBuilder();
        sb.append("Setting background color ");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(sb, this.mBackgroundColor, "AdaptiveHomepageIcon");
        this.mAdaptiveConstantState.mColor = i;
    }

    public final Drawable.ConstantState getConstantState() {
        return this.mAdaptiveConstantState;
    }
}
