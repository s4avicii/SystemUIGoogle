package androidx.leanback.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.CircularIntArray;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.leanback.widget.BaseGridView;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.ItemAlignment;
import androidx.leanback.widget.ItemAlignmentFacet;
import androidx.leanback.widget.WindowAlignment;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public final class GridLayoutManager extends RecyclerView.LayoutManager {
    public static final Rect sTempRect = new Rect();
    public static int[] sTwoInts = new int[2];
    public BaseGridView mBaseGridView;
    public ArrayList<OnChildViewHolderSelectedListener> mChildViewHolderSelectedListeners;
    public int mChildVisibility;
    public final ViewsStateBundle mChildrenStates;
    public GridLinearSmoothScroller mCurrentSmoothScroller;
    public int[] mDisappearingPositions;
    public int mExtraLayoutSpaceInPreLayout;
    public FacetProviderAdapter mFacetProviderAdapter;
    public int mFixedRowSizeSecondary;
    public int mFlag;
    public int mFocusPosition;
    public int mFocusPositionOffset;
    public int mGravity;
    public Grid mGrid;
    public C02232 mGridProvider;
    public final ItemAlignment mItemAlignment;
    public int mMaxPendingMoves;
    public int mMaxSizeSecondary;
    public int[] mMeasuredDimension;
    public int mNumRows;
    public int mNumRowsRequested;
    public ArrayList<BaseGridView.OnLayoutCompletedListener> mOnLayoutCompletedListeners;
    public int mOrientation;
    public OrientationHelper mOrientationHelper;
    public PendingMoveSmoothScroller mPendingMoveSmoothScroller;
    public int mPositionDeltaInPreLayout;
    public final SparseIntArray mPositionToRowInPostLayout;
    public int mPrimaryScrollExtra;
    public RecyclerView.Recycler mRecycler;
    public final C02221 mRequestLayoutRunnable;
    public int[] mRowSizeSecondary;
    public int mRowSizeSecondaryRequested;
    public int mSaveContextLevel;
    public int mScrollOffsetSecondary;
    public int mSizePrimary;
    public float mSmoothScrollSpeedFactor;
    public int mSpacingPrimary;
    public int mSpacingSecondary;
    public RecyclerView.State mState;
    public int mSubFocusPosition;
    public int mVerticalSpacing;
    public final WindowAlignment mWindowAlignment;

    public abstract class GridLinearSmoothScroller extends LinearSmoothScroller {
        public boolean mSkipOnStopInternal;

        public GridLinearSmoothScroller() {
            super(GridLayoutManager.this.mBaseGridView.getContext());
        }

        public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return (25.0f / ((float) displayMetrics.densityDpi)) * GridLayoutManager.this.mSmoothScrollSpeedFactor;
        }

        public void onStopInternal() {
            View findViewByPosition = this.mRecyclerView.mLayout.findViewByPosition(this.mTargetPosition);
            if (findViewByPosition == null) {
                int i = this.mTargetPosition;
                if (i >= 0) {
                    GridLayoutManager.this.scrollToSelection(i, false);
                    return;
                }
                return;
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            int i2 = gridLayoutManager.mFocusPosition;
            int i3 = this.mTargetPosition;
            if (i2 != i3) {
                gridLayoutManager.mFocusPosition = i3;
            }
            if (gridLayoutManager.hasFocus()) {
                GridLayoutManager.this.mFlag |= 32;
                findViewByPosition.requestFocus();
                GridLayoutManager.this.mFlag &= -33;
            }
            GridLayoutManager.this.dispatchChildSelected();
            GridLayoutManager.this.dispatchChildSelectedAndPositioned();
        }

        public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
            int i;
            int i2;
            if (GridLayoutManager.this.getScrollPosition(view, (View) null, GridLayoutManager.sTwoInts)) {
                if (GridLayoutManager.this.mOrientation == 0) {
                    int[] iArr = GridLayoutManager.sTwoInts;
                    i2 = iArr[0];
                    i = iArr[1];
                } else {
                    int[] iArr2 = GridLayoutManager.sTwoInts;
                    int i3 = iArr2[1];
                    i = iArr2[0];
                    i2 = i3;
                }
                action.update(i2, i, calculateTimeForDeceleration((int) Math.sqrt((double) ((i * i) + (i2 * i2)))), this.mDecelerateInterpolator);
            }
        }

        public final int calculateTimeForScrolling(int i) {
            int calculateTimeForScrolling = super.calculateTimeForScrolling(i);
            WindowAlignment windowAlignment = GridLayoutManager.this.mWindowAlignment;
            Objects.requireNonNull(windowAlignment);
            WindowAlignment.Axis axis = windowAlignment.mMainAxis;
            Objects.requireNonNull(axis);
            if (axis.mSize <= 0) {
                return calculateTimeForScrolling;
            }
            WindowAlignment windowAlignment2 = GridLayoutManager.this.mWindowAlignment;
            Objects.requireNonNull(windowAlignment2);
            WindowAlignment.Axis axis2 = windowAlignment2.mMainAxis;
            Objects.requireNonNull(axis2);
            float f = (30.0f / ((float) axis2.mSize)) * ((float) i);
            if (((float) calculateTimeForScrolling) < f) {
                return (int) f;
            }
            return calculateTimeForScrolling;
        }

        public final void onStop() {
            super.onStop();
            if (!this.mSkipOnStopInternal) {
                onStopInternal();
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if (gridLayoutManager.mCurrentSmoothScroller == this) {
                gridLayoutManager.mCurrentSmoothScroller = null;
            }
            if (gridLayoutManager.mPendingMoveSmoothScroller == this) {
                gridLayoutManager.mPendingMoveSmoothScroller = null;
            }
        }
    }

    public static final class LayoutParams extends RecyclerView.LayoutParams {
        public int[] mAlignMultiple;
        public int mAlignX;
        public int mAlignY;
        public ItemAlignmentFacet mAlignmentFacet;
        public int mBottomInset;
        public int mLeftInset;
        public int mRightInset;
        public int mTopInset;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams() {
            super(-2, -2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((RecyclerView.LayoutParams) layoutParams);
        }
    }

    public final class PendingMoveSmoothScroller extends GridLinearSmoothScroller {
        public int mPendingMoves;
        public final boolean mStaggeredGrid;

        public PendingMoveSmoothScroller(int i, boolean z) {
            super();
            this.mPendingMoves = i;
            this.mStaggeredGrid = z;
            this.mTargetPosition = -2;
        }

        public final PointF computeScrollVectorForPosition(int i) {
            int i2;
            int i3 = this.mPendingMoves;
            if (i3 == 0) {
                return null;
            }
            GridLayoutManager gridLayoutManager = GridLayoutManager.this;
            if ((gridLayoutManager.mFlag & 262144) == 0 ? i3 >= 0 : i3 <= 0) {
                i2 = 1;
            } else {
                i2 = -1;
            }
            if (gridLayoutManager.mOrientation == 0) {
                return new PointF((float) i2, 0.0f);
            }
            return new PointF(0.0f, (float) i2);
        }

        public final void onStopInternal() {
            super.onStopInternal();
            this.mPendingMoves = 0;
            View findViewByPosition = this.mRecyclerView.mLayout.findViewByPosition(this.mTargetPosition);
            if (findViewByPosition != null) {
                GridLayoutManager.this.scrollToView(findViewByPosition, true);
            }
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public Bundle childStates = Bundle.EMPTY;
        public int index;

        public SavedState(Parcel parcel) {
            this.index = parcel.readInt();
            this.childStates = parcel.readBundle(GridLayoutManager.class.getClassLoader());
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.index);
            parcel.writeBundle(this.childStates);
        }

        public SavedState() {
        }
    }

    public GridLayoutManager() {
        this((BaseGridView) null);
    }

    public static int getAdapterPositionByView(View view) {
        LayoutParams layoutParams;
        if (view == null || (layoutParams = (LayoutParams) view.getLayoutParams()) == null || layoutParams.isItemRemoved()) {
            return -1;
        }
        return layoutParams.mViewHolder.getAbsoluteAdapterPosition();
    }

    public final void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        try {
            saveContext((RecyclerView.Recycler) null, state);
            if (this.mOrientation != 0) {
                i = i2;
            }
            if (getChildCount() != 0) {
                if (i != 0) {
                    int i3 = 0;
                    if (i >= 0) {
                        i3 = 0 + this.mSizePrimary;
                    }
                    this.mGrid.collectAdjacentPrefetchPositions(i3, i, layoutPrefetchRegistry);
                    leaveContext();
                }
            }
        } finally {
            leaveContext();
        }
    }

    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public final void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
        if (adapter != null) {
            this.mGrid = null;
            this.mRowSizeSecondary = null;
            this.mFlag &= -1025;
            this.mFocusPosition = -1;
            this.mFocusPositionOffset = 0;
            Objects.requireNonNull(this.mChildrenStates);
        }
        if (adapter2 instanceof FacetProviderAdapter) {
            this.mFacetProviderAdapter = (FacetProviderAdapter) adapter2;
        } else {
            this.mFacetProviderAdapter = null;
        }
    }

    public final void onItemsChanged() {
        this.mFocusPositionOffset = 0;
        Objects.requireNonNull(this.mChildrenStates);
    }

    public final void onItemsUpdated(int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            Objects.requireNonNull(this.mChildrenStates);
            i++;
        }
    }

    public final boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
        return false;
    }

    public final int scrollDirectionSecondary(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        int i3 = -i;
        int childCount = getChildCount();
        if (this.mOrientation == 0) {
            while (i2 < childCount) {
                getChildAt(i2).offsetTopAndBottom(i3);
                i2++;
            }
        } else {
            while (i2 < childCount) {
                getChildAt(i2).offsetLeftAndRight(i3);
                i2++;
            }
        }
        this.mScrollOffsetSecondary += i;
        updateSecondaryScrollLimits();
        this.mBaseGridView.invalidate();
        return i;
    }

    public final void scrollToPosition(int i) {
        setSelection(i, false);
    }

    public final void scrollToSelection(int i, boolean z) {
        this.mPrimaryScrollExtra = 0;
        View findViewByPosition = findViewByPosition(i);
        boolean z2 = true;
        boolean z3 = !isSmoothScrolling();
        if (!z3 || this.mBaseGridView.isLayoutRequested() || findViewByPosition == null || getAdapterPositionByView(findViewByPosition) != i) {
            int i2 = this.mFlag;
            if ((i2 & 512) == 0 || (i2 & 64) != 0) {
                this.mFocusPosition = i;
                this.mSubFocusPosition = 0;
                this.mFocusPositionOffset = Integer.MIN_VALUE;
            } else if (!z || this.mBaseGridView.isLayoutRequested()) {
                if (!z3) {
                    GridLinearSmoothScroller gridLinearSmoothScroller = this.mCurrentSmoothScroller;
                    if (gridLinearSmoothScroller != null) {
                        gridLinearSmoothScroller.mSkipOnStopInternal = true;
                    }
                    this.mBaseGridView.stopScroll();
                }
                if (this.mBaseGridView.isLayoutRequested() || findViewByPosition == null || getAdapterPositionByView(findViewByPosition) != i) {
                    this.mFocusPosition = i;
                    this.mSubFocusPosition = 0;
                    this.mFocusPositionOffset = Integer.MIN_VALUE;
                    this.mFlag |= 256;
                    requestLayout();
                    return;
                }
                this.mFlag |= 32;
                scrollToView(findViewByPosition, z);
                this.mFlag &= -33;
            } else {
                this.mFocusPosition = i;
                this.mSubFocusPosition = 0;
                this.mFocusPositionOffset = Integer.MIN_VALUE;
                if (this.mGrid == null) {
                    z2 = false;
                }
                if (!z2) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("GridLayoutManager:");
                    m.append(this.mBaseGridView.getId());
                    Log.w(m.toString(), "setSelectionSmooth should not be called before first layout pass");
                    return;
                }
                C02244 r9 = new GridLinearSmoothScroller() {
                    public final PointF computeScrollVectorForPosition(int i) {
                        if (this.mRecyclerView.mLayout.getChildCount() == 0) {
                            return null;
                        }
                        boolean z = false;
                        int position = RecyclerView.LayoutManager.getPosition(GridLayoutManager.this.getChildAt(0));
                        GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                        int i2 = 1;
                        if ((gridLayoutManager.mFlag & 262144) == 0 ? i < position : i > position) {
                            z = true;
                        }
                        if (z) {
                            i2 = -1;
                        }
                        if (gridLayoutManager.mOrientation == 0) {
                            return new PointF((float) i2, 0.0f);
                        }
                        return new PointF(0.0f, (float) i2);
                    }
                };
                r9.mTargetPosition = i;
                startSmoothScroll(r9);
                int i3 = r9.mTargetPosition;
                if (i3 != this.mFocusPosition) {
                    this.mFocusPosition = i3;
                    this.mSubFocusPosition = 0;
                }
            }
        } else {
            this.mFlag |= 32;
            scrollToView(findViewByPosition, z);
            this.mFlag &= -33;
        }
    }

    public final void scrollToView(View view, boolean z) {
        scrollToView(view, view.findFocus(), z, 0, 0);
    }

    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        setSelection(i, true);
    }

    public GridLayoutManager(BaseGridView baseGridView) {
        this.mSmoothScrollSpeedFactor = 1.0f;
        this.mMaxPendingMoves = 10;
        this.mOrientation = 0;
        this.mOrientationHelper = new OrientationHelper(this) {
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
        this.mPositionToRowInPostLayout = new SparseIntArray();
        this.mFlag = 221696;
        this.mChildViewHolderSelectedListeners = null;
        this.mOnLayoutCompletedListeners = null;
        this.mFocusPosition = -1;
        this.mSubFocusPosition = 0;
        this.mFocusPositionOffset = 0;
        this.mGravity = 8388659;
        this.mNumRowsRequested = 1;
        this.mWindowAlignment = new WindowAlignment();
        this.mItemAlignment = new ItemAlignment();
        this.mMeasuredDimension = new int[2];
        this.mChildrenStates = new ViewsStateBundle();
        this.mRequestLayoutRunnable = new Runnable() {
            public final void run() {
                GridLayoutManager.this.requestLayout();
            }
        };
        this.mGridProvider = new Grid.Provider() {
            public final void addItem(Object obj, int i, int i2, int i3) {
                int i4;
                int i5;
                PendingMoveSmoothScroller pendingMoveSmoothScroller;
                int i6;
                int i7;
                View view = (View) obj;
                if (i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE) {
                    Grid grid = GridLayoutManager.this.mGrid;
                    Objects.requireNonNull(grid);
                    if (!grid.mReversedFlow) {
                        WindowAlignment windowAlignment = GridLayoutManager.this.mWindowAlignment;
                        Objects.requireNonNull(windowAlignment);
                        WindowAlignment.Axis axis = windowAlignment.mMainAxis;
                        Objects.requireNonNull(axis);
                        i7 = axis.mPaddingMin;
                    } else {
                        WindowAlignment windowAlignment2 = GridLayoutManager.this.mWindowAlignment;
                        Objects.requireNonNull(windowAlignment2);
                        WindowAlignment.Axis axis2 = windowAlignment2.mMainAxis;
                        Objects.requireNonNull(axis2);
                        int i8 = axis2.mSize;
                        WindowAlignment windowAlignment3 = GridLayoutManager.this.mWindowAlignment;
                        Objects.requireNonNull(windowAlignment3);
                        WindowAlignment.Axis axis3 = windowAlignment3.mMainAxis;
                        Objects.requireNonNull(axis3);
                        i7 = i8 - axis3.mPaddingMax;
                    }
                    i3 = i7;
                }
                Grid grid2 = GridLayoutManager.this.mGrid;
                Objects.requireNonNull(grid2);
                if (!grid2.mReversedFlow) {
                    i4 = i + i3;
                    i5 = i3;
                } else {
                    i5 = i3 - i;
                    i4 = i3;
                }
                int rowStartSecondary = GridLayoutManager.this.getRowStartSecondary(i2);
                WindowAlignment windowAlignment4 = GridLayoutManager.this.mWindowAlignment;
                Objects.requireNonNull(windowAlignment4);
                WindowAlignment.Axis axis4 = windowAlignment4.mSecondAxis;
                Objects.requireNonNull(axis4);
                int i9 = rowStartSecondary + axis4.mPaddingMin;
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                int i10 = i9 - gridLayoutManager.mScrollOffsetSecondary;
                Objects.requireNonNull(gridLayoutManager.mChildrenStates);
                GridLayoutManager.this.layoutChild(i2, view, i5, i4, i10);
                RecyclerView.State state = GridLayoutManager.this.mState;
                Objects.requireNonNull(state);
                if (!state.mInPreLayout) {
                    GridLayoutManager.this.updateScrollLimits();
                }
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                if (!((gridLayoutManager2.mFlag & 3) == 1 || (pendingMoveSmoothScroller = gridLayoutManager2.mPendingMoveSmoothScroller) == null)) {
                    if (pendingMoveSmoothScroller.mStaggeredGrid && (i6 = pendingMoveSmoothScroller.mPendingMoves) != 0) {
                        pendingMoveSmoothScroller.mPendingMoves = GridLayoutManager.this.processSelectionMoves(true, i6);
                    }
                    int i11 = pendingMoveSmoothScroller.mPendingMoves;
                    if (i11 == 0 || ((i11 > 0 && GridLayoutManager.this.hasCreatedLastItem()) || (pendingMoveSmoothScroller.mPendingMoves < 0 && GridLayoutManager.this.hasCreatedFirstItem()))) {
                        pendingMoveSmoothScroller.mTargetPosition = GridLayoutManager.this.mFocusPosition;
                        pendingMoveSmoothScroller.stop();
                    }
                }
                Objects.requireNonNull(GridLayoutManager.this);
            }

            /* JADX WARNING: type inference failed for: r10v0, types: [java.lang.Object[]] */
            /* JADX WARNING: Removed duplicated region for block: B:28:0x007b  */
            /* JADX WARNING: Removed duplicated region for block: B:47:0x00bb A[ADDED_TO_REGION] */
            /* JADX WARNING: Unknown variable types count: 1 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final int createItem(int r8, boolean r9, java.lang.Object[] r10, boolean r11) {
                /*
                    r7 = this;
                    androidx.leanback.widget.GridLayoutManager r0 = androidx.leanback.widget.GridLayoutManager.this
                    int r1 = r0.mPositionDeltaInPreLayout
                    int r1 = r8 - r1
                    android.view.View r0 = r0.getViewForPosition(r1)
                    android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
                    androidx.leanback.widget.GridLayoutManager$LayoutParams r1 = (androidx.leanback.widget.GridLayoutManager.LayoutParams) r1
                    boolean r1 = r1.isItemRemoved()
                    r2 = 0
                    if (r1 != 0) goto L_0x0131
                    r1 = -1
                    r3 = 1
                    if (r11 == 0) goto L_0x002f
                    if (r9 == 0) goto L_0x0026
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    java.util.Objects.requireNonNull(r9)
                    r9.addViewInt(r0, r1, r3)
                    goto L_0x0042
                L_0x0026:
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    java.util.Objects.requireNonNull(r9)
                    r9.addViewInt(r0, r2, r3)
                    goto L_0x0042
                L_0x002f:
                    if (r9 == 0) goto L_0x003a
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    java.util.Objects.requireNonNull(r9)
                    r9.addViewInt(r0, r1, r2)
                    goto L_0x0042
                L_0x003a:
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    java.util.Objects.requireNonNull(r9)
                    r9.addViewInt(r0, r2, r2)
                L_0x0042:
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    int r9 = r9.mChildVisibility
                    if (r9 == r1) goto L_0x004b
                    r0.setVisibility(r9)
                L_0x004b:
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    androidx.leanback.widget.GridLayoutManager$PendingMoveSmoothScroller r9 = r9.mPendingMoveSmoothScroller
                    if (r9 == 0) goto L_0x00d8
                    boolean r11 = r9.mStaggeredGrid
                    if (r11 != 0) goto L_0x00d8
                    int r11 = r9.mPendingMoves
                    if (r11 != 0) goto L_0x005b
                    goto L_0x00d8
                L_0x005b:
                    r1 = 0
                    if (r11 <= 0) goto L_0x0065
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    int r4 = r11.mFocusPosition
                    int r11 = r11.mNumRows
                    goto L_0x00b4
                L_0x0065:
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    int r4 = r11.mFocusPosition
                    int r11 = r11.mNumRows
                L_0x006b:
                    int r4 = r4 - r11
                L_0x006c:
                    int r11 = r9.mPendingMoves
                    if (r11 == 0) goto L_0x00bb
                    androidx.recyclerview.widget.RecyclerView r11 = r9.mRecyclerView
                    androidx.recyclerview.widget.RecyclerView$LayoutManager r11 = r11.mLayout
                    android.view.View r11 = r11.findViewByPosition(r4)
                    if (r11 != 0) goto L_0x007b
                    goto L_0x00bb
                L_0x007b:
                    androidx.leanback.widget.GridLayoutManager r5 = androidx.leanback.widget.GridLayoutManager.this
                    java.util.Objects.requireNonNull(r5)
                    int r6 = r11.getVisibility()
                    if (r6 != 0) goto L_0x0094
                    boolean r5 = r5.hasFocus()
                    if (r5 == 0) goto L_0x0092
                    boolean r5 = r11.hasFocusable()
                    if (r5 == 0) goto L_0x0094
                L_0x0092:
                    r5 = r3
                    goto L_0x0095
                L_0x0094:
                    r5 = r2
                L_0x0095:
                    if (r5 != 0) goto L_0x0098
                    goto L_0x00ac
                L_0x0098:
                    androidx.leanback.widget.GridLayoutManager r1 = androidx.leanback.widget.GridLayoutManager.this
                    r1.mFocusPosition = r4
                    r1.mSubFocusPosition = r2
                    int r1 = r9.mPendingMoves
                    if (r1 <= 0) goto L_0x00a7
                    int r1 = r1 + -1
                    r9.mPendingMoves = r1
                    goto L_0x00ab
                L_0x00a7:
                    int r1 = r1 + 1
                    r9.mPendingMoves = r1
                L_0x00ab:
                    r1 = r11
                L_0x00ac:
                    int r11 = r9.mPendingMoves
                    if (r11 <= 0) goto L_0x00b6
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    int r11 = r11.mNumRows
                L_0x00b4:
                    int r4 = r4 + r11
                    goto L_0x006c
                L_0x00b6:
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    int r11 = r11.mNumRows
                    goto L_0x006b
                L_0x00bb:
                    if (r1 == 0) goto L_0x00d8
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    boolean r11 = r11.hasFocus()
                    if (r11 == 0) goto L_0x00d8
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    int r4 = r11.mFlag
                    r4 = r4 | 32
                    r11.mFlag = r4
                    r1.requestFocus()
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    int r11 = r9.mFlag
                    r11 = r11 & -33
                    r9.mFlag = r11
                L_0x00d8:
                    androidx.leanback.widget.GridLayoutManager r9 = androidx.leanback.widget.GridLayoutManager.this
                    android.view.View r11 = r0.findFocus()
                    java.util.Objects.requireNonNull(r9)
                    int r9 = androidx.leanback.widget.GridLayoutManager.getSubPositionByView(r0, r11)
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    int r1 = r11.mFlag
                    r4 = r1 & 3
                    if (r4 == r3) goto L_0x00fd
                    int r1 = r11.mFocusPosition
                    if (r8 != r1) goto L_0x012c
                    int r8 = r11.mSubFocusPosition
                    if (r9 != r8) goto L_0x012c
                    androidx.leanback.widget.GridLayoutManager$PendingMoveSmoothScroller r8 = r11.mPendingMoveSmoothScroller
                    if (r8 != 0) goto L_0x012c
                    r11.dispatchChildSelected()
                    goto L_0x012c
                L_0x00fd:
                    r3 = r1 & 4
                    if (r3 != 0) goto L_0x012c
                    r1 = r1 & 16
                    if (r1 != 0) goto L_0x0111
                    int r3 = r11.mFocusPosition
                    if (r8 != r3) goto L_0x0111
                    int r3 = r11.mSubFocusPosition
                    if (r9 != r3) goto L_0x0111
                    r11.dispatchChildSelected()
                    goto L_0x012c
                L_0x0111:
                    if (r1 == 0) goto L_0x012c
                    int r11 = r11.mFocusPosition
                    if (r8 < r11) goto L_0x012c
                    boolean r11 = r0.hasFocusable()
                    if (r11 == 0) goto L_0x012c
                    androidx.leanback.widget.GridLayoutManager r11 = androidx.leanback.widget.GridLayoutManager.this
                    r11.mFocusPosition = r8
                    r11.mSubFocusPosition = r9
                    int r8 = r11.mFlag
                    r8 = r8 & -17
                    r11.mFlag = r8
                    r11.dispatchChildSelected()
                L_0x012c:
                    androidx.leanback.widget.GridLayoutManager r8 = androidx.leanback.widget.GridLayoutManager.this
                    r8.measureChild(r0)
                L_0x0131:
                    r10[r2] = r0
                    androidx.leanback.widget.GridLayoutManager r7 = androidx.leanback.widget.GridLayoutManager.this
                    int r7 = r7.mOrientation
                    if (r7 != 0) goto L_0x013e
                    int r7 = androidx.leanback.widget.GridLayoutManager.getDecoratedMeasuredWidthWithMargin(r0)
                    goto L_0x0142
                L_0x013e:
                    int r7 = androidx.leanback.widget.GridLayoutManager.getDecoratedMeasuredHeightWithMargin(r0)
                L_0x0142:
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.C02232.createItem(int, boolean, java.lang.Object[], boolean):int");
            }

            public final int getCount() {
                return GridLayoutManager.this.mState.getItemCount() + GridLayoutManager.this.mPositionDeltaInPreLayout;
            }

            public final int getEdge(int i) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View findViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                if ((gridLayoutManager2.mFlag & 262144) != 0) {
                    return gridLayoutManager2.mOrientationHelper.getDecoratedEnd(findViewByPosition);
                }
                return gridLayoutManager2.mOrientationHelper.getDecoratedStart(findViewByPosition);
            }

            public final int getSize(int i) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View findViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
                Rect rect = GridLayoutManager.sTempRect;
                gridLayoutManager.getDecoratedBoundsWithMargins(findViewByPosition, rect);
                if (gridLayoutManager.mOrientation == 0) {
                    return rect.width();
                }
                return rect.height();
            }

            public final void removeItem(int i) {
                GridLayoutManager gridLayoutManager = GridLayoutManager.this;
                View findViewByPosition = gridLayoutManager.findViewByPosition(i - gridLayoutManager.mPositionDeltaInPreLayout);
                GridLayoutManager gridLayoutManager2 = GridLayoutManager.this;
                if ((gridLayoutManager2.mFlag & 3) == 1) {
                    gridLayoutManager2.scrapOrRecycleView(gridLayoutManager2.mRecycler, gridLayoutManager2.mChildHelper.indexOfChild(findViewByPosition), findViewByPosition);
                } else {
                    gridLayoutManager2.removeAndRecycleView(findViewByPosition, gridLayoutManager2.mRecycler);
                }
            }
        };
        this.mBaseGridView = baseGridView;
        this.mChildVisibility = -1;
        if (this.mItemPrefetchEnabled) {
            this.mItemPrefetchEnabled = false;
            this.mPrefetchMaxCountObserved = 0;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.mRecycler.updateViewCacheSize();
            }
        }
    }

    public static int getSubPositionByView(View view, View view2) {
        if (view == null || view2 == null) {
            return 0;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Objects.requireNonNull(layoutParams);
        ItemAlignmentFacet itemAlignmentFacet = layoutParams.mAlignmentFacet;
        if (itemAlignmentFacet == null) {
            return 0;
        }
        ItemAlignmentFacet.ItemAlignmentDef[] itemAlignmentDefArr = itemAlignmentFacet.mAlignmentDefs;
        if (itemAlignmentDefArr.length <= 1) {
            return 0;
        }
        while (view2 != view) {
            int id = view2.getId();
            if (id != -1) {
                for (int i = 1; i < itemAlignmentDefArr.length; i++) {
                    Objects.requireNonNull(itemAlignmentDefArr[i]);
                    if (-1 == id) {
                        return i;
                    }
                }
                continue;
            }
            view2 = (View) view2.getParent();
        }
        return 0;
    }

    public final void appendVisibleItems() {
        int i;
        Grid grid = this.mGrid;
        if ((this.mFlag & 262144) != 0) {
            i = 0 - this.mExtraLayoutSpaceInPreLayout;
        } else {
            i = this.mExtraLayoutSpaceInPreLayout + this.mSizePrimary + 0;
        }
        Objects.requireNonNull(grid);
        grid.appendVisibleItems(i, false);
    }

    public final boolean canScrollHorizontally() {
        if (this.mOrientation == 0 || this.mNumRows > 1) {
            return true;
        }
        return false;
    }

    public final boolean canScrollVertically() {
        if (this.mOrientation == 1 || this.mNumRows > 1) {
            return true;
        }
        return false;
    }

    public final void collectInitialPrefetchPositions(int i, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i2 = this.mBaseGridView.mInitialPrefetchItemCount;
        if (i != 0 && i2 != 0) {
            int max = Math.max(0, Math.min(this.mFocusPosition - ((i2 - 1) / 2), i - i2));
            int i3 = max;
            while (i3 < i && i3 < max + i2) {
                ((GapWorker.LayoutPrefetchRegistryImpl) layoutPrefetchRegistry).addPosition(i3, 0);
                i3++;
            }
        }
    }

    public final void dispatchChildSelected() {
        boolean z;
        View view;
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            int i = this.mFocusPosition;
            if (i == -1) {
                view = null;
            } else {
                view = findViewByPosition(i);
            }
            if (view != null) {
                RecyclerView.ViewHolder childViewHolder = this.mBaseGridView.getChildViewHolder(view);
                BaseGridView baseGridView = this.mBaseGridView;
                int i2 = this.mFocusPosition;
                ArrayList<OnChildViewHolderSelectedListener> arrayList2 = this.mChildViewHolderSelectedListeners;
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        this.mChildViewHolderSelectedListeners.get(size).onChildViewHolderSelected(baseGridView, childViewHolder, i2);
                    }
                }
            } else {
                BaseGridView baseGridView2 = this.mBaseGridView;
                ArrayList<OnChildViewHolderSelectedListener> arrayList3 = this.mChildViewHolderSelectedListeners;
                if (arrayList3 != null) {
                    int size2 = arrayList3.size();
                    while (true) {
                        size2--;
                        if (size2 < 0) {
                            break;
                        }
                        this.mChildViewHolderSelectedListeners.get(size2).onChildViewHolderSelected(baseGridView2, (RecyclerView.ViewHolder) null, -1);
                    }
                }
            }
            if ((this.mFlag & 3) != 1 && !this.mBaseGridView.isLayoutRequested()) {
                int childCount = getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    if (getChildAt(i3).isLayoutRequested()) {
                        BaseGridView baseGridView3 = this.mBaseGridView;
                        C02221 r8 = this.mRequestLayoutRunnable;
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api16Impl.postOnAnimation(baseGridView3, r8);
                        return;
                    }
                }
            }
        }
    }

    public final void dispatchChildSelectedAndPositioned() {
        boolean z;
        ArrayList<OnChildViewHolderSelectedListener> arrayList = this.mChildViewHolderSelectedListeners;
        if (arrayList == null || arrayList.size() <= 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            int i = this.mFocusPosition;
            View view = null;
            if (i != -1) {
                view = findViewByPosition(i);
            }
            if (view != null) {
                this.mBaseGridView.getChildViewHolder(view);
                ArrayList<OnChildViewHolderSelectedListener> arrayList2 = this.mChildViewHolderSelectedListeners;
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    while (true) {
                        size--;
                        if (size >= 0) {
                            Objects.requireNonNull(this.mChildViewHolderSelectedListeners.get(size));
                        } else {
                            return;
                        }
                    }
                }
            } else {
                ArrayList<OnChildViewHolderSelectedListener> arrayList3 = this.mChildViewHolderSelectedListeners;
                if (arrayList3 != null) {
                    int size2 = arrayList3.size();
                    while (true) {
                        size2--;
                        if (size2 >= 0) {
                            Objects.requireNonNull(this.mChildViewHolderSelectedListeners.get(size2));
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof RecyclerView.LayoutParams) {
            return new LayoutParams((RecyclerView.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        if (this.mOrientation != 1 || (grid = this.mGrid) == null) {
            return -1;
        }
        Objects.requireNonNull(grid);
        return grid.mNumRows;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        if (r10 != 130) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        if ((r9.mFlag & 524288) == 0) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0043, code lost:
        if ((r9.mFlag & 524288) == 0) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0018, code lost:
        if (r10 != 130) goto L_0x0046;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getMovement(int r10) {
        /*
            r9 = this;
            int r0 = r9.mOrientation
            r1 = 130(0x82, float:1.82E-43)
            r2 = 66
            r3 = 33
            r4 = 0
            r5 = 3
            r6 = 2
            r7 = 17
            r8 = 1
            if (r0 != 0) goto L_0x002b
            r0 = 262144(0x40000, float:3.67342E-40)
            if (r10 == r7) goto L_0x0025
            if (r10 == r3) goto L_0x0023
            if (r10 == r2) goto L_0x001d
            if (r10 == r1) goto L_0x001b
            goto L_0x0046
        L_0x001b:
            r4 = r5
            goto L_0x0047
        L_0x001d:
            int r9 = r9.mFlag
            r9 = r9 & r0
            if (r9 != 0) goto L_0x0047
            goto L_0x0038
        L_0x0023:
            r4 = r6
            goto L_0x0047
        L_0x0025:
            int r9 = r9.mFlag
            r9 = r9 & r0
            if (r9 != 0) goto L_0x0038
            goto L_0x0047
        L_0x002b:
            if (r0 != r8) goto L_0x0046
            r0 = 524288(0x80000, float:7.34684E-40)
            if (r10 == r7) goto L_0x0040
            if (r10 == r3) goto L_0x0047
            if (r10 == r2) goto L_0x003a
            if (r10 == r1) goto L_0x0038
            goto L_0x0046
        L_0x0038:
            r4 = r8
            goto L_0x0047
        L_0x003a:
            int r9 = r9.mFlag
            r9 = r9 & r0
            if (r9 != 0) goto L_0x0023
            goto L_0x001b
        L_0x0040:
            int r9 = r9.mFlag
            r9 = r9 & r0
            if (r9 != 0) goto L_0x001b
            goto L_0x0023
        L_0x0046:
            r4 = r7
        L_0x0047:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.getMovement(int):int");
    }

    public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Grid grid;
        if (this.mOrientation != 0 || (grid = this.mGrid) == null) {
            return -1;
        }
        Objects.requireNonNull(grid);
        return grid.mNumRows;
    }

    public final int getRowSizeSecondary(int i) {
        int i2 = this.mFixedRowSizeSecondary;
        if (i2 != 0) {
            return i2;
        }
        int[] iArr = this.mRowSizeSecondary;
        if (iArr == null) {
            return 0;
        }
        return iArr[i];
    }

    public final int getRowStartSecondary(int i) {
        int i2 = 0;
        if ((this.mFlag & 524288) != 0) {
            for (int i3 = this.mNumRows - 1; i3 > i; i3--) {
                i2 += getRowSizeSecondary(i3) + this.mSpacingSecondary;
            }
            return i2;
        }
        int i4 = 0;
        while (i2 < i) {
            i4 += getRowSizeSecondary(i2) + this.mSpacingSecondary;
            i2++;
        }
        return i4;
    }

    public final boolean getScrollPosition(View view, View view2, int[] iArr) {
        int i;
        int i2;
        int i3;
        int i4;
        int subPositionByView;
        WindowAlignment windowAlignment = this.mWindowAlignment;
        Objects.requireNonNull(windowAlignment);
        WindowAlignment.Axis axis = windowAlignment.mMainAxis;
        if (this.mOrientation == 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            i = view.getLeft() + layoutParams.mLeftInset;
            i2 = layoutParams.mAlignX;
        } else {
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams2);
            i = view.getTop() + layoutParams2.mTopInset;
            i2 = layoutParams2.mAlignY;
        }
        int scroll = axis.getScroll(i + i2);
        if (!(view2 == null || (subPositionByView = getSubPositionByView(view, view2)) == 0)) {
            LayoutParams layoutParams3 = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams3);
            int[] iArr2 = layoutParams3.mAlignMultiple;
            scroll += iArr2[subPositionByView] - iArr2[0];
        }
        if (this.mOrientation == 0) {
            LayoutParams layoutParams4 = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams4);
            i4 = view.getTop() + layoutParams4.mTopInset;
            i3 = layoutParams4.mAlignY;
        } else {
            LayoutParams layoutParams5 = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams5);
            i4 = view.getLeft() + layoutParams5.mLeftInset;
            i3 = layoutParams5.mAlignX;
        }
        int i5 = i4 + i3;
        WindowAlignment windowAlignment2 = this.mWindowAlignment;
        Objects.requireNonNull(windowAlignment2);
        int scroll2 = windowAlignment2.mSecondAxis.getScroll(i5);
        int i6 = scroll + this.mPrimaryScrollExtra;
        if (i6 == 0 && scroll2 == 0) {
            iArr[0] = 0;
            iArr[1] = 0;
            return false;
        }
        iArr[0] = i6;
        iArr[1] = scroll2;
        return true;
    }

    public final int getSizeSecondary() {
        int i;
        if ((this.mFlag & 524288) != 0) {
            i = 0;
        } else {
            i = this.mNumRows - 1;
        }
        return getRowSizeSecondary(i) + getRowStartSecondary(i);
    }

    public final View getViewForPosition(int i) {
        Object obj;
        FacetProviderAdapter facetProviderAdapter;
        View viewForPosition = this.mRecycler.getViewForPosition(i);
        LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
        RecyclerView.ViewHolder childViewHolder = this.mBaseGridView.getChildViewHolder(viewForPosition);
        if (childViewHolder instanceof FacetProvider) {
            obj = ((FacetProvider) childViewHolder).getFacet();
        } else {
            obj = null;
        }
        if (obj == null && (facetProviderAdapter = this.mFacetProviderAdapter) != null) {
            Objects.requireNonNull(childViewHolder);
            FacetProvider facetProvider = facetProviderAdapter.getFacetProvider();
            if (facetProvider != null) {
                obj = facetProvider.getFacet();
            }
        }
        Objects.requireNonNull(layoutParams);
        layoutParams.mAlignmentFacet = (ItemAlignmentFacet) obj;
        return viewForPosition;
    }

    public final boolean isItemFullyVisible(int i) {
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mBaseGridView.findViewHolderForAdapterPosition(i);
        if (findViewHolderForAdapterPosition != null && findViewHolderForAdapterPosition.itemView.getLeft() >= 0 && findViewHolderForAdapterPosition.itemView.getRight() <= this.mBaseGridView.getWidth() && findViewHolderForAdapterPosition.itemView.getTop() >= 0 && findViewHolderForAdapterPosition.itemView.getBottom() <= this.mBaseGridView.getHeight()) {
            return true;
        }
        return false;
    }

    public final void layoutChild(int i, View view, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        if (this.mOrientation == 0) {
            i5 = getDecoratedMeasuredHeightWithMargin(view);
        } else {
            i5 = getDecoratedMeasuredWidthWithMargin(view);
        }
        int i8 = this.mFixedRowSizeSecondary;
        if (i8 > 0) {
            i5 = Math.min(i5, i8);
        }
        int i9 = this.mGravity;
        int i10 = i9 & 112;
        if ((this.mFlag & 786432) != 0) {
            i6 = Gravity.getAbsoluteGravity(i9 & 8388615, 1);
        } else {
            i6 = i9 & 7;
        }
        int i11 = this.mOrientation;
        if (!((i11 == 0 && i10 == 48) || (i11 == 1 && i6 == 3))) {
            if ((i11 == 0 && i10 == 80) || (i11 == 1 && i6 == 5)) {
                i7 = getRowSizeSecondary(i) - i5;
            } else if ((i11 == 0 && i10 == 16) || (i11 == 1 && i6 == 1)) {
                i7 = (getRowSizeSecondary(i) - i5) / 2;
            }
            i4 += i7;
        }
        int i12 = i5 + i4;
        if (this.mOrientation != 0) {
            int i13 = i4;
            i4 = i2;
            i2 = i13;
            int i14 = i12;
            i12 = i3;
            i3 = i14;
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        RecyclerView.LayoutManager.layoutDecoratedWithMargins(view, i2, i4, i3, i12);
        Rect rect = sTempRect;
        super.getDecoratedBoundsWithMargins(view, rect);
        int i15 = i2 - rect.left;
        int i16 = i4 - rect.top;
        int i17 = rect.right - i3;
        Objects.requireNonNull(layoutParams);
        layoutParams.mLeftInset = i15;
        layoutParams.mTopInset = i16;
        layoutParams.mRightInset = i17;
        layoutParams.mBottomInset = rect.bottom - i12;
        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
        Objects.requireNonNull(layoutParams2);
        ItemAlignmentFacet itemAlignmentFacet = layoutParams2.mAlignmentFacet;
        if (itemAlignmentFacet == null) {
            ItemAlignment.Axis axis = this.mItemAlignment.horizontal;
            Objects.requireNonNull(axis);
            layoutParams2.mAlignX = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis, axis.mOrientation);
            ItemAlignment.Axis axis2 = this.mItemAlignment.vertical;
            Objects.requireNonNull(axis2);
            layoutParams2.mAlignY = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis2, axis2.mOrientation);
            return;
        }
        int i18 = this.mOrientation;
        ItemAlignmentFacet.ItemAlignmentDef[] itemAlignmentDefArr = itemAlignmentFacet.mAlignmentDefs;
        int[] iArr = layoutParams2.mAlignMultiple;
        if (iArr == null || iArr.length != itemAlignmentDefArr.length) {
            layoutParams2.mAlignMultiple = new int[itemAlignmentDefArr.length];
        }
        for (int i19 = 0; i19 < itemAlignmentDefArr.length; i19++) {
            layoutParams2.mAlignMultiple[i19] = ItemAlignmentFacetHelper.getAlignmentPosition(view, itemAlignmentDefArr[i19], i18);
        }
        if (i18 == 0) {
            layoutParams2.mAlignX = layoutParams2.mAlignMultiple[0];
        } else {
            layoutParams2.mAlignY = layoutParams2.mAlignMultiple[0];
        }
        if (this.mOrientation == 0) {
            ItemAlignment.Axis axis3 = this.mItemAlignment.vertical;
            Objects.requireNonNull(axis3);
            layoutParams2.mAlignY = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis3, axis3.mOrientation);
            return;
        }
        ItemAlignment.Axis axis4 = this.mItemAlignment.horizontal;
        Objects.requireNonNull(axis4);
        layoutParams2.mAlignX = ItemAlignmentFacetHelper.getAlignmentPosition(view, axis4, axis4.mOrientation);
    }

    public final void leaveContext() {
        int i = this.mSaveContextLevel - 1;
        this.mSaveContextLevel = i;
        if (i == 0) {
            this.mRecycler = null;
            this.mState = null;
            this.mPositionDeltaInPreLayout = 0;
            this.mExtraLayoutSpaceInPreLayout = 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0128 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onAddFocusables(androidx.recyclerview.widget.RecyclerView r18, java.util.ArrayList<android.view.View> r19, int r20, int r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            r2 = r20
            r3 = r21
            int r4 = r0.mFlag
            r5 = 32768(0x8000, float:4.5918E-41)
            r4 = r4 & r5
            r5 = 1
            if (r4 == 0) goto L_0x0012
            return r5
        L_0x0012:
            boolean r4 = r18.hasFocus()
            if (r4 == 0) goto L_0x0129
            androidx.leanback.widget.GridLayoutManager$PendingMoveSmoothScroller r4 = r0.mPendingMoveSmoothScroller
            if (r4 == 0) goto L_0x001d
            return r5
        L_0x001d:
            int r4 = r0.getMovement(r2)
            android.view.View r6 = r18.findFocus()
            r7 = -1
            if (r6 == 0) goto L_0x0045
            androidx.leanback.widget.BaseGridView r9 = r0.mBaseGridView
            if (r9 == 0) goto L_0x0045
            if (r6 == r9) goto L_0x0045
            android.view.View r6 = r0.findContainingItemView(r6)
            if (r6 == 0) goto L_0x0045
            int r9 = r17.getChildCount()
            r10 = 0
        L_0x0039:
            if (r10 >= r9) goto L_0x0045
            android.view.View r11 = r0.getChildAt(r10)
            if (r11 != r6) goto L_0x0042
            goto L_0x0046
        L_0x0042:
            int r10 = r10 + 1
            goto L_0x0039
        L_0x0045:
            r10 = r7
        L_0x0046:
            android.view.View r6 = r0.getChildAt(r10)
            int r6 = getAdapterPositionByView(r6)
            if (r6 != r7) goto L_0x0052
            r9 = 0
            goto L_0x0056
        L_0x0052:
            android.view.View r9 = r0.findViewByPosition(r6)
        L_0x0056:
            if (r9 == 0) goto L_0x005b
            r9.addFocusables(r1, r2, r3)
        L_0x005b:
            androidx.leanback.widget.Grid r11 = r0.mGrid
            if (r11 == 0) goto L_0x0128
            int r11 = r17.getChildCount()
            if (r11 != 0) goto L_0x0067
            goto L_0x0128
        L_0x0067:
            r11 = 3
            r12 = 2
            if (r4 == r11) goto L_0x006d
            if (r4 != r12) goto L_0x0077
        L_0x006d:
            androidx.leanback.widget.Grid r13 = r0.mGrid
            java.util.Objects.requireNonNull(r13)
            int r13 = r13.mNumRows
            if (r13 > r5) goto L_0x0077
            return r5
        L_0x0077:
            androidx.leanback.widget.Grid r13 = r0.mGrid
            if (r13 == 0) goto L_0x0084
            if (r9 == 0) goto L_0x0084
            androidx.leanback.widget.Grid$Location r13 = r13.getLocation(r6)
            int r13 = r13.row
            goto L_0x0085
        L_0x0084:
            r13 = r7
        L_0x0085:
            int r14 = r19.size()
            if (r4 == r5) goto L_0x0090
            if (r4 != r11) goto L_0x008e
            goto L_0x0090
        L_0x008e:
            r15 = r7
            goto L_0x0091
        L_0x0090:
            r15 = r5
        L_0x0091:
            if (r15 <= 0) goto L_0x009c
            int r16 = r17.getChildCount()
            int r16 = r16 + -1
            r8 = r16
            goto L_0x009d
        L_0x009c:
            r8 = 0
        L_0x009d:
            if (r10 != r7) goto L_0x00a9
            if (r15 <= 0) goto L_0x00a3
            r7 = 0
            goto L_0x00ab
        L_0x00a3:
            int r7 = r17.getChildCount()
            int r7 = r7 - r5
            goto L_0x00ab
        L_0x00a9:
            int r7 = r10 + r15
        L_0x00ab:
            if (r15 <= 0) goto L_0x00b0
            if (r7 > r8) goto L_0x014a
            goto L_0x00b2
        L_0x00b0:
            if (r7 < r8) goto L_0x014a
        L_0x00b2:
            android.view.View r10 = r0.getChildAt(r7)
            int r16 = r10.getVisibility()
            if (r16 != 0) goto L_0x0125
            boolean r16 = r10.hasFocusable()
            if (r16 != 0) goto L_0x00c4
            goto L_0x0125
        L_0x00c4:
            if (r9 != 0) goto L_0x00d1
            r10.addFocusables(r1, r2, r3)
            int r10 = r19.size()
            if (r10 <= r14) goto L_0x0125
            goto L_0x014a
        L_0x00d1:
            android.view.View r16 = r0.getChildAt(r7)
            int r12 = getAdapterPositionByView(r16)
            androidx.leanback.widget.Grid r11 = r0.mGrid
            androidx.leanback.widget.Grid$Location r11 = r11.getLocation(r12)
            if (r11 != 0) goto L_0x00e3
        L_0x00e1:
            r12 = 3
            goto L_0x0115
        L_0x00e3:
            if (r4 != r5) goto L_0x00f5
            int r11 = r11.row
            if (r11 != r13) goto L_0x00e1
            if (r12 <= r6) goto L_0x00e1
            r10.addFocusables(r1, r2, r3)
            int r10 = r19.size()
            if (r10 <= r14) goto L_0x00e1
            goto L_0x014a
        L_0x00f5:
            if (r4 != 0) goto L_0x0107
            int r11 = r11.row
            if (r11 != r13) goto L_0x00e1
            if (r12 >= r6) goto L_0x00e1
            r10.addFocusables(r1, r2, r3)
            int r10 = r19.size()
            if (r10 <= r14) goto L_0x00e1
            goto L_0x014a
        L_0x0107:
            r12 = 3
            if (r4 != r12) goto L_0x0117
            int r11 = r11.row
            if (r11 != r13) goto L_0x010f
            goto L_0x0115
        L_0x010f:
            if (r11 >= r13) goto L_0x0112
            goto L_0x014a
        L_0x0112:
            r10.addFocusables(r1, r2, r3)
        L_0x0115:
            r12 = 2
            goto L_0x0125
        L_0x0117:
            r12 = 2
            if (r4 != r12) goto L_0x0125
            int r11 = r11.row
            if (r11 != r13) goto L_0x011f
            goto L_0x0125
        L_0x011f:
            if (r11 <= r13) goto L_0x0122
            goto L_0x014a
        L_0x0122:
            r10.addFocusables(r1, r2, r3)
        L_0x0125:
            int r7 = r7 + r15
            r11 = 3
            goto L_0x00ab
        L_0x0128:
            return r5
        L_0x0129:
            int r4 = r19.size()
            int r6 = r0.mFocusPosition
            android.view.View r0 = r0.findViewByPosition(r6)
            if (r0 == 0) goto L_0x0138
            r0.addFocusables(r1, r2, r3)
        L_0x0138:
            int r0 = r19.size()
            if (r0 == r4) goto L_0x013f
            return r5
        L_0x013f:
            boolean r0 = r18.isFocusable()
            if (r0 == 0) goto L_0x014a
            r0 = r18
            r1.add(r0)
        L_0x014a:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onAddFocusables(androidx.recyclerview.widget.RecyclerView, java.util.ArrayList, int, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x00cb A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View onInterceptFocusSearch(android.view.View r8, int r9) {
        /*
            r7 = this;
            int r0 = r7.mFlag
            r1 = 32768(0x8000, float:4.5918E-41)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0009
            return r8
        L_0x0009:
            android.view.FocusFinder r0 = android.view.FocusFinder.getInstance()
            r1 = 0
            r2 = 0
            r3 = 2
            r4 = 1
            if (r9 == r3) goto L_0x001d
            if (r9 != r4) goto L_0x0016
            goto L_0x001d
        L_0x0016:
            androidx.leanback.widget.BaseGridView r1 = r7.mBaseGridView
            android.view.View r0 = r0.findNextFocus(r1, r8, r9)
            goto L_0x0054
        L_0x001d:
            boolean r5 = r7.canScrollVertically()
            if (r5 == 0) goto L_0x0030
            if (r9 != r3) goto L_0x0028
            r1 = 130(0x82, float:1.82E-43)
            goto L_0x002a
        L_0x0028:
            r1 = 33
        L_0x002a:
            androidx.leanback.widget.BaseGridView r5 = r7.mBaseGridView
            android.view.View r1 = r0.findNextFocus(r5, r8, r1)
        L_0x0030:
            boolean r5 = r7.canScrollHorizontally()
            if (r5 == 0) goto L_0x0053
            int r1 = r7.getLayoutDirection()
            if (r1 != r4) goto L_0x003e
            r1 = r4
            goto L_0x003f
        L_0x003e:
            r1 = r2
        L_0x003f:
            if (r9 != r3) goto L_0x0043
            r5 = r4
            goto L_0x0044
        L_0x0043:
            r5 = r2
        L_0x0044:
            r1 = r1 ^ r5
            if (r1 == 0) goto L_0x004a
            r1 = 66
            goto L_0x004c
        L_0x004a:
            r1 = 17
        L_0x004c:
            androidx.leanback.widget.BaseGridView r5 = r7.mBaseGridView
            android.view.View r0 = r0.findNextFocus(r5, r8, r1)
            goto L_0x0054
        L_0x0053:
            r0 = r1
        L_0x0054:
            if (r0 == 0) goto L_0x0057
            return r0
        L_0x0057:
            androidx.leanback.widget.BaseGridView r1 = r7.mBaseGridView
            int r1 = r1.getDescendantFocusability()
            r5 = 393216(0x60000, float:5.51013E-40)
            if (r1 != r5) goto L_0x006c
            androidx.leanback.widget.BaseGridView r7 = r7.mBaseGridView
            android.view.ViewParent r7 = r7.getParent()
            android.view.View r7 = r7.focusSearch(r8, r9)
            return r7
        L_0x006c:
            int r1 = r7.getMovement(r9)
            androidx.leanback.widget.BaseGridView r5 = r7.mBaseGridView
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mScrollState
            if (r5 == 0) goto L_0x007b
            r5 = r4
            goto L_0x007c
        L_0x007b:
            r5 = r2
        L_0x007c:
            r6 = 131072(0x20000, float:1.83671E-40)
            if (r1 != r4) goto L_0x0098
            if (r5 != 0) goto L_0x0088
            int r1 = r7.mFlag
            r1 = r1 & 4096(0x1000, float:5.74E-42)
            if (r1 != 0) goto L_0x0089
        L_0x0088:
            r0 = r8
        L_0x0089:
            int r1 = r7.mFlag
            r1 = r1 & r6
            if (r1 == 0) goto L_0x00c9
            boolean r1 = r7.hasCreatedLastItem()
            if (r1 != 0) goto L_0x00c9
            r7.processPendingMovement(r4)
            goto L_0x00c8
        L_0x0098:
            if (r1 != 0) goto L_0x00b2
            if (r5 != 0) goto L_0x00a2
            int r1 = r7.mFlag
            r1 = r1 & 2048(0x800, float:2.87E-42)
            if (r1 != 0) goto L_0x00a3
        L_0x00a2:
            r0 = r8
        L_0x00a3:
            int r1 = r7.mFlag
            r1 = r1 & r6
            if (r1 == 0) goto L_0x00c9
            boolean r1 = r7.hasCreatedFirstItem()
            if (r1 != 0) goto L_0x00c9
            r7.processPendingMovement(r2)
            goto L_0x00c8
        L_0x00b2:
            r2 = 3
            if (r1 != r2) goto L_0x00be
            if (r5 != 0) goto L_0x00c8
            int r1 = r7.mFlag
            r1 = r1 & 16384(0x4000, float:2.2959E-41)
            if (r1 != 0) goto L_0x00c9
            goto L_0x00c8
        L_0x00be:
            if (r1 != r3) goto L_0x00c9
            if (r5 != 0) goto L_0x00c8
            int r1 = r7.mFlag
            r1 = r1 & 8192(0x2000, float:1.14794E-41)
            if (r1 != 0) goto L_0x00c9
        L_0x00c8:
            r0 = r8
        L_0x00c9:
            if (r0 == 0) goto L_0x00cc
            return r0
        L_0x00cc:
            androidx.leanback.widget.BaseGridView r0 = r7.mBaseGridView
            android.view.ViewParent r0 = r0.getParent()
            android.view.View r9 = r0.focusSearch(r8, r9)
            if (r9 == 0) goto L_0x00d9
            return r9
        L_0x00d9:
            if (r8 == 0) goto L_0x00dc
            goto L_0x00de
        L_0x00dc:
            androidx.leanback.widget.BaseGridView r8 = r7.mBaseGridView
        L_0x00de:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onInterceptFocusSearch(android.view.View, int):android.view.View");
    }

    public final void onItemsAdded(int i, int i2) {
        Grid grid;
        int i3;
        int i4 = this.mFocusPosition;
        if (!(i4 == -1 || (grid = this.mGrid) == null || grid.mFirstVisibleIndex < 0 || (i3 = this.mFocusPositionOffset) == Integer.MIN_VALUE || i > i4 + i3)) {
            this.mFocusPositionOffset = i3 + i2;
        }
        Objects.requireNonNull(this.mChildrenStates);
    }

    public final void onItemsMoved(int i, int i2) {
        int i3;
        int i4 = this.mFocusPosition;
        if (!(i4 == -1 || (i3 = this.mFocusPositionOffset) == Integer.MIN_VALUE)) {
            int i5 = i4 + i3;
            if (i <= i5 && i5 < i + 1) {
                this.mFocusPositionOffset = (i2 - i) + i3;
            } else if (i < i5 && i2 > i5 - 1) {
                this.mFocusPositionOffset = i3 - 1;
            } else if (i > i5 && i2 < i5) {
                this.mFocusPositionOffset = i3 + 1;
            }
        }
        Objects.requireNonNull(this.mChildrenStates);
    }

    public final void onItemsRemoved(int i, int i2) {
        Grid grid;
        int i3;
        int i4;
        int i5 = this.mFocusPosition;
        if (!(i5 == -1 || (grid = this.mGrid) == null || grid.mFirstVisibleIndex < 0 || (i3 = this.mFocusPositionOffset) == Integer.MIN_VALUE || i > (i4 = i5 + i3))) {
            if (i + i2 > i4) {
                this.mFocusPosition = (i - i4) + i3 + i5;
                this.mFocusPositionOffset = Integer.MIN_VALUE;
            } else {
                this.mFocusPositionOffset = i3 - i2;
            }
        }
        Objects.requireNonNull(this.mChildrenStates);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0216, code lost:
        if (r2 != r11.mReversedFlow) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0311, code lost:
        r11 = r0;
        r17 = r2;
        r13 = r4;
        r22 = r5;
        r20 = r14;
        r21 = r15;
        r14 = r1;
        r15 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x03b4, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:309:0x06dd, code lost:
        if (r1 < 0) goto L_0x070e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:320:0x070c, code lost:
        if (r1 < 0) goto L_0x070e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onLayoutChildren(androidx.recyclerview.widget.RecyclerView.Recycler r24, androidx.recyclerview.widget.RecyclerView.State r25) {
        /*
            r23 = this;
            r6 = r23
            r7 = r25
            int r0 = r6.mNumRows
            if (r0 != 0) goto L_0x0009
            return
        L_0x0009:
            int r0 = r25.getItemCount()
            if (r0 >= 0) goto L_0x0010
            return
        L_0x0010:
            int r0 = r6.mFlag
            r0 = r0 & 64
            if (r0 == 0) goto L_0x0023
            int r0 = r23.getChildCount()
            if (r0 <= 0) goto L_0x0023
            int r0 = r6.mFlag
            r0 = r0 | 128(0x80, float:1.794E-43)
            r6.mFlag = r0
            return
        L_0x0023:
            int r0 = r6.mFlag
            r1 = r0 & 512(0x200, float:7.175E-43)
            if (r1 != 0) goto L_0x0036
            r1 = 0
            r6.mGrid = r1
            r6.mRowSizeSecondary = r1
            r0 = r0 & -1025(0xfffffffffffffbff, float:NaN)
            r6.mFlag = r0
            r23.removeAndRecycleAllViews(r24)
            return
        L_0x0036:
            r0 = r0 & -4
            r8 = 1
            r0 = r0 | r8
            r6.mFlag = r0
            r23.saveContext(r24, r25)
            boolean r0 = r7.mInPreLayout
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = 0
            if (r0 == 0) goto L_0x00ef
            r23.updatePositionDeltaInPreLayout()
            int r0 = r23.getChildCount()
            androidx.leanback.widget.Grid r1 = r6.mGrid
            if (r1 == 0) goto L_0x00e5
            if (r0 <= 0) goto L_0x00e5
            androidx.leanback.widget.BaseGridView r1 = r6.mBaseGridView
            android.view.View r2 = r6.getChildAt(r11)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r1 = r1.getChildViewHolder(r2)
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mOldPosition
            androidx.leanback.widget.BaseGridView r2 = r6.mBaseGridView
            int r3 = r0 + -1
            android.view.View r3 = r6.getChildAt(r3)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r2 = r2.getChildViewHolder(r3)
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mOldPosition
            r9 = 2147483647(0x7fffffff, float:NaN)
        L_0x0076:
            if (r11 >= r0) goto L_0x00da
            android.view.View r3 = r6.getChildAt(r11)
            android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
            androidx.leanback.widget.GridLayoutManager$LayoutParams r4 = (androidx.leanback.widget.GridLayoutManager.LayoutParams) r4
            androidx.leanback.widget.BaseGridView r5 = r6.mBaseGridView
            java.util.Objects.requireNonNull(r5)
            int r5 = androidx.recyclerview.widget.RecyclerView.getChildAdapterPosition(r3)
            boolean r7 = r4.isItemChanged()
            if (r7 != 0) goto L_0x00c1
            boolean r7 = r4.isItemRemoved()
            if (r7 != 0) goto L_0x00c1
            boolean r7 = r3.isLayoutRequested()
            if (r7 != 0) goto L_0x00c1
            boolean r7 = r3.hasFocus()
            if (r7 != 0) goto L_0x00ad
            int r7 = r6.mFocusPosition
            androidx.recyclerview.widget.RecyclerView$ViewHolder r8 = r4.mViewHolder
            int r8 = r8.getAbsoluteAdapterPosition()
            if (r7 == r8) goto L_0x00c1
        L_0x00ad:
            boolean r7 = r3.hasFocus()
            if (r7 == 0) goto L_0x00bd
            int r7 = r6.mFocusPosition
            androidx.recyclerview.widget.RecyclerView$ViewHolder r4 = r4.mViewHolder
            int r4 = r4.getAbsoluteAdapterPosition()
            if (r7 != r4) goto L_0x00c1
        L_0x00bd:
            if (r5 < r1) goto L_0x00c1
            if (r5 <= r2) goto L_0x00d7
        L_0x00c1:
            androidx.recyclerview.widget.OrientationHelper r4 = r6.mOrientationHelper
            int r4 = r4.getDecoratedStart(r3)
            int r4 = java.lang.Math.min(r9, r4)
            androidx.recyclerview.widget.OrientationHelper r5 = r6.mOrientationHelper
            int r3 = r5.getDecoratedEnd(r3)
            int r3 = java.lang.Math.max(r10, r3)
            r10 = r3
            r9 = r4
        L_0x00d7:
            int r11 = r11 + 1
            goto L_0x0076
        L_0x00da:
            if (r10 <= r9) goto L_0x00df
            int r10 = r10 - r9
            r6.mExtraLayoutSpaceInPreLayout = r10
        L_0x00df:
            r23.appendVisibleItems()
            r23.prependVisibleItems()
        L_0x00e5:
            int r0 = r6.mFlag
            r0 = r0 & -4
            r6.mFlag = r0
            r23.leaveContext()
            return
        L_0x00ef:
            boolean r0 = r7.mRunPredictiveAnimations
            if (r0 == 0) goto L_0x0122
            android.util.SparseIntArray r0 = r6.mPositionToRowInPostLayout
            r0.clear()
            int r0 = r23.getChildCount()
            r1 = r11
        L_0x00fd:
            if (r1 >= r0) goto L_0x0122
            androidx.leanback.widget.BaseGridView r2 = r6.mBaseGridView
            android.view.View r3 = r6.getChildAt(r1)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r2 = r2.getChildViewHolder(r3)
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mOldPosition
            if (r2 < 0) goto L_0x011f
            androidx.leanback.widget.Grid r3 = r6.mGrid
            androidx.leanback.widget.Grid$Location r3 = r3.getLocation(r2)
            if (r3 == 0) goto L_0x011f
            android.util.SparseIntArray r4 = r6.mPositionToRowInPostLayout
            int r3 = r3.row
            r4.put(r2, r3)
        L_0x011f:
            int r1 = r1 + 1
            goto L_0x00fd
        L_0x0122:
            boolean r0 = r23.isSmoothScrolling()
            r12 = r0 ^ 1
            int r0 = r6.mFocusPosition
            r13 = -1
            if (r0 == r13) goto L_0x0136
            int r1 = r6.mFocusPositionOffset
            if (r1 == r10) goto L_0x0136
            int r0 = r0 + r1
            r6.mFocusPosition = r0
            r6.mSubFocusPosition = r11
        L_0x0136:
            r6.mFocusPositionOffset = r11
            int r0 = r6.mFocusPosition
            android.view.View r14 = r6.findViewByPosition(r0)
            int r15 = r6.mFocusPosition
            int r5 = r6.mSubFocusPosition
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            boolean r16 = r0.hasFocus()
            androidx.leanback.widget.Grid r0 = r6.mGrid
            if (r0 == 0) goto L_0x014f
            int r1 = r0.mFirstVisibleIndex
            goto L_0x0150
        L_0x014f:
            r1 = r13
        L_0x0150:
            if (r0 == 0) goto L_0x0155
            int r0 = r0.mLastVisibleIndex
            goto L_0x0156
        L_0x0155:
            r0 = r13
        L_0x0156:
            int r2 = r6.mOrientation
            if (r2 != 0) goto L_0x015f
            int r2 = r7.mRemainingScrollHorizontal
            int r3 = r7.mRemainingScrollVertical
            goto L_0x0163
        L_0x015f:
            int r3 = r7.mRemainingScrollHorizontal
            int r2 = r7.mRemainingScrollVertical
        L_0x0163:
            r4 = r2
            androidx.recyclerview.widget.RecyclerView$State r2 = r6.mState
            int r2 = r2.getItemCount()
            if (r2 != 0) goto L_0x0171
            r6.mFocusPosition = r13
            r6.mSubFocusPosition = r11
            goto L_0x0183
        L_0x0171:
            int r9 = r6.mFocusPosition
            if (r9 < r2) goto L_0x017b
            int r2 = r2 - r8
            r6.mFocusPosition = r2
            r6.mSubFocusPosition = r11
            goto L_0x0183
        L_0x017b:
            if (r9 != r13) goto L_0x0183
            if (r2 <= 0) goto L_0x0183
            r6.mFocusPosition = r11
            r6.mSubFocusPosition = r11
        L_0x0183:
            androidx.recyclerview.widget.RecyclerView$State r2 = r6.mState
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mStructureChanged
            r9 = 262144(0x40000, float:3.67342E-40)
            if (r2 != 0) goto L_0x01fe
            androidx.leanback.widget.Grid r2 = r6.mGrid
            if (r2 == 0) goto L_0x01fe
            int r13 = r2.mFirstVisibleIndex
            if (r13 < 0) goto L_0x01fe
            int r13 = r6.mFlag
            r13 = r13 & 256(0x100, float:3.59E-43)
            if (r13 != 0) goto L_0x01fe
            int r2 = r2.mNumRows
            int r13 = r6.mNumRows
            if (r2 != r13) goto L_0x01fe
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.horizontal
            int r13 = r6.mWidth
            java.util.Objects.requireNonNull(r2)
            r2.mSize = r13
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.vertical
            int r13 = r6.mHeight
            java.util.Objects.requireNonNull(r2)
            r2.mSize = r13
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.horizontal
            int r13 = r23.getPaddingLeft()
            int r11 = r23.getPaddingRight()
            java.util.Objects.requireNonNull(r2)
            r2.mPaddingMin = r13
            r2.mPaddingMax = r11
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.vertical
            int r11 = r23.getPaddingTop()
            int r13 = r23.getPaddingBottom()
            java.util.Objects.requireNonNull(r2)
            r2.mPaddingMin = r11
            r2.mPaddingMax = r13
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r2)
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.mMainAxis
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mSize
            r6.mSizePrimary = r2
            r23.updateSecondaryScrollLimits()
            androidx.leanback.widget.Grid r2 = r6.mGrid
            int r11 = r6.mSpacingPrimary
            java.util.Objects.requireNonNull(r2)
            r2.mSpacing = r11
            r2 = r8
            r11 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x02d8
        L_0x01fe:
            int r2 = r6.mFlag
            r2 = r2 & -257(0xfffffffffffffeff, float:NaN)
            r6.mFlag = r2
            androidx.leanback.widget.Grid r11 = r6.mGrid
            if (r11 == 0) goto L_0x0218
            int r13 = r6.mNumRows
            int r10 = r11.mNumRows
            if (r13 != r10) goto L_0x0218
            r2 = r2 & r9
            if (r2 == 0) goto L_0x0213
            r2 = r8
            goto L_0x0214
        L_0x0213:
            r2 = 0
        L_0x0214:
            boolean r10 = r11.mReversedFlow
            if (r2 == r10) goto L_0x023b
        L_0x0218:
            int r2 = r6.mNumRows
            if (r2 != r8) goto L_0x0222
            androidx.leanback.widget.SingleRow r2 = new androidx.leanback.widget.SingleRow
            r2.<init>()
            goto L_0x022b
        L_0x0222:
            androidx.leanback.widget.StaggeredGridDefault r10 = new androidx.leanback.widget.StaggeredGridDefault
            r10.<init>()
            r10.setNumRows(r2)
            r2 = r10
        L_0x022b:
            r6.mGrid = r2
            androidx.leanback.widget.GridLayoutManager$2 r10 = r6.mGridProvider
            r2.mProvider = r10
            int r10 = r6.mFlag
            r10 = r10 & r9
            if (r10 == 0) goto L_0x0238
            r10 = r8
            goto L_0x0239
        L_0x0238:
            r10 = 0
        L_0x0239:
            r2.mReversedFlow = r10
        L_0x023b:
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r2)
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.mMainAxis
            java.util.Objects.requireNonNull(r2)
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r2.mMinEdge = r10
            r10 = 2147483647(0x7fffffff, float:NaN)
            r2.mMaxEdge = r10
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.horizontal
            int r10 = r6.mWidth
            java.util.Objects.requireNonNull(r2)
            r2.mSize = r10
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.vertical
            int r10 = r6.mHeight
            java.util.Objects.requireNonNull(r2)
            r2.mSize = r10
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.horizontal
            int r10 = r23.getPaddingLeft()
            int r11 = r23.getPaddingRight()
            java.util.Objects.requireNonNull(r2)
            r2.mPaddingMin = r10
            r2.mPaddingMax = r11
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.vertical
            int r10 = r23.getPaddingTop()
            int r11 = r23.getPaddingBottom()
            java.util.Objects.requireNonNull(r2)
            r2.mPaddingMin = r10
            r2.mPaddingMax = r11
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r2)
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.mMainAxis
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mSize
            r6.mSizePrimary = r2
            r2 = 0
            r6.mScrollOffsetSecondary = r2
            r23.updateSecondaryScrollLimits()
            androidx.leanback.widget.Grid r2 = r6.mGrid
            int r10 = r6.mSpacingPrimary
            java.util.Objects.requireNonNull(r2)
            r2.mSpacing = r10
            androidx.recyclerview.widget.RecyclerView$Recycler r2 = r6.mRecycler
            r6.detachAndScrapAttachedViews(r2)
            androidx.leanback.widget.Grid r2 = r6.mGrid
            java.util.Objects.requireNonNull(r2)
            r10 = -1
            r2.mLastVisibleIndex = r10
            r2.mFirstVisibleIndex = r10
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r2)
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.mMainAxis
            java.util.Objects.requireNonNull(r2)
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r2.mMinEdge = r10
            r2.mMinScroll = r10
            androidx.leanback.widget.WindowAlignment r2 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r2)
            androidx.leanback.widget.WindowAlignment$Axis r2 = r2.mMainAxis
            java.util.Objects.requireNonNull(r2)
            r11 = 2147483647(0x7fffffff, float:NaN)
            r2.mMaxEdge = r11
            r2.mMaxScroll = r11
            r2 = 0
        L_0x02d8:
            if (r2 == 0) goto L_0x0458
            int r0 = r6.mFlag
            r0 = r0 | 4
            r6.mFlag = r0
            androidx.leanback.widget.Grid r0 = r6.mGrid
            int r1 = r6.mFocusPosition
            java.util.Objects.requireNonNull(r0)
            r0.mStartIndex = r1
            int r2 = r23.getChildCount()
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mFirstVisibleIndex
            int r1 = r6.mFlag
            r1 = r1 & -9
            r6.mFlag = r1
            r1 = r0
            r0 = 0
        L_0x02fc:
            if (r0 >= r2) goto L_0x03ce
            android.view.View r10 = r6.getChildAt(r0)
            int r11 = getAdapterPositionByView(r10)
            if (r1 == r11) goto L_0x0309
            goto L_0x0311
        L_0x0309:
            androidx.leanback.widget.Grid r11 = r6.mGrid
            androidx.leanback.widget.Grid$Location r11 = r11.getLocation(r1)
            if (r11 != 0) goto L_0x031f
        L_0x0311:
            r11 = r0
            r17 = r2
            r13 = r4
            r22 = r5
            r20 = r14
            r21 = r15
            r14 = r1
            r15 = r3
            goto L_0x03b4
        L_0x031f:
            int r9 = r11.row
            int r9 = r6.getRowStartSecondary(r9)
            androidx.leanback.widget.WindowAlignment r13 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r13)
            androidx.leanback.widget.WindowAlignment$Axis r13 = r13.mSecondAxis
            java.util.Objects.requireNonNull(r13)
            int r13 = r13.mPaddingMin
            int r9 = r9 + r13
            int r13 = r6.mScrollOffsetSecondary
            int r9 = r9 - r13
            androidx.recyclerview.widget.OrientationHelper r13 = r6.mOrientationHelper
            int r13 = r13.getDecoratedStart(r10)
            android.graphics.Rect r8 = sTempRect
            r6.getDecoratedBoundsWithMargins(r10, r8)
            r17 = r2
            int r2 = r6.mOrientation
            if (r2 != 0) goto L_0x034b
            int r2 = r8.width()
            goto L_0x034f
        L_0x034b:
            int r2 = r8.height()
        L_0x034f:
            r8 = r2
            android.view.ViewGroup$LayoutParams r2 = r10.getLayoutParams()
            androidx.leanback.widget.GridLayoutManager$LayoutParams r2 = (androidx.leanback.widget.GridLayoutManager.LayoutParams) r2
            java.util.Objects.requireNonNull(r2)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r2 = r2.mViewHolder
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mFlags
            r2 = r2 & 2
            if (r2 == 0) goto L_0x0366
            r2 = 1
            goto L_0x0367
        L_0x0366:
            r2 = 0
        L_0x0367:
            if (r2 == 0) goto L_0x0385
            int r2 = r6.mFlag
            r2 = r2 | 8
            r6.mFlag = r2
            androidx.recyclerview.widget.RecyclerView$Recycler r2 = r6.mRecycler
            r18 = r3
            androidx.recyclerview.widget.ChildHelper r3 = r6.mChildHelper
            int r3 = r3.indexOfChild(r10)
            r6.scrapOrRecycleView(r2, r3, r10)
            android.view.View r2 = r6.getViewForPosition(r1)
            r3 = 0
            r6.addViewInt(r2, r0, r3)
            goto L_0x0388
        L_0x0385:
            r18 = r3
            r2 = r10
        L_0x0388:
            r6.measureChild(r2)
            int r3 = r6.mOrientation
            if (r3 != 0) goto L_0x0394
            int r3 = getDecoratedMeasuredWidthWithMargin(r2)
            goto L_0x0398
        L_0x0394:
            int r3 = getDecoratedMeasuredHeightWithMargin(r2)
        L_0x0398:
            r10 = r3
            int r19 = r13 + r10
            int r3 = r11.row
            r11 = r0
            r0 = r23
            r20 = r14
            r14 = r1
            r1 = r3
            r21 = r15
            r15 = r18
            r3 = r13
            r13 = r4
            r4 = r19
            r22 = r5
            r5 = r9
            r0.layoutChild(r1, r2, r3, r4, r5)
            if (r8 == r10) goto L_0x03b6
        L_0x03b4:
            r0 = 1
            goto L_0x03db
        L_0x03b6:
            int r0 = r11 + 1
            int r1 = r14 + 1
            r4 = r13
            r3 = r15
            r2 = r17
            r14 = r20
            r15 = r21
            r5 = r22
            r8 = 1
            r9 = 262144(0x40000, float:3.67342E-40)
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x02fc
        L_0x03ce:
            r11 = r0
            r17 = r2
            r13 = r4
            r22 = r5
            r20 = r14
            r21 = r15
            r14 = r1
            r15 = r3
            r0 = 0
        L_0x03db:
            if (r0 == 0) goto L_0x0451
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mLastVisibleIndex
            r1 = -1
            int r2 = r17 + -1
        L_0x03e7:
            if (r2 < r11) goto L_0x03fb
            android.view.View r1 = r6.getChildAt(r2)
            androidx.recyclerview.widget.RecyclerView$Recycler r3 = r6.mRecycler
            androidx.recyclerview.widget.ChildHelper r4 = r6.mChildHelper
            int r4 = r4.indexOfChild(r1)
            r6.scrapOrRecycleView(r3, r4, r1)
            int r2 = r2 + -1
            goto L_0x03e7
        L_0x03fb:
            androidx.leanback.widget.Grid r1 = r6.mGrid
            r1.invalidateItemsAfter(r14)
            int r1 = r6.mFlag
            r2 = 65536(0x10000, float:9.18355E-41)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0430
            r23.appendVisibleItems()
            int r1 = r6.mFocusPosition
            if (r1 < 0) goto L_0x0451
            if (r1 > r0) goto L_0x0451
        L_0x0410:
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mLastVisibleIndex
            int r1 = r6.mFocusPosition
            if (r0 >= r1) goto L_0x0451
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            boolean r1 = r0.mReversedFlow
            if (r1 == 0) goto L_0x0429
            r1 = 1
            r10 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x042c
        L_0x0429:
            r1 = 1
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x042c:
            r0.appendVisibleItems(r10, r1)
            goto L_0x0410
        L_0x0430:
            androidx.leanback.widget.Grid r1 = r6.mGrid
            java.util.Objects.requireNonNull(r1)
            boolean r2 = r1.mReversedFlow
            if (r2 == 0) goto L_0x043e
            r2 = 1
            r10 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0441
        L_0x043e:
            r2 = 1
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0441:
            boolean r1 = r1.appendVisibleItems(r10, r2)
            if (r1 == 0) goto L_0x0451
            androidx.leanback.widget.Grid r1 = r6.mGrid
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mLastVisibleIndex
            if (r1 >= r0) goto L_0x0451
            goto L_0x0430
        L_0x0451:
            r23.updateScrollLimits()
            r23.updateSecondaryScrollLimits()
            goto L_0x04a6
        L_0x0458:
            r13 = r4
            r22 = r5
            r20 = r14
            r21 = r15
            r15 = r3
            int r2 = r6.mFlag
            r2 = r2 & -5
            r6.mFlag = r2
            r2 = r2 & -17
            if (r12 == 0) goto L_0x046d
            r3 = 16
            goto L_0x046e
        L_0x046d:
            r3 = 0
        L_0x046e:
            r2 = r2 | r3
            r6.mFlag = r2
            if (r12 == 0) goto L_0x047e
            if (r1 < 0) goto L_0x047b
            int r2 = r6.mFocusPosition
            if (r2 > r0) goto L_0x047b
            if (r2 >= r1) goto L_0x047e
        L_0x047b:
            int r1 = r6.mFocusPosition
            r0 = r1
        L_0x047e:
            androidx.leanback.widget.Grid r2 = r6.mGrid
            java.util.Objects.requireNonNull(r2)
            r2.mStartIndex = r1
            r1 = -1
            if (r0 == r1) goto L_0x04a6
        L_0x0488:
            androidx.leanback.widget.Grid r1 = r6.mGrid
            java.util.Objects.requireNonNull(r1)
            boolean r2 = r1.mReversedFlow
            if (r2 == 0) goto L_0x0496
            r2 = 1
            r10 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0499
        L_0x0496:
            r2 = 1
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x0499:
            boolean r1 = r1.appendVisibleItems(r10, r2)
            if (r1 == 0) goto L_0x04a6
            android.view.View r1 = r6.findViewByPosition(r0)
            if (r1 != 0) goto L_0x04a6
            goto L_0x0488
        L_0x04a6:
            r23.updateScrollLimits()
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r8 = r0.mFirstVisibleIndex
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r9 = r0.mLastVisibleIndex
            int r10 = -r13
            int r11 = -r15
            int r0 = r6.mFocusPosition
            android.view.View r14 = r6.findViewByPosition(r0)
            if (r14 == 0) goto L_0x04d0
            if (r12 == 0) goto L_0x04d0
            android.view.View r2 = r14.findFocus()
            r3 = 0
            r0 = r23
            r1 = r14
            r4 = r10
            r5 = r11
            r0.scrollToView(r1, r2, r3, r4, r5)
        L_0x04d0:
            if (r14 == 0) goto L_0x04de
            if (r16 == 0) goto L_0x04de
            boolean r0 = r14.hasFocus()
            if (r0 != 0) goto L_0x04de
            r14.requestFocus()
            goto L_0x0529
        L_0x04de:
            if (r16 != 0) goto L_0x0529
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            boolean r0 = r0.hasFocus()
            if (r0 != 0) goto L_0x0529
            if (r14 == 0) goto L_0x04f6
            boolean r0 = r14.hasFocusable()
            if (r0 == 0) goto L_0x04f6
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            r0.focusableViewAvailable(r14)
            goto L_0x0512
        L_0x04f6:
            int r0 = r23.getChildCount()
            r1 = 0
        L_0x04fb:
            if (r1 >= r0) goto L_0x0512
            android.view.View r14 = r6.getChildAt(r1)
            if (r14 == 0) goto L_0x050f
            boolean r2 = r14.hasFocusable()
            if (r2 == 0) goto L_0x050f
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            r0.focusableViewAvailable(r14)
            goto L_0x0512
        L_0x050f:
            int r1 = r1 + 1
            goto L_0x04fb
        L_0x0512:
            r1 = r14
            if (r12 == 0) goto L_0x0529
            if (r1 == 0) goto L_0x0529
            boolean r0 = r1.hasFocus()
            if (r0 == 0) goto L_0x0529
            android.view.View r2 = r1.findFocus()
            r3 = 0
            r0 = r23
            r4 = r10
            r5 = r11
            r0.scrollToView(r1, r2, r3, r4, r5)
        L_0x0529:
            r23.appendVisibleItems()
            r23.prependVisibleItems()
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mFirstVisibleIndex
            if (r0 != r8) goto L_0x071c
            androidx.leanback.widget.Grid r0 = r6.mGrid
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mLastVisibleIndex
            if (r0 != r9) goto L_0x071c
            r23.removeInvisibleViewsAtFront()
            r23.removeInvisibleViewsAtEnd()
            boolean r0 = r7.mRunPredictiveAnimations
            if (r0 == 0) goto L_0x0666
            androidx.recyclerview.widget.RecyclerView$Recycler r0 = r6.mRecycler
            java.util.Objects.requireNonNull(r0)
            java.util.List<androidx.recyclerview.widget.RecyclerView$ViewHolder> r0 = r0.mUnmodifiableAttachedScrap
            int r1 = r0.size()
            if (r1 != 0) goto L_0x055a
            goto L_0x0666
        L_0x055a:
            int[] r2 = r6.mDisappearingPositions
            if (r2 == 0) goto L_0x0561
            int r3 = r2.length
            if (r1 <= r3) goto L_0x0570
        L_0x0561:
            if (r2 != 0) goto L_0x0566
            r2 = 16
            goto L_0x0567
        L_0x0566:
            int r2 = r2.length
        L_0x0567:
            if (r2 >= r1) goto L_0x056c
            int r2 = r2 << 1
            goto L_0x0567
        L_0x056c:
            int[] r2 = new int[r2]
            r6.mDisappearingPositions = r2
        L_0x0570:
            r2 = 0
            r3 = 0
        L_0x0572:
            if (r2 >= r1) goto L_0x058a
            java.lang.Object r4 = r0.get(r2)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r4 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r4
            int r4 = r4.getAbsoluteAdapterPosition()
            if (r4 < 0) goto L_0x0587
            int[] r5 = r6.mDisappearingPositions
            int r7 = r3 + 1
            r5[r3] = r4
            r3 = r7
        L_0x0587:
            int r2 = r2 + 1
            goto L_0x0572
        L_0x058a:
            if (r3 <= 0) goto L_0x0661
            int[] r0 = r6.mDisappearingPositions
            r1 = 0
            java.util.Arrays.sort(r0, r1, r3)
            androidx.leanback.widget.Grid r0 = r6.mGrid
            int[] r2 = r6.mDisappearingPositions
            android.util.SparseIntArray r4 = r6.mPositionToRowInPostLayout
            java.util.Objects.requireNonNull(r0)
            int r5 = r0.mLastVisibleIndex
            if (r5 < 0) goto L_0x05a4
            int r7 = java.util.Arrays.binarySearch(r2, r1, r3, r5)
            goto L_0x05a5
        L_0x05a4:
            r7 = 0
        L_0x05a5:
            if (r7 >= 0) goto L_0x0609
            int r1 = -r7
            r7 = 1
            int r1 = r1 - r7
            boolean r7 = r0.mReversedFlow
            if (r7 == 0) goto L_0x05c3
            androidx.leanback.widget.Grid$Provider r7 = r0.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r7 = (androidx.leanback.widget.GridLayoutManager.C02232) r7
            int r7 = r7.getEdge(r5)
            androidx.leanback.widget.Grid$Provider r8 = r0.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r8 = (androidx.leanback.widget.GridLayoutManager.C02232) r8
            int r5 = r8.getSize(r5)
            int r7 = r7 - r5
            int r5 = r0.mSpacing
            int r7 = r7 - r5
            goto L_0x05d7
        L_0x05c3:
            androidx.leanback.widget.Grid$Provider r7 = r0.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r7 = (androidx.leanback.widget.GridLayoutManager.C02232) r7
            int r7 = r7.getEdge(r5)
            androidx.leanback.widget.Grid$Provider r8 = r0.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r8 = (androidx.leanback.widget.GridLayoutManager.C02232) r8
            int r5 = r8.getSize(r5)
            int r5 = r5 + r7
            int r7 = r0.mSpacing
            int r7 = r7 + r5
        L_0x05d7:
            if (r1 >= r3) goto L_0x0609
            r5 = r2[r1]
            int r8 = r4.get(r5)
            if (r8 >= 0) goto L_0x05e2
            r8 = 0
        L_0x05e2:
            androidx.leanback.widget.Grid$Provider r9 = r0.mProvider
            java.lang.Object[] r10 = r0.mTmpItem
            androidx.leanback.widget.GridLayoutManager$2 r9 = (androidx.leanback.widget.GridLayoutManager.C02232) r9
            r11 = 1
            int r5 = r9.createItem(r5, r11, r10, r11)
            androidx.leanback.widget.Grid$Provider r9 = r0.mProvider
            java.lang.Object[] r10 = r0.mTmpItem
            r11 = 0
            r10 = r10[r11]
            androidx.leanback.widget.GridLayoutManager$2 r9 = (androidx.leanback.widget.GridLayoutManager.C02232) r9
            r9.addItem(r10, r5, r8, r7)
            boolean r8 = r0.mReversedFlow
            if (r8 == 0) goto L_0x0602
            int r7 = r7 - r5
            int r5 = r0.mSpacing
            int r7 = r7 - r5
            goto L_0x0606
        L_0x0602:
            int r7 = r7 + r5
            int r5 = r0.mSpacing
            int r7 = r7 + r5
        L_0x0606:
            int r1 = r1 + 1
            goto L_0x05d7
        L_0x0609:
            int r1 = r0.mFirstVisibleIndex
            if (r1 < 0) goto L_0x0613
            r5 = 0
            int r3 = java.util.Arrays.binarySearch(r2, r5, r3, r1)
            goto L_0x0614
        L_0x0613:
            r3 = 0
        L_0x0614:
            if (r3 >= 0) goto L_0x0661
            int r3 = -r3
            int r3 = r3 + -2
            boolean r5 = r0.mReversedFlow
            if (r5 == 0) goto L_0x0626
            androidx.leanback.widget.Grid$Provider r5 = r0.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r5 = (androidx.leanback.widget.GridLayoutManager.C02232) r5
            int r1 = r5.getEdge(r1)
            goto L_0x062e
        L_0x0626:
            androidx.leanback.widget.Grid$Provider r5 = r0.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r5 = (androidx.leanback.widget.GridLayoutManager.C02232) r5
            int r1 = r5.getEdge(r1)
        L_0x062e:
            if (r3 < 0) goto L_0x0661
            r5 = r2[r3]
            int r7 = r4.get(r5)
            if (r7 >= 0) goto L_0x0639
            r7 = 0
        L_0x0639:
            androidx.leanback.widget.Grid$Provider r8 = r0.mProvider
            java.lang.Object[] r9 = r0.mTmpItem
            androidx.leanback.widget.GridLayoutManager$2 r8 = (androidx.leanback.widget.GridLayoutManager.C02232) r8
            r10 = 1
            r11 = 0
            int r5 = r8.createItem(r5, r11, r9, r10)
            boolean r8 = r0.mReversedFlow
            if (r8 == 0) goto L_0x064e
            int r8 = r0.mSpacing
            int r1 = r1 + r8
            int r1 = r1 + r5
            goto L_0x0652
        L_0x064e:
            int r8 = r0.mSpacing
            int r1 = r1 - r8
            int r1 = r1 - r5
        L_0x0652:
            androidx.leanback.widget.Grid$Provider r8 = r0.mProvider
            java.lang.Object[] r9 = r0.mTmpItem
            r10 = 0
            r9 = r9[r10]
            androidx.leanback.widget.GridLayoutManager$2 r8 = (androidx.leanback.widget.GridLayoutManager.C02232) r8
            r8.addItem(r9, r5, r7, r1)
            int r3 = r3 + -1
            goto L_0x062e
        L_0x0661:
            android.util.SparseIntArray r0 = r6.mPositionToRowInPostLayout
            r0.clear()
        L_0x0666:
            int r0 = r6.mFlag
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L_0x0671
            r0 = r0 & -1025(0xfffffffffffffbff, float:NaN)
            r6.mFlag = r0
            goto L_0x068e
        L_0x0671:
            r0 = r0 & -1025(0xfffffffffffffbff, float:NaN)
            r1 = 0
            boolean r2 = r6.processRowSizeSecondary(r1)
            r1 = 1024(0x400, float:1.435E-42)
            if (r2 == 0) goto L_0x067e
            r2 = r1
            goto L_0x067f
        L_0x067e:
            r2 = 0
        L_0x067f:
            r0 = r0 | r2
            r6.mFlag = r0
            r0 = r0 & r1
            if (r0 == 0) goto L_0x068e
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            androidx.leanback.widget.GridLayoutManager$1 r1 = r6.mRequestLayoutRunnable
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postOnAnimation(r0, r1)
        L_0x068e:
            int r0 = r6.mFlag
            r0 = r0 & 4
            if (r0 == 0) goto L_0x06b2
            int r0 = r6.mFocusPosition
            r1 = r21
            if (r0 != r1) goto L_0x06ae
            int r1 = r6.mSubFocusPosition
            r2 = r22
            if (r1 != r2) goto L_0x06ae
            android.view.View r0 = r6.findViewByPosition(r0)
            r3 = r20
            if (r0 != r3) goto L_0x06ae
            int r0 = r6.mFlag
            r0 = r0 & 8
            if (r0 == 0) goto L_0x06b2
        L_0x06ae:
            r23.dispatchChildSelected()
            goto L_0x06bd
        L_0x06b2:
            int r0 = r6.mFlag
            r0 = r0 & 20
            r4 = 16
            if (r0 != r4) goto L_0x06bd
            r23.dispatchChildSelected()
        L_0x06bd:
            r23.dispatchChildSelectedAndPositioned()
            int r0 = r6.mFlag
            r1 = r0 & 64
            if (r1 == 0) goto L_0x0712
            int r1 = r6.mOrientation
            r5 = 1
            if (r1 != r5) goto L_0x06e0
            int r0 = r6.mHeight
            int r0 = -r0
            int r1 = r23.getChildCount()
            if (r1 <= 0) goto L_0x070f
            r1 = 0
            android.view.View r1 = r6.getChildAt(r1)
            int r1 = r1.getTop()
            if (r1 >= 0) goto L_0x070f
            goto L_0x070e
        L_0x06e0:
            r8 = 262144(0x40000, float:3.67342E-40)
            r0 = r0 & r8
            if (r0 == 0) goto L_0x06fa
            int r0 = r6.mWidth
            int r1 = r23.getChildCount()
            if (r1 <= 0) goto L_0x070f
            r9 = 0
            android.view.View r1 = r6.getChildAt(r9)
            int r1 = r1.getRight()
            if (r1 <= r0) goto L_0x070f
            r0 = r1
            goto L_0x070f
        L_0x06fa:
            r9 = 0
            int r0 = r6.mWidth
            int r0 = -r0
            int r1 = r23.getChildCount()
            if (r1 <= 0) goto L_0x070f
            android.view.View r1 = r6.getChildAt(r9)
            int r1 = r1.getLeft()
            if (r1 >= 0) goto L_0x070f
        L_0x070e:
            int r0 = r0 + r1
        L_0x070f:
            r6.scrollDirectionPrimary(r0)
        L_0x0712:
            int r0 = r6.mFlag
            r0 = r0 & -4
            r6.mFlag = r0
            r23.leaveContext()
            return
        L_0x071c:
            r3 = r20
            r1 = r21
            r2 = r22
            r4 = 16
            r5 = 1
            r8 = 262144(0x40000, float:3.67342E-40)
            r9 = 0
            r21 = r1
            r22 = r2
            r20 = r3
            goto L_0x04a6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onLayoutChildren(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):void");
    }

    public final void onLayoutCompleted(RecyclerView.State state) {
        ArrayList<BaseGridView.OnLayoutCompletedListener> arrayList = this.mOnLayoutCompletedListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size >= 0) {
                    this.mOnLayoutCompletedListeners.get(size).onLayoutCompleted();
                } else {
                    return;
                }
            }
        }
    }

    public final boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
        if ((this.mFlag & 32768) == 0 && getAdapterPositionByView(view) != -1 && (this.mFlag & 35) == 0) {
            scrollToView(view, view2, true, 0, 0);
        }
        return true;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mFocusPosition = ((SavedState) parcelable).index;
            this.mFocusPositionOffset = 0;
            Objects.requireNonNull(this.mChildrenStates);
            this.mFlag |= 256;
            requestLayout();
        }
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.index = this.mFocusPosition;
        Objects.requireNonNull(this.mChildrenStates);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getAdapterPositionByView(getChildAt(i)) != -1) {
                Objects.requireNonNull(this.mChildrenStates);
            }
        }
        savedState.childStates = null;
        return savedState;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        if (r5 != false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        if (r5 != false) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        if (r7 == androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
        r7 = 4096;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean performAccessibilityAction(androidx.recyclerview.widget.RecyclerView.Recycler r5, androidx.recyclerview.widget.RecyclerView.State r6, int r7, android.os.Bundle r8) {
        /*
            r4 = this;
            int r8 = r4.mFlag
            r0 = 131072(0x20000, float:1.83671E-40)
            r8 = r8 & r0
            r0 = 0
            r1 = 1
            if (r8 == 0) goto L_0x000b
            r8 = r1
            goto L_0x000c
        L_0x000b:
            r8 = r0
        L_0x000c:
            if (r8 != 0) goto L_0x000f
            return r1
        L_0x000f:
            r4.saveContext(r5, r6)
            int r5 = r4.mFlag
            r8 = 262144(0x40000, float:3.67342E-40)
            r5 = r5 & r8
            if (r5 == 0) goto L_0x001b
            r5 = r1
            goto L_0x001c
        L_0x001b:
            r5 = r0
        L_0x001c:
            int r8 = r4.mOrientation
            r2 = 8192(0x2000, float:1.14794E-41)
            r3 = 4096(0x1000, float:5.74E-42)
            if (r8 != 0) goto L_0x003a
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r8 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT
            int r8 = r8.getId()
            if (r7 != r8) goto L_0x002f
            if (r5 == 0) goto L_0x0042
            goto L_0x004c
        L_0x002f:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r8 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT
            int r8 = r8.getId()
            if (r7 != r8) goto L_0x004d
            if (r5 == 0) goto L_0x004c
            goto L_0x0042
        L_0x003a:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r5 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP
            int r5 = r5.getId()
            if (r7 != r5) goto L_0x0044
        L_0x0042:
            r7 = r2
            goto L_0x004d
        L_0x0044:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r5 = androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN
            int r5 = r5.getId()
            if (r7 != r5) goto L_0x004d
        L_0x004c:
            r7 = r3
        L_0x004d:
            int r5 = r4.mFocusPosition
            if (r5 != 0) goto L_0x0055
            if (r7 != r2) goto L_0x0055
            r8 = r1
            goto L_0x0056
        L_0x0055:
            r8 = r0
        L_0x0056:
            int r6 = r6.getItemCount()
            int r6 = r6 - r1
            if (r5 != r6) goto L_0x0061
            if (r7 != r3) goto L_0x0061
            r5 = r1
            goto L_0x0062
        L_0x0061:
            r5 = r0
        L_0x0062:
            if (r8 != 0) goto L_0x007b
            if (r5 == 0) goto L_0x0067
            goto L_0x007b
        L_0x0067:
            if (r7 == r3) goto L_0x0074
            if (r7 == r2) goto L_0x006c
            goto L_0x0089
        L_0x006c:
            r4.processPendingMovement(r0)
            r5 = -1
            r4.processSelectionMoves(r0, r5)
            goto L_0x0089
        L_0x0074:
            r4.processPendingMovement(r1)
            r4.processSelectionMoves(r0, r1)
            goto L_0x0089
        L_0x007b:
            android.view.accessibility.AccessibilityEvent r5 = android.view.accessibility.AccessibilityEvent.obtain(r3)
            androidx.leanback.widget.BaseGridView r6 = r4.mBaseGridView
            r6.onInitializeAccessibilityEvent(r5)
            androidx.leanback.widget.BaseGridView r6 = r4.mBaseGridView
            r6.requestSendAccessibilityEvent(r6, r5)
        L_0x0089:
            r4.leaveContext()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.performAccessibilityAction(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, android.os.Bundle):boolean");
    }

    public final void prependVisibleItems() {
        int i;
        Grid grid = this.mGrid;
        if ((this.mFlag & 262144) != 0) {
            i = this.mSizePrimary + 0 + this.mExtraLayoutSpaceInPreLayout;
        } else {
            i = 0 - this.mExtraLayoutSpaceInPreLayout;
        }
        Objects.requireNonNull(grid);
        grid.prependVisibleItems(i, false);
    }

    public final void processPendingMovement(boolean z) {
        if (z) {
            if (hasCreatedLastItem()) {
                return;
            }
        } else if (hasCreatedFirstItem()) {
            return;
        }
        PendingMoveSmoothScroller pendingMoveSmoothScroller = this.mPendingMoveSmoothScroller;
        int i = -1;
        boolean z2 = true;
        if (pendingMoveSmoothScroller == null) {
            if (z) {
                i = 1;
            }
            if (this.mNumRows <= 1) {
                z2 = false;
            }
            PendingMoveSmoothScroller pendingMoveSmoothScroller2 = new PendingMoveSmoothScroller(i, z2);
            this.mFocusPositionOffset = 0;
            startSmoothScroll(pendingMoveSmoothScroller2);
        } else if (z) {
            Objects.requireNonNull(pendingMoveSmoothScroller);
            int i2 = pendingMoveSmoothScroller.mPendingMoves;
            if (i2 < GridLayoutManager.this.mMaxPendingMoves) {
                pendingMoveSmoothScroller.mPendingMoves = i2 + 1;
            }
        } else {
            Objects.requireNonNull(pendingMoveSmoothScroller);
            int i3 = pendingMoveSmoothScroller.mPendingMoves;
            if (i3 > (-GridLayoutManager.this.mMaxPendingMoves)) {
                pendingMoveSmoothScroller.mPendingMoves = i3 - 1;
            }
        }
    }

    public final boolean processRowSizeSecondary(boolean z) {
        CircularIntArray[] circularIntArrayArr;
        CircularIntArray circularIntArray;
        int i;
        int i2;
        int i3;
        int i4;
        if (this.mFixedRowSizeSecondary != 0 || this.mRowSizeSecondary == null) {
            return false;
        }
        Grid grid = this.mGrid;
        if (grid == null) {
            circularIntArrayArr = null;
        } else {
            circularIntArrayArr = grid.getItemPositionsInRows(grid.mFirstVisibleIndex, grid.mLastVisibleIndex);
        }
        boolean z2 = false;
        int i5 = -1;
        for (int i6 = 0; i6 < this.mNumRows; i6++) {
            if (circularIntArrayArr == null) {
                circularIntArray = null;
            } else {
                circularIntArray = circularIntArrayArr[i6];
            }
            if (circularIntArray == null) {
                i = 0;
            } else {
                i = (circularIntArray.mTail + 0) & circularIntArray.mCapacityBitmask;
            }
            int i7 = 0;
            int i8 = -1;
            while (i7 < i) {
                if (i7 >= 0) {
                    int i9 = circularIntArray.mTail;
                    int i10 = circularIntArray.mCapacityBitmask;
                    if (i7 < ((i9 + 0) & i10)) {
                        int[] iArr = circularIntArray.mElements;
                        int i11 = i7 + 1;
                        if (i11 < 0 || i11 >= ((i9 + 0) & i10)) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        int i12 = iArr[(i11 + 0) & i10];
                        for (int i13 = iArr[(i7 + 0) & i10]; i13 <= i12; i13++) {
                            View findViewByPosition = findViewByPosition(i13 - this.mPositionDeltaInPreLayout);
                            if (findViewByPosition != null) {
                                if (z) {
                                    measureChild(findViewByPosition);
                                }
                                if (this.mOrientation == 0) {
                                    i4 = getDecoratedMeasuredHeightWithMargin(findViewByPosition);
                                } else {
                                    i4 = getDecoratedMeasuredWidthWithMargin(findViewByPosition);
                                }
                                if (i4 > i8) {
                                    i8 = i4;
                                }
                            }
                        }
                        i7 += 2;
                    }
                } else {
                    Objects.requireNonNull(circularIntArray);
                }
                throw new ArrayIndexOutOfBoundsException();
            }
            int itemCount = this.mState.getItemCount();
            BaseGridView baseGridView = this.mBaseGridView;
            Objects.requireNonNull(baseGridView);
            if (!baseGridView.mHasFixedSize && z && i8 < 0 && itemCount > 0) {
                if (i5 < 0) {
                    int i14 = this.mFocusPosition;
                    if (i14 < 0) {
                        i14 = 0;
                    } else if (i14 >= itemCount) {
                        i14 = itemCount - 1;
                    }
                    if (getChildCount() > 0) {
                        int layoutPosition = this.mBaseGridView.getChildViewHolder(getChildAt(0)).getLayoutPosition();
                        int layoutPosition2 = this.mBaseGridView.getChildViewHolder(getChildAt(getChildCount() - 1)).getLayoutPosition();
                        if (i2 >= layoutPosition && i2 <= layoutPosition2) {
                            if (i2 - layoutPosition <= layoutPosition2 - i2) {
                                i2 = layoutPosition - 1;
                            } else {
                                i2 = layoutPosition2 + 1;
                            }
                            if (i2 < 0 && layoutPosition2 < itemCount - 1) {
                                i2 = layoutPosition2 + 1;
                            } else if (i2 >= itemCount && layoutPosition > 0) {
                                i2 = layoutPosition - 1;
                            }
                        }
                    }
                    if (i2 >= 0 && i2 < itemCount) {
                        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                        int[] iArr2 = this.mMeasuredDimension;
                        View viewForPosition = this.mRecycler.getViewForPosition(i2);
                        if (viewForPosition != null) {
                            LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
                            Rect rect = sTempRect;
                            calculateItemDecorationsForChild(viewForPosition, rect);
                            viewForPosition.measure(ViewGroup.getChildMeasureSpec(makeMeasureSpec, getPaddingRight() + getPaddingLeft() + layoutParams.leftMargin + layoutParams.rightMargin + rect.left + rect.right, layoutParams.width), ViewGroup.getChildMeasureSpec(makeMeasureSpec2, getPaddingBottom() + getPaddingTop() + layoutParams.topMargin + layoutParams.bottomMargin + rect.top + rect.bottom, layoutParams.height));
                            iArr2[0] = getDecoratedMeasuredWidthWithMargin(viewForPosition);
                            iArr2[1] = getDecoratedMeasuredHeightWithMargin(viewForPosition);
                            this.mRecycler.recycleView(viewForPosition);
                        }
                        if (this.mOrientation == 0) {
                            i3 = this.mMeasuredDimension[1];
                        } else {
                            i3 = this.mMeasuredDimension[0];
                        }
                        i5 = i3;
                    }
                }
                if (i5 >= 0) {
                    i8 = i5;
                }
            }
            if (i8 < 0) {
                i8 = 0;
            }
            int[] iArr3 = this.mRowSizeSecondary;
            if (iArr3[i6] != i8) {
                iArr3[i6] = i8;
                z2 = true;
            }
        }
        return z2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x0078  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int processSelectionMoves(boolean r12, int r13) {
        /*
            r11 = this;
            androidx.leanback.widget.Grid r0 = r11.mGrid
            if (r0 != 0) goto L_0x0005
            return r13
        L_0x0005:
            int r1 = r11.mFocusPosition
            r2 = -1
            if (r1 == r2) goto L_0x0014
            androidx.leanback.widget.Grid$Location r0 = r0.getLocation(r1)
            if (r0 != 0) goto L_0x0011
            goto L_0x0014
        L_0x0011:
            int r0 = r0.row
            goto L_0x0015
        L_0x0014:
            r0 = r2
        L_0x0015:
            r3 = 0
            int r4 = r11.getChildCount()
            r5 = 0
            r6 = r5
        L_0x001c:
            r7 = 1
            if (r6 >= r4) goto L_0x0076
            if (r13 == 0) goto L_0x0076
            if (r13 <= 0) goto L_0x0025
            r8 = r6
            goto L_0x0028
        L_0x0025:
            int r8 = r4 + -1
            int r8 = r8 - r6
        L_0x0028:
            android.view.View r9 = r11.getChildAt(r8)
            int r10 = r9.getVisibility()
            if (r10 != 0) goto L_0x003f
            boolean r10 = r11.hasFocus()
            if (r10 == 0) goto L_0x0040
            boolean r10 = r9.hasFocusable()
            if (r10 == 0) goto L_0x003f
            goto L_0x0040
        L_0x003f:
            r7 = r5
        L_0x0040:
            if (r7 != 0) goto L_0x0043
            goto L_0x0073
        L_0x0043:
            android.view.View r7 = r11.getChildAt(r8)
            int r7 = getAdapterPositionByView(r7)
            androidx.leanback.widget.Grid r8 = r11.mGrid
            java.util.Objects.requireNonNull(r8)
            androidx.leanback.widget.Grid$Location r8 = r8.getLocation(r7)
            if (r8 != 0) goto L_0x0058
            r8 = r2
            goto L_0x005a
        L_0x0058:
            int r8 = r8.row
        L_0x005a:
            if (r0 != r2) goto L_0x0060
            r1 = r7
            r0 = r8
        L_0x005e:
            r3 = r9
            goto L_0x0073
        L_0x0060:
            if (r8 != r0) goto L_0x0073
            if (r13 <= 0) goto L_0x0066
            if (r7 > r1) goto L_0x006a
        L_0x0066:
            if (r13 >= 0) goto L_0x0073
            if (r7 >= r1) goto L_0x0073
        L_0x006a:
            if (r13 <= 0) goto L_0x006f
            int r13 = r13 + -1
            goto L_0x0071
        L_0x006f:
            int r13 = r13 + 1
        L_0x0071:
            r1 = r7
            goto L_0x005e
        L_0x0073:
            int r6 = r6 + 1
            goto L_0x001c
        L_0x0076:
            if (r3 == 0) goto L_0x0097
            if (r12 == 0) goto L_0x0094
            boolean r12 = r11.hasFocus()
            if (r12 == 0) goto L_0x008f
            int r12 = r11.mFlag
            r12 = r12 | 32
            r11.mFlag = r12
            r3.requestFocus()
            int r12 = r11.mFlag
            r12 = r12 & -33
            r11.mFlag = r12
        L_0x008f:
            r11.mFocusPosition = r1
            r11.mSubFocusPosition = r5
            goto L_0x0097
        L_0x0094:
            r11.scrollToView(r3, r7)
        L_0x0097:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.processSelectionMoves(boolean, int):int");
    }

    public final void removeInvisibleViewsAtEnd() {
        int i;
        boolean z;
        int i2 = this.mFlag;
        if ((65600 & i2) == 65536) {
            Grid grid = this.mGrid;
            int i3 = this.mFocusPosition;
            if ((i2 & 262144) != 0) {
                i = 0;
            } else {
                i = this.mSizePrimary + 0;
            }
            Objects.requireNonNull(grid);
            while (true) {
                int i4 = grid.mLastVisibleIndex;
                if (i4 < grid.mFirstVisibleIndex || i4 <= i3) {
                    break;
                }
                if (grid.mReversedFlow ? ((C02232) grid.mProvider).getEdge(i4) > i : ((C02232) grid.mProvider).getEdge(i4) < i) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    break;
                }
                ((C02232) grid.mProvider).removeItem(grid.mLastVisibleIndex);
                grid.mLastVisibleIndex--;
            }
            if (grid.mLastVisibleIndex < grid.mFirstVisibleIndex) {
                grid.mLastVisibleIndex = -1;
                grid.mFirstVisibleIndex = -1;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003c, code lost:
        if ((((androidx.leanback.widget.GridLayoutManager.C02232) r1.mProvider).getEdge(r1.mFirstVisibleIndex) + r0) <= r7) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004a, code lost:
        if ((((androidx.leanback.widget.GridLayoutManager.C02232) r1.mProvider).getEdge(r1.mFirstVisibleIndex) - r0) >= r7) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
        r0 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void removeInvisibleViewsAtFront() {
        /*
            r7 = this;
            int r0 = r7.mFlag
            r1 = 65600(0x10040, float:9.1925E-41)
            r1 = r1 & r0
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r1 != r2) goto L_0x006b
            androidx.leanback.widget.Grid r1 = r7.mGrid
            int r2 = r7.mFocusPosition
            r3 = 262144(0x40000, float:3.67342E-40)
            r0 = r0 & r3
            r3 = 0
            if (r0 == 0) goto L_0x0018
            int r7 = r7.mSizePrimary
            int r7 = r7 + r3
            goto L_0x0019
        L_0x0018:
            r7 = r3
        L_0x0019:
            java.util.Objects.requireNonNull(r1)
        L_0x001c:
            int r0 = r1.mLastVisibleIndex
            int r4 = r1.mFirstVisibleIndex
            if (r0 < r4) goto L_0x0060
            if (r4 >= r2) goto L_0x0060
            androidx.leanback.widget.Grid$Provider r0 = r1.mProvider
            androidx.leanback.widget.GridLayoutManager$2 r0 = (androidx.leanback.widget.GridLayoutManager.C02232) r0
            int r0 = r0.getSize(r4)
            boolean r4 = r1.mReversedFlow
            r5 = 1
            if (r4 != 0) goto L_0x003f
            androidx.leanback.widget.Grid$Provider r4 = r1.mProvider
            int r6 = r1.mFirstVisibleIndex
            androidx.leanback.widget.GridLayoutManager$2 r4 = (androidx.leanback.widget.GridLayoutManager.C02232) r4
            int r4 = r4.getEdge(r6)
            int r4 = r4 + r0
            if (r4 > r7) goto L_0x004e
            goto L_0x004c
        L_0x003f:
            androidx.leanback.widget.Grid$Provider r4 = r1.mProvider
            int r6 = r1.mFirstVisibleIndex
            androidx.leanback.widget.GridLayoutManager$2 r4 = (androidx.leanback.widget.GridLayoutManager.C02232) r4
            int r4 = r4.getEdge(r6)
            int r4 = r4 - r0
            if (r4 < r7) goto L_0x004e
        L_0x004c:
            r0 = r5
            goto L_0x004f
        L_0x004e:
            r0 = r3
        L_0x004f:
            if (r0 == 0) goto L_0x0060
            androidx.leanback.widget.Grid$Provider r0 = r1.mProvider
            int r4 = r1.mFirstVisibleIndex
            androidx.leanback.widget.GridLayoutManager$2 r0 = (androidx.leanback.widget.GridLayoutManager.C02232) r0
            r0.removeItem(r4)
            int r0 = r1.mFirstVisibleIndex
            int r0 = r0 + r5
            r1.mFirstVisibleIndex = r0
            goto L_0x001c
        L_0x0060:
            int r7 = r1.mLastVisibleIndex
            int r0 = r1.mFirstVisibleIndex
            if (r7 >= r0) goto L_0x006b
            r7 = -1
            r1.mLastVisibleIndex = r7
            r1.mFirstVisibleIndex = r7
        L_0x006b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.removeInvisibleViewsAtFront():void");
    }

    public final void saveContext(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i = this.mSaveContextLevel;
        if (i == 0) {
            this.mRecycler = recycler;
            this.mState = state;
            this.mPositionDeltaInPreLayout = 0;
            this.mExtraLayoutSpaceInPreLayout = 0;
        }
        this.mSaveContextLevel = i + 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0030, code lost:
        if (r7 > r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0056, code lost:
        if (r7 < r0) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int scrollDirectionPrimary(int r7) {
        /*
            r6 = this;
            int r0 = r6.mFlag
            r1 = r0 & 64
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0059
            r0 = r0 & 3
            if (r0 == r3) goto L_0x0059
            if (r7 <= 0) goto L_0x0033
            androidx.leanback.widget.WindowAlignment r0 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r0)
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mMainAxis
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMaxEdge
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r0 != r1) goto L_0x0021
            r0 = r3
            goto L_0x0022
        L_0x0021:
            r0 = r2
        L_0x0022:
            if (r0 != 0) goto L_0x0059
            androidx.leanback.widget.WindowAlignment r0 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r0)
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mMainAxis
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMaxScroll
            if (r7 <= r0) goto L_0x0059
            goto L_0x0058
        L_0x0033:
            if (r7 >= 0) goto L_0x0059
            androidx.leanback.widget.WindowAlignment r0 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r0)
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mMainAxis
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMinEdge
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 != r1) goto L_0x0047
            r0 = r3
            goto L_0x0048
        L_0x0047:
            r0 = r2
        L_0x0048:
            if (r0 != 0) goto L_0x0059
            androidx.leanback.widget.WindowAlignment r0 = r6.mWindowAlignment
            java.util.Objects.requireNonNull(r0)
            androidx.leanback.widget.WindowAlignment$Axis r0 = r0.mMainAxis
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mMinScroll
            if (r7 >= r0) goto L_0x0059
        L_0x0058:
            r7 = r0
        L_0x0059:
            if (r7 != 0) goto L_0x005c
            return r2
        L_0x005c:
            int r0 = -r7
            int r1 = r6.getChildCount()
            int r4 = r6.mOrientation
            if (r4 != r3) goto L_0x0072
            r4 = r2
        L_0x0066:
            if (r4 >= r1) goto L_0x007f
            android.view.View r5 = r6.getChildAt(r4)
            r5.offsetTopAndBottom(r0)
            int r4 = r4 + 1
            goto L_0x0066
        L_0x0072:
            r4 = r2
        L_0x0073:
            if (r4 >= r1) goto L_0x007f
            android.view.View r5 = r6.getChildAt(r4)
            r5.offsetLeftAndRight(r0)
            int r4 = r4 + 1
            goto L_0x0073
        L_0x007f:
            int r0 = r6.mFlag
            r0 = r0 & 3
            if (r0 != r3) goto L_0x0089
            r6.updateScrollLimits()
            return r7
        L_0x0089:
            int r0 = r6.getChildCount()
            int r1 = r6.mFlag
            r4 = 262144(0x40000, float:3.67342E-40)
            r1 = r1 & r4
            if (r1 == 0) goto L_0x0097
            if (r7 <= 0) goto L_0x009d
            goto L_0x0099
        L_0x0097:
            if (r7 >= 0) goto L_0x009d
        L_0x0099:
            r6.prependVisibleItems()
            goto L_0x00a0
        L_0x009d:
            r6.appendVisibleItems()
        L_0x00a0:
            int r1 = r6.getChildCount()
            if (r1 <= r0) goto L_0x00a8
            r0 = r3
            goto L_0x00a9
        L_0x00a8:
            r0 = r2
        L_0x00a9:
            int r1 = r6.getChildCount()
            int r5 = r6.mFlag
            r4 = r4 & r5
            if (r4 == 0) goto L_0x00b5
            if (r7 <= 0) goto L_0x00bb
            goto L_0x00b7
        L_0x00b5:
            if (r7 >= 0) goto L_0x00bb
        L_0x00b7:
            r6.removeInvisibleViewsAtEnd()
            goto L_0x00be
        L_0x00bb:
            r6.removeInvisibleViewsAtFront()
        L_0x00be:
            int r4 = r6.getChildCount()
            if (r4 >= r1) goto L_0x00c5
            goto L_0x00c6
        L_0x00c5:
            r3 = r2
        L_0x00c6:
            r0 = r0 | r3
            if (r0 == 0) goto L_0x00e5
            int r0 = r6.mFlag
            r0 = r0 & -1025(0xfffffffffffffbff, float:NaN)
            boolean r1 = r6.processRowSizeSecondary(r2)
            r3 = 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L_0x00d6
            r2 = r3
        L_0x00d6:
            r0 = r0 | r2
            r6.mFlag = r0
            r0 = r0 & r3
            if (r0 == 0) goto L_0x00e5
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            androidx.leanback.widget.GridLayoutManager$1 r1 = r6.mRequestLayoutRunnable
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postOnAnimation(r0, r1)
        L_0x00e5:
            androidx.leanback.widget.BaseGridView r0 = r6.mBaseGridView
            r0.invalidate()
            r6.updateScrollLimits()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.scrollDirectionPrimary(int):int");
    }

    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        boolean z;
        int i2;
        if ((this.mFlag & 512) != 0) {
            if (this.mGrid != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                saveContext(recycler, state);
                this.mFlag = (this.mFlag & -4) | 2;
                if (this.mOrientation == 0) {
                    i2 = scrollDirectionPrimary(i);
                } else {
                    i2 = scrollDirectionSecondary(i);
                }
                leaveContext();
                this.mFlag &= -4;
                return i2;
            }
        }
        return 0;
    }

    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        boolean z;
        int i2;
        int i3 = this.mFlag;
        if ((i3 & 512) != 0) {
            if (this.mGrid != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.mFlag = (i3 & -4) | 2;
                saveContext(recycler, state);
                if (this.mOrientation == 1) {
                    i2 = scrollDirectionPrimary(i);
                } else {
                    i2 = scrollDirectionSecondary(i);
                }
                leaveContext();
                this.mFlag &= -4;
                return i2;
            }
        }
        return 0;
    }

    public final void setOrientation(int i) {
        if (i == 0 || i == 1) {
            this.mOrientation = i;
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            WindowAlignment windowAlignment = this.mWindowAlignment;
            Objects.requireNonNull(windowAlignment);
            if (i == 0) {
                windowAlignment.mMainAxis = windowAlignment.horizontal;
                windowAlignment.mSecondAxis = windowAlignment.vertical;
            } else {
                windowAlignment.mMainAxis = windowAlignment.vertical;
                windowAlignment.mSecondAxis = windowAlignment.horizontal;
            }
            Objects.requireNonNull(this.mItemAlignment);
            this.mFlag |= 256;
        }
    }

    public final void setRowHeight(int i) {
        if (i >= 0 || i == -2) {
            this.mRowSizeSecondaryRequested = i;
            return;
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Invalid row height: ", i));
    }

    public final void setSelection(int i, boolean z) {
        if ((this.mFocusPosition != i && i != -1) || this.mSubFocusPosition != 0 || this.mPrimaryScrollExtra != 0) {
            scrollToSelection(i, z);
        }
    }

    public final void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        GridLinearSmoothScroller gridLinearSmoothScroller = this.mCurrentSmoothScroller;
        if (gridLinearSmoothScroller != null) {
            gridLinearSmoothScroller.mSkipOnStopInternal = true;
        }
        super.startSmoothScroll(smoothScroller);
        if (!smoothScroller.mRunning || !(smoothScroller instanceof GridLinearSmoothScroller)) {
            this.mCurrentSmoothScroller = null;
            this.mPendingMoveSmoothScroller = null;
            return;
        }
        GridLinearSmoothScroller gridLinearSmoothScroller2 = (GridLinearSmoothScroller) smoothScroller;
        this.mCurrentSmoothScroller = gridLinearSmoothScroller2;
        if (gridLinearSmoothScroller2 instanceof PendingMoveSmoothScroller) {
            this.mPendingMoveSmoothScroller = (PendingMoveSmoothScroller) gridLinearSmoothScroller2;
        } else {
            this.mPendingMoveSmoothScroller = null;
        }
    }

    public final void updateScrollLimits() {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z3;
        boolean z4;
        if (this.mState.getItemCount() != 0) {
            if ((this.mFlag & 262144) == 0) {
                Grid grid = this.mGrid;
                Objects.requireNonNull(grid);
                i4 = grid.mLastVisibleIndex;
                i3 = this.mState.getItemCount() - 1;
                Grid grid2 = this.mGrid;
                Objects.requireNonNull(grid2);
                i = grid2.mFirstVisibleIndex;
                i2 = 0;
            } else {
                Grid grid3 = this.mGrid;
                Objects.requireNonNull(grid3);
                i4 = grid3.mFirstVisibleIndex;
                Grid grid4 = this.mGrid;
                Objects.requireNonNull(grid4);
                i = grid4.mLastVisibleIndex;
                i2 = this.mState.getItemCount() - 1;
                i3 = 0;
            }
            if (i4 >= 0 && i >= 0) {
                if (i4 == i3) {
                    z = true;
                } else {
                    z = false;
                }
                if (i == i2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                int i11 = Integer.MIN_VALUE;
                int i12 = Integer.MAX_VALUE;
                if (!z) {
                    WindowAlignment windowAlignment = this.mWindowAlignment;
                    Objects.requireNonNull(windowAlignment);
                    WindowAlignment.Axis axis = windowAlignment.mMainAxis;
                    Objects.requireNonNull(axis);
                    if (axis.mMaxEdge == Integer.MAX_VALUE) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3 && !z2) {
                        WindowAlignment windowAlignment2 = this.mWindowAlignment;
                        Objects.requireNonNull(windowAlignment2);
                        WindowAlignment.Axis axis2 = windowAlignment2.mMainAxis;
                        Objects.requireNonNull(axis2);
                        if (axis2.mMinEdge == Integer.MIN_VALUE) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        if (z4) {
                            return;
                        }
                    }
                }
                if (z) {
                    i12 = this.mGrid.findRowMax(true, sTwoInts);
                    View findViewByPosition = findViewByPosition(sTwoInts[1]);
                    if (this.mOrientation == 0) {
                        LayoutParams layoutParams = (LayoutParams) findViewByPosition.getLayoutParams();
                        Objects.requireNonNull(layoutParams);
                        i9 = findViewByPosition.getLeft() + layoutParams.mLeftInset;
                        i10 = layoutParams.mAlignX;
                    } else {
                        LayoutParams layoutParams2 = (LayoutParams) findViewByPosition.getLayoutParams();
                        Objects.requireNonNull(layoutParams2);
                        i9 = findViewByPosition.getTop() + layoutParams2.mTopInset;
                        i10 = layoutParams2.mAlignY;
                    }
                    int i13 = i10 + i9;
                    LayoutParams layoutParams3 = (LayoutParams) findViewByPosition.getLayoutParams();
                    Objects.requireNonNull(layoutParams3);
                    int[] iArr = layoutParams3.mAlignMultiple;
                    if (iArr == null || iArr.length <= 0) {
                        i5 = i13;
                    } else {
                        i5 = (iArr[iArr.length - 1] - iArr[0]) + i13;
                    }
                } else {
                    i5 = Integer.MAX_VALUE;
                }
                if (z2) {
                    i11 = this.mGrid.findRowMin(false, sTwoInts);
                    View findViewByPosition2 = findViewByPosition(sTwoInts[1]);
                    if (this.mOrientation == 0) {
                        LayoutParams layoutParams4 = (LayoutParams) findViewByPosition2.getLayoutParams();
                        Objects.requireNonNull(layoutParams4);
                        i8 = findViewByPosition2.getLeft() + layoutParams4.mLeftInset;
                        i7 = layoutParams4.mAlignX;
                    } else {
                        LayoutParams layoutParams5 = (LayoutParams) findViewByPosition2.getLayoutParams();
                        Objects.requireNonNull(layoutParams5);
                        i8 = findViewByPosition2.getTop() + layoutParams5.mTopInset;
                        i7 = layoutParams5.mAlignY;
                    }
                    i6 = i8 + i7;
                } else {
                    i6 = Integer.MIN_VALUE;
                }
                WindowAlignment windowAlignment3 = this.mWindowAlignment;
                Objects.requireNonNull(windowAlignment3);
                windowAlignment3.mMainAxis.updateMinMax(i11, i12, i6, i5);
            }
        }
    }

    public final void updateSecondaryScrollLimits() {
        WindowAlignment windowAlignment = this.mWindowAlignment;
        Objects.requireNonNull(windowAlignment);
        WindowAlignment.Axis axis = windowAlignment.mSecondAxis;
        Objects.requireNonNull(axis);
        int i = axis.mPaddingMin - this.mScrollOffsetSecondary;
        int sizeSecondary = getSizeSecondary() + i;
        axis.updateMinMax(i, sizeSecondary, i, sizeSecondary);
    }

    public static int getDecoratedMeasuredHeightWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return RecyclerView.LayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    public static int getDecoratedMeasuredWidthWithMargin(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return RecyclerView.LayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
    }

    public final int getDecoratedBottom(View view) {
        return (((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.bottom + view.getBottom()) - ((LayoutParams) view.getLayoutParams()).mBottomInset;
    }

    public final void getDecoratedBoundsWithMargins(View view, Rect rect) {
        super.getDecoratedBoundsWithMargins(view, rect);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        rect.left += layoutParams.mLeftInset;
        rect.top += layoutParams.mTopInset;
        rect.right -= layoutParams.mRightInset;
        rect.bottom -= layoutParams.mBottomInset;
    }

    public final int getDecoratedLeft(View view) {
        return (view.getLeft() - ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left) + ((LayoutParams) view.getLayoutParams()).mLeftInset;
    }

    public final int getDecoratedRight(View view) {
        return (((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.right + view.getRight()) - ((LayoutParams) view.getLayoutParams()).mRightInset;
    }

    public final int getDecoratedTop(View view) {
        return (view.getTop() - ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top) + ((LayoutParams) view.getLayoutParams()).mTopInset;
    }

    public final boolean hasCreatedFirstItem() {
        if (getItemCount() == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(0) != null) {
            return true;
        }
        return false;
    }

    public final boolean hasCreatedLastItem() {
        int itemCount = getItemCount();
        if (itemCount == 0 || this.mBaseGridView.findViewHolderForAdapterPosition(itemCount - 1) != null) {
            return true;
        }
        return false;
    }

    public final void measureChild(View view) {
        int i;
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = sTempRect;
        calculateItemDecorationsForChild(view, rect);
        int i4 = layoutParams.leftMargin + layoutParams.rightMargin + rect.left + rect.right;
        int i5 = layoutParams.topMargin + layoutParams.bottomMargin + rect.top + rect.bottom;
        if (this.mRowSizeSecondaryRequested == -2) {
            i = View.MeasureSpec.makeMeasureSpec(0, 0);
        } else {
            i = View.MeasureSpec.makeMeasureSpec(this.mFixedRowSizeSecondary, 1073741824);
        }
        if (this.mOrientation == 0) {
            i2 = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i4, layoutParams.width);
            i3 = ViewGroup.getChildMeasureSpec(i, i5, layoutParams.height);
        } else {
            int childMeasureSpec = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0), i5, layoutParams.height);
            int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i, i4, layoutParams.width);
            i3 = childMeasureSpec;
            i2 = childMeasureSpec2;
        }
        view.measure(i2, i3);
    }

    public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        boolean z;
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat;
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat2;
        saveContext(recycler, state);
        int itemCount = state.getItemCount();
        int i = this.mFlag;
        if ((262144 & i) != 0) {
            z = true;
        } else {
            z = false;
        }
        if ((i & 2048) == 0 || (itemCount > 1 && !isItemFullyVisible(0))) {
            if (this.mOrientation == 0) {
                if (z) {
                    accessibilityActionCompat2 = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT;
                } else {
                    accessibilityActionCompat2 = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT;
                }
                accessibilityNodeInfoCompat.addAction(accessibilityActionCompat2);
            } else {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        if ((this.mFlag & 4096) == 0 || (itemCount > 1 && !isItemFullyVisible(itemCount - 1))) {
            if (this.mOrientation == 0) {
                if (z) {
                    accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT;
                } else {
                    accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT;
                }
                accessibilityNodeInfoCompat.addAction(accessibilityActionCompat);
            } else {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), 0));
        leaveContext();
    }

    public final void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (this.mGrid != null && (layoutParams instanceof LayoutParams)) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            Objects.requireNonNull(layoutParams2);
            int absoluteAdapterPosition = layoutParams2.mViewHolder.getAbsoluteAdapterPosition();
            int i = -1;
            if (absoluteAdapterPosition >= 0) {
                Grid grid = this.mGrid;
                Objects.requireNonNull(grid);
                Grid.Location location = grid.getLocation(absoluteAdapterPosition);
                if (location != null) {
                    i = location.row;
                }
            }
            if (i >= 0) {
                Grid grid2 = this.mGrid;
                Objects.requireNonNull(grid2);
                int i2 = absoluteAdapterPosition / grid2.mNumRows;
                if (this.mOrientation == 0) {
                    accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, i2, 1, false));
                } else {
                    accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i2, 1, i, 1, false));
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ef  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(androidx.recyclerview.widget.RecyclerView.Recycler r7, androidx.recyclerview.widget.RecyclerView.State r8, int r9, int r10) {
        /*
            r6 = this;
            r6.saveContext(r7, r8)
            int r7 = r6.mOrientation
            if (r7 != 0) goto L_0x001c
            int r7 = android.view.View.MeasureSpec.getSize(r9)
            int r8 = android.view.View.MeasureSpec.getSize(r10)
            int r9 = android.view.View.MeasureSpec.getMode(r10)
            int r10 = r6.getPaddingTop()
            int r0 = r6.getPaddingBottom()
            goto L_0x0030
        L_0x001c:
            int r8 = android.view.View.MeasureSpec.getSize(r9)
            int r7 = android.view.View.MeasureSpec.getSize(r10)
            int r9 = android.view.View.MeasureSpec.getMode(r9)
            int r10 = r6.getPaddingLeft()
            int r0 = r6.getPaddingRight()
        L_0x0030:
            int r0 = r0 + r10
            r6.mMaxSizeSecondary = r8
            int r10 = r6.mRowSizeSecondaryRequested
            r1 = -2
            java.lang.String r2 = "wrong spec"
            r3 = 1073741824(0x40000000, float:2.0)
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = 1
            if (r10 != r1) goto L_0x0085
            int r8 = r6.mNumRowsRequested
            if (r8 != 0) goto L_0x0045
            r8 = r5
        L_0x0045:
            r6.mNumRows = r8
            r10 = 0
            r6.mFixedRowSizeSecondary = r10
            int[] r10 = r6.mRowSizeSecondary
            if (r10 == 0) goto L_0x0051
            int r10 = r10.length
            if (r10 == r8) goto L_0x0055
        L_0x0051:
            int[] r8 = new int[r8]
            r6.mRowSizeSecondary = r8
        L_0x0055:
            androidx.recyclerview.widget.RecyclerView$State r8 = r6.mState
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.mInPreLayout
            if (r8 == 0) goto L_0x0061
            r6.updatePositionDeltaInPreLayout()
        L_0x0061:
            r6.processRowSizeSecondary(r5)
            if (r9 == r4) goto L_0x0079
            if (r9 == 0) goto L_0x0074
            if (r9 != r3) goto L_0x006e
            int r8 = r6.mMaxSizeSecondary
            goto L_0x00e5
        L_0x006e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r6.<init>(r2)
            throw r6
        L_0x0074:
            int r8 = r6.getSizeSecondary()
            goto L_0x00a5
        L_0x0079:
            int r8 = r6.getSizeSecondary()
            int r8 = r8 + r0
            int r9 = r6.mMaxSizeSecondary
            int r8 = java.lang.Math.min(r8, r9)
            goto L_0x00e5
        L_0x0085:
            if (r9 == r4) goto L_0x00a7
            if (r9 == 0) goto L_0x0092
            if (r9 != r3) goto L_0x008c
            goto L_0x00a7
        L_0x008c:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r6.<init>(r2)
            throw r6
        L_0x0092:
            if (r10 != 0) goto L_0x0096
            int r10 = r8 - r0
        L_0x0096:
            r6.mFixedRowSizeSecondary = r10
            int r8 = r6.mNumRowsRequested
            if (r8 != 0) goto L_0x009d
            r8 = r5
        L_0x009d:
            r6.mNumRows = r8
            int r10 = r10 * r8
            int r9 = r6.mSpacingSecondary
            int r8 = r8 - r5
            int r8 = r8 * r9
            int r8 = r8 + r10
        L_0x00a5:
            int r8 = r8 + r0
            goto L_0x00e5
        L_0x00a7:
            int r1 = r6.mNumRowsRequested
            if (r1 != 0) goto L_0x00b4
            if (r10 != 0) goto L_0x00b4
            r6.mNumRows = r5
            int r10 = r8 - r0
            r6.mFixedRowSizeSecondary = r10
            goto L_0x00d5
        L_0x00b4:
            if (r1 != 0) goto L_0x00c1
            r6.mFixedRowSizeSecondary = r10
            int r1 = r6.mSpacingSecondary
            int r2 = r8 + r1
            int r10 = r10 + r1
            int r2 = r2 / r10
            r6.mNumRows = r2
            goto L_0x00d5
        L_0x00c1:
            if (r10 != 0) goto L_0x00d1
            r6.mNumRows = r1
            int r10 = r8 - r0
            int r2 = r6.mSpacingSecondary
            int r3 = r1 + -1
            int r3 = r3 * r2
            int r10 = r10 - r3
            int r10 = r10 / r1
            r6.mFixedRowSizeSecondary = r10
            goto L_0x00d5
        L_0x00d1:
            r6.mNumRows = r1
            r6.mFixedRowSizeSecondary = r10
        L_0x00d5:
            if (r9 != r4) goto L_0x00e5
            int r9 = r6.mFixedRowSizeSecondary
            int r10 = r6.mNumRows
            int r9 = r9 * r10
            int r1 = r6.mSpacingSecondary
            int r10 = r10 - r5
            int r10 = r10 * r1
            int r10 = r10 + r9
            int r10 = r10 + r0
            if (r10 >= r8) goto L_0x00e5
            r8 = r10
        L_0x00e5:
            int r9 = r6.mOrientation
            if (r9 != 0) goto L_0x00ef
            androidx.recyclerview.widget.RecyclerView r9 = r6.mRecyclerView
            r9.setMeasuredDimension(r7, r8)
            goto L_0x00f4
        L_0x00ef:
            androidx.recyclerview.widget.RecyclerView r9 = r6.mRecyclerView
            r9.setMeasuredDimension(r8, r7)
        L_0x00f4:
            r6.leaveContext()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GridLayoutManager.onMeasure(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, int):void");
    }

    public final void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (childCount >= 0) {
                View childAt = getChildAt(childCount);
                removeViewAt(childCount);
                recycler.recycleView(childAt);
            } else {
                return;
            }
        }
    }

    public final void scrollToView(View view, View view2, boolean z, int i, int i2) {
        if ((this.mFlag & 64) == 0) {
            int adapterPositionByView = getAdapterPositionByView(view);
            int subPositionByView = getSubPositionByView(view, view2);
            if (!(adapterPositionByView == this.mFocusPosition && subPositionByView == this.mSubFocusPosition)) {
                this.mFocusPosition = adapterPositionByView;
                this.mSubFocusPosition = subPositionByView;
                this.mFocusPositionOffset = 0;
                if ((this.mFlag & 3) != 1) {
                    dispatchChildSelected();
                }
                if (this.mBaseGridView.isChildrenDrawingOrderEnabledInternal()) {
                    this.mBaseGridView.invalidate();
                }
            }
            if (view != null) {
                if (!view.hasFocus() && this.mBaseGridView.hasFocus()) {
                    view.requestFocus();
                }
                if ((this.mFlag & 131072) == 0 && z) {
                    return;
                }
                if (getScrollPosition(view, view2, sTwoInts) || i != 0 || i2 != 0) {
                    int[] iArr = sTwoInts;
                    int i3 = iArr[0] + i;
                    int i4 = iArr[1] + i2;
                    if ((this.mFlag & 3) == 1) {
                        scrollDirectionPrimary(i3);
                        scrollDirectionSecondary(i4);
                        return;
                    }
                    if (this.mOrientation != 0) {
                        int i5 = i3;
                        i3 = i4;
                        i4 = i5;
                    }
                    if (z) {
                        BaseGridView baseGridView = this.mBaseGridView;
                        Objects.requireNonNull(baseGridView);
                        baseGridView.smoothScrollBy$1(i3, i4, false);
                        return;
                    }
                    this.mBaseGridView.scrollBy(i3, i4);
                    dispatchChildSelectedAndPositioned();
                }
            }
        }
    }

    public final void updatePositionDeltaInPreLayout() {
        if (getChildCount() > 0) {
            Grid grid = this.mGrid;
            Objects.requireNonNull(grid);
            this.mPositionDeltaInPreLayout = grid.mFirstVisibleIndex - ((LayoutParams) getChildAt(0).getLayoutParams()).getViewLayoutPosition();
            return;
        }
        this.mPositionDeltaInPreLayout = 0;
    }

    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }
}
