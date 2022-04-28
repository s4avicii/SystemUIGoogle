package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat$Api31Impl;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.WeakHashMap;

public class ViewPager extends ViewGroup {
    public static final C04241 COMPARATOR = new Comparator<ItemInfo>() {
        public final int compare(Object obj, Object obj2) {
            return ((ItemInfo) obj).position - ((ItemInfo) obj2).position;
        }
    };
    public static final int[] LAYOUT_ATTRS = {16842931};
    public static final C04252 sInterpolator = new Interpolator() {
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public int mActivePointerId = -1;
    public PagerAdapter mAdapter;
    public ArrayList mAdapterChangeListeners;
    public boolean mCalledSuper;
    public int mCloseEnough;
    public int mCurItem;
    public int mDecorChildCount;
    public int mDefaultGutterSize;
    public boolean mDragInGutterEnabled = true;
    public final C04263 mEndScrollRunnable = new Runnable() {
        public final void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    };
    public int mExpectedAdapterCount;
    public long mFakeDragBeginTime;
    public boolean mFakeDragging;
    public boolean mFirstLayout = true;
    public float mFirstOffset = -3.4028235E38f;
    public int mFlingDistance;
    public int mGutterSize;
    public boolean mInLayout;
    public float mInitialMotionX;
    public float mInitialMotionY;
    public OnPageChangeListener mInternalPageChangeListener;
    public boolean mIsBeingDragged;
    public boolean mIsScrollStarted;
    public boolean mIsUnableToDrag;
    public final ArrayList<ItemInfo> mItems = new ArrayList<>();
    public float mLastMotionX;
    public float mLastMotionY;
    public float mLastOffset = Float.MAX_VALUE;
    public EdgeEffect mLeftEdge;
    public int mMaximumVelocity;
    public int mMinimumVelocity;
    public PagerObserver mObserver;
    public int mOffscreenPageLimit = 1;
    public OnPageChangeListener mOnPageChangeListener;
    public ArrayList mOnPageChangeListeners;
    public boolean mPopulatePending;
    public Parcelable mRestoredAdapterState = null;
    public int mRestoredCurItem = -1;
    public EdgeEffect mRightEdge;
    public int mScrollState = 0;
    public Scroller mScroller;
    public boolean mScrollingCacheEnabled;
    public final ItemInfo mTempItem = new ItemInfo();
    public final Rect mTempRect = new Rect();
    public int mTouchSlop;
    public VelocityTracker mVelocityTracker;

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DecorView {
    }

    public static class ItemInfo {
        public Object object;
        public float offset;
        public int position;
        public boolean scrolling;
        public float widthFactor;
    }

    public class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        public MyAccessibilityDelegate() {
        }

        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            PagerAdapter pagerAdapter;
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.viewpager.widget.ViewPager");
            PagerAdapter pagerAdapter2 = ViewPager.this.mAdapter;
            boolean z = true;
            if (pagerAdapter2 == null || pagerAdapter2.getCount() <= 1) {
                z = false;
            }
            accessibilityEvent.setScrollable(z);
            if (accessibilityEvent.getEventType() == 4096 && (pagerAdapter = ViewPager.this.mAdapter) != null) {
                accessibilityEvent.setItemCount(pagerAdapter.getCount());
                accessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
                accessibilityEvent.setToIndex(ViewPager.this.mCurItem);
            }
        }

        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            boolean z;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName("androidx.viewpager.widget.ViewPager");
            PagerAdapter pagerAdapter = ViewPager.this.mAdapter;
            if (pagerAdapter == null || pagerAdapter.getCount() <= 1) {
                z = false;
            } else {
                z = true;
            }
            accessibilityNodeInfoCompat.setScrollable(z);
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }

        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (i != 4096) {
                if (i != 8192 || !ViewPager.this.canScrollHorizontally(-1)) {
                    return false;
                }
                ViewPager viewPager = ViewPager.this;
                viewPager.setCurrentItem(viewPager.mCurItem - 1);
                return true;
            } else if (!ViewPager.this.canScrollHorizontally(1)) {
                return false;
            } else {
                ViewPager viewPager2 = ViewPager.this;
                viewPager2.setCurrentItem(viewPager2.mCurItem + 1);
                return true;
            }
        }
    }

    public interface OnAdapterChangeListener {
        void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public class PagerObserver extends DataSetObserver {
        public PagerObserver() {
        }

        public final void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        public final void onInvalidated() {
            ViewPager.this.dataSetChanged();
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
        public Parcelable adapterState;
        public ClassLoader loader;
        public int position;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("FragmentPager.SavedState{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" position=");
            m.append(this.position);
            m.append("}");
            return m.toString();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
    }

    public ViewPager(Context context) {
        super(context);
        initViewPager(context);
    }

    public final void addTouchables(ArrayList<View> arrayList) {
        ItemInfo infoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public final ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    public final ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    public final void initViewPager(Context context) {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.Api16Impl.getImportantForAccessibility(this) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        }
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            public final Rect mTempRect = new Rect();

            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat onApplyWindowInsets = ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                Objects.requireNonNull(onApplyWindowInsets);
                if (onApplyWindowInsets.mImpl.isConsumed()) {
                    return onApplyWindowInsets;
                }
                Rect rect = this.mTempRect;
                rect.left = onApplyWindowInsets.getSystemWindowInsetLeft();
                rect.top = onApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = onApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = onApplyWindowInsets.getSystemWindowInsetBottom();
                int childCount = ViewPager.this.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    WindowInsetsCompat dispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), onApplyWindowInsets);
                    rect.left = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                WindowInsetsCompat.BuilderImpl30 builderImpl30 = new WindowInsetsCompat.BuilderImpl30(onApplyWindowInsets);
                builderImpl30.setSystemWindowInsets(Insets.m10of(rect.left, rect.top, rect.right, rect.bottom));
                return builderImpl30.build();
            }
        });
    }

    public void onMeasure(int i, int i2) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2;
        boolean z;
        int i3;
        setMeasuredDimension(View.getDefaultSize(0, i), View.getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i4 = 0;
        while (true) {
            boolean z2 = true;
            int i5 = 1073741824;
            if (i4 >= childCount) {
                break;
            }
            View childAt = getChildAt(i4);
            if (!(childAt.getVisibility() == 8 || (layoutParams2 = (LayoutParams) childAt.getLayoutParams()) == null || !layoutParams2.isDecor)) {
                int i6 = layoutParams2.gravity;
                int i7 = i6 & 7;
                int i8 = i6 & 112;
                if (i8 == 48 || i8 == 80) {
                    z = true;
                } else {
                    z = false;
                }
                if (!(i7 == 3 || i7 == 5)) {
                    z2 = false;
                }
                int i9 = Integer.MIN_VALUE;
                if (z) {
                    i3 = Integer.MIN_VALUE;
                    i9 = 1073741824;
                } else if (z2) {
                    i3 = 1073741824;
                } else {
                    i3 = Integer.MIN_VALUE;
                }
                int i10 = layoutParams2.width;
                if (i10 != -2) {
                    if (i10 == -1) {
                        i10 = paddingLeft;
                    }
                    i9 = 1073741824;
                } else {
                    i10 = paddingLeft;
                }
                int i11 = layoutParams2.height;
                if (i11 == -2) {
                    i11 = measuredHeight;
                    i5 = i3;
                } else if (i11 == -1) {
                    i11 = measuredHeight;
                }
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i10, i9), View.MeasureSpec.makeMeasureSpec(i11, i5));
                if (z) {
                    measuredHeight -= childAt.getMeasuredHeight();
                } else if (z2) {
                    paddingLeft -= childAt.getMeasuredWidth();
                }
            }
            i4++;
        }
        View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i12 = 0; i12 < childCount2; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (((float) paddingLeft) * layoutParams.widthFactor), 1073741824), makeMeasureSpec);
            }
        }
    }

    public final void populate() {
        populate(this.mCurItem);
    }

    public final boolean resetTouch() {
        this.mActivePointerId = -1;
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        if (!this.mLeftEdge.isFinished() || !this.mRightEdge.isFinished()) {
            return true;
        }
        return false;
    }

    public final void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, !this.mFirstLayout, false, 0);
    }

    public final void setOffscreenPageLimit(int i) {
        if (i < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + i + " too small; defaulting to " + 1);
            i = 1;
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i;
            populate();
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int gravity;
        public boolean isDecor;
        public boolean needsMeasure;
        public int position;
        public float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    public static boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        if (!z || !view.canScrollHorizontally(-i)) {
            return false;
        }
        return true;
    }

    public final ItemInfo addNewItem(int i, int i2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i;
        itemInfo.object = this.mAdapter.instantiateItem(this, i);
        Objects.requireNonNull(this.mAdapter);
        itemInfo.widthFactor = 1.0f;
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(i2, itemInfo);
        }
        return itemInfo;
    }

    public final boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
        this.mFakeDragBeginTime = uptimeMillis;
        return true;
    }

    public final boolean canScrollHorizontally(int i) {
        if (this.mAdapter == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        if (i < 0) {
            if (scrollX > ((int) (((float) clientWidth) * this.mFirstOffset))) {
                return true;
            }
            return false;
        } else if (i <= 0 || scrollX >= ((int) (((float) clientWidth) * this.mLastOffset))) {
            return false;
        } else {
            return true;
        }
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams) || !super.checkLayoutParams(layoutParams)) {
            return false;
        }
        return true;
    }

    public final void completeScroll(boolean z) {
        boolean z2;
        if (this.mScrollState == 2) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                if (!(scrollX == currX && scrollY == currY)) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        pageScrolled(currX);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z2 = true;
            }
        }
        if (!z2) {
            return;
        }
        if (z) {
            C04263 r8 = this.mEndScrollRunnable;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(this, r8);
            return;
        }
        this.mEndScrollRunnable.run();
    }

    public final void dataSetChanged() {
        boolean z;
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        if (this.mItems.size() >= (this.mOffscreenPageLimit * 2) + 1 || this.mItems.size() >= count) {
            z = false;
        } else {
            z = true;
        }
        int i = this.mCurItem;
        int i2 = 0;
        boolean z2 = false;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z2) {
                        Objects.requireNonNull(this.mAdapter);
                        z2 = true;
                    }
                    this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                    int i3 = this.mCurItem;
                    if (i3 == itemInfo.position) {
                        i = Math.max(0, Math.min(i3, -1 + count));
                    }
                } else {
                    int i4 = itemInfo.position;
                    if (i4 != itemPosition) {
                        if (i4 == this.mCurItem) {
                            i = itemPosition;
                        }
                        itemInfo.position = itemPosition;
                    }
                }
                z = true;
            }
            i2++;
        }
        if (z2) {
            Objects.requireNonNull(this.mAdapter);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z) {
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i5).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = 0.0f;
                }
            }
            setCurrentItemInternal(i, false, true, 0);
            requestLayout();
        }
    }

    public final void dispatchOnPageSelected(int i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i);
        }
        ArrayList arrayList = this.mOnPageChangeListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnPageChangeListener onPageChangeListener2 = (OnPageChangeListener) this.mOnPageChangeListeners.get(i2);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageSelected(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageSelected(i);
        }
    }

    public void endFakeDrag() {
        if (this.mFakeDragging) {
            if (this.mAdapter != null) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                this.mPopulatePending = true;
                int clientWidth = getClientWidth();
                int scrollX = getScrollX();
                ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.position, ((((float) scrollX) / ((float) clientWidth)) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, xVelocity, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, xVelocity);
            }
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.mVelocityTracker = null;
            }
            this.mFakeDragging = false;
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        } else if (this.mAdapter != null) {
            this.mLastMotionX += f;
            float scrollX = ((float) getScrollX()) - f;
            float clientWidth = (float) getClientWidth();
            float f2 = this.mFirstOffset * clientWidth;
            float f3 = this.mLastOffset * clientWidth;
            ItemInfo itemInfo = this.mItems.get(0);
            ArrayList<ItemInfo> arrayList = this.mItems;
            ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
            if (itemInfo.position != 0) {
                f2 = itemInfo.offset * clientWidth;
            }
            if (itemInfo2.position != this.mAdapter.getCount() - 1) {
                f3 = itemInfo2.offset * clientWidth;
            }
            if (scrollX < f2) {
                scrollX = f2;
            } else if (scrollX > f3) {
                scrollX = f3;
            }
            int i = (int) scrollX;
            this.mLastMotionX = (scrollX - ((float) i)) + this.mLastMotionX;
            scrollTo(i, getScrollY());
            pageScrolled(i);
            MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
            this.mVelocityTracker.addMovement(obtain);
            obtain.recycle();
        }
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left = viewGroup.getLeft() + rect.left;
            rect.right = viewGroup.getRight() + rect.right;
            rect.top = viewGroup.getTop() + rect.top;
            rect.bottom = viewGroup.getBottom() + rect.bottom;
            parent = viewGroup.getParent();
        }
        return rect;
    }

    public final void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            r18 = this;
            r0 = r18
            int r1 = r18.getChildCount()
            int r2 = r22 - r20
            int r3 = r23 - r21
            int r4 = r18.getPaddingLeft()
            int r5 = r18.getPaddingTop()
            int r6 = r18.getPaddingRight()
            int r7 = r18.getPaddingBottom()
            int r8 = r18.getScrollX()
            r10 = 0
            r11 = 0
        L_0x0020:
            r12 = 8
            if (r10 >= r1) goto L_0x00b6
            android.view.View r13 = r0.getChildAt(r10)
            int r14 = r13.getVisibility()
            if (r14 == r12) goto L_0x00b2
            android.view.ViewGroup$LayoutParams r12 = r13.getLayoutParams()
            androidx.viewpager.widget.ViewPager$LayoutParams r12 = (androidx.viewpager.widget.ViewPager.LayoutParams) r12
            boolean r14 = r12.isDecor
            if (r14 == 0) goto L_0x00b2
            int r12 = r12.gravity
            r14 = r12 & 7
            r12 = r12 & 112(0x70, float:1.57E-43)
            r15 = 1
            if (r14 == r15) goto L_0x005c
            r15 = 3
            if (r14 == r15) goto L_0x0056
            r15 = 5
            if (r14 == r15) goto L_0x0049
            r14 = r4
            goto L_0x006d
        L_0x0049:
            int r14 = r2 - r6
            int r15 = r13.getMeasuredWidth()
            int r14 = r14 - r15
            int r15 = r13.getMeasuredWidth()
            int r6 = r6 + r15
            goto L_0x0068
        L_0x0056:
            int r14 = r13.getMeasuredWidth()
            int r14 = r14 + r4
            goto L_0x006d
        L_0x005c:
            int r14 = r13.getMeasuredWidth()
            int r14 = r2 - r14
            int r14 = r14 / 2
            int r14 = java.lang.Math.max(r14, r4)
        L_0x0068:
            r17 = r14
            r14 = r4
            r4 = r17
        L_0x006d:
            r15 = 16
            if (r12 == r15) goto L_0x008e
            r15 = 48
            if (r12 == r15) goto L_0x0088
            r15 = 80
            if (r12 == r15) goto L_0x007b
            r12 = r5
            goto L_0x009f
        L_0x007b:
            int r12 = r3 - r7
            int r15 = r13.getMeasuredHeight()
            int r12 = r12 - r15
            int r15 = r13.getMeasuredHeight()
            int r7 = r7 + r15
            goto L_0x009a
        L_0x0088:
            int r12 = r13.getMeasuredHeight()
            int r12 = r12 + r5
            goto L_0x009f
        L_0x008e:
            int r12 = r13.getMeasuredHeight()
            int r12 = r3 - r12
            int r12 = r12 / 2
            int r12 = java.lang.Math.max(r12, r5)
        L_0x009a:
            r17 = r12
            r12 = r5
            r5 = r17
        L_0x009f:
            int r4 = r4 + r8
            int r15 = r13.getMeasuredWidth()
            int r15 = r15 + r4
            int r16 = r13.getMeasuredHeight()
            int r9 = r16 + r5
            r13.layout(r4, r5, r15, r9)
            int r11 = r11 + 1
            r5 = r12
            r4 = r14
        L_0x00b2:
            int r10 = r10 + 1
            goto L_0x0020
        L_0x00b6:
            int r2 = r2 - r4
            int r2 = r2 - r6
            r6 = 0
        L_0x00b9:
            if (r6 >= r1) goto L_0x0106
            android.view.View r8 = r0.getChildAt(r6)
            int r9 = r8.getVisibility()
            if (r9 == r12) goto L_0x0103
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.viewpager.widget.ViewPager$LayoutParams r9 = (androidx.viewpager.widget.ViewPager.LayoutParams) r9
            boolean r10 = r9.isDecor
            if (r10 != 0) goto L_0x0103
            androidx.viewpager.widget.ViewPager$ItemInfo r10 = r0.infoForChild(r8)
            if (r10 == 0) goto L_0x0103
            float r13 = (float) r2
            float r10 = r10.offset
            float r10 = r10 * r13
            int r10 = (int) r10
            int r10 = r10 + r4
            boolean r14 = r9.needsMeasure
            if (r14 == 0) goto L_0x00f6
            r14 = 0
            r9.needsMeasure = r14
            float r9 = r9.widthFactor
            float r13 = r13 * r9
            int r9 = (int) r13
            r13 = 1073741824(0x40000000, float:2.0)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r13)
            int r14 = r3 - r5
            int r14 = r14 - r7
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r13)
            r8.measure(r9, r13)
        L_0x00f6:
            int r9 = r8.getMeasuredWidth()
            int r9 = r9 + r10
            int r13 = r8.getMeasuredHeight()
            int r13 = r13 + r5
            r8.layout(r10, r5, r9, r13)
        L_0x0103:
            int r6 = r6 + 1
            goto L_0x00b9
        L_0x0106:
            r0.mDecorChildCount = r11
            boolean r1 = r0.mFirstLayout
            if (r1 == 0) goto L_0x0113
            int r1 = r0.mCurItem
            r2 = 0
            r0.scrollToItem(r1, r2, r2, r2)
            goto L_0x0114
        L_0x0113:
            r2 = 0
        L_0x0114:
            r0.mFirstLayout = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onLayout(boolean, int, int, int, int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0064  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onPageScrolled(int r13, float r14, int r15) {
        /*
            r12 = this;
            int r0 = r12.mDecorChildCount
            r1 = 1
            r2 = 0
            if (r0 <= 0) goto L_0x006b
            int r0 = r12.getScrollX()
            int r3 = r12.getPaddingLeft()
            int r4 = r12.getPaddingRight()
            int r5 = r12.getWidth()
            int r6 = r12.getChildCount()
            r7 = r2
        L_0x001b:
            if (r7 >= r6) goto L_0x006b
            android.view.View r8 = r12.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.viewpager.widget.ViewPager$LayoutParams r9 = (androidx.viewpager.widget.ViewPager.LayoutParams) r9
            boolean r10 = r9.isDecor
            if (r10 != 0) goto L_0x002c
            goto L_0x0068
        L_0x002c:
            int r9 = r9.gravity
            r9 = r9 & 7
            if (r9 == r1) goto L_0x004d
            r10 = 3
            if (r9 == r10) goto L_0x0047
            r10 = 5
            if (r9 == r10) goto L_0x003a
            r9 = r3
            goto L_0x005c
        L_0x003a:
            int r9 = r5 - r4
            int r10 = r8.getMeasuredWidth()
            int r9 = r9 - r10
            int r10 = r8.getMeasuredWidth()
            int r4 = r4 + r10
            goto L_0x0059
        L_0x0047:
            int r9 = r8.getWidth()
            int r9 = r9 + r3
            goto L_0x005c
        L_0x004d:
            int r9 = r8.getMeasuredWidth()
            int r9 = r5 - r9
            int r9 = r9 / 2
            int r9 = java.lang.Math.max(r9, r3)
        L_0x0059:
            r11 = r9
            r9 = r3
            r3 = r11
        L_0x005c:
            int r3 = r3 + r0
            int r10 = r8.getLeft()
            int r3 = r3 - r10
            if (r3 == 0) goto L_0x0067
            r8.offsetLeftAndRight(r3)
        L_0x0067:
            r3 = r9
        L_0x0068:
            int r7 = r7 + 1
            goto L_0x001b
        L_0x006b:
            androidx.viewpager.widget.ViewPager$OnPageChangeListener r0 = r12.mOnPageChangeListener
            if (r0 == 0) goto L_0x0072
            r0.onPageScrolled(r13, r14, r15)
        L_0x0072:
            java.util.ArrayList r0 = r12.mOnPageChangeListeners
            if (r0 == 0) goto L_0x008c
            int r0 = r0.size()
        L_0x007a:
            if (r2 >= r0) goto L_0x008c
            java.util.ArrayList r3 = r12.mOnPageChangeListeners
            java.lang.Object r3 = r3.get(r2)
            androidx.viewpager.widget.ViewPager$OnPageChangeListener r3 = (androidx.viewpager.widget.ViewPager.OnPageChangeListener) r3
            if (r3 == 0) goto L_0x0089
            r3.onPageScrolled(r13, r14, r15)
        L_0x0089:
            int r2 = r2 + 1
            goto L_0x007a
        L_0x008c:
            androidx.viewpager.widget.ViewPager$OnPageChangeListener r0 = r12.mInternalPageChangeListener
            if (r0 == 0) goto L_0x0093
            r0.onPageScrolled(r13, r14, r15)
        L_0x0093:
            r12.mCalledSuper = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onPageScrolled(int, float, int):void");
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        if (this.mAdapter != null) {
            setCurrentItemInternal(savedState.position, false, true, 0);
            return;
        }
        this.mRestoredCurItem = savedState.position;
        this.mRestoredAdapterState = savedState.adapterState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        PagerAdapter pagerAdapter;
        float f;
        if (this.mFakeDragging) {
            return true;
        }
        boolean z = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mScroller.abortAnimation();
            this.mPopulatePending = false;
            populate();
            float x = motionEvent.getX();
            this.mInitialMotionX = x;
            this.mLastMotionX = x;
            float y = motionEvent.getY();
            this.mInitialMotionY = y;
            this.mLastMotionY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (action != 1) {
            if (action == 2) {
                if (!this.mIsBeingDragged) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        z = resetTouch();
                    } else {
                        float x2 = motionEvent.getX(findPointerIndex);
                        float abs = Math.abs(x2 - this.mLastMotionX);
                        float y2 = motionEvent.getY(findPointerIndex);
                        float abs2 = Math.abs(y2 - this.mLastMotionY);
                        if (abs > ((float) this.mTouchSlop) && abs > abs2) {
                            this.mIsBeingDragged = true;
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                            float f2 = this.mInitialMotionX;
                            if (x2 - f2 > 0.0f) {
                                f = f2 + ((float) this.mTouchSlop);
                            } else {
                                f = f2 - ((float) this.mTouchSlop);
                            }
                            this.mLastMotionX = f;
                            this.mLastMotionY = y2;
                            setScrollState(1);
                            setScrollingCacheEnabled(true);
                            ViewParent parent2 = getParent();
                            if (parent2 != null) {
                                parent2.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                    z = false | performDrag(motionEvent.getX(findPointerIndex2), motionEvent.getY(findPointerIndex2));
                }
            } else if (action != 3) {
                if (action == 5) {
                    int actionIndex = motionEvent.getActionIndex();
                    this.mLastMotionX = motionEvent.getX(actionIndex);
                    this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                } else if (action == 6) {
                    onSecondaryPointerUp(motionEvent);
                    this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                }
            } else if (this.mIsBeingDragged) {
                scrollToItem(this.mCurItem, true, 0, false);
                z = resetTouch();
            }
        } else if (this.mIsBeingDragged) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
            int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            float f3 = (float) clientWidth;
            int i = infoForCurrentScrollPosition.position;
            int determineTargetPage = determineTargetPage(i, ((((float) scrollX) / f3) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + (((float) 0) / f3)), xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX));
            setCurrentItemInternal(determineTargetPage, true, true, xVelocity);
            z = resetTouch();
            if (determineTargetPage == i && z) {
                if (!this.mRightEdge.isFinished()) {
                    this.mRightEdge.onAbsorb(-xVelocity);
                } else if (!this.mLeftEdge.isFinished()) {
                    this.mLeftEdge.onAbsorb(xVelocity);
                }
            }
        }
        if (z) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
        return true;
    }

    public final boolean pageRight() {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || this.mCurItem >= pagerAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public final boolean pageScrolled(int i) {
        if (this.mItems.size() != 0) {
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            int clientWidth = getClientWidth();
            int i2 = clientWidth + 0;
            float f = (float) clientWidth;
            int i3 = infoForCurrentScrollPosition.position;
            float f2 = ((((float) i) / f) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + (((float) 0) / f));
            this.mCalledSuper = false;
            onPageScrolled(i3, f2, (int) (((float) i2) * f2));
            if (this.mCalledSuper) {
                return true;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        } else if (this.mFirstLayout) {
            return false;
        } else {
            this.mCalledSuper = false;
            onPageScrolled(0, 0.0f, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
    }

    public final boolean performDrag(float f, float f2) {
        float f3;
        boolean z;
        boolean z2;
        float f4 = this.mLastMotionX - f;
        this.mLastMotionX = f;
        float height = f2 / ((float) getHeight());
        float width = f4 / ((float) getWidth());
        if (EdgeEffectCompat$Api31Impl.getDistance(this.mLeftEdge) != 0.0f) {
            f3 = -EdgeEffectCompat$Api31Impl.onPullDistance(this.mLeftEdge, -width, 1.0f - height);
        } else if (EdgeEffectCompat$Api31Impl.getDistance(this.mRightEdge) != 0.0f) {
            f3 = EdgeEffectCompat$Api31Impl.onPullDistance(this.mRightEdge, width, height);
        } else {
            f3 = 0.0f;
        }
        float width2 = f3 * ((float) getWidth());
        float f5 = f4 - width2;
        boolean z3 = false;
        boolean z4 = true;
        if (width2 != 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (Math.abs(f5) < 1.0E-4f) {
            return z;
        }
        float scrollX = ((float) getScrollX()) + f5;
        float clientWidth = (float) getClientWidth();
        float f6 = this.mFirstOffset * clientWidth;
        float f7 = this.mLastOffset * clientWidth;
        ItemInfo itemInfo = this.mItems.get(0);
        ArrayList<ItemInfo> arrayList = this.mItems;
        ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
        if (itemInfo.position != 0) {
            f6 = itemInfo.offset * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f7 = itemInfo2.offset * clientWidth;
        } else {
            z3 = true;
        }
        if (scrollX < f6) {
            if (z2) {
                EdgeEffectCompat$Api31Impl.onPullDistance(this.mLeftEdge, (f6 - scrollX) / clientWidth, 1.0f - (f2 / ((float) getHeight())));
            } else {
                z4 = z;
            }
            z = z4;
            scrollX = f6;
        } else if (scrollX > f7) {
            if (z3) {
                EdgeEffectCompat$Api31Impl.onPullDistance(this.mRightEdge, (scrollX - f7) / clientWidth, f2 / ((float) getHeight()));
            } else {
                z4 = z;
            }
            z = z4;
            scrollX = f7;
        }
        int i = (int) scrollX;
        this.mLastMotionX = (scrollX - ((float) i)) + this.mLastMotionX;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        return z;
    }

    /* JADX WARNING: type inference failed for: r0v10, types: [android.view.ViewParent] */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0056, code lost:
        if (r6 == r7) goto L_0x005d;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void populate(int r15) {
        /*
            r14 = this;
            int r0 = r14.mCurItem
            if (r0 == r15) goto L_0x000b
            androidx.viewpager.widget.ViewPager$ItemInfo r0 = r14.infoForPosition(r0)
            r14.mCurItem = r15
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            androidx.viewpager.widget.PagerAdapter r15 = r14.mAdapter
            if (r15 != 0) goto L_0x0011
            return
        L_0x0011:
            boolean r15 = r14.mPopulatePending
            if (r15 == 0) goto L_0x0016
            return
        L_0x0016:
            android.os.IBinder r15 = r14.getWindowToken()
            if (r15 != 0) goto L_0x001d
            return
        L_0x001d:
            androidx.viewpager.widget.PagerAdapter r15 = r14.mAdapter
            java.util.Objects.requireNonNull(r15)
            int r15 = r14.mOffscreenPageLimit
            int r1 = r14.mCurItem
            int r1 = r1 - r15
            r2 = 0
            int r1 = java.lang.Math.max(r2, r1)
            androidx.viewpager.widget.PagerAdapter r3 = r14.mAdapter
            int r3 = r3.getCount()
            int r4 = r3 + -1
            int r5 = r14.mCurItem
            int r5 = r5 + r15
            int r15 = java.lang.Math.min(r4, r5)
            int r4 = r14.mExpectedAdapterCount
            if (r3 != r4) goto L_0x0336
            r4 = r2
        L_0x0040:
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r5 = r14.mItems
            int r5 = r5.size()
            if (r4 >= r5) goto L_0x005c
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r5 = r14.mItems
            java.lang.Object r5 = r5.get(r4)
            androidx.viewpager.widget.ViewPager$ItemInfo r5 = (androidx.viewpager.widget.ViewPager.ItemInfo) r5
            int r6 = r5.position
            int r7 = r14.mCurItem
            if (r6 < r7) goto L_0x0059
            if (r6 != r7) goto L_0x005c
            goto L_0x005d
        L_0x0059:
            int r4 = r4 + 1
            goto L_0x0040
        L_0x005c:
            r5 = 0
        L_0x005d:
            if (r5 != 0) goto L_0x0067
            if (r3 <= 0) goto L_0x0067
            int r5 = r14.mCurItem
            androidx.viewpager.widget.ViewPager$ItemInfo r5 = r14.addNewItem(r5, r4)
        L_0x0067:
            if (r5 == 0) goto L_0x02b6
            int r6 = r4 + -1
            if (r6 < 0) goto L_0x0076
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r6)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x0077
        L_0x0076:
            r7 = 0
        L_0x0077:
            int r8 = r14.getClientWidth()
            r9 = 1073741824(0x40000000, float:2.0)
            if (r8 > 0) goto L_0x0081
            r10 = 0
            goto L_0x008d
        L_0x0081:
            float r10 = r5.widthFactor
            float r10 = r9 - r10
            int r11 = r14.getPaddingLeft()
            float r11 = (float) r11
            float r12 = (float) r8
            float r11 = r11 / r12
            float r10 = r10 + r11
        L_0x008d:
            int r11 = r14.mCurItem
            int r11 = r11 + -1
            r12 = 0
        L_0x0092:
            if (r11 < 0) goto L_0x00f0
            int r13 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r13 < 0) goto L_0x00c0
            if (r11 >= r1) goto L_0x00c0
            if (r7 != 0) goto L_0x009d
            goto L_0x00f0
        L_0x009d:
            int r13 = r7.position
            if (r11 != r13) goto L_0x00ed
            boolean r13 = r7.scrolling
            if (r13 != 0) goto L_0x00ed
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r13 = r14.mItems
            r13.remove(r6)
            androidx.viewpager.widget.PagerAdapter r13 = r14.mAdapter
            java.lang.Object r7 = r7.object
            r13.destroyItem(r14, r11, r7)
            int r6 = r6 + -1
            int r4 = r4 + -1
            if (r6 < 0) goto L_0x00ec
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r6)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x00ed
        L_0x00c0:
            if (r7 == 0) goto L_0x00d6
            int r13 = r7.position
            if (r11 != r13) goto L_0x00d6
            float r7 = r7.widthFactor
            float r12 = r12 + r7
            int r6 = r6 + -1
            if (r6 < 0) goto L_0x00ec
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r6)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x00ed
        L_0x00d6:
            int r7 = r6 + 1
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = r14.addNewItem(r11, r7)
            float r7 = r7.widthFactor
            float r12 = r12 + r7
            int r4 = r4 + 1
            if (r6 < 0) goto L_0x00ec
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r6)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x00ed
        L_0x00ec:
            r7 = 0
        L_0x00ed:
            int r11 = r11 + -1
            goto L_0x0092
        L_0x00f0:
            float r1 = r5.widthFactor
            int r6 = r4 + 1
            int r7 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r7 >= 0) goto L_0x0186
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            int r7 = r7.size()
            if (r6 >= r7) goto L_0x0109
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r6)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x010a
        L_0x0109:
            r7 = 0
        L_0x010a:
            if (r8 > 0) goto L_0x010e
            r8 = 0
            goto L_0x0117
        L_0x010e:
            int r10 = r14.getPaddingRight()
            float r10 = (float) r10
            float r8 = (float) r8
            float r10 = r10 / r8
            float r8 = r10 + r9
        L_0x0117:
            int r9 = r14.mCurItem
            int r9 = r9 + 1
            r10 = r6
        L_0x011c:
            if (r9 >= r3) goto L_0x0186
            int r11 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r11 < 0) goto L_0x014c
            if (r9 <= r15) goto L_0x014c
            if (r7 != 0) goto L_0x0127
            goto L_0x0186
        L_0x0127:
            int r11 = r7.position
            if (r9 != r11) goto L_0x0183
            boolean r11 = r7.scrolling
            if (r11 != 0) goto L_0x0183
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r11 = r14.mItems
            r11.remove(r10)
            androidx.viewpager.widget.PagerAdapter r11 = r14.mAdapter
            java.lang.Object r7 = r7.object
            r11.destroyItem(r14, r9, r7)
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            int r7 = r7.size()
            if (r10 >= r7) goto L_0x0182
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r10)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x0183
        L_0x014c:
            if (r7 == 0) goto L_0x0168
            int r11 = r7.position
            if (r9 != r11) goto L_0x0168
            float r7 = r7.widthFactor
            float r1 = r1 + r7
            int r10 = r10 + 1
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            int r7 = r7.size()
            if (r10 >= r7) goto L_0x0182
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r10)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x0183
        L_0x0168:
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = r14.addNewItem(r9, r10)
            int r10 = r10 + 1
            float r7 = r7.widthFactor
            float r1 = r1 + r7
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            int r7 = r7.size()
            if (r10 >= r7) goto L_0x0182
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r10)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
            goto L_0x0183
        L_0x0182:
            r7 = 0
        L_0x0183:
            int r9 = r9 + 1
            goto L_0x011c
        L_0x0186:
            androidx.viewpager.widget.PagerAdapter r15 = r14.mAdapter
            int r15 = r15.getCount()
            int r1 = r14.getClientWidth()
            if (r1 <= 0) goto L_0x0196
            float r3 = (float) r2
            float r1 = (float) r1
            float r3 = r3 / r1
            goto L_0x0197
        L_0x0196:
            r3 = 0
        L_0x0197:
            r1 = 1065353216(0x3f800000, float:1.0)
            if (r0 == 0) goto L_0x0230
            int r7 = r0.position
            int r8 = r5.position
            if (r7 >= r8) goto L_0x01ed
            float r8 = r0.offset
            float r0 = r0.widthFactor
            float r8 = r8 + r0
            float r8 = r8 + r3
            r0 = r2
        L_0x01a8:
            int r7 = r7 + 1
            int r9 = r5.position
            if (r7 > r9) goto L_0x0230
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r9 = r14.mItems
            int r9 = r9.size()
            if (r0 >= r9) goto L_0x0230
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r9 = r14.mItems
            java.lang.Object r9 = r9.get(r0)
            androidx.viewpager.widget.ViewPager$ItemInfo r9 = (androidx.viewpager.widget.ViewPager.ItemInfo) r9
        L_0x01be:
            int r10 = r9.position
            if (r7 <= r10) goto L_0x01d7
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r10 = r14.mItems
            int r10 = r10.size()
            int r10 = r10 + -1
            if (r0 >= r10) goto L_0x01d7
            int r0 = r0 + 1
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r9 = r14.mItems
            java.lang.Object r9 = r9.get(r0)
            androidx.viewpager.widget.ViewPager$ItemInfo r9 = (androidx.viewpager.widget.ViewPager.ItemInfo) r9
            goto L_0x01be
        L_0x01d7:
            int r10 = r9.position
            if (r7 >= r10) goto L_0x01e6
            androidx.viewpager.widget.PagerAdapter r10 = r14.mAdapter
            java.util.Objects.requireNonNull(r10)
            float r10 = r1 + r3
            float r8 = r8 + r10
            int r7 = r7 + 1
            goto L_0x01d7
        L_0x01e6:
            r9.offset = r8
            float r9 = r9.widthFactor
            float r9 = r9 + r3
            float r8 = r8 + r9
            goto L_0x01a8
        L_0x01ed:
            if (r7 <= r8) goto L_0x0230
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r8 = r14.mItems
            int r8 = r8.size()
            int r8 = r8 + -1
            float r0 = r0.offset
        L_0x01f9:
            int r7 = r7 + -1
            int r9 = r5.position
            if (r7 < r9) goto L_0x0230
            if (r8 < 0) goto L_0x0230
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r9 = r14.mItems
            java.lang.Object r9 = r9.get(r8)
            androidx.viewpager.widget.ViewPager$ItemInfo r9 = (androidx.viewpager.widget.ViewPager.ItemInfo) r9
        L_0x0209:
            int r10 = r9.position
            if (r7 >= r10) goto L_0x021a
            if (r8 <= 0) goto L_0x021a
            int r8 = r8 + -1
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r9 = r14.mItems
            java.lang.Object r9 = r9.get(r8)
            androidx.viewpager.widget.ViewPager$ItemInfo r9 = (androidx.viewpager.widget.ViewPager.ItemInfo) r9
            goto L_0x0209
        L_0x021a:
            int r10 = r9.position
            if (r7 <= r10) goto L_0x0229
            androidx.viewpager.widget.PagerAdapter r10 = r14.mAdapter
            java.util.Objects.requireNonNull(r10)
            float r10 = r1 + r3
            float r0 = r0 - r10
            int r7 = r7 + -1
            goto L_0x021a
        L_0x0229:
            float r10 = r9.widthFactor
            float r10 = r10 + r3
            float r0 = r0 - r10
            r9.offset = r0
            goto L_0x01f9
        L_0x0230:
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r0 = r14.mItems
            int r0 = r0.size()
            float r7 = r5.offset
            int r8 = r5.position
            int r9 = r8 + -1
            if (r8 != 0) goto L_0x0240
            r10 = r7
            goto L_0x0243
        L_0x0240:
            r10 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
        L_0x0243:
            r14.mFirstOffset = r10
            int r15 = r15 + -1
            if (r8 != r15) goto L_0x024e
            float r8 = r5.widthFactor
            float r8 = r8 + r7
            float r8 = r8 - r1
            goto L_0x0251
        L_0x024e:
            r8 = 2139095039(0x7f7fffff, float:3.4028235E38)
        L_0x0251:
            r14.mLastOffset = r8
            int r4 = r4 + -1
        L_0x0255:
            if (r4 < 0) goto L_0x027d
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r8 = r14.mItems
            java.lang.Object r8 = r8.get(r4)
            androidx.viewpager.widget.ViewPager$ItemInfo r8 = (androidx.viewpager.widget.ViewPager.ItemInfo) r8
        L_0x025f:
            int r10 = r8.position
            if (r9 <= r10) goto L_0x026e
            androidx.viewpager.widget.PagerAdapter r10 = r14.mAdapter
            int r9 = r9 + -1
            java.util.Objects.requireNonNull(r10)
            float r10 = r1 + r3
            float r7 = r7 - r10
            goto L_0x025f
        L_0x026e:
            float r11 = r8.widthFactor
            float r11 = r11 + r3
            float r7 = r7 - r11
            r8.offset = r7
            if (r10 != 0) goto L_0x0278
            r14.mFirstOffset = r7
        L_0x0278:
            int r4 = r4 + -1
            int r9 = r9 + -1
            goto L_0x0255
        L_0x027d:
            float r4 = r5.offset
            float r7 = r5.widthFactor
            float r4 = r4 + r7
            float r4 = r4 + r3
            int r5 = r5.position
        L_0x0285:
            int r5 = r5 + 1
            if (r6 >= r0) goto L_0x02b1
            java.util.ArrayList<androidx.viewpager.widget.ViewPager$ItemInfo> r7 = r14.mItems
            java.lang.Object r7 = r7.get(r6)
            androidx.viewpager.widget.ViewPager$ItemInfo r7 = (androidx.viewpager.widget.ViewPager.ItemInfo) r7
        L_0x0291:
            int r8 = r7.position
            if (r5 >= r8) goto L_0x02a0
            androidx.viewpager.widget.PagerAdapter r8 = r14.mAdapter
            int r5 = r5 + 1
            java.util.Objects.requireNonNull(r8)
            float r8 = r1 + r3
            float r4 = r4 + r8
            goto L_0x0291
        L_0x02a0:
            if (r8 != r15) goto L_0x02a8
            float r8 = r7.widthFactor
            float r8 = r8 + r4
            float r8 = r8 - r1
            r14.mLastOffset = r8
        L_0x02a8:
            r7.offset = r4
            float r7 = r7.widthFactor
            float r7 = r7 + r3
            float r4 = r4 + r7
            int r6 = r6 + 1
            goto L_0x0285
        L_0x02b1:
            androidx.viewpager.widget.PagerAdapter r15 = r14.mAdapter
            java.util.Objects.requireNonNull(r15)
        L_0x02b6:
            androidx.viewpager.widget.PagerAdapter r15 = r14.mAdapter
            java.util.Objects.requireNonNull(r15)
            int r15 = r14.getChildCount()
            r0 = r2
        L_0x02c0:
            if (r0 >= r15) goto L_0x02eb
            android.view.View r1 = r14.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r3 = r1.getLayoutParams()
            androidx.viewpager.widget.ViewPager$LayoutParams r3 = (androidx.viewpager.widget.ViewPager.LayoutParams) r3
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.isDecor
            if (r4 != 0) goto L_0x02e8
            float r4 = r3.widthFactor
            r5 = 0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 != 0) goto L_0x02e8
            androidx.viewpager.widget.ViewPager$ItemInfo r1 = r14.infoForChild(r1)
            if (r1 == 0) goto L_0x02e8
            float r4 = r1.widthFactor
            r3.widthFactor = r4
            int r1 = r1.position
            r3.position = r1
        L_0x02e8:
            int r0 = r0 + 1
            goto L_0x02c0
        L_0x02eb:
            boolean r15 = r14.hasFocus()
            if (r15 == 0) goto L_0x0335
            android.view.View r15 = r14.findFocus()
            if (r15 == 0) goto L_0x030b
        L_0x02f7:
            android.view.ViewParent r0 = r15.getParent()
            if (r0 == r14) goto L_0x0306
            boolean r15 = r0 instanceof android.view.View
            if (r15 != 0) goto L_0x0302
            goto L_0x030b
        L_0x0302:
            r15 = r0
            android.view.View r15 = (android.view.View) r15
            goto L_0x02f7
        L_0x0306:
            androidx.viewpager.widget.ViewPager$ItemInfo r15 = r14.infoForChild(r15)
            goto L_0x030c
        L_0x030b:
            r15 = 0
        L_0x030c:
            if (r15 == 0) goto L_0x0314
            int r15 = r15.position
            int r0 = r14.mCurItem
            if (r15 == r0) goto L_0x0335
        L_0x0314:
            int r15 = r14.getChildCount()
            if (r2 >= r15) goto L_0x0335
            android.view.View r15 = r14.getChildAt(r2)
            androidx.viewpager.widget.ViewPager$ItemInfo r0 = r14.infoForChild(r15)
            if (r0 == 0) goto L_0x0332
            int r0 = r0.position
            int r1 = r14.mCurItem
            if (r0 != r1) goto L_0x0332
            r0 = 2
            boolean r15 = r15.requestFocus(r0)
            if (r15 == 0) goto L_0x0332
            goto L_0x0335
        L_0x0332:
            int r2 = r2 + 1
            goto L_0x0314
        L_0x0335:
            return
        L_0x0336:
            android.content.res.Resources r15 = r14.getResources()     // Catch:{ NotFoundException -> 0x0343 }
            int r0 = r14.getId()     // Catch:{ NotFoundException -> 0x0343 }
            java.lang.String r15 = r15.getResourceName(r0)     // Catch:{ NotFoundException -> 0x0343 }
            goto L_0x034b
        L_0x0343:
            int r15 = r14.getId()
            java.lang.String r15 = java.lang.Integer.toHexString(r15)
        L_0x034b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r2 = r14.mExpectedAdapterCount
            r1.append(r2)
            java.lang.String r2 = ", found: "
            r1.append(r2)
            r1.append(r3)
            java.lang.String r2 = " Pager id: "
            r1.append(r2)
            r1.append(r15)
            java.lang.String r15 = " Pager class: "
            r1.append(r15)
            java.lang.Class r15 = r14.getClass()
            r1.append(r15)
            java.lang.String r15 = " Problematic adapter: "
            r1.append(r15)
            androidx.viewpager.widget.PagerAdapter r14 = r14.mAdapter
            java.lang.Class r14 = r14.getClass()
            r1.append(r14)
            java.lang.String r14 = r1.toString()
            r0.<init>(r14)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.populate(int):void");
    }

    public final void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public final void setAdapter(PagerAdapter pagerAdapter) {
        PagerAdapter pagerAdapter2 = this.mAdapter;
        if (pagerAdapter2 != null) {
            synchronized (pagerAdapter2) {
                pagerAdapter2.mViewPagerObserver = null;
            }
            Objects.requireNonNull(this.mAdapter);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = this.mItems.get(i);
                this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
            }
            Objects.requireNonNull(this.mAdapter);
            this.mItems.clear();
            int i2 = 0;
            while (i2 < getChildCount()) {
                if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                    removeViewAt(i2);
                    i2--;
                }
                i2++;
            }
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter3 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (pagerAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            PagerAdapter pagerAdapter4 = this.mAdapter;
            PagerObserver pagerObserver = this.mObserver;
            Objects.requireNonNull(pagerAdapter4);
            synchronized (pagerAdapter4) {
                pagerAdapter4.mViewPagerObserver = pagerObserver;
            }
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                Objects.requireNonNull(this.mAdapter);
                setCurrentItemInternal(this.mRestoredCurItem, false, true, 0);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
            } else if (!z) {
                populate();
            } else {
                requestLayout();
            }
        }
        ArrayList arrayList = this.mAdapterChangeListeners;
        if (arrayList != null && !arrayList.isEmpty()) {
            int size = this.mAdapterChangeListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((OnAdapterChangeListener) this.mAdapterChangeListeners.get(i3)).onAdapterChanged(this, pagerAdapter3, pagerAdapter);
            }
        }
    }

    public final void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        PagerAdapter pagerAdapter = this.mAdapter;
        boolean z3 = false;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z2 || this.mCurItem != i || this.mItems.size() == 0) {
            if (i < 0) {
                i = 0;
            } else if (i >= this.mAdapter.getCount()) {
                i = this.mAdapter.getCount() - 1;
            }
            int i3 = this.mOffscreenPageLimit;
            int i4 = this.mCurItem;
            if (i > i4 + i3 || i < i4 - i3) {
                for (int i5 = 0; i5 < this.mItems.size(); i5++) {
                    this.mItems.get(i5).scrolling = true;
                }
            }
            if (this.mCurItem != i) {
                z3 = true;
            }
            if (this.mFirstLayout) {
                this.mCurItem = i;
                if (z3) {
                    dispatchOnPageSelected(i);
                }
                requestLayout();
                return;
            }
            populate(i);
            scrollToItem(i, z, i2, z3);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    public final void setScrollState(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrollStateChanged(i);
            }
            ArrayList arrayList = this.mOnPageChangeListeners;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    OnPageChangeListener onPageChangeListener2 = (OnPageChangeListener) this.mOnPageChangeListeners.get(i2);
                    if (onPageChangeListener2 != null) {
                        onPageChangeListener2.onPageScrollStateChanged(i);
                    }
                }
            }
            OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
            if (onPageChangeListener3 != null) {
                onPageChangeListener3.onPageScrollStateChanged(i);
            }
        }
    }

    public final void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ItemInfo infoForChild;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                    childAt.addFocusables(arrayList, i, i2);
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if ((i2 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) {
            arrayList.add(this);
        }
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        boolean z;
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        boolean z2 = layoutParams2.isDecor;
        if (view.getClass().getAnnotation(DecorView.class) != null) {
            z = true;
        } else {
            z = false;
        }
        boolean z3 = z2 | z;
        layoutParams2.isDecor = z3;
        if (!this.mInLayout) {
            super.addView(view, i, layoutParams);
        } else if (!z3) {
            layoutParams2.needsMeasure = true;
            addViewInLayout(view, i, layoutParams);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean arrowScroll(int r7) {
        /*
            r6 = this;
            android.view.View r0 = r6.findFocus()
            r1 = 0
            r2 = 1
            if (r0 != r6) goto L_0x0009
            goto L_0x0062
        L_0x0009:
            if (r0 == 0) goto L_0x0063
            android.view.ViewParent r3 = r0.getParent()
        L_0x000f:
            boolean r4 = r3 instanceof android.view.ViewGroup
            if (r4 == 0) goto L_0x001c
            if (r3 != r6) goto L_0x0017
            r3 = r2
            goto L_0x001d
        L_0x0017:
            android.view.ViewParent r3 = r3.getParent()
            goto L_0x000f
        L_0x001c:
            r3 = r1
        L_0x001d:
            if (r3 != 0) goto L_0x0063
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.Class r4 = r0.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            android.view.ViewParent r0 = r0.getParent()
        L_0x0033:
            boolean r4 = r0 instanceof android.view.ViewGroup
            if (r4 == 0) goto L_0x004c
            java.lang.String r4 = " => "
            r3.append(r4)
            java.lang.Class r4 = r0.getClass()
            java.lang.String r4 = r4.getSimpleName()
            r3.append(r4)
            android.view.ViewParent r0 = r0.getParent()
            goto L_0x0033
        L_0x004c:
            java.lang.String r0 = "arrowScroll tried to find focus based on non-child current focused view "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r3 = r3.toString()
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "ViewPager"
            android.util.Log.e(r3, r0)
        L_0x0062:
            r0 = 0
        L_0x0063:
            android.view.FocusFinder r3 = android.view.FocusFinder.getInstance()
            android.view.View r3 = r3.findNextFocus(r6, r0, r7)
            r4 = 66
            r5 = 17
            if (r3 == 0) goto L_0x00b8
            if (r3 == r0) goto L_0x00b8
            if (r7 != r5) goto L_0x0098
            android.graphics.Rect r4 = r6.mTempRect
            android.graphics.Rect r4 = r6.getChildRectInPagerCoordinates(r4, r3)
            int r4 = r4.left
            android.graphics.Rect r5 = r6.mTempRect
            android.graphics.Rect r5 = r6.getChildRectInPagerCoordinates(r5, r0)
            int r5 = r5.left
            if (r0 == 0) goto L_0x0092
            if (r4 < r5) goto L_0x0092
            int r0 = r6.mCurItem
            if (r0 <= 0) goto L_0x00d0
            int r0 = r0 - r2
            r6.setCurrentItem(r0, r2)
            goto L_0x00cf
        L_0x0092:
            boolean r0 = r3.requestFocus()
        L_0x0096:
            r1 = r0
            goto L_0x00d0
        L_0x0098:
            if (r7 != r4) goto L_0x00d0
            android.graphics.Rect r1 = r6.mTempRect
            android.graphics.Rect r1 = r6.getChildRectInPagerCoordinates(r1, r3)
            int r1 = r1.left
            android.graphics.Rect r2 = r6.mTempRect
            android.graphics.Rect r2 = r6.getChildRectInPagerCoordinates(r2, r0)
            int r2 = r2.left
            if (r0 == 0) goto L_0x00b3
            if (r1 > r2) goto L_0x00b3
            boolean r0 = r6.pageRight()
            goto L_0x0096
        L_0x00b3:
            boolean r0 = r3.requestFocus()
            goto L_0x0096
        L_0x00b8:
            if (r7 == r5) goto L_0x00c7
            if (r7 != r2) goto L_0x00bd
            goto L_0x00c7
        L_0x00bd:
            if (r7 == r4) goto L_0x00c2
            r0 = 2
            if (r7 != r0) goto L_0x00d0
        L_0x00c2:
            boolean r1 = r6.pageRight()
            goto L_0x00d0
        L_0x00c7:
            int r0 = r6.mCurItem
            if (r0 <= 0) goto L_0x00d0
            int r0 = r0 - r2
            r6.setCurrentItem(r0, r2)
        L_0x00cf:
            r1 = r2
        L_0x00d0:
            if (r1 == 0) goto L_0x00d9
            int r7 = android.view.SoundEffectConstants.getContantForFocusDirection(r7)
            r6.playSoundEffect(r7)
        L_0x00d9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.arrowScroll(int):boolean");
    }

    public final int determineTargetPage(int i, float f, int i2, int i3) {
        float f2;
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity || EdgeEffectCompat$Api31Impl.getDistance(this.mLeftEdge) != 0.0f || EdgeEffectCompat$Api31Impl.getDistance(this.mRightEdge) != 0.0f) {
            if (i >= this.mCurItem) {
                f2 = 0.4f;
            } else {
                f2 = 0.6f;
            }
            i += (int) (f + f2);
        } else if (i2 <= 0) {
            i++;
        }
        if (this.mItems.size() <= 0) {
            return i;
        }
        ArrayList<ItemInfo> arrayList = this.mItems;
        return Math.max(this.mItems.get(0).position, Math.min(i, arrayList.get(arrayList.size() - 1).position));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r6) {
        /*
            r5 = this;
            boolean r0 = super.dispatchKeyEvent(r6)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0064
            int r0 = r6.getAction()
            if (r0 != 0) goto L_0x005f
            int r0 = r6.getKeyCode()
            r3 = 21
            r4 = 2
            if (r0 == r3) goto L_0x0048
            r3 = 22
            if (r0 == r3) goto L_0x0036
            r3 = 61
            if (r0 == r3) goto L_0x0020
            goto L_0x005f
        L_0x0020:
            boolean r0 = r6.hasNoModifiers()
            if (r0 == 0) goto L_0x002b
            boolean r5 = r5.arrowScroll(r4)
            goto L_0x0060
        L_0x002b:
            boolean r6 = r6.hasModifiers(r1)
            if (r6 == 0) goto L_0x005f
            boolean r5 = r5.arrowScroll(r1)
            goto L_0x0060
        L_0x0036:
            boolean r6 = r6.hasModifiers(r4)
            if (r6 == 0) goto L_0x0041
            boolean r5 = r5.pageRight()
            goto L_0x0060
        L_0x0041:
            r6 = 66
            boolean r5 = r5.arrowScroll(r6)
            goto L_0x0060
        L_0x0048:
            boolean r6 = r6.hasModifiers(r4)
            if (r6 == 0) goto L_0x0058
            int r6 = r5.mCurItem
            if (r6 <= 0) goto L_0x005f
            int r6 = r6 - r1
            r5.setCurrentItem(r6, r1)
            r5 = r1
            goto L_0x0060
        L_0x0058:
            r6 = 17
            boolean r5 = r5.arrowScroll(r6)
            goto L_0x0060
        L_0x005f:
            r5 = r2
        L_0x0060:
            if (r5 == 0) goto L_0x0063
            goto L_0x0064
        L_0x0063:
            r1 = r2
        L_0x0064:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        ItemInfo infoForChild;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    public final void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean z = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (pagerAdapter = this.mAdapter) != null && pagerAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) (getPaddingTop() + (-height)), this.mFirstOffset * ((float) width));
                this.mLeftEdge.setSize(height, width);
                z = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.mRightEdge.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.mLastOffset + 1.0f)) * ((float) width2));
                this.mRightEdge.setSize(height2, width2);
                z |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (z) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public final ItemInfo infoForCurrentScrollPosition() {
        float f;
        float f2;
        int i;
        int clientWidth = getClientWidth();
        float f3 = 0.0f;
        if (clientWidth > 0) {
            f = ((float) getScrollX()) / ((float) clientWidth);
        } else {
            f = 0.0f;
        }
        if (clientWidth > 0) {
            f2 = ((float) 0) / ((float) clientWidth);
        } else {
            f2 = 0.0f;
        }
        ItemInfo itemInfo = null;
        int i2 = 0;
        int i3 = -1;
        boolean z = true;
        float f4 = 0.0f;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo2 = this.mItems.get(i2);
            if (!z && itemInfo2.position != (i = i3 + 1)) {
                itemInfo2 = this.mTempItem;
                itemInfo2.offset = f3 + f4 + f2;
                itemInfo2.position = i;
                Objects.requireNonNull(this.mAdapter);
                itemInfo2.widthFactor = 1.0f;
                i2--;
            }
            f3 = itemInfo2.offset;
            float f5 = itemInfo2.widthFactor + f3 + f2;
            if (!z && f < f3) {
                return itemInfo;
            }
            if (f < f5 || i2 == this.mItems.size() - 1) {
                return itemInfo2;
            }
            i3 = itemInfo2.position;
            f4 = itemInfo2.widthFactor;
            i2++;
            z = false;
            itemInfo = itemInfo2;
        }
        return itemInfo;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f;
        boolean z;
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            resetTouch();
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        if (action == 0) {
            float x = motionEvent.getX();
            this.mInitialMotionX = x;
            this.mLastMotionX = x;
            float y = motionEvent.getY();
            this.mInitialMotionY = y;
            this.mLastMotionY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mIsUnableToDrag = false;
            this.mIsScrollStarted = true;
            this.mScroller.computeScrollOffset();
            if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                this.mIsBeingDragged = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                setScrollState(1);
            } else if (EdgeEffectCompat$Api31Impl.getDistance(this.mLeftEdge) == 0.0f && EdgeEffectCompat$Api31Impl.getDistance(this.mRightEdge) == 0.0f) {
                completeScroll(false);
                this.mIsBeingDragged = false;
            } else {
                this.mIsBeingDragged = true;
                setScrollState(1);
                if (EdgeEffectCompat$Api31Impl.getDistance(this.mLeftEdge) != 0.0f) {
                    EdgeEffectCompat$Api31Impl.onPullDistance(this.mLeftEdge, 0.0f, 1.0f - (this.mLastMotionY / ((float) getHeight())));
                }
                if (EdgeEffectCompat$Api31Impl.getDistance(this.mRightEdge) != 0.0f) {
                    EdgeEffectCompat$Api31Impl.onPullDistance(this.mRightEdge, 0.0f, this.mLastMotionY / ((float) getHeight()));
                }
            }
        } else if (action == 2) {
            int i = this.mActivePointerId;
            if (i != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(i);
                float x2 = motionEvent.getX(findPointerIndex);
                float f2 = x2 - this.mLastMotionX;
                float abs = Math.abs(f2);
                float y2 = motionEvent.getY(findPointerIndex);
                float abs2 = Math.abs(y2 - this.mInitialMotionY);
                int i2 = (f2 > 0.0f ? 1 : (f2 == 0.0f ? 0 : -1));
                if (i2 != 0) {
                    float f3 = this.mLastMotionX;
                    if (!this.mDragInGutterEnabled && ((f3 < ((float) this.mGutterSize) && i2 > 0) || (f3 > ((float) (getWidth() - this.mGutterSize)) && f2 < 0.0f))) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z && canScroll(this, false, (int) f2, (int) x2, (int) y2)) {
                        this.mLastMotionX = x2;
                        this.mLastMotionY = y2;
                        this.mIsUnableToDrag = true;
                        return false;
                    }
                }
                float f4 = (float) this.mTouchSlop;
                if (abs > f4 && abs * 0.5f > abs2) {
                    this.mIsBeingDragged = true;
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(true);
                    }
                    setScrollState(1);
                    if (i2 > 0) {
                        f = this.mInitialMotionX + ((float) this.mTouchSlop);
                    } else {
                        f = this.mInitialMotionX - ((float) this.mTouchSlop);
                    }
                    this.mLastMotionX = f;
                    this.mLastMotionY = y2;
                    setScrollingCacheEnabled(true);
                } else if (abs2 > f4) {
                    this.mIsUnableToDrag = true;
                }
                if (this.mIsBeingDragged && performDrag(x2, y2)) {
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                }
            }
        } else if (action == 6) {
            onSecondaryPointerUp(motionEvent);
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        ItemInfo infoForChild;
        int childCount = getChildCount();
        int i4 = -1;
        if ((i & 2) != 0) {
            i4 = childCount;
            i3 = 0;
            i2 = 1;
        } else {
            i3 = childCount - 1;
            i2 = -1;
        }
        while (i3 != i4) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                return true;
            }
            i3 += i2;
        }
        return false;
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            Objects.requireNonNull(pagerAdapter);
            savedState.adapterState = null;
        }
        return savedState;
    }

    public final void onSecondaryPointerUp(MotionEvent motionEvent) {
        int i;
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            if (actionIndex == 0) {
                i = 1;
            } else {
                i = 0;
            }
            this.mLastMotionX = motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        float f;
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3) {
            return;
        }
        if (i3 <= 0 || this.mItems.isEmpty()) {
            ItemInfo infoForPosition = infoForPosition(this.mCurItem);
            if (infoForPosition != null) {
                f = Math.min(infoForPosition.offset, this.mLastOffset);
            } else {
                f = 0.0f;
            }
            int paddingLeft = (int) (f * ((float) ((i - getPaddingLeft()) - getPaddingRight())));
            if (paddingLeft != getScrollX()) {
                completeScroll(false);
                scrollTo(paddingLeft, getScrollY());
            }
        } else if (!this.mScroller.isFinished()) {
            this.mScroller.setFinalX(getClientWidth() * this.mCurItem);
        } else {
            scrollTo((int) ((((float) getScrollX()) / ((float) (((i3 - getPaddingLeft()) - getPaddingRight()) + 0))) * ((float) (((i - getPaddingLeft()) - getPaddingRight()) + 0))), getScrollY());
        }
    }

    public final void scrollToItem(int i, boolean z, int i2, boolean z2) {
        int i3;
        boolean z3;
        int i4;
        int i5;
        ItemInfo infoForPosition = infoForPosition(i);
        if (infoForPosition != null) {
            i3 = (int) (Math.max(this.mFirstOffset, Math.min(infoForPosition.offset, this.mLastOffset)) * ((float) getClientWidth()));
        } else {
            i3 = 0;
        }
        if (z) {
            if (getChildCount() == 0) {
                setScrollingCacheEnabled(false);
            } else {
                Scroller scroller = this.mScroller;
                if (scroller == null || scroller.isFinished()) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (z3) {
                    if (this.mIsScrollStarted) {
                        i4 = this.mScroller.getCurrX();
                    } else {
                        i4 = this.mScroller.getStartX();
                    }
                    this.mScroller.abortAnimation();
                    setScrollingCacheEnabled(false);
                } else {
                    i4 = getScrollX();
                }
                int i6 = i4;
                int scrollY = getScrollY();
                int i7 = i3 - i6;
                int i8 = 0 - scrollY;
                if (i7 == 0 && i8 == 0) {
                    completeScroll(false);
                    populate();
                    setScrollState(0);
                } else {
                    setScrollingCacheEnabled(true);
                    setScrollState(2);
                    int clientWidth = getClientWidth();
                    int i9 = clientWidth / 2;
                    float f = (float) clientWidth;
                    float f2 = (float) i9;
                    float sin = (((float) Math.sin((double) ((Math.min(1.0f, (((float) Math.abs(i7)) * 1.0f) / f) - 0.5f) * 0.47123894f))) * f2) + f2;
                    int abs = Math.abs(i2);
                    if (abs > 0) {
                        i5 = Math.round(Math.abs(sin / ((float) abs)) * 1000.0f) * 4;
                    } else {
                        Objects.requireNonNull(this.mAdapter);
                        i5 = (int) (((((float) Math.abs(i7)) / ((f * 1.0f) + ((float) 0))) + 1.0f) * 100.0f);
                    }
                    int min = Math.min(i5, 600);
                    this.mIsScrollStarted = false;
                    this.mScroller.startScroll(i6, scrollY, i7, i8, min);
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                }
            }
            if (z2) {
                dispatchOnPageSelected(i);
                return;
            }
            return;
        }
        if (z2) {
            dispatchOnPageSelected(i);
        }
        completeScroll(false);
        scrollTo(i3, 0);
        pageScrolled(i3);
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == null) {
            return true;
        }
        return false;
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, z, false, 0);
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViewPager(context);
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public final int getChildDrawingOrder(int i, int i2) {
        throw null;
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
    }
}
