package com.android.p012wm.shell.dagger;

import android.net.Uri;
import com.android.p012wm.shell.recents.RecentTasksController;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WMShellBaseModule$$ExternalSyntheticLambda1 implements Function {
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda1 INSTANCE = new WMShellBaseModule$$ExternalSyntheticLambda1(0);
    public static final /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda1 INSTANCE$1 = new WMShellBaseModule$$ExternalSyntheticLambda1(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WMShellBaseModule$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                Objects.requireNonNull(recentTasksController);
                return recentTasksController.mImpl;
            default:
                return Uri.parse((String) obj);
        }
    }
}
