package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.OverlayPlugin;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$2$Callback$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ StatusBar$2$Callback$$ExternalSyntheticLambda1(boolean z) {
        this.f$0 = z;
    }

    public final void accept(Object obj) {
        ((OverlayPlugin) obj).setCollapseDesired(this.f$0);
    }
}
