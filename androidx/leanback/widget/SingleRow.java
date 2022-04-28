package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;
import androidx.leanback.widget.GridLayoutManager;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

public final class SingleRow extends Grid {
    public final Grid.Location mTmpLocation = new Grid.Location(0);

    public final boolean appendVisibleItems(int i, boolean z) {
        int i2;
        int i3;
        if (((GridLayoutManager.C02232) this.mProvider).getCount() == 0) {
            return false;
        }
        if (!z && checkAppendOverLimit(i)) {
            return false;
        }
        int startIndexForAppend = getStartIndexForAppend();
        boolean z2 = false;
        while (startIndexForAppend < ((GridLayoutManager.C02232) this.mProvider).getCount()) {
            int createItem = ((GridLayoutManager.C02232) this.mProvider).createItem(startIndexForAppend, true, this.mTmpItem, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                if (this.mReversedFlow) {
                    i3 = Integer.MAX_VALUE;
                } else {
                    i3 = Integer.MIN_VALUE;
                }
                this.mFirstVisibleIndex = startIndexForAppend;
                this.mLastVisibleIndex = startIndexForAppend;
            } else {
                if (this.mReversedFlow) {
                    int i4 = startIndexForAppend - 1;
                    i2 = (((GridLayoutManager.C02232) this.mProvider).getEdge(i4) - ((GridLayoutManager.C02232) this.mProvider).getSize(i4)) - this.mSpacing;
                } else {
                    int i5 = startIndexForAppend - 1;
                    i2 = this.mSpacing + ((GridLayoutManager.C02232) this.mProvider).getSize(i5) + ((GridLayoutManager.C02232) this.mProvider).getEdge(i5);
                }
                this.mLastVisibleIndex = startIndexForAppend;
            }
            ((GridLayoutManager.C02232) this.mProvider).addItem(this.mTmpItem[0], createItem, 0, i2);
            if (z || checkAppendOverLimit(i)) {
                return true;
            }
            startIndexForAppend++;
            z2 = true;
        }
        return z2;
    }

    public final void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i3;
        int i4;
        if (!this.mReversedFlow ? i2 >= 0 : i2 <= 0) {
            if (this.mLastVisibleIndex != ((GridLayoutManager.C02232) this.mProvider).getCount() - 1) {
                i3 = getStartIndexForAppend();
                int size = ((GridLayoutManager.C02232) this.mProvider).getSize(this.mLastVisibleIndex) + this.mSpacing;
                int edge = ((GridLayoutManager.C02232) this.mProvider).getEdge(this.mLastVisibleIndex);
                if (this.mReversedFlow) {
                    size = -size;
                }
                i4 = size + edge;
            } else {
                return;
            }
        } else if (this.mFirstVisibleIndex != 0) {
            i3 = getStartIndexForPrepend();
            int edge2 = ((GridLayoutManager.C02232) this.mProvider).getEdge(this.mFirstVisibleIndex);
            boolean z = this.mReversedFlow;
            int i5 = this.mSpacing;
            if (!z) {
                i5 = -i5;
            }
            i4 = edge2 + i5;
        } else {
            return;
        }
        ((GapWorker.LayoutPrefetchRegistryImpl) layoutPrefetchRegistry).addPosition(i3, Math.abs(i4 - i));
    }

    public final int findRowMax(boolean z, int i, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        if (this.mReversedFlow) {
            return ((GridLayoutManager.C02232) this.mProvider).getEdge(i);
        }
        return ((GridLayoutManager.C02232) this.mProvider).getSize(i) + ((GridLayoutManager.C02232) this.mProvider).getEdge(i);
    }

    public final int findRowMin(boolean z, int i, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        if (this.mReversedFlow) {
            return ((GridLayoutManager.C02232) this.mProvider).getEdge(i) - ((GridLayoutManager.C02232) this.mProvider).getSize(i);
        }
        return ((GridLayoutManager.C02232) this.mProvider).getEdge(i);
    }

    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        CircularIntArray circularIntArray = this.mTmpItemPositionsInRows[0];
        Objects.requireNonNull(circularIntArray);
        circularIntArray.mTail = 0;
        this.mTmpItemPositionsInRows[0].addLast(i);
        this.mTmpItemPositionsInRows[0].addLast(i2);
        return this.mTmpItemPositionsInRows;
    }

    public final int getStartIndexForAppend() {
        int i = this.mLastVisibleIndex;
        if (i >= 0) {
            return i + 1;
        }
        int i2 = this.mStartIndex;
        if (i2 != -1) {
            return Math.min(i2, ((GridLayoutManager.C02232) this.mProvider).getCount() - 1);
        }
        return 0;
    }

    public final int getStartIndexForPrepend() {
        int i = this.mFirstVisibleIndex;
        if (i >= 0) {
            return i - 1;
        }
        int i2 = this.mStartIndex;
        if (i2 != -1) {
            return Math.min(i2, ((GridLayoutManager.C02232) this.mProvider).getCount() - 1);
        }
        return ((GridLayoutManager.C02232) this.mProvider).getCount() - 1;
    }

    public final boolean prependVisibleItems(int i, boolean z) {
        int i2;
        int i3;
        if (((GridLayoutManager.C02232) this.mProvider).getCount() == 0) {
            return false;
        }
        if (!z && checkPrependOverLimit(i)) {
            return false;
        }
        GridLayoutManager.C02232 r0 = (GridLayoutManager.C02232) this.mProvider;
        Objects.requireNonNull(r0);
        int i4 = GridLayoutManager.this.mPositionDeltaInPreLayout;
        int startIndexForPrepend = getStartIndexForPrepend();
        boolean z2 = false;
        while (startIndexForPrepend >= i4) {
            int createItem = ((GridLayoutManager.C02232) this.mProvider).createItem(startIndexForPrepend, false, this.mTmpItem, false);
            if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                if (this.mReversedFlow) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i3 = Integer.MAX_VALUE;
                }
                this.mFirstVisibleIndex = startIndexForPrepend;
                this.mLastVisibleIndex = startIndexForPrepend;
            } else {
                if (this.mReversedFlow) {
                    i2 = ((GridLayoutManager.C02232) this.mProvider).getEdge(startIndexForPrepend + 1) + this.mSpacing + createItem;
                } else {
                    i2 = (((GridLayoutManager.C02232) this.mProvider).getEdge(startIndexForPrepend + 1) - this.mSpacing) - createItem;
                }
                this.mFirstVisibleIndex = startIndexForPrepend;
            }
            ((GridLayoutManager.C02232) this.mProvider).addItem(this.mTmpItem[0], createItem, 0, i2);
            if (z || checkPrependOverLimit(i)) {
                return true;
            }
            startIndexForPrepend--;
            z2 = true;
        }
        return z2;
    }

    public SingleRow() {
        setNumRows(1);
    }

    public final Grid.Location getLocation(int i) {
        return this.mTmpLocation;
    }
}
