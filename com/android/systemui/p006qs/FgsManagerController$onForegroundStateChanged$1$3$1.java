package com.android.systemui.p006qs;

import com.android.systemui.p006qs.FgsManagerController;

/* renamed from: com.android.systemui.qs.FgsManagerController$onForegroundStateChanged$1$3$1 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$onForegroundStateChanged$1$3$1 implements Runnable {
    public final /* synthetic */ FgsManagerController.OnNumberOfPackagesChangedListener $it;
    public final /* synthetic */ int $numPackagesAfter;

    public FgsManagerController$onForegroundStateChanged$1$3$1(FgsManagerController.OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener, int i) {
        this.$it = onNumberOfPackagesChangedListener;
        this.$numPackagesAfter = i;
    }

    public final void run() {
        this.$it.onNumberOfPackagesChanged(this.$numPackagesAfter);
    }
}
