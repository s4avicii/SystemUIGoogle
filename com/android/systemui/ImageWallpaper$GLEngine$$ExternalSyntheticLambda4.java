package com.android.systemui;

import android.os.SystemClock;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda4 implements Supplier {
    public static final /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda4 INSTANCE = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda4();

    public final Object get() {
        return Long.valueOf(SystemClock.elapsedRealtime());
    }
}
