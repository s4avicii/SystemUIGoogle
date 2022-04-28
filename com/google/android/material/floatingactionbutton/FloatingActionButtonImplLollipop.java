package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;
import java.util.Objects;

public final class FloatingActionButtonImplLollipop extends FloatingActionButtonImpl {
    public final void jumpDrawableToCurrentState() {
    }

    public final void onDrawableStateChanged(int[] iArr) {
    }

    public final void updateFromViewRotation() {
    }

    public final AnimatorSet createElevationAnimator(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{f2}).setDuration(100));
        animatorSet.setInterpolator(FloatingActionButtonImpl.ELEVATION_ANIM_INTERPOLATOR);
        return animatorSet;
    }

    public final MaterialShapeDrawable createShapeDrawable() {
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearance;
        Objects.requireNonNull(shapeAppearanceModel);
        return new AlwaysStatefulMaterialShapeDrawable(shapeAppearanceModel);
    }

    public final float getElevation() {
        return this.view.getElevation();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void getPadding(android.graphics.Rect r4) {
        /*
            r3 = this;
            com.google.android.material.shadow.ShadowViewDelegate r0 = r3.shadowViewDelegate
            com.google.android.material.floatingactionbutton.FloatingActionButton$ShadowDelegateImpl r0 = (com.google.android.material.floatingactionbutton.FloatingActionButton.ShadowDelegateImpl) r0
            java.util.Objects.requireNonNull(r0)
            com.google.android.material.floatingactionbutton.FloatingActionButton r0 = com.google.android.material.floatingactionbutton.FloatingActionButton.this
            boolean r0 = r0.compatPadding
            if (r0 == 0) goto L_0x0011
            super.getPadding(r4)
            goto L_0x0042
        L_0x0011:
            boolean r0 = r3.ensureMinTouchTargetSize
            r1 = 0
            if (r0 == 0) goto L_0x0028
            com.google.android.material.floatingactionbutton.FloatingActionButton r0 = r3.view
            java.util.Objects.requireNonNull(r0)
            int r2 = r0.size
            int r0 = r0.getSizeDimension(r2)
            int r2 = r3.minTouchTargetSize
            if (r0 < r2) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r0 = r1
            goto L_0x0029
        L_0x0028:
            r0 = 1
        L_0x0029:
            if (r0 != 0) goto L_0x003f
            int r0 = r3.minTouchTargetSize
            com.google.android.material.floatingactionbutton.FloatingActionButton r3 = r3.view
            java.util.Objects.requireNonNull(r3)
            int r1 = r3.size
            int r3 = r3.getSizeDimension(r1)
            int r0 = r0 - r3
            int r0 = r0 / 2
            r4.set(r0, r0, r0, r0)
            goto L_0x0042
        L_0x003f:
            r4.set(r1, r1, r1, r1)
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButtonImplLollipop.getPadding(android.graphics.Rect):void");
    }

    public final void onElevationsChanged(float f, float f2, float f3) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(FloatingActionButtonImpl.PRESSED_ENABLED_STATE_SET, createElevationAnimator(f, f3));
        stateListAnimator.addState(FloatingActionButtonImpl.HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(f, f2));
        stateListAnimator.addState(FloatingActionButtonImpl.FOCUSED_ENABLED_STATE_SET, createElevationAnimator(f, f2));
        stateListAnimator.addState(FloatingActionButtonImpl.HOVERED_ENABLED_STATE_SET, createElevationAnimator(f, f2));
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        arrayList.add(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{f}).setDuration(0));
        arrayList.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
        animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
        animatorSet.setInterpolator(FloatingActionButtonImpl.ELEVATION_ANIM_INTERPOLATOR);
        stateListAnimator.addState(FloatingActionButtonImpl.ENABLED_STATE_SET, animatorSet);
        stateListAnimator.addState(FloatingActionButtonImpl.EMPTY_STATE_SET, createElevationAnimator(0.0f, 0.0f));
        this.view.setStateListAnimator(stateListAnimator);
        if (shouldAddPadding()) {
            updatePadding();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean shouldAddPadding() {
        /*
            r4 = this;
            com.google.android.material.shadow.ShadowViewDelegate r0 = r4.shadowViewDelegate
            com.google.android.material.floatingactionbutton.FloatingActionButton$ShadowDelegateImpl r0 = (com.google.android.material.floatingactionbutton.FloatingActionButton.ShadowDelegateImpl) r0
            java.util.Objects.requireNonNull(r0)
            com.google.android.material.floatingactionbutton.FloatingActionButton r0 = com.google.android.material.floatingactionbutton.FloatingActionButton.this
            boolean r0 = r0.compatPadding
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0028
            boolean r0 = r4.ensureMinTouchTargetSize
            if (r0 == 0) goto L_0x0025
            com.google.android.material.floatingactionbutton.FloatingActionButton r0 = r4.view
            java.util.Objects.requireNonNull(r0)
            int r3 = r0.size
            int r0 = r0.getSizeDimension(r3)
            int r4 = r4.minTouchTargetSize
            if (r0 < r4) goto L_0x0023
            goto L_0x0025
        L_0x0023:
            r4 = r1
            goto L_0x0026
        L_0x0025:
            r4 = r2
        L_0x0026:
            if (r4 != 0) goto L_0x0029
        L_0x0028:
            r1 = r2
        L_0x0029:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.FloatingActionButtonImplLollipop.shouldAddPadding():boolean");
    }

    public final void initializeBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i) {
        Drawable drawable;
        MaterialShapeDrawable createShapeDrawable = createShapeDrawable();
        this.shapeDrawable = createShapeDrawable;
        createShapeDrawable.setTintList(colorStateList);
        if (mode != null) {
            this.shapeDrawable.setTintMode(mode);
        }
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        if (i > 0) {
            Context context = this.view.getContext();
            ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearance;
            Objects.requireNonNull(shapeAppearanceModel);
            BorderDrawable borderDrawable = new BorderDrawable(shapeAppearanceModel);
            Object obj = ContextCompat.sLock;
            int color = context.getColor(C1777R.color.design_fab_stroke_top_outer_color);
            int color2 = context.getColor(C1777R.color.design_fab_stroke_top_inner_color);
            int color3 = context.getColor(C1777R.color.design_fab_stroke_end_inner_color);
            int color4 = context.getColor(C1777R.color.design_fab_stroke_end_outer_color);
            borderDrawable.topOuterStrokeColor = color;
            borderDrawable.topInnerStrokeColor = color2;
            borderDrawable.bottomOuterStrokeColor = color3;
            borderDrawable.bottomInnerStrokeColor = color4;
            float f = (float) i;
            if (borderDrawable.borderWidth != f) {
                borderDrawable.borderWidth = f;
                borderDrawable.paint.setStrokeWidth(f * 1.3333f);
                borderDrawable.invalidateShader = true;
                borderDrawable.invalidateSelf();
            }
            if (colorStateList != null) {
                borderDrawable.currentBorderTintColor = colorStateList.getColorForState(borderDrawable.getState(), borderDrawable.currentBorderTintColor);
            }
            borderDrawable.borderTint = colorStateList;
            borderDrawable.invalidateShader = true;
            borderDrawable.invalidateSelf();
            this.borderDrawable = borderDrawable;
            BorderDrawable borderDrawable2 = this.borderDrawable;
            Objects.requireNonNull(borderDrawable2);
            MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
            Objects.requireNonNull(materialShapeDrawable);
            drawable = new LayerDrawable(new Drawable[]{borderDrawable2, materialShapeDrawable});
        } else {
            this.borderDrawable = null;
            drawable = this.shapeDrawable;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(colorStateList2), drawable, (Drawable) null);
        this.rippleDrawable = rippleDrawable;
        this.contentBackground = rippleDrawable;
    }

    public static class AlwaysStatefulMaterialShapeDrawable extends MaterialShapeDrawable {
        public final boolean isStateful() {
            return true;
        }

        public AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }
    }

    public FloatingActionButtonImplLollipop(FloatingActionButton floatingActionButton, FloatingActionButton.ShadowDelegateImpl shadowDelegateImpl) {
        super(floatingActionButton, shadowDelegateImpl);
    }
}
