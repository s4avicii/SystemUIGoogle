package com.google.android.material.chip;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Objects;

public final class ChipDrawable extends MaterialShapeDrawable implements Drawable.Callback, TextDrawableHelper.TextDrawableDelegate {
    public static final int[] DEFAULT_STATE = {16842910};
    public static final ShapeDrawable closeIconRippleMask = new ShapeDrawable(new OvalShape());
    public int alpha = 255;
    public boolean checkable;
    public Drawable checkedIcon;
    public ColorStateList checkedIconTint;
    public boolean checkedIconVisible;
    public ColorStateList chipBackgroundColor;
    public float chipCornerRadius = -1.0f;
    public float chipEndPadding;
    public Drawable chipIcon;
    public float chipIconSize;
    public ColorStateList chipIconTint;
    public boolean chipIconVisible;
    public float chipMinHeight;
    public final Paint chipPaint = new Paint(1);
    public float chipStartPadding;
    public ColorStateList chipStrokeColor;
    public float chipStrokeWidth;
    public ColorStateList chipSurfaceColor;
    public Drawable closeIcon;
    public float closeIconEndPadding;
    public RippleDrawable closeIconRipple;
    public float closeIconSize;
    public float closeIconStartPadding;
    public int[] closeIconStateSet;
    public ColorStateList closeIconTint;
    public boolean closeIconVisible;
    public ColorFilter colorFilter;
    public ColorStateList compatRippleColor;
    public final Context context;
    public boolean currentChecked;
    public int currentChipBackgroundColor;
    public int currentChipStrokeColor;
    public int currentChipSurfaceColor;
    public int currentCompatRippleColor;
    public int currentCompositeSurfaceBackgroundColor;
    public int currentTextColor;
    public int currentTint;
    public WeakReference<Delegate> delegate = new WeakReference<>((Object) null);
    public final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    public boolean hasChipIconTint;
    public float iconEndPadding;
    public float iconStartPadding;
    public boolean isShapeThemingEnabled;
    public int maxWidth;
    public final PointF pointF = new PointF();
    public final RectF rectF = new RectF();
    public ColorStateList rippleColor;
    public final Path shapePath = new Path();
    public boolean shouldDrawText;
    public CharSequence text;
    public final TextDrawableHelper textDrawableHelper;
    public float textEndPadding;
    public float textStartPadding;
    public ColorStateList tint;
    public PorterDuffColorFilter tintFilter;
    public PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
    public TextUtils.TruncateAt truncateAt;
    public boolean useCompatRipple;

    public interface Delegate {
        void onChipDrawableSizeChange();
    }

