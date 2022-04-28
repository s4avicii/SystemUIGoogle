package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

public class LinearLayoutManager extends RecyclerView.LayoutManager implements ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider {
    public final AnchorInfo mAnchorInfo;
    public int mInitialPrefetchItemCount;
    public boolean mLastStackFromEnd;
    public final LayoutChunkResult mLayoutChunkResult;
    public LayoutState mLayoutState;
    public int mOrientation;
    public OrientationHelper mOrientationHelper;
    public SavedState mPendingSavedState;
    public int mPendingScrollPosition;
    public int mPendingScrollPositionOffset;
    public int[] mReusableIntPair;
    public boolean mReverseLayout;
    public boolean mShouldReverseLayout;
    public boolean mSmoothScrollbarEnabled;
    public boolean mStackFromEnd;

    public static class AnchorInfo {
        public int mCoordinate;
        public boolean mLayoutFromEnd;
        public OrientationHelper mOrientationHelper;
        public int mPosition;
        public boolean mValid;

        public final void reset() {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mValid = false;
        }

        public final void assignCoordinateFromPadding() {
            int i;
            if (this.mLayoutFromEnd) {
                i = this.mOrientationHelper.getEndAfterPadding();
            } else {
                i = this.mOrientationHelper.getStartAfterPadding();
            }
            this.mCoordinate = i;
        }

        public final void assignFromView(View view, int i) {
            if (this.mLayoutFromEnd) {
                this.mCoordinate = this.mOrientationHelper.getTotalSpaceChange() + this.mOrientationHelper.getDecoratedEnd(view);
            } else {
                this.mCoordinate = this.mOrientationHelper.getDecoratedStart(view);
            }
            this.mPosition = i;
        }

        public final void assignFromViewAndKeepVisibleRect(View view, int i) {
            int totalSpaceChange = this.mOrientationHelper.getTotalSpaceChange();
            if (totalSpaceChange >= 0) {
                assignFromView(view, i);
                return;
            }
            this.mPosition = i;
            if (this.mLayoutFromEnd) {
                int endAfterPadding = (this.mOrientationHelper.getEndAfterPadding() - totalSpaceChange) - this.mOrientationHelper.getDecoratedEnd(view);
                this.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - endAfterPadding;
                if (endAfterPadding > 0) {
                    int decoratedMeasurement = this.mCoordinate - this.mOrientationHelper.getDecoratedMeasurement(view);
                    int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
                    int min = decoratedMeasurement - (Math.min(this.mOrientationHelper.getDecoratedStart(view) - startAfterPadding, 0) + startAfterPadding);
                    if (min < 0) {
                        this.mCoordinate = Math.min(endAfterPadding, -min) + this.mCoordinate;
                        return;
                    }
                    return;
                }
                return;
            }
            int decoratedStart = this.mOrientationHelper.getDecoratedStart(view);
            int startAfterPadding2 = decoratedStart - this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = decoratedStart;
            if (startAfterPadding2 > 0) {
                int endAfterPadding2 = (this.mOrientationHelper.getEndAfterPadding() - Math.min(0, (this.mOrientationHelper.getEndAfterPadding() - totalSpaceChange) - this.mOrientationHelper.getDecoratedEnd(view))) - (this.mOrientationHelper.getDecoratedMeasurement(view) + decoratedStart);
                if (endAfterPadding2 < 0) {
                    this.mCoordinate -= Math.min(startAfterPadding2, -endAfterPadding2);
                }
            }
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("AnchorInfo{mPosition=");
            m.append(this.mPosition);
            m.append(", mCoordinate=");
            m.append(this.mCoordinate);
            m.append(", mLayoutFromEnd=");
            m.append(this.mLayoutFromEnd);
            m.append(", mValid=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.mValid, '}');
        }

        public AnchorInfo() {
            reset();
        }
    }

    public static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;
    }

    public static class LayoutState {
        public int mAvailable;
        public int mCurrentPosition;
        public int mExtraFillSpace = 0;
        public boolean mInfinite;
        public int mItemDirection;
        public int mLastScrollDelta;
        public int mLayoutDirection;
        public int mNoRecycleSpace = 0;
        public int mOffset;
        public boolean mRecycle = true;
        public List<RecyclerView.ViewHolder> mScrapList = null;
        public int mScrollingOffset;

        public final void assignPositionFromScrapList(View view) {
            int viewLayoutPosition;
            int size = this.mScrapList.size();
            View view2 = null;
            int i = Integer.MAX_VALUE;
            for (int i2 = 0; i2 < size; i2++) {
                View view3 = this.mScrapList.get(i2).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.isItemRemoved() && (viewLayoutPosition = (layoutParams.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection) >= 0 && viewLayoutPosition < i) {
                    view2 = view3;
                    if (viewLayoutPosition == 0) {
                        break;
                    }
                    i = viewLayoutPosition;
                }
            }
            if (view2 == null) {
                this.mCurrentPosition = -1;
            } else {
                this.mCurrentPosition = ((RecyclerView.LayoutParams) view2.getLayoutParams()).getViewLayoutPosition();
            }
        }

        public final View next(RecyclerView.Recycler recycler) {
            List<RecyclerView.ViewHolder> list = this.mScrapList;
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    View view = this.mScrapList.get(i).itemView;
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    if (!layoutParams.isItemRemoved() && this.mCurrentPosition == layoutParams.getViewLayoutPosition()) {
                        assignPositionFromScrapList(view);
                        return view;
                    }
                }
                return null;
            }
            View viewForPosition = recycler.getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return viewForPosition;
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mAnchorLayoutFromEnd;
        public int mAnchorOffset;
        public int mAnchorPosition;

        public SavedState() {
        }

        public final int describeContents() {
            return 0;
        }

