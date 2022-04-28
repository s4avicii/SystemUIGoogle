package com.android.p012wm.shell;

import com.android.p012wm.shell.fullscreen.FullscreenUnfoldController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellInitImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$$ExternalSyntheticLambda2 implements Consumer {
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda2 INSTANCE = new ShellInitImpl$$ExternalSyntheticLambda2();

    public final void accept(Object obj) {
        FullscreenUnfoldController fullscreenUnfoldController = (FullscreenUnfoldController) obj;
        Objects.requireNonNull(fullscreenUnfoldController);
        fullscreenUnfoldController.mProgressProvider.addListener(fullscreenUnfoldController.mExecutor, fullscreenUnfoldController);
        fullscreenUnfoldController.mDisplayInsetsController.addInsetsChangedListener(0, fullscreenUnfoldController);
    }
}
