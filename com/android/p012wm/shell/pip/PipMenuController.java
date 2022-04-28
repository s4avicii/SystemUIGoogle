package com.android.p012wm.shell.pip;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.WindowManager;

/* renamed from: com.android.wm.shell.pip.PipMenuController */
public interface PipMenuController {
    void attach(SurfaceControl surfaceControl);

    void detach();

    boolean isMenuVisible();

    void movePipMenu(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect) {
    }

    void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    void resizePipMenu(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect) {
    }

    void updateMenuBounds(Rect rect) {
    }

    static WindowManager.LayoutParams getPipMenuLayoutParams(int i, int i2) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2038, 545521680, -3);
        layoutParams.privateFlags |= 536870912;
        layoutParams.setTitle("PipMenuView");
        return layoutParams;
    }
}
