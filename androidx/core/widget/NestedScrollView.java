package androidx.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public class NestedScrollView extends FrameLayout implements NestedScrollingParent3, NestedScrollingChild {
    public static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    public static final int[] SCROLLVIEW_STYLEABLE = {16843130};
    public int mActivePointerId;
    public final NestedScrollingChildHelper mChildHelper;
    public View mChildToScrollTo;
    public EdgeEffect mEdgeGlowBottom;
    public EdgeEffect mEdgeGlowTop;
    public boolean mFillViewport;
    public boolean mIsBeingDragged;
    public boolean mIsLaidOut;
    public boolean mIsLayoutDirty;
    public int mLastMotionY;
    public long mLastScroll;
    public int mLastScrollerY;
    public int mMaximumVelocity;
    public int mMinimumVelocity;
    public int mNestedYOffset;
    public OnScrollChangeListener mOnScrollChangeListener;
    public final NestedScrollingParentHelper mParentHelper;
    public SavedState mSavedState;
    public final int[] mScrollConsumed;
    public final int[] mScrollOffset;
    public OverScroller mScroller;
    public boolean mSmoothScrollingEnabled;
    public final Rect mTempRect;
    public int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public float mVerticalScrollFactor;

    public interface OnScrollChangeListener {
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int scrollPosition;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("HorizontalScrollView.SavedState{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" scrollPosition=");
            m.append(this.scrollPosition);
            m.append("}");
            return m.toString();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollPosition);
        }
    }

    public NestedScrollView(Context context) {
        this(context, (AttributeSet) null);
    }

    public static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        ViewParent parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewDescendantOf((View) parent, view2);
    }

    public final void addView(View view) {
        if (getChildCount() <= 0) {
            super.addView(view);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public final boolean fullScroll(int i) {
        boolean z;
        int childCount;
        if (i == 130) {
            z = true;
        } else {
            z = false;
        }
        int height = getHeight();
        Rect rect = this.mTempRect;
        rect.top = 0;
        rect.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            View childAt = getChildAt(childCount - 1);
            this.mTempRect.bottom = getPaddingBottom() + childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
            Rect rect2 = this.mTempRect;
            rect2.top = rect2.bottom - height;
        }
        Rect rect3 = this.mTempRect;
        return scrollAndFocus(i, rect3.top, rect3.bottom);
    }

    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        this.mChildHelper.dispatchNestedPreScroll(i, i2, iArr, (int[]) null, i3);
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        onNestedScrollInternal(i4, i5, iArr);
    }

    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        Objects.requireNonNull(nestedScrollingParentHelper);
        if (i2 == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = i;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = i;
        }
        this.mChildHelper.startNestedScroll(2, i2);
    }

    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        View view;
        if (i == 2) {
            i = 130;
        } else if (i == 1) {
            i = 33;
        }
        if (rect == null) {
            view = FocusFinder.getInstance().findNextFocus(this, (View) null, i);
        } else {
            view = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (view != null && !(!isWithinDeltaOfScreen(view, 0, getHeight()))) {
            return view.requestFocus(i, rect);
        }
        return false;
    }

    public final boolean onStartNestedScroll(View view, View view2, int i) {
        return onStartNestedScroll(view, view2, i, 0);
    }

    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return (i & 2) != 0;
    }

    public final void onStopNestedScroll(View view, int i) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        Objects.requireNonNull(nestedScrollingParentHelper);
        if (i == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = 0;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = 0;
        }
        stopNestedScroll(i);
    }

    public final void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void stopNestedScroll(int i) {
        this.mChildHelper.stopNestedScroll(i);
    }

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            boolean z;
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityEvent.setClassName(ScrollView.class.getName());
            if (nestedScrollView.getScrollRange() > 0) {
                z = true;
            } else {
                z = false;
            }
            accessibilityEvent.setScrollable(z);
            accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
            accessibilityEvent.setMaxScrollX(nestedScrollView.getScrollX());
            accessibilityEvent.setMaxScrollY(nestedScrollView.getScrollRange());
        }

        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int scrollRange;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (nestedScrollView.isEnabled() && (scrollRange = nestedScrollView.getScrollRange()) > 0) {
                accessibilityNodeInfoCompat.setScrollable(true);
                if (nestedScrollView.getScrollY() > 0) {
                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
                }
                if (nestedScrollView.getScrollY() < scrollRange) {
                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
                    accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
                }
            }
        }

        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            NestedScrollView nestedScrollView = (NestedScrollView) view;
            if (!nestedScrollView.isEnabled()) {
                return false;
            }
            if (i != 4096) {
                if (i == 8192 || i == 16908344) {
                    int max = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
                    if (max == nestedScrollView.getScrollY()) {
                        return false;
                    }
                    nestedScrollView.smoothScrollBy(0 - nestedScrollView.getScrollX(), max - nestedScrollView.getScrollY(), true);
                    return true;
                } else if (i != 16908346) {
                    return false;
                }
            }
            int min = Math.min(nestedScrollView.getScrollY() + ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange());
            if (min == nestedScrollView.getScrollY()) {
                return false;
            }
            nestedScrollView.smoothScrollBy(0 - nestedScrollView.getScrollX(), min - nestedScrollView.getScrollY(), true);
            return true;
        }
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.nestedScrollViewStyle);
    }

    public final void computeScroll() {
        if (!this.mScroller.isFinished()) {
            this.mScroller.computeScrollOffset();
            int currY = this.mScroller.getCurrY();
            int i = currY - this.mLastScrollerY;
            this.mLastScrollerY = currY;
            int[] iArr = this.mScrollConsumed;
            boolean z = false;
            iArr[1] = 0;
            this.mChildHelper.dispatchNestedPreScroll(0, i, iArr, (int[]) null, 1);
            int i2 = i - this.mScrollConsumed[1];
            int scrollRange = getScrollRange();
            if (i2 != 0) {
                int scrollY = getScrollY();
                overScrollByCompat(i2, getScrollX(), scrollY, scrollRange);
                int scrollY2 = getScrollY() - scrollY;
                int i3 = i2 - scrollY2;
                int[] iArr2 = this.mScrollConsumed;
                iArr2[1] = 0;
                int[] iArr3 = this.mScrollOffset;
                NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
                Objects.requireNonNull(nestedScrollingChildHelper);
                nestedScrollingChildHelper.dispatchNestedScrollInternal(0, scrollY2, 0, i3, iArr3, 1, iArr2);
                i2 = i3 - this.mScrollConsumed[1];
            }
            if (i2 != 0) {
                int overScrollMode = getOverScrollMode();
                if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) {
                    z = true;
                }
                if (z) {
                    if (i2 < 0) {
                        if (this.mEdgeGlowTop.isFinished()) {
                            this.mEdgeGlowTop.onAbsorb((int) this.mScroller.getCurrVelocity());
                        }
                    } else if (this.mEdgeGlowBottom.isFinished()) {
                        this.mEdgeGlowBottom.onAbsorb((int) this.mScroller.getCurrVelocity());
                    }
                }
                this.mScroller.abortAnimation();
                stopNestedScroll(1);
            }
            if (!this.mScroller.isFinished()) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                return;
            }
            stopNestedScroll(1);
        }
    }

    public final boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.mChildHelper.dispatchNestedFling(f, f2, z);
    }

    public final boolean dispatchNestedPreFling(float f, float f2) {
        return this.mChildHelper.dispatchNestedPreFling(f, f2);
    }

    public final boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return this.mChildHelper.dispatchNestedPreScroll(i, i2, iArr, iArr2, 0);
    }

    public final boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
        Objects.requireNonNull(nestedScrollingChildHelper);
        return nestedScrollingChildHelper.dispatchNestedScrollInternal(i, i2, i3, i4, iArr, 0, (int[]) null);
    }

    public final void doScrollY(int i) {
        if (i == 0) {
            return;
        }
        if (this.mSmoothScrollingEnabled) {
            smoothScrollBy(0, i, false);
        } else {
            scrollBy(0, i);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean executeKeyEvent(android.view.KeyEvent r7) {
        /*
            r6 = this;
            android.graphics.Rect r0 = r6.mTempRect
            r0.setEmpty()
            int r0 = r6.getChildCount()
            r1 = 1
            r2 = 0
            if (r0 <= 0) goto L_0x0033
            android.view.View r0 = r6.getChildAt(r2)
            android.view.ViewGroup$LayoutParams r3 = r0.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            int r0 = r0.getHeight()
            int r4 = r3.topMargin
            int r0 = r0 + r4
            int r3 = r3.bottomMargin
            int r0 = r0 + r3
            int r3 = r6.getHeight()
            int r4 = r6.getPaddingTop()
            int r3 = r3 - r4
            int r4 = r6.getPaddingBottom()
            int r3 = r3 - r4
            if (r0 <= r3) goto L_0x0033
            r0 = r1
            goto L_0x0034
        L_0x0033:
            r0 = r2
        L_0x0034:
            r3 = 130(0x82, float:1.82E-43)
            if (r0 != 0) goto L_0x0062
            boolean r0 = r6.isFocused()
            if (r0 == 0) goto L_0x0061
            int r7 = r7.getKeyCode()
            r0 = 4
            if (r7 == r0) goto L_0x0061
            android.view.View r7 = r6.findFocus()
            if (r7 != r6) goto L_0x004c
            r7 = 0
        L_0x004c:
            android.view.FocusFinder r0 = android.view.FocusFinder.getInstance()
            android.view.View r7 = r0.findNextFocus(r6, r7, r3)
            if (r7 == 0) goto L_0x005f
            if (r7 == r6) goto L_0x005f
            boolean r6 = r7.requestFocus(r3)
            if (r6 == 0) goto L_0x005f
            goto L_0x0060
        L_0x005f:
            r1 = r2
        L_0x0060:
            return r1
        L_0x0061:
            return r2
        L_0x0062:
            int r0 = r7.getAction()
            if (r0 != 0) goto L_0x00fb
            int r0 = r7.getKeyCode()
            r4 = 19
            r5 = 33
            if (r0 == r4) goto L_0x00ec
            r4 = 20
            if (r0 == r4) goto L_0x00dc
            r4 = 62
            if (r0 == r4) goto L_0x007c
            goto L_0x00fb
        L_0x007c:
            boolean r7 = r7.isShiftPressed()
            if (r7 == 0) goto L_0x0083
            goto L_0x0084
        L_0x0083:
            r5 = r3
        L_0x0084:
            if (r5 != r3) goto L_0x0088
            r7 = r1
            goto L_0x0089
        L_0x0088:
            r7 = r2
        L_0x0089:
            int r0 = r6.getHeight()
            if (r7 == 0) goto L_0x00c0
            android.graphics.Rect r7 = r6.mTempRect
            int r3 = r6.getScrollY()
            int r3 = r3 + r0
            r7.top = r3
            int r7 = r6.getChildCount()
            if (r7 <= 0) goto L_0x00d1
            int r7 = r7 - r1
            android.view.View r7 = r6.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r1 = r7.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r1 = (android.widget.FrameLayout.LayoutParams) r1
            int r7 = r7.getBottom()
            int r1 = r1.bottomMargin
            int r7 = r7 + r1
            int r1 = r6.getPaddingBottom()
            int r1 = r1 + r7
            android.graphics.Rect r7 = r6.mTempRect
            int r3 = r7.top
            int r3 = r3 + r0
            if (r3 <= r1) goto L_0x00d1
            int r1 = r1 - r0
            r7.top = r1
            goto L_0x00d1
        L_0x00c0:
            android.graphics.Rect r7 = r6.mTempRect
            int r1 = r6.getScrollY()
            int r1 = r1 - r0
            r7.top = r1
            android.graphics.Rect r7 = r6.mTempRect
            int r1 = r7.top
            if (r1 >= 0) goto L_0x00d1
            r7.top = r2
        L_0x00d1:
            android.graphics.Rect r7 = r6.mTempRect
            int r1 = r7.top
            int r0 = r0 + r1
            r7.bottom = r0
            r6.scrollAndFocus(r5, r1, r0)
            goto L_0x00fb
        L_0x00dc:
            boolean r7 = r7.isAltPressed()
            if (r7 != 0) goto L_0x00e7
            boolean r2 = r6.arrowScroll(r3)
            goto L_0x00fb
        L_0x00e7:
            boolean r2 = r6.fullScroll(r3)
            goto L_0x00fb
        L_0x00ec:
            boolean r7 = r7.isAltPressed()
            if (r7 != 0) goto L_0x00f7
            boolean r2 = r6.arrowScroll(r5)
            goto L_0x00fb
        L_0x00f7:
            boolean r2 = r6.fullScroll(r5)
        L_0x00fb:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.executeKeyEvent(android.view.KeyEvent):boolean");
    }

    public final int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mParentHelper;
        Objects.requireNonNull(nestedScrollingParentHelper);
        return nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch;
    }

    public final boolean hasNestedScrollingParent() {
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
        Objects.requireNonNull(nestedScrollingChildHelper);
        if (nestedScrollingChildHelper.getNestedScrollingParentForType(0) != null) {
            return true;
        }
        return false;
    }

    public final boolean isNestedScrollingEnabled() {
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
        Objects.requireNonNull(nestedScrollingChildHelper);
        return nestedScrollingChildHelper.mIsNestedScrollingEnabled;
    }

    public final boolean isWithinDeltaOfScreen(View view, int i, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (this.mTempRect.bottom + i < getScrollY() || this.mTempRect.top - i > getScrollY() + i2) {
            return false;
        }
        return true;
    }

    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z) {
            return false;
        }
        dispatchNestedFling(0.0f, f2, true);
        fling((int) f2);
        return true;
    }

    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        onNestedPreScroll(view, i, i2, iArr, 0);
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        onNestedScrollInternal(i4, i5, (int[]) null);
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSavedState = savedState;
        requestLayout();
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:0x029a  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0255  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            android.view.VelocityTracker r2 = r0.mVelocityTracker
            if (r2 != 0) goto L_0x000e
            android.view.VelocityTracker r2 = android.view.VelocityTracker.obtain()
            r0.mVelocityTracker = r2
        L_0x000e:
            int r2 = r22.getActionMasked()
            r3 = 0
            if (r2 != 0) goto L_0x0017
            r0.mNestedYOffset = r3
        L_0x0017:
            android.view.MotionEvent r4 = android.view.MotionEvent.obtain(r22)
            int r5 = r0.mNestedYOffset
            float r5 = (float) r5
            r6 = 0
            r4.offsetLocation(r6, r5)
            r5 = 2
            r7 = 1
            if (r2 == 0) goto L_0x02e0
            r8 = -1
            r9 = 0
            if (r2 == r7) goto L_0x025c
            if (r2 == r5) goto L_0x009e
            r5 = 3
            if (r2 == r5) goto L_0x005c
            r3 = 5
            if (r2 == r3) goto L_0x0049
            r3 = 6
            if (r2 == r3) goto L_0x0037
            goto L_0x0316
        L_0x0037:
            r21.onSecondaryPointerUp(r22)
            int r2 = r0.mActivePointerId
            int r2 = r1.findPointerIndex(r2)
            float r1 = r1.getY(r2)
            int r1 = (int) r1
            r0.mLastMotionY = r1
            goto L_0x0316
        L_0x0049:
            int r2 = r22.getActionIndex()
            float r3 = r1.getY(r2)
            int r3 = (int) r3
            r0.mLastMotionY = r3
            int r1 = r1.getPointerId(r2)
            r0.mActivePointerId = r1
            goto L_0x0316
        L_0x005c:
            boolean r1 = r0.mIsBeingDragged
            if (r1 == 0) goto L_0x0082
            int r1 = r21.getChildCount()
            if (r1 <= 0) goto L_0x0082
            android.widget.OverScroller r10 = r0.mScroller
            int r11 = r21.getScrollX()
            int r12 = r21.getScrollY()
            r13 = 0
            r14 = 0
            r15 = 0
            int r16 = r21.getScrollRange()
            boolean r1 = r10.springBack(r11, r12, r13, r14, r15, r16)
            if (r1 == 0) goto L_0x0082
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r21)
        L_0x0082:
            r0.mActivePointerId = r8
            r0.mIsBeingDragged = r3
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            if (r1 == 0) goto L_0x008f
            r1.recycle()
            r0.mVelocityTracker = r9
        L_0x008f:
            r0.stopNestedScroll(r3)
            android.widget.EdgeEffect r1 = r0.mEdgeGlowTop
            r1.onRelease()
            android.widget.EdgeEffect r1 = r0.mEdgeGlowBottom
            r1.onRelease()
            goto L_0x0316
        L_0x009e:
            int r2 = r0.mActivePointerId
            int r2 = r1.findPointerIndex(r2)
            if (r2 != r8) goto L_0x00c1
            java.lang.String r1 = "Invalid pointerId="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            int r2 = r0.mActivePointerId
            r1.append(r2)
            java.lang.String r2 = " in onTouchEvent"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "NestedScrollView"
            android.util.Log.e(r2, r1)
            goto L_0x0316
        L_0x00c1:
            float r5 = r1.getY(r2)
            int r5 = (int) r5
            int r8 = r0.mLastMotionY
            int r8 = r8 - r5
            float r9 = r1.getX(r2)
            int r10 = r21.getWidth()
            float r10 = (float) r10
            float r9 = r9 / r10
            float r10 = (float) r8
            int r11 = r21.getHeight()
            float r11 = (float) r11
            float r10 = r10 / r11
            android.widget.EdgeEffect r11 = r0.mEdgeGlowTop
            float r11 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r11)
            int r11 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            r12 = 1065353216(0x3f800000, float:1.0)
            if (r11 == 0) goto L_0x00ff
            android.widget.EdgeEffect r11 = r0.mEdgeGlowTop
            float r10 = -r10
            float r9 = androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r11, r10, r9)
            float r9 = -r9
            android.widget.EdgeEffect r10 = r0.mEdgeGlowTop
            float r10 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r10)
            int r6 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x00fd
            android.widget.EdgeEffect r6 = r0.mEdgeGlowTop
            r6.onRelease()
        L_0x00fd:
            r6 = r9
            goto L_0x0121
        L_0x00ff:
            android.widget.EdgeEffect r11 = r0.mEdgeGlowBottom
            float r11 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r11)
            int r11 = (r11 > r6 ? 1 : (r11 == r6 ? 0 : -1))
            if (r11 == 0) goto L_0x0121
            android.widget.EdgeEffect r11 = r0.mEdgeGlowBottom
            float r9 = r12 - r9
            float r9 = androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r11, r10, r9)
            android.widget.EdgeEffect r10 = r0.mEdgeGlowBottom
            float r10 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r10)
            int r6 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x00fd
            android.widget.EdgeEffect r6 = r0.mEdgeGlowBottom
            r6.onRelease()
            goto L_0x00fd
        L_0x0121:
            int r9 = r21.getHeight()
            float r9 = (float) r9
            float r6 = r6 * r9
            int r6 = java.lang.Math.round(r6)
            if (r6 == 0) goto L_0x0130
            r21.invalidate()
        L_0x0130:
            int r8 = r8 - r6
            boolean r6 = r0.mIsBeingDragged
            if (r6 != 0) goto L_0x0151
            int r6 = java.lang.Math.abs(r8)
            int r9 = r0.mTouchSlop
            if (r6 <= r9) goto L_0x0151
            android.view.ViewParent r6 = r21.getParent()
            if (r6 == 0) goto L_0x0146
            r6.requestDisallowInterceptTouchEvent(r7)
        L_0x0146:
            r0.mIsBeingDragged = r7
            if (r8 <= 0) goto L_0x014e
            int r6 = r0.mTouchSlop
            int r8 = r8 - r6
            goto L_0x0151
        L_0x014e:
            int r6 = r0.mTouchSlop
            int r8 = r8 + r6
        L_0x0151:
            boolean r6 = r0.mIsBeingDragged
            if (r6 == 0) goto L_0x0316
            r14 = 0
            int[] r6 = r0.mScrollConsumed
            int[] r9 = r0.mScrollOffset
            r18 = 0
            androidx.core.view.NestedScrollingChildHelper r13 = r0.mChildHelper
            r15 = r8
            r16 = r6
            r17 = r9
            boolean r6 = r13.dispatchNestedPreScroll(r14, r15, r16, r17, r18)
            if (r6 == 0) goto L_0x0177
            int[] r6 = r0.mScrollConsumed
            r6 = r6[r7]
            int r8 = r8 - r6
            int r6 = r0.mNestedYOffset
            int[] r9 = r0.mScrollOffset
            r9 = r9[r7]
            int r6 = r6 + r9
            r0.mNestedYOffset = r6
        L_0x0177:
            int[] r6 = r0.mScrollOffset
            r6 = r6[r7]
            int r5 = r5 - r6
            r0.mLastMotionY = r5
            int r5 = r21.getScrollY()
            int r6 = r21.getScrollRange()
            int r9 = r21.getOverScrollMode()
            if (r9 == 0) goto L_0x0193
            if (r9 != r7) goto L_0x0191
            if (r6 <= 0) goto L_0x0191
            goto L_0x0193
        L_0x0191:
            r9 = r3
            goto L_0x0194
        L_0x0193:
            r9 = r7
        L_0x0194:
            int r10 = r21.getScrollY()
            boolean r10 = r0.overScrollByCompat(r8, r3, r10, r6)
            if (r10 == 0) goto L_0x01b0
            androidx.core.view.NestedScrollingChildHelper r10 = r0.mChildHelper
            java.util.Objects.requireNonNull(r10)
            android.view.ViewParent r10 = r10.getNestedScrollingParentForType(r3)
            if (r10 == 0) goto L_0x01ab
            r10 = r7
            goto L_0x01ac
        L_0x01ab:
            r10 = r3
        L_0x01ac:
            if (r10 != 0) goto L_0x01b0
            r10 = r7
            goto L_0x01b1
        L_0x01b0:
            r10 = r3
        L_0x01b1:
            int r11 = r21.getScrollY()
            int r15 = r11 - r5
            int r17 = r8 - r15
            int[] r11 = r0.mScrollConsumed
            r11[r7] = r3
            int[] r14 = r0.mScrollOffset
            r19 = 0
            androidx.core.view.NestedScrollingChildHelper r13 = r0.mChildHelper
            java.util.Objects.requireNonNull(r13)
            r16 = 0
            r18 = 0
            r20 = r14
            r14 = r16
            r16 = r18
            r18 = r20
            r20 = r11
            r13.dispatchNestedScrollInternal(r14, r15, r16, r17, r18, r19, r20)
            int r11 = r0.mLastMotionY
            int[] r13 = r0.mScrollOffset
            r14 = r13[r7]
            int r11 = r11 - r14
            r0.mLastMotionY = r11
            int r11 = r0.mNestedYOffset
            r13 = r13[r7]
            int r11 = r11 + r13
            r0.mNestedYOffset = r11
            if (r9 == 0) goto L_0x0252
            int[] r9 = r0.mScrollConsumed
            r9 = r9[r7]
            int r8 = r8 - r9
            int r5 = r5 + r8
            if (r5 >= 0) goto L_0x0216
            android.widget.EdgeEffect r5 = r0.mEdgeGlowTop
            int r6 = -r8
            float r6 = (float) r6
            int r8 = r21.getHeight()
            float r8 = (float) r8
            float r6 = r6 / r8
            float r1 = r1.getX(r2)
            int r2 = r21.getWidth()
            float r2 = (float) r2
            float r1 = r1 / r2
            androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r5, r6, r1)
            android.widget.EdgeEffect r1 = r0.mEdgeGlowBottom
            boolean r1 = r1.isFinished()
            if (r1 != 0) goto L_0x023c
            android.widget.EdgeEffect r1 = r0.mEdgeGlowBottom
            r1.onRelease()
            goto L_0x023c
        L_0x0216:
            if (r5 <= r6) goto L_0x023c
            android.widget.EdgeEffect r5 = r0.mEdgeGlowBottom
            float r6 = (float) r8
            int r8 = r21.getHeight()
            float r8 = (float) r8
            float r6 = r6 / r8
            float r1 = r1.getX(r2)
            int r2 = r21.getWidth()
            float r2 = (float) r2
            float r1 = r1 / r2
            float r12 = r12 - r1
            androidx.core.widget.EdgeEffectCompat$Api31Impl.onPullDistance(r5, r6, r12)
            android.widget.EdgeEffect r1 = r0.mEdgeGlowTop
            boolean r1 = r1.isFinished()
            if (r1 != 0) goto L_0x023c
            android.widget.EdgeEffect r1 = r0.mEdgeGlowTop
            r1.onRelease()
        L_0x023c:
            android.widget.EdgeEffect r1 = r0.mEdgeGlowTop
            boolean r1 = r1.isFinished()
            if (r1 == 0) goto L_0x024c
            android.widget.EdgeEffect r1 = r0.mEdgeGlowBottom
            boolean r1 = r1.isFinished()
            if (r1 != 0) goto L_0x0252
        L_0x024c:
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r21)
            goto L_0x0253
        L_0x0252:
            r3 = r10
        L_0x0253:
            if (r3 == 0) goto L_0x0316
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            r1.clear()
            goto L_0x0316
        L_0x025c:
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            r2 = 1000(0x3e8, float:1.401E-42)
            int r5 = r0.mMaximumVelocity
            float r5 = (float) r5
            r1.computeCurrentVelocity(r2, r5)
            int r2 = r0.mActivePointerId
            float r1 = r1.getYVelocity(r2)
            int r1 = (int) r1
            int r2 = java.lang.Math.abs(r1)
            int r5 = r0.mMinimumVelocity
            if (r2 < r5) goto L_0x02a9
            android.widget.EdgeEffect r2 = r0.mEdgeGlowTop
            float r2 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r2)
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 == 0) goto L_0x0285
            android.widget.EdgeEffect r2 = r0.mEdgeGlowTop
            r2.onAbsorb(r1)
            goto L_0x0295
        L_0x0285:
            android.widget.EdgeEffect r2 = r0.mEdgeGlowBottom
            float r2 = androidx.core.widget.EdgeEffectCompat$Api31Impl.getDistance(r2)
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 == 0) goto L_0x0297
            android.widget.EdgeEffect r2 = r0.mEdgeGlowBottom
            int r5 = -r1
            r2.onAbsorb(r5)
        L_0x0295:
            r2 = r7
            goto L_0x0298
        L_0x0297:
            r2 = r3
        L_0x0298:
            if (r2 != 0) goto L_0x02c5
            int r1 = -r1
            float r2 = (float) r1
            boolean r5 = r0.dispatchNestedPreFling(r6, r2)
            if (r5 != 0) goto L_0x02c5
            r0.dispatchNestedFling(r6, r2, r7)
            r0.fling(r1)
            goto L_0x02c5
        L_0x02a9:
            android.widget.OverScroller r10 = r0.mScroller
            int r11 = r21.getScrollX()
            int r12 = r21.getScrollY()
            r13 = 0
            r14 = 0
            r15 = 0
            int r16 = r21.getScrollRange()
            boolean r1 = r10.springBack(r11, r12, r13, r14, r15, r16)
            if (r1 == 0) goto L_0x02c5
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r21)
        L_0x02c5:
            r0.mActivePointerId = r8
            r0.mIsBeingDragged = r3
            android.view.VelocityTracker r1 = r0.mVelocityTracker
            if (r1 == 0) goto L_0x02d2
            r1.recycle()
            r0.mVelocityTracker = r9
        L_0x02d2:
            r0.stopNestedScroll(r3)
            android.widget.EdgeEffect r1 = r0.mEdgeGlowTop
            r1.onRelease()
            android.widget.EdgeEffect r1 = r0.mEdgeGlowBottom
            r1.onRelease()
            goto L_0x0316
        L_0x02e0:
            int r2 = r21.getChildCount()
            if (r2 != 0) goto L_0x02e7
            return r3
        L_0x02e7:
            boolean r2 = r0.mIsBeingDragged
            if (r2 == 0) goto L_0x02f4
            android.view.ViewParent r2 = r21.getParent()
            if (r2 == 0) goto L_0x02f4
            r2.requestDisallowInterceptTouchEvent(r7)
        L_0x02f4:
            android.widget.OverScroller r2 = r0.mScroller
            boolean r2 = r2.isFinished()
            if (r2 != 0) goto L_0x0304
            android.widget.OverScroller r2 = r0.mScroller
            r2.abortAnimation()
            r0.stopNestedScroll(r7)
        L_0x0304:
            float r2 = r22.getY()
            int r2 = (int) r2
            r0.mLastMotionY = r2
            int r1 = r1.getPointerId(r3)
            r0.mActivePointerId = r1
            androidx.core.view.NestedScrollingChildHelper r1 = r0.mChildHelper
            r1.startNestedScroll(r5, r3)
        L_0x0316:
            android.view.VelocityTracker r0 = r0.mVelocityTracker
            if (r0 == 0) goto L_0x031d
            r0.addMovement(r4)
        L_0x031d:
            r4.recycle()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void requestChildFocus(View view, View view2) {
        if (!this.mIsLayoutDirty) {
            view2.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (computeScrollDeltaToGetChildRectOnScreen != 0) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        } else {
            this.mChildToScrollTo = view2;
        }
        super.requestChildFocus(view, view2);
    }

    public final void requestDisallowInterceptTouchEvent(boolean z) {
        VelocityTracker velocityTracker;
        if (z && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public final boolean scrollAndFocus(int i, int i2, int i3) {
        boolean z;
        boolean z2;
        int i4;
        boolean z3;
        boolean z4;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int height = getHeight();
        int scrollY = getScrollY();
        int i8 = height + scrollY;
        if (i5 == 33) {
            z = true;
        } else {
            z = false;
        }
        ArrayList<View> focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z5 = false;
        for (int i9 = 0; i9 < size; i9++) {
            View view2 = focusables.get(i9);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i6 < bottom && top < i7) {
                if (i6 >= top || bottom >= i7) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (view == null) {
                    view = view2;
                    z5 = z3;
                } else {
                    if ((!z || top >= view.getTop()) && (z || bottom <= view.getBottom())) {
                        z4 = false;
                    } else {
                        z4 = true;
                    }
                    if (z5) {
                        if (z3) {
                            if (!z4) {
                            }
                        }
                    } else if (z3) {
                        view = view2;
                        z5 = true;
                    } else if (!z4) {
                    }
                    view = view2;
                }
            }
        }
        if (view == null) {
            view = this;
        }
        if (i6 < scrollY || i7 > i8) {
            if (z) {
                i4 = i6 - scrollY;
            } else {
                i4 = i7 - i8;
            }
            doScrollY(i4);
            z2 = true;
        } else {
            z2 = false;
        }
        if (view != findFocus()) {
            view.requestFocus(i5);
        }
        return z2;
    }

    public final void setNestedScrollingEnabled(boolean z) {
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
        Objects.requireNonNull(nestedScrollingChildHelper);
        if (nestedScrollingChildHelper.mIsNestedScrollingEnabled) {
            View view = nestedScrollingChildHelper.mView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.stopNestedScroll(view);
        }
        nestedScrollingChildHelper.mIsNestedScrollingEnabled = z;
    }

    public final boolean startNestedScroll(int i) {
        return this.mChildHelper.startNestedScroll(i, 0);
    }

    public final boolean stopGlowAnimations(MotionEvent motionEvent) {
        boolean z;
        if (EdgeEffectCompat$Api31Impl.getDistance(this.mEdgeGlowTop) != 0.0f) {
            EdgeEffectCompat$Api31Impl.onPullDistance(this.mEdgeGlowTop, 0.0f, motionEvent.getY() / ((float) getHeight()));
            z = true;
        } else {
            z = false;
        }
        if (EdgeEffectCompat$Api31Impl.getDistance(this.mEdgeGlowBottom) == 0.0f) {
            return z;
        }
        EdgeEffectCompat$Api31Impl.onPullDistance(this.mEdgeGlowBottom, 0.0f, 1.0f - (motionEvent.getY() / ((float) getHeight())));
        return true;
    }

    public final void stopNestedScroll() {
        stopNestedScroll(0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mIsLaidOut = false;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mEdgeGlowTop = EdgeEffectCompat$Api31Impl.create(context, attributeSet);
        this.mEdgeGlowBottom = EdgeEffectCompat$Api31Impl.create(context, attributeSet);
        this.mScroller = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        if (z != this.mFillViewport) {
            this.mFillViewport = z;
            requestLayout();
        }
        obtainStyledAttributes.recycle();
        this.mParentHelper = new NestedScrollingParentHelper();
        this.mChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
    }

    public final boolean arrowScroll(int i) {
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int height = (int) (((float) getHeight()) * 0.5f);
        if (findNextFocus == null || !isWithinDeltaOfScreen(findNextFocus, height, getHeight())) {
            if (i == 33 && getScrollY() < height) {
                height = getScrollY();
            } else if (i == 130 && getChildCount() > 0) {
                View childAt = getChildAt(0);
                height = Math.min((childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin) - ((getHeight() + getScrollY()) - getPaddingBottom()), height);
            }
            if (height == 0) {
                return false;
            }
            if (i != 130) {
                height = -height;
            }
            doScrollY(height);
        } else {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            findNextFocus.requestFocus(i);
        }
        if (findFocus != null && findFocus.isFocused() && (!isWithinDeltaOfScreen(findFocus, 0, getHeight()))) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    public final int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    public final int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    public final int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    public final int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int i;
        int i2;
        int i3;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        if (rect.bottom < childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin) {
            i = i4 - verticalFadingEdgeLength;
        } else {
            i = i4;
        }
        int i5 = rect.bottom;
        if (i5 > i && rect.top > scrollY) {
            if (rect.height() > height) {
                i3 = rect.top - scrollY;
            } else {
                i3 = rect.bottom - i;
            }
            return Math.min(i3 + 0, (childAt.getBottom() + layoutParams.bottomMargin) - i4);
        } else if (rect.top >= scrollY || i5 >= i) {
            return 0;
        } else {
            if (rect.height() > height) {
                i2 = 0 - (i - rect.bottom);
            } else {
                i2 = 0 - (scrollY - rect.top);
            }
            return Math.max(i2, -getScrollY());
        }
    }

    public final int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public final int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    public final int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        if (childCount == 0) {
            return height;
        }
        View childAt = getChildAt(0);
        int bottom = childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
        int scrollY = getScrollY();
        int max = Math.max(0, bottom - height);
        if (scrollY < 0) {
            return bottom - scrollY;
        }
        if (scrollY > max) {
            return bottom + (scrollY - max);
        }
        return bottom;
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent)) {
            return true;
        }
        return false;
    }

    public final void draw(Canvas canvas) {
        int i;
        super.draw(canvas);
        int scrollY = getScrollY();
        int i2 = 0;
        if (!this.mEdgeGlowTop.isFinished()) {
            int save = canvas.save();
            int width = getWidth();
            int height = getHeight();
            int min = Math.min(0, scrollY);
            if (getClipToPadding()) {
                width -= getPaddingRight() + getPaddingLeft();
                i = getPaddingLeft() + 0;
            } else {
                i = 0;
            }
            if (getClipToPadding()) {
                height -= getPaddingBottom() + getPaddingTop();
                min += getPaddingTop();
            }
            canvas.translate((float) i, (float) min);
            this.mEdgeGlowTop.setSize(width, height);
            if (this.mEdgeGlowTop.draw(canvas)) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            }
            canvas.restoreToCount(save);
        }
        if (!this.mEdgeGlowBottom.isFinished()) {
            int save2 = canvas.save();
            int width2 = getWidth();
            int height2 = getHeight();
            int max = Math.max(getScrollRange(), scrollY) + height2;
            if (getClipToPadding()) {
                width2 -= getPaddingRight() + getPaddingLeft();
                i2 = 0 + getPaddingLeft();
            }
            if (getClipToPadding()) {
                height2 -= getPaddingBottom() + getPaddingTop();
                max -= getPaddingBottom();
            }
            canvas.translate((float) (i2 - width2), (float) max);
            canvas.rotate(180.0f, (float) width2, 0.0f);
            this.mEdgeGlowBottom.setSize(width2, height2);
            if (this.mEdgeGlowBottom.draw(canvas)) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            }
            canvas.restoreToCount(save2);
        }
    }

    public final void fling(int i) {
        if (getChildCount() > 0) {
            this.mScroller.fling(getScrollX(), getScrollY(), 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.mChildHelper.startNestedScroll(2, 1);
            this.mLastScrollerY = getScrollY();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        View childAt = getChildAt(0);
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = ((childAt.getBottom() + ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin) - getScrollY()) - (getHeight() - getPaddingBottom());
        if (bottom < verticalFadingEdgeLength) {
            return ((float) bottom) / ((float) verticalFadingEdgeLength);
        }
        return 1.0f;
    }

    public final int getScrollRange() {
        if (getChildCount() <= 0) {
            return 0;
        }
        View childAt = getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        return Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom()));
    }

    public final float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int scrollY = getScrollY();
        if (scrollY < verticalFadingEdgeLength) {
            return ((float) scrollY) / ((float) verticalFadingEdgeLength);
        }
        return 1.0f;
    }

    public final void measureChild(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft(), layoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    public final void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, 0));
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != 0.0f) {
                if (this.mVerticalScrollFactor == 0.0f) {
                    TypedValue typedValue = new TypedValue();
                    Context context = getContext();
                    if (context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                        this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
                    } else {
                        throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
                    }
                }
                int scrollRange = getScrollRange();
                int scrollY = getScrollY();
                int i = scrollY - ((int) (axisValue * this.mVerticalScrollFactor));
                if (i < 0) {
                    scrollRange = 0;
                } else if (i <= scrollRange) {
                    scrollRange = i;
                }
                if (scrollRange != scrollY) {
                    super.scrollTo(getScrollX(), scrollRange);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0107  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r13) {
        /*
            r12 = this;
            int r0 = r13.getAction()
            r1 = 2
            r2 = 1
            if (r0 != r1) goto L_0x000d
            boolean r3 = r12.mIsBeingDragged
            if (r3 == 0) goto L_0x000d
            return r2
        L_0x000d:
            r0 = r0 & 255(0xff, float:3.57E-43)
            r3 = 0
            r4 = 0
            if (r0 == 0) goto L_0x00b4
            r5 = -1
            if (r0 == r2) goto L_0x0086
            if (r0 == r1) goto L_0x0025
            r1 = 3
            if (r0 == r1) goto L_0x0086
            r1 = 6
            if (r0 == r1) goto L_0x0020
            goto L_0x013e
        L_0x0020:
            r12.onSecondaryPointerUp(r13)
            goto L_0x013e
        L_0x0025:
            int r0 = r12.mActivePointerId
            if (r0 != r5) goto L_0x002b
            goto L_0x013e
        L_0x002b:
            int r4 = r13.findPointerIndex(r0)
            if (r4 != r5) goto L_0x004e
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r1 = "Invalid pointerId="
            r13.append(r1)
            r13.append(r0)
            java.lang.String r0 = " in onInterceptTouchEvent"
            r13.append(r0)
            java.lang.String r13 = r13.toString()
            java.lang.String r0 = "NestedScrollView"
            android.util.Log.e(r0, r13)
            goto L_0x013e
        L_0x004e:
            float r0 = r13.getY(r4)
            int r0 = (int) r0
            int r4 = r12.mLastMotionY
            int r4 = r0 - r4
            int r4 = java.lang.Math.abs(r4)
            int r5 = r12.mTouchSlop
            if (r4 <= r5) goto L_0x013e
            int r4 = r12.getNestedScrollAxes()
            r1 = r1 & r4
            if (r1 != 0) goto L_0x013e
            r12.mIsBeingDragged = r2
            r12.mLastMotionY = r0
            android.view.VelocityTracker r0 = r12.mVelocityTracker
            if (r0 != 0) goto L_0x0074
            android.view.VelocityTracker r0 = android.view.VelocityTracker.obtain()
            r12.mVelocityTracker = r0
        L_0x0074:
            android.view.VelocityTracker r0 = r12.mVelocityTracker
            r0.addMovement(r13)
            r12.mNestedYOffset = r3
            android.view.ViewParent r13 = r12.getParent()
            if (r13 == 0) goto L_0x013e
            r13.requestDisallowInterceptTouchEvent(r2)
            goto L_0x013e
        L_0x0086:
            r12.mIsBeingDragged = r3
            r12.mActivePointerId = r5
            android.view.VelocityTracker r13 = r12.mVelocityTracker
            if (r13 == 0) goto L_0x0093
            r13.recycle()
            r12.mVelocityTracker = r4
        L_0x0093:
            android.widget.OverScroller r5 = r12.mScroller
            int r6 = r12.getScrollX()
            int r7 = r12.getScrollY()
            r8 = 0
            r9 = 0
            r10 = 0
            int r11 = r12.getScrollRange()
            boolean r13 = r5.springBack(r6, r7, r8, r9, r10, r11)
            if (r13 == 0) goto L_0x00af
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r13 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r12)
        L_0x00af:
            r12.stopNestedScroll(r3)
            goto L_0x013e
        L_0x00b4:
            float r0 = r13.getY()
            int r0 = (int) r0
            float r5 = r13.getX()
            int r5 = (int) r5
            int r6 = r12.getChildCount()
            if (r6 <= 0) goto L_0x00e8
            int r6 = r12.getScrollY()
            android.view.View r7 = r12.getChildAt(r3)
            int r8 = r7.getTop()
            int r8 = r8 - r6
            if (r0 < r8) goto L_0x00e8
            int r8 = r7.getBottom()
            int r8 = r8 - r6
            if (r0 >= r8) goto L_0x00e8
            int r6 = r7.getLeft()
            if (r5 < r6) goto L_0x00e8
            int r6 = r7.getRight()
            if (r5 >= r6) goto L_0x00e8
            r5 = r2
            goto L_0x00e9
        L_0x00e8:
            r5 = r3
        L_0x00e9:
            if (r5 != 0) goto L_0x0107
            boolean r13 = r12.stopGlowAnimations(r13)
            if (r13 != 0) goto L_0x00fb
            android.widget.OverScroller r13 = r12.mScroller
            boolean r13 = r13.isFinished()
            if (r13 != 0) goto L_0x00fa
            goto L_0x00fb
        L_0x00fa:
            r2 = r3
        L_0x00fb:
            r12.mIsBeingDragged = r2
            android.view.VelocityTracker r13 = r12.mVelocityTracker
            if (r13 == 0) goto L_0x013e
            r13.recycle()
            r12.mVelocityTracker = r4
            goto L_0x013e
        L_0x0107:
            r12.mLastMotionY = r0
            int r0 = r13.getPointerId(r3)
            r12.mActivePointerId = r0
            android.view.VelocityTracker r0 = r12.mVelocityTracker
            if (r0 != 0) goto L_0x011a
            android.view.VelocityTracker r0 = android.view.VelocityTracker.obtain()
            r12.mVelocityTracker = r0
            goto L_0x011d
        L_0x011a:
            r0.clear()
        L_0x011d:
            android.view.VelocityTracker r0 = r12.mVelocityTracker
            r0.addMovement(r13)
            android.widget.OverScroller r0 = r12.mScroller
            r0.computeScrollOffset()
            boolean r13 = r12.stopGlowAnimations(r13)
            if (r13 != 0) goto L_0x0137
            android.widget.OverScroller r13 = r12.mScroller
            boolean r13 = r13.isFinished()
            if (r13 != 0) goto L_0x0136
            goto L_0x0137
        L_0x0136:
            r2 = r3
        L_0x0137:
            r12.mIsBeingDragged = r2
            androidx.core.view.NestedScrollingChildHelper r13 = r12.mChildHelper
            r13.startNestedScroll(r1, r3)
        L_0x013e:
            boolean r12 = r12.mIsBeingDragged
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        super.onLayout(z, i, i2, i3, i4);
        int i6 = 0;
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && isViewDescendantOf(view, this)) {
            View view2 = this.mChildToScrollTo;
            view2.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (computeScrollDeltaToGetChildRectOnScreen != 0) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                scrollTo(getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                i5 = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                i5 = 0;
            }
            int paddingTop = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
            int scrollY = getScrollY();
            if (paddingTop < i5 && scrollY >= 0) {
                i6 = paddingTop + scrollY > i5 ? i5 - paddingTop : scrollY;
            }
            if (i6 != scrollY) {
                scrollTo(getScrollX(), i6);
            }
        }
        scrollTo(getScrollX(), getScrollY());
        this.mIsLaidOut = true;
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mFillViewport && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredHeight2 = (((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - layoutParams.topMargin) - layoutParams.bottomMargin;
            if (measuredHeight < measuredHeight2) {
                childAt.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight2, 1073741824));
            }
        }
    }

    public final boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        onNestedScrollInternal(i4, 0, (int[]) null);
    }

    public final void onNestedScrollInternal(int i, int i2, int[] iArr) {
        int scrollY = getScrollY();
        scrollBy(0, i);
        int scrollY2 = getScrollY() - scrollY;
        if (iArr != null) {
            iArr[1] = iArr[1] + scrollY2;
        }
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mChildHelper;
        Objects.requireNonNull(nestedScrollingChildHelper);
        nestedScrollingChildHelper.dispatchNestedScrollInternal(0, scrollY2, 0, i - scrollY2, (int[]) null, i2, iArr);
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = getScrollY();
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
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && isWithinDeltaOfScreen(findFocus, 0, i4)) {
            findFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean overScrollByCompat(int r10, int r11, int r12, int r13) {
        /*
            r9 = this;
            int r0 = r9.getOverScrollMode()
            r9.computeHorizontalScrollRange()
            r9.computeHorizontalScrollExtent()
            r9.computeVerticalScrollRange()
            r9.computeVerticalScrollExtent()
            r1 = 1
            r0 = 0
            int r11 = r11 + r0
            int r12 = r12 + r10
            int r13 = r13 + r0
            if (r11 <= 0) goto L_0x001a
        L_0x0017:
            r11 = r0
            r10 = r1
            goto L_0x001e
        L_0x001a:
            if (r11 >= 0) goto L_0x001d
            goto L_0x0017
        L_0x001d:
            r10 = r0
        L_0x001e:
            if (r12 <= r13) goto L_0x0023
            r12 = r13
        L_0x0021:
            r13 = r1
            goto L_0x0028
        L_0x0023:
            if (r12 >= 0) goto L_0x0027
            r12 = r0
            goto L_0x0021
        L_0x0027:
            r13 = r0
        L_0x0028:
            if (r13 == 0) goto L_0x0048
            androidx.core.view.NestedScrollingChildHelper r2 = r9.mChildHelper
            java.util.Objects.requireNonNull(r2)
            android.view.ViewParent r2 = r2.getNestedScrollingParentForType(r1)
            if (r2 == 0) goto L_0x0037
            r2 = r1
            goto L_0x0038
        L_0x0037:
            r2 = r0
        L_0x0038:
            if (r2 != 0) goto L_0x0048
            android.widget.OverScroller r2 = r9.mScroller
            r5 = 0
            r6 = 0
            r7 = 0
            int r8 = r9.getScrollRange()
            r3 = r11
            r4 = r12
            r2.springBack(r3, r4, r5, r6, r7, r8)
        L_0x0048:
            r9.onOverScrolled(r11, r12, r10, r13)
            if (r10 != 0) goto L_0x0051
            if (r13 == 0) goto L_0x0050
            goto L_0x0051
        L_0x0050:
            r1 = r0
        L_0x0051:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.NestedScrollView.overScrollByCompat(int, int, int, int):boolean");
    }

    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        boolean z2;
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            if (z) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            } else {
                smoothScrollBy(0, computeScrollDeltaToGetChildRectOnScreen, false);
            }
        }
        return z2;
    }

    public final void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
            int width2 = childAt.getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int height2 = childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (width >= width2 || i < 0) {
                i = 0;
            } else if (width + i > width2) {
                i = width2 - width;
            }
            if (height >= height2 || i2 < 0) {
                i2 = 0;
            } else if (height + i2 > height2) {
                i2 = height2 - height;
            }
            if (i != getScrollX() || i2 != getScrollY()) {
                super.scrollTo(i, i2);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2, boolean z) {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
                View childAt = getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                int scrollY = getScrollY();
                OverScroller overScroller = this.mScroller;
                int scrollX = getScrollX();
                overScroller.startScroll(scrollX, scrollY, 0, Math.max(0, Math.min(i2 + scrollY, Math.max(0, ((childAt.getHeight() + layoutParams.topMargin) + layoutParams.bottomMargin) - ((getHeight() - getPaddingTop()) - getPaddingBottom())))) - scrollY, 250);
                if (z) {
                    this.mChildHelper.startNestedScroll(2, 1);
                } else {
                    stopNestedScroll(1);
                }
                this.mLastScrollerY = getScrollY();
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
            } else {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                    stopNestedScroll(1);
                }
                scrollBy(i, i2);
            }
            this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void addView(View view, int i) {
        if (getChildCount() <= 0) {
            super.addView(view, i);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public final void onNestedScrollAccepted(View view, View view2, int i) {
        onNestedScrollAccepted(view, view2, i, 0);
    }

    public final void onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0);
    }

    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            super.addView(view, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() <= 0) {
            super.addView(view, i, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public final void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        super.scrollTo(i, i2);
    }

    public final void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }
}
