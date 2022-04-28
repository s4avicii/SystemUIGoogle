package com.android.p012wm.shell.startingsurface;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.IWindowSession;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowManagerGlobal;
import android.window.ClientWindowFrames;
import android.window.TaskSnapshot;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.DecorView;
import com.android.internal.view.BaseIWindow;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6;

/* renamed from: com.android.wm.shell.startingsurface.TaskSnapshotWindow */
public final class TaskSnapshotWindow {
    public final int mActivityType;
    public final Paint mBackgroundPaint;
    public final Runnable mClearWindowHandler;
    public final Rect mFrame = new Rect();
    public boolean mHasDrawn;
    public final boolean mHasImeSurface;
    public final int mOrientationOnCreation;
    public ScreenshotController$$ExternalSyntheticLambda6 mScheduledRunnable;
    public final IWindowSession mSession;
    public boolean mSizeMismatch;
    public TaskSnapshot mSnapshot;
    public final Matrix mSnapshotMatrix;
    public final ShellExecutor mSplashScreenExecutor;
    public final int mStatusBarColor;
    public final SurfaceControl mSurfaceControl;
    public final SystemBarBackgroundPainter mSystemBarBackgroundPainter;
    public final Rect mSystemBarInsets = new Rect();
    public final Rect mTaskBounds;
    public final CharSequence mTitle;
    public final RectF mTmpDstFrame = new RectF();
    public final float[] mTmpFloat9;
    public final RectF mTmpSnapshotSize = new RectF();
    public final SurfaceControl.Transaction mTransaction;
    public final Window mWindow;

    /* renamed from: com.android.wm.shell.startingsurface.TaskSnapshotWindow$SystemBarBackgroundPainter */
    public static class SystemBarBackgroundPainter {
        public final InsetsState mInsetsState;
        public final int mNavigationBarColor;
        public final Paint mNavigationBarPaint;
        public final float mScale;
        public final int mStatusBarColor;
        public final Paint mStatusBarPaint;
        public final Rect mSystemBarInsets = new Rect();
        public final int mWindowFlags;
        public final int mWindowPrivateFlags;

        public SystemBarBackgroundPainter(int i, int i2, int i3, ActivityManager.TaskDescription taskDescription, InsetsState insetsState) {
            boolean z;
            Paint paint = new Paint();
            this.mStatusBarPaint = paint;
            Paint paint2 = new Paint();
            this.mNavigationBarPaint = paint2;
            this.mWindowFlags = i;
            this.mWindowPrivateFlags = i2;
            this.mScale = 1.0f;
            ContextImpl systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
            int color = systemUiContext.getColor(17171091);
            int calculateBarColor = DecorView.calculateBarColor(i, 67108864, color, taskDescription.getStatusBarColor(), i3, 8, taskDescription.getEnsureStatusBarContrastWhenTransparent());
            this.mStatusBarColor = calculateBarColor;
            int navigationBarColor = taskDescription.getNavigationBarColor();
            if (!taskDescription.getEnsureNavigationBarContrastWhenTransparent() || !systemUiContext.getResources().getBoolean(17891702)) {
                z = false;
            } else {
                z = true;
            }
            int calculateBarColor2 = DecorView.calculateBarColor(i, 134217728, color, navigationBarColor, i3, 16, z);
            this.mNavigationBarColor = calculateBarColor2;
            paint.setColor(calculateBarColor);
            paint2.setColor(calculateBarColor2);
            this.mInsetsState = insetsState;
        }

        @VisibleForTesting
        public void drawNavigationBarBackground(Canvas canvas) {
            boolean z;
            Rect rect = new Rect();
            DecorView.getNavigationBarRect(canvas.getWidth(), canvas.getHeight(), this.mSystemBarInsets, rect, this.mScale);
            if ((this.mWindowPrivateFlags & 131072) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (DecorView.NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(this.mInsetsState, this.mNavigationBarColor, this.mWindowFlags, z) && Color.alpha(this.mNavigationBarColor) != 0 && !rect.isEmpty()) {
                canvas.drawRect(rect, this.mNavigationBarPaint);
            }
        }

        public final int getStatusBarColorViewHeight() {
            boolean z;
            if ((this.mWindowPrivateFlags & 131072) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (DecorView.STATUS_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(this.mInsetsState, this.mStatusBarColor, this.mWindowFlags, z)) {
                return (int) (((float) this.mSystemBarInsets.top) * this.mScale);
            }
            return 0;
        }
    }

    /* renamed from: com.android.wm.shell.startingsurface.TaskSnapshotWindow$Window */
    public static class Window extends BaseIWindow {
        public TaskSnapshotWindow mOuter;

        public final void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, boolean z2, boolean z3, int i) {
            TaskSnapshotWindow taskSnapshotWindow = this.mOuter;
            if (taskSnapshotWindow != null) {
                taskSnapshotWindow.mSplashScreenExecutor.execute(new TaskSnapshotWindow$Window$$ExternalSyntheticLambda0(this, mergedConfiguration, z));
            }
        }
    }

    public TaskSnapshotWindow(SurfaceControl surfaceControl, TaskSnapshot taskSnapshot, CharSequence charSequence, ActivityManager.TaskDescription taskDescription, int i, int i2, int i3, Rect rect, int i4, int i5, InsetsState insetsState, StartingSurfaceDrawer$$ExternalSyntheticLambda2 startingSurfaceDrawer$$ExternalSyntheticLambda2, ShellExecutor shellExecutor) {
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        this.mSnapshotMatrix = new Matrix();
        this.mTmpFloat9 = new float[9];
        this.mSplashScreenExecutor = shellExecutor;
        IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
        this.mSession = windowSession;
        Window window = new Window();
        this.mWindow = window;
        window.setSession(windowSession);
        this.mSurfaceControl = surfaceControl;
        this.mSnapshot = taskSnapshot;
        this.mTitle = charSequence;
        int backgroundColor = taskDescription.getBackgroundColor();
        paint.setColor(backgroundColor == 0 ? -1 : backgroundColor);
        this.mTaskBounds = rect;
        this.mSystemBarBackgroundPainter = new SystemBarBackgroundPainter(i2, i3, i, taskDescription, insetsState);
        this.mStatusBarColor = taskDescription.getStatusBarColor();
        this.mOrientationOnCreation = i4;
        this.mActivityType = i5;
        this.mTransaction = new SurfaceControl.Transaction();
        this.mClearWindowHandler = startingSurfaceDrawer$$ExternalSyntheticLambda2;
        this.mHasImeSurface = taskSnapshot.hasImeSurface();
    }

    public final void removeImmediately() {
        this.mSplashScreenExecutor.removeCallbacks(this.mScheduledRunnable);
        try {
            if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                boolean z = this.mHasDrawn;
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1218213214, 3, (String) null, Boolean.valueOf(z));
            }
            this.mSession.remove(this.mWindow);
        } catch (RemoteException unused) {
        }
    }
}
