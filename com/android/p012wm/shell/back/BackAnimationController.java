package com.android.p012wm.shell.back;

import android.app.IActivityTaskManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.HardwareBuffer;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.BackNavigationInfo;
import android.window.IOnBackInvokedCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.back.IBackAnimation;
import com.android.p012wm.shell.bubbles.BubbleController$3$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import java.util.Objects;

/* renamed from: com.android.wm.shell.back.BackAnimationController */
public final class BackAnimationController implements RemoteCallable<BackAnimationController> {
    public static final int PROGRESS_THRESHOLD = SystemProperties.getInt("persist.debug.back_predictability_progress_threshold", -1);
    public final IActivityTaskManager mActivityTaskManager;
    public final BackAnimationImpl mBackAnimation = new BackAnimationImpl();
    public boolean mBackGestureStarted = false;
    public BackNavigationInfo mBackNavigationInfo;
    public IOnBackInvokedCallback mBackToLauncherCallback;
    public final Context mContext;
    public final PointF mInitTouchLocation = new PointF();
    public float mProgressThreshold;
    public final ShellExecutor mShellExecutor;
    public final Point mTouchEventDelta = new Point();
    public final SurfaceControl.Transaction mTransaction;
    public boolean mTriggerBack;

    /* renamed from: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl */
    public class BackAnimationImpl implements BackAnimation {
        public IBackAnimationImpl mBackAnimation;

        public BackAnimationImpl() {
        }

        public final IBackAnimation createExternalInterface() {
            IBackAnimationImpl iBackAnimationImpl = this.mBackAnimation;
            if (iBackAnimationImpl != null) {
                Objects.requireNonNull(iBackAnimationImpl);
                iBackAnimationImpl.mController = null;
            }
            IBackAnimationImpl iBackAnimationImpl2 = new IBackAnimationImpl(BackAnimationController.this);
            this.mBackAnimation = iBackAnimationImpl2;
            return iBackAnimationImpl2;
        }

        public final void onBackMotion(MotionEvent motionEvent, int i) {
            BackAnimationController.this.mShellExecutor.execute(new BubbleController$3$$ExternalSyntheticLambda0(this, motionEvent, i, 1));
        }

        public final void setSwipeThresholds(float f, float f2) {
            BackAnimationController.this.mShellExecutor.execute(new C1788x542d5222(this, f, f2));
        }

        public final void setTriggerBack(boolean z) {
            BackAnimationController.this.mShellExecutor.execute(new C1787x542d5221(this, z));
        }
    }

    /* renamed from: com.android.wm.shell.back.BackAnimationController$IBackAnimationImpl */
    public static class IBackAnimationImpl extends IBackAnimation.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public BackAnimationController mController;

        public IBackAnimationImpl(BackAnimationController backAnimationController) {
            this.mController = backAnimationController;
        }
    }

    public final void finishAnimation() {
        SurfaceControl surfaceControl;
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m79d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -143863875, 0, "BackAnimationController: finishAnimation()", (Object[]) null);
        }
        this.mBackGestureStarted = false;
        this.mTouchEventDelta.set(0, 0);
        this.mInitTouchLocation.set(0.0f, 0.0f);
        BackNavigationInfo backNavigationInfo = this.mBackNavigationInfo;
        boolean z = this.mTriggerBack;
        this.mBackNavigationInfo = null;
        this.mTriggerBack = false;
        if (backNavigationInfo != null) {
            RemoteAnimationTarget departingAnimationTarget = backNavigationInfo.getDepartingAnimationTarget();
            if (departingAnimationTarget != null && this.mTriggerBack && (surfaceControl = departingAnimationTarget.leash) != null && surfaceControl.isValid()) {
                this.mTransaction.remove(departingAnimationTarget.leash);
            }
            SurfaceControl screenshotSurface = backNavigationInfo.getScreenshotSurface();
            if (screenshotSurface != null && screenshotSurface.isValid()) {
                this.mTransaction.remove(screenshotSurface);
            }
            this.mTransaction.apply();
            backNavigationInfo.onBackNavigationFinished(z);
        }
    }

    public final void onBackNavigationInfoReceived(BackNavigationInfo backNavigationInfo) {
        SurfaceControl surfaceControl;
        float f;
        if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
            ShellProtoLogImpl.m79d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -2134376374, 0, "Received backNavigationInfo:%s", String.valueOf(backNavigationInfo));
        }
        if (backNavigationInfo == null) {
            Log.e("BackAnimationController", "Received BackNavigationInfo is null.");
            finishAnimation();
            return;
        }
        int type = backNavigationInfo.getType();
        IOnBackInvokedCallback iOnBackInvokedCallback = null;
        if (type == 2) {
            HardwareBuffer screenshotHardwareBuffer = backNavigationInfo.getScreenshotHardwareBuffer();
            if (screenshotHardwareBuffer != null) {
                WindowConfiguration taskWindowConfiguration = backNavigationInfo.getTaskWindowConfiguration();
                BackNavigationInfo backNavigationInfo2 = this.mBackNavigationInfo;
                if (backNavigationInfo2 == null) {
                    surfaceControl = null;
                } else {
                    surfaceControl = backNavigationInfo2.getScreenshotSurface();
                }
                if (surfaceControl == null) {
                    Log.e("BackAnimationController", "BackNavigationInfo doesn't contain a surface for the screenshot. ");
                } else {
                    float width = (float) taskWindowConfiguration.getBounds().width();
                    float height = (float) taskWindowConfiguration.getBounds().height();
                    float f2 = 1.0f;
                    if (width != ((float) screenshotHardwareBuffer.getWidth())) {
                        f = width / ((float) screenshotHardwareBuffer.getWidth());
                    } else {
                        f = 1.0f;
                    }
                    if (height != ((float) screenshotHardwareBuffer.getHeight())) {
                        f2 = height / ((float) screenshotHardwareBuffer.getHeight());
                    }
                    this.mTransaction.setScale(surfaceControl, f, f2);
                    this.mTransaction.setBuffer(surfaceControl, screenshotHardwareBuffer);
                    this.mTransaction.setVisibility(surfaceControl, true);
                }
            }
            this.mTransaction.apply();
        } else if (type == 1) {
            iOnBackInvokedCallback = this.mBackToLauncherCallback;
        } else if (type == 4) {
            iOnBackInvokedCallback = this.mBackNavigationInfo.getOnBackInvokedCallback();
        }
        if (iOnBackInvokedCallback != null) {
            try {
                iOnBackInvokedCallback.onBackStarted();
            } catch (RemoteException e) {
                Log.e("BackAnimationController", "dispatchOnBackStarted error: ", e);
            }
        }
    }

    @VisibleForTesting
    public BackAnimationController(ShellExecutor shellExecutor, SurfaceControl.Transaction transaction, IActivityTaskManager iActivityTaskManager, Context context) {
        this.mShellExecutor = shellExecutor;
        this.mTransaction = transaction;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mContext = context;
    }

    @VisibleForTesting
    public void setBackToLauncherCallback(IOnBackInvokedCallback iOnBackInvokedCallback) {
        this.mBackToLauncherCallback = iOnBackInvokedCallback;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ShellExecutor getRemoteCallExecutor() {
        return this.mShellExecutor;
    }
}
