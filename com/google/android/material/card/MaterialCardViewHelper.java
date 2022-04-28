package com.google.android.material.card;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import androidx.cardview.widget.RoundRectDrawable;
import androidx.leanback.R$drawable;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.Objects;

public final class MaterialCardViewHelper {
    public static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    public final MaterialShapeDrawable bgDrawable;
    public boolean checkable;
    public Drawable checkedIcon;
    public int checkedIconMargin;
    public int checkedIconSize;
    public ColorStateList checkedIconTint;
    public LayerDrawable clickableForegroundDrawable;
    public Drawable fgDrawable;
    public final MaterialShapeDrawable foregroundContentDrawable;
    public MaterialShapeDrawable foregroundShapeDrawable;
    public boolean isBackgroundOverwritten = false;
    public final MaterialCardView materialCardView;
    public ColorStateList rippleColor;
    public RippleDrawable rippleDrawable;
    public ShapeAppearanceModel shapeAppearanceModel;
    public ColorStateList strokeColor;
    public int strokeWidth;
    public final Rect userContentPadding = new Rect();

    public static float calculateCornerPaddingForCornerTreatment(R$drawable r$drawable, float f) {
        if (r$drawable instanceof RoundedCornerTreatment) {
            return (float) ((1.0d - COS_45) * ((double) f));
        }
        if (r$drawable instanceof CutCornerTreatment) {
            return f / 2.0f;
        }
        return 0.0f;
    }

