package com.android.p012wm.shell.startingsurface;

import android.graphics.drawable.Drawable;
import java.util.function.IntSupplier;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda2 implements IntSupplier {
    public final /* synthetic */ Drawable f$0;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda2(Drawable drawable) {
        this.f$0 = drawable;
    }

    public final int getAsInt() {
        return SplashscreenContentDrawer.estimateWindowBGColor(this.f$0);
    }
}
