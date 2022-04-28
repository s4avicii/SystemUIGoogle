package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatBackgroundHelper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.customview.view.AbsSavedState;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.WeakHashMap;

public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
    public static final int[] CHECKABLE_STATE_SET = {16842911};
    public static final int[] CHECKED_STATE_SET = {16842912};
    public boolean broadcasting;
    public boolean checked;
    public Drawable icon;
    public int iconGravity;
    public int iconLeft;
    public int iconPadding;
    public int iconSize;
    public ColorStateList iconTint;
    public PorterDuff.Mode iconTintMode;
    public int iconTop;
    public final MaterialButtonHelper materialButtonHelper;
    public final LinkedHashSet<OnCheckedChangeListener> onCheckedChangeListeners;
    public OnPressedChangeListener onPressedChangeListenerInternal;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(MaterialButton materialButton, boolean z);
    }

    public interface OnPressedChangeListener {
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean checked;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                getClass().getClassLoader();
            }
            this.checked = parcel.readInt() != 1 ? false : true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }
    }

    public MaterialButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.materialButtonStyle);
    }

    public final boolean isCheckable() {
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        if (materialButtonHelper2 != null) {
            Objects.requireNonNull(materialButtonHelper2);
            if (materialButtonHelper2.checkable) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUsingOriginalBackground() {
        MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
        if (materialButtonHelper2 != null) {
            Objects.requireNonNull(materialButtonHelper2);
            if (!materialButtonHelper2.backgroundOverwritten) {
                return true;
            }
        }
        return false;
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isCheckable()) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        setChecked(savedState.checked);
    }

    public final void resetIconDrawable() {
        boolean z;
        boolean z2;
        int i = this.iconGravity;
        boolean z3 = false;
        if (i == 1 || i == 2) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            setCompoundDrawablesRelative(this.icon, (Drawable) null, (Drawable) null, (Drawable) null);
            return;
        }
        if (i == 3 || i == 4) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            setCompoundDrawablesRelative((Drawable) null, (Drawable) null, this.icon, (Drawable) null);
            return;
        }
        if (i == 16 || i == 32) {
            z3 = true;
        }
        if (z3) {
            setCompoundDrawablesRelative((Drawable) null, this.icon, (Drawable) null, (Drawable) null);
        }
    }

    public final void setBackgroundResource(int i) {
        Drawable drawable;
        if (i != 0) {
            drawable = AppCompatResources.getDrawable(getContext(), i);
        } else {
            drawable = null;
        }
        setBackgroundDrawable(drawable);
    }

    public final void setPressed(boolean z) {
        OnPressedChangeListener onPressedChangeListener = this.onPressedChangeListenerInternal;
        if (onPressedChangeListener != null) {
            MaterialButtonToggleGroup.PressedStateTracker pressedStateTracker = (MaterialButtonToggleGroup.PressedStateTracker) onPressedChangeListener;
            Objects.requireNonNull(pressedStateTracker);
            MaterialButtonToggleGroup.this.invalidate();
        }
        super.setPressed(z);
    }

    public final void toggle() {
        setChecked(!this.checked);
    }

    public final void updateIcon(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        Drawable drawable = this.icon;
        boolean z5 = true;
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            this.icon = mutate;
            mutate.setTintList(this.iconTint);
            PorterDuff.Mode mode = this.iconTintMode;
            if (mode != null) {
                this.icon.setTintMode(mode);
            }
            int i = this.iconSize;
            if (i == 0) {
                i = this.icon.getIntrinsicWidth();
            }
            int i2 = this.iconSize;
            if (i2 == 0) {
                i2 = this.icon.getIntrinsicHeight();
            }
            Drawable drawable2 = this.icon;
            int i3 = this.iconLeft;
            int i4 = this.iconTop;
            drawable2.setBounds(i3, i4, i + i3, i2 + i4);
            this.icon.setVisible(true, z);
        }
        if (z) {
            resetIconDrawable();
            return;
        }
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        Drawable drawable3 = compoundDrawablesRelative[0];
        Drawable drawable4 = compoundDrawablesRelative[1];
        Drawable drawable5 = compoundDrawablesRelative[2];
        int i5 = this.iconGravity;
        if (i5 == 1 || i5 == 2) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 || drawable3 == this.icon) {
            if (i5 == 3 || i5 == 4) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (!z3 || drawable5 == this.icon) {
                if (i5 == 16 || i5 == 32) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (!z4 || drawable4 == this.icon) {
                    z5 = false;
                }
            }
        }
        if (z5) {
            resetIconDrawable();
        }
    }

    public final void updateIconPosition(int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.icon != null && getLayout() != null) {
            int i3 = this.iconGravity;
            boolean z4 = true;
            if (i3 == 1 || i3 == 2) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (i3 == 3 || i3 == 4) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3) {
                    if (!(i3 == 16 || i3 == 32)) {
                        z4 = false;
                    }
                    if (z4) {
                        this.iconLeft = 0;
                        if (i3 == 16) {
                            this.iconTop = 0;
                            updateIcon(false);
                            return;
                        }
                        int i4 = this.iconSize;
                        if (i4 == 0) {
                            i4 = this.icon.getIntrinsicHeight();
                        }
                        TextPaint paint = getPaint();
                        String charSequence = getText().toString();
                        if (getTransformationMethod() != null) {
                            charSequence = getTransformationMethod().getTransformation(charSequence, this).toString();
                        }
                        Rect rect = new Rect();
                        paint.getTextBounds(charSequence, 0, charSequence.length(), rect);
                        int min = (((((i2 - Math.min(rect.height(), getLayout().getHeight())) - getPaddingTop()) - i4) - this.iconPadding) - getPaddingBottom()) / 2;
                        if (this.iconTop != min) {
                            this.iconTop = min;
                            updateIcon(false);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            this.iconTop = 0;
            if (i3 == 1 || i3 == 3) {
                this.iconLeft = 0;
                updateIcon(false);
                return;
            }
            int i5 = this.iconSize;
            if (i5 == 0) {
                i5 = this.icon.getIntrinsicWidth();
            }
            TextPaint paint2 = getPaint();
            String charSequence2 = getText().toString();
            if (getTransformationMethod() != null) {
                charSequence2 = getTransformationMethod().getTransformation(charSequence2, this).toString();
            }
            int min2 = i - Math.min((int) paint2.measureText(charSequence2), getLayout().getEllipsizedWidth());
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            int paddingEnd = ((((min2 - ViewCompat.Api17Impl.getPaddingEnd(this)) - i5) - this.iconPadding) - ViewCompat.Api17Impl.getPaddingStart(this)) / 2;
            if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (this.iconGravity != 4) {
                z4 = false;
            }
            if (z2 != z4) {
                paddingEnd = -paddingEnd;
            }
            if (this.iconLeft != paddingEnd) {
                this.iconLeft = paddingEnd;
                updateIcon(false);
            }
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialButton(android.content.Context r21, android.util.AttributeSet r22, int r23) {
        /*
            r20 = this;
            r0 = r20
            r7 = r22
            r8 = r23
            r9 = 2132018633(0x7f1405c9, float:1.9675578E38)
            r1 = r21
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r1, r7, r8)
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.onCheckedChangeListeners = r1
            r10 = 0
            r0.checked = r10
            r0.broadcasting = r10
            android.content.Context r11 = r20.getContext()
            int[] r3 = com.google.android.material.R$styleable.MaterialButton
            int[] r6 = new int[r10]
            r5 = 2132018633(0x7f1405c9, float:1.9675578E38)
            r1 = r11
            r2 = r22
            r4 = r23
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r2 = 12
            int r2 = r1.getDimensionPixelSize(r2, r10)
            r0.iconPadding = r2
            r2 = 15
            r3 = -1
            int r2 = r1.getInt(r2, r3)
            android.graphics.PorterDuff$Mode r4 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r2 = com.google.android.material.internal.ViewUtils.parseTintMode(r2, r4)
            r0.iconTintMode = r2
            android.content.Context r2 = r20.getContext()
            r4 = 14
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r2, (android.content.res.TypedArray) r1, (int) r4)
            r0.iconTint = r2
            android.content.Context r2 = r20.getContext()
            r4 = 10
            android.graphics.drawable.Drawable r2 = com.google.android.material.resources.MaterialResources.getDrawable(r2, r1, r4)
            r0.icon = r2
            r2 = 11
            r4 = 1
            int r2 = r1.getInteger(r2, r4)
            r0.iconGravity = r2
            r2 = 13
            int r2 = r1.getDimensionPixelSize(r2, r10)
            r0.iconSize = r2
            com.google.android.material.shape.ShapeAppearanceModel$Builder r2 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r11, (android.util.AttributeSet) r7, (int) r8, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r5 = new com.google.android.material.shape.ShapeAppearanceModel
            r5.<init>(r2)
            com.google.android.material.button.MaterialButtonHelper r2 = new com.google.android.material.button.MaterialButtonHelper
            r2.<init>(r0, r5)
            r0.materialButtonHelper = r2
            int r5 = r1.getDimensionPixelOffset(r4, r10)
            r2.insetLeft = r5
            r5 = 2
            int r6 = r1.getDimensionPixelOffset(r5, r10)
            r2.insetRight = r6
            r6 = 3
            int r6 = r1.getDimensionPixelOffset(r6, r10)
            r2.insetTop = r6
            r6 = 4
            int r6 = r1.getDimensionPixelOffset(r6, r10)
            r2.insetBottom = r6
            r6 = 8
            boolean r7 = r1.hasValue(r6)
            if (r7 == 0) goto L_0x00b3
            int r6 = r1.getDimensionPixelSize(r6, r3)
            com.google.android.material.shape.ShapeAppearanceModel r7 = r2.shapeAppearanceModel
            float r6 = (float) r6
            com.google.android.material.shape.ShapeAppearanceModel r6 = r7.withCornerSize(r6)
            r2.setShapeAppearanceModel(r6)
        L_0x00b3:
            r6 = 20
            int r6 = r1.getDimensionPixelSize(r6, r10)
            r2.strokeWidth = r6
            r6 = 7
            int r6 = r1.getInt(r6, r3)
            android.graphics.PorterDuff$Mode r7 = android.graphics.PorterDuff.Mode.SRC_IN
            android.graphics.PorterDuff$Mode r6 = com.google.android.material.internal.ViewUtils.parseTintMode(r6, r7)
            r2.backgroundTintMode = r6
            android.content.Context r6 = r20.getContext()
            r7 = 6
            android.content.res.ColorStateList r6 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r6, (android.content.res.TypedArray) r1, (int) r7)
            r2.backgroundTint = r6
            android.content.Context r6 = r20.getContext()
            r7 = 19
            android.content.res.ColorStateList r6 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r6, (android.content.res.TypedArray) r1, (int) r7)
            r2.strokeColor = r6
            android.content.Context r6 = r20.getContext()
            r7 = 16
            android.content.res.ColorStateList r6 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r6, (android.content.res.TypedArray) r1, (int) r7)
            r2.rippleColor = r6
            r6 = 5
            boolean r6 = r1.getBoolean(r6, r10)
            r2.checkable = r6
            r6 = 9
            int r6 = r1.getDimensionPixelSize(r6, r10)
            r2.elevation = r6
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r6 = androidx.core.view.ViewCompat.Api17Impl.getPaddingStart(r20)
            int r7 = r20.getPaddingTop()
            int r8 = androidx.core.view.ViewCompat.Api17Impl.getPaddingEnd(r20)
            int r9 = r20.getPaddingBottom()
            boolean r11 = r1.hasValue(r10)
            if (r11 == 0) goto L_0x0120
            r2.backgroundOverwritten = r4
            android.content.res.ColorStateList r3 = r2.backgroundTint
            r0.setSupportBackgroundTintList(r3)
            android.graphics.PorterDuff$Mode r3 = r2.backgroundTintMode
            r0.setSupportBackgroundTintMode(r3)
            goto L_0x01bc
        L_0x0120:
            com.google.android.material.shape.MaterialShapeDrawable r11 = new com.google.android.material.shape.MaterialShapeDrawable
            com.google.android.material.shape.ShapeAppearanceModel r12 = r2.shapeAppearanceModel
            r11.<init>((com.google.android.material.shape.ShapeAppearanceModel) r12)
            android.content.Context r12 = r20.getContext()
            r11.initializeElevationOverlay(r12)
            android.content.res.ColorStateList r12 = r2.backgroundTint
            r11.setTintList(r12)
            android.graphics.PorterDuff$Mode r12 = r2.backgroundTintMode
            if (r12 == 0) goto L_0x013a
            r11.setTintMode(r12)
        L_0x013a:
            int r12 = r2.strokeWidth
            float r12 = (float) r12
            android.content.res.ColorStateList r13 = r2.strokeColor
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r14 = r11.drawableState
            r14.strokeWidth = r12
            r11.invalidateSelf()
            r11.setStrokeColor(r13)
            com.google.android.material.shape.MaterialShapeDrawable r12 = new com.google.android.material.shape.MaterialShapeDrawable
            com.google.android.material.shape.ShapeAppearanceModel r13 = r2.shapeAppearanceModel
            r12.<init>((com.google.android.material.shape.ShapeAppearanceModel) r13)
            r12.setTint(r10)
            int r13 = r2.strokeWidth
            float r13 = (float) r13
            boolean r14 = r2.shouldDrawSurfaceColorStroke
            if (r14 == 0) goto L_0x0162
            r14 = 2130968847(0x7f04010f, float:1.754636E38)
            int r14 = androidx.leanback.R$string.getColor(r0, r14)
            goto L_0x0163
        L_0x0162:
            r14 = r10
        L_0x0163:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r15 = r12.drawableState
            r15.strokeWidth = r13
            r12.invalidateSelf()
            android.content.res.ColorStateList r13 = android.content.res.ColorStateList.valueOf(r14)
            r12.setStrokeColor(r13)
            com.google.android.material.shape.MaterialShapeDrawable r13 = new com.google.android.material.shape.MaterialShapeDrawable
            com.google.android.material.shape.ShapeAppearanceModel r14 = r2.shapeAppearanceModel
            r13.<init>((com.google.android.material.shape.ShapeAppearanceModel) r14)
            r2.maskDrawable = r13
            r13.setTint(r3)
            android.graphics.drawable.RippleDrawable r3 = new android.graphics.drawable.RippleDrawable
            android.content.res.ColorStateList r13 = r2.rippleColor
            android.content.res.ColorStateList r13 = com.google.android.material.ripple.RippleUtils.sanitizeRippleDrawableColor(r13)
            android.graphics.drawable.LayerDrawable r15 = new android.graphics.drawable.LayerDrawable
            android.graphics.drawable.Drawable[] r5 = new android.graphics.drawable.Drawable[r5]
            r5[r10] = r12
            r5[r4] = r11
            r15.<init>(r5)
            android.graphics.drawable.InsetDrawable r5 = new android.graphics.drawable.InsetDrawable
            int r11 = r2.insetLeft
            int r12 = r2.insetTop
            int r14 = r2.insetRight
            int r4 = r2.insetBottom
            r18 = r14
            r14 = r5
            r16 = r11
            r17 = r12
            r19 = r4
            r14.<init>(r15, r16, r17, r18, r19)
            com.google.android.material.shape.MaterialShapeDrawable r4 = r2.maskDrawable
            r3.<init>(r13, r5, r4)
            r2.rippleDrawable = r3
            r0.setInternalBackground(r3)
            com.google.android.material.shape.MaterialShapeDrawable r3 = r2.getMaterialShapeDrawable(r10)
            if (r3 == 0) goto L_0x01bc
            int r4 = r2.elevation
            float r4 = (float) r4
            r3.setElevation(r4)
        L_0x01bc:
            int r3 = r2.insetLeft
            int r6 = r6 + r3
            int r3 = r2.insetTop
            int r7 = r7 + r3
            int r3 = r2.insetRight
            int r8 = r8 + r3
            int r2 = r2.insetBottom
            int r9 = r9 + r2
            androidx.core.view.ViewCompat.Api17Impl.setPaddingRelative(r0, r6, r7, r8, r9)
            r1.recycle()
            int r1 = r0.iconPadding
            r0.setCompoundDrawablePadding(r1)
            android.graphics.drawable.Drawable r1 = r0.icon
            if (r1 == 0) goto L_0x01d8
            r10 = 1
        L_0x01d8:
            r0.updateIcon(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.button.MaterialButton.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public final ColorStateList getBackgroundTintList() {
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            return materialButtonHelper2.backgroundTint;
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    public final PorterDuff.Mode getBackgroundTintMode() {
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            return materialButtonHelper2.backgroundTintMode;
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            return appCompatBackgroundHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            R$bool.setParentAbsoluteElevation(this, materialButtonHelper2.getMaterialShapeDrawable(false));
        }
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Class cls;
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (isCheckable()) {
            cls = CompoundButton.class;
        } else {
            cls = Button.class;
        }
        accessibilityEvent.setClassName(cls.getName());
        accessibilityEvent.setChecked(isChecked());
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        Class cls;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (isCheckable()) {
            cls = CompoundButton.class;
        } else {
            cls = Button.class;
        }
        accessibilityNodeInfo.setClassName(cls.getName());
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setChecked(isChecked());
        accessibilityNodeInfo.setClickable(isClickable());
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateIconPosition(i, i2);
    }

    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    public final boolean performClick() {
        toggle();
        return super.performClick();
    }

    public final void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.icon != null) {
            if (this.icon.setState(getDrawableState())) {
                invalidate();
            }
        }
    }

    public final void setBackgroundColor(int i) {
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            if (materialButtonHelper2.getMaterialShapeDrawable(false) != null) {
                materialButtonHelper2.getMaterialShapeDrawable(false).setTint(i);
                return;
            }
            return;
        }
        super.setBackgroundColor(i);
    }

    public final void setBackgroundDrawable(Drawable drawable) {
        if (!isUsingOriginalBackground()) {
            super.setBackgroundDrawable(drawable);
        } else if (drawable != getBackground()) {
            Log.w("MaterialButton", "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            materialButtonHelper2.backgroundOverwritten = true;
            materialButtonHelper2.materialButton.setSupportBackgroundTintList(materialButtonHelper2.backgroundTint);
            materialButtonHelper2.materialButton.setSupportBackgroundTintMode(materialButtonHelper2.backgroundTintMode);
            super.setBackgroundDrawable(drawable);
        } else {
            getBackground().setState(drawable.getState());
        }
    }

    public final void setChecked(boolean z) {
        if (isCheckable() && isEnabled() && this.checked != z) {
            this.checked = z;
            refreshDrawableState();
            if (!this.broadcasting) {
                this.broadcasting = true;
                Iterator<OnCheckedChangeListener> it = this.onCheckedChangeListeners.iterator();
                while (it.hasNext()) {
                    it.next().onCheckedChanged(this, this.checked);
                }
                this.broadcasting = false;
            }
        }
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            materialButtonHelper2.getMaterialShapeDrawable(false).setElevation(f);
        }
    }

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShapeAppearanceModel(shapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public final void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            if (materialButtonHelper2.backgroundTint != colorStateList) {
                materialButtonHelper2.backgroundTint = colorStateList;
                if (materialButtonHelper2.getMaterialShapeDrawable(false) != null) {
                    materialButtonHelper2.getMaterialShapeDrawable(false).setTintList(materialButtonHelper2.backgroundTint);
                    return;
                }
                return;
            }
            return;
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    public final void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper2 = this.materialButtonHelper;
            Objects.requireNonNull(materialButtonHelper2);
            if (materialButtonHelper2.backgroundTintMode != mode) {
                materialButtonHelper2.backgroundTintMode = mode;
                if (materialButtonHelper2.getMaterialShapeDrawable(false) != null && materialButtonHelper2.backgroundTintMode != null) {
                    materialButtonHelper2.getMaterialShapeDrawable(false).setTintMode(materialButtonHelper2.backgroundTintMode);
                    return;
                }
                return;
            }
            return;
        }
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper != null) {
            appCompatBackgroundHelper.setSupportBackgroundTintMode(mode);
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public final void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    public final void setBackgroundTintList(ColorStateList colorStateList) {
        setSupportBackgroundTintList(colorStateList);
    }

    public final void setBackgroundTintMode(PorterDuff.Mode mode) {
        setSupportBackgroundTintMode(mode);
    }

    public final void setInternalBackground(RippleDrawable rippleDrawable) {
        super.setBackgroundDrawable(rippleDrawable);
    }

    public final boolean isChecked() {
        return this.checked;
    }
}
