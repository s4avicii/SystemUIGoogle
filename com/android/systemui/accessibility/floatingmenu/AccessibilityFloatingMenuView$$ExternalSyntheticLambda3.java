package com.android.systemui.accessibility.floatingmenu;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessibilityFloatingMenuView$$ExternalSyntheticLambda3 implements View.OnTouchListener {
    public final /* synthetic */ AccessibilityFloatingMenuView f$0;

    public /* synthetic */ AccessibilityFloatingMenuView$$ExternalSyntheticLambda3(AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        this.f$0 = accessibilityFloatingMenuView;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
        Objects.requireNonNull(accessibilityFloatingMenuView);
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int marginStartEndWith = accessibilityFloatingMenuView.getMarginStartEndWith(accessibilityFloatingMenuView.mLastConfiguration);
        int i = accessibilityFloatingMenuView.mMargin;
        int i2 = accessibilityFloatingMenuView.mPadding;
        Rect rect = new Rect(marginStartEndWith, i, (i2 * 2) + accessibilityFloatingMenuView.mIconWidth + marginStartEndWith, Math.min(accessibilityFloatingMenuView.mDisplayHeight - (i * 2), (accessibilityFloatingMenuView.mTargets.size() * (i2 + accessibilityFloatingMenuView.mIconHeight)) + accessibilityFloatingMenuView.mPadding) + i);
        if (action == 0 && rect.contains(x, y)) {
            accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea = true;
        }
        if (!accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea) {
            return false;
        }
        if (action == 1 || action == 3) {
            accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea = false;
        }
        int i3 = accessibilityFloatingMenuView.mMargin;
        motionEvent.setLocation((float) (x - i3), (float) (y - i3));
        return accessibilityFloatingMenuView.mListView.dispatchTouchEvent(motionEvent);
    }
}
