package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda2 implements Function {
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda2 INSTANCE = new WMShellBaseModule$$ExternalSyntheticLambda2(0);
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda2 INSTANCE$1 = new WMShellBaseModule$$ExternalSyntheticLambda2(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                HideDisplayCutoutController hideDisplayCutoutController = (HideDisplayCutoutController) obj;
                Objects.requireNonNull(hideDisplayCutoutController);
                return hideDisplayCutoutController.mImpl;
            default:
                return ((StatusBar) obj).getNavigationBarView();
        }
    }
}
