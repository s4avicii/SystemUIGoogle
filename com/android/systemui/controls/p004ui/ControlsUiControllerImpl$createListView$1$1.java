package com.android.systemui.controls.p004ui;

import android.view.View;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$createListView$1$1 */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl$createListView$1$1 implements View.OnClickListener {
    public final /* synthetic */ ControlsUiControllerImpl this$0;

    public ControlsUiControllerImpl$createListView$1$1(ControlsUiControllerImpl controlsUiControllerImpl) {
        this.this$0 = controlsUiControllerImpl;
    }

    public final void onClick(View view) {
        Runnable runnable = this.this$0.onDismiss;
        if (runnable == null) {
            runnable = null;
        }
        runnable.run();
    }
}
