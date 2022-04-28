package com.android.systemui.doze;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeScreenState$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DozeScreenState$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r6 = this;
            int r0 = r6.$r8$classId
            r1 = 0
            switch(r0) {
                case 0: goto L_0x0134;
                case 1: goto L_0x012c;
                case 2: goto L_0x00bc;
                case 3: goto L_0x0046;
                case 4: goto L_0x0035;
                case 5: goto L_0x0022;
                case 6: goto L_0x0018;
                case 7: goto L_0x0010;
                case 8: goto L_0x0008;
                default: goto L_0x0006;
            }
        L_0x0006:
            goto L_0x015c
        L_0x0008:
            java.lang.Object r6 = r6.f$0
            com.android.wm.shell.pip.phone.PipMotionHelper r6 = (com.android.p012wm.shell.pip.phone.PipMotionHelper) r6
            com.android.p012wm.shell.pip.phone.PipMotionHelper.$r8$lambda$QFpQr4PSFRGfS8YBsx6HKEKo4u4(r6)
            return
        L_0x0010:
            java.lang.Object r6 = r6.f$0
            com.android.wm.shell.onehanded.OneHandedController r6 = (com.android.p012wm.shell.onehanded.OneHandedController) r6
            r6.startOneHanded()
            return
        L_0x0018:
            java.lang.Object r6 = r6.f$0
            com.android.wifitrackerlib.BaseWifiTracker$Scanner r6 = (com.android.wifitrackerlib.BaseWifiTracker.Scanner) r6
            int r0 = com.android.wifitrackerlib.BaseWifiTracker.Scanner.$r8$clinit
            r6.postScan()
            return
        L_0x0022:
            java.lang.Object r6 = r6.f$0
            com.android.systemui.recents.OverviewProxyService$1 r6 = (com.android.systemui.recents.OverviewProxyService.C10571) r6
            int r0 = com.android.systemui.recents.OverviewProxyService.C10571.$r8$clinit
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.recents.OverviewProxyService r6 = com.android.systemui.recents.OverviewProxyService.this
            java.util.Optional<com.android.wm.shell.pip.Pip> r6 = r6.mPipOptional
            com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda15 r0 = com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda15.INSTANCE
            r6.ifPresent(r0)
            return
        L_0x0035:
            java.lang.Object r6 = r6.f$0
            com.android.systemui.qs.tileimpl.QSTileImpl r6 = (com.android.systemui.p006qs.tileimpl.QSTileImpl) r6
            boolean r0 = com.android.systemui.p006qs.tileimpl.QSTileImpl.DEBUG
            java.util.Objects.requireNonNull(r6)
            androidx.lifecycle.LifecycleRegistry r6 = r6.mLifecycle
            androidx.lifecycle.Lifecycle$State r0 = androidx.lifecycle.Lifecycle.State.CREATED
            r6.setCurrentState(r0)
            return
        L_0x0046:
            java.lang.Object r6 = r6.f$0
            com.android.systemui.qs.QSFgsManagerFooter r6 = (com.android.systemui.p006qs.QSFgsManagerFooter) r6
            java.util.Objects.requireNonNull(r6)
            android.content.Context r0 = r6.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r2 = r6.mNumPackages
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            r4[r1] = r5
            r5 = 2131820548(0x7f110004, float:1.9273814E38)
            java.lang.String r0 = r0.getQuantityString(r5, r2, r4)
            android.widget.TextView r2 = r6.mFooterText
            r2.setText(r0)
            android.widget.TextView r2 = r6.mNumberView
            int r4 = r6.mNumPackages
            java.lang.String r4 = java.lang.Integer.toString(r4)
            r2.setText(r4)
            android.widget.TextView r2 = r6.mNumberView
            r2.setContentDescription(r0)
            com.android.systemui.qs.FgsManagerController r0 = r6.mFgsManagerController
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.phone.SystemUIDialog r0 = r0.dialog
            if (r0 != 0) goto L_0x0084
            goto L_0x0085
        L_0x0084:
            r3 = r1
        L_0x0085:
            if (r3 == 0) goto L_0x00bb
            android.view.View r0 = r6.mRootView
            int r2 = r6.mNumPackages
            r3 = 8
            if (r2 <= 0) goto L_0x009a
            com.android.systemui.qs.FgsManagerController r2 = r6.mFgsManagerController
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.isAvailable
            if (r2 == 0) goto L_0x009a
            r2 = r1
            goto L_0x009b
        L_0x009a:
            r2 = r3
        L_0x009b:
            r0.setVisibility(r2)
            android.widget.ImageView r0 = r6.mDotView
            com.android.systemui.qs.FgsManagerController r2 = r6.mFgsManagerController
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.changesSinceDialog
            if (r2 == 0) goto L_0x00aa
            goto L_0x00ab
        L_0x00aa:
            r1 = r3
        L_0x00ab:
            r0.setVisibility(r1)
            com.android.systemui.qs.VisibilityChangedDispatcher$OnVisibilityChangedListener r0 = r6.mVisibilityChangedListener
            if (r0 == 0) goto L_0x00bb
            android.view.View r6 = r6.mRootView
            int r6 = r6.getVisibility()
            r0.onVisibilityChanged(r6)
        L_0x00bb:
            return
        L_0x00bc:
            java.lang.Object r6 = r6.f$0
            com.android.systemui.accessibility.WindowMagnificationController r6 = (com.android.systemui.accessibility.WindowMagnificationController) r6
            boolean r0 = com.android.systemui.accessibility.WindowMagnificationController.DEBUG
            java.util.Objects.requireNonNull(r6)
            android.view.View r0 = r6.mMirrorView
            if (r0 == 0) goto L_0x012b
            android.graphics.Rect r0 = new android.graphics.Rect
            android.graphics.Rect r2 = r6.mMirrorViewBounds
            r0.<init>(r2)
            android.view.View r2 = r6.mMirrorView
            android.graphics.Rect r3 = r6.mMirrorViewBounds
            r2.getBoundsOnScreen(r3)
            int r2 = r0.width()
            android.graphics.Rect r3 = r6.mMirrorViewBounds
            int r3 = r3.width()
            if (r2 != r3) goto L_0x00ef
            int r0 = r0.height()
            android.graphics.Rect r2 = r6.mMirrorViewBounds
            int r2 = r2.height()
            if (r0 == r2) goto L_0x0109
        L_0x00ef:
            android.view.View r0 = r6.mMirrorView
            android.graphics.Rect r2 = new android.graphics.Rect
            android.graphics.Rect r3 = r6.mMirrorViewBounds
            int r3 = r3.width()
            android.graphics.Rect r4 = r6.mMirrorViewBounds
            int r4 = r4.height()
            r2.<init>(r1, r1, r3, r4)
            java.util.List r2 = java.util.Collections.singletonList(r2)
            r0.setSystemGestureExclusionRects(r2)
        L_0x0109:
            r6.updateSysUIState(r1)
            com.android.systemui.accessibility.WindowMagnifierCallback r0 = r6.mWindowMagnifierCallback
            int r1 = r6.mDisplayId
            android.graphics.Rect r6 = r6.mMirrorViewBounds
            com.android.systemui.accessibility.WindowMagnification r0 = (com.android.systemui.accessibility.WindowMagnification) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.accessibility.WindowMagnificationConnectionImpl r0 = r0.mWindowMagnificationConnectionImpl
            if (r0 == 0) goto L_0x012b
            android.view.accessibility.IWindowMagnificationConnectionCallback r0 = r0.mConnectionCallback
            if (r0 == 0) goto L_0x012b
            r0.onWindowMagnifierBoundsChanged(r1, r6)     // Catch:{ RemoteException -> 0x0123 }
            goto L_0x012b
        L_0x0123:
            r6 = move-exception
            java.lang.String r0 = "WindowMagnificationConnectionImpl"
            java.lang.String r1 = "Failed to inform bounds changed"
            android.util.Log.e(r0, r1, r6)
        L_0x012b:
            return
        L_0x012c:
            java.lang.Object r6 = r6.f$0
            com.android.keyguard.KeyguardPasswordViewController r6 = (com.android.keyguard.KeyguardPasswordViewController) r6
            r6.updateSwitchImeButton()
            return
        L_0x0134:
            java.lang.Object r6 = r6.f$0
            com.android.systemui.doze.DozeScreenState r6 = (com.android.systemui.doze.DozeScreenState) r6
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.biometrics.UdfpsController r0 = r6.mUdfpsController
            if (r0 == 0) goto L_0x0154
            boolean r0 = r0.mOnFingerDown
            if (r0 == 0) goto L_0x0154
            com.android.systemui.doze.DozeLog r0 = r6.mDozeLog
            int r1 = r6.mPendingScreenState
            r0.traceDisplayStateDelayedByUdfps(r1)
            android.os.Handler r0 = r6.mHandler
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0 r6 = r6.mApplyPendingScreenState
            r1 = 1200(0x4b0, double:5.93E-321)
            r0.postDelayed(r6, r1)
            goto L_0x015b
        L_0x0154:
            int r0 = r6.mPendingScreenState
            r6.applyScreenState(r0)
            r6.mPendingScreenState = r1
        L_0x015b:
            return
        L_0x015c:
            java.lang.Object r6 = r6.f$0
            android.animation.ValueAnimator r6 = (android.animation.ValueAnimator) r6
            r6.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0.run():void");
    }
}
