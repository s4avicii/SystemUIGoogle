package com.android.systemui.screenshot;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotSelectorView$$ExternalSyntheticLambda0 implements View.OnTouchListener {
    public final /* synthetic */ ScreenshotSelectorView f$0;

    public /* synthetic */ ScreenshotSelectorView$$ExternalSyntheticLambda0(ScreenshotSelectorView screenshotSelectorView) {
        this.f$0 = screenshotSelectorView;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        ScreenshotSelectorView screenshotSelectorView = this.f$0;
        int i = ScreenshotSelectorView.$r8$clinit;
        Objects.requireNonNull(screenshotSelectorView);
        int action = motionEvent.getAction();
        if (action == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            screenshotSelectorView.mStartPoint = new Point(x, y);
            screenshotSelectorView.mSelectionRect = new Rect(x, y, x, y);
            return true;
        } else if (action == 1) {
            screenshotSelectorView.setVisibility(8);
            Rect rect = screenshotSelectorView.mSelectionRect;
            if (!(screenshotSelectorView.mOnScreenshotSelected == null || rect == null || rect.width() == 0 || rect.height() == 0)) {
                screenshotSelectorView.mOnScreenshotSelected.accept(rect);
            }
            screenshotSelectorView.mStartPoint = null;
            screenshotSelectorView.mSelectionRect = null;
            return true;
        } else if (action != 2) {
            return false;
        } else {
            int x2 = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            Rect rect2 = screenshotSelectorView.mSelectionRect;
            if (rect2 == null) {
                return true;
            }
            rect2.left = Math.min(screenshotSelectorView.mStartPoint.x, x2);
            screenshotSelectorView.mSelectionRect.right = Math.max(screenshotSelectorView.mStartPoint.x, x2);
            screenshotSelectorView.mSelectionRect.top = Math.min(screenshotSelectorView.mStartPoint.y, y2);
            screenshotSelectorView.mSelectionRect.bottom = Math.max(screenshotSelectorView.mStartPoint.y, y2);
            screenshotSelectorView.invalidate();
            return true;
        }
    }
}
