package androidx.slice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.slice.view.R$styleable;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class RowStyle {
    public int mActionDividerHeight = -1;
    public int mBottomDividerEndPadding = -1;
    public int mBottomDividerStartPadding = -1;
    public int mContentEndPadding = -1;
    public int mContentStartPadding = -1;
    public boolean mDisableRecyclerViewItemAnimator = false;
    public int mEndItemEndPadding = -1;
    public int mEndItemStartPadding = -1;
    public int mIconSize = -1;
    public int mImageSize;
    public int mProgressBarEndPadding = -1;
    public int mProgressBarInlineWidth = -1;
    public int mProgressBarStartPadding = -1;
    public int mSeekBarInlineWidth = -1;
    public final SliceStyle mSliceStyle;
    public int mSubContentEndPadding = -1;
    public int mSubContentStartPadding = -1;
    public Integer mSubtitleColor;
    public int mTextActionPadding = -1;
    public Integer mTintColor;
    public Integer mTitleColor;
    public int mTitleEndPadding = -1;
    public int mTitleItemEndPadding = -1;
    public int mTitleItemStartPadding = -1;
    public int mTitleStartPadding = -1;

    public RowStyle(Context context, SliceStyle sliceStyle) {
        this.mSliceStyle = sliceStyle;
        this.mImageSize = context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_small_image_size);
    }

    public final int getSubtitleColor() {
        Integer num = this.mSubtitleColor;
        if (num != null) {
            return num.intValue();
        }
        SliceStyle sliceStyle = this.mSliceStyle;
        Objects.requireNonNull(sliceStyle);
        return sliceStyle.mSubtitleColor;
    }

    public final int getTintColor() {
        Integer num = this.mTintColor;
        if (num != null) {
            return num.intValue();
        }
        SliceStyle sliceStyle = this.mSliceStyle;
        Objects.requireNonNull(sliceStyle);
        return sliceStyle.mTintColor;
    }

    public final int getTitleColor() {
        Integer num = this.mTitleColor;
        if (num != null) {
            return num.intValue();
        }
        SliceStyle sliceStyle = this.mSliceStyle;
        Objects.requireNonNull(sliceStyle);
        return sliceStyle.mTitleColor;
    }

    public static Integer getOptionalColor(TypedArray typedArray, int i) {
        if (typedArray.hasValue(i)) {
            return Integer.valueOf(typedArray.getColor(i, 0));
        }
        return null;
    }

    public RowStyle(Context context, int i, SliceStyle sliceStyle) {
        this.mSliceStyle = sliceStyle;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(i, R$styleable.RowStyle);
        try {
            this.mTitleItemStartPadding = (int) obtainStyledAttributes.getDimension(22, -1.0f);
            this.mTitleItemEndPadding = (int) obtainStyledAttributes.getDimension(21, -1.0f);
            this.mContentStartPadding = (int) obtainStyledAttributes.getDimension(4, -1.0f);
            this.mContentEndPadding = (int) obtainStyledAttributes.getDimension(3, -1.0f);
            this.mTitleStartPadding = (int) obtainStyledAttributes.getDimension(23, -1.0f);
            this.mTitleEndPadding = (int) obtainStyledAttributes.getDimension(20, -1.0f);
            this.mSubContentStartPadding = (int) obtainStyledAttributes.getDimension(15, -1.0f);
            this.mSubContentEndPadding = (int) obtainStyledAttributes.getDimension(14, -1.0f);
            this.mEndItemStartPadding = (int) obtainStyledAttributes.getDimension(7, -1.0f);
            this.mEndItemEndPadding = (int) obtainStyledAttributes.getDimension(6, -1.0f);
            this.mBottomDividerStartPadding = (int) obtainStyledAttributes.getDimension(2, -1.0f);
            this.mBottomDividerEndPadding = (int) obtainStyledAttributes.getDimension(1, -1.0f);
            this.mActionDividerHeight = (int) obtainStyledAttributes.getDimension(0, -1.0f);
            this.mSeekBarInlineWidth = (int) obtainStyledAttributes.getDimension(13, -1.0f);
            this.mProgressBarInlineWidth = (int) obtainStyledAttributes.getDimension(11, -1.0f);
            this.mProgressBarStartPadding = (int) obtainStyledAttributes.getDimension(12, -1.0f);
            this.mProgressBarEndPadding = (int) obtainStyledAttributes.getDimension(10, -1.0f);
            this.mTextActionPadding = (int) obtainStyledAttributes.getDimension(17, 10.0f);
            this.mIconSize = (int) obtainStyledAttributes.getDimension(8, -1.0f);
            this.mDisableRecyclerViewItemAnimator = obtainStyledAttributes.getBoolean(5, false);
            this.mImageSize = (int) obtainStyledAttributes.getDimension(9, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_small_image_size));
            this.mTintColor = getOptionalColor(obtainStyledAttributes, 18);
            this.mTitleColor = getOptionalColor(obtainStyledAttributes, 19);
            this.mSubtitleColor = getOptionalColor(obtainStyledAttributes, 16);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
