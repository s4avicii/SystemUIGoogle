package com.android.p012wm.shell.startingsurface;

/* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda0(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:177:0x0788  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x079f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r39 = this;
            r0 = r39
            int r1 = r0.$r8$classId
            r2 = 1
            switch(r1) {
                case 0: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x07ef
        L_0x000a:
            java.lang.Object r1 = r0.f$0
            com.android.wm.shell.startingsurface.StartingWindowController r1 = (com.android.p012wm.shell.startingsurface.StartingWindowController) r1
            java.lang.Object r3 = r0.f$1
            r7 = r3
            android.window.StartingWindowInfo r7 = (android.window.StartingWindowInfo) r7
            java.lang.Object r0 = r0.f$2
            android.os.IBinder r0 = (android.os.IBinder) r0
            java.util.Objects.requireNonNull(r1)
            java.lang.String r3 = "addStartingWindow"
            r4 = 32
            android.os.Trace.traceBegin(r4, r3)
            com.android.wm.shell.startingsurface.StartingWindowTypeAlgorithm r3 = r1.mStartingWindowTypeAlgorithm
            int r3 = r3.getSuggestedWindowType(r7)
            android.app.ActivityManager$RunningTaskInfo r15 = r7.taskInfo
            r4 = 4
            r5 = 3
            if (r3 == r2) goto L_0x0033
            if (r3 == r5) goto L_0x0033
            if (r3 != r4) goto L_0x0032
            goto L_0x0033
        L_0x0032:
            r2 = 0
        L_0x0033:
            if (r2 == 0) goto L_0x02b8
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer r2 = r1.mStartingSurfaceDrawer
            java.util.Objects.requireNonNull(r2)
            android.app.ActivityManager$RunningTaskInfo r5 = r7.taskInfo
            android.content.pm.ActivityInfo r6 = r7.targetActivityInfo
            if (r6 == 0) goto L_0x0041
            goto L_0x0043
        L_0x0041:
            android.content.pm.ActivityInfo r6 = r5.topActivityInfo
        L_0x0043:
            if (r6 == 0) goto L_0x02b2
            java.lang.String r8 = r6.packageName
            if (r8 != 0) goto L_0x004b
            goto L_0x02b2
        L_0x004b:
            int r8 = r5.displayId
            int r14 = r5.taskId
            int r9 = r7.splashScreenThemeResId
            if (r9 == 0) goto L_0x0054
            goto L_0x0062
        L_0x0054:
            int r9 = r6.getThemeResource()
            if (r9 == 0) goto L_0x005f
            int r9 = r6.getThemeResource()
            goto L_0x0062
        L_0x005f:
            r9 = 16974563(0x10302e3, float:2.406297E-38)
        L_0x0062:
            boolean r10 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r10 == 0) goto L_0x00a1
            java.lang.String r10 = r6.packageName
            java.lang.String r10 = java.lang.String.valueOf(r10)
            java.lang.String r11 = java.lang.Integer.toHexString(r9)
            java.lang.String r11 = java.lang.String.valueOf(r11)
            long r12 = (long) r14
            r39 = r0
            r16 = r1
            long r0 = (long) r3
            r17 = r15
            com.android.wm.shell.protolog.ShellProtoLogGroup r15 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r18 = r7
            java.lang.Object[] r7 = new java.lang.Object[r4]
            r19 = 0
            r7[r19] = r10
            r10 = 1
            r7[r10] = r11
            java.lang.Long r10 = java.lang.Long.valueOf(r12)
            r11 = 2
            r7[r11] = r10
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r1 = 3
            r7[r1] = r0
            r0 = -1859822367(0xffffffff91255ce1, float:-1.3044825E-28)
            r1 = 80
            r10 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r15, r0, r1, r10, r7)
            goto L_0x00a9
        L_0x00a1:
            r39 = r0
            r16 = r1
            r18 = r7
            r17 = r15
        L_0x00a9:
            android.hardware.display.DisplayManager r0 = r2.mDisplayManager
            android.view.Display r0 = r0.getDisplay(r8)
            if (r0 != 0) goto L_0x00b2
            goto L_0x00bf
        L_0x00b2:
            if (r8 != 0) goto L_0x00b7
            android.content.Context r1 = r2.mContext
            goto L_0x00bd
        L_0x00b7:
            android.content.Context r1 = r2.mContext
            android.content.Context r1 = r1.createDisplayContext(r0)
        L_0x00bd:
            if (r1 != 0) goto L_0x00c3
        L_0x00bf:
            r39 = r3
            goto L_0x079b
        L_0x00c3:
            int r7 = r1.getThemeResId()
            java.lang.String r15 = "ShellStartingWindow"
            if (r9 == r7) goto L_0x00f9
            java.lang.String r7 = r6.packageName     // Catch:{ NameNotFoundException -> 0x00db }
            int r10 = r5.userId     // Catch:{ NameNotFoundException -> 0x00db }
            android.os.UserHandle r10 = android.os.UserHandle.of(r10)     // Catch:{ NameNotFoundException -> 0x00db }
            android.content.Context r1 = r1.createPackageContextAsUser(r7, r4, r10)     // Catch:{ NameNotFoundException -> 0x00db }
            r1.setTheme(r9)     // Catch:{ NameNotFoundException -> 0x00db }
            goto L_0x00f9
        L_0x00db:
            r0 = move-exception
            java.lang.String r1 = "Failed creating package context with package name "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r2 = r6.packageName
            r1.append(r2)
            java.lang.String r2 = " for user "
            r1.append(r2)
            int r2 = r5.userId
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Slog.w(r15, r1, r0)
            goto L_0x00bf
        L_0x00f9:
            android.content.res.Configuration r4 = r5.getConfiguration()
            android.content.res.Resources r5 = r1.getResources()
            android.content.res.Configuration r5 = r5.getConfiguration()
            int r5 = r4.diffPublicOnly(r5)
            if (r5 == 0) goto L_0x0171
            boolean r5 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r5 == 0) goto L_0x0123
            java.lang.String r5 = java.lang.String.valueOf(r4)
            com.android.wm.shell.protolog.ShellProtoLogGroup r7 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]
            r11 = 0
            r10[r11] = r5
            r5 = 301074142(0x11f206de, float:3.81851E-28)
            r12 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r7, r5, r11, r12, r10)
            goto L_0x0124
        L_0x0123:
            r11 = 0
        L_0x0124:
            android.content.Context r5 = r1.createConfigurationContext(r4)
            r5.setTheme(r9)
            int[] r7 = com.android.internal.R.styleable.Window
            android.content.res.TypedArray r7 = r5.obtainStyledAttributes(r7)
            r9 = 1
            int r9 = r7.getResourceId(r9, r11)
            if (r9 == 0) goto L_0x016e
            android.graphics.drawable.Drawable r9 = r5.getDrawable(r9)     // Catch:{ NotFoundException -> 0x0157 }
            if (r9 == 0) goto L_0x016e
            boolean r1 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled     // Catch:{ NotFoundException -> 0x0157 }
            if (r1 == 0) goto L_0x0155
            java.lang.String r1 = java.lang.String.valueOf(r4)     // Catch:{ NotFoundException -> 0x0157 }
            com.android.wm.shell.protolog.ShellProtoLogGroup r4 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW     // Catch:{ NotFoundException -> 0x0157 }
            r9 = 1726817767(0x66ed25e7, float:5.5995E23)
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ NotFoundException -> 0x0157 }
            r11 = 0
            r10[r11] = r1     // Catch:{ NotFoundException -> 0x0157 }
            r1 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r4, r9, r11, r1, r10)     // Catch:{ NotFoundException -> 0x0157 }
        L_0x0155:
            r1 = r5
            goto L_0x016e
        L_0x0157:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "failed creating starting window for overrideConfig at taskId: "
            r1.append(r2)
            r1.append(r14)
            java.lang.String r1 = r1.toString()
            android.util.Slog.w(r15, r1, r0)
            goto L_0x00bf
        L_0x016e:
            r7.recycle()
        L_0x0171:
            android.view.WindowManager$LayoutParams r7 = new android.view.WindowManager$LayoutParams
            r4 = 3
            r7.<init>(r4)
            r4 = 0
            r7.setFitInsetsSides(r4)
            r7.setFitInsetsTypes(r4)
            r5 = -3
            r7.format = r5
            r5 = 16843008(0x1010100, float:2.3694275E-38)
            int[] r9 = com.android.internal.R.styleable.Window
            android.content.res.TypedArray r9 = r1.obtainStyledAttributes(r9)
            r10 = 14
            boolean r10 = r9.getBoolean(r10, r4)
            if (r10 == 0) goto L_0x0195
            r5 = 17891584(0x1110100, float:2.663301E-38)
        L_0x0195:
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = 4
            if (r3 != r11) goto L_0x01a2
            r11 = 33
            boolean r11 = r9.getBoolean(r11, r4)
            if (r11 == 0) goto L_0x01a3
        L_0x01a2:
            r5 = r5 | r10
        L_0x01a3:
            r10 = 50
            int r11 = r7.layoutInDisplayCutoutMode
            int r10 = r9.getInt(r10, r11)
            r7.layoutInDisplayCutoutMode = r10
            r10 = 8
            int r4 = r9.getResourceId(r10, r4)
            r7.windowAnimations = r4
            r9.recycle()
            r4 = r18
            if (r8 != 0) goto L_0x01c3
            boolean r8 = r4.isKeyguardOccluded
            if (r8 == 0) goto L_0x01c3
            r8 = 524288(0x80000, float:7.34684E-40)
            r5 = r5 | r8
        L_0x01c3:
            r8 = 131096(0x20018, float:1.83705E-40)
            r5 = r5 | r8
            r7.flags = r5
            r5 = r39
            r7.token = r5
            java.lang.String r8 = r6.packageName
            r7.packageName = r8
            int r8 = r7.privateFlags
            r8 = r8 | 16
            r7.privateFlags = r8
            android.content.res.Resources r8 = r1.getResources()
            android.content.res.CompatibilityInfo r8 = r8.getCompatibilityInfo()
            boolean r8 = r8.supportsScreen()
            if (r8 != 0) goto L_0x01eb
            int r8 = r7.privateFlags
            r8 = r8 | 128(0x80, float:1.794E-43)
            r7.privateFlags = r8
        L_0x01eb:
            java.lang.String r8 = "Splash Screen "
            java.lang.StringBuilder r8 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r8)
            java.lang.String r6 = r6.packageName
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            r7.setTitle(r6)
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$SplashScreenViewSupplier r6 = new com.android.wm.shell.startingsurface.StartingSurfaceDrawer$SplashScreenViewSupplier
            r6.<init>()
            android.widget.FrameLayout r13 = new android.widget.FrameLayout
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer r8 = r2.mSplashscreenContentDrawer
            java.util.Objects.requireNonNull(r8)
            android.view.ContextThemeWrapper r9 = new android.view.ContextThemeWrapper
            android.content.Context r8 = r8.mContext
            android.content.res.Resources$Theme r8 = r8.getTheme()
            r9.<init>(r1, r8)
            r13.<init>(r9)
            r8 = 0
            r13.setPadding(r8, r8, r8, r8)
            r13.setFitsSystemWindows(r8)
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda3 r12 = new com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda3
            r8 = r12
            r9 = r2
            r10 = r6
            r11 = r14
            r39 = r15
            r15 = r12
            r12 = r5
            r18 = r13
            r8.<init>(r9, r10, r11, r12, r13)
            com.android.wm.shell.startingsurface.StartingSurface$SysuiProxy r8 = r2.mSysuiProxy
            if (r8 == 0) goto L_0x0248
            com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4 r8 = (com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4) r8
            java.lang.Object r8 = r8.f$0
            com.android.systemui.statusbar.phone.StatusBar r8 = (com.android.systemui.statusbar.phone.StatusBar) r8
            long[] r9 = com.android.systemui.statusbar.phone.StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.util.concurrency.DelayableExecutor r9 = r8.mMainExecutor
            com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2 r10 = new com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2
            r11 = 1
            r10.<init>(r8, r11)
            r9.execute(r10)
            goto L_0x0249
        L_0x0248:
            r11 = 1
        L_0x0249:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer r8 = r2.mSplashscreenContentDrawer
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1 r10 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1
            r9 = 4
            r10.<init>(r6, r9)
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda4 r9 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda4
            r9.<init>(r6, r11)
            java.util.Objects.requireNonNull(r8)
            android.os.Handler r11 = r8.mSplashscreenWorkerHandler
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda0 r12 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda0
            r13 = r4
            r4 = r12
            r19 = r5
            r5 = r8
            r20 = r6
            r6 = r1
            r1 = r7
            r7 = r13
            r8 = r3
            r4.<init>(r5, r6, r7, r8, r9, r10)
            r11.post(r12)
            r8 = r2
            r9 = r14
            r10 = r19
            r11 = r18
            r12 = r0
            r13 = r1
            r1 = r14
            r14 = r3
            boolean r0 = r8.addWindow(r9, r10, r11, r12, r13, r14)     // Catch:{ RuntimeException -> 0x0299 }
            if (r0 == 0) goto L_0x00bf
            android.view.Choreographer r0 = r2.mChoreographer     // Catch:{ RuntimeException -> 0x0299 }
            r4 = 2
            r5 = 0
            r0.postCallback(r4, r15, r5)     // Catch:{ RuntimeException -> 0x0299 }
            android.util.SparseArray<com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord> r0 = r2.mStartingWindowRecords     // Catch:{ RuntimeException -> 0x0299 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ RuntimeException -> 0x0299 }
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord r0 = (com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord) r0     // Catch:{ RuntimeException -> 0x0299 }
            android.window.SplashScreenView r2 = r20.get()     // Catch:{ RuntimeException -> 0x0299 }
            int r2 = r2.getInitBackgroundColor()     // Catch:{ RuntimeException -> 0x0299 }
            r0.mBGColor = r2     // Catch:{ RuntimeException -> 0x0299 }
            goto L_0x00bf
        L_0x0299:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "failed creating starting window at taskId: "
            r2.append(r4)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r2 = r39
            android.util.Slog.w(r2, r1, r0)
            goto L_0x00bf
        L_0x02b2:
            r16 = r1
            r17 = r15
            goto L_0x00bf
        L_0x02b8:
            r19 = r0
            r16 = r1
            r13 = r7
            r17 = r15
            r0 = 2
            if (r3 != r0) goto L_0x00bf
            android.window.TaskSnapshot r0 = r13.taskSnapshot
            r1 = r16
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer r2 = r1.mStartingSurfaceDrawer
            java.util.Objects.requireNonNull(r2)
            android.app.ActivityManager$RunningTaskInfo r4 = r13.taskInfo
            int r4 = r4.taskId
            android.window.StartingWindowRemovalInfo r5 = r2.mTmpRemovalInfo
            r5.taskId = r4
            r6 = 1
            r2.removeWindowSynced(r5, r6)
            com.android.wm.shell.common.ShellExecutor r5 = r2.mSplashScreenExecutor
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda2 r6 = new com.android.wm.shell.startingsurface.StartingSurfaceDrawer$$ExternalSyntheticLambda2
            r6.<init>(r2, r4)
            android.app.ActivityManager$RunningTaskInfo r7 = r13.taskInfo
            int r8 = r7.taskId
            boolean r9 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r9 == 0) goto L_0x02fa
            long r9 = (long) r8
            com.android.wm.shell.protolog.ShellProtoLogGroup r11 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r12 = 1
            java.lang.Object[] r14 = new java.lang.Object[r12]
            java.lang.Long r9 = java.lang.Long.valueOf(r9)
            r10 = 0
            r14[r10] = r9
            r9 = 1037658567(0x3dd969c7, float:0.106158786)
            r10 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r11, r9, r12, r10, r14)
        L_0x02fa:
            android.view.WindowManager$LayoutParams r9 = r13.topOpaqueWindowLayoutParams
            android.view.WindowManager$LayoutParams r10 = r13.mainWindowLayoutParams
            android.view.InsetsState r11 = r13.topOpaqueWindowInsetsState
            java.lang.String r12 = "ShellStartingWindow"
            if (r9 == 0) goto L_0x0763
            if (r10 == 0) goto L_0x0763
            if (r11 != 0) goto L_0x030a
            goto L_0x0763
        L_0x030a:
            android.view.WindowManager$LayoutParams r14 = new android.view.WindowManager$LayoutParams
            r14.<init>()
            android.view.InsetsFlags r15 = r9.insetsFlags
            int r15 = r15.appearance
            r16 = r1
            int r1 = r9.flags
            r39 = r3
            int r3 = r9.privateFlags
            r18 = r4
            java.lang.String r4 = r10.packageName
            r14.packageName = r4
            int r4 = r10.windowAnimations
            r14.windowAnimations = r4
            float r4 = r10.dimAmount
            r14.dimAmount = r4
            r4 = 3
            r14.type = r4
            android.hardware.HardwareBuffer r4 = r0.getHardwareBuffer()
            int r4 = r4.getFormat()
            r14.format = r4
            r4 = -830922809(0xffffffffce791fc7, float:-1.04490234E9)
            r4 = r4 & r1
            r4 = r4 | 8
            r4 = r4 | 16
            r14.flags = r4
            r4 = 131072(0x20000, float:1.83671E-40)
            r4 = r4 & r3
            r10 = 536870912(0x20000000, float:1.0842022E-19)
            r4 = r4 | r10
            r10 = 33554432(0x2000000, float:9.403955E-38)
            r4 = r4 | r10
            r14.privateFlags = r4
            r4 = r19
            r14.token = r4
            r10 = -1
            r14.width = r10
            r14.height = r10
            android.view.InsetsFlags r10 = r14.insetsFlags
            r10.appearance = r15
            r19 = r2
            android.view.InsetsFlags r2 = r9.insetsFlags
            int r2 = r2.behavior
            r10.behavior = r2
            int r2 = r9.layoutInDisplayCutoutMode
            r14.layoutInDisplayCutoutMode = r2
            int r2 = r9.getFitInsetsTypes()
            r14.setFitInsetsTypes(r2)
            int r2 = r9.getFitInsetsSides()
            r14.setFitInsetsSides(r2)
            boolean r2 = r9.isFitInsetsIgnoringVisibility()
            r14.setFitInsetsIgnoringVisibility(r2)
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r9 = 0
            r2[r9] = r8
            java.lang.String r8 = "SnapshotStartingWindow for taskId=%s"
            java.lang.String r2 = java.lang.String.format(r8, r2)
            r14.setTitle(r2)
            android.graphics.Point r2 = r0.getTaskSize()
            android.graphics.Rect r8 = new android.graphics.Rect
            int r10 = r2.x
            int r2 = r2.y
            r8.<init>(r9, r9, r10, r2)
            int r29 = r0.getOrientation()
            int r2 = r7.topActivityType
            int r10 = r7.displayId
            android.view.IWindowSession r34 = android.view.WindowManagerGlobal.getWindowSession()
            android.view.SurfaceControl r35 = new android.view.SurfaceControl
            r35.<init>()
            r36 = r4
            android.window.ClientWindowFrames r4 = new android.window.ClientWindowFrames
            r4.<init>()
            android.view.InsetsSourceControl[] r9 = new android.view.InsetsSourceControl[r9]
            android.util.MergedConfiguration r37 = new android.util.MergedConfiguration
            r37.<init>()
            android.app.ActivityManager$TaskDescription r7 = r7.taskDescription
            if (r7 == 0) goto L_0x03bf
            r38 = r4
            goto L_0x03ca
        L_0x03bf:
            android.app.ActivityManager$TaskDescription r7 = new android.app.ActivityManager$TaskDescription
            r7.<init>()
            r38 = r4
            r4 = -1
            r7.setBackgroundColor(r4)
        L_0x03ca:
            r24 = r7
            com.android.wm.shell.startingsurface.TaskSnapshotWindow r4 = new com.android.wm.shell.startingsurface.TaskSnapshotWindow
            java.lang.CharSequence r23 = r14.getTitle()
            r20 = r4
            r21 = r35
            r22 = r0
            r25 = r15
            r26 = r1
            r27 = r3
            r28 = r8
            r30 = r2
            r31 = r11
            r32 = r6
            r33 = r5
            r20.<init>(r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33)
            com.android.wm.shell.startingsurface.TaskSnapshotWindow$Window r0 = r4.mWindow
            android.view.InsetsState r30 = new android.view.InsetsState
            r30.<init>()
            android.view.InputChannel r26 = new android.view.InputChannel
            r26.<init>()
            r1 = 0
            java.lang.String r3 = "TaskSnapshot#addToDisplay"
            r5 = 32
            android.os.Trace.traceBegin(r5, r3)     // Catch:{ RemoteException -> 0x0433 }
            r23 = 8
            android.view.InsetsVisibilities r3 = r13.requestedVisibilities     // Catch:{ RemoteException -> 0x0433 }
            r20 = r34
            r21 = r0
            r22 = r14
            r24 = r10
            r25 = r3
            r27 = r30
            r28 = r9
            int r3 = r20.addToDisplay(r21, r22, r23, r24, r25, r26, r27, r28)     // Catch:{ RemoteException -> 0x0433 }
            android.os.Trace.traceEnd(r5)     // Catch:{ RemoteException -> 0x0433 }
            if (r3 >= 0) goto L_0x043a
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0433 }
            r7.<init>()     // Catch:{ RemoteException -> 0x0433 }
            java.lang.String r8 = "Failed to add snapshot starting window res="
            r7.append(r8)     // Catch:{ RemoteException -> 0x0433 }
            r7.append(r3)     // Catch:{ RemoteException -> 0x0433 }
            java.lang.String r3 = r7.toString()     // Catch:{ RemoteException -> 0x0433 }
            android.util.Slog.w(r12, r3)     // Catch:{ RemoteException -> 0x0433 }
            goto L_0x0784
        L_0x0431:
            r5 = 32
        L_0x0433:
            com.android.wm.shell.common.ShellExecutor r3 = r4.mSplashScreenExecutor
            java.lang.Runnable r7 = r4.mClearWindowHandler
            r3.executeDelayed(r7, r1)
        L_0x043a:
            java.util.Objects.requireNonNull(r0)
            r0.mOuter = r4
            java.lang.String r3 = "TaskSnapshot#relayout"
            android.os.Trace.traceBegin(r5, r3)     // Catch:{ RemoteException -> 0x0461 }
            r23 = -1
            r24 = -1
            r25 = 0
            r26 = 0
            r20 = r34
            r21 = r0
            r22 = r14
            r27 = r38
            r28 = r37
            r29 = r35
            r31 = r9
            r20.relayout(r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31)     // Catch:{ RemoteException -> 0x0461 }
            android.os.Trace.traceEnd(r5)     // Catch:{ RemoteException -> 0x0461 }
            goto L_0x0468
        L_0x0461:
            com.android.wm.shell.common.ShellExecutor r0 = r4.mSplashScreenExecutor
            java.lang.Runnable r3 = r4.mClearWindowHandler
            r0.executeDelayed(r3, r1)
        L_0x0468:
            r0 = r38
            android.graphics.Rect r3 = r0.frame
            int r7 = android.view.WindowInsets.Type.systemBars()
            r8 = 0
            android.graphics.Insets r3 = r11.calculateInsets(r3, r7, r8)
            android.graphics.Rect r3 = r3.toRect()
            android.graphics.Rect r0 = r0.frame
            android.graphics.Rect r7 = r4.mFrame
            r7.set(r0)
            android.graphics.Rect r0 = r4.mSystemBarInsets
            r0.set(r3)
            android.window.TaskSnapshot r0 = r4.mSnapshot
            android.hardware.HardwareBuffer r0 = r0.getHardwareBuffer()
            android.graphics.Rect r7 = r4.mFrame
            int r7 = r7.width()
            int r8 = r0.getWidth()
            if (r7 != r8) goto L_0x04a6
            android.graphics.Rect r7 = r4.mFrame
            int r7 = r7.height()
            int r0 = r0.getHeight()
            if (r7 == r0) goto L_0x04a4
            goto L_0x04a6
        L_0x04a4:
            r0 = 0
            goto L_0x04a7
        L_0x04a6:
            r0 = 1
        L_0x04a7:
            r4.mSizeMismatch = r0
            com.android.wm.shell.startingsurface.TaskSnapshotWindow$SystemBarBackgroundPainter r0 = r4.mSystemBarBackgroundPainter
            java.util.Objects.requireNonNull(r0)
            android.graphics.Rect r0 = r0.mSystemBarInsets
            r0.set(r3)
            boolean r0 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r0 == 0) goto L_0x04cd
            boolean r0 = r4.mSizeMismatch
            com.android.wm.shell.protolog.ShellProtoLogGroup r3 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r7 = 1663777552(0x632b3b10, float:3.158649E21)
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r9 = 0
            r8[r9] = r0
            r0 = 0
            r9 = 3
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r3, r7, r9, r0, r8)
        L_0x04cd:
            boolean r0 = r4.mSizeMismatch
            if (r0 == 0) goto L_0x072a
            android.window.TaskSnapshot r0 = r4.mSnapshot
            android.hardware.HardwareBuffer r0 = r0.getHardwareBuffer()
            android.view.SurfaceSession r3 = new android.view.SurfaceSession
            r3.<init>()
            int r7 = r0.getWidth()
            float r7 = (float) r7
            int r8 = r0.getHeight()
            float r8 = (float) r8
            float r7 = r7 / r8
            android.graphics.Rect r8 = r4.mFrame
            int r8 = r8.width()
            float r8 = (float) r8
            android.graphics.Rect r9 = r4.mFrame
            int r9 = r9.height()
            float r9 = (float) r9
            float r8 = r8 / r9
            float r7 = r7 - r8
            float r7 = java.lang.Math.abs(r7)
            r8 = 1008981770(0x3c23d70a, float:0.01)
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x0504
            r7 = 1
            goto L_0x0505
        L_0x0504:
            r7 = 0
        L_0x0505:
            android.view.SurfaceControl$Builder r8 = new android.view.SurfaceControl$Builder
            r8.<init>(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.CharSequence r9 = r4.mTitle
            r3.append(r9)
            java.lang.String r9 = " - task-snapshot-surface"
            r3.append(r9)
            java.lang.String r3 = r3.toString()
            android.view.SurfaceControl$Builder r3 = r8.setName(r3)
            android.view.SurfaceControl$Builder r3 = r3.setBLASTLayer()
            int r8 = r0.getFormat()
            android.view.SurfaceControl$Builder r3 = r3.setFormat(r8)
            android.view.SurfaceControl r8 = r4.mSurfaceControl
            android.view.SurfaceControl$Builder r3 = r3.setParent(r8)
            java.lang.String r8 = "TaskSnapshotWindow.drawSizeMismatchSnapshot"
            android.view.SurfaceControl$Builder r3 = r3.setCallsite(r8)
            android.view.SurfaceControl r3 = r3.build()
            android.view.SurfaceControl$Transaction r8 = r4.mTransaction
            r8.show(r3)
            r8 = 0
            if (r7 == 0) goto L_0x0608
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            android.window.TaskSnapshot r9 = r4.mSnapshot
            android.hardware.HardwareBuffer r9 = r9.getHardwareBuffer()
            int r10 = r9.getWidth()
            int r11 = r9.getHeight()
            r12 = 0
            r0.set(r12, r12, r10, r11)
            android.window.TaskSnapshot r10 = r4.mSnapshot
            android.graphics.Rect r10 = r10.getContentInsets()
            int r11 = r9.getWidth()
            float r11 = (float) r11
            android.window.TaskSnapshot r12 = r4.mSnapshot
            android.graphics.Point r12 = r12.getTaskSize()
            int r12 = r12.x
            float r12 = (float) r12
            float r11 = r11 / r12
            int r9 = r9.getHeight()
            float r9 = (float) r9
            android.window.TaskSnapshot r12 = r4.mSnapshot
            android.graphics.Point r12 = r12.getTaskSize()
            int r12 = r12.y
            float r12 = (float) r12
            float r9 = r9 / r12
            android.graphics.Rect r12 = r4.mTaskBounds
            int r12 = r12.top
            if (r12 != 0) goto L_0x058e
            android.graphics.Rect r12 = r4.mFrame
            int r12 = r12.top
            if (r12 != 0) goto L_0x058e
            r12 = 1
            goto L_0x058f
        L_0x058e:
            r12 = 0
        L_0x058f:
            int r13 = r10.left
            float r13 = (float) r13
            float r13 = r13 * r11
            int r13 = (int) r13
            if (r12 == 0) goto L_0x0598
            r12 = 0
            goto L_0x059d
        L_0x0598:
            int r12 = r10.top
            float r12 = (float) r12
            float r12 = r12 * r9
            int r12 = (int) r12
        L_0x059d:
            int r14 = r10.right
            float r14 = (float) r14
            float r14 = r14 * r11
            int r11 = (int) r14
            int r10 = r10.bottom
            float r10 = (float) r10
            float r10 = r10 * r9
            int r9 = (int) r10
            r0.inset(r13, r12, r11, r9)
            android.window.TaskSnapshot r9 = r4.mSnapshot
            android.hardware.HardwareBuffer r9 = r9.getHardwareBuffer()
            int r10 = r9.getWidth()
            float r10 = (float) r10
            android.window.TaskSnapshot r11 = r4.mSnapshot
            android.graphics.Point r11 = r11.getTaskSize()
            int r11 = r11.x
            float r11 = (float) r11
            float r10 = r10 / r11
            int r9 = r9.getHeight()
            float r9 = (float) r9
            android.window.TaskSnapshot r11 = r4.mSnapshot
            android.graphics.Point r11 = r11.getTaskSize()
            int r11 = r11.y
            float r11 = (float) r11
            float r9 = r9 / r11
            android.graphics.Rect r11 = new android.graphics.Rect
            int r12 = r0.width()
            float r12 = (float) r12
            float r12 = r12 / r10
            r10 = 1056964608(0x3f000000, float:0.5)
            float r12 = r12 + r10
            int r12 = (int) r12
            int r13 = r0.height()
            float r13 = (float) r13
            float r13 = r13 / r9
            float r13 = r13 + r10
            int r9 = (int) r13
            r10 = 0
            r11.<init>(r10, r10, r12, r9)
            android.graphics.Rect r9 = r4.mSystemBarInsets
            int r9 = r9.left
            r11.offset(r9, r10)
            android.view.SurfaceControl$Transaction r9 = r4.mTransaction
            r9.setWindowCrop(r3, r0)
            android.view.SurfaceControl$Transaction r9 = r4.mTransaction
            int r10 = r11.left
            float r10 = (float) r10
            int r12 = r11.top
            float r12 = (float) r12
            r9.setPosition(r3, r10, r12)
            android.graphics.RectF r9 = r4.mTmpSnapshotSize
            r9.set(r0)
            android.graphics.RectF r0 = r4.mTmpDstFrame
            r0.set(r11)
            goto L_0x0624
        L_0x0608:
            android.graphics.RectF r9 = r4.mTmpSnapshotSize
            int r10 = r0.getWidth()
            float r10 = (float) r10
            int r0 = r0.getHeight()
            float r0 = (float) r0
            r9.set(r8, r8, r10, r0)
            android.graphics.RectF r0 = r4.mTmpDstFrame
            android.graphics.Rect r9 = r4.mFrame
            r0.set(r9)
            android.graphics.RectF r0 = r4.mTmpDstFrame
            r0.offsetTo(r8, r8)
            r11 = 0
        L_0x0624:
            android.graphics.Matrix r0 = r4.mSnapshotMatrix
            android.graphics.RectF r9 = r4.mTmpSnapshotSize
            android.graphics.RectF r10 = r4.mTmpDstFrame
            android.graphics.Matrix$ScaleToFit r12 = android.graphics.Matrix.ScaleToFit.FILL
            r0.setRectToRect(r9, r10, r12)
            android.view.SurfaceControl$Transaction r0 = r4.mTransaction
            android.graphics.Matrix r9 = r4.mSnapshotMatrix
            float[] r10 = r4.mTmpFloat9
            r0.setMatrix(r3, r9, r10)
            android.view.SurfaceControl$Transaction r0 = r4.mTransaction
            android.window.TaskSnapshot r9 = r4.mSnapshot
            android.graphics.ColorSpace r9 = r9.getColorSpace()
            r0.setColorSpace(r3, r9)
            android.view.SurfaceControl$Transaction r0 = r4.mTransaction
            android.window.TaskSnapshot r9 = r4.mSnapshot
            android.hardware.HardwareBuffer r9 = r9.getHardwareBuffer()
            r0.setBuffer(r3, r9)
            if (r7 == 0) goto L_0x0721
            android.graphics.Rect r0 = r4.mFrame
            int r0 = r0.width()
            android.graphics.Rect r7 = r4.mFrame
            int r7 = r7.height()
            r9 = 2336(0x920, float:3.273E-42)
            r10 = 1
            android.graphics.GraphicBuffer r0 = android.graphics.GraphicBuffer.create(r0, r7, r10, r9)
            android.graphics.Canvas r7 = r0.lockCanvas()
            com.android.wm.shell.startingsurface.TaskSnapshotWindow$SystemBarBackgroundPainter r9 = r4.mSystemBarBackgroundPainter
            int r9 = r9.getStatusBarColorViewHeight()
            int r10 = r7.getWidth()
            int r12 = r11.right
            if (r10 <= r12) goto L_0x0677
            r10 = 1
            goto L_0x0678
        L_0x0677:
            r10 = 0
        L_0x0678:
            int r12 = r7.getHeight()
            int r13 = r11.bottom
            if (r12 <= r13) goto L_0x0682
            r12 = 1
            goto L_0x0683
        L_0x0682:
            r12 = 0
        L_0x0683:
            if (r10 == 0) goto L_0x06b3
            int r10 = r11.right
            float r10 = (float) r10
            int r13 = r4.mStatusBarColor
            int r13 = android.graphics.Color.alpha(r13)
            r14 = 255(0xff, float:3.57E-43)
            if (r13 != r14) goto L_0x0693
            float r8 = (float) r9
        L_0x0693:
            r22 = r8
            int r8 = r7.getWidth()
            float r8 = (float) r8
            if (r12 == 0) goto L_0x069f
            int r9 = r11.bottom
            goto L_0x06a3
        L_0x069f:
            int r9 = r7.getHeight()
        L_0x06a3:
            float r9 = (float) r9
            android.graphics.Paint r13 = r4.mBackgroundPaint
            r20 = r7
            r21 = r10
            r23 = r8
            r24 = r9
            r25 = r13
            r20.drawRect(r21, r22, r23, r24, r25)
        L_0x06b3:
            if (r12 == 0) goto L_0x06d3
            r21 = 0
            int r8 = r11.bottom
            float r8 = (float) r8
            int r9 = r7.getWidth()
            float r9 = (float) r9
            int r10 = r7.getHeight()
            float r10 = (float) r10
            android.graphics.Paint r12 = r4.mBackgroundPaint
            r20 = r7
            r22 = r8
            r23 = r9
            r24 = r10
            r25 = r12
            r20.drawRect(r21, r22, r23, r24, r25)
        L_0x06d3:
            com.android.wm.shell.startingsurface.TaskSnapshotWindow$SystemBarBackgroundPainter r8 = r4.mSystemBarBackgroundPainter
            java.util.Objects.requireNonNull(r8)
            int r9 = r8.getStatusBarColorViewHeight()
            if (r9 <= 0) goto L_0x0710
            int r10 = r8.mStatusBarColor
            int r10 = android.graphics.Color.alpha(r10)
            if (r10 == 0) goto L_0x0710
            int r10 = r7.getWidth()
            int r11 = r11.right
            if (r10 <= r11) goto L_0x0710
            android.graphics.Rect r10 = r8.mSystemBarInsets
            int r10 = r10.right
            float r10 = (float) r10
            float r12 = r8.mScale
            float r10 = r10 * r12
            int r10 = (int) r10
            float r11 = (float) r11
            r22 = 0
            int r12 = r7.getWidth()
            int r12 = r12 - r10
            float r10 = (float) r12
            float r9 = (float) r9
            android.graphics.Paint r12 = r8.mStatusBarPaint
            r20 = r7
            r21 = r11
            r23 = r10
            r24 = r9
            r25 = r12
            r20.drawRect(r21, r22, r23, r24, r25)
        L_0x0710:
            r8.drawNavigationBarBackground(r7)
            r0.unlockCanvasAndPost(r7)
            android.view.SurfaceControl$Transaction r7 = r4.mTransaction
            android.view.SurfaceControl r8 = r4.mSurfaceControl
            android.hardware.HardwareBuffer r0 = android.hardware.HardwareBuffer.createFromGraphicBuffer(r0)
            r7.setBuffer(r8, r0)
        L_0x0721:
            android.view.SurfaceControl$Transaction r0 = r4.mTransaction
            r0.apply()
            r3.release()
            goto L_0x0747
        L_0x072a:
            android.view.SurfaceControl$Transaction r0 = r4.mTransaction
            android.view.SurfaceControl r3 = r4.mSurfaceControl
            android.window.TaskSnapshot r7 = r4.mSnapshot
            android.hardware.HardwareBuffer r7 = r7.getHardwareBuffer()
            android.view.SurfaceControl$Transaction r0 = r0.setBuffer(r3, r7)
            android.view.SurfaceControl r3 = r4.mSurfaceControl
            android.window.TaskSnapshot r7 = r4.mSnapshot
            android.graphics.ColorSpace r7 = r7.getColorSpace()
            android.view.SurfaceControl$Transaction r0 = r0.setColorSpace(r3, r7)
            r0.apply()
        L_0x0747:
            r0 = 1
            r4.mHasDrawn = r0
            android.view.IWindowSession r0 = r4.mSession     // Catch:{ RemoteException -> 0x0753 }
            com.android.wm.shell.startingsurface.TaskSnapshotWindow$Window r3 = r4.mWindow     // Catch:{ RemoteException -> 0x0753 }
            r7 = 0
            r0.finishDrawing(r3, r7)     // Catch:{ RemoteException -> 0x0753 }
            goto L_0x075a
        L_0x0753:
            com.android.wm.shell.common.ShellExecutor r0 = r4.mSplashScreenExecutor
            java.lang.Runnable r3 = r4.mClearWindowHandler
            r0.executeDelayed(r3, r1)
        L_0x075a:
            r0 = 0
            r4.mSnapshot = r0
            android.view.SurfaceControl r0 = r4.mSurfaceControl
            r0.release()
            goto L_0x0785
        L_0x0763:
            r16 = r1
            r39 = r3
            r18 = r4
            r36 = r19
            r19 = r2
            r5 = 32
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "unable to create taskSnapshot surface for task: "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            android.util.Slog.w(r12, r0)
        L_0x0784:
            r4 = 0
        L_0x0785:
            if (r4 != 0) goto L_0x0788
            goto L_0x079d
        L_0x0788:
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord r0 = new com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord
            r1 = 2
            r2 = 0
            r3 = r36
            r0.<init>(r3, r2, r4, r1)
            r1 = r19
            android.util.SparseArray<com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord> r1 = r1.mStartingWindowRecords
            r2 = r18
            r1.put(r2, r0)
            goto L_0x079d
        L_0x079b:
            r5 = 32
        L_0x079d:
            if (r39 == 0) goto L_0x07eb
            r1 = r17
            int r0 = r1.taskId
            r1 = r16
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer r2 = r1.mStartingSurfaceDrawer
            java.util.Objects.requireNonNull(r2)
            android.util.SparseArray<com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord> r2 = r2.mStartingWindowRecords
            java.lang.Object r2 = r2.get(r0)
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord r2 = (com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord) r2
            if (r2 != 0) goto L_0x07b6
            r2 = 0
            goto L_0x07b8
        L_0x07b6:
            int r2 = r2.mBGColor
        L_0x07b8:
            if (r2 == 0) goto L_0x07c7
            android.util.SparseIntArray r3 = r1.mTaskBackgroundColors
            monitor-enter(r3)
            android.util.SparseIntArray r4 = r1.mTaskBackgroundColors     // Catch:{ all -> 0x07c4 }
            r4.append(r0, r2)     // Catch:{ all -> 0x07c4 }
            monitor-exit(r3)     // Catch:{ all -> 0x07c4 }
            goto L_0x07c7
        L_0x07c4:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x07c4 }
            throw r0
        L_0x07c7:
            com.android.internal.util.function.TriConsumer<java.lang.Integer, java.lang.Integer, java.lang.Integer> r1 = r1.mTaskLaunchingCallback
            if (r1 == 0) goto L_0x07eb
            r3 = 1
            r4 = r39
            if (r4 == r3) goto L_0x07d9
            r3 = 3
            if (r4 == r3) goto L_0x07d9
            r3 = 4
            if (r4 != r3) goto L_0x07d7
            goto L_0x07d9
        L_0x07d7:
            r3 = 0
            goto L_0x07da
        L_0x07d9:
            r3 = 1
        L_0x07da:
            if (r3 == 0) goto L_0x07eb
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.accept(r0, r3, r2)
        L_0x07eb:
            android.os.Trace.traceEnd(r5)
            return
        L_0x07ef:
            java.lang.Object r1 = r0.f$0
            com.google.android.setupcompat.internal.SetupCompatServiceInvoker r1 = (com.google.android.setupcompat.internal.SetupCompatServiceInvoker) r1
            java.lang.Object r2 = r0.f$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r0 = r0.f$2
            android.os.Bundle r0 = (android.os.Bundle) r0
            com.google.android.setupcompat.util.Logger r3 = com.google.android.setupcompat.internal.SetupCompatServiceInvoker.LOG
            java.util.Objects.requireNonNull(r1)
            android.content.Context r3 = r1.context     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            long r4 = r1.waitTimeInMillisForServiceConnection     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            com.google.android.setupcompat.internal.SetupCompatServiceProvider r3 = com.google.android.setupcompat.internal.SetupCompatServiceProvider.getInstance(r3)     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            com.google.android.setupcompat.ISetupCompatService r1 = r3.getService(r4, r1)     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            if (r1 == 0) goto L_0x0814
            r1.validateActivity(r2, r0)     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            goto L_0x082e
        L_0x0814:
            com.google.android.setupcompat.util.Logger r0 = com.google.android.setupcompat.internal.SetupCompatServiceInvoker.LOG     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            java.lang.String r1 = "BindBack failed since service reference is null. Are the permissions valid?"
            r0.mo18774w(r1)     // Catch:{ RemoteException | InterruptedException | TimeoutException -> 0x081c }
            goto L_0x082e
        L_0x081c:
            r0 = move-exception
            com.google.android.setupcompat.util.Logger r1 = com.google.android.setupcompat.internal.SetupCompatServiceInvoker.LOG
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r2
            java.lang.String r2 = "Exception occurred while %s trying bind back to SetupWizard."
            java.lang.String r2 = java.lang.String.format(r2, r3)
            r1.mo18772e(r2, r0)
        L_0x082e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.StartingWindowController$$ExternalSyntheticLambda0.run():void");
    }
}
