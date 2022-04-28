package com.android.systemui.statusbar.phone;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda1 implements View.OnTouchListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda1(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.mAutoHideController.checkUserAutoHide(motionEvent);
        NotificationRemoteInputManager notificationRemoteInputManager = statusBar.mRemoteInputManager;
        Objects.requireNonNull(notificationRemoteInputManager);
        if (motionEvent.getAction() == 4 && motionEvent.getX() == 0.0f && motionEvent.getY() == 0.0f && notificationRemoteInputManager.isRemoteInputActive()) {
            notificationRemoteInputManager.closeRemoteInputs();
        }
        if (motionEvent.getAction() == 0 && statusBar.mExpandedVisible) {
            statusBar.mShadeController.animateCollapsePanels();
        }
        return statusBar.mNotificationShadeWindowView.onTouchEvent(motionEvent);
    }
}
