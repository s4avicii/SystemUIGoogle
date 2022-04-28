package com.google.android.material.chip;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.WeakHashMap;

public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable {
    public static final int[] CHECKABLE_STATE_SET = {16842911};
    public static final Rect EMPTY_BOUNDS = new Rect();
    public static final int[] SELECTED_STATE = {16842913};
    public ChipDrawable chipDrawable;
    public boolean closeIconFocused;
    public boolean closeIconHovered;
    public boolean closeIconPressed;
    public boolean deferredCheckedValue;
    public boolean ensureMinTouchTargetSize;
    public final C19821 fontCallback;
    public InsetDrawable insetBackgroundDrawable;
    public int lastLayoutDirection;
    public int minTouchTargetSize;
    public CompoundButton.OnCheckedChangeListener onCheckedChangeListenerInternal;
    public final Rect rect;
    public final RectF rectF;
    public RippleDrawable ripple;

    public class ChipTouchHelper extends ExploreByTouchHelper {
        public final void getVisibleVirtualViews(ArrayList arrayList) {
            boolean z = false;
            arrayList.add(0);
            Chip chip = Chip.this;
            Rect rect = Chip.EMPTY_BOUNDS;
            if (chip.hasCloseIcon()) {
                Chip chip2 = Chip.this;
                Objects.requireNonNull(chip2);
                ChipDrawable chipDrawable = chip2.chipDrawable;
                if (chipDrawable != null && chipDrawable.closeIconVisible) {
                    z = true;
                }
                if (z) {
                    Objects.requireNonNull(Chip.this);
                }
            }
        }

        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 == 16) {
                if (i == 0) {
                    return Chip.this.performClick();
                }
                if (i == 1) {
                    Chip chip = Chip.this;
                    Objects.requireNonNull(chip);
                    chip.playSoundEffect(0);
                }
            }
            return false;
        }

        public final void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
            if (i == 1) {
                Chip chip = Chip.this;
                chip.closeIconFocused = z;
                chip.refreshDrawableState();
            }
        }

        public ChipTouchHelper(Chip chip) {
            super(chip);
        }

        public final int getVirtualViewAt(float f, float f2) {
            Chip chip = Chip.this;
            Rect rect = Chip.EMPTY_BOUNDS;
            if (!chip.hasCloseIcon() || !Chip.this.getCloseIconTouchBounds().contains(f, f2)) {
                return 0;
            }
            return 1;
        }

        public final void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.mInfo.setCheckable(Chip.this.isCheckable());
            accessibilityNodeInfoCompat.mInfo.setClickable(Chip.this.isClickable());
            accessibilityNodeInfoCompat.setClassName(Chip.this.getAccessibilityClassName());
            accessibilityNodeInfoCompat.mInfo.setText(Chip.this.getText());
        }

        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            CharSequence charSequence = "";
            if (i == 1) {
                Objects.requireNonNull(Chip.this);
                CharSequence text = Chip.this.getText();
                Context context = Chip.this.getContext();
                Object[] objArr = new Object[1];
                if (!TextUtils.isEmpty(text)) {
                    charSequence = text;
                }
                objArr[0] = charSequence;
                accessibilityNodeInfoCompat.setContentDescription(context.getString(C1777R.string.mtrl_chip_close_icon_content_description, objArr).trim());
                Chip chip = Chip.this;
                Objects.requireNonNull(chip);
                RectF closeIconTouchBounds = chip.getCloseIconTouchBounds();
                chip.rect.set((int) closeIconTouchBounds.left, (int) closeIconTouchBounds.top, (int) closeIconTouchBounds.right, (int) closeIconTouchBounds.bottom);
                accessibilityNodeInfoCompat.setBoundsInParent(chip.rect);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                accessibilityNodeInfoCompat.mInfo.setEnabled(Chip.this.isEnabled());
                return;
            }
            accessibilityNodeInfoCompat.setContentDescription(charSequence);
            accessibilityNodeInfoCompat.setBoundsInParent(Chip.EMPTY_BOUNDS);
        }
    }

    public Chip(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (i3 == 0) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public final void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (i3 == 0) {
            super.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public final void setLines(int i) {
        if (i <= 1) {
            super.setLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public final void setMaxLines(int i) {
        if (i <= 1) {
            super.setMaxLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public final void setMinLines(int i) {
        if (i <= 1) {
            super.setMinLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.textDrawableHelper.setTextAppearance(new TextAppearance(chipDrawable2.context, i), chipDrawable2.context);
        }
        updateTextPaintDrawState();
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.chipStyle);
    }

    public final boolean ensureAccessibleTouchTarget(int i) {
        int i2;
        this.minTouchTargetSize = i;
        float f = 0.0f;
        int i3 = 0;
        if (!this.ensureMinTouchTargetSize) {
            InsetDrawable insetDrawable = this.insetBackgroundDrawable;
            if (insetDrawable == null) {
                updateBackgroundDrawable();
            } else if (insetDrawable != null) {
                this.insetBackgroundDrawable = null;
                setMinWidth(0);
                ChipDrawable chipDrawable2 = this.chipDrawable;
                if (chipDrawable2 != null) {
                    f = chipDrawable2.chipMinHeight;
                }
                setMinHeight((int) f);
                updateBackgroundDrawable();
            }
            return false;
        }
        ChipDrawable chipDrawable3 = this.chipDrawable;
        Objects.requireNonNull(chipDrawable3);
        int max = Math.max(0, i - ((int) chipDrawable3.chipMinHeight));
        int max2 = Math.max(0, i - this.chipDrawable.getIntrinsicWidth());
        if (max2 > 0 || max > 0) {
            if (max2 > 0) {
                i2 = max2 / 2;
            } else {
                i2 = 0;
            }
            if (max > 0) {
                i3 = max / 2;
            }
            int i4 = i3;
            if (this.insetBackgroundDrawable != null) {
                Rect rect2 = new Rect();
                this.insetBackgroundDrawable.getPadding(rect2);
                if (rect2.top == i4 && rect2.bottom == i4 && rect2.left == i2 && rect2.right == i2) {
                    updateBackgroundDrawable();
                    return true;
                }
            }
            if (getMinHeight() != i) {
                setMinHeight(i);
            }
            if (getMinWidth() != i) {
                setMinWidth(i);
            }
            this.insetBackgroundDrawable = new InsetDrawable(this.chipDrawable, i2, i4, i2, i4);
            updateBackgroundDrawable();
            return true;
        }
        InsetDrawable insetDrawable2 = this.insetBackgroundDrawable;
        if (insetDrawable2 == null) {
            updateBackgroundDrawable();
        } else if (insetDrawable2 != null) {
            this.insetBackgroundDrawable = null;
            setMinWidth(0);
            ChipDrawable chipDrawable4 = this.chipDrawable;
            if (chipDrawable4 != null) {
                f = chipDrawable4.chipMinHeight;
            }
            setMinHeight((int) f);
            updateBackgroundDrawable();
        }
        return false;
    }

    public final RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        hasCloseIcon();
        return this.rectF;
    }

    public final TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 == null) {
            return null;
        }
        Objects.requireNonNull(chipDrawable2);
        return chipDrawable2.truncateAt;
    }

    public final boolean hasCloseIcon() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            Drawable drawable = chipDrawable2.closeIcon;
            if (drawable == null) {
                drawable = null;
            } else if (drawable instanceof WrappedDrawable) {
                drawable = ((WrappedDrawable) drawable).getWrappedDrawable();
            }
            if (drawable != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean isCheckable() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            Objects.requireNonNull(chipDrawable2);
            if (chipDrawable2.checkable) {
                return true;
            }
        }
        return false;
    }

    public final void onChipDrawableSizeChange() {
        ensureAccessibleTouchTarget(this.minTouchTargetSize);
        requestLayout();
        invalidateOutline();
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isChecked()) {
            View.mergeDrawableStates(onCreateDrawableState, SELECTED_STATE);
        }
        if (isCheckable()) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public final void setBackground(Drawable drawable) {
        Drawable drawable2 = this.insetBackgroundDrawable;
        if (drawable2 == null) {
            drawable2 = this.chipDrawable;
        }
        if (drawable == drawable2 || drawable == this.ripple) {
            super.setBackground(drawable);
        } else {
            Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
        }
    }

    public final void setBackgroundColor(int i) {
        Log.w("Chip", "Do not set the background color; Chip manages its own background drawable.");
    }

    public final void setBackgroundDrawable(Drawable drawable) {
        Drawable drawable2 = this.insetBackgroundDrawable;
        if (drawable2 == null) {
            drawable2 = this.chipDrawable;
        }
        if (drawable == drawable2 || drawable == this.ripple) {
            super.setBackgroundDrawable(drawable);
        } else {
            Log.w("Chip", "Do not set the background drawable; Chip manages its own background drawable.");
        }
    }

    public final void setBackgroundResource(int i) {
        Log.w("Chip", "Do not set the background resource; Chip manages its own background drawable.");
    }

    public final void setBackgroundTintList(ColorStateList colorStateList) {
        Log.w("Chip", "Do not set the background tint list; Chip manages its own background drawable.");
    }

    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        Log.w("Chip", "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    public final void setChecked(boolean z) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 == null) {
            this.deferredCheckedValue = z;
            return;
        }
        Objects.requireNonNull(chipDrawable2);
        if (chipDrawable2.checkable) {
            boolean isChecked = isChecked();
            super.setChecked(z);
            if (isChecked != z && (onCheckedChangeListener = this.onCheckedChangeListenerInternal) != null) {
                onCheckedChangeListener.onCheckedChanged(this, z);
            }
        }
    }

    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public final void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.chipDrawable != null) {
            if (truncateAt != TextUtils.TruncateAt.MARQUEE) {
                super.setEllipsize(truncateAt);
                ChipDrawable chipDrawable2 = this.chipDrawable;
                if (chipDrawable2 != null) {
                    Objects.requireNonNull(chipDrawable2);
                    chipDrawable2.truncateAt = truncateAt;
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
    }

    public final void setLayoutDirection(int i) {
        if (this.chipDrawable != null) {
            super.setLayoutDirection(i);
        }
    }

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    public final void setSingleLine(boolean z) {
        if (z) {
            super.setSingleLine(z);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        CharSequence charSequence2;
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            if (charSequence == null) {
                charSequence = "";
            }
            Objects.requireNonNull(chipDrawable2);
            if (chipDrawable2.shouldDrawText) {
                charSequence2 = null;
            } else {
                charSequence2 = charSequence;
            }
            super.setText(charSequence2, bufferType);
            ChipDrawable chipDrawable3 = this.chipDrawable;
            if (chipDrawable3 != null) {
                chipDrawable3.setText(charSequence);
            }
        }
    }

    public final void updateBackgroundDrawable() {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        Objects.requireNonNull(chipDrawable2);
        ColorStateList sanitizeRippleDrawableColor = RippleUtils.sanitizeRippleDrawableColor(chipDrawable2.rippleColor);
        Drawable drawable = this.insetBackgroundDrawable;
        if (drawable == null) {
            drawable = this.chipDrawable;
        }
        this.ripple = new RippleDrawable(sanitizeRippleDrawableColor, drawable, (Drawable) null);
        ChipDrawable chipDrawable3 = this.chipDrawable;
        Objects.requireNonNull(chipDrawable3);
        if (chipDrawable3.useCompatRipple) {
            chipDrawable3.useCompatRipple = false;
            chipDrawable3.compatRippleColor = null;
            chipDrawable3.onStateChange(chipDrawable3.getState());
        }
        RippleDrawable rippleDrawable = this.ripple;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, rippleDrawable);
        updatePaddingInternal();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Chip(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = r19
            r1 = 2132018647(0x7f1405d7, float:1.9675607E38)
            r2 = r17
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r2, r7, r8, r1)
            r0.<init>(r1, r7, r8)
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.rect = r1
            android.graphics.RectF r1 = new android.graphics.RectF
            r1.<init>()
            r0.rectF = r1
            com.google.android.material.chip.Chip$1 r1 = new com.google.android.material.chip.Chip$1
            r1.<init>()
            r0.fontCallback = r1
            android.content.Context r9 = r16.getContext()
            r10 = 8388627(0x800013, float:1.175497E-38)
            r11 = 1
            if (r7 != 0) goto L_0x0032
            goto L_0x0093
        L_0x0032:
            java.lang.String r1 = "http://schemas.android.com/apk/res/android"
            java.lang.String r2 = "background"
            java.lang.String r2 = r7.getAttributeValue(r1, r2)
            java.lang.String r3 = "Chip"
            if (r2 == 0) goto L_0x0043
            java.lang.String r2 = "Do not set the background; Chip manages its own background drawable."
            android.util.Log.w(r3, r2)
        L_0x0043:
            java.lang.String r2 = "drawableLeft"
            java.lang.String r2 = r7.getAttributeValue(r1, r2)
            if (r2 != 0) goto L_0x058e
            java.lang.String r2 = "drawableStart"
            java.lang.String r2 = r7.getAttributeValue(r1, r2)
            if (r2 != 0) goto L_0x0586
            java.lang.String r2 = "drawableEnd"
            java.lang.String r2 = r7.getAttributeValue(r1, r2)
            java.lang.String r4 = "Please set end drawable using R.attr#closeIcon."
            if (r2 != 0) goto L_0x0580
            java.lang.String r2 = "drawableRight"
            java.lang.String r2 = r7.getAttributeValue(r1, r2)
            if (r2 != 0) goto L_0x057a
            java.lang.String r2 = "singleLine"
            boolean r2 = r7.getAttributeBooleanValue(r1, r2, r11)
            if (r2 == 0) goto L_0x0572
            java.lang.String r2 = "lines"
            int r2 = r7.getAttributeIntValue(r1, r2, r11)
            if (r2 != r11) goto L_0x0572
            java.lang.String r2 = "minLines"
            int r2 = r7.getAttributeIntValue(r1, r2, r11)
            if (r2 != r11) goto L_0x0572
            java.lang.String r2 = "maxLines"
            int r2 = r7.getAttributeIntValue(r1, r2, r11)
            if (r2 != r11) goto L_0x0572
            java.lang.String r2 = "gravity"
            int r1 = r7.getAttributeIntValue(r1, r2, r10)
            if (r1 == r10) goto L_0x0093
            java.lang.String r1 = "Chip text must be vertically center and start aligned"
            android.util.Log.w(r3, r1)
        L_0x0093:
            com.google.android.material.chip.ChipDrawable r12 = new com.google.android.material.chip.ChipDrawable
            r12.<init>(r9, r7, r8)
            android.content.Context r1 = r12.context
            int[] r13 = com.google.android.material.R$styleable.Chip
            r14 = 0
            int[] r6 = new int[r14]
            r5 = 2132018647(0x7f1405d7, float:1.9675607E38)
            r2 = r18
            r3 = r13
            r4 = r19
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r15 = 37
            boolean r2 = r1.hasValue(r15)
            r12.isShapeThemingEnabled = r2
            android.content.Context r2 = r12.context
            r3 = 24
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r2, (android.content.res.TypedArray) r1, (int) r3)
            android.content.res.ColorStateList r3 = r12.chipSurfaceColor
            if (r3 == r2) goto L_0x00c8
            r12.chipSurfaceColor = r2
            int[] r2 = r12.getState()
            r12.onStateChange(r2)
        L_0x00c8:
            android.content.Context r2 = r12.context
            r3 = 11
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r2, (android.content.res.TypedArray) r1, (int) r3)
            android.content.res.ColorStateList r3 = r12.chipBackgroundColor
            if (r3 == r2) goto L_0x00dd
            r12.chipBackgroundColor = r2
            int[] r2 = r12.getState()
            r12.onStateChange(r2)
        L_0x00dd:
            r2 = 19
            r3 = 0
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.chipMinHeight
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x00f2
            r12.chipMinHeight = r2
            r12.invalidateSelf()
            r12.onSizeChange()
        L_0x00f2:
            r2 = 12
            boolean r4 = r1.hasValue(r2)
            if (r4 == 0) goto L_0x0111
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.chipCornerRadius
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0111
            r12.chipCornerRadius = r2
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r4 = r12.drawableState
            com.google.android.material.shape.ShapeAppearanceModel r4 = r4.shapeAppearanceModel
            com.google.android.material.shape.ShapeAppearanceModel r2 = r4.withCornerSize(r2)
            r12.setShapeAppearanceModel(r2)
        L_0x0111:
            android.content.Context r2 = r12.context
            r4 = 22
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r2, (android.content.res.TypedArray) r1, (int) r4)
            android.content.res.ColorStateList r4 = r12.chipStrokeColor
            if (r4 == r2) goto L_0x012d
            r12.chipStrokeColor = r2
            boolean r4 = r12.isShapeThemingEnabled
            if (r4 == 0) goto L_0x0126
            r12.setStrokeColor(r2)
        L_0x0126:
            int[] r2 = r12.getState()
            r12.onStateChange(r2)
        L_0x012d:
            r2 = 23
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.chipStrokeWidth
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x014e
            r12.chipStrokeWidth = r2
            android.graphics.Paint r4 = r12.chipPaint
            r4.setStrokeWidth(r2)
            boolean r4 = r12.isShapeThemingEnabled
            if (r4 == 0) goto L_0x014b
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r4 = r12.drawableState
            r4.strokeWidth = r2
            r12.invalidateSelf()
        L_0x014b:
            r12.invalidateSelf()
        L_0x014e:
            android.content.Context r2 = r12.context
            r4 = 36
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r2, (android.content.res.TypedArray) r1, (int) r4)
            android.content.res.ColorStateList r4 = r12.rippleColor
            if (r4 == r2) goto L_0x016f
            r12.rippleColor = r2
            boolean r4 = r12.useCompatRipple
            if (r4 == 0) goto L_0x0165
            android.content.res.ColorStateList r2 = com.google.android.material.ripple.RippleUtils.sanitizeRippleDrawableColor(r2)
            goto L_0x0166
        L_0x0165:
            r2 = 0
        L_0x0166:
            r12.compatRippleColor = r2
            int[] r2 = r12.getState()
            r12.onStateChange(r2)
        L_0x016f:
            r2 = 5
            java.lang.CharSequence r2 = r1.getText(r2)
            r12.setText(r2)
            android.content.Context r2 = r12.context
            boolean r4 = r1.hasValue(r14)
            if (r4 == 0) goto L_0x018b
            int r4 = r1.getResourceId(r14, r14)
            if (r4 == 0) goto L_0x018b
            com.google.android.material.resources.TextAppearance r5 = new com.google.android.material.resources.TextAppearance
            r5.<init>(r2, r4)
            goto L_0x018c
        L_0x018b:
            r5 = 0
        L_0x018c:
            java.util.Objects.requireNonNull(r5)
            float r2 = r5.textSize
            float r2 = r1.getDimension(r11, r2)
            r5.textSize = r2
            com.google.android.material.internal.TextDrawableHelper r2 = r12.textDrawableHelper
            android.content.Context r4 = r12.context
            r2.setTextAppearance(r5, r4)
            r2 = 3
            int r4 = r1.getInt(r2, r14)
            if (r4 == r11) goto L_0x01b5
            r5 = 2
            if (r4 == r5) goto L_0x01b0
            if (r4 == r2) goto L_0x01ab
            goto L_0x01b9
        L_0x01ab:
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.END
            r12.truncateAt = r2
            goto L_0x01b9
        L_0x01b0:
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.MIDDLE
            r12.truncateAt = r2
            goto L_0x01b9
        L_0x01b5:
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.START
            r12.truncateAt = r2
        L_0x01b9:
            r2 = 18
            boolean r2 = r1.getBoolean(r2, r14)
            r12.setChipIconVisible(r2)
            java.lang.String r2 = "http://schemas.android.com/apk/res-auto"
            if (r7 == 0) goto L_0x01df
            java.lang.String r4 = "chipIconEnabled"
            java.lang.String r4 = r7.getAttributeValue(r2, r4)
            if (r4 == 0) goto L_0x01df
            java.lang.String r4 = "chipIconVisible"
            java.lang.String r4 = r7.getAttributeValue(r2, r4)
            if (r4 != 0) goto L_0x01df
            r4 = 15
            boolean r4 = r1.getBoolean(r4, r14)
            r12.setChipIconVisible(r4)
        L_0x01df:
            android.content.Context r4 = r12.context
            r5 = 14
            android.graphics.drawable.Drawable r4 = com.google.android.material.resources.MaterialResources.getDrawable(r4, r1, r5)
            android.graphics.drawable.Drawable r5 = r12.chipIcon
            if (r5 == 0) goto L_0x01f6
            boolean r6 = r5 instanceof androidx.core.graphics.drawable.WrappedDrawable
            if (r6 == 0) goto L_0x01f7
            androidx.core.graphics.drawable.WrappedDrawable r5 = (androidx.core.graphics.drawable.WrappedDrawable) r5
            android.graphics.drawable.Drawable r5 = r5.getWrappedDrawable()
            goto L_0x01f7
        L_0x01f6:
            r5 = 0
        L_0x01f7:
            if (r5 == r4) goto L_0x0223
            float r6 = r12.calculateChipIconWidth()
            if (r4 == 0) goto L_0x0204
            android.graphics.drawable.Drawable r4 = r4.mutate()
            goto L_0x0205
        L_0x0204:
            r4 = 0
        L_0x0205:
            r12.chipIcon = r4
            float r4 = r12.calculateChipIconWidth()
            com.google.android.material.chip.ChipDrawable.unapplyChildDrawable(r5)
            boolean r5 = r12.showsChipIcon()
            if (r5 == 0) goto L_0x0219
            android.graphics.drawable.Drawable r5 = r12.chipIcon
            r12.applyChildDrawable(r5)
        L_0x0219:
            r12.invalidateSelf()
            int r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x0223
            r12.onSizeChange()
        L_0x0223:
            r4 = 17
            boolean r5 = r1.hasValue(r4)
            if (r5 == 0) goto L_0x024b
            android.content.Context r5 = r12.context
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r5, (android.content.res.TypedArray) r1, (int) r4)
            r12.hasChipIconTint = r11
            android.content.res.ColorStateList r5 = r12.chipIconTint
            if (r5 == r4) goto L_0x024b
            r12.chipIconTint = r4
            boolean r5 = r12.showsChipIcon()
            if (r5 == 0) goto L_0x0244
            android.graphics.drawable.Drawable r5 = r12.chipIcon
            r5.setTintList(r4)
        L_0x0244:
            int[] r4 = r12.getState()
            r12.onStateChange(r4)
        L_0x024b:
            r4 = 16
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r4 = r1.getDimension(r4, r5)
            float r5 = r12.chipIconSize
            int r5 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r5 == 0) goto L_0x026d
            float r5 = r12.calculateChipIconWidth()
            r12.chipIconSize = r4
            float r4 = r12.calculateChipIconWidth()
            r12.invalidateSelf()
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x026d
            r12.onSizeChange()
        L_0x026d:
            r4 = 31
            boolean r4 = r1.getBoolean(r4, r14)
            r12.setCloseIconVisible(r4)
            if (r7 == 0) goto L_0x0291
            java.lang.String r4 = "closeIconEnabled"
            java.lang.String r4 = r7.getAttributeValue(r2, r4)
            if (r4 == 0) goto L_0x0291
            java.lang.String r4 = "closeIconVisible"
            java.lang.String r4 = r7.getAttributeValue(r2, r4)
            if (r4 != 0) goto L_0x0291
            r4 = 26
            boolean r4 = r1.getBoolean(r4, r14)
            r12.setCloseIconVisible(r4)
        L_0x0291:
            android.content.Context r4 = r12.context
            r5 = 25
            android.graphics.drawable.Drawable r4 = com.google.android.material.resources.MaterialResources.getDrawable(r4, r1, r5)
            android.graphics.drawable.Drawable r5 = r12.closeIcon
            if (r5 == 0) goto L_0x02a8
            boolean r6 = r5 instanceof androidx.core.graphics.drawable.WrappedDrawable
            if (r6 == 0) goto L_0x02a9
            androidx.core.graphics.drawable.WrappedDrawable r5 = (androidx.core.graphics.drawable.WrappedDrawable) r5
            android.graphics.drawable.Drawable r5 = r5.getWrappedDrawable()
            goto L_0x02a9
        L_0x02a8:
            r5 = 0
        L_0x02a9:
            if (r5 == r4) goto L_0x02e6
            float r6 = r12.calculateCloseIconWidth()
            if (r4 == 0) goto L_0x02b6
            android.graphics.drawable.Drawable r4 = r4.mutate()
            goto L_0x02b7
        L_0x02b6:
            r4 = 0
        L_0x02b7:
            r12.closeIcon = r4
            android.graphics.drawable.RippleDrawable r4 = new android.graphics.drawable.RippleDrawable
            android.content.res.ColorStateList r10 = r12.rippleColor
            android.content.res.ColorStateList r10 = com.google.android.material.ripple.RippleUtils.sanitizeRippleDrawableColor(r10)
            android.graphics.drawable.Drawable r11 = r12.closeIcon
            android.graphics.drawable.ShapeDrawable r15 = com.google.android.material.chip.ChipDrawable.closeIconRippleMask
            r4.<init>(r10, r11, r15)
            r12.closeIconRipple = r4
            float r4 = r12.calculateCloseIconWidth()
            com.google.android.material.chip.ChipDrawable.unapplyChildDrawable(r5)
            boolean r5 = r12.showsCloseIcon()
            if (r5 == 0) goto L_0x02dc
            android.graphics.drawable.Drawable r5 = r12.closeIcon
            r12.applyChildDrawable(r5)
        L_0x02dc:
            r12.invalidateSelf()
            int r4 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x02e6
            r12.onSizeChange()
        L_0x02e6:
            android.content.Context r4 = r12.context
            r5 = 30
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r4, (android.content.res.TypedArray) r1, (int) r5)
            android.content.res.ColorStateList r5 = r12.closeIconTint
            if (r5 == r4) goto L_0x0306
            r12.closeIconTint = r4
            boolean r5 = r12.showsCloseIcon()
            if (r5 == 0) goto L_0x02ff
            android.graphics.drawable.Drawable r5 = r12.closeIcon
            r5.setTintList(r4)
        L_0x02ff:
            int[] r4 = r12.getState()
            r12.onStateChange(r4)
        L_0x0306:
            r4 = 28
            float r4 = r1.getDimension(r4, r3)
            float r5 = r12.closeIconSize
            int r5 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r5 == 0) goto L_0x0320
            r12.closeIconSize = r4
            r12.invalidateSelf()
            boolean r4 = r12.showsCloseIcon()
            if (r4 == 0) goto L_0x0320
            r12.onSizeChange()
        L_0x0320:
            r4 = 6
            boolean r4 = r1.getBoolean(r4, r14)
            boolean r5 = r12.checkable
            if (r5 == r4) goto L_0x0345
            r12.checkable = r4
            float r5 = r12.calculateChipIconWidth()
            if (r4 != 0) goto L_0x0337
            boolean r4 = r12.currentChecked
            if (r4 == 0) goto L_0x0337
            r12.currentChecked = r14
        L_0x0337:
            float r4 = r12.calculateChipIconWidth()
            r12.invalidateSelf()
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x0345
            r12.onSizeChange()
        L_0x0345:
            r4 = 10
            boolean r4 = r1.getBoolean(r4, r14)
            r12.setCheckedIconVisible(r4)
            if (r7 == 0) goto L_0x0369
            java.lang.String r4 = "checkedIconEnabled"
            java.lang.String r4 = r7.getAttributeValue(r2, r4)
            if (r4 == 0) goto L_0x0369
            java.lang.String r4 = "checkedIconVisible"
            java.lang.String r2 = r7.getAttributeValue(r2, r4)
            if (r2 != 0) goto L_0x0369
            r2 = 8
            boolean r2 = r1.getBoolean(r2, r14)
            r12.setCheckedIconVisible(r2)
        L_0x0369:
            android.content.Context r2 = r12.context
            r4 = 7
            android.graphics.drawable.Drawable r2 = com.google.android.material.resources.MaterialResources.getDrawable(r2, r1, r4)
            android.graphics.drawable.Drawable r4 = r12.checkedIcon
            if (r4 == r2) goto L_0x0392
            float r4 = r12.calculateChipIconWidth()
            r12.checkedIcon = r2
            float r2 = r12.calculateChipIconWidth()
            android.graphics.drawable.Drawable r5 = r12.checkedIcon
            com.google.android.material.chip.ChipDrawable.unapplyChildDrawable(r5)
            android.graphics.drawable.Drawable r5 = r12.checkedIcon
            r12.applyChildDrawable(r5)
            r12.invalidateSelf()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0392
            r12.onSizeChange()
        L_0x0392:
            r2 = 9
            boolean r4 = r1.hasValue(r2)
            if (r4 == 0) goto L_0x03c3
            android.content.Context r4 = r12.context
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r4, (android.content.res.TypedArray) r1, (int) r2)
            android.content.res.ColorStateList r4 = r12.checkedIconTint
            if (r4 == r2) goto L_0x03c3
            r12.checkedIconTint = r2
            boolean r4 = r12.checkedIconVisible
            if (r4 == 0) goto L_0x03b4
            android.graphics.drawable.Drawable r4 = r12.checkedIcon
            if (r4 == 0) goto L_0x03b4
            boolean r4 = r12.checkable
            if (r4 == 0) goto L_0x03b4
            r4 = 1
            goto L_0x03b5
        L_0x03b4:
            r4 = r14
        L_0x03b5:
            if (r4 == 0) goto L_0x03bc
            android.graphics.drawable.Drawable r4 = r12.checkedIcon
            r4.setTintList(r2)
        L_0x03bc:
            int[] r2 = r12.getState()
            r12.onStateChange(r2)
        L_0x03c3:
            android.content.Context r2 = r12.context
            r4 = 39
            com.google.android.material.animation.MotionSpec.createFromAttribute(r2, r1, r4)
            android.content.Context r2 = r12.context
            r4 = 33
            com.google.android.material.animation.MotionSpec.createFromAttribute(r2, r1, r4)
            r2 = 21
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.chipStartPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x03e5
            r12.chipStartPadding = r2
            r12.invalidateSelf()
            r12.onSizeChange()
        L_0x03e5:
            r2 = 35
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.iconStartPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0405
            float r4 = r12.calculateChipIconWidth()
            r12.iconStartPadding = r2
            float r2 = r12.calculateChipIconWidth()
            r12.invalidateSelf()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0405
            r12.onSizeChange()
        L_0x0405:
            r2 = 34
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.iconEndPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0425
            float r4 = r12.calculateChipIconWidth()
            r12.iconEndPadding = r2
            float r2 = r12.calculateChipIconWidth()
            r12.invalidateSelf()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x0425
            r12.onSizeChange()
        L_0x0425:
            r2 = 41
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.textStartPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0439
            r12.textStartPadding = r2
            r12.invalidateSelf()
            r12.onSizeChange()
        L_0x0439:
            r2 = 40
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.textEndPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x044d
            r12.textEndPadding = r2
            r12.invalidateSelf()
            r12.onSizeChange()
        L_0x044d:
            r2 = 29
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.closeIconStartPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0467
            r12.closeIconStartPadding = r2
            r12.invalidateSelf()
            boolean r2 = r12.showsCloseIcon()
            if (r2 == 0) goto L_0x0467
            r12.onSizeChange()
        L_0x0467:
            r2 = 27
            float r2 = r1.getDimension(r2, r3)
            float r4 = r12.closeIconEndPadding
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0481
            r12.closeIconEndPadding = r2
            r12.invalidateSelf()
            boolean r2 = r12.showsCloseIcon()
            if (r2 == 0) goto L_0x0481
            r12.onSizeChange()
        L_0x0481:
            r2 = 13
            float r2 = r1.getDimension(r2, r3)
            float r3 = r12.chipEndPadding
            int r3 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r3 == 0) goto L_0x0495
            r12.chipEndPadding = r2
            r12.invalidateSelf()
            r12.onSizeChange()
        L_0x0495:
            r2 = 4
            r3 = 2147483647(0x7fffffff, float:NaN)
            int r2 = r1.getDimensionPixelSize(r2, r3)
            r12.maxWidth = r2
            r1.recycle()
            int[] r6 = new int[r14]
            r5 = 2132018647(0x7f1405d7, float:1.9675607E38)
            r1 = r9
            r2 = r18
            r3 = r13
            r4 = r19
            r10 = 0
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r2 = 32
            boolean r2 = r1.getBoolean(r2, r14)
            r0.ensureMinTouchTargetSize = r2
            android.content.Context r2 = r16.getContext()
            r3 = 48
            float r2 = com.google.android.material.internal.ViewUtils.dpToPx(r2, r3)
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            float r2 = (float) r2
            r3 = 20
            float r2 = r1.getDimension(r3, r2)
            double r2 = (double) r2
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            r0.minTouchTargetSize = r2
            r1.recycle()
            com.google.android.material.chip.ChipDrawable r1 = r0.chipDrawable
            if (r1 == r12) goto L_0x04f8
            if (r1 == 0) goto L_0x04e8
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference
            r2.<init>(r10)
            r1.delegate = r2
        L_0x04e8:
            r0.chipDrawable = r12
            r12.shouldDrawText = r14
            java.lang.ref.WeakReference r1 = new java.lang.ref.WeakReference
            r1.<init>(r0)
            r12.delegate = r1
            int r1 = r0.minTouchTargetSize
            r0.ensureAccessibleTouchTarget(r1)
        L_0x04f8:
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            float r1 = androidx.core.view.ViewCompat.Api21Impl.getElevation(r16)
            r12.setElevation(r1)
            r5 = 2132018647(0x7f1405d7, float:1.9675607E38)
            int[] r6 = new int[r14]
            r1 = r9
            r2 = r18
            r3 = r13
            r4 = r19
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r2 = 37
            boolean r2 = r1.hasValue(r2)
            r1.recycle()
            com.google.android.material.chip.Chip$ChipTouchHelper r1 = new com.google.android.material.chip.Chip$ChipTouchHelper
            r1.<init>(r0)
            boolean r1 = r16.hasCloseIcon()
            if (r1 == 0) goto L_0x052a
            com.google.android.material.chip.ChipDrawable r1 = r0.chipDrawable
            if (r1 == 0) goto L_0x052a
            boolean r1 = r1.closeIconVisible
        L_0x052a:
            androidx.core.view.ViewCompat.setAccessibilityDelegate(r0, r10)
            if (r2 != 0) goto L_0x0537
            com.google.android.material.chip.Chip$2 r1 = new com.google.android.material.chip.Chip$2
            r1.<init>()
            r0.setOutlineProvider(r1)
        L_0x0537:
            boolean r1 = r0.deferredCheckedValue
            r0.setChecked(r1)
            java.lang.CharSequence r1 = r12.text
            r0.setText(r1)
            android.text.TextUtils$TruncateAt r1 = r12.truncateAt
            r0.setEllipsize(r1)
            r16.updateTextPaintDrawState()
            com.google.android.material.chip.ChipDrawable r1 = r0.chipDrawable
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.shouldDrawText
            if (r1 != 0) goto L_0x0559
            r1 = 1
            r0.setLines(r1)
            r0.setHorizontallyScrolling(r1)
        L_0x0559:
            r1 = 8388627(0x800013, float:1.175497E-38)
            r0.setGravity(r1)
            r16.updatePaddingInternal()
            boolean r1 = r0.ensureMinTouchTargetSize
            if (r1 == 0) goto L_0x056b
            int r1 = r0.minTouchTargetSize
            r0.setMinHeight(r1)
        L_0x056b:
            int r1 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r16)
            r0.lastLayoutDirection = r1
            return
        L_0x0572:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "Chip does not support multi-line text"
            r0.<init>(r1)
            throw r0
        L_0x057a:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            r0.<init>(r4)
            throw r0
        L_0x0580:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            r0.<init>(r4)
            throw r0
        L_0x0586:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "Please set start drawable using R.attr#chipIcon."
            r0.<init>(r1)
            throw r0
        L_0x058e:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "Please set left drawable using R.attr#chipIcon."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent);
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void drawableStateChanged() {
        int i;
        super.drawableStateChanged();
        ChipDrawable chipDrawable2 = this.chipDrawable;
        boolean z = false;
        if (chipDrawable2 != null && ChipDrawable.isStateful(chipDrawable2.closeIcon)) {
            ChipDrawable chipDrawable3 = this.chipDrawable;
            int isEnabled = isEnabled();
            if (this.closeIconFocused) {
                isEnabled++;
            }
            if (this.closeIconHovered) {
                isEnabled++;
            }
            if (this.closeIconPressed) {
                isEnabled++;
            }
            if (isChecked()) {
                isEnabled++;
            }
            int[] iArr = new int[isEnabled];
            if (isEnabled()) {
                iArr[0] = 16842910;
                i = 1;
            } else {
                i = 0;
            }
            if (this.closeIconFocused) {
                iArr[i] = 16842908;
                i++;
            }
            if (this.closeIconHovered) {
                iArr[i] = 16843623;
                i++;
            }
            if (this.closeIconPressed) {
                iArr[i] = 16842919;
                i++;
            }
            if (isChecked()) {
                iArr[i] = 16842913;
            }
            Objects.requireNonNull(chipDrawable3);
            if (!Arrays.equals(chipDrawable3.closeIconStateSet, iArr)) {
                chipDrawable3.closeIconStateSet = iArr;
                if (chipDrawable3.showsCloseIcon()) {
                    z = chipDrawable3.onStateChange(chipDrawable3.getState(), iArr);
                }
            }
        }
        if (z) {
            invalidate();
        }
    }

    public final CharSequence getAccessibilityClassName() {
        if (isCheckable()) {
            ViewParent parent = getParent();
            if (!(parent instanceof ChipGroup)) {
                return "android.widget.CompoundButton";
            }
            ChipGroup chipGroup = (ChipGroup) parent;
            Objects.requireNonNull(chipGroup);
            if (chipGroup.singleSelection) {
                return "android.widget.RadioButton";
            }
            return "android.widget.CompoundButton";
        } else if (isClickable()) {
            return "android.widget.Button";
        } else {
            return "android.view.View";
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        R$bool.setParentAbsoluteElevation(this, this.chipDrawable);
    }

    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7) {
            boolean contains = getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY());
            if (this.closeIconHovered != contains) {
                this.closeIconHovered = contains;
                refreshDrawableState();
            }
        } else if (actionMasked == 10 && this.closeIconHovered) {
            this.closeIconHovered = false;
            refreshDrawableState();
        }
        return super.onHoverEvent(motionEvent);
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setClickable(isClickable());
        if (getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup) getParent();
            Objects.requireNonNull(chipGroup);
            int i2 = -1;
            if (chipGroup.singleLine) {
                int i3 = 0;
                i = 0;
                while (true) {
                    if (i3 >= chipGroup.getChildCount()) {
                        break;
                    }
                    if (chipGroup.getChildAt(i3) instanceof Chip) {
                        if (((Chip) chipGroup.getChildAt(i3)) == this) {
                            break;
                        }
                        i++;
                    }
                    i3++;
                }
            }
            i = -1;
            Object tag = getTag(C1777R.C1779id.row_index_key);
            if (tag instanceof Integer) {
                i2 = ((Integer) tag).intValue();
            }
            accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i2, 1, i, 1, isChecked()).mInfo);
        }
    }

    @TargetApi(24)
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (!getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) || !isEnabled()) {
            return null;
        }
        return PointerIcon.getSystemIcon(getContext(), 1002);
    }

    @TargetApi(17)
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.lastLayoutDirection != i) {
            this.lastLayoutDirection = i;
            updatePaddingInternal();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001e, code lost:
        if (r0 != 3) goto L_0x0050;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            android.graphics.RectF r1 = r5.getCloseIconTouchBounds()
            float r2 = r6.getX()
            float r3 = r6.getY()
            boolean r1 = r1.contains(r2, r3)
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x0043
            if (r0 == r3) goto L_0x002f
            r4 = 2
            if (r0 == r4) goto L_0x0021
            r1 = 3
            if (r0 == r1) goto L_0x0038
            goto L_0x0050
        L_0x0021:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L_0x0050
            if (r1 != 0) goto L_0x004e
            if (r0 == 0) goto L_0x004e
            r5.closeIconPressed = r2
            r5.refreshDrawableState()
            goto L_0x004e
        L_0x002f:
            boolean r0 = r5.closeIconPressed
            if (r0 == 0) goto L_0x0038
            r5.playSoundEffect(r2)
            r0 = r3
            goto L_0x0039
        L_0x0038:
            r0 = r2
        L_0x0039:
            boolean r1 = r5.closeIconPressed
            if (r1 == 0) goto L_0x0051
            r5.closeIconPressed = r2
            r5.refreshDrawableState()
            goto L_0x0051
        L_0x0043:
            if (r1 == 0) goto L_0x0050
            boolean r0 = r5.closeIconPressed
            if (r0 == r3) goto L_0x004e
            r5.closeIconPressed = r3
            r5.refreshDrawableState()
        L_0x004e:
            r0 = r3
            goto L_0x0051
        L_0x0050:
            r0 = r2
        L_0x0051:
            if (r0 != 0) goto L_0x0059
            boolean r5 = super.onTouchEvent(r6)
            if (r5 == 0) goto L_0x005a
        L_0x0059:
            r2 = r3
        L_0x005a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.setElevation(f);
        }
    }

    public final void setGravity(int i) {
        if (i != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i);
        }
    }

    public final void setMaxWidth(int i) {
        super.setMaxWidth(i);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            Objects.requireNonNull(chipDrawable2);
            chipDrawable2.maxWidth = i;
        }
    }

    public final void updatePaddingInternal() {
        ChipDrawable chipDrawable2;
        if (!TextUtils.isEmpty(getText()) && (chipDrawable2 = this.chipDrawable) != null) {
            Objects.requireNonNull(chipDrawable2);
            float f = chipDrawable2.chipEndPadding;
            ChipDrawable chipDrawable3 = this.chipDrawable;
            Objects.requireNonNull(chipDrawable3);
            int calculateCloseIconWidth = (int) (this.chipDrawable.calculateCloseIconWidth() + f + chipDrawable3.textEndPadding);
            ChipDrawable chipDrawable4 = this.chipDrawable;
            Objects.requireNonNull(chipDrawable4);
            float f2 = chipDrawable4.chipStartPadding;
            ChipDrawable chipDrawable5 = this.chipDrawable;
            Objects.requireNonNull(chipDrawable5);
            int calculateChipIconWidth = (int) (this.chipDrawable.calculateChipIconWidth() + f2 + chipDrawable5.textStartPadding);
            if (this.insetBackgroundDrawable != null) {
                Rect rect2 = new Rect();
                this.insetBackgroundDrawable.getPadding(rect2);
                calculateChipIconWidth += rect2.left;
                calculateCloseIconWidth += rect2.right;
            }
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(this, calculateChipIconWidth, paddingTop, calculateCloseIconWidth, paddingBottom);
        }
    }

    public final void updateTextPaintDrawState() {
        TextAppearance textAppearance;
        TextPaint paint = getPaint();
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            paint.drawableState = chipDrawable2.getState();
        }
        ChipDrawable chipDrawable3 = this.chipDrawable;
        if (chipDrawable3 != null) {
            TextDrawableHelper textDrawableHelper = chipDrawable3.textDrawableHelper;
            Objects.requireNonNull(textDrawableHelper);
            textAppearance = textDrawableHelper.textAppearance;
        } else {
            textAppearance = null;
        }
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), paint, this.fontCallback);
        }
    }

    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    public final void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
    }

    public final void setTextAppearance(int i) {
        super.setTextAppearance(i);
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 != null) {
            chipDrawable2.textDrawableHelper.setTextAppearance(new TextAppearance(chipDrawable2.context, i), chipDrawable2.context);
        }
        updateTextPaintDrawState();
    }

    public final void getFocusedRect(Rect rect2) {
        super.getFocusedRect(rect2);
    }

    public final void onFocusChanged(boolean z, int i, Rect rect2) {
        super.onFocusChanged(z, i, rect2);
    }
}
