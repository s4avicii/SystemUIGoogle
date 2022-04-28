package com.android.systemui.p006qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.ScrollView;

/* renamed from: com.android.systemui.qs.NonInterceptingScrollView */
public class NonInterceptingScrollView extends ScrollView {
    public float mDownY;
    public boolean mScrollEnabled = true;
    public final int mTouchSlop;

    public final boolean canScrollHorizontally(int i) {
        if (!this.mScrollEnabled || !super.canScrollHorizontally(i)) {
            return false;
        }
        return true;
    }

    public final boolean canScrollVertically(int i) {
        if (!this.mScrollEnabled || !super.canScrollVertically(i)) {
            return false;
        }
        return true;
    }

    public NonInterceptingScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public final int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
        }
        return 0;
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ViewParent parent;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (canScrollVertically(1) && (parent = getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            this.mDownY = motionEvent.getY();
        } else if (actionMasked == 2 && ((float) ((int) motionEvent.getY())) - this.mDownY < ((float) (-this.mTouchSlop)) && !canScrollVertically(1)) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            if (canScrollVertically(1)) {
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            } else if (!canScrollVertically(-1)) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }
}
