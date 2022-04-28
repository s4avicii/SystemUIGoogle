package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public final AnchorInfo mAnchorInfo;
    public final C03421 mCheckForGapsRunnable;
    public int mGapStrategy;
    public boolean mLastLayoutFromEnd;
    public boolean mLastLayoutRTL;
    public final LayoutState mLayoutState;
    public LazySpanLookup mLazySpanLookup;
    public int mOrientation;
    public SavedState mPendingSavedState;
    public int mPendingScrollPosition;
    public int mPendingScrollPositionOffset;
    public int[] mPrefetchDistances;
    public OrientationHelper mPrimaryOrientation;
    public BitSet mRemainingSpans;
    public boolean mReverseLayout;
    public OrientationHelper mSecondaryOrientation;
    public boolean mShouldReverseLayout;
    public int mSizePerSpan;
    public boolean mSmoothScrollbarEnabled;
    public int mSpanCount = -1;
    public Span[] mSpans;
    public final Rect mTmpRect;

    public class AnchorInfo {
        public boolean mInvalidateOffsets;
        public boolean mLayoutFromEnd;
        public int mOffset;
        public int mPosition;
        public int[] mSpanReferenceLines;
        public boolean mValid;

        public final void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            int[] iArr = this.mSpanReferenceLines;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }

        public AnchorInfo() {
            reset();
        }
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        public Span mSpan;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public static class LazySpanLookup {
        public int[] mData;
        public List<FullSpanItem> mFullSpanItems;

        @SuppressLint({"BanParcelableUsage"})
        public static class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>() {
                public final Object createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                public final Object[] newArray(int i) {
                    return new FullSpanItem[i];
                }
            };
            public int mGapDir;
            public int[] mGapPerSpan;
            public boolean mHasUnwantedGapAfter;
            public int mPosition;

            public FullSpanItem(Parcel parcel) {
                this.mPosition = parcel.readInt();
                this.mGapDir = parcel.readInt();
                this.mHasUnwantedGapAfter = parcel.readInt() != 1 ? false : true;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    int[] iArr = new int[readInt];
                    this.mGapPerSpan = iArr;
                    parcel.readIntArray(iArr);
                }
            }

            public final int describeContents() {
                return 0;
            }

            public final String toString() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("FullSpanItem{mPosition=");
                m.append(this.mPosition);
                m.append(", mGapDir=");
                m.append(this.mGapDir);
                m.append(", mHasUnwantedGapAfter=");
                m.append(this.mHasUnwantedGapAfter);
                m.append(", mGapPerSpan=");
                m.append(Arrays.toString(this.mGapPerSpan));
                m.append('}');
                return m.toString();
            }

            public final void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.mPosition);
                parcel.writeInt(this.mGapDir);
                parcel.writeInt(this.mHasUnwantedGapAfter ? 1 : 0);
                int[] iArr = this.mGapPerSpan;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(iArr.length);
                parcel.writeIntArray(this.mGapPerSpan);
            }

            public FullSpanItem() {
            }
        }

        public final void clear() {
            int[] iArr = this.mData;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.mFullSpanItems = null;
        }

        public final void ensureSize(int i) {
            int[] iArr = this.mData;
            if (iArr == null) {
                int[] iArr2 = new int[(Math.max(i, 10) + 1)];
                this.mData = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i >= iArr.length) {
                int length = iArr.length;
                while (length <= i) {
                    length *= 2;
                }
                int[] iArr3 = new int[length];
                this.mData = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                int[] iArr4 = this.mData;
                Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:28:0x0061  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x006b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int invalidateAfter(int r6) {
            /*
                r5 = this;
                int[] r0 = r5.mData
                r1 = -1
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                int r0 = r0.length
                if (r6 < r0) goto L_0x000a
                return r1
            L_0x000a:
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r5.mFullSpanItems
                if (r0 != 0) goto L_0x000f
                goto L_0x005e
            L_0x000f:
                r2 = 0
                if (r0 != 0) goto L_0x0013
                goto L_0x002b
            L_0x0013:
                int r0 = r0.size()
                int r0 = r0 + r1
            L_0x0018:
                if (r0 < 0) goto L_0x002b
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r3 = r5.mFullSpanItems
                java.lang.Object r3 = r3.get(r0)
                androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r3 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem) r3
                int r4 = r3.mPosition
                if (r4 != r6) goto L_0x0028
                r2 = r3
                goto L_0x002b
            L_0x0028:
                int r0 = r0 + -1
                goto L_0x0018
            L_0x002b:
                if (r2 == 0) goto L_0x0032
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r5.mFullSpanItems
                r0.remove(r2)
            L_0x0032:
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r5.mFullSpanItems
                int r0 = r0.size()
                r2 = 0
            L_0x0039:
                if (r2 >= r0) goto L_0x004b
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r3 = r5.mFullSpanItems
                java.lang.Object r3 = r3.get(r2)
                androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r3 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem) r3
                int r3 = r3.mPosition
                if (r3 < r6) goto L_0x0048
                goto L_0x004c
            L_0x0048:
                int r2 = r2 + 1
                goto L_0x0039
            L_0x004b:
                r2 = r1
            L_0x004c:
                if (r2 == r1) goto L_0x005e
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r5.mFullSpanItems
                java.lang.Object r0 = r0.get(r2)
                androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r0 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem) r0
                java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r3 = r5.mFullSpanItems
                r3.remove(r2)
                int r0 = r0.mPosition
                goto L_0x005f
            L_0x005e:
                r0 = r1
            L_0x005f:
                if (r0 != r1) goto L_0x006b
                int[] r0 = r5.mData
                int r2 = r0.length
                java.util.Arrays.fill(r0, r6, r2, r1)
                int[] r5 = r5.mData
                int r5 = r5.length
                return r5
            L_0x006b:
                int r0 = r0 + 1
                int[] r2 = r5.mData
                int r2 = r2.length
                int r0 = java.lang.Math.min(r0, r2)
                int[] r5 = r5.mData
                java.util.Arrays.fill(r5, r6, r0, r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.invalidateAfter(int):int");
        }

        public final void offsetForAddition(int i, int i2) {
            int[] iArr = this.mData;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                ensureSize(i3);
                int[] iArr2 = this.mData;
                System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
                Arrays.fill(this.mData, i, i3, -1);
                List<FullSpanItem> list = this.mFullSpanItems;
                if (list != null) {
                    for (int size = list.size() - 1; size >= 0; size--) {
                        FullSpanItem fullSpanItem = this.mFullSpanItems.get(size);
                        int i4 = fullSpanItem.mPosition;
                        if (i4 >= i) {
                            fullSpanItem.mPosition = i4 + i2;
                        }
                    }
                }
            }
        }

        public final void offsetForRemoval(int i, int i2) {
            int[] iArr = this.mData;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                ensureSize(i3);
                int[] iArr2 = this.mData;
                System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
                int[] iArr3 = this.mData;
                Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
                List<FullSpanItem> list = this.mFullSpanItems;
                if (list != null) {
                    for (int size = list.size() - 1; size >= 0; size--) {
                        FullSpanItem fullSpanItem = this.mFullSpanItems.get(size);
                        int i4 = fullSpanItem.mPosition;
                        if (i4 >= i) {
                            if (i4 < i3) {
                                this.mFullSpanItems.remove(size);
                            } else {
                                fullSpanItem.mPosition = i4 - i2;
                            }
                        }
                    }
                }
            }
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
        public int mAnchorPosition;
        public List<LazySpanLookup.FullSpanItem> mFullSpanItems;
        public boolean mLastLayoutRTL;
        public boolean mReverseLayout;
        public int[] mSpanLookup;
        public int mSpanLookupSize;
        public int[] mSpanOffsets;
        public int mSpanOffsetsSize;
        public int mVisibleAnchorPosition;

        public SavedState() {
        }

        public final int describeContents() {
            return 0;
        }

        public SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mVisibleAnchorPosition = parcel.readInt();
            int readInt = parcel.readInt();
            this.mSpanOffsetsSize = readInt;
            if (readInt > 0) {
                int[] iArr = new int[readInt];
                this.mSpanOffsets = iArr;
                parcel.readIntArray(iArr);
            }
            int readInt2 = parcel.readInt();
            this.mSpanLookupSize = readInt2;
            if (readInt2 > 0) {
                int[] iArr2 = new int[readInt2];
                this.mSpanLookup = iArr2;
                parcel.readIntArray(iArr2);
            }
            boolean z = false;
            this.mReverseLayout = parcel.readInt() == 1;
            this.mAnchorLayoutFromEnd = parcel.readInt() == 1;
            this.mLastLayoutRTL = parcel.readInt() == 1 ? true : z;
            this.mFullSpanItems = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mVisibleAnchorPosition);
            parcel.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                parcel.writeIntArray(this.mSpanOffsets);
            }
            parcel.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                parcel.writeIntArray(this.mSpanLookup);
            }
            parcel.writeInt(this.mReverseLayout ? 1 : 0);
            parcel.writeInt(this.mAnchorLayoutFromEnd ? 1 : 0);
            parcel.writeInt(this.mLastLayoutRTL ? 1 : 0);
            parcel.writeList(this.mFullSpanItems);
        }

        public SavedState(SavedState savedState) {
            this.mSpanOffsetsSize = savedState.mSpanOffsetsSize;
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mVisibleAnchorPosition = savedState.mVisibleAnchorPosition;
            this.mSpanOffsets = savedState.mSpanOffsets;
            this.mSpanLookupSize = savedState.mSpanLookupSize;
            this.mSpanLookup = savedState.mSpanLookup;
            this.mReverseLayout = savedState.mReverseLayout;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = savedState.mLastLayoutRTL;
            this.mFullSpanItems = savedState.mFullSpanItems;
        }
    }

    public class Span {
        public int mCachedEnd = Integer.MIN_VALUE;
        public int mCachedStart = Integer.MIN_VALUE;
        public int mDeletedSize = 0;
        public final int mIndex;
        public ArrayList<View> mViews = new ArrayList<>();

        public final View getFocusableViewAfter(int i, int i2) {
            View view = null;
            if (i2 != -1) {
                int size = this.mViews.size() - 1;
                while (size >= 0) {
                    View view2 = this.mViews.get(size);
                    if ((StaggeredGridLayoutManager.this.mReverseLayout && RecyclerView.LayoutManager.getPosition(view2) >= i) || ((!StaggeredGridLayoutManager.this.mReverseLayout && RecyclerView.LayoutManager.getPosition(view2) <= i) || !view2.hasFocusable())) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.mViews.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = this.mViews.get(i3);
                    if ((StaggeredGridLayoutManager.this.mReverseLayout && RecyclerView.LayoutManager.getPosition(view3) <= i) || ((!StaggeredGridLayoutManager.this.mReverseLayout && RecyclerView.LayoutManager.getPosition(view3) >= i) || !view3.hasFocusable())) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }

        public Span(int i) {
            this.mIndex = i;
        }

        public final void calculateCachedEnd() {
            ArrayList<View> arrayList = this.mViews;
            View view = arrayList.get(arrayList.size() - 1);
            LayoutParams layoutParams = getLayoutParams(view);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
            Objects.requireNonNull(layoutParams);
        }

        public final void clear() {
            this.mViews.clear();
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
            this.mDeletedSize = 0;
        }

        public final int findFirstPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1);
            }
            return findOnePartiallyVisibleChild(0, this.mViews.size());
        }

        public final int findLastPartiallyVisibleItemPosition() {
            if (StaggeredGridLayoutManager.this.mReverseLayout) {
                return findOnePartiallyVisibleChild(0, this.mViews.size());
            }
            return findOnePartiallyVisibleChild(this.mViews.size() - 1, -1);
        }

        public final int findOnePartiallyVisibleChild(int i, int i2) {
            int i3;
            boolean z;
            int startAfterPadding = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            int endAfterPadding = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
            if (i2 > i) {
                i3 = 1;
            } else {
                i3 = -1;
            }
            while (i != i2) {
                View view = this.mViews.get(i);
                int decoratedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                int decoratedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
                boolean z2 = false;
                if (decoratedStart <= endAfterPadding) {
                    z = true;
                } else {
                    z = false;
                }
                if (decoratedEnd >= startAfterPadding) {
                    z2 = true;
                }
                if (!z || !z2 || (decoratedStart >= startAfterPadding && decoratedEnd <= endAfterPadding)) {
                    i += i3;
                } else {
                    Objects.requireNonNull(StaggeredGridLayoutManager.this);
                    return RecyclerView.LayoutManager.getPosition(view);
                }
            }
            return -1;
        }

        public final int getEndLine(int i) {
            int i2 = this.mCachedEnd;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.mViews.size() == 0) {
                return i;
            }
            calculateCachedEnd();
            return this.mCachedEnd;
        }

        public final int getStartLine(int i) {
            int i2 = this.mCachedStart;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.mViews.size() == 0) {
                return i;
            }
            View view = this.mViews.get(0);
            LayoutParams layoutParams = getLayoutParams(view);
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
            Objects.requireNonNull(layoutParams);
            return this.mCachedStart;
        }

        public static LayoutParams getLayoutParams(View view) {
            return (LayoutParams) view.getLayoutParams();
        }
    }

    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public final void onItemsAdded(int i, int i2) {
        handleUpdate(i, i2, 1);
    }

    public final void onItemsRemoved(int i, int i2) {
        handleUpdate(i, i2, 2);
    }

    public final void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, 4);
    }

    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        onLayoutChildren(recycler, state, true);
    }

    public final void onLayoutCompleted(RecyclerView.State state) {
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    public final void prepareLayoutStateForDelta(int i, RecyclerView.State state) {
        int i2;
        int i3;
        if (i > 0) {
            i3 = getLastChildPosition();
            i2 = 1;
        } else {
            i2 = -1;
            i3 = getFirstChildPosition();
        }
        this.mLayoutState.mRecycle = true;
        updateLayoutState(i3, state);
        setLayoutStateDirection(i2);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = i3 + layoutState.mItemDirection;
        layoutState.mAvailable = Math.abs(i);
    }

    public static int updateSpecWithExtra(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - i2) - i3), mode);
        }
        return i;
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
        boolean z;
        int i3;
        int i4;
        if (this.mOrientation != 0) {
            i = i2;
        }
        if (getChildCount() != 0 && i != 0) {
            prepareLayoutStateForDelta(i, state);
            int[] iArr = this.mPrefetchDistances;
            if (iArr == null || iArr.length < this.mSpanCount) {
                this.mPrefetchDistances = new int[this.mSpanCount];
            }
            int i5 = 0;
            for (int i6 = 0; i6 < this.mSpanCount; i6++) {
                LayoutState layoutState = this.mLayoutState;
                if (layoutState.mItemDirection == -1) {
                    i4 = layoutState.mStartLine;
                    i3 = this.mSpans[i6].getStartLine(i4);
                } else {
                    i4 = this.mSpans[i6].getEndLine(layoutState.mEndLine);
                    i3 = this.mLayoutState.mEndLine;
                }
                int i7 = i4 - i3;
                if (i7 >= 0) {
                    this.mPrefetchDistances[i5] = i7;
                    i5++;
                }
            }
            Arrays.sort(this.mPrefetchDistances, 0, i5);
            int i8 = 0;
            while (i8 < i5) {
                LayoutState layoutState2 = this.mLayoutState;
                Objects.requireNonNull(layoutState2);
                int i9 = layoutState2.mCurrentPosition;
                if (i9 < 0 || i9 >= state.getItemCount()) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    ((GapWorker.LayoutPrefetchRegistryImpl) layoutPrefetchRegistry).addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[i8]);
                    LayoutState layoutState3 = this.mLayoutState;
                    layoutState3.mCurrentPosition += layoutState3.mItemDirection;
                    i8++;
                } else {
                    return;
                }
            }
        }
    }

    public final int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Span span;
        boolean z;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        RecyclerView.Recycler recycler2 = recycler;
        LayoutState layoutState2 = layoutState;
        int i15 = 0;
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        if (this.mLayoutState.mInfinite) {
            if (layoutState2.mLayoutDirection == 1) {
                i = Integer.MAX_VALUE;
            } else {
                i = Integer.MIN_VALUE;
            }
        } else if (layoutState2.mLayoutDirection == 1) {
            i = layoutState2.mEndLine + layoutState2.mAvailable;
        } else {
            i = layoutState2.mStartLine - layoutState2.mAvailable;
        }
        int i16 = layoutState2.mLayoutDirection;
        for (int i17 = 0; i17 < this.mSpanCount; i17++) {
            if (!this.mSpans[i17].mViews.isEmpty()) {
                updateRemainingSpans(this.mSpans[i17], i16, i);
            }
        }
        if (this.mShouldReverseLayout) {
            i2 = this.mPrimaryOrientation.getEndAfterPadding();
        } else {
            i2 = this.mPrimaryOrientation.getStartAfterPadding();
        }
        boolean z2 = false;
        while (true) {
            int i18 = layoutState2.mCurrentPosition;
            if (i18 < 0 || i18 >= state.getItemCount()) {
                i3 = i15;
            } else {
                i3 = 1;
            }
            if (i3 == 0 || (!this.mLayoutState.mInfinite && this.mRemainingSpans.isEmpty())) {
                int i19 = i15;
            } else {
                View viewForPosition = recycler2.getViewForPosition(layoutState2.mCurrentPosition);
                layoutState2.mCurrentPosition += layoutState2.mItemDirection;
                LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
                int viewLayoutPosition = layoutParams.getViewLayoutPosition();
                LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
                Objects.requireNonNull(lazySpanLookup);
                int[] iArr = lazySpanLookup.mData;
                if (iArr == null || viewLayoutPosition >= iArr.length) {
                    i5 = -1;
                } else {
                    i5 = iArr[viewLayoutPosition];
                }
                if (i5 == -1) {
                    i6 = 1;
                } else {
                    i6 = i15;
                }
                if (i6 != 0) {
                    if (preferLastSpan(layoutState2.mLayoutDirection)) {
                        i12 = -1;
                        i13 = this.mSpanCount - 1;
                        i14 = -1;
                    } else {
                        i14 = this.mSpanCount;
                        i13 = i15;
                        i12 = 1;
                    }
                    Span span2 = null;
                    if (layoutState2.mLayoutDirection == 1) {
                        int startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
                        int i20 = Integer.MAX_VALUE;
                        while (i13 != i14) {
                            Span span3 = this.mSpans[i13];
                            int endLine = span3.getEndLine(startAfterPadding);
                            if (endLine < i20) {
                                span2 = span3;
                                i20 = endLine;
                            }
                            i13 += i12;
                        }
                    } else {
                        int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
                        int i21 = Integer.MIN_VALUE;
                        while (i13 != i14) {
                            Span span4 = this.mSpans[i13];
                            int startLine = span4.getStartLine(endAfterPadding);
                            if (startLine > i21) {
                                span2 = span4;
                                i21 = startLine;
                            }
                            i13 += i12;
                        }
                    }
                    span = span2;
                    LazySpanLookup lazySpanLookup2 = this.mLazySpanLookup;
                    Objects.requireNonNull(lazySpanLookup2);
                    lazySpanLookup2.ensureSize(viewLayoutPosition);
                    lazySpanLookup2.mData[viewLayoutPosition] = span.mIndex;
                } else {
                    span = this.mSpans[i5];
                }
                layoutParams.mSpan = span;
                if (layoutState2.mLayoutDirection == 1) {
                    z = false;
                    addViewInt(viewForPosition, -1, false);
                } else {
                    z = false;
                    addViewInt(viewForPosition, 0, false);
                }
                if (this.mOrientation == 1) {
                    measureChildWithDecorationsAndMargin(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.mWidthMode, z ? 1 : 0, layoutParams.width, z), RecyclerView.LayoutManager.getChildMeasureSpec(this.mHeight, this.mHeightMode, getPaddingBottom() + getPaddingTop(), layoutParams.height, true), z);
                } else {
                    measureChildWithDecorationsAndMargin(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(this.mWidth, this.mWidthMode, getPaddingRight() + getPaddingLeft(), layoutParams.width, true), RecyclerView.LayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.mHeightMode, 0, layoutParams.height, false), false);
                }
                if (layoutState2.mLayoutDirection == 1) {
                    i8 = span.getEndLine(i2);
                    i7 = this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + i8;
                } else {
                    i7 = span.getStartLine(i2);
                    i8 = i7 - this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition);
                }
                if (layoutState2.mLayoutDirection == 1) {
                    Span span5 = layoutParams.mSpan;
                    Objects.requireNonNull(span5);
                    LayoutParams layoutParams2 = (LayoutParams) viewForPosition.getLayoutParams();
                    layoutParams2.mSpan = span5;
                    span5.mViews.add(viewForPosition);
                    span5.mCachedEnd = Integer.MIN_VALUE;
                    if (span5.mViews.size() == 1) {
                        span5.mCachedStart = Integer.MIN_VALUE;
                    }
                    if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                        span5.mDeletedSize = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + span5.mDeletedSize;
                    }
                } else {
                    Span span6 = layoutParams.mSpan;
                    Objects.requireNonNull(span6);
                    LayoutParams layoutParams3 = (LayoutParams) viewForPosition.getLayoutParams();
                    layoutParams3.mSpan = span6;
                    span6.mViews.add(0, viewForPosition);
                    span6.mCachedStart = Integer.MIN_VALUE;
                    if (span6.mViews.size() == 1) {
                        span6.mCachedEnd = Integer.MIN_VALUE;
                    }
                    if (layoutParams3.isItemRemoved() || layoutParams3.isItemChanged()) {
                        span6.mDeletedSize = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(viewForPosition) + span6.mDeletedSize;
                    }
                }
                if (!isLayoutRTL() || this.mOrientation != 1) {
                    i9 = this.mSecondaryOrientation.getStartAfterPadding() + (span.mIndex * this.mSizePerSpan);
                    i10 = this.mSecondaryOrientation.getDecoratedMeasurement(viewForPosition) + i9;
                } else {
                    i10 = this.mSecondaryOrientation.getEndAfterPadding() - (((this.mSpanCount - 1) - span.mIndex) * this.mSizePerSpan);
                    i9 = i10 - this.mSecondaryOrientation.getDecoratedMeasurement(viewForPosition);
                }
                if (this.mOrientation == 1) {
                    RecyclerView.LayoutManager.layoutDecoratedWithMargins(viewForPosition, i9, i8, i10, i7);
                } else {
                    RecyclerView.LayoutManager.layoutDecoratedWithMargins(viewForPosition, i8, i9, i7, i10);
                }
                updateRemainingSpans(span, this.mLayoutState.mLayoutDirection, i);
                recycle(recycler2, this.mLayoutState);
                if (!this.mLayoutState.mStopInFocusable || !viewForPosition.hasFocusable()) {
                    i11 = 0;
                } else {
                    i11 = 0;
                    this.mRemainingSpans.set(span.mIndex, false);
                }
                z2 = true;
                i15 = i11;
            }
        }
        int i192 = i15;
        if (!z2) {
            recycle(recycler2, this.mLayoutState);
        }
        if (this.mLayoutState.mLayoutDirection == -1) {
            i4 = this.mPrimaryOrientation.getStartAfterPadding() - getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
        } else {
            i4 = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
        }
        if (i4 > 0) {
            return Math.min(layoutState2.mAvailable, i4);
        }
        return i192;
    }

    public final View findFirstVisibleItemClosestToEnd(boolean z) {
        int startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
        int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(childAt);
            int decoratedEnd = this.mPrimaryOrientation.getDecoratedEnd(childAt);
            if (decoratedEnd > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedEnd <= endAfterPadding || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    public final View findFirstVisibleItemClosestToStart(boolean z) {
        int startAfterPadding = this.mPrimaryOrientation.getStartAfterPadding();
        int endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding();
        int childCount = getChildCount();
        View view = null;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int decoratedStart = this.mPrimaryOrientation.getDecoratedStart(childAt);
            if (this.mPrimaryOrientation.getDecoratedEnd(childAt) > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedStart >= startAfterPadding || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    public final void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int maxEnd = getMaxEnd(Integer.MIN_VALUE);
        if (maxEnd != Integer.MIN_VALUE && (endAfterPadding = this.mPrimaryOrientation.getEndAfterPadding() - maxEnd) > 0) {
            int i = endAfterPadding - (-scrollBy(-endAfterPadding, recycler, state));
            if (z && i > 0) {
                this.mPrimaryOrientation.offsetChildren(i);
            }
        }
    }

    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final int getMaxEnd(int i) {
        int endLine = this.mSpans[0].getEndLine(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int endLine2 = this.mSpans[i2].getEndLine(i);
            if (endLine2 > endLine) {
                endLine = endLine2;
            }
        }
        return endLine;
    }

    public final int getMinStart(int i) {
        int startLine = this.mSpans[0].getStartLine(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int startLine2 = this.mSpans[i2].getStartLine(i);
            if (startLine2 < startLine) {
                startLine = startLine2;
            }
        }
        return startLine;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleUpdate(int r7, int r8, int r9) {
        /*
            r6 = this;
            boolean r0 = r6.mShouldReverseLayout
            if (r0 == 0) goto L_0x0009
            int r0 = r6.getLastChildPosition()
            goto L_0x000d
        L_0x0009:
            int r0 = r6.getFirstChildPosition()
        L_0x000d:
            r1 = 8
            if (r9 != r1) goto L_0x001a
            if (r7 >= r8) goto L_0x0016
            int r2 = r8 + 1
            goto L_0x001c
        L_0x0016:
            int r2 = r7 + 1
            r3 = r8
            goto L_0x001d
        L_0x001a:
            int r2 = r7 + r8
        L_0x001c:
            r3 = r7
        L_0x001d:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.mLazySpanLookup
            r4.invalidateAfter(r3)
            r4 = 1
            if (r9 == r4) goto L_0x003c
            r5 = 2
            if (r9 == r5) goto L_0x0036
            if (r9 == r1) goto L_0x002b
            goto L_0x0041
        L_0x002b:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.mLazySpanLookup
            r9.offsetForRemoval(r7, r4)
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r7 = r6.mLazySpanLookup
            r7.offsetForAddition(r8, r4)
            goto L_0x0041
        L_0x0036:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.mLazySpanLookup
            r9.offsetForRemoval(r7, r8)
            goto L_0x0041
        L_0x003c:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.mLazySpanLookup
            r9.offsetForAddition(r7, r8)
        L_0x0041:
            if (r2 > r0) goto L_0x0044
            return
        L_0x0044:
            boolean r7 = r6.mShouldReverseLayout
            if (r7 == 0) goto L_0x004d
            int r7 = r6.getFirstChildPosition()
            goto L_0x0051
        L_0x004d:
            int r7 = r6.getLastChildPosition()
        L_0x0051:
            if (r3 > r7) goto L_0x0056
            r6.requestLayout()
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.handleUpdate(int, int, int):void");
    }

    public final boolean isAutoMeasureEnabled() {
        if (this.mGapStrategy != 0) {
            return true;
        }
        return false;
    }

    public final void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z) {
        calculateItemDecorationsForChild(view, this.mTmpRect);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.leftMargin;
        Rect rect = this.mTmpRect;
        int updateSpecWithExtra = updateSpecWithExtra(i, i3 + rect.left, layoutParams.rightMargin + rect.right);
        int i4 = layoutParams.topMargin;
        Rect rect2 = this.mTmpRect;
        int updateSpecWithExtra2 = updateSpecWithExtra(i2, i4 + rect2.top, layoutParams.bottomMargin + rect2.bottom);
        if (shouldMeasureChild(view, updateSpecWithExtra, updateSpecWithExtra2, layoutParams)) {
            view.measure(updateSpecWithExtra, updateSpecWithExtra2);
        }
    }

    public final void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
        this.mLazySpanLookup.clear();
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
    }

    public final void onDetachedFromWindow(RecyclerView recyclerView) {
        C03421 r0 = this.mCheckForGapsRunnable;
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 != null) {
            recyclerView2.removeCallbacks(r0);
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].clear();
        }
        recyclerView.requestLayout();
    }

    public final void onItemsChanged() {
        this.mLazySpanLookup.clear();
        requestLayout();
    }

    public final void onItemsMoved(int i, int i2) {
        handleUpdate(i, i2, 8);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:236:0x042e, code lost:
        if (checkForGaps() != false) goto L_0x0432;
     */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onLayoutChildren(androidx.recyclerview.widget.RecyclerView.Recycler r12, androidx.recyclerview.widget.RecyclerView.State r13, boolean r14) {
        /*
            r11 = this;
            androidx.recyclerview.widget.StaggeredGridLayoutManager$AnchorInfo r0 = r11.mAnchorInfo
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r1 = r11.mPendingSavedState
            r2 = -1
            if (r1 != 0) goto L_0x000b
            int r1 = r11.mPendingScrollPosition
            if (r1 == r2) goto L_0x0018
        L_0x000b:
            int r1 = r13.getItemCount()
            if (r1 != 0) goto L_0x0018
            r11.removeAndRecycleAllViews(r12)
            r0.reset()
            return
        L_0x0018:
            boolean r1 = r0.mValid
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x0029
            int r1 = r11.mPendingScrollPosition
            if (r1 != r2) goto L_0x0029
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r1 = r11.mPendingSavedState
            if (r1 == 0) goto L_0x0027
            goto L_0x0029
        L_0x0027:
            r1 = r3
            goto L_0x002a
        L_0x0029:
            r1 = r4
        L_0x002a:
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 == 0) goto L_0x0211
            r0.reset()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r6 = r11.mPendingSavedState
            if (r6 == 0) goto L_0x00bd
            int r7 = r6.mSpanOffsetsSize
            r8 = 0
            if (r7 <= 0) goto L_0x0080
            int r9 = r11.mSpanCount
            if (r7 != r9) goto L_0x0072
            r6 = r3
        L_0x003f:
            int r7 = r11.mSpanCount
            if (r6 >= r7) goto L_0x0080
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r7 = r11.mSpans
            r7 = r7[r6]
            r7.clear()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r7 = r11.mPendingSavedState
            int[] r9 = r7.mSpanOffsets
            r9 = r9[r6]
            if (r9 == r5) goto L_0x0064
            boolean r7 = r7.mAnchorLayoutFromEnd
            if (r7 == 0) goto L_0x005d
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getEndAfterPadding()
            goto L_0x0063
        L_0x005d:
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getStartAfterPadding()
        L_0x0063:
            int r9 = r9 + r7
        L_0x0064:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r7 = r11.mSpans
            r7 = r7[r6]
            java.util.Objects.requireNonNull(r7)
            r7.mCachedStart = r9
            r7.mCachedEnd = r9
            int r6 = r6 + 1
            goto L_0x003f
        L_0x0072:
            r6.mSpanOffsets = r8
            r6.mSpanOffsetsSize = r3
            r6.mSpanLookupSize = r3
            r6.mSpanLookup = r8
            r6.mFullSpanItems = r8
            int r7 = r6.mVisibleAnchorPosition
            r6.mAnchorPosition = r7
        L_0x0080:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r6 = r11.mPendingSavedState
            boolean r7 = r6.mLastLayoutRTL
            r11.mLastLayoutRTL = r7
            boolean r6 = r6.mReverseLayout
            r11.assertNotInLayoutOrScroll(r8)
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r7 = r11.mPendingSavedState
            if (r7 == 0) goto L_0x0095
            boolean r8 = r7.mReverseLayout
            if (r8 == r6) goto L_0x0095
            r7.mReverseLayout = r6
        L_0x0095:
            r11.mReverseLayout = r6
            r11.requestLayout()
            r11.resolveShouldLayoutReverse()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r6 = r11.mPendingSavedState
            int r7 = r6.mAnchorPosition
            if (r7 == r2) goto L_0x00aa
            r11.mPendingScrollPosition = r7
            boolean r7 = r6.mAnchorLayoutFromEnd
            r0.mLayoutFromEnd = r7
            goto L_0x00ae
        L_0x00aa:
            boolean r7 = r11.mShouldReverseLayout
            r0.mLayoutFromEnd = r7
        L_0x00ae:
            int r7 = r6.mSpanLookupSize
            if (r7 <= r4) goto L_0x00c4
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r7 = r11.mLazySpanLookup
            int[] r8 = r6.mSpanLookup
            r7.mData = r8
            java.util.List<androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r6 = r6.mFullSpanItems
            r7.mFullSpanItems = r6
            goto L_0x00c4
        L_0x00bd:
            r11.resolveShouldLayoutReverse()
            boolean r6 = r11.mShouldReverseLayout
            r0.mLayoutFromEnd = r6
        L_0x00c4:
            java.util.Objects.requireNonNull(r13)
            boolean r6 = r13.mInPreLayout
            if (r6 != 0) goto L_0x01ce
            int r6 = r11.mPendingScrollPosition
            if (r6 != r2) goto L_0x00d1
            goto L_0x01ce
        L_0x00d1:
            if (r6 < 0) goto L_0x01ca
            int r7 = r13.getItemCount()
            if (r6 < r7) goto L_0x00db
            goto L_0x01ca
        L_0x00db:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r6 = r11.mPendingSavedState
            if (r6 == 0) goto L_0x00f0
            int r7 = r6.mAnchorPosition
            if (r7 == r2) goto L_0x00f0
            int r6 = r6.mSpanOffsetsSize
            if (r6 >= r4) goto L_0x00e8
            goto L_0x00f0
        L_0x00e8:
            r0.mOffset = r5
            int r6 = r11.mPendingScrollPosition
            r0.mPosition = r6
            goto L_0x01c8
        L_0x00f0:
            int r6 = r11.mPendingScrollPosition
            android.view.View r6 = r11.findViewByPosition(r6)
            if (r6 == 0) goto L_0x0182
            boolean r7 = r11.mShouldReverseLayout
            if (r7 == 0) goto L_0x0101
            int r7 = r11.getLastChildPosition()
            goto L_0x0105
        L_0x0101:
            int r7 = r11.getFirstChildPosition()
        L_0x0105:
            r0.mPosition = r7
            int r7 = r11.mPendingScrollPositionOffset
            if (r7 == r5) goto L_0x0137
            boolean r7 = r0.mLayoutFromEnd
            if (r7 == 0) goto L_0x0123
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getEndAfterPadding()
            int r8 = r11.mPendingScrollPositionOffset
            int r7 = r7 - r8
            androidx.recyclerview.widget.OrientationHelper r8 = r11.mPrimaryOrientation
            int r6 = r8.getDecoratedEnd(r6)
            int r7 = r7 - r6
            r0.mOffset = r7
            goto L_0x01c8
        L_0x0123:
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getStartAfterPadding()
            int r8 = r11.mPendingScrollPositionOffset
            int r7 = r7 + r8
            androidx.recyclerview.widget.OrientationHelper r8 = r11.mPrimaryOrientation
            int r6 = r8.getDecoratedStart(r6)
            int r7 = r7 - r6
            r0.mOffset = r7
            goto L_0x01c8
        L_0x0137:
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getDecoratedMeasurement(r6)
            androidx.recyclerview.widget.OrientationHelper r8 = r11.mPrimaryOrientation
            int r8 = r8.getTotalSpace()
            if (r7 <= r8) goto L_0x015a
            boolean r6 = r0.mLayoutFromEnd
            if (r6 == 0) goto L_0x0150
            androidx.recyclerview.widget.OrientationHelper r6 = r11.mPrimaryOrientation
            int r6 = r6.getEndAfterPadding()
            goto L_0x0156
        L_0x0150:
            androidx.recyclerview.widget.OrientationHelper r6 = r11.mPrimaryOrientation
            int r6 = r6.getStartAfterPadding()
        L_0x0156:
            r0.mOffset = r6
            goto L_0x01c8
        L_0x015a:
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getDecoratedStart(r6)
            androidx.recyclerview.widget.OrientationHelper r8 = r11.mPrimaryOrientation
            int r8 = r8.getStartAfterPadding()
            int r7 = r7 - r8
            if (r7 >= 0) goto L_0x016d
            int r6 = -r7
            r0.mOffset = r6
            goto L_0x01c8
        L_0x016d:
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mPrimaryOrientation
            int r7 = r7.getEndAfterPadding()
            androidx.recyclerview.widget.OrientationHelper r8 = r11.mPrimaryOrientation
            int r6 = r8.getDecoratedEnd(r6)
            int r7 = r7 - r6
            if (r7 >= 0) goto L_0x017f
            r0.mOffset = r7
            goto L_0x01c8
        L_0x017f:
            r0.mOffset = r5
            goto L_0x01c8
        L_0x0182:
            int r6 = r11.mPendingScrollPosition
            r0.mPosition = r6
            int r7 = r11.mPendingScrollPositionOffset
            if (r7 != r5) goto L_0x01ab
            int r6 = r11.calculateScrollDirectionForPosition(r6)
            if (r6 != r4) goto L_0x0192
            r6 = r4
            goto L_0x0193
        L_0x0192:
            r6 = r3
        L_0x0193:
            r0.mLayoutFromEnd = r6
            if (r6 == 0) goto L_0x01a0
            androidx.recyclerview.widget.StaggeredGridLayoutManager r6 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r6 = r6.mPrimaryOrientation
            int r6 = r6.getEndAfterPadding()
            goto L_0x01a8
        L_0x01a0:
            androidx.recyclerview.widget.StaggeredGridLayoutManager r6 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r6 = r6.mPrimaryOrientation
            int r6 = r6.getStartAfterPadding()
        L_0x01a8:
            r0.mOffset = r6
            goto L_0x01c6
        L_0x01ab:
            boolean r6 = r0.mLayoutFromEnd
            if (r6 == 0) goto L_0x01bb
            androidx.recyclerview.widget.StaggeredGridLayoutManager r6 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r6 = r6.mPrimaryOrientation
            int r6 = r6.getEndAfterPadding()
            int r6 = r6 - r7
            r0.mOffset = r6
            goto L_0x01c6
        L_0x01bb:
            androidx.recyclerview.widget.StaggeredGridLayoutManager r6 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r6 = r6.mPrimaryOrientation
            int r6 = r6.getStartAfterPadding()
            int r6 = r6 + r7
            r0.mOffset = r6
        L_0x01c6:
            r0.mInvalidateOffsets = r4
        L_0x01c8:
            r6 = r4
            goto L_0x01cf
        L_0x01ca:
            r11.mPendingScrollPosition = r2
            r11.mPendingScrollPositionOffset = r5
        L_0x01ce:
            r6 = r3
        L_0x01cf:
            if (r6 == 0) goto L_0x01d2
            goto L_0x020f
        L_0x01d2:
            boolean r6 = r11.mLastLayoutFromEnd
            if (r6 == 0) goto L_0x01ee
            int r6 = r13.getItemCount()
            int r7 = r11.getChildCount()
        L_0x01de:
            int r7 = r7 + r2
            if (r7 < 0) goto L_0x020a
            android.view.View r8 = r11.getChildAt(r7)
            int r8 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r8)
            if (r8 < 0) goto L_0x01de
            if (r8 >= r6) goto L_0x01de
            goto L_0x020b
        L_0x01ee:
            int r6 = r13.getItemCount()
            int r7 = r11.getChildCount()
            r8 = r3
        L_0x01f7:
            if (r8 >= r7) goto L_0x020a
            android.view.View r9 = r11.getChildAt(r8)
            int r9 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r9)
            if (r9 < 0) goto L_0x0207
            if (r9 >= r6) goto L_0x0207
            r8 = r9
            goto L_0x020b
        L_0x0207:
            int r8 = r8 + 1
            goto L_0x01f7
        L_0x020a:
            r8 = r3
        L_0x020b:
            r0.mPosition = r8
            r0.mOffset = r5
        L_0x020f:
            r0.mValid = r4
        L_0x0211:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r6 = r11.mPendingSavedState
            if (r6 != 0) goto L_0x022e
            int r6 = r11.mPendingScrollPosition
            if (r6 != r2) goto L_0x022e
            boolean r6 = r0.mLayoutFromEnd
            boolean r7 = r11.mLastLayoutFromEnd
            if (r6 != r7) goto L_0x0227
            boolean r6 = r11.isLayoutRTL()
            boolean r7 = r11.mLastLayoutRTL
            if (r6 == r7) goto L_0x022e
        L_0x0227:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r6 = r11.mLazySpanLookup
            r6.clear()
            r0.mInvalidateOffsets = r4
        L_0x022e:
            int r6 = r11.getChildCount()
            if (r6 <= 0) goto L_0x02ec
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r6 = r11.mPendingSavedState
            if (r6 == 0) goto L_0x023c
            int r6 = r6.mSpanOffsetsSize
            if (r6 >= r4) goto L_0x02ec
        L_0x023c:
            boolean r6 = r0.mInvalidateOffsets
            if (r6 == 0) goto L_0x025e
            r1 = r3
        L_0x0241:
            int r6 = r11.mSpanCount
            if (r1 >= r6) goto L_0x02ec
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            r6 = r6[r1]
            r6.clear()
            int r6 = r0.mOffset
            if (r6 == r5) goto L_0x025b
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r7 = r11.mSpans
            r7 = r7[r1]
            java.util.Objects.requireNonNull(r7)
            r7.mCachedStart = r6
            r7.mCachedEnd = r6
        L_0x025b:
            int r1 = r1 + 1
            goto L_0x0241
        L_0x025e:
            if (r1 != 0) goto L_0x0280
            androidx.recyclerview.widget.StaggeredGridLayoutManager$AnchorInfo r1 = r11.mAnchorInfo
            int[] r1 = r1.mSpanReferenceLines
            if (r1 != 0) goto L_0x0267
            goto L_0x0280
        L_0x0267:
            r1 = r3
        L_0x0268:
            int r6 = r11.mSpanCount
            if (r1 >= r6) goto L_0x02ec
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            r6 = r6[r1]
            r6.clear()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$AnchorInfo r7 = r11.mAnchorInfo
            int[] r7 = r7.mSpanReferenceLines
            r7 = r7[r1]
            r6.mCachedStart = r7
            r6.mCachedEnd = r7
            int r1 = r1 + 1
            goto L_0x0268
        L_0x0280:
            r1 = r3
        L_0x0281:
            int r6 = r11.mSpanCount
            if (r1 >= r6) goto L_0x02c4
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            r6 = r6[r1]
            boolean r7 = r11.mShouldReverseLayout
            int r8 = r0.mOffset
            java.util.Objects.requireNonNull(r6)
            if (r7 == 0) goto L_0x0297
            int r9 = r6.getEndLine(r5)
            goto L_0x029b
        L_0x0297:
            int r9 = r6.getStartLine(r5)
        L_0x029b:
            r6.clear()
            if (r9 != r5) goto L_0x02a1
            goto L_0x02c1
        L_0x02a1:
            if (r7 == 0) goto L_0x02ad
            androidx.recyclerview.widget.StaggeredGridLayoutManager r10 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r10 = r10.mPrimaryOrientation
            int r10 = r10.getEndAfterPadding()
            if (r9 < r10) goto L_0x02c1
        L_0x02ad:
            if (r7 != 0) goto L_0x02ba
            androidx.recyclerview.widget.StaggeredGridLayoutManager r7 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r7 = r7.mPrimaryOrientation
            int r7 = r7.getStartAfterPadding()
            if (r9 <= r7) goto L_0x02ba
            goto L_0x02c1
        L_0x02ba:
            if (r8 == r5) goto L_0x02bd
            int r9 = r9 + r8
        L_0x02bd:
            r6.mCachedEnd = r9
            r6.mCachedStart = r9
        L_0x02c1:
            int r1 = r1 + 1
            goto L_0x0281
        L_0x02c4:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$AnchorInfo r1 = r11.mAnchorInfo
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            java.util.Objects.requireNonNull(r1)
            int r7 = r6.length
            int[] r8 = r1.mSpanReferenceLines
            if (r8 == 0) goto L_0x02d3
            int r8 = r8.length
            if (r8 >= r7) goto L_0x02dc
        L_0x02d3:
            androidx.recyclerview.widget.StaggeredGridLayoutManager r8 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r8 = r8.mSpans
            int r8 = r8.length
            int[] r8 = new int[r8]
            r1.mSpanReferenceLines = r8
        L_0x02dc:
            r8 = r3
        L_0x02dd:
            if (r8 >= r7) goto L_0x02ec
            int[] r9 = r1.mSpanReferenceLines
            r10 = r6[r8]
            int r10 = r10.getStartLine(r5)
            r9[r8] = r10
            int r8 = r8 + 1
            goto L_0x02dd
        L_0x02ec:
            r11.detachAndScrapAttachedViews(r12)
            androidx.recyclerview.widget.LayoutState r1 = r11.mLayoutState
            r1.mRecycle = r3
            androidx.recyclerview.widget.OrientationHelper r1 = r11.mSecondaryOrientation
            int r1 = r1.getTotalSpace()
            int r6 = r11.mSpanCount
            int r6 = r1 / r6
            r11.mSizePerSpan = r6
            androidx.recyclerview.widget.OrientationHelper r6 = r11.mSecondaryOrientation
            int r6 = r6.getMode()
            android.view.View.MeasureSpec.makeMeasureSpec(r1, r6)
            int r1 = r0.mPosition
            r11.updateLayoutState(r1, r13)
            boolean r1 = r0.mLayoutFromEnd
            if (r1 == 0) goto L_0x0329
            r11.setLayoutStateDirection(r2)
            androidx.recyclerview.widget.LayoutState r1 = r11.mLayoutState
            r11.fill(r12, r1, r13)
            r11.setLayoutStateDirection(r4)
            androidx.recyclerview.widget.LayoutState r1 = r11.mLayoutState
            int r2 = r0.mPosition
            int r6 = r1.mItemDirection
            int r2 = r2 + r6
            r1.mCurrentPosition = r2
            r11.fill(r12, r1, r13)
            goto L_0x0340
        L_0x0329:
            r11.setLayoutStateDirection(r4)
            androidx.recyclerview.widget.LayoutState r1 = r11.mLayoutState
            r11.fill(r12, r1, r13)
            r11.setLayoutStateDirection(r2)
            androidx.recyclerview.widget.LayoutState r1 = r11.mLayoutState
            int r2 = r0.mPosition
            int r6 = r1.mItemDirection
            int r2 = r2 + r6
            r1.mCurrentPosition = r2
            r11.fill(r12, r1, r13)
        L_0x0340:
            androidx.recyclerview.widget.OrientationHelper r1 = r11.mSecondaryOrientation
            int r1 = r1.getMode()
            r2 = 1073741824(0x40000000, float:2.0)
            if (r1 != r2) goto L_0x034c
            goto L_0x03ec
        L_0x034c:
            r1 = 0
            int r2 = r11.getChildCount()
            r6 = r3
        L_0x0352:
            if (r6 >= r2) goto L_0x0374
            android.view.View r7 = r11.getChildAt(r6)
            androidx.recyclerview.widget.OrientationHelper r8 = r11.mSecondaryOrientation
            int r8 = r8.getDecoratedMeasurement(r7)
            float r8 = (float) r8
            int r9 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r9 >= 0) goto L_0x0364
            goto L_0x0371
        L_0x0364:
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r7 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r7
            java.util.Objects.requireNonNull(r7)
            float r1 = java.lang.Math.max(r1, r8)
        L_0x0371:
            int r6 = r6 + 1
            goto L_0x0352
        L_0x0374:
            int r6 = r11.mSizePerSpan
            int r7 = r11.mSpanCount
            float r7 = (float) r7
            float r1 = r1 * r7
            int r1 = java.lang.Math.round(r1)
            androidx.recyclerview.widget.OrientationHelper r7 = r11.mSecondaryOrientation
            int r7 = r7.getMode()
            if (r7 != r5) goto L_0x0390
            androidx.recyclerview.widget.OrientationHelper r5 = r11.mSecondaryOrientation
            int r5 = r5.getTotalSpace()
            int r1 = java.lang.Math.min(r1, r5)
        L_0x0390:
            int r5 = r11.mSpanCount
            int r5 = r1 / r5
            r11.mSizePerSpan = r5
            androidx.recyclerview.widget.OrientationHelper r5 = r11.mSecondaryOrientation
            int r5 = r5.getMode()
            android.view.View.MeasureSpec.makeMeasureSpec(r1, r5)
            int r1 = r11.mSizePerSpan
            if (r1 != r6) goto L_0x03a4
            goto L_0x03ec
        L_0x03a4:
            r1 = r3
        L_0x03a5:
            if (r1 >= r2) goto L_0x03ec
            android.view.View r5 = r11.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r7 = r5.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r7 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r7
            java.util.Objects.requireNonNull(r7)
            boolean r8 = r11.isLayoutRTL()
            if (r8 == 0) goto L_0x03d4
            int r8 = r11.mOrientation
            if (r8 != r4) goto L_0x03d4
            int r8 = r11.mSpanCount
            int r9 = r8 + -1
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r7 = r7.mSpan
            int r7 = r7.mIndex
            int r9 = r9 - r7
            int r9 = -r9
            int r10 = r11.mSizePerSpan
            int r9 = r9 * r10
            int r8 = r8 - r4
            int r8 = r8 - r7
            int r7 = -r8
            int r7 = r7 * r6
            int r9 = r9 - r7
            r5.offsetLeftAndRight(r9)
            goto L_0x03e9
        L_0x03d4:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r7 = r7.mSpan
            int r7 = r7.mIndex
            int r8 = r11.mSizePerSpan
            int r8 = r8 * r7
            int r7 = r7 * r6
            int r9 = r11.mOrientation
            if (r9 != r4) goto L_0x03e5
            int r8 = r8 - r7
            r5.offsetLeftAndRight(r8)
            goto L_0x03e9
        L_0x03e5:
            int r8 = r8 - r7
            r5.offsetTopAndBottom(r8)
        L_0x03e9:
            int r1 = r1 + 1
            goto L_0x03a5
        L_0x03ec:
            int r1 = r11.getChildCount()
            if (r1 <= 0) goto L_0x0403
            boolean r1 = r11.mShouldReverseLayout
            if (r1 == 0) goto L_0x03fd
            r11.fixEndGap(r12, r13, r4)
            r11.fixStartGap(r12, r13, r3)
            goto L_0x0403
        L_0x03fd:
            r11.fixStartGap(r12, r13, r4)
            r11.fixEndGap(r12, r13, r3)
        L_0x0403:
            if (r14 == 0) goto L_0x0431
            java.util.Objects.requireNonNull(r13)
            boolean r14 = r13.mInPreLayout
            if (r14 != 0) goto L_0x0431
            int r14 = r11.mGapStrategy
            if (r14 == 0) goto L_0x041e
            int r14 = r11.getChildCount()
            if (r14 <= 0) goto L_0x041e
            android.view.View r14 = r11.hasGapsToFix()
            if (r14 == 0) goto L_0x041e
            r14 = r4
            goto L_0x041f
        L_0x041e:
            r14 = r3
        L_0x041f:
            if (r14 == 0) goto L_0x0431
            androidx.recyclerview.widget.StaggeredGridLayoutManager$1 r14 = r11.mCheckForGapsRunnable
            androidx.recyclerview.widget.RecyclerView r1 = r11.mRecyclerView
            if (r1 == 0) goto L_0x042a
            r1.removeCallbacks(r14)
        L_0x042a:
            boolean r14 = r11.checkForGaps()
            if (r14 == 0) goto L_0x0431
            goto L_0x0432
        L_0x0431:
            r4 = r3
        L_0x0432:
            java.util.Objects.requireNonNull(r13)
            boolean r14 = r13.mInPreLayout
            if (r14 == 0) goto L_0x043e
            androidx.recyclerview.widget.StaggeredGridLayoutManager$AnchorInfo r14 = r11.mAnchorInfo
            r14.reset()
        L_0x043e:
            boolean r14 = r0.mLayoutFromEnd
            r11.mLastLayoutFromEnd = r14
            boolean r14 = r11.isLayoutRTL()
            r11.mLastLayoutRTL = r14
            if (r4 == 0) goto L_0x0452
            androidx.recyclerview.widget.StaggeredGridLayoutManager$AnchorInfo r14 = r11.mAnchorInfo
            r14.reset()
            r11.onLayoutChildren(r12, r13, r3)
        L_0x0452:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.onLayoutChildren(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, boolean):void");
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.mPendingSavedState = savedState;
            if (this.mPendingScrollPosition != -1) {
                Objects.requireNonNull(savedState);
                savedState.mSpanOffsets = null;
                savedState.mSpanOffsetsSize = 0;
                savedState.mAnchorPosition = -1;
                savedState.mVisibleAnchorPosition = -1;
                SavedState savedState2 = this.mPendingSavedState;
                Objects.requireNonNull(savedState2);
                savedState2.mSpanOffsets = null;
                savedState2.mSpanOffsetsSize = 0;
                savedState2.mSpanLookupSize = 0;
                savedState2.mSpanLookup = null;
                savedState2.mFullSpanItems = null;
            }
            requestLayout();
        }
    }

    public final Parcelable onSaveInstanceState() {
        int i;
        View view;
        int i2;
        int i3;
        int[] iArr;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        savedState2.mReverseLayout = this.mReverseLayout;
        savedState2.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        savedState2.mLastLayoutRTL = this.mLastLayoutRTL;
        LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
        if (lazySpanLookup == null || (iArr = lazySpanLookup.mData) == null) {
            savedState2.mSpanLookupSize = 0;
        } else {
            savedState2.mSpanLookup = iArr;
            savedState2.mSpanLookupSize = iArr.length;
            savedState2.mFullSpanItems = lazySpanLookup.mFullSpanItems;
        }
        int i4 = -1;
        if (getChildCount() > 0) {
            if (this.mLastLayoutFromEnd) {
                i = getLastChildPosition();
            } else {
                i = getFirstChildPosition();
            }
            savedState2.mAnchorPosition = i;
            if (this.mShouldReverseLayout) {
                view = findFirstVisibleItemClosestToEnd(true);
            } else {
                view = findFirstVisibleItemClosestToStart(true);
            }
            if (view != null) {
                i4 = RecyclerView.LayoutManager.getPosition(view);
            }
            savedState2.mVisibleAnchorPosition = i4;
            int i5 = this.mSpanCount;
            savedState2.mSpanOffsetsSize = i5;
            savedState2.mSpanOffsets = new int[i5];
            for (int i6 = 0; i6 < this.mSpanCount; i6++) {
                if (this.mLastLayoutFromEnd) {
                    i2 = this.mSpans[i6].getEndLine(Integer.MIN_VALUE);
                    if (i2 != Integer.MIN_VALUE) {
                        i3 = this.mPrimaryOrientation.getEndAfterPadding();
                    } else {
                        savedState2.mSpanOffsets[i6] = i2;
                    }
                } else {
                    i2 = this.mSpans[i6].getStartLine(Integer.MIN_VALUE);
                    if (i2 != Integer.MIN_VALUE) {
                        i3 = this.mPrimaryOrientation.getStartAfterPadding();
                    } else {
                        savedState2.mSpanOffsets[i6] = i2;
                    }
                }
                i2 -= i3;
                savedState2.mSpanOffsets[i6] = i2;
            }
        } else {
            savedState2.mAnchorPosition = -1;
            savedState2.mVisibleAnchorPosition = -1;
            savedState2.mSpanOffsetsSize = 0;
        }
        return savedState2;
    }

    public final void onScrollStateChanged(int i) {
        if (i == 0) {
            checkForGaps();
        }
    }

    public final boolean preferLastSpan(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.mOrientation == 0) {
            if (i == -1) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3 != this.mShouldReverseLayout) {
                return true;
            }
            return false;
        }
        if (i == -1) {
            z = true;
        } else {
            z = false;
        }
        if (z == this.mShouldReverseLayout) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 == isLayoutRTL()) {
            return true;
        }
        return false;
    }

    public final void recycle(RecyclerView.Recycler recycler, LayoutState layoutState) {
        int i;
        int i2;
        if (layoutState.mRecycle && !layoutState.mInfinite) {
            if (layoutState.mAvailable != 0) {
                int i3 = 1;
                if (layoutState.mLayoutDirection == -1) {
                    int i4 = layoutState.mStartLine;
                    int startLine = this.mSpans[0].getStartLine(i4);
                    while (i3 < this.mSpanCount) {
                        int startLine2 = this.mSpans[i3].getStartLine(i4);
                        if (startLine2 > startLine) {
                            startLine = startLine2;
                        }
                        i3++;
                    }
                    int i5 = i4 - startLine;
                    if (i5 < 0) {
                        i2 = layoutState.mEndLine;
                    } else {
                        i2 = layoutState.mEndLine - Math.min(i5, layoutState.mAvailable);
                    }
                    recycleFromEnd(recycler, i2);
                    return;
                }
                int i6 = layoutState.mEndLine;
                int endLine = this.mSpans[0].getEndLine(i6);
                while (i3 < this.mSpanCount) {
                    int endLine2 = this.mSpans[i3].getEndLine(i6);
                    if (endLine2 < endLine) {
                        endLine = endLine2;
                    }
                    i3++;
                }
                int i7 = endLine - layoutState.mEndLine;
                if (i7 < 0) {
                    i = layoutState.mStartLine;
                } else {
                    i = Math.min(i7, layoutState.mAvailable) + layoutState.mStartLine;
                }
                recycleFromStart(recycler, i);
            } else if (layoutState.mLayoutDirection == -1) {
                recycleFromEnd(recycler, layoutState.mEndLine);
            } else {
                recycleFromStart(recycler, layoutState.mStartLine);
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

    public final void scrollToPosition(int i) {
        SavedState savedState = this.mPendingSavedState;
        if (!(savedState == null || savedState.mAnchorPosition == i)) {
            savedState.mSpanOffsets = null;
            savedState.mSpanOffsetsSize = 0;
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
        }
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        requestLayout();
    }

    public final void setLayoutStateDirection(int i) {
        boolean z;
        LayoutState layoutState = this.mLayoutState;
        layoutState.mLayoutDirection = i;
        boolean z2 = this.mShouldReverseLayout;
        int i2 = 1;
        if (i == -1) {
            z = true;
        } else {
            z = false;
        }
        if (z2 != z) {
            i2 = -1;
        }
        layoutState.mItemDirection = i2;
    }

    public final void smoothScrollToPosition(RecyclerView recyclerView, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
    }

    public final boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState == null) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateLayoutState(int r5, androidx.recyclerview.widget.RecyclerView.State r6) {
        /*
            r4 = this;
            androidx.recyclerview.widget.LayoutState r0 = r4.mLayoutState
            r1 = 0
            r0.mAvailable = r1
            r0.mCurrentPosition = r5
            boolean r0 = r4.isSmoothScrolling()
            r2 = 1
            if (r0 == 0) goto L_0x0030
            java.util.Objects.requireNonNull(r6)
            int r6 = r6.mTargetPosition
            r0 = -1
            if (r6 == r0) goto L_0x0030
            boolean r0 = r4.mShouldReverseLayout
            if (r6 >= r5) goto L_0x001c
            r5 = r2
            goto L_0x001d
        L_0x001c:
            r5 = r1
        L_0x001d:
            if (r0 != r5) goto L_0x0027
            androidx.recyclerview.widget.OrientationHelper r5 = r4.mPrimaryOrientation
            int r5 = r5.getTotalSpace()
            r6 = r1
            goto L_0x0032
        L_0x0027:
            androidx.recyclerview.widget.OrientationHelper r5 = r4.mPrimaryOrientation
            int r5 = r5.getTotalSpace()
            r6 = r5
            r5 = r1
            goto L_0x0032
        L_0x0030:
            r5 = r1
            r6 = r5
        L_0x0032:
            androidx.recyclerview.widget.RecyclerView r0 = r4.mRecyclerView
            if (r0 == 0) goto L_0x003c
            boolean r0 = r0.mClipToPadding
            if (r0 == 0) goto L_0x003c
            r0 = r2
            goto L_0x003d
        L_0x003c:
            r0 = r1
        L_0x003d:
            if (r0 == 0) goto L_0x0056
            androidx.recyclerview.widget.LayoutState r0 = r4.mLayoutState
            androidx.recyclerview.widget.OrientationHelper r3 = r4.mPrimaryOrientation
            int r3 = r3.getStartAfterPadding()
            int r3 = r3 - r6
            r0.mStartLine = r3
            androidx.recyclerview.widget.LayoutState r6 = r4.mLayoutState
            androidx.recyclerview.widget.OrientationHelper r0 = r4.mPrimaryOrientation
            int r0 = r0.getEndAfterPadding()
            int r0 = r0 + r5
            r6.mEndLine = r0
            goto L_0x0066
        L_0x0056:
            androidx.recyclerview.widget.LayoutState r0 = r4.mLayoutState
            androidx.recyclerview.widget.OrientationHelper r3 = r4.mPrimaryOrientation
            int r3 = r3.getEnd()
            int r3 = r3 + r5
            r0.mEndLine = r3
            androidx.recyclerview.widget.LayoutState r5 = r4.mLayoutState
            int r6 = -r6
            r5.mStartLine = r6
        L_0x0066:
            androidx.recyclerview.widget.LayoutState r5 = r4.mLayoutState
            r5.mStopInFocusable = r1
            r5.mRecycle = r2
            androidx.recyclerview.widget.OrientationHelper r6 = r4.mPrimaryOrientation
            int r6 = r6.getMode()
            if (r6 != 0) goto L_0x007d
            androidx.recyclerview.widget.OrientationHelper r4 = r4.mPrimaryOrientation
            int r4 = r4.getEnd()
            if (r4 != 0) goto L_0x007d
            r1 = r2
        L_0x007d:
            r5.mInfinite = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.updateLayoutState(int, androidx.recyclerview.widget.RecyclerView$State):void");
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mLazySpanLookup = new LazySpanLookup();
        this.mGapStrategy = 2;
        this.mTmpRect = new Rect();
        this.mAnchorInfo = new AnchorInfo();
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new Runnable() {
            public final void run() {
                StaggeredGridLayoutManager.this.checkForGaps();
            }
        };
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        int i3 = properties.orientation;
        if (i3 == 0 || i3 == 1) {
            assertNotInLayoutOrScroll((String) null);
            if (i3 != this.mOrientation) {
                this.mOrientation = i3;
                OrientationHelper orientationHelper = this.mPrimaryOrientation;
                this.mPrimaryOrientation = this.mSecondaryOrientation;
                this.mSecondaryOrientation = orientationHelper;
                requestLayout();
            }
            int i4 = properties.spanCount;
            assertNotInLayoutOrScroll((String) null);
            if (i4 != this.mSpanCount) {
                this.mLazySpanLookup.clear();
                requestLayout();
                this.mSpanCount = i4;
                this.mRemainingSpans = new BitSet(this.mSpanCount);
                this.mSpans = new Span[this.mSpanCount];
                for (int i5 = 0; i5 < this.mSpanCount; i5++) {
                    this.mSpans[i5] = new Span(i5);
                }
                requestLayout();
            }
            boolean z = properties.reverseLayout;
            assertNotInLayoutOrScroll((String) null);
            SavedState savedState = this.mPendingSavedState;
            if (!(savedState == null || savedState.mReverseLayout == z)) {
                savedState.mReverseLayout = z;
            }
            this.mReverseLayout = z;
            requestLayout();
            this.mLayoutState = new LayoutState();
            this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
            this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public final int calculateScrollDirectionForPosition(int i) {
        boolean z;
        if (getChildCount() != 0) {
            if (i < getFirstChildPosition()) {
                z = true;
            } else {
                z = false;
            }
            if (z != this.mShouldReverseLayout) {
                return -1;
            }
            return 1;
        } else if (this.mShouldReverseLayout) {
            return 1;
        } else {
            return -1;
        }
    }

    public final boolean checkForGaps() {
        int i;
        if (!(getChildCount() == 0 || this.mGapStrategy == 0 || !this.mIsAttachedToWindow)) {
            if (this.mShouldReverseLayout) {
                i = getLastChildPosition();
                getFirstChildPosition();
            } else {
                i = getFirstChildPosition();
                getLastChildPosition();
            }
            if (i == 0 && hasGapsToFix() != null) {
                this.mLazySpanLookup.clear();
                this.mRequestedSimpleAnimations = true;
                requestLayout();
                return true;
            }
        }
        return false;
    }

    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollExtent(state, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    public final int computeScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollOffset(state, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public final int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return ScrollbarHelper.computeScrollRange(state, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled), findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled), this, this.mSmoothScrollbarEnabled);
    }

    public final PointF computeScrollVectorForPosition(int i) {
        int calculateScrollDirectionForPosition = calculateScrollDirectionForPosition(i);
        PointF pointF = new PointF();
        if (calculateScrollDirectionForPosition == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = (float) calculateScrollDirectionForPosition;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = (float) calculateScrollDirectionForPosition;
        }
        return pointF;
    }

    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    public final void fixStartGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int minStart = getMinStart(Integer.MAX_VALUE);
        if (minStart != Integer.MAX_VALUE && (startAfterPadding = minStart - this.mPrimaryOrientation.getStartAfterPadding()) > 0) {
            int scrollBy = startAfterPadding - scrollBy(startAfterPadding, recycler, state);
            if (z && scrollBy > 0) {
                this.mPrimaryOrientation.offsetChildren(-scrollBy);
            }
        }
    }

    public final int getFirstChildPosition() {
        if (getChildCount() == 0) {
            return 0;
        }
        return RecyclerView.LayoutManager.getPosition(getChildAt(0));
    }

    public final int getLastChildPosition() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return RecyclerView.LayoutManager.getPosition(getChildAt(childCount - 1));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d7, code lost:
        if (r10 == r11) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e9, code lost:
        if (r10 == r11) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ed, code lost:
        r10 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00b5 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View hasGapsToFix() {
        /*
            r13 = this;
            int r0 = r13.getChildCount()
            r1 = 1
            int r0 = r0 - r1
            java.util.BitSet r2 = new java.util.BitSet
            int r3 = r13.mSpanCount
            r2.<init>(r3)
            int r3 = r13.mSpanCount
            r4 = 0
            r2.set(r4, r3, r1)
            int r3 = r13.mOrientation
            r5 = -1
            if (r3 != r1) goto L_0x0020
            boolean r3 = r13.isLayoutRTL()
            if (r3 == 0) goto L_0x0020
            r3 = r1
            goto L_0x0021
        L_0x0020:
            r3 = r5
        L_0x0021:
            boolean r6 = r13.mShouldReverseLayout
            if (r6 == 0) goto L_0x0027
            r6 = r5
            goto L_0x002b
        L_0x0027:
            int r0 = r0 + 1
            r6 = r0
            r0 = r4
        L_0x002b:
            if (r0 >= r6) goto L_0x002e
            r5 = r1
        L_0x002e:
            if (r0 == r6) goto L_0x010c
            android.view.View r7 = r13.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r8 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r8
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r8.mSpan
            int r9 = r9.mIndex
            boolean r9 = r2.get(r9)
            if (r9 == 0) goto L_0x00bd
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r8.mSpan
            boolean r10 = r13.mShouldReverseLayout
            r11 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r10 == 0) goto L_0x0076
            java.util.Objects.requireNonNull(r9)
            int r10 = r9.mCachedEnd
            if (r10 == r11) goto L_0x0054
            goto L_0x0059
        L_0x0054:
            r9.calculateCachedEnd()
            int r10 = r9.mCachedEnd
        L_0x0059:
            androidx.recyclerview.widget.OrientationHelper r11 = r13.mPrimaryOrientation
            int r11 = r11.getEndAfterPadding()
            if (r10 >= r11) goto L_0x00b2
            java.util.ArrayList<android.view.View> r9 = r9.mViews
            int r10 = r9.size()
            int r10 = r10 - r1
            java.lang.Object r9 = r9.get(r10)
            android.view.View r9 = (android.view.View) r9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r9 = androidx.recyclerview.widget.StaggeredGridLayoutManager.Span.getLayoutParams(r9)
            java.util.Objects.requireNonNull(r9)
            goto L_0x00b0
        L_0x0076:
            java.util.Objects.requireNonNull(r9)
            int r10 = r9.mCachedStart
            if (r10 == r11) goto L_0x007e
            goto L_0x0099
        L_0x007e:
            java.util.ArrayList<android.view.View> r10 = r9.mViews
            java.lang.Object r10 = r10.get(r4)
            android.view.View r10 = (android.view.View) r10
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r11 = androidx.recyclerview.widget.StaggeredGridLayoutManager.Span.getLayoutParams(r10)
            androidx.recyclerview.widget.StaggeredGridLayoutManager r12 = androidx.recyclerview.widget.StaggeredGridLayoutManager.this
            androidx.recyclerview.widget.OrientationHelper r12 = r12.mPrimaryOrientation
            int r10 = r12.getDecoratedStart(r10)
            r9.mCachedStart = r10
            java.util.Objects.requireNonNull(r11)
            int r10 = r9.mCachedStart
        L_0x0099:
            androidx.recyclerview.widget.OrientationHelper r11 = r13.mPrimaryOrientation
            int r11 = r11.getStartAfterPadding()
            if (r10 <= r11) goto L_0x00b2
            java.util.ArrayList<android.view.View> r9 = r9.mViews
            java.lang.Object r9 = r9.get(r4)
            android.view.View r9 = (android.view.View) r9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r9 = androidx.recyclerview.widget.StaggeredGridLayoutManager.Span.getLayoutParams(r9)
            java.util.Objects.requireNonNull(r9)
        L_0x00b0:
            r9 = r1
            goto L_0x00b3
        L_0x00b2:
            r9 = r4
        L_0x00b3:
            if (r9 == 0) goto L_0x00b6
            return r7
        L_0x00b6:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r8.mSpan
            int r9 = r9.mIndex
            r2.clear(r9)
        L_0x00bd:
            int r0 = r0 + r5
            if (r0 == r6) goto L_0x002e
            android.view.View r9 = r13.getChildAt(r0)
            boolean r10 = r13.mShouldReverseLayout
            if (r10 == 0) goto L_0x00da
            androidx.recyclerview.widget.OrientationHelper r10 = r13.mPrimaryOrientation
            int r10 = r10.getDecoratedEnd(r7)
            androidx.recyclerview.widget.OrientationHelper r11 = r13.mPrimaryOrientation
            int r11 = r11.getDecoratedEnd(r9)
            if (r10 >= r11) goto L_0x00d7
            return r7
        L_0x00d7:
            if (r10 != r11) goto L_0x00ed
            goto L_0x00eb
        L_0x00da:
            androidx.recyclerview.widget.OrientationHelper r10 = r13.mPrimaryOrientation
            int r10 = r10.getDecoratedStart(r7)
            androidx.recyclerview.widget.OrientationHelper r11 = r13.mPrimaryOrientation
            int r11 = r11.getDecoratedStart(r9)
            if (r10 <= r11) goto L_0x00e9
            return r7
        L_0x00e9:
            if (r10 != r11) goto L_0x00ed
        L_0x00eb:
            r10 = r1
            goto L_0x00ee
        L_0x00ed:
            r10 = r4
        L_0x00ee:
            if (r10 == 0) goto L_0x002e
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r9 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r8 = r8.mSpan
            int r8 = r8.mIndex
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r9 = r9.mSpan
            int r9 = r9.mIndex
            int r8 = r8 - r9
            if (r8 >= 0) goto L_0x0103
            r8 = r1
            goto L_0x0104
        L_0x0103:
            r8 = r4
        L_0x0104:
            if (r3 >= 0) goto L_0x0108
            r9 = r1
            goto L_0x0109
        L_0x0108:
            r9 = r4
        L_0x0109:
            if (r8 == r9) goto L_0x002e
            return r7
        L_0x010c:
            r13 = 0
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.hasGapsToFix():android.view.View");
    }

    public final boolean isLayoutRTL() {
        if (getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    public final void offsetChildrenHorizontal(int i) {
        super.offsetChildrenHorizontal(i);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            Span span = this.mSpans[i2];
            Objects.requireNonNull(span);
            int i3 = span.mCachedStart;
            if (i3 != Integer.MIN_VALUE) {
                span.mCachedStart = i3 + i;
            }
            int i4 = span.mCachedEnd;
            if (i4 != Integer.MIN_VALUE) {
                span.mCachedEnd = i4 + i;
            }
        }
    }

    public final void offsetChildrenVertical(int i) {
        super.offsetChildrenVertical(i);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            Span span = this.mSpans[i2];
            Objects.requireNonNull(span);
            int i3 = span.mCachedStart;
            if (i3 != Integer.MIN_VALUE) {
                span.mCachedStart = i3 + i;
            }
            int i4 = span.mCachedEnd;
            if (i4 != Integer.MIN_VALUE) {
                span.mCachedEnd = i4 + i;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0038, code lost:
        if (r8.mOrientation == 1) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003d, code lost:
        if (r8.mOrientation == 0) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004b, code lost:
        if (isLayoutRTL() == false) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0057, code lost:
        if (isLayoutRTL() == false) goto L_0x005b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x005e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View onFocusSearchFailed(android.view.View r9, int r10, androidx.recyclerview.widget.RecyclerView.Recycler r11, androidx.recyclerview.widget.RecyclerView.State r12) {
        /*
            r8 = this;
            int r0 = r8.getChildCount()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            android.view.View r9 = r8.findContainingItemView(r9)
            if (r9 != 0) goto L_0x000f
            return r1
        L_0x000f:
            r8.resolveShouldLayoutReverse()
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = 1
            r3 = -1
            if (r10 == r2) goto L_0x004e
            r4 = 2
            if (r10 == r4) goto L_0x0042
            r4 = 17
            if (r10 == r4) goto L_0x003b
            r4 = 33
            if (r10 == r4) goto L_0x0036
            r4 = 66
            if (r10 == r4) goto L_0x0031
            r4 = 130(0x82, float:1.82E-43)
            if (r10 == r4) goto L_0x002c
            goto L_0x0040
        L_0x002c:
            int r10 = r8.mOrientation
            if (r10 != r2) goto L_0x0040
            goto L_0x0059
        L_0x0031:
            int r10 = r8.mOrientation
            if (r10 != 0) goto L_0x0040
            goto L_0x0059
        L_0x0036:
            int r10 = r8.mOrientation
            if (r10 != r2) goto L_0x0040
            goto L_0x005b
        L_0x003b:
            int r10 = r8.mOrientation
            if (r10 != 0) goto L_0x0040
            goto L_0x005b
        L_0x0040:
            r10 = r0
            goto L_0x005c
        L_0x0042:
            int r10 = r8.mOrientation
            if (r10 != r2) goto L_0x0047
            goto L_0x0059
        L_0x0047:
            boolean r10 = r8.isLayoutRTL()
            if (r10 == 0) goto L_0x0059
            goto L_0x005b
        L_0x004e:
            int r10 = r8.mOrientation
            if (r10 != r2) goto L_0x0053
            goto L_0x005b
        L_0x0053:
            boolean r10 = r8.isLayoutRTL()
            if (r10 == 0) goto L_0x005b
        L_0x0059:
            r10 = r2
            goto L_0x005c
        L_0x005b:
            r10 = r3
        L_0x005c:
            if (r10 != r0) goto L_0x005f
            return r1
        L_0x005f:
            android.view.ViewGroup$LayoutParams r0 = r9.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LayoutParams r0 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) r0
            java.util.Objects.requireNonNull(r0)
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span r0 = r0.mSpan
            if (r10 != r2) goto L_0x0071
            int r4 = r8.getLastChildPosition()
            goto L_0x0075
        L_0x0071:
            int r4 = r8.getFirstChildPosition()
        L_0x0075:
            r8.updateLayoutState(r4, r12)
            r8.setLayoutStateDirection(r10)
            androidx.recyclerview.widget.LayoutState r5 = r8.mLayoutState
            int r6 = r5.mItemDirection
            int r6 = r6 + r4
            r5.mCurrentPosition = r6
            r6 = 1051372203(0x3eaaaaab, float:0.33333334)
            androidx.recyclerview.widget.OrientationHelper r7 = r8.mPrimaryOrientation
            int r7 = r7.getTotalSpace()
            float r7 = (float) r7
            float r7 = r7 * r6
            int r6 = (int) r7
            r5.mAvailable = r6
            androidx.recyclerview.widget.LayoutState r5 = r8.mLayoutState
            r5.mStopInFocusable = r2
            r6 = 0
            r5.mRecycle = r6
            r8.fill(r11, r5, r12)
            boolean r11 = r8.mShouldReverseLayout
            r8.mLastLayoutFromEnd = r11
            android.view.View r11 = r0.getFocusableViewAfter(r4, r10)
            if (r11 == 0) goto L_0x00a7
            if (r11 == r9) goto L_0x00a7
            return r11
        L_0x00a7:
            boolean r11 = r8.preferLastSpan(r10)
            if (r11 == 0) goto L_0x00c2
            int r11 = r8.mSpanCount
            int r11 = r11 - r2
        L_0x00b0:
            if (r11 < 0) goto L_0x00d7
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r12 = r8.mSpans
            r12 = r12[r11]
            android.view.View r12 = r12.getFocusableViewAfter(r4, r10)
            if (r12 == 0) goto L_0x00bf
            if (r12 == r9) goto L_0x00bf
            return r12
        L_0x00bf:
            int r11 = r11 + -1
            goto L_0x00b0
        L_0x00c2:
            r11 = r6
        L_0x00c3:
            int r12 = r8.mSpanCount
            if (r11 >= r12) goto L_0x00d7
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r12 = r8.mSpans
            r12 = r12[r11]
            android.view.View r12 = r12.getFocusableViewAfter(r4, r10)
            if (r12 == 0) goto L_0x00d4
            if (r12 == r9) goto L_0x00d4
            return r12
        L_0x00d4:
            int r11 = r11 + 1
            goto L_0x00c3
        L_0x00d7:
            boolean r11 = r8.mReverseLayout
            r11 = r11 ^ r2
            if (r10 != r3) goto L_0x00de
            r12 = r2
            goto L_0x00df
        L_0x00de:
            r12 = r6
        L_0x00df:
            if (r11 != r12) goto L_0x00e3
            r11 = r2
            goto L_0x00e4
        L_0x00e3:
            r11 = r6
        L_0x00e4:
            if (r11 == 0) goto L_0x00eb
            int r12 = r0.findFirstPartiallyVisibleItemPosition()
            goto L_0x00ef
        L_0x00eb:
            int r12 = r0.findLastPartiallyVisibleItemPosition()
        L_0x00ef:
            android.view.View r12 = r8.findViewByPosition(r12)
            if (r12 == 0) goto L_0x00f8
            if (r12 == r9) goto L_0x00f8
            return r12
        L_0x00f8:
            boolean r10 = r8.preferLastSpan(r10)
            if (r10 == 0) goto L_0x0127
            int r10 = r8.mSpanCount
            int r10 = r10 - r2
        L_0x0101:
            if (r10 < 0) goto L_0x014a
            int r12 = r0.mIndex
            if (r10 != r12) goto L_0x0108
            goto L_0x0124
        L_0x0108:
            if (r11 == 0) goto L_0x0113
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r12 = r8.mSpans
            r12 = r12[r10]
            int r12 = r12.findFirstPartiallyVisibleItemPosition()
            goto L_0x011b
        L_0x0113:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r12 = r8.mSpans
            r12 = r12[r10]
            int r12 = r12.findLastPartiallyVisibleItemPosition()
        L_0x011b:
            android.view.View r12 = r8.findViewByPosition(r12)
            if (r12 == 0) goto L_0x0124
            if (r12 == r9) goto L_0x0124
            return r12
        L_0x0124:
            int r10 = r10 + -1
            goto L_0x0101
        L_0x0127:
            int r10 = r8.mSpanCount
            if (r6 >= r10) goto L_0x014a
            if (r11 == 0) goto L_0x0136
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r10 = r8.mSpans
            r10 = r10[r6]
            int r10 = r10.findFirstPartiallyVisibleItemPosition()
            goto L_0x013e
        L_0x0136:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$Span[] r10 = r8.mSpans
            r10 = r10[r6]
            int r10 = r10.findLastPartiallyVisibleItemPosition()
        L_0x013e:
            android.view.View r10 = r8.findViewByPosition(r10)
            if (r10 == 0) goto L_0x0147
            if (r10 == r9) goto L_0x0147
            return r10
        L_0x0147:
            int r6 = r6 + 1
            goto L_0x0127
        L_0x014a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.onFocusSearchFailed(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):android.view.View");
    }

    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View findFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(false);
            View findFirstVisibleItemClosestToEnd = findFirstVisibleItemClosestToEnd(false);
            if (findFirstVisibleItemClosestToStart != null && findFirstVisibleItemClosestToEnd != null) {
                int position = RecyclerView.LayoutManager.getPosition(findFirstVisibleItemClosestToStart);
                int position2 = RecyclerView.LayoutManager.getPosition(findFirstVisibleItemClosestToEnd);
                if (position < position2) {
                    accessibilityEvent.setFromIndex(position);
                    accessibilityEvent.setToIndex(position2);
                    return;
                }
                accessibilityEvent.setFromIndex(position2);
                accessibilityEvent.setToIndex(position);
            }
        }
    }

    public final void recycleFromEnd(RecyclerView.Recycler recycler, int i) {
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            View childAt = getChildAt(childCount);
            if (this.mPrimaryOrientation.getDecoratedStart(childAt) >= i && this.mPrimaryOrientation.getTransformedStartWithDecoration(childAt) >= i) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                if (layoutParams.mSpan.mViews.size() != 1) {
                    Span span = layoutParams.mSpan;
                    Objects.requireNonNull(span);
                    int size = span.mViews.size();
                    View remove = span.mViews.remove(size - 1);
                    LayoutParams layoutParams2 = Span.getLayoutParams(remove);
                    layoutParams2.mSpan = null;
                    if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                        span.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(remove);
                    }
                    if (size == 1) {
                        span.mCachedStart = Integer.MIN_VALUE;
                    }
                    span.mCachedEnd = Integer.MIN_VALUE;
                    removeAndRecycleView(childAt, recycler);
                    childCount--;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final void recycleFromStart(RecyclerView.Recycler recycler, int i) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd(childAt) <= i && this.mPrimaryOrientation.getTransformedEndWithDecoration(childAt) <= i) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                if (layoutParams.mSpan.mViews.size() != 1) {
                    Span span = layoutParams.mSpan;
                    Objects.requireNonNull(span);
                    View remove = span.mViews.remove(0);
                    LayoutParams layoutParams2 = Span.getLayoutParams(remove);
                    layoutParams2.mSpan = null;
                    if (span.mViews.size() == 0) {
                        span.mCachedEnd = Integer.MIN_VALUE;
                    }
                    if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                        span.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(remove);
                    }
                    span.mCachedStart = Integer.MIN_VALUE;
                    removeAndRecycleView(childAt, recycler);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final int scrollBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        prepareLayoutStateForDelta(i, state);
        int fill = fill(recycler, this.mLayoutState, state);
        if (this.mLayoutState.mAvailable >= fill) {
            if (i < 0) {
                i = -fill;
            } else {
                i = fill;
            }
        }
        this.mPrimaryOrientation.offsetChildren(-i);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        LayoutState layoutState = this.mLayoutState;
        layoutState.mAvailable = 0;
        recycle(recycler, layoutState);
        return i;
    }

    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return scrollBy(i, recycler, state);
    }

    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return scrollBy(i, recycler, state);
    }

    public final void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.mOrientation == 1) {
            int height = rect.height() + paddingBottom;
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            i4 = RecyclerView.LayoutManager.chooseSize(i2, height, ViewCompat.Api16Impl.getMinimumHeight(recyclerView));
            i3 = RecyclerView.LayoutManager.chooseSize(i, (this.mSizePerSpan * this.mSpanCount) + paddingRight, ViewCompat.Api16Impl.getMinimumWidth(this.mRecyclerView));
        } else {
            int width = rect.width() + paddingRight;
            RecyclerView recyclerView2 = this.mRecyclerView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            i3 = RecyclerView.LayoutManager.chooseSize(i, width, ViewCompat.Api16Impl.getMinimumWidth(recyclerView2));
            i4 = RecyclerView.LayoutManager.chooseSize(i2, (this.mSizePerSpan * this.mSpanCount) + paddingBottom, ViewCompat.Api16Impl.getMinimumHeight(this.mRecyclerView));
        }
        this.mRecyclerView.setMeasuredDimension(i3, i4);
    }

    public final void updateRemainingSpans(Span span, int i, int i2) {
        Objects.requireNonNull(span);
        int i3 = span.mDeletedSize;
        if (i == -1) {
            int i4 = span.mCachedStart;
            if (i4 == Integer.MIN_VALUE) {
                View view = span.mViews.get(0);
                LayoutParams layoutParams = Span.getLayoutParams(view);
                span.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                Objects.requireNonNull(layoutParams);
                i4 = span.mCachedStart;
            }
            if (i4 + i3 <= i2) {
                this.mRemainingSpans.set(span.mIndex, false);
                return;
            }
            return;
        }
        int i5 = span.mCachedEnd;
        if (i5 == Integer.MIN_VALUE) {
            span.calculateCachedEnd();
            i5 = span.mCachedEnd;
        }
        if (i5 - i3 >= i2) {
            this.mRemainingSpans.set(span.mIndex, false);
        }
    }

    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }
}
