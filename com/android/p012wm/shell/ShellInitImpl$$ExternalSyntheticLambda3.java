package com.android.p012wm.shell;

import android.graphics.Rect;
import android.provider.DeviceConfig;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import com.android.p012wm.shell.pip.phone.PipResizeGestureHandler;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.p012wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda1;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellInitImpl$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$$ExternalSyntheticLambda3 implements Consumer {
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda3 INSTANCE = new ShellInitImpl$$ExternalSyntheticLambda3();

    public final void accept(Object obj) {
        PipTouchHandler pipTouchHandler = (PipTouchHandler) obj;
        Objects.requireNonNull(pipTouchHandler);
        pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(C1777R.bool.config_pipEnableResizeForMenu);
        pipTouchHandler.reloadResources();
        PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
        Objects.requireNonNull(pipMotionHelper);
        PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
        Objects.requireNonNull(motionBoundsState);
        PhysicsAnimator<Rect> instance = PhysicsAnimator.getInstance(motionBoundsState.mBoundsInMotion);
        pipMotionHelper.mTemporaryBoundsPhysicsAnimator = instance;
        instance.customAnimationHandler = pipMotionHelper.mSfAnimationHandlerThreadLocal.get();
        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
        Objects.requireNonNull(pipResizeGestureHandler);
        pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
        pipResizeGestureHandler.reloadResources();
        pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
        DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() {
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("pip_pinch_resize")) {
                    PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                }
            }
        });
        pipTouchHandler.mPipDismissTargetHandler.init();
        pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
        DeviceConfig.addOnPropertiesChangedListener("systemui", pipTouchHandler.mMainExecutor, new PipTouchHandler$$ExternalSyntheticLambda0(pipTouchHandler));
        pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
        DeviceConfig.addOnPropertiesChangedListener("systemui", pipTouchHandler.mMainExecutor, new PipTouchHandler$$ExternalSyntheticLambda1(pipTouchHandler));
    }
}
