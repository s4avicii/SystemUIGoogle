package androidx.slice.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.slice.SliceItem;
import androidx.slice.view.R$styleable;
import com.android.p012wm.shell.C1777R;
import java.util.List;
import java.util.Objects;

public final class SliceStyle {
    public final Context mContext;
    public final int mDefaultRowStyleRes;
    public final boolean mExpandToAvailableHeight;
    public final int mGridAllImagesHeight;
    public final int mGridBigPicMaxHeight;
    public final int mGridBottomPadding;
    public final int mGridImageTextHeight;
    public final int mGridMaxHeight;
    public final int mGridMinHeight;
    public final int mGridRawImageTextHeight;
    public final int mGridSubtitleSize;
    public final int mGridTitleSize;
    public final int mGridTopPadding;
    public final int mHeaderSubtitleSize;
    public final int mHeaderTitleSize;
    public final boolean mHideHeaderRow;
    public final float mImageCornerRadius;
    public final int mListLargeHeight;
    public final int mListMinScrollHeight;
    public final SparseArray<RowStyle> mResourceToRowStyle = new SparseArray<>();
    public final int mRowInlineRangeHeight;
    public final int mRowMaxHeight;
    public final int mRowMinHeight;
    public final int mRowRangeHeight;
    public final int mRowSelectionHeight;
    public final int mRowSingleTextWithRangeHeight;
    public final int mRowSingleTextWithSelectionHeight;
    public final int mRowTextWithRangeHeight;
    public final int mRowTextWithSelectionHeight;
    public final int mSubtitleColor;
    public final int mSubtitleSize;
    public int mTintColor = -1;
    public final int mTitleColor;
    public final int mTitleSize;
    public final int mVerticalGridTextPadding;
    public final int mVerticalHeaderTextPadding;
    public final int mVerticalTextPadding;

    public final int getListItemsHeight(List<SliceContent> list, SliceViewPolicy sliceViewPolicy) {
        if (list == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            SliceContent sliceContent = list.get(i2);
            if (i2 != 0 || !shouldSkipFirstListItem(list)) {
                i = sliceContent.getHeight(this, sliceViewPolicy) + i;
            }
        }
        return i;
    }

    public final RowStyle getRowStyle(SliceItem sliceItem) {
        int i = this.mDefaultRowStyleRes;
        if (i == 0) {
            return new RowStyle(this.mContext, this);
        }
        RowStyle rowStyle = this.mResourceToRowStyle.get(i);
        if (rowStyle != null) {
            return rowStyle;
        }
        RowStyle rowStyle2 = new RowStyle(this.mContext, i, this);
        this.mResourceToRowStyle.put(i, rowStyle2);
        return rowStyle2;
    }

    public final boolean shouldSkipFirstListItem(List<SliceContent> list) {
        if (this.mHideHeaderRow && list.size() > 1 && (list.get(0) instanceof RowContent)) {
            RowContent rowContent = (RowContent) list.get(0);
            Objects.requireNonNull(rowContent);
            if (rowContent.mIsHeader) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    public SliceStyle(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.SliceView, C1777R.attr.sliceViewStyle, 2132018739);
        try {
            int color = obtainStyledAttributes.getColor(20, -1);
            if (color == -1) {
                color = this.mTintColor;
            }
            this.mTintColor = color;
            this.mTitleColor = obtainStyledAttributes.getColor(21, 0);
            this.mSubtitleColor = obtainStyledAttributes.getColor(17, 0);
            this.mHeaderTitleSize = (int) obtainStyledAttributes.getDimension(8, 0.0f);
            this.mHeaderSubtitleSize = (int) obtainStyledAttributes.getDimension(6, 0.0f);
            this.mVerticalHeaderTextPadding = (int) obtainStyledAttributes.getDimension(7, 0.0f);
            this.mTitleSize = (int) obtainStyledAttributes.getDimension(22, 0.0f);
            this.mSubtitleSize = (int) obtainStyledAttributes.getDimension(18, 0.0f);
            this.mVerticalTextPadding = (int) obtainStyledAttributes.getDimension(19, 0.0f);
            this.mGridTitleSize = (int) obtainStyledAttributes.getDimension(4, 0.0f);
            this.mGridSubtitleSize = (int) obtainStyledAttributes.getDimension(2, 0.0f);
            this.mVerticalGridTextPadding = (int) obtainStyledAttributes.getDimension(3, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_grid_text_inner_padding));
            this.mGridTopPadding = (int) obtainStyledAttributes.getDimension(5, 0.0f);
            this.mGridBottomPadding = (int) obtainStyledAttributes.getDimension(1, 0.0f);
            this.mDefaultRowStyleRes = obtainStyledAttributes.getResourceId(16, 0);
            this.mRowMinHeight = (int) obtainStyledAttributes.getDimension(13, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_row_min_height));
            this.mRowMaxHeight = (int) obtainStyledAttributes.getDimension(12, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_row_max_height));
            this.mRowRangeHeight = (int) obtainStyledAttributes.getDimension(14, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_row_range_height));
            this.mRowSingleTextWithRangeHeight = (int) obtainStyledAttributes.getDimension(15, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_row_range_single_text_height));
            this.mRowInlineRangeHeight = (int) obtainStyledAttributes.getDimension(11, (float) context.getResources().getDimensionPixelSize(C1777R.dimen.abc_slice_row_range_inline_height));
            this.mExpandToAvailableHeight = obtainStyledAttributes.getBoolean(0, false);
            this.mHideHeaderRow = obtainStyledAttributes.getBoolean(9, false);
            this.mContext = context;
            this.mImageCornerRadius = obtainStyledAttributes.getDimension(10, 0.0f);
            obtainStyledAttributes.recycle();
            Resources resources = context.getResources();
            this.mRowTextWithRangeHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_row_range_multi_text_height);
            this.mRowSelectionHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_row_selection_height);
            this.mRowTextWithSelectionHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_row_selection_multi_text_height);
            this.mRowSingleTextWithSelectionHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_row_selection_single_text_height);
            resources.getDimensionPixelSize(C1777R.dimen.abc_slice_big_pic_min_height);
            this.mGridBigPicMaxHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_big_pic_max_height);
            this.mGridAllImagesHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_image_only_height);
            this.mGridImageTextHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_image_text_height);
            this.mGridRawImageTextHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_raw_image_text_offset);
            this.mGridMinHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_min_height);
            this.mGridMaxHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_grid_max_height);
            this.mListMinScrollHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_row_min_height);
            this.mListLargeHeight = resources.getDimensionPixelSize(C1777R.dimen.abc_slice_large_height);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
