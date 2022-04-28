package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import java.util.WeakHashMap;
import kotlinx.atomicfu.AtomicFU;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    public int activePointerId = -1;
    public FlingRunnable flingRunnable;
    public boolean isBeingDragged;
    public int lastMotionY;
    public OverScroller scroller;
    public int touchSlop = -1;
    public VelocityTracker velocityTracker;

    public class FlingRunnable implements Runnable {
        public final V layout;
        public final CoordinatorLayout parent;

        public FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
            this.parent = coordinatorLayout;
            this.layout = v;
        }

        public final void run() {
            OverScroller overScroller;
            if (this.layout != null && (overScroller = HeaderBehavior.this.scroller) != null) {
                if (overScroller.computeScrollOffset()) {
                    HeaderBehavior headerBehavior = HeaderBehavior.this;
                    headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
                    V v = this.layout;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimation(v, this);
                    return;
                }
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
            }
        }
    }

    public HeaderBehavior() {
    }

    public boolean canDragView(V v) {
        return false;
    }

    public void onFlingFinished(CoordinatorLayout coordinatorLayout, V v) {
    }

    public final int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int i) {
        return setHeaderTopBottomOffset(coordinatorLayout, v, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean z;
        int findPointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.isBeingDragged) {
            int i = this.activePointerId;
            if (i == -1 || (findPointerIndex = motionEvent.findPointerIndex(i)) == -1) {
                return false;
            }
            int y = (int) motionEvent.getY(findPointerIndex);
            if (Math.abs(y - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = y;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            if (!canDragView(v) || !coordinatorLayout.isPointInChildBounds(v, x, y2)) {
                z = false;
            } else {
                z = true;
            }
            this.isBeingDragged = z;
            if (z) {
                this.lastMotionY = y2;
                this.activePointerId = motionEvent.getPointerId(0);
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                OverScroller overScroller = this.scroller;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(motionEvent);
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00de A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r20, V r21, android.view.MotionEvent r22) {
        /*
            r19 = this;
            r6 = r19
            r2 = r21
            r7 = r22
            int r0 = r22.getActionMasked()
            r1 = 0
            r3 = -1
            r8 = 1
            r9 = 0
            if (r0 == r8) goto L_0x005e
            r4 = 2
            if (r0 == r4) goto L_0x0034
            r2 = 3
            if (r0 == r2) goto L_0x00c5
            r1 = 6
            if (r0 == r1) goto L_0x001a
            goto L_0x005b
        L_0x001a:
            int r0 = r22.getActionIndex()
            if (r0 != 0) goto L_0x0022
            r0 = r8
            goto L_0x0023
        L_0x0022:
            r0 = r9
        L_0x0023:
            int r1 = r7.getPointerId(r0)
            r6.activePointerId = r1
            float r0 = r7.getY(r0)
            r1 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 + r1
            int r0 = (int) r0
            r6.lastMotionY = r0
            goto L_0x005b
        L_0x0034:
            int r0 = r6.activePointerId
            int r0 = r7.findPointerIndex(r0)
            if (r0 != r3) goto L_0x003d
            return r9
        L_0x003d:
            float r0 = r7.getY(r0)
            int r0 = (int) r0
            int r1 = r6.lastMotionY
            int r1 = r1 - r0
            r6.lastMotionY = r0
            int r4 = r6.getMaxDragOffset(r2)
            r5 = 0
            int r0 = r19.getTopBottomOffsetForScrollingSibling()
            int r3 = r0 - r1
            r0 = r19
            r1 = r20
            r2 = r21
            r0.setHeaderTopBottomOffset(r1, r2, r3, r4, r5)
        L_0x005b:
            r0 = r9
            goto L_0x00d3
        L_0x005e:
            android.view.VelocityTracker r0 = r6.velocityTracker
            if (r0 == 0) goto L_0x00c5
            r0.addMovement(r7)
            android.view.VelocityTracker r0 = r6.velocityTracker
            r4 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r4)
            android.view.VelocityTracker r0 = r6.velocityTracker
            int r4 = r6.activePointerId
            float r0 = r0.getYVelocity(r4)
            int r4 = r6.getScrollRangeForDragFling(r2)
            int r4 = -r4
            r18 = 0
            com.google.android.material.appbar.HeaderBehavior$FlingRunnable r5 = r6.flingRunnable
            if (r5 == 0) goto L_0x0084
            r2.removeCallbacks(r5)
            r6.flingRunnable = r1
        L_0x0084:
            android.widget.OverScroller r5 = r6.scroller
            if (r5 != 0) goto L_0x0093
            android.widget.OverScroller r5 = new android.widget.OverScroller
            android.content.Context r10 = r21.getContext()
            r5.<init>(r10)
            r6.scroller = r5
        L_0x0093:
            android.widget.OverScroller r10 = r6.scroller
            r11 = 0
            int r12 = r19.getTopAndBottomOffset()
            r13 = 0
            int r14 = java.lang.Math.round(r0)
            r15 = 0
            r16 = 0
            r17 = r4
            r10.fling(r11, r12, r13, r14, r15, r16, r17, r18)
            android.widget.OverScroller r0 = r6.scroller
            boolean r0 = r0.computeScrollOffset()
            if (r0 == 0) goto L_0x00be
            com.google.android.material.appbar.HeaderBehavior$FlingRunnable r0 = new com.google.android.material.appbar.HeaderBehavior$FlingRunnable
            r4 = r20
            r0.<init>(r4, r2)
            r6.flingRunnable = r0
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postOnAnimation(r2, r0)
            goto L_0x00c3
        L_0x00be:
            r4 = r20
            r19.onFlingFinished(r20, r21)
        L_0x00c3:
            r0 = r8
            goto L_0x00c6
        L_0x00c5:
            r0 = r9
        L_0x00c6:
            r6.isBeingDragged = r9
            r6.activePointerId = r3
            android.view.VelocityTracker r2 = r6.velocityTracker
            if (r2 == 0) goto L_0x00d3
            r2.recycle()
            r6.velocityTracker = r1
        L_0x00d3:
            android.view.VelocityTracker r1 = r6.velocityTracker
            if (r1 == 0) goto L_0x00da
            r1.addMovement(r7)
        L_0x00da:
            boolean r1 = r6.isBeingDragged
            if (r1 != 0) goto L_0x00e2
            if (r0 == 0) goto L_0x00e1
            goto L_0x00e2
        L_0x00e1:
            r8 = r9
        L_0x00e2:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.HeaderBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        int clamp;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i2 == 0 || topAndBottomOffset < i2 || topAndBottomOffset > i3 || topAndBottomOffset == (clamp = AtomicFU.clamp(i, i2, i3))) {
            return 0;
        }
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper != null) {
            viewOffsetHelper.setTopAndBottomOffset(clamp);
        } else {
            this.tempTopBottomOffset = clamp;
        }
        return topAndBottomOffset - clamp;
    }

    public int getMaxDragOffset(V v) {
        return -v.getHeight();
    }

    public int getScrollRangeForDragFling(V v) {
        return v.getHeight();
    }

    public int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
