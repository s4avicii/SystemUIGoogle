package androidx.drawerlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.RotationUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.R$styleable;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public class DrawerLayout extends ViewGroup {
    public static final int[] LAYOUT_ATTRS = {16842931};
    public static final int[] THEME_ATTRS = {16843828};
    public final C01311 mActionDismiss = new AccessibilityViewCommand() {
        public final boolean perform(View view) {
            Objects.requireNonNull(DrawerLayout.this);
            if (!DrawerLayout.isDrawerOpen(view) || DrawerLayout.this.getDrawerLockMode(view) == 2) {
                return false;
            }
            DrawerLayout.this.closeDrawer(view);
            return true;
        }
    };
    public Rect mChildHitRect;
    public Matrix mChildInvertedMatrix;
    public boolean mChildrenCanceledTouch;
    public boolean mDrawStatusBarBackground;
    public float mDrawerElevation;
    public int mDrawerState;
    public boolean mFirstLayout = true;
    public boolean mInLayout;
    public float mInitialMotionX;
    public float mInitialMotionY;
    public WindowInsetsCompat mLastInsets;
    public final ViewDragCallback mLeftCallback;
    public final ViewDragHelper mLeftDragger;
    public int mLockModeEnd = 3;
    public int mLockModeLeft = 3;
    public int mLockModeRight = 3;
    public int mLockModeStart = 3;
    public int mMinDrawerMargin;
    public final ArrayList<View> mNonDrawerViews;
    public final ViewDragCallback mRightCallback;
    public final ViewDragHelper mRightDragger;
    public int mScrimColor = -1728053248;
    public float mScrimOpacity;
    public Paint mScrimPaint = new Paint();
    public Drawable mStatusBarBackground;

    public class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public AccessibilityDelegate() {
            new Rect();
        }

        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int[] iArr = DrawerLayout.THEME_ATTRS;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName("androidx.drawerlayout.widget.DrawerLayout");
            accessibilityNodeInfoCompat.mInfo.setFocusable(false);
            accessibilityNodeInfoCompat.mInfo.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            int[] iArr = DrawerLayout.THEME_ATTRS;
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
            accessibilityEvent.getText();
            View findVisibleDrawer = DrawerLayout.this.findVisibleDrawer();
            if (findVisibleDrawer == null) {
                return true;
            }
            int drawerViewAbsoluteGravity = DrawerLayout.this.getDrawerViewAbsoluteGravity(findVisibleDrawer);
            DrawerLayout drawerLayout = DrawerLayout.this;
            Objects.requireNonNull(drawerLayout);
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            Gravity.getAbsoluteGravity(drawerViewAbsoluteGravity, ViewCompat.Api17Impl.getLayoutDirection(drawerLayout));
            return true;
        }

        public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName("androidx.drawerlayout.widget.DrawerLayout");
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
        public int lockModeEnd;
        public int lockModeLeft;
        public int lockModeRight;
        public int lockModeStart;
        public int openDrawerGravity = 0;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.openDrawerGravity = parcel.readInt();
            this.lockModeLeft = parcel.readInt();
            this.lockModeRight = parcel.readInt();
            this.lockModeStart = parcel.readInt();
            this.lockModeEnd = parcel.readInt();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeInt(this.openDrawerGravity);
            parcel.writeInt(this.lockModeLeft);
            parcel.writeInt(this.lockModeRight);
            parcel.writeInt(this.lockModeStart);
            parcel.writeInt(this.lockModeEnd);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public class ViewDragCallback extends ViewDragHelper.Callback {
        public final int mAbsGravity;
        public ViewDragHelper mDragger;
        public final C01341 mPeekRunnable = new Runnable() {
            public final void run() {
                boolean z;
                int i;
                View view;
                int i2;
                ViewDragCallback viewDragCallback = ViewDragCallback.this;
                Objects.requireNonNull(viewDragCallback);
                ViewDragHelper viewDragHelper = viewDragCallback.mDragger;
                Objects.requireNonNull(viewDragHelper);
                int i3 = viewDragHelper.mEdgeSize;
                int i4 = 3;
                if (viewDragCallback.mAbsGravity == 3) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    view = DrawerLayout.this.findDrawerWithGravity(3);
                    if (view != null) {
                        i2 = -view.getWidth();
                    } else {
                        i2 = 0;
                    }
                    i = i2 + i3;
                } else {
                    view = DrawerLayout.this.findDrawerWithGravity(5);
                    i = DrawerLayout.this.getWidth() - i3;
                }
                if (view == null) {
                    return;
                }
                if (((z && view.getLeft() < i) || (!z && view.getLeft() > i)) && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                    viewDragCallback.mDragger.smoothSlideViewTo(view, i, view.getTop());
                    ((LayoutParams) view.getLayoutParams()).isPeeking = true;
                    DrawerLayout.this.invalidate();
                    if (viewDragCallback.mAbsGravity == 3) {
                        i4 = 5;
                    }
                    View findDrawerWithGravity = DrawerLayout.this.findDrawerWithGravity(i4);
                    if (findDrawerWithGravity != null) {
                        DrawerLayout.this.closeDrawer(findDrawerWithGravity);
                    }
                    DrawerLayout drawerLayout = DrawerLayout.this;
                    Objects.requireNonNull(drawerLayout);
                    if (!drawerLayout.mChildrenCanceledTouch) {
                        long uptimeMillis = SystemClock.uptimeMillis();
                        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                        int childCount = drawerLayout.getChildCount();
                        for (int i5 = 0; i5 < childCount; i5++) {
                            drawerLayout.getChildAt(i5).dispatchTouchEvent(obtain);
                        }
                        obtain.recycle();
                        drawerLayout.mChildrenCanceledTouch = true;
                    }
                }
            }
        };

        public final void onEdgeDragStarted(int i, int i2) {
            View view;
            if ((i & 1) == 1) {
                view = DrawerLayout.this.findDrawerWithGravity(3);
            } else {
                view = DrawerLayout.this.findDrawerWithGravity(5);
            }
            if (view != null && DrawerLayout.this.getDrawerLockMode(view) == 0) {
                this.mDragger.captureChildView(view, i2);
            }
        }

        public ViewDragCallback(int i) {
            this.mAbsGravity = i;
        }

        public final int clampViewPositionHorizontal(View view, int i) {
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i, 0));
            }
            int width = DrawerLayout.this.getWidth();
            return Math.max(width - view.getWidth(), Math.min(i, width));
        }

        public final int getViewHorizontalDragRange(View view) {
            Objects.requireNonNull(DrawerLayout.this);
            if (DrawerLayout.isDrawerView(view)) {
                return view.getWidth();
            }
            return 0;
        }

        public final void onEdgeTouched() {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160);
        }

        public final void onViewDragStateChanged(int i) {
            View rootView;
            DrawerLayout drawerLayout = DrawerLayout.this;
            ViewDragHelper viewDragHelper = this.mDragger;
            Objects.requireNonNull(viewDragHelper);
            View view = viewDragHelper.mCapturedView;
            Objects.requireNonNull(drawerLayout);
            ViewDragHelper viewDragHelper2 = drawerLayout.mLeftDragger;
            Objects.requireNonNull(viewDragHelper2);
            int i2 = viewDragHelper2.mDragState;
            ViewDragHelper viewDragHelper3 = drawerLayout.mRightDragger;
            Objects.requireNonNull(viewDragHelper3);
            int i3 = viewDragHelper3.mDragState;
            int i4 = 2;
            if (i2 == 1 || i3 == 1) {
                i4 = 1;
            } else if (!(i2 == 2 || i3 == 2)) {
                i4 = 0;
            }
            if (view != null && i == 0) {
                float f = ((LayoutParams) view.getLayoutParams()).onScreen;
                if (f == 0.0f) {
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    if ((layoutParams.openState & 1) == 1) {
                        layoutParams.openState = 0;
                        drawerLayout.updateChildrenImportantForAccessibility(view, false);
                        drawerLayout.updateChildAccessibilityAction(view);
                        if (drawerLayout.hasWindowFocus() && (rootView = drawerLayout.getRootView()) != null) {
                            rootView.sendAccessibilityEvent(32);
                        }
                    }
                } else if (f == 1.0f) {
                    LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                    if ((layoutParams2.openState & 1) == 0) {
                        layoutParams2.openState = 1;
                        drawerLayout.updateChildrenImportantForAccessibility(view, true);
                        drawerLayout.updateChildAccessibilityAction(view);
                        if (drawerLayout.hasWindowFocus()) {
                            drawerLayout.sendAccessibilityEvent(32);
                        }
                    }
                }
            }
            if (i4 != drawerLayout.mDrawerState) {
                drawerLayout.mDrawerState = i4;
            }
        }

        public final void onViewReleased(View view, float f, float f2) {
            int i;
            Objects.requireNonNull(DrawerLayout.this);
            float f3 = ((LayoutParams) view.getLayoutParams()).onScreen;
            int width = view.getWidth();
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i2 > 0 || (i2 == 0 && f3 > 0.5f)) {
                    i = 0;
                } else {
                    i = -width;
                }
            } else {
                int width2 = DrawerLayout.this.getWidth();
                if (f < 0.0f || (f == 0.0f && f3 > 0.5f)) {
                    width2 -= width;
                }
                i = width2;
            }
            this.mDragger.settleCapturedViewAt(i, view.getTop());
            DrawerLayout.this.invalidate();
        }

        public final boolean tryCaptureView(View view, int i) {
            Objects.requireNonNull(DrawerLayout.this);
            if (!DrawerLayout.isDrawerView(view) || !DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, this.mAbsGravity) || DrawerLayout.this.getDrawerLockMode(view) != 0) {
                return false;
            }
            return true;
        }

        public final int clampViewPositionVertical(View view, int i) {
            return view.getTop();
        }

        public final void onViewCaptured(View view, int i) {
            ((LayoutParams) view.getLayoutParams()).isPeeking = false;
            int i2 = 3;
            if (this.mAbsGravity == 3) {
                i2 = 5;
            }
            View findDrawerWithGravity = DrawerLayout.this.findDrawerWithGravity(i2);
            if (findDrawerWithGravity != null) {
                DrawerLayout.this.closeDrawer(findDrawerWithGravity);
            }
        }

        public final void onViewPositionChanged(View view, int i, int i2) {
            float f;
            int i3;
            int width = view.getWidth();
            if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(view, 3)) {
                f = (float) (i + width);
            } else {
                f = (float) (DrawerLayout.this.getWidth() - i);
            }
            float f2 = f / ((float) width);
            Objects.requireNonNull(DrawerLayout.this);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (f2 != layoutParams.onScreen) {
                layoutParams.onScreen = f2;
            }
            if (f2 == 0.0f) {
                i3 = 4;
            } else {
                i3 = 0;
            }
            view.setVisibility(i3);
            DrawerLayout.this.invalidate();
        }
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z;
        if (i == 4) {
            if (findVisibleDrawer() != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                keyEvent.startTracking();
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        View findVisibleDrawer = findVisibleDrawer();
        if (findVisibleDrawer != null && getDrawerLockMode(findVisibleDrawer) == 0) {
            closeDrawers(false);
        }
        if (findVisibleDrawer != null) {
            return true;
        }
        return false;
    }

    public final void onRtlPropertiesChanged(int i) {
    }

    public static final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            boolean z;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            int[] iArr = DrawerLayout.THEME_ATTRS;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getImportantForAccessibility(view) == 4 || ViewCompat.Api16Impl.getImportantForAccessibility(view) == 2) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                accessibilityNodeInfoCompat.mParentVirtualDescendantId = -1;
                accessibilityNodeInfoCompat.mInfo.setParent((View) null);
            }
        }
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams) || !super.checkLayoutParams(layoutParams)) {
            return false;
        }
        return true;
    }

    public final View findDrawerWithGravity(int i) {
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity = Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((getDrawerViewAbsoluteGravity(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        int i5;
        boolean z2;
        int i6;
        boolean z3 = true;
        this.mInLayout = true;
        int i7 = i3 - i;
        int childCount = getChildCount();
        int i8 = 0;
        while (i8 < childCount) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (isContentView(childAt)) {
                    int i9 = layoutParams.leftMargin;
                    childAt.layout(i9, layoutParams.topMargin, childAt.getMeasuredWidth() + i9, childAt.getMeasuredHeight() + layoutParams.topMargin);
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                        float f2 = (float) measuredWidth;
                        i5 = (-measuredWidth) + ((int) (layoutParams.onScreen * f2));
                        f = ((float) (measuredWidth + i5)) / f2;
                    } else {
                        float f3 = (float) measuredWidth;
                        int i10 = i7 - ((int) (layoutParams.onScreen * f3));
                        f = ((float) (i7 - i10)) / f3;
                        i5 = i10;
                    }
                    if (f != layoutParams.onScreen) {
                        z2 = z3;
                    } else {
                        z2 = false;
                    }
                    int i11 = layoutParams.gravity & 112;
                    if (i11 == 16) {
                        int i12 = i4 - i2;
                        int i13 = (i12 - measuredHeight) / 2;
                        int i14 = layoutParams.topMargin;
                        if (i13 < i14) {
                            i13 = i14;
                        } else {
                            int i15 = i13 + measuredHeight;
                            int i16 = i12 - layoutParams.bottomMargin;
                            if (i15 > i16) {
                                i13 = i16 - measuredHeight;
                            }
                        }
                        childAt.layout(i5, i13, measuredWidth + i5, measuredHeight + i13);
                    } else if (i11 != 80) {
                        int i17 = layoutParams.topMargin;
                        childAt.layout(i5, i17, measuredWidth + i5, measuredHeight + i17);
                    } else {
                        int i18 = i4 - i2;
                        childAt.layout(i5, (i18 - layoutParams.bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i5, i18 - layoutParams.bottomMargin);
                    }
                    if (z2) {
                        LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                        if (f != layoutParams2.onScreen) {
                            layoutParams2.onScreen = f;
                        }
                    }
                    if (layoutParams.onScreen > 0.0f) {
                        i6 = 0;
                    } else {
                        i6 = 4;
                    }
                    if (childAt.getVisibility() != i6) {
                        childAt.setVisibility(i6);
                    }
                }
            }
            i8++;
            z3 = true;
        }
        WindowInsets rootWindowInsets = getRootWindowInsets();
        if (rootWindowInsets != null) {
            Insets systemGestureInsets = WindowInsetsCompat.toWindowInsetsCompat(rootWindowInsets, (View) null).mImpl.getSystemGestureInsets();
            ViewDragHelper viewDragHelper = this.mLeftDragger;
            Objects.requireNonNull(viewDragHelper);
            viewDragHelper.mEdgeSize = Math.max(viewDragHelper.mDefaultEdgeSize, systemGestureInsets.left);
            ViewDragHelper viewDragHelper2 = this.mRightDragger;
            Objects.requireNonNull(viewDragHelper2);
            viewDragHelper2.mEdgeSize = Math.max(viewDragHelper2.mDefaultEdgeSize, systemGestureInsets.right);
        }
        this.mInLayout = false;
        this.mFirstLayout = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0048  */
    @android.annotation.SuppressLint({"WrongConstant"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r17, int r18) {
        /*
            r16 = this;
            r0 = r16
            int r1 = android.view.View.MeasureSpec.getMode(r17)
            int r2 = android.view.View.MeasureSpec.getMode(r18)
            int r3 = android.view.View.MeasureSpec.getSize(r17)
            int r4 = android.view.View.MeasureSpec.getSize(r18)
            r5 = 1073741824(0x40000000, float:2.0)
            r6 = 300(0x12c, float:4.2E-43)
            if (r1 != r5) goto L_0x001a
            if (r2 == r5) goto L_0x0026
        L_0x001a:
            boolean r5 = r16.isInEditMode()
            if (r5 == 0) goto L_0x01a9
            if (r1 != 0) goto L_0x0023
            r3 = r6
        L_0x0023:
            if (r2 != 0) goto L_0x0026
            r4 = r6
        L_0x0026:
            r0.setMeasuredDimension(r3, r4)
            androidx.core.view.WindowInsetsCompat r1 = r0.mLastInsets
            r2 = 0
            if (r1 == 0) goto L_0x0038
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r1 = androidx.core.view.ViewCompat.Api16Impl.getFitsSystemWindows(r16)
            if (r1 == 0) goto L_0x0038
            r1 = 1
            goto L_0x0039
        L_0x0038:
            r1 = r2
        L_0x0039:
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r5 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r5 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r16)
            int r6 = r16.getChildCount()
            r7 = r2
            r8 = r7
            r9 = r8
        L_0x0046:
            if (r2 >= r6) goto L_0x01a8
            android.view.View r10 = r0.getChildAt(r2)
            int r11 = r10.getVisibility()
            r12 = 8
            if (r11 != r12) goto L_0x0056
            goto L_0x00f9
        L_0x0056:
            android.view.ViewGroup$LayoutParams r11 = r10.getLayoutParams()
            androidx.drawerlayout.widget.DrawerLayout$LayoutParams r11 = (androidx.drawerlayout.widget.DrawerLayout.LayoutParams) r11
            r12 = 3
            if (r1 == 0) goto L_0x00d8
            int r13 = r11.gravity
            int r13 = android.view.Gravity.getAbsoluteGravity(r13, r5)
            boolean r14 = androidx.core.view.ViewCompat.Api16Impl.getFitsSystemWindows(r10)
            if (r14 == 0) goto L_0x0097
            androidx.core.view.WindowInsetsCompat r14 = r0.mLastInsets
            if (r13 != r12) goto L_0x0080
            int r12 = r14.getSystemWindowInsetLeft()
            int r13 = r14.getSystemWindowInsetTop()
            int r15 = r14.getSystemWindowInsetBottom()
            androidx.core.view.WindowInsetsCompat r14 = r14.replaceSystemWindowInsets(r12, r13, r7, r15)
            goto L_0x0093
        L_0x0080:
            r12 = 5
            if (r13 != r12) goto L_0x0093
            int r12 = r14.getSystemWindowInsetTop()
            int r13 = r14.getSystemWindowInsetRight()
            int r15 = r14.getSystemWindowInsetBottom()
            androidx.core.view.WindowInsetsCompat r14 = r14.replaceSystemWindowInsets(r7, r12, r13, r15)
        L_0x0093:
            androidx.core.view.ViewCompat.dispatchApplyWindowInsets(r10, r14)
            goto L_0x00d8
        L_0x0097:
            androidx.core.view.WindowInsetsCompat r12 = r0.mLastInsets
            r14 = 3
            if (r13 != r14) goto L_0x00ad
            int r13 = r12.getSystemWindowInsetLeft()
            int r14 = r12.getSystemWindowInsetTop()
            int r15 = r12.getSystemWindowInsetBottom()
            androidx.core.view.WindowInsetsCompat r12 = r12.replaceSystemWindowInsets(r13, r14, r7, r15)
            goto L_0x00c0
        L_0x00ad:
            r14 = 5
            if (r13 != r14) goto L_0x00c0
            int r13 = r12.getSystemWindowInsetTop()
            int r14 = r12.getSystemWindowInsetRight()
            int r15 = r12.getSystemWindowInsetBottom()
            androidx.core.view.WindowInsetsCompat r12 = r12.replaceSystemWindowInsets(r7, r13, r14, r15)
        L_0x00c0:
            int r13 = r12.getSystemWindowInsetLeft()
            r11.leftMargin = r13
            int r13 = r12.getSystemWindowInsetTop()
            r11.topMargin = r13
            int r13 = r12.getSystemWindowInsetRight()
            r11.rightMargin = r13
            int r12 = r12.getSystemWindowInsetBottom()
            r11.bottomMargin = r12
        L_0x00d8:
            boolean r12 = isContentView(r10)
            if (r12 == 0) goto L_0x00ff
            int r7 = r11.leftMargin
            int r7 = r3 - r7
            int r12 = r11.rightMargin
            int r7 = r7 - r12
            r12 = 1073741824(0x40000000, float:2.0)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r12)
            int r13 = r11.topMargin
            int r13 = r4 - r13
            int r11 = r11.bottomMargin
            int r13 = r13 - r11
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r12)
            r10.measure(r7, r11)
        L_0x00f9:
            r13 = r17
            r14 = r18
            goto L_0x017f
        L_0x00ff:
            boolean r12 = isDrawerView(r10)
            if (r12 == 0) goto L_0x0184
            float r12 = androidx.core.view.ViewCompat.Api21Impl.getElevation(r10)
            float r13 = r0.mDrawerElevation
            int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
            if (r12 == 0) goto L_0x0112
            androidx.core.view.ViewCompat.Api21Impl.setElevation(r10, r13)
        L_0x0112:
            int r12 = r0.getDrawerViewAbsoluteGravity(r10)
            r12 = r12 & 7
            r13 = 3
            if (r12 != r13) goto L_0x011c
            r7 = 1
        L_0x011c:
            if (r7 == 0) goto L_0x0120
            if (r8 != 0) goto L_0x0124
        L_0x0120:
            if (r7 != 0) goto L_0x015a
            if (r9 == 0) goto L_0x015a
        L_0x0124:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Child drawer has absolute gravity "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            r2 = r12 & 3
            r3 = 3
            if (r2 == r3) goto L_0x013e
            r2 = r12 & 5
            r3 = 5
            if (r2 != r3) goto L_0x0139
            java.lang.String r2 = "RIGHT"
            goto L_0x0140
        L_0x0139:
            java.lang.String r2 = java.lang.Integer.toHexString(r12)
            goto L_0x0140
        L_0x013e:
            java.lang.String r2 = "LEFT"
        L_0x0140:
            r1.append(r2)
            java.lang.String r2 = " but this "
            r1.append(r2)
            java.lang.String r2 = "DrawerLayout"
            r1.append(r2)
            java.lang.String r2 = " already has a drawer view along that edge"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x015a:
            if (r7 == 0) goto L_0x015e
            r8 = 1
            goto L_0x015f
        L_0x015e:
            r9 = 1
        L_0x015f:
            int r7 = r0.mMinDrawerMargin
            int r12 = r11.leftMargin
            int r7 = r7 + r12
            int r12 = r11.rightMargin
            int r7 = r7 + r12
            int r12 = r11.width
            r13 = r17
            int r7 = android.view.ViewGroup.getChildMeasureSpec(r13, r7, r12)
            int r12 = r11.topMargin
            int r14 = r11.bottomMargin
            int r12 = r12 + r14
            int r11 = r11.height
            r14 = r18
            int r11 = android.view.ViewGroup.getChildMeasureSpec(r14, r12, r11)
            r10.measure(r7, r11)
        L_0x017f:
            int r2 = r2 + 1
            r7 = 0
            goto L_0x0046
        L_0x0184:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Child "
            r1.append(r3)
            r1.append(r10)
            java.lang.String r3 = " at index "
            r1.append(r3)
            r1.append(r2)
            java.lang.String r2 = " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x01a8:
            return
        L_0x01a9:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "DrawerLayout must be measured with MeasureSpec.EXACTLY."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onMeasure(int, int):void");
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        View findDrawerWithGravity;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        int i = savedState.openDrawerGravity;
        if (!(i == 0 || (findDrawerWithGravity = findDrawerWithGravity(i)) == null)) {
            openDrawer(findDrawerWithGravity);
        }
        int i2 = savedState.lockModeLeft;
        if (i2 != 3) {
            setDrawerLockMode(i2, 3);
        }
        int i3 = savedState.lockModeRight;
        if (i3 != 3) {
            setDrawerLockMode(i3, 5);
        }
        int i4 = savedState.lockModeStart;
        if (i4 != 3) {
            setDrawerLockMode(i4, 8388611);
        }
        int i5 = savedState.lockModeEnd;
        if (i5 != 3) {
            setDrawerLockMode(i5, 8388613);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        if (getDrawerLockMode(r7) != 2) goto L_0x005c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            androidx.customview.widget.ViewDragHelper r0 = r6.mLeftDragger
            r0.processTouchEvent(r7)
            androidx.customview.widget.ViewDragHelper r0 = r6.mRightDragger
            r0.processTouchEvent(r7)
            int r0 = r7.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0060
            if (r0 == r2) goto L_0x0020
            r7 = 3
            if (r0 == r7) goto L_0x001a
            goto L_0x006e
        L_0x001a:
            r6.closeDrawers(r2)
            r6.mChildrenCanceledTouch = r1
            goto L_0x006e
        L_0x0020:
            float r0 = r7.getX()
            float r7 = r7.getY()
            androidx.customview.widget.ViewDragHelper r3 = r6.mLeftDragger
            int r4 = (int) r0
            int r5 = (int) r7
            android.view.View r3 = r3.findTopChildUnder(r4, r5)
            if (r3 == 0) goto L_0x005b
            boolean r3 = isContentView(r3)
            if (r3 == 0) goto L_0x005b
            float r3 = r6.mInitialMotionX
            float r0 = r0 - r3
            float r3 = r6.mInitialMotionY
            float r7 = r7 - r3
            androidx.customview.widget.ViewDragHelper r3 = r6.mLeftDragger
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.mTouchSlop
            float r0 = r0 * r0
            float r7 = r7 * r7
            float r7 = r7 + r0
            int r3 = r3 * r3
            float r0 = (float) r3
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 >= 0) goto L_0x005b
            android.view.View r7 = r6.findOpenDrawer()
            if (r7 == 0) goto L_0x005b
            int r7 = r6.getDrawerLockMode(r7)
            r0 = 2
            if (r7 != r0) goto L_0x005c
        L_0x005b:
            r1 = r2
        L_0x005c:
            r6.closeDrawers(r1)
            goto L_0x006e
        L_0x0060:
            float r0 = r7.getX()
            float r7 = r7.getY()
            r6.mInitialMotionX = r0
            r6.mInitialMotionY = r7
            r6.mChildrenCanceledTouch = r1
        L_0x006e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    public final void setDrawerLockMode(int i, int i2) {
        View findDrawerWithGravity;
        ViewDragHelper viewDragHelper;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, ViewCompat.Api17Impl.getLayoutDirection(this));
        if (i2 == 3) {
            this.mLockModeLeft = i;
        } else if (i2 == 5) {
            this.mLockModeRight = i;
        } else if (i2 == 8388611) {
            this.mLockModeStart = i;
        } else if (i2 == 8388613) {
            this.mLockModeEnd = i;
        }
        if (i != 0) {
            if (absoluteGravity == 3) {
                viewDragHelper = this.mLeftDragger;
            } else {
                viewDragHelper = this.mRightDragger;
            }
            viewDragHelper.cancel();
        }
        if (i == 1) {
            View findDrawerWithGravity2 = findDrawerWithGravity(absoluteGravity);
            if (findDrawerWithGravity2 != null) {
                closeDrawer(findDrawerWithGravity2);
            }
        } else if (i == 2 && (findDrawerWithGravity = findDrawerWithGravity(absoluteGravity)) != null) {
            openDrawer(findDrawerWithGravity);
        }
    }

    public final void updateChildAccessibilityAction(View view) {
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS;
        ViewCompat.removeActionWithId(accessibilityActionCompat.getId(), view);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        if (isDrawerOpen(view) && getDrawerLockMode(view) != 2) {
            ViewCompat.replaceAccessibilityAction(view, accessibilityActionCompat, (String) null, this.mActionDismiss);
        }
    }

    /* JADX INFO: finally extract failed */
    public DrawerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, C1777R.attr.drawerLayoutStyle);
        new ChildAccessibilityDelegate();
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        this.mMinDrawerMargin = (int) ((64.0f * f) + 0.5f);
        float f2 = f * 400.0f;
        ViewDragCallback viewDragCallback = new ViewDragCallback(3);
        this.mLeftCallback = viewDragCallback;
        ViewDragCallback viewDragCallback2 = new ViewDragCallback(5);
        this.mRightCallback = viewDragCallback2;
        ViewDragHelper viewDragHelper = new ViewDragHelper(getContext(), this, viewDragCallback);
        viewDragHelper.mTouchSlop = (int) (((float) viewDragHelper.mTouchSlop) * 1.0f);
        this.mLeftDragger = viewDragHelper;
        viewDragHelper.mTrackingEdges = 1;
        viewDragHelper.mMinVelocity = f2;
        viewDragCallback.mDragger = viewDragHelper;
        ViewDragHelper viewDragHelper2 = new ViewDragHelper(getContext(), this, viewDragCallback2);
        viewDragHelper2.mTouchSlop = (int) (((float) viewDragHelper2.mTouchSlop) * 1.0f);
        this.mRightDragger = viewDragHelper2;
        viewDragHelper2.mTrackingEdges = 2;
        viewDragHelper2.mMinVelocity = f2;
        viewDragCallback2.mDragger = viewDragHelper2;
        setFocusableInTouchMode(true);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.Api16Impl.getFitsSystemWindows(this)) {
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
                public static void setup(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, float f, float f2) {
                    float f3;
                    if (i != 0) {
                        SurfaceControl build = new SurfaceControl.Builder().setName("Transition Unrotate").setContainerLayer().setParent(surfaceControl).build();
                        RotationUtils.rotateSurface(transaction, build, i);
                        boolean z = false;
                        Point point = new Point(0, 0);
                        if (i % 2 != 0) {
                            z = true;
                        }
                        if (z) {
                            f3 = f2;
                        } else {
                            f3 = f;
                        }
                        if (!z) {
                            f = f2;
                        }
                        RotationUtils.rotatePoint(point, i, (int) f3, (int) f);
                        transaction.setPosition(build, (float) point.x, (float) point.y);
                        transaction.show(build);
                    }
                }

                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    boolean z;
                    DrawerLayout drawerLayout = (DrawerLayout) view;
                    boolean z2 = true;
                    if (windowInsetsCompat.mImpl.getSystemWindowInsets().top > 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Objects.requireNonNull(drawerLayout);
                    drawerLayout.mLastInsets = windowInsetsCompat;
                    drawerLayout.mDrawStatusBarBackground = z;
                    if (z || drawerLayout.getBackground() != null) {
                        z2 = false;
                    }
                    drawerLayout.setWillNotDraw(z2);
                    drawerLayout.requestLayout();
                    return windowInsetsCompat.mImpl.consumeSystemWindowInsets();
                }
            });
            setSystemUiVisibility(1280);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(THEME_ATTRS);
            try {
                this.mStatusBarBackground = obtainStyledAttributes.getDrawable(0);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.DrawerLayout, C1777R.attr.drawerLayoutStyle, 0);
        try {
            if (obtainStyledAttributes2.hasValue(0)) {
                this.mDrawerElevation = obtainStyledAttributes2.getDimension(0, 0.0f);
            } else {
                this.mDrawerElevation = getResources().getDimension(C1777R.dimen.def_drawer_elevation);
            }
            obtainStyledAttributes2.recycle();
            this.mNonDrawerViews = new ArrayList<>();
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            throw th;
        }
    }

    public static boolean isContentView(View view) {
        if (((LayoutParams) view.getLayoutParams()).gravity == 0) {
            return true;
        }
        return false;
    }

    public static boolean isDrawerOpen(View view) {
        if (!isDrawerView(view)) {
            throw new IllegalArgumentException("View " + view + " is not a drawer");
        } else if ((((LayoutParams) view.getLayoutParams()).openState & 1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDrawerView(View view) {
        int i = ((LayoutParams) view.getLayoutParams()).gravity;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int absoluteGravity = Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(view));
        if ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) {
            return false;
        }
        return true;
    }

    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (getDescendantFocusability() != 393216) {
            int childCount = getChildCount();
            boolean z = false;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (!isDrawerView(childAt)) {
                    this.mNonDrawerViews.add(childAt);
                } else if (isDrawerOpen(childAt)) {
                    childAt.addFocusables(arrayList, i, i2);
                    z = true;
                }
            }
            if (!z) {
                int size = this.mNonDrawerViews.size();
                for (int i4 = 0; i4 < size; i4++) {
                    View view = this.mNonDrawerViews.get(i4);
                    if (view.getVisibility() == 0) {
                        view.addFocusables(arrayList, i, i2);
                    }
                }
            }
            this.mNonDrawerViews.clear();
        }
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (findOpenDrawer() != null || isDrawerView(view)) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(view, 4);
            return;
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
    }

    public final boolean checkDrawerViewAbsoluteGravity(View view, int i) {
        if ((getDrawerViewAbsoluteGravity(view) & i) == i) {
            return true;
        }
        return false;
    }

    public final void closeDrawer(View view) {
        if (isDrawerView(view)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.mFirstLayout) {
                layoutParams.onScreen = 0.0f;
                layoutParams.openState = 0;
            } else {
                layoutParams.openState |= 4;
                if (checkDrawerViewAbsoluteGravity(view, 3)) {
                    this.mLeftDragger.smoothSlideViewTo(view, -view.getWidth(), view.getTop());
                } else {
                    this.mRightDragger.smoothSlideViewTo(view, getWidth(), view.getTop());
                }
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public final void closeDrawers(boolean z) {
        boolean z2;
        int childCount = getChildCount();
        boolean z3 = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (isDrawerView(childAt) && (!z || layoutParams.isPeeking)) {
                int width = childAt.getWidth();
                if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                    z2 = this.mLeftDragger.smoothSlideViewTo(childAt, -width, childAt.getTop());
                } else {
                    z2 = this.mRightDragger.smoothSlideViewTo(childAt, getWidth(), childAt.getTop());
                }
                z3 |= z2;
                layoutParams.isPeeking = false;
            }
        }
        ViewDragCallback viewDragCallback = this.mLeftCallback;
        Objects.requireNonNull(viewDragCallback);
        DrawerLayout.this.removeCallbacks(viewDragCallback.mPeekRunnable);
        ViewDragCallback viewDragCallback2 = this.mRightCallback;
        Objects.requireNonNull(viewDragCallback2);
        DrawerLayout.this.removeCallbacks(viewDragCallback2.mPeekRunnable);
        if (z3) {
            invalidate();
        }
    }

    public final void computeScroll() {
        int childCount = getChildCount();
        float f = 0.0f;
        for (int i = 0; i < childCount; i++) {
            f = Math.max(f, ((LayoutParams) getChildAt(i).getLayoutParams()).onScreen);
        }
        this.mScrimOpacity = f;
        boolean continueSettling = this.mLeftDragger.continueSettling();
        boolean continueSettling2 = this.mRightDragger.continueSettling();
        if (continueSettling || continueSettling2) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
    }

    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        boolean z;
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.mScrimOpacity <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            if (this.mChildHitRect == null) {
                this.mChildHitRect = new Rect();
            }
            childAt.getHitRect(this.mChildHitRect);
            if (this.mChildHitRect.contains((int) x, (int) y) && !isContentView(childAt)) {
                if (!childAt.getMatrix().isIdentity()) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.offsetLocation((float) (getScrollX() - childAt.getLeft()), (float) (getScrollY() - childAt.getTop()));
                    Matrix matrix = childAt.getMatrix();
                    if (!matrix.isIdentity()) {
                        if (this.mChildInvertedMatrix == null) {
                            this.mChildInvertedMatrix = new Matrix();
                        }
                        matrix.invert(this.mChildInvertedMatrix);
                        obtain.transform(this.mChildInvertedMatrix);
                    }
                    z = childAt.dispatchGenericMotionEvent(obtain);
                    obtain.recycle();
                } else {
                    float scrollX = (float) (getScrollX() - childAt.getLeft());
                    float scrollY = (float) (getScrollY() - childAt.getTop());
                    motionEvent.offsetLocation(scrollX, scrollY);
                    z = childAt.dispatchGenericMotionEvent(motionEvent);
                    motionEvent.offsetLocation(-scrollX, -scrollY);
                }
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        int height = getHeight();
        boolean isContentView = isContentView(view);
        int width = getWidth();
        int save = canvas.save();
        int i = 0;
        if (isContentView) {
            int childCount = getChildCount();
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != view && childAt.getVisibility() == 0) {
                    Drawable background = childAt.getBackground();
                    if (background == null || background.getOpacity() != -1) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z && isDrawerView(childAt) && childAt.getHeight() >= height) {
                        if (checkDrawerViewAbsoluteGravity(childAt, 3)) {
                            int right = childAt.getRight();
                            if (right > i2) {
                                i2 = right;
                            }
                        } else {
                            int left = childAt.getLeft();
                            if (left < width) {
                                width = left;
                            }
                        }
                    }
                }
            }
            canvas.clipRect(i2, 0, width, getHeight());
            i = i2;
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(save);
        float f = this.mScrimOpacity;
        if (f > 0.0f && isContentView) {
            int i4 = this.mScrimColor;
            this.mScrimPaint.setColor((((int) (((float) ((-16777216 & i4) >>> 24)) * f)) << 24) | (i4 & 16777215));
            canvas.drawRect((float) i, 0.0f, (float) width, (float) getHeight(), this.mScrimPaint);
        }
        return drawChild;
    }

    public final View findOpenDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((((LayoutParams) childAt.getLayoutParams()).openState & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    public final View findVisibleDrawer() {
        boolean z;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (isDrawerView(childAt)) {
                if (isDrawerView(childAt)) {
                    if (((LayoutParams) childAt.getLayoutParams()).onScreen > 0.0f) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        return childAt;
                    }
                } else {
                    throw new IllegalArgumentException("View " + childAt + " is not a drawer");
                }
            }
        }
        return null;
    }

    public final int getDrawerLockMode(View view) {
        int i;
        int i2;
        int i3;
        int i4;
        if (isDrawerView(view)) {
            int i5 = ((LayoutParams) view.getLayoutParams()).gravity;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
            if (i5 == 3) {
                int i6 = this.mLockModeLeft;
                if (i6 != 3) {
                    return i6;
                }
                if (layoutDirection == 0) {
                    i = this.mLockModeStart;
                } else {
                    i = this.mLockModeEnd;
                }
                int i7 = i;
                if (i7 != 3) {
                    return i7;
                }
            } else if (i5 == 5) {
                int i8 = this.mLockModeRight;
                if (i8 != 3) {
                    return i8;
                }
                if (layoutDirection == 0) {
                    i2 = this.mLockModeEnd;
                } else {
                    i2 = this.mLockModeStart;
                }
                int i9 = i2;
                if (i9 != 3) {
                    return i9;
                }
            } else if (i5 == 8388611) {
                int i10 = this.mLockModeStart;
                if (i10 != 3) {
                    return i10;
                }
                if (layoutDirection == 0) {
                    i3 = this.mLockModeLeft;
                } else {
                    i3 = this.mLockModeRight;
                }
                int i11 = i3;
                if (i11 != 3) {
                    return i11;
                }
            } else if (i5 == 8388613) {
                int i12 = this.mLockModeEnd;
                if (i12 != 3) {
                    return i12;
                }
                if (layoutDirection == 0) {
                    i4 = this.mLockModeRight;
                } else {
                    i4 = this.mLockModeLeft;
                }
                int i13 = i4;
                if (i13 != 3) {
                    return i13;
                }
            }
            return 0;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public final int getDrawerViewAbsoluteGravity(View view) {
        int i = ((LayoutParams) view.getLayoutParams()).gravity;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(this));
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    public final void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
            if (windowInsetsCompat != null) {
                i = windowInsetsCompat.getSystemWindowInsetTop();
            } else {
                i = 0;
            }
            if (i > 0) {
                this.mStatusBarBackground.setBounds(0, 0, getWidth(), i);
                this.mStatusBarBackground.draw(canvas);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001b, code lost:
        if (r0 != 3) goto L_0x0092;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006e A[LOOP:0: B:10:0x002b->B:31:0x006e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x006c A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            int r0 = r10.getActionMasked()
            androidx.customview.widget.ViewDragHelper r1 = r9.mLeftDragger
            boolean r1 = r1.shouldInterceptTouchEvent(r10)
            androidx.customview.widget.ViewDragHelper r2 = r9.mRightDragger
            boolean r2 = r2.shouldInterceptTouchEvent(r10)
            r1 = r1 | r2
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x0094
            if (r0 == r3) goto L_0x008d
            r10 = 2
            if (r0 == r10) goto L_0x001f
            r10 = 3
            if (r0 == r10) goto L_0x008d
            goto L_0x0092
        L_0x001f:
            androidx.customview.widget.ViewDragHelper r10 = r9.mLeftDragger
            java.util.Objects.requireNonNull(r10)
            float[] r0 = r10.mInitialMotionX
            if (r0 != 0) goto L_0x0029
            goto L_0x0071
        L_0x0029:
            int r0 = r0.length
            r4 = r2
        L_0x002b:
            if (r4 >= r0) goto L_0x0071
            int r5 = r10.mPointersDown
            int r6 = r3 << r4
            r5 = r5 & r6
            if (r5 == 0) goto L_0x0036
            r5 = r3
            goto L_0x0037
        L_0x0036:
            r5 = r2
        L_0x0037:
            if (r5 != 0) goto L_0x003a
            goto L_0x0069
        L_0x003a:
            float[] r5 = r10.mInitialMotionX
            if (r5 == 0) goto L_0x0062
            float[] r6 = r10.mInitialMotionY
            if (r6 == 0) goto L_0x0062
            float[] r7 = r10.mLastMotionX
            if (r7 == 0) goto L_0x0062
            float[] r8 = r10.mLastMotionY
            if (r8 != 0) goto L_0x004b
            goto L_0x0062
        L_0x004b:
            r7 = r7[r4]
            r5 = r5[r4]
            float r7 = r7 - r5
            r5 = r8[r4]
            r6 = r6[r4]
            float r5 = r5 - r6
            float r7 = r7 * r7
            float r5 = r5 * r5
            float r5 = r5 + r7
            int r6 = r10.mTouchSlop
            int r6 = r6 * r6
            float r6 = (float) r6
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x0069
            r5 = r3
            goto L_0x006a
        L_0x0062:
            java.lang.String r5 = "ViewDragHelper"
            java.lang.String r6 = "Inconsistent pointer event stream: pointer is down, but there is no initial motion recorded. Is something intercepting or modifying events?"
            android.util.Log.w(r5, r6)
        L_0x0069:
            r5 = r2
        L_0x006a:
            if (r5 == 0) goto L_0x006e
            r10 = r3
            goto L_0x0072
        L_0x006e:
            int r4 = r4 + 1
            goto L_0x002b
        L_0x0071:
            r10 = r2
        L_0x0072:
            if (r10 == 0) goto L_0x0092
            androidx.drawerlayout.widget.DrawerLayout$ViewDragCallback r10 = r9.mLeftCallback
            java.util.Objects.requireNonNull(r10)
            androidx.drawerlayout.widget.DrawerLayout r0 = androidx.drawerlayout.widget.DrawerLayout.this
            androidx.drawerlayout.widget.DrawerLayout$ViewDragCallback$1 r10 = r10.mPeekRunnable
            r0.removeCallbacks(r10)
            androidx.drawerlayout.widget.DrawerLayout$ViewDragCallback r10 = r9.mRightCallback
            java.util.Objects.requireNonNull(r10)
            androidx.drawerlayout.widget.DrawerLayout r0 = androidx.drawerlayout.widget.DrawerLayout.this
            androidx.drawerlayout.widget.DrawerLayout$ViewDragCallback$1 r10 = r10.mPeekRunnable
            r0.removeCallbacks(r10)
            goto L_0x0092
        L_0x008d:
            r9.closeDrawers(r3)
            r9.mChildrenCanceledTouch = r2
        L_0x0092:
            r10 = r2
            goto L_0x00bc
        L_0x0094:
            float r0 = r10.getX()
            float r10 = r10.getY()
            r9.mInitialMotionX = r0
            r9.mInitialMotionY = r10
            float r4 = r9.mScrimOpacity
            r5 = 0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x00b9
            androidx.customview.widget.ViewDragHelper r4 = r9.mLeftDragger
            int r0 = (int) r0
            int r10 = (int) r10
            android.view.View r10 = r4.findTopChildUnder(r0, r10)
            if (r10 == 0) goto L_0x00b9
            boolean r10 = isContentView(r10)
            if (r10 == 0) goto L_0x00b9
            r10 = r3
            goto L_0x00ba
        L_0x00b9:
            r10 = r2
        L_0x00ba:
            r9.mChildrenCanceledTouch = r2
        L_0x00bc:
            if (r1 != 0) goto L_0x00e1
            if (r10 != 0) goto L_0x00e1
            int r10 = r9.getChildCount()
            r0 = r2
        L_0x00c5:
            if (r0 >= r10) goto L_0x00da
            android.view.View r1 = r9.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.drawerlayout.widget.DrawerLayout$LayoutParams r1 = (androidx.drawerlayout.widget.DrawerLayout.LayoutParams) r1
            boolean r1 = r1.isPeeking
            if (r1 == 0) goto L_0x00d7
            r10 = r3
            goto L_0x00db
        L_0x00d7:
            int r0 = r0 + 1
            goto L_0x00c5
        L_0x00da:
            r10 = r2
        L_0x00db:
            if (r10 != 0) goto L_0x00e1
            boolean r9 = r9.mChildrenCanceledTouch
            if (r9 == 0) goto L_0x00e2
        L_0x00e1:
            r2 = r3
        L_0x00e2:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public final Parcelable onSaveInstanceState() {
        boolean z;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int i2 = layoutParams.openState;
            boolean z2 = true;
            if (i2 == 1) {
                z = true;
            } else {
                z = false;
            }
            if (i2 != 2) {
                z2 = false;
            }
            if (z || z2) {
                savedState.openDrawerGravity = layoutParams.gravity;
            } else {
                i++;
            }
        }
        savedState.lockModeLeft = this.mLockModeLeft;
        savedState.lockModeRight = this.mLockModeRight;
        savedState.lockModeStart = this.mLockModeStart;
        savedState.lockModeEnd = this.mLockModeEnd;
        return savedState;
    }

    public final void openDrawer(View view) {
        if (isDrawerView(view)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.mFirstLayout) {
                layoutParams.onScreen = 1.0f;
                layoutParams.openState = 1;
                updateChildrenImportantForAccessibility(view, true);
                updateChildAccessibilityAction(view);
            } else {
                layoutParams.openState |= 2;
                if (checkDrawerViewAbsoluteGravity(view, 3)) {
                    this.mLeftDragger.smoothSlideViewTo(view, 0, view.getTop());
                } else {
                    this.mRightDragger.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop());
                }
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            closeDrawers(true);
        }
    }

    public final void updateChildrenImportantForAccessibility(View view, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((z || isDrawerView(childAt)) && (!z || childAt != view)) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setImportantForAccessibility(childAt, 4);
            } else {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.setImportantForAccessibility(childAt, 1);
            }
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity = 0;
        public boolean isPeeking;
        public float onScreen;
        public int openState;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
