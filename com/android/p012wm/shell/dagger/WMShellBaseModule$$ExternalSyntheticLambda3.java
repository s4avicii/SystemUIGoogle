package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda3 implements Function {
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda3 INSTANCE = new WMShellBaseModule$$ExternalSyntheticLambda3(0);
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda3 INSTANCE$1 = new WMShellBaseModule$$ExternalSyntheticLambda3(1);
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda3 INSTANCE$2 = new WMShellBaseModule$$ExternalSyntheticLambda3(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((BubbleController) obj).asBubbles();
            case 1:
                int i = AnnotationLinkSpan.$r8$clinit;
                return ((AnnotationLinkSpan.LinkInfo) obj).mListener;
            default:
                return Boolean.valueOf(((StatusBar) obj).toggleSplitScreenMode(271, 286));
        }
    }
}
