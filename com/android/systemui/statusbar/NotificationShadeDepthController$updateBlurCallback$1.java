package com.android.systemui.statusbar;

import android.view.Choreographer;

/* compiled from: NotificationShadeDepthController.kt */
public final class NotificationShadeDepthController$updateBlurCallback$1 implements Choreographer.FrameCallback {
    public final /* synthetic */ NotificationShadeDepthController this$0;

    public NotificationShadeDepthController$updateBlurCallback$1(NotificationShadeDepthController notificationShadeDepthController) {
        this.this$0 = notificationShadeDepthController;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x008e, code lost:
        if (r0.blursDisabledForUnlock != false) goto L_0x0090;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void doFrame(long r8) {
        /*
            r7 = this;
            com.android.systemui.statusbar.NotificationShadeDepthController r8 = r7.this$0
            r9 = 0
            r8.updateScheduled = r9
            com.android.systemui.statusbar.NotificationShadeDepthController$DepthAnimation r8 = r8.shadeAnimation
            java.util.Objects.requireNonNull(r8)
            float r8 = r8.radius
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r0 = r0.blurUtils
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.minBlurRadius
            float r0 = (float) r0
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r1 = r1.blurUtils
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.maxBlurRadius
            float r1 = (float) r1
            float r8 = android.util.MathUtils.constrain(r8, r0, r1)
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r1 = r0.blurUtils
            boolean r0 = r0.shouldApplyShadeBlur()
            r2 = 0
            if (r0 == 0) goto L_0x0037
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            java.util.Objects.requireNonNull(r0)
            float r0 = r0.shadeExpansion
            goto L_0x0038
        L_0x0037:
            r0 = r2
        L_0x0038:
            float r0 = com.android.systemui.animation.ShadeInterpolation.getNotificationScrimAlpha(r0)
            float r0 = r1.blurRadiusOfRatio(r0)
            r1 = 1061997773(0x3f4ccccd, float:0.8)
            float r0 = r0 * r1
            r1 = 1045220556(0x3e4ccccc, float:0.19999999)
            float r8 = r8 * r1
            float r8 = r8 + r0
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            java.util.Objects.requireNonNull(r0)
            float r0 = r0.qsPanelExpansion
            float r0 = com.android.systemui.animation.ShadeInterpolation.getNotificationScrimAlpha(r0)
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            java.util.Objects.requireNonNull(r1)
            float r1 = r1.shadeExpansion
            float r0 = r0 * r1
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r1 = r1.blurUtils
            float r0 = r1.blurRadiusOfRatio(r0)
            float r8 = java.lang.Math.max(r8, r0)
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r1 = r0.blurUtils
            float r0 = r0.transitionToFullShadeProgress
            float r0 = r1.blurRadiusOfRatio(r0)
            float r8 = java.lang.Math.max(r8, r0)
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            float r0 = r0.wakeAndUnlockBlurRadius
            float r8 = java.lang.Math.max(r8, r0)
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.blursDisabledForAppLaunch
            if (r0 != 0) goto L_0x0090
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.blursDisabledForUnlock
            if (r0 == 0) goto L_0x0091
        L_0x0090:
            r8 = r2
        L_0x0091:
            com.android.systemui.statusbar.NotificationShadeDepthController r0 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r0 = r0.blurUtils
            java.util.Objects.requireNonNull(r0)
            int r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            r3 = 1
            if (r1 != 0) goto L_0x009f
            r1 = r3
            goto L_0x00a0
        L_0x009f:
            r1 = r9
        L_0x00a0:
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L_0x00a6
            r0 = r2
            goto L_0x00b0
        L_0x00a6:
            int r1 = r0.minBlurRadius
            float r1 = (float) r1
            int r0 = r0.maxBlurRadius
            float r0 = (float) r0
            float r0 = android.util.MathUtils.map(r1, r0, r2, r4, r8)
        L_0x00b0:
            float r0 = android.util.MathUtils.saturate(r0)
            int r8 = (int) r8
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            boolean r5 = r1.scrimsVisible
            if (r5 == 0) goto L_0x00bd
            r8 = r9
            r0 = r2
        L_0x00bd:
            com.android.systemui.statusbar.BlurUtils r1 = r1.blurUtils
            boolean r1 = r1.supportsBlursOnWindows()
            if (r1 != 0) goto L_0x00c6
            r8 = r9
        L_0x00c6:
            float r8 = (float) r8
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.NotificationShadeDepthController$DepthAnimation r1 = r1.brightnessMirrorSpring
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.NotificationShadeDepthController r5 = com.android.systemui.statusbar.NotificationShadeDepthController.this
            com.android.systemui.statusbar.BlurUtils r5 = r5.blurUtils
            float r1 = r1.radius
            java.util.Objects.requireNonNull(r5)
            int r6 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r6 != 0) goto L_0x00e0
            r6 = r3
            goto L_0x00e1
        L_0x00e0:
            r6 = r9
        L_0x00e1:
            if (r6 == 0) goto L_0x00e4
            goto L_0x00ee
        L_0x00e4:
            int r6 = r5.minBlurRadius
            float r6 = (float) r6
            int r5 = r5.maxBlurRadius
            float r5 = (float) r5
            float r2 = android.util.MathUtils.map(r6, r5, r2, r4, r1)
        L_0x00ee:
            float r4 = r4 - r2
            float r4 = r4 * r8
            int r8 = (int) r4
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            boolean r2 = r1.scrimsVisible
            if (r2 == 0) goto L_0x00fc
            boolean r1 = r1.blursDisabledForAppLaunch
            if (r1 != 0) goto L_0x00fc
            r9 = r3
        L_0x00fc:
            r1 = 4096(0x1000, double:2.0237E-320)
            java.lang.String r3 = "shade_blur_radius"
            android.os.Trace.traceCounter(r1, r3, r8)
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            com.android.systemui.statusbar.BlurUtils r2 = r1.blurUtils
            android.view.View r1 = r1.blurRoot
            r3 = 0
            if (r1 != 0) goto L_0x010f
            r1 = r3
            goto L_0x0113
        L_0x010f:
            android.view.ViewRootImpl r1 = r1.getViewRootImpl()
        L_0x0113:
            if (r1 != 0) goto L_0x0123
            com.android.systemui.statusbar.NotificationShadeDepthController r1 = r7.this$0
            java.util.Objects.requireNonNull(r1)
            android.view.View r1 = r1.root
            if (r1 == 0) goto L_0x011f
            r3 = r1
        L_0x011f:
            android.view.ViewRootImpl r1 = r3.getViewRootImpl()
        L_0x0123:
            r2.applyBlur(r1, r8, r9)
            com.android.systemui.statusbar.NotificationShadeDepthController r9 = r7.this$0
            r9.lastAppliedBlur = r8
            com.android.systemui.util.WallpaperController r9 = r9.wallpaperController
            java.util.Objects.requireNonNull(r9)
            r9.notificationShadeZoomOut = r0
            r9.updateZoom()
            com.android.systemui.statusbar.NotificationShadeDepthController r9 = r7.this$0
            java.util.ArrayList r9 = r9.listeners
            java.util.Iterator r9 = r9.iterator()
        L_0x013c:
            boolean r1 = r9.hasNext()
            if (r1 == 0) goto L_0x014f
            java.lang.Object r1 = r9.next()
            com.android.systemui.statusbar.NotificationShadeDepthController$DepthListener r1 = (com.android.systemui.statusbar.NotificationShadeDepthController.DepthListener) r1
            r1.onWallpaperZoomOutChanged(r0)
            r1.onBlurRadiusChanged(r8)
            goto L_0x013c
        L_0x014f:
            com.android.systemui.statusbar.NotificationShadeDepthController r7 = r7.this$0
            com.android.systemui.statusbar.NotificationShadeWindowController r7 = r7.notificationShadeWindowController
            r7.setBackgroundBlurRadius(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShadeDepthController$updateBlurCallback$1.doFrame(long):void");
    }
}
