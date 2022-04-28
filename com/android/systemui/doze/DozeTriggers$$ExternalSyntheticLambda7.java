package com.android.systemui.doze;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTriggers;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ DozeTriggers f$0;
    public final /* synthetic */ DozeMachine.State f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda7(DozeTriggers dozeTriggers, DozeMachine.State state, int i) {
        this.f$0 = dozeTriggers;
        this.f$1 = state;
        this.f$2 = i;
    }

    public final void accept(Object obj) {
        DozeTriggers dozeTriggers = this.f$0;
        DozeMachine.State state = this.f$1;
        int i = this.f$2;
        Boolean bool = (Boolean) obj;
        Objects.requireNonNull(dozeTriggers);
        if ((bool == null || !bool.booleanValue()) && state == DozeMachine.State.DOZE) {
            dozeTriggers.mMachine.requestState(DozeMachine.State.DOZE_AOD);
            Optional ofNullable = Optional.ofNullable(DozeTriggers.DozingUpdateUiEvent.fromReason(i));
            UiEventLogger uiEventLogger = dozeTriggers.mUiEventLogger;
            Objects.requireNonNull(uiEventLogger);
            ofNullable.ifPresent(new DozeTriggers$$ExternalSyntheticLambda3(uiEventLogger, 0));
        }
    }
}
