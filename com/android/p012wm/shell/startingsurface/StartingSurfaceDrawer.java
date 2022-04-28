package com.android.p012wm.shell.startingsurface;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import android.window.StartingWindowRemovalInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.systemui.p006qs.tiles.CastTile$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4;
import java.util.Objects;
import java.util.function.Supplier;

/* renamed from: com.android.wm.shell.startingsurface.StartingSurfaceDrawer */
public final class StartingSurfaceDrawer {
    public final SparseArray<SurfaceControlViewHost> mAnimatedSplashScreenSurfaceHosts = new SparseArray<>(1);
    public Choreographer mChoreographer;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final ShellExecutor mSplashScreenExecutor;
    @VisibleForTesting
    public final SplashscreenContentDrawer mSplashscreenContentDrawer;
    @VisibleForTesting
    public final SparseArray<StartingWindowRecord> mStartingWindowRecords = new SparseArray<>();
    public StartingSurface.SysuiProxy mSysuiProxy;
    public final StartingWindowRemovalInfo mTmpRemovalInfo = new StartingWindowRemovalInfo();
    public final WindowManagerGlobal mWindowManagerGlobal;

    /* renamed from: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$SplashScreenViewSupplier */
    public static class SplashScreenViewSupplier implements Supplier<SplashScreenView> {
        public boolean mIsViewSet;
        public Runnable mUiThreadInitTask;
        public SplashScreenView mView;

        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0001 */
        /* JADX WARNING: Removed duplicated region for block: B:1:0x0001 A[LOOP:0: B:1:0x0001->B:16:0x0001, LOOP_START, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final android.window.SplashScreenView get() {
            /*
                r1 = this;
                monitor-enter(r1)
            L_0x0001:
                boolean r0 = r1.mIsViewSet     // Catch:{ all -> 0x0017 }
                if (r0 != 0) goto L_0x0009
                r1.wait()     // Catch:{ InterruptedException -> 0x0001 }
                goto L_0x0001
            L_0x0009:
                java.lang.Runnable r0 = r1.mUiThreadInitTask     // Catch:{ all -> 0x0017 }
                if (r0 == 0) goto L_0x0013
                r0.run()     // Catch:{ all -> 0x0017 }
                r0 = 0
                r1.mUiThreadInitTask = r0     // Catch:{ all -> 0x0017 }
            L_0x0013:
                android.window.SplashScreenView r0 = r1.mView     // Catch:{ all -> 0x0017 }
                monitor-exit(r1)     // Catch:{ all -> 0x0017 }
                return r0
            L_0x0017:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0017 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer.SplashScreenViewSupplier.get():android.window.SplashScreenView");
        }
    }

    /* renamed from: com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecord */
    public static class StartingWindowRecord {
        public final IBinder mAppToken;
        public int mBGColor;
        public SplashScreenView mContentView;
        public final long mCreateTime;
        public final View mDecorView;
        public boolean mSetSplashScreen;
        @StartingWindowInfo.StartingWindowType
        public int mSuggestType;
        public final TaskSnapshotWindow mTaskSnapshotWindow;

