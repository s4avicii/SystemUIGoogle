package com.android.systemui;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00f0 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            int r0 = r12.$r8$classId
            r1 = 1
            r2 = 0
            r3 = 0
            switch(r0) {
                case 0: goto L_0x0125;
                case 1: goto L_0x009b;
                case 2: goto L_0x0091;
                case 3: goto L_0x0089;
                case 4: goto L_0x0074;
                case 5: goto L_0x0063;
                case 6: goto L_0x0040;
                case 7: goto L_0x0038;
                case 8: goto L_0x002e;
                case 9: goto L_0x0021;
                case 10: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x016c
        L_0x000a:
            java.lang.Object r12 = r12.f$0
            com.google.android.systemui.elmyra.gates.CameraVisibility r12 = (com.google.android.systemui.elmyra.gates.CameraVisibility) r12
            int r0 = com.google.android.systemui.elmyra.gates.CameraVisibility.C22461.$r8$clinit
            java.util.Objects.requireNonNull(r12)
            boolean r0 = r12.isCameraShowing()
            boolean r1 = r12.mCameraShowing
            if (r1 == r0) goto L_0x0020
            r12.mCameraShowing = r0
            r12.notifyListener()
        L_0x0020:
            return
        L_0x0021:
            java.lang.Object r12 = r12.f$0
            com.google.android.systemui.assist.OpaEnabledReceiver r12 = (com.google.android.systemui.assist.OpaEnabledReceiver) r12
            java.util.Objects.requireNonNull(r12)
            android.content.Context r0 = r12.mContext
            r12.dispatchOpaEnabledState(r0)
            return
        L_0x002e:
            java.lang.Object r12 = r12.f$0
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r12 = (com.android.p012wm.shell.pip.PipAnimationController.PipTransitionAnimator) r12
            java.util.Objects.requireNonNull(r12)
            r12.mContentOverlay = r2
            return
        L_0x0038:
            java.lang.Object r12 = r12.f$0
            android.animation.ValueAnimator r12 = (android.animation.ValueAnimator) r12
            r12.start()
            return
        L_0x0040:
            java.lang.Object r12 = r12.f$0
            com.android.wm.shell.bubbles.BubbleStackView r12 = (com.android.p012wm.shell.bubbles.BubbleStackView) r12
            int r0 = com.android.p012wm.shell.bubbles.BubbleStackView.FLYOUT_HIDE_AFTER
            java.util.Objects.requireNonNull(r12)
            android.view.ViewGroup r0 = r12.mManageMenu
            android.view.View r0 = r0.getChildAt(r3)
            r0.requestAccessibilityFocus()
            com.android.wm.shell.bubbles.BubbleViewProvider r12 = r12.mExpandedBubble
            com.android.wm.shell.bubbles.BubbleExpandedView r12 = r12.getExpandedView()
            java.util.Objects.requireNonNull(r12)
            com.android.wm.shell.TaskView r12 = r12.mTaskView
            if (r12 == 0) goto L_0x0062
            r12.onLocationChanged()
        L_0x0062:
            return
        L_0x0063:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.statusbar.phone.PhoneStatusBarPolicy r12 = (com.android.systemui.statusbar.phone.PhoneStatusBarPolicy) r12
            boolean r0 = com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.DEBUG
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.statusbar.phone.StatusBarIconController r0 = r12.mIconController
            java.lang.String r12 = r12.mSlotScreenRecord
            r0.setIconVisibility(r12, r3)
            return
        L_0x0074:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.qs.tiles.dialog.InternetDialog r12 = (com.android.systemui.p006qs.tiles.dialog.InternetDialog) r12
            boolean r0 = com.android.systemui.p006qs.tiles.dialog.InternetDialog.DEBUG
            java.util.Objects.requireNonNull(r12)
            r12.mIsSearchingHidden = r1
            android.widget.TextView r0 = r12.mInternetDialogSubTitle
            java.lang.CharSequence r12 = r12.getSubtitleText()
            r0.setText(r12)
            return
        L_0x0089:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.privacy.television.TvOngoingPrivacyChip r12 = (com.android.systemui.privacy.television.TvOngoingPrivacyChip) r12
            r12.makeAccessibilityAnnouncement()
            return
        L_0x0091:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.navigationbar.buttons.KeyButtonRipple r12 = (com.android.systemui.navigationbar.buttons.KeyButtonRipple) r12
            android.view.animation.PathInterpolator r0 = com.android.systemui.navigationbar.buttons.KeyButtonRipple.ALPHA_OUT_INTERPOLATOR
            r12.enterHardware()
            return
        L_0x009b:
            java.lang.Object r12 = r12.f$0
            androidx.dynamicanimation.animation.AnimationHandler r12 = (androidx.dynamicanimation.animation.AnimationHandler) r12
            java.lang.ThreadLocal<androidx.dynamicanimation.animation.AnimationHandler> r0 = androidx.dynamicanimation.animation.AnimationHandler.sAnimatorHandler
            java.util.Objects.requireNonNull(r12)
            androidx.dynamicanimation.animation.AnimationHandler$AnimationCallbackDispatcher r12 = r12.mCallbackDispatcher
            java.util.Objects.requireNonNull(r12)
            androidx.dynamicanimation.animation.AnimationHandler r0 = androidx.dynamicanimation.animation.AnimationHandler.this
            long r4 = android.os.SystemClock.uptimeMillis()
            r0.mCurrentFrameTime = r4
            androidx.dynamicanimation.animation.AnimationHandler r0 = androidx.dynamicanimation.animation.AnimationHandler.this
            long r4 = r0.mCurrentFrameTime
            long r6 = android.os.SystemClock.uptimeMillis()
            r8 = r3
        L_0x00ba:
            java.util.ArrayList<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback> r9 = r0.mAnimationCallbacks
            int r9 = r9.size()
            if (r8 >= r9) goto L_0x00f3
            java.util.ArrayList<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback> r9 = r0.mAnimationCallbacks
            java.lang.Object r9 = r9.get(r8)
            androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback r9 = (androidx.dynamicanimation.animation.AnimationHandler.AnimationFrameCallback) r9
            if (r9 != 0) goto L_0x00cd
            goto L_0x00f0
        L_0x00cd:
            androidx.collection.SimpleArrayMap<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback, java.lang.Long> r10 = r0.mDelayedCallbackStartTime
            java.util.Objects.requireNonNull(r10)
            java.lang.Object r10 = r10.getOrDefault(r9, r2)
            java.lang.Long r10 = (java.lang.Long) r10
            if (r10 != 0) goto L_0x00db
            goto L_0x00e8
        L_0x00db:
            long r10 = r10.longValue()
            int r10 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r10 >= 0) goto L_0x00ea
            androidx.collection.SimpleArrayMap<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback, java.lang.Long> r10 = r0.mDelayedCallbackStartTime
            r10.remove(r9)
        L_0x00e8:
            r10 = r1
            goto L_0x00eb
        L_0x00ea:
            r10 = r3
        L_0x00eb:
            if (r10 == 0) goto L_0x00f0
            r9.doAnimationFrame(r4)
        L_0x00f0:
            int r8 = r8 + 1
            goto L_0x00ba
        L_0x00f3:
            boolean r1 = r0.mListDirty
            if (r1 == 0) goto L_0x0111
            java.util.ArrayList<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback> r1 = r0.mAnimationCallbacks
            int r1 = r1.size()
        L_0x00fd:
            int r1 = r1 + -1
            if (r1 < 0) goto L_0x010f
            java.util.ArrayList<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback> r2 = r0.mAnimationCallbacks
            java.lang.Object r2 = r2.get(r1)
            if (r2 != 0) goto L_0x00fd
            java.util.ArrayList<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback> r2 = r0.mAnimationCallbacks
            r2.remove(r1)
            goto L_0x00fd
        L_0x010f:
            r0.mListDirty = r3
        L_0x0111:
            androidx.dynamicanimation.animation.AnimationHandler r0 = androidx.dynamicanimation.animation.AnimationHandler.this
            java.util.ArrayList<androidx.dynamicanimation.animation.AnimationHandler$AnimationFrameCallback> r0 = r0.mAnimationCallbacks
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0124
            androidx.dynamicanimation.animation.AnimationHandler r12 = androidx.dynamicanimation.animation.AnimationHandler.this
            androidx.dynamicanimation.animation.AnimationHandler$FrameCallbackScheduler r0 = r12.mScheduler
            com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 r12 = r12.mRunnable
            r0.postFrameCallback(r12)
        L_0x0124:
            return
        L_0x0125:
            java.lang.Object r12 = r12.f$0
            com.android.systemui.ImageWallpaper$GLEngine r12 = (com.android.systemui.ImageWallpaper.GLEngine) r12
            java.util.Objects.requireNonNull(r12)
            com.android.systemui.glwallpaper.ImageWallpaperRenderer r0 = r12.mRenderer
            java.util.Objects.requireNonNull(r0)
            r12.mRenderer = r2
            com.android.systemui.glwallpaper.EglHelper r0 = r12.mEglHelper
            java.util.Objects.requireNonNull(r0)
            boolean r1 = r0.hasEglSurface()
            if (r1 == 0) goto L_0x0141
            r0.destroyEglSurface()
        L_0x0141:
            boolean r1 = r0.hasEglContext()
            if (r1 == 0) goto L_0x0158
            boolean r1 = r0.hasEglContext()
            if (r1 == 0) goto L_0x0158
            android.opengl.EGLDisplay r1 = r0.mEglDisplay
            android.opengl.EGLContext r4 = r0.mEglContext
            android.opengl.EGL14.eglDestroyContext(r1, r4)
            android.opengl.EGLContext r1 = android.opengl.EGL14.EGL_NO_CONTEXT
            r0.mEglContext = r1
        L_0x0158:
            boolean r1 = r0.hasEglDisplay()
            if (r1 == 0) goto L_0x0167
            android.opengl.EGLDisplay r1 = r0.mEglDisplay
            android.opengl.EGL14.eglTerminate(r1)
            android.opengl.EGLDisplay r1 = android.opengl.EGL14.EGL_NO_DISPLAY
            r0.mEglDisplay = r1
        L_0x0167:
            r0.mEglReady = r3
            r12.mEglHelper = r2
            return
        L_0x016c:
            java.lang.Object r12 = r12.f$0
            com.google.android.systemui.statusbar.phone.AutoTileManagerGoogle$1 r12 = (com.google.android.systemui.statusbar.phone.AutoTileManagerGoogle.C24311) r12
            java.util.Objects.requireNonNull(r12)
            com.google.android.systemui.statusbar.phone.AutoTileManagerGoogle r12 = com.google.android.systemui.statusbar.phone.AutoTileManagerGoogle.this
            com.android.systemui.statusbar.policy.BatteryController r0 = r12.mBatteryController
            com.google.android.systemui.statusbar.phone.AutoTileManagerGoogle$1 r12 = r12.mBatteryControllerCallback
            r0.removeCallback(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0.run():void");
    }
}
