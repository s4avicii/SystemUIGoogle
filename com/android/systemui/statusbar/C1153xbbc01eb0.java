package com.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* renamed from: com.android.systemui.statusbar.LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 */
/* compiled from: LockscreenShadeTransitionController.kt */
public final class C1153xbbc01eb0 implements Runnable {
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public C1153xbbc01eb0(LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        this.this$0 = lockscreenShadeTransitionController;
    }

    public final void run() {
        this.this$0.logger.logGoingToLockedShadeAborted();
        this.this$0.setDragDownAmountAnimated(0.0f, 0, (Function0<Unit>) null);
    }
}
