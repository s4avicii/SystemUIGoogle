package com.android.p012wm.shell.bubbles.animation;

import android.graphics.Matrix;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* renamed from: com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix */
public final class AnimatableScaleMatrix extends Matrix {
    public static final C18231 SCALE_X = new FloatPropertyCompat<AnimatableScaleMatrix>() {
        public final float getValue(Object obj) {
            return ((AnimatableScaleMatrix) obj).mScaleX * 499.99997f;
        }

        public final void setValue(Object obj, float f) {
            ((AnimatableScaleMatrix) obj).setScaleX(f * 0.002f);
        }
    };
    public static final C18242 SCALE_Y = new FloatPropertyCompat<AnimatableScaleMatrix>() {
        public final float getValue(Object obj) {
            return ((AnimatableScaleMatrix) obj).mScaleY * 499.99997f;
        }

        public final void setValue(Object obj, float f) {
            ((AnimatableScaleMatrix) obj).setScaleY(f * 0.002f);
        }
    };
    public float mPivotX = 0.0f;
    public float mPivotY = 0.0f;
    public float mScaleX = 1.0f;
    public float mScaleY = 1.0f;

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public final void setScale(float f, float f2, float f3, float f4) {
        this.mScaleX = f;
        this.mScaleY = f2;
        this.mPivotX = f3;
        this.mPivotY = f4;
        super.setScale(f, f2, f3, f4);
    }

    public final void setScaleX(float f) {
        this.mScaleX = f;
        super.setScale(f, this.mScaleY, this.mPivotX, this.mPivotY);
    }

    public final void setScaleY(float f) {
        this.mScaleY = f;
        super.setScale(this.mScaleX, f, this.mPivotX, this.mPivotY);
    }
}