        public StartingWindowRecord(IBinder iBinder, View view, TaskSnapshotWindow taskSnapshotWindow, @StartingWindowInfo.StartingWindowType int i) {
            this.mAppToken = iBinder;
            this.mDecorView = view;
            this.mTaskSnapshotWindow = taskSnapshotWindow;
            if (taskSnapshotWindow != null) {
                Objects.requireNonNull(taskSnapshotWindow);
                this.mBGColor = taskSnapshotWindow.mBackgroundPaint.getColor();
            }
            this.mSuggestType = i;
            this.mCreateTime = SystemClock.uptimeMillis();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005a, code lost:
        if (r19.getParent() != null) goto L_0x0064;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0067  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean addWindow(int r17, android.os.IBinder r18, android.widget.FrameLayout r19, android.view.Display r20, android.view.WindowManager.LayoutParams r21, @android.window.StartingWindowInfo.StartingWindowType int r22) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            r10 = r19
            java.lang.String r11 = "view not successfully added to wm, removing view"
            java.lang.String r12 = "ShellStartingWindow"
            android.content.Context r0 = r19.getContext()
            r13 = 1
            r14 = 32
            java.lang.String r4 = "addRootView"
            android.os.Trace.traceBegin(r14, r4)     // Catch:{ BadTokenException -> 0x0037 }
            android.view.WindowManagerGlobal r4 = r1.mWindowManagerGlobal     // Catch:{ BadTokenException -> 0x0037 }
            r8 = 0
            int r9 = r0.getUserId()     // Catch:{ BadTokenException -> 0x0037 }
            r5 = r19
            r6 = r21
            r7 = r20
            r4.addView(r5, r6, r7, r8, r9)     // Catch:{ BadTokenException -> 0x0037 }
            android.os.Trace.traceEnd(r14)
            android.view.ViewParent r0 = r19.getParent()
            if (r0 != 0) goto L_0x0033
            goto L_0x005c
        L_0x0033:
            r0 = r13
            goto L_0x0065
        L_0x0035:
            r0 = move-exception
            goto L_0x0074
        L_0x0037:
            r0 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0035 }
            r4.<init>()     // Catch:{ all -> 0x0035 }
            r4.append(r3)     // Catch:{ all -> 0x0035 }
            java.lang.String r5 = " already running, starting window not displayed. "
            r4.append(r5)     // Catch:{ all -> 0x0035 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0035 }
            r4.append(r0)     // Catch:{ all -> 0x0035 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0035 }
            android.util.Slog.w(r12, r0)     // Catch:{ all -> 0x0035 }
            android.os.Trace.traceEnd(r14)
            android.view.ViewParent r0 = r19.getParent()
            if (r0 != 0) goto L_0x0064
        L_0x005c:
            android.util.Slog.w(r12, r11)
            android.view.WindowManagerGlobal r0 = r1.mWindowManagerGlobal
            r0.removeView(r10, r13)
        L_0x0064:
            r0 = 0
        L_0x0065:
            if (r0 == 0) goto L_0x0073
            android.window.StartingWindowRemovalInfo r4 = r1.mTmpRemovalInfo
            r4.taskId = r2
            r1.removeWindowSynced(r4, r13)
            r4 = r22
            r1.saveSplashScreenRecord(r3, r2, r10, r4)
        L_0x0073:
            return r0
        L_0x0074:
            android.os.Trace.traceEnd(r14)
            android.view.ViewParent r2 = r19.getParent()
            if (r2 != 0) goto L_0x0085
            android.util.Slog.w(r12, r11)
            android.view.WindowManagerGlobal r1 = r1.mWindowManagerGlobal
            r1.removeView(r10, r13)
        L_0x0085:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer.addWindow(int, android.os.IBinder, android.widget.FrameLayout, android.view.Display, android.view.WindowManager$LayoutParams, int):boolean");
    }

