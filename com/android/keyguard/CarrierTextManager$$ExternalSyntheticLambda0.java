package com.android.keyguard;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CarrierTextManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x0227  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0286  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x02f4  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x032d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            int r0 = r12.$r8$classId
            r1 = 0
            r2 = 2
            r3 = 4
            r4 = 0
            r5 = 1
            r6 = 0
            switch(r0) {
                case 0: goto L_0x034f;
                case 1: goto L_0x01b1;
                case 2: goto L_0x019c;
                case 3: goto L_0x010a;
                case 4: goto L_0x00f4;
                case 5: goto L_0x00ce;
                case 6: goto L_0x00bc;
                case 7: goto L_0x00a5;
                case 8: goto L_0x0098;
                case 9: goto L_0x0033;
                case 10: goto L_0x002b;
                case 11: goto L_0x0021;
                case 12: goto L_0x000d;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0372
        L_0x000d:
            java.lang.Object r12 = r12.f$0
            com.google.android.systemui.dreamliner.DockGestureController r12 = (com.google.android.systemui.dreamliner.DockGestureController) r12
            int r0 = com.google.android.systemui.dreamliner.DockGestureController.$r8$clinit
            java.util.Objects.requireNonNull(r12)
            android.widget.FrameLayout r0 = r12.mPhotoPreview
            r0.setAlpha(r4)
            android.widget.FrameLayout r12 = r12.mPhotoPreview
            r12.setVisibility(r3)
            return
        L_0x0021:
            java.lang.Object r12 = r12.f$0
            com.google.android.systemui.assist.uihints.NgaUiController r12 = (com.google.android.systemui.assist.uihints.NgaUiController) r12
            boolean r0 = com.google.android.systemui.assist.uihints.NgaUiController.VERBOSE
            r12.closeNgaUi()
            return
        L_0x002b:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.assist.AssistManager$UiController r12 = (com.android.systemui.assist.AssistManager.UiController) r12
            r12.hide()
            return
        L_0x0033:
            java.lang.Object r12 = r12.f$0
            com.android.wm.shell.pip.phone.PipTouchHandler r12 = (com.android.p012wm.shell.pip.phone.PipTouchHandler) r12
            java.util.Objects.requireNonNull(r12)
            com.android.wm.shell.pip.PipBoundsState r0 = r12.mPipBoundsState
            boolean r0 = r0.isStashed()
            if (r0 == 0) goto L_0x0052
            r12.animateToUnStashedState()
            com.android.wm.shell.pip.PipUiEventLogger r0 = r12.mPipUiEventLogger
            com.android.wm.shell.pip.PipUiEventLogger$PipUiEventEnum r1 = com.android.p012wm.shell.pip.PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED
            r0.log(r1)
            com.android.wm.shell.pip.PipBoundsState r12 = r12.mPipBoundsState
            r12.setStashed(r6)
            goto L_0x0097
        L_0x0052:
            com.android.wm.shell.pip.phone.PhonePipMenuController r0 = r12.mMenuController
            r6 = 1
            com.android.wm.shell.pip.PipBoundsState r2 = r12.mPipBoundsState
            android.graphics.Rect r7 = r2.getBounds()
            r8 = 1
            boolean r10 = r12.willResizeMenu()
            java.util.Objects.requireNonNull(r0)
            if (r10 == 0) goto L_0x0084
            boolean r12 = r0.isMenuVisible()
            if (r12 == 0) goto L_0x0084
            com.android.wm.shell.pip.phone.PipMenuView r12 = r0.mPipMenuView
            java.util.Objects.requireNonNull(r12)
            android.view.View r2 = r12.mMenuContainer
            r2.setAlpha(r4)
            android.view.View r2 = r12.mSettingsButton
            r2.setAlpha(r4)
            android.view.View r2 = r12.mDismissButton
            r2.setAlpha(r4)
            android.view.View r12 = r12.mEnterSplitButton
            r12.setAlpha(r4)
        L_0x0084:
            boolean r12 = r0.maybeCreateSyncApplier()
            if (r12 != 0) goto L_0x008b
            goto L_0x0097
        L_0x008b:
            r0.movePipMenu(r1, r1, r7)
            r0.updateMenuBounds(r7)
            com.android.wm.shell.pip.phone.PipMenuView r5 = r0.mPipMenuView
            r9 = r10
            r5.showMenu(r6, r7, r8, r9, r10)
        L_0x0097:
            return
        L_0x0098:
            java.lang.Object r12 = r12.f$0
            com.android.wm.shell.bubbles.BubbleController$BubblesImpl r12 = (com.android.p012wm.shell.bubbles.BubbleController.BubblesImpl) r12
            java.util.Objects.requireNonNull(r12)
            com.android.wm.shell.bubbles.BubbleController r12 = com.android.p012wm.shell.bubbles.BubbleController.this
            r12.collapseStack()
            return
        L_0x00a5:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.tuner.TunerFragment r12 = (com.android.systemui.tuner.TunerFragment) r12
            java.lang.String[] r0 = com.android.systemui.tuner.TunerFragment.DEBUG_ONLY
            java.util.Objects.requireNonNull(r12)
            android.app.Activity r0 = r12.getActivity()
            if (r0 == 0) goto L_0x00bb
            android.app.Activity r12 = r12.getActivity()
            r12.finish()
        L_0x00bb:
            return
        L_0x00bc:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager r12 = (com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager) r12
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.statusbar.NotificationShadeWindowController r0 = r12.mNotificationShadeWindowController
            boolean r1 = r12.mOccluded
            r0.setKeyguardOccluded(r1)
            r12.reset(r5)
            return
        L_0x00ce:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.statusbar.phone.HeadsUpAppearanceController r12 = (com.android.systemui.statusbar.phone.HeadsUpAppearanceController) r12
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.statusbar.phone.NotificationIconAreaController r0 = r12.mNotificationIconAreaController
            T r12 = r12.mView
            com.android.systemui.statusbar.HeadsUpStatusBarView r12 = (com.android.systemui.statusbar.HeadsUpStatusBarView) r12
            java.util.Objects.requireNonNull(r12)
            android.graphics.Rect r12 = r12.mIconDrawingRect
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.NotificationIconContainer r0 = r0.mNotificationIcons
            java.util.Objects.requireNonNull(r0)
            r0.mIsolatedIconLocation = r12
            r0.resetViewStates()
            r0.calculateIconTranslations()
            r0.applyIconStates()
            return
        L_0x00f4:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.keyguard.DismissCallbackWrapper r12 = (com.android.systemui.keyguard.DismissCallbackWrapper) r12
            java.util.Objects.requireNonNull(r12)
            com.android.internal.policy.IKeyguardDismissCallback r12 = r12.mCallback     // Catch:{ RemoteException -> 0x0101 }
            r12.onDismissCancelled()     // Catch:{ RemoteException -> 0x0101 }
            goto L_0x0109
        L_0x0101:
            r12 = move-exception
            java.lang.String r0 = "DismissCallbackWrapper"
            java.lang.String r1 = "Failed to call callback"
            android.util.Log.i(r0, r1, r12)
        L_0x0109:
            return
        L_0x010a:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.biometrics.AuthContainerView r12 = (com.android.systemui.biometrics.AuthContainerView) r12
            int r0 = com.android.systemui.biometrics.AuthContainerView.$r8$clinit
            java.util.Objects.requireNonNull(r12)
            android.view.View r0 = r12.mPanelView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            android.view.ViewPropertyAnimator r0 = r0.translationY(r4)
            r5 = 250(0xfa, double:1.235E-321)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r5)
            android.view.animation.PathInterpolator r1 = r12.mLinearOutSlowIn
            android.view.ViewPropertyAnimator r0 = r0.setInterpolator(r1)
            android.view.ViewPropertyAnimator r0 = r0.withLayer()
            com.android.wm.shell.TaskView$$ExternalSyntheticLambda3 r1 = new com.android.wm.shell.TaskView$$ExternalSyntheticLambda3
            r1.<init>(r12, r2)
            android.view.ViewPropertyAnimator r0 = r0.withEndAction(r1)
            r0.start()
            android.widget.ScrollView r0 = r12.mBiometricScrollView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            android.view.ViewPropertyAnimator r0 = r0.translationY(r4)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r5)
            android.view.animation.PathInterpolator r1 = r12.mLinearOutSlowIn
            android.view.ViewPropertyAnimator r0 = r0.setInterpolator(r1)
            android.view.ViewPropertyAnimator r0 = r0.withLayer()
            r0.start()
            com.android.systemui.biometrics.AuthCredentialView r0 = r12.mCredentialView
            if (r0 == 0) goto L_0x0180
            boolean r0 = r0.isAttachedToWindow()
            if (r0 == 0) goto L_0x0180
            com.android.systemui.biometrics.AuthCredentialView r0 = r12.mCredentialView
            float r1 = r12.mTranslationY
            r0.setY(r1)
            com.android.systemui.biometrics.AuthCredentialView r0 = r12.mCredentialView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            android.view.ViewPropertyAnimator r0 = r0.translationY(r4)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r5)
            android.view.animation.PathInterpolator r1 = r12.mLinearOutSlowIn
            android.view.ViewPropertyAnimator r0 = r0.setInterpolator(r1)
            android.view.ViewPropertyAnimator r0 = r0.withLayer()
            r0.start()
        L_0x0180:
            android.view.ViewPropertyAnimator r0 = r12.animate()
            r1 = 1065353216(0x3f800000, float:1.0)
            android.view.ViewPropertyAnimator r0 = r0.alpha(r1)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r5)
            android.view.animation.PathInterpolator r12 = r12.mLinearOutSlowIn
            android.view.ViewPropertyAnimator r12 = r0.setInterpolator(r12)
            android.view.ViewPropertyAnimator r12 = r12.withLayer()
            r12.start()
            return
        L_0x019c:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.appops.AppOpsControllerImpl r12 = (com.android.systemui.appops.AppOpsControllerImpl) r12
            int[] r0 = com.android.systemui.appops.AppOpsControllerImpl.OPS
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.appops.AppOpsControllerImpl$1 r0 = r12.mAudioRecordingCallback
            android.media.AudioManager r12 = r12.mAudioManager
            java.util.List r12 = r12.getActiveRecordingConfigurations()
            r0.onRecordingConfigChanged(r12)
            return
        L_0x01b1:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.ImageWallpaper$GLEngine r12 = (com.android.systemui.ImageWallpaper.GLEngine) r12
            int r0 = com.android.systemui.ImageWallpaper.GLEngine.MIN_SURFACE_WIDTH
            java.util.Objects.requireNonNull(r12)
            java.lang.String r0 = "ImageWallpaper#drawFrame"
            android.os.Trace.beginSection(r0)
            java.lang.String r0 = "ImageWallpaper#preRender"
            android.os.Trace.beginSection(r0)
            android.view.SurfaceHolder r0 = r12.getSurfaceHolder()
            android.graphics.Rect r0 = r0.getSurfaceFrame()
            com.android.systemui.ImageWallpaper r1 = com.android.systemui.ImageWallpaper.this
            android.os.HandlerThread r1 = r1.mWorker
            if (r1 != 0) goto L_0x01d3
            goto L_0x01dc
        L_0x01d3:
            android.os.Handler r1 = r1.getThreadHandler()
            java.lang.Runnable r2 = r12.mFinishRenderingTask
            r1.removeCallbacks(r2)
        L_0x01dc:
            com.android.systemui.glwallpaper.EglHelper r1 = r12.mEglHelper
            boolean r1 = r1.hasEglContext()
            java.lang.String r2 = "ImageWallpaper"
            if (r1 != 0) goto L_0x01fe
            com.android.systemui.glwallpaper.EglHelper r1 = r12.mEglHelper
            r1.destroyEglSurface()
            com.android.systemui.glwallpaper.EglHelper r1 = r12.mEglHelper
            boolean r1 = r1.createEglContext()
            if (r1 != 0) goto L_0x01fc
            android.graphics.RectF r1 = com.android.systemui.ImageWallpaper.LOCAL_COLOR_BOUNDS
            java.lang.String r1 = "recreate egl context failed!"
            android.util.Log.w(r2, r1)
            goto L_0x01fe
        L_0x01fc:
            r1 = r5
            goto L_0x01ff
        L_0x01fe:
            r1 = r6
        L_0x01ff:
            com.android.systemui.glwallpaper.EglHelper r4 = r12.mEglHelper
            boolean r4 = r4.hasEglContext()
            if (r4 == 0) goto L_0x022f
            com.android.systemui.glwallpaper.EglHelper r4 = r12.mEglHelper
            boolean r4 = r4.hasEglSurface()
            if (r4 != 0) goto L_0x022f
            com.android.systemui.glwallpaper.EglHelper r4 = r12.mEglHelper
            android.view.SurfaceHolder r7 = r12.getSurfaceHolder()
            com.android.systemui.glwallpaper.ImageWallpaperRenderer r8 = r12.mRenderer
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.glwallpaper.ImageWallpaperRenderer$WallpaperTexture r8 = r8.mTexture
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.mWcgContent
            boolean r4 = r4.createEglSurface(r7, r8)
            if (r4 != 0) goto L_0x022f
            android.graphics.RectF r4 = com.android.systemui.ImageWallpaper.LOCAL_COLOR_BOUNDS
            java.lang.String r4 = "recreate egl surface failed!"
            android.util.Log.w(r2, r4)
        L_0x022f:
            com.android.systemui.glwallpaper.EglHelper r4 = r12.mEglHelper
            boolean r4 = r4.hasEglContext()
            if (r4 == 0) goto L_0x0256
            com.android.systemui.glwallpaper.EglHelper r4 = r12.mEglHelper
            boolean r4 = r4.hasEglSurface()
            if (r4 == 0) goto L_0x0256
            if (r1 == 0) goto L_0x0256
            com.android.systemui.glwallpaper.ImageWallpaperRenderer r1 = r12.mRenderer
            r1.onSurfaceCreated()
            com.android.systemui.glwallpaper.ImageWallpaperRenderer r1 = r12.mRenderer
            int r4 = r0.width()
            int r0 = r0.height()
            java.util.Objects.requireNonNull(r1)
            android.opengl.GLES20.glViewport(r6, r6, r4, r0)
        L_0x0256:
            android.os.Trace.endSection()
            java.lang.String r0 = "ImageWallpaper#requestRender"
            android.os.Trace.beginSection(r0)
            android.view.SurfaceHolder r0 = r12.getSurfaceHolder()
            android.graphics.Rect r0 = r0.getSurfaceFrame()
            com.android.systemui.glwallpaper.EglHelper r1 = r12.mEglHelper
            boolean r1 = r1.hasEglContext()
            if (r1 == 0) goto L_0x0283
            com.android.systemui.glwallpaper.EglHelper r1 = r12.mEglHelper
            boolean r1 = r1.hasEglSurface()
            if (r1 == 0) goto L_0x0283
            int r1 = r0.width()
            if (r1 <= 0) goto L_0x0283
            int r1 = r0.height()
            if (r1 <= 0) goto L_0x0283
            goto L_0x0284
        L_0x0283:
            r5 = r6
        L_0x0284:
            if (r5 == 0) goto L_0x02f4
            com.android.systemui.glwallpaper.ImageWallpaperRenderer r0 = r12.mRenderer
            java.util.Objects.requireNonNull(r0)
            r1 = 16384(0x4000, float:2.2959E-41)
            android.opengl.GLES20.glClear(r1)
            android.graphics.Rect r1 = r0.mSurfaceSize
            int r1 = r1.width()
            android.graphics.Rect r4 = r0.mSurfaceSize
            int r4 = r4.height()
            android.opengl.GLES20.glViewport(r6, r6, r1, r4)
            com.android.systemui.glwallpaper.ImageGLWallpaper r1 = r0.mWallpaper
            java.util.Objects.requireNonNull(r1)
            r4 = 33984(0x84c0, float:4.7622E-41)
            android.opengl.GLES20.glActiveTexture(r4)
            int r4 = r1.mTextureId
            r5 = 3553(0xde1, float:4.979E-42)
            android.opengl.GLES20.glBindTexture(r5, r4)
            int r1 = r1.mUniTexture
            android.opengl.GLES20.glUniform1i(r1, r6)
            com.android.systemui.glwallpaper.ImageGLWallpaper r0 = r0.mWallpaper
            java.util.Objects.requireNonNull(r0)
            r0 = 6
            android.opengl.GLES20.glDrawArrays(r3, r6, r0)
            com.android.systemui.glwallpaper.EglHelper r0 = r12.mEglHelper
            java.util.Objects.requireNonNull(r0)
            android.opengl.EGLDisplay r1 = r0.mEglDisplay
            android.opengl.EGLSurface r0 = r0.mEglSurface
            boolean r0 = android.opengl.EGL14.eglSwapBuffers(r1, r0)
            int r1 = android.opengl.EGL14.eglGetError()
            r3 = 12288(0x3000, float:1.7219E-41)
            if (r1 == r3) goto L_0x02ea
            java.lang.String r3 = "eglSwapBuffers failed: "
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            java.lang.String r1 = android.opengl.GLUtils.getEGLErrorString(r1)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            java.lang.String r3 = "EglHelper"
            android.util.Log.w(r3, r1)
        L_0x02ea:
            if (r0 != 0) goto L_0x0323
            android.graphics.RectF r0 = com.android.systemui.ImageWallpaper.LOCAL_COLOR_BOUNDS
            java.lang.String r0 = "drawFrame failed!"
            android.util.Log.e(r2, r0)
            goto L_0x0323
        L_0x02f4:
            android.graphics.RectF r1 = com.android.systemui.ImageWallpaper.LOCAL_COLOR_BOUNDS
            java.lang.String r1 = "requestRender: not ready, has context="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            com.android.systemui.glwallpaper.EglHelper r3 = r12.mEglHelper
            boolean r3 = r3.hasEglContext()
            r1.append(r3)
            java.lang.String r3 = ", has surface="
            r1.append(r3)
            com.android.systemui.glwallpaper.EglHelper r3 = r12.mEglHelper
            boolean r3 = r3.hasEglSurface()
            r1.append(r3)
            java.lang.String r3 = ", frame="
            r1.append(r3)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.e(r2, r0)
        L_0x0323:
            android.os.Trace.endSection()
            com.android.systemui.ImageWallpaper r0 = com.android.systemui.ImageWallpaper.this
            android.os.HandlerThread r0 = r0.mWorker
            if (r0 != 0) goto L_0x032d
            goto L_0x0348
        L_0x032d:
            if (r0 != 0) goto L_0x0330
            goto L_0x0339
        L_0x0330:
            android.os.Handler r0 = r0.getThreadHandler()
            java.lang.Runnable r1 = r12.mFinishRenderingTask
            r0.removeCallbacks(r1)
        L_0x0339:
            com.android.systemui.ImageWallpaper r0 = com.android.systemui.ImageWallpaper.this
            android.os.HandlerThread r0 = r0.mWorker
            android.os.Handler r0 = r0.getThreadHandler()
            java.lang.Runnable r1 = r12.mFinishRenderingTask
            r2 = 1000(0x3e8, double:4.94E-321)
            r0.postDelayed(r1, r2)
        L_0x0348:
            r12.reportEngineShown(r6)
            android.os.Trace.endSection()
            return
        L_0x034f:
            java.lang.Object r12 = r12.f$0
            com.android.keyguard.CarrierTextManager r12 = (com.android.keyguard.CarrierTextManager) r12
            java.util.Objects.requireNonNull(r12)
            android.content.Context r0 = r12.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String r1 = "android.hardware.telephony"
            boolean r0 = r0.hasSystemFeature(r1)
            if (r0 == 0) goto L_0x0371
            java.util.concurrent.atomic.AtomicBoolean r1 = r12.mNetworkSupported
            boolean r0 = r1.compareAndSet(r6, r0)
            if (r0 == 0) goto L_0x0371
            com.android.keyguard.CarrierTextManager$CarrierTextCallback r0 = r12.mCarrierTextCallback
            r12.handleSetListening(r0)
        L_0x0371:
            return
        L_0x0372:
            java.lang.Object r12 = r12.f$0
            com.google.android.systemui.reversecharging.ReverseChargingViewController r12 = (com.google.android.systemui.reversecharging.ReverseChargingViewController) r12
            boolean r0 = com.google.android.systemui.reversecharging.ReverseChargingViewController.DEBUG
            java.util.Objects.requireNonNull(r12)
            com.google.android.systemui.ambientmusic.AmbientIndicationContainer r0 = r12.mAmbientIndicationContainer
            if (r0 == 0) goto L_0x0441
            boolean r0 = r12.mReverse
            java.lang.String r3 = "updateMessage(): rtx="
            java.lang.String r4 = "ReverseChargingViewCtrl"
            if (r0 != 0) goto L_0x03f6
            com.android.systemui.statusbar.policy.BatteryController r0 = r12.mBatteryController
            boolean r0 = r0.isWirelessCharging()
            if (r0 == 0) goto L_0x03f6
            java.lang.String r0 = r12.mName
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x03f6
            android.content.Context r0 = r12.mContext
            android.content.res.Resources r0 = r0.getResources()
            r7 = 2131953163(0x7f13060b, float:1.954279E38)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r8 = r12.mName
            r2[r6] = r8
            int r6 = r12.mLevel
            double r8 = (double) r6
            r10 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r8 = r8 / r10
            java.text.NumberFormat r6 = java.text.NumberFormat.getPercentInstance()
            java.lang.String r6 = r6.format(r8)
            r2[r5] = r6
            java.lang.String r0 = r0.getString(r7, r2)
            boolean r2 = com.google.android.systemui.reversecharging.ReverseChargingViewController.DEBUG
            if (r2 == 0) goto L_0x03d7
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            boolean r3 = r12.mReverse
            r2.append(r3)
            java.lang.String r3 = " wlcString="
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r4, r2)
        L_0x03d7:
            com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle r2 = r12.mKeyguardIndicationController
            r2.setReverseChargingMessage(r0)
            com.google.android.systemui.ambientmusic.AmbientIndicationContainer r2 = r12.mAmbientIndicationContainer
            java.util.Objects.requireNonNull(r2)
            java.lang.CharSequence r3 = r2.mWirelessChargingMessage
            boolean r3 = java.util.Objects.equals(r3, r0)
            if (r3 == 0) goto L_0x03ee
            java.lang.CharSequence r3 = r2.mReverseChargingMessage
            if (r3 != 0) goto L_0x03ee
            goto L_0x0441
        L_0x03ee:
            r2.mWirelessChargingMessage = r0
            r2.mReverseChargingMessage = r1
            r2.updatePill()
            goto L_0x0441
        L_0x03f6:
            com.google.android.systemui.statusbar.KeyguardIndicationControllerGoogle r0 = r12.mKeyguardIndicationController
            boolean r2 = r12.mProvidingBattery
            java.lang.String r5 = ""
            if (r2 == 0) goto L_0x0401
            java.lang.String r2 = r12.mReverseCharging
            goto L_0x0402
        L_0x0401:
            r2 = r5
        L_0x0402:
            r0.setReverseChargingMessage(r2)
            com.google.android.systemui.ambientmusic.AmbientIndicationContainer r0 = r12.mAmbientIndicationContainer
            boolean r2 = r12.mProvidingBattery
            if (r2 == 0) goto L_0x040e
            java.lang.String r2 = r12.mReverseCharging
            goto L_0x040f
        L_0x040e:
            r2 = r5
        L_0x040f:
            java.util.Objects.requireNonNull(r0)
            java.lang.CharSequence r6 = r0.mReverseChargingMessage
            boolean r6 = java.util.Objects.equals(r6, r2)
            if (r6 == 0) goto L_0x041f
            java.lang.CharSequence r6 = r0.mWirelessChargingMessage
            if (r6 != 0) goto L_0x041f
            goto L_0x0426
        L_0x041f:
            r0.mWirelessChargingMessage = r1
            r0.mReverseChargingMessage = r2
            r0.updatePill()
        L_0x0426:
            boolean r0 = com.google.android.systemui.reversecharging.ReverseChargingViewController.DEBUG
            if (r0 == 0) goto L_0x0441
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            boolean r1 = r12.mReverse
            r0.append(r1)
            java.lang.String r1 = " rtxString="
            r0.append(r1)
            boolean r1 = r12.mProvidingBattery
            if (r1 == 0) goto L_0x043e
            java.lang.String r5 = r12.mReverseCharging
        L_0x043e:
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2.m15m(r0, r5, r4)
        L_0x0441:
            com.android.systemui.statusbar.phone.StatusBarIconController r0 = r12.mStatusBarIconController
            java.lang.String r1 = r12.mSlotReverseCharging
            java.lang.String r2 = r12.mContentDescription
            r3 = 2131232232(0x7f0805e8, float:1.8080567E38)
            r0.setIcon(r1, r3, r2)
            com.android.systemui.statusbar.phone.StatusBarIconController r0 = r12.mStatusBarIconController
            java.lang.String r1 = r12.mSlotReverseCharging
            boolean r12 = r12.mProvidingBattery
            r0.setIconVisibility(r1, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0.run():void");
    }
}
