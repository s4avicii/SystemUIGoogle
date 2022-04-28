package com.android.p012wm.shell.startingsurface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$ImmobileIconDrawable$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1929xa76c91d0 implements Runnable {
    public final /* synthetic */ SplashscreenIconDrawableFactory$ImmobileIconDrawable f$0;
    public final /* synthetic */ Drawable f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ C1929xa76c91d0(SplashscreenIconDrawableFactory$ImmobileIconDrawable splashscreenIconDrawableFactory$ImmobileIconDrawable, Drawable drawable, int i) {
        this.f$0 = splashscreenIconDrawableFactory$ImmobileIconDrawable;
        this.f$1 = drawable;
        this.f$2 = i;
    }

    public final void run() {
        SplashscreenIconDrawableFactory$ImmobileIconDrawable splashscreenIconDrawableFactory$ImmobileIconDrawable = this.f$0;
        Drawable drawable = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(splashscreenIconDrawableFactory$ImmobileIconDrawable);
        synchronized (splashscreenIconDrawableFactory$ImmobileIconDrawable.mPaint) {
            Trace.traceBegin(32, "preDrawIcon");
            splashscreenIconDrawableFactory$ImmobileIconDrawable.mIconBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(splashscreenIconDrawableFactory$ImmobileIconDrawable.mIconBitmap);
            drawable.setBounds(0, 0, i, i);
            drawable.draw(canvas);
            Trace.traceEnd(32);
        }
    }
}
