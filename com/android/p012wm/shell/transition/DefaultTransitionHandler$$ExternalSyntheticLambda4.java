package com.android.p012wm.shell.transition;

import android.view.SurfaceControl;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ WindowThumbnail f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;
    public final /* synthetic */ Runnable f$3;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda4(DefaultTransitionHandler defaultTransitionHandler, WindowThumbnail windowThumbnail, SurfaceControl.Transaction transaction, DefaultTransitionHandler$$ExternalSyntheticLambda1 defaultTransitionHandler$$ExternalSyntheticLambda1) {
        this.f$0 = defaultTransitionHandler;
        this.f$1 = windowThumbnail;
        this.f$2 = transaction;
        this.f$3 = defaultTransitionHandler$$ExternalSyntheticLambda1;
    }

    public final void run() {
        DefaultTransitionHandler defaultTransitionHandler = this.f$0;
        WindowThumbnail windowThumbnail = this.f$1;
        SurfaceControl.Transaction transaction = this.f$2;
        Runnable runnable = this.f$3;
        Objects.requireNonNull(defaultTransitionHandler);
        Objects.requireNonNull(windowThumbnail);
        SurfaceControl surfaceControl = windowThumbnail.mSurfaceControl;
        if (surfaceControl != null) {
            transaction.remove(surfaceControl);
            transaction.apply();
            windowThumbnail.mSurfaceControl.release();
            windowThumbnail.mSurfaceControl = null;
        }
        defaultTransitionHandler.mTransactionPool.release(transaction);
        runnable.run();
    }
}
