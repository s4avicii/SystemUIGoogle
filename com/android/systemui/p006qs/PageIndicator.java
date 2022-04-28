package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import java.util.ArrayList;

/* renamed from: com.android.systemui.qs.PageIndicator */
public class PageIndicator extends ViewGroup {
    public boolean mAnimating;
    public final C09811 mAnimationCallback = new Animatable2.AnimationCallback() {
        public final void onAnimationEnd(Drawable drawable) {
            super.onAnimationEnd(drawable);
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).unregisterAnimationCallback(PageIndicator.this.mAnimationCallback);
            }
            PageIndicator pageIndicator = PageIndicator.this;
            pageIndicator.mAnimating = false;
            if (pageIndicator.mQueuedPositions.size() != 0) {
                PageIndicator pageIndicator2 = PageIndicator.this;
                pageIndicator2.setPosition(pageIndicator2.mQueuedPositions.remove(0).intValue());
            }
        }
    };
    public final int mPageDotWidth;
    public final int mPageIndicatorHeight;
    public final int mPageIndicatorWidth;
    public int mPosition = -1;
    public final ArrayList<Integer> mQueuedPositions = new ArrayList<>();
    public ColorStateList mTint;

    public static int getTransition(boolean z, boolean z2, boolean z3) {
        return z3 ? z ? z2 ? C1777R.C1778drawable.major_b_a_animation : C1777R.C1778drawable.major_b_c_animation : z2 ? C1777R.C1778drawable.major_a_b_animation : C1777R.C1778drawable.major_c_b_animation : z ? z2 ? C1777R.C1778drawable.minor_b_c_animation : C1777R.C1778drawable.minor_b_a_animation : z2 ? C1777R.C1778drawable.minor_c_b_animation : C1777R.C1778drawable.minor_a_b_animation;
    }

    public void setLocation(float f) {
        int i = (int) f;
        int i2 = 0;
        setContentDescription(getContext().getString(C1777R.string.accessibility_quick_settings_page, new Object[]{Integer.valueOf(i + 1), Integer.valueOf(getChildCount())}));
        int i3 = i << 1;
        if (f != ((float) i)) {
            i2 = 1;
        }
        int i4 = i3 | i2;
        int i5 = this.mPosition;
        if (this.mQueuedPositions.size() != 0) {
            ArrayList<Integer> arrayList = this.mQueuedPositions;
            i5 = arrayList.get(arrayList.size() - 1).intValue();
        }
        if (i4 != i5) {
            if (this.mAnimating) {
                this.mQueuedPositions.add(Integer.valueOf(i4));
            } else {
                setPosition(i4);
            }
        }
    }

    public final void setNumPages(int i) {
        int i2;
        if (i > 1) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        setVisibility(i2);
        if (i != getChildCount()) {
            if (this.mAnimating) {
                Log.w("PageIndicator", "setNumPages during animation");
            }
            while (i < getChildCount()) {
                removeViewAt(getChildCount() - 1);
            }
            while (i > getChildCount()) {
                ImageView imageView = new ImageView(this.mContext);
                imageView.setImageResource(C1777R.C1778drawable.minor_a_b);
                imageView.setImageTintList(this.mTint);
                addView(imageView, new ViewGroup.LayoutParams(this.mPageIndicatorWidth, this.mPageIndicatorHeight));
            }
            setIndex(this.mPosition >> 1);
            requestLayout();
        }
    }

    public PageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16843041});
        if (obtainStyledAttributes.hasValue(0)) {
            this.mTint = obtainStyledAttributes.getColorStateList(0);
        } else {
            this.mTint = Utils.getColorAttr(context, 16843829);
        }
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mPageIndicatorWidth = resources.getDimensionPixelSize(C1777R.dimen.qs_page_indicator_width);
        this.mPageIndicatorHeight = resources.getDimensionPixelSize(C1777R.dimen.qs_page_indicator_height);
        this.mPageDotWidth = resources.getDimensionPixelSize(C1777R.dimen.qs_page_indicator_dot_width);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        if (childCount != 0) {
            for (int i5 = 0; i5 < childCount; i5++) {
                int i6 = (this.mPageIndicatorWidth - this.mPageDotWidth) * i5;
                getChildAt(i5).layout(i6, 0, this.mPageIndicatorWidth + i6, this.mPageIndicatorHeight);
            }
        }
    }

    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        if (childCount == 0) {
            super.onMeasure(i, i2);
            return;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mPageIndicatorWidth, 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mPageIndicatorHeight, 1073741824);
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).measure(makeMeasureSpec, makeMeasureSpec2);
        }
        int i4 = this.mPageIndicatorWidth;
        int i5 = this.mPageDotWidth;
        setMeasuredDimension(((childCount - 1) * (i4 - i5)) + i5, this.mPageIndicatorHeight);
    }

    public final void setIndex(int i) {
        boolean z;
        float f;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ImageView imageView = (ImageView) getChildAt(i2);
            imageView.setTranslationX(0.0f);
            imageView.setImageResource(C1777R.C1778drawable.major_a_b);
            if (i2 == i) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                f = 1.0f;
            } else {
                f = 0.42f;
            }
            imageView.setAlpha(f);
        }
    }

    public final void setPosition(int i) {
        boolean z;
        boolean z2;
        if (!isVisibleToUser() || Math.abs(this.mPosition - i) != 1) {
            setIndex(i >> 1);
        } else {
            int i2 = this.mPosition;
            int i3 = i2 >> 1;
            int i4 = i >> 1;
            setIndex(i3);
            if ((i2 & 1) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z ? i2 >= i : i2 <= i) {
                z2 = false;
            } else {
                z2 = true;
            }
            int min = Math.min(i3, i4);
            int max = Math.max(i3, i4);
            if (max == min) {
                max++;
            }
            ImageView imageView = (ImageView) getChildAt(min);
            ImageView imageView2 = (ImageView) getChildAt(max);
            if (!(imageView == null || imageView2 == null)) {
                imageView2.setTranslationX(imageView.getX() - imageView2.getX());
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) getContext().getDrawable(getTransition(z, z2, false));
                imageView.setImageDrawable(animatedVectorDrawable);
                animatedVectorDrawable.forceAnimationOnUI();
                animatedVectorDrawable.registerAnimationCallback(this.mAnimationCallback);
                animatedVectorDrawable.start();
                imageView.setAlpha(0.42f);
                AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) getContext().getDrawable(getTransition(z, z2, true));
                imageView2.setImageDrawable(animatedVectorDrawable2);
                animatedVectorDrawable2.forceAnimationOnUI();
                animatedVectorDrawable2.registerAnimationCallback(this.mAnimationCallback);
                animatedVectorDrawable2.start();
                imageView2.setAlpha(1.0f);
                this.mAnimating = true;
            }
        }
        this.mPosition = i;
    }
}
