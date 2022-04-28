package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.systemui.R$styleable;

@RemoteViews.RemoteView
public class AnimatedImageView extends ImageView {
    public boolean mAllowAnimation;
    public AnimationDrawable mAnim;
    public boolean mAttached;
    public int mDrawableId;
    public final boolean mHasOverlappingRendering;

    public AnimatedImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAllowAnimation = true;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.AnimatedImageView, 0, 0);
        try {
            this.mHasOverlappingRendering = obtainStyledAttributes.getBoolean(0, true);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public final void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mDrawableId = 0;
        } else if (this.mDrawableId != drawable.hashCode()) {
            this.mDrawableId = drawable.hashCode();
        } else {
            return;
        }
        super.setImageDrawable(drawable);
        updateAnim();
    }

    @RemotableViewMethod
    public final void setImageResource(int i) {
        if (this.mDrawableId != i) {
            this.mDrawableId = i;
            super.setImageResource(i);
            updateAnim();
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttached = true;
        updateAnim();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimationDrawable animationDrawable = this.mAnim;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        this.mAttached = false;
    }

    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mAnim == null) {
            return;
        }
        if (!isShown() || !this.mAllowAnimation) {
            this.mAnim.stop();
        } else {
            this.mAnim.start();
        }
    }

    public final void updateAnim() {
        AnimationDrawable animationDrawable;
        Drawable drawable = getDrawable();
        if (this.mAttached && (animationDrawable = this.mAnim) != null) {
            animationDrawable.stop();
        }
        if (drawable instanceof AnimationDrawable) {
            this.mAnim = (AnimationDrawable) drawable;
            if (isShown() && this.mAllowAnimation) {
                this.mAnim.start();
                return;
            }
            return;
        }
        this.mAnim = null;
    }

    public boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }
}
