package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

public abstract class OrientationHelper {
    public int mLastTotalSpace = Integer.MIN_VALUE;
    public final RecyclerView.LayoutManager mLayoutManager;
    public final Rect mTmpRect = new Rect();

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedMeasurement(View view);

    public abstract int getDecoratedMeasurementInOther(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public abstract int getTransformedEndWithDecoration(View view);

    public abstract int getTransformedStartWithDecoration(View view);

    public abstract void offsetChildren(int i);

    public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager layoutManager, int i) {
        if (i == 0) {
            return new OrientationHelper(layoutManager) {
                public final int getEnd() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mWidth;
                }

                public final int getEndAfterPadding() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mWidth - this.mLayoutManager.getPaddingRight();
                }

                public final int getEndPadding() {
                    return this.mLayoutManager.getPaddingRight();
                }

                public final int getMode() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mWidthMode;
                }

                public final int getModeInOther() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mHeightMode;
                }

                public final int getStartAfterPadding() {
                    return this.mLayoutManager.getPaddingLeft();
                }

                public final int getTotalSpace() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return (layoutManager.mWidth - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
                }

                public final int getTransformedEndWithDecoration(View view) {
                    this.mLayoutManager.getTransformedBoundingBox(view, this.mTmpRect);
                    return this.mTmpRect.right;
                }

                public final int getTransformedStartWithDecoration(View view) {
                    this.mLayoutManager.getTransformedBoundingBox(view, this.mTmpRect);
                    return this.mTmpRect.left;
                }

                public final void offsetChildren(int i) {
                    this.mLayoutManager.offsetChildrenHorizontal(i);
                }

                public final int getDecoratedEnd(View view) {
                    return this.mLayoutManager.getDecoratedRight(view) + ((RecyclerView.LayoutParams) view.getLayoutParams()).rightMargin;
                }

                public final int getDecoratedMeasurement(View view) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    Objects.requireNonNull(this.mLayoutManager);
                    return RecyclerView.LayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
                }

                public final int getDecoratedMeasurementInOther(View view) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    Objects.requireNonNull(this.mLayoutManager);
                    return RecyclerView.LayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
                }

                public final int getDecoratedStart(View view) {
                    return this.mLayoutManager.getDecoratedLeft(view) - ((RecyclerView.LayoutParams) view.getLayoutParams()).leftMargin;
                }
            };
        }
        if (i == 1) {
            return new OrientationHelper(layoutManager) {
                public final int getEnd() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mHeight;
                }

                public final int getEndAfterPadding() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mHeight - this.mLayoutManager.getPaddingBottom();
                }

                public final int getEndPadding() {
                    return this.mLayoutManager.getPaddingBottom();
                }

                public final int getMode() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mHeightMode;
                }

                public final int getModeInOther() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mWidthMode;
                }

                public final int getStartAfterPadding() {
                    return this.mLayoutManager.getPaddingTop();
                }

                public final int getTotalSpace() {
                    RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
                    Objects.requireNonNull(layoutManager);
                    return (layoutManager.mHeight - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
                }

                public final int getTransformedEndWithDecoration(View view) {
                    this.mLayoutManager.getTransformedBoundingBox(view, this.mTmpRect);
                    return this.mTmpRect.bottom;
                }

                public final int getTransformedStartWithDecoration(View view) {
                    this.mLayoutManager.getTransformedBoundingBox(view, this.mTmpRect);
                    return this.mTmpRect.top;
                }

                public final void offsetChildren(int i) {
                    this.mLayoutManager.offsetChildrenVertical(i);
                }

                public final int getDecoratedEnd(View view) {
                    return this.mLayoutManager.getDecoratedBottom(view) + ((RecyclerView.LayoutParams) view.getLayoutParams()).bottomMargin;
                }

                public final int getDecoratedMeasurement(View view) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    Objects.requireNonNull(this.mLayoutManager);
                    return RecyclerView.LayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
                }

                public final int getDecoratedMeasurementInOther(View view) {
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    Objects.requireNonNull(this.mLayoutManager);
                    return RecyclerView.LayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
                }

                public final int getDecoratedStart(View view) {
                    return this.mLayoutManager.getDecoratedTop(view) - ((RecyclerView.LayoutParams) view.getLayoutParams()).topMargin;
                }
            };
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public final int getTotalSpaceChange() {
        if (Integer.MIN_VALUE == this.mLastTotalSpace) {
            return 0;
        }
        return getTotalSpace() - this.mLastTotalSpace;
    }

    public OrientationHelper(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }
}
