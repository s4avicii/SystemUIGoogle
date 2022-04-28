package com.android.p012wm.shell.transition;

import android.os.VibrationAttributes;
import android.os.Vibrator;
import android.window.RemoteTransition;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.statusbar.phone.StatusBarCommandQueueCallbacks;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.transition.Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Transitions$IShellTransitionsImpl$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RemoteTransition remoteTransition = (RemoteTransition) this.f$0;
                RemoteTransitionHandler remoteTransitionHandler = ((Transitions) obj).mRemoteTransitionHandler;
                Objects.requireNonNull(remoteTransitionHandler);
                boolean z = false;
                for (int size = remoteTransitionHandler.mFilters.size() - 1; size >= 0; size--) {
                    if (((RemoteTransition) remoteTransitionHandler.mFilters.get(size).second).asBinder().equals(remoteTransition.asBinder())) {
                        remoteTransitionHandler.mFilters.remove(size);
                        z = true;
                    }
                }
                if (z) {
                    remoteTransitionHandler.unhandleDeath(remoteTransition.asBinder(), (Transitions.TransitionFinishCallback) null);
                    return;
                }
                return;
            default:
                StatusBarCommandQueueCallbacks statusBarCommandQueueCallbacks = (StatusBarCommandQueueCallbacks) this.f$0;
                VibrationAttributes vibrationAttributes = StatusBarCommandQueueCallbacks.HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES;
                Objects.requireNonNull(statusBarCommandQueueCallbacks);
                ((Vibrator) obj).vibrate(statusBarCommandQueueCallbacks.mCameraLaunchGestureVibrationEffect, StatusBarCommandQueueCallbacks.HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
                return;
        }
    }
}
