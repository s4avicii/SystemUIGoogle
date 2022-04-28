package com.android.p012wm.shell.startingsurface;

import android.content.Context;
import java.util.Objects;
import java.util.function.IntSupplier;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda3 implements IntSupplier {
    public final /* synthetic */ SplashscreenContentDrawer f$0;
    public final /* synthetic */ Context f$1;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda3(SplashscreenContentDrawer splashscreenContentDrawer, Context context) {
        this.f$0 = splashscreenContentDrawer;
        this.f$1 = context;
    }

    public final int getAsInt() {
        SplashscreenContentDrawer splashscreenContentDrawer = this.f$0;
        Context context = this.f$1;
        Objects.requireNonNull(splashscreenContentDrawer);
        return SplashscreenContentDrawer.peekWindowBGColor(context, splashscreenContentDrawer.mTmpAttrs);
    }
}