    public final int getOpacity() {
        return -3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
        r0 = r0.textColor;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isStateful() {
        /*
            r3 = this;
            android.content.res.ColorStateList r0 = r3.chipSurfaceColor
            boolean r0 = isStateful((android.content.res.ColorStateList) r0)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0069
            android.content.res.ColorStateList r0 = r3.chipBackgroundColor
            boolean r0 = isStateful((android.content.res.ColorStateList) r0)
            if (r0 != 0) goto L_0x0069
            android.content.res.ColorStateList r0 = r3.chipStrokeColor
            boolean r0 = isStateful((android.content.res.ColorStateList) r0)
            if (r0 != 0) goto L_0x0069
            boolean r0 = r3.useCompatRipple
            if (r0 == 0) goto L_0x0026
            android.content.res.ColorStateList r0 = r3.compatRippleColor
            boolean r0 = isStateful((android.content.res.ColorStateList) r0)
            if (r0 != 0) goto L_0x0069
        L_0x0026:
            com.google.android.material.internal.TextDrawableHelper r0 = r3.textDrawableHelper
            java.util.Objects.requireNonNull(r0)
            com.google.android.material.resources.TextAppearance r0 = r0.textAppearance
            if (r0 == 0) goto L_0x003b
            android.content.res.ColorStateList r0 = r0.textColor
            if (r0 == 0) goto L_0x003b
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x003b
            r0 = r1
            goto L_0x003c
        L_0x003b:
            r0 = r2
        L_0x003c:
            if (r0 != 0) goto L_0x0069
            boolean r0 = r3.checkedIconVisible
            if (r0 == 0) goto L_0x004c
            android.graphics.drawable.Drawable r0 = r3.checkedIcon
            if (r0 == 0) goto L_0x004c
            boolean r0 = r3.checkable
            if (r0 == 0) goto L_0x004c
            r0 = r1
            goto L_0x004d
        L_0x004c:
            r0 = r2
        L_0x004d:
            if (r0 != 0) goto L_0x0069
            android.graphics.drawable.Drawable r0 = r3.chipIcon
            boolean r0 = isStateful((android.graphics.drawable.Drawable) r0)
            if (r0 != 0) goto L_0x0069
            android.graphics.drawable.Drawable r0 = r3.checkedIcon
            boolean r0 = isStateful((android.graphics.drawable.Drawable) r0)
            if (r0 != 0) goto L_0x0069
            android.content.res.ColorStateList r3 = r3.tint
            boolean r3 = isStateful((android.content.res.ColorStateList) r3)
            if (r3 == 0) goto L_0x0068
            goto L_0x0069
        L_0x0068:
            r1 = r2
        L_0x0069:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.ChipDrawable.isStateful():boolean");
    }

    public final boolean onStateChange(int[] iArr) {
        if (this.isShapeThemingEnabled) {
            super.onStateChange(iArr);
        }
        return onStateChange(iArr, this.closeIconStateSet);
    }

    public static void unapplyChildDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
    }

    public final void applyChildDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            drawable.setLevel(getLevel());
            drawable.setVisible(isVisible(), false);
            if (drawable == this.closeIcon) {
                if (drawable.isStateful()) {
                    drawable.setState(this.closeIconStateSet);
                }
                drawable.setTintList(this.closeIconTint);
                return;
            }
            Drawable drawable2 = this.chipIcon;
            if (drawable == drawable2 && this.hasChipIconTint) {
                drawable2.setTintList(this.chipIconTint);
            }
            if (drawable.isStateful()) {
                drawable.setState(getState());
            }
        }
    }

    public final void draw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        Canvas canvas2 = canvas;
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && (i = this.alpha) != 0) {
            if (i < 255) {
                i2 = canvas.saveLayerAlpha((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, i);
            } else {
                i2 = 0;
            }
            if (!this.isShapeThemingEnabled) {
                this.chipPaint.setColor(this.currentChipSurfaceColor);
                this.chipPaint.setStyle(Paint.Style.FILL);
                this.rectF.set(bounds);
                canvas2.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
            }
            if (!this.isShapeThemingEnabled) {
                this.chipPaint.setColor(this.currentChipBackgroundColor);
                this.chipPaint.setStyle(Paint.Style.FILL);
                Paint paint = this.chipPaint;
                ColorFilter colorFilter2 = this.colorFilter;
                if (colorFilter2 == null) {
                    colorFilter2 = this.tintFilter;
                }
                paint.setColorFilter(colorFilter2);
                this.rectF.set(bounds);
                canvas2.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
            }
            if (this.isShapeThemingEnabled) {
                super.draw(canvas);
            }
            if (this.chipStrokeWidth > 0.0f && !this.isShapeThemingEnabled) {
                this.chipPaint.setColor(this.currentChipStrokeColor);
                this.chipPaint.setStyle(Paint.Style.STROKE);
                if (!this.isShapeThemingEnabled) {
                    Paint paint2 = this.chipPaint;
                    ColorFilter colorFilter3 = this.colorFilter;
                    if (colorFilter3 == null) {
                        colorFilter3 = this.tintFilter;
                    }
                    paint2.setColorFilter(colorFilter3);
                }
                RectF rectF2 = this.rectF;
                float f = this.chipStrokeWidth / 2.0f;
                rectF2.set(((float) bounds.left) + f, ((float) bounds.top) + f, ((float) bounds.right) - f, ((float) bounds.bottom) - f);
                float f2 = this.chipCornerRadius - (this.chipStrokeWidth / 2.0f);
                canvas2.drawRoundRect(this.rectF, f2, f2, this.chipPaint);
            }
            this.chipPaint.setColor(this.currentCompatRippleColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.rectF.set(bounds);
            if (!this.isShapeThemingEnabled) {
                canvas2.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
            } else {
                RectF rectF3 = new RectF(bounds);
                Path path = this.shapePath;
                ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
                MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
                shapeAppearancePathProvider.calculatePath(materialShapeDrawableState.shapeAppearanceModel, materialShapeDrawableState.interpolation, rectF3, this.pathShadowListener, path);
                drawShape(canvas, this.chipPaint, this.shapePath, this.drawableState.shapeAppearanceModel, getBoundsAsRectF());
            }
            if (showsChipIcon()) {
                calculateChipIconBounds(bounds, this.rectF);
                RectF rectF4 = this.rectF;
                float f3 = rectF4.left;
                float f4 = rectF4.top;
                canvas2.translate(f3, f4);
                this.chipIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
                this.chipIcon.draw(canvas2);
                canvas2.translate(-f3, -f4);
            }
            if (showsCheckedIcon()) {
                calculateChipIconBounds(bounds, this.rectF);
                RectF rectF5 = this.rectF;
                float f5 = rectF5.left;
                float f6 = rectF5.top;
                canvas2.translate(f5, f6);
                this.checkedIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
                this.checkedIcon.draw(canvas2);
                canvas2.translate(-f5, -f6);
            }
            if (!this.shouldDrawText || this.text == null) {
                i3 = i2;
                i4 = 0;
                i5 = 255;
            } else {
                PointF pointF2 = this.pointF;
                pointF2.set(0.0f, 0.0f);
                Paint.Align align = Paint.Align.LEFT;
                if (this.text != null) {
                    float calculateChipIconWidth = calculateChipIconWidth() + this.chipStartPadding + this.textStartPadding;
                    if (getLayoutDirection() == 0) {
                        pointF2.x = ((float) bounds.left) + calculateChipIconWidth;
                        align = Paint.Align.LEFT;
                    } else {
                        pointF2.x = ((float) bounds.right) - calculateChipIconWidth;
                        align = Paint.Align.RIGHT;
                    }
                    TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
                    Objects.requireNonNull(textDrawableHelper2);
                    textDrawableHelper2.textPaint.getFontMetrics(this.fontMetrics);
                    Paint.FontMetrics fontMetrics2 = this.fontMetrics;
                    pointF2.y = ((float) bounds.centerY()) - ((fontMetrics2.descent + fontMetrics2.ascent) / 2.0f);
                }
                RectF rectF6 = this.rectF;
                rectF6.setEmpty();
                if (this.text != null) {
                    float calculateChipIconWidth2 = calculateChipIconWidth() + this.chipStartPadding + this.textStartPadding;
                    float calculateCloseIconWidth = calculateCloseIconWidth() + this.chipEndPadding + this.textEndPadding;
                    if (getLayoutDirection() == 0) {
                        rectF6.left = ((float) bounds.left) + calculateChipIconWidth2;
                        rectF6.right = ((float) bounds.right) - calculateCloseIconWidth;
                    } else {
                        rectF6.left = ((float) bounds.left) + calculateCloseIconWidth;
                        rectF6.right = ((float) bounds.right) - calculateChipIconWidth2;
                    }
                    rectF6.top = (float) bounds.top;
                    rectF6.bottom = (float) bounds.bottom;
                }
                TextDrawableHelper textDrawableHelper3 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper3);
                if (textDrawableHelper3.textAppearance != null) {
                    TextDrawableHelper textDrawableHelper4 = this.textDrawableHelper;
                    Objects.requireNonNull(textDrawableHelper4);
                    textDrawableHelper4.textPaint.drawableState = getState();
                    TextDrawableHelper textDrawableHelper5 = this.textDrawableHelper;
                    Context context2 = this.context;
                    Objects.requireNonNull(textDrawableHelper5);
                    textDrawableHelper5.textAppearance.updateDrawState(context2, textDrawableHelper5.textPaint, textDrawableHelper5.fontCallback);
                }
                TextDrawableHelper textDrawableHelper6 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper6);
                textDrawableHelper6.textPaint.setTextAlign(align);
                if (Math.round(this.textDrawableHelper.getTextWidth(this.text.toString())) > Math.round(this.rectF.width())) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    i6 = canvas.save();
                    canvas2.clipRect(this.rectF);
                } else {
                    i6 = 0;
                }
                CharSequence charSequence = this.text;
                if (z && this.truncateAt != null) {
                    TextDrawableHelper textDrawableHelper7 = this.textDrawableHelper;
                    Objects.requireNonNull(textDrawableHelper7);
                    charSequence = TextUtils.ellipsize(charSequence, textDrawableHelper7.textPaint, this.rectF.width(), this.truncateAt);
                }
                CharSequence charSequence2 = charSequence;
                int length = charSequence2.length();
                PointF pointF3 = this.pointF;
                float f7 = pointF3.x;
                float f8 = pointF3.y;
                TextDrawableHelper textDrawableHelper8 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper8);
                i3 = i2;
                float f9 = f7;
                i4 = 0;
                float f10 = f8;
                i5 = 255;
                canvas.drawText(charSequence2, 0, length, f9, f10, textDrawableHelper8.textPaint);
                if (z) {
                    canvas2.restoreToCount(i6);
                }
            }
            if (showsCloseIcon()) {
                RectF rectF7 = this.rectF;
                rectF7.setEmpty();
                if (showsCloseIcon()) {
                    float f11 = this.chipEndPadding + this.closeIconEndPadding;
                    if (getLayoutDirection() == 0) {
                        float f12 = ((float) bounds.right) - f11;
                        rectF7.right = f12;
                        rectF7.left = f12 - this.closeIconSize;
                    } else {
                        float f13 = ((float) bounds.left) + f11;
                        rectF7.left = f13;
                        rectF7.right = f13 + this.closeIconSize;
                    }
                    float exactCenterY = bounds.exactCenterY();
                    float f14 = this.closeIconSize;
                    float f15 = exactCenterY - (f14 / 2.0f);
                    rectF7.top = f15;
                    rectF7.bottom = f15 + f14;
                }
                RectF rectF8 = this.rectF;
                float f16 = rectF8.left;
                float f17 = rectF8.top;
                canvas2.translate(f16, f17);
                this.closeIcon.setBounds(i4, i4, (int) this.rectF.width(), (int) this.rectF.height());
                this.closeIconRipple.setBounds(this.closeIcon.getBounds());
                this.closeIconRipple.jumpToCurrentState();
                this.closeIconRipple.draw(canvas2);
                canvas2.translate(-f16, -f17);
            }
            if (this.alpha < i5) {
                canvas2.restoreToCount(i3);
            }
        }
    }

    public final float getChipCornerRadius() {
        if (this.isShapeThemingEnabled) {
            return getTopLeftCornerResolvedSize();
        }
        return this.chipCornerRadius;
    }

    public final int getIntrinsicHeight() {
        return (int) this.chipMinHeight;
    }

    public final int getIntrinsicWidth() {
        return Math.min(Math.round(calculateCloseIconWidth() + this.textDrawableHelper.getTextWidth(this.text.toString()) + calculateChipIconWidth() + this.chipStartPadding + this.textStartPadding + this.textEndPadding + this.chipEndPadding), this.maxWidth);
    }

    @TargetApi(21)
    public final void getOutline(Outline outline) {
        if (this.isShapeThemingEnabled) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            outline.setRoundRect(bounds, this.chipCornerRadius);
        } else {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), (int) this.chipMinHeight, this.chipCornerRadius);
        }
        outline.setAlpha(((float) this.alpha) / 255.0f);
    }

    public final void onSizeChange() {
        Delegate delegate2 = this.delegate.get();
        if (delegate2 != null) {
            delegate2.onChipDrawableSizeChange();
        }
    }

    public final void setAlpha(int i) {
        if (this.alpha != i) {
            this.alpha = i;
            invalidateSelf();
        }
    }

    public final void setCheckedIconVisible(boolean z) {
        boolean z2;
        if (this.checkedIconVisible != z) {
            boolean showsCheckedIcon = showsCheckedIcon();
            this.checkedIconVisible = z;
            boolean showsCheckedIcon2 = showsCheckedIcon();
            if (showsCheckedIcon != showsCheckedIcon2) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (showsCheckedIcon2) {
                    applyChildDrawable(this.checkedIcon);
                } else {
                    unapplyChildDrawable(this.checkedIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setChipIconVisible(boolean z) {
        boolean z2;
        if (this.chipIconVisible != z) {
            boolean showsChipIcon = showsChipIcon();
            this.chipIconVisible = z;
            boolean showsChipIcon2 = showsChipIcon();
            if (showsChipIcon != showsChipIcon2) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (showsChipIcon2) {
                    applyChildDrawable(this.chipIcon);
                } else {
                    unapplyChildDrawable(this.chipIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setCloseIconVisible(boolean z) {
        boolean z2;
        if (this.closeIconVisible != z) {
            boolean showsCloseIcon = showsCloseIcon();
            this.closeIconVisible = z;
            boolean showsCloseIcon2 = showsCloseIcon();
            if (showsCloseIcon != showsCloseIcon2) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                if (showsCloseIcon2) {
                    applyChildDrawable(this.closeIcon);
                } else {
                    unapplyChildDrawable(this.closeIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setColorFilter(ColorFilter colorFilter2) {
        if (this.colorFilter != colorFilter2) {
            this.colorFilter = colorFilter2;
            invalidateSelf();
        }
    }

    public final void setText(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "";
        }
        if (!TextUtils.equals(this.text, charSequence)) {
            this.text = charSequence;
            TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
            Objects.requireNonNull(textDrawableHelper2);
            textDrawableHelper2.textWidthDirty = true;
            invalidateSelf();
            onSizeChange();
        }
    }

    public final void setTintList(ColorStateList colorStateList) {
        if (this.tint != colorStateList) {
            this.tint = colorStateList;
            onStateChange(getState());
        }
    }

    public final void setTintMode(PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        if (this.tintMode != mode) {
            this.tintMode = mode;
            ColorStateList colorStateList = this.tint;
            if (colorStateList == null || mode == null) {
                porterDuffColorFilter = null;
            } else {
                porterDuffColorFilter = new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
            }
            this.tintFilter = porterDuffColorFilter;
            invalidateSelf();
        }
    }

    public final boolean showsCheckedIcon() {
        if (!this.checkedIconVisible || this.checkedIcon == null || !this.currentChecked) {
            return false;
        }
        return true;
    }

    public final boolean showsChipIcon() {
        if (!this.chipIconVisible || this.chipIcon == null) {
            return false;
        }
        return true;
    }

    public final boolean showsCloseIcon() {
        if (!this.closeIconVisible || this.closeIcon == null) {
            return false;
        }
        return true;
    }

    public ChipDrawable(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i, 2132018647);
        initializeElevationOverlay(context2);
        this.context = context2;
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        this.text = "";
        textDrawableHelper2.textPaint.density = context2.getResources().getDisplayMetrics().density;
        int[] iArr = DEFAULT_STATE;
        setState(iArr);
        if (!Arrays.equals(this.closeIconStateSet, iArr)) {
            this.closeIconStateSet = iArr;
            if (showsCloseIcon()) {
                onStateChange(getState(), iArr);
            }
        }
        this.shouldDrawText = true;
        closeIconRippleMask.setTint(-1);
    }

    public final void calculateChipIconBounds(Rect rect, RectF rectF2) {
        Drawable drawable;
        Drawable drawable2;
        rectF2.setEmpty();
        if (showsChipIcon() || showsCheckedIcon()) {
            float f = this.chipStartPadding + this.iconStartPadding;
            if (this.currentChecked) {
                drawable = this.checkedIcon;
            } else {
                drawable = this.chipIcon;
            }
            float f2 = this.chipIconSize;
            if (f2 <= 0.0f && drawable != null) {
                f2 = (float) drawable.getIntrinsicWidth();
            }
            if (getLayoutDirection() == 0) {
                float f3 = ((float) rect.left) + f;
                rectF2.left = f3;
                rectF2.right = f3 + f2;
            } else {
                float f4 = ((float) rect.right) - f;
                rectF2.right = f4;
                rectF2.left = f4 - f2;
            }
            if (this.currentChecked) {
                drawable2 = this.checkedIcon;
            } else {
                drawable2 = this.chipIcon;
            }
            float f5 = this.chipIconSize;
            if (f5 <= 0.0f && drawable2 != null) {
                float ceil = (float) Math.ceil((double) ViewUtils.dpToPx(this.context, 24));
                if (((float) drawable2.getIntrinsicHeight()) <= ceil) {
                    ceil = (float) drawable2.getIntrinsicHeight();
                }
                f5 = ceil;
            }
            float exactCenterY = rect.exactCenterY() - (f5 / 2.0f);
            rectF2.top = exactCenterY;
            rectF2.bottom = exactCenterY + f5;
        }
    }

    public final float calculateChipIconWidth() {
        Drawable drawable;
        if (!showsChipIcon() && !showsCheckedIcon()) {
            return 0.0f;
        }
        float f = this.iconStartPadding;
        if (this.currentChecked) {
            drawable = this.checkedIcon;
        } else {
            drawable = this.chipIcon;
        }
        float f2 = this.chipIconSize;
        if (f2 <= 0.0f && drawable != null) {
            f2 = (float) drawable.getIntrinsicWidth();
        }
        return f2 + f + this.iconEndPadding;
    }

    public final float calculateCloseIconWidth() {
        if (showsCloseIcon()) {
            return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
        }
        return 0.0f;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final boolean onLayoutDirectionChanged(int i) {
        boolean onLayoutDirectionChanged = super.onLayoutDirectionChanged(i);
        if (showsChipIcon()) {
            onLayoutDirectionChanged |= this.chipIcon.setLayoutDirection(i);
        }
        if (showsCheckedIcon()) {
            onLayoutDirectionChanged |= this.checkedIcon.setLayoutDirection(i);
        }
        if (showsCloseIcon()) {
            onLayoutDirectionChanged |= this.closeIcon.setLayoutDirection(i);
        }
        if (!onLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    public final boolean onLevelChange(int i) {
        boolean onLevelChange = super.onLevelChange(i);
        if (showsChipIcon()) {
            onLevelChange |= this.chipIcon.setLevel(i);
        }
        if (showsCheckedIcon()) {
            onLevelChange |= this.checkedIcon.setLevel(i);
        }
        if (showsCloseIcon()) {
            onLevelChange |= this.closeIcon.setLevel(i);
        }
        if (onLevelChange) {
            invalidateSelf();
        }
        return onLevelChange;
    }

    public final void onTextSizeChange() {
        onSizeChange();
        invalidateSelf();
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (showsChipIcon()) {
            visible |= this.chipIcon.setVisible(z, z2);
        }
        if (showsCheckedIcon()) {
            visible |= this.checkedIcon.setVisible(z, z2);
        }
        if (showsCloseIcon()) {
            visible |= this.closeIcon.setVisible(z, z2);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0168  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onStateChange(int[] r9, int[] r10) {
        /*
            r8 = this;
            boolean r0 = super.onStateChange(r9)
            android.content.res.ColorStateList r1 = r8.chipSurfaceColor
            r2 = 0
            if (r1 == 0) goto L_0x0010
            int r3 = r8.currentChipSurfaceColor
            int r1 = r1.getColorForState(r9, r3)
            goto L_0x0011
        L_0x0010:
            r1 = r2
        L_0x0011:
            int r1 = r8.compositeElevationOverlayIfNeeded(r1)
            int r3 = r8.currentChipSurfaceColor
            r4 = 1
            if (r3 == r1) goto L_0x001d
            r8.currentChipSurfaceColor = r1
            r0 = r4
        L_0x001d:
            android.content.res.ColorStateList r3 = r8.chipBackgroundColor
            if (r3 == 0) goto L_0x0028
            int r5 = r8.currentChipBackgroundColor
            int r3 = r3.getColorForState(r9, r5)
            goto L_0x0029
        L_0x0028:
            r3 = r2
        L_0x0029:
            int r3 = r8.compositeElevationOverlayIfNeeded(r3)
            int r5 = r8.currentChipBackgroundColor
            if (r5 == r3) goto L_0x0034
            r8.currentChipBackgroundColor = r3
            r0 = r4
        L_0x0034:
            int r1 = androidx.core.graphics.ColorUtils.compositeColors(r3, r1)
            int r3 = r8.currentCompositeSurfaceBackgroundColor
            if (r3 == r1) goto L_0x003e
            r3 = r4
            goto L_0x003f
        L_0x003e:
            r3 = r2
        L_0x003f:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r5 = r8.drawableState
            android.content.res.ColorStateList r5 = r5.fillColor
            if (r5 != 0) goto L_0x0047
            r5 = r4
            goto L_0x0048
        L_0x0047:
            r5 = r2
        L_0x0048:
            r3 = r3 | r5
            if (r3 == 0) goto L_0x0055
            r8.currentCompositeSurfaceBackgroundColor = r1
            android.content.res.ColorStateList r0 = android.content.res.ColorStateList.valueOf(r1)
            r8.setFillColor(r0)
            r0 = r4
        L_0x0055:
            android.content.res.ColorStateList r1 = r8.chipStrokeColor
            if (r1 == 0) goto L_0x0060
            int r3 = r8.currentChipStrokeColor
            int r1 = r1.getColorForState(r9, r3)
            goto L_0x0061
        L_0x0060:
            r1 = r2
        L_0x0061:
            int r3 = r8.currentChipStrokeColor
            if (r3 == r1) goto L_0x0068
            r8.currentChipStrokeColor = r1
            r0 = r4
        L_0x0068:
            android.content.res.ColorStateList r1 = r8.compatRippleColor
            if (r1 == 0) goto L_0x007b
            boolean r1 = com.google.android.material.ripple.RippleUtils.shouldDrawRippleCompat(r9)
            if (r1 == 0) goto L_0x007b
            android.content.res.ColorStateList r1 = r8.compatRippleColor
            int r3 = r8.currentCompatRippleColor
            int r1 = r1.getColorForState(r9, r3)
            goto L_0x007c
        L_0x007b:
            r1 = r2
        L_0x007c:
            int r3 = r8.currentCompatRippleColor
            if (r3 == r1) goto L_0x0087
            r8.currentCompatRippleColor = r1
            boolean r1 = r8.useCompatRipple
            if (r1 == 0) goto L_0x0087
            r0 = r4
        L_0x0087:
            com.google.android.material.internal.TextDrawableHelper r1 = r8.textDrawableHelper
            java.util.Objects.requireNonNull(r1)
            com.google.android.material.resources.TextAppearance r1 = r1.textAppearance
            if (r1 == 0) goto L_0x00b1
            com.google.android.material.internal.TextDrawableHelper r1 = r8.textDrawableHelper
            java.util.Objects.requireNonNull(r1)
            com.google.android.material.resources.TextAppearance r1 = r1.textAppearance
            java.util.Objects.requireNonNull(r1)
            android.content.res.ColorStateList r1 = r1.textColor
            if (r1 == 0) goto L_0x00b1
            com.google.android.material.internal.TextDrawableHelper r1 = r8.textDrawableHelper
            java.util.Objects.requireNonNull(r1)
            com.google.android.material.resources.TextAppearance r1 = r1.textAppearance
            java.util.Objects.requireNonNull(r1)
            android.content.res.ColorStateList r1 = r1.textColor
            int r3 = r8.currentTextColor
            int r1 = r1.getColorForState(r9, r3)
            goto L_0x00b2
        L_0x00b1:
            r1 = r2
        L_0x00b2:
            int r3 = r8.currentTextColor
            if (r3 == r1) goto L_0x00b9
            r8.currentTextColor = r1
            r0 = r4
        L_0x00b9:
            int[] r1 = r8.getState()
            r3 = 16842912(0x10100a0, float:2.3694006E-38)
            if (r1 != 0) goto L_0x00c3
            goto L_0x00d0
        L_0x00c3:
            int r5 = r1.length
            r6 = r2
        L_0x00c5:
            if (r6 >= r5) goto L_0x00d0
            r7 = r1[r6]
            if (r7 != r3) goto L_0x00cd
            r1 = r4
            goto L_0x00d1
        L_0x00cd:
            int r6 = r6 + 1
            goto L_0x00c5
        L_0x00d0:
            r1 = r2
        L_0x00d1:
            if (r1 == 0) goto L_0x00d9
            boolean r1 = r8.checkable
            if (r1 == 0) goto L_0x00d9
            r1 = r4
            goto L_0x00da
        L_0x00d9:
            r1 = r2
        L_0x00da:
            boolean r3 = r8.currentChecked
            if (r3 == r1) goto L_0x00f6
            android.graphics.drawable.Drawable r3 = r8.checkedIcon
            if (r3 == 0) goto L_0x00f6
            float r0 = r8.calculateChipIconWidth()
            r8.currentChecked = r1
            float r1 = r8.calculateChipIconWidth()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00f3
            r0 = r4
            r1 = r0
            goto L_0x00f7
        L_0x00f3:
            r1 = r2
            r0 = r4
            goto L_0x00f7
        L_0x00f6:
            r1 = r2
        L_0x00f7:
            android.content.res.ColorStateList r3 = r8.tint
            if (r3 == 0) goto L_0x0102
            int r5 = r8.currentTint
            int r3 = r3.getColorForState(r9, r5)
            goto L_0x0103
        L_0x0102:
            r3 = r2
        L_0x0103:
            int r5 = r8.currentTint
            if (r5 == r3) goto L_0x0124
            r8.currentTint = r3
            android.content.res.ColorStateList r0 = r8.tint
            android.graphics.PorterDuff$Mode r3 = r8.tintMode
            if (r0 == 0) goto L_0x0120
            if (r3 != 0) goto L_0x0112
            goto L_0x0120
        L_0x0112:
            int[] r5 = r8.getState()
            int r0 = r0.getColorForState(r5, r2)
            android.graphics.PorterDuffColorFilter r5 = new android.graphics.PorterDuffColorFilter
            r5.<init>(r0, r3)
            goto L_0x0121
        L_0x0120:
            r5 = 0
        L_0x0121:
            r8.tintFilter = r5
            goto L_0x0125
        L_0x0124:
            r4 = r0
        L_0x0125:
            android.graphics.drawable.Drawable r0 = r8.chipIcon
            boolean r0 = isStateful((android.graphics.drawable.Drawable) r0)
            if (r0 == 0) goto L_0x0134
            android.graphics.drawable.Drawable r0 = r8.chipIcon
            boolean r0 = r0.setState(r9)
            r4 = r4 | r0
        L_0x0134:
            android.graphics.drawable.Drawable r0 = r8.checkedIcon
            boolean r0 = isStateful((android.graphics.drawable.Drawable) r0)
            if (r0 == 0) goto L_0x0143
            android.graphics.drawable.Drawable r0 = r8.checkedIcon
            boolean r0 = r0.setState(r9)
            r4 = r4 | r0
        L_0x0143:
            android.graphics.drawable.Drawable r0 = r8.closeIcon
            boolean r0 = isStateful((android.graphics.drawable.Drawable) r0)
            if (r0 == 0) goto L_0x0160
            int r0 = r9.length
            int r3 = r10.length
            int r0 = r0 + r3
            int[] r0 = new int[r0]
            int r3 = r9.length
            java.lang.System.arraycopy(r9, r2, r0, r2, r3)
            int r9 = r9.length
            int r3 = r10.length
            java.lang.System.arraycopy(r10, r2, r0, r9, r3)
            android.graphics.drawable.Drawable r9 = r8.closeIcon
            boolean r9 = r9.setState(r0)
            r4 = r4 | r9
        L_0x0160:
            android.graphics.drawable.RippleDrawable r9 = r8.closeIconRipple
            boolean r9 = isStateful((android.graphics.drawable.Drawable) r9)
            if (r9 == 0) goto L_0x016f
            android.graphics.drawable.RippleDrawable r9 = r8.closeIconRipple
            boolean r9 = r9.setState(r10)
            r4 = r4 | r9
        L_0x016f:
            if (r4 == 0) goto L_0x0174
            r8.invalidateSelf()
        L_0x0174:
            if (r1 == 0) goto L_0x0179
            r8.onSizeChange()
        L_0x0179:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.ChipDrawable.onStateChange(int[], int[]):boolean");
    }

    public static boolean isStateful(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    public static boolean isStateful(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    public final int getAlpha() {
        return this.alpha;
    }

    public final ColorFilter getColorFilter() {
        return this.colorFilter;
    }
}
