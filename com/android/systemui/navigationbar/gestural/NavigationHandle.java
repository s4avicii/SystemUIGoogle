package com.android.systemui.navigationbar.gestural;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.navigationbar.buttons.ButtonInterface;

public class NavigationHandle extends View implements ButtonInterface {
    public final int mBottom;
    public final int mDarkColor;
    public final int mLightColor;
    public final Paint mPaint;
    public final int mRadius;
    public boolean mRequiresInvalidate;

    public NavigationHandle(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void abortCurrentGesture() {
    }

    public final void setImageDrawable(Drawable drawable) {
    }

    public final void setVertical(boolean z) {
    }

    public NavigationHandle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mPaint = paint;
        Resources resources = context.getResources();
        this.mRadius = resources.getDimensionPixelSize(C1777R.dimen.navigation_handle_radius);
        this.mBottom = resources.getDimensionPixelSize(C1777R.dimen.navigation_handle_bottom);
        int themeAttr = Utils.getThemeAttr(context, C1777R.attr.darkIconTheme);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(context, C1777R.attr.lightIconTheme));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, themeAttr);
        this.mLightColor = Utils.getColorAttrDefaultColor(contextThemeWrapper, C1777R.attr.homeHandleColor);
        this.mDarkColor = Utils.getColorAttrDefaultColor(contextThemeWrapper2, C1777R.attr.homeHandleColor);
        paint.setAntiAlias(true);
        setFocusable(false);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int i = this.mRadius * 2;
        int width = getWidth();
        int i2 = (height - this.mBottom) - i;
        float f = (float) (i2 + i);
        int i3 = this.mRadius;
        canvas.drawRoundRect(0.0f, (float) i2, (float) width, f, (float) i3, (float) i3, this.mPaint);
    }

    public final void setAlpha(float f) {
        super.setAlpha(f);
        if (f > 0.0f && this.mRequiresInvalidate) {
            this.mRequiresInvalidate = false;
            invalidate();
        }
    }

    public final void setDarkIntensity(float f) {
        int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(this.mLightColor), Integer.valueOf(this.mDarkColor))).intValue();
        if (this.mPaint.getColor() != intValue) {
            this.mPaint.setColor(intValue);
            if (getVisibility() != 0 || getAlpha() <= 0.0f) {
                this.mRequiresInvalidate = true;
            } else {
                invalidate();
            }
        }
    }
}
