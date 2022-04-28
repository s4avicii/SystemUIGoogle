package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import kotlinx.atomicfu.AtomicFU;

public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior {
    public int currentOffset;
    public int downPreScrollRange;
    public int downScrollRange;
    public ValueAnimator elevationOverlayAnimator;
    public boolean haveChildWithInterpolator;
    public WindowInsetsCompat lastInsets;
    public boolean liftOnScroll;
    public final ArrayList liftOnScrollListeners;
    public WeakReference<View> liftOnScrollTargetView;
    public int liftOnScrollTargetViewId;
    public boolean liftable;
    public boolean lifted;
    public ArrayList listeners;
    public int pendingAction;
    public Drawable statusBarForeground;
    public int[] tmpStatesArray;
    public int totalScrollRange;

    public static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior<T> {
        public WeakReference<View> lastNestedScrollingChildRef;
        public int lastStartedType;
        public ValueAnimator offsetAnimator;
        public int offsetDelta;
        public BaseDragCallback onDragCallback;
        public SavedState savedState;

        public static abstract class BaseDragCallback<T extends AppBarLayout> {
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
            public boolean firstVisibleChildAtMinimumHeight;
            public int firstVisibleChildIndex;
            public float firstVisibleChildPercentageShown;
            public boolean fullyScrolled;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                boolean z = true;
                this.fullyScrolled = parcel.readByte() != 0;
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibleChildPercentageShown = parcel.readFloat();
                this.firstVisibleChildAtMinimumHeight = parcel.readByte() == 0 ? false : z;
            }

            public final void writeToParcel(Parcel parcel, int i) {
                parcel.writeParcelable(this.mSuperState, i);
                parcel.writeByte(this.fullyScrolled ? (byte) 1 : 0);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte(this.firstVisibleChildAtMinimumHeight ? (byte) 1 : 0);
            }

            public SavedState(android.view.AbsSavedState absSavedState) {
                super(absSavedState);
            }
        }

        public BaseBehavior() {
        }

        public final /* bridge */ /* synthetic */ void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int[] iArr, int i3) {
            onNestedPreScroll(coordinatorLayout, (AppBarLayout) view, view2, i2, iArr);
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final boolean canDragView(View view) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (this.onDragCallback == null) {
                WeakReference<View> weakReference = this.lastNestedScrollingChildRef;
                if (weakReference == null) {
                    return true;
                }
                View view2 = weakReference.get();
                if (view2 == null || !view2.isShown() || view2.canScrollVertically(-1)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public final int getMaxDragOffset(View view) {
            return -((AppBarLayout) view).getDownNestedScrollRange();
        }

        public final int getScrollRangeForDragFling(View view) {
            return ((AppBarLayout) view).getTotalScrollRange();
        }

        public boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                return false;
            }
            return true;
        }

        public final void onFlingFinished(CoordinatorLayout coordinatorLayout, View view) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            snapToChildIfNeeded(coordinatorLayout, appBarLayout);
            if (appBarLayout.liftOnScroll) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(findFirstScrollingChild(coordinatorLayout)));
            }
        }

        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            boolean z;
            int i2;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            super.onLayoutChild(coordinatorLayout, appBarLayout, i);
            int i3 = appBarLayout.pendingAction;
            SavedState savedState2 = this.savedState;
            if (savedState2 == null || (i3 & 8) != 0) {
                if (i3 != 0) {
                    if ((i3 & 4) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if ((i3 & 2) != 0) {
                        int i4 = -appBarLayout.getTotalScrollRange();
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, i4);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, i4);
                        }
                    } else if ((i3 & 1) != 0) {
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, 0);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
                        }
                    }
                }
            } else if (savedState2.fullyScrolled) {
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, -appBarLayout.getTotalScrollRange());
            } else {
                View childAt = appBarLayout.getChildAt(savedState2.firstVisibleChildIndex);
                int i5 = -childAt.getBottom();
                if (this.savedState.firstVisibleChildAtMinimumHeight) {
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    i2 = appBarLayout.getTopInset() + ViewCompat.Api16Impl.getMinimumHeight(childAt) + i5;
                } else {
                    i2 = Math.round(((float) childAt.getHeight()) * this.savedState.firstVisibleChildPercentageShown) + i5;
                }
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, i2);
            }
            appBarLayout.pendingAction = 0;
            this.savedState = null;
            int clamp = AtomicFU.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0);
            ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
            if (viewOffsetHelper != null) {
                viewOffsetHelper.setTopAndBottomOffset(clamp);
            } else {
                this.tempTopBottomOffset = clamp;
            }
            updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, getTopAndBottomOffset(), 0, true);
            appBarLayout.onOffsetChanged(getTopAndBottomOffset());
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return true;
        }

        public final boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).height != -2) {
                return false;
            }
            coordinatorLayout.onMeasureChild(appBarLayout, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0));
            return true;
        }

        public final void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int[] iArr) {
            int i2;
            int i3;
            if (i != 0) {
                if (i < 0) {
                    i3 = -appBarLayout.getTotalScrollRange();
                    i2 = appBarLayout.getDownNestedPreScrollRange() + i3;
                } else {
                    Objects.requireNonNull(appBarLayout);
                    i3 = -appBarLayout.getTotalScrollRange();
                    i2 = 0;
                }
                int i4 = i3;
                int i5 = i2;
                if (i4 != i5) {
                    iArr[1] = setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, getTopBottomOffsetForScrollingSibling() - i, i4, i5);
                }
            }
            Objects.requireNonNull(appBarLayout);
            if (appBarLayout.liftOnScroll) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
            }
        }

        public final void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int[] iArr) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (i3 < 0) {
                CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
                AppBarLayout appBarLayout2 = appBarLayout;
                iArr[1] = setHeaderTopBottomOffset(coordinatorLayout2, appBarLayout2, getTopBottomOffsetForScrollingSibling() - i3, -appBarLayout.getDownNestedScrollRange(), 0);
            }
            if (i3 == 0) {
                updateAccessibilityActions(coordinatorLayout, appBarLayout);
            }
        }

        public final void onRestoreInstanceState(View view, Parcelable parcelable) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (parcelable instanceof SavedState) {
                this.savedState = (SavedState) parcelable;
            } else {
                this.savedState = null;
            }
        }

        public final Parcelable onSaveInstanceState(View view) {
            boolean z;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            android.view.AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = appBarLayout.getChildCount();
            boolean z2 = false;
            int i = 0;
            while (i < childCount) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset > 0 || bottom < 0) {
                    i++;
                } else {
                    SavedState savedState2 = new SavedState(absSavedState);
                    if ((-getTopAndBottomOffset()) >= appBarLayout.getTotalScrollRange()) {
                        z = true;
                    } else {
                        z = false;
                    }
                    savedState2.fullyScrolled = z;
                    savedState2.firstVisibleChildIndex = i;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (bottom == appBarLayout.getTopInset() + ViewCompat.Api16Impl.getMinimumHeight(childAt)) {
                        z2 = true;
                    }
                    savedState2.firstVisibleChildAtMinimumHeight = z2;
                    savedState2.firstVisibleChildPercentageShown = ((float) bottom) / ((float) childAt.getHeight());
                    return savedState2;
                }
            }
            return absSavedState;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
            if (r2 != false) goto L_0x002d;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onStartNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout r2, android.view.View r3, android.view.View r4, android.view.View r5, int r6, int r7) {
            /*
                r1 = this;
                com.google.android.material.appbar.AppBarLayout r3 = (com.google.android.material.appbar.AppBarLayout) r3
                r5 = r6 & 2
                r6 = 1
                r0 = 0
                if (r5 == 0) goto L_0x002c
                boolean r5 = r3.liftOnScroll
                if (r5 != 0) goto L_0x002d
                int r5 = r3.getTotalScrollRange()
                if (r5 == 0) goto L_0x0014
                r5 = r6
                goto L_0x0015
            L_0x0014:
                r5 = r0
            L_0x0015:
                if (r5 == 0) goto L_0x0028
                int r2 = r2.getHeight()
                int r4 = r4.getHeight()
                int r2 = r2 - r4
                int r3 = r3.getHeight()
                if (r2 > r3) goto L_0x0028
                r2 = r6
                goto L_0x0029
            L_0x0028:
                r2 = r0
            L_0x0029:
                if (r2 == 0) goto L_0x002c
                goto L_0x002d
            L_0x002c:
                r6 = r0
            L_0x002d:
                if (r6 == 0) goto L_0x0036
                android.animation.ValueAnimator r2 = r1.offsetAnimator
                if (r2 == 0) goto L_0x0036
                r2.cancel()
            L_0x0036:
                r2 = 0
                r1.lastNestedScrollingChildRef = r2
                r1.lastStartedType = r7
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.onStartNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.View, android.view.View, int, int):boolean");
        }

        public final void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            if (this.lastStartedType == 0 || i == 1) {
                snapToChildIfNeeded(coordinatorLayout, appBarLayout);
                if (appBarLayout.liftOnScroll) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(view2));
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference<>(view2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:30:0x0093  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x0098  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x00a4  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x0146  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int setHeaderTopBottomOffset(androidx.coordinatorlayout.widget.CoordinatorLayout r11, android.view.View r12, int r13, int r14, int r15) {
            /*
                r10 = this;
                com.google.android.material.appbar.AppBarLayout r12 = (com.google.android.material.appbar.AppBarLayout) r12
                int r0 = r10.getTopBottomOffsetForScrollingSibling()
                r1 = 0
                if (r14 == 0) goto L_0x014c
                if (r0 < r14) goto L_0x014c
                if (r0 > r15) goto L_0x014c
                int r13 = kotlinx.atomicfu.AtomicFU.clamp((int) r13, (int) r14, (int) r15)
                if (r0 == r13) goto L_0x014e
                java.util.Objects.requireNonNull(r12)
                boolean r14 = r12.haveChildWithInterpolator
                if (r14 == 0) goto L_0x008e
                int r14 = java.lang.Math.abs(r13)
                int r15 = r12.getChildCount()
                r2 = r1
            L_0x0023:
                if (r2 >= r15) goto L_0x008e
                android.view.View r3 = r12.getChildAt(r2)
                android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
                com.google.android.material.appbar.AppBarLayout$LayoutParams r4 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r4
                java.util.Objects.requireNonNull(r4)
                android.view.animation.Interpolator r5 = r4.scrollInterpolator
                int r6 = r3.getTop()
                if (r14 < r6) goto L_0x008b
                int r6 = r3.getBottom()
                if (r14 > r6) goto L_0x008b
                if (r5 == 0) goto L_0x008e
                int r15 = r4.scrollFlags
                r2 = r15 & 1
                if (r2 == 0) goto L_0x005f
                int r2 = r3.getHeight()
                int r6 = r4.topMargin
                int r2 = r2 + r6
                int r4 = r4.bottomMargin
                int r2 = r2 + r4
                int r2 = r2 + r1
                r15 = r15 & 2
                if (r15 == 0) goto L_0x0060
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r15 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                int r15 = androidx.core.view.ViewCompat.Api16Impl.getMinimumHeight(r3)
                int r2 = r2 - r15
                goto L_0x0060
            L_0x005f:
                r2 = r1
            L_0x0060:
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r15 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                boolean r15 = androidx.core.view.ViewCompat.Api16Impl.getFitsSystemWindows(r3)
                if (r15 == 0) goto L_0x006d
                int r15 = r12.getTopInset()
                int r2 = r2 - r15
            L_0x006d:
                if (r2 <= 0) goto L_0x008e
                int r15 = r3.getTop()
                int r14 = r14 - r15
                float r15 = (float) r2
                float r14 = (float) r14
                float r14 = r14 / r15
                float r14 = r5.getInterpolation(r14)
                float r14 = r14 * r15
                int r14 = java.lang.Math.round(r14)
                int r15 = java.lang.Integer.signum(r13)
                int r2 = r3.getTop()
                int r2 = r2 + r14
                int r2 = r2 * r15
                goto L_0x008f
            L_0x008b:
                int r2 = r2 + 1
                goto L_0x0023
            L_0x008e:
                r2 = r13
            L_0x008f:
                com.google.android.material.appbar.ViewOffsetHelper r14 = r10.viewOffsetHelper
                if (r14 == 0) goto L_0x0098
                boolean r14 = r14.setTopAndBottomOffset(r2)
                goto L_0x009b
            L_0x0098:
                r10.tempTopBottomOffset = r2
                r14 = r1
            L_0x009b:
                int r15 = r0 - r13
                int r2 = r13 - r2
                r10.offsetDelta = r2
                r2 = 1
                if (r14 == 0) goto L_0x0134
                r3 = r1
            L_0x00a5:
                int r4 = r12.getChildCount()
                if (r3 >= r4) goto L_0x0134
                android.view.View r4 = r12.getChildAt(r3)
                android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
                com.google.android.material.appbar.AppBarLayout$LayoutParams r4 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r4
                java.util.Objects.requireNonNull(r4)
                com.google.android.material.appbar.AppBarLayout$ChildScrollEffect r5 = r4.scrollEffect
                if (r5 == 0) goto L_0x0130
                int r4 = r4.scrollFlags
                r4 = r4 & r2
                if (r4 == 0) goto L_0x0130
                android.view.View r4 = r12.getChildAt(r3)
                int r6 = r10.getTopAndBottomOffset()
                float r6 = (float) r6
                com.google.android.material.appbar.AppBarLayout$CompressChildScrollEffect r5 = (com.google.android.material.appbar.AppBarLayout.CompressChildScrollEffect) r5
                android.graphics.Rect r7 = r5.relativeRect
                r4.getDrawingRect(r7)
                r12.offsetDescendantRectToMyCoords(r4, r7)
                int r8 = r12.getTopInset()
                int r8 = -r8
                r7.offset(r1, r8)
                android.graphics.Rect r7 = r5.relativeRect
                int r7 = r7.top
                float r7 = (float) r7
                float r6 = java.lang.Math.abs(r6)
                float r7 = r7 - r6
                r6 = 0
                int r8 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                if (r8 > 0) goto L_0x0127
                android.graphics.Rect r8 = r5.relativeRect
                int r8 = r8.height()
                float r8 = (float) r8
                float r8 = r7 / r8
                float r8 = java.lang.Math.abs(r8)
                r9 = 1065353216(0x3f800000, float:1.0)
                float r6 = kotlinx.atomicfu.AtomicFU.clamp((float) r8, (float) r6, (float) r9)
                float r7 = -r7
                float r6 = r9 - r6
                float r6 = r6 * r6
                float r9 = r9 - r6
                android.graphics.Rect r6 = r5.relativeRect
                int r6 = r6.height()
                float r6 = (float) r6
                r8 = 1050253722(0x3e99999a, float:0.3)
                float r6 = r6 * r8
                float r6 = r6 * r9
                float r7 = r7 - r6
                r4.setTranslationY(r7)
                android.graphics.Rect r6 = r5.ghostRect
                r4.getDrawingRect(r6)
                android.graphics.Rect r6 = r5.ghostRect
                float r7 = -r7
                int r7 = (int) r7
                r6.offset(r1, r7)
                android.graphics.Rect r5 = r5.ghostRect
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                androidx.core.view.ViewCompat.Api18Impl.setClipBounds(r4, r5)
                goto L_0x0130
            L_0x0127:
                r5 = 0
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r7 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                androidx.core.view.ViewCompat.Api18Impl.setClipBounds(r4, r5)
                r4.setTranslationY(r6)
            L_0x0130:
                int r3 = r3 + 1
                goto L_0x00a5
            L_0x0134:
                if (r14 != 0) goto L_0x013d
                boolean r14 = r12.haveChildWithInterpolator
                if (r14 == 0) goto L_0x013d
                r11.dispatchDependentViewsChanged(r12)
            L_0x013d:
                int r14 = r10.getTopAndBottomOffset()
                r12.onOffsetChanged(r14)
                if (r13 >= r0) goto L_0x0147
                r2 = -1
            L_0x0147:
                updateAppBarLayoutDrawableState(r11, r12, r13, r2, r1)
                r1 = r15
                goto L_0x014e
            L_0x014c:
                r10.offsetDelta = r1
            L_0x014e:
                r10.updateAccessibilityActions(r11, r12)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.setHeaderTopBottomOffset(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, int, int, int):int");
        }

        public final void updateAccessibilityActions(CoordinatorLayout coordinatorLayout, final T t) {
            AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD;
            ViewCompat.removeActionWithId(accessibilityActionCompat.getId(), coordinatorLayout);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat2 = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD;
            ViewCompat.removeActionWithId(accessibilityActionCompat2.getId(), coordinatorLayout);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            final View findFirstScrollingChild = findFirstScrollingChild(coordinatorLayout);
            if (findFirstScrollingChild != null && t.getTotalScrollRange() != 0) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) findFirstScrollingChild.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                if (layoutParams.mBehavior instanceof ScrollingViewBehavior) {
                    if (getTopBottomOffsetForScrollingSibling() != (-t.getTotalScrollRange()) && findFirstScrollingChild.canScrollVertically(1)) {
                        ViewCompat.replaceAccessibilityAction(coordinatorLayout, accessibilityActionCompat, (String) null, new AccessibilityViewCommand(false) {
                            public final boolean perform(View view) {
                                AppBarLayout appBarLayout = AppBarLayout.this;
                                boolean z = true;
                                Objects.requireNonNull(appBarLayout);
                                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                appBarLayout.setExpanded(z, ViewCompat.Api19Impl.isLaidOut(appBarLayout), true);
                                return true;
                            }
                        });
                    }
                    if (getTopBottomOffsetForScrollingSibling() == 0) {
                        return;
                    }
                    if (findFirstScrollingChild.canScrollVertically(-1)) {
                        final int i = -t.getDownNestedPreScrollRange();
                        if (i != 0) {
                            final CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
                            final T t2 = t;
                            ViewCompat.replaceAccessibilityAction(coordinatorLayout, accessibilityActionCompat2, (String) null, new AccessibilityViewCommand() {
                                public final boolean perform(View view) {
                                    BaseBehavior.this.onNestedPreScroll(coordinatorLayout2, t2, findFirstScrollingChild, i, new int[]{0, 0});
                                    return true;
                                }
                            });
                            return;
                        }
                        return;
                    }
                    ViewCompat.replaceAccessibilityAction(coordinatorLayout, accessibilityActionCompat2, (String) null, new AccessibilityViewCommand(true) {
                        public final boolean perform(View view) {
                            AppBarLayout appBarLayout = AppBarLayout.this;
                            boolean z = true;
                            Objects.requireNonNull(appBarLayout);
                            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            appBarLayout.setExpanded(z, ViewCompat.Api19Impl.isLaidOut(appBarLayout), true);
                            return true;
                        }
                    });
                }
            }
        }

        public static View findFirstScrollingChild(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if ((childAt instanceof NestedScrollingChild) || (childAt instanceof ListView) || (childAt instanceof ScrollView)) {
                    return childAt;
                }
            }
            return null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:27:0x0066  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0074  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void updateAppBarLayoutDrawableState(androidx.coordinatorlayout.widget.CoordinatorLayout r7, com.google.android.material.appbar.AppBarLayout r8, int r9, int r10, boolean r11) {
            /*
                int r0 = java.lang.Math.abs(r9)
                int r1 = r8.getChildCount()
                r2 = 0
                r3 = r2
            L_0x000a:
                r4 = 0
                if (r3 >= r1) goto L_0x0021
                android.view.View r5 = r8.getChildAt(r3)
                int r6 = r5.getTop()
                if (r0 < r6) goto L_0x001e
                int r6 = r5.getBottom()
                if (r0 > r6) goto L_0x001e
                goto L_0x0022
            L_0x001e:
                int r3 = r3 + 1
                goto L_0x000a
            L_0x0021:
                r5 = r4
            L_0x0022:
                r0 = 1
                if (r5 == 0) goto L_0x0061
                android.view.ViewGroup$LayoutParams r1 = r5.getLayoutParams()
                com.google.android.material.appbar.AppBarLayout$LayoutParams r1 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r1
                java.util.Objects.requireNonNull(r1)
                int r1 = r1.scrollFlags
                r3 = r1 & 1
                if (r3 == 0) goto L_0x0061
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                int r3 = androidx.core.view.ViewCompat.Api16Impl.getMinimumHeight(r5)
                if (r10 <= 0) goto L_0x004e
                r10 = r1 & 12
                if (r10 == 0) goto L_0x004e
                int r9 = -r9
                int r10 = r5.getBottom()
                int r10 = r10 - r3
                int r1 = r8.getTopInset()
                int r10 = r10 - r1
                if (r9 < r10) goto L_0x0061
                goto L_0x005f
            L_0x004e:
                r10 = r1 & 2
                if (r10 == 0) goto L_0x0061
                int r9 = -r9
                int r10 = r5.getBottom()
                int r10 = r10 - r3
                int r1 = r8.getTopInset()
                int r10 = r10 - r1
                if (r9 < r10) goto L_0x0061
            L_0x005f:
                r9 = r0
                goto L_0x0062
            L_0x0061:
                r9 = r2
            L_0x0062:
                boolean r10 = r8.liftOnScroll
                if (r10 == 0) goto L_0x006e
                android.view.View r9 = findFirstScrollingChild(r7)
                boolean r9 = r8.shouldLift(r9)
            L_0x006e:
                boolean r9 = r8.setLiftedState(r9)
                if (r11 != 0) goto L_0x00c5
                if (r9 == 0) goto L_0x00c8
                java.util.Objects.requireNonNull(r7)
                androidx.coordinatorlayout.widget.DirectedAcyclicGraph r7 = r7.mChildDag
                java.util.Objects.requireNonNull(r7)
                java.lang.Object r7 = r7.mGraph
                androidx.collection.SimpleArrayMap r7 = (androidx.collection.SimpleArrayMap) r7
                java.util.Objects.requireNonNull(r7)
                java.lang.Object r7 = r7.getOrDefault(r8, r4)
                java.util.ArrayList r7 = (java.util.ArrayList) r7
                if (r7 != 0) goto L_0x008e
                goto L_0x0093
            L_0x008e:
                java.util.ArrayList r4 = new java.util.ArrayList
                r4.<init>(r7)
            L_0x0093:
                if (r4 != 0) goto L_0x0099
                java.util.List r4 = java.util.Collections.emptyList()
            L_0x0099:
                int r7 = r4.size()
                r9 = r2
            L_0x009e:
                if (r9 >= r7) goto L_0x00c3
                java.lang.Object r10 = r4.get(r9)
                android.view.View r10 = (android.view.View) r10
                android.view.ViewGroup$LayoutParams r10 = r10.getLayoutParams()
                androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r10 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r10
                java.util.Objects.requireNonNull(r10)
                androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r10 = r10.mBehavior
                boolean r11 = r10 instanceof com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior
                if (r11 == 0) goto L_0x00c0
                com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior r10 = (com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior) r10
                java.util.Objects.requireNonNull(r10)
                int r7 = r10.overlayTop
                if (r7 == 0) goto L_0x00c3
                r2 = r0
                goto L_0x00c3
            L_0x00c0:
                int r9 = r9 + 1
                goto L_0x009e
            L_0x00c3:
                if (r2 == 0) goto L_0x00c8
            L_0x00c5:
                r8.jumpDrawablesToCurrentState()
            L_0x00c8:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.updateAppBarLayoutDrawableState(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, int, int, boolean):void");
        }

        public final void animateOffsetTo(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i) {
            int i2;
            int abs = Math.abs(getTopBottomOffsetForScrollingSibling() - i);
            float abs2 = Math.abs(0.0f);
            if (abs2 > 0.0f) {
                i2 = Math.round((((float) abs) / abs2) * 1000.0f) * 3;
            } else {
                i2 = (int) (((((float) abs) / ((float) appBarLayout.getHeight())) + 1.0f) * 150.0f);
            }
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            if (topBottomOffsetForScrollingSibling == i) {
                ValueAnimator valueAnimator = this.offsetAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.offsetAnimator.cancel();
                    return;
                }
                return;
            }
            ValueAnimator valueAnimator2 = this.offsetAnimator;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.offsetAnimator = valueAnimator3;
                valueAnimator3.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BaseBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
            } else {
                valueAnimator2.cancel();
            }
            this.offsetAnimator.setDuration((long) Math.min(i2, 600));
            this.offsetAnimator.setIntValues(new int[]{topBottomOffsetForScrollingSibling, i});
            this.offsetAnimator.start();
        }

        public final int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public final void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, T t) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int childCount = t.getChildCount();
            int i = 0;
            while (true) {
                z = true;
                if (i >= childCount) {
                    i = -1;
                    break;
                }
                View childAt = t.getChildAt(i);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                if ((layoutParams.scrollFlags & 32) == 32) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (z4) {
                    top -= layoutParams.topMargin;
                    bottom += layoutParams.bottomMargin;
                }
                int i2 = -topBottomOffsetForScrollingSibling;
                if (top <= i2 && bottom >= i2) {
                    break;
                }
                i++;
            }
            if (i >= 0) {
                View childAt2 = t.getChildAt(i);
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                Objects.requireNonNull(layoutParams2);
                int i3 = layoutParams2.scrollFlags;
                if ((i3 & 17) == 17) {
                    int i4 = -childAt2.getTop();
                    int i5 = -childAt2.getBottom();
                    if (i == t.getChildCount() - 1) {
                        i5 += t.getPaddingTop() + t.getTopInset();
                    }
                    if ((i3 & 2) == 2) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        i5 += ViewCompat.Api16Impl.getMinimumHeight(childAt2);
                    } else {
                        if ((i3 & 5) == 5) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (z3) {
                            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                            int minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(childAt2) + i5;
                            if (topBottomOffsetForScrollingSibling < minimumHeight) {
                                i4 = minimumHeight;
                            } else {
                                i5 = minimumHeight;
                            }
                        }
                    }
                    if ((i3 & 32) != 32) {
                        z = false;
                    }
                    if (z) {
                        i4 += layoutParams2.topMargin;
                        i5 -= layoutParams2.bottomMargin;
                    }
                    if (topBottomOffsetForScrollingSibling < (i5 + i4) / 2) {
                        i4 = i5;
                    }
                    animateOffsetTo(coordinatorLayout, t, AtomicFU.clamp(i4, -t.getTotalScrollRange(), 0));
                }
            }
        }
    }

    public interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
        void onOffsetChanged(int i);
    }

    public static class Behavior extends BaseBehavior<AppBarLayout> {

        public static abstract class DragCallback extends BaseBehavior.BaseDragCallback<AppBarLayout> {
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static abstract class ChildScrollEffect {
    }

    public static class CompressChildScrollEffect extends ChildScrollEffect {
        public final Rect ghostRect = new Rect();
        public final Rect relativeRect = new Rect();
    }

    public interface LiftOnScrollListener {
        void onUpdate();
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            this.overlayTop = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            obtainStyledAttributes.recycle();
        }

        public final float getOverlapRatioForOffset(View view) {
            int i;
            int i2;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                CoordinatorLayout.Behavior behavior = layoutParams.mBehavior;
                if (behavior instanceof BaseBehavior) {
                    i = ((BaseBehavior) behavior).getTopBottomOffsetForScrollingSibling();
                } else {
                    i = 0;
                }
                if ((downNestedPreScrollRange == 0 || totalScrollRange + i > downNestedPreScrollRange) && (i2 = totalScrollRange - downNestedPreScrollRange) != 0) {
                    return (((float) i) / ((float) i2)) + 1.0f;
                }
            }
            return 0.0f;
        }

        public final int getScrollRange(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout) view).getTotalScrollRange();
            }
            return view.getMeasuredHeight();
        }

        public final void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view) {
            if (view instanceof AppBarLayout) {
                ViewCompat.removeActionWithId(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId(), coordinatorLayout);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
                ViewCompat.removeActionWithId(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId(), coordinatorLayout);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            }
        }

        public final AppBarLayout findFirstDependency$1(List list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = (View) list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            int i;
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view2.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            CoordinatorLayout.Behavior behavior = layoutParams.mBehavior;
            if (behavior instanceof BaseBehavior) {
                int bottom = (view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta + this.verticalLayoutGap;
                if (this.overlayTop == 0) {
                    i = 0;
                } else {
                    float overlapRatioForOffset = getOverlapRatioForOffset(view2);
                    int i2 = this.overlayTop;
                    i = AtomicFU.clamp((int) (overlapRatioForOffset * ((float) i2)), 0, i2);
                }
                int i3 = bottom - i;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(i3);
            }
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.liftOnScroll) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
                }
            }
            return false;
        }

        public final boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout appBarLayout;
            List<View> dependencies = coordinatorLayout.getDependencies(view);
            int size = dependencies.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    appBarLayout = null;
                    break;
                }
                View view2 = dependencies.get(i);
                if (view2 instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) view2;
                    break;
                }
                i++;
            }
            if (appBarLayout != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.tempRect1;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    appBarLayout.setExpanded(false, !z, true);
                    return true;
                }
            }
            return false;
        }

        public final boolean layoutDependsOn(View view, View view2) {
            return view2 instanceof AppBarLayout;
        }
    }

    public AppBarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final void setOrientation(int i) {
        if (i == 1) {
            super.setOrientation(i);
            return;
        }
        throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.appBarLayoutStyle);
    }

    /* renamed from: generateDefaultLayoutParams  reason: collision with other method in class */
    public final LinearLayout.LayoutParams m298generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final CoordinatorLayout.Behavior<AppBarLayout> getBehavior() {
        return new Behavior();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getDownNestedPreScrollRange() {
        /*
            r9 = this;
            int r0 = r9.downPreScrollRange
            r1 = -1
            if (r0 == r1) goto L_0x0006
            return r0
        L_0x0006:
            int r0 = r9.getChildCount()
            int r0 = r0 + -1
            r1 = 0
            r2 = r1
        L_0x000e:
            if (r0 < 0) goto L_0x0060
            android.view.View r3 = r9.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r4 = r3.getLayoutParams()
            com.google.android.material.appbar.AppBarLayout$LayoutParams r4 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r4
            int r5 = r3.getMeasuredHeight()
            int r6 = r4.scrollFlags
            r7 = r6 & 5
            r8 = 5
            if (r7 != r8) goto L_0x005a
            int r7 = r4.topMargin
            int r4 = r4.bottomMargin
            int r7 = r7 + r4
            r4 = r6 & 8
            if (r4 == 0) goto L_0x0036
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r4 = androidx.core.view.ViewCompat.Api16Impl.getMinimumHeight(r3)
        L_0x0034:
            int r4 = r4 + r7
            goto L_0x0045
        L_0x0036:
            r4 = r6 & 2
            if (r4 == 0) goto L_0x0043
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r4 = androidx.core.view.ViewCompat.Api16Impl.getMinimumHeight(r3)
            int r4 = r5 - r4
            goto L_0x0034
        L_0x0043:
            int r4 = r7 + r5
        L_0x0045:
            if (r0 != 0) goto L_0x0058
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r3 = androidx.core.view.ViewCompat.Api16Impl.getFitsSystemWindows(r3)
            if (r3 == 0) goto L_0x0058
            int r3 = r9.getTopInset()
            int r5 = r5 - r3
            int r4 = java.lang.Math.min(r4, r5)
        L_0x0058:
            int r2 = r2 + r4
            goto L_0x005d
        L_0x005a:
            if (r2 <= 0) goto L_0x005d
            goto L_0x0060
        L_0x005d:
            int r0 = r0 + -1
            goto L_0x000e
        L_0x0060:
            int r0 = java.lang.Math.max(r1, r2)
            r9.downPreScrollRange = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.getDownNestedPreScrollRange():int");
    }

    public final int getDownNestedScrollRange() {
        int i = this.downScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = layoutParams.topMargin + layoutParams.bottomMargin + childAt.getMeasuredHeight();
            int i4 = layoutParams.scrollFlags;
            if ((i4 & 1) == 0) {
                break;
            }
            i3 += measuredHeight;
            if ((i4 & 2) != 0) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                i3 -= ViewCompat.Api16Impl.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.downScrollRange = max;
        return max;
    }

    public final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int i = this.totalScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i4 = layoutParams.scrollFlags;
            if ((i4 & 1) == 0) {
                break;
            }
            int i5 = measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin + i3;
            if (i2 == 0) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
                    i5 -= getTopInset();
                }
            }
            i3 = i5;
            if ((i4 & 2) != 0) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                i3 -= ViewCompat.Api16Impl.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.totalScrollRange = max;
        return max;
    }

    public final int[] onCreateDrawableState(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] iArr = this.tmpStatesArray;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        boolean z = this.liftable;
        if (z) {
            i2 = C1777R.attr.state_liftable;
        } else {
            i2 = -2130969767;
        }
        iArr[0] = i2;
        if (!z || !this.lifted) {
            i3 = -2130969768;
        } else {
            i3 = C1777R.attr.state_lifted;
        }
        iArr[1] = i3;
        if (z) {
            i4 = C1777R.attr.state_collapsible;
        } else {
            i4 = -2130969764;
        }
        iArr[2] = i4;
        if (!z || !this.lifted) {
            i5 = -2130969763;
        } else {
            i5 = C1777R.attr.state_collapsed;
        }
        iArr[3] = i5;
        return View.mergeDrawableStates(onCreateDrawableState, iArr);
    }

    public final void onOffsetChanged(int i) {
        this.currentOffset = i;
        if (!willNotDraw()) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }
        ArrayList arrayList = this.listeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                BaseOnOffsetChangedListener baseOnOffsetChangedListener = (BaseOnOffsetChangedListener) this.listeners.get(i2);
                if (baseOnOffsetChangedListener != null) {
                    baseOnOffsetChangedListener.onOffsetChanged(i);
                }
            }
        }
    }

    public final void setExpanded(boolean z, boolean z2, boolean z3) {
        int i;
        int i2;
        if (z) {
            i = 1;
        } else {
            i = 2;
        }
        int i3 = 0;
        if (z2) {
            i2 = 4;
        } else {
            i2 = 0;
        }
        int i4 = i | i2;
        if (z3) {
            i3 = 8;
        }
        this.pendingAction = i4 | i3;
        requestLayout();
    }

    public final boolean setLiftedState(boolean z) {
        float f;
        if (this.lifted == z) {
            return false;
        }
        this.lifted = z;
        refreshDrawableState();
        if (this.liftOnScroll && (getBackground() instanceof MaterialShapeDrawable)) {
            final MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) getBackground();
            float dimension = getResources().getDimension(C1777R.dimen.design_appbar_elevation);
            if (z) {
                f = 0.0f;
            } else {
                f = dimension;
            }
            if (!z) {
                dimension = 0.0f;
            }
            ValueAnimator valueAnimator = this.elevationOverlayAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, dimension});
            this.elevationOverlayAnimator = ofFloat;
            ofFloat.setDuration((long) getResources().getInteger(C1777R.integer.app_bar_elevation_anim_duration));
            this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
            this.elevationOverlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    materialShapeDrawable.setElevation(floatValue);
                    Drawable drawable = AppBarLayout.this.statusBarForeground;
                    if (drawable instanceof MaterialShapeDrawable) {
                        ((MaterialShapeDrawable) drawable).setElevation(floatValue);
                    }
                    Iterator it = AppBarLayout.this.liftOnScrollListeners.iterator();
                    while (it.hasNext()) {
                        Objects.requireNonNull(materialShapeDrawable);
                        ((LiftOnScrollListener) it.next()).onUpdate();
                    }
                }
            });
            this.elevationOverlayAnimator.start();
        }
        return true;
    }

    public final boolean shouldLift(View view) {
        int i;
        View view2;
        View view3 = null;
        if (this.liftOnScrollTargetView == null && (i = this.liftOnScrollTargetViewId) != -1) {
            if (view != null) {
                view2 = view.findViewById(i);
            } else {
                view2 = null;
            }
            if (view2 == null && (getParent() instanceof ViewGroup)) {
                view2 = ((ViewGroup) getParent()).findViewById(this.liftOnScrollTargetViewId);
            }
            if (view2 != null) {
                this.liftOnScrollTargetView = new WeakReference<>(view2);
            }
        }
        WeakReference<View> weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            view3 = weakReference.get();
        }
        if (view3 != null) {
            view = view3;
        }
        if (view == null || (!view.canScrollVertically(-1) && view.getScrollY() <= 0)) {
            return false;
        }
        return true;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AppBarLayout(android.content.Context r16, android.util.AttributeSet r17, int r18) {
        /*
            r15 = this;
            r0 = r15
            r7 = r17
            r8 = r18
            r1 = 2132018383(0x7f1404cf, float:1.9675071E38)
            r2 = r16
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r2, r7, r8, r1)
            r15.<init>(r1, r7, r8)
            r9 = -1
            r0.totalScrollRange = r9
            r0.downPreScrollRange = r9
            r0.downScrollRange = r9
            r10 = 0
            r0.pendingAction = r10
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.liftOnScrollListeners = r1
            android.content.Context r11 = r15.getContext()
            r12 = 1
            r15.setOrientation(r12)
            android.view.ViewOutlineProvider r1 = r15.getOutlineProvider()
            android.view.ViewOutlineProvider r2 = android.view.ViewOutlineProvider.BACKGROUND
            if (r1 != r2) goto L_0x0037
            android.view.ViewOutlineProvider r1 = android.view.ViewOutlineProvider.BOUNDS
            r15.setOutlineProvider(r1)
        L_0x0037:
            r5 = 2132018383(0x7f1404cf, float:1.9675071E38)
            android.content.Context r13 = r15.getContext()
            int[] r3 = com.google.android.material.appbar.ViewUtilsLollipop.STATE_LIST_ANIM_ATTRS
            int[] r6 = new int[r10]
            r1 = r13
            r2 = r17
            r4 = r18
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            boolean r2 = r1.hasValue(r10)     // Catch:{ all -> 0x0196 }
            if (r2 == 0) goto L_0x005c
            int r2 = r1.getResourceId(r10, r10)     // Catch:{ all -> 0x0196 }
            android.animation.StateListAnimator r2 = android.animation.AnimatorInflater.loadStateListAnimator(r13, r2)     // Catch:{ all -> 0x0196 }
            r15.setStateListAnimator(r2)     // Catch:{ all -> 0x0196 }
        L_0x005c:
            r1.recycle()
            int[] r3 = com.google.android.material.R$styleable.AppBarLayout
            r5 = 2132018383(0x7f1404cf, float:1.9675071E38)
            int[] r6 = new int[r10]
            r1 = r11
            r2 = r17
            r4 = r18
            android.content.res.TypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            android.graphics.drawable.Drawable r2 = r1.getDrawable(r10)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r15, r2)
            android.graphics.drawable.Drawable r2 = r15.getBackground()
            boolean r2 = r2 instanceof android.graphics.drawable.ColorDrawable
            if (r2 == 0) goto L_0x009c
            android.graphics.drawable.Drawable r2 = r15.getBackground()
            android.graphics.drawable.ColorDrawable r2 = (android.graphics.drawable.ColorDrawable) r2
            com.google.android.material.shape.MaterialShapeDrawable r3 = new com.google.android.material.shape.MaterialShapeDrawable
            r3.<init>()
            int r2 = r2.getColor()
            android.content.res.ColorStateList r2 = android.content.res.ColorStateList.valueOf(r2)
            r3.setFillColor(r2)
            r3.initializeElevationOverlay(r11)
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r15, r3)
        L_0x009c:
            r2 = 4
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x00aa
            boolean r2 = r1.getBoolean(r2, r10)
            r15.setExpanded(r2, r10, r10)
        L_0x00aa:
            r2 = 3
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x010a
            int r3 = r1.getDimensionPixelSize(r2, r10)
            float r3 = (float) r3
            android.content.res.Resources r4 = r15.getResources()
            r5 = 2131492867(0x7f0c0003, float:1.8609198E38)
            int r4 = r4.getInteger(r5)
            android.animation.StateListAnimator r5 = new android.animation.StateListAnimator
            r5.<init>()
            int[] r2 = new int[r2]
            r2 = {16842910, 2130969767, -2130969768} // fill-array
            r6 = 16842910(0x101009e, float:2.3694E-38)
            float[] r7 = new float[r12]
            r8 = 0
            r7[r10] = r8
            java.lang.String r11 = "elevation"
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r15, r11, r7)
            long r13 = (long) r4
            android.animation.ObjectAnimator r4 = r7.setDuration(r13)
            r5.addState(r2, r4)
            int[] r2 = new int[r12]
            r2[r10] = r6
            float[] r4 = new float[r12]
            r4[r10] = r3
            android.animation.ObjectAnimator r3 = android.animation.ObjectAnimator.ofFloat(r15, r11, r4)
            android.animation.ObjectAnimator r3 = r3.setDuration(r13)
            r5.addState(r2, r3)
            int[] r2 = new int[r10]
            float[] r3 = new float[r12]
            r3[r10] = r8
            android.animation.ObjectAnimator r3 = android.animation.ObjectAnimator.ofFloat(r15, r11, r3)
            r6 = 0
            android.animation.ObjectAnimator r3 = r3.setDuration(r6)
            r5.addState(r2, r3)
            r15.setStateListAnimator(r5)
        L_0x010a:
            r2 = 2
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x0118
            boolean r2 = r1.getBoolean(r2, r10)
            r15.setKeyboardNavigationCluster(r2)
        L_0x0118:
            boolean r2 = r1.hasValue(r12)
            if (r2 == 0) goto L_0x0125
            boolean r2 = r1.getBoolean(r12, r10)
            r15.setTouchscreenBlocksFocus(r2)
        L_0x0125:
            r2 = 5
            boolean r2 = r1.getBoolean(r2, r10)
            r0.liftOnScroll = r2
            r2 = 6
            int r2 = r1.getResourceId(r2, r9)
            r0.liftOnScrollTargetViewId = r2
            r2 = 7
            android.graphics.drawable.Drawable r2 = r1.getDrawable(r2)
            android.graphics.drawable.Drawable r3 = r0.statusBarForeground
            if (r3 == r2) goto L_0x018a
            r4 = 0
            if (r3 == 0) goto L_0x0142
            r3.setCallback(r4)
        L_0x0142:
            if (r2 == 0) goto L_0x0148
            android.graphics.drawable.Drawable r4 = r2.mutate()
        L_0x0148:
            r0.statusBarForeground = r4
            if (r4 == 0) goto L_0x0177
            boolean r2 = r4.isStateful()
            if (r2 == 0) goto L_0x015b
            android.graphics.drawable.Drawable r2 = r0.statusBarForeground
            int[] r3 = r15.getDrawableState()
            r2.setState(r3)
        L_0x015b:
            android.graphics.drawable.Drawable r2 = r0.statusBarForeground
            int r3 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r15)
            r2.setLayoutDirection(r3)
            android.graphics.drawable.Drawable r2 = r0.statusBarForeground
            int r3 = r15.getVisibility()
            if (r3 != 0) goto L_0x016e
            r3 = r12
            goto L_0x016f
        L_0x016e:
            r3 = r10
        L_0x016f:
            r2.setVisible(r3, r10)
            android.graphics.drawable.Drawable r2 = r0.statusBarForeground
            r2.setCallback(r15)
        L_0x0177:
            android.graphics.drawable.Drawable r2 = r0.statusBarForeground
            if (r2 == 0) goto L_0x0182
            int r2 = r15.getTopInset()
            if (r2 <= 0) goto L_0x0182
            r10 = r12
        L_0x0182:
            r2 = r10 ^ 1
            r15.setWillNotDraw(r2)
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r15)
        L_0x018a:
            r1.recycle()
            com.google.android.material.appbar.AppBarLayout$1 r1 = new com.google.android.material.appbar.AppBarLayout$1
            r1.<init>()
            androidx.core.view.ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(r15, r1)
            return
        L_0x0196:
            r0 = move-exception
            r1.recycle()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public final void draw(Canvas canvas) {
        boolean z;
        super.draw(canvas);
        if (this.statusBarForeground == null || getTopInset() <= 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            int save = canvas.save();
            canvas.translate(0.0f, (float) (-this.currentOffset));
            this.statusBarForeground.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarForeground;
        if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
            invalidateDrawable(drawable);
        }
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(this);
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            if (childCount >= 1) {
                minimumHeight = ViewCompat.Api16Impl.getMinimumHeight(getChildAt(childCount - 1));
            } else {
                minimumHeight = 0;
            }
            if (minimumHeight == 0) {
                return getHeight() / 3;
            }
        }
        return (minimumHeight * 2) + topInset;
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        R$bool.setParentAbsoluteElevation(this);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeakReference<View> weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        boolean z3;
        super.onLayout(z, i, i2, i3, i4);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z4 = true;
        if (ViewCompat.Api16Impl.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
            int topInset = getTopInset();
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                getChildAt(childCount).offsetTopAndBottom(topInset);
            }
        }
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        this.haveChildWithInterpolator = false;
        int childCount2 = getChildCount();
        int i5 = 0;
        while (true) {
            if (i5 >= childCount2) {
                break;
            }
            LayoutParams layoutParams = (LayoutParams) getChildAt(i5).getLayoutParams();
            Objects.requireNonNull(layoutParams);
            if (layoutParams.scrollInterpolator != null) {
                this.haveChildWithInterpolator = true;
                break;
            }
            i5++;
        }
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.liftOnScroll) {
            int childCount3 = getChildCount();
            int i6 = 0;
            while (true) {
                if (i6 >= childCount3) {
                    z2 = false;
                    break;
                }
                LayoutParams layoutParams2 = (LayoutParams) getChildAt(i6).getLayoutParams();
                Objects.requireNonNull(layoutParams2);
                int i7 = layoutParams2.scrollFlags;
                if ((i7 & 1) != 1 || (i7 & 10) == 0) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (z3) {
                    z2 = true;
                    break;
                }
                i6++;
            }
            if (!z2) {
                z4 = false;
            }
        }
        if (this.liftable != z4) {
            this.liftable = z4;
            refreshDrawableState();
        }
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getFitsSystemWindows(this) && shouldOffsetFirstChild()) {
                int measuredHeight = getMeasuredHeight();
                if (mode == Integer.MIN_VALUE) {
                    measuredHeight = AtomicFU.clamp(getTopInset() + getMeasuredHeight(), 0, View.MeasureSpec.getSize(i2));
                } else if (mode == 0) {
                    measuredHeight += getTopInset();
                }
                setMeasuredDimension(getMeasuredWidth(), measuredHeight);
            }
        }
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        R$bool.setElevation(this, f);
    }

    public final void setVisibility(int i) {
        boolean z;
        super.setVisibility(i);
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
    }

    public final boolean shouldOffsetFirstChild() {
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        if (childAt.getVisibility() == 8) {
            return false;
        }
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
            return true;
        }
        return false;
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == this.statusBarForeground) {
            return true;
        }
        return false;
    }

    /* renamed from: generateLayoutParams  reason: collision with other method in class */
    public final LinearLayout.LayoutParams m299generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public static LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public ChildScrollEffect scrollEffect;
        public int scrollFlags = 1;
        public Interpolator scrollInterpolator;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            CompressChildScrollEffect compressChildScrollEffect;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppBarLayout_Layout);
            this.scrollFlags = obtainStyledAttributes.getInt(1, 0);
            if (obtainStyledAttributes.getInt(0, 0) != 1) {
                compressChildScrollEffect = null;
            } else {
                compressChildScrollEffect = new CompressChildScrollEffect();
            }
            this.scrollEffect = compressChildScrollEffect;
            if (obtainStyledAttributes.hasValue(2)) {
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(2, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams() {
            super(-1, -2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }
}
