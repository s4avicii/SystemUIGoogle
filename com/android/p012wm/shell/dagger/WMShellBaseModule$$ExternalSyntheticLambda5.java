package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda5 implements Function {
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda5 INSTANCE = new WMShellBaseModule$$ExternalSyntheticLambda5(0);
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda5 INSTANCE$1 = new WMShellBaseModule$$ExternalSyntheticLambda5(1);
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda5 INSTANCE$2 = new WMShellBaseModule$$ExternalSyntheticLambda5(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                LegacySplitScreenController legacySplitScreenController = (LegacySplitScreenController) obj;
                Objects.requireNonNull(legacySplitScreenController);
                return legacySplitScreenController.mImpl;
            case 1:
                return Boolean.valueOf(((StatusBar) obj).isKeyguardShowing());
            default:
                return Boolean.valueOf(((LegacySplitScreen) obj).isDividerVisible());
        }
    }
}
