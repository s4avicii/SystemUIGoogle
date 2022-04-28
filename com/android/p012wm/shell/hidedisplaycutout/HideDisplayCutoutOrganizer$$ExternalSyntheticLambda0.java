package com.android.p012wm.shell.hidedisplaycutout;

import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import java.util.Objects;
import java.util.function.BiConsumer;

/* renamed from: com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ HideDisplayCutoutOrganizer f$0;
    public final /* synthetic */ WindowContainerTransaction f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;

    public /* synthetic */ HideDisplayCutoutOrganizer$$ExternalSyntheticLambda0(HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer, WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        this.f$0 = hideDisplayCutoutOrganizer;
        this.f$1 = windowContainerTransaction;
        this.f$2 = transaction;
    }

    public final void accept(Object obj, Object obj2) {
        HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer = this.f$0;
        WindowContainerTransaction windowContainerTransaction = this.f$1;
        SurfaceControl.Transaction transaction = this.f$2;
        Objects.requireNonNull(hideDisplayCutoutOrganizer);
        hideDisplayCutoutOrganizer.applyBoundsAndOffsets((WindowContainerToken) obj, (SurfaceControl) obj2, windowContainerTransaction, transaction);
    }
}
