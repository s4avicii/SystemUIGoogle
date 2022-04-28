package com.android.systemui.statusbar.phone;

import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda13 implements StatusBarWindowStateListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda13(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onStatusBarWindowStateChanged(int i) {
        StatusBar statusBar = this.f$0;
        Objects.requireNonNull(statusBar);
        statusBar.mBubblesOptional.ifPresent(new PipController$$ExternalSyntheticLambda4(statusBar, 2));
        statusBar.mStatusBarWindowState = i;
    }
}
