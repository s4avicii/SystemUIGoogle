package com.android.p012wm.shell;

import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutoutOrganizer;
import com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PrintWriter printWriter = (PrintWriter) this.f$0;
                HideDisplayCutoutController hideDisplayCutoutController = (HideDisplayCutoutController) obj;
                Objects.requireNonNull(hideDisplayCutoutController);
                printWriter.print("HideDisplayCutoutController");
                printWriter.println(" states: ");
                printWriter.print("  ");
                printWriter.print("mEnabled=");
                printWriter.println(hideDisplayCutoutController.mEnabled);
                HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer = hideDisplayCutoutController.mOrganizer;
                Objects.requireNonNull(hideDisplayCutoutOrganizer);
                printWriter.print("HideDisplayCutoutOrganizer");
                printWriter.println(" states: ");
                synchronized (hideDisplayCutoutOrganizer) {
                    printWriter.print("  ");
                    printWriter.print("mDisplayAreaMap=");
                    printWriter.println(hideDisplayCutoutOrganizer.mDisplayAreaMap);
                }
                printWriter.print("  ");
                printWriter.print("getDisplayBoundsOfNaturalOrientation()=");
                printWriter.println(hideDisplayCutoutOrganizer.getDisplayBoundsOfNaturalOrientation());
                printWriter.print("  ");
                printWriter.print("mDefaultDisplayBounds=");
                printWriter.println(hideDisplayCutoutOrganizer.mDefaultDisplayBounds);
                printWriter.print("  ");
                printWriter.print("mCurrentDisplayBounds=");
                printWriter.println(hideDisplayCutoutOrganizer.mCurrentDisplayBounds);
                printWriter.print("  ");
                printWriter.print("mDefaultCutoutInsets=");
                printWriter.println(hideDisplayCutoutOrganizer.mDefaultCutoutInsets);
                printWriter.print("  ");
                printWriter.print("mCurrentCutoutInsets=");
                printWriter.println(hideDisplayCutoutOrganizer.mCurrentCutoutInsets);
                printWriter.print("  ");
                printWriter.print("mRotation=");
                printWriter.println(hideDisplayCutoutOrganizer.mRotation);
                printWriter.print("  ");
                printWriter.print("mStatusBarHeight=");
                printWriter.println(hideDisplayCutoutOrganizer.mStatusBarHeight);
                printWriter.print("  ");
                printWriter.print("mOffsetX=");
                printWriter.println(hideDisplayCutoutOrganizer.mOffsetX);
                printWriter.print("  ");
                printWriter.print("mOffsetY=");
                printWriter.println(hideDisplayCutoutOrganizer.mOffsetY);
                return;
            default:
                StartingSurfaceDrawer.SplashScreenViewSupplier splashScreenViewSupplier = (StartingSurfaceDrawer.SplashScreenViewSupplier) this.f$0;
                Runnable runnable = (Runnable) obj;
                Objects.requireNonNull(splashScreenViewSupplier);
                synchronized (splashScreenViewSupplier) {
                    splashScreenViewSupplier.mUiThreadInitTask = runnable;
                }
                return;
        }
    }
}
