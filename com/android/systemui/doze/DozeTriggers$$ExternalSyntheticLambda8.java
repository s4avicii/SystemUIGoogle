package com.android.systemui.doze;

import com.android.internal.util.Preconditions;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.doze.DozeMachine;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ DozeTriggers f$0;
    public final /* synthetic */ Runnable f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda8(DozeTriggers dozeTriggers, ScreenDecorations$2$$ExternalSyntheticLambda0 screenDecorations$2$$ExternalSyntheticLambda0, int i) {
        this.f$0 = dozeTriggers;
        this.f$1 = screenDecorations$2$$ExternalSyntheticLambda0;
        this.f$2 = i;
    }

    public final void accept(Object obj) {
        DozeTriggers dozeTriggers = this.f$0;
        Runnable runnable = this.f$1;
        int i = this.f$2;
        Boolean bool = (Boolean) obj;
        if (bool != null) {
            Objects.requireNonNull(dozeTriggers);
            if (bool.booleanValue()) {
                dozeTriggers.mDozeLog.tracePulseDropped("inPocket");
                dozeTriggers.mPulsePending = false;
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            }
        }
        dozeTriggers.mPulsePending = false;
        if (dozeTriggers.mDozeHost.isPulsingBlocked() || !dozeTriggers.canPulse()) {
            dozeTriggers.mDozeLog.tracePulseDropped(dozeTriggers.mPulsePending, dozeTriggers.mMachine.getState(), dozeTriggers.mDozeHost.isPulsingBlocked());
            return;
        }
        DozeMachine dozeMachine = dozeTriggers.mMachine;
        Objects.requireNonNull(dozeMachine);
        Preconditions.checkState(!dozeMachine.isExecutingTransition());
        dozeMachine.requestState(DozeMachine.State.DOZE_REQUEST_PULSE, i);
    }
}
