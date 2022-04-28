package androidx.viewpager2.widget;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Objects;

public final class ScrollEventAdapter extends RecyclerView.OnScrollListener {
    public int mAdapterState;
    public ViewPager2.OnPageChangeCallback mCallback;
    public boolean mDataSetChangeHappened;
    public boolean mDispatchSelected;
    public int mDragStartPosition;
    public boolean mFakeDragging;
    public final LinearLayoutManager mLayoutManager;
    public final RecyclerView mRecyclerView;
    public boolean mScrollHappened;
    public int mScrollState;
    public ScrollEventValues mScrollValues = new ScrollEventValues();
    public int mTarget;
    public final ViewPager2 mViewPager;

    public static final class ScrollEventValues {
        public float mOffset;
        public int mOffsetPx;
        public int mPosition;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0027, code lost:
        if (r6 == r7) goto L_0x002c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onScrolled(androidx.recyclerview.widget.RecyclerView r5, int r6, int r7) {
        /*
            r4 = this;
            r5 = 1
            r4.mScrollHappened = r5
            r4.updateScrollEventValues()
            boolean r0 = r4.mDispatchSelected
            r1 = 0
            r2 = -1
            if (r0 == 0) goto L_0x004b
            r4.mDispatchSelected = r1
            if (r7 > 0) goto L_0x002c
            if (r7 != 0) goto L_0x002a
            if (r6 >= 0) goto L_0x0016
            r6 = r5
            goto L_0x0017
        L_0x0016:
            r6 = r1
        L_0x0017:
            androidx.viewpager2.widget.ViewPager2 r7 = r4.mViewPager
            java.util.Objects.requireNonNull(r7)
            androidx.viewpager2.widget.ViewPager2$LinearLayoutManagerImpl r7 = r7.mLayoutManager
            int r7 = r7.getLayoutDirection()
            if (r7 != r5) goto L_0x0026
            r7 = r5
            goto L_0x0027
        L_0x0026:
            r7 = r1
        L_0x0027:
            if (r6 != r7) goto L_0x002a
            goto L_0x002c
        L_0x002a:
            r6 = r1
            goto L_0x002d
        L_0x002c:
            r6 = r5
        L_0x002d:
            if (r6 == 0) goto L_0x0039
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r6 = r4.mScrollValues
            int r7 = r6.mOffsetPx
            if (r7 == 0) goto L_0x0039
            int r6 = r6.mPosition
            int r6 = r6 + r5
            goto L_0x003d
        L_0x0039:
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r6 = r4.mScrollValues
            int r6 = r6.mPosition
        L_0x003d:
            r4.mTarget = r6
            int r7 = r4.mDragStartPosition
            if (r7 == r6) goto L_0x005d
            androidx.viewpager2.widget.ViewPager2$OnPageChangeCallback r7 = r4.mCallback
            if (r7 == 0) goto L_0x005d
            r7.onPageSelected(r6)
            goto L_0x005d
        L_0x004b:
            int r6 = r4.mAdapterState
            if (r6 != 0) goto L_0x005d
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r6 = r4.mScrollValues
            int r6 = r6.mPosition
            if (r6 != r2) goto L_0x0056
            r6 = r1
        L_0x0056:
            androidx.viewpager2.widget.ViewPager2$OnPageChangeCallback r7 = r4.mCallback
            if (r7 == 0) goto L_0x005d
            r7.onPageSelected(r6)
        L_0x005d:
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r6 = r4.mScrollValues
            int r7 = r6.mPosition
            if (r7 != r2) goto L_0x0064
            r7 = r1
        L_0x0064:
            float r0 = r6.mOffset
            int r6 = r6.mOffsetPx
            androidx.viewpager2.widget.ViewPager2$OnPageChangeCallback r3 = r4.mCallback
            if (r3 == 0) goto L_0x006f
            r3.onPageScrolled(r7, r0, r6)
        L_0x006f:
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r6 = r4.mScrollValues
            int r7 = r6.mPosition
            int r0 = r4.mTarget
            if (r7 == r0) goto L_0x0079
            if (r0 != r2) goto L_0x0087
        L_0x0079:
            int r6 = r6.mOffsetPx
            if (r6 != 0) goto L_0x0087
            int r6 = r4.mScrollState
            if (r6 == r5) goto L_0x0087
            r4.dispatchStateChanged(r1)
            r4.resetState()
        L_0x0087:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.ScrollEventAdapter.onScrolled(androidx.recyclerview.widget.RecyclerView, int, int):void");
    }

    public final void resetState() {
        this.mAdapterState = 0;
        this.mScrollState = 0;
        ScrollEventValues scrollEventValues = this.mScrollValues;
        Objects.requireNonNull(scrollEventValues);
        scrollEventValues.mPosition = -1;
        scrollEventValues.mOffset = 0.0f;
        scrollEventValues.mOffsetPx = 0;
        this.mDragStartPosition = -1;
        this.mTarget = -1;
        this.mDispatchSelected = false;
        this.mScrollHappened = false;
        this.mFakeDragging = false;
        this.mDataSetChangeHappened = false;
    }

    public final void dispatchStateChanged(int i) {
        if ((this.mAdapterState != 3 || this.mScrollState != 0) && this.mScrollState != i) {
            this.mScrollState = i;
            ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
            if (onPageChangeCallback != null) {
                onPageChangeCallback.onPageScrollStateChanged(i);
            }
        }
    }

    public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
        boolean z;
        boolean z2;
        ViewPager2.OnPageChangeCallback onPageChangeCallback;
        ViewPager2.OnPageChangeCallback onPageChangeCallback2;
        int i2 = this.mAdapterState;
        boolean z3 = true;
        if (!(i2 == 1 && this.mScrollState == 1) && i == 1) {
            this.mFakeDragging = false;
            this.mAdapterState = 1;
            int i3 = this.mTarget;
            if (i3 != -1) {
                this.mDragStartPosition = i3;
                this.mTarget = -1;
            } else if (this.mDragStartPosition == -1) {
                this.mDragStartPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            }
            dispatchStateChanged(1);
            return;
        }
        if (i2 == 1 || i2 == 4) {
            z = true;
        } else {
            z = false;
        }
        if (!z || i != 2) {
            if (i2 == 1 || i2 == 4) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 && i == 0) {
                updateScrollEventValues();
                if (!this.mScrollHappened) {
                    int i4 = this.mScrollValues.mPosition;
                    if (!(i4 == -1 || (onPageChangeCallback2 = this.mCallback) == null)) {
                        onPageChangeCallback2.onPageScrolled(i4, 0.0f, 0);
                    }
                } else {
                    ScrollEventValues scrollEventValues = this.mScrollValues;
                    if (scrollEventValues.mOffsetPx == 0) {
                        int i5 = this.mDragStartPosition;
                        int i6 = scrollEventValues.mPosition;
                        if (!(i5 == i6 || (onPageChangeCallback = this.mCallback) == null)) {
                            onPageChangeCallback.onPageSelected(i6);
                        }
                    } else {
                        z3 = false;
                    }
                }
                if (z3) {
                    dispatchStateChanged(0);
                    resetState();
                }
            }
            if (this.mAdapterState == 2 && i == 0 && this.mDataSetChangeHappened) {
                updateScrollEventValues();
                ScrollEventValues scrollEventValues2 = this.mScrollValues;
                if (scrollEventValues2.mOffsetPx == 0) {
                    int i7 = this.mTarget;
                    int i8 = scrollEventValues2.mPosition;
                    if (i7 != i8) {
                        if (i8 == -1) {
                            i8 = 0;
                        }
                        ViewPager2.OnPageChangeCallback onPageChangeCallback3 = this.mCallback;
                        if (onPageChangeCallback3 != null) {
                            onPageChangeCallback3.onPageSelected(i8);
                        }
                    }
                    dispatchStateChanged(0);
                    resetState();
                }
            }
        } else if (this.mScrollHappened) {
            dispatchStateChanged(2);
            this.mDispatchSelected = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0168, code lost:
        if (r4[r12 - 1][1] >= r2) goto L_0x016b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0181  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0195  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x019a  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01a2  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0192 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateScrollEventValues() {
        /*
            r12 = this;
            androidx.viewpager2.widget.ScrollEventAdapter$ScrollEventValues r0 = r12.mScrollValues
            androidx.recyclerview.widget.LinearLayoutManager r1 = r12.mLayoutManager
            int r1 = r1.findFirstVisibleItemPosition()
            r0.mPosition = r1
            r2 = 0
            r3 = 0
            r4 = -1
            if (r1 != r4) goto L_0x0016
            r0.mPosition = r4
            r0.mOffset = r2
            r0.mOffsetPx = r3
            return
        L_0x0016:
            androidx.recyclerview.widget.LinearLayoutManager r5 = r12.mLayoutManager
            android.view.View r1 = r5.findViewByPosition(r1)
            if (r1 != 0) goto L_0x0025
            r0.mPosition = r4
            r0.mOffset = r2
            r0.mOffsetPx = r3
            return
        L_0x0025:
            androidx.recyclerview.widget.LinearLayoutManager r4 = r12.mLayoutManager
            java.util.Objects.requireNonNull(r4)
            android.view.ViewGroup$LayoutParams r4 = r1.getLayoutParams()
            androidx.recyclerview.widget.RecyclerView$LayoutParams r4 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r4
            android.graphics.Rect r4 = r4.mDecorInsets
            int r4 = r4.left
            androidx.recyclerview.widget.LinearLayoutManager r5 = r12.mLayoutManager
            java.util.Objects.requireNonNull(r5)
            android.view.ViewGroup$LayoutParams r5 = r1.getLayoutParams()
            androidx.recyclerview.widget.RecyclerView$LayoutParams r5 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r5
            android.graphics.Rect r5 = r5.mDecorInsets
            int r5 = r5.right
            androidx.recyclerview.widget.LinearLayoutManager r6 = r12.mLayoutManager
            java.util.Objects.requireNonNull(r6)
            android.view.ViewGroup$LayoutParams r6 = r1.getLayoutParams()
            androidx.recyclerview.widget.RecyclerView$LayoutParams r6 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r6
            android.graphics.Rect r6 = r6.mDecorInsets
            int r6 = r6.top
            androidx.recyclerview.widget.LinearLayoutManager r7 = r12.mLayoutManager
            java.util.Objects.requireNonNull(r7)
            android.view.ViewGroup$LayoutParams r7 = r1.getLayoutParams()
            androidx.recyclerview.widget.RecyclerView$LayoutParams r7 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r7
            android.graphics.Rect r7 = r7.mDecorInsets
            int r7 = r7.bottom
            android.view.ViewGroup$LayoutParams r8 = r1.getLayoutParams()
            boolean r9 = r8 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r9 == 0) goto L_0x0077
            android.view.ViewGroup$MarginLayoutParams r8 = (android.view.ViewGroup.MarginLayoutParams) r8
            int r9 = r8.leftMargin
            int r4 = r4 + r9
            int r9 = r8.rightMargin
            int r5 = r5 + r9
            int r9 = r8.topMargin
            int r6 = r6 + r9
            int r8 = r8.bottomMargin
            int r7 = r7 + r8
        L_0x0077:
            int r8 = r1.getHeight()
            int r8 = r8 + r6
            int r8 = r8 + r7
            int r7 = r1.getWidth()
            int r7 = r7 + r4
            int r7 = r7 + r5
            androidx.recyclerview.widget.LinearLayoutManager r5 = r12.mLayoutManager
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mOrientation
            r9 = 1
            if (r5 != 0) goto L_0x008f
            r5 = r9
            goto L_0x0090
        L_0x008f:
            r5 = r3
        L_0x0090:
            if (r5 == 0) goto L_0x00b3
            int r1 = r1.getLeft()
            int r1 = r1 - r4
            androidx.recyclerview.widget.RecyclerView r4 = r12.mRecyclerView
            int r4 = r4.getPaddingLeft()
            int r1 = r1 - r4
            androidx.viewpager2.widget.ViewPager2 r4 = r12.mViewPager
            java.util.Objects.requireNonNull(r4)
            androidx.viewpager2.widget.ViewPager2$LinearLayoutManagerImpl r4 = r4.mLayoutManager
            int r4 = r4.getLayoutDirection()
            if (r4 != r9) goto L_0x00ad
            r4 = r9
            goto L_0x00ae
        L_0x00ad:
            r4 = r3
        L_0x00ae:
            if (r4 == 0) goto L_0x00b1
            int r1 = -r1
        L_0x00b1:
            r8 = r7
            goto L_0x00bf
        L_0x00b3:
            int r1 = r1.getTop()
            int r1 = r1 - r6
            androidx.recyclerview.widget.RecyclerView r4 = r12.mRecyclerView
            int r4 = r4.getPaddingTop()
            int r1 = r1 - r4
        L_0x00bf:
            int r1 = -r1
            r0.mOffsetPx = r1
            if (r1 >= 0) goto L_0x01ba
            androidx.viewpager2.widget.AnimateLayoutChangeDetector r1 = new androidx.viewpager2.widget.AnimateLayoutChangeDetector
            androidx.recyclerview.widget.LinearLayoutManager r12 = r12.mLayoutManager
            r1.<init>(r12)
            int r12 = r12.getChildCount()
            if (r12 != 0) goto L_0x00d3
            goto L_0x016b
        L_0x00d3:
            androidx.recyclerview.widget.LinearLayoutManager r2 = r1.mLayoutManager
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mOrientation
            if (r2 != 0) goto L_0x00de
            r2 = r9
            goto L_0x00df
        L_0x00de:
            r2 = r3
        L_0x00df:
            r4 = 2
            int[] r5 = new int[r4]
            r5[r9] = r4
            r5[r3] = r12
            java.lang.Class<int> r4 = int.class
            java.lang.Object r4 = java.lang.reflect.Array.newInstance(r4, r5)
            int[][] r4 = (int[][]) r4
            r5 = r3
        L_0x00ef:
            if (r5 >= r12) goto L_0x0139
            androidx.recyclerview.widget.LinearLayoutManager r6 = r1.mLayoutManager
            android.view.View r6 = r6.getChildAt(r5)
            if (r6 == 0) goto L_0x0131
            android.view.ViewGroup$LayoutParams r7 = r6.getLayoutParams()
            boolean r8 = r7 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r8 == 0) goto L_0x0104
            android.view.ViewGroup$MarginLayoutParams r7 = (android.view.ViewGroup.MarginLayoutParams) r7
            goto L_0x0106
        L_0x0104:
            android.view.ViewGroup$MarginLayoutParams r7 = androidx.viewpager2.widget.AnimateLayoutChangeDetector.ZERO_MARGIN_LAYOUT_PARAMS
        L_0x0106:
            r8 = r4[r5]
            if (r2 == 0) goto L_0x0111
            int r10 = r6.getLeft()
            int r11 = r7.leftMargin
            goto L_0x0117
        L_0x0111:
            int r10 = r6.getTop()
            int r11 = r7.topMargin
        L_0x0117:
            int r10 = r10 - r11
            r8[r3] = r10
            r8 = r4[r5]
            if (r2 == 0) goto L_0x0125
            int r6 = r6.getRight()
            int r7 = r7.rightMargin
            goto L_0x012b
        L_0x0125:
            int r6 = r6.getBottom()
            int r7 = r7.bottomMargin
        L_0x012b:
            int r6 = r6 + r7
            r8[r9] = r6
            int r5 = r5 + 1
            goto L_0x00ef
        L_0x0131:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "null view contained in the view hierarchy"
            r12.<init>(r0)
            throw r12
        L_0x0139:
            androidx.viewpager2.widget.AnimateLayoutChangeDetector$1 r2 = new androidx.viewpager2.widget.AnimateLayoutChangeDetector$1
            r2.<init>()
            java.util.Arrays.sort(r4, r2)
            r2 = r9
        L_0x0142:
            if (r2 >= r12) goto L_0x0154
            int r5 = r2 + -1
            r5 = r4[r5]
            r5 = r5[r9]
            r6 = r4[r2]
            r6 = r6[r3]
            if (r5 == r6) goto L_0x0151
            goto L_0x016d
        L_0x0151:
            int r2 = r2 + 1
            goto L_0x0142
        L_0x0154:
            r2 = r4[r3]
            r2 = r2[r9]
            r5 = r4[r3]
            r5 = r5[r3]
            int r2 = r2 - r5
            r5 = r4[r3]
            r5 = r5[r3]
            if (r5 > 0) goto L_0x016d
            int r12 = r12 - r9
            r12 = r4[r12]
            r12 = r12[r9]
            if (r12 >= r2) goto L_0x016b
            goto L_0x016d
        L_0x016b:
            r12 = r9
            goto L_0x016e
        L_0x016d:
            r12 = r3
        L_0x016e:
            if (r12 == 0) goto L_0x0178
            androidx.recyclerview.widget.LinearLayoutManager r12 = r1.mLayoutManager
            int r12 = r12.getChildCount()
            if (r12 > r9) goto L_0x0197
        L_0x0178:
            androidx.recyclerview.widget.LinearLayoutManager r12 = r1.mLayoutManager
            int r12 = r12.getChildCount()
            r2 = r3
        L_0x017f:
            if (r2 >= r12) goto L_0x0192
            androidx.recyclerview.widget.LinearLayoutManager r4 = r1.mLayoutManager
            android.view.View r4 = r4.getChildAt(r2)
            boolean r4 = androidx.viewpager2.widget.AnimateLayoutChangeDetector.hasRunningChangingLayoutTransition(r4)
            if (r4 == 0) goto L_0x018f
            r12 = r9
            goto L_0x0193
        L_0x018f:
            int r2 = r2 + 1
            goto L_0x017f
        L_0x0192:
            r12 = r3
        L_0x0193:
            if (r12 == 0) goto L_0x0197
            r12 = r9
            goto L_0x0198
        L_0x0197:
            r12 = r3
        L_0x0198:
            if (r12 == 0) goto L_0x01a2
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started."
            r12.<init>(r0)
            throw r12
        L_0x01a2:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.util.Locale r1 = java.util.Locale.US
            java.lang.Object[] r2 = new java.lang.Object[r9]
            int r0 = r0.mOffsetPx
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2[r3] = r0
            java.lang.String r0 = "Page can only be offset by a positive amount, not by %d"
            java.lang.String r0 = java.lang.String.format(r1, r0, r2)
            r12.<init>(r0)
            throw r12
        L_0x01ba:
            if (r8 != 0) goto L_0x01bd
            goto L_0x01c1
        L_0x01bd:
            float r12 = (float) r1
            float r1 = (float) r8
            float r2 = r12 / r1
        L_0x01c1:
            r0.mOffset = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.ScrollEventAdapter.updateScrollEventValues():void");
    }

    public ScrollEventAdapter(ViewPager2 viewPager2) {
        this.mViewPager = viewPager2;
        ViewPager2.RecyclerViewImpl recyclerViewImpl = viewPager2.mRecyclerView;
        this.mRecyclerView = recyclerViewImpl;
        Objects.requireNonNull(recyclerViewImpl);
        this.mLayoutManager = (LinearLayoutManager) recyclerViewImpl.mLayout;
        resetState();
    }
}
