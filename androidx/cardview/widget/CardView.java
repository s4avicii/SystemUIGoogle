package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.preference.R$color;
import androidx.preference.R$drawable;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.WeakHashMap;

public class CardView extends FrameLayout {
    public static final int[] COLOR_BACKGROUND_ATTR = {16842801};
    public static final R$drawable IMPL = new R$drawable();
    public final C00931 mCardViewDelegate;
    public boolean mCompatPadding;
    public final Rect mContentPadding;
    public boolean mPreventCornerOverlap;
    public final Rect mShadowBounds;
    public int mUserSetMinHeight;
    public int mUserSetMinWidth;

    public CardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
    }

    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
    }

    public CardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.cardViewStyle);
    }

    public final void setMinimumHeight(int i) {
        this.mUserSetMinHeight = i;
        super.setMinimumHeight(i);
    }

    public final void setMinimumWidth(int i) {
        this.mUserSetMinWidth = i;
        super.setMinimumWidth(i);
    }

    public void setRadius(float f) {
        C00931 r1 = this.mCardViewDelegate;
        Objects.requireNonNull(r1);
        RoundRectDrawable roundRectDrawable = (RoundRectDrawable) r1.mCardBackground;
        Objects.requireNonNull(roundRectDrawable);
        if (f != roundRectDrawable.mRadius) {
            roundRectDrawable.mRadius = f;
            roundRectDrawable.updateBounds((Rect) null);
            roundRectDrawable.invalidateSelf();
        }
    }

    public CardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ColorStateList colorStateList;
        int i2;
        Rect rect = new Rect();
        this.mContentPadding = rect;
        this.mShadowBounds = new Rect();
        C00931 r1 = new CardViewDelegate() {
            public Drawable mCardBackground;

            public final void setShadowPadding(int i, int i2, int i3, int i4) {
                CardView.this.mShadowBounds.set(i, i2, i3, i4);
                CardView cardView = CardView.this;
                Rect rect = cardView.mContentPadding;
                CardView.super.setPadding(i + rect.left, i2 + rect.top, i3 + rect.right, i4 + rect.bottom);
            }
        };
        this.mCardViewDelegate = r1;
        int[] iArr = R$color.CardView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, C1777R.style.CardView);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, C1777R.style.CardView);
        if (obtainStyledAttributes.hasValue(2)) {
            colorStateList = obtainStyledAttributes.getColorStateList(2);
        } else {
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(COLOR_BACKGROUND_ATTR);
            int color = obtainStyledAttributes2.getColor(0, 0);
            obtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color, fArr);
            if (fArr[2] > 0.5f) {
                i2 = getResources().getColor(C1777R.color.cardview_light_background);
            } else {
                i2 = getResources().getColor(C1777R.color.cardview_dark_background);
            }
            colorStateList = ColorStateList.valueOf(i2);
        }
        float dimension = obtainStyledAttributes.getDimension(3, 0.0f);
        float dimension2 = obtainStyledAttributes.getDimension(4, 0.0f);
        float dimension3 = obtainStyledAttributes.getDimension(5, 0.0f);
        this.mCompatPadding = obtainStyledAttributes.getBoolean(7, false);
        this.mPreventCornerOverlap = obtainStyledAttributes.getBoolean(6, true);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        rect.left = obtainStyledAttributes.getDimensionPixelSize(10, dimensionPixelSize);
        rect.top = obtainStyledAttributes.getDimensionPixelSize(12, dimensionPixelSize);
        rect.right = obtainStyledAttributes.getDimensionPixelSize(11, dimensionPixelSize);
        rect.bottom = obtainStyledAttributes.getDimensionPixelSize(9, dimensionPixelSize);
        dimension3 = dimension2 > dimension3 ? dimension2 : dimension3;
        this.mUserSetMinWidth = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.mUserSetMinHeight = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        obtainStyledAttributes.recycle();
        R$drawable r$drawable = IMPL;
        RoundRectDrawable roundRectDrawable = new RoundRectDrawable(colorStateList, dimension);
        r1.mCardBackground = roundRectDrawable;
        setBackgroundDrawable(roundRectDrawable);
        setClipToOutline(true);
        setElevation(dimension2);
        RoundRectDrawable roundRectDrawable2 = (RoundRectDrawable) r1.mCardBackground;
        Objects.requireNonNull(this);
        boolean z = this.mCompatPadding;
        Objects.requireNonNull(this);
        boolean z2 = this.mPreventCornerOverlap;
        Objects.requireNonNull(roundRectDrawable2);
        if (!(dimension3 == roundRectDrawable2.mPadding && roundRectDrawable2.mInsetForPadding == z && roundRectDrawable2.mInsetForRadius == z2)) {
            roundRectDrawable2.mPadding = dimension3;
            roundRectDrawable2.mInsetForPadding = z;
            roundRectDrawable2.mInsetForRadius = z2;
            roundRectDrawable2.updateBounds((Rect) null);
            roundRectDrawable2.invalidateSelf();
        }
        r$drawable.updatePadding(r1);
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }
}
