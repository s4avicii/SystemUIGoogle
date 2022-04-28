package com.android.systemui.statusbar.window;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarWindowController$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ StatusBarWindowController f$0;

    public /* synthetic */ StatusBarWindowController$$ExternalSyntheticLambda1(StatusBarWindowController statusBarWindowController) {
        this.f$0 = statusBarWindowController;
    }

    public final Object get() {
        StatusBarWindowController statusBarWindowController = this.f$0;
        Objects.requireNonNull(statusBarWindowController);
        return statusBarWindowController.mStatusBarWindowView;
    }
}
