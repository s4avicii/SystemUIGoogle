package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.provider.Settings;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.leanback.R$string;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;
import java.util.Objects;

public final class DeterminateDrawable<S extends BaseProgressIndicatorSpec> extends DrawableWithAnimatedVisibilityChange {
    public static final C20671 INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat<DeterminateDrawable>() {
        public final float getValue(Object obj) {
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) obj;
            Objects.requireNonNull(determinateDrawable);
            return determinateDrawable.indicatorFraction * 10000.0f;
        }

        public final void setValue(Object obj, float f) {
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) obj;
            Objects.requireNonNull(determinateDrawable);
            determinateDrawable.indicatorFraction = f / 10000.0f;
            determinateDrawable.invalidateSelf();
        }
    };
    public DrawingDelegate<S> drawingDelegate;
    public float indicatorFraction;
    public boolean skipAnimationOnLevelChange = false;
    public final SpringAnimation springAnimation;
    public final SpringForce springForce;

    public final void draw(Canvas canvas) {
        Rect rect = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(rect)) {
            canvas.save();
            DrawingDelegate<S> drawingDelegate2 = this.drawingDelegate;
            float growFraction = getGrowFraction();
            Objects.requireNonNull(drawingDelegate2);
            drawingDelegate2.spec.validateSpec();
            drawingDelegate2.adjustCanvas(canvas, growFraction);
            this.drawingDelegate.fillTrack(canvas, this.paint);
            Canvas canvas2 = canvas;
            this.drawingDelegate.fillIndicator(canvas2, this.paint, 0.0f, this.indicatorFraction, R$string.compositeARGBWithAlpha(this.baseSpec.indicatorColors[0], this.totalAlpha));
            canvas.restore();
        }
    }

    public final int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    public final int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public final void jumpToCurrentState() {
        this.springAnimation.skipToEnd();
        this.indicatorFraction = ((float) getLevel()) / 10000.0f;
        invalidateSelf();
    }

    public final boolean onLevelChange(int i) {
        if (this.skipAnimationOnLevelChange) {
            this.springAnimation.skipToEnd();
            this.indicatorFraction = ((float) i) / 10000.0f;
            invalidateSelf();
        } else {
            SpringAnimation springAnimation2 = this.springAnimation;
            Objects.requireNonNull(springAnimation2);
            springAnimation2.mValue = this.indicatorFraction * 10000.0f;
            springAnimation2.mStartValueIsSet = true;
            this.springAnimation.animateToFinalPosition((float) i);
        }
        return true;
    }

    public DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseProgressIndicatorSpec, DrawingDelegate<S> drawingDelegate2) {
        super(context, baseProgressIndicatorSpec);
        this.drawingDelegate = drawingDelegate2;
        drawingDelegate2.drawable = this;
        SpringForce springForce2 = new SpringForce();
        this.springForce = springForce2;
        springForce2.setDampingRatio(1.0f);
        springForce2.setStiffness(50.0f);
        SpringAnimation springAnimation2 = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
        this.springAnimation = springAnimation2;
        springAnimation2.mSpring = springForce2;
        if (this.growFraction != 1.0f) {
            this.growFraction = 1.0f;
            invalidateSelf();
        }
    }

    public final boolean setVisibleInternal(boolean z, boolean z2, boolean z3) {
        boolean visibleInternal = super.setVisibleInternal(z, z2, z3);
        AnimatorDurationScaleProvider animatorDurationScaleProvider = this.animatorDurationScaleProvider;
        ContentResolver contentResolver = this.context.getContentResolver();
        Objects.requireNonNull(animatorDurationScaleProvider);
        float f = Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        if (f == 0.0f) {
            this.skipAnimationOnLevelChange = true;
        } else {
            this.skipAnimationOnLevelChange = false;
            this.springForce.setStiffness(50.0f / f);
        }
        return visibleInternal;
    }
}
