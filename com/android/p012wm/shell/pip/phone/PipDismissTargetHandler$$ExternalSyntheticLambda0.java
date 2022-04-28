package com.android.p012wm.shell.pip.phone;

import android.view.View;
import android.view.WindowInsets;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipDismissTargetHandler$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipDismissTargetHandler$$ExternalSyntheticLambda0 implements View.OnApplyWindowInsetsListener {
    public final /* synthetic */ PipDismissTargetHandler f$0;

    public /* synthetic */ PipDismissTargetHandler$$ExternalSyntheticLambda0(PipDismissTargetHandler pipDismissTargetHandler) {
        this.f$0 = pipDismissTargetHandler;
    }

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        PipDismissTargetHandler pipDismissTargetHandler = this.f$0;
        Objects.requireNonNull(pipDismissTargetHandler);
        if (!windowInsets.equals(pipDismissTargetHandler.mWindowInsets)) {
            pipDismissTargetHandler.mWindowInsets = windowInsets;
            pipDismissTargetHandler.updateMagneticTargetSize();
        }
        return windowInsets;
    }
}
