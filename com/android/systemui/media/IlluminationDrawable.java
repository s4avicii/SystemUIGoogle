package com.android.systemui.media;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import androidx.annotation.Keep;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R$styleable;
import com.android.systemui.animation.Interpolators;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

@Keep
/* compiled from: IlluminationDrawable.kt */
public final class IlluminationDrawable extends Drawable {
    /* access modifiers changed from: private */
    public ValueAnimator backgroundAnimation;
    /* access modifiers changed from: private */
    public int backgroundColor;
    private float cornerRadius;
    private float cornerRadiusOverride = -1.0f;
    private float highlight;
    /* access modifiers changed from: private */
    public int highlightColor;
    /* access modifiers changed from: private */
    public final ArrayList<LightSourceDrawable> lightSources = new ArrayList<>();
    /* access modifiers changed from: private */
    public Paint paint = new Paint();
    private int[] themeAttrs;
    private float[] tmpHsl = {0.0f, 0.0f, 0.0f};

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        if (typedArray.hasValue(0)) {
            this.cornerRadius = typedArray.getDimension(0, getCornerRadius());
        }
        if (typedArray.hasValue(1)) {
            this.highlight = ((float) typedArray.getInteger(1, 0)) / 100.0f;
        }
    }

    public int getOpacity() {
        return -2;
    }

    public final void registerLightSource(View view) {
        if (view.getBackground() instanceof LightSourceDrawable) {
            Drawable background = view.getBackground();
            Objects.requireNonNull(background, "null cannot be cast to non-null type com.android.systemui.media.LightSourceDrawable");
            registerLightSource((LightSourceDrawable) background);
        } else if (view.getForeground() instanceof LightSourceDrawable) {
            Drawable foreground = view.getForeground();
            Objects.requireNonNull(foreground, "null cannot be cast to non-null type com.android.systemui.media.LightSourceDrawable");
            registerLightSource((LightSourceDrawable) foreground);
        }
    }

    private final void animateBackground() {
        float f;
        ColorUtils.colorToHSL(this.backgroundColor, this.tmpHsl);
        float[] fArr = this.tmpHsl;
        float f2 = fArr[2];
        float f3 = this.highlight;
        if (f2 < 1.0f - f3) {
            f = f2 + f3;
        } else {
            f = f2 - f3;
        }
        fArr[2] = MathUtils.constrain(f, 0.0f, 1.0f);
        int color = this.paint.getColor();
        int i = this.highlightColor;
        int HSLToColor = ColorUtils.HSLToColor(this.tmpHsl);
        ValueAnimator valueAnimator = this.backgroundAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.setDuration(370);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        ofFloat.addUpdateListener(new IlluminationDrawable$animateBackground$1$1(this, color, i, HSLToColor));
        ofFloat.addListener(new IlluminationDrawable$animateBackground$1$2(this));
        ofFloat.start();
        this.backgroundAnimation = ofFloat;
    }

    private final void setBackgroundColor(int i) {
        if (i != this.backgroundColor) {
            this.backgroundColor = i;
            animateBackground();
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.IlluminationDrawable.canApplyTheme():boolean");
    }

    public int getAlpha() {
        return this.paint.getAlpha();
    }

    public final float getCornerRadius() {
        float f = this.cornerRadiusOverride;
        if (f >= 0.0f) {
            return f;
        }
        return this.cornerRadius;
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        this.themeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
    }

    public void setAlpha(int i) {
        if (i != this.paint.getAlpha()) {
            this.paint.setAlpha(i);
            invalidateSelf();
            for (LightSourceDrawable alpha : this.lightSources) {
                alpha.setAlpha(i);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setCornerRadiusOverride(Float f) {
        float f2;
        if (f == null) {
            f2 = -1.0f;
        } else {
            f2 = f.floatValue();
        }
        this.cornerRadiusOverride = f2;
    }

    public void setXfermode(Xfermode xfermode) {
        if (!Intrinsics.areEqual(xfermode, this.paint.getXfermode())) {
            this.paint.setXfermode(xfermode);
            invalidateSelf();
        }
    }

    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, R$styleable.IlluminationDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRoundRect(0.0f, 0.0f, (float) getBounds().width(), (float) getBounds().height(), getCornerRadius(), getCornerRadius(), this.paint);
    }

    public void getOutline(Outline outline) {
        outline.setRoundRect(getBounds(), getCornerRadius());
    }

    public void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        Intrinsics.checkNotNull(colorStateList);
        setBackgroundColor(colorStateList.getDefaultColor());
    }

    private final void registerLightSource(LightSourceDrawable lightSourceDrawable) {
        lightSourceDrawable.setAlpha(this.paint.getAlpha());
        this.lightSources.add(lightSourceDrawable);
    }

    public final void setCornerRadius(float f) {
        this.cornerRadius = f;
    }
}
