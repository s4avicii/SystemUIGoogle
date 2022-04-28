package com.android.systemui.globalactions;

import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$1$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0833xc30ba73b implements Function {
    public static final /* synthetic */ C0833xc30ba73b INSTANCE = new C0833xc30ba73b();

    public final Object apply(Object obj) {
        StatusBar statusBar = (StatusBar) obj;
        Objects.requireNonNull(statusBar);
        StatusBarWindowController statusBarWindowController = statusBar.mStatusBarWindowController;
        Objects.requireNonNull(statusBarWindowController);
        return Integer.valueOf(statusBarWindowController.mBarHeight);
    }
}