    public final float calculateActualCornerPadding() {
        ShapeAppearanceModel shapeAppearanceModel2 = this.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel2);
        float calculateCornerPaddingForCornerTreatment = calculateCornerPaddingForCornerTreatment(shapeAppearanceModel2.topLeftCorner, this.bgDrawable.getTopLeftCornerResolvedSize());
        ShapeAppearanceModel shapeAppearanceModel3 = this.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel3);
        R$drawable r$drawable = shapeAppearanceModel3.topRightCorner;
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        Objects.requireNonNull(materialShapeDrawable);
        ShapeAppearanceModel shapeAppearanceModel4 = materialShapeDrawable.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel4);
        float max = Math.max(calculateCornerPaddingForCornerTreatment, calculateCornerPaddingForCornerTreatment(r$drawable, shapeAppearanceModel4.topRightCornerSize.getCornerSize(materialShapeDrawable.getBoundsAsRectF())));
        ShapeAppearanceModel shapeAppearanceModel5 = this.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel5);
        R$drawable r$drawable2 = shapeAppearanceModel5.bottomRightCorner;
        MaterialShapeDrawable materialShapeDrawable2 = this.bgDrawable;
        Objects.requireNonNull(materialShapeDrawable2);
        ShapeAppearanceModel shapeAppearanceModel6 = materialShapeDrawable2.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel6);
        float calculateCornerPaddingForCornerTreatment2 = calculateCornerPaddingForCornerTreatment(r$drawable2, shapeAppearanceModel6.bottomRightCornerSize.getCornerSize(materialShapeDrawable2.getBoundsAsRectF()));
        ShapeAppearanceModel shapeAppearanceModel7 = this.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel7);
        R$drawable r$drawable3 = shapeAppearanceModel7.bottomLeftCorner;
        MaterialShapeDrawable materialShapeDrawable3 = this.bgDrawable;
        Objects.requireNonNull(materialShapeDrawable3);
        ShapeAppearanceModel shapeAppearanceModel8 = materialShapeDrawable3.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel8);
        return Math.max(max, Math.max(calculateCornerPaddingForCornerTreatment2, calculateCornerPaddingForCornerTreatment(r$drawable3, shapeAppearanceModel8.bottomLeftCornerSize.getCornerSize(materialShapeDrawable3.getBoundsAsRectF()))));
    }

    public final LayerDrawable getClickableForeground() {
        if (this.rippleDrawable == null) {
            this.foregroundShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.rippleDrawable = new RippleDrawable(this.rippleColor, (Drawable) null, this.foregroundShapeDrawable);
        }
        if (this.clickableForegroundDrawable == null) {
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{this.rippleDrawable, this.foregroundContentDrawable, this.checkedIcon});
            this.clickableForegroundDrawable = layerDrawable;
            layerDrawable.setId(2, C1777R.C1779id.mtrl_card_checked_layer_id);
        }
        return this.clickableForegroundDrawable;
    }

    public final C19811 insetDrawable(Drawable drawable) {
        int i;
        int i2;
        float f;
        MaterialCardView materialCardView2 = this.materialCardView;
        Objects.requireNonNull(materialCardView2);
        if (materialCardView2.mCompatPadding) {
            MaterialCardView materialCardView3 = this.materialCardView;
            Objects.requireNonNull(materialCardView3);
            CardView.C00931 r0 = materialCardView3.mCardViewDelegate;
            Objects.requireNonNull(r0);
            RoundRectDrawable roundRectDrawable = (RoundRectDrawable) r0.mCardBackground;
            Objects.requireNonNull(roundRectDrawable);
            float f2 = roundRectDrawable.mPadding * 1.5f;
            float f3 = 0.0f;
            if (shouldAddCornerPaddingOutsideCardBackground()) {
                f = calculateActualCornerPadding();
            } else {
                f = 0.0f;
            }
            int ceil = (int) Math.ceil((double) (f2 + f));
            MaterialCardView materialCardView4 = this.materialCardView;
            Objects.requireNonNull(materialCardView4);
            CardView.C00931 r02 = materialCardView4.mCardViewDelegate;
            Objects.requireNonNull(r02);
            RoundRectDrawable roundRectDrawable2 = (RoundRectDrawable) r02.mCardBackground;
            Objects.requireNonNull(roundRectDrawable2);
            float f4 = roundRectDrawable2.mPadding;
            if (shouldAddCornerPaddingOutsideCardBackground()) {
                f3 = calculateActualCornerPadding();
            }
            i2 = (int) Math.ceil((double) (f4 + f3));
            i = ceil;
        } else {
            i2 = 0;
            i = 0;
        }
        return new InsetDrawable(drawable, i2, i, i2, i) {
            public final int getMinimumHeight() {
                return -1;
            }

            public final int getMinimumWidth() {
                return -1;
            }

            public final boolean getPadding(Rect rect) {
                return false;
            }
        };
    }

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        this.bgDrawable.setShapeAppearanceModel(shapeAppearanceModel2);
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        materialShapeDrawable.shadowBitmapDrawingEnable = !materialShapeDrawable.isRoundRect();
        MaterialShapeDrawable materialShapeDrawable2 = this.foregroundContentDrawable;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel2);
        }
        MaterialShapeDrawable materialShapeDrawable3 = this.foregroundShapeDrawable;
        if (materialShapeDrawable3 != null) {
            materialShapeDrawable3.setShapeAppearanceModel(shapeAppearanceModel2);
        }
    }

    public final boolean shouldAddCornerPaddingOutsideCardBackground() {
        MaterialCardView materialCardView2 = this.materialCardView;
        Objects.requireNonNull(materialCardView2);
        if (materialCardView2.mPreventCornerOverlap && this.bgDrawable.isRoundRect()) {
            MaterialCardView materialCardView3 = this.materialCardView;
            Objects.requireNonNull(materialCardView3);
            if (materialCardView3.mCompatPadding) {
                return true;
            }
        }
        return false;
    }

    public final void updateContentPadding() {
        boolean z;
        float f;
        MaterialCardView materialCardView2 = this.materialCardView;
        Objects.requireNonNull(materialCardView2);
        boolean z2 = true;
        if (!materialCardView2.mPreventCornerOverlap || this.bgDrawable.isRoundRect()) {
            z = false;
        } else {
            z = true;
        }
        if (!z && !shouldAddCornerPaddingOutsideCardBackground()) {
            z2 = false;
        }
        float f2 = 0.0f;
        if (z2) {
            f = calculateActualCornerPadding();
        } else {
            f = 0.0f;
        }
        MaterialCardView materialCardView3 = this.materialCardView;
        Objects.requireNonNull(materialCardView3);
        if (materialCardView3.mPreventCornerOverlap) {
            MaterialCardView materialCardView4 = this.materialCardView;
            Objects.requireNonNull(materialCardView4);
            if (materialCardView4.mCompatPadding) {
                MaterialCardView materialCardView5 = this.materialCardView;
                Objects.requireNonNull(materialCardView5);
                CardView.C00931 r0 = materialCardView5.mCardViewDelegate;
                Objects.requireNonNull(r0);
                RoundRectDrawable roundRectDrawable = (RoundRectDrawable) r0.mCardBackground;
                Objects.requireNonNull(roundRectDrawable);
                f2 = (float) ((1.0d - COS_45) * ((double) roundRectDrawable.mRadius));
            }
        }
        int i = (int) (f - f2);
        MaterialCardView materialCardView6 = this.materialCardView;
        Rect rect = this.userContentPadding;
        int i2 = rect.left + i;
        int i3 = rect.top + i;
        int i4 = rect.right + i;
        int i5 = rect.bottom + i;
        Objects.requireNonNull(materialCardView6);
        materialCardView6.mContentPadding.set(i2, i3, i4, i5);
        CardView.IMPL.updatePadding(materialCardView6.mCardViewDelegate);
    }

    public MaterialCardViewHelper(MaterialCardView materialCardView2, AttributeSet attributeSet) {
        this.materialCardView = materialCardView2;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(materialCardView2.getContext(), attributeSet, C1777R.attr.materialCardViewStyle, 2132018645);
        this.bgDrawable = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(materialCardView2.getContext());
        materialShapeDrawable.setShadowColor();
        ShapeAppearanceModel shapeAppearanceModel2 = materialShapeDrawable.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel2);
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel2);
        TypedArray obtainStyledAttributes = materialCardView2.getContext().obtainStyledAttributes(attributeSet, R$styleable.CardView, C1777R.attr.materialCardViewStyle, C1777R.style.CardView);
        if (obtainStyledAttributes.hasValue(3)) {
            builder.setAllCornerSizes(obtainStyledAttributes.getDimension(3, 0.0f));
        }
        this.foregroundContentDrawable = new MaterialShapeDrawable();
        setShapeAppearanceModel(new ShapeAppearanceModel(builder));
        obtainStyledAttributes.recycle();
    }
}