    public final void onAppSplashScreenViewRemoved(int i, boolean z) {
        String str;
        SurfaceControlViewHost surfaceControlViewHost = this.mAnimatedSplashScreenSurfaceHosts.get(i);
        if (surfaceControlViewHost != null) {
            this.mAnimatedSplashScreenSurfaceHosts.remove(i);
            if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                if (z) {
                    str = "Server cleaned up";
                } else {
                    str = "App removed";
                }
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -1158385819, 4, (String) null, str, Long.valueOf((long) i));
            }
            SplashScreenView.releaseIconHost(surfaceControlViewHost);
        }
    }

    public final void removeWindowInner(View view, boolean z) {
        StartingSurface.SysuiProxy sysuiProxy = this.mSysuiProxy;
        if (sysuiProxy != null) {
            StatusBar statusBar = (StatusBar) ((StatusBar$$ExternalSyntheticLambda4) sysuiProxy).f$0;
            long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
            Objects.requireNonNull(statusBar);
            statusBar.mMainExecutor.execute(new ScrimView$$ExternalSyntheticLambda2(statusBar, false));
        }
        if (z) {
            view.setVisibility(8);
        }
        this.mWindowManagerGlobal.removeView(view, false);
    }

    public final void removeWindowSynced(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
        long j;
        long j2;
        StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRemovalInfo;
        int i = startingWindowRemovalInfo2.taskId;
        StartingWindowRecord startingWindowRecord = this.mStartingWindowRecords.get(i);
        if (startingWindowRecord != null) {
            if (startingWindowRecord.mDecorView != null) {
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 2081268676, 1, (String) null, Long.valueOf((long) i));
                }
                SplashScreenView splashScreenView = startingWindowRecord.mContentView;
                if (splashScreenView == null) {
                    Slog.e("ShellStartingWindow", "Found empty splash screen, remove!");
                    removeWindowInner(startingWindowRecord.mDecorView, false);
                } else if (z || startingWindowRecord.mSuggestType == 4) {
                    removeWindowInner(startingWindowRecord.mDecorView, false);
                } else if (startingWindowRemovalInfo2.playRevealAnimation) {
                    SplashscreenContentDrawer splashscreenContentDrawer = this.mSplashscreenContentDrawer;
                    SurfaceControl surfaceControl = startingWindowRemovalInfo2.windowAnimationLeash;
                    Rect rect = startingWindowRemovalInfo2.mainFrame;
                    CastTile$$ExternalSyntheticLambda1 castTile$$ExternalSyntheticLambda1 = new CastTile$$ExternalSyntheticLambda1(this, startingWindowRecord, 5);
                    long j3 = startingWindowRecord.mCreateTime;
                    Objects.requireNonNull(splashscreenContentDrawer);
                    long j4 = j3;
                    SplashscreenContentDrawer$$ExternalSyntheticLambda1 splashscreenContentDrawer$$ExternalSyntheticLambda1 = r13;
                    SplashscreenContentDrawer$$ExternalSyntheticLambda1 splashscreenContentDrawer$$ExternalSyntheticLambda12 = new SplashscreenContentDrawer$$ExternalSyntheticLambda1(splashscreenContentDrawer, splashScreenView, surfaceControl, rect, castTile$$ExternalSyntheticLambda1);
                    if (splashScreenView.getIconView() == null) {
                        splashscreenContentDrawer$$ExternalSyntheticLambda1.run();
                    } else {
                        long uptimeMillis = SystemClock.uptimeMillis() - j4;
                        if (splashScreenView.getIconAnimationDuration() != null) {
                            j2 = splashScreenView.getIconAnimationDuration().toMillis();
                        } else {
                            j2 = 0;
                        }
                        long showingDuration = SplashscreenContentDrawer.getShowingDuration(j2, uptimeMillis) - uptimeMillis;
                        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                            String valueOf = String.valueOf(showingDuration);
                            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 482713286, 0, (String) null, valueOf);
                        }
                        if (showingDuration > 0) {
                            splashScreenView.postDelayed(splashscreenContentDrawer$$ExternalSyntheticLambda1, showingDuration);
                        } else {
                            splashscreenContentDrawer$$ExternalSyntheticLambda1.run();
                        }
                    }
                } else {
                    removeWindowInner(startingWindowRecord.mDecorView, true);
                }
                this.mStartingWindowRecords.remove(i);
            }
            if (startingWindowRecord.mTaskSnapshotWindow != null) {
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -1612451307, 1, (String) null, Long.valueOf((long) i));
                }
                if (z) {
                    startingWindowRecord.mTaskSnapshotWindow.removeImmediately();
                    return;
                }
                TaskSnapshotWindow taskSnapshotWindow = startingWindowRecord.mTaskSnapshotWindow;
                StartingSurfaceDrawer$$ExternalSyntheticLambda1 startingSurfaceDrawer$$ExternalSyntheticLambda1 = new StartingSurfaceDrawer$$ExternalSyntheticLambda1(this, i);
                boolean z2 = startingWindowRemovalInfo2.deferRemoveForIme;
                Objects.requireNonNull(taskSnapshotWindow);
                if (taskSnapshotWindow.mActivityType == 2) {
                    taskSnapshotWindow.removeImmediately();
                    startingSurfaceDrawer$$ExternalSyntheticLambda1.run();
                    return;
                }
                ScreenshotController$$ExternalSyntheticLambda6 screenshotController$$ExternalSyntheticLambda6 = taskSnapshotWindow.mScheduledRunnable;
                if (screenshotController$$ExternalSyntheticLambda6 != null) {
                    taskSnapshotWindow.mSplashScreenExecutor.removeCallbacks(screenshotController$$ExternalSyntheticLambda6);
                    taskSnapshotWindow.mScheduledRunnable = null;
                }
                ScreenshotController$$ExternalSyntheticLambda6 screenshotController$$ExternalSyntheticLambda62 = new ScreenshotController$$ExternalSyntheticLambda6(taskSnapshotWindow, startingSurfaceDrawer$$ExternalSyntheticLambda1, 4);
                taskSnapshotWindow.mScheduledRunnable = screenshotController$$ExternalSyntheticLambda62;
                if (!taskSnapshotWindow.mHasImeSurface || !z2) {
                    j = 100;
                } else {
                    j = 600;
                }
                taskSnapshotWindow.mSplashScreenExecutor.executeDelayed(screenshotController$$ExternalSyntheticLambda62, j);
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 89657544, 1, (String) null, Long.valueOf(j));
                }
            }
        }
    }

    @VisibleForTesting
    public void saveSplashScreenRecord(IBinder iBinder, int i, View view, @StartingWindowInfo.StartingWindowType int i2) {
        this.mStartingWindowRecords.put(i, new StartingWindowRecord(iBinder, view, (TaskSnapshotWindow) null, i2));
    }

    public StartingSurfaceDrawer(Context context, ShellExecutor shellExecutor, IconProvider iconProvider, TransactionPool transactionPool) {
        this.mContext = context;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayManager = displayManager;
        this.mSplashScreenExecutor = shellExecutor;
        this.mSplashscreenContentDrawer = new SplashscreenContentDrawer(context, iconProvider, transactionPool);
        shellExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda7(this, 8));
        this.mWindowManagerGlobal = WindowManagerGlobal.getInstance();
        displayManager.getDisplay(0);
    }
}
