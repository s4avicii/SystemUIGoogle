package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.animation.PathInterpolator;
import androidx.annotation.Keep;
import androidx.recyclerview.R$styleable;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.animation.Interpolators;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;

@Keep
/* compiled from: LightSourceDrawable.kt */
public final class LightSourceDrawable extends Drawable {
    private boolean active;
    private int highlightColor = -1;
    private Paint paint = new Paint();
    private boolean pressed;
    /* access modifiers changed from: private */
    public Animator rippleAnimation;
    /* access modifiers changed from: private */
    public final RippleData rippleData = new RippleData();
    private int[] themeAttrs;

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        if (typedArray.hasValue(3)) {
            RippleData rippleData2 = this.rippleData;
            float dimension = typedArray.getDimension(3, 0.0f);
            Objects.requireNonNull(rippleData2);
            rippleData2.minSize = dimension;
        }
        if (typedArray.hasValue(2)) {
            RippleData rippleData3 = this.rippleData;
            float dimension2 = typedArray.getDimension(2, 0.0f);
            Objects.requireNonNull(rippleData3);
            rippleData3.maxSize = dimension2;
        }
        if (typedArray.hasValue(1)) {
            RippleData rippleData4 = this.rippleData;
            Objects.requireNonNull(rippleData4);
            rippleData4.highlight = ((float) typedArray.getInteger(1, 0)) / 100.0f;
        }
    }

    public int getOpacity() {
        return -2;
    }

    public void getOutline(Outline outline) {
    }

    public boolean hasFocusStateSpecified() {
        return true;
    }

    public boolean isProjected() {
        return true;
    }

    public boolean isStateful() {
        return true;
    }

    private final void illuminate() {
        RippleData rippleData2 = this.rippleData;
        Objects.requireNonNull(rippleData2);
        rippleData2.alpha = 1.0f;
        invalidateSelf();
        Animator animator = this.rippleAnimation;
        if (animator != null) {
            animator.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        ofFloat.setStartDelay(133);
        ofFloat.setDuration(800 - ofFloat.getStartDelay());
        PathInterpolator pathInterpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        ofFloat.setInterpolator(pathInterpolator);
        ofFloat.addUpdateListener(new LightSourceDrawable$illuminate$1$1$1(this));
        RippleData rippleData3 = this.rippleData;
        Objects.requireNonNull(rippleData3);
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{rippleData3.progress, 1.0f});
        ofFloat2.setDuration(800);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.addUpdateListener(new LightSourceDrawable$illuminate$1$2$1(this));
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.addListener(new LightSourceDrawable$illuminate$1$3(this));
        animatorSet.start();
        this.rippleAnimation = animatorSet;
    }

    private final void setActive(boolean z) {
        if (z != this.active) {
            this.active = z;
            if (z) {
                Animator animator = this.rippleAnimation;
                if (animator != null) {
                    animator.cancel();
                }
                RippleData rippleData2 = this.rippleData;
                Objects.requireNonNull(rippleData2);
                rippleData2.alpha = 1.0f;
                RippleData rippleData3 = this.rippleData;
                Objects.requireNonNull(rippleData3);
                rippleData3.progress = 0.05f;
            } else {
                Animator animator2 = this.rippleAnimation;
                if (animator2 != null) {
                    animator2.cancel();
                }
                RippleData rippleData4 = this.rippleData;
                Objects.requireNonNull(rippleData4);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{rippleData4.alpha, 0.0f});
                ofFloat.setDuration(200);
                ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                ofFloat.addUpdateListener(new LightSourceDrawable$active$1$1(this));
                ofFloat.addListener(new LightSourceDrawable$active$1$2(this));
                ofFloat.start();
                this.rippleAnimation = ofFloat;
            }
            invalidateSelf();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0008, code lost:
        if (r0.length <= 0) goto L_0x000a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean canApplyTheme() {
        /*
            r1 = this;
            int[] r0 = r1.themeAttrs
            if (r0 == 0) goto L_0x000a
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            int r0 = r0.length
            if (r0 > 0) goto L_0x0010
        L_0x000a:
            boolean r1 = super.canApplyTheme()
            if (r1 == 0) goto L_0x0012
        L_0x0010:
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.LightSourceDrawable.canApplyTheme():boolean");
    }

    public void draw(Canvas canvas) {
        RippleData rippleData2 = this.rippleData;
        Objects.requireNonNull(rippleData2);
        float f = rippleData2.minSize;
        RippleData rippleData3 = this.rippleData;
        Objects.requireNonNull(rippleData3);
        float f2 = rippleData3.maxSize;
        RippleData rippleData4 = this.rippleData;
        Objects.requireNonNull(rippleData4);
        float lerp = MathUtils.lerp(f, f2, rippleData4.progress);
        int i = this.highlightColor;
        RippleData rippleData5 = this.rippleData;
        Objects.requireNonNull(rippleData5);
        int alphaComponent = ColorUtils.setAlphaComponent(i, (int) (rippleData5.alpha * ((float) 255)));
        Paint paint2 = this.paint;
        RippleData rippleData6 = this.rippleData;
        Objects.requireNonNull(rippleData6);
        float f3 = rippleData6.f59x;
        RippleData rippleData7 = this.rippleData;
        Objects.requireNonNull(rippleData7);
        float f4 = lerp;
        paint2.setShader(new RadialGradient(f3, rippleData7.f60y, f4, new int[]{alphaComponent, 0}, R$styleable.GRADIENT_STOPS, Shader.TileMode.CLAMP));
        RippleData rippleData8 = this.rippleData;
        Objects.requireNonNull(rippleData8);
        float f5 = rippleData8.f59x;
        RippleData rippleData9 = this.rippleData;
        Objects.requireNonNull(rippleData9);
        canvas.drawCircle(f5, rippleData9.f60y, lerp, this.paint);
    }

    public Rect getDirtyBounds() {
        RippleData rippleData2 = this.rippleData;
        Objects.requireNonNull(rippleData2);
        float f = rippleData2.minSize;
        RippleData rippleData3 = this.rippleData;
        Objects.requireNonNull(rippleData3);
        float f2 = rippleData3.maxSize;
        RippleData rippleData4 = this.rippleData;
        Objects.requireNonNull(rippleData4);
        float lerp = MathUtils.lerp(f, f2, rippleData4.progress);
        RippleData rippleData5 = this.rippleData;
        Objects.requireNonNull(rippleData5);
        RippleData rippleData6 = this.rippleData;
        Objects.requireNonNull(rippleData6);
        RippleData rippleData7 = this.rippleData;
        Objects.requireNonNull(rippleData7);
        RippleData rippleData8 = this.rippleData;
        Objects.requireNonNull(rippleData8);
        Rect rect = new Rect((int) (rippleData5.f59x - lerp), (int) (rippleData6.f60y - lerp), (int) (rippleData7.f59x + lerp), (int) (rippleData8.f60y + lerp));
        rect.union(super.getDirtyBounds());
        return rect;
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, com.android.systemui.R$styleable.IlluminationDrawable);
        this.themeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
    }

    public void setAlpha(int i) {
        if (i != this.paint.getAlpha()) {
            this.paint.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setHighlightColor(int i) {
        if (this.highlightColor != i) {
            this.highlightColor = i;
            invalidateSelf();
        }
    }

    public void setHotspot(float f, float f2) {
        RippleData rippleData2 = this.rippleData;
        Objects.requireNonNull(rippleData2);
        rippleData2.f59x = f;
        RippleData rippleData3 = this.rippleData;
        Objects.requireNonNull(rippleData3);
        rippleData3.f60y = f2;
        if (this.active) {
            invalidateSelf();
        }
    }

    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, com.android.systemui.R$styleable.IlluminationDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
    }

    public boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        if (iArr == null) {
            return onStateChange;
        }
        boolean z = this.pressed;
        boolean z2 = false;
        this.pressed = false;
        int length = iArr.length;
        int i = 0;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (i < length) {
            int i2 = iArr[i];
            i++;
            switch (i2) {
                case 16842908:
                    z4 = true;
                    break;
                case 16842910:
                    z3 = true;
                    break;
                case 16842919:
                    this.pressed = true;
                    break;
                case 16843623:
                    z5 = true;
                    break;
            }
        }
        if (z3 && (this.pressed || z4 || z5)) {
            z2 = true;
        }
        setActive(z2);
        if (z && !this.pressed) {
            illuminate();
        }
        return onStateChange;
    }

    public final int getHighlightColor() {
        return this.highlightColor;
    }
}
