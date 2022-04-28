package com.google.android.systemui.gamedashboard;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Insets;
import android.graphics.ParcelableColorSpace;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceControl;
import com.android.internal.util.ScreenshotHelper;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ ShortcutBarView f$0;
    public final /* synthetic */ ScreenshotHelper f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ ActivityManager.RunningTaskInfo f$3;
    public final /* synthetic */ Handler f$4;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda4(ShortcutBarView shortcutBarView, ScreenshotHelper screenshotHelper, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler) {
        this.f$0 = shortcutBarView;
        this.f$1 = screenshotHelper;
        this.f$2 = rect;
        this.f$3 = runningTaskInfo;
        this.f$4 = handler;
    }

    public final void accept(Object obj) {
        ParcelableColorSpace parcelableColorSpace;
        ShortcutBarView shortcutBarView = this.f$0;
        ScreenshotHelper screenshotHelper = this.f$1;
        Rect rect = this.f$2;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.f$3;
        Handler handler = this.f$4;
        int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
        Objects.requireNonNull(shortcutBarView);
        Bitmap asBitmap = ((SurfaceControl.ScreenshotHardwareBuffer) obj).asBitmap();
        if (asBitmap.getConfig() == Bitmap.Config.HARDWARE) {
            if (asBitmap.getColorSpace() == null) {
                parcelableColorSpace = new ParcelableColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
            } else {
                parcelableColorSpace = new ParcelableColorSpace(asBitmap.getColorSpace());
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("bitmap_util_buffer", asBitmap.getHardwareBuffer());
            bundle.putParcelable("bitmap_util_color_space", parcelableColorSpace);
            ComponentName componentName = new ComponentName(shortcutBarView.getClass().getPackageName(), shortcutBarView.getClass().getSimpleName());
            screenshotHelper.provideScreenshot(bundle, rect, Insets.NONE, runningTaskInfo.taskId, 1, componentName, 5, handler, (Consumer) null);
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Passed bitmap must have hardware config, found: ");
        m.append(asBitmap.getConfig());
        throw new IllegalArgumentException(m.toString());
    }
}
