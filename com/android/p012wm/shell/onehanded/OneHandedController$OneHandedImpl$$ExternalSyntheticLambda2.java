package com.android.p012wm.shell.onehanded;

import com.android.p012wm.shell.onehanded.OneHandedController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.onehanded.OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ OneHandedController.OneHandedImpl f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2 = false;

    public /* synthetic */ OneHandedController$OneHandedImpl$$ExternalSyntheticLambda2(OneHandedController.OneHandedImpl oneHandedImpl, boolean z) {
        this.f$0 = oneHandedImpl;
        this.f$1 = z;
    }

    public final void run() {
        OneHandedController.OneHandedImpl oneHandedImpl = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        Objects.requireNonNull(oneHandedImpl);
        OneHandedController.this.setLockedDisabled(z, z2);
    }
}
