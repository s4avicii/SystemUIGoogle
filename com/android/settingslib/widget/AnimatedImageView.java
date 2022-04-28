package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class AnimatedImageView extends ImageView {
    public AnimatedRotateDrawable mDrawable;

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDrawable != null) {
            getVisibility();
            this.mDrawable.stop();
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mDrawable != null) {
            getVisibility();
            this.mDrawable.stop();
        }
    }

    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mDrawable != null) {
            getVisibility();
            this.mDrawable.stop();
        }
    }

    public final void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateDrawable();
    }

    public final void setImageResource(int i) {
        super.setImageResource(i);
        updateDrawable();
    }

    public final void updateDrawable() {
        AnimatedRotateDrawable animatedRotateDrawable;
        if (isShown() && (animatedRotateDrawable = this.mDrawable) != null) {
            animatedRotateDrawable.stop();
        }
        AnimatedRotateDrawable drawable = getDrawable();
        if (drawable instanceof AnimatedRotateDrawable) {
            AnimatedRotateDrawable animatedRotateDrawable2 = drawable;
            this.mDrawable = animatedRotateDrawable2;
            animatedRotateDrawable2.setFramesCount(56);
            this.mDrawable.setFramesDuration(32);
            isShown();
            return;
        }
        this.mDrawable = null;
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
