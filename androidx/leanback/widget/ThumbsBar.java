package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;

public class ThumbsBar extends LinearLayout {
    public int mHeroThumbHeightInPixel;
    public int mHeroThumbWidthInPixel;
    public int mMeasuredMarginInPixel;
    public int mNumOfThumbs;
    public int mThumbHeightInPixel;
    public int mThumbWidthInPixel;

    public ThumbsBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ThumbsBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNumOfThumbs = -1;
        new SparseArray();
        this.mThumbWidthInPixel = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_thumbs_width);
        this.mThumbHeightInPixel = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_thumbs_height);
        this.mHeroThumbHeightInPixel = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_hero_thumbs_width);
        this.mHeroThumbWidthInPixel = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_hero_thumbs_height);
        this.mMeasuredMarginInPixel = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_thumbs_margin);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int childCount = getChildCount() / 2;
        View childAt = getChildAt(childCount);
        int width = (getWidth() / 2) - (childAt.getMeasuredWidth() / 2);
        int measuredWidth = (childAt.getMeasuredWidth() / 2) + (getWidth() / 2);
        childAt.layout(width, getPaddingTop(), measuredWidth, childAt.getMeasuredHeight() + getPaddingTop());
        int measuredHeight = (childAt.getMeasuredHeight() / 2) + getPaddingTop();
        for (int i5 = childCount - 1; i5 >= 0; i5--) {
            int i6 = width - this.mMeasuredMarginInPixel;
            View childAt2 = getChildAt(i5);
            childAt2.layout(i6 - childAt2.getMeasuredWidth(), measuredHeight - (childAt2.getMeasuredHeight() / 2), i6, (childAt2.getMeasuredHeight() / 2) + measuredHeight);
            width = i6 - childAt2.getMeasuredWidth();
        }
        while (true) {
            childCount++;
            if (childCount < this.mNumOfThumbs) {
                int i7 = measuredWidth + this.mMeasuredMarginInPixel;
                View childAt3 = getChildAt(childCount);
                childAt3.layout(i7, measuredHeight - (childAt3.getMeasuredHeight() / 2), childAt3.getMeasuredWidth() + i7, (childAt3.getMeasuredHeight() / 2) + measuredHeight);
                measuredWidth = i7 + childAt3.getMeasuredWidth();
            } else {
                return;
            }
        }
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth() - this.mHeroThumbWidthInPixel;
        int i3 = this.mThumbWidthInPixel + this.mMeasuredMarginInPixel;
        int i4 = ((measuredWidth + i3) - 1) / i3;
        if (i4 < 2) {
            i4 = 2;
        } else if ((i4 & 1) != 0) {
            i4++;
        }
        int i5 = i4 + 1;
        if (this.mNumOfThumbs != i5) {
            this.mNumOfThumbs = i5;
            while (getChildCount() > this.mNumOfThumbs) {
                removeView(getChildAt(getChildCount() - 1));
            }
            while (getChildCount() < this.mNumOfThumbs) {
                addView(new ImageView(getContext()), new LinearLayout.LayoutParams(this.mThumbWidthInPixel, this.mThumbHeightInPixel));
            }
            int childCount = getChildCount() / 2;
            for (int i6 = 0; i6 < getChildCount(); i6++) {
                View childAt = getChildAt(i6);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (childCount == i6) {
                    layoutParams.width = this.mHeroThumbWidthInPixel;
                    layoutParams.height = this.mHeroThumbHeightInPixel;
                } else {
                    layoutParams.width = this.mThumbWidthInPixel;
                    layoutParams.height = this.mThumbHeightInPixel;
                }
                childAt.setLayoutParams(layoutParams);
            }
        }
    }
}
