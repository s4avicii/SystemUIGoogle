package com.android.p012wm.shell;

import com.android.p012wm.shell.apppairs.AppPairsController;
import com.android.p012wm.shell.apppairs.AppPairsPool;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellInitImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$$ExternalSyntheticLambda1 implements Consumer {
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda1 INSTANCE = new ShellInitImpl$$ExternalSyntheticLambda1();

    public final void accept(Object obj) {
        AppPairsController appPairsController = (AppPairsController) obj;
        Objects.requireNonNull(appPairsController);
        if (appPairsController.mPairsPool == null) {
            appPairsController.setPairsPool(new AppPairsPool(appPairsController));
        }
    }
}
