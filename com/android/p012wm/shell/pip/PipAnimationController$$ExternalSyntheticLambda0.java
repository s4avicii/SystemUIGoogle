package com.android.p012wm.shell.pip;

import android.animation.AnimationHandler;
import android.provider.DeviceConfig;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.p006qs.tiles.MicrophoneToggleTile;
import java.util.function.Supplier;

/* renamed from: com.android.wm.shell.pip.PipAnimationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipAnimationController$$ExternalSyntheticLambda0 implements Supplier {
    public static final /* synthetic */ PipAnimationController$$ExternalSyntheticLambda0 INSTANCE = new PipAnimationController$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ PipAnimationController$$ExternalSyntheticLambda0 INSTANCE$1 = new PipAnimationController$$ExternalSyntheticLambda0(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PipAnimationController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                AnimationHandler animationHandler = new AnimationHandler();
                animationHandler.setProvider(new SfVsyncFrameCallbackProvider());
                return animationHandler;
            default:
                int i = MicrophoneToggleTile.$r8$clinit;
                return Boolean.valueOf(DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true));
        }
    }
}