        public SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mAnchorOffset = parcel.readInt();
            this.mAnchorLayoutFromEnd = parcel.readInt() != 1 ? false : true;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mAnchorOffset);
            parcel.writeInt(this.mAnchorLayoutFromEnd ? 1 : 0);
        }

        public SavedState(SavedState savedState) {
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mAnchorOffset = savedState.mAnchorOffset;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
        }
    }

    public LinearLayoutManager() {
        this(1);
    }

    public final int convertFocusDirectionToLayoutDirection(int i) {
        if (i == 1) {
            return (this.mOrientation != 1 && isLayoutRTL()) ? 1 : -1;
        }
        if (i == 2) {
            return (this.mOrientation != 1 && isLayoutRTL()) ? -1 : 1;
        }
        if (i == 17) {
            return this.mOrientation == 0 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 33) {
            return this.mOrientation == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i != 66) {
            if (i != 130) {
                return Integer.MIN_VALUE;
            }
            if (this.mOrientation == 1) {
                return 1;
            }
            return Integer.MIN_VALUE;
        } else if (this.mOrientation == 0) {
            return 1;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public final boolean isAutoMeasureEnabled() {
        return true;
    }

    public void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo, int i) {
    }

    public final void onDetachedFromWindow(RecyclerView recyclerView) {
    }

    public void onLayoutCompleted(RecyclerView.State state) {
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mAnchorInfo.reset();
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll((String) null);
        if (this.mStackFromEnd != z) {
            this.mStackFromEnd = z;
            requestLayout();
        }
    }

    public LinearLayoutManager(int i) {
        this.mOrientation = 1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialPrefetchItemCount = 2;
        this.mReusableIntPair = new int[2];
        setOrientation(i);
        assertNotInLayoutOrScroll((String) null);
        if (this.mReverseLayout) {
            this.mReverseLayout = false;
            requestLayout();
        }
    }

    public final void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public final boolean canScrollHorizontally() {
        if (this.mOrientation == 0) {
            return true;
        }
        return false;
    }

    public final boolean canScrollVertically() {
        if (this.mOrientation == 1) {
            return true;
        }
        return false;
    }

    public final void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i3;
        if (this.mOrientation != 0) {
            i = i2;
        }
        if (getChildCount() != 0 && i != 0) {
            ensureLayoutState();
            if (i > 0) {
                i3 = 1;
            } else {
                i3 = -1;
            }
            updateLayoutState(i3, Math.abs(i), true, state);
            collectPrefetchPositionsForLayoutState(state, this.mLayoutState, layoutPrefetchRegistry);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void collectInitialPrefetchPositions(int r7, androidx.recyclerview.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry r8) {
        /*
            r6 = this;
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r0 = r6.mPendingSavedState
            r1 = 0
            r2 = -1
            r3 = 1
            if (r0 == 0) goto L_0x0013
            int r4 = r0.mAnchorPosition
            if (r4 < 0) goto L_0x000d
            r5 = r3
            goto L_0x000e
        L_0x000d:
            r5 = r1
        L_0x000e:
            if (r5 == 0) goto L_0x0013
            boolean r0 = r0.mAnchorLayoutFromEnd
            goto L_0x0022
        L_0x0013:
            r6.resolveShouldLayoutReverse()
            boolean r0 = r6.mShouldReverseLayout
            int r4 = r6.mPendingScrollPosition
            if (r4 != r2) goto L_0x0022
            if (r0 == 0) goto L_0x0021
            int r4 = r7 + -1
            goto L_0x0022
        L_0x0021:
            r4 = r1
        L_0x0022:
            if (r0 == 0) goto L_0x0025
            goto L_0x0026
        L_0x0025:
            r2 = r3
        L_0x0026:
            r0 = r1
        L_0x0027:
            int r3 = r6.mInitialPrefetchItemCount
            if (r0 >= r3) goto L_0x0039
            if (r4 < 0) goto L_0x0039
            if (r4 >= r7) goto L_0x0039
            r3 = r8
            androidx.recyclerview.widget.GapWorker$LayoutPrefetchRegistryImpl r3 = (androidx.recyclerview.widget.GapWorker.LayoutPrefetchRegistryImpl) r3
            r3.addPosition(r4, r1)
            int r4 = r4 + r2
            int r0 = r0 + 1
            goto L_0x0027
        L_0x0039:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.LinearLayoutManager.collectInitialPrefetchPositions(int, androidx.recyclerview.widget.RecyclerView$LayoutManager$LayoutPrefetchRegistry):void");
    }

    public void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = layoutState.mCurrentPosition;
        if (i >= 0 && i < state.getItemCount()) {
            ((GapWorker.LayoutPrefetchRegistryImpl) layoutPrefetchRegistry).addPosition(i, Math.max(0, layoutState.mScrollingOffset));
        }
    }

    public final void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = new LayoutState();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        if (r10.mInPreLayout == false) goto L_0x0059;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int fill(androidx.recyclerview.widget.RecyclerView.Recycler r8, androidx.recyclerview.widget.LinearLayoutManager.LayoutState r9, androidx.recyclerview.widget.RecyclerView.State r10, boolean r11) {
        /*
            r7 = this;
            int r0 = r9.mAvailable
            int r1 = r9.mScrollingOffset
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 == r2) goto L_0x0010
            if (r0 >= 0) goto L_0x000d
            int r1 = r1 + r0
            r9.mScrollingOffset = r1
        L_0x000d:
            r7.recycleByLayoutState(r8, r9)
        L_0x0010:
            int r1 = r9.mAvailable
            int r3 = r9.mExtraFillSpace
            int r1 = r1 + r3
            androidx.recyclerview.widget.LinearLayoutManager$LayoutChunkResult r3 = r7.mLayoutChunkResult
        L_0x0017:
            boolean r4 = r9.mInfinite
            if (r4 != 0) goto L_0x001d
            if (r1 <= 0) goto L_0x007a
        L_0x001d:
            int r4 = r9.mCurrentPosition
            r5 = 0
            if (r4 < 0) goto L_0x002a
            int r6 = r10.getItemCount()
            if (r4 >= r6) goto L_0x002a
            r4 = 1
            goto L_0x002b
        L_0x002a:
            r4 = r5
        L_0x002b:
            if (r4 == 0) goto L_0x007a
            java.util.Objects.requireNonNull(r3)
            r3.mConsumed = r5
            r3.mFinished = r5
            r3.mIgnoreConsumed = r5
            r3.mFocusable = r5
            r7.layoutChunk(r8, r10, r9, r3)
            boolean r4 = r3.mFinished
            if (r4 == 0) goto L_0x0040
            goto L_0x007a
        L_0x0040:
            int r4 = r9.mOffset
            int r5 = r3.mConsumed
            int r6 = r9.mLayoutDirection
            int r5 = r5 * r6
            int r5 = r5 + r4
            r9.mOffset = r5
            boolean r4 = r3.mIgnoreConsumed
            if (r4 == 0) goto L_0x0059
            java.util.List<androidx.recyclerview.widget.RecyclerView$ViewHolder> r4 = r9.mScrapList
            if (r4 != 0) goto L_0x0059
            java.util.Objects.requireNonNull(r10)
            boolean r4 = r10.mInPreLayout
            if (r4 != 0) goto L_0x0061
        L_0x0059:
            int r4 = r9.mAvailable
            int r5 = r3.mConsumed
            int r4 = r4 - r5
            r9.mAvailable = r4
            int r1 = r1 - r5
        L_0x0061:
            int r4 = r9.mScrollingOffset
            if (r4 == r2) goto L_0x0074
            int r5 = r3.mConsumed
            int r4 = r4 + r5
            r9.mScrollingOffset = r4
            int r5 = r9.mAvailable
            if (r5 >= 0) goto L_0x0071
            int r4 = r4 + r5
            r9.mScrollingOffset = r4
        L_0x0071:
            r7.recycleByLayoutState(r8, r9)
        L_0x0074:
            if (r11 == 0) goto L_0x0017
            boolean r4 = r3.mFocusable
            if (r4 == 0) goto L_0x0017
        L_0x007a:
            int r7 = r9.mAvailable
            int r0 = r0 - r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.LinearLayoutManager.fill(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.LinearLayoutManager$LayoutState, androidx.recyclerview.widget.RecyclerView$State, boolean):int");
    }

    public final View findFirstVisibleChildClosestToEnd(boolean z) {
        if (this.mShouldReverseLayout) {
            return findOneVisibleChild(0, getChildCount(), z);
        }
        return findOneVisibleChild(getChildCount() - 1, -1, z);
    }

    public final View findFirstVisibleChildClosestToStart(boolean z) {
        if (this.mShouldReverseLayout) {
            return findOneVisibleChild(getChildCount() - 1, -1, z);
        }
        return findOneVisibleChild(0, getChildCount(), z);
    }

    public View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int i2;
        boolean z3;
        boolean z4;
        ensureLayoutState();
        int childCount = getChildCount();
        int i3 = -1;
        if (z2) {
            i2 = getChildCount() - 1;
            i = -1;
        } else {
            i3 = childCount;
            i2 = 0;
            i = 1;
        }
        int itemCount = state.getItemCount();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        View view3 = null;
        while (i2 != i3) {
            View childAt = getChildAt(i2);
            int position = RecyclerView.LayoutManager.getPosition(childAt);
            int decoratedStart = this.mOrientationHelper.getDecoratedStart(childAt);
            int decoratedEnd = this.mOrientationHelper.getDecoratedEnd(childAt);
            if (position >= 0 && position < itemCount) {
                if (!((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (decoratedEnd > startAfterPadding || decoratedStart >= startAfterPadding) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (decoratedStart < endAfterPadding || decoratedEnd <= endAfterPadding) {
                        z4 = false;
                    } else {
                        z4 = true;
                    }
                    if (!z3 && !z4) {
                        return childAt;
                    }
                    if (z) {
                        if (!z4) {
                            if (view != null) {
                            }
                            view = childAt;
                        }
                    } else if (!z3) {
                        if (view != null) {
                        }
                        view = childAt;
                    }
                    view2 = childAt;
                } else if (view3 == null) {
                    view3 = childAt;
                }
            }
            i2 += i;
        }
        if (view != null) {
            return view;
        }
        if (view2 != null) {
            return view2;
        }
        return view3;
    }

    public final int fixLayoutEndGap(int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int endAfterPadding2 = this.mOrientationHelper.getEndAfterPadding() - i;
        if (endAfterPadding2 <= 0) {
            return 0;
        }
        int i2 = -scrollBy(-endAfterPadding2, recycler, state);
        int i3 = i + i2;
        if (!z || (endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - i3) <= 0) {
            return i2;
        }
        this.mOrientationHelper.offsetChildren(endAfterPadding);
        return endAfterPadding + i2;
    }

    public final int fixLayoutStartGap(int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int startAfterPadding2 = i - this.mOrientationHelper.getStartAfterPadding();
        if (startAfterPadding2 <= 0) {
            return 0;
        }
        int i2 = -scrollBy(startAfterPadding2, recycler, state);
        int i3 = i + i2;
        if (!z || (startAfterPadding = i3 - this.mOrientationHelper.getStartAfterPadding()) <= 0) {
            return i2;
        }
        this.mOrientationHelper.offsetChildren(-startAfterPadding);
        return i2 - startAfterPadding;
    }

    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public final View getChildClosestToEnd() {
        int i;
        if (this.mShouldReverseLayout) {
            i = 0;
        } else {
            i = getChildCount() - 1;
        }
        return getChildAt(i);
    }

    public final View getChildClosestToStart() {
        int i;
        if (this.mShouldReverseLayout) {
            i = getChildCount() - 1;
        } else {
            i = 0;
        }
        return getChildAt(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:137:0x0231  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0192  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayoutChildren(androidx.recyclerview.widget.RecyclerView.Recycler r18, androidx.recyclerview.widget.RecyclerView.State r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r3 = r0.mPendingSavedState
            r4 = -1
            if (r3 != 0) goto L_0x000f
            int r3 = r0.mPendingScrollPosition
            if (r3 == r4) goto L_0x0019
        L_0x000f:
            int r3 = r19.getItemCount()
            if (r3 != 0) goto L_0x0019
            r17.removeAndRecycleAllViews(r18)
            return
        L_0x0019:
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r3 = r0.mPendingSavedState
            r5 = 0
            r6 = 1
            if (r3 == 0) goto L_0x002a
            int r3 = r3.mAnchorPosition
            if (r3 < 0) goto L_0x0025
            r7 = r6
            goto L_0x0026
        L_0x0025:
            r7 = r5
        L_0x0026:
            if (r7 == 0) goto L_0x002a
            r0.mPendingScrollPosition = r3
        L_0x002a:
            r17.ensureLayoutState()
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mRecycle = r5
            r17.resolveShouldLayoutReverse()
            androidx.recyclerview.widget.RecyclerView r3 = r0.mRecyclerView
            if (r3 != 0) goto L_0x003a
        L_0x0038:
            r3 = 0
            goto L_0x0049
        L_0x003a:
            android.view.View r3 = r3.getFocusedChild()
            if (r3 == 0) goto L_0x0038
            androidx.recyclerview.widget.ChildHelper r8 = r0.mChildHelper
            boolean r8 = r8.isHidden(r3)
            if (r8 == 0) goto L_0x0049
            goto L_0x0038
        L_0x0049:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r8 = r0.mAnchorInfo
            boolean r9 = r8.mValid
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r9 == 0) goto L_0x0083
            int r9 = r0.mPendingScrollPosition
            if (r9 != r4) goto L_0x0083
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r9 = r0.mPendingSavedState
            if (r9 == 0) goto L_0x005a
            goto L_0x0083
        L_0x005a:
            if (r3 == 0) goto L_0x0245
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getDecoratedStart(r3)
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getEndAfterPadding()
            if (r8 >= r9) goto L_0x0078
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getDecoratedEnd(r3)
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getStartAfterPadding()
            if (r8 > r9) goto L_0x0245
        L_0x0078:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r8 = r0.mAnchorInfo
            int r9 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r3)
            r8.assignFromViewAndKeepVisibleRect(r3, r9)
            goto L_0x0245
        L_0x0083:
            r8.reset()
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r3 = r0.mAnchorInfo
            boolean r8 = r0.mShouldReverseLayout
            boolean r9 = r0.mStackFromEnd
            r8 = r8 ^ r9
            r3.mLayoutFromEnd = r8
            java.util.Objects.requireNonNull(r19)
            boolean r8 = r2.mInPreLayout
            if (r8 != 0) goto L_0x018d
            int r8 = r0.mPendingScrollPosition
            if (r8 != r4) goto L_0x009c
            goto L_0x018d
        L_0x009c:
            if (r8 < 0) goto L_0x0189
            int r9 = r19.getItemCount()
            if (r8 < r9) goto L_0x00a6
            goto L_0x0189
        L_0x00a6:
            int r8 = r0.mPendingScrollPosition
            r3.mPosition = r8
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r9 = r0.mPendingSavedState
            if (r9 == 0) goto L_0x00db
            int r11 = r9.mAnchorPosition
            if (r11 < 0) goto L_0x00b4
            r11 = r6
            goto L_0x00b5
        L_0x00b4:
            r11 = r5
        L_0x00b5:
            if (r11 == 0) goto L_0x00db
            boolean r8 = r9.mAnchorLayoutFromEnd
            r3.mLayoutFromEnd = r8
            if (r8 == 0) goto L_0x00cc
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getEndAfterPadding()
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r9 = r0.mPendingSavedState
            int r9 = r9.mAnchorOffset
            int r8 = r8 - r9
            r3.mCoordinate = r8
            goto L_0x0187
        L_0x00cc:
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getStartAfterPadding()
            androidx.recyclerview.widget.LinearLayoutManager$SavedState r9 = r0.mPendingSavedState
            int r9 = r9.mAnchorOffset
            int r8 = r8 + r9
            r3.mCoordinate = r8
            goto L_0x0187
        L_0x00db:
            int r9 = r0.mPendingScrollPositionOffset
            if (r9 != r10) goto L_0x016a
            android.view.View r8 = r0.findViewByPosition(r8)
            if (r8 == 0) goto L_0x0148
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getDecoratedMeasurement(r8)
            androidx.recyclerview.widget.OrientationHelper r11 = r0.mOrientationHelper
            int r11 = r11.getTotalSpace()
            if (r9 <= r11) goto L_0x00f8
            r3.assignCoordinateFromPadding()
            goto L_0x0187
        L_0x00f8:
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getDecoratedStart(r8)
            androidx.recyclerview.widget.OrientationHelper r11 = r0.mOrientationHelper
            int r11 = r11.getStartAfterPadding()
            int r9 = r9 - r11
            if (r9 >= 0) goto L_0x0113
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getStartAfterPadding()
            r3.mCoordinate = r8
            r3.mLayoutFromEnd = r5
            goto L_0x0187
        L_0x0113:
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getEndAfterPadding()
            androidx.recyclerview.widget.OrientationHelper r11 = r0.mOrientationHelper
            int r11 = r11.getDecoratedEnd(r8)
            int r9 = r9 - r11
            if (r9 >= 0) goto L_0x012d
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getEndAfterPadding()
            r3.mCoordinate = r8
            r3.mLayoutFromEnd = r6
            goto L_0x0187
        L_0x012d:
            boolean r9 = r3.mLayoutFromEnd
            if (r9 == 0) goto L_0x013f
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r8 = r9.getDecoratedEnd(r8)
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getTotalSpaceChange()
            int r9 = r9 + r8
            goto L_0x0145
        L_0x013f:
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getDecoratedStart(r8)
        L_0x0145:
            r3.mCoordinate = r9
            goto L_0x0187
        L_0x0148:
            int r8 = r17.getChildCount()
            if (r8 <= 0) goto L_0x0166
            android.view.View r8 = r0.getChildAt(r5)
            int r8 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r8)
            int r9 = r0.mPendingScrollPosition
            if (r9 >= r8) goto L_0x015c
            r8 = r6
            goto L_0x015d
        L_0x015c:
            r8 = r5
        L_0x015d:
            boolean r9 = r0.mShouldReverseLayout
            if (r8 != r9) goto L_0x0163
            r8 = r6
            goto L_0x0164
        L_0x0163:
            r8 = r5
        L_0x0164:
            r3.mLayoutFromEnd = r8
        L_0x0166:
            r3.assignCoordinateFromPadding()
            goto L_0x0187
        L_0x016a:
            boolean r8 = r0.mShouldReverseLayout
            r3.mLayoutFromEnd = r8
            if (r8 == 0) goto L_0x017c
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getEndAfterPadding()
            int r9 = r0.mPendingScrollPositionOffset
            int r8 = r8 - r9
            r3.mCoordinate = r8
            goto L_0x0187
        L_0x017c:
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getStartAfterPadding()
            int r9 = r0.mPendingScrollPositionOffset
            int r8 = r8 + r9
            r3.mCoordinate = r8
        L_0x0187:
            r8 = r6
            goto L_0x018e
        L_0x0189:
            r0.mPendingScrollPosition = r4
            r0.mPendingScrollPositionOffset = r10
        L_0x018d:
            r8 = r5
        L_0x018e:
            if (r8 == 0) goto L_0x0192
            goto L_0x0241
        L_0x0192:
            int r8 = r17.getChildCount()
            if (r8 != 0) goto L_0x019a
            goto L_0x022d
        L_0x019a:
            androidx.recyclerview.widget.RecyclerView r8 = r0.mRecyclerView
            if (r8 != 0) goto L_0x01a0
        L_0x019e:
            r8 = 0
            goto L_0x01af
        L_0x01a0:
            android.view.View r8 = r8.getFocusedChild()
            if (r8 == 0) goto L_0x019e
            androidx.recyclerview.widget.ChildHelper r9 = r0.mChildHelper
            boolean r9 = r9.isHidden(r8)
            if (r9 == 0) goto L_0x01af
            goto L_0x019e
        L_0x01af:
            if (r8 == 0) goto L_0x01da
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.recyclerview.widget.RecyclerView$LayoutParams r9 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r9
            boolean r11 = r9.isItemRemoved()
            if (r11 != 0) goto L_0x01cf
            int r11 = r9.getViewLayoutPosition()
            if (r11 < 0) goto L_0x01cf
            int r9 = r9.getViewLayoutPosition()
            int r11 = r19.getItemCount()
            if (r9 >= r11) goto L_0x01cf
            r9 = r6
            goto L_0x01d0
        L_0x01cf:
            r9 = r5
        L_0x01d0:
            if (r9 == 0) goto L_0x01da
            int r9 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r8)
            r3.assignFromViewAndKeepVisibleRect(r8, r9)
            goto L_0x022b
        L_0x01da:
            boolean r8 = r0.mLastStackFromEnd
            boolean r9 = r0.mStackFromEnd
            if (r8 == r9) goto L_0x01e1
            goto L_0x022d
        L_0x01e1:
            boolean r8 = r3.mLayoutFromEnd
            android.view.View r8 = r0.findReferenceChild(r1, r2, r8, r9)
            if (r8 == 0) goto L_0x022d
            int r9 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r8)
            r3.assignFromView(r8, r9)
            boolean r9 = r2.mInPreLayout
            if (r9 != 0) goto L_0x022b
            boolean r9 = r17.supportsPredictiveItemAnimations()
            if (r9 == 0) goto L_0x022b
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getDecoratedStart(r8)
            androidx.recyclerview.widget.OrientationHelper r11 = r0.mOrientationHelper
            int r8 = r11.getDecoratedEnd(r8)
            androidx.recyclerview.widget.OrientationHelper r11 = r0.mOrientationHelper
            int r11 = r11.getStartAfterPadding()
            androidx.recyclerview.widget.OrientationHelper r12 = r0.mOrientationHelper
            int r12 = r12.getEndAfterPadding()
            if (r8 > r11) goto L_0x0218
            if (r9 >= r11) goto L_0x0218
            r13 = r6
            goto L_0x0219
        L_0x0218:
            r13 = r5
        L_0x0219:
            if (r9 < r12) goto L_0x021f
            if (r8 <= r12) goto L_0x021f
            r8 = r6
            goto L_0x0220
        L_0x021f:
            r8 = r5
        L_0x0220:
            if (r13 != 0) goto L_0x0224
            if (r8 == 0) goto L_0x022b
        L_0x0224:
            boolean r8 = r3.mLayoutFromEnd
            if (r8 == 0) goto L_0x0229
            r11 = r12
        L_0x0229:
            r3.mCoordinate = r11
        L_0x022b:
            r8 = r6
            goto L_0x022e
        L_0x022d:
            r8 = r5
        L_0x022e:
            if (r8 == 0) goto L_0x0231
            goto L_0x0241
        L_0x0231:
            r3.assignCoordinateFromPadding()
            boolean r8 = r0.mStackFromEnd
            if (r8 == 0) goto L_0x023e
            int r8 = r19.getItemCount()
            int r8 = r8 + r4
            goto L_0x023f
        L_0x023e:
            r8 = r5
        L_0x023f:
            r3.mPosition = r8
        L_0x0241:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r3 = r0.mAnchorInfo
            r3.mValid = r6
        L_0x0245:
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r8 = r3.mLastScrollDelta
            if (r8 < 0) goto L_0x024d
            r8 = r6
            goto L_0x024e
        L_0x024d:
            r8 = r4
        L_0x024e:
            r3.mLayoutDirection = r8
            int[] r3 = r0.mReusableIntPair
            r3[r5] = r5
            r3[r6] = r5
            r0.calculateExtraLayoutSpace(r2, r3)
            int[] r3 = r0.mReusableIntPair
            r3 = r3[r5]
            int r3 = java.lang.Math.max(r5, r3)
            androidx.recyclerview.widget.OrientationHelper r8 = r0.mOrientationHelper
            int r8 = r8.getStartAfterPadding()
            int r8 = r8 + r3
            int[] r3 = r0.mReusableIntPair
            r3 = r3[r6]
            int r3 = java.lang.Math.max(r5, r3)
            androidx.recyclerview.widget.OrientationHelper r9 = r0.mOrientationHelper
            int r9 = r9.getEndPadding()
            int r9 = r9 + r3
            java.util.Objects.requireNonNull(r19)
            boolean r3 = r2.mInPreLayout
            if (r3 == 0) goto L_0x02b5
            int r3 = r0.mPendingScrollPosition
            if (r3 == r4) goto L_0x02b5
            int r11 = r0.mPendingScrollPositionOffset
            if (r11 == r10) goto L_0x02b5
            android.view.View r3 = r0.findViewByPosition(r3)
            if (r3 == 0) goto L_0x02b5
            boolean r10 = r0.mShouldReverseLayout
            if (r10 == 0) goto L_0x02a0
            androidx.recyclerview.widget.OrientationHelper r10 = r0.mOrientationHelper
            int r10 = r10.getEndAfterPadding()
            androidx.recyclerview.widget.OrientationHelper r11 = r0.mOrientationHelper
            int r3 = r11.getDecoratedEnd(r3)
            int r10 = r10 - r3
            int r3 = r0.mPendingScrollPositionOffset
            goto L_0x02af
        L_0x02a0:
            androidx.recyclerview.widget.OrientationHelper r10 = r0.mOrientationHelper
            int r3 = r10.getDecoratedStart(r3)
            androidx.recyclerview.widget.OrientationHelper r10 = r0.mOrientationHelper
            int r10 = r10.getStartAfterPadding()
            int r3 = r3 - r10
            int r10 = r0.mPendingScrollPositionOffset
        L_0x02af:
            int r10 = r10 - r3
            if (r10 <= 0) goto L_0x02b4
            int r8 = r8 + r10
            goto L_0x02b5
        L_0x02b4:
            int r9 = r9 - r10
        L_0x02b5:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r3 = r0.mAnchorInfo
            boolean r10 = r3.mLayoutFromEnd
            if (r10 == 0) goto L_0x02c0
            boolean r10 = r0.mShouldReverseLayout
            if (r10 == 0) goto L_0x02c4
            goto L_0x02c6
        L_0x02c0:
            boolean r10 = r0.mShouldReverseLayout
            if (r10 == 0) goto L_0x02c6
        L_0x02c4:
            r10 = r4
            goto L_0x02c7
        L_0x02c6:
            r10 = r6
        L_0x02c7:
            r0.onAnchorReady(r1, r2, r3, r10)
            r17.detachAndScrapAttachedViews(r18)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            androidx.recyclerview.widget.OrientationHelper r10 = r0.mOrientationHelper
            int r10 = r10.getMode()
            if (r10 != 0) goto L_0x02e1
            androidx.recyclerview.widget.OrientationHelper r10 = r0.mOrientationHelper
            int r10 = r10.getEnd()
            if (r10 != 0) goto L_0x02e1
            r10 = r6
            goto L_0x02e2
        L_0x02e1:
            r10 = r5
        L_0x02e2:
            r3.mInfinite = r10
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            java.util.Objects.requireNonNull(r3)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mNoRecycleSpace = r5
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r3 = r0.mAnchorInfo
            boolean r10 = r3.mLayoutFromEnd
            if (r10 == 0) goto L_0x033a
            int r10 = r3.mPosition
            int r3 = r3.mCoordinate
            r0.updateLayoutStateToFillStart(r10, r3)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mExtraFillSpace = r8
            r0.fill(r1, r3, r2, r5)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r8 = r3.mOffset
            int r10 = r3.mCurrentPosition
            int r3 = r3.mAvailable
            if (r3 <= 0) goto L_0x030c
            int r9 = r9 + r3
        L_0x030c:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r3 = r0.mAnchorInfo
            int r11 = r3.mPosition
            int r3 = r3.mCoordinate
            r0.updateLayoutStateToFillEnd(r11, r3)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mExtraFillSpace = r9
            int r9 = r3.mCurrentPosition
            int r11 = r3.mItemDirection
            int r9 = r9 + r11
            r3.mCurrentPosition = r9
            r0.fill(r1, r3, r2, r5)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r9 = r3.mOffset
            int r3 = r3.mAvailable
            if (r3 <= 0) goto L_0x0380
            r0.updateLayoutStateToFillStart(r10, r8)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r8 = r0.mLayoutState
            r8.mExtraFillSpace = r3
            r0.fill(r1, r8, r2, r5)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r8 = r3.mOffset
            goto L_0x0380
        L_0x033a:
            int r10 = r3.mPosition
            int r3 = r3.mCoordinate
            r0.updateLayoutStateToFillEnd(r10, r3)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mExtraFillSpace = r9
            r0.fill(r1, r3, r2, r5)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r9 = r3.mOffset
            int r10 = r3.mCurrentPosition
            int r3 = r3.mAvailable
            if (r3 <= 0) goto L_0x0353
            int r8 = r8 + r3
        L_0x0353:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r3 = r0.mAnchorInfo
            int r11 = r3.mPosition
            int r3 = r3.mCoordinate
            r0.updateLayoutStateToFillStart(r11, r3)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mExtraFillSpace = r8
            int r8 = r3.mCurrentPosition
            int r11 = r3.mItemDirection
            int r8 = r8 + r11
            r3.mCurrentPosition = r8
            r0.fill(r1, r3, r2, r5)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r8 = r3.mOffset
            int r3 = r3.mAvailable
            if (r3 <= 0) goto L_0x0380
            r0.updateLayoutStateToFillEnd(r10, r9)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r9 = r0.mLayoutState
            r9.mExtraFillSpace = r3
            r0.fill(r1, r9, r2, r5)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            int r9 = r3.mOffset
        L_0x0380:
            int r3 = r17.getChildCount()
            if (r3 <= 0) goto L_0x03a4
            boolean r3 = r0.mShouldReverseLayout
            boolean r10 = r0.mStackFromEnd
            r3 = r3 ^ r10
            if (r3 == 0) goto L_0x0398
            int r3 = r0.fixLayoutEndGap(r9, r1, r2, r6)
            int r8 = r8 + r3
            int r9 = r9 + r3
            int r3 = r0.fixLayoutStartGap(r8, r1, r2, r5)
            goto L_0x03a2
        L_0x0398:
            int r3 = r0.fixLayoutStartGap(r8, r1, r2, r6)
            int r8 = r8 + r3
            int r9 = r9 + r3
            int r3 = r0.fixLayoutEndGap(r9, r1, r2, r5)
        L_0x03a2:
            int r8 = r8 + r3
            int r9 = r9 + r3
        L_0x03a4:
            boolean r3 = r2.mRunPredictiveAnimations
            if (r3 == 0) goto L_0x0448
            int r3 = r17.getChildCount()
            if (r3 == 0) goto L_0x0448
            boolean r3 = r2.mInPreLayout
            if (r3 != 0) goto L_0x0448
            boolean r3 = r17.supportsPredictiveItemAnimations()
            if (r3 != 0) goto L_0x03ba
            goto L_0x0448
        L_0x03ba:
            java.util.Objects.requireNonNull(r18)
            java.util.List<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r1.mUnmodifiableAttachedScrap
            int r10 = r3.size()
            android.view.View r11 = r0.getChildAt(r5)
            int r11 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r11)
            r12 = r5
            r13 = r12
            r14 = r13
        L_0x03ce:
            if (r12 >= r10) goto L_0x0406
            java.lang.Object r15 = r3.get(r12)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r15 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r15
            boolean r16 = r15.isRemoved()
            if (r16 == 0) goto L_0x03dd
            goto L_0x0402
        L_0x03dd:
            int r6 = r15.getLayoutPosition()
            if (r6 >= r11) goto L_0x03e5
            r6 = 1
            goto L_0x03e6
        L_0x03e5:
            r6 = r5
        L_0x03e6:
            boolean r7 = r0.mShouldReverseLayout
            if (r6 == r7) goto L_0x03ec
            r6 = r4
            goto L_0x03ed
        L_0x03ec:
            r6 = 1
        L_0x03ed:
            if (r6 != r4) goto L_0x03f9
            androidx.recyclerview.widget.OrientationHelper r6 = r0.mOrientationHelper
            android.view.View r7 = r15.itemView
            int r6 = r6.getDecoratedMeasurement(r7)
            int r13 = r13 + r6
            goto L_0x0402
        L_0x03f9:
            androidx.recyclerview.widget.OrientationHelper r6 = r0.mOrientationHelper
            android.view.View r7 = r15.itemView
            int r6 = r6.getDecoratedMeasurement(r7)
            int r14 = r14 + r6
        L_0x0402:
            int r12 = r12 + 1
            r6 = 1
            goto L_0x03ce
        L_0x0406:
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r4 = r0.mLayoutState
            r4.mScrapList = r3
            if (r13 <= 0) goto L_0x0426
            android.view.View r3 = r17.getChildClosestToStart()
            int r3 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r3)
            r0.updateLayoutStateToFillStart(r3, r8)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mExtraFillSpace = r13
            r3.mAvailable = r5
            r4 = 0
            r3.assignPositionFromScrapList(r4)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r0.fill(r1, r3, r2, r5)
        L_0x0426:
            if (r14 <= 0) goto L_0x0443
            android.view.View r3 = r17.getChildClosestToEnd()
            int r3 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r3)
            r0.updateLayoutStateToFillEnd(r3, r9)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r3.mExtraFillSpace = r14
            r3.mAvailable = r5
            r4 = 0
            r3.assignPositionFromScrapList(r4)
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r3 = r0.mLayoutState
            r0.fill(r1, r3, r2, r5)
            goto L_0x0444
        L_0x0443:
            r4 = 0
        L_0x0444:
            androidx.recyclerview.widget.LinearLayoutManager$LayoutState r1 = r0.mLayoutState
            r1.mScrapList = r4
        L_0x0448:
            boolean r1 = r2.mInPreLayout
            if (r1 != 0) goto L_0x0458
            androidx.recyclerview.widget.OrientationHelper r1 = r0.mOrientationHelper
            java.util.Objects.requireNonNull(r1)
            int r2 = r1.getTotalSpace()
            r1.mLastTotalSpace = r2
            goto L_0x045d
        L_0x0458:
            androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo r1 = r0.mAnchorInfo
            r1.reset()
        L_0x045d:
            boolean r1 = r0.mStackFromEnd
            r0.mLastStackFromEnd = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.LinearLayoutManager.onLayoutChildren(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):void");
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mPendingSavedState = savedState;
            if (this.mPendingScrollPosition != -1) {
                Objects.requireNonNull(savedState);
                savedState.mAnchorPosition = -1;
            }
            requestLayout();
        }
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        if (getChildCount() > 0) {
            ensureLayoutState();
            boolean z = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            savedState2.mAnchorLayoutFromEnd = z;
            if (z) {
                View childClosestToEnd = getChildClosestToEnd();
                savedState2.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(childClosestToEnd);
                savedState2.mAnchorPosition = RecyclerView.LayoutManager.getPosition(childClosestToEnd);
            } else {
                View childClosestToStart = getChildClosestToStart();
                savedState2.mAnchorPosition = RecyclerView.LayoutManager.getPosition(childClosestToStart);
                savedState2.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(childClosestToStart) - this.mOrientationHelper.getStartAfterPadding();
            }
        } else {
            savedState2.mAnchorPosition = -1;
        }
        return savedState2;
    }

    public final void prepareForDrop(View view, View view2) {
        boolean z;
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        ensureLayoutState();
        resolveShouldLayoutReverse();
        int position = RecyclerView.LayoutManager.getPosition(view);
        int position2 = RecyclerView.LayoutManager.getPosition(view2);
        if (position < position2) {
            z = true;
        } else {
            z = true;
        }
        if (this.mShouldReverseLayout) {
            if (z) {
                scrollToPositionWithOffset(position2, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedMeasurement(view) + this.mOrientationHelper.getDecoratedStart(view2)));
                return;
            }
            scrollToPositionWithOffset(position2, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view2));
        } else if (z) {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.getDecoratedStart(view2));
        } else {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.getDecoratedEnd(view2) - this.mOrientationHelper.getDecoratedMeasurement(view));
        }
    }

    public final void recycleByLayoutState(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (layoutState.mRecycle && !layoutState.mInfinite) {
            int i = layoutState.mScrollingOffset;
            int i2 = layoutState.mNoRecycleSpace;
            if (layoutState.mLayoutDirection == -1) {
                int childCount = getChildCount();
                if (i >= 0) {
                    int end = (this.mOrientationHelper.getEnd() - i) + i2;
                    if (this.mShouldReverseLayout) {
                        for (int i3 = 0; i3 < childCount; i3++) {
                            View childAt = getChildAt(i3);
                            if (this.mOrientationHelper.getDecoratedStart(childAt) < end || this.mOrientationHelper.getTransformedStartWithDecoration(childAt) < end) {
                                recycleChildren(recycler, 0, i3);
                                return;
                            }
                        }
                        return;
                    }
                    int i4 = childCount - 1;
                    for (int i5 = i4; i5 >= 0; i5--) {
                        View childAt2 = getChildAt(i5);
                        if (this.mOrientationHelper.getDecoratedStart(childAt2) < end || this.mOrientationHelper.getTransformedStartWithDecoration(childAt2) < end) {
                            recycleChildren(recycler, i4, i5);
                            return;
                        }
                    }
                }
            } else if (i >= 0) {
                int i6 = i - i2;
                int childCount2 = getChildCount();
                if (this.mShouldReverseLayout) {
                    int i7 = childCount2 - 1;
                    for (int i8 = i7; i8 >= 0; i8--) {
                        View childAt3 = getChildAt(i8);
                        if (this.mOrientationHelper.getDecoratedEnd(childAt3) > i6 || this.mOrientationHelper.getTransformedEndWithDecoration(childAt3) > i6) {
                            recycleChildren(recycler, i7, i8);
                            return;
                        }
                    }
                    return;
                }
                for (int i9 = 0; i9 < childCount2; i9++) {
                    View childAt4 = getChildAt(i9);
                    if (this.mOrientationHelper.getDecoratedEnd(childAt4) > i6 || this.mOrientationHelper.getTransformedEndWithDecoration(childAt4) > i6) {
                        recycleChildren(recycler, 0, i9);
                        return;
                    }
                }
            }
        }
    }

    public final void recycleChildren(RecyclerView.Recycler recycler, int i, int i2) {
        if (i != i2) {
            if (i2 > i) {
                while (true) {
                    i2--;
                    if (i2 >= i) {
                        View childAt = getChildAt(i2);
                        removeViewAt(i2);
                        recycler.recycleView(childAt);
                    } else {
                        return;
                    }
                }
            } else {
                while (i > i2) {
                    View childAt2 = getChildAt(i);
                    removeViewAt(i);
                    recycler.recycleView(childAt2);
                    i--;
                }
            }
        }
    }

    public final void resolveShouldLayoutReverse() {
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout;
        } else {
            this.mShouldReverseLayout = !this.mReverseLayout;
        }
    }

    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public final void scrollToPosition(int i) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.mAnchorPosition = -1;
        }
        requestLayout();
    }

    public final void scrollToPositionWithOffset(int i, int i2) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = i2;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            Objects.requireNonNull(savedState);
            savedState.mAnchorPosition = -1;
        }
        requestLayout();
    }

    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public final void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll((String) null);
            if (i != this.mOrientation || this.mOrientationHelper == null) {
                OrientationHelper createOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
                this.mOrientationHelper = createOrientationHelper;
                this.mAnchorInfo.mOrientationHelper = createOrientationHelper;
                this.mOrientation = i;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("invalid orientation:", i));
    }

    public final boolean shouldMeasureTwice() {
        boolean z;
        if (!(this.mHeightMode == 1073741824 || this.mWidthMode == 1073741824)) {
            int childCount = getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    z = false;
                    break;
                }
                ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    z = true;
                    break;
                }
                i++;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
    }

    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd) {
            return true;
        }
        return false;
    }

    public final void updateLayoutState(int i, int i2, boolean z, RecyclerView.State state) {
        boolean z2;
        int i3;
        int i4;
        LayoutState layoutState = this.mLayoutState;
        int i5 = 1;
        boolean z3 = false;
        if (this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        layoutState.mInfinite = z2;
        this.mLayoutState.mLayoutDirection = i;
        int[] iArr = this.mReusableIntPair;
        iArr[0] = 0;
        iArr[1] = 0;
        calculateExtraLayoutSpace(state, iArr);
        int max = Math.max(0, this.mReusableIntPair[0]);
        int max2 = Math.max(0, this.mReusableIntPair[1]);
        if (i == 1) {
            z3 = true;
        }
        LayoutState layoutState2 = this.mLayoutState;
        if (z3) {
            i3 = max2;
        } else {
            i3 = max;
        }
        layoutState2.mExtraFillSpace = i3;
        if (!z3) {
            max = max2;
        }
        layoutState2.mNoRecycleSpace = max;
        if (z3) {
            layoutState2.mExtraFillSpace = this.mOrientationHelper.getEndPadding() + i3;
            View childClosestToEnd = getChildClosestToEnd();
            LayoutState layoutState3 = this.mLayoutState;
            if (this.mShouldReverseLayout) {
                i5 = -1;
            }
            layoutState3.mItemDirection = i5;
            int position = RecyclerView.LayoutManager.getPosition(childClosestToEnd);
            LayoutState layoutState4 = this.mLayoutState;
            layoutState3.mCurrentPosition = position + layoutState4.mItemDirection;
            layoutState4.mOffset = this.mOrientationHelper.getDecoratedEnd(childClosestToEnd);
            i4 = this.mOrientationHelper.getDecoratedEnd(childClosestToEnd) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            View childClosestToStart = getChildClosestToStart();
            LayoutState layoutState5 = this.mLayoutState;
            layoutState5.mExtraFillSpace = this.mOrientationHelper.getStartAfterPadding() + layoutState5.mExtraFillSpace;
            LayoutState layoutState6 = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                i5 = -1;
            }
            layoutState6.mItemDirection = i5;
            int position2 = RecyclerView.LayoutManager.getPosition(childClosestToStart);
            LayoutState layoutState7 = this.mLayoutState;
            layoutState6.mCurrentPosition = position2 + layoutState7.mItemDirection;
            layoutState7.mOffset = this.mOrientationHelper.getDecoratedStart(childClosestToStart);
            i4 = (-this.mOrientationHelper.getDecoratedStart(childClosestToStart)) + this.mOrientationHelper.getStartAfterPadding();
        }
        LayoutState layoutState8 = this.mLayoutState;
        layoutState8.mAvailable = i2;
        if (z) {
            layoutState8.mAvailable = i2 - i4;
        }
        layoutState8.mScrollingOffset = i4;
    }

    public final void updateLayoutStateToFillEnd(int i, int i2) {
        int i3;
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - i2;
        LayoutState layoutState = this.mLayoutState;
        if (this.mShouldReverseLayout) {
            i3 = -1;
        } else {
            i3 = 1;
        }
        layoutState.mItemDirection = i3;
        layoutState.mCurrentPosition = i;
        layoutState.mLayoutDirection = 1;
        layoutState.mOffset = i2;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    public final void updateLayoutStateToFillStart(int i, int i2) {
        int i3;
        this.mLayoutState.mAvailable = i2 - this.mOrientationHelper.getStartAfterPadding();
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = i;
        if (this.mShouldReverseLayout) {
            i3 = 1;
        } else {
            i3 = -1;
        }
        layoutState.mItemDirection = i3;
        layoutState.mLayoutDirection = -1;
        layoutState.mOffset = i2;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    public void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
        boolean z;
        int i;
        int i2;
        Objects.requireNonNull(state);
        if (state.mTargetPosition != -1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            i = this.mOrientationHelper.getTotalSpace();
        } else {
            i = 0;
        }
        if (this.mLayoutState.mLayoutDirection == -1) {
            i2 = 0;
        } else {
            i2 = i;
            i = 0;
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return ScrollbarHelper.computeScrollExtent(state, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    public final int computeScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return ScrollbarHelper.computeScrollOffset(state, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public final int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return ScrollbarHelper.computeScrollRange(state, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    public final PointF computeScrollVectorForPosition(int i) {
        if (getChildCount() == 0) {
            return null;
        }
        boolean z = false;
        int i2 = 1;
        if (i < RecyclerView.LayoutManager.getPosition(getChildAt(0))) {
            z = true;
        }
        if (z != this.mShouldReverseLayout) {
            i2 = -1;
        }
        if (this.mOrientation == 0) {
            return new PointF((float) i2, 0.0f);
        }
        return new PointF(0.0f, (float) i2);
    }

    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    public int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final int findFirstVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), false);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
    }

    public final int findLastVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, false);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
    }

    public final View findOnePartiallyOrCompletelyInvisibleChild(int i, int i2) {
        char c;
        int i3;
        int i4;
        ensureLayoutState();
        if (i2 > i) {
            c = 1;
        } else if (i2 < i) {
            c = 65535;
        } else {
            c = 0;
        }
        if (c == 0) {
            return getChildAt(i);
        }
        if (this.mOrientationHelper.getDecoratedStart(getChildAt(i)) < this.mOrientationHelper.getStartAfterPadding()) {
            i4 = 16644;
            i3 = 16388;
        } else {
            i4 = 4161;
            i3 = 4097;
        }
        if (this.mOrientation == 0) {
            return this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(i, i2, i4, i3);
        }
        return this.mVerticalBoundCheck.findOneViewWithinBoundFlags(i, i2, i4, i3);
    }

    public final View findOneVisibleChild(int i, int i2, boolean z) {
        int i3;
        ensureLayoutState();
        if (z) {
            i3 = 24579;
        } else {
            i3 = 320;
        }
        if (this.mOrientation == 0) {
            return this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(i, i2, i3, 320);
        }
        return this.mVerticalBoundCheck.findOneViewWithinBoundFlags(i, i2, i3, 320);
    }

    public final View findViewByPosition(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i - RecyclerView.LayoutManager.getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (RecyclerView.LayoutManager.getPosition(childAt) == i) {
                return childAt;
            }
        }
        return super.findViewByPosition(i);
    }

    public final boolean isLayoutRTL() {
        if (getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    public void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z;
        boolean z2;
        View next = layoutState.next(recycler);
        if (next == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) next.getLayoutParams();
        if (layoutState.mScrapList == null) {
            boolean z3 = this.mShouldReverseLayout;
            if (layoutState.mLayoutDirection == -1) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z3 == z2) {
                addViewInt(next, -1, false);
            } else {
                addViewInt(next, 0, false);
            }
        } else {
            boolean z4 = this.mShouldReverseLayout;
            if (layoutState.mLayoutDirection == -1) {
                z = true;
            } else {
                z = false;
            }
            if (z4 == z) {
                addViewInt(next, -1, true);
            } else {
                addViewInt(next, 0, true);
            }
        }
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) next.getLayoutParams();
        Rect itemDecorInsetsForChild = this.mRecyclerView.getItemDecorInsetsForChild(next);
        int childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(this.mWidth, this.mWidthMode, getPaddingRight() + getPaddingLeft() + layoutParams2.leftMargin + layoutParams2.rightMargin + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right + 0, layoutParams2.width, canScrollHorizontally());
        int childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(this.mHeight, this.mHeightMode, getPaddingBottom() + getPaddingTop() + layoutParams2.topMargin + layoutParams2.bottomMargin + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom + 0, layoutParams2.height, canScrollVertically());
        if (shouldMeasureChild(next, childMeasureSpec, childMeasureSpec2, layoutParams2)) {
            next.measure(childMeasureSpec, childMeasureSpec2);
        }
        layoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(next);
        if (this.mOrientation == 1) {
            if (isLayoutRTL()) {
                i4 = this.mWidth - getPaddingRight();
                i2 = i4 - this.mOrientationHelper.getDecoratedMeasurementInOther(next);
            } else {
                int paddingLeft = getPaddingLeft();
                int i7 = paddingLeft;
                i4 = this.mOrientationHelper.getDecoratedMeasurementInOther(next) + paddingLeft;
                i2 = i7;
            }
            if (layoutState.mLayoutDirection == -1) {
                i = layoutState.mOffset;
                i3 = i - layoutChunkResult.mConsumed;
            } else {
                i3 = layoutState.mOffset;
                i = layoutChunkResult.mConsumed + i3;
            }
        } else {
            i3 = getPaddingTop();
            int decoratedMeasurementInOther = this.mOrientationHelper.getDecoratedMeasurementInOther(next) + i3;
            if (layoutState.mLayoutDirection == -1) {
                i6 = layoutState.mOffset;
                i5 = i6 - layoutChunkResult.mConsumed;
            } else {
                i5 = layoutState.mOffset;
                i6 = layoutChunkResult.mConsumed + i5;
            }
            int i8 = i5;
            i = decoratedMeasurementInOther;
            i2 = i8;
        }
        RecyclerView.LayoutManager.layoutDecoratedWithMargins(next, i2, i3, i4, i);
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = next.hasFocusable();
    }

    public View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int convertFocusDirectionToLayoutDirection;
        View view2;
        View view3;
        resolveShouldLayoutReverse();
        if (getChildCount() == 0 || (convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i)) == Integer.MIN_VALUE) {
            return null;
        }
        ensureLayoutState();
        updateLayoutState(convertFocusDirectionToLayoutDirection, (int) (((float) this.mOrientationHelper.getTotalSpace()) * 0.33333334f), false, state);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mScrollingOffset = Integer.MIN_VALUE;
        layoutState.mRecycle = false;
        fill(recycler, layoutState, state, true);
        if (convertFocusDirectionToLayoutDirection == -1) {
            if (this.mShouldReverseLayout) {
                view2 = findOnePartiallyOrCompletelyInvisibleChild(getChildCount() - 1, -1);
            } else {
                view2 = findOnePartiallyOrCompletelyInvisibleChild(0, getChildCount());
            }
        } else if (this.mShouldReverseLayout) {
            view2 = findOnePartiallyOrCompletelyInvisibleChild(0, getChildCount());
        } else {
            view2 = findOnePartiallyOrCompletelyInvisibleChild(getChildCount() - 1, -1);
        }
        if (convertFocusDirectionToLayoutDirection == -1) {
            view3 = getChildClosestToStart();
        } else {
            view3 = getChildClosestToEnd();
        }
        if (!view3.hasFocusable()) {
            return view2;
        }
        if (view2 == null) {
            return null;
        }
        return view3;
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(findLastVisibleItemPosition());
        }
    }

    public final int scrollBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i2;
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        ensureLayoutState();
        this.mLayoutState.mRecycle = true;
        if (i > 0) {
            i2 = 1;
        } else {
            i2 = -1;
        }
        int abs = Math.abs(i);
        updateLayoutState(i2, abs, true, state);
        LayoutState layoutState = this.mLayoutState;
        int fill = fill(recycler, layoutState, state, false) + layoutState.mScrollingOffset;
        if (fill < 0) {
            return 0;
        }
        if (abs > fill) {
            i = i2 * fill;
        }
        this.mOrientationHelper.offsetChildren(-i);
        this.mLayoutState.mLastScrollDelta = i;
        return i;
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mOrientation = 1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        this.mLayoutChunkResult = new LayoutChunkResult();
        this.mInitialPrefetchItemCount = 2;
        this.mReusableIntPair = new int[2];
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        setOrientation(properties.orientation);
        boolean z = properties.reverseLayout;
        assertNotInLayoutOrScroll((String) null);
        if (z != this.mReverseLayout) {
            this.mReverseLayout = z;
            requestLayout();
        }
        setStackFromEnd(properties.stackFromEnd);
    }
}
