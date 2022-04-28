package com.android.systemui;

import android.opengl.GLES20;
import com.android.systemui.ImageWallpaper;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ ImageWallpaper.GLEngine f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda1(ImageWallpaper.GLEngine gLEngine, int i, int i2) {
        this.f$0 = gLEngine;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void run() {
        ImageWallpaper.GLEngine gLEngine = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(gLEngine);
        Objects.requireNonNull(gLEngine.mRenderer);
        GLES20.glViewport(0, 0, i, i2);
    }
}
