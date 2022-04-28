package androidx.recyclerview.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat$Api31Impl;
import androidx.customview.view.AbsSavedState;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.leanback.widget.GridLayoutManager;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public class RecyclerView extends ViewGroup implements NestedScrollingChild {
    public static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    public static final int[] NESTED_SCROLLING_ATTRS = {16843830};
    public static final StretchEdgeEffectFactory sDefaultEdgeEffectFactory = new StretchEdgeEffectFactory();
    public static final C03343 sQuinticInterpolator = new Interpolator() {
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public Adapter mAdapter;
    public AdapterHelper mAdapterHelper;
    public boolean mAdapterUpdateDuringMeasure;
    public EdgeEffect mBottomGlow;
    public ChildDrawingOrderCallback mChildDrawingOrderCallback;
    public ChildHelper mChildHelper;
    public boolean mClipToPadding;
    public boolean mDataSetHasChangedAfterLayout;
    public boolean mDispatchItemsChangedEvent;
    public int mDispatchScrollCounter;
    public int mEatenAccessibilityChangeFlags;
    public StretchEdgeEffectFactory mEdgeEffectFactory;
    public boolean mFirstLayoutComplete;
    public GapWorker mGapWorker;
    public boolean mHasFixedSize;
    public boolean mIgnoreMotionEventTillDown;
    public int mInitialTouchX;
    public int mInitialTouchY;
    public int mInterceptRequestLayoutDepth;
    public OnItemTouchListener mInterceptingOnItemTouchListener;
    public boolean mIsAttached;
    public ItemAnimator mItemAnimator;
    public ItemAnimatorRestoreListener mItemAnimatorListener;
    public C03332 mItemAnimatorRunner;
    public final ArrayList<ItemDecoration> mItemDecorations;
    public boolean mItemsAddedOrRemoved;
    public boolean mItemsChanged;
    public int mLastAutoMeasureNonExactMeasuredHeight;
    public int mLastAutoMeasureNonExactMeasuredWidth;
    public boolean mLastAutoMeasureSkippedDueToExact;
    public int mLastTouchX;
    public int mLastTouchY;
    public LayoutManager mLayout;
    public int mLayoutOrScrollCounter;
    public boolean mLayoutSuppressed;
    public boolean mLayoutWasDefered;
    public EdgeEffect mLeftGlow;
    public final int mMaxFlingVelocity;
    public final int mMinFlingVelocity;
    public final int[] mMinMaxLayoutPositions;
    public final int[] mNestedOffsets;
    public final RecyclerViewDataObserver mObserver;
    public ArrayList mOnChildAttachStateListeners;
    public OnFlingListener mOnFlingListener;
    public final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
    public final List<ViewHolder> mPendingAccessibilityImportanceChange;
    public SavedState mPendingSavedState;
    public boolean mPostedAnimatorRunner;
    public GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    public boolean mPreserveFocusAfterLayout;
    public final Recycler mRecycler;
    public final ArrayList mRecyclerListeners;
    public final int[] mReusableIntPair;
    public EdgeEffect mRightGlow;
    public float mScaledHorizontalScrollFactor;
    public float mScaledVerticalScrollFactor;
    public ArrayList mScrollListeners;
    public final int[] mScrollOffset;
    public int mScrollPointerId;
    public int mScrollState;
    public NestedScrollingChildHelper mScrollingChildHelper;
    public final State mState;
    public final Rect mTempRect;
    public final Rect mTempRect2;
    public final RectF mTempRectF;
    public EdgeEffect mTopGlow;
    public int mTouchSlop;
    public final C03321 mUpdateChildViewsRunnable;
    public VelocityTracker mVelocityTracker;
    public final ViewFlinger mViewFlinger;
    public final C03354 mViewInfoProcessCallback;
    public final ViewInfoStore mViewInfoStore;

    public static abstract class Adapter<VH extends ViewHolder> {
        public boolean mHasStableIds = false;
        public final AdapterDataObservable mObservable = new AdapterDataObservable();
        public StateRestorationPolicy mStateRestorationPolicy = StateRestorationPolicy.ALLOW;

        public enum StateRestorationPolicy {
            ALLOW
        }

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH vh, int i);

        public abstract ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i);

        public void onDetachedFromRecyclerView() {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewRecycled(VH vh) {
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int i) {
            AdapterDataObservable adapterDataObservable = this.mObservable;
            Objects.requireNonNull(adapterDataObservable);
            adapterDataObservable.notifyItemRangeChanged(i, 1, (Object) null);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.notifyItemMoved(i, i2);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.notifyItemRangeInserted(i, i2);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.notifyItemRangeRemoved(i, i2);
        }

        public final void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver(adapterDataObserver);
        }

        public final void setHasStableIds(boolean z) {
            if (!this.mObservable.hasObservers()) {
                this.mHasStableIds = z;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public void onBindViewHolder(VH vh, int i, List<Object> list) {
            onBindViewHolder(vh, i);
        }
    }

    public static class AdapterDataObservable extends Observable<AdapterDataObserver> {
        public final boolean hasObservers() {
            return !this.mObservers.isEmpty();
        }

        public final void notifyChanged() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onChanged();
            }
        }

        public final void notifyItemMoved(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeMoved(i, i2);
            }
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeChanged(i, i2, obj);
            }
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeInserted(i, i2);
            }
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((AdapterDataObserver) this.mObservers.get(size)).onItemRangeRemoved(i, i2);
            }
        }
    }

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder();
    }

    public static class EdgeEffectFactory {
    }

    public static abstract class ItemAnimator {
        public long mAddDuration = 120;
        public long mChangeDuration = 250;
        public ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList<>();
        public ItemAnimatorListener mListener = null;
        public long mMoveDuration = 250;
        public long mRemoveDuration = 120;

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        public interface ItemAnimatorListener {
        }

        public static class ItemHolderInfo {
            public int left;
            public int top;

            public final ItemHolderInfo setFrom(ViewHolder viewHolder) {
                View view = viewHolder.itemView;
                this.left = view.getLeft();
                this.top = view.getTop();
                view.getRight();
                view.getBottom();
                return this;
            }
        }

        public abstract boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, ItemHolderInfo itemHolderInfo, ItemHolderInfo itemHolderInfo2);

        public abstract void endAnimation(ViewHolder viewHolder);

        public abstract void endAnimations();

        public abstract boolean isRunning();

        public abstract void runPendingAnimations();

        public static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int i = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            if ((i & 4) != 0) {
                return i;
            }
            int i2 = viewHolder.mOldPosition;
            int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
            if (i2 == -1 || absoluteAdapterPosition == -1 || i2 == absoluteAdapterPosition) {
                return i;
            }
            return i | 2048;
        }

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List<Object> list) {
            if (!((SimpleItemAnimator) this).mSupportsChangeAnimations || viewHolder.isInvalid()) {
                return true;
            }
            return false;
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            boolean z;
            ItemAnimatorListener itemAnimatorListener = this.mListener;
            if (itemAnimatorListener != null) {
                ItemAnimatorRestoreListener itemAnimatorRestoreListener = (ItemAnimatorRestoreListener) itemAnimatorListener;
                boolean z2 = true;
                viewHolder.setIsRecyclable(true);
                if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                    viewHolder.mShadowedHolder = null;
                }
                viewHolder.mShadowingHolder = null;
                if ((viewHolder.mFlags & 16) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    RecyclerView recyclerView = RecyclerView.this;
                    View view = viewHolder.itemView;
                    Objects.requireNonNull(recyclerView);
                    recyclerView.startInterceptRequestLayout();
                    ChildHelper childHelper = recyclerView.mChildHelper;
                    Objects.requireNonNull(childHelper);
                    C03365 r5 = (C03365) childHelper.mCallback;
                    Objects.requireNonNull(r5);
                    int indexOfChild = RecyclerView.this.indexOfChild(view);
                    if (indexOfChild == -1) {
                        childHelper.unhideViewInternal(view);
                    } else if (childHelper.mBucket.get(indexOfChild)) {
                        childHelper.mBucket.remove(indexOfChild);
                        childHelper.unhideViewInternal(view);
                        ((C03365) childHelper.mCallback).removeViewAt(indexOfChild);
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                        recyclerView.mRecycler.unscrapView(childViewHolderInt);
                        recyclerView.mRecycler.recycleViewHolderInternal(childViewHolderInt);
                    }
                    recyclerView.stopInterceptRequestLayout(!z2);
                    if (!z2 && viewHolder.isTmpDetached()) {
                        RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                    }
                }
            }
        }

        public final void dispatchAnimationsFinished() {
            int size = this.mFinishedListeners.size();
            for (int i = 0; i < size; i++) {
                this.mFinishedListeners.get(i).onAnimationsFinished();
            }
            this.mFinishedListeners.clear();
        }
    }

    public class ItemAnimatorRestoreListener implements ItemAnimator.ItemAnimatorListener {
        public ItemAnimatorRestoreListener() {
        }
    }

    public static abstract class LayoutManager {
        public ChildHelper mChildHelper;
        public int mHeight;
        public int mHeightMode;
        public ViewBoundsCheck mHorizontalBoundCheck;
        public final C03381 mHorizontalBoundCheckCallback;
        public boolean mIsAttachedToWindow = false;
        public boolean mItemPrefetchEnabled = true;
        public boolean mMeasurementCacheEnabled = true;
        public int mPrefetchMaxCountObserved;
        public boolean mPrefetchMaxObservedInInitialPrefetch;
        public RecyclerView mRecyclerView;
        public boolean mRequestedSimpleAnimations = false;
        public SmoothScroller mSmoothScroller;
        public ViewBoundsCheck mVerticalBoundCheck;
        public final C03392 mVerticalBoundCheckCallback;
        public int mWidth;
        public int mWidthMode;

        public interface LayoutPrefetchRegistry {
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
            if (r5 == 1073741824) goto L_0x0020;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getChildMeasureSpec(int r4, int r5, int r6, int r7, boolean r8) {
            /*
                int r4 = r4 - r6
                r6 = 0
                int r4 = java.lang.Math.max(r6, r4)
                r0 = -2
                r1 = -1
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = 1073741824(0x40000000, float:2.0)
                if (r8 == 0) goto L_0x001a
                if (r7 < 0) goto L_0x0011
                goto L_0x001c
            L_0x0011:
                if (r7 != r1) goto L_0x002f
                if (r5 == r2) goto L_0x0020
                if (r5 == 0) goto L_0x002f
                if (r5 == r3) goto L_0x0020
                goto L_0x002f
            L_0x001a:
                if (r7 < 0) goto L_0x001e
            L_0x001c:
                r5 = r3
                goto L_0x0031
            L_0x001e:
                if (r7 != r1) goto L_0x0022
            L_0x0020:
                r7 = r4
                goto L_0x0031
            L_0x0022:
                if (r7 != r0) goto L_0x002f
                if (r5 == r2) goto L_0x002c
                if (r5 != r3) goto L_0x0029
                goto L_0x002c
            L_0x0029:
                r7 = r4
                r5 = r6
                goto L_0x0031
            L_0x002c:
                r7 = r4
                r5 = r2
                goto L_0x0031
            L_0x002f:
                r5 = r6
                r7 = r5
            L_0x0031:
                int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, int, boolean):int");
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int i, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            return -1;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            return -1;
        }

        public boolean isAutoMeasureEnabled() {
            return false;
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
            return null;
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        public View onInterceptFocusSearch(View view, int i) {
            return null;
        }

        public void onItemsAdded(int i, int i2) {
        }

        public void onItemsChanged() {
        }

        public void onItemsMoved(int i, int i2) {
        }

        public void onItemsRemoved(int i, int i2) {
        }

        public void onItemsUpdated(int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
            onItemsUpdated(i, i2);
        }

        public void onLayoutCompleted(State state) {
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int i) {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b3, code lost:
            if (r9 == false) goto L_0x00ba;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean requestChildRectangleOnScreen(androidx.recyclerview.widget.RecyclerView r10, android.view.View r11, android.graphics.Rect r12, boolean r13, boolean r14) {
            /*
                r9 = this;
                r0 = 2
                int[] r0 = new int[r0]
                int r1 = r9.getPaddingLeft()
                int r2 = r9.getPaddingTop()
                int r3 = r9.mWidth
                int r4 = r9.getPaddingRight()
                int r3 = r3 - r4
                int r4 = r9.mHeight
                int r5 = r9.getPaddingBottom()
                int r4 = r4 - r5
                int r5 = r11.getLeft()
                int r6 = r12.left
                int r5 = r5 + r6
                int r6 = r11.getScrollX()
                int r5 = r5 - r6
                int r6 = r11.getTop()
                int r7 = r12.top
                int r6 = r6 + r7
                int r11 = r11.getScrollY()
                int r6 = r6 - r11
                int r11 = r12.width()
                int r11 = r11 + r5
                int r12 = r12.height()
                int r12 = r12 + r6
                int r5 = r5 - r1
                r1 = 0
                int r7 = java.lang.Math.min(r1, r5)
                int r6 = r6 - r2
                int r2 = java.lang.Math.min(r1, r6)
                int r11 = r11 - r3
                int r3 = java.lang.Math.max(r1, r11)
                int r12 = r12 - r4
                int r12 = java.lang.Math.max(r1, r12)
                int r4 = r9.getLayoutDirection()
                r8 = 1
                if (r4 != r8) goto L_0x005f
                if (r3 == 0) goto L_0x005a
                goto L_0x0067
            L_0x005a:
                int r3 = java.lang.Math.max(r7, r11)
                goto L_0x0067
            L_0x005f:
                if (r7 == 0) goto L_0x0062
                goto L_0x0066
            L_0x0062:
                int r7 = java.lang.Math.min(r5, r3)
            L_0x0066:
                r3 = r7
            L_0x0067:
                if (r2 == 0) goto L_0x006a
                goto L_0x006e
            L_0x006a:
                int r2 = java.lang.Math.min(r6, r12)
            L_0x006e:
                r0[r1] = r3
                r0[r8] = r2
                r11 = r0[r1]
                r12 = r0[r8]
                if (r14 == 0) goto L_0x00b5
                android.view.View r14 = r10.getFocusedChild()
                if (r14 != 0) goto L_0x0080
            L_0x007e:
                r9 = r1
                goto L_0x00b3
            L_0x0080:
                int r0 = r9.getPaddingLeft()
                int r2 = r9.getPaddingTop()
                int r3 = r9.mWidth
                int r4 = r9.getPaddingRight()
                int r3 = r3 - r4
                int r4 = r9.mHeight
                int r5 = r9.getPaddingBottom()
                int r4 = r4 - r5
                androidx.recyclerview.widget.RecyclerView r5 = r9.mRecyclerView
                android.graphics.Rect r5 = r5.mTempRect
                r9.getDecoratedBoundsWithMargins(r14, r5)
                int r9 = r5.left
                int r9 = r9 - r11
                if (r9 >= r3) goto L_0x007e
                int r9 = r5.right
                int r9 = r9 - r11
                if (r9 <= r0) goto L_0x007e
                int r9 = r5.top
                int r9 = r9 - r12
                if (r9 >= r4) goto L_0x007e
                int r9 = r5.bottom
                int r9 = r9 - r12
                if (r9 > r2) goto L_0x00b2
                goto L_0x007e
            L_0x00b2:
                r9 = r8
            L_0x00b3:
                if (r9 == 0) goto L_0x00ba
            L_0x00b5:
                if (r11 != 0) goto L_0x00bb
                if (r12 == 0) goto L_0x00ba
                goto L_0x00bb
            L_0x00ba:
                return r1
            L_0x00bb:
                if (r13 == 0) goto L_0x00c1
                r10.scrollBy(r11, r12)
                goto L_0x00c4
            L_0x00c1:
                r10.smoothScrollBy(r11, r12)
            L_0x00c4:
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.requestChildRectangleOnScreen(androidx.recyclerview.widget.RecyclerView, android.view.View, android.graphics.Rect, boolean, boolean):boolean");
        }

        public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int i) {
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public boolean shouldMeasureTwice() {
            return false;
        }

        public boolean supportsPredictiveItemAnimations() {
            return this instanceof GridLayoutManager;
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            Properties properties = new Properties();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecyclerView, i, i2);
            properties.orientation = obtainStyledAttributes.getInt(0, 1);
            properties.spanCount = obtainStyledAttributes.getInt(10, 1);
            properties.reverseLayout = obtainStyledAttributes.getBoolean(9, false);
            properties.stackFromEnd = obtainStyledAttributes.getBoolean(11, false);
            obtainStyledAttributes.recycle();
            return properties;
        }

        public void assertNotInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.assertNotInLayoutOrScroll(str);
            }
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.getItemDecorInsetsForChild(view));
            }
        }

        public final View findContainingItemView(View view) {
            View findContainingItemView;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (findContainingItemView = recyclerView.findContainingItemView(view)) == null || this.mChildHelper.isHidden(findContainingItemView)) {
                return null;
            }
            return findContainingItemView;
        }

        public final View getChildAt(int i) {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper != null) {
                return childHelper.getChildAt(i);
            }
            return null;
        }

        public final int getChildCount() {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper != null) {
                return childHelper.getChildCount();
            }
            return 0;
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect2 = layoutParams.mDecorInsets;
            rect.set((view.getLeft() - rect2.left) - layoutParams.leftMargin, (view.getTop() - rect2.top) - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
        }

        public final int getItemCount() {
            Adapter adapter;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                Objects.requireNonNull(recyclerView);
                adapter = recyclerView.mAdapter;
            } else {
                adapter = null;
            }
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public final int getLayoutDirection() {
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api17Impl.getLayoutDirection(recyclerView);
        }

        public final int getPaddingBottom() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public final int getPaddingLeft() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public final int getPaddingRight() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public final int getPaddingTop() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public final boolean hasFocus() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || !recyclerView.hasFocus()) {
                return false;
            }
            return true;
        }

        public final boolean isSmoothScrolling() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            if (smoothScroller != null) {
                Objects.requireNonNull(smoothScroller);
                if (smoothScroller.mRunning) {
                    return true;
                }
            }
            return false;
        }

        public void offsetChildrenHorizontal(int i) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                Objects.requireNonNull(recyclerView);
                int childCount = recyclerView.mChildHelper.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    recyclerView.mChildHelper.getChildAt(i2).offsetLeftAndRight(i);
                }
            }
        }

        public void offsetChildrenVertical(int i) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                Objects.requireNonNull(recyclerView);
                int childCount = recyclerView.mChildHelper.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    recyclerView.mChildHelper.getChildAt(i2).offsetTopAndBottom(i);
                }
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.mRecyclerView;
            Recycler recycler = recyclerView.mRecycler;
            State state = recyclerView.mState;
            if (recyclerView != null && accessibilityEvent != null) {
                boolean z = true;
                if (!recyclerView.canScrollVertically(1) && !this.mRecyclerView.canScrollVertically(-1) && !this.mRecyclerView.canScrollHorizontally(-1) && !this.mRecyclerView.canScrollHorizontally(1)) {
                    z = false;
                }
                accessibilityEvent.setScrollable(z);
                Adapter adapter = this.mRecyclerView.mAdapter;
                if (adapter != null) {
                    accessibilityEvent.setItemCount(adapter.getItemCount());
                }
            }
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), 0));
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onMeasure(Recycler recycler, State state, int i, int i2) {
            this.mRecyclerView.defaultOnMeasure(i, i2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x006a A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean performAccessibilityAction(androidx.recyclerview.widget.RecyclerView.Recycler r2, androidx.recyclerview.widget.RecyclerView.State r3, int r4, android.os.Bundle r5) {
            /*
                r1 = this;
                androidx.recyclerview.widget.RecyclerView r2 = r1.mRecyclerView
                r3 = 0
                if (r2 != 0) goto L_0x0006
                return r3
            L_0x0006:
                r5 = 4096(0x1000, float:5.74E-42)
                r0 = 1
                if (r4 == r5) goto L_0x003e
                r5 = 8192(0x2000, float:1.14794E-41)
                if (r4 == r5) goto L_0x0012
                r2 = r3
                r4 = r2
                goto L_0x0068
            L_0x0012:
                r4 = -1
                boolean r2 = r2.canScrollVertically(r4)
                if (r2 == 0) goto L_0x0027
                int r2 = r1.mHeight
                int r5 = r1.getPaddingTop()
                int r2 = r2 - r5
                int r5 = r1.getPaddingBottom()
                int r2 = r2 - r5
                int r2 = -r2
                goto L_0x0028
            L_0x0027:
                r2 = r3
            L_0x0028:
                androidx.recyclerview.widget.RecyclerView r5 = r1.mRecyclerView
                boolean r4 = r5.canScrollHorizontally(r4)
                if (r4 == 0) goto L_0x0067
                int r4 = r1.mWidth
                int r5 = r1.getPaddingLeft()
                int r4 = r4 - r5
                int r5 = r1.getPaddingRight()
                int r4 = r4 - r5
                int r4 = -r4
                goto L_0x0068
            L_0x003e:
                boolean r2 = r2.canScrollVertically(r0)
                if (r2 == 0) goto L_0x0051
                int r2 = r1.mHeight
                int r4 = r1.getPaddingTop()
                int r2 = r2 - r4
                int r4 = r1.getPaddingBottom()
                int r2 = r2 - r4
                goto L_0x0052
            L_0x0051:
                r2 = r3
            L_0x0052:
                androidx.recyclerview.widget.RecyclerView r4 = r1.mRecyclerView
                boolean r4 = r4.canScrollHorizontally(r0)
                if (r4 == 0) goto L_0x0067
                int r4 = r1.mWidth
                int r5 = r1.getPaddingLeft()
                int r4 = r4 - r5
                int r5 = r1.getPaddingRight()
                int r4 = r4 - r5
                goto L_0x0068
            L_0x0067:
                r4 = r3
            L_0x0068:
                if (r2 != 0) goto L_0x006d
                if (r4 != 0) goto L_0x006d
                return r3
            L_0x006d:
                androidx.recyclerview.widget.RecyclerView r1 = r1.mRecyclerView
                r1.smoothScrollBy$1(r4, r2, r0)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.LayoutManager.performAccessibilityAction(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State, int, android.os.Bundle):boolean");
        }

        public final void removeAndRecycleView(View view, Recycler recycler) {
            ChildHelper childHelper = this.mChildHelper;
            Objects.requireNonNull(childHelper);
            C03365 r0 = (C03365) childHelper.mCallback;
            Objects.requireNonNull(r0);
            int indexOfChild = RecyclerView.this.indexOfChild(view);
            if (indexOfChild >= 0) {
                if (childHelper.mBucket.remove(indexOfChild)) {
                    childHelper.unhideViewInternal(view);
                }
                ((C03365) childHelper.mCallback).removeViewAt(indexOfChild);
            }
            recycler.recycleView(view);
        }

        public final void requestLayout() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public final void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        public final boolean shouldReMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            if (!this.mMeasurementCacheEnabled || !isMeasurementUpToDate(view.getMeasuredWidth(), i, layoutParams.width) || !isMeasurementUpToDate(view.getMeasuredHeight(), i2, layoutParams.height)) {
                return true;
            }
            return false;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, int i) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.mSmoothScroller;
            if (!(smoothScroller2 == null || smoothScroller == smoothScroller2 || !smoothScroller2.mRunning)) {
                smoothScroller2.stop();
            }
            this.mSmoothScroller = smoothScroller;
            RecyclerView recyclerView = this.mRecyclerView;
            ViewFlinger viewFlinger = recyclerView.mViewFlinger;
            Objects.requireNonNull(viewFlinger);
            RecyclerView.this.removeCallbacks(viewFlinger);
            viewFlinger.mOverScroller.abortAnimation();
            if (smoothScroller.mStarted) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("An instance of ");
                m.append(smoothScroller.getClass().getSimpleName());
                m.append(" was started more than once. Each instance of");
                m.append(smoothScroller.getClass().getSimpleName());
                m.append(" is intended to only be used once. You should create a new instance for each use.");
                Log.w("RecyclerView", m.toString());
            }
            smoothScroller.mRecyclerView = recyclerView;
            smoothScroller.mLayoutManager = this;
            int i = smoothScroller.mTargetPosition;
            if (i != -1) {
                recyclerView.mState.mTargetPosition = i;
                smoothScroller.mRunning = true;
                smoothScroller.mPendingInitialRun = true;
                smoothScroller.mTargetView = recyclerView.mLayout.findViewByPosition(i);
                smoothScroller.mRecyclerView.mViewFlinger.postOnAnimation();
                smoothScroller.mStarted = true;
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public LayoutManager() {
            C03381 r0 = new ViewBoundsCheck.Callback() {
                public final View getChildAt(int i) {
                    return LayoutManager.this.getChildAt(i);
                }

                public final int getParentEnd() {
                    LayoutManager layoutManager = LayoutManager.this;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mWidth - LayoutManager.this.getPaddingRight();
                }

                public final int getParentStart() {
                    return LayoutManager.this.getPaddingLeft();
                }

                public final int getChildEnd(View view) {
                    return LayoutManager.this.getDecoratedRight(view) + ((LayoutParams) view.getLayoutParams()).rightMargin;
                }

                public final int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedLeft(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
                }
            };
            this.mHorizontalBoundCheckCallback = r0;
            C03392 r1 = new ViewBoundsCheck.Callback() {
                public final View getChildAt(int i) {
                    return LayoutManager.this.getChildAt(i);
                }

                public final int getParentEnd() {
                    LayoutManager layoutManager = LayoutManager.this;
                    Objects.requireNonNull(layoutManager);
                    return layoutManager.mHeight - LayoutManager.this.getPaddingBottom();
                }

                public final int getParentStart() {
                    return LayoutManager.this.getPaddingTop();
                }

                public final int getChildEnd(View view) {
                    return LayoutManager.this.getDecoratedBottom(view) + ((LayoutParams) view.getLayoutParams()).bottomMargin;
                }

                public final int getChildStart(View view) {
                    return LayoutManager.this.getDecoratedTop(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
                }
            };
            this.mVerticalBoundCheckCallback = r1;
            this.mHorizontalBoundCheck = new ViewBoundsCheck(r0);
            this.mVerticalBoundCheck = new ViewBoundsCheck(r1);
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            if (mode == Integer.MIN_VALUE) {
                return Math.min(size, Math.max(i2, i3));
            }
            if (mode != 1073741824) {
                return Math.max(i2, i3);
            }
            return size;
        }

        public static int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public static int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public static int getPosition(View view) {
            return ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        }

        public static boolean isMeasurementUpToDate(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode != Integer.MIN_VALUE) {
                if (mode == 0) {
                    return true;
                }
                if (mode == 1073741824 && size == i) {
                    return true;
                }
                return false;
            } else if (size >= i) {
                return true;
            } else {
                return false;
            }
        }

        public static void layoutDecoratedWithMargins(View view, int i, int i2, int i3, int i4) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = layoutParams.mDecorInsets;
            view.layout(i + rect.left + layoutParams.leftMargin, i2 + rect.top + layoutParams.topMargin, (i3 - rect.right) - layoutParams.rightMargin, (i4 - rect.bottom) - layoutParams.bottomMargin);
        }

        public final void addViewInt(View view, int i, boolean z) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (z || childViewHolderInt.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(childViewHolderInt);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (childViewHolderInt.wasReturnedFromScrap() || childViewHolderInt.isScrap()) {
                if (childViewHolderInt.isScrap()) {
                    childViewHolderInt.mScrapContainer.unscrapView(childViewHolderInt);
                } else {
                    childViewHolderInt.mFlags &= -33;
                }
                this.mChildHelper.attachViewToParent(view, i, view.getLayoutParams(), false);
            } else {
                int i2 = -1;
                if (view.getParent() == this.mRecyclerView) {
                    int indexOfChild = this.mChildHelper.indexOfChild(view);
                    if (i == -1) {
                        i = this.mChildHelper.getChildCount();
                    }
                    if (indexOfChild == -1) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                        m.append(this.mRecyclerView.indexOfChild(view));
                        throw new IllegalStateException(ChildHelper$$ExternalSyntheticOutline0.m18m(this.mRecyclerView, m));
                    } else if (indexOfChild != i) {
                        LayoutManager layoutManager = this.mRecyclerView.mLayout;
                        Objects.requireNonNull(layoutManager);
                        View childAt = layoutManager.getChildAt(indexOfChild);
                        if (childAt != null) {
                            layoutManager.getChildAt(indexOfChild);
                            layoutManager.mChildHelper.detachViewFromParent(indexOfChild);
                            LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                            ViewHolder childViewHolderInt2 = RecyclerView.getChildViewHolderInt(childAt);
                            if (childViewHolderInt2.isRemoved()) {
                                layoutManager.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(childViewHolderInt2);
                            } else {
                                layoutManager.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(childViewHolderInt2);
                            }
                            layoutManager.mChildHelper.attachViewToParent(childAt, i, layoutParams2, childViewHolderInt2.isRemoved());
                        } else {
                            throw new IllegalArgumentException("Cannot move a child from non-existing index:" + indexOfChild + layoutManager.mRecyclerView.toString());
                        }
                    }
                } else {
                    this.mChildHelper.addView(view, i, false);
                    layoutParams.mInsetsDirty = true;
                    SmoothScroller smoothScroller = this.mSmoothScroller;
                    if (smoothScroller != null && smoothScroller.mRunning) {
                        Objects.requireNonNull(smoothScroller);
                        Objects.requireNonNull(smoothScroller.mRecyclerView);
                        ViewHolder childViewHolderInt3 = RecyclerView.getChildViewHolderInt(view);
                        if (childViewHolderInt3 != null) {
                            i2 = childViewHolderInt3.getLayoutPosition();
                        }
                        if (i2 == smoothScroller.mTargetPosition) {
                            smoothScroller.mTargetView = view;
                        }
                    }
                }
            }
            if (layoutParams.mPendingInvalidate) {
                childViewHolderInt.itemView.invalidate();
                layoutParams.mPendingInvalidate = false;
            }
        }

        public final void detachAndScrapAttachedViews(Recycler recycler) {
            int childCount = getChildCount();
            while (true) {
                childCount--;
                if (childCount >= 0) {
                    scrapOrRecycleView(recycler, childCount, getChildAt(childCount));
                } else {
                    return;
                }
            }
        }

        public View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(childAt);
                if (childViewHolderInt != null && childViewHolderInt.getLayoutPosition() == i && !childViewHolderInt.shouldIgnore()) {
                    State state = this.mRecyclerView.mState;
                    Objects.requireNonNull(state);
                    if (state.mInPreLayout || !childViewHolderInt.isRemoved()) {
                        return childAt;
                    }
                }
            }
            return null;
        }

        public int getDecoratedBottom(View view) {
            return ((LayoutParams) view.getLayoutParams()).mDecorInsets.bottom + view.getBottom();
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.left;
        }

        public int getDecoratedRight(View view) {
            return ((LayoutParams) view.getLayoutParams()).mDecorInsets.right + view.getRight();
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - ((LayoutParams) view.getLayoutParams()).mDecorInsets.top;
        }

        public final void getTransformedBoundingBox(View view, Rect rect) {
            Matrix matrix;
            Rect rect2 = ((LayoutParams) view.getLayoutParams()).mDecorInsets;
            rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            if (!(this.mRecyclerView == null || (matrix = view.getMatrix()) == null || matrix.isIdentity())) {
                RectF rectF = this.mRecyclerView.mTempRectF;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public final void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && !this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                RecyclerView recyclerView = this.mRecyclerView;
                onInitializeAccessibilityNodeInfoForItem(recyclerView.mRecycler, recyclerView.mState, view, accessibilityNodeInfoCompat);
            }
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            if (isSmoothScrolling() || recyclerView.isComputingLayout()) {
                return true;
            }
            return false;
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            int childCount = getChildCount();
            while (true) {
                childCount--;
                if (childCount < 0) {
                    return;
                }
                if (!RecyclerView.getChildViewHolderInt(getChildAt(childCount)).shouldIgnore()) {
                    View childAt = getChildAt(childCount);
                    removeViewAt(childCount);
                    recycler.recycleView(childAt);
                }
            }
        }

        public final void removeAndRecycleScrapInt(Recycler recycler) {
            Objects.requireNonNull(recycler);
            int size = recycler.mAttachedScrap.size();
            for (int i = size - 1; i >= 0; i--) {
                View view = recycler.mAttachedScrap.get(i).itemView;
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (!childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.setIsRecyclable(false);
                    if (childViewHolderInt.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(view, false);
                    }
                    ItemAnimator itemAnimator = this.mRecyclerView.mItemAnimator;
                    if (itemAnimator != null) {
                        itemAnimator.endAnimation(childViewHolderInt);
                    }
                    childViewHolderInt.setIsRecyclable(true);
                    ViewHolder childViewHolderInt2 = RecyclerView.getChildViewHolderInt(view);
                    childViewHolderInt2.mScrapContainer = null;
                    childViewHolderInt2.mInChangeScrap = false;
                    childViewHolderInt2.mFlags &= -33;
                    recycler.recycleViewHolderInternal(childViewHolderInt2);
                }
            }
            recycler.mAttachedScrap.clear();
            ArrayList<ViewHolder> arrayList = recycler.mChangedScrap;
            if (arrayList != null) {
                arrayList.clear();
            }
            if (size > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public final void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                ChildHelper childHelper = this.mChildHelper;
                Objects.requireNonNull(childHelper);
                int offset = childHelper.getOffset(i);
                C03365 r0 = (C03365) childHelper.mCallback;
                Objects.requireNonNull(r0);
                View childAt = RecyclerView.this.getChildAt(offset);
                if (childAt != null) {
                    if (childHelper.mBucket.remove(offset)) {
                        childHelper.unhideViewInternal(childAt);
                    }
                    ((C03365) childHelper.mCallback).removeViewAt(offset);
                }
            }
        }

        public final void scrapOrRecycleView(Recycler recycler, int i, View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (!childViewHolderInt.shouldIgnore()) {
                if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved()) {
                    Adapter adapter = this.mRecyclerView.mAdapter;
                    Objects.requireNonNull(adapter);
                    if (!adapter.mHasStableIds) {
                        removeViewAt(i);
                        recycler.recycleViewHolderInternal(childViewHolderInt);
                        return;
                    }
                }
                getChildAt(i);
                this.mChildHelper.detachViewFromParent(i);
                recycler.scrapView(view);
                ViewInfoStore viewInfoStore = this.mRecyclerView.mViewInfoStore;
                Objects.requireNonNull(viewInfoStore);
                viewInfoStore.removeFromDisappearedInLayout(childViewHolderInt);
            }
        }

        public final void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        public final void setMeasureSpecs(int i, int i2) {
            this.mWidth = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            this.mWidthMode = mode;
            if (mode == 0) {
                int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
            }
            this.mHeight = View.MeasureSpec.getSize(i2);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.mHeightMode = mode2;
            if (mode2 == 0) {
                int[] iArr2 = RecyclerView.NESTED_SCROLLING_ATTRS;
            }
        }

        public void setMeasuredDimension(Rect rect, int i, int i2) {
            int paddingRight = getPaddingRight() + getPaddingLeft() + rect.width();
            int paddingBottom = getPaddingBottom() + getPaddingTop() + rect.height();
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            this.mRecyclerView.setMeasuredDimension(chooseSize(i, paddingRight, ViewCompat.Api16Impl.getMinimumWidth(recyclerView)), chooseSize(i2, paddingBottom, ViewCompat.Api16Impl.getMinimumHeight(this.mRecyclerView)));
        }

        public final void setMeasuredDimensionFromChildren(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.mRecyclerView.defaultOnMeasure(i, i2);
                return;
            }
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MIN_VALUE;
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                Rect rect = this.mRecyclerView.mTempRect;
                getDecoratedBoundsWithMargins(childAt, rect);
                int i8 = rect.left;
                if (i8 < i4) {
                    i4 = i8;
                }
                int i9 = rect.right;
                if (i9 > i3) {
                    i3 = i9;
                }
                int i10 = rect.top;
                if (i10 < i5) {
                    i5 = i10;
                }
                int i11 = rect.bottom;
                if (i11 > i6) {
                    i6 = i11;
                }
            }
            this.mRecyclerView.mTempRect.set(i4, i5, i3, i6);
            setMeasuredDimension(this.mRecyclerView.mTempRect, i, i2);
        }

        public final boolean shouldMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            if (view.isLayoutRequested() || !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(view.getWidth(), i, layoutParams.width) || !isMeasurementUpToDate(view.getHeight(), i2, layoutParams.height)) {
                return true;
            }
            return false;
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return requestChildRectangleOnScreen(recyclerView, view, rect, z, false);
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public final Rect mDecorInsets = new Rect();
        public boolean mInsetsDirty = true;
        public boolean mPendingInvalidate = false;
        public ViewHolder mViewHolder;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        public final boolean isItemChanged() {
            ViewHolder viewHolder = this.mViewHolder;
            Objects.requireNonNull(viewHolder);
            if ((viewHolder.mFlags & 2) != 0) {
                return true;
            }
            return false;
        }

        public final boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
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

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view);

        void onChildViewDetachedFromWindow(View view);
    }

    public static abstract class OnFlingListener {
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent$1(MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(MotionEvent motionEvent);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }

    public static class RecycledViewPool {
        public int mAttachCount = 0;
        public SparseArray<ScrapData> mScrap = new SparseArray<>();

        public static class ScrapData {
            public long mBindRunningAverageNs = 0;
            public long mCreateRunningAverageNs = 0;
            public int mMaxScrap = 5;
            public final ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();
        }

        public final ScrapData getScrapDataForType(int i) {
            ScrapData scrapData = this.mScrap.get(i);
            if (scrapData != null) {
                return scrapData;
            }
            ScrapData scrapData2 = new ScrapData();
            this.mScrap.put(i, scrapData2);
            return scrapData2;
        }
    }

    public final class Recycler {
        public final ArrayList<ViewHolder> mAttachedScrap;
        public final ArrayList<ViewHolder> mCachedViews = new ArrayList<>();
        public ArrayList<ViewHolder> mChangedScrap = null;
        public RecycledViewPool mRecyclerPool;
        public int mRequestedCacheMax;
        public final List<ViewHolder> mUnmodifiableAttachedScrap;
        public int mViewCacheMax;

        public Recycler() {
            ArrayList<ViewHolder> arrayList = new ArrayList<>();
            this.mAttachedScrap = arrayList;
            this.mUnmodifiableAttachedScrap = Collections.unmodifiableList(arrayList);
            this.mRequestedCacheMax = 2;
            this.mViewCacheMax = 2;
        }

        public final int convertPreLayoutPositionToPostLayout(int i) {
            if (i < 0 || i >= RecyclerView.this.mState.getItemCount()) {
                StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("invalid position ", i, ". State item count is ");
                m.append(RecyclerView.this.mState.getItemCount());
                throw new IndexOutOfBoundsException(ChildHelper$$ExternalSyntheticOutline0.m18m(RecyclerView.this, m));
            }
            State state = RecyclerView.this.mState;
            Objects.requireNonNull(state);
            if (!state.mInPreLayout) {
                return i;
            }
            AdapterHelper adapterHelper = RecyclerView.this.mAdapterHelper;
            Objects.requireNonNull(adapterHelper);
            return adapterHelper.findPositionOffset(i, 0);
        }

        public final void recycleAndClearCachedViews() {
            for (int size = this.mCachedViews.size() - 1; size >= 0; size--) {
                recycleCachedViewAt(size);
            }
            this.mCachedViews.clear();
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
            GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = RecyclerView.this.mPrefetchRegistry;
            Objects.requireNonNull(layoutPrefetchRegistryImpl);
            int[] iArr2 = layoutPrefetchRegistryImpl.mPrefetchArray;
            if (iArr2 != null) {
                Arrays.fill(iArr2, -1);
            }
            layoutPrefetchRegistryImpl.mCount = 0;
        }

        public final void recycleCachedViewAt(int i) {
            addViewHolderToRecycledViewPool(this.mCachedViews.get(i), true);
            this.mCachedViews.remove(i);
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x032c, code lost:
            r7 = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:228:0x04a4, code lost:
            if (r7.isInvalid() == false) goto L_0x04e5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:237:0x04e3, code lost:
            if (r6 == false) goto L_0x04e5;
         */
        /* JADX WARNING: Removed duplicated region for block: B:119:0x026d  */
        /* JADX WARNING: Removed duplicated region for block: B:205:0x043e  */
        /* JADX WARNING: Removed duplicated region for block: B:219:0x048c  */
        /* JADX WARNING: Removed duplicated region for block: B:220:0x048f  */
        /* JADX WARNING: Removed duplicated region for block: B:292:0x05d5  */
        /* JADX WARNING: Removed duplicated region for block: B:293:0x05e3  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0095  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x009c  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final androidx.recyclerview.widget.RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int r18, long r19) {
            /*
                r17 = this;
                r0 = r17
                r1 = r18
                if (r1 < 0) goto L_0x0606
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r2 = r2.mState
                int r2 = r2.getItemCount()
                if (r1 >= r2) goto L_0x0606
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r2 = r2.mState
                java.util.Objects.requireNonNull(r2)
                boolean r2 = r2.mInPreLayout
                r3 = 32
                r4 = 0
                r5 = 0
                if (r2 == 0) goto L_0x0097
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r2 = r0.mChangedScrap
                if (r2 == 0) goto L_0x0092
                int r2 = r2.size()
                if (r2 != 0) goto L_0x002b
                goto L_0x0092
            L_0x002b:
                r6 = r5
            L_0x002c:
                if (r6 >= r2) goto L_0x0049
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mChangedScrap
                java.lang.Object r7 = r7.get(r6)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r7 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r7
                boolean r8 = r7.wasReturnedFromScrap()
                if (r8 != 0) goto L_0x0046
                int r8 = r7.getLayoutPosition()
                if (r8 != r1) goto L_0x0046
                r7.addFlags(r3)
                goto L_0x0093
            L_0x0046:
                int r6 = r6 + 1
                goto L_0x002c
            L_0x0049:
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r6 = r6.mAdapter
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mHasStableIds
                if (r6 == 0) goto L_0x0092
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r6 = r6.mAdapterHelper
                java.util.Objects.requireNonNull(r6)
                int r6 = r6.findPositionOffset(r1, r5)
                if (r6 <= 0) goto L_0x0092
                androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r7 = r7.mAdapter
                int r7 = r7.getItemCount()
                if (r6 >= r7) goto L_0x0092
                androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r7 = r7.mAdapter
                long r6 = r7.getItemId(r6)
                r8 = r5
            L_0x0074:
                if (r8 >= r2) goto L_0x0092
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r9 = r0.mChangedScrap
                java.lang.Object r9 = r9.get(r8)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r9 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r9
                boolean r10 = r9.wasReturnedFromScrap()
                if (r10 != 0) goto L_0x008f
                long r10 = r9.mItemId
                int r10 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r10 != 0) goto L_0x008f
                r9.addFlags(r3)
                r7 = r9
                goto L_0x0093
            L_0x008f:
                int r8 = r8 + 1
                goto L_0x0074
            L_0x0092:
                r7 = r4
            L_0x0093:
                if (r7 == 0) goto L_0x0098
                r2 = 1
                goto L_0x0099
            L_0x0097:
                r7 = r4
            L_0x0098:
                r2 = r5
            L_0x0099:
                r6 = -1
                if (r7 != 0) goto L_0x026b
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mAttachedScrap
                int r7 = r7.size()
                r8 = r5
            L_0x00a3:
                if (r8 >= r7) goto L_0x00d5
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r9 = r0.mAttachedScrap
                java.lang.Object r9 = r9.get(r8)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r9 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r9
                boolean r10 = r9.wasReturnedFromScrap()
                if (r10 != 0) goto L_0x00d2
                int r10 = r9.getLayoutPosition()
                if (r10 != r1) goto L_0x00d2
                boolean r10 = r9.isInvalid()
                if (r10 != 0) goto L_0x00d2
                androidx.recyclerview.widget.RecyclerView r10 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r10 = r10.mState
                boolean r10 = r10.mInPreLayout
                if (r10 != 0) goto L_0x00cd
                boolean r10 = r9.isRemoved()
                if (r10 != 0) goto L_0x00d2
            L_0x00cd:
                r9.addFlags(r3)
                goto L_0x01c7
            L_0x00d2:
                int r8 = r8 + 1
                goto L_0x00a3
            L_0x00d5:
                androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.ChildHelper r7 = r7.mChildHelper
                java.util.Objects.requireNonNull(r7)
                java.util.ArrayList r8 = r7.mHiddenViews
                int r8 = r8.size()
                r9 = r5
            L_0x00e3:
                if (r9 >= r8) goto L_0x010e
                java.util.ArrayList r10 = r7.mHiddenViews
                java.lang.Object r10 = r10.get(r9)
                android.view.View r10 = (android.view.View) r10
                androidx.recyclerview.widget.ChildHelper$Callback r11 = r7.mCallback
                androidx.recyclerview.widget.RecyclerView$5 r11 = (androidx.recyclerview.widget.RecyclerView.C03365) r11
                java.util.Objects.requireNonNull(r11)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r11 = androidx.recyclerview.widget.RecyclerView.getChildViewHolderInt(r10)
                int r12 = r11.getLayoutPosition()
                if (r12 != r1) goto L_0x010b
                boolean r12 = r11.isInvalid()
                if (r12 != 0) goto L_0x010b
                boolean r11 = r11.isRemoved()
                if (r11 != 0) goto L_0x010b
                goto L_0x010f
            L_0x010b:
                int r9 = r9 + 1
                goto L_0x00e3
            L_0x010e:
                r10 = r4
            L_0x010f:
                if (r10 == 0) goto L_0x019f
                androidx.recyclerview.widget.RecyclerView$ViewHolder r7 = androidx.recyclerview.widget.RecyclerView.getChildViewHolderInt(r10)
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.ChildHelper r8 = r8.mChildHelper
                java.util.Objects.requireNonNull(r8)
                androidx.recyclerview.widget.ChildHelper$Callback r9 = r8.mCallback
                androidx.recyclerview.widget.RecyclerView$5 r9 = (androidx.recyclerview.widget.RecyclerView.C03365) r9
                java.util.Objects.requireNonNull(r9)
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                int r9 = r9.indexOfChild(r10)
                if (r9 < 0) goto L_0x0187
                androidx.recyclerview.widget.ChildHelper$Bucket r11 = r8.mBucket
                boolean r11 = r11.get(r9)
                if (r11 == 0) goto L_0x016f
                androidx.recyclerview.widget.ChildHelper$Bucket r11 = r8.mBucket
                r11.clear(r9)
                r8.unhideViewInternal(r10)
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.ChildHelper r8 = r8.mChildHelper
                int r8 = r8.indexOfChild(r10)
                if (r8 == r6) goto L_0x0156
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.ChildHelper r9 = r9.mChildHelper
                r9.detachViewFromParent(r8)
                r0.scrapView(r10)
                r8 = 8224(0x2020, float:1.1524E-41)
                r7.addFlags(r8)
                goto L_0x01cd
            L_0x0156:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "layout index should not be -1 after unhiding a view:"
                r2.append(r3)
                r2.append(r7)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r0 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r0, r2)
                r1.<init>(r0)
                throw r1
            L_0x016f:
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "trying to unhide a view that was not hidden"
                r1.append(r2)
                r1.append(r10)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x0187:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "view is not a child, cannot hide "
                r1.append(r2)
                r1.append(r10)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x019f:
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mCachedViews
                int r7 = r7.size()
                r8 = r5
            L_0x01a6:
                if (r8 >= r7) goto L_0x01cc
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r9 = r0.mCachedViews
                java.lang.Object r9 = r9.get(r8)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r9 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r9
                boolean r10 = r9.isInvalid()
                if (r10 != 0) goto L_0x01c9
                int r10 = r9.getLayoutPosition()
                if (r10 != r1) goto L_0x01c9
                boolean r10 = r9.isAttachedToTransitionOverlay()
                if (r10 != 0) goto L_0x01c9
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mCachedViews
                r7.remove(r8)
            L_0x01c7:
                r7 = r9
                goto L_0x01cd
            L_0x01c9:
                int r8 = r8 + 1
                goto L_0x01a6
            L_0x01cc:
                r7 = r4
            L_0x01cd:
                if (r7 == 0) goto L_0x026b
                boolean r8 = r7.isRemoved()
                if (r8 == 0) goto L_0x01df
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r8 = r8.mState
                java.util.Objects.requireNonNull(r8)
                boolean r8 = r8.mInPreLayout
                goto L_0x0226
            L_0x01df:
                int r8 = r7.mPosition
                if (r8 < 0) goto L_0x0252
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r9 = r9.mAdapter
                int r9 = r9.getItemCount()
                if (r8 >= r9) goto L_0x0252
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r8 = r8.mState
                java.util.Objects.requireNonNull(r8)
                boolean r8 = r8.mInPreLayout
                if (r8 != 0) goto L_0x0207
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r8 = r8.mAdapter
                int r9 = r7.mPosition
                int r8 = r8.getItemViewType(r9)
                int r9 = r7.mItemViewType
                if (r8 == r9) goto L_0x0207
                goto L_0x0223
            L_0x0207:
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r8 = r8.mAdapter
                java.util.Objects.requireNonNull(r8)
                boolean r8 = r8.mHasStableIds
                if (r8 == 0) goto L_0x0225
                long r8 = r7.mItemId
                androidx.recyclerview.widget.RecyclerView r10 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r10 = r10.mAdapter
                int r11 = r7.mPosition
                long r10 = r10.getItemId(r11)
                int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                if (r8 != 0) goto L_0x0223
                goto L_0x0225
            L_0x0223:
                r8 = r5
                goto L_0x0226
            L_0x0225:
                r8 = 1
            L_0x0226:
                if (r8 != 0) goto L_0x0250
                r8 = 4
                r7.addFlags(r8)
                boolean r8 = r7.isScrap()
                if (r8 == 0) goto L_0x023f
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                android.view.View r9 = r7.itemView
                r8.removeDetachedView(r9, r5)
                androidx.recyclerview.widget.RecyclerView$Recycler r8 = r7.mScrapContainer
                r8.unscrapView(r7)
                goto L_0x024b
            L_0x023f:
                boolean r8 = r7.wasReturnedFromScrap()
                if (r8 == 0) goto L_0x024b
                int r8 = r7.mFlags
                r8 = r8 & -33
                r7.mFlags = r8
            L_0x024b:
                r0.recycleViewHolderInternal(r7)
                r7 = r4
                goto L_0x026b
            L_0x0250:
                r2 = 1
                goto L_0x026b
            L_0x0252:
                java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "Inconsistency detected. Invalid view holder adapter position"
                r2.append(r3)
                r2.append(r7)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r0 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r0, r2)
                r1.<init>(r0)
                throw r1
            L_0x026b:
                if (r7 != 0) goto L_0x043c
                androidx.recyclerview.widget.RecyclerView r12 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r12 = r12.mAdapterHelper
                java.util.Objects.requireNonNull(r12)
                int r12 = r12.findPositionOffset(r1, r5)
                if (r12 < 0) goto L_0x041b
                androidx.recyclerview.widget.RecyclerView r13 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r13 = r13.mAdapter
                int r13 = r13.getItemCount()
                if (r12 >= r13) goto L_0x041b
                androidx.recyclerview.widget.RecyclerView r13 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r13 = r13.mAdapter
                int r13 = r13.getItemViewType(r12)
                androidx.recyclerview.widget.RecyclerView r14 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r14 = r14.mAdapter
                java.util.Objects.requireNonNull(r14)
                boolean r14 = r14.mHasStableIds
                if (r14 == 0) goto L_0x033b
                androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r7 = r7.mAdapter
                long r14 = r7.getItemId(r12)
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mAttachedScrap
                int r7 = r7.size()
                int r7 = r7 + r6
            L_0x02a6:
                if (r7 < 0) goto L_0x0302
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r10 = r0.mAttachedScrap
                java.lang.Object r10 = r10.get(r7)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r10 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r10
                java.util.Objects.requireNonNull(r10)
                long r8 = r10.mItemId
                int r8 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
                if (r8 != 0) goto L_0x02ff
                boolean r8 = r10.wasReturnedFromScrap()
                if (r8 != 0) goto L_0x02ff
                int r8 = r10.mItemViewType
                if (r13 != r8) goto L_0x02e0
                r10.addFlags(r3)
                boolean r3 = r10.isRemoved()
                if (r3 == 0) goto L_0x032c
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r3 = r3.mState
                java.util.Objects.requireNonNull(r3)
                boolean r3 = r3.mInPreLayout
                if (r3 != 0) goto L_0x032c
                int r3 = r10.mFlags
                r3 = r3 & -15
                r3 = r3 | 2
                r10.mFlags = r3
                goto L_0x032c
            L_0x02e0:
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r8 = r0.mAttachedScrap
                r8.remove(r7)
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                android.view.View r9 = r10.itemView
                r8.removeDetachedView(r9, r5)
                android.view.View r8 = r10.itemView
                androidx.recyclerview.widget.RecyclerView$ViewHolder r8 = androidx.recyclerview.widget.RecyclerView.getChildViewHolderInt(r8)
                r8.mScrapContainer = r4
                r8.mInChangeScrap = r5
                int r9 = r8.mFlags
                r9 = r9 & -33
                r8.mFlags = r9
                r0.recycleViewHolderInternal(r8)
            L_0x02ff:
                int r7 = r7 + -1
                goto L_0x02a6
            L_0x0302:
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r0.mCachedViews
                int r3 = r3.size()
                int r3 = r3 + r6
            L_0x0309:
                if (r3 < 0) goto L_0x0335
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mCachedViews
                java.lang.Object r7 = r7.get(r3)
                r10 = r7
                androidx.recyclerview.widget.RecyclerView$ViewHolder r10 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r10
                java.util.Objects.requireNonNull(r10)
                long r7 = r10.mItemId
                int r7 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
                if (r7 != 0) goto L_0x0332
                boolean r7 = r10.isAttachedToTransitionOverlay()
                if (r7 != 0) goto L_0x0332
                int r7 = r10.mItemViewType
                if (r13 != r7) goto L_0x032e
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r0.mCachedViews
                r7.remove(r3)
            L_0x032c:
                r7 = r10
                goto L_0x0336
            L_0x032e:
                r0.recycleCachedViewAt(r3)
                goto L_0x0335
            L_0x0332:
                int r3 = r3 + -1
                goto L_0x0309
            L_0x0335:
                r7 = r4
            L_0x0336:
                if (r7 == 0) goto L_0x033b
                r7.mPosition = r12
                r2 = 1
            L_0x033b:
                if (r7 != 0) goto L_0x0387
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r3 = r0.mRecyclerPool
                if (r3 != 0) goto L_0x0348
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r3 = new androidx.recyclerview.widget.RecyclerView$RecycledViewPool
                r3.<init>()
                r0.mRecyclerPool = r3
            L_0x0348:
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r3 = r0.mRecyclerPool
                java.util.Objects.requireNonNull(r3)
                android.util.SparseArray<androidx.recyclerview.widget.RecyclerView$RecycledViewPool$ScrapData> r3 = r3.mScrap
                java.lang.Object r3 = r3.get(r13)
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool$ScrapData r3 = (androidx.recyclerview.widget.RecyclerView.RecycledViewPool.ScrapData) r3
                if (r3 == 0) goto L_0x037e
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r7 = r3.mScrapHeap
                boolean r7 = r7.isEmpty()
                if (r7 != 0) goto L_0x037e
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r3.mScrapHeap
                int r7 = r3.size()
                int r7 = r7 + r6
            L_0x0366:
                if (r7 < 0) goto L_0x037e
                java.lang.Object r6 = r3.get(r7)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r6
                boolean r6 = r6.isAttachedToTransitionOverlay()
                if (r6 != 0) goto L_0x037b
                java.lang.Object r3 = r3.remove(r7)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r3 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r3
                goto L_0x037f
            L_0x037b:
                int r7 = r7 + -1
                goto L_0x0366
            L_0x037e:
                r3 = r4
            L_0x037f:
                if (r3 == 0) goto L_0x0386
                r3.resetInternal()
                int[] r6 = androidx.recyclerview.widget.RecyclerView.NESTED_SCROLLING_ATTRS
            L_0x0386:
                r7 = r3
            L_0x0387:
                if (r7 != 0) goto L_0x043c
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                java.util.Objects.requireNonNull(r3)
                long r6 = java.lang.System.nanoTime()
                r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r3 = (r19 > r8 ? 1 : (r19 == r8 ? 0 : -1))
                if (r3 == 0) goto L_0x03b8
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r3 = r0.mRecyclerPool
                java.util.Objects.requireNonNull(r3)
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool$ScrapData r3 = r3.getScrapDataForType(r13)
                long r8 = r3.mCreateRunningAverageNs
                r10 = 0
                int r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                if (r3 == 0) goto L_0x03b4
                long r8 = r8 + r6
                int r3 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
                if (r3 >= 0) goto L_0x03b2
                goto L_0x03b4
            L_0x03b2:
                r3 = r5
                goto L_0x03b5
            L_0x03b4:
                r3 = 1
            L_0x03b5:
                if (r3 != 0) goto L_0x03b8
                return r4
            L_0x03b8:
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r8 = r3.mAdapter
                java.util.Objects.requireNonNull(r8)
                java.lang.String r9 = "RV CreateView"
                android.os.Trace.beginSection(r9)     // Catch:{ all -> 0x0416 }
                androidx.recyclerview.widget.RecyclerView$ViewHolder r3 = r8.onCreateViewHolder(r3, r13)     // Catch:{ all -> 0x0416 }
                android.view.View r8 = r3.itemView     // Catch:{ all -> 0x0416 }
                android.view.ViewParent r8 = r8.getParent()     // Catch:{ all -> 0x0416 }
                if (r8 != 0) goto L_0x040e
                r3.mItemViewType = r13     // Catch:{ all -> 0x0416 }
                android.os.Trace.endSection()
                int[] r8 = androidx.recyclerview.widget.RecyclerView.NESTED_SCROLLING_ATTRS
                android.view.View r8 = r3.itemView
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.findNestedRecyclerView(r8)
                if (r8 == 0) goto L_0x03e6
                java.lang.ref.WeakReference r9 = new java.lang.ref.WeakReference
                r9.<init>(r8)
                r3.mNestedRecyclerView = r9
            L_0x03e6:
                androidx.recyclerview.widget.RecyclerView r8 = androidx.recyclerview.widget.RecyclerView.this
                java.util.Objects.requireNonNull(r8)
                long r8 = java.lang.System.nanoTime()
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r10 = r0.mRecyclerPool
                long r8 = r8 - r6
                java.util.Objects.requireNonNull(r10)
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool$ScrapData r6 = r10.getScrapDataForType(r13)
                long r10 = r6.mCreateRunningAverageNs
                r12 = 0
                int r7 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                if (r7 != 0) goto L_0x0402
                goto L_0x040a
            L_0x0402:
                r12 = 4
                long r10 = r10 / r12
                r14 = 3
                long r10 = r10 * r14
                long r8 = r8 / r12
                long r8 = r8 + r10
            L_0x040a:
                r6.mCreateRunningAverageNs = r8
                r7 = r3
                goto L_0x043c
            L_0x040e:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0416 }
                java.lang.String r1 = "ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)"
                r0.<init>(r1)     // Catch:{ all -> 0x0416 }
                throw r0     // Catch:{ all -> 0x0416 }
            L_0x0416:
                r0 = move-exception
                android.os.Trace.endSection()
                throw r0
            L_0x041b:
                java.lang.IndexOutOfBoundsException r2 = new java.lang.IndexOutOfBoundsException
                java.lang.String r3 = "Inconsistency detected. Invalid item position "
                java.lang.String r4 = "(offset:"
                java.lang.String r5 = ").state:"
                java.lang.StringBuilder r1 = androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0.m19m(r3, r1, r4, r12, r5)
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r3 = r3.mState
                int r3 = r3.getItemCount()
                r1.append(r3)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r0 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r0, r1)
                r2.<init>(r0)
                throw r2
            L_0x043c:
                if (r2 == 0) goto L_0x047b
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r3 = r3.mState
                java.util.Objects.requireNonNull(r3)
                boolean r3 = r3.mInPreLayout
                if (r3 != 0) goto L_0x047b
                int r3 = r7.mFlags
                r6 = r3 & 8192(0x2000, float:1.14794E-41)
                if (r6 == 0) goto L_0x0451
                r6 = 1
                goto L_0x0452
            L_0x0451:
                r6 = r5
            L_0x0452:
                if (r6 == 0) goto L_0x047b
                r3 = r3 & -8193(0xffffffffffffdfff, float:NaN)
                r3 = r3 | r5
                r7.mFlags = r3
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r3 = r3.mState
                boolean r3 = r3.mRunSimpleAnimations
                if (r3 == 0) goto L_0x047b
                androidx.recyclerview.widget.RecyclerView.ItemAnimator.buildAdapterChangeFlagsForAnimations(r7)
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$ItemAnimator r3 = r3.mItemAnimator
                r7.getUnmodifiedPayloads()
                java.util.Objects.requireNonNull(r3)
                androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r3 = new androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo
                r3.<init>()
                r3.setFrom(r7)
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                r6.recordAnimationInfoIfBouncedHiddenView(r7, r3)
            L_0x047b:
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r3 = r3.mState
                java.util.Objects.requireNonNull(r3)
                boolean r3 = r3.mInPreLayout
                if (r3 == 0) goto L_0x048f
                boolean r3 = r7.isBound()
                if (r3 == 0) goto L_0x048f
                r7.mPreLayoutPosition = r1
                goto L_0x04e5
            L_0x048f:
                boolean r3 = r7.isBound()
                if (r3 == 0) goto L_0x04a6
                int r3 = r7.mFlags
                r3 = r3 & 2
                if (r3 == 0) goto L_0x049d
                r3 = 1
                goto L_0x049e
            L_0x049d:
                r3 = r5
            L_0x049e:
                if (r3 != 0) goto L_0x04a6
                boolean r3 = r7.isInvalid()
                if (r3 == 0) goto L_0x04e5
            L_0x04a6:
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r3 = r3.mAdapterHelper
                java.util.Objects.requireNonNull(r3)
                int r3 = r3.findPositionOffset(r1, r5)
                r7.mBindingAdapter = r4
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                r7.mOwnerRecyclerView = r6
                int r8 = r7.mItemViewType
                java.util.Objects.requireNonNull(r6)
                long r9 = java.lang.System.nanoTime()
                r11 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r6 = (r19 > r11 ? 1 : (r19 == r11 ? 0 : -1))
                if (r6 == 0) goto L_0x04e9
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r6 = r0.mRecyclerPool
                java.util.Objects.requireNonNull(r6)
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool$ScrapData r6 = r6.getScrapDataForType(r8)
                long r11 = r6.mBindRunningAverageNs
                r13 = 0
                int r6 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
                if (r6 == 0) goto L_0x04e2
                long r11 = r11 + r9
                int r6 = (r11 > r19 ? 1 : (r11 == r19 ? 0 : -1))
                if (r6 >= 0) goto L_0x04e0
                goto L_0x04e2
            L_0x04e0:
                r6 = r5
                goto L_0x04e3
            L_0x04e2:
                r6 = 1
            L_0x04e3:
                if (r6 != 0) goto L_0x04e9
            L_0x04e5:
                r1 = 1
                r3 = r5
                goto L_0x05cd
            L_0x04e9:
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r6 = r6.mAdapter
                java.util.Objects.requireNonNull(r6)
                androidx.recyclerview.widget.RecyclerView$Adapter<? extends androidx.recyclerview.widget.RecyclerView$ViewHolder> r8 = r7.mBindingAdapter
                if (r8 != 0) goto L_0x04f6
                r8 = 1
                goto L_0x04f7
            L_0x04f6:
                r8 = r5
            L_0x04f7:
                if (r8 == 0) goto L_0x0512
                r7.mPosition = r3
                boolean r11 = r6.mHasStableIds
                if (r11 == 0) goto L_0x0505
                long r11 = r6.getItemId(r3)
                r7.mItemId = r11
            L_0x0505:
                int r11 = r7.mFlags
                r11 = r11 & -520(0xfffffffffffffdf8, float:NaN)
                r11 = r11 | 1
                r7.mFlags = r11
                java.lang.String r11 = "RV OnBindView"
                android.os.Trace.beginSection(r11)
            L_0x0512:
                r7.mBindingAdapter = r6
                java.util.List r11 = r7.getUnmodifiedPayloads()
                r6.onBindViewHolder(r7, r3, r11)
                if (r8 == 0) goto L_0x053c
                java.util.ArrayList r3 = r7.mPayloads
                if (r3 == 0) goto L_0x0524
                r3.clear()
            L_0x0524:
                int r3 = r7.mFlags
                r3 = r3 & -1025(0xfffffffffffffbff, float:NaN)
                r7.mFlags = r3
                android.view.View r3 = r7.itemView
                android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
                boolean r6 = r3 instanceof androidx.recyclerview.widget.RecyclerView.LayoutParams
                if (r6 == 0) goto L_0x0539
                androidx.recyclerview.widget.RecyclerView$LayoutParams r3 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r3
                r6 = 1
                r3.mInsetsDirty = r6
            L_0x0539:
                android.os.Trace.endSection()
            L_0x053c:
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                java.util.Objects.requireNonNull(r3)
                long r11 = java.lang.System.nanoTime()
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool r3 = r0.mRecyclerPool
                int r6 = r7.mItemViewType
                long r11 = r11 - r9
                java.util.Objects.requireNonNull(r3)
                androidx.recyclerview.widget.RecyclerView$RecycledViewPool$ScrapData r3 = r3.getScrapDataForType(r6)
                long r8 = r3.mBindRunningAverageNs
                r13 = 0
                int r6 = (r8 > r13 ? 1 : (r8 == r13 ? 0 : -1))
                if (r6 != 0) goto L_0x055a
                goto L_0x0562
            L_0x055a:
                r13 = 4
                long r8 = r8 / r13
                r15 = 3
                long r8 = r8 * r15
                long r11 = r11 / r13
                long r11 = r11 + r8
            L_0x0562:
                r3.mBindRunningAverageNs = r11
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                java.util.Objects.requireNonNull(r3)
                android.view.accessibility.AccessibilityManager r3 = r3.mAccessibilityManager
                if (r3 == 0) goto L_0x0575
                boolean r3 = r3.isEnabled()
                if (r3 == 0) goto L_0x0575
                r3 = 1
                goto L_0x0576
            L_0x0575:
                r3 = r5
            L_0x0576:
                if (r3 == 0) goto L_0x05be
                android.view.View r3 = r7.itemView
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                int r6 = androidx.core.view.ViewCompat.Api16Impl.getImportantForAccessibility(r3)
                r8 = 1
                if (r6 != 0) goto L_0x0586
                androidx.core.view.ViewCompat.Api16Impl.setImportantForAccessibility(r3, r8)
            L_0x0586:
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate r6 = r6.mAccessibilityDelegate
                if (r6 != 0) goto L_0x058d
                goto L_0x05bc
            L_0x058d:
                androidx.core.view.AccessibilityDelegateCompat r6 = r6.getItemDelegate()
                boolean r9 = r6 instanceof androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate.ItemDelegate
                if (r9 == 0) goto L_0x05b9
                r9 = r6
                androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate$ItemDelegate r9 = (androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate.ItemDelegate) r9
                java.util.Objects.requireNonNull(r9)
                android.view.View$AccessibilityDelegate r10 = androidx.core.view.ViewCompat.Api29Impl.getAccessibilityDelegate(r3)
                if (r10 != 0) goto L_0x05a2
                goto L_0x05b0
            L_0x05a2:
                boolean r4 = r10 instanceof androidx.core.view.AccessibilityDelegateCompat.AccessibilityDelegateAdapter
                if (r4 == 0) goto L_0x05ab
                androidx.core.view.AccessibilityDelegateCompat$AccessibilityDelegateAdapter r10 = (androidx.core.view.AccessibilityDelegateCompat.AccessibilityDelegateAdapter) r10
                androidx.core.view.AccessibilityDelegateCompat r4 = r10.mCompat
                goto L_0x05b0
            L_0x05ab:
                androidx.core.view.AccessibilityDelegateCompat r4 = new androidx.core.view.AccessibilityDelegateCompat
                r4.<init>(r10)
            L_0x05b0:
                if (r4 == 0) goto L_0x05b9
                if (r4 == r9) goto L_0x05b9
                java.util.WeakHashMap r9 = r9.mOriginalItemDelegates
                r9.put(r3, r4)
            L_0x05b9:
                androidx.core.view.ViewCompat.setAccessibilityDelegate(r3, r6)
            L_0x05bc:
                r3 = r8
                goto L_0x05bf
            L_0x05be:
                r3 = 1
            L_0x05bf:
                androidx.recyclerview.widget.RecyclerView r4 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r4 = r4.mState
                java.util.Objects.requireNonNull(r4)
                boolean r4 = r4.mInPreLayout
                if (r4 == 0) goto L_0x05cc
                r7.mPreLayoutPosition = r1
            L_0x05cc:
                r1 = r3
            L_0x05cd:
                android.view.View r4 = r7.itemView
                android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
                if (r4 != 0) goto L_0x05e3
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r0 = r0.generateDefaultLayoutParams()
                androidx.recyclerview.widget.RecyclerView$LayoutParams r0 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r0
                android.view.View r4 = r7.itemView
                r4.setLayoutParams(r0)
                goto L_0x05fc
            L_0x05e3:
                androidx.recyclerview.widget.RecyclerView r6 = androidx.recyclerview.widget.RecyclerView.this
                boolean r6 = r6.checkLayoutParams(r4)
                if (r6 != 0) goto L_0x05f9
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r0 = r0.generateLayoutParams((android.view.ViewGroup.LayoutParams) r4)
                androidx.recyclerview.widget.RecyclerView$LayoutParams r0 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r0
                android.view.View r4 = r7.itemView
                r4.setLayoutParams(r0)
                goto L_0x05fc
            L_0x05f9:
                r0 = r4
                androidx.recyclerview.widget.RecyclerView$LayoutParams r0 = (androidx.recyclerview.widget.RecyclerView.LayoutParams) r0
            L_0x05fc:
                r0.mViewHolder = r7
                if (r2 == 0) goto L_0x0603
                if (r3 == 0) goto L_0x0603
                r5 = r1
            L_0x0603:
                r0.mPendingInvalidate = r5
                return r7
            L_0x0606:
                java.lang.IndexOutOfBoundsException r2 = new java.lang.IndexOutOfBoundsException
                java.lang.String r3 = "Invalid item position "
                java.lang.String r4 = "("
                java.lang.String r5 = "). Item count:"
                java.lang.StringBuilder r1 = androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0.m19m(r3, r1, r4, r1, r5)
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$State r3 = r3.mState
                int r3 = r3.getItemCount()
                r1.append(r3)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r0 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r0, r1)
                r2.<init>(r0)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.Recycler.tryGetViewHolderForPositionByDeadline(int, long):androidx.recyclerview.widget.RecyclerView$ViewHolder");
        }

        public final void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.mFlags &= -33;
        }

        public final void updateViewCacheSize() {
            int i;
            LayoutManager layoutManager = RecyclerView.this.mLayout;
            if (layoutManager != null) {
                i = layoutManager.mPrefetchMaxCountObserved;
            } else {
                i = 0;
            }
            this.mViewCacheMax = this.mRequestedCacheMax + i;
            for (int size = this.mCachedViews.size() - 1; size >= 0 && this.mCachedViews.size() > this.mViewCacheMax; size--) {
                recycleCachedViewAt(size);
            }
        }

        public final void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean z) {
            AccessibilityDelegateCompat accessibilityDelegateCompat;
            RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            View view = viewHolder.itemView;
            RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = RecyclerView.this.mAccessibilityDelegate;
            if (recyclerViewAccessibilityDelegate != null) {
                AccessibilityDelegateCompat itemDelegate = recyclerViewAccessibilityDelegate.getItemDelegate();
                if (itemDelegate instanceof RecyclerViewAccessibilityDelegate.ItemDelegate) {
                    RecyclerViewAccessibilityDelegate.ItemDelegate itemDelegate2 = (RecyclerViewAccessibilityDelegate.ItemDelegate) itemDelegate;
                    Objects.requireNonNull(itemDelegate2);
                    accessibilityDelegateCompat = (AccessibilityDelegateCompat) itemDelegate2.mOriginalItemDelegates.remove(view);
                } else {
                    accessibilityDelegateCompat = null;
                }
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegateCompat);
            }
            if (z) {
                Objects.requireNonNull(RecyclerView.this);
                int size = RecyclerView.this.mRecyclerListeners.size();
                for (int i = 0; i < size; i++) {
                    ((RecyclerListener) RecyclerView.this.mRecyclerListeners.get(i)).onViewRecycled(viewHolder);
                }
                Adapter adapter = RecyclerView.this.mAdapter;
                if (adapter != null) {
                    adapter.onViewRecycled(viewHolder);
                }
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.mState != null) {
                    recyclerView.mViewInfoStore.removeViewHolder(viewHolder);
                }
            }
            viewHolder.mBindingAdapter = null;
            viewHolder.mOwnerRecyclerView = null;
            if (this.mRecyclerPool == null) {
                this.mRecyclerPool = new RecycledViewPool();
            }
            RecycledViewPool recycledViewPool = this.mRecyclerPool;
            Objects.requireNonNull(recycledViewPool);
            int i2 = viewHolder.mItemViewType;
            ArrayList<ViewHolder> arrayList = recycledViewPool.getScrapDataForType(i2).mScrapHeap;
            if (recycledViewPool.mScrap.get(i2).mMaxScrap > arrayList.size()) {
                viewHolder.resetInternal();
                arrayList.add(viewHolder);
            }
        }

        public final void recycleView(View view) {
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (childViewHolderInt.isScrap()) {
                childViewHolderInt.mScrapContainer.unscrapView(childViewHolderInt);
            } else if (childViewHolderInt.wasReturnedFromScrap()) {
                childViewHolderInt.mFlags &= -33;
            }
            recycleViewHolderInternal(childViewHolderInt);
            if (RecyclerView.this.mItemAnimator != null && !childViewHolderInt.isRecyclable()) {
                RecyclerView.this.mItemAnimator.endAnimation(childViewHolderInt);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x004b  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x004e  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0094 A[LOOP:1: B:48:0x0094->B:59:0x00c0, LOOP_START, PHI: r3 
          PHI: (r3v16 int) = (r3v13 int), (r3v17 int) binds: [B:47:0x0092, B:59:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARNING: Removed duplicated region for block: B:64:0x00cd  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x00d1  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void recycleViewHolderInternal(androidx.recyclerview.widget.RecyclerView.ViewHolder r10) {
            /*
                r9 = this;
                boolean r0 = r10.isScrap()
                r1 = 1
                r2 = 0
                if (r0 != 0) goto L_0x0111
                android.view.View r0 = r10.itemView
                android.view.ViewParent r0 = r0.getParent()
                if (r0 == 0) goto L_0x0012
                goto L_0x0111
            L_0x0012:
                boolean r0 = r10.isTmpDetached()
                if (r0 != 0) goto L_0x00f8
                boolean r0 = r10.shouldIgnore()
                if (r0 != 0) goto L_0x00e6
                int r0 = r10.mFlags
                r0 = r0 & 16
                if (r0 != 0) goto L_0x0030
                android.view.View r0 = r10.itemView
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                boolean r0 = androidx.core.view.ViewCompat.Api16Impl.hasTransientState(r0)
                if (r0 == 0) goto L_0x0030
                r0 = r1
                goto L_0x0031
            L_0x0030:
                r0 = r2
            L_0x0031:
                androidx.recyclerview.widget.RecyclerView r3 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$Adapter r3 = r3.mAdapter
                if (r3 == 0) goto L_0x0041
                if (r0 == 0) goto L_0x0041
                boolean r3 = r3.onFailedToRecycleView(r10)
                if (r3 == 0) goto L_0x0041
                r3 = r1
                goto L_0x0042
            L_0x0041:
                r3 = r2
            L_0x0042:
                if (r3 != 0) goto L_0x004e
                boolean r3 = r10.isRecyclable()
                if (r3 == 0) goto L_0x004b
                goto L_0x004e
            L_0x004b:
                r1 = r2
                goto L_0x00d3
            L_0x004e:
                int r3 = r9.mViewCacheMax
                if (r3 <= 0) goto L_0x00ca
                int r3 = r10.mFlags
                r3 = r3 & 526(0x20e, float:7.37E-43)
                if (r3 == 0) goto L_0x005a
                r3 = r1
                goto L_0x005b
            L_0x005a:
                r3 = r2
            L_0x005b:
                if (r3 != 0) goto L_0x00ca
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r3 = r9.mCachedViews
                int r3 = r3.size()
                int r4 = r9.mViewCacheMax
                if (r3 < r4) goto L_0x006e
                if (r3 <= 0) goto L_0x006e
                r9.recycleCachedViewAt(r2)
                int r3 = r3 + -1
            L_0x006e:
                int[] r4 = androidx.recyclerview.widget.RecyclerView.NESTED_SCROLLING_ATTRS
                if (r3 <= 0) goto L_0x00c3
                androidx.recyclerview.widget.RecyclerView r4 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.GapWorker$LayoutPrefetchRegistryImpl r4 = r4.mPrefetchRegistry
                int r5 = r10.mPosition
                java.util.Objects.requireNonNull(r4)
                int[] r6 = r4.mPrefetchArray
                if (r6 == 0) goto L_0x0091
                int r6 = r4.mCount
                int r6 = r6 * 2
                r7 = r2
            L_0x0084:
                if (r7 >= r6) goto L_0x0091
                int[] r8 = r4.mPrefetchArray
                r8 = r8[r7]
                if (r8 != r5) goto L_0x008e
                r4 = r1
                goto L_0x0092
            L_0x008e:
                int r7 = r7 + 2
                goto L_0x0084
            L_0x0091:
                r4 = r2
            L_0x0092:
                if (r4 != 0) goto L_0x00c3
            L_0x0094:
                int r3 = r3 + -1
                if (r3 < 0) goto L_0x00c2
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r4 = r9.mCachedViews
                java.lang.Object r4 = r4.get(r3)
                androidx.recyclerview.widget.RecyclerView$ViewHolder r4 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r4
                int r4 = r4.mPosition
                androidx.recyclerview.widget.RecyclerView r5 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.GapWorker$LayoutPrefetchRegistryImpl r5 = r5.mPrefetchRegistry
                java.util.Objects.requireNonNull(r5)
                int[] r6 = r5.mPrefetchArray
                if (r6 == 0) goto L_0x00bf
                int r6 = r5.mCount
                int r6 = r6 * 2
                r7 = r2
            L_0x00b2:
                if (r7 >= r6) goto L_0x00bf
                int[] r8 = r5.mPrefetchArray
                r8 = r8[r7]
                if (r8 != r4) goto L_0x00bc
                r4 = r1
                goto L_0x00c0
            L_0x00bc:
                int r7 = r7 + 2
                goto L_0x00b2
            L_0x00bf:
                r4 = r2
            L_0x00c0:
                if (r4 != 0) goto L_0x0094
            L_0x00c2:
                int r3 = r3 + r1
            L_0x00c3:
                java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r4 = r9.mCachedViews
                r4.add(r3, r10)
                r3 = r1
                goto L_0x00cb
            L_0x00ca:
                r3 = r2
            L_0x00cb:
                if (r3 != 0) goto L_0x00d1
                r9.addViewHolderToRecycledViewPool(r10, r1)
                goto L_0x00d2
            L_0x00d1:
                r1 = r2
            L_0x00d2:
                r2 = r3
            L_0x00d3:
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.ViewInfoStore r9 = r9.mViewInfoStore
                r9.removeViewHolder(r10)
                if (r2 != 0) goto L_0x00e5
                if (r1 != 0) goto L_0x00e5
                if (r0 == 0) goto L_0x00e5
                r9 = 0
                r10.mBindingAdapter = r9
                r10.mOwnerRecyclerView = r9
            L_0x00e5:
                return
            L_0x00e6:
                java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
                java.lang.String r0 = "Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle."
                java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r9 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r9, r0)
                r10.<init>(r9)
                throw r10
            L_0x00f8:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Tmp detached view should be removed from RecyclerView before it can be recycled: "
                r1.append(r2)
                r1.append(r10)
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r9 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r9, r1)
                r0.<init>(r9)
                throw r0
            L_0x0111:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.String r3 = "Scrapped or attached views may not be recycled. isScrap:"
                java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
                boolean r4 = r10.isScrap()
                r3.append(r4)
                java.lang.String r4 = " isAttached:"
                r3.append(r4)
                android.view.View r10 = r10.itemView
                android.view.ViewParent r10 = r10.getParent()
                if (r10 == 0) goto L_0x012e
                goto L_0x012f
            L_0x012e:
                r1 = r2
            L_0x012f:
                r3.append(r1)
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r9 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r9, r3)
                r0.<init>(r9)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.Recycler.recycleViewHolderInternal(androidx.recyclerview.widget.RecyclerView$ViewHolder):void");
        }

        public final void scrapView(View view) {
            boolean z;
            boolean z2;
            boolean z3;
            ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            Objects.requireNonNull(childViewHolderInt);
            int i = childViewHolderInt.mFlags;
            if ((i & 12) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if ((i & 2) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    RecyclerView recyclerView = RecyclerView.this;
                    Objects.requireNonNull(recyclerView);
                    ItemAnimator itemAnimator = recyclerView.mItemAnimator;
                    if (itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(childViewHolderInt, childViewHolderInt.getUnmodifiedPayloads())) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (!z3) {
                        if (this.mChangedScrap == null) {
                            this.mChangedScrap = new ArrayList<>();
                        }
                        childViewHolderInt.mScrapContainer = this;
                        childViewHolderInt.mInChangeScrap = true;
                        this.mChangedScrap.add(childViewHolderInt);
                        return;
                    }
                }
            }
            if (childViewHolderInt.isInvalid() && !childViewHolderInt.isRemoved()) {
                Adapter adapter = RecyclerView.this.mAdapter;
                Objects.requireNonNull(adapter);
                if (!adapter.mHasStableIds) {
                    throw new IllegalArgumentException(ChildHelper$$ExternalSyntheticOutline0.m18m(RecyclerView.this, VendorAtomValue$$ExternalSyntheticOutline1.m1m("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.")));
                }
            }
            childViewHolderInt.mScrapContainer = this;
            childViewHolderInt.mInChangeScrap = false;
            this.mAttachedScrap.add(childViewHolderInt);
        }

        public final View getViewForPosition(int i) {
            return tryGetViewHolderForPositionByDeadline(i, Long.MAX_VALUE).itemView;
        }
    }

    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder);
    }

    public class RecyclerViewDataObserver extends AdapterDataObserver {
        public RecyclerViewDataObserver() {
        }

        public final void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll((String) null);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.mState.mStructureChanged = true;
            recyclerView.processDataSetCompletelyChanged(true);
            if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
                RecyclerView.this.requestLayout();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0026, code lost:
            if (r0.mPendingUpdates.size() == 1) goto L_0x002a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onItemRangeChanged(int r5, int r6, java.lang.Object r7) {
            /*
                r4 = this;
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                r1 = 0
                r0.assertNotInLayoutOrScroll(r1)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r0 = r0.mAdapterHelper
                r1 = 1
                if (r6 >= r1) goto L_0x0011
                java.util.Objects.requireNonNull(r0)
                goto L_0x0029
            L_0x0011:
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r2 = r0.mPendingUpdates
                r3 = 4
                androidx.recyclerview.widget.AdapterHelper$UpdateOp r5 = r0.obtainUpdateOp(r3, r5, r6, r7)
                r2.add(r5)
                int r5 = r0.mExistingUpdateTypes
                r5 = r5 | r3
                r0.mExistingUpdateTypes = r5
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r5 = r0.mPendingUpdates
                int r5 = r5.size()
                if (r5 != r1) goto L_0x0029
                goto L_0x002a
            L_0x0029:
                r1 = 0
            L_0x002a:
                if (r1 == 0) goto L_0x002f
                r4.triggerUpdateProcessor()
            L_0x002f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.RecyclerViewDataObserver.onItemRangeChanged(int, int, java.lang.Object):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0025, code lost:
            if (r0.mPendingUpdates.size() == 1) goto L_0x0029;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onItemRangeInserted(int r5, int r6) {
            /*
                r4 = this;
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                r1 = 0
                r0.assertNotInLayoutOrScroll(r1)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r0 = r0.mAdapterHelper
                r2 = 1
                if (r6 >= r2) goto L_0x0011
                java.util.Objects.requireNonNull(r0)
                goto L_0x0028
            L_0x0011:
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r3 = r0.mPendingUpdates
                androidx.recyclerview.widget.AdapterHelper$UpdateOp r5 = r0.obtainUpdateOp(r2, r5, r6, r1)
                r3.add(r5)
                int r5 = r0.mExistingUpdateTypes
                r5 = r5 | r2
                r0.mExistingUpdateTypes = r5
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r5 = r0.mPendingUpdates
                int r5 = r5.size()
                if (r5 != r2) goto L_0x0028
                goto L_0x0029
            L_0x0028:
                r2 = 0
            L_0x0029:
                if (r2 == 0) goto L_0x002e
                r4.triggerUpdateProcessor()
            L_0x002e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.RecyclerViewDataObserver.onItemRangeInserted(int, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0027, code lost:
            if (r0.mPendingUpdates.size() == 1) goto L_0x002b;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onItemRangeMoved(int r6, int r7) {
            /*
                r5 = this;
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                r1 = 0
                r0.assertNotInLayoutOrScroll(r1)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r0 = r0.mAdapterHelper
                java.util.Objects.requireNonNull(r0)
                if (r6 != r7) goto L_0x0010
                goto L_0x002a
            L_0x0010:
                r2 = 1
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r3 = r0.mPendingUpdates
                r4 = 8
                androidx.recyclerview.widget.AdapterHelper$UpdateOp r6 = r0.obtainUpdateOp(r4, r6, r7, r1)
                r3.add(r6)
                int r6 = r0.mExistingUpdateTypes
                r6 = r6 | r4
                r0.mExistingUpdateTypes = r6
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r6 = r0.mPendingUpdates
                int r6 = r6.size()
                if (r6 != r2) goto L_0x002a
                goto L_0x002b
            L_0x002a:
                r2 = 0
            L_0x002b:
                if (r2 == 0) goto L_0x0030
                r5.triggerUpdateProcessor()
            L_0x0030:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.RecyclerViewDataObserver.onItemRangeMoved(int, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0026, code lost:
            if (r0.mPendingUpdates.size() == 1) goto L_0x002a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onItemRangeRemoved(int r6, int r7) {
            /*
                r5 = this;
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                r1 = 0
                r0.assertNotInLayoutOrScroll(r1)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.AdapterHelper r0 = r0.mAdapterHelper
                r2 = 1
                if (r7 >= r2) goto L_0x0011
                java.util.Objects.requireNonNull(r0)
                goto L_0x0029
            L_0x0011:
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r3 = r0.mPendingUpdates
                r4 = 2
                androidx.recyclerview.widget.AdapterHelper$UpdateOp r6 = r0.obtainUpdateOp(r4, r6, r7, r1)
                r3.add(r6)
                int r6 = r0.mExistingUpdateTypes
                r6 = r6 | r4
                r0.mExistingUpdateTypes = r6
                java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r6 = r0.mPendingUpdates
                int r6 = r6.size()
                if (r6 != r2) goto L_0x0029
                goto L_0x002a
            L_0x0029:
                r2 = 0
            L_0x002a:
                if (r2 == 0) goto L_0x002f
                r5.triggerUpdateProcessor()
            L_0x002f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.RecyclerViewDataObserver.onItemRangeRemoved(int, int):void");
        }

        public final void triggerUpdateProcessor() {
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
            RecyclerView recyclerView = RecyclerView.this;
            if (!recyclerView.mHasFixedSize || !recyclerView.mIsAttached) {
                recyclerView.mAdapterUpdateDuringMeasure = true;
                recyclerView.requestLayout();
                return;
            }
            C03321 r0 = recyclerView.mUpdateChildViewsRunnable;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(recyclerView, r0);
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public Parcelable mLayoutState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mLayoutState = parcel.readParcelable(classLoader == null ? LayoutManager.class.getClassLoader() : classLoader);
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public static abstract class SmoothScroller {
        public LayoutManager mLayoutManager;
        public boolean mPendingInitialRun;
        public RecyclerView mRecyclerView;
        public final Action mRecyclingAction = new Action();
        public boolean mRunning;
        public boolean mStarted;
        public int mTargetPosition = -1;
        public View mTargetView;

        public static class Action {
            public boolean mChanged = false;
            public int mConsecutiveUpdates = 0;
            public int mDuration = Integer.MIN_VALUE;
            public int mDx = 0;
            public int mDy = 0;
            public Interpolator mInterpolator = null;
            public int mJumpToPosition = -1;

            public final void runIfNecessary(RecyclerView recyclerView) {
                int i = this.mJumpToPosition;
                if (i >= 0) {
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(i);
                    this.mChanged = false;
                } else if (this.mChanged) {
                    Interpolator interpolator = this.mInterpolator;
                    if (interpolator == null || this.mDuration >= 1) {
                        int i2 = this.mDuration;
                        if (i2 >= 1) {
                            recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, i2, interpolator);
                            int i3 = this.mConsecutiveUpdates + 1;
                            this.mConsecutiveUpdates = i3;
                            if (i3 > 10) {
                                Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                            }
                            this.mChanged = false;
                            return;
                        }
                        throw new IllegalStateException("Scroll duration must be a positive number");
                    }
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else {
                    this.mConsecutiveUpdates = 0;
                }
            }

            public final void update(int i, int i2, int i3, BaseInterpolator baseInterpolator) {
                this.mDx = i;
                this.mDy = i2;
                this.mDuration = i3;
                this.mInterpolator = baseInterpolator;
                this.mChanged = true;
            }
        }

        public interface ScrollVectorProvider {
            PointF computeScrollVectorForPosition(int i);
        }

        public abstract void onStop();

        public abstract void onTargetFound(View view, Action action);

        public PointF computeScrollVectorForPosition(int i) {
            LayoutManager layoutManager = this.mLayoutManager;
            if (layoutManager instanceof ScrollVectorProvider) {
                return ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(i);
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("You should override computeScrollVectorForPosition when the LayoutManager does not implement ");
            m.append(ScrollVectorProvider.class.getCanonicalName());
            Log.w("RecyclerView", m.toString());
            return null;
        }

        public final void onAnimation(int i, int i2) {
            PointF computeScrollVectorForPosition;
            RecyclerView recyclerView = this.mRecyclerView;
            int i3 = -1;
            if (this.mTargetPosition == -1 || recyclerView == null) {
                stop();
            }
            if (this.mPendingInitialRun && this.mTargetView == null && this.mLayoutManager != null && (computeScrollVectorForPosition = computeScrollVectorForPosition(this.mTargetPosition)) != null) {
                float f = computeScrollVectorForPosition.x;
                if (!(f == 0.0f && computeScrollVectorForPosition.y == 0.0f)) {
                    recyclerView.scrollStep((int) Math.signum(f), (int) Math.signum(computeScrollVectorForPosition.y), (int[]) null);
                }
            }
            boolean z = false;
            this.mPendingInitialRun = false;
            View view = this.mTargetView;
            if (view != null) {
                Objects.requireNonNull(this.mRecyclerView);
                ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    i3 = childViewHolderInt.getLayoutPosition();
                }
                if (i3 == this.mTargetPosition) {
                    View view2 = this.mTargetView;
                    State state = recyclerView.mState;
                    onTargetFound(view2, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    stop();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (this.mRunning) {
                State state2 = recyclerView.mState;
                Action action = this.mRecyclingAction;
                LinearSmoothScroller linearSmoothScroller = (LinearSmoothScroller) this;
                if (linearSmoothScroller.mRecyclerView.mLayout.getChildCount() == 0) {
                    linearSmoothScroller.stop();
                } else {
                    int i4 = linearSmoothScroller.mInterimTargetDx;
                    int i5 = i4 - i;
                    if (i4 * i5 <= 0) {
                        i5 = 0;
                    }
                    linearSmoothScroller.mInterimTargetDx = i5;
                    int i6 = linearSmoothScroller.mInterimTargetDy;
                    int i7 = i6 - i2;
                    if (i6 * i7 <= 0) {
                        i7 = 0;
                    }
                    linearSmoothScroller.mInterimTargetDy = i7;
                    if (i5 == 0 && i7 == 0) {
                        PointF computeScrollVectorForPosition2 = linearSmoothScroller.computeScrollVectorForPosition(linearSmoothScroller.mTargetPosition);
                        if (computeScrollVectorForPosition2 != null) {
                            float f2 = computeScrollVectorForPosition2.x;
                            if (!(f2 == 0.0f && computeScrollVectorForPosition2.y == 0.0f)) {
                                float f3 = computeScrollVectorForPosition2.y;
                                float sqrt = (float) Math.sqrt((double) ((f3 * f3) + (f2 * f2)));
                                float f4 = computeScrollVectorForPosition2.x / sqrt;
                                computeScrollVectorForPosition2.x = f4;
                                float f5 = computeScrollVectorForPosition2.y / sqrt;
                                computeScrollVectorForPosition2.y = f5;
                                linearSmoothScroller.mTargetVector = computeScrollVectorForPosition2;
                                linearSmoothScroller.mInterimTargetDx = (int) (f4 * 10000.0f);
                                linearSmoothScroller.mInterimTargetDy = (int) (f5 * 10000.0f);
                                action.update((int) (((float) linearSmoothScroller.mInterimTargetDx) * 1.2f), (int) (((float) linearSmoothScroller.mInterimTargetDy) * 1.2f), (int) (((float) linearSmoothScroller.calculateTimeForScrolling(10000)) * 1.2f), linearSmoothScroller.mLinearInterpolator);
                            }
                        }
                        int i8 = linearSmoothScroller.mTargetPosition;
                        Objects.requireNonNull(action);
                        action.mJumpToPosition = i8;
                        linearSmoothScroller.stop();
                    }
                }
                Action action2 = this.mRecyclingAction;
                Objects.requireNonNull(action2);
                if (action2.mJumpToPosition >= 0) {
                    z = true;
                }
                this.mRecyclingAction.runIfNecessary(recyclerView);
                if (z && this.mRunning) {
                    this.mPendingInitialRun = true;
                    recyclerView.mViewFlinger.postOnAnimation();
                }
            }
        }

        public final void stop() {
            if (this.mRunning) {
                this.mRunning = false;
                onStop();
                this.mRecyclerView.mState.mTargetPosition = -1;
                this.mTargetView = null;
                this.mTargetPosition = -1;
                this.mPendingInitialRun = false;
                LayoutManager layoutManager = this.mLayoutManager;
                Objects.requireNonNull(layoutManager);
                if (layoutManager.mSmoothScroller == this) {
                    layoutManager.mSmoothScroller = null;
                }
                this.mLayoutManager = null;
                this.mRecyclerView = null;
            }
        }
    }

    public static class State {
        public int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        public long mFocusedItemId;
        public int mFocusedItemPosition;
        public int mFocusedSubChildId;
        public boolean mInPreLayout = false;
        public boolean mIsMeasuring = false;
        public int mItemCount = 0;
        public int mLayoutStep = 1;
        public int mPreviousLayoutItemCount = 0;
        public int mRemainingScrollHorizontal;
        public int mRemainingScrollVertical;
        public boolean mRunPredictiveAnimations = false;
        public boolean mRunSimpleAnimations = false;
        public boolean mStructureChanged = false;
        public int mTargetPosition = -1;
        public boolean mTrackOldChangeHolders = false;

        public final void assertLayoutStep(int i) {
            if ((this.mLayoutStep & i) == 0) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Layout state should be one of ");
                m.append(Integer.toBinaryString(i));
                m.append(" but it is ");
                m.append(Integer.toBinaryString(this.mLayoutStep));
                throw new IllegalStateException(m.toString());
            }
        }

        public final int getItemCount() {
            if (this.mInPreLayout) {
                return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
            }
            return this.mItemCount;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("State{mTargetPosition=");
            m.append(this.mTargetPosition);
            m.append(", mData=");
            m.append((Object) null);
            m.append(", mItemCount=");
            m.append(this.mItemCount);
            m.append(", mIsMeasuring=");
            m.append(this.mIsMeasuring);
            m.append(", mPreviousLayoutItemCount=");
            m.append(this.mPreviousLayoutItemCount);
            m.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            m.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
            m.append(", mStructureChanged=");
            m.append(this.mStructureChanged);
            m.append(", mInPreLayout=");
            m.append(this.mInPreLayout);
            m.append(", mRunSimpleAnimations=");
            m.append(this.mRunSimpleAnimations);
            m.append(", mRunPredictiveAnimations=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.mRunPredictiveAnimations, '}');
        }
    }

    public static class StretchEdgeEffectFactory extends EdgeEffectFactory {
    }

    public class ViewFlinger implements Runnable {
        public boolean mEatRunOnAnimationRequest = false;
        public Interpolator mInterpolator;
        public int mLastFlingX;
        public int mLastFlingY;
        public OverScroller mOverScroller;
        public boolean mReSchedulePostAnimationCallback = false;

        public final void smoothScrollBy(int i, int i2, int i3, Interpolator interpolator) {
            boolean z;
            int i4;
            if (i3 == Integer.MIN_VALUE) {
                int abs = Math.abs(i);
                int abs2 = Math.abs(i2);
                if (abs > abs2) {
                    z = true;
                } else {
                    z = false;
                }
                RecyclerView recyclerView = RecyclerView.this;
                if (z) {
                    i4 = recyclerView.getWidth();
                } else {
                    i4 = recyclerView.getHeight();
                }
                if (!z) {
                    abs = abs2;
                }
                i3 = Math.min((int) (((((float) abs) / ((float) i4)) + 1.0f) * 300.0f), 2000);
            }
            int i5 = i3;
            if (interpolator == null) {
                interpolator = RecyclerView.sQuinticInterpolator;
            }
            if (this.mInterpolator != interpolator) {
                this.mInterpolator = interpolator;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            RecyclerView.this.setScrollState(2);
            this.mOverScroller.startScroll(0, 0, i, i2, i5);
            postOnAnimation();
        }

        public ViewFlinger() {
            C03343 r0 = RecyclerView.sQuinticInterpolator;
            this.mInterpolator = r0;
            this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), r0);
        }

        public final void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            RecyclerView recyclerView = RecyclerView.this;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(recyclerView, this);
        }

        public final void run() {
            int i;
            int i2;
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            int i3;
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.mLayout == null) {
                recyclerView.removeCallbacks(this);
                this.mOverScroller.abortAnimation();
                return;
            }
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
            recyclerView.consumePendingUpdateOperations();
            OverScroller overScroller = this.mOverScroller;
            if (overScroller.computeScrollOffset()) {
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i4 = currX - this.mLastFlingX;
                int i5 = currY - this.mLastFlingY;
                this.mLastFlingX = currX;
                this.mLastFlingY = currY;
                RecyclerView recyclerView2 = RecyclerView.this;
                int[] iArr = recyclerView2.mReusableIntPair;
                iArr[0] = 0;
                iArr[1] = 0;
                if (recyclerView2.getScrollingChildHelper().dispatchNestedPreScroll(i4, i5, iArr, (int[]) null, 1)) {
                    int[] iArr2 = RecyclerView.this.mReusableIntPair;
                    i4 -= iArr2[0];
                    i5 -= iArr2[1];
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(i4, i5);
                }
                RecyclerView recyclerView3 = RecyclerView.this;
                if (recyclerView3.mAdapter != null) {
                    int[] iArr3 = recyclerView3.mReusableIntPair;
                    iArr3[0] = 0;
                    iArr3[1] = 0;
                    recyclerView3.scrollStep(i4, i5, iArr3);
                    RecyclerView recyclerView4 = RecyclerView.this;
                    int[] iArr4 = recyclerView4.mReusableIntPair;
                    i = iArr4[0];
                    i2 = iArr4[1];
                    i4 -= i;
                    i5 -= i2;
                    SmoothScroller smoothScroller = recyclerView4.mLayout.mSmoothScroller;
                    if (smoothScroller != null && !smoothScroller.mPendingInitialRun && smoothScroller.mRunning) {
                        int itemCount = recyclerView4.mState.getItemCount();
                        if (itemCount == 0) {
                            smoothScroller.stop();
                        } else if (smoothScroller.mTargetPosition >= itemCount) {
                            smoothScroller.mTargetPosition = itemCount - 1;
                            smoothScroller.onAnimation(i, i2);
                        } else {
                            smoothScroller.onAnimation(i, i2);
                        }
                    }
                } else {
                    i2 = 0;
                    i = 0;
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView recyclerView5 = RecyclerView.this;
                int[] iArr5 = recyclerView5.mReusableIntPair;
                iArr5[0] = 0;
                iArr5[1] = 0;
                NestedScrollingChildHelper scrollingChildHelper = recyclerView5.getScrollingChildHelper();
                Objects.requireNonNull(scrollingChildHelper);
                scrollingChildHelper.dispatchNestedScrollInternal(i, i2, i4, i5, (int[]) null, 1, iArr5);
                RecyclerView recyclerView6 = RecyclerView.this;
                int[] iArr6 = recyclerView6.mReusableIntPair;
                int i6 = i4 - iArr6[0];
                int i7 = i5 - iArr6[1];
                if (!(i == 0 && i2 == 0)) {
                    recyclerView6.dispatchOnScrolled(i, i2);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                if (overScroller.getCurrX() == overScroller.getFinalX()) {
                    z = true;
                } else {
                    z = false;
                }
                if (overScroller.getCurrY() == overScroller.getFinalY()) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (overScroller.isFinished() || ((z || i6 != 0) && (z2 || i7 != 0))) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                RecyclerView recyclerView7 = RecyclerView.this;
                SmoothScroller smoothScroller2 = recyclerView7.mLayout.mSmoothScroller;
                if (smoothScroller2 == null || !smoothScroller2.mPendingInitialRun) {
                    z4 = false;
                } else {
                    z4 = true;
                }
                if (z4 || !z3) {
                    postOnAnimation();
                    RecyclerView recyclerView8 = RecyclerView.this;
                    GapWorker gapWorker = recyclerView8.mGapWorker;
                    if (gapWorker != null) {
                        gapWorker.postFromTraversal(recyclerView8, i, i2);
                    }
                } else {
                    if (recyclerView7.getOverScrollMode() != 2) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        if (i6 < 0) {
                            i3 = -currVelocity;
                        } else if (i6 > 0) {
                            i3 = currVelocity;
                        } else {
                            i3 = 0;
                        }
                        if (i7 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i7 <= 0) {
                            currVelocity = 0;
                        }
                        RecyclerView recyclerView9 = RecyclerView.this;
                        if (i3 < 0) {
                            recyclerView9.ensureLeftGlow();
                            recyclerView9.mLeftGlow.onAbsorb(-i3);
                        } else if (i3 > 0) {
                            recyclerView9.ensureRightGlow();
                            recyclerView9.mRightGlow.onAbsorb(i3);
                        }
                        if (currVelocity < 0) {
                            recyclerView9.ensureTopGlow();
                            recyclerView9.mTopGlow.onAbsorb(-currVelocity);
                        } else if (currVelocity > 0) {
                            recyclerView9.ensureBottomGlow();
                            recyclerView9.mBottomGlow.onAbsorb(currVelocity);
                        } else {
                            Objects.requireNonNull(recyclerView9);
                        }
                        if (!(i3 == 0 && currVelocity == 0)) {
                            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api16Impl.postInvalidateOnAnimation(recyclerView9);
                        }
                    }
                    GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = RecyclerView.this.mPrefetchRegistry;
                    Objects.requireNonNull(layoutPrefetchRegistryImpl);
                    int[] iArr7 = layoutPrefetchRegistryImpl.mPrefetchArray;
                    if (iArr7 != null) {
                        Arrays.fill(iArr7, -1);
                    }
                    layoutPrefetchRegistryImpl.mCount = 0;
                }
            }
            SmoothScroller smoothScroller3 = RecyclerView.this.mLayout.mSmoothScroller;
            if (smoothScroller3 != null && smoothScroller3.mPendingInitialRun) {
                smoothScroller3.onAnimation(0, 0);
            }
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                RecyclerView.this.removeCallbacks(this);
                RecyclerView recyclerView10 = RecyclerView.this;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(recyclerView10, this);
                return;
            }
            RecyclerView.this.setScrollState(0);
            RecyclerView recyclerView11 = RecyclerView.this;
            Objects.requireNonNull(recyclerView11);
            recyclerView11.getScrollingChildHelper().stopNestedScroll(1);
        }
    }

    public static abstract class ViewHolder {
        public static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        public final View itemView;
        public Adapter<? extends ViewHolder> mBindingAdapter;
        public int mFlags;
        public boolean mInChangeScrap = false;
        public int mIsRecyclableCount = 0;
        public long mItemId = -1;
        public int mItemViewType = -1;
        public WeakReference<RecyclerView> mNestedRecyclerView;
        public int mOldPosition = -1;
        public RecyclerView mOwnerRecyclerView;
        public ArrayList mPayloads = null;
        public int mPendingAccessibilityState = -1;
        public int mPosition = -1;
        public int mPreLayoutPosition = -1;
        public Recycler mScrapContainer = null;
        public ViewHolder mShadowedHolder = null;
        public ViewHolder mShadowingHolder = null;
        public List<Object> mUnmodifiedPayloads = null;
        public int mWasImportantForAccessibilityBeforeHidden = 0;

        public final void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            ArrayList arrayList = this.mPayloads;
            if (arrayList != null) {
                arrayList.clear();
            }
            this.mFlags &= -1025;
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        public final void setIsRecyclable(boolean z) {
            int i;
            int i2 = this.mIsRecyclableCount;
            if (z) {
                i = i2 - 1;
            } else {
                i = i2 + 1;
            }
            this.mIsRecyclableCount = i;
            if (i < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && i == 1) {
                this.mFlags |= 16;
            } else if (z && i == 0) {
                this.mFlags &= -17;
            }
        }

        public final void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(1024);
            } else if ((1024 & this.mFlags) == 0) {
                if (this.mPayloads == null) {
                    ArrayList arrayList = new ArrayList();
                    this.mPayloads = arrayList;
                    this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
                }
                this.mPayloads.add(obj);
            }
        }

        public final void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        public final int getAbsoluteAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.getAdapterPositionInRecyclerView(this);
        }

        public final int getBindingAdapterPosition() {
            RecyclerView recyclerView;
            int adapterPositionInRecyclerView;
            if (this.mBindingAdapter == null || (recyclerView = this.mOwnerRecyclerView) == null) {
                return -1;
            }
            Objects.requireNonNull(recyclerView);
            Adapter<? extends ViewHolder> adapter = recyclerView.mAdapter;
            if (adapter == null || (adapterPositionInRecyclerView = this.mOwnerRecyclerView.getAdapterPositionInRecyclerView(this)) == -1 || this.mBindingAdapter != adapter) {
                return -1;
            }
            return adapterPositionInRecyclerView;
        }

        public final int getLayoutPosition() {
            int i = this.mPreLayoutPosition;
            if (i == -1) {
                return this.mPosition;
            }
            return i;
        }

        public final List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            ArrayList arrayList = this.mPayloads;
            if (arrayList == null || arrayList.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        public final boolean isAttachedToTransitionOverlay() {
            if (this.itemView.getParent() == null || this.itemView.getParent() == this.mOwnerRecyclerView) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public final boolean isBound() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            return false;
        }

        public final boolean isInvalid() {
            if ((this.mFlags & 4) != 0) {
                return true;
            }
            return false;
        }

        public final boolean isRecyclable() {
            if ((this.mFlags & 16) == 0) {
                View view = this.itemView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!ViewCompat.Api16Impl.hasTransientState(view)) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public final boolean isRemoved() {
            if ((this.mFlags & 8) != 0) {
                return true;
            }
            return false;
        }

        public final boolean isScrap() {
            if (this.mScrapContainer != null) {
                return true;
            }
            return false;
        }

        public final boolean isTmpDetached() {
            if ((this.mFlags & 256) != 0) {
                return true;
            }
            return false;
        }

        public final void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).mInsetsDirty = true;
            }
        }

        public final boolean shouldIgnore() {
            if ((this.mFlags & 128) != 0) {
                return true;
            }
            return false;
        }

        public final boolean wasReturnedFromScrap() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }

        public ViewHolder(View view) {
            if (view != null) {
                this.itemView = view;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        public final String toString() {
            String str;
            boolean z;
            String str2;
            if (getClass().isAnonymousClass()) {
                str = "ViewHolder";
            } else {
                str = getClass().getSimpleName();
            }
            StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m(str, "{");
            m.append(Integer.toHexString(hashCode()));
            m.append(" position=");
            m.append(this.mPosition);
            m.append(" id=");
            m.append(this.mItemId);
            m.append(", oldPos=");
            m.append(this.mOldPosition);
            m.append(", pLpos:");
            m.append(this.mPreLayoutPosition);
            StringBuilder sb = new StringBuilder(m.toString());
            if (isScrap()) {
                sb.append(" scrap ");
                if (this.mInChangeScrap) {
                    str2 = "[changeScrap]";
                } else {
                    str2 = "[attachedScrap]";
                }
                sb.append(str2);
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            boolean z2 = true;
            if ((this.mFlags & 2) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" not recyclable(");
                m2.append(this.mIsRecyclableCount);
                m2.append(")");
                sb.append(m2.toString());
            }
            if ((this.mFlags & 512) == 0 && !isInvalid()) {
                z2 = false;
            }
            if (z2) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 1
            int[] r1 = new int[r0]
            r2 = 0
            r3 = 16843830(0x1010436, float:2.369658E-38)
            r1[r2] = r3
            NESTED_SCROLLING_ATTRS = r1
            r1 = 4
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<android.content.Context> r3 = android.content.Context.class
            r1[r2] = r3
            java.lang.Class<android.util.AttributeSet> r2 = android.util.AttributeSet.class
            r1[r0] = r2
            java.lang.Class r0 = java.lang.Integer.TYPE
            r2 = 2
            r1[r2] = r0
            r2 = 3
            r1[r2] = r0
            LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = r1
            androidx.recyclerview.widget.RecyclerView$3 r0 = new androidx.recyclerview.widget.RecyclerView$3
            r0.<init>()
            sQuinticInterpolator = r0
            androidx.recyclerview.widget.RecyclerView$StretchEdgeEffectFactory r0 = new androidx.recyclerview.widget.RecyclerView$StretchEdgeEffectFactory
            r0.<init>()
            sDefaultEdgeEffectFactory = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.<clinit>():void");
    }

    public RecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(getContext(), attributeSet);
        }
        throw new IllegalStateException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, VendorAtomValue$$ExternalSyntheticOutline1.m1m("RecyclerView has no LayoutManager")));
    }

    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    public void setAdapter(Adapter adapter) {
        suppressLayout(false);
        Adapter adapter2 = this.mAdapter;
        if (adapter2 != null) {
            adapter2.mObservable.unregisterObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView();
        }
        removeAndRecycleViews();
        AdapterHelper adapterHelper = this.mAdapterHelper;
        Objects.requireNonNull(adapterHelper);
        adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPendingUpdates);
        adapterHelper.recycleUpdateOpsAndClearList(adapterHelper.mPostponedList);
        adapterHelper.mExistingUpdateTypes = 0;
        Adapter adapter3 = this.mAdapter;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mObserver);
            adapter.onAttachedToRecyclerView(this);
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.onAdapterChanged(adapter3, this.mAdapter);
        }
        Recycler recycler = this.mRecycler;
        Adapter adapter4 = this.mAdapter;
        Objects.requireNonNull(recycler);
        recycler.mAttachedScrap.clear();
        recycler.recycleAndClearCachedViews();
        if (recycler.mRecyclerPool == null) {
            recycler.mRecyclerPool = new RecycledViewPool();
        }
        RecycledViewPool recycledViewPool = recycler.mRecyclerPool;
        if (adapter3 != null) {
            recycledViewPool.mAttachCount--;
        }
        if (recycledViewPool.mAttachCount == 0) {
            for (int i = 0; i < recycledViewPool.mScrap.size(); i++) {
                recycledViewPool.mScrap.valueAt(i).mScrapHeap.clear();
            }
        }
        if (adapter4 != null) {
            recycledViewPool.mAttachCount++;
        }
        this.mState.mStructureChanged = true;
        processDataSetCompletelyChanged(false);
        requestLayout();
    }

    public void smoothScrollBy$1(int i, int i2) {
        smoothScrollBy$1(i, i2, false);
    }

    public final void stopScroll() {
        SmoothScroller smoothScroller;
        setScrollState(0);
        ViewFlinger viewFlinger = this.mViewFlinger;
        Objects.requireNonNull(viewFlinger);
        RecyclerView.this.removeCallbacks(viewFlinger);
        viewFlinger.mOverScroller.abortAnimation();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && (smoothScroller = layoutManager.mSmoothScroller) != null) {
            smoothScroller.stop();
        }
    }

    public static abstract class ItemDecoration {
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            rect.set(0, 0, 0, 0);
        }
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.recyclerViewStyle);
    }

    public static void clearNestedRecyclerViewIfNotNested(ViewHolder viewHolder) {
        WeakReference<RecyclerView> weakReference = viewHolder.mNestedRecyclerView;
        if (weakReference != null) {
            View view = weakReference.get();
            while (view != null) {
                if (view != viewHolder.itemView) {
                    ViewParent parent = view.getParent();
                    if (parent instanceof View) {
                        view = (View) parent;
                    } else {
                        view = null;
                    }
                } else {
                    return;
                }
            }
            viewHolder.mNestedRecyclerView = null;
        }
    }

    public static RecyclerView findNestedRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView findNestedRecyclerView = findNestedRecyclerView(viewGroup.getChildAt(i));
            if (findNestedRecyclerView != null) {
                return findNestedRecyclerView;
            }
        }
        return null;
    }

    public static ViewHolder getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).mViewHolder;
    }

    public final void addAnimatingView(ViewHolder viewHolder) {
        boolean z;
        View view = viewHolder.itemView;
        if (view.getParent() == this) {
            z = true;
        } else {
            z = false;
        }
        this.mRecycler.unscrapView(getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
        } else if (!z) {
            ChildHelper childHelper = this.mChildHelper;
            Objects.requireNonNull(childHelper);
            childHelper.addView(view, -1, true);
        } else {
            ChildHelper childHelper2 = this.mChildHelper;
            Objects.requireNonNull(childHelper2);
            C03365 r6 = (C03365) childHelper2.mCallback;
            Objects.requireNonNull(r6);
            int indexOfChild = RecyclerView.this.indexOfChild(view);
            if (indexOfChild >= 0) {
                childHelper2.mBucket.set(indexOfChild);
                childHelper2.hideViewInternal(view);
                return;
            }
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null || !layoutManager.onAddFocusables(this, arrayList, i, i2)) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    public final void addItemDecoration(ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        this.mItemDecorations.add(itemDecoration);
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public final void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams) || !this.mLayout.checkLayoutParams((LayoutParams) layoutParams)) {
            return false;
        }
        return true;
    }

    public final void clearOldPositions() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.mOldPosition = -1;
                childViewHolderInt.mPreLayoutPosition = -1;
            }
        }
        Recycler recycler = this.mRecycler;
        Objects.requireNonNull(recycler);
        int size = recycler.mCachedViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = recycler.mCachedViews.get(i2);
            Objects.requireNonNull(viewHolder);
            viewHolder.mOldPosition = -1;
            viewHolder.mPreLayoutPosition = -1;
        }
        int size2 = recycler.mAttachedScrap.size();
        for (int i3 = 0; i3 < size2; i3++) {
            ViewHolder viewHolder2 = recycler.mAttachedScrap.get(i3);
            Objects.requireNonNull(viewHolder2);
            viewHolder2.mOldPosition = -1;
            viewHolder2.mPreLayoutPosition = -1;
        }
        ArrayList<ViewHolder> arrayList = recycler.mChangedScrap;
        if (arrayList != null) {
            int size3 = arrayList.size();
            for (int i4 = 0; i4 < size3; i4++) {
                ViewHolder viewHolder3 = recycler.mChangedScrap.get(i4);
                Objects.requireNonNull(viewHolder3);
                viewHolder3.mOldPosition = -1;
                viewHolder3.mPreLayoutPosition = -1;
            }
        }
    }

    public final int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollExtent(this.mState);
        }
        return 0;
    }

    public final int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollOffset(this.mState);
        }
        return 0;
    }

    public final int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.mLayout.computeHorizontalScrollRange(this.mState);
        }
        return 0;
    }

    public final int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollExtent(this.mState);
        }
        return 0;
    }

    public final int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollOffset(this.mState);
        }
        return 0;
    }

    public final int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.mLayout.computeVerticalScrollRange(this.mState);
        }
        return 0;
    }

    public final void considerReleasingGlowsOnScroll(int i, int i2) {
        boolean z;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished() || i <= 0) {
            z = false;
        } else {
            this.mLeftGlow.onRelease();
            z = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mRightGlow;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i < 0) {
            this.mRightGlow.onRelease();
            z |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i2 > 0) {
            this.mTopGlow.onRelease();
            z |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i2 < 0) {
            this.mBottomGlow.onRelease();
            z |= this.mBottomGlow.isFinished();
        }
        if (z) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final void consumePendingUpdateOperations() {
        boolean z;
        boolean z2;
        boolean z3;
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            Trace.beginSection("RV FullInvalidate");
            dispatchLayout();
            Trace.endSection();
        } else if (this.mAdapterHelper.hasPendingUpdates()) {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            Objects.requireNonNull(adapterHelper);
            boolean z4 = false;
            if ((adapterHelper.mExistingUpdateTypes & 4) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                AdapterHelper adapterHelper2 = this.mAdapterHelper;
                Objects.requireNonNull(adapterHelper2);
                if ((adapterHelper2.mExistingUpdateTypes & 11) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (!z2) {
                    Trace.beginSection("RV PartialInvalidate");
                    startInterceptRequestLayout();
                    onEnterLayoutOrScroll();
                    this.mAdapterHelper.preProcess();
                    if (!this.mLayoutWasDefered) {
                        int childCount = this.mChildHelper.getChildCount();
                        int i = 0;
                        while (true) {
                            if (i >= childCount) {
                                break;
                            }
                            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                                if ((childViewHolderInt.mFlags & 2) != 0) {
                                    z3 = true;
                                } else {
                                    z3 = false;
                                }
                                if (z3) {
                                    z4 = true;
                                    break;
                                }
                            }
                            i++;
                        }
                        if (z4) {
                            dispatchLayout();
                        } else {
                            this.mAdapterHelper.consumePostponedUpdates();
                        }
                    }
                    stopInterceptRequestLayout(true);
                    onExitLayoutOrScroll(true);
                    Trace.endSection();
                    return;
                }
            }
            if (this.mAdapterHelper.hasPendingUpdates()) {
                Trace.beginSection("RV FullInvalidate");
                dispatchLayout();
                Trace.endSection();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:153:0x037b, code lost:
        if (r15.mChildHelper.isHidden(getFocusedChild()) == false) goto L_0x044b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0411, code lost:
        r5 = r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x03e6  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x03e9  */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x0432  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dispatchLayout() {
        /*
            r15 = this;
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r15.mAdapter
            java.lang.String r1 = "RecyclerView"
            if (r0 != 0) goto L_0x000c
            java.lang.String r15 = "No adapter attached; skipping layout"
            android.util.Log.w(r1, r15)
            return
        L_0x000c:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            if (r0 != 0) goto L_0x0016
            java.lang.String r15 = "No layout manager attached; skipping layout"
            android.util.Log.e(r1, r15)
            return
        L_0x0016:
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            r2 = 0
            r0.mIsMeasuring = r2
            boolean r0 = r15.mLastAutoMeasureSkippedDueToExact
            r3 = 1
            if (r0 == 0) goto L_0x0032
            int r0 = r15.mLastAutoMeasureNonExactMeasuredWidth
            int r4 = r15.getWidth()
            if (r0 != r4) goto L_0x0030
            int r0 = r15.mLastAutoMeasureNonExactMeasuredHeight
            int r4 = r15.getHeight()
            if (r0 == r4) goto L_0x0032
        L_0x0030:
            r0 = r3
            goto L_0x0033
        L_0x0032:
            r0 = r2
        L_0x0033:
            r15.mLastAutoMeasureNonExactMeasuredWidth = r2
            r15.mLastAutoMeasureNonExactMeasuredHeight = r2
            r15.mLastAutoMeasureSkippedDueToExact = r2
            androidx.recyclerview.widget.RecyclerView$State r4 = r15.mState
            int r4 = r4.mLayoutStep
            if (r4 != r3) goto L_0x004b
            r15.dispatchLayoutStep1()
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            r0.setExactMeasureSpecsFrom(r15)
            r15.dispatchLayoutStep2()
            goto L_0x0090
        L_0x004b:
            androidx.recyclerview.widget.AdapterHelper r4 = r15.mAdapterHelper
            java.util.Objects.requireNonNull(r4)
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r5 = r4.mPostponedList
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0062
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r4 = r4.mPendingUpdates
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x0062
            r4 = r3
            goto L_0x0063
        L_0x0062:
            r4 = r2
        L_0x0063:
            if (r4 != 0) goto L_0x0088
            if (r0 != 0) goto L_0x0088
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mWidth
            int r4 = r15.getWidth()
            if (r0 != r4) goto L_0x0088
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mHeight
            int r4 = r15.getHeight()
            if (r0 == r4) goto L_0x0082
            goto L_0x0088
        L_0x0082:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            r0.setExactMeasureSpecsFrom(r15)
            goto L_0x0090
        L_0x0088:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            r0.setExactMeasureSpecsFrom(r15)
            r15.dispatchLayoutStep2()
        L_0x0090:
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            r4 = 4
            r0.assertLayoutStep(r4)
            r15.startInterceptRequestLayout()
            r15.onEnterLayoutOrScroll()
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            r0.mLayoutStep = r3
            boolean r0 = r0.mRunSimpleAnimations
            r5 = 0
            if (r0 == 0) goto L_0x02dc
            androidx.recyclerview.widget.ChildHelper r0 = r15.mChildHelper
            int r0 = r0.getChildCount()
            int r0 = r0 - r3
        L_0x00ac:
            if (r0 < 0) goto L_0x0200
            androidx.recyclerview.widget.ChildHelper r6 = r15.mChildHelper
            android.view.View r6 = r6.getChildAt(r0)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = getChildViewHolderInt(r6)
            boolean r7 = r6.shouldIgnore()
            if (r7 == 0) goto L_0x00c0
            goto L_0x01fb
        L_0x00c0:
            long r7 = r15.getChangedHolderKey(r6)
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r9 = r15.mItemAnimator
            java.util.Objects.requireNonNull(r9)
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r9 = new androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo
            r9.<init>()
            r9.setFrom(r6)
            androidx.recyclerview.widget.ViewInfoStore r10 = r15.mViewInfoStore
            java.util.Objects.requireNonNull(r10)
            androidx.collection.LongSparseArray<androidx.recyclerview.widget.RecyclerView$ViewHolder> r10 = r10.mOldChangedHolders
            java.util.Objects.requireNonNull(r10)
            java.lang.Object r10 = r10.get(r7, r5)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r10 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r10
            if (r10 == 0) goto L_0x01f6
            boolean r11 = r10.shouldIgnore()
            if (r11 != 0) goto L_0x01f6
            androidx.recyclerview.widget.ViewInfoStore r11 = r15.mViewInfoStore
            java.util.Objects.requireNonNull(r11)
            androidx.collection.SimpleArrayMap<androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.ViewInfoStore$InfoRecord> r11 = r11.mLayoutHolderMap
            java.util.Objects.requireNonNull(r11)
            java.lang.Object r11 = r11.getOrDefault(r10, r5)
            androidx.recyclerview.widget.ViewInfoStore$InfoRecord r11 = (androidx.recyclerview.widget.ViewInfoStore.InfoRecord) r11
            if (r11 == 0) goto L_0x0102
            int r11 = r11.flags
            r11 = r11 & r3
            if (r11 == 0) goto L_0x0102
            r11 = r3
            goto L_0x0103
        L_0x0102:
            r11 = r2
        L_0x0103:
            androidx.recyclerview.widget.ViewInfoStore r12 = r15.mViewInfoStore
            java.util.Objects.requireNonNull(r12)
            androidx.collection.SimpleArrayMap<androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.ViewInfoStore$InfoRecord> r12 = r12.mLayoutHolderMap
            java.util.Objects.requireNonNull(r12)
            java.lang.Object r12 = r12.getOrDefault(r6, r5)
            androidx.recyclerview.widget.ViewInfoStore$InfoRecord r12 = (androidx.recyclerview.widget.ViewInfoStore.InfoRecord) r12
            if (r12 == 0) goto L_0x011c
            int r12 = r12.flags
            r12 = r12 & r3
            if (r12 == 0) goto L_0x011c
            r12 = r3
            goto L_0x011d
        L_0x011c:
            r12 = r2
        L_0x011d:
            if (r11 == 0) goto L_0x0128
            if (r10 != r6) goto L_0x0128
            androidx.recyclerview.widget.ViewInfoStore r4 = r15.mViewInfoStore
            r4.addToPostLayout(r6, r9)
            goto L_0x01fb
        L_0x0128:
            androidx.recyclerview.widget.ViewInfoStore r13 = r15.mViewInfoStore
            java.util.Objects.requireNonNull(r13)
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r4 = r13.popFromLayoutStep(r10, r4)
            androidx.recyclerview.widget.ViewInfoStore r13 = r15.mViewInfoStore
            r13.addToPostLayout(r6, r9)
            androidx.recyclerview.widget.ViewInfoStore r9 = r15.mViewInfoStore
            java.util.Objects.requireNonNull(r9)
            r13 = 8
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r9 = r9.popFromLayoutStep(r6, r13)
            if (r4 != 0) goto L_0x01cc
            androidx.recyclerview.widget.ChildHelper r4 = r15.mChildHelper
            int r4 = r4.getChildCount()
            r9 = r2
        L_0x014a:
            if (r9 >= r4) goto L_0x01a8
            androidx.recyclerview.widget.ChildHelper r11 = r15.mChildHelper
            android.view.View r11 = r11.getChildAt(r9)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r11 = getChildViewHolderInt(r11)
            if (r11 != r6) goto L_0x0159
            goto L_0x01a5
        L_0x0159:
            long r12 = r15.getChangedHolderKey(r11)
            int r12 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r12 != 0) goto L_0x01a5
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r15.mAdapter
            java.lang.String r1 = " \n View Holder 2:"
            if (r0 == 0) goto L_0x0188
            boolean r0 = r0.mHasStableIds
            if (r0 == 0) goto L_0x0188
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:"
            r2.append(r3)
            r2.append(r11)
            r2.append(r1)
            r2.append(r6)
            java.lang.String r15 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r15, r2)
            r0.<init>(r15)
            throw r0
        L_0x0188:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:"
            r2.append(r3)
            r2.append(r11)
            r2.append(r1)
            r2.append(r6)
            java.lang.String r15 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r15, r2)
            r0.<init>(r15)
            throw r0
        L_0x01a5:
            int r9 = r9 + 1
            goto L_0x014a
        L_0x01a8:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "Problem while matching changed view holders with the newones. The pre-layout information for the change holder "
            r4.append(r7)
            r4.append(r10)
            java.lang.String r7 = " cannot be found but it is necessary for "
            r4.append(r7)
            r4.append(r6)
            java.lang.String r6 = r15.exceptionLabel()
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            android.util.Log.e(r1, r4)
            goto L_0x01fb
        L_0x01cc:
            r10.setIsRecyclable(r2)
            if (r11 == 0) goto L_0x01d4
            r15.addAnimatingView(r10)
        L_0x01d4:
            if (r10 == r6) goto L_0x01ea
            if (r12 == 0) goto L_0x01db
            r15.addAnimatingView(r6)
        L_0x01db:
            r10.mShadowedHolder = r6
            r15.addAnimatingView(r10)
            androidx.recyclerview.widget.RecyclerView$Recycler r7 = r15.mRecycler
            r7.unscrapView(r10)
            r6.setIsRecyclable(r2)
            r6.mShadowingHolder = r10
        L_0x01ea:
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r7 = r15.mItemAnimator
            boolean r4 = r7.animateChange(r10, r6, r4, r9)
            if (r4 == 0) goto L_0x01fb
            r15.postAnimationRunner()
            goto L_0x01fb
        L_0x01f6:
            androidx.recyclerview.widget.ViewInfoStore r4 = r15.mViewInfoStore
            r4.addToPostLayout(r6, r9)
        L_0x01fb:
            int r0 = r0 + -1
            r4 = 4
            goto L_0x00ac
        L_0x0200:
            androidx.recyclerview.widget.ViewInfoStore r0 = r15.mViewInfoStore
            androidx.recyclerview.widget.RecyclerView$4 r1 = r15.mViewInfoProcessCallback
            java.util.Objects.requireNonNull(r0)
            androidx.collection.SimpleArrayMap<androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.ViewInfoStore$InfoRecord> r4 = r0.mLayoutHolderMap
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mSize
        L_0x020e:
            int r4 = r4 + -1
            if (r4 < 0) goto L_0x02dc
            androidx.collection.SimpleArrayMap<androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.ViewInfoStore$InfoRecord> r6 = r0.mLayoutHolderMap
            java.lang.Object r6 = r6.keyAt(r4)
            r8 = r6
            androidx.recyclerview.widget.RecyclerView$ViewHolder r8 = (androidx.recyclerview.widget.RecyclerView.ViewHolder) r8
            androidx.collection.SimpleArrayMap<androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.ViewInfoStore$InfoRecord> r6 = r0.mLayoutHolderMap
            java.lang.Object r6 = r6.removeAt(r4)
            androidx.recyclerview.widget.ViewInfoStore$InfoRecord r6 = (androidx.recyclerview.widget.ViewInfoStore.InfoRecord) r6
            int r7 = r6.flags
            r9 = r7 & 3
            r10 = 3
            if (r9 != r10) goto L_0x023a
            java.util.Objects.requireNonNull(r1)
            androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
            androidx.recyclerview.widget.RecyclerView$LayoutManager r9 = r7.mLayout
            android.view.View r8 = r8.itemView
            androidx.recyclerview.widget.RecyclerView$Recycler r7 = r7.mRecycler
            r9.removeAndRecycleView(r8, r7)
            goto L_0x02cf
        L_0x023a:
            r9 = r7 & 1
            if (r9 == 0) goto L_0x0259
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r7 = r6.preInfo
            if (r7 != 0) goto L_0x0252
            java.util.Objects.requireNonNull(r1)
            androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
            androidx.recyclerview.widget.RecyclerView$LayoutManager r9 = r7.mLayout
            android.view.View r8 = r8.itemView
            androidx.recyclerview.widget.RecyclerView$Recycler r7 = r7.mRecycler
            r9.removeAndRecycleView(r8, r7)
            goto L_0x02cf
        L_0x0252:
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r9 = r6.postInfo
            r1.processDisappeared(r8, r7, r9)
            goto L_0x02cf
        L_0x0259:
            r9 = r7 & 14
            r10 = 14
            if (r9 != r10) goto L_0x0268
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r7 = r6.preInfo
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r9 = r6.postInfo
            r1.processAppeared(r8, r7, r9)
            goto L_0x02cf
        L_0x0268:
            r9 = r7 & 12
            r10 = 12
            if (r9 != r10) goto L_0x02ba
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r7 = r6.preInfo
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r9 = r6.postInfo
            java.util.Objects.requireNonNull(r1)
            r8.setIsRecyclable(r2)
            androidx.recyclerview.widget.RecyclerView r10 = androidx.recyclerview.widget.RecyclerView.this
            boolean r11 = r10.mDataSetHasChangedAfterLayout
            if (r11 == 0) goto L_0x028c
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r10 = r10.mItemAnimator
            boolean r7 = r10.animateChange(r8, r8, r7, r9)
            if (r7 == 0) goto L_0x02cf
            androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
            r7.postAnimationRunner()
            goto L_0x02cf
        L_0x028c:
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r10 = r10.mItemAnimator
            androidx.recyclerview.widget.SimpleItemAnimator r10 = (androidx.recyclerview.widget.SimpleItemAnimator) r10
            java.util.Objects.requireNonNull(r10)
            int r11 = r7.left
            int r12 = r9.left
            if (r11 != r12) goto L_0x02a5
            int r13 = r7.top
            int r14 = r9.top
            if (r13 == r14) goto L_0x02a0
            goto L_0x02a5
        L_0x02a0:
            r10.dispatchAnimationFinished(r8)
            r7 = r2
            goto L_0x02b2
        L_0x02a5:
            int r13 = r7.top
            int r14 = r9.top
            r7 = r10
            r9 = r11
            r10 = r13
            r11 = r12
            r12 = r14
            boolean r7 = r7.animateMove(r8, r9, r10, r11, r12)
        L_0x02b2:
            if (r7 == 0) goto L_0x02cf
            androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
            r7.postAnimationRunner()
            goto L_0x02cf
        L_0x02ba:
            r9 = r7 & 4
            if (r9 == 0) goto L_0x02c4
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r7 = r6.preInfo
            r1.processDisappeared(r8, r7, r5)
            goto L_0x02cf
        L_0x02c4:
            r7 = r7 & 8
            if (r7 == 0) goto L_0x02cf
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r7 = r6.preInfo
            androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo r9 = r6.postInfo
            r1.processAppeared(r8, r7, r9)
        L_0x02cf:
            r6.flags = r2
            r6.preInfo = r5
            r6.postInfo = r5
            androidx.core.util.Pools$SimplePool r7 = androidx.recyclerview.widget.ViewInfoStore.InfoRecord.sPool
            r7.release(r6)
            goto L_0x020e
        L_0x02dc:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            androidx.recyclerview.widget.RecyclerView$Recycler r1 = r15.mRecycler
            r0.removeAndRecycleScrapInt(r1)
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            int r1 = r0.mItemCount
            r0.mPreviousLayoutItemCount = r1
            r15.mDataSetHasChangedAfterLayout = r2
            r15.mDispatchItemsChangedEvent = r2
            r0.mRunSimpleAnimations = r2
            r0.mRunPredictiveAnimations = r2
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            r0.mRequestedSimpleAnimations = r2
            androidx.recyclerview.widget.RecyclerView$Recycler r0 = r15.mRecycler
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ViewHolder> r0 = r0.mChangedScrap
            if (r0 == 0) goto L_0x02fe
            r0.clear()
        L_0x02fe:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            boolean r1 = r0.mPrefetchMaxObservedInInitialPrefetch
            if (r1 == 0) goto L_0x030d
            r0.mPrefetchMaxCountObserved = r2
            r0.mPrefetchMaxObservedInInitialPrefetch = r2
            androidx.recyclerview.widget.RecyclerView$Recycler r0 = r15.mRecycler
            r0.updateViewCacheSize()
        L_0x030d:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r15.mLayout
            androidx.recyclerview.widget.RecyclerView$State r1 = r15.mState
            r0.onLayoutCompleted(r1)
            r15.onExitLayoutOrScroll(r3)
            r15.stopInterceptRequestLayout(r2)
            androidx.recyclerview.widget.ViewInfoStore r0 = r15.mViewInfoStore
            java.util.Objects.requireNonNull(r0)
            androidx.collection.SimpleArrayMap<androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.ViewInfoStore$InfoRecord> r1 = r0.mLayoutHolderMap
            r1.clear()
            androidx.collection.LongSparseArray<androidx.recyclerview.widget.RecyclerView$ViewHolder> r0 = r0.mOldChangedHolders
            r0.clear()
            int[] r0 = r15.mMinMaxLayoutPositions
            r1 = r0[r2]
            r4 = r0[r3]
            r15.findMinMaxChildLayoutPositions(r0)
            int[] r0 = r15.mMinMaxLayoutPositions
            r6 = r0[r2]
            if (r6 != r1) goto L_0x033e
            r0 = r0[r3]
            if (r0 == r4) goto L_0x033d
            goto L_0x033e
        L_0x033d:
            r3 = r2
        L_0x033e:
            if (r3 == 0) goto L_0x0343
            r15.dispatchOnScrolled(r2, r2)
        L_0x0343:
            boolean r0 = r15.mPreserveFocusAfterLayout
            r3 = -1
            if (r0 == 0) goto L_0x044b
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r15.mAdapter
            if (r0 == 0) goto L_0x044b
            boolean r0 = r15.hasFocus()
            if (r0 == 0) goto L_0x044b
            int r0 = r15.getDescendantFocusability()
            r1 = 393216(0x60000, float:5.51013E-40)
            if (r0 == r1) goto L_0x044b
            int r0 = r15.getDescendantFocusability()
            r1 = 131072(0x20000, float:1.83671E-40)
            if (r0 != r1) goto L_0x036b
            boolean r0 = r15.isFocused()
            if (r0 == 0) goto L_0x036b
            goto L_0x044b
        L_0x036b:
            boolean r0 = r15.isFocused()
            if (r0 != 0) goto L_0x037f
            android.view.View r0 = r15.getFocusedChild()
            androidx.recyclerview.widget.ChildHelper r1 = r15.mChildHelper
            boolean r0 = r1.isHidden(r0)
            if (r0 != 0) goto L_0x037f
            goto L_0x044b
        L_0x037f:
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            long r0 = r0.mFocusedItemId
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 == 0) goto L_0x03d0
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r15.mAdapter
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mHasStableIds
            if (r0 == 0) goto L_0x03d0
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            long r0 = r0.mFocusedItemId
            androidx.recyclerview.widget.RecyclerView$Adapter r6 = r15.mAdapter
            if (r6 == 0) goto L_0x03cd
            boolean r6 = r6.mHasStableIds
            if (r6 != 0) goto L_0x039d
            goto L_0x03cd
        L_0x039d:
            androidx.recyclerview.widget.ChildHelper r6 = r15.mChildHelper
            int r6 = r6.getUnfilteredChildCount()
            r7 = r2
            r8 = r5
        L_0x03a5:
            if (r7 >= r6) goto L_0x03ce
            androidx.recyclerview.widget.ChildHelper r9 = r15.mChildHelper
            android.view.View r9 = r9.getUnfilteredChildAt(r7)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r9 = getChildViewHolderInt(r9)
            if (r9 == 0) goto L_0x03ca
            boolean r10 = r9.isRemoved()
            if (r10 != 0) goto L_0x03ca
            long r10 = r9.mItemId
            int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r10 != 0) goto L_0x03ca
            androidx.recyclerview.widget.ChildHelper r8 = r15.mChildHelper
            android.view.View r10 = r9.itemView
            boolean r8 = r8.isHidden(r10)
            if (r8 == 0) goto L_0x03d1
            r8 = r9
        L_0x03ca:
            int r7 = r7 + 1
            goto L_0x03a5
        L_0x03cd:
            r8 = r5
        L_0x03ce:
            r9 = r8
            goto L_0x03d1
        L_0x03d0:
            r9 = r5
        L_0x03d1:
            if (r9 == 0) goto L_0x03e9
            androidx.recyclerview.widget.ChildHelper r0 = r15.mChildHelper
            android.view.View r1 = r9.itemView
            boolean r0 = r0.isHidden(r1)
            if (r0 != 0) goto L_0x03e9
            android.view.View r0 = r9.itemView
            boolean r0 = r0.hasFocusable()
            if (r0 != 0) goto L_0x03e6
            goto L_0x03e9
        L_0x03e6:
            android.view.View r5 = r9.itemView
            goto L_0x0430
        L_0x03e9:
            androidx.recyclerview.widget.ChildHelper r0 = r15.mChildHelper
            int r0 = r0.getChildCount()
            if (r0 <= 0) goto L_0x0430
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            int r1 = r0.mFocusedItemPosition
            r6 = -1
            if (r1 == r6) goto L_0x03f9
            r2 = r1
        L_0x03f9:
            int r0 = r0.getItemCount()
            r1 = r2
        L_0x03fe:
            if (r1 >= r0) goto L_0x0416
            androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = r15.findViewHolderForAdapterPosition(r1)
            if (r6 != 0) goto L_0x0407
            goto L_0x0416
        L_0x0407:
            android.view.View r7 = r6.itemView
            boolean r7 = r7.hasFocusable()
            if (r7 == 0) goto L_0x0413
            android.view.View r0 = r6.itemView
        L_0x0411:
            r5 = r0
            goto L_0x0430
        L_0x0413:
            int r1 = r1 + 1
            goto L_0x03fe
        L_0x0416:
            int r0 = java.lang.Math.min(r0, r2)
        L_0x041a:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x0430
            androidx.recyclerview.widget.RecyclerView$ViewHolder r1 = r15.findViewHolderForAdapterPosition(r0)
            if (r1 != 0) goto L_0x0425
            goto L_0x0430
        L_0x0425:
            android.view.View r2 = r1.itemView
            boolean r2 = r2.hasFocusable()
            if (r2 == 0) goto L_0x041a
            android.view.View r0 = r1.itemView
            goto L_0x0411
        L_0x0430:
            if (r5 == 0) goto L_0x044b
            androidx.recyclerview.widget.RecyclerView$State r0 = r15.mState
            int r0 = r0.mFocusedSubChildId
            long r1 = (long) r0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x0448
            android.view.View r0 = r5.findViewById(r0)
            if (r0 == 0) goto L_0x0448
            boolean r1 = r0.isFocusable()
            if (r1 == 0) goto L_0x0448
            r5 = r0
        L_0x0448:
            r5.requestFocus()
        L_0x044b:
            androidx.recyclerview.widget.RecyclerView$State r15 = r15.mState
            r15.mFocusedItemId = r3
            r0 = -1
            r15.mFocusedItemPosition = r0
            r15.mFocusedSubChildId = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.dispatchLayout():void");
    }

    public final void dispatchLayoutStep1() {
        View view;
        ViewHolder viewHolder;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i;
        View findContainingItemView;
        this.mState.assertLayoutStep(1);
        fillRemainingScrollValues(this.mState);
        this.mState.mIsMeasuring = false;
        startInterceptRequestLayout();
        ViewInfoStore viewInfoStore = this.mViewInfoStore;
        Objects.requireNonNull(viewInfoStore);
        viewInfoStore.mLayoutHolderMap.clear();
        viewInfoStore.mOldChangedHolders.clear();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        if (!this.mPreserveFocusAfterLayout || !hasFocus() || this.mAdapter == null) {
            view = null;
        } else {
            view = getFocusedChild();
        }
        if (view == null || (findContainingItemView = findContainingItemView(view)) == null) {
            viewHolder = null;
        } else {
            viewHolder = getChildViewHolder(findContainingItemView);
        }
        long j = -1;
        if (viewHolder == null) {
            State state = this.mState;
            state.mFocusedItemId = -1;
            state.mFocusedItemPosition = -1;
            state.mFocusedSubChildId = -1;
        } else {
            State state2 = this.mState;
            Adapter adapter = this.mAdapter;
            Objects.requireNonNull(adapter);
            if (adapter.mHasStableIds) {
                j = viewHolder.mItemId;
            }
            state2.mFocusedItemId = j;
            State state3 = this.mState;
            if (this.mDataSetHasChangedAfterLayout) {
                i = -1;
            } else if (viewHolder.isRemoved()) {
                i = viewHolder.mOldPosition;
            } else {
                i = viewHolder.getAbsoluteAdapterPosition();
            }
            state3.mFocusedItemPosition = i;
            State state4 = this.mState;
            View view2 = viewHolder.itemView;
            int id = view2.getId();
            while (!view2.isFocused() && (view2 instanceof ViewGroup) && view2.hasFocus()) {
                view2 = ((ViewGroup) view2).getFocusedChild();
                if (view2.getId() != -1) {
                    id = view2.getId();
                }
            }
            state4.mFocusedSubChildId = id;
        }
        State state5 = this.mState;
        if (!state5.mRunSimpleAnimations || !this.mItemsChanged) {
            z = false;
        } else {
            z = true;
        }
        state5.mTrackOldChangeHolders = z;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        state5.mInPreLayout = state5.mRunPredictiveAnimations;
        state5.mItemCount = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            int childCount = this.mChildHelper.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i2));
                if (!childViewHolderInt.shouldIgnore()) {
                    if (childViewHolderInt.isInvalid()) {
                        Adapter adapter2 = this.mAdapter;
                        Objects.requireNonNull(adapter2);
                        if (!adapter2.mHasStableIds) {
                        }
                    }
                    ItemAnimator itemAnimator = this.mItemAnimator;
                    ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt);
                    childViewHolderInt.getUnmodifiedPayloads();
                    Objects.requireNonNull(itemAnimator);
                    ItemAnimator.ItemHolderInfo itemHolderInfo = new ItemAnimator.ItemHolderInfo();
                    itemHolderInfo.setFrom(childViewHolderInt);
                    this.mViewInfoStore.addToPreLayout(childViewHolderInt, itemHolderInfo);
                    if (this.mState.mTrackOldChangeHolders) {
                        if ((childViewHolderInt.mFlags & 2) != 0) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        if (z4 && !childViewHolderInt.isRemoved() && !childViewHolderInt.shouldIgnore() && !childViewHolderInt.isInvalid()) {
                            long changedHolderKey = getChangedHolderKey(childViewHolderInt);
                            ViewInfoStore viewInfoStore2 = this.mViewInfoStore;
                            Objects.requireNonNull(viewInfoStore2);
                            viewInfoStore2.mOldChangedHolders.put(changedHolderKey, childViewHolderInt);
                        }
                    }
                }
            }
        }
        if (this.mState.mRunPredictiveAnimations) {
            int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
            for (int i3 = 0; i3 < unfilteredChildCount; i3++) {
                ViewHolder childViewHolderInt2 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i3));
                if (!childViewHolderInt2.shouldIgnore() && childViewHolderInt2.mOldPosition == -1) {
                    childViewHolderInt2.mOldPosition = childViewHolderInt2.mPosition;
                }
            }
            State state6 = this.mState;
            boolean z5 = state6.mStructureChanged;
            state6.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, state6);
            this.mState.mStructureChanged = z5;
            for (int i4 = 0; i4 < this.mChildHelper.getChildCount(); i4++) {
                ViewHolder childViewHolderInt3 = getChildViewHolderInt(this.mChildHelper.getChildAt(i4));
                if (!childViewHolderInt3.shouldIgnore()) {
                    ViewInfoStore viewInfoStore3 = this.mViewInfoStore;
                    Objects.requireNonNull(viewInfoStore3);
                    SimpleArrayMap<ViewHolder, ViewInfoStore.InfoRecord> simpleArrayMap = viewInfoStore3.mLayoutHolderMap;
                    Objects.requireNonNull(simpleArrayMap);
                    ViewInfoStore.InfoRecord orDefault = simpleArrayMap.getOrDefault(childViewHolderInt3, null);
                    if (orDefault == null || (orDefault.flags & 4) == 0) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (!z2) {
                        ItemAnimator.buildAdapterChangeFlagsForAnimations(childViewHolderInt3);
                        if ((childViewHolderInt3.mFlags & 8192) != 0) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        ItemAnimator itemAnimator2 = this.mItemAnimator;
                        childViewHolderInt3.getUnmodifiedPayloads();
                        Objects.requireNonNull(itemAnimator2);
                        ItemAnimator.ItemHolderInfo itemHolderInfo2 = new ItemAnimator.ItemHolderInfo();
                        itemHolderInfo2.setFrom(childViewHolderInt3);
                        if (z3) {
                            recordAnimationInfoIfBouncedHiddenView(childViewHolderInt3, itemHolderInfo2);
                        } else {
                            ViewInfoStore viewInfoStore4 = this.mViewInfoStore;
                            Objects.requireNonNull(viewInfoStore4);
                            SimpleArrayMap<ViewHolder, ViewInfoStore.InfoRecord> simpleArrayMap2 = viewInfoStore4.mLayoutHolderMap;
                            Objects.requireNonNull(simpleArrayMap2);
                            ViewInfoStore.InfoRecord orDefault2 = simpleArrayMap2.getOrDefault(childViewHolderInt3, null);
                            if (orDefault2 == null) {
                                orDefault2 = ViewInfoStore.InfoRecord.obtain();
                                viewInfoStore4.mLayoutHolderMap.put(childViewHolderInt3, orDefault2);
                            }
                            orDefault2.flags |= 2;
                            orDefault2.preInfo = itemHolderInfo2;
                        }
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    public final void dispatchOnScrolled(int i, int i2) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i, scrollY - i2);
        ArrayList arrayList = this.mScrollListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                ((OnScrollListener) this.mScrollListeners.get(size)).onScrolled(this, i, i2);
            }
        }
        this.mDispatchScrollCounter--;
    }

    public final void ensureBottomGlow() {
        if (this.mBottomGlow == null) {
            Objects.requireNonNull(this.mEdgeEffectFactory);
            EdgeEffect edgeEffect = new EdgeEffect(getContext());
            this.mBottomGlow = edgeEffect;
            if (this.mClipToPadding) {
                edgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                edgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    public final void ensureLeftGlow() {
        if (this.mLeftGlow == null) {
            Objects.requireNonNull(this.mEdgeEffectFactory);
            EdgeEffect edgeEffect = new EdgeEffect(getContext());
            this.mLeftGlow = edgeEffect;
            if (this.mClipToPadding) {
                edgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                edgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    public final void ensureRightGlow() {
        if (this.mRightGlow == null) {
            Objects.requireNonNull(this.mEdgeEffectFactory);
            EdgeEffect edgeEffect = new EdgeEffect(getContext());
            this.mRightGlow = edgeEffect;
            if (this.mClipToPadding) {
                edgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                edgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    public final void ensureTopGlow() {
        if (this.mTopGlow == null) {
            Objects.requireNonNull(this.mEdgeEffectFactory);
            EdgeEffect edgeEffect = new EdgeEffect(getContext());
            this.mTopGlow = edgeEffect;
            if (this.mClipToPadding) {
                edgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                edgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    public final String exceptionLabel() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" ");
        m.append(super.toString());
        m.append(", adapter:");
        m.append(this.mAdapter);
        m.append(", layout:");
        m.append(this.mLayout);
        m.append(", context:");
        m.append(getContext());
        return m.toString();
    }

    public final void fillRemainingScrollValues(State state) {
        if (this.mScrollState == 2) {
            OverScroller overScroller = this.mViewFlinger.mOverScroller;
            state.mRemainingScrollHorizontal = overScroller.getFinalX() - overScroller.getCurrX();
            state.mRemainingScrollVertical = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        state.mRemainingScrollHorizontal = 0;
        state.mRemainingScrollVertical = 0;
    }

    public final void findMinMaxChildLayoutPositions(int[] iArr) {
        int childCount = this.mChildHelper.getChildCount();
        if (childCount == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < childCount; i3++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getChildAt(i3));
            if (!childViewHolderInt.shouldIgnore()) {
                int layoutPosition = childViewHolderInt.getLayoutPosition();
                if (layoutPosition < i) {
                    i = layoutPosition;
                }
                if (layoutPosition > i2) {
                    i2 = layoutPosition;
                }
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    public final ViewHolder findViewHolderForAdapterPosition(int i) {
        ViewHolder viewHolder = null;
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && getAdapterPositionInRecyclerView(childViewHolderInt) == i) {
                if (!this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    return childViewHolderInt;
                }
                viewHolder = childViewHolderInt;
            }
        }
        return viewHolder;
    }

    public final ViewHolder findViewHolderForPosition(int i, boolean z) {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        ViewHolder viewHolder = null;
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved()) {
                if (z) {
                    if (childViewHolderInt.mPosition != i) {
                        continue;
                    }
                } else if (childViewHolderInt.getLayoutPosition() != i) {
                    continue;
                }
                if (!this.mChildHelper.isHidden(childViewHolderInt.itemView)) {
                    return childViewHolderInt;
                }
                viewHolder = childViewHolderInt;
            }
        }
        return viewHolder;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:114:0x016a, code lost:
        if (r3 > 0) goto L_0x019e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0184, code lost:
        if (r6 > 0) goto L_0x019e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0187, code lost:
        if (r3 < 0) goto L_0x019e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x018a, code lost:
        if (r6 < 0) goto L_0x019e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0192, code lost:
        if ((r6 * r2) <= 0) goto L_0x019d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x019a, code lost:
        if ((r6 * r2) >= 0) goto L_0x019d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:132:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0073  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View focusSearch(android.view.View r14, int r15) {
        /*
            r13 = this;
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r13.mLayout
            android.view.View r0 = r0.onInterceptFocusSearch(r14, r15)
            if (r0 == 0) goto L_0x0009
            return r0
        L_0x0009:
            androidx.recyclerview.widget.RecyclerView$Adapter r0 = r13.mAdapter
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x001f
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r13.mLayout
            if (r0 == 0) goto L_0x001f
            boolean r0 = r13.isComputingLayout()
            if (r0 != 0) goto L_0x001f
            boolean r0 = r13.mLayoutSuppressed
            if (r0 != 0) goto L_0x001f
            r0 = r1
            goto L_0x0020
        L_0x001f:
            r0 = r2
        L_0x0020:
            android.view.FocusFinder r3 = android.view.FocusFinder.getInstance()
            r4 = 33
            r5 = 17
            r6 = 0
            r7 = 2
            if (r0 == 0) goto L_0x0091
            if (r15 == r7) goto L_0x0030
            if (r15 != r1) goto L_0x0091
        L_0x0030:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r13.mLayout
            boolean r0 = r0.canScrollVertically()
            if (r0 == 0) goto L_0x0046
            if (r15 != r7) goto L_0x003d
            r0 = 130(0x82, float:1.82E-43)
            goto L_0x003e
        L_0x003d:
            r0 = r4
        L_0x003e:
            android.view.View r0 = r3.findNextFocus(r13, r14, r0)
            if (r0 != 0) goto L_0x0046
            r0 = r1
            goto L_0x0047
        L_0x0046:
            r0 = r2
        L_0x0047:
            if (r0 != 0) goto L_0x0071
            androidx.recyclerview.widget.RecyclerView$LayoutManager r8 = r13.mLayout
            boolean r8 = r8.canScrollHorizontally()
            if (r8 == 0) goto L_0x0071
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r13.mLayout
            int r0 = r0.getLayoutDirection()
            if (r0 != r1) goto L_0x005b
            r0 = r1
            goto L_0x005c
        L_0x005b:
            r0 = r2
        L_0x005c:
            if (r15 != r7) goto L_0x0060
            r8 = r1
            goto L_0x0061
        L_0x0060:
            r8 = r2
        L_0x0061:
            r0 = r0 ^ r8
            if (r0 == 0) goto L_0x0067
            r0 = 66
            goto L_0x0068
        L_0x0067:
            r0 = r5
        L_0x0068:
            android.view.View r0 = r3.findNextFocus(r13, r14, r0)
            if (r0 != 0) goto L_0x0070
            r0 = r1
            goto L_0x0071
        L_0x0070:
            r0 = r2
        L_0x0071:
            if (r0 == 0) goto L_0x008c
            r13.consumePendingUpdateOperations()
            android.view.View r0 = r13.findContainingItemView(r14)
            if (r0 != 0) goto L_0x007d
            return r6
        L_0x007d:
            r13.startInterceptRequestLayout()
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r13.mLayout
            androidx.recyclerview.widget.RecyclerView$Recycler r8 = r13.mRecycler
            androidx.recyclerview.widget.RecyclerView$State r9 = r13.mState
            r0.onFocusSearchFailed(r14, r15, r8, r9)
            r13.stopInterceptRequestLayout(r2)
        L_0x008c:
            android.view.View r0 = r3.findNextFocus(r13, r14, r15)
            goto L_0x00b5
        L_0x0091:
            android.view.View r3 = r3.findNextFocus(r13, r14, r15)
            if (r3 != 0) goto L_0x00b4
            if (r0 == 0) goto L_0x00b4
            r13.consumePendingUpdateOperations()
            android.view.View r0 = r13.findContainingItemView(r14)
            if (r0 != 0) goto L_0x00a3
            return r6
        L_0x00a3:
            r13.startInterceptRequestLayout()
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r13.mLayout
            androidx.recyclerview.widget.RecyclerView$Recycler r3 = r13.mRecycler
            androidx.recyclerview.widget.RecyclerView$State r8 = r13.mState
            android.view.View r0 = r0.onFocusSearchFailed(r14, r15, r3, r8)
            r13.stopInterceptRequestLayout(r2)
            goto L_0x00b5
        L_0x00b4:
            r0 = r3
        L_0x00b5:
            if (r0 == 0) goto L_0x00cc
            boolean r3 = r0.hasFocusable()
            if (r3 != 0) goto L_0x00cc
            android.view.View r1 = r13.getFocusedChild()
            if (r1 != 0) goto L_0x00c8
            android.view.View r13 = super.focusSearch(r14, r15)
            return r13
        L_0x00c8:
            r13.requestChildOnScreen(r0, r6)
            return r14
        L_0x00cc:
            if (r0 == 0) goto L_0x019d
            if (r0 == r13) goto L_0x019d
            if (r0 != r14) goto L_0x00d4
            goto L_0x019d
        L_0x00d4:
            android.view.View r3 = r13.findContainingItemView(r0)
            if (r3 != 0) goto L_0x00dd
            r1 = r2
            goto L_0x019e
        L_0x00dd:
            if (r14 != 0) goto L_0x00e1
            goto L_0x019e
        L_0x00e1:
            android.view.View r3 = r13.findContainingItemView(r14)
            if (r3 != 0) goto L_0x00e9
            goto L_0x019e
        L_0x00e9:
            android.graphics.Rect r3 = r13.mTempRect
            int r6 = r14.getWidth()
            int r8 = r14.getHeight()
            r3.set(r2, r2, r6, r8)
            android.graphics.Rect r3 = r13.mTempRect2
            int r6 = r0.getWidth()
            int r8 = r0.getHeight()
            r3.set(r2, r2, r6, r8)
            android.graphics.Rect r2 = r13.mTempRect
            r13.offsetDescendantRectToMyCoords(r14, r2)
            android.graphics.Rect r2 = r13.mTempRect2
            r13.offsetDescendantRectToMyCoords(r0, r2)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r2 = r13.mLayout
            int r2 = r2.getLayoutDirection()
            if (r2 != r1) goto L_0x0117
            r2 = -1
            goto L_0x0118
        L_0x0117:
            r2 = r1
        L_0x0118:
            android.graphics.Rect r3 = r13.mTempRect
            int r6 = r3.left
            android.graphics.Rect r8 = r13.mTempRect2
            int r9 = r8.left
            if (r6 < r9) goto L_0x0126
            int r10 = r3.right
            if (r10 > r9) goto L_0x012e
        L_0x0126:
            int r10 = r3.right
            int r11 = r8.right
            if (r10 >= r11) goto L_0x012e
            r6 = r1
            goto L_0x013b
        L_0x012e:
            int r10 = r3.right
            int r11 = r8.right
            if (r10 > r11) goto L_0x0136
            if (r6 < r11) goto L_0x013a
        L_0x0136:
            if (r6 <= r9) goto L_0x013a
            r6 = -1
            goto L_0x013b
        L_0x013a:
            r6 = 0
        L_0x013b:
            int r9 = r3.top
            int r10 = r8.top
            if (r9 < r10) goto L_0x0145
            int r11 = r3.bottom
            if (r11 > r10) goto L_0x014d
        L_0x0145:
            int r11 = r3.bottom
            int r12 = r8.bottom
            if (r11 >= r12) goto L_0x014d
            r3 = r1
            goto L_0x015a
        L_0x014d:
            int r3 = r3.bottom
            int r8 = r8.bottom
            if (r3 > r8) goto L_0x0155
            if (r9 < r8) goto L_0x0159
        L_0x0155:
            if (r9 <= r10) goto L_0x0159
            r3 = -1
            goto L_0x015a
        L_0x0159:
            r3 = 0
        L_0x015a:
            if (r15 == r1) goto L_0x0195
            if (r15 == r7) goto L_0x018d
            if (r15 == r5) goto L_0x018a
            if (r15 == r4) goto L_0x0187
            r2 = 66
            if (r15 == r2) goto L_0x0184
            r2 = 130(0x82, float:1.82E-43)
            if (r15 != r2) goto L_0x016d
            if (r3 <= 0) goto L_0x019d
            goto L_0x019e
        L_0x016d:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Invalid direction: "
            r0.append(r1)
            r0.append(r15)
            java.lang.String r13 = androidx.recyclerview.widget.ChildHelper$$ExternalSyntheticOutline0.m18m(r13, r0)
            r14.<init>(r13)
            throw r14
        L_0x0184:
            if (r6 <= 0) goto L_0x019d
            goto L_0x019e
        L_0x0187:
            if (r3 >= 0) goto L_0x019d
            goto L_0x019e
        L_0x018a:
            if (r6 >= 0) goto L_0x019d
            goto L_0x019e
        L_0x018d:
            if (r3 > 0) goto L_0x019e
            if (r3 != 0) goto L_0x019d
            int r6 = r6 * r2
            if (r6 <= 0) goto L_0x019d
            goto L_0x019e
        L_0x0195:
            if (r3 < 0) goto L_0x019e
            if (r3 != 0) goto L_0x019d
            int r6 = r6 * r2
            if (r6 >= 0) goto L_0x019d
            goto L_0x019e
        L_0x019d:
            r1 = 0
        L_0x019e:
            if (r1 == 0) goto L_0x01a1
            goto L_0x01a5
        L_0x01a1:
            android.view.View r0 = super.focusSearch(r14, r15)
        L_0x01a5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.focusSearch(android.view.View, int):android.view.View");
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateDefaultLayoutParams();
        }
        throw new IllegalStateException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, VendorAtomValue$$ExternalSyntheticOutline1.m1m("RecyclerView has no LayoutManager")));
    }

    public final int getBaseline() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return super.getBaseline();
        }
        Objects.requireNonNull(layoutManager);
        return -1;
    }

    public final long getChangedHolderKey(ViewHolder viewHolder) {
        Adapter adapter = this.mAdapter;
        Objects.requireNonNull(adapter);
        if (!adapter.mHasStableIds) {
            return (long) viewHolder.mPosition;
        }
        Objects.requireNonNull(viewHolder);
        return viewHolder.mItemId;
    }

    public int getChildDrawingOrder(int i, int i2) {
        ChildDrawingOrderCallback childDrawingOrderCallback = this.mChildDrawingOrderCallback;
        if (childDrawingOrderCallback == null) {
            return super.getChildDrawingOrder(i, i2);
        }
        return childDrawingOrderCallback.onGetChildDrawingOrder();
    }

    public final NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return this.mScrollingChildHelper;
    }

    public void initFastScroller(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
            throw new IllegalArgumentException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, VendorAtomValue$$ExternalSyntheticOutline1.m1m("Trying to set fast scroller without both required drawables.")));
        }
        Resources resources = getContext().getResources();
        new FastScroller(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(C1777R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(C1777R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(C1777R.dimen.fastscroll_margin));
    }

    public final boolean isComputingLayout() {
        if (this.mLayoutOrScrollCounter > 0) {
            return true;
        }
        return false;
    }

    public final void jumpToPositionForSmoothScroller(int i) {
        if (this.mLayout != null) {
            setScrollState(2);
            this.mLayout.scrollToPosition(i);
            awakenScrollBars();
        }
    }

    public final void markItemDecorInsetsDirty() {
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ((LayoutParams) this.mChildHelper.getUnfilteredChildAt(i).getLayoutParams()).mInsetsDirty = true;
        }
        Recycler recycler = this.mRecycler;
        Objects.requireNonNull(recycler);
        int size = recycler.mCachedViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            LayoutParams layoutParams = (LayoutParams) recycler.mCachedViews.get(i2).itemView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.mInsetsDirty = true;
            }
        }
    }

    public final void offsetPositionRecordsForRemove(int i, int i2, boolean z) {
        int i3 = i + i2;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i4 = 0; i4 < unfilteredChildCount; i4++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i4));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                int i5 = childViewHolderInt.mPosition;
                if (i5 >= i3) {
                    childViewHolderInt.offsetPosition(-i2, z);
                    this.mState.mStructureChanged = true;
                } else if (i5 >= i) {
                    childViewHolderInt.addFlags(8);
                    childViewHolderInt.offsetPosition(-i2, z);
                    childViewHolderInt.mPosition = i - 1;
                    this.mState.mStructureChanged = true;
                }
            }
        }
        Recycler recycler = this.mRecycler;
        Objects.requireNonNull(recycler);
        int size = recycler.mCachedViews.size();
        while (true) {
            size--;
            if (size >= 0) {
                ViewHolder viewHolder = recycler.mCachedViews.get(size);
                if (viewHolder != null) {
                    int i6 = viewHolder.mPosition;
                    if (i6 >= i3) {
                        viewHolder.offsetPosition(-i2, z);
                    } else if (i6 >= i) {
                        viewHolder.addFlags(8);
                        recycler.recycleCachedViewAt(size);
                    }
                }
            } else {
                requestLayout();
                return;
            }
        }
    }

    public final void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    public final void onExitLayoutOrScroll(boolean z) {
        int i;
        boolean z2 = true;
        int i2 = this.mLayoutOrScrollCounter - 1;
        this.mLayoutOrScrollCounter = i2;
        if (i2 < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (z) {
                int i3 = this.mEatenAccessibilityChangeFlags;
                this.mEatenAccessibilityChangeFlags = 0;
                if (i3 != 0) {
                    AccessibilityManager accessibilityManager = this.mAccessibilityManager;
                    if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                        z2 = false;
                    }
                    if (z2) {
                        AccessibilityEvent obtain = AccessibilityEvent.obtain();
                        obtain.setEventType(2048);
                        obtain.setContentChangeTypes(i3);
                        sendAccessibilityEventUnchecked(obtain);
                    }
                }
                for (int size = this.mPendingAccessibilityImportanceChange.size() - 1; size >= 0; size--) {
                    ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(size);
                    if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (i = viewHolder.mPendingAccessibilityState) != -1) {
                        View view = viewHolder.itemView;
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api16Impl.setImportantForAccessibility(view, i);
                        viewHolder.mPendingAccessibilityState = -1;
                    }
                }
                this.mPendingAccessibilityImportanceChange.clear();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onGenericMotionEvent(android.view.MotionEvent r15) {
        /*
            r14 = this;
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r14.mLayout
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r0 = r14.mLayoutSuppressed
            if (r0 == 0) goto L_0x000b
            return r1
        L_0x000b:
            int r0 = r15.getAction()
            r2 = 8
            if (r0 != r2) goto L_0x00f5
            int r0 = r15.getSource()
            r0 = r0 & 2
            r2 = 0
            if (r0 == 0) goto L_0x003c
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r14.mLayout
            boolean r0 = r0.canScrollVertically()
            if (r0 == 0) goto L_0x002c
            r0 = 9
            float r0 = r15.getAxisValue(r0)
            float r0 = -r0
            goto L_0x002d
        L_0x002c:
            r0 = r2
        L_0x002d:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r3 = r14.mLayout
            boolean r3 = r3.canScrollHorizontally()
            if (r3 == 0) goto L_0x0054
            r3 = 10
            float r3 = r15.getAxisValue(r3)
            goto L_0x0062
        L_0x003c:
            int r0 = r15.getSource()
            r3 = 4194304(0x400000, float:5.877472E-39)
            r0 = r0 & r3
            if (r0 == 0) goto L_0x0060
            r0 = 26
            float r3 = r15.getAxisValue(r0)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r14.mLayout
            boolean r0 = r0.canScrollVertically()
            if (r0 == 0) goto L_0x0056
            float r0 = -r3
        L_0x0054:
            r3 = r2
            goto L_0x0062
        L_0x0056:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r14.mLayout
            boolean r0 = r0.canScrollHorizontally()
            if (r0 == 0) goto L_0x0060
            r0 = r2
            goto L_0x0062
        L_0x0060:
            r0 = r2
            r3 = r0
        L_0x0062:
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x006a
            int r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r2 == 0) goto L_0x00f5
        L_0x006a:
            float r2 = r14.mScaledHorizontalScrollFactor
            float r3 = r3 * r2
            int r2 = (int) r3
            float r3 = r14.mScaledVerticalScrollFactor
            float r0 = r0 * r3
            int r0 = (int) r0
            androidx.recyclerview.widget.RecyclerView$LayoutManager r3 = r14.mLayout
            if (r3 != 0) goto L_0x007f
            java.lang.String r14 = "RecyclerView"
            java.lang.String r15 = "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument."
            android.util.Log.e(r14, r15)
            goto L_0x00f5
        L_0x007f:
            boolean r4 = r14.mLayoutSuppressed
            if (r4 == 0) goto L_0x0085
            goto L_0x00f5
        L_0x0085:
            int[] r4 = r14.mReusableIntPair
            r4[r1] = r1
            r5 = 1
            r4[r5] = r1
            boolean r3 = r3.canScrollHorizontally()
            androidx.recyclerview.widget.RecyclerView$LayoutManager r4 = r14.mLayout
            boolean r4 = r4.canScrollVertically()
            if (r4 == 0) goto L_0x009b
            r6 = r3 | 2
            goto L_0x009c
        L_0x009b:
            r6 = r3
        L_0x009c:
            float r7 = r15.getY()
            float r8 = r15.getX()
            int r7 = r14.releaseHorizontalGlow(r2, r7)
            int r2 = r2 - r7
            int r7 = r14.releaseVerticalGlow(r0, r8)
            int r0 = r0 - r7
            androidx.core.view.NestedScrollingChildHelper r7 = r14.getScrollingChildHelper()
            r7.startNestedScroll(r6, r5)
            if (r3 == 0) goto L_0x00b9
            r9 = r2
            goto L_0x00ba
        L_0x00b9:
            r9 = r1
        L_0x00ba:
            if (r4 == 0) goto L_0x00be
            r10 = r0
            goto L_0x00bf
        L_0x00be:
            r10 = r1
        L_0x00bf:
            int[] r11 = r14.mReusableIntPair
            int[] r12 = r14.mScrollOffset
            r13 = 1
            androidx.core.view.NestedScrollingChildHelper r8 = r14.getScrollingChildHelper()
            boolean r6 = r8.dispatchNestedPreScroll(r9, r10, r11, r12, r13)
            if (r6 == 0) goto L_0x00d6
            int[] r6 = r14.mReusableIntPair
            r7 = r6[r1]
            int r2 = r2 - r7
            r6 = r6[r5]
            int r0 = r0 - r6
        L_0x00d6:
            if (r3 == 0) goto L_0x00da
            r3 = r2
            goto L_0x00db
        L_0x00da:
            r3 = r1
        L_0x00db:
            if (r4 == 0) goto L_0x00df
            r4 = r0
            goto L_0x00e0
        L_0x00df:
            r4 = r1
        L_0x00e0:
            r14.scrollByInternal(r3, r4, r15, r5)
            androidx.recyclerview.widget.GapWorker r15 = r14.mGapWorker
            if (r15 == 0) goto L_0x00ee
            if (r2 != 0) goto L_0x00eb
            if (r0 == 0) goto L_0x00ee
        L_0x00eb:
            r15.postFromTraversal(r14, r2, r0)
        L_0x00ee:
            androidx.core.view.NestedScrollingChildHelper r14 = r14.getScrollingChildHelper()
            r14.stopNestedScroll(r5)
        L_0x00f5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        if (this.mLayoutSuppressed) {
            return false;
        }
        this.mInterceptingOnItemTouchListener = null;
        if (findInterceptingOnItemTouchListener(motionEvent)) {
            resetScroll();
            setScrollState(0);
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean canScrollVertically = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.mIgnoreMotionEventTillDown) {
                this.mIgnoreMotionEventTillDown = false;
            }
            this.mScrollPointerId = motionEvent.getPointerId(0);
            int x = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY() + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            EdgeEffect edgeEffect = this.mLeftGlow;
            if (edgeEffect == null || EdgeEffectCompat$Api31Impl.getDistance(edgeEffect) == 0.0f) {
                z = false;
            } else {
                EdgeEffectCompat$Api31Impl.onPullDistance(this.mLeftGlow, 0.0f, 1.0f - (motionEvent.getY() / ((float) getHeight())));
                z = true;
            }
            EdgeEffect edgeEffect2 = this.mRightGlow;
            if (!(edgeEffect2 == null || EdgeEffectCompat$Api31Impl.getDistance(edgeEffect2) == 0.0f)) {
                EdgeEffectCompat$Api31Impl.onPullDistance(this.mRightGlow, 0.0f, motionEvent.getY() / ((float) getHeight()));
                z = true;
            }
            EdgeEffect edgeEffect3 = this.mTopGlow;
            if (!(edgeEffect3 == null || EdgeEffectCompat$Api31Impl.getDistance(edgeEffect3) == 0.0f)) {
                EdgeEffectCompat$Api31Impl.onPullDistance(this.mTopGlow, 0.0f, motionEvent.getX() / ((float) getWidth()));
                z = true;
            }
            EdgeEffect edgeEffect4 = this.mBottomGlow;
            if (!(edgeEffect4 == null || EdgeEffectCompat$Api31Impl.getDistance(edgeEffect4) == 0.0f)) {
                EdgeEffectCompat$Api31Impl.onPullDistance(this.mBottomGlow, 0.0f, 1.0f - (motionEvent.getX() / ((float) getWidth())));
                z = true;
            }
            if (z || this.mScrollState == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
                getScrollingChildHelper().stopNestedScroll(1);
            }
            int[] iArr = this.mNestedOffsets;
            iArr[1] = 0;
            iArr[0] = 0;
            if (canScrollVertically) {
                canScrollHorizontally |= true;
            }
            getScrollingChildHelper().startNestedScroll(canScrollHorizontally ? 1 : 0, 0);
        } else if (actionMasked == 1) {
            this.mVelocityTracker.clear();
            getScrollingChildHelper().stopNestedScroll(0);
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
            if (findPointerIndex < 0) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Error processing scroll; pointer index for id ");
                m.append(this.mScrollPointerId);
                m.append(" not found. Did any MotionEvents get skipped?");
                Log.e("RecyclerView", m.toString());
                return false;
            }
            int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            if (this.mScrollState != 1) {
                int i = x2 - this.mInitialTouchX;
                int i2 = y2 - this.mInitialTouchY;
                if (!canScrollHorizontally || Math.abs(i) <= this.mTouchSlop) {
                    z2 = false;
                } else {
                    this.mLastTouchX = x2;
                    z2 = true;
                }
                if (canScrollVertically && Math.abs(i2) > this.mTouchSlop) {
                    this.mLastTouchY = y2;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            resetScroll();
            setScrollState(0);
        } else if (actionMasked == 5) {
            this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
            int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.mLastTouchX = x3;
            this.mInitialTouchX = x3;
            int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.mLastTouchY = y3;
            this.mInitialTouchY = y3;
        } else if (actionMasked == 6) {
            onPointerUp(motionEvent);
        }
        if (this.mScrollState == 1) {
            return true;
        }
        return false;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Trace.beginSection("RV OnLayout");
        dispatchLayout();
        Trace.endSection();
        this.mFirstLayoutComplete = true;
    }

    public void onMeasure(int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            defaultOnMeasure(i, i2);
            return;
        }
        boolean z = false;
        if (layoutManager.isAutoMeasureEnabled()) {
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            this.mLastAutoMeasureSkippedDueToExact = z;
            if (!z && this.mAdapter != null) {
                if (this.mState.mLayoutStep == 1) {
                    dispatchLayoutStep1();
                }
                this.mLayout.setMeasureSpecs(i, i2);
                this.mState.mIsMeasuring = true;
                dispatchLayoutStep2();
                this.mLayout.setMeasuredDimensionFromChildren(i, i2);
                if (this.mLayout.shouldMeasureTwice()) {
                    this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.mState.mIsMeasuring = true;
                    dispatchLayoutStep2();
                    this.mLayout.setMeasuredDimensionFromChildren(i, i2);
                }
                this.mLastAutoMeasureNonExactMeasuredWidth = getMeasuredWidth();
                this.mLastAutoMeasureNonExactMeasuredHeight = getMeasuredHeight();
            }
        } else if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
        } else {
            if (this.mAdapterUpdateDuringMeasure) {
                startInterceptRequestLayout();
                onEnterLayoutOrScroll();
                processAdapterUpdatesAndSetAnimationFlags();
                onExitLayoutOrScroll(true);
                State state = this.mState;
                if (state.mRunPredictiveAnimations) {
                    state.mInPreLayout = true;
                } else {
                    this.mAdapterHelper.consumeUpdatesInOnePass();
                    this.mState.mInPreLayout = false;
                }
                this.mAdapterUpdateDuringMeasure = false;
                stopInterceptRequestLayout(false);
            } else if (this.mState.mRunPredictiveAnimations) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            Adapter adapter = this.mAdapter;
            if (adapter != null) {
                this.mState.mItemCount = adapter.getItemCount();
            } else {
                this.mState.mItemCount = 0;
            }
            startInterceptRequestLayout();
            this.mLayout.onMeasure(this.mRecycler, this.mState, i, i2);
            stopInterceptRequestLayout(false);
            this.mState.mInPreLayout = false;
        }
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.mPendingSavedState = savedState;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        requestLayout();
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.mPendingSavedState;
        if (savedState2 != null) {
            savedState.mLayoutState = savedState2.mLayoutState;
        } else {
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager != null) {
                savedState.mLayoutState = layoutManager.onSaveInstanceState();
            } else {
                savedState.mLayoutState = null;
            }
        }
        return savedState;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:220:0x036e, code lost:
        if (r1 < r3) goto L_0x0371;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x0381, code lost:
        if (r3 != 0) goto L_0x03e3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x0374  */
    /* JADX WARNING: Removed duplicated region for block: B:225:0x0376  */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x03ea  */
    /* JADX WARNING: Removed duplicated region for block: B:251:0x041e  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x0426  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            boolean r2 = r0.mLayoutSuppressed
            r3 = 0
            if (r2 != 0) goto L_0x042d
            boolean r2 = r0.mIgnoreMotionEventTillDown
            if (r2 == 0) goto L_0x000f
            goto L_0x042d
        L_0x000f:
            androidx.recyclerview.widget.RecyclerView$OnItemTouchListener r2 = r0.mInterceptingOnItemTouchListener
            r4 = 3
            r5 = 0
            r6 = 1
            if (r2 != 0) goto L_0x0023
            int r2 = r20.getAction()
            if (r2 != 0) goto L_0x001e
            r2 = r3
            goto L_0x0031
        L_0x001e:
            boolean r2 = r19.findInterceptingOnItemTouchListener(r20)
            goto L_0x0031
        L_0x0023:
            r2.onTouchEvent(r1)
            int r2 = r20.getAction()
            if (r2 == r4) goto L_0x002e
            if (r2 != r6) goto L_0x0030
        L_0x002e:
            r0.mInterceptingOnItemTouchListener = r5
        L_0x0030:
            r2 = r6
        L_0x0031:
            if (r2 == 0) goto L_0x003a
            r19.resetScroll()
            r0.setScrollState(r3)
            return r6
        L_0x003a:
            androidx.recyclerview.widget.RecyclerView$LayoutManager r2 = r0.mLayout
            if (r2 != 0) goto L_0x003f
            return r3
        L_0x003f:
            boolean r2 = r2.canScrollHorizontally()
            androidx.recyclerview.widget.RecyclerView$LayoutManager r5 = r0.mLayout
            boolean r5 = r5.canScrollVertically()
            android.view.VelocityTracker r7 = r0.mVelocityTracker
            if (r7 != 0) goto L_0x0053
            android.view.VelocityTracker r7 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r7
        L_0x0053:
            int r7 = r20.getActionMasked()
            int r8 = r20.getActionIndex()
            if (r7 != 0) goto L_0x0063
            int[] r9 = r0.mNestedOffsets
            r9[r6] = r3
            r9[r3] = r3
        L_0x0063:
            android.view.MotionEvent r9 = android.view.MotionEvent.obtain(r20)
            int[] r10 = r0.mNestedOffsets
            r11 = r10[r3]
            float r11 = (float) r11
            r10 = r10[r6]
            float r10 = (float) r10
            r9.offsetLocation(r11, r10)
            r10 = 1056964608(0x3f000000, float:0.5)
            if (r7 == 0) goto L_0x03f3
            java.lang.String r11 = "RecyclerView"
            r12 = 2
            if (r7 == r6) goto L_0x01a2
            if (r7 == r12) goto L_0x00b0
            if (r7 == r4) goto L_0x00a8
            r2 = 5
            if (r7 == r2) goto L_0x008c
            r2 = 6
            if (r7 == r2) goto L_0x0087
            goto L_0x019e
        L_0x0087:
            r19.onPointerUp(r20)
            goto L_0x019e
        L_0x008c:
            int r2 = r1.getPointerId(r8)
            r0.mScrollPointerId = r2
            float r2 = r1.getX(r8)
            float r2 = r2 + r10
            int r2 = (int) r2
            r0.mLastTouchX = r2
            r0.mInitialTouchX = r2
            float r1 = r1.getY(r8)
            float r1 = r1 + r10
            int r1 = (int) r1
            r0.mLastTouchY = r1
            r0.mInitialTouchY = r1
            goto L_0x019e
        L_0x00a8:
            r19.resetScroll()
            r0.setScrollState(r3)
            goto L_0x019e
        L_0x00b0:
            int r4 = r0.mScrollPointerId
            int r4 = r1.findPointerIndex(r4)
            if (r4 >= 0) goto L_0x00d0
            java.lang.String r1 = "Error processing scroll; pointer index for id "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r0 = r0.mScrollPointerId
            r1.append(r0)
            java.lang.String r0 = " not found. Did any MotionEvents get skipped?"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.e(r11, r0)
            return r3
        L_0x00d0:
            float r7 = r1.getX(r4)
            float r7 = r7 + r10
            int r7 = (int) r7
            float r4 = r1.getY(r4)
            float r4 = r4 + r10
            int r4 = (int) r4
            int r8 = r0.mLastTouchX
            int r8 = r8 - r7
            int r10 = r0.mLastTouchY
            int r10 = r10 - r4
            int r11 = r0.mScrollState
            if (r11 == r6) goto L_0x0119
            if (r2 == 0) goto L_0x00fd
            if (r8 <= 0) goto L_0x00f2
            int r11 = r0.mTouchSlop
            int r8 = r8 - r11
            int r8 = java.lang.Math.max(r3, r8)
            goto L_0x00f9
        L_0x00f2:
            int r11 = r0.mTouchSlop
            int r8 = r8 + r11
            int r8 = java.lang.Math.min(r3, r8)
        L_0x00f9:
            if (r8 == 0) goto L_0x00fd
            r11 = r6
            goto L_0x00fe
        L_0x00fd:
            r11 = r3
        L_0x00fe:
            if (r5 == 0) goto L_0x0114
            if (r10 <= 0) goto L_0x010a
            int r12 = r0.mTouchSlop
            int r10 = r10 - r12
            int r10 = java.lang.Math.max(r3, r10)
            goto L_0x0111
        L_0x010a:
            int r12 = r0.mTouchSlop
            int r10 = r10 + r12
            int r10 = java.lang.Math.min(r3, r10)
        L_0x0111:
            if (r10 == 0) goto L_0x0114
            r11 = r6
        L_0x0114:
            if (r11 == 0) goto L_0x0119
            r0.setScrollState(r6)
        L_0x0119:
            int r11 = r0.mScrollState
            if (r11 != r6) goto L_0x019e
            int[] r11 = r0.mReusableIntPair
            r11[r3] = r3
            r11[r6] = r3
            float r11 = r20.getY()
            int r11 = r0.releaseHorizontalGlow(r8, r11)
            int r8 = r8 - r11
            float r11 = r20.getX()
            int r11 = r0.releaseVerticalGlow(r10, r11)
            int r10 = r10 - r11
            if (r2 == 0) goto L_0x0139
            r12 = r8
            goto L_0x013a
        L_0x0139:
            r12 = r3
        L_0x013a:
            if (r5 == 0) goto L_0x013e
            r13 = r10
            goto L_0x013f
        L_0x013e:
            r13 = r3
        L_0x013f:
            int[] r14 = r0.mReusableIntPair
            int[] r15 = r0.mScrollOffset
            r16 = 0
            androidx.core.view.NestedScrollingChildHelper r11 = r19.getScrollingChildHelper()
            boolean r11 = r11.dispatchNestedPreScroll(r12, r13, r14, r15, r16)
            if (r11 == 0) goto L_0x0170
            int[] r11 = r0.mReusableIntPair
            r12 = r11[r3]
            int r8 = r8 - r12
            r11 = r11[r6]
            int r10 = r10 - r11
            int[] r11 = r0.mNestedOffsets
            r12 = r11[r3]
            int[] r13 = r0.mScrollOffset
            r14 = r13[r3]
            int r12 = r12 + r14
            r11[r3] = r12
            r12 = r11[r6]
            r13 = r13[r6]
            int r12 = r12 + r13
            r11[r6] = r12
            android.view.ViewParent r11 = r19.getParent()
            r11.requestDisallowInterceptTouchEvent(r6)
        L_0x0170:
            int[] r11 = r0.mScrollOffset
            r12 = r11[r3]
            int r7 = r7 - r12
            r0.mLastTouchX = r7
            r7 = r11[r6]
            int r4 = r4 - r7
            r0.mLastTouchY = r4
            if (r2 == 0) goto L_0x0180
            r2 = r8
            goto L_0x0181
        L_0x0180:
            r2 = r3
        L_0x0181:
            if (r5 == 0) goto L_0x0185
            r4 = r10
            goto L_0x0186
        L_0x0185:
            r4 = r3
        L_0x0186:
            boolean r1 = r0.scrollByInternal(r2, r4, r1, r3)
            if (r1 == 0) goto L_0x0193
            android.view.ViewParent r1 = r19.getParent()
            r1.requestDisallowInterceptTouchEvent(r6)
        L_0x0193:
            androidx.recyclerview.widget.GapWorker r1 = r0.mGapWorker
            if (r1 == 0) goto L_0x019e
            if (r8 != 0) goto L_0x019b
            if (r10 == 0) goto L_0x019e
        L_0x019b:
            r1.postFromTraversal(r0, r8, r10)
        L_0x019e:
            r18 = r9
            goto L_0x041b
        L_0x01a2:
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            r1.addMovement(r9)
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            r4 = 1000(0x3e8, float:1.401E-42)
            int r7 = r0.mMaxFlingVelocity
            float r7 = (float) r7
            r1.computeCurrentVelocity(r4, r7)
            r1 = 0
            if (r2 == 0) goto L_0x01be
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            int r4 = r0.mScrollPointerId
            float r2 = r2.getXVelocity(r4)
            float r2 = -r2
            goto L_0x01bf
        L_0x01be:
            r2 = r1
        L_0x01bf:
            if (r5 == 0) goto L_0x01cb
            android.view.VelocityTracker r4 = r0.mVelocityTracker
            int r5 = r0.mScrollPointerId
            float r4 = r4.getYVelocity(r5)
            float r4 = -r4
            goto L_0x01cc
        L_0x01cb:
            r4 = r1
        L_0x01cc:
            int r5 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x01d9
            int r5 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r5 == 0) goto L_0x01d5
            goto L_0x01d9
        L_0x01d5:
            r18 = r9
            goto L_0x03eb
        L_0x01d9:
            int r2 = (int) r2
            int r4 = (int) r4
            androidx.recyclerview.widget.RecyclerView$LayoutManager r5 = r0.mLayout
            if (r5 != 0) goto L_0x01e6
            java.lang.String r1 = "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument."
            android.util.Log.e(r11, r1)
            goto L_0x0263
        L_0x01e6:
            boolean r7 = r0.mLayoutSuppressed
            if (r7 == 0) goto L_0x01ec
            goto L_0x0263
        L_0x01ec:
            boolean r5 = r5.canScrollHorizontally()
            androidx.recyclerview.widget.RecyclerView$LayoutManager r7 = r0.mLayout
            boolean r7 = r7.canScrollVertically()
            if (r5 == 0) goto L_0x0200
            int r8 = java.lang.Math.abs(r2)
            int r10 = r0.mMinFlingVelocity
            if (r8 >= r10) goto L_0x0201
        L_0x0200:
            r2 = r3
        L_0x0201:
            if (r7 == 0) goto L_0x020b
            int r8 = java.lang.Math.abs(r4)
            int r10 = r0.mMinFlingVelocity
            if (r8 >= r10) goto L_0x020c
        L_0x020b:
            r4 = r3
        L_0x020c:
            if (r2 != 0) goto L_0x0211
            if (r4 != 0) goto L_0x0211
            goto L_0x0263
        L_0x0211:
            if (r2 == 0) goto L_0x0238
            android.widget.EdgeEffect r8 = r0.mLeftGlow
            if (r8 == 0) goto L_0x0226
            float r8 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r8)
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x0226
            android.widget.EdgeEffect r8 = r0.mLeftGlow
            int r2 = -r2
            r8.onAbsorb(r2)
            goto L_0x0237
        L_0x0226:
            android.widget.EdgeEffect r8 = r0.mRightGlow
            if (r8 == 0) goto L_0x0238
            float r8 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r8)
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x0238
            android.widget.EdgeEffect r8 = r0.mRightGlow
            r8.onAbsorb(r2)
        L_0x0237:
            r2 = r3
        L_0x0238:
            if (r4 == 0) goto L_0x025f
            android.widget.EdgeEffect r8 = r0.mTopGlow
            if (r8 == 0) goto L_0x024d
            float r8 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r8)
            int r8 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x024d
            android.widget.EdgeEffect r1 = r0.mTopGlow
            int r4 = -r4
            r1.onAbsorb(r4)
            goto L_0x025e
        L_0x024d:
            android.widget.EdgeEffect r8 = r0.mBottomGlow
            if (r8 == 0) goto L_0x025f
            float r8 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r8)
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x025f
            android.widget.EdgeEffect r1 = r0.mBottomGlow
            r1.onAbsorb(r4)
        L_0x025e:
            r4 = r3
        L_0x025f:
            if (r2 != 0) goto L_0x0267
            if (r4 != 0) goto L_0x0267
        L_0x0263:
            r18 = r9
            goto L_0x03e8
        L_0x0267:
            float r1 = (float) r2
            float r8 = (float) r4
            boolean r10 = r0.dispatchNestedPreFling(r1, r8)
            if (r10 != 0) goto L_0x03e5
            if (r5 != 0) goto L_0x0275
            if (r7 == 0) goto L_0x0274
            goto L_0x0275
        L_0x0274:
            r6 = r3
        L_0x0275:
            r0.dispatchNestedFling(r1, r8, r6)
            androidx.recyclerview.widget.RecyclerView$OnFlingListener r1 = r0.mOnFlingListener
            if (r1 == 0) goto L_0x0384
            androidx.recyclerview.widget.SnapHelper r1 = (androidx.recyclerview.widget.SnapHelper) r1
            androidx.recyclerview.widget.RecyclerView r8 = r1.mRecyclerView
            java.util.Objects.requireNonNull(r8)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r8 = r8.mLayout
            if (r8 != 0) goto L_0x0288
            goto L_0x0291
        L_0x0288:
            androidx.recyclerview.widget.RecyclerView r10 = r1.mRecyclerView
            java.util.Objects.requireNonNull(r10)
            androidx.recyclerview.widget.RecyclerView$Adapter r10 = r10.mAdapter
            if (r10 != 0) goto L_0x0295
        L_0x0291:
            r18 = r9
            goto L_0x0381
        L_0x0295:
            androidx.recyclerview.widget.RecyclerView r10 = r1.mRecyclerView
            java.util.Objects.requireNonNull(r10)
            int r10 = r10.mMinFlingVelocity
            int r11 = java.lang.Math.abs(r4)
            if (r11 > r10) goto L_0x02ad
            int r11 = java.lang.Math.abs(r2)
            if (r11 <= r10) goto L_0x02a9
            goto L_0x02ad
        L_0x02a9:
            r18 = r9
            goto L_0x0380
        L_0x02ad:
            boolean r10 = r8 instanceof androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
            if (r10 != 0) goto L_0x02b2
            goto L_0x02b8
        L_0x02b2:
            androidx.recyclerview.widget.RecyclerView$SmoothScroller r11 = r1.createScroller(r8)
            if (r11 != 0) goto L_0x02bc
        L_0x02b8:
            r18 = r9
            goto L_0x037c
        L_0x02bc:
            androidx.recyclerview.widget.PagerSnapHelper r1 = (androidx.recyclerview.widget.PagerSnapHelper) r1
            int r3 = r8.getItemCount()
            if (r3 != 0) goto L_0x02c5
            goto L_0x02de
        L_0x02c5:
            boolean r12 = r8.canScrollVertically()
            if (r12 == 0) goto L_0x02d0
            androidx.recyclerview.widget.OrientationHelper r1 = r1.getVerticalHelper(r8)
            goto L_0x02dc
        L_0x02d0:
            boolean r12 = r8.canScrollHorizontally()
            if (r12 == 0) goto L_0x02db
            androidx.recyclerview.widget.OrientationHelper r1 = r1.getHorizontalHelper(r8)
            goto L_0x02dc
        L_0x02db:
            r1 = 0
        L_0x02dc:
            if (r1 != 0) goto L_0x02e2
        L_0x02de:
            r18 = r9
            goto L_0x0370
        L_0x02e2:
            r12 = -2147483648(0xffffffff80000000, float:-0.0)
            r13 = 2147483647(0x7fffffff, float:NaN)
            int r14 = r8.getChildCount()
            r15 = 0
            r16 = 0
            r17 = 0
        L_0x02f0:
            if (r15 >= r14) goto L_0x0318
            r20 = r14
            android.view.View r14 = r8.getChildAt(r15)
            if (r14 != 0) goto L_0x02fd
            r18 = r9
            goto L_0x0311
        L_0x02fd:
            r18 = r9
            int r9 = androidx.recyclerview.widget.PagerSnapHelper.distanceToCenter(r14, r1)
            if (r9 > 0) goto L_0x030a
            if (r9 <= r12) goto L_0x030a
            r12 = r9
            r17 = r14
        L_0x030a:
            if (r9 < 0) goto L_0x0311
            if (r9 >= r13) goto L_0x0311
            r13 = r9
            r16 = r14
        L_0x0311:
            int r15 = r15 + 1
            r14 = r20
            r9 = r18
            goto L_0x02f0
        L_0x0318:
            r18 = r9
            boolean r1 = r8.canScrollHorizontally()
            if (r1 == 0) goto L_0x0323
            if (r2 <= 0) goto L_0x0327
            goto L_0x0325
        L_0x0323:
            if (r4 <= 0) goto L_0x0327
        L_0x0325:
            r1 = 1
            goto L_0x0328
        L_0x0327:
            r1 = 0
        L_0x0328:
            if (r1 == 0) goto L_0x0331
            if (r16 == 0) goto L_0x0331
            int r1 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r16)
            goto L_0x0371
        L_0x0331:
            if (r1 != 0) goto L_0x033a
            if (r17 == 0) goto L_0x033a
            int r1 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r17)
            goto L_0x0371
        L_0x033a:
            if (r1 == 0) goto L_0x033e
            r16 = r17
        L_0x033e:
            if (r16 != 0) goto L_0x0341
            goto L_0x0370
        L_0x0341:
            int r9 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r16)
            int r12 = r8.getItemCount()
            if (r10 == 0) goto L_0x0365
            r10 = r8
            androidx.recyclerview.widget.RecyclerView$SmoothScroller$ScrollVectorProvider r10 = (androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider) r10
            int r12 = r12 + -1
            android.graphics.PointF r10 = r10.computeScrollVectorForPosition(r12)
            if (r10 == 0) goto L_0x0365
            float r12 = r10.x
            r13 = 0
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 < 0) goto L_0x0363
            float r10 = r10.y
            int r10 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r10 >= 0) goto L_0x0365
        L_0x0363:
            r10 = 1
            goto L_0x0366
        L_0x0365:
            r10 = 0
        L_0x0366:
            if (r10 != r1) goto L_0x036a
            r1 = -1
            goto L_0x036b
        L_0x036a:
            r1 = 1
        L_0x036b:
            int r1 = r1 + r9
            if (r1 < 0) goto L_0x0370
            if (r1 < r3) goto L_0x0371
        L_0x0370:
            r1 = -1
        L_0x0371:
            r3 = -1
            if (r1 != r3) goto L_0x0376
            r3 = 0
            goto L_0x037c
        L_0x0376:
            r11.mTargetPosition = r1
            r8.startSmoothScroll(r11)
            r3 = 1
        L_0x037c:
            if (r3 == 0) goto L_0x0380
            r3 = 1
            goto L_0x0381
        L_0x0380:
            r3 = 0
        L_0x0381:
            if (r3 == 0) goto L_0x0386
            goto L_0x03e3
        L_0x0384:
            r18 = r9
        L_0x0386:
            if (r6 == 0) goto L_0x03e7
            if (r7 == 0) goto L_0x038c
            r5 = r5 | 2
        L_0x038c:
            androidx.core.view.NestedScrollingChildHelper r1 = r19.getScrollingChildHelper()
            r3 = 1
            r1.startNestedScroll(r5, r3)
            int r1 = r0.mMaxFlingVelocity
            int r3 = -r1
            int r1 = java.lang.Math.min(r2, r1)
            int r8 = java.lang.Math.max(r3, r1)
            int r1 = r0.mMaxFlingVelocity
            int r2 = -r1
            int r1 = java.lang.Math.min(r4, r1)
            int r9 = java.lang.Math.max(r2, r1)
            androidx.recyclerview.widget.RecyclerView$ViewFlinger r1 = r0.mViewFlinger
            java.util.Objects.requireNonNull(r1)
            androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
            r3 = 2
            r2.setScrollState(r3)
            r2 = 0
            r1.mLastFlingY = r2
            r1.mLastFlingX = r2
            android.view.animation.Interpolator r2 = r1.mInterpolator
            androidx.recyclerview.widget.RecyclerView$3 r3 = sQuinticInterpolator
            if (r2 == r3) goto L_0x03cf
            r1.mInterpolator = r3
            android.widget.OverScroller r2 = new android.widget.OverScroller
            androidx.recyclerview.widget.RecyclerView r4 = androidx.recyclerview.widget.RecyclerView.this
            android.content.Context r4 = r4.getContext()
            r2.<init>(r4, r3)
            r1.mOverScroller = r2
        L_0x03cf:
            android.widget.OverScroller r5 = r1.mOverScroller
            r6 = 0
            r7 = 0
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = 2147483647(0x7fffffff, float:NaN)
            r12 = -2147483648(0xffffffff80000000, float:-0.0)
            r13 = 2147483647(0x7fffffff, float:NaN)
            r5.fling(r6, r7, r8, r9, r10, r11, r12, r13)
            r1.postOnAnimation()
        L_0x03e3:
            r3 = 1
            goto L_0x03e8
        L_0x03e5:
            r18 = r9
        L_0x03e7:
            r3 = 0
        L_0x03e8:
            if (r3 != 0) goto L_0x03ee
            r3 = 0
        L_0x03eb:
            r0.setScrollState(r3)
        L_0x03ee:
            r19.resetScroll()
            r1 = 1
            goto L_0x041c
        L_0x03f3:
            r18 = r9
            int r3 = r1.getPointerId(r3)
            r0.mScrollPointerId = r3
            float r3 = r20.getX()
            float r3 = r3 + r10
            int r3 = (int) r3
            r0.mLastTouchX = r3
            r0.mInitialTouchX = r3
            float r1 = r20.getY()
            float r1 = r1 + r10
            int r1 = (int) r1
            r0.mLastTouchY = r1
            r0.mInitialTouchY = r1
            if (r5 == 0) goto L_0x0413
            r2 = r2 | 2
        L_0x0413:
            androidx.core.view.NestedScrollingChildHelper r1 = r19.getScrollingChildHelper()
            r3 = 0
            r1.startNestedScroll(r2, r3)
        L_0x041b:
            r1 = 0
        L_0x041c:
            if (r1 != 0) goto L_0x0426
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            r1 = r18
            r0.addMovement(r1)
            goto L_0x0428
        L_0x0426:
            r1 = r18
        L_0x0428:
            r1.recycle()
            r0 = 1
            return r0
        L_0x042d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            C03332 r0 = this.mItemAnimatorRunner;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(this, r0);
            this.mPostedAnimatorRunner = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0067, code lost:
        if (r4.mHasStableIds != false) goto L_0x0069;
     */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x008b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void processAdapterUpdatesAndSetAnimationFlags() {
        /*
            r6 = this;
            boolean r0 = r6.mDataSetHasChangedAfterLayout
            r1 = 0
            if (r0 == 0) goto L_0x001f
            androidx.recyclerview.widget.AdapterHelper r0 = r6.mAdapterHelper
            java.util.Objects.requireNonNull(r0)
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r2 = r0.mPendingUpdates
            r0.recycleUpdateOpsAndClearList(r2)
            java.util.ArrayList<androidx.recyclerview.widget.AdapterHelper$UpdateOp> r2 = r0.mPostponedList
            r0.recycleUpdateOpsAndClearList(r2)
            r0.mExistingUpdateTypes = r1
            boolean r0 = r6.mDispatchItemsChangedEvent
            if (r0 == 0) goto L_0x001f
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r6.mLayout
            r0.onItemsChanged()
        L_0x001f:
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r0 = r6.mItemAnimator
            r2 = 1
            if (r0 == 0) goto L_0x002e
            androidx.recyclerview.widget.RecyclerView$LayoutManager r0 = r6.mLayout
            boolean r0 = r0.supportsPredictiveItemAnimations()
            if (r0 == 0) goto L_0x002e
            r0 = r2
            goto L_0x002f
        L_0x002e:
            r0 = r1
        L_0x002f:
            if (r0 == 0) goto L_0x0037
            androidx.recyclerview.widget.AdapterHelper r0 = r6.mAdapterHelper
            r0.preProcess()
            goto L_0x003c
        L_0x0037:
            androidx.recyclerview.widget.AdapterHelper r0 = r6.mAdapterHelper
            r0.consumeUpdatesInOnePass()
        L_0x003c:
            boolean r0 = r6.mItemsAddedOrRemoved
            if (r0 != 0) goto L_0x0047
            boolean r0 = r6.mItemsChanged
            if (r0 == 0) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            r0 = r1
            goto L_0x0048
        L_0x0047:
            r0 = r2
        L_0x0048:
            androidx.recyclerview.widget.RecyclerView$State r3 = r6.mState
            boolean r4 = r6.mFirstLayoutComplete
            if (r4 == 0) goto L_0x006b
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r4 = r6.mItemAnimator
            if (r4 == 0) goto L_0x006b
            boolean r4 = r6.mDataSetHasChangedAfterLayout
            if (r4 != 0) goto L_0x005e
            if (r0 != 0) goto L_0x005e
            androidx.recyclerview.widget.RecyclerView$LayoutManager r5 = r6.mLayout
            boolean r5 = r5.mRequestedSimpleAnimations
            if (r5 == 0) goto L_0x006b
        L_0x005e:
            if (r4 == 0) goto L_0x0069
            androidx.recyclerview.widget.RecyclerView$Adapter r4 = r6.mAdapter
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4.mHasStableIds
            if (r4 == 0) goto L_0x006b
        L_0x0069:
            r4 = r2
            goto L_0x006c
        L_0x006b:
            r4 = r1
        L_0x006c:
            r3.mRunSimpleAnimations = r4
            androidx.recyclerview.widget.RecyclerView$State r3 = r6.mState
            boolean r4 = r3.mRunSimpleAnimations
            if (r4 == 0) goto L_0x008c
            if (r0 == 0) goto L_0x008c
            boolean r0 = r6.mDataSetHasChangedAfterLayout
            if (r0 != 0) goto L_0x008c
            androidx.recyclerview.widget.RecyclerView$ItemAnimator r0 = r6.mItemAnimator
            if (r0 == 0) goto L_0x0088
            androidx.recyclerview.widget.RecyclerView$LayoutManager r6 = r6.mLayout
            boolean r6 = r6.supportsPredictiveItemAnimations()
            if (r6 == 0) goto L_0x0088
            r6 = r2
            goto L_0x0089
        L_0x0088:
            r6 = r1
        L_0x0089:
            if (r6 == 0) goto L_0x008c
            r1 = r2
        L_0x008c:
            r3.mRunPredictiveAnimations = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.processAdapterUpdatesAndSetAnimationFlags():void");
    }

    public final void processDataSetCompletelyChanged(boolean z) {
        this.mDispatchItemsChangedEvent = z | this.mDispatchItemsChangedEvent;
        this.mDataSetHasChangedAfterLayout = true;
        int unfilteredChildCount = this.mChildHelper.getUnfilteredChildCount();
        for (int i = 0; i < unfilteredChildCount; i++) {
            ViewHolder childViewHolderInt = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(i));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        Recycler recycler = this.mRecycler;
        Objects.requireNonNull(recycler);
        int size = recycler.mCachedViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = recycler.mCachedViews.get(i2);
            if (viewHolder != null) {
                viewHolder.addFlags(6);
                viewHolder.addChangePayload((Object) null);
            }
        }
        Adapter adapter = RecyclerView.this.mAdapter;
        if (adapter == null || !adapter.mHasStableIds) {
            recycler.recycleAndClearCachedViews();
        }
    }

    public final void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        boolean z = false;
        int i = (viewHolder.mFlags & -8193) | 0;
        viewHolder.mFlags = i;
        if (this.mState.mTrackOldChangeHolders) {
            if ((i & 2) != 0) {
                z = true;
            }
            if (z && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
                long changedHolderKey = getChangedHolderKey(viewHolder);
                ViewInfoStore viewInfoStore = this.mViewInfoStore;
                Objects.requireNonNull(viewInfoStore);
                viewInfoStore.mOldChangedHolders.put(changedHolderKey, viewHolder);
            }
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    public final void removeAndRecycleViews() {
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        Recycler recycler = this.mRecycler;
        Objects.requireNonNull(recycler);
        recycler.mAttachedScrap.clear();
        recycler.recycleAndClearCachedViews();
    }

    public final void removeItemDecoration(ItemDecoration itemDecoration) {
        boolean z;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            if (getOverScrollMode() == 2) {
                z = true;
            } else {
                z = false;
            }
            setWillNotDraw(z);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public final void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, view, view2) && view2 != null) {
            requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public final void requestChildOnScreen(View view, View view2) {
        View view3;
        boolean z;
        if (view2 != null) {
            view3 = view2;
        } else {
            view3 = view;
        }
        this.mTempRect.set(0, 0, view3.getWidth(), view3.getHeight());
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.mInsetsDirty) {
                Rect rect = layoutParams2.mDecorInsets;
                Rect rect2 = this.mTempRect;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            offsetRectIntoDescendantCoords(view, this.mTempRect);
        }
        LayoutManager layoutManager = this.mLayout;
        Rect rect3 = this.mTempRect;
        boolean z2 = !this.mFirstLayoutComplete;
        if (view2 == null) {
            z = true;
        } else {
            z = false;
        }
        layoutManager.requestChildRectangleOnScreen(this, view, rect3, z2, z);
    }

    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, z);
    }

    public final void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.mOnItemTouchListeners.size();
        for (int i = 0; i < size; i++) {
            this.mOnItemTouchListeners.get(i).onRequestDisallowInterceptTouchEvent(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public final void requestLayout() {
        if (this.mInterceptRequestLayoutDepth != 0 || this.mLayoutSuppressed) {
            this.mLayoutWasDefered = true;
        } else {
            super.requestLayout();
        }
    }

    public final void resetScroll() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        boolean z = false;
        getScrollingChildHelper().stopNestedScroll(0);
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mTopGlow;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            z |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mRightGlow;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            z |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            z |= this.mBottomGlow.isFinished();
        }
        if (z) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final void scrollBy(int i, int i2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutSuppressed) {
            boolean canScrollHorizontally = layoutManager.canScrollHorizontally();
            boolean canScrollVertically = this.mLayout.canScrollVertically();
            if (canScrollHorizontally || canScrollVertically) {
                if (!canScrollHorizontally) {
                    i = 0;
                }
                if (!canScrollVertically) {
                    i2 = 0;
                }
                scrollByInternal(i, i2, (MotionEvent) null, 0);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean scrollByInternal(int r18, int r19, android.view.MotionEvent r20, int r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r17.consumePendingUpdateOperations()
            androidx.recyclerview.widget.RecyclerView$Adapter r3 = r0.mAdapter
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x0023
            int[] r3 = r0.mReusableIntPair
            r3[r5] = r5
            r3[r4] = r5
            r0.scrollStep(r1, r2, r3)
            int[] r3 = r0.mReusableIntPair
            r6 = r3[r5]
            r3 = r3[r4]
            int r7 = r1 - r6
            int r8 = r2 - r3
            goto L_0x0027
        L_0x0023:
            r3 = r5
            r6 = r3
            r7 = r6
            r8 = r7
        L_0x0027:
            java.util.ArrayList<androidx.recyclerview.widget.RecyclerView$ItemDecoration> r9 = r0.mItemDecorations
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto L_0x0032
            r17.invalidate()
        L_0x0032:
            int[] r15 = r0.mReusableIntPair
            r15[r5] = r5
            r15[r4] = r5
            int[] r14 = r0.mScrollOffset
            androidx.core.view.NestedScrollingChildHelper r9 = r17.getScrollingChildHelper()
            java.util.Objects.requireNonNull(r9)
            r10 = r6
            r11 = r3
            r12 = r7
            r13 = r8
            r16 = r15
            r15 = r21
            r9.dispatchNestedScrollInternal(r10, r11, r12, r13, r14, r15, r16)
            int[] r9 = r0.mReusableIntPair
            r10 = r9[r5]
            int r7 = r7 - r10
            r10 = r9[r4]
            int r8 = r8 - r10
            r10 = r9[r5]
            if (r10 != 0) goto L_0x005f
            r9 = r9[r4]
            if (r9 == 0) goto L_0x005d
            goto L_0x005f
        L_0x005d:
            r9 = r5
            goto L_0x0060
        L_0x005f:
            r9 = r4
        L_0x0060:
            int r10 = r0.mLastTouchX
            int[] r11 = r0.mScrollOffset
            r12 = r11[r5]
            int r10 = r10 - r12
            r0.mLastTouchX = r10
            int r10 = r0.mLastTouchY
            r12 = r11[r4]
            int r10 = r10 - r12
            r0.mLastTouchY = r10
            int[] r10 = r0.mNestedOffsets
            r12 = r10[r5]
            r13 = r11[r5]
            int r12 = r12 + r13
            r10[r5] = r12
            r12 = r10[r4]
            r11 = r11[r4]
            int r12 = r12 + r11
            r10[r4] = r12
            int r10 = r17.getOverScrollMode()
            r11 = 2
            if (r10 == r11) goto L_0x0123
            if (r20 == 0) goto L_0x0120
            r10 = 8194(0x2002, float:1.1482E-41)
            int r11 = r20.getSource()
            r11 = r11 & r10
            if (r11 != r10) goto L_0x0094
            r10 = r4
            goto L_0x0095
        L_0x0094:
            r10 = r5
        L_0x0095:
            if (r10 != 0) goto L_0x0120
            float r10 = r20.getX()
            float r7 = (float) r7
            float r11 = r20.getY()
            float r8 = (float) r8
            r12 = 0
            int r13 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            r14 = 1065353216(0x3f800000, float:1.0)
            if (r13 >= 0) goto L_0x00c0
            r17.ensureLeftGlow()
            android.widget.EdgeEffect r13 = r0.mLeftGlow
            float r15 = -r7
            int r4 = r17.getWidth()
            float r4 = (float) r4
            float r15 = r15 / r4
            int r4 = r17.getHeight()
            float r4 = (float) r4
            float r11 = r11 / r4
            float r4 = r14 - r11
            androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r13, r15, r4)
            goto L_0x00d9
        L_0x00c0:
            int r4 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r4 <= 0) goto L_0x00db
            r17.ensureRightGlow()
            android.widget.EdgeEffect r4 = r0.mRightGlow
            int r13 = r17.getWidth()
            float r13 = (float) r13
            float r13 = r7 / r13
            int r15 = r17.getHeight()
            float r15 = (float) r15
            float r11 = r11 / r15
            androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r4, r13, r11)
        L_0x00d9:
            r4 = 1
            goto L_0x00dc
        L_0x00db:
            r4 = r5
        L_0x00dc:
            int r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r11 >= 0) goto L_0x00f6
            r17.ensureTopGlow()
            android.widget.EdgeEffect r4 = r0.mTopGlow
            float r11 = -r8
            int r13 = r17.getHeight()
            float r13 = (float) r13
            float r11 = r11 / r13
            int r13 = r17.getWidth()
            float r13 = (float) r13
            float r10 = r10 / r13
            androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r4, r11, r10)
            goto L_0x0110
        L_0x00f6:
            int r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r11 <= 0) goto L_0x0111
            r17.ensureBottomGlow()
            android.widget.EdgeEffect r4 = r0.mBottomGlow
            int r11 = r17.getHeight()
            float r11 = (float) r11
            float r11 = r8 / r11
            int r13 = r17.getWidth()
            float r13 = (float) r13
            float r10 = r10 / r13
            float r14 = r14 - r10
            androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r4, r11, r14)
        L_0x0110:
            r4 = 1
        L_0x0111:
            if (r4 != 0) goto L_0x011b
            int r4 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r4 != 0) goto L_0x011b
            int r4 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r4 == 0) goto L_0x0120
        L_0x011b:
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r17)
        L_0x0120:
            r17.considerReleasingGlowsOnScroll(r18, r19)
        L_0x0123:
            if (r6 != 0) goto L_0x0127
            if (r3 == 0) goto L_0x012a
        L_0x0127:
            r0.dispatchOnScrolled(r6, r3)
        L_0x012a:
            boolean r1 = r17.awakenScrollBars()
            if (r1 != 0) goto L_0x0133
            r17.invalidate()
        L_0x0133:
            if (r9 != 0) goto L_0x013c
            if (r6 != 0) goto L_0x013c
            if (r3 == 0) goto L_0x013a
            goto L_0x013c
        L_0x013a:
            r4 = r5
            goto L_0x013d
        L_0x013c:
            r4 = 1
        L_0x013d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.scrollByInternal(int, int, android.view.MotionEvent, int):boolean");
    }

    public final void scrollTo(int i, int i2) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int i) {
        if (!this.mLayoutSuppressed) {
            stopScroll();
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager == null) {
                Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            layoutManager.scrollToPosition(i);
            awakenScrollBars();
        }
    }

    public final void setClipToPadding(boolean z) {
        if (z != this.mClipToPadding) {
            this.mBottomGlow = null;
            this.mTopGlow = null;
            this.mRightGlow = null;
            this.mLeftGlow = null;
        }
        this.mClipToPadding = z;
        super.setClipToPadding(z);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager != this.mLayout) {
            stopScroll();
            if (this.mLayout != null) {
                ItemAnimator itemAnimator = this.mItemAnimator;
                if (itemAnimator != null) {
                    itemAnimator.endAnimations();
                }
                this.mLayout.removeAndRecycleAllViews(this.mRecycler);
                this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
                Recycler recycler = this.mRecycler;
                Objects.requireNonNull(recycler);
                recycler.mAttachedScrap.clear();
                recycler.recycleAndClearCachedViews();
                if (this.mIsAttached) {
                    LayoutManager layoutManager2 = this.mLayout;
                    Objects.requireNonNull(layoutManager2);
                    layoutManager2.mIsAttachedToWindow = false;
                    layoutManager2.onDetachedFromWindow(this);
                }
                this.mLayout.setRecyclerView((RecyclerView) null);
                this.mLayout = null;
            } else {
                Recycler recycler2 = this.mRecycler;
                Objects.requireNonNull(recycler2);
                recycler2.mAttachedScrap.clear();
                recycler2.recycleAndClearCachedViews();
            }
            ChildHelper childHelper = this.mChildHelper;
            Objects.requireNonNull(childHelper);
            childHelper.mBucket.reset();
            int size = childHelper.mHiddenViews.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                C03365 r3 = (C03365) childHelper.mCallback;
                Objects.requireNonNull(r3);
                ViewHolder childViewHolderInt = getChildViewHolderInt((View) childHelper.mHiddenViews.get(size));
                if (childViewHolderInt != null) {
                    RecyclerView.this.setChildImportantForAccessibilityInternal(childViewHolderInt, childViewHolderInt.mWasImportantForAccessibilityBeforeHidden);
                    childViewHolderInt.mWasImportantForAccessibilityBeforeHidden = 0;
                }
                childHelper.mHiddenViews.remove(size);
            }
            C03365 r0 = (C03365) childHelper.mCallback;
            Objects.requireNonNull(r0);
            int childCount = r0.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = RecyclerView.this.getChildAt(i);
                RecyclerView.this.dispatchChildDetached(childAt);
                childAt.clearAnimation();
            }
            RecyclerView.this.removeAllViews();
            this.mLayout = layoutManager;
            if (layoutManager != null) {
                if (layoutManager.mRecyclerView == null) {
                    layoutManager.setRecyclerView(this);
                    if (this.mIsAttached) {
                        LayoutManager layoutManager3 = this.mLayout;
                        Objects.requireNonNull(layoutManager3);
                        layoutManager3.mIsAttachedToWindow = true;
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("LayoutManager ");
                    sb.append(layoutManager);
                    sb.append(" is already attached to a RecyclerView:");
                    throw new IllegalArgumentException(ChildHelper$$ExternalSyntheticOutline0.m18m(layoutManager.mRecyclerView, sb));
                }
            }
            this.mRecycler.updateViewCacheSize();
            requestLayout();
        }
    }

    @Deprecated
    public final void setLayoutTransition(LayoutTransition layoutTransition) {
        if (layoutTransition == null) {
            super.setLayoutTransition((LayoutTransition) null);
            return;
        }
        throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
    }

    /* access modifiers changed from: package-private */
    public final void setScrollState(int i) {
        SmoothScroller smoothScroller;
        if (i != this.mScrollState) {
            this.mScrollState = i;
            if (i != 2) {
                ViewFlinger viewFlinger = this.mViewFlinger;
                Objects.requireNonNull(viewFlinger);
                RecyclerView.this.removeCallbacks(viewFlinger);
                viewFlinger.mOverScroller.abortAnimation();
                LayoutManager layoutManager = this.mLayout;
                if (!(layoutManager == null || (smoothScroller = layoutManager.mSmoothScroller) == null)) {
                    smoothScroller.stop();
                }
            }
            LayoutManager layoutManager2 = this.mLayout;
            if (layoutManager2 != null) {
                layoutManager2.onScrollStateChanged(i);
            }
            ArrayList arrayList = this.mScrollListeners;
            if (arrayList != null) {
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        ((OnScrollListener) this.mScrollListeners.get(size)).onScrollStateChanged(this, i);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public final void smoothScrollBy$1(int i, int i2, boolean z) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutSuppressed) {
            int i3 = 0;
            if (!layoutManager.canScrollHorizontally()) {
                i = 0;
            }
            if (!this.mLayout.canScrollVertically()) {
                i2 = 0;
            }
            if (i != 0 || i2 != 0) {
                if (z) {
                    if (i != 0) {
                        i3 = 1;
                    }
                    if (i2 != 0) {
                        i3 |= 2;
                    }
                    getScrollingChildHelper().startNestedScroll(i3, 1);
                }
                this.mViewFlinger.smoothScrollBy(i, i2, Integer.MIN_VALUE, (Interpolator) null);
            }
        }
    }

    public void smoothScrollToPosition(int i) {
        if (!this.mLayoutSuppressed) {
            LayoutManager layoutManager = this.mLayout;
            if (layoutManager == null) {
                Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            } else {
                layoutManager.smoothScrollToPosition(this, i);
            }
        }
    }

    public final void startInterceptRequestLayout() {
        int i = this.mInterceptRequestLayoutDepth + 1;
        this.mInterceptRequestLayoutDepth = i;
        if (i == 1 && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
    }

    public final void stopInterceptRequestLayout(boolean z) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!z && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (z && this.mLayoutWasDefered && !this.mLayoutSuppressed && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutSuppressed) {
                this.mLayoutWasDefered = false;
            }
        }
        this.mInterceptRequestLayoutDepth--;
    }

    public final void suppressLayout(boolean z) {
        if (z != this.mLayoutSuppressed) {
            assertNotInLayoutOrScroll("Do not suppressLayout in layout or scroll");
            if (!z) {
                this.mLayoutSuppressed = false;
                if (!(!this.mLayoutWasDefered || this.mLayout == null || this.mAdapter == null)) {
                    requestLayout();
                }
                this.mLayoutWasDefered = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
            this.mLayoutSuppressed = true;
            this.mIgnoreMotionEventTillDown = true;
            stopScroll();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ClassLoader classLoader;
        Constructor<? extends U> constructor;
        NoSuchMethodException noSuchMethodException;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        int i2 = i;
        this.mObserver = new RecyclerViewDataObserver();
        this.mRecycler = new Recycler();
        this.mViewInfoStore = new ViewInfoStore();
        this.mUpdateChildViewsRunnable = new Runnable() {
            public final void run() {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.mFirstLayoutComplete && !recyclerView.isLayoutRequested()) {
                    RecyclerView recyclerView2 = RecyclerView.this;
                    if (!recyclerView2.mIsAttached) {
                        recyclerView2.requestLayout();
                    } else if (recyclerView2.mLayoutSuppressed) {
                        recyclerView2.mLayoutWasDefered = true;
                    } else {
                        recyclerView2.consumePendingUpdateOperations();
                    }
                }
            }
        };
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mRecyclerListeners = new ArrayList();
        this.mItemDecorations = new ArrayList<>();
        this.mOnItemTouchListeners = new ArrayList<>();
        this.mInterceptRequestLayoutDepth = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mEdgeEffectFactory = sDefaultEdgeEffectFactory;
        this.mItemAnimator = new DefaultItemAnimator();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScaledHorizontalScrollFactor = Float.MIN_VALUE;
        this.mScaledVerticalScrollFactor = Float.MIN_VALUE;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new ViewFlinger();
        this.mPrefetchRegistry = new GapWorker.LayoutPrefetchRegistryImpl();
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mNestedOffsets = new int[2];
        this.mReusableIntPair = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() {
            public final void run() {
                ItemAnimator itemAnimator = RecyclerView.this.mItemAnimator;
                if (itemAnimator != null) {
                    itemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mLastAutoMeasureNonExactMeasuredWidth = 0;
        this.mLastAutoMeasureNonExactMeasuredHeight = 0;
        this.mViewInfoProcessCallback = new Object() {
            /* JADX WARNING: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:9:0x002f  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void processAppeared(androidx.recyclerview.widget.RecyclerView.ViewHolder r8, androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo r9, androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo r10) {
                /*
                    r7 = this;
                    androidx.recyclerview.widget.RecyclerView r7 = androidx.recyclerview.widget.RecyclerView.this
                    java.util.Objects.requireNonNull(r7)
                    r0 = 0
                    r8.setIsRecyclable(r0)
                    androidx.recyclerview.widget.RecyclerView$ItemAnimator r0 = r7.mItemAnimator
                    r1 = r0
                    androidx.recyclerview.widget.SimpleItemAnimator r1 = (androidx.recyclerview.widget.SimpleItemAnimator) r1
                    if (r9 == 0) goto L_0x0029
                    java.util.Objects.requireNonNull(r1)
                    int r3 = r9.left
                    int r5 = r10.left
                    if (r3 != r5) goto L_0x001f
                    int r0 = r9.top
                    int r2 = r10.top
                    if (r0 == r2) goto L_0x0029
                L_0x001f:
                    int r4 = r9.top
                    int r6 = r10.top
                    r2 = r8
                    boolean r8 = r1.animateMove(r2, r3, r4, r5, r6)
                    goto L_0x002d
                L_0x0029:
                    r1.animateAdd(r8)
                    r8 = 1
                L_0x002d:
                    if (r8 == 0) goto L_0x0032
                    r7.postAnimationRunner()
                L_0x0032:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.C03354.processAppeared(androidx.recyclerview.widget.RecyclerView$ViewHolder, androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo, androidx.recyclerview.widget.RecyclerView$ItemAnimator$ItemHolderInfo):void");
            }

            public final void processDisappeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                int i;
                int i2;
                boolean z;
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView recyclerView = RecyclerView.this;
                Objects.requireNonNull(recyclerView);
                recyclerView.addAnimatingView(viewHolder);
                viewHolder.setIsRecyclable(false);
                SimpleItemAnimator simpleItemAnimator = (SimpleItemAnimator) recyclerView.mItemAnimator;
                Objects.requireNonNull(simpleItemAnimator);
                int i3 = itemHolderInfo.left;
                int i4 = itemHolderInfo.top;
                View view = viewHolder.itemView;
                if (itemHolderInfo2 == null) {
                    i = view.getLeft();
                } else {
                    i = itemHolderInfo2.left;
                }
                int i5 = i;
                if (itemHolderInfo2 == null) {
                    i2 = view.getTop();
                } else {
                    i2 = itemHolderInfo2.top;
                }
                int i6 = i2;
                if (viewHolder.isRemoved() || (i3 == i5 && i4 == i6)) {
                    simpleItemAnimator.animateRemove(viewHolder);
                    z = true;
                } else {
                    view.layout(i5, i6, view.getWidth() + i5, view.getHeight() + i6);
                    z = simpleItemAnimator.animateMove(viewHolder, i3, i4, i5, i6);
                }
                if (z) {
                    recyclerView.postAnimationRunner();
                }
            }
        };
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
        this.mScaledVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        ItemAnimator itemAnimator = this.mItemAnimator;
        ItemAnimatorRestoreListener itemAnimatorRestoreListener = this.mItemAnimatorListener;
        Objects.requireNonNull(itemAnimator);
        itemAnimator.mListener = itemAnimatorRestoreListener;
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback() {
            public final void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
                int i = updateOp.cmd;
                if (i == 1) {
                    RecyclerView.this.mLayout.onItemsAdded(updateOp.positionStart, updateOp.itemCount);
                } else if (i == 2) {
                    RecyclerView.this.mLayout.onItemsRemoved(updateOp.positionStart, updateOp.itemCount);
                } else if (i == 4) {
                    RecyclerView recyclerView = RecyclerView.this;
                    recyclerView.mLayout.onItemsUpdated(recyclerView, updateOp.positionStart, updateOp.itemCount);
                } else if (i == 8) {
                    RecyclerView.this.mLayout.onItemsMoved(updateOp.positionStart, updateOp.itemCount);
                }
            }

            public final void markViewHoldersUpdated(int i, int i2, Object obj) {
                int i3;
                int i4;
                RecyclerView recyclerView = RecyclerView.this;
                Objects.requireNonNull(recyclerView);
                int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
                int i5 = i2 + i;
                for (int i6 = 0; i6 < unfilteredChildCount; i6++) {
                    View unfilteredChildAt = recyclerView.mChildHelper.getUnfilteredChildAt(i6);
                    ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(unfilteredChildAt);
                    if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && (i4 = childViewHolderInt.mPosition) >= i && i4 < i5) {
                        childViewHolderInt.addFlags(2);
                        childViewHolderInt.addChangePayload(obj);
                        ((LayoutParams) unfilteredChildAt.getLayoutParams()).mInsetsDirty = true;
                    }
                }
                Recycler recycler = recyclerView.mRecycler;
                Objects.requireNonNull(recycler);
                int size = recycler.mCachedViews.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        ViewHolder viewHolder = recycler.mCachedViews.get(size);
                        if (viewHolder != null && (i3 = viewHolder.mPosition) >= i && i3 < i5) {
                            viewHolder.addFlags(2);
                            recycler.recycleCachedViewAt(size);
                        }
                    } else {
                        RecyclerView.this.mItemsChanged = true;
                        return;
                    }
                }
            }

            public final void offsetPositionsForAdd(int i, int i2) {
                RecyclerView recyclerView = RecyclerView.this;
                Objects.requireNonNull(recyclerView);
                int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
                for (int i3 = 0; i3 < unfilteredChildCount; i3++) {
                    ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i3));
                    if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i) {
                        childViewHolderInt.offsetPosition(i2, false);
                        recyclerView.mState.mStructureChanged = true;
                    }
                }
                Recycler recycler = recyclerView.mRecycler;
                Objects.requireNonNull(recycler);
                int size = recycler.mCachedViews.size();
                for (int i4 = 0; i4 < size; i4++) {
                    ViewHolder viewHolder = recycler.mCachedViews.get(i4);
                    if (viewHolder != null && viewHolder.mPosition >= i) {
                        viewHolder.offsetPosition(i2, false);
                    }
                }
                recyclerView.requestLayout();
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            public final void offsetPositionsForMove(int i, int i2) {
                int i3;
                int i4;
                int i5;
                int i6;
                int i7;
                int i8;
                int i9;
                RecyclerView recyclerView = RecyclerView.this;
                Objects.requireNonNull(recyclerView);
                int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
                int i10 = -1;
                if (i < i2) {
                    i5 = i;
                    i4 = i2;
                    i3 = -1;
                } else {
                    i4 = i;
                    i5 = i2;
                    i3 = 1;
                }
                for (int i11 = 0; i11 < unfilteredChildCount; i11++) {
                    ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i11));
                    if (childViewHolderInt != null && (i9 = childViewHolderInt.mPosition) >= i5 && i9 <= i4) {
                        if (i9 == i) {
                            childViewHolderInt.offsetPosition(i2 - i, false);
                        } else {
                            childViewHolderInt.offsetPosition(i3, false);
                        }
                        recyclerView.mState.mStructureChanged = true;
                    }
                }
                Recycler recycler = recyclerView.mRecycler;
                Objects.requireNonNull(recycler);
                if (i < i2) {
                    i7 = i;
                    i6 = i2;
                } else {
                    i6 = i;
                    i7 = i2;
                    i10 = 1;
                }
                int size = recycler.mCachedViews.size();
                for (int i12 = 0; i12 < size; i12++) {
                    ViewHolder viewHolder = recycler.mCachedViews.get(i12);
                    if (viewHolder != null && (i8 = viewHolder.mPosition) >= i7 && i8 <= i6) {
                        if (i8 == i) {
                            viewHolder.offsetPosition(i2 - i, false);
                        } else {
                            viewHolder.offsetPosition(i10, false);
                        }
                    }
                }
                recyclerView.requestLayout();
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }
        });
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback() {
            public final int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            public final void removeViewAt(int i) {
                View childAt = RecyclerView.this.getChildAt(i);
                if (childAt != null) {
                    RecyclerView.this.dispatchChildDetached(childAt);
                    childAt.clearAnimation();
                }
                RecyclerView.this.removeViewAt(i);
            }
        });
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api26Impl.getImportantForAutofill(this) == 0) {
            ViewCompat.Api26Impl.setImportantForAutofill(this, 8);
        }
        if (ViewCompat.Api16Impl.getImportantForAccessibility(this) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate = new RecyclerViewAccessibilityDelegate(this);
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, recyclerViewAccessibilityDelegate);
        int[] iArr = R$styleable.RecyclerView;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet2, iArr, i2, 0);
        TypedArray typedArray = obtainStyledAttributes;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        String string = typedArray.getString(8);
        if (typedArray.getInt(2, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.mClipToPadding = typedArray.getBoolean(1, true);
        if (typedArray.getBoolean(3, false)) {
            initFastScroller((StateListDrawable) typedArray.getDrawable(6), typedArray.getDrawable(7), (StateListDrawable) typedArray.getDrawable(4), typedArray.getDrawable(5));
        }
        typedArray.recycle();
        if (string != null) {
            String trim = string.trim();
            if (!trim.isEmpty()) {
                if (trim.charAt(0) == '.') {
                    trim = context.getPackageName() + trim;
                } else if (!trim.contains(".")) {
                    trim = RecyclerView.class.getPackage().getName() + '.' + trim;
                }
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = Class.forName(trim, false, classLoader).asSubclass(LayoutManager.class);
                    Object[] objArr = null;
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        objArr = new Object[]{context2, attributeSet2, Integer.valueOf(i), 0};
                    } catch (NoSuchMethodException e) {
                        noSuchMethodException = e;
                        constructor = asSubclass.getConstructor(new Class[0]);
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((LayoutManager) constructor.newInstance(objArr));
                } catch (NoSuchMethodException e2) {
                    e2.initCause(noSuchMethodException);
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + trim, e2);
                } catch (ClassNotFoundException e3) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + trim, e3);
                } catch (InvocationTargetException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + trim, e4);
                } catch (InstantiationException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + trim, e5);
                } catch (IllegalAccessException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + trim, e6);
                } catch (ClassCastException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + trim, e7);
                }
            }
        }
        int[] iArr2 = NESTED_SCROLLING_ATTRS;
        TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet2, iArr2, i2, 0);
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr2, attributeSet, obtainStyledAttributes2, i, 0);
        boolean z = obtainStyledAttributes2.getBoolean(0, true);
        obtainStyledAttributes2.recycle();
        setNestedScrollingEnabled(z);
    }

    public static int getChildAdapterPosition(View view) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getAbsoluteAdapterPosition();
        }
        return -1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0064  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int releaseVerticalGlow(int r4, float r5) {
        /*
            r3 = this;
            int r0 = r3.getWidth()
            float r0 = (float) r0
            float r5 = r5 / r0
            float r4 = (float) r4
            int r0 = r3.getHeight()
            float r0 = (float) r0
            float r4 = r4 / r0
            android.widget.EdgeEffect r0 = r3.mTopGlow
            r1 = 0
            if (r0 == 0) goto L_0x0033
            float r0 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0033
            android.widget.EdgeEffect r0 = r3.mTopGlow
            float r4 = -r4
            float r4 = androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r0, r4, r5)
            float r4 = -r4
            android.widget.EdgeEffect r5 = r3.mTopGlow
            float r5 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r5)
            int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x0031
            android.widget.EdgeEffect r5 = r3.mTopGlow
            r5.onRelease()
        L_0x0031:
            r1 = r4
            goto L_0x0058
        L_0x0033:
            android.widget.EdgeEffect r0 = r3.mBottomGlow
            if (r0 == 0) goto L_0x0058
            float r0 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0058
            android.widget.EdgeEffect r0 = r3.mBottomGlow
            r2 = 1065353216(0x3f800000, float:1.0)
            float r2 = r2 - r5
            float r4 = androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r0, r4, r2)
            android.widget.EdgeEffect r5 = r3.mBottomGlow
            float r5 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r5)
            int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x0031
            android.widget.EdgeEffect r5 = r3.mBottomGlow
            r5.onRelease()
            goto L_0x0031
        L_0x0058:
            int r4 = r3.getHeight()
            float r4 = (float) r4
            float r1 = r1 * r4
            int r4 = java.lang.Math.round(r1)
            if (r4 == 0) goto L_0x0067
            r3.invalidate()
        L_0x0067:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.releaseVerticalGlow(int, float):int");
    }

    public final void assertNotInLayoutOrScroll(String str) {
        if (isComputingLayout()) {
            if (str == null) {
                throw new IllegalStateException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot call this method while RecyclerView is computing a layout or scrolling")));
            }
            throw new IllegalStateException(str);
        } else if (this.mDispatchScrollCounter > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, VendorAtomValue$$ExternalSyntheticOutline1.m1m(""))));
        }
    }

    public final void defaultOnMeasure(int i, int i2) {
        int paddingRight = getPaddingRight() + getPaddingLeft();
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setMeasuredDimension(LayoutManager.chooseSize(i, paddingRight, ViewCompat.Api16Impl.getMinimumWidth(this)), LayoutManager.chooseSize(i2, getPaddingBottom() + getPaddingTop(), ViewCompat.Api16Impl.getMinimumHeight(this)));
    }

    public final void dispatchChildDetached(View view) {
        getChildViewHolderInt(view);
        Adapter adapter = this.mAdapter;
        ArrayList arrayList = this.mOnChildAttachStateListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size >= 0) {
                    ((OnChildAttachStateChangeListener) this.mOnChildAttachStateListeners.get(size)).onChildViewDetachedFromWindow(view);
                } else {
                    return;
                }
            }
        }
    }

    public final void dispatchLayoutStep2() {
        boolean z;
        boolean z2;
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        if (this.mPendingSavedState != null) {
            Adapter adapter = this.mAdapter;
            Objects.requireNonNull(adapter);
            int ordinal = adapter.mStateRestorationPolicy.ordinal();
            if (ordinal == 1 ? adapter.getItemCount() <= 0 : ordinal == 2) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                Parcelable parcelable = this.mPendingSavedState.mLayoutState;
                if (parcelable != null) {
                    this.mLayout.onRestoreInstanceState(parcelable);
                }
                this.mPendingSavedState = null;
            }
        }
        State state = this.mState;
        state.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, state);
        State state2 = this.mState;
        state2.mStructureChanged = false;
        if (!state2.mRunSimpleAnimations || this.mItemAnimator == null) {
            z = false;
        } else {
            z = true;
        }
        state2.mRunSimpleAnimations = z;
        state2.mLayoutStep = 4;
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
    }

    public final boolean dispatchNestedFling(float f, float f2, boolean z) {
        return getScrollingChildHelper().dispatchNestedFling(f, f2, z);
    }

    public final boolean dispatchNestedPreFling(float f, float f2) {
        return getScrollingChildHelper().dispatchNestedPreFling(f, f2);
    }

    public final boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        return scrollingChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2, 0);
    }

    public final boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        return scrollingChildHelper.dispatchNestedScrollInternal(i, i2, i3, i4, iArr, 0, (int[]) null);
    }

    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public void draw(Canvas canvas) {
        boolean z;
        int i;
        boolean z2;
        boolean z3;
        int i2;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        boolean z4 = false;
        for (int i3 = 0; i3 < size; i3++) {
            this.mItemDecorations.get(i3).onDrawOver(canvas, this);
        }
        EdgeEffect edgeEffect = this.mLeftGlow;
        boolean z5 = true;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z = false;
        } else {
            int save = canvas.save();
            if (this.mClipToPadding) {
                i2 = getPaddingBottom();
            } else {
                i2 = 0;
            }
            canvas.rotate(270.0f);
            canvas.translate((float) ((-getHeight()) + i2), 0.0f);
            EdgeEffect edgeEffect2 = this.mLeftGlow;
            if (edgeEffect2 == null || !edgeEffect2.draw(canvas)) {
                z = false;
            } else {
                z = true;
            }
            canvas.restoreToCount(save);
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int save2 = canvas.save();
            if (this.mClipToPadding) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.mTopGlow;
            if (edgeEffect4 == null || !edgeEffect4.draw(canvas)) {
                z3 = false;
            } else {
                z3 = true;
            }
            z |= z3;
            canvas.restoreToCount(save2);
        }
        EdgeEffect edgeEffect5 = this.mRightGlow;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            if (this.mClipToPadding) {
                i = getPaddingTop();
            } else {
                i = 0;
            }
            canvas.rotate(90.0f);
            canvas.translate((float) i, (float) (-width));
            EdgeEffect edgeEffect6 = this.mRightGlow;
            if (edgeEffect6 == null || !edgeEffect6.draw(canvas)) {
                z2 = false;
            } else {
                z2 = true;
            }
            z |= z2;
            canvas.restoreToCount(save3);
        }
        EdgeEffect edgeEffect7 = this.mBottomGlow;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.mClipToPadding) {
                canvas.translate((float) (getPaddingRight() + (-getWidth())), (float) (getPaddingBottom() + (-getHeight())));
            } else {
                canvas.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            EdgeEffect edgeEffect8 = this.mBottomGlow;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z4 = true;
            }
            z |= z4;
            canvas.restoreToCount(save4);
        }
        if (z || this.mItemAnimator == null || this.mItemDecorations.size() <= 0 || !this.mItemAnimator.isRunning()) {
            z5 = z;
        }
        if (z5) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final boolean drawChild(Canvas canvas, View view, long j) {
        return super.drawChild(canvas, view, j);
    }

    public final View findContainingItemView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = (View) parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    public final boolean findInterceptingOnItemTouchListener(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int size = this.mOnItemTouchListeners.size();
        int i = 0;
        while (i < size) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(i);
            if (!onItemTouchListener.onInterceptTouchEvent$1(motionEvent) || action == 3) {
                i++;
            } else {
                this.mInterceptingOnItemTouchListener = onItemTouchListener;
                return true;
            }
        }
        return false;
    }

    public final int getAdapterPositionInRecyclerView(ViewHolder viewHolder) {
        boolean z;
        Objects.requireNonNull(viewHolder);
        if ((viewHolder.mFlags & 524) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z && viewHolder.isBound()) {
            AdapterHelper adapterHelper = this.mAdapterHelper;
            int i = viewHolder.mPosition;
            Objects.requireNonNull(adapterHelper);
            int size = adapterHelper.mPendingUpdates.size();
            for (int i2 = 0; i2 < size; i2++) {
                AdapterHelper.UpdateOp updateOp = adapterHelper.mPendingUpdates.get(i2);
                int i3 = updateOp.cmd;
                if (i3 != 1) {
                    if (i3 == 2) {
                        int i4 = updateOp.positionStart;
                        if (i4 <= i) {
                            int i5 = updateOp.itemCount;
                            if (i4 + i5 <= i) {
                                i -= i5;
                            }
                        } else {
                            continue;
                        }
                    } else if (i3 == 8) {
                        int i6 = updateOp.positionStart;
                        if (i6 == i) {
                            i = updateOp.itemCount;
                        } else {
                            if (i6 < i) {
                                i--;
                            }
                            if (updateOp.itemCount <= i) {
                                i++;
                            }
                        }
                    }
                } else if (updateOp.positionStart <= i) {
                    i += updateOp.itemCount;
                }
            }
            return i;
        }
        return -1;
    }

    public final ViewHolder getChildViewHolder(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return getChildViewHolderInt(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    public final Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        State state = this.mState;
        Objects.requireNonNull(state);
        if (state.mInPreLayout && (layoutParams.isItemChanged() || layoutParams.mViewHolder.isInvalid())) {
            return layoutParams.mDecorInsets;
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i).getItemOffsets(this.mTempRect, view, this, this.mState);
            int i2 = rect.left;
            Rect rect2 = this.mTempRect;
            rect.left = i2 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        layoutParams.mInsetsDirty = false;
        return rect;
    }

    public final boolean hasNestedScrollingParent() {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        if (scrollingChildHelper.getNestedScrollingParentForType(0) != null) {
            return true;
        }
        return false;
    }

    public final boolean isNestedScrollingEnabled() {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        return scrollingChildHelper.mIsNestedScrollingEnabled;
    }

    public final void onAttachedToWindow() {
        boolean z;
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || isLayoutRequested()) {
            z = false;
        } else {
            z = true;
        }
        this.mFirstLayoutComplete = z;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            Objects.requireNonNull(layoutManager);
            layoutManager.mIsAttachedToWindow = true;
        }
        this.mPostedAnimatorRunner = false;
        ThreadLocal<GapWorker> threadLocal = GapWorker.sGapWorker;
        GapWorker gapWorker = threadLocal.get();
        this.mGapWorker = gapWorker;
        if (gapWorker == null) {
            this.mGapWorker = new GapWorker();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Display display = ViewCompat.Api17Impl.getDisplay(this);
            float f = 60.0f;
            if (!isInEditMode() && display != null) {
                float refreshRate = display.getRefreshRate();
                if (refreshRate >= 30.0f) {
                    f = refreshRate;
                }
            }
            GapWorker gapWorker2 = this.mGapWorker;
            gapWorker2.mFrameIntervalNs = (long) (1.0E9f / f);
            threadLocal.set(gapWorker2);
        }
        GapWorker gapWorker3 = this.mGapWorker;
        Objects.requireNonNull(gapWorker3);
        gapWorker3.mRecyclerViews.add(this);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        stopScroll();
        this.mIsAttached = false;
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.mIsAttachedToWindow = false;
            layoutManager.onDetachedFromWindow(this);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        removeCallbacks(this.mItemAnimatorRunner);
        Objects.requireNonNull(this.mViewInfoStore);
        do {
        } while (ViewInfoStore.InfoRecord.sPool.acquire() != null);
        GapWorker gapWorker = this.mGapWorker;
        if (gapWorker != null) {
            gapWorker.mRecyclerViews.remove(this);
            this.mGapWorker = null;
        }
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i = 0; i < size; i++) {
            this.mItemDecorations.get(i).onDraw(canvas, this);
        }
    }

    public final void onPointerUp(MotionEvent motionEvent) {
        int i;
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            if (actionIndex == 0) {
                i = 1;
            } else {
                i = 0;
            }
            this.mScrollPointerId = motionEvent.getPointerId(i);
            int x = (int) (motionEvent.getX(i) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY(i) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            this.mBottomGlow = null;
            this.mTopGlow = null;
            this.mRightGlow = null;
            this.mLeftGlow = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0064  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int releaseHorizontalGlow(int r4, float r5) {
        /*
            r3 = this;
            int r0 = r3.getHeight()
            float r0 = (float) r0
            float r5 = r5 / r0
            float r4 = (float) r4
            int r0 = r3.getWidth()
            float r0 = (float) r0
            float r4 = r4 / r0
            android.widget.EdgeEffect r0 = r3.mLeftGlow
            r1 = 0
            if (r0 == 0) goto L_0x0036
            float r0 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0036
            android.widget.EdgeEffect r0 = r3.mLeftGlow
            float r4 = -r4
            r2 = 1065353216(0x3f800000, float:1.0)
            float r2 = r2 - r5
            float r4 = androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r0, r4, r2)
            float r4 = -r4
            android.widget.EdgeEffect r5 = r3.mLeftGlow
            float r5 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r5)
            int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x0034
            android.widget.EdgeEffect r5 = r3.mLeftGlow
            r5.onRelease()
        L_0x0034:
            r1 = r4
            goto L_0x0058
        L_0x0036:
            android.widget.EdgeEffect r0 = r3.mRightGlow
            if (r0 == 0) goto L_0x0058
            float r0 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r0)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0058
            android.widget.EdgeEffect r0 = r3.mRightGlow
            float r4 = androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r0, r4, r5)
            android.widget.EdgeEffect r5 = r3.mRightGlow
            float r5 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r5)
            int r5 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x0034
            android.widget.EdgeEffect r5 = r3.mRightGlow
            r5.onRelease()
            goto L_0x0034
        L_0x0058:
            int r4 = r3.getWidth()
            float r4 = (float) r4
            float r1 = r1 * r4
            int r4 = java.lang.Math.round(r1)
            if (r4 == 0) goto L_0x0067
            r3.invalidate()
        L_0x0067:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.releaseHorizontalGlow(int, float):int");
    }

    public final void removeDetachedView(View view, boolean z) {
        ViewHolder childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            if (childViewHolderInt.isTmpDetached()) {
                childViewHolderInt.mFlags &= -257;
            } else if (!childViewHolderInt.shouldIgnore()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                sb.append(childViewHolderInt);
                throw new IllegalArgumentException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, sb));
            }
        }
        view.clearAnimation();
        dispatchChildDetached(view);
        super.removeDetachedView(view, z);
    }

    public final void scrollStep(int i, int i2, int[] iArr) {
        int i3;
        int i4;
        ViewHolder viewHolder;
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        Trace.beginSection("RV Scroll");
        fillRemainingScrollValues(this.mState);
        if (i != 0) {
            i3 = this.mLayout.scrollHorizontallyBy(i, this.mRecycler, this.mState);
        } else {
            i3 = 0;
        }
        if (i2 != 0) {
            i4 = this.mLayout.scrollVerticallyBy(i2, this.mRecycler, this.mState);
        } else {
            i4 = 0;
        }
        Trace.endSection();
        int childCount = this.mChildHelper.getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = this.mChildHelper.getChildAt(i5);
            ViewHolder childViewHolder = getChildViewHolder(childAt);
            if (!(childViewHolder == null || (viewHolder = childViewHolder.mShadowingHolder) == null)) {
                View view = viewHolder.itemView;
                int left = childAt.getLeft();
                int top = childAt.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
        onExitLayoutOrScroll(true);
        stopInterceptRequestLayout(false);
        if (iArr != null) {
            iArr[0] = i3;
            iArr[1] = i4;
        }
    }

    public final void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        int i;
        int i2 = 0;
        if (isComputingLayout()) {
            if (accessibilityEvent != null) {
                i = accessibilityEvent.getContentChangeTypes();
            } else {
                i = 0;
            }
            if (i != 0) {
                i2 = i;
            }
            this.mEatenAccessibilityChangeFlags |= i2;
            i2 = 1;
        }
        if (i2 == 0) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int i) {
        if (isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = i;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        View view = viewHolder.itemView;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(view, i);
        return true;
    }

    public final void setNestedScrollingEnabled(boolean z) {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        if (scrollingChildHelper.mIsNestedScrollingEnabled) {
            View view = scrollingChildHelper.mView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.stopNestedScroll(view);
        }
        scrollingChildHelper.mIsNestedScrollingEnabled = z;
    }

    public final boolean startNestedScroll(int i) {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        return scrollingChildHelper.startNestedScroll(i, 0);
    }

    public final void stopNestedScroll() {
        NestedScrollingChildHelper scrollingChildHelper = getScrollingChildHelper();
        Objects.requireNonNull(scrollingChildHelper);
        scrollingChildHelper.stopNestedScroll(0);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException(ChildHelper$$ExternalSyntheticOutline0.m18m(this, VendorAtomValue$$ExternalSyntheticOutline1.m1m("RecyclerView has no LayoutManager")));
    }

    public final void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    public final void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy$1(i, i2);
    }

    public final boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public final boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public final boolean isLayoutSuppressed() {
        return this.mLayoutSuppressed;
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int i, int i2) {
        }

        public void onItemRangeInserted(int i, int i2) {
        }

        public void onItemRangeMoved(int i, int i2) {
        }

        public void onItemRangeRemoved(int i, int i2) {
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            onItemRangeChanged(i, i2);
        }
    }
}
