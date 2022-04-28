package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.CrossWindowBlurListeners;
import android.view.SurfaceControl;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlurUtils.kt */
public final class BlurUtils implements Dumpable {
    public final CrossWindowBlurListeners crossWindowBlurListeners;
    public int lastAppliedBlur;
    public final int maxBlurRadius;
    public final int minBlurRadius;

    public final float blurRadiusOfRatio(float f) {
        boolean z;
        if (f == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return 0.0f;
        }
        return MathUtils.lerp((float) this.minBlurRadius, (float) this.maxBlurRadius, f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0043, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0044, code lost:
        kotlin.p018io.CloseableKt.closeFinally(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0047, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void applyBlur(android.view.ViewRootImpl r4, int r5, boolean r6) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x0048
            android.view.SurfaceControl r0 = r4.getSurfaceControl()
            boolean r0 = r0.isValid()
            if (r0 != 0) goto L_0x000d
            goto L_0x0048
        L_0x000d:
            android.view.SurfaceControl$Transaction r0 = r3.createTransaction()
            r1 = 0
            boolean r2 = r3.supportsBlursOnWindows()     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x0033
            android.view.SurfaceControl r2 = r4.getSurfaceControl()     // Catch:{ all -> 0x0041 }
            r0.setBackgroundBlurRadius(r2, r5)     // Catch:{ all -> 0x0041 }
            int r2 = r3.lastAppliedBlur     // Catch:{ all -> 0x0041 }
            if (r2 != 0) goto L_0x0028
            if (r5 == 0) goto L_0x0028
            r0.setEarlyWakeupStart()     // Catch:{ all -> 0x0041 }
        L_0x0028:
            int r2 = r3.lastAppliedBlur     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x0031
            if (r5 != 0) goto L_0x0031
            r0.setEarlyWakeupEnd()     // Catch:{ all -> 0x0041 }
        L_0x0031:
            r3.lastAppliedBlur = r5     // Catch:{ all -> 0x0041 }
        L_0x0033:
            android.view.SurfaceControl r3 = r4.getSurfaceControl()     // Catch:{ all -> 0x0041 }
            r0.setOpaque(r3, r6)     // Catch:{ all -> 0x0041 }
            r0.apply()     // Catch:{ all -> 0x0041 }
            kotlin.p018io.CloseableKt.closeFinally(r0, r1)
            return
        L_0x0041:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0043 }
        L_0x0043:
            r4 = move-exception
            kotlin.p018io.CloseableKt.closeFinally(r0, r3)
            throw r4
        L_0x0048:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.BlurUtils.applyBlur(android.view.ViewRootImpl, int, boolean):void");
    }

    public SurfaceControl.Transaction createTransaction() {
        return new SurfaceControl.Transaction();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BlurUtils:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(Intrinsics.stringPlus("minBlurRadius: ", Integer.valueOf(this.minBlurRadius)));
        indentingPrintWriter.println(Intrinsics.stringPlus("maxBlurRadius: ", Integer.valueOf(this.maxBlurRadius)));
        indentingPrintWriter.println(Intrinsics.stringPlus("supportsBlursOnWindows: ", Boolean.valueOf(supportsBlursOnWindows())));
        indentingPrintWriter.println(Intrinsics.stringPlus("CROSS_WINDOW_BLUR_SUPPORTED: ", Boolean.valueOf(CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED)));
        indentingPrintWriter.println(Intrinsics.stringPlus("isHighEndGfx: ", Boolean.valueOf(ActivityManager.isHighEndGfx())));
    }

    public final boolean supportsBlursOnWindows() {
        if (!CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED || !ActivityManager.isHighEndGfx() || !this.crossWindowBlurListeners.isCrossWindowBlurEnabled() || SystemProperties.getBoolean("persist.sysui.disableBlur", false)) {
            return false;
        }
        return true;
    }

    public BlurUtils(Resources resources, CrossWindowBlurListeners crossWindowBlurListeners2, DumpManager dumpManager) {
        this.crossWindowBlurListeners = crossWindowBlurListeners2;
        this.minBlurRadius = resources.getDimensionPixelSize(C1777R.dimen.min_window_blur_radius);
        this.maxBlurRadius = resources.getDimensionPixelSize(C1777R.dimen.max_window_blur_radius);
        dumpManager.registerDumpable(BlurUtils.class.getName(), this);
    }
}
