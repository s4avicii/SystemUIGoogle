package com.android.systemui.statusbar.window;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class StatusBarWindowView extends FrameLayout {
    public int mLeftInset = 0;
    public int mRightInset = 0;
    public int mTopInset = 0;
    public float mTouchDownY = 0.0f;

    public StatusBarWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 && motionEvent.getRawY() > ((float) getHeight())) {
            this.mTouchDownY = motionEvent.getRawY();
            motionEvent.setLocation(motionEvent.getRawX(), (float) this.mTopInset);
        } else if (motionEvent.getAction() == 2 && this.mTouchDownY != 0.0f) {
            motionEvent.setLocation(motionEvent.getRawX(), (motionEvent.getRawY() + ((float) this.mTopInset)) - this.mTouchDownY);
        } else if (motionEvent.getAction() == 1) {
            this.mTouchDownY = 0.0f;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0037, code lost:
        r3 = (android.widget.FrameLayout.LayoutParams) r2.getLayoutParams();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets r8) {
        /*
            r7 = this;
            int r0 = android.view.WindowInsets.Type.systemBars()
            android.graphics.Insets r0 = r8.getInsetsIgnoringVisibility(r0)
            int r1 = r0.left
            r7.mLeftInset = r1
            int r0 = r0.right
            r7.mRightInset = r0
            r0 = 0
            r7.mTopInset = r0
            android.view.WindowInsets r1 = r7.getRootWindowInsets()
            android.view.DisplayCutout r1 = r1.getDisplayCutout()
            if (r1 == 0) goto L_0x0025
            android.graphics.Insets r1 = r1.getWaterfallInsets()
            int r1 = r1.top
            r7.mTopInset = r1
        L_0x0025:
            int r1 = r7.getChildCount()
        L_0x0029:
            if (r0 >= r1) goto L_0x005f
            android.view.View r2 = r7.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r3 = r2.getLayoutParams()
            boolean r3 = r3 instanceof android.widget.FrameLayout.LayoutParams
            if (r3 == 0) goto L_0x005c
            android.view.ViewGroup$LayoutParams r3 = r2.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            int r4 = r3.rightMargin
            int r5 = r7.mRightInset
            if (r4 != r5) goto L_0x004f
            int r4 = r3.leftMargin
            int r6 = r7.mLeftInset
            if (r4 != r6) goto L_0x004f
            int r4 = r3.topMargin
            int r6 = r7.mTopInset
            if (r4 == r6) goto L_0x005c
        L_0x004f:
            r3.rightMargin = r5
            int r4 = r7.mLeftInset
            r3.leftMargin = r4
            int r4 = r7.mTopInset
            r3.topMargin = r4
            r2.requestLayout()
        L_0x005c:
            int r0 = r0 + 1
            goto L_0x0029
        L_0x005f:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.window.StatusBarWindowView.onApplyWindowInsets(android.view.WindowInsets):android.view.WindowInsets");
    }
}
