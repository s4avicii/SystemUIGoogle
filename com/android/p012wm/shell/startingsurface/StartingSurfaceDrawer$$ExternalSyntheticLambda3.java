package com.android.p012wm.shell.startingsurface;

import android.os.IBinder;
import android.os.Trace;
import android.util.Slog;
import android.widget.FrameLayout;
import android.window.SplashScreenView;
import com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer;
import java.util.Objects;

/* renamed from: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StartingSurfaceDrawer$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ StartingSurfaceDrawer f$0;
    public final /* synthetic */ StartingSurfaceDrawer.SplashScreenViewSupplier f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ IBinder f$3;
    public final /* synthetic */ FrameLayout f$4;

    public /* synthetic */ StartingSurfaceDrawer$$ExternalSyntheticLambda3(StartingSurfaceDrawer startingSurfaceDrawer, StartingSurfaceDrawer.SplashScreenViewSupplier splashScreenViewSupplier, int i, IBinder iBinder, FrameLayout frameLayout) {
        this.f$0 = startingSurfaceDrawer;
        this.f$1 = splashScreenViewSupplier;
        this.f$2 = i;
        this.f$3 = iBinder;
        this.f$4 = frameLayout;
    }

    public final void run() {
        StartingSurfaceDrawer startingSurfaceDrawer = this.f$0;
        StartingSurfaceDrawer.SplashScreenViewSupplier splashScreenViewSupplier = this.f$1;
        int i = this.f$2;
        IBinder iBinder = this.f$3;
        FrameLayout frameLayout = this.f$4;
        Objects.requireNonNull(startingSurfaceDrawer);
        Trace.traceBegin(32, "addSplashScreenView");
        SplashScreenView splashScreenView = splashScreenViewSupplier.get();
        StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = startingSurfaceDrawer.mStartingWindowRecords.get(i);
        if (startingWindowRecord != null && iBinder == startingWindowRecord.mAppToken) {
            if (splashScreenView != null) {
                try {
                    frameLayout.addView(splashScreenView);
                } catch (RuntimeException e) {
                    Slog.w("ShellStartingWindow", "failed set content view to starting window at taskId: " + i, e);
                    splashScreenView = null;
                }
            }
            if (!startingWindowRecord.mSetSplashScreen) {
                startingWindowRecord.mContentView = splashScreenView;
                startingWindowRecord.mSetSplashScreen = true;
            }
        }
        Trace.traceEnd(32);
    }
}
