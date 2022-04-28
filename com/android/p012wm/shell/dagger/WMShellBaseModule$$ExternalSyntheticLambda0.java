package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.back.BackAnimationController;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda0 implements Function {
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda0 INSTANCE = new WMShellBaseModule$$ExternalSyntheticLambda0();

    public final Object apply(Object obj) {
        BackAnimationController backAnimationController = (BackAnimationController) obj;
        Objects.requireNonNull(backAnimationController);
        return backAnimationController.mBackAnimation;
    }
}
