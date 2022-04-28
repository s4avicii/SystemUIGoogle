package com.android.systemui.statusbar;

import android.os.SystemClock;
import android.view.View;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;

/* compiled from: LockscreenShadeTransitionController.kt */
public final class LockscreenShadeTransitionController$bindController$1 implements View.OnClickListener {
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public LockscreenShadeTransitionController$bindController$1(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    public final void onClick(View view) {
        if (this.this$0.statusBarStateController.getState() == 1) {
            LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
            Objects.requireNonNull(lockscreenShadeTransitionController);
            StatusBar statusBar = lockscreenShadeTransitionController.statusbar;
            if (statusBar == null) {
                statusBar = null;
            }
            statusBar.wakeUpIfDozing(SystemClock.uptimeMillis(), view, "SHADE_CLICK");
            this.this$0.goToLockedShade(view, true);
        }
    }
}
